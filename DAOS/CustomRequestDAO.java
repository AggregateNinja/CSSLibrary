package DAOS;

import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IStructureCheckable;
import DOS.CustomRequests;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * @date: Nov 26, 2013
 * @author: Keith Maggio <keith@csslis.com>
 *
 * @project:
 * @package: DAOS
 * @file name: CustomRequestDAO.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc. All Rights Reserved
 */
public class CustomRequestDAO implements DAOInterface, IStructureCheckable {

    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`customRequests`";

    public boolean InsertCustomRequests(CustomRequests cr) {
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
                    + " `idclients`,"
                    + " `idtests`)"
                    + " values (?,?)";

            PreparedStatement pStmt = con.prepareStatement(stmt);

            pStmt.setInt(1, cr.getIdclients());
            pStmt.setInt(2, cr.getIdtests());

            pStmt.executeUpdate();

            pStmt.close();

            return true;
        } catch (Exception ex) {
            String message = ex.toString();
            //TODO:  Add logging for failures
            System.out.println(message);
            return false;
        }
    }

    public boolean UpdateCustomRequests(CustomRequests cr) {
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
                    + " `idclients` = ?,"
                    + " `idtests` = ? "
                    + "WHERE `idCustomRequests` = " + cr.getIdCustomRequests();

            PreparedStatement pStmt = con.prepareStatement(stmt);

            pStmt.setInt(1, cr.getIdclients());
            pStmt.setInt(2, cr.getIdtests());

            pStmt.executeUpdate();

            pStmt.close();

            return true;
        } catch (Exception ex) {
            String message = ex.toString();
            //TODO:  Add logging for failures
            System.out.println(message);
            return false;
        }
    }

    public boolean DeleteCustomRequests(CustomRequests cr) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }

        try {
            Statement stmt = con.createStatement();

            String query = "DELETE FROM " + table
                    + " WHERE `idCustomRequests` = " + cr.getIdCustomRequests();

            stmt.executeQuery(query);

            stmt.close();

            return true;
        } catch (Exception ex) {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return false;
        }
    }

    public List<Integer> GetAllCustomRequestTestIDs(Integer ClientID) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        try {
            List<Integer> aotlist = new ArrayList<>();
            Statement stmt = con.createStatement();

            String query = "SELECT `idtests` FROM " + table
                    + " WHERE `idclients` = " + ClientID;

            stmt.executeQuery(query);
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                aotlist.add(rs.getInt("idtests"));
            }

            rs.close();
            stmt.close();

            return aotlist;
        } catch (Exception ex) {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }

    public List<Integer> GetAllClientRelationships(Integer TestID) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        try {
            List<Integer> aotlist = new ArrayList<>();
            Statement stmt = con.createStatement();

            String query = "SELECT `idclients` FROM " + table
                    + " WHERE `idtests` = " + TestID;

            stmt.executeQuery(query);
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                aotlist.add(rs.getInt("idclients"));
            }

            rs.close();
            stmt.close();

            return aotlist;
        } catch (Exception ex) {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }

    public Integer GetCustomRequestsId(Integer ClientId, Integer TestId) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        try {
            Integer id = null;
            Statement stmt = con.createStatement();

            String query = "SELECT `idCustomRequests` FROM " + table
                    + " WHERE `idclients` = " + ClientId
                    + " AND `idtests` = " + TestId;

            stmt.executeQuery(query);
            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                id = rs.getInt("idCustomRequests");
            }

            rs.close();
            stmt.close();

            return id;
        } catch (Exception ex) {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }

    public boolean Exists(Integer ClientId, Integer TestID) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }

        try {
            boolean exists = false;
            Statement stmt = con.createStatement();

            String query = "SELECT `idCustomRequests` FROM " + table
                    + " WHERE `idclients` = " + ClientId
                    + " AND `idtests` = " + TestID;

            stmt.executeQuery(query);
            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                exists = true;
            }

            rs.close();
            stmt.close();

            return exists;
        } catch (Exception ex) {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return false;
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
        String query = "SELECT `customRequests`.`idcustomRequests`,\n"
                + "    `customRequests`.`idclients`,\n"
                + "    `customRequests`.`idtests`\n"
                + "FROM `css`.`customRequests` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, con);
    }
}
