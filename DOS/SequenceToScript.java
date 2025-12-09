/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DOS;

import Utility.Diff;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author eboss
 */
@Entity
@Table(name = "seq2script")
public class SequenceToScript implements Serializable  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;    
    @Basic(optional = false)
    @Column(name = "idOrders")
    private int idOrders;    
    @Basic(optional = false)
    @Column(name = "accession")
    private String accession;    
    @Column(name = "created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;    
    @Column(name = "transmitted")
    private boolean transmitted;    
    @Column(name = "transDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date transDate;    
    @Column(name = "instPath")
    private String instPath;    
    @Column(name = "transReady")
    private boolean transReady;    
    @Column(name = "s2sDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date s2sDate;    
    @Column(name = "s2sPath")
    private String s2sPath;    
    @Column(name = "reportGen")
    private boolean reportGen;    
    @Column(name = "reportGenPath")
    private String reportGenPath;    
    @Column(name = "reportGenDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date reportGenDate;
    
    public SequenceToScript() {
        
    }
    
    @Diff(fieldName="id", isUniqueId = true)
    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }
    
    @Diff(fieldName="idOrders")
    public int getIdOrders()
    {
        return idOrders;
    }

    public void setIdOrders(int idOrders)
    {
        this.idOrders = idOrders;
    }
    
    @Diff(fieldName="accession")
    public String getAccession()
    {
        return accession;
    }

    public void setAccession(String accession)
    {
        this.accession = accession;
    }
    
    @Diff(fieldName="created")
    public Date getCreated()
    {
        return created;
    }

    public void setCreated(Date created)
    {
        this.created = created;
    }
    
    @Diff(fieldName="transmitted")
    public boolean getTransmitted()
    {
        return transmitted;
    }

    public void setTransmitted(boolean transmitted)
    {
        this.transmitted = transmitted;
    }
    
    @Diff(fieldName="transDate")
    public Date getTransDate()
    {
        return transDate;
    }

    public void setTransDate(Date transDate)
    {
        this.transDate = transDate;
    }
    
    @Diff(fieldName="instPath")
    public String getInstPath()
    {
        return instPath;
    }

    public void setInstPath(String instPath)
    {
        this.instPath = instPath;
    }
    
    @Diff(fieldName="transReady")
    public boolean getTransReady()
    {
        return transReady;
    }

    public void setTranReady(boolean transReady)
    {
        this.transReady = transReady;
    }
    
    @Diff(fieldName="s2sDate")
    public Date getS2SDate()
    {
        return s2sDate;
    }

    public void setS2SDate(Date s2sDate)
    {
        this.s2sDate = s2sDate;
    }
    
    @Diff(fieldName="s2sPath")
    public String getS2SPath()
    {
        return s2sPath;
    }

    public void setS2SPath(String s2sPath)
    {
        this.s2sPath = s2sPath;
    }
    
    @Diff(fieldName="reportGen")
    public boolean getReportGen()
    {
        return reportGen;
    }

    public void setReportGen(boolean reportGen)
    {
        this.reportGen = reportGen;
    }
    
    @Diff(fieldName="reportGenPath")
    public String getReportGenPath()
    {
        return reportGenPath;
    }

    public void setReportGenPath(String reportGenPath)
    {
        this.reportGenPath = reportGenPath;
    }
    
    @Diff(fieldName="reportGenDate")
    public Date getReportGenDate()
    {
        return reportGenDate;
    }

    public void setReportGenDate(Date reportGenDate)
    {
        this.reportGenDate = reportGenDate;
    }
    
}
