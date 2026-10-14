package ur_os.memory.freememorymagament;

public class FragmentationCalculator {
    
    public static int getTotalFreeMemory(FreeMemorySlotManager manager){
        int total = 0;
        for (MemorySlot slot : manager.list) {
            total += slot.getSize();
        }
        return total;
    }

    public static int getLargestFreeSlot(FreeMemorySlotManager manager){
        int largest = 0;
        for (MemorySlot slot : manager.list) {
            if(slot.getSize() > largest){
                largest = slot.getSize();
            }
        }
        return largest;
    }

    public static int calcExternalFragmentation(FreeMemorySlotManager manager){
        return getTotalFreeMemory(manager) - getLargestFreeSlot(manager);
    }

}
