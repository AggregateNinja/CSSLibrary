
package DAOS;

import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IStructureCheckable;
import DOS.MicroSources;
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

public class MicroSourcesDAO implements DAOInterface, IStructureCheckable {

    //--------------------------------------------------------------------------
    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`microSources`";

    private final ArrayList<String> fields = new ArrayList<>();
    ArrayList<Integer> generatedIDs = new ArrayList<>();

    //--------------------------------------------------------------------------
    
    public MicroSourcesDAO(){
        fields.add("sourceName");
        fields.add("sourceAbbr");
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
            MicroSources ms = (MicroSources) obj;
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
            MicroSources ms = (MicroSources) obj;
            String stmt = GenerateUpdateStatement(fields)
                    + " WHERE `idmicroSources` = " + ms.getIdmicroSources();
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
            MicroSources ms = (MicroSources) obj;
            Statement stmt = con.createStatement();
            String query = "DELETE FROM " + table + " "
                    + "WHERE `idmicroSources` = " + ms.getIdmicroSources();
            
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
            MicroSources ms = new MicroSources();
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + " WHERE `idmicroSources` = " + ID;

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                FromResultSet(ms, rs);
            }

            rs.close();
            stmt.close();

            return ms;
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
            MicroSources ms = new MicroSources();
            PreparedStatement stmt = null; //con.createStatement();

            String query = "SELECT * FROM " + table
                    + " WHERE `sourceName` = ?";

            stmt = createStatement(con, query, name);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                FromResultSet(ms, rs);
            }

            rs.close();
            stmt.close();

            return ms;
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
            MicroSources ms = new MicroSources();
            PreparedStatement stmt = null; //con.createStatement();

            String query = "SELECT * FROM " + table
                    + " WHERE `sourceAbbr` = ?";

            stmt = createStatement(con, query, abbr);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                FromResultSet(ms, rs);
            }

            rs.close();
            stmt.close();

            return ms;
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
            MicroSources ms = new MicroSources();
            PreparedStatement stmt = null; //con.createStatement();

            String query = "SELECT * FROM " + table
                    + " WHERE `externalId` = ?";

            stmt = createStatement(con, query, ID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                FromResultSet(ms, rs);
            }

            rs.close();
            stmt.close();

            return ms;
        }catch (Exception ex) {
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }
    
    public ArrayList<MicroSources> GetAllActive(){
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        try{
            ArrayList<MicroSources> list = new ArrayList<>();
            MicroSources ms = null;
            Statement stmt = con.createStatement();
            String query = "SELECT * FROM " + table + " "
                    + "WHERE `active` = " + true;
            
            ResultSet rs = stmt.executeQuery(query);
            
            while(rs.next()){
                ms = new MicroSources();
                ms = FromResultSet(ms, rs);
                list.add(ms);
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
    
    public List<MicroSources> SearchSourceByName(String NameFragment, boolean CaseSensitive, boolean ActiveOnly) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        List<MicroSources> list = new ArrayList<>();
        try {
            PreparedStatement stmt = null;
            ResultSet rs = null;

            //stmt = con.createStatement();

            String query = "SELECT * FROM " + table + " ";
            if (CaseSensitive == false) {
                query += "WHERE (LOWER(`sourceName`) LIKE LOWER(?)) ";
            } else {
                query += "WHERE (`sourceName` LIKE ?) ";
            }
            
            if(ActiveOnly)
            {
                query += "AND `active` = true ";
            }

            String nameParam = SQLUtil.createSearchParam(NameFragment);
            stmt = createStatement(con, query, nameParam);
            rs = stmt.executeQuery();

            while (rs.next()) {
                MicroSources ms = new MicroSources();
                list.add(FromResultSet(ms, rs));
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
    
    public List<MicroSources> SearchSourceByAbbr(String NameFragment, boolean CaseSensitive, boolean ActiveOnly) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        List<MicroSources> list = new ArrayList<>();
        try {
            PreparedStatement stmt = null;
            ResultSet rs = null;

            //stmt = con.createStatement();

            String query = "SELECT * FROM " + table + " ";
            if (CaseSensitive == false) {
                query += "WHERE (LOWER(`sourceAbbr`) LIKE LOWER(?)) ";
            } else {
                query += "WHERE (`sourceAbbr` LIKE ?) ";
            }
            
            if(ActiveOnly)
            {
                query += "AND `active` = true ";
            }

            String nameParam = SQLUtil.createSearchParam(NameFragment);
            stmt = createStatement(con, query, nameParam);
            rs = stmt.executeQuery();

            while (rs.next()) {
                MicroSources ms = new MicroSources();
                list.add(FromResultSet(ms, rs));
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
    private MicroSources FromResultSet(MicroSources obj, ResultSet rs) throws SQLException {
        obj.setIdmicroSources(rs.getInt("idmicroSources"));
        obj.setSourceName(rs.getString("sourceName"));
        obj.setSourceAbbr(rs.getString("sourceAbbr"));
        obj.setExternalId(rs.getString("externalId"));
        obj.setActive(rs.getBoolean("active"));

        return obj;
    }

    //--------------------------------------------------------------------------
    private PreparedStatement SetStatement(MicroSources obj, PreparedStatement pStmt) throws SQLException {
        SQLUtil.SafeSetString(pStmt, 1, obj.getSourceName());
        SQLUtil.SafeSetString(pStmt, 2, obj.getSourceAbbr());
        SQLUtil.SafeSetString(pStmt, 3, obj.getExternalId());
        SQLUtil.SafeSetBoolean(pStmt, 4, obj.getActive());
        
        return pStmt;
    }
    
    @Override
    public String structureCheck() {
        return DatabaseStructureCheck.structureCheck(fields, table, con);
    }
}
