/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ur_os.process;

/**
 *
 * @author user
 */
public abstract class Instruction {
    protected ProcessInstructionType type;
    protected int cycleNumber;
    protected int remainingCycles;
    boolean finished;
    
    public Instruction(){
        this(ProcessInstructionType.CPU, 1); //Use for CPU instructions
    }
    
    
    public Instruction(ProcessInstructionType type, int cycleNumber){
        this.type = type;
        this.cycleNumber = cycleNumber; //Use for IO instructions
        this.remainingCycles = cycleNumber;
        this.finished = false;
    }

    public ProcessInstructionType getType() {
        return type;
    }

    public int getCycleNumber() {
        return cycleNumber;
    }

    public int getRemainingCycles() {
        return remainingCycles;
    }
    
    public boolean isFinished(){
        return finished;
    }
    
    
    public boolean advanceInstruction(){
        if(remainingCycles > 0){
            this.remainingCycles--;
            if(remainingCycles == 0){
                finished = true;
            }
        }else{
            System.out.println("Error in instruction execution!");
        }
        
        return finished;
    }
    
    public String toString(){
        return "C: "+this.getCycleNumber()+" RC: "+this.remainingCycles+" T: "+this.type+" F: "+this.finished;
    }
    
    
    
}
