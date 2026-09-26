/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ur_os.process.planning;

import java.util.ArrayList;
import java.util.Arrays;
import ur_os.process.Process;
import ur_os.process.ProcessState;
import ur_os.system.OS;

/**
 *
 * @author prestamour
 */
public class MFQ extends Scheduler{

    int currentScheduler;
    
    private ArrayList<Scheduler> schedulers;
    
    MFQ(OS os){
        super(os);
        currentScheduler = -1;
        schedulers = new ArrayList();
    }
    
    MFQ(OS os, Scheduler... s){ //Received multiple arrays
        this(os);
        schedulers.addAll(Arrays.asList(s));
        if(s.length > 0)
            currentScheduler = 0;
    }
    
    
    @Override
    public void addProcess(Process p){
       //ToDo
        
    }
    
    void defineCurrentScheduler(){
       //ToDo
    }
    
   
    @Override
    public void getNext(boolean cpuEmpty) {
        
        //ToDo
  
    }
    
    @Override
    public void newProcess(boolean cpuEmpty) {} //Non-preemtive in this event

    @Override
    public void IOReturningProcess(boolean cpuEmpty) {} //Non-preemtive in this event
    
}
