/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ur_os.process.planning;

import ur_os.process.Process;
import ur_os.system.InterruptType;
import ur_os.system.OS;

/**
 *
 * @author prestamour
 */
public class RoundRobin extends Scheduler{

    int q;
    int cont;
    boolean autoload; //Parameter useful for multischedulers algorithms, like Priority and MFQ
    
    RoundRobin(OS os){
        super(os);
        q = 5;
        cont=0;
        autoload = false;
    }
    
    RoundRobin(OS os, int q){
        this(os);
        this.q = q;
    }
    
    RoundRobin(OS os, int q, boolean autoload){
        this(os,q);
        this.autoload = autoload;
    }
    

    
    void resetCounter(){
        cont=0;
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
