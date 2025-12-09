
package BOS;


import BL.SettingsBL;
import BL.SettingsBL.SettingTypeDefinition;
import DOS.Setting;
import DOS.SettingLookup;
import DOS.SettingType;
import DOS.SettingValueType;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Wraps the DOs associated with a setting
 */
public class SettingBO
{
    private final SettingType settingType;          // e.g. Payor, Order, Patient
    
    private final SettingValueType settingValueType;// e.g. Boolean, Integer
    
    private final Setting setting;                  // The actual setting name, description, default value, etc.
    
    private final SettingLookup settingLookup;      // optional; a mapping of a system object to this setting
                                                    //  this will be [NULL] if the setting is
                                                    //  system-wide and doesn't map to a particular row.
                                                    //  in that case, the system's setting is the
                                                    //  settings row's 'defaultValue'
    
    private final SettingTypeDefinition settingTypeDefinition ;// Details of the setting type
    
    public String dateFormat = "yyyy-MM-dd hh:mm:ss";
    
    public SettingBO(
            SettingType settingType,
            SettingValueType settingValueType,
            Setting setting,
            SettingLookup settingLookup,
            SettingTypeDefinition settingTypeDefinition)
    {
        this.settingType = settingType;
        this.settingValueType = settingValueType;
        this.setting = setting;
        this.settingLookup = settingLookup;
        this.settingTypeDefinition = settingTypeDefinition;
    }
    
    public Setting getSetting()
    {
        return this.setting;
    }
    
    public SettingType getSettingType()
    {
        return this.settingType;
    }
    
    public SettingLookup getSettingLookup()
    {
        return this.settingLookup;
    }
    
    public SettingValueType getSettingValueType()
    {
        return this.settingValueType;
    }
    
    public SettingTypeDefinition getSettingTypeDefinition()
    {
        return settingTypeDefinition;
    }
    
    public Class getSettingValueClass()
    {
        return SettingsBL.SettingValueTypeDefinition.getBySystemName(this.settingValueType.getSystemName()).javaClass;
    }
    
    public Boolean getBooleanValue()
    {
        Object value = getValue();
        if (value == null) return null;
        return Boolean.valueOf(value.toString());
    }
    
    public Integer getIntegerValue() throws NumberFormatException
    {
        Object value = getValue();
        if (value == null) return null;
        return Integer.valueOf(value.toString());
    }
    
    public String getStringValue()
    {
        Object value = getValue();
        if (value == null) return null;
        return value.toString();
    }
    
    public Double getDoubleValue() throws NumberFormatException
    {
        Object value = getValue();
        if (value == null) return null;
        return Double.valueOf(value.toString());
    }
    
    public Date getDateValue() throws Exception
    {
        Object value = getValue();
        if (value == null) return null;
        
        DateFormat format = new SimpleDateFormat(dateFormat, Locale.ENGLISH);
        return format.parse(value.toString());
    }
    
    private Object getValue()
    {
        if (settingLookup != null)
        {
            return settingLookup.getValue();
        }
        else
        {
            return setting.getDefaultValue();
        }
    }
    
    /**
     * Attempts to set the setting's value; throws an exception if the provided
     *  setting value is incompatible with the settingValueType and whether
     *  a blank value is acceptable for this setting.
     * 
     * @param valueObj
     * @throws Exception 
     */
    public void setValue(Object valueObj) throws Exception
    {
        String value = null;
        
        // If it's blank and that's allowed, make the string value NULL
        if (setting.isAllowBlank() && (valueObj == null || valueObj.toString().isEmpty()))
        {
            value = null;
        }
        else
        {
            // Otherwise try to parse out the value by type
            Class settingValueClass = getSettingValueClass();
            try
            {
                if (settingValueClass == String.class)
                {
                    if (valueObj == null || valueObj.toString().isEmpty()) throw new Exception();
                }
                else if (settingValueClass == Boolean.class)
                {                
                    Boolean boolVal = Boolean.valueOf(valueObj.toString());
                }
                else if (settingValueClass == Integer.class)
                {
                    Integer intVal = Integer.valueOf(valueObj.toString());
                }
                else if (settingValueClass == Double.class)
                {
                    Double doubleVal = Double.valueOf(valueObj.toString());
                }
                else if (settingValueClass == Date.class)
                {
                    DateFormat format = new SimpleDateFormat(dateFormat, Locale.ENGLISH);
                    format.parse(valueObj.toString());                    
                }
            }
            catch (Exception ex)
            {
                throw new Exception("SettingBO: Attempting to setValue a string"
                        + " '" + valueObj.toString() + "' which is not of the"
                        + " expected type " + getSettingValueClass().getName());
            }
            
            value = valueObj.toString();            
        }

        // Set it depending on whether this is an "overall" setting, or a 
        // setting attached to an individual object through a lookup table
        if (settingLookup != null)
        {
            settingLookup.setValue(value);
        }
        else
        {
            setting.setDefaultValue(value);
        }        
    }
    
    /**
     * Returns true if the setting applies to the system as a whole, and not
     *  an individual object (e.g. client, patient, order..)
     * @return 
     */
    public boolean isSystemWide()
    {
        return (this.settingTypeDefinition.lookupTableName == null);
    }

}
