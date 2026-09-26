/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ur_os.process;

import java.util.ArrayList;
import java.util.Random;
import ur_os.memory.MemoryInstruction;
import static ur_os.process.ProcessInstructionType.CPU;

/**
 *
 * @author super
 */
public class ProcessInstructionList {
    
    private static final int BURSTS_SIMPLE_SIM = 6;
    ArrayList<Instruction> instructions;
    Random r;
    int currentInstruction;
    boolean finished;

    public ProcessInstructionList() {
        instructions = new ArrayList();
        r = new Random();
        this.currentInstruction = 0;
        finished = false;
    }
    
    public ProcessInstructionList(ProcessInstructionList b) {
        this(b.getList());
    }
    
    public ProcessInstructionList(ArrayList<Instruction> b) {
        this();
        for (Instruction inst : b) {
            switch(inst.getType()){
                case CPU:
                    instructions.add(new CPUInstruction(inst));
                    break;
                case IO:
                    instructions.add(new IOInstruction(inst));
                    break;
                case MEMORY:
                    instructions.add(new MemoryInstruction(inst));
                    break;
            }
        }
    }
    
    public boolean isFinished(){
        return finished;
    }
    
    public boolean advanceInstruction(){
        boolean burstFinished = false;
        if(currentInstruction < instructions.size()){
            if(instructions.get(currentInstruction).advanceInstruction()){  //True means that the current instruction is finished
                
                if(currentInstruction < instructions.size()-1){
                    if(this.getCurrentInstruction().getType() != this.geNextInstruction().getType())
                        burstFinished = true; //If there is a change in instruction type, then the burst is done
                }
                
                currentInstruction++;
                
                if(currentInstruction == instructions.size()){
                    //If it was the last instruction or an END instruction, then the burst and the process's instructions are done
                    burstFinished = true;
                    finished = true;
                }
                
                return burstFinished; //Return if the existing burst is done or not
            }
        }else{
            finished = true;
            return true;
        }
        return false;  //False means that the current instruction is not finished (multicycle instruction)
    }
    
    public void addInstruction(Instruction b){
        int last = instructions.size()-1;
        //if(last >= 0 && instructions.get(last).getType() != b.getType()){
        if(last >= 0){
                instructions.add(b);
        }else if(last == -1 && b.getType() == ProcessInstructionType.CPU){
            instructions.add(b);
        }else
            System.out.println("Error in burst addition");
    }
    
    private ArrayList<Instruction> getList(){
        return this.instructions;
    }
    
    public int getRemainingTimeInCurrentBurst(){
        return instructions.get(currentInstruction).getRemainingCycles();
    }
    
    public boolean isCurrentBurstCPU(){
        return instructions.get(currentInstruction).getType() == ProcessInstructionType.CPU;
    }
    
    public void generateSimpleBursts(){
        
        Instruction temp = new CPUInstruction();    
        addInstruction(temp);
        temp = new IOInstruction(BURSTS_SIMPLE_SIM);    
        addInstruction(temp);
        temp = new CPUInstruction();    
        addInstruction(temp);
        
    }
    
    
    public void generateRandomInstructions(int numCPUBursts, int maxCPUCycles, int maxIOCycles){
    
        
        if(numCPUBursts > 0){
            Instruction temp;
            int temp_val;
            do{
                temp_val = r.nextInt(maxCPUCycles);
            }while(temp_val == 0);
            temp = new CPUInstruction();
            for (int i = 0; i < temp_val; i++) {
                addInstruction(temp);   //Add several CPU instructions 
            }
            
            for (int i = 1; i < numCPUBursts; i++) {
                do{
                    temp_val = r.nextInt(maxIOCycles);
                }while(temp_val == 0);
                temp = new IOInstruction(temp_val);    
                addInstruction(temp);
                
                temp = new CPUInstruction();
                for (i = 0; i < temp_val; i++) {
                    addInstruction(temp);   //Add several CPU instructions 
                }
            }
            
        }else{
            System.out.println("Error - Burst list empty!");
        }
        
    }
    
    public int getTotalExecutionTime(){
        int tot = 0;
        
        for (Instruction inst : instructions) {
            tot = tot + inst.getCycleNumber();
        }
        
        return tot;
    }
    
    @Override
    public String toString(){
       StringBuilder sb = new StringBuilder();
       
       sb.append("Current instruction: ");
       sb.append(this.currentInstruction);
       sb.append("\n");
       sb.append("Instruction list: ");
        for (Instruction inst : instructions) {
            sb.append(inst.toString());
            sb.append("\n");
        }
       return sb.toString();
    }

    Instruction geNextInstruction() {
        if(!finished)
            if(currentInstruction < instructions.size()-1)
                return instructions.get(currentInstruction+1);
            else
                return null;
        else
            return null;
            
    }

    Instruction getCurrentInstruction() {
        
        if(!finished)
            return instructions.get(currentInstruction);
        else
            return null;
    }
    
}
 