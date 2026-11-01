/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ur_os.virtualmemory;

import java.util.LinkedList;
import ur_os.memory.paging.PageTable;
import java.util.Collections;
import ur_os.memory.paging.PageTableEntry;
/**
 *
 * @author user
 */
public class PVMM_LFU extends ProcessVirtualMemoryManager{

    public PVMM_LFU(){
        type = ProcessVirtualMemoryManagerType.LFU;
    }
    
    @Override
    public int getVictim(LinkedList<Integer> memoryAccesses, PageTable pt) {
        int victim = -1;
        int minCount = Integer.MAX_VALUE;
        int oldestClock = Integer.MAX_VALUE;
        int page = 0;
        for (PageTableEntry pte : pt.getList()) {
            if (pte.isValid()) {
                int count = Collections.frequency(memoryAccesses, page);
                if (count < minCount || (count == minCount && pte.getClock() < oldestClock)) {
                    victim = page;
                    minCount = count;
                    oldestClock = pte.getClock();
                }
            }
            page++;
        }
        return victim;
    }
    
}
