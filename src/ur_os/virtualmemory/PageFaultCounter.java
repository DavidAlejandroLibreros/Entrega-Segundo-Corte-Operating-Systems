package ur_os.virtualmemory;

import java.util.Map;
import java.util.TreeMap;

public class PageFaultCounter {
    private static int total = 0;
    private static final Map<Integer, Integer> byProcess = new TreeMap<>();
 
    public static void add(int pid){
        total++;
        byProcess.merge(pid, 1, Integer::sum);
    }
 
    public static int getTotal(){
        return total;
    }
 
    public static int get(int pid){
        return byProcess.getOrDefault(pid, 0);
    }
 
    public static void reset(){
        total = 0;
        byProcess.clear();
    }
 
    public static void show(){
        System.out.println("Page faults: " + total);
        for (Map.Entry<Integer, Integer> e : byProcess.entrySet()) {
            System.out.println("  Process " + e.getKey() + ": " + e.getValue());
        }
    }
}
