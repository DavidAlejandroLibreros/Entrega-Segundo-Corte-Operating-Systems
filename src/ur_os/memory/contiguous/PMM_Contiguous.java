/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ur_os.memory.contiguous;

import ur_os.memory.MemoryManagerType;
import ur_os.memory.ProcessMemoryManager;
import ur_os.memory.freememorymagament.MemorySlot;
import ur_os.process.Process;

/**
 *
 * @author user
 */
public class PMM_Contiguous extends ProcessMemoryManager{
    
    MemorySlot memorySlot;
    MemorySlot vMemorySlot;
    boolean dirty;
    boolean valid;
    
    public PMM_Contiguous() {
        this( null, new MemorySlot(), null,false);
    }
    
    public PMM_Contiguous(Process p, MemorySlot vm, boolean lazySwap){
        this(p, vm, null,lazySwap);
    }
    
    public PMM_Contiguous(Process p, MemorySlot vm, MemorySlot m, boolean lazySwap) {
        super(p, MemoryManagerType.CONTIGUOUS, vm.getSize());
        if(m != null){
            memorySlot = new MemorySlot(m);
            valid = true;
        }else{
            memorySlot = null;
            valid = false;
        }
        
        vMemorySlot = new MemorySlot(vm); //Swap memory allocation
        dirty = false;
    }
    
    
    public PMM_Contiguous(PMM_Contiguous pmm) {
        super(pmm);
        if(pmm.getType() == this.getType()){
            this.memorySlot = new MemorySlot(pmm.getMemorySlot());
            this.vMemorySlot = new MemorySlot(pmm.getVMemorySlot());
            this.dirty = pmm.dirty;
            this.valid = pmm.valid;
            
        }else{
            System.out.println("Error - Wrong PMM parameter");
        }
    }
    
    public int getBase() {
        return memorySlot.getBase();
    }

    public void setBase(int base) {
        memorySlot.setBase(base);
    }
    
    public int getVBase() {
        return vMemorySlot.getBase();
    }

    public void setVBase(int vbase) {
        vMemorySlot.setBase(vbase);
    }

    public int getLimit() {
        return memorySlot.getSize();
    }

    public void setLimit(int limit) {
        memorySlot.setSize(limit);
    }
    
    public MemorySlot getMemorySlot(){
        return this.memorySlot;
    }
    
    public void setMemorySlot(MemorySlot m){
        memorySlot = new MemorySlot(m);
    }

    public MemorySlot getVMemorySlot() {
        return vMemorySlot;
    }

    public void setVMemorySlot(MemorySlot vMemorySlot) {
        this.vMemorySlot = vMemorySlot;
        dirty = false;
    }

    public boolean isDirty() {
        return dirty;
    }

    public void setDirty() {
        this.dirty = true;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }
    
    @Override
    public String toString(){
        return "MemSlot: "+memorySlot+" vSlot: "+vMemorySlot+" Valid:"+this.valid+" Dirty: "+dirty;
    }

    @Override
    public int getVictim() {
        return -1; //Not a necessary method for this class
    }
    
}
