package DAOS;

import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IStructureCheckable;
import DOS.Multichoice;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import static Utility.SQLUtil.createStatement;

/**
 * @date: Oct 3, 2013
 * @author: Keith Maggio <keith@csslis.com>
 *
 * @project:
 * @package: DAOS
 * @file name: MultichoiceDAO.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc. All Rights Reserved
 */
public class MultichoiceDAO implements DAOInterface, IStructureCheckable {

    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`multichoice`";

    public boolean InsertMultichoice(Multichoice choice) throws SQLException {
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
                    + " `testId`,"
                    + " `choice`,"
                    + " `isAbnormal`,"
                    + " `choiceOrder`)"
                    + " values (?,?,?,?)";

            PreparedStatement pStmt = con.prepareStatement(stmt);

            pStmt.setInt(1, choice.getTestId());
            pStmt.setString(2, choice.getChoice());
            pStmt.setBoolean(3, choice.getIsAbnormal());
            pStmt.setInt(4, choice.getChoiceOrder());

            pStmt.executeUpdate();

            pStmt.close();

            return true;
        } catch (Exception ex) {
            String message = ex.toString();
            //TODO:  Add logging for failures
            System.out.println(getClass().toString() + ": Insert: " + message);
            return false;
        }
    }

    public boolean UpdateMultichoice(Multichoice choice) throws SQLException {
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
                    + " `testId` = ?,"
                    + " `choice` = ?,"
                    + " `isAbnormal` = ?,"
                    + " `choiceOrder` = ? "
                    + "WHERE `idMultiChoice` = " + choice.getIdMultiChoice();

            PreparedStatement pStmt = con.prepareStatement(stmt);

            pStmt.setInt(1, choice.getTestId());
            pStmt.setString(2, choice.getChoice());
            pStmt.setBoolean(3, choice.getIsAbnormal());
            pStmt.setInt(4, choice.getChoiceOrder());

            pStmt.executeUpdate();

            pStmt.close();

            return true;
        } catch (Exception ex) {
            String message = ex.toString();
            //TODO:  Add logging for failures
            System.out.println(getClass().toString() + ": Update: " + message);
            return false;
        }
    }
    
    
    /**
     * Returns the multichoice option based on the test Id and the supplied string
     * Trims whitespace off of the ends before doing comparison.
     * @param testId
     * @param choiceName
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public Multichoice GetChoice(int testId, String choiceName) throws SQLException, Exception
    {
        if (con.isClosed()) con = CheckDBConnection.Check(dbs, con);
        
        if (choiceName != null) choiceName = choiceName.trim();
        
        String query = "SELECT * FROM " + table
                + " WHERE `testId` = ?"
                + " AND `choice` = ?";
        
        PreparedStatement pStmt = con.prepareStatement(query);
        pStmt.setInt(1, testId);
        pStmt.setString(2, choiceName);
        ResultSet rs = pStmt.executeQuery();
        Multichoice mc = null;
        if (rs.next())
        {
            mc = new Multichoice();
            mc.setIdMultiChoice(rs.getInt("idMultiChoice"));
            mc.setTestId(rs.getInt("testId"));
            mc.setChoice(rs.getString("choice"));
            mc.setIsAbnormal(rs.getBoolean("isAbnormal"));
            mc.setChoiceOrder(rs.getInt("choiceOrder"));
        }
        return mc;
    }    

    public Multichoice GetChoice(int testId, int choiceOrder) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        try {
            Multichoice mc = new Multichoice();
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + " WHERE `testId` = " + testId
                    + " AND `choiceOrder` = " + choiceOrder;

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                mc.setIdMultiChoice(rs.getInt("idMultiChoice"));
                mc.setTestId(rs.getInt("testId"));
                mc.setChoice(rs.getString("choice"));
                mc.setIsAbnormal(rs.getBoolean("isAbnormal"));
                mc.setChoiceOrder(rs.getInt("choiceOrder"));
            }

            rs.close();
            stmt.close();

            return mc;
        } catch (Exception ex) {
            String message = ex.toString();
            //TODO:  Add logging for failures
            System.out.println(getClass().toString() + ": GetChoice: " + message);
            return null;
        }
    }

    public Multichoice GetChoiceByID(int id) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        try {
            Multichoice mc = new Multichoice();
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + " WHERE `idMultiChoice` = " + id;
            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                mc.setIdMultiChoice(rs.getInt("idMultiChoice"));
                mc.setTestId(rs.getInt("testId"));
                mc.setChoice(rs.getString("choice"));
                mc.setIsAbnormal(rs.getBoolean("isAbnormal"));
                mc.setChoiceOrder(rs.getInt("choiceOrder"));
            }

            rs.close();
            stmt.close();

            return mc;
        } catch (Exception ex) {
            String message = ex.toString();
            //TODO:  Add logging for failures
            System.out.println(getClass().toString() + ": GetChoice: " + message);
            return null;
        }
    }
    
    public Multichoice GetChoiceByNameAndTestId(String name, int testId) throws Exception
    {
        if (name == null) return null;
        if (con.isClosed()) con = CheckDBConnection.Check(dbs, con);
        
        String query = "SELECT * FROM " + table + 
                " WHERE testId = " + testId +
                " AND choice = ?";
        
        PreparedStatement stmt = null; //con.createStatement();
        stmt = createStatement(con, query, name);
        ResultSet rs = stmt.executeQuery();
        Multichoice mc = null;
        if (rs.next())
        {
            mc = new Multichoice();
            mc.setIdMultiChoice(rs.getInt("idMultiChoice"));
            mc.setTestId(rs.getInt("testId"));
            mc.setChoice(rs.getString("choice"));
            mc.setIsAbnormal(rs.getBoolean("isAbnormal"));
            mc.setChoiceOrder(rs.getInt("choiceOrder"));            
        }
        
        return mc;
        
    }

    public int GetChoiceId(int testId, int choiceOrder) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return -1;
        }

        try {
            int id = 0;
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + " WHERE `testId` = " + testId
                    + " AND `choiceOrder` = " + choiceOrder;

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                id = rs.getInt("idMultiChoice");
            }

            rs.close();
            stmt.close();

            return id;
        } catch (Exception ex) {
            String message = ex.toString();
            //TODO:  Add logging for failures
            System.out.println(getClass().toString() + ": GetChoiceId: " + message);
            return 0;
        }
    }

    public Multichoice[] GetAllChoicesForTest(int testId) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        try {
            ArrayList<Multichoice> list = new ArrayList<Multichoice>();
            Statement stmt = null;
            ResultSet rs = null;
            int x = 0;

            stmt = con.createStatement();
            String query = "SELECT * FROM " + table + " "
                    + "WHERE `testId` = " + testId + ";";

            rs = stmt.executeQuery(query);

            while (rs.next()) {
                Multichoice mc = new Multichoice();
                mc.setIdMultiChoice(rs.getInt("idMultiChoice"));
                mc.setTestId(rs.getInt("testId"));
                mc.setChoice(rs.getString("choice"));
                mc.setIsAbnormal(rs.getBoolean("isAbnormal"));
                mc.setChoiceOrder(rs.getInt("choiceOrder"));
                list.add(mc);
            }

            rs.close();
            stmt.close();

            if (list.isEmpty()) {
                return null;
            }

            Multichoice[] outins = new Multichoice[list.size()];
            outins = list.toArray(outins);
            return outins;
        } catch (Exception ex) {
            String message = ex.toString();
            //TODO:  Add logging for failures
            System.out.println(getClass().toString() + ": GetAllChoicesForTest: " + message);
            return null;
        }
    }

    public ArrayList<Multichoice> GetAllChoicesForTestList(int testId) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        try {
            ArrayList<Multichoice> list = new ArrayList<Multichoice>();
            Statement stmt = null;
            ResultSet rs = null;
            int x = 0;

            stmt = con.createStatement();
            String query = "SELECT * FROM " + table + " "
                    + "WHERE `testId` = " + testId + " "
                    + "ORDER BY `choiceOrder` ASC";

            rs = stmt.executeQuery(query);

            while (rs.next()) {
                Multichoice mc = new Multichoice();
                mc.setIdMultiChoice(rs.getInt("idMultiChoice"));
                mc.setTestId(rs.getInt("testId"));
                mc.setChoice(rs.getString("choice"));
                mc.setIsAbnormal(rs.getBoolean("isAbnormal"));
                mc.setChoiceOrder(rs.getInt("choiceOrder"));
                list.add(mc);
            }

            rs.close();
            stmt.close();

            if (list.isEmpty()) {
                return null;
            }

            return list;
        } catch (Exception ex) {
            String message = ex.toString();
            //TODO:  Add logging for failures
            System.out.println(getClass().toString() + ": GetAllChoicesForTest: " + message);
            return null;
        }
    }

    public ResultSet SearchByTestID(int test) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        try {
            Statement stmt = con.createStatement();

            String query = "SELECT `choiceOrder` AS 'Order', `choice` AS 'Choice' "
                    + "FROM " + table + " "
                    + "WHERE `testId`  " + test + " "
                    + "ORDER BY `choice`";

            ResultSet rs = stmt.executeQuery(query);

            return rs;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }

    @Override
    public Boolean Insert(Serializable obj)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Boolean Update(Serializable obj)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Boolean Delete(Serializable obj)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Serializable getByID(Integer ID)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public String structureCheck() {
        String query = "SELECT `multichoice`.`idMultiChoice`,\n"
                + "    `multichoice`.`testId`,\n"
                + "    `multichoice`.`choice`,\n"
                + "    `multichoice`.`isAbnormal`,\n"
                + "    `multichoice`.`choiceOrder`\n"
                + "FROM `css`.`multichoice` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, con);
    }
}
