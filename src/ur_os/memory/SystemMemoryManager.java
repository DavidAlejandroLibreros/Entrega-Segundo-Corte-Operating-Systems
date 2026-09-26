/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ur_os.memory;

import ur_os.system.OS;

/**
 *
 * @author user
 */
public abstract class SystemMemoryManager {
    
    protected MemoryManagerType type;
    OS os;
    
    public SystemMemoryManager(OS os){
        this.os = os;
    }
    
    public int getPhysicalAddress(int logicalAddress, ProcessMemoryManager pmm){
        return getPhysicalAddress(logicalAddress, pmm, false);
    }
    
    public abstract int getPhysicalAddress(int logicalAddress, ProcessMemoryManager pmm, boolean store); //Important to mark as dirty the changed memory
    
    
    public MemoryManagerType getType(){
        return type;
    }
    
    public OS getOS(){
        return os;
    }
    
}
