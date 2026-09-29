package ur_os.memory.freememorymagament;

import java.util.ArrayList;
import java.util.List;

public class SegregatedFitMemorySlotManager extends FreeMemorySlotManager{

    /*La clase k agrupa los huecos con tamaño en el rango [2^k, 2^(k+1) - 1]
      Con 32 clases se cubre cualquier tamaño posible de un int*/
    private static final int NUM_CLASSES = 32;

    private final List<List<MemorySlot>> classes = new ArrayList<>();

    public SegregatedFitMemorySlotManager(int memSize){
        super(memSize);
        for (int i = 0; i < NUM_CLASSES; i++) {
            classes.add(new ArrayList<>());
        }
    }

    //Devuelve el índice de la clase de un tamaño: floor(log2(size))
    private int classOf(int size){
        return 31 - Integer.numberOfLeadingZeros(Math.max(size, 1));
    }

    /*Vacía las clases y vuelve a repartir los huecos libres actuales.
      Se hace en cada llamada porque el padre modifica list directamente al liberar memoria*/
    private void rebuildClasses(){
        for (List<MemorySlot> c : classes) {
            c.clear();
        }
        for (MemorySlot slot : list) {
            classes.get(classOf(slot.getSize())).add(slot);
        }
    }

    @Override
    public MemorySlot getSlot(int size) {
        rebuildClasses();

        int start = classOf(size);
        MemorySlot chosen = null;

        //Paso 1: en la clase propia hay que verificar que el hueco alcance
        for (MemorySlot slot : classes.get(start)) {
            if(slot.canContain(size)){
                chosen = slot;
                break;
            }
        }

        //Paso 2: en las clases superiores cualquier hueco alcanza, se toma el primero de la clase más baja no vacía
        for (int k = start + 1; chosen == null && k < NUM_CLASSES; k++) {
            List<MemorySlot> c = classes.get(k);
            if(!c.isEmpty()){
                chosen = c.get(0);
            }
        }

        if(chosen == null){
            System.out.println("Error: La memoria solicitada es demasiado grande para la memoria disponible");
            return null;
        }

        if(chosen.getSize() == size){
            //Ajuste exacto: el hueco sale completo de la lista
            list.remove(chosen);
            return chosen;
        }else{
            //El hueco es más grande: se parte, el remanente queda en la lista
            return chosen.assignMemory(size);
        }
    }

}