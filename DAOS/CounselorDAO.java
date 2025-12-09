package DAOS;

import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IStructureCheckable;
import DOS.Counselors;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.SQLUtil;
import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static Utility.SQLUtil.createStatement;

/**
 * @date: Mar 13, 2012
 * @author: Keith Maggio
 *
 * @project: CSSLibrary
 * @package: DAOS
 * @file name: CounselorDAO.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc. All Rights Reserved
 */
public class CounselorDAO implements DAOInterface, IStructureCheckable {

    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`counselors`";

    public boolean InsertCounselor(Counselors counselor) throws SQLException {
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
                    + " `number`,"
                    + " `firstName`,"
                    + " `lastName`,"
                    + " `NPI`,"
                    + " `UPIN`,"
                    + " `address1`,"
                    + " `address2`,"
                    + " `city`,"
                    + " `state`,"
                    + " `zip`,"
                    + " `externalId`,"
                    + " `phone`,"
                    + " `fax`,"
                    + " `email`)"
                    + " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement pStmt = con.prepareStatement(stmt);

            pStmt.setInt(1, counselor.getNumber());
            pStmt.setString(2, counselor.getFirstName());
            pStmt.setString(3, counselor.getLastName());
            SQLUtil.SafeSetInteger(pStmt, 4, counselor.getNpi());
            pStmt.setString(5, counselor.getUpin());
            pStmt.setString(6, counselor.getAddress1());
            pStmt.setString(7, counselor.getAddress2());
            pStmt.setString(8, counselor.getCity());
            pStmt.setString(9, counselor.getState());
            pStmt.setString(10, counselor.getZip());
            pStmt.setString(11, counselor.getExternalId());
            pStmt.setString(12, counselor.getPhone());
            pStmt.setString(13, counselor.getFax());
            pStmt.setString(14, counselor.getEmail());

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

    public boolean UpdateCounselor(Counselors counselor) throws SQLException {
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
                    + " `number` = ?,"
                    + " `firstName` = ?,"
                    + " `lastName` = ?,"
                    + " `NPI` = ?,"
                    + " `UPIN` = ?,"
                    + " `address1` = ?,"
                    + " `address2` = ?,"
                    + " `city` = ?,"
                    + " `state` = ?,"
                    + " `zip` = ?,"
                    + " `externalId` = ?,"
                    + " `phone` = ?,"
                    + " `fax` = ?,"
                    + " `email` = ? "
                    + "WHERE `idcounselors` = " + counselor.getIdcounselors();


            PreparedStatement pStmt = con.prepareStatement(stmt);

            pStmt.setInt(1, counselor.getNumber());
            pStmt.setString(2, counselor.getFirstName());
            pStmt.setString(3, counselor.getLastName());
            pStmt.setInt(4, counselor.getNpi());
            pStmt.setString(5, counselor.getUpin());
            pStmt.setString(6, counselor.getAddress1());
            pStmt.setString(7, counselor.getAddress2());
            pStmt.setString(8, counselor.getCity());
            pStmt.setString(9, counselor.getState());
            pStmt.setString(10, counselor.getZip());
            pStmt.setString(11, counselor.getExternalId());
            pStmt.setString(12, counselor.getPhone());
            pStmt.setString(13, counselor.getFax());
            pStmt.setString(14, counselor.getEmail());

            pStmt.executeUpdate();

            pStmt.close();

            return true;
        } catch (Exception ex) {
            String message = ex.toString();
            //TODO:  Add exception handleing
            System.out.println(message);
            return false;
        }
    }

    public Counselors GetCounselor(int CounselorNumber) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        try {
            Counselors counselor = new Counselors();
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + "WHERE `number` = " + CounselorNumber;

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {

                counselor.setIdcounselors(rs.getInt("idcounselors"));
                counselor.setNumber(rs.getInt("number"));
                counselor.setFirstName(rs.getString("firstName"));
                counselor.setLastName(rs.getString("lastName"));
                counselor.setNpi(rs.getInt("NPI"));
                counselor.setUpin(rs.getString("UPIN"));
                counselor.setAddress1(rs.getString("address1"));
                counselor.setAddress2(rs.getString("address2"));
                counselor.setCity(rs.getString("city"));
                counselor.setState(rs.getString("state"));
                counselor.setZip(rs.getString("zip"));
                counselor.setExternalId(rs.getString("externalId"));
                counselor.setPhone(rs.getString("phone"));
                counselor.setFax(rs.getString("fax"));
                counselor.setEmail(rs.getString("email"));

            }

            rs.close();
            stmt.close();

            return counselor;
        } catch (Exception ex) {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }

    public Counselors GetCounselorById(int Id) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        try {
            Counselors counselor = new Counselors();
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + "WHERE `idcounselors` = " + Id;

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {

                counselor.setIdcounselors(rs.getInt("idcounselors"));
                counselor.setNumber(rs.getInt("number"));
                counselor.setFirstName(rs.getString("firstName"));
                counselor.setLastName(rs.getString("lastName"));
                counselor.setNpi(rs.getInt("NPI"));
                counselor.setUpin(rs.getString("UPIN"));
                counselor.setAddress1(rs.getString("address1"));
                counselor.setAddress2(rs.getString("address2"));
                counselor.setCity(rs.getString("city"));
                counselor.setState(rs.getString("state"));
                counselor.setZip(rs.getString("zip"));
                counselor.setExternalId(rs.getString("externalId"));
                counselor.setPhone(rs.getString("phone"));
                counselor.setFax(rs.getString("fax"));
                counselor.setEmail(rs.getString("email"));

            }

            rs.close();
            stmt.close();

            return counselor;
        } catch (Exception ex) {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }

    public boolean CounselorsExists(int CounselorNo) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }

        try {
            Statement stmt = null;
            ResultSet rs = null;
            int rowCount = -1;

            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT COUNT(*) FROM " + table + " WHERE "
                    + "`number` = " + CounselorNo);
            rs.next();
            rowCount = rs.getInt(1);

            rs.close();
            stmt.close();

            if (rowCount > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception ex) {
            //TODO:  Add Exception Handling
            return false;
        }
    }

    public int GetCounselorID(int CounselorNo) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return -1;
        }

        try {
            Statement stmt = null;
            ResultSet rs = null;
            int x = 0;

            stmt = con.createStatement();
            String query = "SELECT `idcounselors` FROM " + table + " "
                    + "WHERE `number` = " + CounselorNo;

            rs = stmt.executeQuery(query);

            if (rs.next()) {
                x = rs.getInt("idcounselors");
            }
            rs.close();
            stmt.close();
            return x;
        } catch (Exception ex) {
            return 0;
        }
    }

    public Integer GetNextAvailableCounselorNumber() {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        try {
            int max = -1;
            Statement stmt = null;
            String query = "SELECT MAX(`number`) AS 'Next' FROM " + table + ";";
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                max = rs.getInt("Next");
            }
            rs.close();
            stmt.close();
            return max;
        } catch (Exception ex) {
            //TODO: Add exception handling
            System.out.println(ex.toString());
            return -1;
        }
    }

    public List<Counselors> SearchCounselorByName(String FirstNameFragment, String LastNameFragment) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        List<Counselors> clist = new ArrayList<>();
        try {
            PreparedStatement stmt = null;
            ResultSet rs = null;
            String whereLName = "";
            String whereFName = "";
            String whereclause = "";
            String firstNameParam = "";
            String lastNameParam = "";
            if (LastNameFragment != null) {
                lastNameParam = SQLUtil.createSearchParam(LastNameFragment);
                whereLName = "WHERE `lastName` LIKE ? ";
            }
            if (FirstNameFragment != null) {
                firstNameParam = SQLUtil.createSearchParam(FirstNameFragment);
                if (whereLName.isEmpty()) {
                    whereFName = "WHERE ";
                } else {
                    whereFName = "AND ";
                }
                whereFName += "`firstName` LIKE ? ";
            }
            whereclause = whereLName + whereFName + ";";
            //stmt = con.createStatement();

            String query = "SELECT * FROM " + table + " "
                    + whereclause;


            if (LastNameFragment != null && FirstNameFragment != null){
                stmt = createStatement(con, query, lastNameParam, firstNameParam);
            }
            else if(LastNameFragment != null){
                stmt = createStatement(con, query, lastNameParam);
            }
            else {
                stmt = createStatement(con, query, firstNameParam);
            }
            
            
            rs = stmt.executeQuery();

            while (rs.next()) {
                Counselors counselor = new Counselors();
                counselor.setIdcounselors(rs.getInt("idcounselors"));
                counselor.setNumber(rs.getInt("number"));
                counselor.setFirstName(rs.getString("firstName"));
                counselor.setLastName(rs.getString("lastName"));
                counselor.setNpi(rs.getInt("NPI"));
                counselor.setUpin(rs.getString("UPIN"));
                counselor.setAddress1(rs.getString("address1"));
                counselor.setAddress2(rs.getString("address2"));
                counselor.setCity(rs.getString("city"));
                counselor.setState(rs.getString("state"));
                counselor.setZip(rs.getString("zip"));
                counselor.setExternalId(rs.getString("externalId"));
                counselor.setPhone(rs.getString("phone"));
                counselor.setFax(rs.getString("externalId"));
                counselor.setEmail(rs.getString("externalId"));
                clist.add(counselor);
            }
            rs.close();
            stmt.close();
            return clist;
        } catch (Exception ex) {
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
        String query = "SELECT `counselors`.`idcounselors`,\n"
                + "    `counselors`.`number`,\n"
                + "    `counselors`.`firstName`,\n"
                + "    `counselors`.`lastName`,\n"
                + "    `counselors`.`NPI`,\n"
                + "    `counselors`.`UPIN`,\n"
                + "    `counselors`.`address1`,\n"
                + "    `counselors`.`address2`,\n"
                + "    `counselors`.`city`,\n"
                + "    `counselors`.`state`,\n"
                + "    `counselors`.`zip`,\n"
                + "    `counselors`.`externalId`,\n"
                + "    `counselors`.`phone`,\n"
                + "    `counselors`.`fax`,\n"
                + "    `counselors`.`email`\n"
                + "FROM `css`.`counselors` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, con);
    }
}
