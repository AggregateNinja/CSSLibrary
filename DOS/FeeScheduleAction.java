package DOS;

import java.util.Objects;

/**
 * Data object for a fee schedule action (cpt or test). The Enums in this class
 * are mapped to the system name and type, which are used in combination to
 * refer to these actions within the application. They should not be changed as
 * it will break code. If a user wants to change the display of the action, it
 * can be done by changing the Name.
 *
 * @author Tom Rush <trush at csslis.com>
 */
public class FeeScheduleAction
{

    private static final long serialversionUID = 42L;

    private Integer idFeeScheduleActions;
    private String name;
    private String systemName;
    private Integer sortOrder;
    private String actionTypeName;

    public enum Action
    {

        BREAK_DOWN("breakdown"),
        FREE("free"),
        IGNORE("ignore"),
        UNQUANTIFIED("unquantified");

        String systemName;

        Action(String systemName)
        {
            this.systemName = systemName;
        }

        public String getSystemName()
        {
            return this.systemName;
        }
    }

    public enum TypeName
    {

        TEST("test"),
        CPT("cpt");

        String typeName;

        TypeName(String typeName)
        {
            this.typeName = typeName;
        }

        public String getTypeName()
        {
            return this.typeName;
        }
    }

    public Integer getIdFeeScheduleActions()
    {
        return idFeeScheduleActions;
    }

    public void setIdFeeScheduleActions(Integer idFeeScheduleActions)
    {
        this.idFeeScheduleActions = idFeeScheduleActions;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getSystemName()
    {
        return systemName;
    }

    public void setSystemName(String systemName)
    {
        this.systemName = systemName;
    }

    public Integer getSortOrder()
    {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder)
    {
        this.sortOrder = sortOrder;
    }

    public String getActionTypeName()
    {
        return actionTypeName;
    }

    public void setActionTypeName(String actionTypeName)
    {
        this.actionTypeName = actionTypeName;
    }

    public boolean is(Action action)
    {
        return (this.getSystemName() != null && this.getSystemName().equals(action.getSystemName()));
    }

    @Override
    public String toString()
    {
        return getName();
    }

    @Override
    public int hashCode()
    {
        int hash = 5;
        hash = 53 * hash + Objects.hashCode(this.idFeeScheduleActions);
        return hash;
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
        final FeeScheduleAction other = (FeeScheduleAction) obj;
        if (!Objects.equals(this.idFeeScheduleActions, other.idFeeScheduleActions))
        {
            return false;
        }
        return true;
    }

}
