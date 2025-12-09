
package Utility;

import DOS.Departments;
import DOS.Locations;

/**
 * Provides definitions for system objects that use the new address tables
 * 
 * Allows the use of a generic DAO to insert/update/delete address lookups
 * and avoid duplicating code
 */
public enum AddressLookupDefinition
{
    LOCATION(Locations.class, "locationAddresses","idLocationAddresses","locationId", "locationAddressLog"),
    DEPARTMENT(Departments.class, "departmentAddresses","idDepartmentAddresses","departmentId", "departmentAddressLog");

    public Class classType;         // The actual data object type
    public String tableName;        // The name of the address lookup table for this object
    public String uniqueIdName;     // The unique database identifier for the data object
    public String foreignKeyName;   // How the unique id looks in other tables
    public String diffLogTableName; // The name of the diff log table to track changes

    AddressLookupDefinition(Class classType, String tableName,
            String uniqueIdName, String foreignKeyName, String diffLogTableName)
    {
        this.tableName = tableName;
        this.uniqueIdName = uniqueIdName;
        this.foreignKeyName = foreignKeyName;
        this.classType = classType;
        this.diffLogTableName = diffLogTableName;
    }
}
