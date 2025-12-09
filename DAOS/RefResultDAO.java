package DAOS;

import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IStructureCheckable;
import DOS.RefResult;
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
public class RefResultDAO implements DAOInterface, IStructureCheckable
{
    //--------------------------------------------------------------------------
    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`refResults`";
    private final ArrayList<String> fields = new ArrayList<>();
    ArrayList<Integer> generatedIDs = new ArrayList<>();
    //--------------------------------------------------------------------------

    public RefResultDAO()
    {
        fields.add("resultId");
        fields.add("refTestId");
        fields.add("resultNo");
        fields.add("resultText");
        fields.add("resultRemark");                
        fields.add("resultChoice");
        fields.add("textComment");
        fields.add("refFlagId");
        fields.add("refFileId");
        fields.add("invalidated");
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
            RefResult rr = (RefResult) obj;
            String stmt = GenerateInsertStatement(fields);
            PreparedStatement pStmt = con.prepareStatement(stmt);
            SetStatement(rr, pStmt);
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
            RefResult rr = (RefResult) obj;
            String stmt = GenerateUpdateStatement(fields)
                    + " WHERE `idRefResults` = " + rr.getIdRefResults();
            PreparedStatement pStmt = con.prepareStatement(stmt);
            SetStatement(rr, pStmt);
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
            RefResult rr = (RefResult) obj;
            Statement stmt = con.createStatement();
            String query = "DELETE FROM " + table + " "
                    + "WHERE `idRefResults` = " + rr.getIdRefResults();
            
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
            RefResult rr = new RefResult();
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + " WHERE `idRefResults` = " + ID;

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                FromResultSet(rr, rs);
            }

            rs.close();
            stmt.close();

            return rr;
        }catch (SQLException ex) {
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }
    
    public ArrayList<RefResult> GetAllByResultId(Integer resultId)
    {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        try{
            ArrayList<RefResult> results = new ArrayList<>();
            
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + " WHERE `resultId` = " + resultId;

            ResultSet rs = stmt.executeQuery(query);

            while (rs.next())
            {
                RefResult rr = new RefResult();
                FromResultSet(rr, rs);
                results.add(rr);
            }

            rs.close();
            stmt.close();

            return results;
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
    private RefResult FromResultSet(RefResult obj, ResultSet rs) throws SQLException
    {
        obj.setIdRefResults(rs.getInt("idRefResults"));
        obj.setResultId(rs.getInt("resultId"));
        obj.setRefTestId(rs.getInt("refTestId"));
        obj.setResultNo(rs.getDouble("resultNo"));
        obj.setResultText(rs.getString("resultText"));
        obj.setResultRemark(rs.getInt("resultRemark"));
        obj.setResultChoice(rs.getInt("resultChoice"));
        obj.setTextComment(rs.getString("textComment"));
        obj.setRefFlagId(rs.getInt("refFlagId"));
        obj.setRefFileId(rs.getInt("refFileId"));
        obj.setInvalidated(rs.getBoolean("invalidated"));
        
        return obj;
    }

    //--------------------------------------------------------------------------
    private PreparedStatement SetStatement(RefResult obj, PreparedStatement pStmt) throws SQLException {
        int i = 0;
        
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getResultId());
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getRefTestId());
        SQLUtil.SafeSetDouble(pStmt, ++i, obj.getResultNo());
        SQLUtil.SafeSetString(pStmt, ++i, obj.getResultText());
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getResultRemark());
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getResultChoice());
        SQLUtil.SafeSetString(pStmt, ++i, obj.getTextComment());
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getRefFlagId());
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getRefFileId());
        SQLUtil.SafeSetBoolean(pStmt, ++i, obj.isInvalidated());
        
        return pStmt;
    }    
      
    @Override
    public String structureCheck() {
        return DatabaseStructureCheck.structureCheck(fields, table, con);
    }
    
}
