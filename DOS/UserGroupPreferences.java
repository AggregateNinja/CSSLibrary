package DOS;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @date 10/26/2015
 * @author Robert Hussey <r.hussey@csslis.com>
 */
@Entity
@Table(name = "userGroupPreferences")
public class UserGroupPreferences implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idUserGroupPreferences")
    private Integer idUserGroupPreferences;
    @Basic(optional = false)
    @Column(name = "preferenceValueMapId")
    private Integer preferenceValueMapId;
    @Basic(optional = false)
    @Column(name = "userGroupId")
    private Integer userGroupId;
    @Column(name = "userId")
    private Integer userId;

    public Integer getIdUserGroupPreferences()
    {
        return idUserGroupPreferences;
    }

    public void setIdUserGroupPreferences(Integer idUserGroupPreferences)
    {
        this.idUserGroupPreferences = idUserGroupPreferences;
    }

    public Integer getPreferenceValueMapId()
    {
        return preferenceValueMapId;
    }

    public void setPreferenceValueMapId(Integer preferenceValueMapId)
    {
        this.preferenceValueMapId = preferenceValueMapId;
    }

    public Integer getUserGroupId()
    {
        return userGroupId;
    }

    public void setUserGroupId(Integer userGroupId)
    {
        this.userGroupId = userGroupId;
    }

    public Integer getUserId()
    {
        return userId;
    }

    public void setUserId(Integer userId)
    {
        this.userId = userId;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idUserGroupPreferences != null ? idUserGroupPreferences.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Users)) {
            return false;
        }
        UserGroupPreferences other = (UserGroupPreferences) object;
        return (this.idUserGroupPreferences != null || other.idUserGroupPreferences == null) &&
               (this.idUserGroupPreferences == null || this.idUserGroupPreferences.equals(other.idUserGroupPreferences));
    }

    @Override
    public String toString() {
        return "DOS.UserGroupPreferences[ idUserGroupPreferences=" + idUserGroupPreferences + " ]";
    }
}
