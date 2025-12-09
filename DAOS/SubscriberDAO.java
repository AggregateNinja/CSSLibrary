package DAOS;

import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IStructureCheckable;
import DAOS.IDAOS.ISubscriberDAO;
import DOS.Subscriber;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.Convert;
import Utility.SQLUtil;
import static Utility.SQLUtil.createStatement;
import java.io.Serializable;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.util.ArrayList;

/**
 * @date: Mar 12, 2012
 * @author: CSS Dev
 *
 * @project: CSSLibrary
 * @package: DAOS
 * @file name: PatientDAO.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc. All Rights Reserved
 */
public class SubscriberDAO implements ISubscriberDAO, DAOInterface, IStructureCheckable {

    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`subscriber`";

    public boolean InsertSubscriber(Subscriber subscriber) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }

        /*if(GetSubscriberIdByAR(Integer.parseInt(subscriber.getArNo())) != 0)
         {
         return UpdateSubscriber(subscriber);
         }*/
        try {
            String stmt = "INSERT INTO " + table + "("
                    + " `arNo`,"
                    + " `lastName`,"
                    + " `firstName`,"
                    + " `middleName`,"
                    + " `sex`,"
                    + " `ssn`,"
                    + " `dob`,"
                    + " `addressStreet`,"
                    + " `addressStreet2`,"
                    + " `addressCity`,"
                    + " `addressState`,"
                    + " `addressZip`,"
                    + " `phone`,"
                    + " `workPhone`,"
                    + " `insurance`,"
                    + " `secondaryInsurance`,"
                    + " `policyNumber`,"
                    + " `groupNumber`,"
                    + " `secondaryPolicyNumber`,"
                    + " `secondaryGroupNumber`,"
                    + " `medicareNumber`,"
                    + " `medicaidNumber`,"
                    + " `active`,"
                    + " `deactivatedDate`)"
                    + " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement pStmt = con.prepareStatement(stmt);

            pStmt.setString(1, subscriber.getArNo());
            pStmt.setString(2, subscriber.getLastName());
            pStmt.setString(3, subscriber.getFirstName());
            pStmt.setString(4, subscriber.getMiddleName());
            pStmt.setString(5, subscriber.getSex());
            pStmt.setString(6, subscriber.getSsn());
            SQLUtil.SafeSetDate(pStmt, 7, subscriber.getDob());
            //pStmt.setTimestamp(7,Convert.ToSQLDateTime(subscriber.getDob()));
            //pStmt.setDate(7,subscriber.getDob());
            pStmt.setString(8, subscriber.getAddressStreet());
            pStmt.setString(9, subscriber.getAddressStreet2());
            pStmt.setString(10, subscriber.getAddressCity());
            pStmt.setString(11, subscriber.getAddressState());
            pStmt.setString(12, subscriber.getAddressZip());
            pStmt.setString(13, subscriber.getPhone());
            pStmt.setString(14, subscriber.getWorkPhone());
            pStmt.setInt(15, subscriber.getInsurance());
            SQLUtil.SafeSetInteger(pStmt, 16, subscriber.getSecondaryInsurance());
            pStmt.setString(17, subscriber.getPolicyNumber());
            pStmt.setString(18, subscriber.getGroupNumber());
            pStmt.setString(19, subscriber.getSecondaryPolicyNumber());
            pStmt.setString(20, subscriber.getSecondaryGroupNumber());
            pStmt.setString(21, subscriber.getMedicareNumber());
            pStmt.setString(22, subscriber.getMedicaidNumber());
            pStmt.setBoolean(23, subscriber.getActive());
            SQLUtil.SafeSetDate(pStmt, 24, subscriber.getDeactivatedDate());
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
    
    /**
     * Inserts the provided subscriber and returns the generated unique identifier.
     * @param subscriber
     * @return
     * @throws SQLException 
     */
    public Integer InsertSubscriberGetId(Subscriber subscriber) throws SQLException, IllegalArgumentException
    {
        // If we get a null subscriber, or if we have an attempt to insert
        // a subscriber with a unique database identifier (should have been an update)
        if (subscriber == null || (subscriber.getIdSubscriber() != null && subscriber.getIdSubscriber() > 0))
        {
            throw new IllegalArgumentException("SubscriberDAO:InsertSubscriberGetId: Received a NULL subcriber, or a subscriber that already has a unique identifier");
        }
        
        if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);
            
        String stmt = "INSERT INTO " + table + "("
                + " `arNo`,"
                + " `lastName`,"
                + " `firstName`,"
                + " `middleName`,"
                + " `sex`,"
                + " `ssn`,"
                + " `dob`,"
                + " `addressStreet`,"
                + " `addressStreet2`,"
                + " `addressCity`,"
                + " `addressState`,"
                + " `addressZip`,"
                + " `phone`,"
                + " `workPhone`,"
                + " `insurance`,"
                + " `secondaryInsurance`,"
                + " `policyNumber`,"
                + " `groupNumber`,"
                + " `secondaryPolicyNumber`,"
                + " `secondaryGroupNumber`,"
                + " `medicareNumber`,"
                + " `medicaidNumber`,"
                + " `active`,"
                + " `deactivatedDate`)"
                + " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pStmt = con.prepareStatement(stmt, Statement.RETURN_GENERATED_KEYS);

        pStmt.setString(1, subscriber.getArNo());
        pStmt.setString(2, subscriber.getLastName());
        pStmt.setString(3, subscriber.getFirstName());
        pStmt.setString(4, subscriber.getMiddleName());
        pStmt.setString(5, subscriber.getSex());
        pStmt.setString(6, subscriber.getSsn());
        SQLUtil.SafeSetDate(pStmt, 7, subscriber.getDob());
        pStmt.setString(8, subscriber.getAddressStreet());
        pStmt.setString(9, subscriber.getAddressStreet2());
        pStmt.setString(10, subscriber.getAddressCity());
        pStmt.setString(11, subscriber.getAddressState());
        pStmt.setString(12, subscriber.getAddressZip());
        pStmt.setString(13, subscriber.getPhone());
        pStmt.setString(14, subscriber.getWorkPhone());
        pStmt.setInt(15, subscriber.getInsurance());
        SQLUtil.SafeSetInteger(pStmt, 16, subscriber.getSecondaryInsurance());
        pStmt.setString(17, subscriber.getPolicyNumber());
        pStmt.setString(18, subscriber.getGroupNumber());
        pStmt.setString(19, subscriber.getSecondaryPolicyNumber());
        pStmt.setString(20, subscriber.getSecondaryGroupNumber());
        pStmt.setString(21, subscriber.getMedicareNumber());
        pStmt.setString(22, subscriber.getMedicaidNumber());
        pStmt.setBoolean(23, subscriber.getActive());
        SQLUtil.SafeSetDate(pStmt, 24, subscriber.getDeactivatedDate());

        int affectedRows = pStmt.executeUpdate();

        if (affectedRows == 0)
        {
            pStmt.close();
            throw new SQLException("SubscriberDAO::InsertSubscriberGetId: " + pStmt.toString() + " Insert failed");
        }

        Integer newSubscriberId;
        try (ResultSet generatedKeys = pStmt.getGeneratedKeys())
        {
            if (generatedKeys.next())
            {
                newSubscriberId = generatedKeys.getInt(1);
            }
            else
            {
                pStmt.close();
                throw new SQLException("SubscriberDAO::InsertSubscriberGetId: " + pStmt.toString() + " Insert failed");
            }
        }
        pStmt.close();
        return newSubscriberId;
    }

    public boolean UpdateSubscriber(Subscriber subscriber) throws SQLException {
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
                    + " `arNo` = ?,"
                    + " `lastName` = ?,"
                    + " `firstName` = ?,"
                    + " `middleName` = ?,"
                    + " `sex` = ?,"
                    + " `ssn` = ?,"
                    + " `dob` = ?,"
                    + " `addressStreet` = ?,"
                    + " `addressStreet2` = ?,"
                    + " `addressCity` = ?,"
                    + " `addressState` = ?,"
                    + " `addressZip` = ?,"
                    + " `phone` = ?,"
                    + " `workPhone` = ?,"
                    + " `insurance` = ?,"
                    + " `secondaryInsurance` = ?,"
                    + " `policyNumber` = ?,"
                    + " `groupNumber` = ?,"
                    + " `secondaryPolicyNumber` = ?,"
                    + " `secondaryGroupNumber` = ?,"
                    + " `medicareNumber` = ?,"
                    + " `medicaidNumber` = ?,"
                    + " `active` = ?,"
                    + " `deactivatedDate` = ? "
                    //+ "WHERE `arNo` = " + subscriber.getArNo();
                    + " WHERE `idsubscriber` = " + subscriber.getIdSubscriber();

            PreparedStatement pStmt = con.prepareStatement(stmt);

            pStmt.setString(1, subscriber.getArNo());
            pStmt.setString(2, subscriber.getLastName());
            pStmt.setString(3, subscriber.getFirstName());
            pStmt.setString(4, subscriber.getMiddleName());
            pStmt.setString(5, subscriber.getSex());
            pStmt.setString(6, subscriber.getSsn());
            SQLUtil.SafeSetDate(pStmt, 7, subscriber.getDob());
            //pStmt.setTimestamp(7,Convert.ToSQLDateTime(subscriber.getDob()));
            //pStmt.setString(7,subscriber.getDob());
            pStmt.setString(8, subscriber.getAddressStreet());
            pStmt.setString(9, subscriber.getAddressStreet2());
            pStmt.setString(10, subscriber.getAddressCity());
            pStmt.setString(11, subscriber.getAddressState());
            pStmt.setString(12, subscriber.getAddressZip());
            pStmt.setString(13, subscriber.getPhone());
            pStmt.setString(14, subscriber.getWorkPhone());
            pStmt.setInt(15, subscriber.getInsurance());
            SQLUtil.SafeSetInteger(pStmt, 16, subscriber.getSecondaryInsurance());
            pStmt.setString(17, subscriber.getPolicyNumber());
            pStmt.setString(18, subscriber.getGroupNumber());
            pStmt.setString(19, subscriber.getSecondaryPolicyNumber());
            pStmt.setString(20, subscriber.getSecondaryGroupNumber());
            pStmt.setString(21, subscriber.getMedicareNumber());
            pStmt.setString(22, subscriber.getMedicaidNumber());
            pStmt.setBoolean(23, subscriber.getActive());
            SQLUtil.SafeSetDate(pStmt, 24, subscriber.getDeactivatedDate());
            pStmt.executeUpdate();

            pStmt.close();

            return true;
        } catch (Exception ex) {
            String message = "SubscriberDAO::UpdateSubscriber error - " + ex.toString();
            //TODO:  Add exception handleing
            System.out.println(message);
            return false;
        }
    }
    
    @Override
    public Subscriber GetSubscriber(String MasterNumber) throws SQLException
    {
        return GetSubscriber(MasterNumber, true);
    }
    
    @Override
    public int GetSubscriberIdByAR(String ar)
    {
        return GetSubscriberIdByAR(ar, true);
    }
    
    @Override
    public boolean SubscriberExists(String ar)
    {
        return SubscriberExists(ar, true);
    }

    public Subscriber GetSubscriber(String MasterNumber, boolean IsActive) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        try {
            Subscriber subscriber = new Subscriber();
            PreparedStatement stmt = null; //con.createStatement();

            String query = "SELECT * FROM " + table
                    + " WHERE `arNo` = ?"
                    + " AND `active` = " + (IsActive?1:0);

            stmt = createStatement(con, query, MasterNumber);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                subscriber.setIdSubscriber(rs.getInt("idSubscriber"));
                subscriber.setArNo(rs.getString("arNo"));
                subscriber.setLastName(rs.getString("lastName"));
                subscriber.setFirstName(rs.getString("firstName"));
                subscriber.setMiddleName(rs.getString("middleName"));
                subscriber.setSex(rs.getString("sex"));
                subscriber.setSsn(rs.getString("ssn"));
                subscriber.setDob(rs.getDate("dob"));
                subscriber.setAddressStreet(rs.getString("addressStreet"));
                subscriber.setAddressStreet2(rs.getString("addressStreet2"));
                subscriber.setAddressCity(rs.getString("addressCity"));
                subscriber.setAddressState(rs.getString("addressState"));
                subscriber.setAddressZip(rs.getString("addressZip"));
                subscriber.setPhone(rs.getString("phone"));
                subscriber.setWorkPhone(rs.getString("workPhone"));
                subscriber.setInsurance(rs.getInt("insurance"));
                subscriber.setSecondaryInsurance(rs.getInt("secondaryInsurance"));
                subscriber.setPolicyNumber(rs.getString("policyNumber"));
                subscriber.setGroupNumber(rs.getString("groupNumber"));
                subscriber.setSecondaryPolicyNumber(rs.getString("secondaryPolicyNumber"));
                subscriber.setSecondaryGroupNumber(rs.getString("secondaryGroupNumber"));
                subscriber.setMedicareNumber(rs.getString("medicareNumber"));
                subscriber.setMedicaidNumber(rs.getString("medicaidNumber"));
                subscriber.setActive(rs.getBoolean("active"));
                subscriber.setDeactivatedDate(rs.getDate("deactivatedDate"));

            }

            rs.close();
            stmt.close();

            return subscriber;
        } catch (Exception ex) {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }

    @Override
    public Subscriber GetSubscriberById(int Id) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        try {
            Subscriber subscriber = new Subscriber();
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + " WHERE `idSubscriber` = " + Id;

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                subscriber.setIdSubscriber(rs.getInt("idSubscriber"));
                subscriber.setArNo(rs.getString("arNo"));
                subscriber.setLastName(rs.getString("lastName"));
                subscriber.setFirstName(rs.getString("firstName"));
                subscriber.setMiddleName(rs.getString("middleName"));
                subscriber.setSex(rs.getString("sex"));
                subscriber.setSsn(rs.getString("ssn"));
                subscriber.setDob(rs.getDate("dob"));
                subscriber.setAddressStreet(rs.getString("addressStreet"));
                subscriber.setAddressStreet2(rs.getString("addressStreet2"));
                subscriber.setAddressCity(rs.getString("addressCity"));
                subscriber.setAddressState(rs.getString("addressState"));
                subscriber.setAddressZip(rs.getString("addressZip"));
                subscriber.setPhone(rs.getString("phone"));
                subscriber.setWorkPhone(rs.getString("workPhone"));
                subscriber.setInsurance(rs.getInt("insurance"));
                subscriber.setSecondaryInsurance(rs.getInt("secondaryInsurance"));
                subscriber.setPolicyNumber(rs.getString("policyNumber"));
                subscriber.setGroupNumber(rs.getString("groupNumber"));
                subscriber.setSecondaryPolicyNumber(rs.getString("secondaryPolicyNumber"));
                subscriber.setSecondaryGroupNumber(rs.getString("secondaryGroupNumber"));
                subscriber.setMedicareNumber(rs.getString("medicareNumber"));
                subscriber.setMedicaidNumber(rs.getString("medicaidNumber"));
                subscriber.setActive(rs.getBoolean("active"));
                subscriber.setDeactivatedDate(rs.getDate("deactivatedDate"));

            }

            rs.close();
            stmt.close();

            return subscriber;
        } catch (Exception ex) {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }

    @Override
    public int GetLastInsertedID() throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return -1;
        }

        int id = 0;

        try {
            Statement stmt = con.createStatement();

            String query = "SELECT LAST_INSERT_ID();";

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                id = rs.getInt(1);
            }

            rs.close();
            stmt.close();

            return id;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return 0;
        }
    }

    //Only to be used during bulk import
    public int GetSubscriberIdByAR(String ar, boolean IsActive) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return -1;
        }

        int id = 0;
        String ids = "";
        try {
            PreparedStatement stmt = null; //con.createStatement();

            String query = "SELECT `idSubscriber` FROM "
                    + table
                    + " WHERE `arNo` = ?"
                    + " AND `active` = " + (IsActive?1:0);

            stmt = createStatement(con, query, ar);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                ids = rs.getString(1);
            }

            if (ids.isEmpty() == false) {
                id = Integer.parseInt(ids);
            }

            rs.close();
            stmt.close();

            return id;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return 0;
        }
    }

    public boolean SubscriberExists(String ar, boolean IsActive) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }

        try {
            PreparedStatement stmt = null; //con.createStatement();
            int rowCount = -1;

            String query = "Select COUNT(*) FROM " + table
                    + " WHERE `arNo` = ?"
                    + " AND `active` = " + (IsActive?1:0);

            stmt = createStatement(con, query, ar);
            ResultSet rs = stmt.executeQuery();

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
            return false;
        }
    }

    @Override
    public ResultSet GetResultSetByQuery(String Select, String Where) {
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
            int rowCount = -1;

            String query = Select + " FROM " + table
                    + Where;

            ResultSet rs = stmt.executeQuery(query);

            return rs;
        } catch (Exception ex) {
            return null;
        }
    }
    
    public ResultSet SearchLastName(String lastNameFragment, boolean activeOnly)
            throws SQLException
    {
        if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);
        if (lastNameFragment == null) lastNameFragment = "";
        lastNameFragment = lastNameFragment.replaceAll("%", "") + '%';
        String sql = "SELECT * FROM " + table + " WHERE `lastName` LIKE ?";
        if (activeOnly) sql += " AND `active` = 1";
        sql += " ORDER BY `lastName` ASC, `firstName` ASC";
        PreparedStatement pStmt = con.prepareStatement(sql);
        pStmt.setString(1, lastNameFragment);
        ResultSet rs = pStmt.executeQuery();
        return rs;
    }
    
    public ResultSet SearchFirstName(String firstNameFragment, boolean activeOnly)
            throws SQLException
    {
        if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);
        if (firstNameFragment == null) firstNameFragment = "";
        firstNameFragment = firstNameFragment.replaceAll("%", "") + '%';
        String sql = "SELECT * FROM " + table + " WHERE `firstName` LIKE ?";
        if (activeOnly) sql += " AND `active` = 1";
        sql += " ORDER BY `lastName` ASC, `firstName` ASC";
        PreparedStatement pStmt = con.prepareStatement(sql);
        pStmt.setString(1, firstNameFragment);
        ResultSet rs = pStmt.executeQuery();
        return rs;
    }
    
    public ResultSet SearchFullName(String lastNameFragment, String firstNameFragment, boolean activeOnly)
            throws SQLException
    {
        if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);
        if (firstNameFragment == null) firstNameFragment = "";
        firstNameFragment = firstNameFragment.replaceAll("%", "") + '%';
        if (lastNameFragment == null) lastNameFragment = "";
        lastNameFragment = lastNameFragment.replaceAll("%", "") + '%';
        
        String sql = "SELECT * FROM " + table + " WHERE `firstName` LIKE ? AND `lastName` LIKE ?";
        if (activeOnly) sql += " AND `active` = 1";
        sql += " ORDER BY `lastName` ASC, `firstName` ASC";
        PreparedStatement pStmt = con.prepareStatement(sql);
        pStmt.setString(1, firstNameFragment);
        pStmt.setString(2, lastNameFragment);
        ResultSet rs = pStmt.executeQuery();
        return rs;        
    }    
    
    public java.sql.Date DeactivateByID(int ID) 
    {
        try {
            if (con.isClosed()) {
                CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
        }
        
        try
        {
            java.util.Date uDate = new java.util.Date();
            java.sql.Date sDate = Convert.ToSQLDate(uDate);
            
            String stmt = "UPDATE " + table + "SET "
                    + "`active` = ?, "
                    + "`deactivatedDate` = ? "
                    + "WHERE `active` = ? "
                    + "AND `idSubscriber` = " + ID;
            
            PreparedStatement pStmt = con.prepareStatement(stmt);
            
            pStmt.setBoolean(1, false);
            pStmt.setDate(2, sDate);
            pStmt.setBoolean(3, true);
            
            pStmt.executeUpdate();
            
            pStmt.close();
            
            return sDate;
            
        }catch(Exception ex){
            System.out.println(ex.toString());
            return null;
        }
    }

    @Override
    public Boolean Insert(Serializable obj)
    {
        try
        {
            return InsertSubscriber((Subscriber)obj);
        }
        catch (SQLException ex)
        {
            Logger.getLogger(SubscriberDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public Boolean Update(Serializable obj)
    {
        try
        {
            return UpdateSubscriber((Subscriber)obj);
        }
        catch (SQLException ex)
        {
            Logger.getLogger(SubscriberDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public Boolean Delete(Serializable obj)
    {
        int ID = ((Subscriber)obj).getIdSubscriber();
        return (DeactivateByID(ID) != null ? true : null);
    }

    @Override
    public Serializable getByID(Integer ID)
    {
        try
        {
            return GetSubscriberById(ID);
        }
        catch (SQLException ex)
        {
            Logger.getLogger(SubscriberDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public Long[] GetAllArNos() {
        
        ArrayList<Long> arNos = new ArrayList<>();
        
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        
        try {
            
            PreparedStatement stmt = null;

            String query = "SELECT DISTINCT CAST(s.arNo AS UNSIGNED) AS `NumericArNo` " +
                "FROM " + table + " s " +
                "WHERE CAST(s.arNo AS UNSIGNED) <= 9223372036854775807 " +
                "ORDER BY NumericArNo ASC";

            stmt = createStatement(con, query);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                arNos.add(rs.getLong("NumericArNo"));
            }
            
            rs.close();
            stmt.close();
            
        } catch (SQLException ex) {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
        
        int arCount = arNos.size();
        Object[] tmpArNos = arNos.toArray();
        Long[] aryArNos = new Long[arCount];
        System.arraycopy(tmpArNos, 0, aryArNos, 0, arCount);
        
        return aryArNos;
    }
    
    @Override
    public String structureCheck() {
        String query = "SELECT `subscriber`.`idSubscriber`,\n"
                + "    `subscriber`.`arNo`,\n"
                + "    `subscriber`.`lastName`,\n"
                + "    `subscriber`.`firstName`,\n"
                + "    `subscriber`.`middleName`,\n"
                + "    `subscriber`.`sex`,\n"
                + "    `subscriber`.`ssn`,\n"
                + "    `subscriber`.`dob`,\n"
                + "    `subscriber`.`addressStreet`,\n"
                + "    `subscriber`.`addressStreet2`,\n"
                + "    `subscriber`.`addressCity`,\n"
                + "    `subscriber`.`addressState`,\n"
                + "    `subscriber`.`addressZip`,\n"
                + "    `subscriber`.`phone`,\n"
                + "    `subscriber`.`workPhone`,\n"
                + "    `subscriber`.`insurance`,\n"
                + "    `subscriber`.`secondaryInsurance`,\n"
                + "    `subscriber`.`policyNumber`,\n"
                + "    `subscriber`.`groupNumber`,\n"
                + "    `subscriber`.`secondaryPolicyNumber`,\n"
                + "    `subscriber`.`secondaryGroupNumber`,\n"
                + "    `subscriber`.`medicareNumber`,\n"
                + "    `subscriber`.`medicaidNumber`,\n"
                + "    `subscriber`.`active`,\n"
                + "    `subscriber`.`deactivatedDate`\n"
                + "FROM `css`.`subscriber` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, con);
    }
}
