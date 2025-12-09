package DAOS.IDAOS;

/**
 * Used for the database structure check in the Database.DatabaseStructureCheck class.
 * @author Nick Engell
 */
public interface IStructureCheckable {

    /**
     * Executes a select statement on this table with all fields limited to 1 row.
     * Tests that the underlying table structure is correct.
     * @return A String containing an error message, or null if no error occurred.
     */
    public String structureCheck();

//    /**
//     * Get the database table name. 
//     * This method exists for better readability when running the structure check.
//     * @return The table name of this table.
//     */
//    public String getTable();
    
}
