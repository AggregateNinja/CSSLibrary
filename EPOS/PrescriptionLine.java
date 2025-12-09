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

public class PrescriptionLine 
{
    private String LastName;
    private String Accession;
    private Date OrderedDate;
    private String Comment;

    public PrescriptionLine()
    {
    }

    public PrescriptionLine(String LastName, String Accession, Date OrderedDate, String Comment)
    {
        this.LastName = LastName;
        this.Accession = Accession;
        this.OrderedDate = OrderedDate;
        this.Comment = Comment;
    }

    public String getLastName()
    {
        return LastName;
    }

    public void setLastName(String LastName)
    {
        this.LastName = LastName;
    }

    public String getAccession()
    {
        return Accession;
    }

    public void setAccession(String Accession)
    {
        this.Accession = Accession;
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
        hash = 67 * hash + (this.LastName != null ? this.LastName.hashCode() : 0);
        hash = 67 * hash + (this.Accession != null ? this.Accession.hashCode() : 0);;
        hash = 67 * hash + (this.OrderedDate != null ? this.OrderedDate.hashCode() : 0);
        hash = 67 * hash + (this.Comment != null ? this.Comment.hashCode() : 0);
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
        final PrescriptionLine other = (PrescriptionLine) obj;
        if ((this.LastName == null) ? (other.LastName != null) : !this.LastName.equals(other.LastName))
        {
            return false;
        }
        if (this.Accession != other.Accession)
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
        return "PrescriptionLine{" + "LastName=" + LastName + ", Accession=" + Accession + ", OrderedDate=" + OrderedDate + ", Comment=" + Comment + '}';
    }
    
}
