/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ur_os.memory.paging;

import ur_os.memory.MemoryDivision;

/**
 *
 * @author user
 */
public class MemoryFrame implements MemoryDivision{
    
    int frameID;

    public MemoryFrame() {
        frameID = -1;
    }
    
    public MemoryFrame(int frameID) {
        this.frameID = frameID;
    }

    public int getFrameID() {
        return frameID;
    }

    public void setFrameID(int frameID) {
        this.frameID = frameID;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MemoryFrame other = (MemoryFrame) obj;
        return this.frameID == other.frameID;
    }
    
    @Override
    public String toString(){
        return ""+frameID;
    }
    
    
}
