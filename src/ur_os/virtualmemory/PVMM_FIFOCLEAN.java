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
public class PVMM_FIFOCLEAN extends ProcessVirtualMemoryManager{

    public PVMM_FIFOCLEAN(){
        type = ProcessVirtualMemoryManagerType.FIFOCLEAN;
    }

    @Override
    public int getVictim(LinkedList<Integer> memoryAccesses, PageTable pt) {

        int cleanVictim = -1;
        int oldestClean = Integer.MAX_VALUE;
        int dirtyVictim = -1;
        int oldestDirty = Integer.MAX_VALUE;

        for (int page = 0; page < pt.getList().size(); page++) {
            if (!pt.getList().get(page).isValid()) {
                continue; //Solo las paginas cargadas en memoria pueden ser victimas
            }
            int clock = pt.getList().get(page).getClock();
            if (pt.getList().get(page).isDirty()) {
                if (clock < oldestDirty) {
                    dirtyVictim = page;
                    oldestDirty = clock;
                }
            } else {
                if (clock < oldestClean) {
                    cleanVictim = page;
                    oldestClean = clock;
                }
            }
        }

        if (cleanVictim != -1) {
            return cleanVictim; //Hay una pagina limpia: no hace falta escribirla a swap
        }
        return dirtyVictim; //Todas sucias (FIFO puro), o -1 si no hay paginas cargadas
    }

}