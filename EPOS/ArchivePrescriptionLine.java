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
 * @author Keith Maggio <keith@csslis.com>
 */

import java.util.Date;

public class ArchivePrescriptionLine 
{
    private String ExportKey;
    private String Accession;
    private int ClientNumber;
    private Date OrderedDate;
    private String Comment;

    public ArchivePrescriptionLine()
    {
    }

    public ArchivePrescriptionLine(String ExportKey, String Accession, int ClientNumber, Date OrderedDate, String Comment)
    {
        this.ExportKey = ExportKey;
        this.Accession = Accession;
        this.ClientNumber = ClientNumber;
        this.OrderedDate = OrderedDate;
        this.Comment = Comment;
    }

    public String getExportKey()
    {
        return ExportKey;
    }

    public void setExportKey(String ExportKey)
    {
        this.ExportKey = ExportKey;
    }

    public String getAccession()
    {
        return Accession;
    }

    public void setAccession(String Accession)
    {
        this.Accession = Accession;
    }

    public int getClientNumber()
    {
        return ClientNumber;
    }

    public void setClientNumber(int ClientNumber)
    {
        this.ClientNumber = ClientNumber;
    }

    public Date getOrderedDate()
    {
        return OrderedDate;
    }

    public void setOrderedDate(Date OrderedDate)
    {
        this.OrderedDate = OrderedDate;
    }

    public String getComment()
    {
        return Comment;
    }

    public void setComment(String Comment)
    {
        this.Comment = Comment;
    }

    @Override
    public int hashCode()
    {
        int hash = 5;
        hash = 37 * hash + (this.ExportKey != null ? this.ExportKey.hashCode() : 0);
        hash = 37 * hash + (this.Accession != null ? this.Accession.hashCode() : 0);
        hash = 37 * hash + this.ClientNumber;
        hash = 37 * hash + (this.OrderedDate != null ? this.OrderedDate.hashCode() : 0);
        hash = 37 * hash + (this.Comment != null ? this.Comment.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        final ArchivePrescriptionLine other = (ArchivePrescriptionLine) obj;
        if ((this.ExportKey == null) ? (other.ExportKey != null) : !this.ExportKey.equals(other.ExportKey))
        {
            return false;
        }
        if (this.Accession != other.Accession)
        {
            return false;
        }
        if (this.ClientNumber != other.ClientNumber)
        {
            return false;
        }
        if (this.OrderedDate != other.OrderedDate && (this.OrderedDate == null || !this.OrderedDate.equals(other.OrderedDate)))
        {
            return false;
        }
        if ((this.Comment == null) ? (other.Comment != null) : !this.Comment.equals(other.Comment))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "ArchivePrescriptionLine{" + "ExportKey=" + ExportKey + ", Accession=" + Accession + ", ClientNumber=" + ClientNumber + ", OrderedDate=" + OrderedDate + ", Comment=" + Comment + '}';
    }
    
    
    
}
