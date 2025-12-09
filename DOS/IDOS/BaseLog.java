/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DOS.IDOS;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author
 * Keith
 * Maggio
 * <keith@csslis.com>
 */
public abstract class BaseLog implements Serializable
{
    @Column(name = "idResults")
    private Integer idResults;
    @Basic(optional = false)
    @Column(name = "action")
    private String action;
    @Column(name = "idUser")
    private int idUser;
    @Basic(optional = false)
    @Column(name = "createdDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Column(name = "preValue")
    private String preValue;
    @Column(name = "newValue")
    private String newValue;
    @Column(name = "preRemark")
    private Integer preRemark;
    @Column(name = "newRemark")
    private Integer newRemark;
    @Column(name = "description")
    private String description;
    @Column(name = "idOrders")
    private Integer idOrders;
    @Column(name = "idPatients")
    private Integer idPatients;
    @Column(name = "idSubscriber")
    private Integer idSubscriber;
    @Column(name = "idTests")
    private Integer idTests;
    @Column(name = "prevClient")
    private Integer prevClient;
    @Column(name = "newClient")
    private Integer newClient;
    @Column(name = "prevDoctor")
    private Integer prevDoctor;
    @Column(name = "newDoctor")
    private Integer newDoctor;
    @Column(name = "preInsurance")
    private Integer preInsurance;
    @Column(name = "newInsurance")
    private Integer newInsurance;
    @Column(name = "preSecondInsurance")
    private Integer preSecondInsurance;
    @Column(name = "newSecondInsurance")
    private Integer newSecondInsurance;
    @Column(name = "prePatient")
    private Integer prePatient;
    @Column(name = "newPatient")
    private Integer newPatient;
    @Column(name = "preSubscriber")
    private Integer preSubscriber;
    @Column(name = "newSubscriber")
    private Integer newSubscriber;
    
    public Integer getIdResults()
    {
        return idResults;
    }

    public void setIdResults(Integer idResults)
    {
        this.idResults = idResults;
    }

    public String getAction()
    {
        return action;
    }

    public void setAction(String action)
    {
        this.action = action;
    }

    public int getIdUser()
    {
        return idUser;
    }

    public void setIdUser(int idUser)
    {
        this.idUser = idUser;
    }

    public Date getCreatedDate()
    {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate)
    {
        this.createdDate = createdDate;
    }

    public String getPreValue()
    {
        return preValue;
    }

    public void setPreValue(String preValue)
    {
        this.preValue = preValue;
    }

    public String getNewValue()
    {
        return newValue;
    }

    public void setNewValue(String newValue)
    {
        this.newValue = newValue;
    }

    public Integer getPreRemark()
    {
        return preRemark;
    }

    public void setPreRemark(Integer preRemark)
    {
        this.preRemark = preRemark;
    }

    public Integer getNewRemark()
    {
        return newRemark;
    }

    public void setNewRemark(Integer newRemark)
    {
        this.newRemark = newRemark;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public Integer getIdOrders()
    {
        return idOrders;
    }

    public void setIdOrders(Integer idOrders)
    {
        this.idOrders = idOrders;
    }

    public Integer getIdPatients()
    {
        return idPatients;
    }

    public void setIdPatients(Integer idPatients)
    {
        this.idPatients = idPatients;
    }

    public Integer getIdSubscriber()
    {
        return idSubscriber;
    }

    public void setIdSubscriber(Integer idSubscriber)
    {
        this.idSubscriber = idSubscriber;
    }

    public Integer getIdTests()
    {
        return idTests;
    }

    public void setIdTests(Integer idTests)
    {
        this.idTests = idTests;
    }

    public Integer getPrevClient()
    {
        return prevClient;
    }

    public void setPrevClient(Integer prevClient)
    {
        this.prevClient = prevClient;
    }

    public Integer getNewClient()
    {
        return newClient;
    }

    public void setNewClient(Integer newClient)
    {
        this.newClient = newClient;
    }

    public Integer getPrevDoctor()
    {
        return prevDoctor;
    }

    public void setPrevDoctor(Integer prevDoctor)
    {
        this.prevDoctor = prevDoctor;
    }

    public Integer getNewDoctor()
    {
        return newDoctor;
    }

    public void setNewDoctor(Integer newDoctor)
    {
        this.newDoctor = newDoctor;
    }

    public Integer getPreInsurance()
    {
        return preInsurance;
    }

    public void setPreInsurance(Integer preInsurance)
    {
        this.preInsurance = preInsurance;
    }

    public Integer getNewInsurance()
    {
        return newInsurance;
    }

    public void setNewInsurance(Integer newInsurance)
    {
        this.newInsurance = newInsurance;
    }

    public Integer getPreSecondInsurance()
    {
        return preSecondInsurance;
    }

    public void setPreSecondInsurance(Integer preSecondInsurance)
    {
        this.preSecondInsurance = preSecondInsurance;
    }

    public Integer getNewSecondInsurance()
    {
        return newSecondInsurance;
    }

    public void setNewSecondInsurance(Integer newSecondInsurance)
    {
        this.newSecondInsurance = newSecondInsurance;
    }

    public Integer getPrePatient()
    {
        return prePatient;
    }

    public void setPrePatient(Integer prePatient)
    {
        this.prePatient = prePatient;
    }

    public Integer getPreSubscriber()
    {
        return preSubscriber;
    }

    public void setPreSubscriber(Integer preSubscriber)
    {
        this.preSubscriber = preSubscriber;
    }

    public Integer getNewPatient()
    {
        return newPatient;
    }

    public void setNewPatient(Integer newPatient)
    {
        this.newPatient = newPatient;
    }

    public Integer getNewSubscriber()
    {
        return newSubscriber;
    }

    public void setNewSubscriber(Integer newSubscriber)
    {
        this.newSubscriber = newSubscriber;
    }
    
}
