/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ur_os.memory.freememorymagament;

import ur_os.memory.segmentation.SegmentTableEntry;
import ur_os.memory.segmentation.PMM_Segmentation;
import ur_os.memory.contiguous.SMM_Contiguous;
import java.util.ArrayList;
import java.util.LinkedList;
import ur_os.process.Process;
import ur_os.memory.ProcessMemoryManager;
import static ur_os.memory.MemoryManagerType.CONTIGUOUS;
import static ur_os.memory.MemoryManagerType.SEGMENTATION;
import ur_os.memory.contiguous.PMM_Contiguous;

/**
 *
 * @author super
 */
public abstract class FreeMemorySlotManager extends FreeMemoryManager{
    
    LinkedList<MemorySlot> list;
    
    public FreeMemorySlotManager(){
        this(ur_os.system.SystemOS.MEMORY_SIZE);
    }
    
    public FreeMemorySlotManager(int memSize){
        list = new LinkedList();
        list.add(new MemorySlot(0,memSize));
    }
    
    public abstract MemorySlot getSlot(int size);
    
    public void fuseSlots(){
        int tam = list.size();
        for (int i = 0; i < tam-1; i++) {
            if(list.get(i).getEnd()+1 == list.get(i+1).getBase()){
                list.get(i).addSlot(list.get(i+1));
                list.remove(list.get(i+1));
                tam--;
                i--;
            }
        }
        for (int i = 0; i < tam; i++) {
            if(list.get(i).getSize() == 0){
                list.remove(i);
                tam--;
                i--;
            }
        }
        
    }
    
    private void returnMemorySlot(MemorySlot m){
        
        
        int i = 0;
        //Find the slot with a higher base address than the one inserted
        while(i<list.size() && list.get(i).getBase() < m.getBase()){
            i++;
        }
        
        if(i > 0){
            i--;
        }
        
        if(i == 0 && list.get(i).getBase() > m.getBase()){//If the slot is the highest one
            list.addFirst(m);
        }else if (i == list.size()-1){//If the slot is the first
            list.getLast().addSlot(m);
        }else{
            list.add(i+1, m);
        }
        
        fuseSlots();
        
    }

    @Override
    public void reclaimMemory(Process p){
        ProcessMemoryManager pmm = p.getPMM();
        switch (pmm.getType()) {
            case SEGMENTATION:
                PMM_Segmentation pmms = (PMM_Segmentation)p.getPMM();
                ArrayList<SegmentTableEntry> list = pmms.getSt().getTable();
                for (SegmentTableEntry ste : list) {
                    this.returnMemorySlot(ste.getMemorySlot());
                }
                
                break;
            default:
            case CONTIGUOUS:
                PMM_Contiguous pmmc = (PMM_Contiguous)p.getPMM();
                this.returnMemorySlot(pmmc.getMemorySlot());
                pmmc.setValid(false);
                break;
        }
    }
    
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for (MemorySlot memorySlot : list) {
            sb.append(memorySlot.toString());
            sb.append("\n");
        }
        return sb.toString();
    }

    public int getSize() {
        return this.list.size();
    }
    
}
