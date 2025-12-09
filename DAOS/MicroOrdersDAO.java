
package DAOS;

import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IStructureCheckable;
import DOS.MicroOrders;
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
import java.util.List;

/**
 * @date:   May 11, 2015  5:20:29 PM
 * @author: Ryan Piper ryan@csslis.com
 * 
 * @project: CSSLibrary
 * @file name: ClientCopyDAO.java  (UTF-8)
 *
 * @Description: 
 *
 */

public class MicroOrdersDAO implements DAOInterface, IStructureCheckable {

    //--------------------------------------------------------------------------
    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`microOrders`";

    private final ArrayList<String> fields = new ArrayList<>();
    ArrayList<Integer> generatedIDs = new ArrayList<>();

    //--------------------------------------------------------------------------
    
    public MicroOrdersDAO(){
        fields.add("idResults");
        fields.add("idmicroSites");
        fields.add("idmicroSources");
        fields.add("comment");
        fields.add("complete");
        fields.add("completedBy");
        fields.add("completedOn");
        fields.add("colonyCount");
        fields.add("finalReport");
        fields.add("prelimReport");
        fields.add("gramStain");
    }
    
    @Override
    public Boolean Insert(Serializable obj) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }

        try {
            MicroOrders mo = (MicroOrders) obj;
            String stmt = GenerateInsertStatement(fields);
            PreparedStatement pStmt = con.prepareStatement(stmt);
            SetStatement(mo, pStmt);
            pStmt.executeUpdate();

            pStmt.close();

            return true;
        } catch (Exception ex) {
            String message = ex.toString();
            System.out.println(message);
            return false;
        }
    }

    @Override
    public Boolean Update(Serializable obj) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }
        try {
            MicroOrders mo = (MicroOrders) obj;
            String stmt = GenerateUpdateStatement(fields)
                    + " WHERE `idmicroOrders` = " + mo.getIdmicroOrders();
            PreparedStatement pStmt = con.prepareStatement(stmt);

            SetStatement(mo, pStmt);

            pStmt.executeUpdate();

            pStmt.close();

            return true;
        } catch (Exception ex) {
            String message = ex.toString();
            System.out.println(message);
            return false;
        }
    }

    @Override
    public Boolean Delete(Serializable obj) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }
        try{
            MicroOrders mo = (MicroOrders) obj;
            Statement stmt = con.createStatement();
            String query = "DELETE FROM " + table + " "
                    + "WHERE `idmicroOrders` = " + mo.getIdmicroOrders();
            
            int result = stmt.executeUpdate(query);
            
            stmt.close();
            
            return true;
        }catch (Exception ex) {
            String message = ex.toString();
            System.out.println(message);
            return false;
        }
    }

    @Override
    public Serializable getByID(Integer ID) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }
        try{
            MicroOrders mo = new MicroOrders();
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + " WHERE `idmicroOrders` = " + ID;

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                FromResultSet(mo, rs);
            }

            rs.close();
            stmt.close();

            return mo;
        }catch (Exception ex) {
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }
    
    public Serializable getByIdResults(Integer idResults) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }
        try{
            MicroOrders mo = new MicroOrders();
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + " WHERE `idResults` = " + idResults;

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                FromResultSet(mo, rs);
            }

            rs.close();
            stmt.close();

            return mo;
        }catch (Exception ex) {
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
    private MicroOrders FromResultSet(MicroOrders obj, ResultSet rs) throws SQLException {
        obj.setIdmicroOrders(rs.getInt("idmicroorders"));
        obj.setIdResults(rs.getInt("idResults"));
        obj.setIdmicroSites(rs.getInt("idmicroSites"));
        obj.setIdmicroSources(rs.getInt("idmicroSources"));
        obj.setComment(rs.getString("comment"));
        obj.setComplete(rs.getBoolean("complete"));
        obj.setCompletedBy(rs.getInt("completedBy"));
        obj.setCompletedOn(rs.getTimestamp("completedOn"));
        obj.setColonyCount(rs.getString("colonyCount"));
        obj.setFinalReport(rs.getString("finalReport"));
        obj.setPrelimReport(rs.getString("prelimReport"));
        obj.setGramStain(rs.getString("gramStain"));
        return obj;
    }

    //--------------------------------------------------------------------------
    private PreparedStatement SetStatement(MicroOrders obj, PreparedStatement pStmt) throws SQLException {
        SQLUtil.SafeSetInteger(pStmt, 1, obj.getIdResults());
        SQLUtil.SafeSetInteger(pStmt, 2, obj.getIdmicroSites());
        SQLUtil.SafeSetInteger(pStmt, 3, obj.getIdmicroSources());
        SQLUtil.SafeSetString(pStmt, 4, obj.getComment());
        SQLUtil.SafeSetBoolean(pStmt, 5, obj.getComplete());
        SQLUtil.SafeSetInteger(pStmt, 6, obj.getCompletedBy());
        SQLUtil.SafeSetTimeStamp(pStmt, 7, obj.getCompletedOn());
        SQLUtil.SafeSetString(pStmt, 8, obj.getColonyCount());
        SQLUtil.SafeSetString(pStmt, 9, obj.getFinalReport());
        SQLUtil.SafeSetString(pStmt, 10, obj.getPrelimReport());
        SQLUtil.SafeSetString(pStmt, 11, obj.getGramStain());
        
        return pStmt;
    }
    
    @Override
    public String structureCheck() {
        return DatabaseStructureCheck.structureCheck(fields, table, con);
    }
}
