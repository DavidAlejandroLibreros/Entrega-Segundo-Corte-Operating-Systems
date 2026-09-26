/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ur_os.memory.paging;

import ur_os.memory.MemoryAddress;
import ur_os.memory.MemoryManagerType;
import ur_os.memory.ProcessMemoryManager;
import ur_os.memory.SystemMemoryManager;
import ur_os.system.InterruptType;
import ur_os.system.OS;

/**
 *
 * @author user
 */
public class SMM_Paging extends SystemMemoryManager{

    public SMM_Paging(OS os) {
        super(os);
        type = MemoryManagerType.PAGING;
    }
    
    
    
    @Override
    public int getPhysicalAddress(int logicalAddress, ProcessMemoryManager pmm, boolean store){
        
        if(pmm.getType() == MemoryManagerType.PAGING){
            PMM_Paging pmmp = (PMM_Paging)pmm;
        
            //INCLUDE THE STORE VALUE TO MARK DIRTY THE PAGE
            MemoryAddress la = pmmp.getPageMemoryAddressFromLocalAddress(logicalAddress);
            
            MemoryAddress pa = pmmp.getFrameMemoryAddressFromLogicalMemoryAddress(la);
            
            
            //Only valid for Virtual Memory
            if(pa == null){
                //There was a page fault, so the page needs to be brought to memory from swap
                
                int pageVictim = pmmp.getVictim(); //Find a page that needs to leave memory if there is no space
                int frameVictimInSwap;
                
                int frameVictim;
                
                if(pageVictim == -1){ //If no victim was found because there are still frames available
                    frameVictim = getOS().getFreeFrame(); //Obtain a new free frame to store the page from swap
                    frameVictimInSwap = -1;
                }else{//If there are no free frames, then a pageVictim was selected
                    frameVictim = pmmp.getFrameMemoryAddressFromLogicalMemoryAddress(pageVictim); //Find the frame number in memory of the victim page
                    frameVictimInSwap = pmmp.getVFrameMemoryAddressFromLogicalMemoryAddress(pageVictim); //Find the frame number in Swap memory of the victim page
                }
                
                int pageToLoad = la.getDivision(); //Get the pageID of the desired page
                int frameToLoadInSwap= pmmp.getVFrameMemoryAddressFromLogicalMemoryAddress(pageToLoad); //Find the frame number in Swap memory of the desired page
                
                MemoryPageExchange mpe = new MemoryPageExchange(pageVictim, frameVictimInSwap, frameVictim, pageToLoad, frameToLoadInSwap);
                
                if(pageVictim != -1){ //If there was a page identified to leave memory, then it may have to be sent to swap memory
                    if(pmmp.isPageDirty(pageVictim)){ //If the page is dirty, then it must be updated in the swap memory
                        mpe.setFullExchange(true); //This is a full exchange, so the frame does not have to be freed
                        getOS().interrupt(InterruptType.STORE_PAGE, pmmp.getProcess(),mpe); //Send the frame in memory of page la to swap memory. All the information needed is in mpe
                        pmmp.setPageValid(mpe.getFrameVictim(),false); //Set the newly unloaded page as invalid
                    }
                }
                
                getOS().interrupt(InterruptType.LOAD_PAGE, pmmp.getProcess(),mpe); //Load the frame in swap of page la to real memory
                pmmp.setFrameID(pageToLoad, frameVictim);
                pmmp.getPT().getList().get(pageToLoad).setClock(getOS().getClock());//Set the loading time to the page
                return getPhysicalAddress(logicalAddress, pmm, store); //Try again!
            }else{
                if(store){
                    pmmp.setPageDirty(pa.getDivision(),true); //Set the accessed page for storage as dirty
                }
                return pa.getAddress();
            }
            
            
        
        }
        return -1;
    };
    
}
