
package DAOS;

import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IStructureCheckable;
import DOS.RefFile;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.SQLUtil;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author TomR
 */
public class RefFileDAO implements DAOInterface, IStructureCheckable
{
    //--------------------------------------------------------------------------
    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`refFiles`";
    private final ArrayList<String> fields = new ArrayList<>();
    ArrayList<Integer> generatedIDs = new ArrayList<>();
    //--------------------------------------------------------------------------
    public RefFileDAO()
    {
        fields.add("departmentId");
        fields.add("fileName");
    }

    @Override
    public Boolean Insert(Serializable obj)
    {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }
        
        try
        {
            RefFile rf = (RefFile) obj;
            String stmt = GenerateInsertStatement(fields);
            PreparedStatement pStmt = con.prepareStatement(stmt);
            SetStatement(rf, pStmt);
            pStmt.executeUpdate();
            pStmt.close();
            return true;
        }
        catch (SQLException ex)
        {
            String message = ex.toString();
            System.out.println(message);
            return false;
        }         
    }

    @Override
    public Boolean Update(Serializable obj)
    {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }
        try {
            RefFile rf = (RefFile) obj;
            String stmt = GenerateUpdateStatement(fields)
                    + " WHERE `idRefFiles` = " + rf.getIdRefFiles();
            PreparedStatement pStmt = con.prepareStatement(stmt);
            SetStatement(rf, pStmt);
            pStmt.executeUpdate();
            pStmt.close();

            return true;
        } catch (SQLException ex) {
            String message = ex.toString();
            System.out.println(message);
            return false;
        }        
    }

    @Override
    public Boolean Delete(Serializable obj)
    {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }
        try{
            RefFile rf = (RefFile) obj;
            Statement stmt = con.createStatement();
            String query = "DELETE FROM " + table + " "
                    + "WHERE `idRefFiles` = " + rf.getIdRefFiles();
            
            int result = stmt.executeUpdate(query);
            
            stmt.close();
            
            return true;
        }catch (SQLException ex) {
            String message = ex.toString();
            System.out.println(message);
            return false;
        }        
    }

    @Override
    public Serializable getByID(Integer ID)
    {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }
        try{
            RefFile rf = new RefFile();
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + " WHERE `idRefFiles` = " + ID;

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                FromResultSet(rf, rs);
            }

            rs.close();
            stmt.close();

            return rf;
        }catch (SQLException ex) {
            String message = ex.toString();
            System.out.println(message);
            return null;
        }         
    }
    
    //--------------------------------------------------------------------------
    private String GenerateInsertStatement(ArrayList<String> fields) {

        String stmt = "INSERT INTO " + table + "(";
        String values = ") values (";
        for (int i = 0; i < fields.size(); ++i) {
            stmt += " `" + fields.get(i) + "`";
            values += "?";
            if (i != fields.size() - 1) {
                stmt += ", ";
                values += ",";
            }
        }
        values += ")";
        return stmt + values;
    }
    
    //--------------------------------------------------------------------------
    private String GenerateUpdateStatement(ArrayList<String> fields) {

        String stmt = "UPDATE " + table + " SET";
        for (int i = 0; i < fields.size(); ++i) {
            stmt += " `" + fields.get(i) + "` = ?";
            if (i != fields.size() - 1) {
                stmt += ",";
            }
        }
        return stmt;
    }

    //--------------------------------------------------------------------------
    private RefFile FromResultSet(RefFile obj, ResultSet rs) throws SQLException
    {
        obj.setIdRefFiles(rs.getInt("idRefFiles"));
        obj.setDepartmentId(rs.getInt("departmentId"));        
        obj.setFileName(rs.getString("fileName"));
        
        return obj;
    }

    //--------------------------------------------------------------------------
    private PreparedStatement SetStatement(RefFile obj, PreparedStatement pStmt) throws SQLException {
        int i = 0;
        
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getIdRefFiles());
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getDepartmentId());
        SQLUtil.SafeSetString(pStmt, ++i, obj.getFileName());
        
        return pStmt;
    }
    
    @Override
    public String structureCheck() {
        return DatabaseStructureCheck.structureCheck(fields, table, con);
    }
    
}
