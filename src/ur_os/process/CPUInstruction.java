/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ur_os.process;

/**
 *
 * @author user
 */
public class CPUInstruction extends Instruction{
    
    public CPUInstruction(){
        super();
    }
    
    public CPUInstruction(Instruction i){
        this();
        if(i instanceof CPUInstruction){    
            CPUInstruction m =(CPUInstruction)i;
            this.type = m.type;
            this.cycleNumber = m.cycleNumber;
            this.remainingCycles = m.remainingCycles;
        }
    }
    
}
