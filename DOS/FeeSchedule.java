package DOS;

import java.util.Objects;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class FeeSchedule
{

    private static final long serialversionUID = 42L;

    private Integer idFeeSchedules;
    private String abbr;
    private String name;
    private String description;
    private boolean quantifyLines;

    public Integer getIdFeeSchedules()
    {
        return idFeeSchedules;
    }

    public void setIdFeeSchedules(Integer idFeeSchedules)
    {
        this.idFeeSchedules = idFeeSchedules;
    }

    public String getAbbr()
    {
        return abbr;
    }

    public void setAbbr(String abbr)
    {
        this.abbr = abbr;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }
    
    public boolean getQuantifyLines()
    {
        return quantifyLines;
    }
    
    public void setQuantifyLines(boolean quantifyLines)
    {
        this.quantifyLines = quantifyLines;
    }

    @Override
    public String toString()
    {
        return ToStringBuilder.reflectionToString(this);
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
        final FeeSchedule other = (FeeSchedule) obj;

        if (idFeeSchedules == null || idFeeSchedules == 0)
        {
            return false;
        }

        if (other.getIdFeeSchedules() == null || other.getIdFeeSchedules() == 0)
        {
            return false;
        }

        return (idFeeSchedules.equals(other.getIdFeeSchedules()));
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.idFeeSchedules);
        return hash;
    }
}
