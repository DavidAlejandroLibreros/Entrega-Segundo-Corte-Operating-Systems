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
 
        int victim = -1;
        int oldestLoad = Integer.MAX_VALUE;
 
        for (int page = 0; page < pt.getList().size(); page++) {
            if (pt.getList().get(page).isValid() && pt.getList().get(page).getClock() < oldestLoad) {
                victim = page;
                oldestLoad = pt.getList().get(page).getClock();
            }
        }
 
        return victim;
    }
 
}