/*
 * Computer Service & Support, Inc.  All Rights Reserved.
 */

package DOS;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Rob
 */
public class DiagnosisValidityLookupLog implements Serializable 
{
    private static final long serialVersionUID = 42L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idDiagnosisValidityLog")
    private Integer idDiagnosisValidityLog;
    @Basic(optional = false)
    @Column(name = "idDiagnosisValidityLookup")
    private Integer idDiagnosisValidityLookup;
    @Basic(optional = false)
    @Column(name = "action")
    private String action;
    @Column(name = "field")
    private String field;
    @Column(name = "preValue")
    private String preValue;
    @Column(name = "postValue")
    private String postValue;
    @Column(name = "description")
    private String description;
    @Basic(optional = false)
    @Column(name = "performedByUserId")
    private Integer performedByUserId;
    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    public Integer getIdDiagnosisValidityLog()
    {
        return idDiagnosisValidityLog;
    }

    public void setIdDiagnosisValidityLog(Integer idDiagnosisValidityLog)
    {
        this.idDiagnosisValidityLog = idDiagnosisValidityLog;
    }

    public Integer getIdDiagnosisValidityLookup()
    {
        return idDiagnosisValidityLookup;
    }

    public void setIdDiagnosisValidityLookup(Integer idDiagnosisValidityLookup)
    {
        this.idDiagnosisValidityLookup = idDiagnosisValidityLookup;
    }

    public String getAction()
    {
        return action;
    }

    public void setAction(String action)
    {
        this.action = action;
    }

    public String getField()
    {
        return field;
    }

    public void setField(String field)
    {
        this.field = field;
    }

    public String getPreValue()
    {
        return preValue;
    }

    public void setPreValue(String preValue)
    {
        this.preValue = preValue;
    }

    public String getPostValue()
    {
        return postValue;
    }

    public void setPostValue(String postValue)
    {
        this.postValue = postValue;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public Integer getPerformedByUserId()
    {
        return performedByUserId;
    }

    public void setPerformedByUserId(Integer performedByUserId)
    {
        this.performedByUserId = performedByUserId;
    }

    public Date getDate()
    {
        return date;
    }

    public void setDate(Date date)
    {
        this.date = date;
    }
}
