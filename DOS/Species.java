
package DOS;

import java.io.Serializable;
import javax.persistence.*;

/**
 * @date:   Mar 3, 2012
 * @author: Ryan
 * 
 * @project: CSSLibrary 
 * @package: DAOS
 * @file name: Species.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc.  All Rights Reserved
 */



@Entity
@Table(name = "species", catalog = "css", schema = "")
@NamedQueries({
    @NamedQuery(name = "Species.findAll", query = "SELECT s FROM Species s")})
public class Species implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idspecies", nullable = false)
    private Integer idspecies;
    @Basic(optional = false)
    @Column(name = "name", nullable = false, length = 45)
    private String name;

    public Species() {
    }

    public Species(int idspecies) {
        this.idspecies = idspecies;
    }

    public Species(int idspecies, String name) {
        this.idspecies = idspecies;
        this.name = name;
    }

    public int getIdspecies() {
        return idspecies;
    }

    public void setIdspecies(int idspecies) {
        this.idspecies = idspecies;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idspecies != null ? idspecies.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Species)) {
            return false;
        }
        Species other = (Species) object;
        if ((this.idspecies == null && other.idspecies != null) || (this.idspecies != null && !this.idspecies.equals(other.idspecies))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DAOS.Species[ idspecies=" + idspecies + " ]";
    }

}
