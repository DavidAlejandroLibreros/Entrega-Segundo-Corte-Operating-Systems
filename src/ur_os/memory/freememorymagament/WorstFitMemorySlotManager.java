/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ur_os.memory.freememorymagament;

/**
 *
 * @author super
 */
public class WorstFitMemorySlotManager extends FreeMemorySlotManager{
    
    public WorstFitMemorySlotManager(int memSize){
        super(memSize);
    }
    
    @Override
    public MemorySlot getSlot(int size) {
        MemorySlot worst = null;
        
        //Recorremos toda la lista buscando el slot más grande, sin importar si alcanza o no aún
        for (MemorySlot memorySlot : list) {
            if(memorySlot.canContain(size)){
                if(worst == null || memorySlot.getSize() > worst.getSize()){
                    worst = memorySlot;
                }
            }
        }

        if(worst == null){
            System.out.println("Error: La memoria solicitada es demasiado grande para la memoria disponible");
            return null;
        }

        if(worst.getSize() == size){
            list.remove(worst);
            return worst;
        }else{
            return worst.assignMemory(size);
        }
    }
    
}
