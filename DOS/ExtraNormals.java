/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DOS;

import static Utility.DateUtil.dateBetween;
import java.io.Serializable;
import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;
import org.joda.time.LocalDate;

/**
 *
 * @author Ryan
 */
@Entity
@Table(name = "extranormals")
@NamedQueries({
    @NamedQuery(name = "Extranormals.findAll", query = "SELECT e FROM Extranormals e")})
public class ExtraNormals implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idextraNormals")
    private Integer idextraNormals;
    @Column(name = "test")
    private int test;
    @Column(name = "species")
    private int species;
    @Basic(optional = false)
    @Column(name = "lowNormal")
    private double lowNormal;
    @Basic(optional = false)
    @Column(name = "highNormal")
    private double highNormal;
    @Basic(optional = false)
    @Column(name = "alertLow")
    private double alertLow;
    @Basic(optional = false)
    @Column(name = "alertHigh")
    private double alertHigh;
    @Basic(optional = false)
    @Column(name = "criticalLow")
    private double criticalLow;
    @Basic(optional = false)
    @Column(name = "criticalHigh")
    private double criticalHigh;
    
    @Basic(optional = false)
    @Column(name = "ageLow")
    private int ageLow;
    
    @Basic(optional = false)
    @Column(name = "ageUnitsLow")
    private int ageUnitsLow;
 
    @Basic(optional = false)
    @Column(name = "ageHigh")
    private int ageHigh;
    
    @Basic(optional = false)
    @Column(name = "ageUnitsHigh")
    private int ageUnitsHigh;

    @Basic(optional = false)
    @Column(name = "sex")
    private String sex;
    @Column(name = "active")
    private boolean active;
    @Column(name = "deactivatedDate")
    private Date deactivatedDate;
    @Basic(optional = false)
    @Column(name = "printNormals")
    private String printNormals;
    @Basic(optional = false)
    @Column(name = "type")
    private int type;

    public ExtraNormals() {
    }

    public ExtraNormals(Integer idextraNormals) {
        this.idextraNormals = idextraNormals;
    }

    public ExtraNormals(Integer idextraNormals, double lowNormal, double highNormal, double alertLow, double alertHigh, double criticalLow, double criticalHigh, int ageLow, int ageUnitsLow, int ageHigh, int ageUnitsHigh, String sex) {
        this.idextraNormals = idextraNormals;
        this.lowNormal = lowNormal;
        this.highNormal = highNormal;
        this.alertLow = alertLow;
        this.alertHigh = alertHigh;
        this.criticalLow = criticalLow;
        this.criticalHigh = criticalHigh;
        this.ageLow = ageLow;
        this.ageUnitsLow = ageUnitsLow;
        this.ageHigh = ageHigh;
        this.ageUnitsHigh = ageUnitsHigh;
        this.sex = sex;
    }

    public Integer getIdextraNormals() {
        return idextraNormals;
    }

    public void setIdextraNormals(Integer idextraNormals) {
        this.idextraNormals = idextraNormals;
    }

    public int getTest() {
        return test;
    }

    public void setTest(int test) {
        this.test = test;
    }

    public int getSpecies() {
        return species;
    }

    public void setSpecies(int species) {
        this.species = species;
    }
    
    

    public double getLowNormal() {
        return lowNormal;
    }

    public void setLowNormal(double lowNormal) {
        this.lowNormal = lowNormal;
    }

    public double getHighNormal() {
        return highNormal;
    }

    public void setHighNormal(double highNormal) {
        this.highNormal = highNormal;
    }

    public double getAlertLow() {
        return alertLow;
    }

    public void setAlertLow(double alertLow) {
        this.alertLow = alertLow;
    }

    public double getAlertHigh() {
        return alertHigh;
    }

    public void setAlertHigh(double alertHigh) {
        this.alertHigh = alertHigh;
    }

    public double getCriticalLow() {
        return criticalLow;
    }

    public void setCriticalLow(double criticalLow) {
        this.criticalLow = criticalLow;
    }

    public double getCriticalHigh() {
        return criticalHigh;
    }

    public void setCriticalHigh(double criticalHigh) {
        this.criticalHigh = criticalHigh;
    }

    public int getAgeLow() {
        return ageLow;
    }
        
    public void setAgeLow(int ageLow) {
        this.ageLow = ageLow;
    }
    
    public int getAgeHigh() {
        return ageHigh;
    }
        
    public void setAgeHigh(int ageHigh) {
        this.ageHigh = ageHigh;
    }
    
    public int getAgeUnitsLow() {
        return this.ageUnitsLow;
    }
    
    public int getAgeUnitsHigh() {
        return this.ageUnitsHigh;
    }
    
    public String getAgeUnitsLowString() {
        return this.getAgeUnitsString(true);
    }
    
    public String getAgeUnitsHighString() {
        return this.getAgeUnitsString(false);
    }
    
    private String getAgeUnitsString(boolean low) {
        int ageUnits;
        if(low) {
            ageUnits = getAgeUnitsLow();
        }
        else {
            ageUnits = getAgeUnitsHigh();
        }
        
        if(ageUnits == 0) {
            return "Years";
        }
        else if (ageUnits == 1) {
            return "Months";
        }
        else if (ageUnits == 2) {
            return "Days";
        }
        else {
            return "Unknown Unit";
        }
    }
    
    public void setAgeUnitsLow(int ageUnitsLow) {
        this.ageUnitsLow = ageUnitsLow;
    }
    
    public void setAgeUnitsHigh(int ageUnitsHigh) {
        this.ageUnitsHigh = ageUnitsHigh;
    }
     
//     /**
//     * Checks if a date is between this ExtraNormal's low age and high age with respect to a compareDate.
//     */
//    public boolean containsDate(LocalDate date, LocalDate compareDate) {
//        return false; //dateBetween(date, getAgeLow(), getAgeLowMonths(), getAgeLowDays(), getAgeHigh(), getAgeHighMonths(), getAgeHighDays());
//    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Date getDeactivatedDate() {
        return deactivatedDate;
    }

    public void setDeactivatedDate(Date deactivatedDate) {
        this.deactivatedDate = deactivatedDate;
    }

    public String getPrintNormals() {
        return printNormals;
    }

    public void setPrintNormals(String printNormals) {
        this.printNormals = printNormals;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
    
    public ExtraNormals Copy()
    {
        ExtraNormals result = new ExtraNormals();
        result.setIdextraNormals(getIdextraNormals());
        result.setSpecies(getSpecies());
        result.setLowNormal(getLowNormal());
        result.setHighNormal(getHighNormal());
        result.setAlertLow(getAlertLow());
        result.setAlertHigh(getAlertHigh());
        result.setCriticalLow(getCriticalLow());
        result.setCriticalHigh(getCriticalHigh());
        result.setAgeLow(getAgeLow());
        result.setAgeHigh(getAgeHigh());
        result.setAgeUnitsLow(getAgeUnitsLow());
        result.setAgeUnitsHigh(getAgeUnitsHigh());
        result.setSex(getSex());
        result.setActive(getActive());
        result.setDeactivatedDate(getDeactivatedDate());
        result.setPrintNormals(getPrintNormals());
        result.setType(getType());
        result.setTest(getTest());
        
        return result;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idextraNormals != null ? idextraNormals.hashCode() : 0);
        return hash;
    }
   
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ExtraNormals)) {
            return false;
        }
        
        ExtraNormals other = (ExtraNormals) object;
        
        // Neither are saved yet; need to look at the data
        if (getIdextraNormals() == null && other.getIdextraNormals() == null)
        {
            if (getSpecies() != other.getSpecies()) return false;
            if (getLowNormal() != other.getLowNormal()) return false;
            if (getHighNormal() != other.getHighNormal()) return false;
            if (getAlertLow() != other.getAlertLow()) return false;
            if (getAlertHigh() != other.getAlertHigh()) return false;
            if (getAgeLow() != other.getAgeLow()) return false;
            if (getAgeHigh() != other.getAgeHigh()) return false;
            if (getAgeUnitsLow() != other.getAgeUnitsLow()) return false;
            if (getAgeUnitsHigh() != other.getAgeUnitsHigh()) return false;
            // sex can either be "Male", "Female" or null, so Objects.equals must be used.
            if (!Objects.equals(getSex(), other.getSex())) return false;
            if (getActive() != other.getActive()) return false;
            if (getDeactivatedDate() != other.getDeactivatedDate()) return false;
            if (!getPrintNormals().equals(other.getPrintNormals())) return false;
            if (getType() != other.getType()) return false;
            if (getTest() != other.getTest()) return false;
            return true;
        }
        
        if ((this.idextraNormals == null && other.idextraNormals != null) || (this.idextraNormals != null && !this.idextraNormals.equals(other.idextraNormals))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DOS.Extranormals[ idextraNormals=" + idextraNormals + " ]";
    }
    
}
