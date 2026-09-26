/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package ur_os.system;

/**
 *
 * @author pedro.wightman
 */
public enum InterruptType {
    IO_DONE,
    FINISH_PROCESS,
    CPU_TO_IO,
    CPU_TO_MEMORY,
    MEMORY_DONE,
    SCHEDULER_CPU_TO_RQ,
    SCHEDULER_RQ_TO_CPU,
    LOAD,
    STORE,
    LOAD_SLOT,
    STORE_SLOT,
    LOAD_PAGE,
    STORE_PAGE
    
}
