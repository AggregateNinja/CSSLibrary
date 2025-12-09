/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DOS;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author TomR
 */
public class AdvancedPrescriptions
{    
    private static final long serialVersionUID = 1L;    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idAdvancedPrescriptions")
    private Integer idAdvancedPrescriptions;
    @Column(name = "advancedOrderId")
    private Integer advancedOrderId;
    @Column(name = "drugId")
    private Integer drugId;
    
    public AdvancedPrescriptions() {}
    
    public AdvancedPrescriptions(
        Integer idAdvancedPrescriptions,
        Integer advancedOrderId,
        Integer drugId)
    {
        this.idAdvancedPrescriptions = idAdvancedPrescriptions;
        this.advancedOrderId = advancedOrderId;
        this.drugId = drugId;        
    }

    public Integer getIdAdvancedPrescriptions() {
        return idAdvancedPrescriptions;
    }

    public void setIdAdvancedPrescriptions(Integer idAdvancedPrescriptions) {
        this.idAdvancedPrescriptions = idAdvancedPrescriptions;
    }

    public Integer getAdvancedOrderId() {
        return advancedOrderId;
    }

    public void setAdvancedOrderId(Integer advancedOrderId) {
        this.advancedOrderId = advancedOrderId;
    }

    public Integer getDrugId() {
        return drugId;
    }

    public void setDrugId(Integer drugId) {
        this.drugId = drugId;
    }
    
    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AdvancedPrescriptions))
        {
            return false;
        }
        AdvancedPrescriptions other = (AdvancedPrescriptions) object;
        if ((this.idAdvancedPrescriptions == null && other.idAdvancedPrescriptions != null)
                || (this.idAdvancedPrescriptions != null && !this.idAdvancedPrescriptions.equals(other.idAdvancedPrescriptions)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "DOS.AdvancedPrescriptions[ id=" + idAdvancedPrescriptions + ", advancedOrderId=" + advancedOrderId + ", drugId =" + drugId + " ]";
    }            
}

