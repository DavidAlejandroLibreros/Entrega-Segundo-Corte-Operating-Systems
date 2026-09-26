/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ur_os.process;

import ur_os.memory.ProcessMemoryManager;
import ur_os.memory.contiguous.PMM_Contiguous;
import java.util.Random;
import ur_os.system.OS;

/**
 *
 * @author super
 */
public class Process implements Comparable{
    public static final int NUM_CPU_CYCLES = 3;
    public static final int MAX_CPU_CYCLES = 10;
    public static final int MAX_IO_CYCLES = 10;
    int pid;
    int time_init;
    int time_finished;
    ProcessInstructionList pil;
    ProcessState state;
    int currentScheduler;
    int size;
    int priority;
    

    ProcessMemoryManager pmm;
    
    
    public Process() {
        this(false);
    }
    
    public Process(boolean auto) {
        this(false, false, -1, 0, -1, null);
    }
    
    public Process(int pid, int time_init) {
        this(false, false, pid, time_init, -1, null);
    }
    
    public Process(int pid, int time_init, boolean autoproc) {
        this(autoproc, false, pid, time_init, -1, null);
    }
    
    public Process(int pid, int time_init, ProcessMemoryManager pmm) {
        this(false, true, pid, time_init, -1, pmm);
    }
    
    public Process(boolean autoProc, boolean autoMem, int pid, int time_init, int priority, ProcessMemoryManager pmm) {
        this.pid = pid;
        this.time_init = time_init;
        time_finished = -1;
        size = 0;
        if(pmm == null)
            this.pmm = new PMM_Contiguous();
        else
            this.pmm = pmm;
        
        pil = new ProcessInstructionList();
        Random r = new Random();
        
        if(priority >=0)
            this.priority = priority;
        else
            this.priority = r.nextInt(OS.MAX_PROCESS_PRIORITY);
        
        if(autoProc){
            pil.generateRandomInstructions(NUM_CPU_CYCLES, MAX_CPU_CYCLES, MAX_IO_CYCLES);
            //pil.generateSimpleBursts(); //Generates process with 3 bursts (CPU, IO, CPU) with 5 cycles each
            
        }
        
        
        
        state = ProcessState.NEW;
        currentScheduler = 0;
        
        
    }
    
    public Process(Process p) {
        this.pid = p.pid;
        this.time_init = p.time_init;
        this.pil = new ProcessInstructionList(p.getPBL());
        this.pmm = p.pmm;
        
    }

    public ProcessMemoryManager getPMM() {
        return pmm;
    }
    
    public void setPMM(ProcessMemoryManager pmm){
        this.pmm = pmm;
        size = pmm.getSize();
    }

    public boolean advanceInstruction(){
        return pil.advanceInstruction();
    }
    
    public Instruction getNextInstruction(){
        return pil.geNextInstruction();
    }
    
    public Instruction getCurrentInstruction(){
        return pil.getCurrentInstruction();
    }
    
    public boolean isFinished(){
        return pil.isFinished();
    }

    public void setTime_finished(int time_finished) {
        this.time_finished = time_finished;
    }
    
    public void addInstruction(Instruction pb){
        pil.addInstruction(pb);
    }
    
    public void addCPUInstructions(int n){
        for (int i = 0; i < n; i++) {
            pil.addInstruction(new CPUInstruction());
        }
    }
    
    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getTime_init() {
        return time_init;
    }

    public void setTime_init(int time_init) {
        this.time_init = time_init;
    }
    
    public ProcessInstructionList getPBL(){
        return pil;
    }

    public ProcessState getState() {
        return state;
    }

    public int getTime_finished() {
        return time_finished;
    }

    public int getTotalExecutionTime(){
        return pil.getTotalExecutionTime();
    }
    
    public void setState(ProcessState state) {
        this.state = state;
    }

    public void setSize(int size){
        this.size = size;
    }
    
    public int getSize() {
        return size;
    }
 
    public int getRemainingTimeInCurrentBurst(){
        return pil.getRemainingTimeInCurrentBurst();
    }
    
    public boolean isCurrentBurstCPU(){
        return pil.isCurrentBurstCPU();
    }

    public ProcessInstructionList getPbl() {
        return pil;
    }

    public void setPil(ProcessInstructionList pil) {
        this.pil = pil;
    }

    public int getCurrentScheduler() {
        return currentScheduler;
    }

    public void setCurrentScheduler(int currentScheduler) {
        this.currentScheduler = currentScheduler;
    }
    
    
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("PID: ");
        sb.append(pid);
        sb.append(" Size: ");
        sb.append(pmm.getSize());
        sb.append(" t_init: ");
        sb.append(time_init);
        sb.append(" t_finished: ");
        sb.append(time_finished);
        sb.append("\nPMM:\n");
        sb.append(pmm.toString());
        sb.append("\nPIL:\n");
        sb.append(pil.toString());
        
        return sb.toString();
    }
    
    

    @Override
    public int compareTo(Object o) {
        if(o instanceof Process){
            Process p = (Process)o;
            return this.getPid() - p.getPid();
        }
        
        return -1;
    }
    
    @Override
    public boolean equals(Object o){
    
        if(o instanceof Process){
            Process p = (Process)o;
            return this.getPid() == p.getPid();
        }
        
        return false;
        
    }

    public int getPriority() {
        return priority;
    }
    
    
}
