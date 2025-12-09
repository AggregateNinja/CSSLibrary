/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAOS;

import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IStructureCheckable;
import DOS.Territory;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.Convert;
import Utility.SQLUtil;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static Utility.SQLUtil.createStatement;

/**
 * @date: Apr 28, 2014
 * @author: Derrick J. Piper <derrick@csslis.com>
 *
 * @project:
 * @package: DAOS
 * @file name: TerritoryDAO.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc. All Rights Reserved
 */
public class TerritoryDAO implements DAOInterface, IStructureCheckable
{

    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`territory`";
    
    /**
     * All fields except idterritory
     */
    private final ArrayList<String> fields = new ArrayList<>();

    public TerritoryDAO()
    {
        fields.add("territoryName");
        fields.add("description");
        fields.add("created");
        fields.add("createdBy");
    }

    /**
     * Description
     * @param obj Serializable, The territory to be inserted.
     * @return type Boolean returns true if inserted.
     */
    @Override
    public Boolean Insert(Serializable obj)
    {
        Territory ter = (Territory) obj;
        try
        {
            try
            {
                if (con.isClosed())
                {
                    con = CheckDBConnection.Check(dbs, con);
                }
            }
            catch (SQLException sex)
            {
                System.out.println(sex.toString());
                return false;
            }
            String stmt = GenerateInsertStatement(fields);
            PreparedStatement pStmt = con.prepareStatement(stmt);
            SetStatementFromTerritory(ter, pStmt);

            pStmt.executeUpdate();

            pStmt.close();

            return true;
        }
        catch (SQLException ex)
        {
            Logger.getLogger(Territory.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    /**
     * Description
     * @param obj Serializable The territory to be updated.
     * @return type Boolean returns true if updated.
     */
    @Override
    public Boolean Update(Serializable obj)
    {
        Territory ter = (Territory) obj;
        try
        {
            try
            {
                if (con.isClosed())
                {
                    con = CheckDBConnection.Check(dbs, con);
                }
            }
            catch (SQLException sex)
            {
                System.out.println(sex.toString());
                return false;
            }
            String stmt = GenerateUpdateStatement(fields) + " WHERE `idterritory` = " + ter.getIdterritory();
            PreparedStatement pStmt = con.prepareStatement(stmt);

            SetStatementFromTerritory(ter, pStmt);

            pStmt.executeUpdate();

            pStmt.close();

            return true;
        }
        catch (SQLException ex)
        {
            Logger.getLogger(Territory.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    /**
     * Description
     * @param obj Serializable The territory to be deleted.
     * @return type Boolean returns true if deleted.
     */
    @Override
    public Boolean Delete(Serializable obj)
    {
        try
        {
            if (con.isClosed())
            {
                con = CheckDBConnection.Check(dbs, con);
            }
        }
        catch (SQLException sex)
        {
            System.out.println(sex.toString());
            return false;
        }
        return false;
    }

    /**
     * Description
     * @param ID Serializable The territory ID.
     * @return type Integer, The ID of the territory associated with the given ID.
     */
    @Override
    public Serializable getByID(Integer ID)
    {
        try
        {
            return GetTerritoryByID(ID);
        }
        catch (SQLException ex)
        {
            Logger.getLogger(TerritoryDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public boolean InsertWithID(Territory ter) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }
        
        try{
            String stmt = "INSERT INTO " + table + " ("
                    + "`idterritory`, "
                    + "`territoryName`, "
                    + "`description`, "
                    + "`created`, "
                    + "`createdBy`) "
                    + "VALUES (?,?,?,?,?)";
            
            PreparedStatement pStmt = con.prepareStatement(stmt);
            
            SQLUtil.SafeSetInteger(pStmt, 1, ter.getIdterritory());
            SQLUtil.SafeSetString(pStmt, 2, ter.getTerritoryName());
            SQLUtil.SafeSetString(pStmt, 3, ter.getDescription());
            SQLUtil.SafeSetTimeStamp(pStmt, 4, Convert.ToSQLDateTime(ter.getCreated()));
            SQLUtil.SafeSetInteger(pStmt, 5, ter.getCreatedBy());
            
            pStmt.executeUpdate();

            pStmt.close();

            return true;
        }catch(Exception ex){
            System.out.println(ex.toString());
            return false;
        }
    }
    
    /**
     * Description
     * @param name Array list The territory's name.
     * @param ID Array list The territory's ID.
     * @return type String, The name of the territory associated with the given ID.
     */
    public ArrayList<Territory> GetNameFromID(String name, Integer ID)
    {
        try
        {
            try
            {
                if (con.isClosed())
                {
                    con = CheckDBConnection.Check(dbs, con);
                }
            }
            catch (SQLException sex)
            {
                System.out.println(sex.toString());
                return null;
            }
            ArrayList<Territory> terList = new ArrayList<>();

            Statement stmt = con.createStatement();

            String query = "SELECT `name` FROM " + table
                    + " WHERE `idterritory` = " + ID;

            ResultSet rs = stmt.executeQuery(query);

            while (rs.next())
            {
                Territory ter = new Territory();
                SetTerritoryFromResultSet(ter, rs);
                terList.add(ter);
            }

            rs.close();
            stmt.close();
            return terList;
        }
        catch (SQLException ex)
        {
            Logger.getLogger(TerritoryDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    /**
     * Description
     * @param ter the Id of the territory for identification.
     * @return type Boolean returns true if the territory was up updated.
     * @throws SQLException if query fails.
     */
    public boolean UpdateTerritory(Territory ter) throws SQLException
    {
        try
        {
            if (con.isClosed())
            {
                con = CheckDBConnection.Check(dbs, con);
            }
        }
        catch (SQLException sex)
        {
            System.out.println(sex.toString());
            return false;
        }

        try
        {
            String stmt = "UPDATE " + table + " SET "
                    + "`territoryName` = ?, "
                    + "`description` = ?, "
                    + "`created` = ?, "
                    + "`createdBy` = ?, "
                    + "WHERE `idterritory` = " + ter.getIdterritory();

            PreparedStatement pStmt = con.prepareStatement(stmt);

            pStmt.setString(1, ter.getTerritoryName());
            pStmt.setString(2, ter.getDescription());
            SQLUtil.SafeSetTimeStamp(pStmt, 3, Convert.ToSQLDateTime(ter.getCreated()));
            //pStmt.setTimestamp(3, (Timestamp) ter.getCreated());
            pStmt.setInt(4, ter.getCreatedBy());

            pStmt.executeUpdate();

            pStmt.close();

            return true;
        }
        catch (SQLException ex)
        {
            System.out.println(ex.toString());
            return false;
        }
    }

    /**
     * Description
     * @param ID the Id of the territory for identification.
     * @return type String, The territory associated with the given ID.
     * @throws SQLException if the query fails.
     */
    public Territory GetTerritoryByID(int ID) throws SQLException
    {
        try
        {
            if (con.isClosed())
            {
                con = CheckDBConnection.Check(dbs, con);
            }
        }
        catch (SQLException sex)
        {
            System.out.println(sex.toString());
            return null;
        }

        Territory ter = new Territory();

        try
        {
            String query = "SELECT * FROM " + table + " "
                    + "WHERE `idterritory` = ?";

            PreparedStatement pStmt = con.prepareStatement(query);
            pStmt.setInt(1, ID);

            ResultSet rs = pStmt.executeQuery();

            while (rs.next())
            {
                SetTerritoryFromResultSet(ter, rs);
            }

            rs.close();
            pStmt.close();

            return ter;
        }
        catch (SQLException ex)
        {
            System.out.println(ex.toString());
            return null;
        }
    }

    public Territory GetTerritoryByName(String name)
    {
        try
        {
            if (con.isClosed())
            {
                con = CheckDBConnection.Check(dbs, con);
            }
        }
        catch (SQLException sex)
        {
            System.out.println(sex.toString());
            return null;
        }

        Territory ter = new Territory();

        try
        {
            String query = "SELECT * FROM " + table + " "
                    + "WHERE `territoryName` = ?";

            PreparedStatement pStmt = con.prepareStatement(query);
            pStmt.setString(1, name);

            ResultSet rs = pStmt.executeQuery();

            while (rs.next())
            {
                SetTerritoryFromResultSet(ter, rs);
            }

            rs.close();
            pStmt.close();

            return ter;
        }
        catch (SQLException ex)
        {
            System.out.println(ex.toString());
            return null;
        }
    }
    
    /**
     * Description
     * @return type List, Returns all territories.
     * @throws SQLException if the query fails.
     */
    public ArrayList<Territory> GetAllTerritories() throws SQLException
    {
        try
        {
            if (con.isClosed())
            {
                con = CheckDBConnection.Check(dbs, con);
            }
        }
        catch (SQLException sex)
        {
            System.out.println(sex.toString());
            return null;
        }

        ArrayList<Territory> terList = new ArrayList<>();

        try
        {
            Statement stmt = con.createStatement();
            String query = "SELECT * FROM " + table + " ";

            ResultSet rs = stmt.executeQuery(query);

            while (rs.next())
            {
                Territory ter = new Territory();
                SetTerritoryFromResultSet(ter, rs);
                terList.add(ter);
            }

            rs.close();
            stmt.close();

            return terList;
        }
        catch (SQLException ex)
        {
            System.out.println(ex.toString());
            return null;
        }

    }

    /**
     * Description
     * @param name String, The string fragment to compare in the database.
     * @return type String, The matching territory name associated with the entered pattern.
     * @throws SQLException if the query.
     */
    public ArrayList<Territory> GetTerritoryLike(String name) throws SQLException
    {
        try
        {
            if (con.isClosed())
            {
                con = CheckDBConnection.Check(dbs, con);
            }
        }
        catch (SQLException sex)
        {
            System.out.println(sex.toString());
            return null;
        }

        ArrayList<Territory> terList = new ArrayList<>();

        try
        {
            PreparedStatement stmt = null; //con.createStatement();
            String query = "SELECT * FROM" + table
                    + "WHERE `territoryName` LIKE ?";

            String nameParam = SQLUtil.createSearchParam(name);
            stmt = createStatement(con, query, nameParam);
            ResultSet rs = stmt.executeQuery();

            while (rs.next())
            {
                Territory ter = new Territory();
                SetTerritoryFromResultSet(ter, rs);
                terList.add(ter);
            }

            rs.close();
            stmt.close();

            return terList;
        }
        catch (SQLException ex)
        {
            System.out.println(ex.toString());
            return null;
        }
    }

    /**
     * Description
     * @param idterritory Integer, The ID of the territory.
     * @return type Boolean returns true if the territory does exist.
     * @throws SQLException if the query fails.
     */
    public boolean TerritoryExists(int idterritory) throws SQLException
    {
        try
        {
            if (con.isClosed())
            {
                con = CheckDBConnection.Check(dbs, con);
            }
        }
        catch (SQLException sex)
        {
            System.out.println(sex.toString());
            return false;
        }

        try
        {
            Statement stmt = con.createStatement();
            int rowCount = -1;

            String query = "SELECT COUNT(*) FROM " + table
                    + " WHERE `idterritory` = " + idterritory;

            ResultSet rs = stmt.executeQuery(query);

            rs.next();
            rowCount = rs.getInt(1);

            rs.close();
            stmt.close();
            return rowCount > 0;
        }
        catch (SQLException ex)
        {
            return false;
        }
    }

    /**
     * Description
     * @param NameFragment String A piece of the territory's name.
     * @param CaseSensitive Boolean To distinguish upper and lowercase letters.
     * @return type String, The name of the territory associated with the given fragment.
     */
    public List<Territory> SearchTerritoryByName(String NameFragment, boolean CaseSensitive)
    {
        try
        {
            if (con.isClosed())
            {
                con = CheckDBConnection.Check(dbs, con);
            }
        }
        catch (SQLException sex)
        {
            System.out.println(sex.toString());
            return null;
        }

        List<Territory> tlist = new ArrayList<>();
        try
        {
            PreparedStatement stmt = null;
            ResultSet rs = null;

            //stmt = con.createStatement();

            String query = "SELECT * FROM " + table + " ";
            if (CaseSensitive == false)
            {
                query += "WHERE LOWER(`territoryName`) LIKE LOWER(?) ";
            }
            else
            {
                query += "WHERE `territoryName` LIKE ? ";
            }

            String nameParam = SQLUtil.createSearchParam(NameFragment);
            stmt = createStatement(con, query, nameParam);
            rs = stmt.executeQuery();

            while (rs.next())
            {
                Territory t = new Territory();
                tlist.add(SetTerritoryFromResultSet(t, rs));
            }

            rs.close();
            stmt.close();
            return tlist;
        }
        catch (SQLException ex)
        {
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }

    public List<Territory> SearchTerritoryByName(String NameFragment)
    {
        return SearchTerritoryByName(NameFragment, false);
    }

    /**
     * Description
     * @param description String of the region name.
     * @return type String, the territory dictated by the region it is located in.
     * @throws SQLException if the query fails.
     */
    public List<Territory> GetTerritoryByDescription(String description) throws SQLException
    {
        try
        {
            if (con.isClosed())
            {
                con = CheckDBConnection.Check(dbs, con);
            }
        }
        catch (SQLException sex)
        {
            System.out.println(sex.toString());
            return null;
        }

        List<Territory> tlist = new ArrayList<>();

        try
        {
            String query = "SELECT * FROM " + table + " "
                    + "WHERE `description` = ? ";

            PreparedStatement stmt = createStatement(con, query, description);//con.createStatement();

            ResultSet rs = stmt.executeQuery(query);

            while (rs.next())
            {
                Territory ter = new Territory();
                SetTerritoryFromResultSet(ter, rs);
                tlist.add(ter);
            }

            rs.close();
            stmt.close();

            return tlist;
        }
        catch (SQLException ex)
        {
            System.out.println(ex.toString());
            return null;
        }
    }

    private String GenerateInsertStatement(ArrayList<String> fields)
    {

        String stmt = "INSERT INTO " + table + "(";
        String values = ") values (";
        for (int i = 0; i < fields.size(); ++i)
        {
            stmt += " `" + fields.get(i) + "`";
            values += "?";
            if (i != fields.size() - 1)
            {
                stmt += ", ";
                values += ",";
            }
        }
        values += ")";
        return stmt + values;
    }

    private String GenerateUpdateStatement(ArrayList<String> fields)
    {

        String stmt = "UPDATE " + table + " SET";
        for (int i = 0; i < fields.size(); ++i)
        {
            stmt += " `" + fields.get(i) + "` = ?";
            if (i != fields.size() - 1)
            {
                stmt += ",";
            }
        }
        return stmt;
    }

    private Territory SetTerritoryFromResultSet(Territory territory, ResultSet rs) throws SQLException
    {
        territory.setIdterritory(rs.getInt("idterritory"));
        territory.setTerritoryName(rs.getString("territoryName"));
        territory.setDescription(rs.getString("description"));
        territory.setCreated(rs.getTimestamp("created"));
        territory.setCreatedBy(rs.getInt("createdBy"));

        return territory;
    }

    private PreparedStatement SetStatementFromTerritory(Territory ter, PreparedStatement pStmt) throws SQLException
    {
        pStmt.setString(1, ter.getTerritoryName());
        pStmt.setString(2, ter.getDescription());
        pStmt.setTimestamp(3, Convert.ToSQLDateTime(ter.getCreated()));
        pStmt.setInt(4, ter.getCreatedBy());
        return pStmt;
    }
    
    @Override
    public String structureCheck() {
        return DatabaseStructureCheck.structureCheck(fields, table, con);
    }
}