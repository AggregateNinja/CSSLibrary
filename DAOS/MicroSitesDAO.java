
package DAOS;

import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IStructureCheckable;
import DOS.MicroSites;
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

public class MicroSitesDAO implements DAOInterface, IStructureCheckable {

    //--------------------------------------------------------------------------
    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`microSites`";

    private final ArrayList<String> fields = new ArrayList<>();
    ArrayList<Integer> generatedIDs = new ArrayList<>();

    //--------------------------------------------------------------------------
    
    public MicroSitesDAO()
    {
        fields.add("siteName");
        fields.add("siteAbbr");
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
            MicroSites ms = (MicroSites) obj;
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
            MicroSites ms = (MicroSites) obj;
            String stmt = GenerateUpdateStatement(fields)
                    + " WHERE `idmicroSites` = " + ms.getIdmicroSites();
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
            MicroSites ms = (MicroSites) obj;
            Statement stmt = con.createStatement();
            String query = "DELETE FROM " + table + " "
                    + "WHERE `idmicroSites` = " + ms.getIdmicroSites();
            
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
            MicroSites ms = new MicroSites();
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + " WHERE `idmicroSites` = " + ID;

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
            MicroSites ms = new MicroSites();
            String query = "SELECT * FROM " + table
                    + " WHERE `siteName` = ?";            
            PreparedStatement pStmt = con.prepareStatement(query);
            pStmt.setString(1, name);
            
            ResultSet rs = pStmt.executeQuery();

            if (rs.next()) {
                FromResultSet(ms, rs);
            }

            rs.close();
            pStmt.close();

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
            MicroSites ms = new MicroSites();            
            String query = "SELECT * FROM " + table
                    + " WHERE `siteAbbr` = ?";
            PreparedStatement pStmt = con.prepareStatement(query);
            pStmt.setString(1, abbr);

            ResultSet rs = pStmt.executeQuery();
            
            if (rs.next()) {
                FromResultSet(ms, rs);
            }

            rs.close();
            pStmt.close();

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
            MicroSites ms = new MicroSites();
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
    
    public ArrayList<MicroSites> GetAllActive(){
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        try{
            ArrayList<MicroSites> list = new ArrayList<>();
            MicroSites ms = null;
            Statement stmt = con.createStatement();
            String query = "SELECT * FROM " + table + " "
                    + "WHERE `active` = " + true;
            
            ResultSet rs = stmt.executeQuery(query);
            
            while(rs.next()){
                ms = new MicroSites();
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
    
    public List<MicroSites> SearchSiteByName(String NameFragment, boolean CaseSensitive, boolean ActiveOnly) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        List<MicroSites> list = new ArrayList<>();
        try {
            PreparedStatement stmt = null;
            ResultSet rs = null;

            //stmt = con.createStatement();

            String query = "SELECT * FROM " + table + " ";
            if (CaseSensitive == false) {
                query += "WHERE (LOWER(`siteName`) LIKE LOWER(?)) ";
            } else {
                query += "WHERE (`siteName` LIKE ?) ";
            }
            
            if(ActiveOnly)
            {
                query += "AND `active` = true ";
            }

            String nameParam = SQLUtil.createSearchParam(NameFragment);
            stmt = createStatement(con, query, nameParam);
            rs = stmt.executeQuery();

            while (rs.next()) {
                MicroSites ms = new MicroSites();
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
    
    public List<MicroSites> SearchSiteByAbbr(String NameFragment, boolean CaseSensitive, boolean ActiveOnly) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        List<MicroSites> list = new ArrayList<>();
        try {
            PreparedStatement stmt = null;
            ResultSet rs = null;

            //stmt = con.createStatement();

            String query = "SELECT * FROM " + table + " ";
            if (CaseSensitive == false) {
                query += "WHERE (LOWER(`siteAbbr`) LIKE LOWER(?)) ";
            } else {
                query += "WHERE (`siteAbbr` LIKE ?) ";
            }
            
            if(ActiveOnly)
            {
                query += "AND `active` = true ";
            }

            String nameParam = SQLUtil.createSearchParam(NameFragment);
            stmt = createStatement(con, query, nameParam);
            rs = stmt.executeQuery();

            while (rs.next()) {
                MicroSites ms = new MicroSites();
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
    
    /**
     * Inserts the microbiology site and returns the unique identifier
     *  generated. Returns NULL if unsuccessful.
     * 
     * @param site
     * @return idmicroSites or NULL if unsuccessful
     */
    public Integer InsertGetId(MicroSites site)
    {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
      
        try
        {
            String sql = GenerateInsertStatement(fields);
            PreparedStatement pStmt = con.prepareStatement(sql,
                                      Statement.RETURN_GENERATED_KEYS);      
            SetStatement(site, pStmt);            
            int affectedRows = pStmt.executeUpdate();
            
            if (affectedRows == 0)
            {
                throw new SQLException("Insert failed, no rows affected.");
            }
            
            Integer microSiteId;
            
            try (ResultSet generatedKeys = pStmt.getGeneratedKeys()) 
            {
                if (generatedKeys.next())
                {
                    microSiteId = generatedKeys.getInt(1);
                }
                else
                {
                    throw new SQLException("Insert failed, no ID obtained.");
                }
            }
            pStmt.close();
            
            return microSiteId;
        } catch (SQLException ex) {
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
    private MicroSites FromResultSet(MicroSites obj, ResultSet rs) throws SQLException {
        obj.setIdmicroSites(rs.getInt("idmicroSites"));
        obj.setSiteName(rs.getString("siteName"));
        obj.setSiteAbbr(rs.getString("siteAbbr"));
        obj.setExternalId(rs.getString("externalId"));
        obj.setActive(rs.getBoolean("active"));

        return obj;
    }

    //--------------------------------------------------------------------------
    private PreparedStatement SetStatement(MicroSites obj, PreparedStatement pStmt) throws SQLException {
        int i = 0;
        SQLUtil.SafeSetString(pStmt, ++i, obj.getSiteName());
        SQLUtil.SafeSetString(pStmt, ++i, obj.getSiteAbbr());
        SQLUtil.SafeSetString(pStmt, ++i, obj.getExternalId());
        SQLUtil.SafeSetBoolean(pStmt, ++i, obj.getActive());

        return pStmt;
    }
    
    @Override
    public String structureCheck() {
        return DatabaseStructureCheck.structureCheck(fields, table, con);
    }
}
