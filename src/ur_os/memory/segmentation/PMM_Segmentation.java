/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ur_os.memory.segmentation;

import ur_os.memory.MemoryAddress;
import ur_os.memory.ProcessMemoryManager;
import ur_os.memory.MemoryManagerType;

/**
 *
 * @author super
 */
public class PMM_Segmentation extends ProcessMemoryManager{
    
    SegmentTable st;
    SegmentTable vst;
    int assignedSegments;
    int loadedSegments;

    public PMM_Segmentation(int processSize) {
        super(MemoryManagerType.SEGMENTATION,processSize);
        st = new SegmentTable(processSize);
    }

    public PMM_Segmentation(SegmentTable st) {
        this.st = st;
    }
    
    public PMM_Segmentation(PMM_Segmentation pmm) {
        super(pmm);
        if(pmm.getType() == this.getType()){
            this.st = new SegmentTable(pmm.getSt());
        }else{
            System.out.println("Error - Wrong PMM parameter");
        }
    }

    public SegmentTable getSt() {
        return st;
    }
    
    public void addSegment(int base, int limit){
        st.addSegment(base, limit);
    }
    
    public SegmentTableEntry getSegment(int i){
        return st.getSegment(i);
    }
    
    public MemoryAddress getSegmentMemoryAddressFromLocalAddress(int locAdd){
        
        return getSegmentMemoryAddressFromLocalAddress(locAdd, false);
    }
    
    public MemoryAddress getSegmentMemoryAddressFromLocalAddress(int locAdd, boolean store){
        
        return st.getSegmentMemoryAddressFromLocalAddress(locAdd, store);
    }
    
    public MemoryAddress getPhysicalMemoryAddressFromLogicalMemoryAddress(MemoryAddress m){
        
        return st.getPhysicalMemoryAddressFromLogicalMemoryAddress(m);
    }
    
     @Override
    public String toString(){
        return st.toString();
    }

    @Override
    public int getVictim() {
        return pvmm.getVictim(memoryAccesses,null);
    }
    
}
