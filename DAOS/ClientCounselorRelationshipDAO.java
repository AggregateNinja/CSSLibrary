package DAOS;

import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IStructureCheckable;
import DOS.ClientCounselorRelationship;
import DOS.ClientCounselorRelationship;
import DOS.Counselors;
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

/**
 * @date: Nov 25, 2013
 * @author: Keith Maggio <keith@csslis.com>
 *
 * @project:
 * @package: DAOS
 * @file name: ClientCounselorRelationshipDAO.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc. All Rights Reserved
 */
public class ClientCounselorRelationshipDAO implements DAOInterface, IStructureCheckable {

    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`clientCounselorRelationship`";

    public boolean InsertClientCounselorRelationship(ClientCounselorRelationship ccr) throws SQLException {
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
                    + " `idcounselors`)"
                    + " values (?,?)";

            PreparedStatement pStmt = con.prepareStatement(stmt);

            pStmt.setInt(1, ccr.getIdClients());
            pStmt.setInt(2, ccr.getIdCounselors());

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

    public boolean UpdateClientCounselorRelationship(ClientCounselorRelationship ccr) {
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
                    + " `idcounselors` = ? "
                    + "WHERE `idclientCounselorRelationship` = " + ccr.getIdClientCounselorRelationship();

            PreparedStatement pStmt = con.prepareStatement(stmt);

            pStmt.setInt(1, ccr.getIdClients());
            pStmt.setInt(2, ccr.getIdCounselors());

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

    public boolean DeleteClientCounselorRelationship(ClientCounselorRelationship cdr) {
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
                    + " WHERE `idclientCounselorRelationship` = " + cdr.getIdClientCounselorRelationship();

            stmt.executeUpdate(query);

            stmt.close();

            return true;
        } catch (Exception ex) {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return false;
        }
    }

    public List<Integer> GetAllCounselorRelationships(Integer ClientID) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        try {
            List<Integer> cdrlist = new ArrayList<>();
            Statement stmt = con.createStatement();

            String query = "SELECT `idcounselors` FROM " + table
                    + " WHERE `idclients` = " + ClientID;

            stmt.executeQuery(query);
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                cdrlist.add(rs.getInt("idcounselors"));
            }

            rs.close();
            stmt.close();

            return cdrlist;
        } catch (Exception ex) {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }

    public List<Integer> GetAllClientRelationships(Integer CounselorID) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        try {
            List<Integer> cdrlist = new ArrayList<>();
            Statement stmt = con.createStatement();

            String query = "SELECT `idclients` FROM " + table
                    + " WHERE `idcounselors` = " + CounselorID;

            stmt.executeQuery(query);
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                cdrlist.add(rs.getInt("idclients"));
            }

            rs.close();
            stmt.close();

            return cdrlist;
        } catch (Exception ex) {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }

    public Integer GetClientCounselorRelationshipId(Integer ClientId, Integer CounselorId) {
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

            String query = "SELECT `idclientCounselorRelationship` FROM " + table
                    + " WHERE `idclients` = " + ClientId
                    + " AND `idcounselors` = " + CounselorId;

            stmt.executeQuery(query);
            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                id = rs.getInt("idclientCounselorRelationship");
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

    public boolean Exists(Integer ClientId, Integer CounselorId) {
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

            String query = "SELECT `idclientCounselorRelationship` FROM " + table
                    + " WHERE `idclients` = " + ClientId
                    + " AND `idcounselors` = " + CounselorId;

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
        String query = "SELECT `clientCounselorRelationship`.`idclientCounselorRelationship`,\n"
                + "    `clientCounselorRelationship`.`idclients`,\n"
                + "    `clientCounselorRelationship`.`idcounselors`\n"
                + "FROM `css`.`clientCounselorRelationship` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, con);
    }
}
