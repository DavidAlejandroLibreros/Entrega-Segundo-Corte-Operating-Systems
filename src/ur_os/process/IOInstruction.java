/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ur_os.process;

import java.util.Random;

/**
 *
 * @author user
 */
public class IOInstruction extends Instruction{
    
    public IOInstruction(){
        super(ProcessInstructionType.IO,5);
    }
    
    public IOInstruction(boolean rand){
        this(0);
        Random r = new Random();
        this.cycleNumber = r.nextInt(10);
    }
   
    public IOInstruction(int cycleNumber){
        super(ProcessInstructionType.IO,cycleNumber);
    }
    
    public IOInstruction(Instruction i){
        this();
        if(i instanceof IOInstruction){    
            IOInstruction m =(IOInstruction)i;
            this.type = m.type;
            this.cycleNumber = m.cycleNumber;
            this.remainingCycles = m.remainingCycles;
        }
    }
    
    
}
