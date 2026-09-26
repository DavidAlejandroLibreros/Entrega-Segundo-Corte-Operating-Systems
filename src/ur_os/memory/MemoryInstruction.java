/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ur_os.memory;

import java.util.Random;
import ur_os.process.Instruction;
import ur_os.process.ProcessInstructionType;

/**
 *
 * @author super
 */
public class MemoryInstruction extends Instruction{
    MemoryOperationType mtype;
    int logicalAddress;
    byte content;

    public MemoryInstruction() {
        this(MemoryOperationType.LOAD,0,(byte)0);
    }
    
    public MemoryInstruction(MemoryOperationType mtype, int logicalAddress, byte content) {
        this(mtype, logicalAddress, content ,3);
    }
    
    public MemoryInstruction(Instruction i){
        this();
        if(i instanceof MemoryInstruction){   
            MemoryInstruction m =(MemoryInstruction)i;
            this.type = m.type;
            this.cycleNumber = m.cycleNumber;
            this.remainingCycles = m.remainingCycles;
            this.mtype = m.mtype;
            this.logicalAddress = m.logicalAddress;
            this.content = m.content;
        }
    }
    
        
    public MemoryInstruction(MemoryOperationType mtype, int logicalAddress, byte content, boolean rand) {
        this(mtype, logicalAddress, content ,0);
        if(rand){
            Random r = new Random();
            this.cycleNumber = r.nextInt(10);
        }
        
    }
    
    public MemoryInstruction(MemoryOperationType mtype, int logicalAddress, byte content, int cycleNumber) {
        super(ProcessInstructionType.MEMORY,cycleNumber);
        this.mtype = mtype;
        this.logicalAddress = logicalAddress;
        this.content = content;
    }
    
    
    public MemoryOperationType getMType() {
        return mtype;
    }

    public void setMType(MemoryOperationType mtype) {
        this.mtype = mtype;
    }

    public int getLogicalAddress() {
        return logicalAddress;
    }

    public void setLogicalAddress(int logicalAddress) {
        this.logicalAddress = logicalAddress;
    }

    public byte getContent() {
        return content;
    }

    public void setContent(byte content) {
        this.content = content;
    }
    
    
    @Override
    public String toString(){
        return super.toString()+" "+mtype + "," + logicalAddress + ","+content;
    }
    
    
}
