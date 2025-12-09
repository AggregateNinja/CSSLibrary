/*
 * Computer Service & Support, Inc.  All Rights Reserved.
 */

package DOS;

import Utility.Diff;
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
public class DiagnosisValidity implements Serializable 
{
    private static final long serialVersionUID = 42L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idDiagnosisValidity")
    private Integer idDiagnosisValidity;
    @Column(name = "billingPayorId")
    private Integer billingPayorId;
    @Basic(optional = false)
    @Column(name = "active")
    private Boolean active;

    @Diff(fieldName = "idDiagnosisValidity", isUniqueId = true)
    public Integer getIdDiagnosisValidity()
    {
        return idDiagnosisValidity;
    }

    public void setIdDiagnosisValidity(Integer idDiagnosisValidity)
    {
        this.idDiagnosisValidity = idDiagnosisValidity;
    }

    @Diff(fieldName = "billingPayorId", isUniqueId = false)
    public Integer getBillingPayorId()
    {
        return billingPayorId;
    }

    public void setBillingPayorId(Integer billingPayorId)
    {
        this.billingPayorId = billingPayorId;
    }

    @Diff(fieldName = "active", isUniqueId = false)
    public Boolean getActive()
    {
        return active;
    }

    public void setActive(boolean active)
    {
        this.active = active;
    }
}
