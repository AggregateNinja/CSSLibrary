
package DOS;

import Utility.Diff;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.*;

/**
 * @date:   Mar 25, 2012
 * @author: Ryan
 * 
 * @project: CSSLibrary 
 * @package: DOS
 * @file name: Subscriber.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc.  All Rights Reserved
 */



@Entity
@Table(name = "subscriber")
@NamedQueries({
    @NamedQuery(name = "Subscriber.findAll", query = "SELECT s FROM Subscriber s")})
public class Subscriber implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idSubscriber")
    private Integer idSubscriber;
    @Basic(optional = false)
    @Column(name = "arNo")
    private String arNo;
    @Basic(optional = false)
    @Column(name = "lastName")
    private String lastName;
    @Basic(optional = false)
    @Column(name = "firstName")
    private String firstName;
    @Column(name = "middleName")
    private String middleName;
    @Column(name = "sex")
    private String sex;
    @Column(name = "ssn")
    private String ssn;
    @Column(name = "dob")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dob;
    @Column(name = "addressStreet")
    private String addressStreet;
    @Column(name = "addressStreet2")
    private String addressStreet2;
    @Column(name = "addressCity")
    private String addressCity;
    @Column(name = "addressState")
    private String addressState;
    @Column(name = "addressZip")
    private String addressZip;
    @Column(name = "phone")
    private String phone;
    @Column(name = "workPhone")
    private String workPhone;
    @Basic(optional = false)
    @Column(name = "insurance")
    private int insurance;
    @Column(name = "secondaryInsurance")
    private Integer secondaryInsurance;
    @Column(name = "policyNumber")
    private String policyNumber;
    @Column(name = "groupNumber")
    private String groupNumber;
    @Column(name = "secondaryPolicyNumber")
    private String secondaryPolicyNumber;
    @Column(name = "secondaryGroupNumber")
    private String secondaryGroupNumber;
    @Column(name = "medicareNumber")
    private String medicareNumber;
    @Column(name = "medicaidNumber")
    private String medicaidNumber;
    @Column(name = "active")
    private Boolean active;
    @Column(name = "deactivatedDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deactivatedDate;

    public Subscriber() {
    }

    public Subscriber(Integer idSubscriber) {
        this.idSubscriber = idSubscriber;
    }

    public Subscriber(Integer idSubscriber, String arNo, String lastName, String firstName, int insurance) {
        this.idSubscriber = idSubscriber;
        this.arNo = arNo;
        this.lastName = lastName;
        this.firstName = firstName;
        this.insurance = insurance;
    }

    public Integer getIdSubscriber() {
        return idSubscriber;
    }

    public void setIdSubscriber(Integer idSubscriber) {
        this.idSubscriber = idSubscriber;
    }

    @Diff(fieldName="arNo")
    public String getArNo() {
        return arNo;
    }

    public void setArNo(String arNo) {
        this.arNo = arNo;
    }

    @Diff(fieldName="lastName")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Diff(fieldName="firstName")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Diff(fieldName="middleName")
    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    @Diff(fieldName="sex")
    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Diff(fieldName="ssn")
    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    @Diff(fieldName="dob")
    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    @Diff(fieldName="addressStreet")
    public String getAddressStreet() {
        return addressStreet;
    }

    public void setAddressStreet(String addressStreet) {
        this.addressStreet = addressStreet;
    }

    @Diff(fieldName="addressStreet2")
    public String getAddressStreet2() {
        return addressStreet2;
    }

    public void setAddressStreet2(String addressStreet2) {
        this.addressStreet2 = addressStreet2;
    }

    @Diff(fieldName="addressCity")
    public String getAddressCity() {
        return addressCity;
    }

    public void setAddressCity(String addressCity) {
        this.addressCity = addressCity;
    }

    @Diff(fieldName="addressState")
    public String getAddressState() {
        return addressState;
    }

    public void setAddressState(String addressState) {
        this.addressState = addressState;
    }

    @Diff(fieldName="addressZip")
    public String getAddressZip() {
        return addressZip;
    }

    public void setAddressZip(String addressZip) {
        this.addressZip = addressZip;
    }

    @Diff(fieldName="phone")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Diff(fieldName="workPhone")
    public String getWorkPhone() {
        return workPhone;
    }

    public void setWorkPhone(String workPhone) {
        this.workPhone = workPhone;
    }

    @Diff(fieldName="insurance")
    public int getInsurance() {
        return insurance;
    }

    public void setInsurance(int insurance) {
        this.insurance = insurance;
    }

    @Diff(fieldName="secondaryInsurance")
    public Integer getSecondaryInsurance() {
        return secondaryInsurance;
    }

    public void setSecondaryInsurance(Integer secondaryInsurance) {
        this.secondaryInsurance = secondaryInsurance;
    }

    @Diff(fieldName="policyNumber")
    public String getPolicyNumber() {
        return policyNumber;
    }

    public void setPolicyNumber(String policyNumber) {
        this.policyNumber = policyNumber;
    }

    @Diff(fieldName="groupNumber")
    public String getGroupNumber() {
        return groupNumber;
    }

    public void setGroupNumber(String groupNumber) {
        this.groupNumber = groupNumber;
    }

    @Diff(fieldName="secondaryPolicyNumber")
    public String getSecondaryPolicyNumber() {
        return secondaryPolicyNumber;
    }

    public void setSecondaryPolicyNumber(String secondaryPolicyNumber) {
        this.secondaryPolicyNumber = secondaryPolicyNumber;
    }

    @Diff(fieldName="secondaryGroupNumber")
    public String getSecondaryGroupNumber() {
        return secondaryGroupNumber;
    }

    public void setSecondaryGroupNumber(String secondaryGroupNumber) {
        this.secondaryGroupNumber = secondaryGroupNumber;
    }

    @Diff(fieldName="medicareNumber")
    public String getMedicareNumber() {
        return medicareNumber;
    }

    public void setMedicareNumber(String medicareNumber) {
        this.medicareNumber = medicareNumber;
    }

    @Diff(fieldName="medicaidNumber")
    public String getMedicaidNumber() {
        return medicaidNumber;
    }

    public void setMedicaidNumber(String medicaidNumber) {
        this.medicaidNumber = medicaidNumber;
    }

    @Diff(fieldName="active")
    public Boolean getActive()
    {
        return active;
    }

    public void setActive(Boolean active)
    {
        this.active = active;
    }

    @Diff(fieldName="deactivatedDate")
    public Date getDeactivatedDate()
    {
        return deactivatedDate;
    }

    public void setDeactivatedDate(Date deactivatedDate)
    {
        this.deactivatedDate = deactivatedDate;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSubscriber != null ? idSubscriber.hashCode() * (active != null && active == true ? 1 : -1) : 0);
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
        final Subscriber other = (Subscriber) obj;
        if (!Objects.equals(this.arNo, other.arNo))
        {
            return false;
        }
        if (!Objects.equals(this.lastName, other.lastName))
        {
            return false;
        }
        if (!Objects.equals(this.firstName, other.firstName))
        {
            return false;
        }
        if (!Objects.equals(this.middleName, other.middleName))
        {
            return false;
        }
        if (!Objects.equals(this.sex, other.sex))
        {
            return false;
        }
        if (!Objects.equals(this.ssn, other.ssn))
        {
            return false;
        }
        if (!Objects.equals(this.dob, other.dob))
        {
            return false;
        }
        if (!Objects.equals(this.addressStreet, other.addressStreet))
        {
            return false;
        }
        if (!Objects.equals(this.addressStreet2, other.addressStreet2))
        {
            return false;
        }
        if (!Objects.equals(this.addressCity, other.addressCity))
        {
            return false;
        }
        if (!Objects.equals(this.addressState, other.addressState))
        {
            return false;
        }
        if (!Objects.equals(this.addressZip, other.addressZip))
        {
            return false;
        }
        if (!Objects.equals(this.phone, other.phone))
        {
            return false;
        }
        if (!Objects.equals(this.workPhone, other.workPhone))
        {
            return false;
        }
        if (this.insurance != other.insurance)
        {
            return false;
        }
        if (!Objects.equals(this.secondaryInsurance, other.secondaryInsurance))
        {
            return false;
        }
        if (!Objects.equals(this.policyNumber, other.policyNumber))
        {
            return false;
        }
        if (!Objects.equals(this.groupNumber, other.groupNumber))
        {
            return false;
        }
        if (!Objects.equals(this.secondaryPolicyNumber, other.secondaryPolicyNumber))
        {
            return false;
        }
        if (!Objects.equals(this.secondaryGroupNumber, other.secondaryGroupNumber))
        {
            return false;
        }
        if (!Objects.equals(this.medicareNumber, other.medicareNumber))
        {
            return false;
        }
        if (!Objects.equals(this.medicaidNumber, other.medicaidNumber))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DOS.Subscriber[ idSubscriber=" + idSubscriber + " ]";
    }

}
