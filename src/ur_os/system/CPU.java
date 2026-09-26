/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ur_os.system;

import ur_os.memory.Memory;
import ur_os.memory.MemoryInstruction;
import ur_os.memory.freememorymagament.MemorySlot;
import ur_os.process.CPUInstruction;
import ur_os.process.Instruction;
import ur_os.process.Process;
import ur_os.process.ProcessInstructionType;
import ur_os.process.ProcessState;
import ur_os.virtualmemory.SwapMemory;


/**
 *
 * @author super
 */
public class CPU {
    
    Process p;
    OS os;
    MemoryUnit mu;
    
    
    public CPU(){
        this(null,null,null);
    }
    
    public CPU(OS os, Memory m, SwapMemory s){
        this.os = os;
        if(os != null)
            this.mu = new MemoryUnit(m,s,this,os.smm);
        else
            this.mu = new MemoryUnit(m,s,this,null);
    }
    
    public CPU(Memory m, SwapMemory s){
        this(null,m,s);
    }
    
    public void setOS(OS os){
        this.os = os;
        mu.setSMM(os.smm);
    }
    
    public void addProcess(Process p){
        this.p = p;
        p.setState(ProcessState.CPU);
    }
    
    public Process getProcess(){
        return p;
    }
    
    public boolean isEmpty(){
        return p == null;
    }
    
    public void update(){
        if(!isEmpty())
            advanceInstruction();
        mu.update();
    }
    
    public MemoryUnit getMemoryUnit(){
        return mu;
    }
    
    public void addProcessToMemoryUnit(Process p){
        mu.addProcess(p);
    }
    
    public void advanceInstruction(){
        
        Instruction i = p.getCurrentInstruction();
        p.advanceInstruction();
        Process tempp;
        switch(i.getType()){
            case MEMORY:
                //executeMemoryOperation((MemoryInstruction) i);
                System.out.println("Executing Memory instruction");
                tempp = removeProcess();
                os.interrupt(InterruptType.CPU_TO_MEMORY, tempp);
                break;
                
            case IO:
                System.out.println("Executing IO instruction");
                tempp = removeProcess();
                os.interrupt(InterruptType.CPU_TO_IO, tempp);
                break;
                
            case CPU:
                executeCPUOperation((CPUInstruction) i);
                break;
            
            case END:
                tempp = removeProcess();
                os.interrupt(InterruptType.FINISH_PROCESS, tempp);
                break;
        }
        
        
    }
    
    public void interrupt(InterruptType t, Process p, Instruction i){
        os.interrupt(t, p, i);
    }
    
    public void executeCPUOperation(CPUInstruction i){
        System.out.println("Executing CPU instruction");
    }
    
    public Process removeProcess(){
        Process t = p;
        p = null;
        return t;
    }
    
    public Process extractProcess(){
        Process temp = p;
        p = null;
        return temp;
    }
    
    
    public String toString(){
        if(!isEmpty())
            return "CPU: "+p.toString();
        else
            return "CPU: Empty";
    }

    void loadSlot(MemorySlot m, MemorySlot vm) {
        mu.loadSlot(m,vm);
    }
    
    void storeSlot(MemorySlot m, MemorySlot vm) {
        mu.storeSlot(m,vm);
    }
    
    void loadPage(int frameMem, int frameSwap) {
        mu.loadPage(frameMem,frameSwap); //Bring the page from frame frameSwap in swapt to frame frameMem in memory
    }
    
    void storePage(int frameMem, int frameSwap) {
        mu.storePage(frameMem,frameSwap); //Send the page from frame frameMem in memory to frameSwap in swapt
    }
    
   
    
}
