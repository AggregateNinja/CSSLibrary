package DOS.IDOS;

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
 * @date:   Mar 26, 2014
 * @author: Keith Maggio <keith@csslis.com>
 * 
 * @project:  
 * @package: DOS.IDOS
 * @file name: InstOrd1.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc.  All Rights Reserved
 */

public class BaseInstOrd implements Serializable 
{
    @Column(name = "id")
    private Integer id;
    @Column(name = "accession")
    private String accession;
    @Column(name = "patientID")
    private String patientID;
    @Column(name = "patientLast")
    private String patientLast;
    @Column(name = "patientFirst")
    private String patientFirst;
    @Column(name = "DOB")
    private String dob;
    @Column(name = "age")
    private String age;
    @Column(name = "sex")
    private String sex;
    @Column(name = "orderDate")
    private String orderDate;
    @Column(name = "specimenDate")
    private String specimenDate;
    @Column(name = "clientNumber")
    private String clientNumber;
    @Column(name = "clientName")
    private String clientName;
    @Column(name = "doctorNumber")
    private String doctorNumber;
    @Column(name = "doctorName")
    private String doctorName;
    @Column(name = "fasting")
    private String fasting;
    @Column(name = "testName")
    private String testName;
    @Column(name = "testNumber")
    private String testNumber;
    @Column(name = "testXref")
    private String testXref;
    @Column(name = "onlineCode")
    private String onlineCode;
    @Column(name = "specimenType")
    private String specimenType;
    @Column(name = "sent")
    private boolean sent;

    public BaseInstOrd()
    {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getAccession()
    {
        return accession;
    }

    public void setAccession(String accession)
    {
        this.accession = accession;
    }

    public String getPatientID()
    {
        return patientID;
    }

    public void setPatientID(String patientID)
    {
        this.patientID = patientID;
    }

    public String getPatientLast()
    {
        return patientLast;
    }

    public void setPatientLast(String patientLast)
    {
        this.patientLast = patientLast;
    }

    public String getPatientFirst()
    {
        return patientFirst;
    }

    public void setPatientFirst(String patientFirst)
    {
        this.patientFirst = patientFirst;
    }

    public String getDob()
    {
        return dob;
    }

    public void setDob(String dob)
    {
        this.dob = dob;
    }

    public String getAge()
    {
        return age;
    }

    public void setAge(String age)
    {
        this.age = age;
    }

    public String getSex()
    {
        return sex;
    }

    public void setSex(String sex)
    {
        this.sex = sex;
    }

    public String getOrderDate()
    {
        return orderDate;
    }

    public void setOrderDate(String orderDate)
    {
        this.orderDate = orderDate;
    }

    public String getSpecimenDate()
    {
        return specimenDate;
    }

    public void setSpecimenDate(String specimenDate)
    {
        this.specimenDate = specimenDate;
    }

    public String getClientNumber()
    {
        return clientNumber;
    }

    public void setClientNumber(String clientNumber)
    {
        this.clientNumber = clientNumber;
    }

    public String getClientName()
    {
        return clientName;
    }

    public void setClientName(String clientName)
    {
        this.clientName = clientName;
    }

    public String getDoctorNumber()
    {
        return doctorNumber;
    }

    public void setDoctorNumber(String doctorNumber)
    {
        this.doctorNumber = doctorNumber;
    }

    public String getDoctorName()
    {
        return doctorName;
    }

    public void setDoctorName(String doctorName)
    {
        this.doctorName = doctorName;
    }

    public String getFasting()
    {
        return fasting;
    }

    public void setFasting(String fasting)
    {
        this.fasting = fasting;
    }

    public String getTestName()
    {
        return testName;
    }

    public void setTestName(String testName)
    {
        this.testName = testName;
    }

    public String getTestNumber()
    {
        return testNumber;
    }

    public void setTestNumber(String testNumber)
    {
        this.testNumber = testNumber;
    }

    public String getTestXref()
    {
        return testXref;
    }

    public void setTestXref(String testXref)
    {
        this.testXref = testXref;
    }

    public String getOnlineCode()
    {
        return onlineCode;
    }

    public void setOnlineCode(String onlineCode)
    {
        this.onlineCode = onlineCode;
    }

    public String getSpecimenType()
    {
        return specimenType;
    }

    public void setSpecimenType(String specimenType)
    {
        this.specimenType = specimenType;
    }

    public boolean isSent() {
        return sent;
    }

    public void setSent(boolean sent) {
        this.sent = sent;
    }
}
