package ur_os.memory.freememorymagament;

import java.util.ArrayList;
import ur_os.memory.paging.PMM_Paging;
import ur_os.process.Process;
import ur_os.system.OS;

public class FragmentationCalculator {
    //Calculo de fragmentación externa, Memoria Total Libre - Memoria del hueco libre más grande
    public static int getTotalFreeMemory(FreeMemorySlotManager manager){
        int total = 0;
        for (MemorySlot slot : manager.list) {
            total += slot.getSize();
        }
        return total;
    }

    public static int getLargestFreeSlot(FreeMemorySlotManager manager){
        int largest = 0;
        for (MemorySlot slot : manager.list) {
            if(slot.getSize() > largest){
                largest = slot.getSize();
            }
        }
        return largest;
    }
    public static int calcExternalFragmentation(FreeMemorySlotManager manager){
        return getTotalFreeMemory(manager) - getLargestFreeSlot(manager);
    }

    //Cálculo de Fragmentación interna, Memoria total asignada a un proceso - Memoria realmente utilizada por el proceso ((Páginas asignadas * Tamaño de página) - Tamaño del proceso))
    public static int calcInternalFragmentation(PMM_Paging pmm){
        return pmm.getPT().getSize() * OS.PAGE_SIZE - pmm.getSize();
    }

    public static int calcInternalFragmentation(ArrayList<Process> processes){
        int total = 0;
        for (Process p : processes) {
            if(!p.isFinished() && p.getPMM() instanceof PMM_Paging){
                total += calcInternalFragmentation((PMM_Paging)p.getPMM());
            }
        }
        return total;
    }
}


