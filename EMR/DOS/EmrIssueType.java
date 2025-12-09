
package EMR.DOS;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Id;

public class EmrIssueType
{
    @Id
    @Basic(optional = false)
    @Column(name = "")
    private Integer idIssueTypes;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;

    public Integer getIdIssueTypes()
    {
        return idIssueTypes;
    }

    public void setIdIssueTypes(Integer idIssueTypes)
    {
        this.idIssueTypes = idIssueTypes;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
    
    @Override
    public String toString()
    {
        return this.name;
    }

    @Override
    public boolean equals(Object obj)
    {
        return super.equals(obj);
    }

    @Override
    public int hashCode()
    {
        return this.idIssueTypes;
    }
    
}
