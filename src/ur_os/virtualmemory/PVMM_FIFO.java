/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ur_os.virtualmemory;

import java.util.LinkedList;
import ur_os.memory.paging.PageTable;

/**
 *
 * @author user
 */
public class PVMM_FIFO extends ProcessVirtualMemoryManager{

    public PVMM_FIFO(){
        type = ProcessVirtualMemoryManagerType.FIFO;
    }
    
    @Override
    public int getVictim(LinkedList<Integer> memoryAccesses, PageTable pt) {
        
        //To do
        
        return -1;
    }
    
}
