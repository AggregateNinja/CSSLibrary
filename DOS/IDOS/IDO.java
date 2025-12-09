
package DOS.IDOS;

public interface IDO
{
    // Defines which schema the implementing DO will affect when processed by
    // DAOs / BLs that take this into consideration
    public enum DatabaseSchema
    {
        CSS(Database.DatabaseSingleton.getDatabaseSingleton().getProperties().getCssSchema()),
        EMRORDERS("emrorders"),
        CSSWEB(Database.DatabaseSingleton.getDatabaseSingleton().getProperties().getWebSchema());
        
        private final String name;
        
        DatabaseSchema(String dbName)
        {
            name = dbName;
        }
        
        @Override
        public String toString()
        {
            return this.name;
        }
        
        public String getName()
        {
            return this.name;
        }
    }
    
    public DatabaseSchema getDatabaseSchema();
    public void setDatabaseSchema(DatabaseSchema schema);
}
