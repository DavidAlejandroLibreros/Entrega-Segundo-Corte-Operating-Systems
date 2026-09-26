/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ur_os.memory.freememorymagament;


/**
 *
 * @author super
 */
public class FirstFitMemorySlotManager extends FreeMemorySlotManager{

    public FirstFitMemorySlotManager(int memSize){
        super(memSize);
    }
    
    @Override
    public MemorySlot getSlot(int size) {
        MemorySlot m;
        for (MemorySlot memorySlot : list) {
            if(memorySlot.canContain(size)){
                if(memorySlot.getSize() == size){
                    /*If the requested amount is the slot's size, then the slot
                      is removed from the list, and the original one is sent to
                      the process
                    */
                    m = memorySlot;
                    list.remove(m);
                    return m;
                }else{
                    /*If the requested amount is not the slot's size, then a new
                      memory slot is created to be returned and the existing one
                      is updated*/
                    m = memorySlot.assignMemory(size);
                    return m;
                }
            }
        }
        
        //If there is no slot big enough to contain the requested memory, it will return null
        System.out.println("Error - Memory cannot allocate a slot big enough for the requested memory");
        return null;
    }

    
    
}
