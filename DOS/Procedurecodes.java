
package DOS;

import java.io.Serializable;
import javax.persistence.*;

/**
 * @date:   Mar 26, 2012
 * @author: Ryan
 * 
 * @project: CSSLibrary 
 * @package: DOS
 * @file name: Procedurecodes.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc.  All Rights Reserved
 */



@Entity
@Table(name = "procedurecodes")
@NamedQueries({
    @NamedQuery(name = "Procedurecodes.findAll", query = "SELECT p FROM Procedurecodes p")})
public class Procedurecodes implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idprocedureCodes")
    private Integer idprocedureCodes;
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @Column(name = "cpt")
    private String cpt;
    @Column(name = "modifier1")
    private String modifier1;
    @Column(name = "modifier2")
    private String modifier2;
    @Column(name = "modifier3")
    private String modifier3;
    @Column(name = "modifier4")
    private String modifier4;
    @Column(name = "modifier5")
    private String modifier5;
    @Column(name = "modifier6")
    private String modifier6;
    @Column(name = "multiplier")
    private Integer multiplier;
    @Basic(optional = false)
    @Column(name = "test")
    private int test;

    public Procedurecodes() {
    }

    public Procedurecodes(Integer idprocedureCodes) {
        this.idprocedureCodes = idprocedureCodes;
    }

    public Procedurecodes(Integer idprocedureCodes, String cpt, int test) {
        this.idprocedureCodes = idprocedureCodes;
        this.cpt = cpt;
        this.test = test;
    }

    public Integer getIdprocedureCodes() {
        return idprocedureCodes;
    }

    public void setIdprocedureCodes(Integer idprocedureCodes) {
        this.idprocedureCodes = idprocedureCodes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpt() {
        return cpt;
    }

    public void setCpt(String cpt) {
        this.cpt = cpt;
    }

    public String getModifier1() {
        return modifier1;
    }

    public void setModifier1(String modifier1) {
        this.modifier1 = modifier1;
    }

    public String getModifier2() {
        return modifier2;
    }

    public void setModifier2(String modifier2) {
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

    public Integer getMultiplier()
    {
        return multiplier;
    }

    public void setMultiplier(Integer multiplier)
    {
        this.multiplier = multiplier;
    }

    public int getTest() {
        return test;
    }

    public void setTest(int test) {
        this.test = test;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idprocedureCodes != null ? idprocedureCodes.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Procedurecodes)) {
            return false;
        }
        Procedurecodes other = (Procedurecodes) object;
        if ((this.idprocedureCodes == null && other.idprocedureCodes != null) || (this.idprocedureCodes != null && !this.idprocedureCodes.equals(other.idprocedureCodes))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DOS.Procedurecodes[ idprocedureCodes=" + idprocedureCodes + " ]";
    }

}
