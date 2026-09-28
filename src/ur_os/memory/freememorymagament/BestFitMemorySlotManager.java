/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ur_os.memory.freememorymagament;

/**
 *
 * @author super
 */
public class BestFitMemorySlotManager extends FreeMemorySlotManager{
    
    public BestFitMemorySlotManager(int memSize){
        super(memSize);
    }
    
    @Override
    public MemorySlot getSlot(int size) {
        MemorySlot best = null;

        //Recorremos toda la lista buscando el slot más pequeño que aún alcance
        for (MemorySlot memorySlot : list) {
            if(memorySlot.canContain(size)){
                if(best == null || memorySlot.getSize() < best.getSize()){
                    best = memorySlot;
                }
            }
        }
        
        if(best == null){
            //Si ningún slot alcanza, se retorna null
            System.out.println("Error: La memoria solicitada es demasiado grande para la memoria disponible");
            return null;
            }

        if(best.getSize() == size){
            /*Si el tamaño pedido coincide exactamente con el del slot,
              se remueve el slot completo de la lista y se retorna tal cual*/
            list.remove(best);
            return best;
        }else{
            /*Si el slot es más grande de lo pedido, se parte: assignMemory
              actualiza el slot original (que sigue en la lista) y retorna
              un nuevo MemorySlot con el pedazo asignado*/
            return best.assignMemory(size);
        }

    }
    
}
