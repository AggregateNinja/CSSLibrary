
package DAOS;

import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IStructureCheckable;
import DOS.MicroOrders;
import DOS.MicroResults;
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

public class MicroResultsDAO implements DAOInterface, IStructureCheckable {

    //--------------------------------------------------------------------------
    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`microResults`";

    private final ArrayList<String> fields = new ArrayList<>();
    ArrayList<Integer> generatedIDs = new ArrayList<>();

    //--------------------------------------------------------------------------
    
    public MicroResultsDAO()
    {
        fields.add("microOrderId");
        fields.add("microOrganismId");
        fields.add("microAntibioticsId");
        fields.add("microSusceptibilityId");
        fields.add("resultCount");
        fields.add("posted");
        fields.add("postedDate");
        fields.add("postedBy");
        fields.add("invalidated");
        fields.add("invalidatedDate");
        fields.add("invalidatedBy");        
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
            MicroResults mr = (MicroResults) obj;
            String stmt = GenerateInsertStatement(fields);
            PreparedStatement pStmt = con.prepareStatement(stmt);
            SetStatement(mr, pStmt);
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
            MicroResults mr = (MicroResults) obj;
            String stmt = GenerateUpdateStatement(fields)
                    + " WHERE `idMicroResults` = " + mr.getIdMicroResults();
            PreparedStatement pStmt = con.prepareStatement(stmt);

            SetStatement(mr, pStmt);

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
            MicroResults mr = (MicroResults) obj;
            Statement stmt = con.createStatement();
            String query = "DELETE FROM " + table + " "
                    + "WHERE `idMicroResults` = " + mr.getIdMicroResults();
            
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
            MicroResults mr = new MicroResults();
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + " WHERE `idMicroResults` = " + ID;

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                FromResultSet(mr, rs);
            }

            rs.close();
            stmt.close();

            return mr;
        }catch (Exception ex) {
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }
    
    public ArrayList<MicroResults> getByIdMicroOrders(Integer idMicroOrders) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        try{
            
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + " WHERE `microOrderId` = " + idMicroOrders;

            ResultSet rs = stmt.executeQuery(query);
            ArrayList<MicroResults> microResults = new ArrayList<>();
            while (rs.next())
            {                
                microResults.add(FromResultSet(new MicroResults(), rs));
                
            }

            rs.close();
            stmt.close();

            return microResults;
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
    private MicroResults FromResultSet(MicroResults obj, ResultSet rs) throws SQLException
    {
        obj.setIdMicroResults(rs.getInt("idMicroResults"));
        obj.setMicroOrderId(rs.getInt("microOrderId"));
        obj.setMicroOrganismsId(rs.getInt("microOrganismId"));
        obj.setMicroAntibioticsId(rs.getInt("microAntibioticsId"));
        obj.setMicroSusceptibilityId(rs.getInt("microSusceptibilityId"));
        obj.setResultCount(rs.getString("resultCount"));
        obj.setPosted(rs.getBoolean("posted"));
        obj.setPostedDate(rs.getTimestamp("postedDate"));
        obj.setPostedBy(rs.getInt("postedBy"));
        obj.setInvalidated(rs.getBoolean("invalidated"));
        obj.setInvalidatedDate(rs.getTimestamp("invalidatedDate"));
        obj.setInvalidatedBy(rs.getInt("invalidatedBy"));
        
        return obj;
    }

    //--------------------------------------------------------------------------
    private PreparedStatement SetStatement(MicroResults obj, PreparedStatement pStmt) throws SQLException {
        int i = 0;
        //SQLUtil.SafeSetInteger(pStmt, ++i, obj.getIdMicroResults());
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getMicroOrderId());
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getMicroOrganismsId());
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getMicroAntibioticsId());
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getMicroSusceptibilityId());
        SQLUtil.SafeSetString(pStmt, ++i, obj.getResultCount());
        SQLUtil.SafeSetBoolean(pStmt, ++i, obj.isPosted());
        SQLUtil.SafeSetTimeStamp(pStmt, ++i, obj.getPostedDate());
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getPostedBy());
        SQLUtil.SafeSetBoolean(pStmt, ++i, obj.isInvalidated());
        SQLUtil.SafeSetTimeStamp(pStmt, ++i, obj.getInvalidatedDate());
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getInvalidatedBy());
        
        return pStmt;
    }
    
    @Override
    public String structureCheck() {
        return DatabaseStructureCheck.structureCheck(fields, table, con);
    }
}
