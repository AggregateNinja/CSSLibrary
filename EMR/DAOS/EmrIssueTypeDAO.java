
package EMR.DAOS;

import DAOS.IDAOS.IStructureCheckable;
import EMR.DOS.EmrIssueType;
import Database.CheckDBConnection;
import Database.DatabaseSingleton;
import Database.DatabaseStructureCheck;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class EmrIssueTypeDAO implements IStructureCheckable
{
    // System enum that maps to the database text.
    // If we decide to change the wording on an issue type in the database, we just have
    // to change it in this enum instead of changing any/all the hard-coded strings
    public enum IssueTypeEnum
    {
        INSURANCE("Insurance"),
        PRESCRIPTION("Prescription"),
        DIAGNOSIS_CODE("Diagnosis Code"),
        DOCTOR("Doctor");
        
        private final String name;
        
        private IssueTypeEnum(String name)
        {
            this.name = name;
        }
        
        public String getDatabaseName()
        {
            return this.name;
        }
    }
    
    public static EmrIssueType getByEnum(IssueTypeEnum issueEnum)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (issueEnum == null) throw new IllegalArgumentException(
                "EmrIssueTypeDAO::getByEnum received a NULL IssueTypeEnum argument!");
        
        DatabaseSingleton dbs = DatabaseSingleton.getDatabaseSingleton();
        Connection conn = dbs.getConnection(true);
        if (conn.isValid(2) == false) CheckDBConnection.Check(dbs, conn);
        
        return getByName(issueEnum.getDatabaseName());
        
    }
    
    public static EmrIssueType getById(int id)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (id <= 0) throw new IllegalArgumentException(
                "EmrIssueTypeDAO::getById received an id argument of " + id);
        
        DatabaseSingleton dbs = DatabaseSingleton.getDatabaseSingleton();
        Connection conn = dbs.getConnection(true);
        if (conn.isValid(2) == false) CheckDBConnection.Check(dbs, conn);
        
        String sql = "SELECT * FROM `emrorders`.`issueTypes` WHERE idIssueTypes = ?";
        PreparedStatement pStmt = conn.prepareStatement(sql);
        pStmt.setInt(1, id);
        
        EmrIssueType issueType = null;
        ResultSet rs = pStmt.executeQuery();
        if (rs.next())
        {
            issueType = getFromResultSet(rs);
        }
        return issueType;
    }
    
    public static EmrIssueType getByName(String name)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (name == null || name.isEmpty()) throw new IllegalArgumentException(
                "EmrIssueTypeDAO::getById received a NULL or empty `name` argument");
        
        DatabaseSingleton dbs = DatabaseSingleton.getDatabaseSingleton();
        Connection conn = dbs.getConnection(true);
        if (conn.isValid(2) == false) CheckDBConnection.Check(dbs, conn);
        
        String sql = "SELECT * FROM `emrorders`.`issueTypes` WHERE `name` = ?";
        PreparedStatement pStmt = conn.prepareStatement(sql);
        pStmt.setString(1, name);
        
        EmrIssueType issueType = null;
        ResultSet rs = pStmt.executeQuery();
        if (rs.next())
        {
            issueType = getFromResultSet(rs);
        }
        return issueType;
    }
    
    public static List<EmrIssueType> getAll()
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        DatabaseSingleton dbs = DatabaseSingleton.getDatabaseSingleton();
        Connection conn = dbs.getConnection(true);
        if (conn.isValid(2) == false) CheckDBConnection.Check(dbs, conn);
        
        String sql = "SELECT * FROM `emrorders`.`issueTypes`";
        PreparedStatement pStmt = conn.prepareStatement(sql);
        List<EmrIssueType> issueTypes = null;
        ResultSet rs = pStmt.executeQuery();
        while (rs.next())
        {
            if (issueTypes == null) issueTypes = new LinkedList<>();
            issueTypes.add(getFromResultSet(rs));
        }
        return issueTypes;
    }
    
    
    private static EmrIssueType getFromResultSet(ResultSet rs)
            throws SQLException
    {
        EmrIssueType issueType = new EmrIssueType();
        issueType.setIdIssueTypes(rs.getInt("idIssueTypes"));
        issueType.setName(rs.getString("name"));
        return issueType;
    }
    
    @Override
    public String structureCheck() {
        String query = "SELECT `issueTypes`.`idIssueTypes`,\n"
                + "    `issueTypes`.`name`\n"
                + "FROM `emrorders`.`issueTypes` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, "`emrorders`.`issueTypes`", Database.DatabaseSingleton.getDatabaseSingleton().getConnection(true));
    }

}
