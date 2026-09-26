/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ur_os.memory;

import java.util.LinkedList;
import ur_os.virtualmemory.ProcessVirtualMemoryManager;
import ur_os.process.Process;
/**
 *
 * @author super
 */
public abstract class ProcessMemoryManager {
    
    MemoryManagerType type;
    int processSize;
    protected ProcessVirtualMemoryManager pvmm;
    Process p;
    protected LinkedList<Integer> memoryAccesses; //Stores pages or segments accessed by the process

    public ProcessMemoryManager() {
        this(null,MemoryManagerType.CONTIGUOUS,100);
    }
    
    public ProcessMemoryManager(MemoryManagerType type) {
        this(null,type,100);
    }
    
    public ProcessMemoryManager(MemoryManagerType type, int process_size) {
        this(null,type,process_size);
    }
    
    public ProcessMemoryManager(Process p, MemoryManagerType type, int processSize) {
        this(p,type,processSize, null);
    }
    
    public ProcessMemoryManager(Process p, MemoryManagerType type, int processSize, ProcessVirtualMemoryManager pvmm) {
        this.type = type;
        this.processSize = processSize;
        this.p = p;
        memoryAccesses = new LinkedList();
        this.pvmm = pvmm;
    }
    
    public ProcessMemoryManager(ProcessMemoryManager pmm) {
        this.type = pmm.type;
        this.processSize = pmm.processSize;
        this.p = pmm.p;
        memoryAccesses = new LinkedList(pmm.memoryAccesses);
        this.pvmm = pmm.getPVMM();
    }

    public Process getProcess() {
        return p;
    }
    
    public void addMemoryAccess(int division){
        this.memoryAccesses.add(division); //Add the segment/page accessed bu the program
    }
    
    public MemoryManagerType getType(){
        return type;
    }
 
    public int getSize(){
        return processSize;
    }

    public ProcessVirtualMemoryManager getPVMM() {
        return pvmm;
    }

    public void setPVMM(ProcessVirtualMemoryManager pvmm) {
        this.pvmm = pvmm;
    }
    
    public abstract int getVictim();
    
}
