/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ur_os.memory.paging;

/**
 *
 * @author user
 */
public class MemoryPageExchange {
    
    int pageVictim; //Victim page in memory, to be replaced by new one from swap
    int frameVictimInSwap; //Frame ID of the victim page in Swap, that must ne updated, if page is dirty
    int frameVictim; //Assigned frame to victim page in memory
    int pageToLoad; //Page to load from Swap
    int frameToLoadFromSwap; //Frame in swap of the page to be loaded
    boolean fullExchange; //This is true is the pages will use the same frameVictim

    public MemoryPageExchange(int pageVictim, int frameVictimInSwap, int frameVictim, int pageToLoad, int frameToLoadInSwap) {
        this(pageVictim, frameVictimInSwap, frameVictim, pageToLoad, frameToLoadInSwap, false);
    }
    
    public MemoryPageExchange(int pageVictim, int frameVictimInSwap, int frameVictim, int pageToLoad, int frameToLoadInSwap, boolean fullExchange) {
        this.pageVictim = pageVictim;
        this.frameVictimInSwap = frameVictimInSwap;
        this.frameVictim = frameVictim;
        this.pageToLoad = pageToLoad;
        this.frameToLoadFromSwap = frameToLoadInSwap;
        this.fullExchange = fullExchange;
    }
    

    public int getPageVictim() {
        return pageVictim;
    }

    public int getFrameVictimInSwap() {
        return frameVictimInSwap;
    }

    public int getFrameVictim() {
        return frameVictim;
    }

    public int getPageToLoad() {
        return pageToLoad;
    }

    public int getFrameToLoadFromSwap() {
        return frameToLoadFromSwap;
    }

    public boolean isFullExchange() {
        return fullExchange;
    }

    public void setFullExchange(boolean fullExchange) {
        this.fullExchange = fullExchange;
    }
    
    
    
    
}
