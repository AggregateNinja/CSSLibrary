/*
 * Copyright (C) 2013 CSS, Inc.  All Rights Reserved.
 * This software is the proprietary information of CSS, Inc.
 */

package EPOS;

/**
 * {Insert Class description here}
 *
 * @version $Revision: 1.1.1.1 $
 * @since Build {insert version here} 03/14/2013  
 * @author Ryan S. Piper <Ryan@csslis.com >
 */

import java.util.Date;

public class ArchiveResultLine {
    
    private String Exportkey;
    private int TestNumber;
    private int SubTestNumber;
    private String TestName;
    private String Result;
    private String Range;
    private String Units;
    private int SortIndex;
    private int Abnormal;
    private int Low;
    private int High;
    private int CidLow;
    private int CidHigh;
    private String ResultText;
    private String TcdRemark;
    private String MixedAnswer;
    private String TextAnswer;
    private int RemarkAbnormal;
    private String CptCode;
    private String ResultAnswer;

    public ArchiveResultLine() {
    }

    public ArchiveResultLine(String Exportkey, int TestNumber, int SubTestNumber, String TestName, String Result, String Range, String Units, int SortIndex, int Abnormal, int Low, int High, int CidLow, int CidHigh, String ResultText, String TcdRemark, String MixedAnswer, String TextAnswer, int RemarkAbnormal, String CptCode, String ResultAnswer) {
        this.Exportkey = Exportkey;
        this.TestNumber = TestNumber;
        this.SubTestNumber = SubTestNumber;
        this.TestName = TestName;
        this.Result = Result;
        this.Range = Range;
        this.Units = Units;
        this.SortIndex = SortIndex;
        this.Abnormal = Abnormal;
        this.Low = Low;
        this.High = High;
        this.CidLow = CidLow;
        this.CidHigh = CidHigh;
        this.ResultText = ResultText;
        this.TcdRemark = TcdRemark;
        this.MixedAnswer = MixedAnswer;
        this.TextAnswer = TextAnswer;
        this.RemarkAbnormal = RemarkAbnormal;
        this.CptCode = CptCode;
        this.ResultAnswer = ResultAnswer;
    }

    public String getExportkey() {
        return Exportkey;
    }

    public void setExportkey(String Exportkey) {
        this.Exportkey = Exportkey;
    }

    public int getTestNumber() {
        return TestNumber;
    }

    public void setTestNumber(int TestNumber) {
        this.TestNumber = TestNumber;
    }

    public int getSubTestNumber() {
        return SubTestNumber;
    }

    public void setSubTestNumber(int SubTestNumber) {
        this.SubTestNumber = SubTestNumber;
    }

    public String getTestName() {
        return TestName;
    }

    public void setTestName(String TestName) {
        this.TestName = TestName;
    }

    public String getResult() {
        return Result;
    }

    public void setResult(String Result) {
        this.Result = Result;
    }

    public String getRange() {
        return Range;
    }

    public void setRange(String Range) {
        this.Range = Range;
    }

    public String getUnits() {
        return Units;
    }

    public void setUnits(String Units) {
        this.Units = Units;
    }

    public int getSortIndex() {
        return SortIndex;
    }

    public void setSortIndex(int SortIndex) {
        this.SortIndex = SortIndex;
    }

    public int getAbnormal() {
        return Abnormal;
    }

    public void setAbnormal(int Abnormal) {
        this.Abnormal = Abnormal;
    }

    public int getLow() {
        return Low;
    }

    public void setLow(int Low) {
        this.Low = Low;
    }

    public int getHigh() {
        return High;
    }

    public void setHigh(int High) {
        this.High = High;
    }

    public int getCidLow() {
        return CidLow;
    }

    public void setCidLow(int CidLow) {
        this.CidLow = CidLow;
    }

    public int getCidHigh() {
        return CidHigh;
    }

    public void setCidHigh(int CidHigh) {
        this.CidHigh = CidHigh;
    }

    public String getResultText() {
        return ResultText;
    }

    public void setResultText(String ResultText) {
        this.ResultText = ResultText;
    }

    public String getTcdRemark() {
        return TcdRemark;
    }

    public void setTcdRemark(String TcdRemark) {
        this.TcdRemark = TcdRemark;
    }

    public String getMixedAnswer() {
        return MixedAnswer;
    }

    public void setMixedAnswer(String MixedAnswer) {
        this.MixedAnswer = MixedAnswer;
    }

    public String getTextAnswer() {
        return TextAnswer;
    }

    public void setTextAnswer(String TextAnswer) {
        this.TextAnswer = TextAnswer;
    }

    public int getRemarkAbnormal() {
        return RemarkAbnormal;
    }

    public void setRemarkAbnormal(int RemarkAbnormal) {
        this.RemarkAbnormal = RemarkAbnormal;
    }

    public String getCptCode() {
        return CptCode;
    }

    public void setCptCode(String CptCode) {
        this.CptCode = CptCode;
    }

    public String getResultAnswer() {
        return ResultAnswer;
    }

    public void setResultAnswer(String ResultAnswer) {
        this.ResultAnswer = ResultAnswer;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + (this.Exportkey != null ? this.Exportkey.hashCode() : 0);
        hash = 71 * hash + this.TestNumber;
        hash = 71 * hash + this.SubTestNumber;
        hash = 71 * hash + (this.TestName != null ? this.TestName.hashCode() : 0);
        hash = 71 * hash + (this.Result != null ? this.Result.hashCode() : 0);
        hash = 71 * hash + (this.Range != null ? this.Range.hashCode() : 0);
        hash = 71 * hash + (this.Units != null ? this.Units.hashCode() : 0);
        hash = 71 * hash + this.SortIndex;
        hash = 71 * hash + this.Abnormal;
        hash = 71 * hash + this.Low;
        hash = 71 * hash + this.High;
        hash = 71 * hash + this.CidLow;
        hash = 71 * hash + this.CidHigh;
        hash = 71 * hash + (this.ResultText != null ? this.ResultText.hashCode() : 0);
        hash = 71 * hash + (this.TcdRemark != null ? this.TcdRemark.hashCode() : 0);
        hash = 71 * hash + (this.MixedAnswer != null ? this.MixedAnswer.hashCode() : 0);
        hash = 71 * hash + (this.TextAnswer != null ? this.TextAnswer.hashCode() : 0);
        hash = 71 * hash + this.RemarkAbnormal;
        hash = 71 * hash + (this.CptCode != null ? this.CptCode.hashCode() : 0);
        hash = 71 * hash + (this.ResultAnswer != null ? this.ResultAnswer.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ArchiveResultLine other = (ArchiveResultLine) obj;
        if ((this.Exportkey == null) ? (other.Exportkey != null) : !this.Exportkey.equals(other.Exportkey)) {
            return false;
        }
        if (this.TestNumber != other.TestNumber) {
            return false;
        }
        if (this.SubTestNumber != other.SubTestNumber) {
            return false;
        }
        if ((this.TestName == null) ? (other.TestName != null) : !this.TestName.equals(other.TestName)) {
            return false;
        }
        if ((this.Result == null) ? (other.Result != null) : !this.Result.equals(other.Result)) {
            return false;
        }
        if ((this.Range == null) ? (other.Range != null) : !this.Range.equals(other.Range)) {
            return false;
        }
        if ((this.Units == null) ? (other.Units != null) : !this.Units.equals(other.Units)) {
            return false;
        }
        if (this.SortIndex != other.SortIndex) {
            return false;
        }
        if (this.Abnormal != other.Abnormal) {
            return false;
        }
        if (this.Low != other.Low) {
            return false;
        }
        if (this.High != other.High) {
            return false;
        }
        if (this.CidLow != other.CidLow) {
            return false;
        }
        if (this.CidHigh != other.CidHigh) {
            return false;
        }
        if ((this.ResultText == null) ? (other.ResultText != null) : !this.ResultText.equals(other.ResultText)) {
            return false;
        }
        if ((this.TcdRemark == null) ? (other.TcdRemark != null) : !this.TcdRemark.equals(other.TcdRemark)) {
            return false;
        }
        if ((this.MixedAnswer == null) ? (other.MixedAnswer != null) : !this.MixedAnswer.equals(other.MixedAnswer)) {
            return false;
        }
        if ((this.TextAnswer == null) ? (other.TextAnswer != null) : !this.TextAnswer.equals(other.TextAnswer)) {
            return false;
        }
        if (this.RemarkAbnormal != other.RemarkAbnormal) {
            return false;
        }
        if ((this.CptCode == null) ? (other.CptCode != null) : !this.CptCode.equals(other.CptCode)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ArchiveResultLine{" + "Exportkey=" + Exportkey + ", TestNumber=" + TestNumber + ", SubTestNumber=" + SubTestNumber + ", TestName=" + TestName + ", Result=" + Result + ", Range=" + Range + ", Units=" + Units + ", SortIndex=" + SortIndex + ", Abnormal=" + Abnormal + ", Low=" + Low + ", High=" + High + ", CidLow=" + CidLow + ", CidHigh=" + CidHigh + ", ResultText=" + ResultText + ", TcdRemark=" + TcdRemark + ", MixedAnswer=" + MixedAnswer + ", TextAnswer=" + TextAnswer + ", RemarkAbnormal=" + RemarkAbnormal + ", CptCode=" + CptCode + ", ResultAnswer=" + ResultAnswer + '}';
    }
     
}
