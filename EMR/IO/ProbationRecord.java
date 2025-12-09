/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package EMR.IO;

import org.coury.jfilehelpers.annotations.FieldAlign;
import org.coury.jfilehelpers.annotations.FieldFixedLength;
import org.coury.jfilehelpers.annotations.FixedLengthRecord;
import org.coury.jfilehelpers.enums.AlignMode;
import org.coury.jfilehelpers.enums.FixedMode;

/******************************************************************************/
/**
 *
 * @author Michael
 */
/******************************************************************************/
@FixedLengthRecord(fixedMode=FixedMode.AllowMoreChars)
public class ProbationRecord
{
    /**************************************************************************/    
    @FieldAlign(alignMode=AlignMode.Left)
    @FieldFixedLength(20)
    private String MasterNumber;
    
    /**************************************************************************/    
    @FieldFixedLength(1)
    private String spacer1;
    
    /**************************************************************************/    
    @FieldAlign(alignMode=AlignMode.Left)
    @FieldFixedLength(10)
    private String PatientId;
    
    /**************************************************************************/    
    @FieldFixedLength(1)
    private String spacer2;
    
    /**************************************************************************/    
    @FieldAlign(alignMode=AlignMode.Left)
    @FieldFixedLength(10)
    private String SpecimenDate;
    
    /**************************************************************************/    
    @FieldFixedLength(1)
    private String spacer3;
    
    /**************************************************************************/    
    @FieldAlign(alignMode=AlignMode.Left)
    @FieldFixedLength(1)
    private String RecordType;
    
    /**************************************************************************/    
    @FieldFixedLength(1)
    private String spacer4;
    
    /**************************************************************************/    
    @FieldAlign(alignMode=AlignMode.Left)
    @FieldFixedLength(3)
    private String ClientCode;
    
    /**************************************************************************/    
    @FieldFixedLength(1)
    private String spacer5;
    
    /**************************************************************************/    
    @FieldAlign(alignMode=AlignMode.Left)
    @FieldFixedLength(3)
    private String EnterUser;
    
    /**************************************************************************/    
    @FieldFixedLength(1)
    private String spacer6;
    
    /**************************************************************************/    
    @FieldAlign(alignMode=AlignMode.Left)
    @FieldFixedLength(5)
    private String SpecimenTime;
    
    /**************************************************************************/    
    @FieldFixedLength(1)
    private String spacer7;
    
    /**************************************************************************/    
    @FieldAlign(alignMode=AlignMode.Left)
    @FieldFixedLength(10)
    private String OrderDate;
    
    /**************************************************************************/    
    @FieldFixedLength(1)
    private String spacer8;
    
    /**************************************************************************/    
    @FieldAlign(alignMode=AlignMode.Left)
    @FieldFixedLength(1)
    private String TestCode;
    
    /**************************************************************************/    
    @FieldFixedLength(1)
    private String spacer9;
    
    /**************************************************************************/    
    @FieldAlign(alignMode=AlignMode.Left)
    @FieldFixedLength(32)
    private String ResultComment;
    
    /**************************************************************************/    
    @FieldFixedLength(1)
    private String spacer10;
    
    /**************************************************************************/    
    @FieldAlign(alignMode=AlignMode.Left)
    @FieldFixedLength(30)
    private String DrawComment;
    
    /**************************************************************************/  
    public String getMasterNumber(){return MasterNumber;}
    public void setMasterNumber(String MasterNumber) {this.MasterNumber = MasterNumber;}
    
    /**************************************************************************/      
    public String getPatientId(){return PatientId;}
    public void setPatientId(String PatientId) {this.PatientId = PatientId;}
    
    /**************************************************************************/
    public String getSpecimenDate(){return SpecimenDate;}
    public void setSpecimenDate(String SpecimenDate) {this.SpecimenDate = SpecimenDate;}
    
    /**************************************************************************/
    public String getRecordType(){return RecordType;}
    public void setRecordType(String RecordType) {this.RecordType = RecordType;}
    
    /**************************************************************************/
    public String getClientCode(){return ClientCode;}
    public void setClientCode(String ClientCode) {this.ClientCode = ClientCode;}
    
    /**************************************************************************/
    public String getEnterUser(){return EnterUser;}
    public void setEnterUser(String EnterUser) {this.EnterUser = EnterUser;}
    
    /**************************************************************************/
    public String getSpecimenTime(){return SpecimenTime;}
    public void setSpecimenTime(String SpecimenTime) {this.SpecimenTime = SpecimenTime;}
    
    /**************************************************************************/
    public String getOrderDate(){return OrderDate;}
    public void setOrderDate(String OrderDate) {this.OrderDate = OrderDate;}
    
    /**************************************************************************/
    public String getTestCode(){return TestCode;}
    public void setTestCode(String TestCode) {this.TestCode = TestCode;}
    
    /**************************************************************************/
    public String getResultComment(){return ResultComment;}
    public void setResultComment(String ResultComment) {this.ResultComment = ResultComment;}
    
    /**************************************************************************/
    public String getDrawComment(){return DrawComment;}
    public void setDrawComment(String DrawComment) {this.DrawComment = DrawComment;}

    /**************************************************************************/        
}

/******************************************************************************/
/******************************************************************************/
/******************************************************************************/
