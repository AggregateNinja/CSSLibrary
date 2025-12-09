package Web.DOS;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @date:   Jun 2, 2014
 * @author: Keith Maggio <keith@csslis.com>
 * 
 * @project:  
 * @package: Web.DOS
 * @file name: WebUsers.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc.  All Rights Reserved
 */

@Entity 
@Table(name = "WebUsers")
@NamedQueries(
{
    @NamedQuery(name = "WebUsers.findAll", query = "SELECT w FROM WebUsers w")
})
public class WebUsers implements Serializable 
{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idUsers")
    private Integer idUsers;
    @Basic(optional = false)
    @Column(name = "email")
    private String email;
    @Basic(optional = false)
    @Column(name = "password")
    private String password;
    @Basic(optional = false)
    @Column(name = "userSalt")
    private String userSalt;
    @Basic(optional = false)
    @Column(name = "isVerified")
    private boolean isVerified;
    @Basic(optional = false)
    @Column(name = "verificationCode")
    private String verificationCode;
    @Basic(optional = false)
    @Column(name = "dateCreated")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;
    @Column(name = "dateUpdated")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateUpdated;
    @Basic(optional = false)
    @Column(name = "typeId")
    private Integer typeId;

    public WebUsers()
    {
    }

    public WebUsers(Integer idUsers)
    {
        this.idUsers = idUsers;
    }

    public WebUsers(Integer idUsers, String email, String password, String userSalt, boolean isVerified, String verificationCode, Date dateCreated)
    {
        this.idUsers = idUsers;
        this.email = email;
        this.password = password;
        this.userSalt = userSalt;
        this.isVerified = isVerified;
        this.verificationCode = verificationCode;
        this.dateCreated = dateCreated;
    }

    public Integer getIdUsers()
    {
        return idUsers;
    }

    public void setIdUsers(Integer idUsers)
    {
        this.idUsers = idUsers;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getUserSalt()
    {
        return userSalt;
    }

    public void setUserSalt(String userSalt)
    {
        this.userSalt = userSalt;
    }

    public boolean getIsVerified()
    {
        return isVerified;
    }

    public void setIsVerified(boolean isVerified)
    {
        this.isVerified = isVerified;
    }

    public String getVerificationCode()
    {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode)
    {
        this.verificationCode = verificationCode;
    }

    public Date getDateCreated()
    {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated)
    {
        this.dateCreated = dateCreated;
    }

    public Date getDateUpdated()
    {
        return dateUpdated;
    }

    public void setDateUpdated(Date dateUpdated)
    {
        this.dateUpdated = dateUpdated;
    }

    public Integer getTypeId()
    {
        return typeId;
    }

    public void setTypeId(Integer typeId)
    {
        this.typeId = typeId;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (idUsers != null ? idUsers.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof WebUsers))
        {
            return false;
        }
        WebUsers other = (WebUsers) object;
        if ((this.idUsers == null && other.idUsers != null) || (this.idUsers != null && !this.idUsers.equals(other.idUsers)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "Web.DOS.WebUsers[ idUsers=" + idUsers + " ]";
    }

}
