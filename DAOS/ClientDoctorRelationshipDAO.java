package DAOS;

import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IStructureCheckable;
import DOS.ClientDoctorRelationship;
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
 * @file name: ClientDoctorRelationshipDAO.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc. All Rights Reserved
 */
public class ClientDoctorRelationshipDAO implements DAOInterface, IStructureCheckable {

    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`clientDoctorRelationship`";

    public boolean InsertClientDoctorRelationship(ClientDoctorRelationship cdr) throws SQLException {
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
                    + " `iddoctors`)"
                    + " values (?,?)";

            PreparedStatement pStmt = con.prepareStatement(stmt);

            pStmt.setInt(1, cdr.getIdClients());
            pStmt.setInt(2, cdr.getIdDoctors());

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

    public boolean UpdateClientDoctorRelationship(ClientDoctorRelationship cdr) {
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
                    + " `iddoctors` = ? "
                    + "WHERE `idclientDoctorRelationship` = " + cdr.getIdclientDoctorRelationship();

            PreparedStatement pStmt = con.prepareStatement(stmt);

            pStmt.setInt(1, cdr.getIdClients());
            pStmt.setInt(2, cdr.getIdDoctors());

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

    public boolean DeleteClientDoctorRelationship(ClientDoctorRelationship cdr) {
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
                    + " WHERE `idclientDoctorRelationship` = " + cdr.getIdclientDoctorRelationship();

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

    public List<Integer> GetAllDoctorRelationships(Integer ClientID) {
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

            String query = "SELECT `iddoctors` FROM " + table
                    + " WHERE `idclients` = " + ClientID;

            stmt.executeQuery(query);
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                cdrlist.add(rs.getInt("iddoctors"));
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

    public List<Integer> GetAllClientRelationships(Integer DoctorID) {
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
                    + " WHERE `iddoctors` = " + DoctorID;

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

    public Integer GetClientDoctorRelationshipId(Integer ClientId, Integer DoctorId) {
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

            String query = "SELECT `idclientDoctorRelationship` FROM " + table
                    + " WHERE `idclients` = " + ClientId
                    + " AND `iddoctors` = " + DoctorId;

            stmt.executeQuery(query);
            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                id = rs.getInt("idclientDoctorRelationship");
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

    public boolean Exists(Integer ClientId, Integer DoctorId) {
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

            String query = "SELECT `idclientDoctorRelationship` FROM " + table
                    + " WHERE `idclients` = " + ClientId
                    + " AND `iddoctors` = " + DoctorId;

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
        String query = "SELECT `clientDoctorRelationship`.`idclientDoctorRelationship`,\n"
                + "    `clientDoctorRelationship`.`idclients`,\n"
                + "    `clientDoctorRelationship`.`iddoctors`\n"
                + "FROM `css`.`clientDoctorRelationship` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, con);
    }
}
