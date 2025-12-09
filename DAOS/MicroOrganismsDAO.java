
package DAOS;

import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IStructureCheckable;
import DOS.MicroOrganisms;
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

public class MicroOrganismsDAO implements DAOInterface, IStructureCheckable {

    //--------------------------------------------------------------------------
    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`microOrganisms`";

    private final ArrayList<String> fields = new ArrayList<>();
    ArrayList<Integer> generatedIDs = new ArrayList<>();

    //--------------------------------------------------------------------------
    
    public MicroOrganismsDAO(){
        fields.add("organismName");
        fields.add("organismAbbr");
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
            MicroOrganisms mo = (MicroOrganisms) obj;
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
    
    /**
     * Inserts a new micro organism row and returns the new DO object,
     *  or returns the existing DO object it already exists (uses name as key)
     * @param organismName
     * @param abbreviation
     * @param externalId
     * @return 
     */
    public MicroOrganisms InsertGetObject(String organismName, String abbreviation, String externalId)
    {
        // Name is required field
        if (organismName == null || organismName.isEmpty()) return null;
        if (externalId == null) externalId = "";
        if (abbreviation == null) abbreviation = "";
        
        try
        {
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
            MicroOrganisms existingOrganism = (MicroOrganisms)getByName(organismName);
            
            if (existingOrganism != null && existingOrganism.getIdmicroOrganisms() != null &&
                    existingOrganism.getIdmicroOrganisms() > 0)
            {
                System.out.println("Got existing organism!");
                return existingOrganism;
            }
        }
        catch (Exception ex)
        {
            System.out.println("Could not locate organism " + organismName + " in the database. Inserting new record");
        }
        
        // We could not locate this organism. Insert and return a new DO
        try
        {
            MicroOrganisms newOrganism = new MicroOrganisms();
            newOrganism.setActive(true);
            newOrganism.setOrganismAbbr(abbreviation);
            newOrganism.setOrganismName(organismName);
            newOrganism.setExternalId(externalId);
            
            String sql = GenerateInsertStatement(fields);
            PreparedStatement pStmt = con.prepareStatement(sql,
                                      Statement.RETURN_GENERATED_KEYS);      
            pStmt = SetStatement(newOrganism, pStmt);            
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
                    newOrganism.setIdmicroOrganisms(newId);
                }
                else
                {
                    throw new SQLException("Insert failed, no ID obtained.");
                }
            }            
            
            pStmt.close();
            return newOrganism;
            
        }
        catch (Exception ex)
        {
            String message = ex.toString();
            System.out.println(message);
            return null;
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
            MicroOrganisms mo = (MicroOrganisms) obj;
            String stmt = GenerateUpdateStatement(fields)
                    + " WHERE `idmicroOrganisms` = " + mo.getIdmicroOrganisms();
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
            MicroOrganisms mo = (MicroOrganisms) obj;
            Statement stmt = con.createStatement();
            String query = "DELETE FROM " + table + " "
                    + "WHERE `idmicroOrganisms` = " + mo.getIdmicroOrganisms();
            
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
            MicroOrganisms mo = new MicroOrganisms();
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + " WHERE `idmicroOrganisms` = " + ID;

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
            MicroOrganisms mo = new MicroOrganisms();
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + " WHERE `organismName` = ?";

            PreparedStatement pStmt = con.prepareStatement(query);
            pStmt.setString(1, name);
            ResultSet rs = pStmt.executeQuery();
            
            //ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                FromResultSet(mo, rs);
            }

            rs.close();
            //stmt.close();
            pStmt.close();

            return mo;
        }catch (Exception ex) {
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
            MicroOrganisms mo = new MicroOrganisms();
            PreparedStatement stmt = null; //con.createStatement();

            String query = "SELECT * FROM " + table
                    + " WHERE `organismAbbr` = ?";

            stmt = createStatement(con, query, abbr);
            ResultSet rs = stmt.executeQuery();

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
            MicroOrganisms mo = new MicroOrganisms();
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + " WHERE `externalId` = " + ID;

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
    
    public ArrayList<MicroOrganisms> GetAllActive(){
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        try{
            ArrayList<MicroOrganisms> list = new ArrayList<>();
            MicroOrganisms mo = null;
            Statement stmt = con.createStatement();
            String query = "SELECT * FROM " + table + " "
                    + "WHERE `active` = " + true;
            
            ResultSet rs = stmt.executeQuery(query);
            
            while(rs.next()){
                mo = new MicroOrganisms();
                mo = FromResultSet(mo, rs);
                list.add(mo);
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
    
    public List<MicroOrganisms> SearchOrganismByName(String NameFragment, boolean CaseSensitive, boolean ActiveOnly) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        List<MicroOrganisms> list = new ArrayList<>();
        try {
            PreparedStatement stmt = null;
            ResultSet rs = null;

            //stmt = con.createStatement();

            String query = "SELECT * FROM " + table + " ";
            if (CaseSensitive == false) {
                query += "WHERE (LOWER(`organismName`) LIKE LOWER(?)) ";
            } else {
                query += "WHERE (`organismName` LIKE ?) ";
            }
            
            if(ActiveOnly)
            {
                query += "AND `active` = true ";
            }

            String nameParam = SQLUtil.createSearchParam(NameFragment);
            stmt = createStatement(con, query, nameParam);
            rs = stmt.executeQuery();

            while (rs.next()) {
                MicroOrganisms mo = new MicroOrganisms();
                list.add(FromResultSet(mo, rs));
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
    
    public List<MicroOrganisms> SearchOrganismByAbbr(String NameFragment, boolean CaseSensitive, boolean ActiveOnly) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        List<MicroOrganisms> list = new ArrayList<>();
        try {
            PreparedStatement stmt = null;
            ResultSet rs = null;

            //stmt = con.createStatement();

            String query = "SELECT * FROM " + table + " ";
            if (CaseSensitive == false) {
                query += "WHERE (LOWER(`organismAbbr`) LIKE LOWER(?)) ";
            } else {
                query += "WHERE (`organismAbbr` LIKE ?) ";
            }
            
            if(ActiveOnly)
            {
                query += "AND `active` = true ";
            }

            String nameParam = SQLUtil.createSearchParam(NameFragment);
            stmt = createStatement(con, query, nameParam);
            rs = stmt.executeQuery();

            while (rs.next()) {
                MicroOrganisms mo = new MicroOrganisms();
                list.add(FromResultSet(mo, rs));
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
    private MicroOrganisms FromResultSet(MicroOrganisms obj, ResultSet rs) throws SQLException {
        obj.setIdmicroOrganisms(rs.getInt("idmicroOrganisms"));
        obj.setOrganismName(rs.getString("organismName"));
        obj.setOrganismAbbr(rs.getString("organismAbbr"));
        obj.setExternalId(rs.getString("externalId"));
        obj.setActive(rs.getBoolean("active"));

        return obj;
    }

    //--------------------------------------------------------------------------
    private PreparedStatement SetStatement(MicroOrganisms obj, PreparedStatement pStmt) throws SQLException {
        SQLUtil.SafeSetString(pStmt, 1, obj.getOrganismName());
        SQLUtil.SafeSetString(pStmt, 2, obj.getOrganismAbbr());
        SQLUtil.SafeSetString(pStmt, 3, obj.getExternalId());
        SQLUtil.SafeSetBoolean(pStmt, 4, obj.getActive());
        
        return pStmt;
    }
    
    @Override
    public String structureCheck() {
        return DatabaseStructureCheck.structureCheck(fields, table, con);
    }
}
