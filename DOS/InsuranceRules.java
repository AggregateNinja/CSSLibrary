package DOS;

import Utility.Diff;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * @date:   Sep 18, 2013
 * @author: Keith Maggio <keith@csslis.com>
 * 
 * @project:  
 * @package: DOS
 * @file name: InsuranceRules.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc.  All Rights Reserved
 */

@Entity 
@Table(name = "insuranceRules")
@NamedQueries(
{
    @NamedQuery(name = "InsuranceRules.findAll", query = "SELECT i FROM InsuranceRules i")
})
public class InsuranceRules implements Serializable 
{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idinsuranceRules")
    private Integer idinsuranceRules;
    @Column(name = "doctorRequired")
    private boolean doctorRequired;
    @Column(name = "addressRequired")
    private boolean addressRequired;
    @Column(name = "ageRequired")
    private boolean ageRequired;
    @Column(name = "birthDateRequired")
    private boolean birthDateRequired;
    @Column(name = "locationRequired")
    private boolean locationRequired;
    @Column(name = "genderRequired")
    private boolean genderRequired;
    @Column(name = "diagnosisRequired")
    private boolean diagnosisRequired;
    @Column(name = "subscriberRequired")
    private boolean subscriberRequired;
    @Column(name = "relationshipRequired")
    private boolean relationshipRequired;
    @Column(name = "groupNumberRequired")
    private boolean groupNumberRequired;
    @Column(name = "group2NumberRequired")
    private boolean group2NumberRequired;
    @Column(name = "policyNumberRequired")
    private boolean policyNumberRequired;
    @Column(name = "policy2NumberRequired")
    private boolean policy2NumberRequired;
    @Column(name = "medicareNumberRequired")
    private boolean medicareNumberRequired;
    @Column(name = "medicaidNumberRequired")
    private boolean medicaidNumberRequired;

    public InsuranceRules()
    {

    }

    public InsuranceRules(Integer idinsurancesRules)
    {
        this.idinsuranceRules = idinsurancesRules;
    }
    
    /**
     * Copy constructor (useful for diffs)
     * @param other 
     */
    public InsuranceRules(InsuranceRules other)
    {
        this.idinsuranceRules = other.getIdinsurancesRules();
        this.doctorRequired = other.getDoctorRequired();
        this.ageRequired = other.getAgeRequired();
        this.birthDateRequired = other.getBirthDateRequired();
        this.locationRequired = other.getLocationRequired();
        this.genderRequired = other.getGenderRequired();
        this.diagnosisRequired = other.getDiagnosisRequired();
        this.subscriberRequired = other.getSubscriberRequired();
        this.relationshipRequired = other.getRelationshipRequired();
        this.groupNumberRequired = other.getGroupNumberRequired();
        this.group2NumberRequired = other.getGroup2NumberRequired();
        this.policyNumberRequired = other.getPolicyNumberRequired();
        this.policy2NumberRequired = other.getPolicy2NumberRequired();
        this.medicareNumberRequired = other.getMedicareNumberRequired();
        this.medicaidNumberRequired = other.getMedicaidNumberRequired();
    }

    @Diff(fieldName = "idinsuranceRules")
    public Integer getIdinsurancesRules()
    {
        return idinsuranceRules;
    }

    public void setIdinsurancesRules(Integer idinsurancesRules)
    {
        this.idinsuranceRules = idinsurancesRules;
    }

    @Diff(fieldName = "doctorRequired")
    public boolean getDoctorRequired()
    {
        return doctorRequired;
    }

    public void setDoctorRequired(Boolean doctorRequired)
    {
        this.doctorRequired = doctorRequired;
    }

    @Diff(fieldName = "addressRequired")
    public boolean getAddressRequired()
    {
        return addressRequired;
    }

    public void setAddressRequired(Boolean addressRequired)
    {
        this.addressRequired = addressRequired;
    }
    
    @Diff(fieldName = "ageRequired")
    public boolean getAgeRequired()
    {
        return ageRequired;
    }

    public void setAgeRequired(Boolean ageRequired)
    {
        this.ageRequired = ageRequired;
    }

    @Diff(fieldName = "birthDateRequired")
    public boolean getBirthDateRequired()
    {
        return birthDateRequired;
    }

    public void setBirthDateRequired(Boolean birthDateRequired)
    {
        this.birthDateRequired = birthDateRequired;
    }

    @Diff(fieldName = "locationRequired")
    public boolean getLocationRequired()
    {
        return locationRequired;
    }

    public void setLocationRequired(Boolean locationRequired)
    {
        this.locationRequired = locationRequired;
    }

    @Diff(fieldName = "genderRequired")
    public boolean getGenderRequired()
    {
        return genderRequired;
    }

    public void setGenderRequired(Boolean genderRequired)
    {
        this.genderRequired = genderRequired;
    }

    @Diff(fieldName = "diagnosisRequired")
    public boolean getDiagnosisRequired()
    {
        return diagnosisRequired;
    }

    public void setDiagnosisRequired(Boolean diagnosisRequired)
    {
        this.diagnosisRequired = diagnosisRequired;
    }

    @Diff(fieldName = "subscriberRequired")
    public boolean getSubscriberRequired()
    {
        return subscriberRequired;
    }

    public void setSubscriberRequired(Boolean subscriberRequired)
    {
        this.subscriberRequired = subscriberRequired;
    }

    @Diff(fieldName = "relationshipRequired")
    public boolean getRelationshipRequired()
    {
        return relationshipRequired;
    }

    public void setRelationshipRequired(Boolean relationshipRequired)
    {
        this.relationshipRequired = relationshipRequired;
    }

    @Diff(fieldName = "groupNumberRequired")
    public boolean getGroupNumberRequired()
    {
        return groupNumberRequired;
    }

    public void setGroupNumberRequired(Boolean groupNumberRequired)
    {
        this.groupNumberRequired = groupNumberRequired;
    }

    @Diff(fieldName = "group2NumberRequired")
    public boolean getGroup2NumberRequired()
    {
        return group2NumberRequired;
    }

    public void setGroup2NumberRequired(Boolean group2NumberRequired)
    {
        this.group2NumberRequired = group2NumberRequired;
    }

    @Diff(fieldName = "policyNumberRequired")
    public boolean getPolicyNumberRequired()
    {
        return policyNumberRequired;
    }

    public void setPolicyNumberRequired(Boolean policyNumberRequired)
    {
        this.policyNumberRequired = policyNumberRequired;
    }

    @Diff(fieldName = "policy2NumberRequired")
    public boolean getPolicy2NumberRequired()
    {
        return policy2NumberRequired;
    }

    public void setPolicy2NumberRequired(Boolean policy2NumberRequired)
    {
        this.policy2NumberRequired = policy2NumberRequired;
    }

    @Diff(fieldName = "medicareRequired")
    public boolean getMedicareNumberRequired()
    {
        return medicareNumberRequired;
    }

    public void setMedicareNumberRequired(Boolean medicareNumberRequired)
    {
        this.medicareNumberRequired = medicareNumberRequired;
    }

    @Diff(fieldName = "medicaidRequired")
    public boolean getMedicaidNumberRequired()
    {
        return medicaidNumberRequired;
    }

    public void setMedicaidNumberRequired(Boolean medicaidNumberRequired)
    {
        this.medicaidNumberRequired = medicaidNumberRequired;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (idinsuranceRules != null ? idinsuranceRules.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InsuranceRules))
        {
            return false;
        }
        InsuranceRules other = (InsuranceRules) object;
        if ((this.idinsuranceRules == null && other.idinsuranceRules != null) || (this.idinsuranceRules != null && !this.idinsuranceRules.equals(other.idinsuranceRules)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "DOS.InsuranceRules[ idinsuranceRules=" + idinsuranceRules + " ]";
    }

}
