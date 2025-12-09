
package DOS;

import Utility.Diff;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import javax.persistence.*;

/**
 * @date:   Mar 25, 2012
 * @author: Ryan
 * 
 * @project: CSSLibrary 
 * @package: DOS
 * @file name: Patients.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc.  All Rights Reserved
 */



@Entity
@Table(name = "patients")
@NamedQueries({
    @NamedQuery(name = "Patients.findAll", query = "SELECT p FROM Patients p")})
public class Patients implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idPatients")
    private Integer idPatients;
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
    @Temporal(TemporalType.DATE)
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
    @Column(name = "subscriber")
    private Integer subscriber;
    @Column(name = "relationship")
    private String relationship;
    @Column(name = "counselor")
    private Integer counselor;
    @Basic(optional = false)
    @Column(name = "species", nullable = false)
    private int species;
    @Column(name = "height")
    private Integer height;
    @Column(name = "weight")
    private Integer weight;
    @Column(name = "ethnicity")
    private String ethnicity;
    @Column(name = "smoker")
    private Boolean smoker;
    @Column(name = "active")
    private Boolean active;
    @Column(name = "deactivatedDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deactivatedDate;
    @Column(name = "patientMRN")
    private String patientMRN;
    @Column(name = "DOI")
    @Temporal(TemporalType.TIMESTAMP)
    private Date DOI;
    @Column(name = "EOA")
    private String EOA;
    @Column(name = "labPatientId")
    private String labPatientId;
    
    private ArrayList<String> patientDiffs; // For passing differences to Order Entry UI
    
    public Patients() {
    }

    public Patients(Integer idPatients) {
        this.idPatients = idPatients;
    }

    public Patients(Integer idPatients, String arNo, String lastName, String firstName) {
        this.idPatients = idPatients;
        this.arNo = arNo;
        this.lastName = lastName;
        this.firstName = firstName;
    }
    
    public Patients(Integer idPatients, String arNo, String lastName, String firstName, String middleName, String phone) {
        this.idPatients = idPatients;
        this.arNo = arNo;
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.phone = phone;
    }

    @Diff(fieldName = "idPatients", isUniqueId = true)
    public Integer getIdPatients() {
        return idPatients;
    }

    public void setIdPatients(Integer idPatients) {
        this.idPatients = idPatients;
    }

    public ArrayList<String> getPatientDiffs()
    {
        return patientDiffs;
    }
    
    @Diff(fieldName="arNo")
    public String getArNo() {
        return arNo;
    }

    public void setArNo(String arNo) {
        this.arNo = arNo;
    }

    @Diff(fieldName="lastName", isUserVisible=true)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Diff(fieldName="firstName", isUserVisible=true)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Diff(fieldName="middleName", isUserVisible=true)
    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    @Diff(fieldName="sex", isUserVisible=true)
    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Diff(fieldName="ssn", isUserVisible=true)
    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    @Diff(fieldName="dob", isUserVisible=true)
    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    @Diff(fieldName="addressStreet", isUserVisible=true)
    public String getAddressStreet() {
        return addressStreet;
    }

    public void setAddressStreet(String addressStreet) {
        this.addressStreet = addressStreet;
    }

    @Diff(fieldName="addressStreet2", isUserVisible=true)
    public String getAddressStreet2() {
        return addressStreet2;
    }

    public void setAddressStreet2(String addressStreet2) {
        this.addressStreet2 = addressStreet2;
    }

    @Diff(fieldName="addressCity", isUserVisible=true)
    public String getAddressCity() {
        return addressCity;
    }

    public void setAddressCity(String addressCity) {
        this.addressCity = addressCity;
    }

    @Diff(fieldName="addressState", isUserVisible=true)
    public String getAddressState() {
        return addressState;
    }

    public void setAddressState(String addressState) {
        this.addressState = addressState;
    }

    @Diff(fieldName="addressZip", isUserVisible=true)
    public String getAddressZip() {
        return addressZip;
    }

    public void setAddressZip(String addressZip) {
        this.addressZip = addressZip;
    }

    @Diff(fieldName="phone", isUserVisible=true)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Diff(fieldName="workPhone", isUserVisible=true)
    public String getWorkPhone() {
        return workPhone;
    }

    public void setWorkPhone(String workPhone) {
        this.workPhone = workPhone;
    }

    @Diff(fieldName="subscriber")
    public Integer getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(Integer subscriber) {
        this.subscriber = subscriber;
    }

    @Diff(fieldName="relationship")
    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    @Diff(fieldName="counselor")
    public Integer getCounselor()
    {
        return counselor;
    }

    public void setCounselor(Integer counselor)
    {
        this.counselor = counselor;
    }

    @Diff(fieldName="species")
    public int getSpecies()
    {
        return species;
    }

    public void setSpecies(int species)
    {
        this.species = species;
    }

    @Diff(fieldName="height", isUserVisible=true)
    public Integer getHeight()
    {
        return height;
    }

    public void setHeight(Integer height)
    {
        this.height = height;
    }

    @Diff(fieldName="weight", isUserVisible=true)
    public Integer getWeight()
    {
        return weight;
    }

    public void setWeight(Integer weight)
    {
        this.weight = weight;
    }

    @Diff(fieldName="ethnicity", isUserVisible=true)
    public String getEthnicity()
    {
        return ethnicity;
    }

    public void setEthnicity(String ethnicity)
    {
        this.ethnicity = ethnicity;
    }

    @Diff(fieldName="smoker", isUserVisible=true)
    public Boolean getSmoker()
    {
        return smoker;
    }

    public void setSmoker(Boolean smoker)
    {
        this.smoker = smoker;
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

    @Diff(fieldName="patientMRN", isUserVisible=true)
    public String getPatientMRN()
    {
        return patientMRN;
    }

    public void setPatientMRN(String patientMRN)
    {
        this.patientMRN = patientMRN;
    }

    @Diff(fieldName="DOI", isUserVisible=true)
    public Date getDOI()
    {
        return DOI;
    }

    public void setDOI(Date DOI)
    {
        this.DOI = DOI;
    }

    @Diff(fieldName="EOA", isUserVisible=true)
    public String getEOA()
    {
        return EOA;
    }

    public void setEOA(String EOA)
    {
        this.EOA = EOA;
    }
    
    @Diff(fieldName="labPatientId", isUserVisible=true)
    public String getLabPatientId()
    {
        return labPatientId;
    }
    
    public void setLabPatientId(String labPatientId)
    {
        this.labPatientId = labPatientId;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPatients != null ? idPatients.hashCode() * (active != null && active == true ? 1 : -1) : 0);
        return hash;
    }

    /*@Override
    public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof Patients)) {
    return false;
    }
    Patients other = (Patients) object;
    if ((this.idPatients == null && other.idPatients != null) || (this.idPatients != null && !this.idPatients.equals(other.idPatients))) {
    return false;
    }
    return true;
    }*/
    @Override
    public boolean equals(Object obj)
    {
        patientDiffs = new ArrayList();
        
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        final Patients other = (Patients) obj;
        if (!Objects.equals(this.arNo, other.arNo))
        {
            //return false;
            patientDiffs.add("- Patient number changed.\n");
        }
        if (!Objects.equals(this.lastName, other.lastName) ||
                !Objects.equals(this.firstName, other.firstName) ||
                !Objects.equals(this.middleName, other.middleName))
        {
            //return false;
            patientDiffs.add("- Patient name changed.\n");
        }
        /*if (!Objects.equals(this.firstName, other.firstName))
        {
            return false;
        }
        if (!Objects.equals(this.middleName, other.middleName))
        {
            return false;
        }*/
        if (!Objects.equals(this.sex, other.sex))
        {
            //return false;
            patientDiffs.add("- Patient sex changed.\n");
        }
        if (this.ssn != null && other.ssn != null && !Objects.equals(this.ssn.toLowerCase().replace("-", "").trim(), other.ssn.toLowerCase().replace("-", "").trim()))
        //if (!Objects.equals(this.ssn.toLowerCase().replace("-", "").trim(), other.ssn.toLowerCase().replace("-", "").trim()))
        //if (!Objects.equals(this.ssn, other.ssn))
        {
            //return false;
            patientDiffs.add("- Patient SSN changed.\n");
        }
        if (!Objects.equals(this.dob, other.dob))
        {
            //return false;
            patientDiffs.add("- Patient date of birth changed.\n");
        }
        if (!Objects.equals(this.addressStreet, other.addressStreet) ||
                !Objects.equals(this.addressStreet2, other.addressStreet2) || 
                !Objects.equals(this.addressCity, other.addressCity) ||
                !Objects.equals(this.addressState, other.addressState) || 
                !Objects.equals(this.addressZip, other.addressZip))
        {
            //return false;
            patientDiffs.add("- Patient address changed.\n");
        }/*
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
        }*/
        if (this.phone != null && other.phone != null && !Objects.equals(this.phone.toLowerCase().replace("-", "").replace("(", "").replace(")", "").trim(), other.phone.toLowerCase().replace("-", "").replace("(", "").replace(")", "").trim()))
        //if (this.phone != null && other.phone != null && !Objects.equals(this.phone.toLowerCase().replace("-", "").replace("(", "").replace(")", "").trim(), other.phone.toLowerCase().replace("-", "").replace("(", "").replace(")", "").trim()))
        //if (!Objects.equals(this.phone, other.phone))
        {
            //return false;
            patientDiffs.add("- Patient phone # changed.\n");
        }
        if (this.workPhone != null && other.workPhone != null && !Objects.equals(this.workPhone.toLowerCase().replace("-", "").replace("(", "").replace(")", "").trim(), other.workPhone.toLowerCase().replace("-", "").replace("(", "").replace(")", "").trim()))
        //if (!Objects.equals(this.workPhone, other.workPhone))
        {
            //return false;
            patientDiffs.add("- Patient work phone # changed.\n");
        }
        if (!Objects.equals(this.subscriber, other.subscriber))
        {
            //return false;
            patientDiffs.add("- Patient subscriber changed.\n");
        }
        if (this.relationship == null || !Objects.equals(this.relationship.toLowerCase().trim(), other.relationship.toLowerCase().trim()))
        //if (!Objects.equals(this.relationship, other.relationship))
        {
            //return false;
            patientDiffs.add("- Patient relationship changed.\n");
        }
        if (!Objects.equals(this.counselor, other.counselor))
        {
            //return false;
            patientDiffs.add("- Patient counselor changed.\n");
        }
        if (this.species != other.species)
        {
            //return false;
            patientDiffs.add("- Patient species changed.\n");
        }
        if (!Objects.equals(this.height, other.height))
        {
            //return false;
            patientDiffs.add("- Patient height changed.\n");
        }
        if (!Objects.equals(this.weight, other.weight))
        {
            //return false;
            patientDiffs.add("- Patient weight changed.\n");
        }
        if (!Objects.equals(this.ethnicity, other.ethnicity))
        {
            //return false;
            patientDiffs.add("- Patient ethnicity changed.\n");
        }
        if (!Objects.equals(this.smoker, other.smoker))
        {
            //return false;
            patientDiffs.add("- Patient smoking/nonsmoking changed.\n");
        }
        if(!Objects.equals(this.patientMRN, other.patientMRN))
        {
            //return false;
            patientDiffs.add("- Patient MRN changed.\n");
        }
        if (!Objects.equals(this.labPatientId, other.labPatientId))
        {
            patientDiffs.add("- Patient Lab ID changed.\n");
        }
        //return true;
        
        return patientDiffs.isEmpty();
    }

    @Override
    public String toString() {
        String name = firstName + " " + lastName;
        return "(" + idPatients + ")" + (firstName != null ? name : (lastName != null ? lastName : ""));
    }
    
    public Boolean phoneNumberIsValid() {
        String patientPhone = this.phone;
        if (patientPhone != null && patientPhone.trim().isEmpty() == false) {
            String numericPhone = patientPhone.replaceAll("[^\\d.]", "");

            if (numericPhone.length() == 10 && numericPhone.equals("0000000000") == false && numericPhone.equals("1111111111") == false
                    && numericPhone.equals("2222222222") == false && numericPhone.equals("3333333333") == false
                    && numericPhone.equals("4444444444") == false && numericPhone.equals("5555555555") == false
                    && numericPhone.equals("6666666666") == false && numericPhone.equals("7777777777") == false
                    && numericPhone.equals("8888888888") == false && numericPhone.equals("9999999999") == false) {
                return true;
            }

        }
        
        return false;
    }

}
