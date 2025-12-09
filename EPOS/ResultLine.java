package EPOS;

/**
 * @date:   Mar 12, 2012
 * @author: Keith
 * 
 * @project: CSSLibrary 
 * @package: EPOS
 * @file name: OrderLine.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc.  All Rights Reserved
 */

import java.util.Date;

public class ResultLine
{
    
    private String OrderID;
    private String ARNumber;
    private Date DateOfService;
    private Date OrderedDate;
    private int TestID;
    private int SubtestID;
    private int PanelID;
    private Double ResultNumber;
    private String ResultText;
    private int ResultRemark;
    private int ResultChoice;
    private String ReportedBy;
    private Date ReportedDate;
    private int IsApproved;
    private String ApprovedBy;
    private Date ApprovedDate;
    private int IsInvalidated;
    private String InvalidatedBy;
    private Date InvalidatedDate;
    private int IsUpdated;
    private String UpdatedBy;
    private Date UpdatedDate;
    private int IsAbnormal;
    private int IsLow;
    private int IsHigh;
    private int IsCIDLow;
    private int IsCIDHigh;
    private int IsNoCharge;

    public String getTextAnswer() {
        return TextAnswer;
    }

    public void setTextAnswer(String TextAnswer) {
        this.TextAnswer = TextAnswer;
    }
    private String TextAnswer;

    public int getIsAbnormal() {
        return IsAbnormal;
    }

    public void setIsAbnormal(int IsAbnormal) {
        this.IsAbnormal = IsAbnormal;
    }

    public int getIsCIDHigh() {
        return IsCIDHigh;
    }

    public void setIsCIDHigh(int IsCIDHigh) {
        this.IsCIDHigh = IsCIDHigh;
    }

    public int getIsCIDLow() {
        return IsCIDLow;
    }

    public void setIsCIDLow(int IsCIDLow) {
        this.IsCIDLow = IsCIDLow;
    }

    public int getIsHigh() {
        return IsHigh;
    }

    public void setIsHigh(int IsHigh) {
        this.IsHigh = IsHigh;
    }

    public int getIsLow() {
        return IsLow;
    }

    public void setIsLow(int IsLow) {
        this.IsLow = IsLow;
    }

    public ResultLine()
    {
    }

    public ResultLine(String OrderID, String ARNumber, Date DateOfService, Date OrderedDate, int TestID, int SubtestID, int PanelID, Double ResultNumber, String ResultText, int ResultRemark, int ResultChoice, String ReportedBy, Date ReportedDate, int IsApproved, String ApprovedBy, Date ApprovedDate, int IsInvalidated, String InvalidatedBy, Date InvalidatedDate, int IsUpdated, String UpdatedBy, Date UpdatedDate)
    {
        this.OrderID = OrderID;
        this.ARNumber = ARNumber;
        this.DateOfService = DateOfService;
        this.OrderedDate = OrderedDate;
        this.TestID = TestID;
        this.SubtestID = SubtestID;
        this.PanelID = PanelID;
        this.ResultNumber = ResultNumber;
        this.ResultText = ResultText;
        this.ResultRemark = ResultRemark;
        this.ResultChoice = ResultChoice;
        this.ReportedBy = ReportedBy;
        this.ReportedDate = ReportedDate;
        this.IsApproved = IsApproved;
        this.ApprovedBy = ApprovedBy;
        this.ApprovedDate = ApprovedDate;
        this.IsInvalidated = IsInvalidated;
        this.InvalidatedBy = InvalidatedBy;
        this.InvalidatedDate = InvalidatedDate;
        this.IsUpdated = IsUpdated;
        this.UpdatedBy = UpdatedBy;
        this.UpdatedDate = UpdatedDate;
    }

    public String getARNumber()
    {
        return ARNumber;
    }

    public void setARNumber(String ARNumber)
    {
        this.ARNumber = ARNumber;
    }

    public Date getDateOfService()
    {
        return DateOfService;
    }

    public void setDateOfService(Date DateOfService)
    {
        this.DateOfService = DateOfService;
    }

    public int getSubtestID()
    {
        return SubtestID;
    }

    public void setSubtestID(int SubtestID)
    {
        this.SubtestID = SubtestID;
    }

    public String getApprovedBy()
    {
        return ApprovedBy;
    }

    public void setApprovedBy(String ApprovedBy)
    {
        this.ApprovedBy = ApprovedBy;
    }

    public Date getApprovedDate()
    {
        return ApprovedDate;
    }

    public void setApprovedDate(Date ApprovedDate)
    {
        this.ApprovedDate = ApprovedDate;
    }

    public String getInvalidatedBy()
    {
        return InvalidatedBy;
    }

    public void setInvalidatedBy(String InvalidatedBy)
    {
        this.InvalidatedBy = InvalidatedBy;
    }

    public Date getInvalidatedDate()
    {
        return InvalidatedDate;
    }

    public void setInvalidatedDate(Date InvalidatedDate)
    {
        this.InvalidatedDate = InvalidatedDate;
    }

    public int getIsApproved()
    {
        return IsApproved;
    }

    public void setIsApproved(int IsApproved)
    {
        this.IsApproved = IsApproved;
    }

    public int getIsInvalidated()
    {
        return IsInvalidated;
    }

    public void setIsInvalidated(int IsInvalidated)
    {
        this.IsInvalidated = IsInvalidated;
    }

    public int getIsUpdated()
    {
        return IsUpdated;
    }

    public void setIsUpdated(int IsUpdated)
    {
        this.IsUpdated = IsUpdated;
    }

    public String getOrderID()
    {
        return OrderID;
    }

    public void setOrderID(String OrderID)
    {
        this.OrderID = OrderID;
    }

    public int getPanelID()
    {
        return PanelID;
    }

    public void setPanelID(int PanelID)
    {
        this.PanelID = PanelID;
    }

    public String getReportedBy()
    {
        return ReportedBy;
    }

    public void setReportedBy(String ReportedBy)
    {
        this.ReportedBy = ReportedBy;
    }

    public Date getReportedDate()
    {
        return ReportedDate;
    }

    public void setReportedDate(Date ReportedDate)
    {
        this.ReportedDate = ReportedDate;
    }

    public int getResultChoice()
    {
        return ResultChoice;
    }

    public void setResultChoice(int ResultChoice)
    {
        this.ResultChoice = ResultChoice;
    }

    public Double getResultNumber()
    {
        return ResultNumber;
    }

    public void setResultNumber(Double ResultNumber)
    {
        this.ResultNumber = ResultNumber;
    }

    public int getResultRemark()
    {
        return ResultRemark;
    }

    public void setResultRemark(int ResultRemark)
    {
        this.ResultRemark = ResultRemark;
    }

    public String getResultText()
    {
        return ResultText;
    }

    public void setResultText(String ResultText)
    {
        this.ResultText = ResultText;
    }

    public int getTestID()
    {
        return TestID;
    }

    public void setTestID(int TestID)
    {
        this.TestID = TestID;
    }

    public String getUpdatedBy()
    {
        return UpdatedBy;
    }

    public void setUpdatedBy(String UpdatedBy)
    {
        this.UpdatedBy = UpdatedBy;
    }

    public Date getUpdatedDate()
    {
        return UpdatedDate;
    }

    public void setUpdatedDate(Date UpdatedDate)
    {
        this.UpdatedDate = UpdatedDate;
    }
    
    public Date getOrderedDate()
    {
        return OrderedDate;
    }

    public void setOrderedDate(Date OrderedDate)
    {
        this.OrderedDate = OrderedDate;
    }

    public int getNoCharge()
    {
        return IsNoCharge;
    }

    public void setNoCharge(int IsNoCharge)
    {
        this.IsNoCharge = IsNoCharge;
    }
    
    
}
