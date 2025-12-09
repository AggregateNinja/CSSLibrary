package Web.DAOS;

/**
 * @date:   Mar 12, 2012
 * @author: Keith Maggio
 * 
 * @project: CSSLibrary 
 * @package: DAOS
 * @file name: OrderDAO.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc.  All Rights Reserved
 */

import DAOS.IDAOS.IOrderDAO;
import DAOS.IDAOS.IStructureCheckable;
import DOS.Orders;
import Utility.Convert;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import DAOS.PatientDAO;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.SQLUtil;

import static Utility.SQLUtil.createStatement;

public class OrderDAO implements IOrderDAO, IStructureCheckable
{
    Web.Database.WebDatabaseSingleton dbs = Web.Database.WebDatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    
    private final String table = "`orders`";
    
    /**
     * All fields except idOrders
     */
    private final ArrayList<String> fields = new ArrayList<String>();
    
    public OrderDAO()
    {
        fields.add("accession");
        fields.add("clientId");
        fields.add("locationId");
        fields.add("doctorId");
        fields.add("orderDate");
        fields.add("specimenDate");
        fields.add("patientId");
        fields.add("subscriberId");
        fields.add("isAdvancedOrder");
        fields.add("room");
        fields.add("bed");
        fields.add("insurance");
        fields.add("secondaryInsurance");
        fields.add("policyNumber");
        fields.add("groupNumber");
        fields.add("secondaryPolicyNumber");
        fields.add("secondaryGroupNumber");
        fields.add("medicareNumber");
        fields.add("medicaidNumber");
        fields.add("reportType");
        fields.add("requisition");
        fields.add("billOnly");
        fields.add("active");
        fields.add("hold");
        fields.add("stage");
        fields.add("holdComment");
        fields.add("resultComment");
        fields.add("internalComment");
        fields.add("hl7Transmitted");
        fields.add("payment");
        fields.add("billable");
        fields.add("emrOrderId");
    }
    
    @Override
    public boolean InsertOrder(Orders order) throws SQLException
    {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }
                
        String stmt = GenerateInsertStatement(fields);

        PreparedStatement pStmt = con.prepareStatement(stmt);
            
        try
        {
            /*pStmt.setInt(1,remark.getRemarkNo());
            pStmt.setInt(2,remark.getRemarkName());
            pStmt.setString(3,remark.getRemarkAbbr());
            pStmt.setInt(4,remark.getRemarkType());
            pStmt.setBytes(5,remark.getRemarkText());
            pStmt.setInt(6,remark.getIsAbnormal());
            pStmt.setInt(7,remark.getRemarkDepartment());
            pStmt.setInt(8,remark.getNoCharge());*/
            
        
            pStmt.setString(1, order.getAccession());
            pStmt.setInt(2, order.getClientId());
            pStmt.setInt(3, order.getLocationId());
            SQLUtil.SafeSetInteger(pStmt, 4, order.getDoctorId());
            pStmt.setTimestamp(5, Convert.ToSQLDateTime(order.getOrderDate()));
            pStmt.setTimestamp(6, Convert.ToSQLDateTime(order.getSpecimenDate()));
            pStmt.setInt(7, order.getPatientId());
            SQLUtil.SafeSetInteger(pStmt, 8, order.getSubscriberId());
            pStmt.setBoolean(9, order.getIsAdvancedOrder());
            SQLUtil.SafeSetString(pStmt, 10, order.getRoom());
            SQLUtil.SafeSetString(pStmt, 11, order.getBed());
            SQLUtil.SafeSetInteger(pStmt, 12, order.getInsurance());
            SQLUtil.SafeSetInteger(pStmt, 13, order.getSecondaryInsurance());
            SQLUtil.SafeSetString(pStmt, 14, order.getPolicyNumber());
            SQLUtil.SafeSetString(pStmt, 15, order.getGroupNumber());
            SQLUtil.SafeSetString(pStmt, 16, order.getSecondaryPolicyNumber());
            SQLUtil.SafeSetString(pStmt, 17, order.getSecondaryGroupNumber());
            SQLUtil.SafeSetString(pStmt, 18, order.getMedicareNumber());
            SQLUtil.SafeSetString(pStmt, 19, order.getMedicaidNumber());
            SQLUtil.SafeSetInteger(pStmt, 20, order.getReportType());
            SQLUtil.SafeSetInteger(pStmt, 21, order.getRequisition());
            SQLUtil.SafeSetBoolean(pStmt, 22, order.isBillOnly());
            SQLUtil.SafeSetBoolean(pStmt, 23, order.isActive());
            SQLUtil.SafeSetBoolean(pStmt, 24, order.isHold());
            SQLUtil.SafeSetInteger(pStmt, 25, order.getStage());
            SQLUtil.SafeSetString(pStmt, 26, order.getHoldComment());
            SQLUtil.SafeSetString(pStmt, 27, order.getResultComment());
            SQLUtil.SafeSetString(pStmt, 28, order.getInternalComment());
            pStmt.setBoolean(29, order.getHl7Transmitted());
            SQLUtil.SafeSetDouble(pStmt, 30, order.getPayment());
            SQLUtil.SafeSetBoolean(pStmt, 31, order.getBillable());
            SQLUtil.SafeSetString(pStmt, 32, order.getEmrOrderId());
            
            pStmt.executeUpdate();
            
            pStmt.close();
            
            return true;
        }catch(Exception ex)
        {
            String message = ex.toString();
            //TODO:  Add logging for failures
            System.out.println(message);
            System.out.println(pStmt.toString());
            return false;
        }
    }
    
    @Override
    public boolean UpdateOrder(Orders order) throws SQLException
    {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }
                
        String stmt = GenerateUpdateStatement(fields)
                + " WHERE `idOrders` = " + order.getIdOrders();

        PreparedStatement pStmt = con.prepareStatement(stmt);
            
        try
        {
            pStmt.setString(1, order.getAccession());
            pStmt.setInt(2, order.getClientId());
            pStmt.setInt(3, order.getLocationId());
            SQLUtil.SafeSetInteger(pStmt, 4, order.getDoctorId());
            pStmt.setTimestamp(5, Convert.ToSQLDateTime(order.getOrderDate()));
            pStmt.setTimestamp(6, Convert.ToSQLDateTime(order.getSpecimenDate()));
            pStmt.setInt(7, order.getPatientId());
            SQLUtil.SafeSetInteger(pStmt, 8, order.getSubscriberId());
            pStmt.setBoolean(9, order.getIsAdvancedOrder());
            SQLUtil.SafeSetString(pStmt, 10, order.getRoom());
            SQLUtil.SafeSetString(pStmt, 11, order.getBed());
            SQLUtil.SafeSetInteger(pStmt, 12, order.getInsurance());
            SQLUtil.SafeSetInteger(pStmt, 13, order.getSecondaryInsurance());
            SQLUtil.SafeSetString(pStmt, 14, order.getPolicyNumber());
            SQLUtil.SafeSetString(pStmt, 15, order.getGroupNumber());
            SQLUtil.SafeSetString(pStmt, 16, order.getSecondaryPolicyNumber());
            SQLUtil.SafeSetString(pStmt, 17, order.getSecondaryGroupNumber());
            SQLUtil.SafeSetString(pStmt, 18, order.getMedicareNumber());
            SQLUtil.SafeSetString(pStmt, 19, order.getMedicaidNumber());
            SQLUtil.SafeSetInteger(pStmt, 20, order.getReportType());
            SQLUtil.SafeSetInteger(pStmt, 21, order.getRequisition());
            SQLUtil.SafeSetBoolean(pStmt, 22, order.isBillOnly());
            SQLUtil.SafeSetBoolean(pStmt, 23, order.isActive());
            SQLUtil.SafeSetBoolean(pStmt, 24, order.isHold());
            SQLUtil.SafeSetInteger(pStmt, 25, order.getStage());
            SQLUtil.SafeSetString(pStmt, 26, order.getHoldComment());
            SQLUtil.SafeSetString(pStmt, 27, order.getResultComment());
            SQLUtil.SafeSetString(pStmt, 28, order.getInternalComment());
            pStmt.setBoolean(29, order.getHl7Transmitted());
            SQLUtil.SafeSetDouble(pStmt, 30, order.getPayment());
            SQLUtil.SafeSetBoolean(pStmt, 31, order.getBillable());
            SQLUtil.SafeSetString(pStmt, 32, order.getEmrOrderId());
            
            pStmt.executeUpdate();
            
            pStmt.close();
            
            return true;
        }catch(Exception ex)
        {
            String message = ex.toString();
            //TODO:  Add exception handleing
            System.out.println(message);
            System.out.println(pStmt.toString());
            return false;
        }
    }
    
    @Override
    public Orders GetOrder(String AccessionNumber, int... locationIds) throws SQLException
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
            Orders order = new Orders();
            PreparedStatement stmt  = null; //con.createStatement();
            
            String query = "SELECT * FROM " + table
                    + "WHERE `accession` = ?";
            if (locationIds.length > 0)
            {
                query += " AND `locationId` IN (";
                for (int locationId : locationIds)
                {
                    query += locationId + ",";
                }
                query = query.substring(0, query.length()-1);
                query += ")";
            }
            
            stmt = createStatement(con, query, AccessionNumber);
            ResultSet rs = stmt.executeQuery();
            
            if(rs.next())
            {
                setOrderFromResultSet(order, rs);
            }
            
            rs.close();
            stmt.close();
            
            return order;
        }
        catch(Exception ex)
        {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return null;
        }    
    }
    
    @Override
    public Orders GetOrder(String AccessionNumber) throws SQLException
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
            Orders order = new Orders();
            PreparedStatement stmt  = null; //con.createStatement();
            
            String query = "SELECT * FROM " + table
                    + "WHERE `accession` = ?";
            
            stmt = createStatement(con, query, AccessionNumber);
            ResultSet rs = stmt.executeQuery();
            
            if(rs.next())
            {
                setOrderFromResultSet(order, rs);
            }
            
            rs.close();
            stmt.close();
            
            return order;
        }
        catch(Exception ex)
        {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return null;
        }    
    }
    
    @Override
    public Orders GetOrder(String AccessionNumber, String ARNumber) throws SQLException
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
            Orders order = new Orders();
            PatientDAO pdao = new PatientDAO();
            PreparedStatement stmt  = null; //con.createStatement();
            int temp = Integer.parseInt(ARNumber);
            int PatID = pdao.GetPatientIdByAR(temp);
            
            String query = "SELECT * FROM " + table
                    + "WHERE `accession` = ?"
                    + " AND `patientId` = " + PatID;
            
            stmt = createStatement(con, query, AccessionNumber);
            ResultSet rs = stmt.executeQuery();
            
            if(rs.next())
            {
                setOrderFromResultSet(order, rs);
            }
            
            rs.close();
            stmt.close();
            
            return order;
        }
        catch(Exception ex)
        {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return null;
        }    
    }
    
    @Override
    public Orders GetOrder(String AccessionNumber, java.util.Date oDate) throws SQLException
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
            Orders order = new Orders();
            PatientDAO pdao = new PatientDAO();
            PreparedStatement stmt  = null; //con.createStatement();
            java.sql.Date nDate = Convert.ToSQLDate(oDate);

            
            String query = "SELECT * FROM " + table
                    + "WHERE `accession` = ?"
                    + " AND `orderDate` LIKE '" + nDate + "%'";
            
            stmt = createStatement(con, query, AccessionNumber);
            ResultSet rs = stmt.executeQuery();
            
            if(rs.next())
            {
                setOrderFromResultSet(order, rs);
            }
            
            rs.close();
            stmt.close();
            
            return order;
        }
        catch(Exception ex)
        {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return null;
        }    
    }
    
    @Override
    public List<Orders> GetAllOrdersWithAccession(String AccessionNumber) throws SQLException
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
            ArrayList<Orders> olist = new ArrayList<>();
            PreparedStatement stmt  = null; //con.createStatement();
            
            String query = "SELECT * FROM " + table
                    + "WHERE `accession` = ?";
            
            stmt = createStatement(con, query, AccessionNumber);
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next())
            {
                Orders order = new Orders();
                setOrderFromResultSet(order, rs);
                olist.add(order);
            }
            
            rs.close();
            stmt.close();
            
            return olist;
        }
        catch(Exception ex)
        {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return null;
        }    
    }
    
    @Override
    public Orders GetOrderById(int Id) throws SQLException
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
            Orders order = new Orders();
            Statement stmt  = con.createStatement();
            
            String query = "SELECT * FROM " + table
                    + "WHERE `idOrders` = " + Id;
            
            ResultSet rs = stmt.executeQuery(query);
            
            if(rs.next())
            {
                setOrderFromResultSet(order, rs);
            }
            
            rs.close();
            stmt.close();
            
            return order;
        }
        catch(Exception ex)
        {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return null;
        }    
    }
    
    @Override
    public int GetOrderIdByOrderDate(String accession, java.util.Date orderDate)
    {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return -1;
        }
                
        int id;
        String ids = "";
        try
        {
            PreparedStatement stmt = null;//con.createStatement();
            
            String query = "SELECT `idOrders` FROM "
                    + table +
                    "WHERE `accession` = ?"
                    + " AND `orderDate` = '" + Convert.ToSQLDateTime(orderDate) + "';";
            
            stmt = createStatement(con, query, accession);
            ResultSet rs = stmt.executeQuery();
            
            if(rs.next())
            {
                ids = rs.getString(1);
            }
            
            id = Integer.parseInt(ids);
            
            rs.close();
            stmt.close();
            
            return id;
        }catch(Exception ex)
        {
            System.out.println("OrderDAO::GetOrderIdByOrderDate : " + ex.toString());
            return 0;
        }
    }
    
    @Override
    public int GetOrderIdForPurge(String accession, java.util.Date dos1, java.util.Date dos2)
    {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return -1;
        }
                
        int id;
        String ids = "";
        try
        {
            PreparedStatement stmt = null; //con.createStatement();
            
            String query = "SELECT `idOrders` FROM "
                    + table +
                    "WHERE `accession` = ?"
                    + " AND `orderDate` >= '" + Convert.ToSQLDateTime(dos1) + "'"
                    + " AND `orderDate` <= '" + Convert.ToSQLDateTime(dos2)+ "'";
            
            stmt = createStatement(con, query, accession);
            ResultSet rs = stmt.executeQuery();
            
            if(rs.next())
            {
                ids = rs.getString(1);
            }
            
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
    
    @Override
    public boolean OrderExists(String acc)
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
            PreparedStatement stmt = null;
            ResultSet rs = null;
            int rowCount = -1;
        
            //stmt = con.createStatement();
            String query = "SELECT COUNT(*) FROM " + table + " WHERE "
                    + "`accession` = ?";
            stmt = createStatement(con, query, acc);
            rs = stmt.executeQuery();
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
            //TODO:  Add Exception Handling
            return false;
        }    
    }
    
    @Override
    public boolean OrderExists(String acc, int patId)
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
            PreparedStatement stmt = null;
            ResultSet rs = null;
            int rowCount = -1;
        
            //stmt = con.createStatement();
            String query = "SELECT COUNT(*) FROM " + table + " WHERE "
                    + "`accession` = ?"
                    + " AND `patientId` = " + patId;
            stmt = createStatement(con, query, acc);
            rs = stmt.executeQuery();
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
            //TODO:  Add Exception Handling
            return false;
        }
    }
    
    @Override
    public boolean OrderExists(String acc, java.util.Date orderedDate)
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
            PreparedStatement stmt = null;
            ResultSet rs = null;
            int rowCount = -1;
        
            //stmt = con.createStatement();
            String query = "SELECT COUNT(*) FROM " + table + " WHERE "
                    + "`accession` = ?"
                    + " AND `orderDate` = " + Convert.ToSQLDateTime(orderedDate);
            stmt = createStatement(con, query, acc);
            rs = stmt.executeQuery();
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
            //TODO:  Add Exception Handling
            return false;
        }
    }
    
    @Override
    public int AccessionCount(String acc)
    {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return -1;
        }
                
        try
        {
            PreparedStatement stmt = null;
            ResultSet rs = null;
            int rowCount = -1;
        
            //stmt = con.createStatement();
            String query = "SELECT COUNT(*) FROM " + table + " WHERE "
                    + "`accession` = ?";
            stmt = createStatement(con, query, acc);
            rs = stmt.executeQuery();
            rs.next();
            rowCount = rs.getInt(1);
            
            rs.close();
            stmt.close();
            
            return rowCount;
        }catch(Exception ex)
        {
            //TODO:  Add Exception Handling
            return 0;
        } 
    }
    
    @Override
    public List<Orders> GetOrdersByPatient(int patientID, java.sql.Timestamp specDate)
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
            List<Orders> ls = new ArrayList<Orders>();
            Orders order;
        
            Statement stmt = con.createStatement();
            
            String query = "SELECT * FROM " + table
                    + " WHERE `patientId` = " + patientID
                    + " AND `specimenDate` < '" + specDate + "'"
                    + " ORDER BY `specimenDate` DESC";
            
            ResultSet rs = stmt.executeQuery(query);
            
            while(rs.next())
            {
                order = new Orders();
                
                setOrderFromResultSet(order, rs);
                
                ls.add(order);
            }
            
            rs.close();
            stmt.close();
        
            return ls;
        }catch(Exception e){
            System.out.println(e.toString());
            return null;
        }
    }
    
    @Override
    public List<Orders> SearchOrdersByPatientName(String FirstNameFragment, String LastNameFragment)
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
            
            String query =  "SELECT * FROM orders o " +
                            "LEFT OUTER JOIN patients p " +
                            "ON o.patientId = p.idPatients ";
            if(FirstNameFragment != null && LastNameFragment != null)
            {
                query += "WHERE p.firstName LIKE '" + FirstNameFragment + "%' " +
                            "AND p.lastName LIKE '" + LastNameFragment + "%';";
                            
            }
            else if(FirstNameFragment == null && LastNameFragment != null)
            {
                query += "WHERE p.lastname LIKE '" + LastNameFragment + "%';";
            }
            else if(FirstNameFragment != null && LastNameFragment == null)
            {
                query += "WHERE p.firstName LIKE '" + FirstNameFragment + "%';";
            }
            else
            {
                return null;
            }
            
            List<Orders> ls = new ArrayList<Orders>();
            Orders order;
        
            Statement stmt = con.createStatement();
            
            ResultSet rs = stmt.executeQuery(query);
            
            while(rs.next())
            {
                order = new Orders();
                
                setOrderFromResultSet(order, rs);
                
                ls.add(order);
            }
            
            rs.close();
            stmt.close();
        
            return ls;
        }catch(Exception e){
            System.out.println(e.toString());
            return null;
        }
    }
    
    @Override
    public List<Orders> SearchOrdersByPatientNameEMROrder(String FirstNameFragment, String LastNameFragment, String emrOrderId)
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

            String query = "SELECT * FROM orders o "
                    + "LEFT OUTER JOIN patients p "
                    + "ON o.patientId = p.idPatients ";
            if (FirstNameFragment != null && LastNameFragment != null) {
                query += "WHERE p.firstName LIKE '" + FirstNameFragment + "%' "
                        + "AND p.lastName LIKE '" + LastNameFragment + "%' ";

            } else if (FirstNameFragment == null && LastNameFragment != null) {
                query += "WHERE p.lastname LIKE '" + LastNameFragment + "%' ";
            } else if (FirstNameFragment != null && LastNameFragment == null) {
                query += "WHERE p.firstName LIKE '" + FirstNameFragment + "%' ";
            } else {
                return null;
            }

            if (emrOrderId != null && emrOrderId.isEmpty() == false)
            {
                query += " AND SUBSTRING_INDEX(emrOrderId, ')', -1) LIKE '%" + emrOrderId + "%'";
            }
            
            List<Orders> ls = new ArrayList<>();
            Orders order;

            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                order = new Orders();

                setOrderFromResultSet(order, rs);

                ls.add(order);
            }

            rs.close();
            stmt.close();

            return ls;
        } catch (Exception e) {
            System.out.println(e.toString());
            return null;
        }        
    }
    
    @Override
    public boolean PurgeOrder(int OrderID)
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
            Statement stmt = null;
            
            String query = "DELETE FROM " + table + " WHERE "
                    + "`idOrders` = " + OrderID;
            
            stmt = con.createStatement();
            int delete = stmt.executeUpdate(query);
            
            if (delete == 1)
                return true;
            else
                return false;
        }
        catch (Exception ex)
        {
            //TODO: Add exception handling
            System.out.println(ex.toString());
            return false;
        }
    }
    
    /**
     * @deprecated Database updated to use VARCHAR accessions.
     */
    @Deprecated
    @Override
    public int GetMaxAccession()
    {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return -1;
        }
                
        try
        {
            Statement stmt = null;
            String query = "SELECT MAX(`accession`) FROM " + table + ";";
            stmt = con.createStatement();
            int max = stmt.executeUpdate(query);
            
            return max;
        }
        catch (Exception ex)
        {
            //TODO: Add exception handling
            System.out.println(ex.toString());
            return -1;
        }
            
    }
    
    @Override
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
    
    private Orders setOrderFromResultSet(Orders order, ResultSet rs) throws SQLException
    {
        order.setIdOrders(rs.getInt("idOrders"));
        order.setAccession(rs.getString("accession"));
        order.setClientId(rs.getInt("clientId"));
        order.setLocationId(rs.getInt("locationId"));
        order.setDoctorId(rs.getInt("doctorId"));
        order.setOrderDate(rs.getTimestamp("orderDate"));
        order.setSpecimenDate(rs.getTimestamp("specimenDate"));
        order.setPatientId(rs.getInt("patientId"));
        order.setSubscriberId(rs.getInt("subscriberId"));
        order.setIsAdvancedOrder(rs.getBoolean("isAdvancedOrder"));
        order.setInsurance(rs.getInt("insurance"));
        order.setSecondaryInsurance(rs.getInt("secondaryInsurance"));
        order.setPolicyNumber(rs.getString("policyNumber"));
        order.setGroupNumber(rs.getString("groupNumber"));
        order.setSecondaryPolicyNumber(rs.getString("secondaryPolicyNumber"));
        order.setSecondaryGroupNumber(rs.getString("secondaryGroupNumber"));
        order.setMedicareNumber(rs.getString("medicareNumber"));
        order.setMedicaidNumber(rs.getString("medicaidNumber"));
        order.setReportType(rs.getInt("reportType"));
        order.setRequisition(rs.getInt("requisition"));
        order.setBillOnly(rs.getBoolean("billOnly"));
        order.setActive(rs.getBoolean("active"));
        order.setHold(rs.getBoolean("hold"));
        order.setStage(rs.getInt("stage"));
        order.setHoldComment(rs.getString("holdComment"));
        order.setResultComment(rs.getString("resultComment"));
        order.setInternalComment(rs.getString("internalComment"));
        order.setHl7Transmitted(rs.getBoolean("hl7Transmitted"));
        order.setPayment(rs.getDouble("payment"));
        order.setBillable(rs.getBoolean("billable"));
        order.setEmrOrderId(rs.getString("emrOrderId"));
        order.setRoom(rs.getString("Room"));
        order.setBed(rs.getString("Bed"));
        
        return order;
    }
    
    private String GenerateInsertStatement(ArrayList<String> fields)
    {
        String stmt = "INSERT INTO " + table + "(";
        String values = ") values (";
        for(int i = 0; i < fields.size(); ++i)
        {
            stmt += " `" + fields.get(i) + "`";
            values += "?";
            if(i != fields.size() - 1)
            {
                stmt += ", ";
                values +=",";
            }
        }
        values += ")";
        return stmt + values;
    }
    
    private String GenerateUpdateStatement(ArrayList<String> fields)
    {
        String stmt = "UPDATE " + table + " SET";
        for(int i = 0; i < fields.size(); ++i)
        {
            stmt += " `" + fields.get(i) + "` = ?";
            if(i != fields.size() - 1)
            {
                stmt += ",";
            }
        }
        return stmt;
    }
    
    @Override
    public String structureCheck() {
        return DatabaseStructureCheck.structureCheck(fields, table, con);
    }
}
