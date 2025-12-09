
package DAOS;

import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IStructureCheckable;
import DOS.MicroAntibiotics;
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

import static Utility.SQLUtil.createStatement;

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

public class MicroAntibioticsDAO implements DAOInterface, IStructureCheckable {

    //--------------------------------------------------------------------------
    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`microAntibiotics`";

    private final ArrayList<String> fields = new ArrayList<>();
    ArrayList<Integer> generatedIDs = new ArrayList<>();

    //--------------------------------------------------------------------------
    
    public MicroAntibioticsDAO(){
        fields.add("antibioticName");
        fields.add("antibioticAbbr");
        fields.add("externalId");
        fields.add("active");
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
            MicroAntibiotics ma = (MicroAntibiotics) obj;
            String stmt = GenerateInsertStatement(fields);
            PreparedStatement pStmt = con.prepareStatement(stmt);
            SetStatement(ma, pStmt);
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
            MicroAntibiotics ma = (MicroAntibiotics) obj;
            String stmt = GenerateUpdateStatement(fields)
                    + " WHERE `idmicroAntibiotics` = " + ma.getIdmicroAntibiotics();
            PreparedStatement pStmt = con.prepareStatement(stmt);

            SetStatement(ma, pStmt);

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
            MicroAntibiotics ma = (MicroAntibiotics) obj;
            Statement stmt = con.createStatement();
            String query = "DELETE FROM " + table + " "
                    + "WHERE `idmicroAntibiotics` = " + ma.getIdmicroAntibiotics();
            
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
            MicroAntibiotics ma = new MicroAntibiotics();
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + " WHERE `idmicroAntibiotics` = " + ID;

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                FromResultSet(ma, rs);
            }

            rs.close();
            stmt.close();

            return ma;
        }catch (Exception ex) {
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
            MicroAntibiotics ma = new MicroAntibiotics();

            String query = "SELECT * FROM " + table
                    + " WHERE `antibioticName` = ?";

            PreparedStatement pStmt = con.prepareStatement(query);
            SQLUtil.SafeSetString(pStmt, 1, name);
            ResultSet rs = pStmt.executeQuery();

            if (rs.next()) {
                FromResultSet(ma, rs);
            }

            rs.close();
            pStmt.close();

            return ma;
        }catch (SQLException ex) {
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }
    
    public Serializable getByAbbr(String abbr) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }
        try{
            MicroAntibiotics ma = new MicroAntibiotics();
            PreparedStatement stmt = null; //con.createStatement();

            String query = "SELECT * FROM " + table
                    + " WHERE `antibioticAbbr` = ?";

            stmt = createStatement(con, query, abbr);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                FromResultSet(ma, rs);
            }

            rs.close();
            stmt.close();

            return ma;
        }catch (Exception ex) {
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }
    
    public Serializable getByExternalId(String ID) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }
        try{
            MicroAntibiotics ma = new MicroAntibiotics();
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + " WHERE `externalId` = " + ID;

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                FromResultSet(ma, rs);
            }

            rs.close();
            stmt.close();

            return ma;
        }catch (Exception ex) {
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }
    
    public ArrayList<MicroAntibiotics> GetAllActive(){
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        try{
            ArrayList<MicroAntibiotics> list = new ArrayList<>();
            MicroAntibiotics ma = null;
            Statement stmt = con.createStatement();
            String query = "SELECT * FROM " + table + " "
                    + "WHERE `active` = " + true;
            
            ResultSet rs = stmt.executeQuery(query);
            
            while(rs.next()){
                ma = new MicroAntibiotics();
                ma = FromResultSet(ma, rs);
                list.add(ma);
            }
            
            rs.close();
            stmt.close();
            
            return list;
        }catch(Exception ex){
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }
    
    public List<MicroAntibiotics> SearchAntibioticByName(String NameFragment, boolean CaseSensitive, boolean ActiveOnly) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        List<MicroAntibiotics> list = new ArrayList<>();
        try {
            PreparedStatement stmt = null;
            ResultSet rs = null;

            //stmt = con.createStatement();

            String query = "SELECT * FROM " + table + " ";
            if (CaseSensitive == false) {
                query += "WHERE (LOWER(`antibioticName`) LIKE LOWER(?)) ";
            } else {
                query += "WHERE (`antibioticName` LIKE ?) ";
            }
            
            if(ActiveOnly)
            {
                query += "AND `active` = true ";
            }

            String nameParam = SQLUtil.createSearchParam(NameFragment);
            stmt = createStatement(con, query, nameParam);
            rs = stmt.executeQuery();

            while (rs.next()) {
                MicroAntibiotics ma = new MicroAntibiotics();
                list.add(FromResultSet(ma, rs));
            }

            rs.close();
            stmt.close();
            return list;
        } catch (Exception ex) {
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }
    
    public MicroAntibiotics InsertGetObject(String antibioticName, String antibioticAbbr, String externalId)
    {
        // Antibiotic name and abbr are required fields
        if (antibioticName == null || antibioticName.isEmpty() ||
                antibioticAbbr == null || antibioticAbbr.isEmpty()) return null;
        if (externalId == null) externalId = "";
        try {
            if (con.isClosed())
            {
                con = CheckDBConnection.Check(dbs, con);
            }
        }
        catch (SQLException ex)
        {
            System.out.println(ex.toString());
            return null;
        }
        
        // If this organism name already exists in our database, return that.
        try
        {
            MicroAntibiotics existingAntibiotic = (MicroAntibiotics)getByName(antibioticName);
            
            if (existingAntibiotic != null && existingAntibiotic.getIdmicroAntibiotics()!= null &&
                    existingAntibiotic.getIdmicroAntibiotics() > 0)
            {
                System.out.println("Got existing antibiotic!");
                return existingAntibiotic;
            }
        }
        catch (Exception ex)
        {
            System.out.println("Could not locate antibiotic " + antibioticName + " in the database. Inserting new record");
        }
        
        // We could not locate this organism. Insert and return a new DO
        try
        {
            MicroAntibiotics newAntibiotic = new MicroAntibiotics();
            newAntibiotic.setActive(true);
            newAntibiotic.setAntibioticAbbr(antibioticAbbr);
            newAntibiotic.setAntibioticName(antibioticName);
            newAntibiotic.setExternalId(externalId);
            
            String sql = GenerateInsertStatement(fields);
            PreparedStatement pStmt = con.prepareStatement(sql,
                                      Statement.RETURN_GENERATED_KEYS);      
            pStmt = SetStatement(newAntibiotic, pStmt);            
            int affectedRows = pStmt.executeUpdate();            
            
            if (affectedRows == 0)
            {
                throw new Exception();
            }

            Integer newId = null;
            try (ResultSet generatedKeys = pStmt.getGeneratedKeys()) 
            {
                if (generatedKeys.next())
                {
                    newId = generatedKeys.getInt(1);
                    newAntibiotic.setIdmicroAntibiotics(newId);
                }
                else
                {
                    throw new SQLException("Insert failed, no ID obtained.");
                }
            }
            
            pStmt.close();
            return newAntibiotic;
        }
        catch (Exception ex)
        {
            String message = ex.toString();
            System.out.println(message);
            return null;
        }        
    }
        
    
    public List<MicroAntibiotics> SearchAntibioticByAbbr(String NameFragment, boolean CaseSensitive, boolean ActiveOnly) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        List<MicroAntibiotics> list = new ArrayList<>();
        try {
            PreparedStatement stmt = null;
            ResultSet rs = null;

            //stmt = con.createStatement();

            String query = "SELECT * FROM " + table + " ";
            if (CaseSensitive == false) {
                query += "WHERE (LOWER(`antibioticAbbr`) LIKE LOWER(?)) ";
            } else {
                query += "WHERE (`antibioticAbbr` LIKE ?) ";
            }
            
            if(ActiveOnly)
            {
                query += "AND `active` = true ";
            }

            String nameParam = SQLUtil.createSearchParam(NameFragment);
            stmt = createStatement(con, query, nameParam);
            rs = stmt.executeQuery();

            while (rs.next()) {
                MicroAntibiotics ma = new MicroAntibiotics();
                list.add(FromResultSet(ma, rs));
            }

            rs.close();
            stmt.close();
            return list;
        } catch (Exception ex) {
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
    private MicroAntibiotics FromResultSet(MicroAntibiotics obj, ResultSet rs) throws SQLException {
        obj.setIdmicroAntibiotics(rs.getInt("idmicroAntibiotics"));
        obj.setAntibioticName(rs.getString("antibioticName"));
        obj.setAntibioticAbbr(rs.getString("antibioticAbbr"));
        obj.setExternalId(rs.getString("externalId"));
        obj.setActive(rs.getBoolean("active"));

        return obj;
    }

    //--------------------------------------------------------------------------
    private PreparedStatement SetStatement(MicroAntibiotics obj, PreparedStatement pStmt) throws SQLException {
        SQLUtil.SafeSetString(pStmt, 1, obj.getAntibioticName());
        SQLUtil.SafeSetString(pStmt, 2, obj.getAntibioticAbbr());
        SQLUtil.SafeSetString(pStmt, 3, obj.getExternalId());
        SQLUtil.SafeSetBoolean(pStmt, 4, obj.getActive());
        
        return pStmt;
    }
    
    @Override
    public String structureCheck() {
        return DatabaseStructureCheck.structureCheck(fields, table, con);
    }
}
