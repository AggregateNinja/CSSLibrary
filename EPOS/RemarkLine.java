
package EPOS;

/**
 * @date:   Mar 5, 2012
 * @author: Ryan
 * 
 * @project: CSSLibrary 
 * @package: EPOS
 * @file name: RemarkLine.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc.  All Rights Reserved
 */



public class RemarkLine {
    
    private int RemarkNumber;
    private String RemarkText;
    private String SortSequence;
    private int Department;
    private boolean NoBillFlag;
    private int TestNumber;
    private int SubtestNumber;
    
    public RemarkLine(){
        
    }
    
    public RemarkLine(int RemNum, String RemTxt, String sort, int dept,
            boolean noBill, int testNum, int subtestNum){
        RemarkNumber = RemNum;
        RemarkText = RemTxt;
        SortSequence = sort;
        Department = dept;
        NoBillFlag = noBill;
        TestNumber = testNum;
        SubtestNumber = subtestNum;
    }
    
    public int getRemarkNumber(){
        return RemarkNumber;
    }
    
    public void setRemarkNumber(int x){
        RemarkNumber = x;
    }
    
    public String getRemarkText(){
        return RemarkText;
    }
    
    public void setRemarkText(String x){
        RemarkText = x;
    }
    
    public String getSortSequence(){
        return SortSequence;
    }
    
    public void setSortSequence(String x){
        SortSequence = x;
    }
    
    public int getDepartment(){
        return Department;
    }
    
    public void setDepartment(int x){
        Department = x;
    }
    
    public boolean getNoBillFlag(){
        return NoBillFlag;
    }
    
    public void setNoBillFlag(boolean x){
        NoBillFlag = x;
    }
    
    public int getTestNumber(){
        return TestNumber;
    }
    
    public void setTestNumber(int x){
        TestNumber = x;
    }
    
    public int getSubtestNumber(){
        return SubtestNumber;
    }
    
    public void setSubtestNumber(int x){
        SubtestNumber = x;
    }
}
