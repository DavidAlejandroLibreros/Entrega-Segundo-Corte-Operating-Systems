/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ur_os.memory.paging;

/**
 *
 * @author super
 */
public class PageTableEntry implements Comparable{
    
    MemoryFrame frameId;
    int clock;
    boolean valid;
    boolean dirty;

    public PageTableEntry(int frameId) {
        this(frameId, false, 0);
    }
    
    public PageTableEntry(int frameId, boolean valid) {
        this(frameId, valid, 0);
    }
    
    public PageTableEntry(int frameId, boolean valid, int clock) {
        this.frameId = new MemoryFrame(frameId);
        this.valid = valid;
        dirty = false;
        this.clock = clock;
    }
    
    public void setDirty(boolean dirty){
        this.dirty = dirty;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }
    
    public int getClock(){
        return clock;
    }
    
    public void setClock(int clock){
        this.clock = clock;
    }
    
    public int getFrameId(){
        return frameId.getFrameID();
    }
    
    public void setFrameId(int frame){
        frameId.setFrameID(frame);
    }
    
    public boolean isDirty(){
        return dirty;
    }
    
    @Override
    public String toString(){
        return "Frame: "+frameId+" Valid: "+valid+" Dirty: "+dirty+" Clock: "+clock;
    }

    @Override
    public int compareTo(Object o) {
        if(o instanceof PageTableEntry){
            PageTableEntry p = (PageTableEntry)o;
            return this.getFrameId() - p.getFrameId();
        }
        return -999;
    }
    
    
}
