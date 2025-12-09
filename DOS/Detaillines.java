
package DOS;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

/**
 * @date:   Mar 3, 2012
 * @author: Ryan
 * 
 * @project: CSSLibrary 
 * @package: DAOS
 * @file name: Detaillines.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc.  All Rights Reserved
 */



@Entity
@Table(name = "detaillines", catalog = "css", schema = "")
@NamedQueries({
    @NamedQuery(name = "Detaillines.findAll", query = "SELECT d FROM Detaillines d")})
public class Detaillines implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "iddetailLines", nullable = false)
    private Integer iddetailLines;
    @Basic(optional = false)
    @Column(name = "detail", nullable = false)
    private int detail;
    @Basic(optional = false)
    @Column(name = "price", nullable = false)
    private BigDecimal price;
    @Column(name = "cpt", length = 10)
    private String cpt;
    @Column(name = "cpt_multiplier")
    private Integer cptMultiplier;
    @Basic(optional = false)
    @Column(name = "created", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;
    @Basic(optional = false)
    @Column(name = "insurance", nullable = false)
    private int insurance;
    @Column(name = "modifier", length = 2)
    private String modifier;
    @Column(name = "modifier2", length = 2)
    private String modifier2;
    @Column(name = "modifier3", length = 2)
    private String modifier3;
    @Column(name = "modifier4", length = 2)
    private String modifier4;
    @Column(name = "modifier5", length = 2)
    private String modifier5;
    @Column(name = "modifier6", length = 2)
    private String modifier6;
    @Column(name = "complete", length = 1)
    private boolean complete;

    public Detaillines() {
    }

    public Detaillines(Integer iddetailLines) {
        this.iddetailLines = iddetailLines;
    }

    public Detaillines(Integer iddetailLines, int detail, BigDecimal price, Date created, int insurance) {
        this.iddetailLines = iddetailLines;
        this.detail = detail;
        this.price = price;
        this.created = created;
        this.insurance = insurance;
    }

    public Integer getIddetailLines() {
        return iddetailLines;
    }

    public void setIddetailLines(Integer iddetailLines) {
        this.iddetailLines = iddetailLines;
    }

    public int getDetail() {
        return detail;
    }

    public void setDetail(int detail) {
        this.detail = detail;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getCpt() {
        return cpt;
    }

    public void setCpt(String cpt) {
        this.cpt = cpt;
    }

    public Integer getCptMultiplier() {
        return cptMultiplier;
    }

    public void setCptMultiplier(Integer cptMultiplier) {
        this.cptMultiplier = cptMultiplier;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public int getInsurance() {
        return insurance;
    }

    public void setInsurance(int insurance) {
        this.insurance = insurance;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    public String getModifier2()
    {
        return modifier2;
    }

    public void setModifier2(String modifier2)
    {
        this.modifier2 = modifier2;
    }

    public String getModifier3()
    {
        return modifier3;
    }

    public void setModifier3(String modifier3)
    {
        this.modifier3 = modifier3;
    }

    public String getModifier4()
    {
        return modifier4;
    }

    public void setModifier4(String modifier4)
    {
        this.modifier4 = modifier4;
    }

    public String getModifier5()
    {
        return modifier5;
    }

    public void setModifier5(String modifier5)
    {
        this.modifier5 = modifier5;
    }

    public String getModifier6()
    {
        return modifier6;
    }

    public void setModifier6(String modifier6)
    {
        this.modifier6 = modifier6;
    }

    public boolean isComplete()
    {
        return complete;
    }

    public void setComplete(boolean complete)
    {
        this.complete = complete;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iddetailLines != null ? iddetailLines.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Detaillines)) {
            return false;
        }
        Detaillines other = (Detaillines) object;
        if ((this.iddetailLines == null && other.iddetailLines != null) || (this.iddetailLines != null && !this.iddetailLines.equals(other.iddetailLines))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DAOS.Detaillines[ iddetailLines=" + iddetailLines + " ]";
    }

}
