/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DOS;

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
 * @author rypip_000
 */
@Entity
@Table(name = "microOrders")
@NamedQueries({
    @NamedQuery(name = "MicroOrders.findAll", query = "SELECT m FROM MicroOrders m")})
public class MicroOrders implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idmicroOrders")
    private Integer idmicroOrders;
    @Basic(optional = false)
    @Column(name = "idResults")
    private Integer idResults;
    @Basic(optional = false)
    @Column(name = "idmicroSites")
    private Integer idmicroSites;
    @Basic(optional = false)
    @Column(name = "idmicroSources")
    private Integer idmicroSources;
    @Column(name = "comment")
    private String comment;
    @Basic(optional = false)
    @Column(name = "complete")
    private boolean complete;
    @Basic(optional = false)
    @Column(name = "completedBy")
    private Integer completedBy;
    @Column(name = "completedOn")
    @Temporal(TemporalType.TIMESTAMP)
    private Date completedOn;
    @Column(name = "colonyCount")
    private String colonyCount;
    @Column(name = "finalReport")
    private String finalReport;
    @Column(name = "prelimReport")
    private String prelimReport;
    @Column(name = "gramStain")
    private String gramStain;

    public MicroOrders() {
    }

    public MicroOrders(Integer idmicroOrders) {
        this.idmicroOrders = idmicroOrders;
    }

    public MicroOrders(Integer idmicroOrders, boolean complete) {
        this.idmicroOrders = idmicroOrders;
        this.complete = complete;
    }

    public Integer getIdmicroOrders() {
        return idmicroOrders;
    }

    public void setIdmicroOrders(Integer idmicroOrders) {
        this.idmicroOrders = idmicroOrders;
    }

    public Integer getIdResults() {
        return idResults;
    }

    public void setIdResults(Integer idResults) {
        this.idResults = idResults;
    }

    public Integer getIdmicroSites() {
        return idmicroSites;
    }

    public void setIdmicroSites(Integer idmicroSites) {
        this.idmicroSites = idmicroSites;
    }

    public Integer getIdmicroSources() {
        return idmicroSources;
    }

    public void setIdmicroSources(Integer idmicroSources) {
        this.idmicroSources = idmicroSources;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean getComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public Integer getCompletedBy() {
        return completedBy;
    }

    public void setCompletedBy(Integer completedBy) {
        this.completedBy = completedBy;
    }

    public Date getCompletedOn() {
        return completedOn;
    }

    public void setCompletedOn(Date completedOn) {
        this.completedOn = completedOn;
    }

    public String getColonyCount() {
        return colonyCount;
    }

    public void setColonyCount(String colonyCount) {
        this.colonyCount = colonyCount;
    }

    public String getFinalReport() {
        return finalReport;
    }

    public void setFinalReport(String finalReport) {
        this.finalReport = finalReport;
    }

    public String getPrelimReport() {
        return prelimReport;
    }

    public void setPrelimReport(String prelimReport) {
        this.prelimReport = prelimReport;
    }

    public String getGramStain() {
        return gramStain;
    }

    public void setGramStain(String gramStain) {
        this.gramStain = gramStain;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idmicroOrders != null ? idmicroOrders.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MicroOrders)) {
            return false;
        }
        MicroOrders other = (MicroOrders) object;
        if ((this.idmicroOrders == null && other.idmicroOrders != null) || (this.idmicroOrders != null && !this.idmicroOrders.equals(other.idmicroOrders))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DOS.MicroOrders[ idmicroOrders=" + idmicroOrders + " ]";
    }
    
}
