package DOS;

import Utility.Diff;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

/**
 * @date: Mar 16, 2012
 * @author: Ryan
 *
 * @project: CSSLibrary
 * @package: DOS
 * @file name: Tests.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc. All Rights Reserved
 */
@Entity
@Table(name = "tests")
@NamedQueries(
{
    @NamedQuery(name = "Tests.findAll", query = "SELECT t FROM Tests t")
})
public class Tests implements Serializable
{

    @Column(name = "resultType")
    private String resultType;
    @Column(name = "lowNormal")
    private double lowNormal;
    @Column(name = "highNormal")
    private double highNormal;
    @Column(name = "alertLow")
    private double alertLow;
    @Column(name = "alertHigh")
    private double alertHigh;
    @Column(name = "criticalLow")
    private double criticalLow;
    @Column(name = "criticalHigh")
    private double criticalHigh;
    @Column(name = "relatedDrug")
    private Integer relatedDrug;
    @Column(name = "decimalPositions")
    private Integer decimalPositions;
    @Column(name = "subtest")
    private Integer subtest;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idtests")
    private Integer idtests;
    @Basic(optional = false)
    @Column(name = "number")
    private int number;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @Column(name = "abbr")
    private String abbr;
    @Basic(optional = false)
    @Column(name = "testType")
    private int testType;
    @Column(name = "printNormals")
    private String printNormals;
    @Column(name = "units")
    private String units;
    @Column(name = "remark")
    private int remark;
    @Basic(optional = false)
    @Column(name = "department")
    private int department;
    @Column(name = "instrument")
    private int instrument;
    @Column(name = "onlineCode1")
    private String onlineCode1;
    @Column(name = "onlineCode2")
    private String onlineCode2;
    @Basic(optional = false)
    @Column(name = "created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;
    @Column(name = "printOrder")
    private int printOrder;
    @Column(name = "reportHeaderId")
    private Integer reportHeaderId;
    @Column(name = "specimenType")
    private int specimenType;
    @Column(name = "loinc")
    private String loinc;
    @Column(name = "billingOnly")
    private boolean billingOnly;
    @Column(name = "labelPrint")
    private boolean labelPrint;
    @Column(name = "tubeTypeId")
    private Integer tubeTypeId;
    @Column(name = "headerPrint")
    private boolean headerPrint;
    @Column(name = "active")
    private boolean active;
    @Column(name = "deactivatedDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deactivatedDate;
    @Column(name = "testComment")
    private byte[] testComment;
    @Column(name = "extraNormals")
    private boolean extraNormals;
    @Column(name = "AOE")
    private boolean AOE;
    @Column(name = "cycles")
    private int cycles;
    @Column(name = "stat")
    private boolean stat;
    @Column(name = "header")
    private boolean header;
    @Column(name = "maxReportable")
    private Double maxReportable;
    @Column(name = "minReportable")
    private Double minReportable;
    @Column(name = "useMaximums")
    private boolean useMaximums;
    @Column(name = "maxLowResult")
    private String maxLowResult;
    @Column(name = "maxHighResult")
    private String maxHighResult;
    @Column(name = "maxLowNumeric")
    private double maxLowNumeric;
    @Column(name = "maxHighNumeric")
    private double maxHighNumeric;
    @Column(name = "printedName")
    private String printedName;
    @Column(name = "printOnReport")
    private boolean printOnReport;
    @Column(name = "noRounding")
    private boolean noRounding;
    @Column(name = "lowRemark")
    private Integer lowRemark;
    @Column(name = "lowShowNumeric")
    private boolean lowShowNumeric;
    @Column(name = "highRemark")
    private Integer highRemark;
    @Column(name = "highShowNumeric")
    private boolean highShowNumeric;
    @Column(name = "alertLowRemark")
    private Integer alertLowRemark;
    @Column(name = "alertLowShowNumeric")
    private boolean alertLowShowNumeric;
    @Column(name = "alertHighRemark")
    private Integer alertHighRemark;
    @Column(name = "alertHighShowNumeric")
    private boolean alertHighShowNumeric;
    @Column(name = "critLowRemark")
    private Integer critLowRemark;
    @Column(name = "critLowShowNumeric")
    private boolean critLowShowNumeric;
    @Column(name = "critHighRemark")
    private Integer critHighRemark;
    @Column(name = "critHighShowNumeric")
    private boolean critHighShowNumeric;
    @Column(name = "normalRemark")
    private Integer normalRemark;
    @Column(name = "normalShowNumeric")
    private boolean normalShowNumeric;
    @Column(name = "isOrderable")
    private boolean isOrderable;
    @Column(name = "placeOfServiceId")
    private Integer placeOfServiceId;
    @Column(name = "cost")
    private double cost;

    public Tests()
    {
    }

    public Tests(int idtests)
    {
        this.idtests = idtests;
    }

    public Tests(int idtests, int number, String name, String abbr, int testType, String resultType, int department, Date created)
    {
        this.idtests = idtests;
        this.number = number;
        this.name = name;
        this.abbr = abbr;
        this.testType = testType;
        this.resultType = resultType;
        this.department = department;
        this.created = created;
    }

    @Diff(fieldName = "idtests", isUniqueId = true)
    public Integer getIdtests()
    {
        return idtests;
    }

    public void setIdtests(int idtests)
    {
        this.idtests = idtests;
    }

    @Diff(fieldName = "number")
    public int getNumber()
    {
        return number;
    }

    public void setNumber(int number)
    {
        this.number = number;
    }

    @Diff(fieldName = "name", isUserVisible = true)
    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    @Diff(fieldName = "abbr", isUserVisible = true)
    public String getAbbr()
    {
        return abbr;
    }

    public void setAbbr(String abbr)
    {
        this.abbr = abbr;
    }

    @Diff(fieldName = "testType", isUserVisible = true)
    public int getTestType()
    {
        return testType;
    }

    public void setTestType(int testType)
    {
        this.testType = testType;
    }

    @Diff(fieldName = "printNormals", isUserVisible = true)
    public String getPrintNormals()
    {
        return printNormals;
    }

    public void setPrintNormals(String printNormals)
    {
        this.printNormals = printNormals;
    }

    @Diff(fieldName = "reportHeaderId")
    public Integer getReportHeaderId()
    {
        return reportHeaderId;
    }

    public void setReportHeaderId(Integer reportHeaderId)
    {
        this.reportHeaderId = reportHeaderId;
    }

    @Diff(fieldName = "units", isUserVisible = true)
    public String getUnits()
    {
        return units;
    }

    public void setUnits(String units)
    {
        this.units = units;
    }

    @Diff(fieldName = "remark")
    public int getRemark()
    {
        return remark;
    }

    public void setRemark(int remark)
    {
        this.remark = remark;
    }

    @Diff(fieldName = "department")
    public int getDepartment()
    {
        return department;
    }

    public void setDepartment(int department)
    {
        this.department = department;
    }

    @Diff(fieldName = "instrument")
    public int getInstrument()
    {
        return instrument;
    }

    public void setInstrument(int instrument)
    {
        this.instrument = instrument;
    }

    @Diff(fieldName = "onlineCode1", isUserVisible = true)
    public String getOnlineCode1()
    {
        return onlineCode1;
    }

    public void setOnlineCode1(String onlineCode1)
    {
        this.onlineCode1 = onlineCode1;
    }

    @Diff(fieldName = "onlineCode2", isUserVisible = true)
    public String getOnlineCode2()
    {
        return onlineCode2;
    }

    public void setOnlineCode2(String onlineCode2)
    {
        this.onlineCode2 = onlineCode2;
    }

    public Date getCreated()
    {
        return created;
    }

    public void setCreated(Date created)
    {
        this.created = created;
    }

    @Diff(fieldName = "printOrder")
    public int getPrintOrder()
    {
        return printOrder;
    }

    public void setPrintOrder(int printOrder)
    {
        this.printOrder = printOrder;
    }

    @Diff(fieldName = "specimenType")
    public int getSpecimenType()
    {
        return specimenType;
    }

    public void setSpecimenType(int specimenType)
    {
        this.specimenType = specimenType;
    }

    @Diff(fieldName = "loinc")
    public String getLoinc()
    {
        return loinc;
    }

    public void setLoinc(String loinc)
    {
        this.loinc = loinc;
    }

    @Diff(fieldName = "resultType", isUserVisible = true)
    public String getResultType()
    {
        return resultType;
    }

    public void setResultType(String resultType)
    {
        this.resultType = resultType;
    }

    @Diff(fieldName = "lowNormal", isUserVisible = true)
    public double getLowNormal()
    {
        return lowNormal;
    }

    public void setLowNormal(double lowNormal)
    {
        this.lowNormal = lowNormal;
    }

    @Diff(fieldName = "highNormal", isUserVisible = true)
    public double getHighNormal()
    {
        return highNormal;
    }

    public void setHighNormal(double highNormal)
    {
        this.highNormal = highNormal;
    }

    @Diff(fieldName = "alertLow", isUserVisible = true)
    public double getAlertLow()
    {
        return alertLow;
    }

    public void setAlertLow(double alertLow)
    {
        this.alertLow = alertLow;
    }

    @Diff(fieldName = "alertHigh", isUserVisible = true)
    public double getAlertHigh()
    {
        return alertHigh;
    }

    public void setAlertHigh(double alertHigh)
    {
        this.alertHigh = alertHigh;
    }

    @Diff(fieldName = "criticalLow", isUserVisible = true)
    public double getCriticalLow()
    {
        return criticalLow;
    }

    public void setCriticalLow(double criticalLow)
    {
        this.criticalLow = criticalLow;
    }

    @Diff(fieldName = "criticalHigh", isUserVisible = true)
    public double getCriticalHigh()
    {
        return criticalHigh;
    }

    public void setCriticalHigh(double criticalHigh)
    {
        this.criticalHigh = criticalHigh;
    }

    @Diff(fieldName = "relatedDrug")
    public Integer getRelatedDrug()
    {
        return relatedDrug;
    }

    public void setRelatedDrug(Integer relatedDrug)
    {
        this.relatedDrug = relatedDrug;
    }

    @Diff(fieldName = "decimalPositions", isUserVisible = true)
    public Integer getDecimalPositions()
    {
        return decimalPositions;
    }

    public void setDecimalPositions(Integer decimalPositions)
    {
        this.decimalPositions = decimalPositions;
    }

    @Diff(fieldName = "subtest")
    public Integer getSubtest()
    {
        return subtest;
    }

    public void setSubtest(Integer subtest)
    {
        this.subtest = subtest;
    }

    @Diff(fieldName = "billingOnly", isUserVisible = true)
    public boolean getBillingOnly()
    {
        return billingOnly;
    }

    public void setBillingOnly(boolean billingOnly)
    {
        this.billingOnly = billingOnly;
    }

    @Diff(fieldName = "labelPrint")
    public boolean getLabelPrint()
    {
        return labelPrint;
    }

    public void setLabelPrint(boolean labelPrint)
    {
        this.labelPrint = labelPrint;
    }

    @Diff(fieldName = "tubeTypeId")
    public Integer getTubeTypeId()
    {
        return tubeTypeId;
    }

    public void setTubeTypeId(Integer tubeTypeId)
    {
        this.tubeTypeId = tubeTypeId;
    }

    @Diff(fieldName = "headerPrint")
    public boolean getHeaderPrint()
    {
        return headerPrint;
    }

    public void setHeaderPrint(boolean headerPrint)
    {
        this.headerPrint = headerPrint;
    }

    @Diff(fieldName = "active")
    public boolean getActive()
    {
        return active;
    }

    public boolean isBattery()
    {
        return header;
    }

    public boolean isCalculation()
    {
        return testType == 2;
    }

    public boolean isPanelHeader()
    {
        return testType == 0;
    }

    public void setActive(boolean active)
    {
        this.active = active;
    }

    public Date getDeactivatedDate()
    {
        return deactivatedDate;
    }

    public void setDeactivatedDate(Date deactivatedDate)
    {
        this.deactivatedDate = deactivatedDate;
    }

    @Diff(fieldName = "testComment", isUserVisible = true)
    public byte[] getTestComment()
    {
        return testComment;
    }

    public void setTestComment(byte[] testComment)
    {
        this.testComment = testComment;
    }

    @Diff(fieldName = "extraNormals")
    public boolean getExtraNormals()
    {
        return extraNormals;
    }

    public void setExtraNormals(boolean extraNormals)
    {
        this.extraNormals = extraNormals;
    }

    @Diff(fieldName = "aoe")
    public boolean getAOE()
    {
        return AOE;
    }

    public void setAOE(boolean AOE)
    {
        this.AOE = AOE;
    }

    @Diff(fieldName = "cycles", isUserVisible = true)
    public int getCycles()
    {
        return cycles;
    }

    public void setCycles(int cycles)
    {
        this.cycles = cycles;
    }

    @Diff(fieldName = "stat")
    public boolean getStat()
    {
        return stat;
    }

    public void setStat(boolean stat)
    {
        this.stat = stat;
    }

    @Diff(fieldName = "header")
    public boolean getHeader()
    {
        return header;
    }

    public void setHeader(boolean header)
    {
        this.header = header;
    }

    @Diff(fieldName = "useMaximums")
    public boolean getUseMaximums()
    {
        return useMaximums;
    }

    public void setUseMaximums(boolean useMaximums)
    {
        this.useMaximums = useMaximums;
    }

    @Diff(fieldName = "maxLowResult", isUserVisible = true)
    public String getMaxLowResult()
    {
        return maxLowResult;
    }

    public void setMaxLowResult(String maxLowResult)
    {
        this.maxLowResult = maxLowResult;
    }

    @Diff(fieldName = "maxHighResult", isUserVisible = true)
    public String getMaxHighResult()
    {
        return maxHighResult;
    }

    public void setMaxHighResult(String maxHighResult)
    {
        this.maxHighResult = maxHighResult;
    }

    @Diff(fieldName = "maxLowNumeric", isUserVisible = true)
    public double getMaxLowNumeric()
    {
        return maxLowNumeric;
    }

    public void setMaxLowNumeric(double maxLowNumeric)
    {
        this.maxLowNumeric = maxLowNumeric;
    }

    @Diff(fieldName = "maxHighNumeric", isUserVisible = true)
    public double getMaxHighNumeric()
    {
        return maxHighNumeric;
    }

    public void setMaxHighNumeric(double maxHighNumeric)
    {
        this.maxHighNumeric = maxHighNumeric;
    }

    @Diff(fieldName = "maxReportable", isUserVisible = true)
    public Double getMaxReportable()
    {
        return maxReportable;
    }

    public void setMaxReportable(Double maxReportable)
    {
        this.maxReportable = maxReportable;
    }

    @Diff(fieldName = "minReportable", isUserVisible = true)
    public Double getMinReportable()
    {
        return minReportable;
    }

    public void setMinReportable(Double minReportable)
    {
        this.minReportable = minReportable;
    }

    @Diff(fieldName = "printedName", isUserVisible = true)
    public String getPrintedName()
    {
        return printedName;
    }

    public void setPrintedName(String printedName)
    {
        this.printedName = printedName;
    }

    @Diff(fieldName = "printOnReport", isUserVisible = true)
    public boolean getPrintOnReport()
    {
        return printOnReport;
    }

    public void setPrintOnReport(boolean printOnReport)
    {
        this.printOnReport = printOnReport;
    }

    @Diff(fieldName = "noRounding", isUserVisible = true)
    public boolean getNoRounding()
    {
        return noRounding;
    }

    public void setNoRounding(boolean noRounding)
    {
        this.noRounding = noRounding;
    }

    @Diff(fieldName = "lowRemark")
    public Integer getLowRemark()
    {
        return lowRemark;
    }

    public void setLowRemark(Integer lowRemark)
    {
        this.lowRemark = lowRemark;
    }

    @Diff(fieldName = "lowShowNumeric", isUserVisible = true)
    public boolean getLowShowNumeric()
    {
        return lowShowNumeric;
    }

    public void setLowShowNumeric(boolean lowShowNumeric)
    {
        this.lowShowNumeric = lowShowNumeric;
    }

    @Diff(fieldName = "highRemark")
    public Integer getHighRemark()
    {
        return highRemark;
    }

    public void setHighRemark(Integer highRemark)
    {
        this.highRemark = highRemark;
    }

    @Diff(fieldName = "highShowNumeric", isUserVisible = true)
    public boolean getHighShowNumeric()
    {
        return highShowNumeric;
    }

    public void setHighShowNumeric(boolean highShowNumeric)
    {
        this.highShowNumeric = highShowNumeric;
    }

    @Diff(fieldName = "alertLowRemark")
    public Integer getAlertLowRemark()
    {
        return alertLowRemark;
    }

    public void setAlertLowRemark(Integer alertLowRemark)
    {
        this.alertLowRemark = alertLowRemark;
    }

    @Diff(fieldName = "alertLowShowNumeric", isUserVisible = true)
    public boolean getAlertLowShowNumeric()
    {
        return alertLowShowNumeric;
    }

    public void setAlertLowShowNumeric(boolean alertLowShowNumeric)
    {
        this.alertLowShowNumeric = alertLowShowNumeric;
    }

    @Diff(fieldName = "alertHighRemark")
    public Integer getAlertHighRemark()
    {
        return alertHighRemark;
    }

    public void setAlertHighRemark(Integer alertHighRemark)
    {
        this.alertHighRemark = alertHighRemark;
    }

    @Diff(fieldName = "alertHighShowNumeric", isUserVisible = true)
    public boolean getAlertHighShowNumeric()
    {
        return alertHighShowNumeric;
    }

    public void setAlertHighShowNumeric(boolean alertHighShowNumeric)
    {
        this.alertHighShowNumeric = alertHighShowNumeric;
    }

    @Diff(fieldName = "critLowRemark")
    public Integer getCritLowRemark()
    {
        return critLowRemark;
    }

    public void setCritLowRemark(Integer critLowRemark)
    {
        this.critLowRemark = critLowRemark;
    }

    @Diff(fieldName = "critLowShowNumeric", isUserVisible = true)
    public boolean getCritLowShowNumeric()
    {
        return critLowShowNumeric;
    }

    public void setCritLowShowNumeric(boolean critLowShowNumeric)
    {
        this.critLowShowNumeric = critLowShowNumeric;
    }

    @Diff(fieldName = "critHighRemark")
    public Integer getCritHighRemark()
    {
        return critHighRemark;
    }

    public void setCritHighRemark(Integer critHighRemark)
    {
        this.critHighRemark = critHighRemark;
    }

    @Diff(fieldName = "critHighShowNumeric", isUserVisible = true)
    public boolean getCritHighShowNumeric()
    {
        return critHighShowNumeric;
    }

    public void setCritHighShowNumeric(boolean critHighShowNumeric)
    {
        this.critHighShowNumeric = critHighShowNumeric;
    }

    @Diff(fieldName = "normalRemark", isUserVisible = true)
    public Integer getNormalRemark()
    {
        return normalRemark;
    }

    public void setNormalRemark(Integer normalRemark)
    {
        this.normalRemark = normalRemark;
    }

    @Diff(fieldName = "normalShowNumeric", isUserVisible = true)
    public boolean getNormalShowNumeric()
    {
        return normalShowNumeric;
    }

    public void setNormalShowNumeric(boolean normalShowNumeric)
    {
        this.normalShowNumeric = normalShowNumeric;
    }
    
    @Diff(fieldName = "isOrderable", isUserVisible = true)
    public boolean isOrderable()
    {
        return isOrderable;
    }

    public void setIsOrderable(boolean isOrderable)
    {
        this.isOrderable = isOrderable;
    }

    @Diff(fieldName = "placeOfServiceId", isUserVisible = false)
    public Integer getPlaceOfServiceId()
    {
        return placeOfServiceId;
    }

    public void setPlaceOfServiceId(Integer placeOfServiceId)
    {
        this.placeOfServiceId = placeOfServiceId;
    }

    @Diff(fieldName = "cost", isUserVisible = false)
    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    /**
     * Provides a new Tests object matching this one EXCEPT for the unique
     * database identifier (idTests), which is null, and the created date which
     * is the current date.
     *
     * Note: if this test is saved as new, and the old test is marked as active,
     * there will be two active tests for this number (not good).
     *
     * This is used when modifying an existing test and diff logging
     *
     * @return
     */
    public Tests CopyWithoutIdentifier()
    {
        Tests testCopy = new Tests();

        // ============ Non-copied fields ================
        testCopy.idtests = null;
        testCopy.created = new java.util.Date();
        // ===============================================

        testCopy.resultType = getResultType();
        testCopy.lowNormal = getLowNormal();
        testCopy.highNormal = getHighNormal();
        testCopy.alertLow = getAlertLow();
        testCopy.alertHigh = getAlertHigh();
        testCopy.criticalLow = getCriticalLow();
        testCopy.criticalHigh = getCriticalHigh();
        testCopy.relatedDrug = getRelatedDrug();
        testCopy.decimalPositions = getDecimalPositions();
        testCopy.subtest = getSubtest();
        testCopy.number = getNumber();
        testCopy.name = getName();
        testCopy.abbr = getAbbr();
        testCopy.testType = getTestType();
        testCopy.printNormals = getPrintNormals();
        testCopy.units = getUnits();
        testCopy.remark = getRemark();
        testCopy.department = getDepartment();
        testCopy.instrument = getInstrument();
        testCopy.onlineCode1 = getOnlineCode1();
        testCopy.onlineCode2 = getOnlineCode2();
        testCopy.printOrder = getPrintOrder();
        testCopy.specimenType = getSpecimenType();
        testCopy.loinc = getLoinc();
        testCopy.billingOnly = getBillingOnly();
        testCopy.labelPrint = getLabelPrint();
        testCopy.tubeTypeId = getTubeTypeId();
        testCopy.headerPrint = getHeaderPrint();
        testCopy.active = getActive();
        testCopy.deactivatedDate = getDeactivatedDate();
        testCopy.testComment = getTestComment();
        testCopy.extraNormals = getExtraNormals();
        testCopy.AOE = getAOE();
        testCopy.cycles = getCycles();
        testCopy.stat = getStat();
        testCopy.header = getHeader();
        testCopy.maxReportable = getMaxReportable();
        testCopy.minReportable = getMinReportable();
        testCopy.useMaximums = getUseMaximums();
        testCopy.maxLowResult = getMaxLowResult();
        testCopy.maxHighResult = getMaxHighResult();
        testCopy.maxLowNumeric = getMaxLowNumeric();
        testCopy.maxHighNumeric = getMaxHighNumeric();
        testCopy.printedName = getPrintedName();
        testCopy.printOnReport = getPrintOnReport();
        testCopy.noRounding = getNoRounding();
        testCopy.lowRemark = getLowRemark();
        testCopy.lowShowNumeric = getLowShowNumeric();
        testCopy.highRemark = getHighRemark();
        testCopy.highShowNumeric = getHighShowNumeric();
        testCopy.alertLowRemark = getAlertLowRemark();
        testCopy.alertLowShowNumeric = getAlertLowShowNumeric();
        testCopy.alertHighRemark = getAlertHighRemark();
        testCopy.alertHighShowNumeric = getAlertHighShowNumeric();
        testCopy.critLowRemark = getCritLowRemark();
        testCopy.critLowShowNumeric = getCritLowShowNumeric();
        testCopy.critHighRemark = getCritHighRemark();
        testCopy.critHighShowNumeric = getCritHighShowNumeric();
        testCopy.normalRemark = getNormalRemark();
        testCopy.normalShowNumeric = getNormalShowNumeric();
        testCopy.isOrderable = isOrderable();
        testCopy.setPlaceOfServiceId(getPlaceOfServiceId());
        testCopy.setCost(cost);
        return testCopy;
    }


    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (idtests != null ? idtests.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tests))
        {
            return false;
        }
        Tests other = (Tests) object;
        if ((this.idtests == null && other.idtests != null) || (this.idtests != null && !this.idtests.equals(other.idtests)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "("+number+") " + name;//"DOS.Tests[ idtests=" + idtests + ", number=" + number + " ]";
    }
}
