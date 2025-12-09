/*
 * Computer Service & Support, Inc.  All Rights Reserved Sep 3, 2014
 */

package DOS;

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
 * @date:   Sep 3, 2014  1:52:32 PM
 * @author: Ryan Piper ryan@csslis.com
 * 
 * @project: CSSLibrary
 * @file name: SecButton.java  (UTF-8)
 *
 * @Description: 
 *
 */

@Entity
@Table(name = "sec_button")
@NamedQueries({
    @NamedQuery(name = "SecButton.findAll", query = "SELECT s FROM SecButton s")})
public class SecButton implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Column(name = "parent")
    private Integer parent;
    @Basic(optional = false)
    @Column(name = "level")
    private int level;

    public SecButton() {
    }

    public SecButton(Integer id) {
        this.id = id;
    }

    public SecButton(Integer id, String name, int level) {
        this.id = id;
        this.name = name;
        this.level = level;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getParent() {
        return parent;
    }

    public void setParent(Integer parent) {
        this.parent = parent;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SecButton)) {
            return false;
        }
        SecButton other = (SecButton) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DOS.SecButton[ id=" + id + " ]";
    }

}
