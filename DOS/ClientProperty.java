
package DOS;

import Utility.Diff;
import java.util.Date;

public class ClientProperty
{
    private int id;
    private String name;
    private String description;
    private boolean active;
    private Date createdDate;
    private int createdByUserId;
    private boolean isUserEditable;   // If false, user cannot change or delete
    private boolean isUserSelectable; // If false, property is read-only
    private boolean isUserVisible;    // If true, the property is not visible at
                                      // all to users and is therefore not
                                      // editable or selectable

    public ClientProperty() {}

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    @Diff(fieldName="name")
    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    @Diff(fieldName="description")
    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    @Diff(fieldName="active")
    public boolean isActive()
    {
        return active;
    }

    public void setActive(boolean active)
    {
        this.active = active;
    }

    public Date getCreatedDate()
    {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate)
    {
        this.createdDate = createdDate;
    }

    public int getCreatedByUserId()
    {
        return createdByUserId;
    }

    public void setCreatedByUserId(int createdByUserId)
    {
        this.createdByUserId = createdByUserId;
    }

    @Diff(fieldName="isUserEditable")
    public boolean isUserEditable()
    {
        return isUserEditable;
    }

    public void setUserEditable(boolean isUserEditable)
    {
        this.isUserEditable = isUserEditable;
    }

    @Diff(fieldName="isUserSelectable")
    public boolean isUserSelectable() {
        return isUserSelectable;
    }

    public void setUserSelectable(boolean isUserSelectable)
    {
        this.isUserSelectable = isUserSelectable;
    }

    @Diff(fieldName="isUserVisible")
    public boolean isUserVisible()
    {
        return isUserVisible;
    }

    public void setUserVisible(boolean isUserVisible)
    {
        this.isUserVisible = isUserVisible;
    }
    
    @Override
    public String toString()
    {
        return String.valueOf(id);
    }

    @Override
    public int hashCode()
    {
        return this.id;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        final ClientProperty other = (ClientProperty) obj;
        return other.equals(this);
    }
    
    public ClientProperty copy()
    {
        ClientProperty copy = new ClientProperty();
        copy.setId(id);
        copy.setName(name);
        copy.setDescription(description);
        copy.setActive(active);
        copy.setCreatedDate(createdDate);
        copy.setCreatedByUserId(createdByUserId);
        copy.setUserEditable(isUserEditable);
        copy.setUserSelectable(isUserSelectable);
        copy.setUserVisible(isUserVisible);
        return copy;
    }
}
