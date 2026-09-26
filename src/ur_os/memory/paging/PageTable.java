/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ur_os.memory.paging;

import java.util.ArrayList;
import java.util.LinkedList;
import ur_os.system.OS;

/**
 *
 * @author super
 */
public class PageTable {
    
    ArrayList<PageTableEntry> pageTable;
    public static final int SAMPLE_PROCESS_SIZE = 100;
    int programSize; //Size of the program in bytes
    int size; //Number of pages that the program uses
    boolean virtualMemory;
    int assignedPages;
    
    public PageTable(){
        this((SAMPLE_PROCESS_SIZE/OS.PAGE_SIZE)+1);
    }
     
    
    public PageTable(int programSize){
        this(programSize, -1, false);
    }
    
    
    public PageTable(int programSize, int assignedPages, boolean virtualMemory){
        this.programSize = programSize;
        this.virtualMemory = virtualMemory;
        this.size = java.lang.Math.floorDiv(programSize,OS.PAGE_SIZE)+1;
        this.assignedPages = assignedPages;
        this.pageTable = new ArrayList(size); 
    }
    
    public PageTable(PageTable pt){
        this(pt.getProgramSize());
        pageTable = new ArrayList(pt.getTable());
    }
    
    private ArrayList<PageTableEntry> getTable(){
        return pageTable;
    }
    
    public int getFrameIdFromPage(int page){
        if(page >= 0 && page < size){
            
            if(pageTable.get(page).isValid())           
                return pageTable.get(page).getFrameId();
            else{
                //System.out.println("Page fault!");
                return -1;
            }
                
        }
        //System.out.println("ERROR in memory access - Invalid page");
        return -2;
    }
    
    public void addFrameID(int frame){
        addFrameID(frame,false);
    }
    
    public void addFrameID(int frame, boolean valid){
        pageTable.add(new PageTableEntry(frame,valid));
    }
    
    public void setFrameID(int page, int frame){
        if(page == pageTable.size()){
            pageTable.add(new PageTableEntry(frame)); //If it is a new page
        }else if(page < pageTable.size()){
            pageTable.get(page).setFrameId(frame); //Update a frameID for an existing page
        }else{
            System.out.println("Error - Including erroneous page number");
        }
        pageTable.get(page).setValid(true);
        pageTable.get(page).setDirty(false);
        
        
    }
    
    public boolean isPageDirty(int page){
        if(page < pageTable.size()){
            return pageTable.get(page).isDirty();
        }else{
            System.out.println("Error - Including erroneous page number");
        }
        return false;
    }
    
    public void setPageDirty(int page, boolean dirty){
        if(page < pageTable.size()){
            pageTable.get(page).setDirty(dirty);
        }else{
            System.out.println("Error - Including erroneous page number");
        }
    }
    
    public boolean isPageValid(int page){
        if(page < pageTable.size()){
            return pageTable.get(page).isValid();
        }else{
            System.out.println("Error - Including erroneous page number");
        }
        return false;
    }
    
    public void setPageValid(int page, boolean valid){
        if(page < pageTable.size()){
            pageTable.get(page).setValid(valid); //Update valid value for an existing page
        }else{
            System.out.println("Error - Including erroneous page number");
        }
    }
    
    public LinkedList<PageTableEntry> getValidList(){
        LinkedList<PageTableEntry> validPages= new LinkedList();
        int i=0;
        for(PageTableEntry p: pageTable){
            if(p.isValid()){
                validPages.add(p);
            }
            i++;
        }
        return validPages;
    }

    public int getSize() {
        return size;
    }

    public int getProgramSize() {
        return programSize;
    }
    
    public static int getPageSize() {
        return OS.PAGE_SIZE;
    }
    
    public ArrayList<PageTableEntry> getList(){
        return this.pageTable;
    }
    
    public String toString(){
        StringBuilder sb = new StringBuilder();
        int count = 0;
        for (PageTableEntry pageTableEntry : pageTable) {
            sb.append("Page: ");
            sb.append(count++);
            sb.append(" ");
            sb.append(pageTableEntry.toString());
            sb.append("\n");
        }
        
        return sb.toString();
    }
}
