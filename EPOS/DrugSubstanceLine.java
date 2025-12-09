package EPOS;

/**
 * @date:   Jun 18, 2013
 * @author: CSS Dev
 * 
 * @project:  
 * @package: EPOS
 * @file name: DrugSubstanceLine.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc.  All Rights Reserved
 */

public class DrugSubstanceLine 
{
    Integer index;
    String genericName;
    String substance;

    public DrugSubstanceLine()
    {
    }

    public DrugSubstanceLine(Integer index, String genericName, String substance)
    {
        this.index = index;
        this.genericName = genericName;
        this.substance = substance;
    }

    public Integer getIndex()
    {
        return index;
    }

    public void setIndex(Integer index)
    {
        this.index = index;
    }

    public String getGenericName()
    {
        return genericName;
    }

    public void setGenericName(String genericName)
    {
        this.genericName = genericName;
    }

    public String getSubstance()
    {
        return substance;
    }

    public void setSubstance(String substance)
    {
        this.substance = substance;
    }
    
    
}
