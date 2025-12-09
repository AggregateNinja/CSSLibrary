
package DAOS;

import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IStructureCheckable;
import DOS.MicroOrganisms;
import DOS.MicroSusceptibility;
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

public class MicroSusceptibilityDAO implements DAOInterface, IStructureCheckable {

    //--------------------------------------------------------------------------
    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`microSusceptibility`";

    private final ArrayList<String> fields = new ArrayList<>();
    ArrayList<Integer> generatedIDs = new ArrayList<>();

    //--------------------------------------------------------------------------
    
    public MicroSusceptibilityDAO(){
        //fields.add("id");
        fields.add("name");
        fields.add("abbr");
        fields.add("externalId");
        fields.add("instrumentId");

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
            MicroSusceptibility ms = (MicroSusceptibility) obj;
            String stmt = GenerateInsertStatement(fields);
            PreparedStatement pStmt = con.prepareStatement(stmt);
            SetStatement(ms, pStmt);
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
            MicroSusceptibility ms = (MicroSusceptibility) obj;
            String stmt = GenerateUpdateStatement(fields)
                    + " WHERE `id` = " + ms.getId();
            PreparedStatement pStmt = con.prepareStatement(stmt);

            SetStatement(ms, pStmt);

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
            MicroSusceptibility ms = (MicroSusceptibility) obj;
            Statement stmt = con.createStatement();
            String query = "DELETE FROM " + table + " "
                    + "WHERE `id` = " + ms.getId();
            
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
            MicroSusceptibility ms = new MicroSusceptibility();
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + " WHERE `id` = " + ID;

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                FromResultSet(ms, rs);
            }

            rs.close();
            stmt.close();

            return ms;
        }catch (SQLException ex) {
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }
    
    public ArrayList<MicroSusceptibility> GetByInstrumentId(int instrumentId)
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
            
            Statement stmt = con.createStatement();
            String query = "SELECT * FROM " + table + " WHERE instrumentId = " + instrumentId;
            ResultSet rs = stmt.executeQuery(query);
            ArrayList<MicroSusceptibility> susceptibilities = new ArrayList<>();
            MicroSusceptibility ms;
            while (rs.next())
            {
                ms = new MicroSusceptibility();
                susceptibilities.add(FromResultSet(ms, rs));
            }

            rs.close();
            stmt.close();

            return susceptibilities;
        }
        catch (SQLException ex)
        {
            String message = ex.toString();
            System.out.println(message);
            return null;
        }        
    }
    
    public ArrayList<MicroSusceptibility> GetAll()
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
            
            Statement stmt = con.createStatement();
            String query = "SELECT * FROM " + table;
            ResultSet rs = stmt.executeQuery(query);
            ArrayList<MicroSusceptibility> susceptibilities = new ArrayList<>();
            MicroSusceptibility ms;
            while (rs.next()) {
                 ms = new MicroSusceptibility();
                susceptibilities.add(FromResultSet(ms, rs));
            }

            rs.close();
            stmt.close();

            return susceptibilities;
        }catch (SQLException ex) {
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }
    
    public Serializable getByName(String name) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }
        try{
            MicroSusceptibility ms = new MicroSusceptibility();
            //Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + " WHERE `name` = ?";

            PreparedStatement pStmt = con.prepareStatement(query);
            pStmt.setString(1, name);
            ResultSet rs = pStmt.executeQuery();
            
            //ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                FromResultSet(ms, rs);
            }

            rs.close();
            //stmt.close();
            pStmt.close();
            
            return ms;
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
    private MicroSusceptibility FromResultSet(MicroSusceptibility obj, ResultSet rs) throws SQLException
    {
        obj.setId(rs.getInt("id"));
        obj.setName(rs.getString("name"));
        obj.setAbbr(rs.getString("abbr"));
        obj.setExternalId(rs.getString("externalId"));
        obj.setInstrumentId(rs.getInt("instrumentId"));
        
        return obj;
    }

    //--------------------------------------------------------------------------
    private PreparedStatement SetStatement(MicroSusceptibility obj, PreparedStatement pStmt) throws SQLException {
        int i =0;
        SQLUtil.SafeSetInteger(pStmt,++i, obj.getId());
        SQLUtil.SafeSetString(pStmt, ++i, obj.getName());
        SQLUtil.SafeSetString(pStmt, ++i, obj.getAbbr());
        SQLUtil.SafeSetString(pStmt, ++i, obj.getExternalId());
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getInstrumentId());
        
        return pStmt;
    }
    
    @Override
    public String structureCheck() {
        return DatabaseStructureCheck.structureCheck(fields, table, con);
    }
}
