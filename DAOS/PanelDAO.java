package DAOS;

/**
 * @date: Mar 13, 2012
 * @author: Ryan
 *
 * @project: CSSLibrary
 * @package: DAOS
 * @file name: PanelDAO.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc. All Rights Reserved
 */
import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IStructureCheckable;
import DOS.Panels;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PanelDAO implements DAOInterface, IStructureCheckable {

    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`panels`";

    public boolean InsertPanel(Panels panel) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }

        try {
            String stmt = "INSERT INTO " + table + "("
                    + " `idpanels`,"
                    + " `subtestId`,"
                    + " `subtestOrder`,"
                    + " `optional`)"
                    + " values (?,?,?,?)";

            PreparedStatement pStmt = con.prepareStatement(stmt);

            pStmt.setInt(1, panel.getIdpanels());
            pStmt.setInt(2, panel.getSubtestId());
            pStmt.setInt(3, panel.getSubtestOrder());
            pStmt.setBoolean(4, panel.isOptional());

            pStmt.executeUpdate();

            pStmt.close();

            return true;
        } catch (Exception ex) {
            System.out.println("Panel (" + panel.getIdpanels() + ":" + panel.getSubtestId() + ") - " + ex.toString());
            return false;
        }
    }

    public boolean UpdatePanel(Panels panel) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }
        try {
            String stmt = "UPDATE " + table + " SET"
                    + " `idpanels` = ?,"
                    + " `subtestID` = ?,"
                    + " `subtestOrder` = ?"
                    + " `optional` = ?"
                    + " WHERE `idpanels` = " + panel.getIdpanels()
                    + " AND `subtestID` = " + panel.getSubtestId();

            PreparedStatement pStmt = con.prepareStatement(stmt);

            pStmt.setInt(1, panel.getIdpanels());
            pStmt.setInt(2, panel.getSubtestId());
            pStmt.setInt(3, panel.getSubtestOrder());
            pStmt.setBoolean(4, panel.isOptional());

            pStmt.executeUpdate();

            pStmt.close();

            return true;
        } catch (Exception ex) {
            System.out.println("Panel (" + panel.getIdpanels() + ":" + panel.getSubtestId() + ") - " + ex.toString());
            return false;
        }
    }
    
    /**
     * Given an idpanels, returns Panels objects in the order
     *  they are specified in the panels table.
     * @param id
     * @return 
     */
    public List<Panels> GetOrderedPanels(int id)
    {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        try {
            List<Panels> ls = new ArrayList<Panels>();
            Panels panel;
            Statement stmt = con.createStatement();

            String query = "SELECT `idpanels`,"
                    + " `subtestID`,"
                    + " `subtestOrder`,"
                    + " `optional`"
                    + " FROM " + table
                    + " WHERE `idpanels` = " + id
                    + " ORDER BY `subtestOrder`";
                    
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                panel = new Panels();
                panel.setIdpanels(rs.getInt("idpanels"));
                panel.setSubtestId(rs.getInt("subtestID"));
                panel.setSubtestOrder(rs.getInt("subtestOrder"));
                panel.setOptional(rs.getBoolean("optional"));

                ls.add(panel);
            }

            rs.close();
            stmt.close();

            return ls;
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            return null;
        }       
    }

    public List<Panels> GetPanels(int id) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        try {
            List<Panels> ls = new ArrayList<Panels>();
            Panels panel;
            Statement stmt = con.createStatement();

            String query = "SELECT `idpanels`,"
                    + " `subtestID`,"
                    + " `subtestOrder`,"
                    + " `optional`"
                    + " FROM " + table
                    + " WHERE `idpanels` = " + id;
                    
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                panel = new Panels();
                panel.setIdpanels(rs.getInt("idpanels"));
                panel.setSubtestId(rs.getInt("subtestID"));
                panel.setSubtestOrder(rs.getInt("subtestOrder"));
                panel.setOptional(rs.getBoolean("optional"));
                
                ls.add(panel);
            }

            rs.close();
            stmt.close();

            return ls;
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            return null;
        }

    }
    
    /**
     * Checks to see if there are subtest rows for the supplied test.
     * NOTE: Does NOT check whether the associated tests are active.
     * @param idTests
     * @return 
     */
    public Boolean IsPanelHeader(int idTests)
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
            Statement stmt = con.createStatement();
            
            String query = "SELECT COUNT(*) FROM " + table +
                    " WHERE `idpanels` = " + idTests;
            
            ResultSet rs = stmt.executeQuery(query);
            
            int i=0;
            if (rs.next()) {
                i = rs.getInt(1);
            }
            rs.close();
            stmt.close();

            return i > 0;
        }
        catch (SQLException ex)
        {
            System.out.println(ex.toString());            
        }
        return null;
    }

    public boolean Exists(int testID, int subID) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }

        int i = 0;
        Statement stmt = con.createStatement();

        String query = "SELECT COUNT(*) "
                + "FROM " + table
                + " WHERE `idpanels` = " + testID
                + " AND `subtestId` = " + subID;

        ResultSet rs = stmt.executeQuery(query);

        if (rs.next()) {
            i = rs.getInt(1);
        }
        rs.close();
        stmt.close();

        return i > 0;

    }
    
    public boolean IsTestIDSubtest(int subID) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }

        int i = 0;
        Statement stmt = con.createStatement();

        String query = "SELECT COUNT(*) "
                + "FROM " + table
                + " WHERE `subtestId` = " + subID;

        ResultSet rs = stmt.executeQuery(query);

        if (rs.next()) {
            i = rs.getInt(1);
        }
        rs.close();
        stmt.close();

        return i > 0;

    }

    public List<Panels> GetParentPanels(int SubTestID) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        List<Panels> ls = new ArrayList<Panels>();
        Panels panel;
        Statement stmt = con.createStatement();

        String query = "SELECT `idpanels`,"
                + " `subtestID`,"
                + " `subtestOrder`,"
                + " `optional`"
                + " FROM " + table
                + " WHERE `subtestID` = " + SubTestID;

        ResultSet rs = stmt.executeQuery(query);

        while (rs.next()) {
            panel = new Panels();
            panel.setIdpanels(rs.getInt("idpanels"));
            panel.setSubtestId(rs.getInt("subtestID"));
            panel.setSubtestOrder(rs.getInt("subtestOrder"));
            panel.setOptional(rs.getBoolean("optional"));

            ls.add(panel);
        }

        rs.close();
        stmt.close();

        return ls;
    }
    
    /**
     * Returns an ordered list of the subtests associated with the provided panel id.
     * @param panelID
     * @return
     * @throws SQLException 
     */
    public ArrayList<Integer> GetSubTestIdsOrdered(int panelID) throws SQLException
    {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        int ret = 0;
        Statement stmt = con.createStatement();

        String query = "SELECT "
                + " `subtestID` "
                + " FROM " + table
                + " WHERE `idpanels` = " + panelID
                + " ORDER BY subtestOrder ASC";


        ResultSet rs = stmt.executeQuery(query);
        ArrayList<Integer> results = new ArrayList();
        while (rs.next()) {
            results.add(rs.getInt("subtestId"));
        }

        rs.close();
        stmt.close();

        return results;         
    }
    
    /**
     *  Returns a hashset of test IDs associated with the supplied panel ID.
     * @param panelID
     * @return
     * @throws SQLException 
     */
    public HashSet<Integer> GetSubTestIDs(int panelID) throws SQLException
    {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        int ret = 0;
        Statement stmt = con.createStatement();

        String query = "SELECT "
                + " `subtestID` "
                + " FROM " + table
                + " WHERE `idpanels` = " + panelID + " ";


        ResultSet rs = stmt.executeQuery(query);
        HashSet<Integer> results = new HashSet();
        while (rs.next()) {
            results.add(rs.getInt("subtestId"));
        }

        rs.close();
        stmt.close();

        return results;        
    }

    public int GetSubTestID(int panelID, int order) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return -1;
        }

        int ret = 0;
        Statement stmt = con.createStatement();

        String query = "SELECT "
                + " `subtestID` "
                + " FROM " + table
                + " WHERE `idpanels` = " + panelID + " "
                + " AND `subtestOrder` = " + order;

        ResultSet rs = stmt.executeQuery(query);

        if (rs.next()) {
            ret = rs.getInt("subtestId");
        }

        rs.close();
        stmt.close();

        return ret;
    }

    public Panels GetPanel(int PanelID, int SubTestID) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        Panels panel = null;
        Statement stmt = con.createStatement();

        String query = "SELECT `idpanels`,"
                + " `subtestID`,"
                + " `subtestOrder`,"
                + " `optional`"
                + " FROM " + table
                + " WHERE `idpanels` = " + PanelID
                + " AND `subtestID` = " + SubTestID;

        ResultSet rs = stmt.executeQuery(query);

        if (rs.next()) {
            panel = new Panels();
            panel.setIdpanels(rs.getInt("idpanels"));
            panel.setSubtestId(rs.getInt("subtestID"));
            panel.setSubtestOrder(rs.getInt("subtestOrder"));
            panel.setOptional(rs.getBoolean("optional"));
        }

        rs.close();
        stmt.close();

        return panel;
    }

    public boolean RemoveTestFromPanel(int panelId, int subtestId) throws SQLException {
        try {
            try {
                if (con.isClosed()) {
                    con = CheckDBConnection.Check(dbs, con);
                }
            } catch (SQLException sex) {
                System.out.println(sex.toString());
                return false;
            }

            String stmt = "DELETE FROM " + table + " "
                    + "WHERE `idpanels` = " + panelId + " "
                    + "AND `subtestId` = " + subtestId;

            PreparedStatement pStmt = con.prepareStatement(stmt);

            pStmt.executeUpdate();

            pStmt.close();

            return true;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return false;
        }
    }
    
    public boolean CheckIfPanelHeader(Integer testID) throws SQLException
    {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }

        int i = 0;
        Statement stmt = con.createStatement();

        String query = "SELECT COUNT(*) "
                + "FROM " + table
                + " WHERE `idpanels` = " + testID;

        ResultSet rs = stmt.executeQuery(query);

        if (rs.next()) {
            i = rs.getInt(1);
        }
        rs.close();
        stmt.close();

        return i > 0;
    }

    @Override
    public Boolean Insert(Serializable obj)
    {
        try
        {
            return InsertPanel((Panels)obj);
        }
        catch (SQLException ex)
        {
            Logger.getLogger(PanelDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public Boolean Update(Serializable obj)
    {
        try
        {
            return UpdatePanel((Panels)obj);
        }
        catch (SQLException ex)
        {
            Logger.getLogger(PanelDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public Boolean Delete(Serializable obj)
    {
        return false;
    }

    @Override
    public Serializable getByID(Integer ID)
    {
        throw new UnsupportedOperationException("Panels require 2 ID fields. Do an Instance check before using this DAO."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public String structureCheck() {
        String query = "SELECT `panels`.`idpanels`,\n"
                + "    `panels`.`subtestId`,\n"
                + "    `panels`.`subtestOrder`,\n"
                + "    `panels`.`optional`\n"
                + "FROM `css`.`panels` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, con);
    }
}
