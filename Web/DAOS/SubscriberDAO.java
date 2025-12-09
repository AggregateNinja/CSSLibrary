package Web.DAOS;

import DAOS.*;
import DAOS.IDAOS.IStructureCheckable;
import DAOS.IDAOS.ISubscriberDAO;
import DOS.Subscriber;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.Convert;
import Utility.SQLUtil;
import java.sql.*;

import static Utility.SQLUtil.createStatement;

/**
 * @date:   Mar 12, 2012
 * @author: CSS Dev
 * 
 * @project: CSSLibrary
 * @package: DAOS
 * @file name: PatientDAO.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc.  All Rights Reserved
 */

public class SubscriberDAO implements ISubscriberDAO, IStructureCheckable
{
    Web.Database.WebDatabaseSingleton dbs = Web.Database.WebDatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    
    private final String table = "`subscriber`";
    
    public boolean InsertSubscriber(Subscriber subscriber) throws SQLException
    {
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
        try
        {
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
                    + " `medicaidNumber`)"
                    + " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            
            PreparedStatement pStmt = con.prepareStatement(stmt);
            
            pStmt.setString(1,subscriber.getArNo());
            pStmt.setString(2,subscriber.getLastName());
            pStmt.setString(3,subscriber.getFirstName());
            pStmt.setString(4,subscriber.getMiddleName());
            pStmt.setString(5,subscriber.getSex());
            pStmt.setString(6,subscriber.getSsn());
            SQLUtil.SafeSetDate(pStmt, 7, subscriber.getDob());
            //pStmt.setTimestamp(7,Convert.ToSQLDateTime(subscriber.getDob()));
            //pStmt.setDate(7,subscriber.getDob());
            pStmt.setString(8,subscriber.getAddressStreet());
            pStmt.setString(9,subscriber.getAddressStreet2());
            pStmt.setString(10,subscriber.getAddressCity());
            pStmt.setString(11,subscriber.getAddressState());
            pStmt.setString(12,subscriber.getAddressZip());
            pStmt.setString(13,subscriber.getPhone());
            pStmt.setString(14,subscriber.getWorkPhone());
            pStmt.setInt(15,subscriber.getInsurance());
            pStmt.setInt(16,subscriber.getSecondaryInsurance());
            pStmt.setString(17,subscriber.getPolicyNumber());
            pStmt.setString(18,subscriber.getGroupNumber());
            pStmt.setString(19,subscriber.getSecondaryPolicyNumber());
            pStmt.setString(20,subscriber.getSecondaryGroupNumber());
            pStmt.setString(21,subscriber.getMedicareNumber());
            pStmt.setString(22,subscriber.getMedicaidNumber());
                         
            pStmt.executeUpdate();
            
            pStmt.close();
            
            return true;
        }catch(Exception ex)
        {
            String message = ex.toString();
            //TODO:  Add logging for failures
            System.out.println(message);
            return false;
        }
    }
    
    public boolean UpdateSubscriber(Subscriber subscriber) throws SQLException
    {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }
                
        try
        {
                       
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
                    + " `medicaidNumber` = ? "
                    + "WHERE `arNo` = " + subscriber.getArNo();
   
            PreparedStatement pStmt = con.prepareStatement(stmt);
            
            pStmt.setString(1,subscriber.getArNo());
            pStmt.setString(2,subscriber.getLastName());
            pStmt.setString(3,subscriber.getFirstName());
            pStmt.setString(4,subscriber.getMiddleName());
            pStmt.setString(5,subscriber.getSex());
            pStmt.setString(6,subscriber.getSsn());
            SQLUtil.SafeSetDate(pStmt, 7, subscriber.getDob());
            //pStmt.setTimestamp(7,Convert.ToSQLDateTime(subscriber.getDob()));
            //pStmt.setString(7,subscriber.getDob());
            pStmt.setString(8,subscriber.getAddressStreet());
            pStmt.setString(9,subscriber.getAddressStreet2());
            pStmt.setString(10,subscriber.getAddressCity());
            pStmt.setString(11,subscriber.getAddressState());
            pStmt.setString(12,subscriber.getAddressZip());
            pStmt.setString(13,subscriber.getPhone());
            pStmt.setString(14,subscriber.getWorkPhone());
            pStmt.setInt(15,subscriber.getInsurance());
            pStmt.setInt(16,subscriber.getSecondaryInsurance());
            pStmt.setString(17,subscriber.getPolicyNumber());
            pStmt.setString(18,subscriber.getGroupNumber());
            pStmt.setString(19,subscriber.getSecondaryPolicyNumber());
            pStmt.setString(20,subscriber.getSecondaryGroupNumber());
            pStmt.setString(21,subscriber.getMedicareNumber());
            pStmt.setString(22,subscriber.getMedicaidNumber());
              
            pStmt.executeUpdate();
            
            pStmt.close();
            
            return true;
        }catch(Exception ex)
        {
            String message = ex.toString();
            //TODO:  Add exception handleing
            System.out.println(message);
            return false;
        }
    }
    
    public Subscriber GetSubscriber(String MasterNumber) throws SQLException
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
            Subscriber subscriber = new Subscriber();
            PreparedStatement stmt  = null; //con.createStatement();
            
            String query = "SELECT * FROM " + table
                    + "WHERE `arNo` = ?";
            
            stmt = createStatement(con, query, MasterNumber);
            ResultSet rs = stmt.executeQuery();
            
            if(rs.next())
            {
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

              
                
            }
            
            rs.close();
            stmt.close();
            
            return subscriber;
        }
        catch(Exception ex)
        {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return null;
        }    
    }
    
    public Subscriber GetSubscriberById(int Id) throws SQLException
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
            Subscriber subscriber = new Subscriber();
            Statement stmt  = con.createStatement();
            
            String query = "SELECT * FROM " + table
                    + "WHERE `idSubscriber` = " + Id;
            
            ResultSet rs = stmt.executeQuery(query);
            
            if(rs.next())
            {
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

              
                
            }
            
            rs.close();
            stmt.close();
            
            return subscriber;
        }
        catch(Exception ex)
        {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return null;
        }    
    }
    
    public int GetLastInsertedID() throws SQLException
    {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return -1;
        }
                
        int id = 0;
        
        try
        {
            Statement stmt = con.createStatement();
            
            String query = "SELECT LAST_INSERT_ID();";
            
            ResultSet rs = stmt.executeQuery(query);
            
            if(rs.next())
            {
                id = rs.getInt(1);
            }
            
            rs.close();
            stmt.close();
            
            return id;
        }
        catch(Exception ex)
        {
            System.out.println(ex.toString());
            return 0;
        }
    }
    
    //Only to be used during bulk import
    public int GetSubscriberIdByAR(String ar)
    {
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
        try
        {
            PreparedStatement stmt = null; //con.createStatement();
            
            String query = "SELECT `idSubscriber` FROM "
                    + table +
                    "WHERE `arNo` = ?";
            
            stmt = createStatement(con, query, ar);
            ResultSet rs = stmt.executeQuery();
            
            if(rs.next())
            {
                ids = rs.getString(1);
            }
            
            if(ids.isEmpty() == false)
                id = Integer.parseInt(ids);
            
            rs.close();
            stmt.close();
            
            return id;
        }catch(Exception ex)
        {
            System.out.println(ex.toString());
            return 0;
        }
    }
    
    public boolean SubscriberExists(String ar)
    {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }
                
        try
        {
            PreparedStatement stmt = null; //con.createStatement();
            int rowCount = -1;
            
            String query = "Select COUNT(*) FROM " + table
                    + " WHERE `arNo` = ?";
            
            stmt = createStatement(con, query, ar);
            ResultSet rs = stmt.executeQuery();
            
            rs.next();
            rowCount = rs.getInt(1);
            
            rs.close();
            stmt.close();
            
            if (rowCount > 0)
                return true;
            else
                return false;
        }catch(Exception ex)
        {
            return false;
        }
    }
    
    public ResultSet GetResultSetByQuery(String Select, String Where)
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
            int rowCount = -1;
            
            String query = Select + " FROM " + table
                    + Where;
            
            ResultSet rs = stmt.executeQuery(query);
            
            return rs;
        }catch(Exception ex)
        {
            return null;
        }
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
                + "    `subscriber`.`medicaidNumber`\n"
                + "FROM `cssweb`.`subscriber` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, con);
    }
}
