/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ur_os.memory.contiguous;

import ur_os.memory.ProcessMemoryManager;
import ur_os.memory.MemoryManagerType;
import ur_os.memory.SystemMemoryManager;
import ur_os.system.InterruptType;
import ur_os.system.OS;

/**
 *
 * @author super
 */
public class SMM_Contiguous extends SystemMemoryManager{
    
    public SMM_Contiguous(OS os){
        super(os);
        type = MemoryManagerType.CONTIGUOUS;
    }
    
    @Override
    public int getPhysicalAddress(int logicalAddress, ProcessMemoryManager pmm, boolean store){
        if(pmm.getType() == MemoryManagerType.CONTIGUOUS){
            PMM_Contiguous pmmc = (PMM_Contiguous)pmm;
            
            if(pmmc.isValid()){ //If the emory slot is loaded, then there is no problem, the logical address can be accessed
                int temp = pmmc.getBase();
                if(logicalAddress < pmmc.getLimit()){
                    temp += logicalAddress;
                }else{
                    System.out.println("Error - Illegal Memory Address Request");
                    temp = -1;
                }
                if(temp != -1 && store){
                    pmmc.setDirty(); //If it was a store instruction, then the slot is dirty
                }
                return temp;
            }else{
                //The memory slot is not loaded, so the PMM must request the OS to load it
                
                System.out.println("Memory slot fault!");
                getOS().interrupt(InterruptType.LOAD_SLOT, pmmc.getProcess(),pmmc.getVMemorySlot()); //Bring the slot in swap to real memory
                System.out.println("New slot allocated: "+pmmc.getMemorySlot());
                return getPhysicalAddress(logicalAddress, pmm); //Try again
                
            }
            
            
        }
        return -1;
    }
    
    
    
}
