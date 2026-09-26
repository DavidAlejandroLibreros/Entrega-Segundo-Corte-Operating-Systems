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
public class SJF_NP extends Scheduler{

    
    SJF_NP(OS os){
        super(os);
    }
    
   
    @Override
    public void getNext(boolean cpuEmpty) {
       //ToDo
        
    }
    
    @Override
    public void newProcess(boolean cpuEmpty) {} //Non-preemtive

    @Override
    public void IOReturningProcess(boolean cpuEmpty) {} //Non-preemtive
    
}
