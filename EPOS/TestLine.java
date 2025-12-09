
package EPOS;

/**
 * @date:   Mar 3, 2012
 * @author: Ryan
 * 
 * @project: CSSLibrary 
 * @package: EPOS
 * @file name: TestLine.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc.  All Rights Reserved
 */



public class TestLine {

    private int  TestNumber;
    private int SubtestNumber;
    private int  Resno;
    private String TestName;
    private String Abbreviation;
    private int  Type;
    private String TypeText;
    private int  Department;
    private int  InstrumentNumber;
    private String  OnlineCode;
    private int  PrintOrderFlag;
    private String ResultType;
    private int DecimalPositions;
    private int  Required;
    private String Stat;
    private int  Cycles;
    private int  Multiplier;
    private String UnitOfMeasure;
    private String NormalRange;
    private int  NormalsLow;
    private int  NormalsHigh;
    private int  PanicLow;
    private int  PanicHigh;
    private int  WorksheetNumber;
    private int  Column;
    private int DegreeOfDiff;
    private int TCDRemark;
    private int HasExtraNormals;
    private int GroupItemCount;
    private String GroupNumbers;
    
    public TestLine(){
    }
    
    public TestLine(int tNo, int stNo, int resNo, String name, String abbr,
            int type, String typeText, int dept, int inst, String online, 
            int printOrder, String resType, int decimal, int req, String stat,
            int cycles, int multi, String units, String normals, int low,
            int high, int pLow, int pHigh, int wsNo, int col, int dof, int tRemark,
            int xtraNrml, int groupCount, String groupNos)
    {
        TestNumber = tNo;
        SubtestNumber = stNo;
        Resno = resNo;
        TestName = name;
        Abbreviation = abbr;
        Type = type;
        TypeText = typeText;
        Department = dept;
        InstrumentNumber = inst;
        OnlineCode = online;
        PrintOrderFlag = printOrder;
        ResultType = resType;
        DecimalPositions = decimal;
        Required = req;
        Stat = stat;
        Cycles = cycles;
        Multiplier = multi;
        UnitOfMeasure = units;
        NormalRange = normals;
        NormalsLow = low;
        NormalsHigh = high;
        PanicLow = pLow;
        PanicHigh = pHigh;
        WorksheetNumber = wsNo;
        Column = col;
        DegreeOfDiff = dof;
        TCDRemark = tRemark;
        HasExtraNormals = xtraNrml;
        GroupItemCount = groupCount;
        GroupNumbers = groupNos;
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
    
    public int getResno(){
        return Resno;
    }
    
    public void setResno(int x){
        Resno = x;
    }
    
    public String getTestName(){
        return TestName;
    }
    
    public void setTestName(String x){
        TestName = x;
    }
    
    public String getAbbreviation(){
        return Abbreviation;
    }
    
    public void setAbbreviation(String x){
        Abbreviation = x;
    }
    
    public int getType(){
        return Type;
    }
    
    public void setType(int x){
        Type = x;
    }
    
    public String getTypeText(){
        return TypeText;
    }
    
    public void setTypeText(String x){
        TypeText = x;
    }
    
    public int getDepartment(){
        return Department;
    }
    
    public void setDepartment( int x){
        Department = x;
    }
    
    public int getInstrument(){
        return InstrumentNumber;
    }
    
    public void setInstrument(int x){
        InstrumentNumber = x;
    }
    
    public String getOnlineCode(){
        return OnlineCode;
    }
    
    public void setOnlineCode(String x){
        OnlineCode = x;
    }
    
    public int getPrintOrderFlag(){
        return PrintOrderFlag;
    }
    
    public void setPrintOrderFlag(int x)
    {
        PrintOrderFlag = x;
    }
    
    public String getResultType(){
        return ResultType;
    }
    
    public void setResultType(String x){
        ResultType = x;
    }
    
    public int getDecimalPosition(){
        return DecimalPositions;
    }
    
    public void setDecimalPosition(int x){
        DecimalPositions = x;
    }
    
    public int getRequired(){
        return Required;
    }
    
    public void setRequired(int x){
        Required = x;
    }
    
    public String getStat(){
        return Stat;
    }
    
    public void setStat(String x){
        Stat = x;
    }
    
    public int getCycles(){
        return Cycles;
    }
    
    public void setCycles(int x){
        Cycles = x;
    }
    
    public int getMultipier(){
        return Multiplier;
    }
    
    public void setMultiplier(int x){
        Multiplier = x;
    }
    
    public String getUnitOfMeasure(){
        return UnitOfMeasure;
    }
    
    public void setUnitOfMeasure(String x){
        UnitOfMeasure = x;
    }
    
    public String getNormalRange(){
        return NormalRange;
    }
    
    public void setNormalRange(String x){
        NormalRange = x;
    }
    
    public int getNormalLow(){
        return NormalsLow;
    }
    
    public void setNormalsLow(int x){
        NormalsLow = x;
    }
    
    public int getNormalsHigh(){
        return NormalsHigh;
    }
    
    public void setNormalsHigh(int x){
        NormalsHigh = x;
    }
    
    public int getPanicLow(){
        return PanicLow;
    }
    
    public void setPanicLow(int x){
        PanicLow = x;
    }
    
    public int getPanicHigh(){
        return PanicHigh;
    }
    
    public void setPanicHigh(int x){
        PanicHigh = x;
    }
    
    public int getWorkSheetNumber(){
        return WorksheetNumber;
    }
    
    public void setWorksheetNumber(int x){
        WorksheetNumber = x;
    }
    
    public int getColumn(){
        return Column;
    }
    
    public void setColumn(int x){
        Column = x;
    }
    
    public int getDegreeOfDiff(){
        return DegreeOfDiff;
    }
    
    public void setDegreeOfDiff(int x){
        DegreeOfDiff = x;
    }
    
    public int getTCDRemark(){
        return TCDRemark;
    }
    
    public void setTCDRemark(int x){
        TCDRemark = x;
    }
    
    public int getHasExtraNormals(){
        return HasExtraNormals;
    }
    
    public void setHasExraNormals(int x){
        HasExtraNormals = x;
    }
    
    public int getGroupItemCount(){
        return GroupItemCount;
    }
    
    public void setGroupItemCount(int x){
        GroupItemCount = x;
    }
    
    public String getGroupNumbers(){
        return GroupNumbers;
    }
    
    public void setGroupNumbers(String x){
        GroupNumbers = x;
    }
}
