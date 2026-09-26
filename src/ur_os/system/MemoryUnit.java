/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ur_os.system;

import java.util.ArrayList;
import ur_os.memory.Memory;
import ur_os.memory.MemoryInstruction;
import ur_os.memory.SystemMemoryManager;
import ur_os.memory.freememorymagament.MemorySlot;
import ur_os.process.ProcessState;
import ur_os.process.Process;
import ur_os.virtualmemory.SwapMemory;



/**
 *
 * @author super
 */
public class MemoryUnit {
    
    Memory memory;
    SwapMemory swap;
    protected ArrayList<ur_os.process.Process> processes;
    SystemMemoryManager smm;
    CPU cpu;
    
    public MemoryUnit(){
        this(new Memory(SystemOS.MEMORY_SIZE),new SwapMemory(SystemOS.MEMORY_SIZE),null,null);
    }
    
    public MemoryUnit(Memory m, SwapMemory s, CPU cpu, SystemMemoryManager smm){
        memory = m;
        swap = s;
        processes = new ArrayList();
        this.cpu = cpu;
        this.smm = smm;
    }
    
    public void addProcess(ur_os.process.Process p){
        p.setState(ProcessState.NEW_MEMORY);
        processes.add(p);
    }
    
    public void update(){
        Process temp;
        MemoryInstruction mop;
        for (int i=0; i< processes.size(); i++) {
            if(processes.get(i).getState() != ProcessState.NEW_MEMORY){
                mop = (MemoryInstruction)processes.get(i).getCurrentInstruction();
                executeMemoryOperation(processes.get(i), mop);
                if(processes.get(i).advanceInstruction()){//If the process finishes the current burst
                    System.out.println("MEMORY OPERATION EXECUTION DONE");
                    temp = processes.get(i);
                    processes.remove(processes.get(i));
                    cpu.os.interrupt(InterruptType.MEMORY_DONE, temp);
                    i--;
                }else{
                    System.out.println("MEMORY OPERATION EXECUTION - "+mop.getRemainingCycles()+" cycles left");
                }
            }else{
                processes.get(i).setState(ProcessState.MEMORY);
            }
        }
    }
    
    public void executeMemoryOperation(Process p, MemoryInstruction mop){
        int logAdd, phyAdd;
        if(mop != null){
            System.out.println("Process "+p.getPid()+" is executing "+mop);
            //mu.executeMemoryOperation(mop);
            switch(mop.getMType()){
                case LOAD:
                logAdd = mop.getLogicalAddress();
                phyAdd = smm.getPhysicalAddress(logAdd, p.getPMM());
                load(phyAdd);
               break;
               
            case STORE:
                logAdd = mop.getLogicalAddress();
                phyAdd = smm.getPhysicalAddress(logAdd, p.getPMM(),true);
                store(phyAdd,mop.getContent());
                break;
            
            }
            
        }
    }
    
    public void removeProcess(ur_os.process.Process p){
        processes.remove(p); //The process is found thanks to the equals() method
    }
    
    public String toString(){
        
        if(!processes.isEmpty()){
            StringBuilder sb = new StringBuilder();
            sb.append("MU ");
            for (ur_os.process.Process p : processes) {
                sb.append(p);
                sb.append("\n");
            }

            return sb.toString();
        
        }else
            return "MU: Empty";
    }
    
    public byte load(int physicalAddress){
        byte b = memory.get(physicalAddress);
        System.out.println("The obtained data is: "+b);
        return b;
    }
    
    public void store(int physicalAddress, byte content){
        memory.set(physicalAddress, content);
        System.out.println("The data "+memory.get(physicalAddress)+" is stored in: "+physicalAddress);
    }

    void loadSlot(MemorySlot m, MemorySlot vm) {
        int base = m.getBase();
        int vbase = vm.getBase();
        for (int i = 0; i < vm.getSize(); i++) {
            memory.set(base+i, swap.get(vbase+i)); //Assign each byte from the slot in swap to the slot in memory
        }
    }
    
    void storeSlot(MemorySlot m, MemorySlot vm) {
        int base = m.getBase();
        int vbase = vm.getBase();
        for (int i = 0; i < vm.getSize(); i++) {
            swap.set(vbase+i, memory.get(base+i)); //Assign each byte from the slot in memory to the slot in swap
        }
    }
    
    void loadPage(int frameMem, int frameSwap) {
        int base = frameMem*OS.PAGE_SIZE;
        int vbase = frameSwap*OS.PAGE_SIZE;
        for (int i = 0; i < OS.PAGE_SIZE; i++) {
            memory.set(base+i, swap.get(vbase+i)); //Assign each byte from the frame in swap to the frame in memory
        }
    }
    
    void storePage(int frameMem, int frameSwap) {
        int base = frameMem*OS.PAGE_SIZE;
        int vbase = frameSwap*OS.PAGE_SIZE;;
        for (int i = 0; i < OS.PAGE_SIZE; i++) {
            swap.set(vbase+i, memory.get(base+i)); //Assign each byte from the memory in memory to the frame in swap
        }

    }
    
    

    void setSMM(SystemMemoryManager smm) {
        this.smm = smm;
    }
    
}
