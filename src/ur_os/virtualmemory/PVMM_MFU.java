/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ur_os.virtualmemory;

import java.util.Collections;
import java.util.LinkedList;
import ur_os.memory.paging.PageTable;
import ur_os.memory.paging.PageTableEntry;

/**
 *
 * @author user
 */
public class PVMM_MFU extends ProcessVirtualMemoryManager{

    public PVMM_MFU(){
        type = ProcessVirtualMemoryManagerType.MFU;
    }
    
    @Override
    public int getVictim(LinkedList<Integer> memoryAccesses, PageTable pt) {
        
        // Sale la página válida con MÁS accesos en el historial; en empate, la que lleva más tiempo en memoria (menor clock).
        int victim = -1;
        int maxCount = -1;
        int oldestClock = Integer.MAX_VALUE;
        int page = 0;
        for (PageTableEntry pte : pt.getList()) {
            if (pte.isValid()) {
                int count = Collections.frequency(memoryAccesses, page);
                if (count > maxCount || (count == maxCount && pte.getClock() < oldestClock)) {
                    victim = page;
                    maxCount = count;
                    oldestClock = pte.getClock();
                }
            }
            page++;
        }
        return victim;
    }
    
}
