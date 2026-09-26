/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ur_os.memory;

import java.util.LinkedList;
import java.util.Random;

/**
 *
 * @author super
 */
public class MemoryOperationList {
    LinkedList<MemoryInstruction> mol;
    Random r;
    public static final int MAX_SIZE_SIMPLE_LIST = 10;

    public MemoryOperationList() {
        mol = new LinkedList();
        r = new Random();
    }
    
    public void generateSimpleMemoryOperations(int processSize){
        MemoryOperationType mtype;
        byte b;
        
        for (int i = 0; i < MAX_SIZE_SIMPLE_LIST; i++) {
            mtype = r.nextInt() % 2 == 0 ? MemoryOperationType.LOAD :MemoryOperationType.STORE;
            if(mtype == MemoryOperationType.STORE){
                b = (byte)(r.nextInt()%128);
            }else{
                b=0;
            }
            
            mol.add(new MemoryInstruction(mtype, r.nextInt(processSize),b));
        }
    }
    
    public void add(MemoryInstruction m){
        mol.add(m);
    }
    
    public MemoryInstruction getNext(){
        if(mol.size() > 0)
            return mol.remove();
        else
            return null;
    }
    
    public int getSize(){
        return mol.size();
    }
    
    @Override
    public String toString(){
       StringBuilder sb = new StringBuilder();
       
        for (MemoryInstruction memoryOperation : mol) {
            sb.append(memoryOperation.toString());
            sb.append("\n");
        }
       
       return sb.toString();
    }
    
}
