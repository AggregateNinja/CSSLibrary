/*
 * Computer Service & Support, Inc.  All Rights Reserved.
 */

package DOS;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author Rob
 */
public class DiagnosisValidityStatus implements Serializable, Comparable<DiagnosisValidityStatus>
{
    private static final long serialVersionUID = 42L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idDiagnosisValidityStatus")
    private Integer idDiagnosisValidityStatus;
    @Basic(optional = false)
    @Column(name = "name")
    private String type;
    @Column(name = "description")
    private String description;
    @Column(name = "priority")
    private Integer priority;
    @Basic(optional = false)
    @Column(name = "active")
    private Boolean active;

    public Integer getIdDiagnosisValidityStatus()
    {
        return idDiagnosisValidityStatus;
    }

    public void setIdDiagnosisValidityStatus(Integer idDiagnosisValidityStatus)
    {
        this.idDiagnosisValidityStatus = idDiagnosisValidityStatus;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public Integer getPriority()
    {
        return priority;
    }

    public void setPriority(Integer priority)
    {
        this.priority = priority;
    }

    public Boolean getActive()
    {
        return active;
    }

    public void setActive(boolean active)
    {
        this.active = active;
    }

    /**
     * Valid statuses are sorted first (priority numbers start low and go up).
     * 
     * Non-active validity statuses are sorted to the bottom of the list.
     * @param other
     * @return 
     */
    @Override
    public int compareTo(DiagnosisValidityStatus other)
    {
        // Jump out of comparison if NULL
        if (other == null) return -1;
        if (other.priority == null) return -1;
        if (other.active == null) return -1;
        if (this.active == null) return 1;
        if (this.priority == null) return 1;

        // Push inactive stuff to the end
        if (this.active == true && other.active == false) return -1;
        if (this.active == false && other.active == true) return 1;
        
        // Sort low numbers to the top
        if (this.priority < other.priority) return -1;
        if (this.priority > other.priority) return 1;
        
        return 0;
    }
}
