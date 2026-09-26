/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ur_os.memory.segmentation;


import ur_os.memory.freememorymagament.MemorySlot;

/**
 *
 * @author super
 */
public class SegmentTableEntry {
    boolean dirty;
    MemorySlot m;
    boolean valid;

    public SegmentTableEntry(int base, int limit) {
        m = new MemorySlot(base, limit);
        dirty = false;
        valid = false;
    }
    
    public SegmentTableEntry(MemorySlot m) {
        this(m.getBase(), m.getSize());
    }
    
    public void setSegment(int base, int limit){
        m = new MemorySlot(base, limit);
    }
    
    public void setMemorySlot(MemorySlot m){
        setSegment(m.getBase(), m.getSize());
    }
    
    public MemorySlot getMemorySlot(){
        return m;
    }
    
    public void markDirty(){
        dirty = true;
    }
    
    public boolean isValid(){
        return valid;
    }
    
    public void setValid(boolean v){
        valid = v;
    }

    public int getBase() {
        return m.getBase();
    }

    public void setBase(int base) {
        this.m.setBase(base);
    }

    public int getLimit() {
        return m.getSize();
    }

    public void setLimit(int limit) {
        this.m.setSize(limit);
    }
    
    public boolean isDirty(){
        return dirty;
    }
    
    public void setDirty(){
        dirty = true;
    }
    
    @Override
    public String toString(){
        return "Base: "+m.getBase()+" Limit: "+m.getSize()+" Dirty: "+dirty;
    }
    
    
}
