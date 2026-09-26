/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ur_os.memory.freememorymagament;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import ur_os.memory.paging.PMM_Paging;
import ur_os.memory.paging.PageTableEntry;
import ur_os.process.Process;
import ur_os.system.OS;
import ur_os.system.SystemOS;

/**
 *
 * @author super
 */
public class FreeFramesManager extends FreeMemoryManager{
    
    LinkedList<Integer> freeFrames;
    
    public FreeFramesManager(){
        this(SystemOS.MEMORY_SIZE);
    }
    
    public FreeFramesManager(int size){
        freeFrames = new LinkedList();
        int numFrames = size/OS.PAGE_SIZE;
        for (int i = 0; i < numFrames; i++) {
            freeFrames.add(i);
        }
    }
    
    public int getFrame(){
        return freeFrames.pop();
    }
    
    public void addFrame(int f){
        freeFrames.add(f);
    }
    
    public int getSize(){
        return freeFrames.size();
    }

    public void reclaimFrame(int frameID){
        this.freeFrames.add(frameID);
    }
    
    @Override
    public void reclaimMemory(Process p) {
        PMM_Paging pmm = (PMM_Paging)p.getPMM();
        
        ArrayList<PageTableEntry> pteList = pmm.getPT().getList();
        
        for (PageTableEntry pte : pteList) {
            if(pte.getFrameId() != -1){
                this.freeFrames.add(pte.getFrameId());
            }
        }
        
        //this.freeFrames.sort(new SortByPageTableEntry());
        
        java.util.Collections.sort(freeFrames);
    }
    
}

//Class for comparing integers in a list
/*class SortByPageTableEntry implements Comparator<Integer> {
 
    // Method
    // Sorting in ascending order of roll number
    public int compare(Integer a, Integer b)
    {
 
        return a.intValue() - b.intValue();
    }
}*/