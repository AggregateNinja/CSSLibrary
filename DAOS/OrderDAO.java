package DAOS;

/**
 * @date: Mar 12, 2012
 * @author: Keith Maggio
 *
 * @project: CSSLibrary
 * @package: DAOS
 * @file name: OrderDAO.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc. All Rights Reserved
 */
import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IOrderDAO;
import DAOS.IDAOS.IStructureCheckable;
import DOS.Orders;
import Utility.Convert;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.SQLUtil;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;

import static Utility.SQLUtil.createStatement;

public class OrderDAO implements IOrderDAO, DAOInterface, IStructureCheckable {

    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`orders`";
    private final int CONNECTION_VALIDATION_TIMEOUT = 3; // seconds
    
    public enum DateOfServiceType
    {
        ORDER_DATE,
        SPECIMEN_DATE
    }
    
    /**
     * All fields except idOrders
     */
    private final ArrayList<String> fields = new ArrayList<String>();

    public OrderDAO() {
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
        fields.add("isFasting");
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
        fields.add("DOI");
        fields.add("EOA");
        fields.add("ReleaseJobID");
        fields.add("releaseDate");
        fields.add("reqId");
        fields.add("icdVersion");
        fields.add("allPAndT");
        fields.add("retransmit");
        fields.add("miles");
        fields.add("patientCount");
        fields.add("poNum");
    }
    
    public boolean UpdateHoldFlag(Integer idOrders, boolean hold) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }
        
        try {
            String query = "UPDATE " + table + " " +
                "SET hold = ? " +
                "WHERE idOrders = ?";
            PreparedStatement pStmt = con.prepareStatement(query);
            pStmt.setBoolean(1, hold);
            pStmt.setInt(2, idOrders);
            
            int retVal = pStmt.executeUpdate();

            pStmt.close();

            return retVal > 0;
        } catch (Exception ex) {
            String message = ex.toString();
            System.out.println(message);
            return false;
        }
    }

    @Override
    public boolean InsertOrder(Orders order) throws SQLException {
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

        try {
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
            pStmt.setBoolean(12, order.getIsFasting());
            SQLUtil.SafeSetInteger(pStmt, 13, order.getInsurance());
            SQLUtil.SafeSetInteger(pStmt, 14, order.getSecondaryInsurance());
            SQLUtil.SafeSetString(pStmt, 15, order.getPolicyNumber());
            SQLUtil.SafeSetString(pStmt, 16, order.getGroupNumber());
            SQLUtil.SafeSetString(pStmt, 17, order.getSecondaryPolicyNumber());
            SQLUtil.SafeSetString(pStmt, 18, order.getSecondaryGroupNumber());
            SQLUtil.SafeSetString(pStmt, 19, order.getMedicareNumber());
            SQLUtil.SafeSetString(pStmt, 20, order.getMedicaidNumber());
            SQLUtil.SafeSetInteger(pStmt, 21, order.getReportType());
            SQLUtil.SafeSetInteger(pStmt, 22, order.getRequisition());
            SQLUtil.SafeSetBoolean(pStmt, 23, order.isBillOnly());
            SQLUtil.SafeSetBoolean(pStmt, 24, order.isActive());
            SQLUtil.SafeSetBoolean(pStmt, 25, order.isHold());
            SQLUtil.SafeSetInteger(pStmt, 26, order.getStage());
            SQLUtil.SafeSetString(pStmt, 27, order.getHoldComment());
            SQLUtil.SafeSetString(pStmt, 28, order.getResultComment());
            SQLUtil.SafeSetString(pStmt, 29, order.getInternalComment());
            pStmt.setBoolean(30, order.getHl7Transmitted());
            SQLUtil.SafeSetDouble(pStmt, 31, order.getPayment());
            SQLUtil.SafeSetBoolean(pStmt, 32, order.getBillable());
            SQLUtil.SafeSetString(pStmt, 33, order.getEmrOrderId());
            SQLUtil.SafeSetDate(pStmt, 34, order.getDOI());
            SQLUtil.SafeSetString(pStmt, 35, order.getEOA());
            SQLUtil.SafeSetInteger(pStmt, 36, order.getReleaseJobID());
            SQLUtil.SafeSetTimeStamp(pStmt, 37, order.getReleaseDate());
            SQLUtil.SafeSetString(pStmt, 38, order.getReqId());
            SQLUtil.SafeSetInteger(pStmt, 39, order.getIcdVersion());
            SQLUtil.SafeSetBoolean(pStmt, 40, order.isAllPAndT());
            SQLUtil.SafeSetBoolean(pStmt, 41, order.isRetransmit());
            pStmt.setObject(42, order.getMiles(), java.sql.Types.INTEGER);
            pStmt.setObject(43, order.getPatientCount(), java.sql.Types.INTEGER);
            SQLUtil.SafeSetString(pStmt, 44, order.getPoNum());
                       
            pStmt.executeUpdate();

            pStmt.close();

            return true;
        } catch (Exception ex) {
            String message = ex.toString();
            //TODO:  Add logging for failures
            System.out.println(message);
            System.out.println(pStmt.toString());
            String accession = "[null]";
            if (order != null && order.getAccession() != null)
            {
                accession = order.getAccession();
            }
            int userId = Preferences.userRoot().getInt("id",0);
            SysLogDAO.Add(userId, "OrderDAO::Insert: accession " + accession, ex.toString());
            return false;
        }
    }
    
    /**
     * Inserts the supplied order and returns the new identifier
     *  that is created on success, null on failure.
     * @param order
     * @return Integer OrderId; null on failure
     */
    public Integer InsertOrderGetId(Orders order)
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
            String sql = GenerateInsertStatement(fields);
            PreparedStatement pStmt = con.prepareStatement(sql,
                                      Statement.RETURN_GENERATED_KEYS);
            
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
            pStmt.setBoolean(12, order.getIsFasting());
            SQLUtil.SafeSetInteger(pStmt, 13, order.getInsurance());
            SQLUtil.SafeSetInteger(pStmt, 14, order.getSecondaryInsurance());
            SQLUtil.SafeSetString(pStmt, 15, order.getPolicyNumber());
            SQLUtil.SafeSetString(pStmt, 16, order.getGroupNumber());
            SQLUtil.SafeSetString(pStmt, 17, order.getSecondaryPolicyNumber());
            SQLUtil.SafeSetString(pStmt, 18, order.getSecondaryGroupNumber());
            SQLUtil.SafeSetString(pStmt, 19, order.getMedicareNumber());
            SQLUtil.SafeSetString(pStmt, 20, order.getMedicaidNumber());
            SQLUtil.SafeSetInteger(pStmt, 21, order.getReportType());
            SQLUtil.SafeSetInteger(pStmt, 22, order.getRequisition());
            SQLUtil.SafeSetBoolean(pStmt, 23, order.isBillOnly());
            SQLUtil.SafeSetBoolean(pStmt, 24, order.isActive());
            SQLUtil.SafeSetBoolean(pStmt, 25, order.isHold());
            SQLUtil.SafeSetInteger(pStmt, 26, order.getStage());
            SQLUtil.SafeSetString(pStmt, 27, order.getHoldComment());
            SQLUtil.SafeSetString(pStmt, 28, order.getResultComment());
            SQLUtil.SafeSetString(pStmt, 29, order.getInternalComment());
            pStmt.setBoolean(30, order.getHl7Transmitted());
            SQLUtil.SafeSetDouble(pStmt, 31, order.getPayment());
            SQLUtil.SafeSetBoolean(pStmt, 32, order.getBillable());
            SQLUtil.SafeSetString(pStmt, 33, order.getEmrOrderId());
            SQLUtil.SafeSetDate(pStmt, 34, order.getDOI());
            SQLUtil.SafeSetString(pStmt, 35, order.getEOA());
            SQLUtil.SafeSetInteger(pStmt, 36, order.getReleaseJobID());
            SQLUtil.SafeSetTimeStamp(pStmt, 37, order.getReleaseDate());
            SQLUtil.SafeSetString(pStmt, 38, order.getReqId());
            SQLUtil.SafeSetInteger(pStmt, 39, order.getIcdVersion());
            SQLUtil.SafeSetBoolean(pStmt, 40, order.isAllPAndT());
            SQLUtil.SafeSetBoolean(pStmt, 41, order.isRetransmit());
            pStmt.setObject(42, order.getMiles(), java.sql.Types.INTEGER);
            pStmt.setObject(43, order.getPatientCount(), java.sql.Types.INTEGER);
            SQLUtil.SafeSetString(pStmt, 44, order.getPoNum());

            int affectedRows = pStmt.executeUpdate();
            
            if (affectedRows == 0)
            {
                throw new SQLException("Insert failed, no rows affected.");
            }

            
            Integer orderId;
            
            try (ResultSet generatedKeys = pStmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    orderId = generatedKeys.getInt(1);
                }
                else {
                    throw new SQLException("Insert failed, no ID obtained.");
                }
            }
            pStmt.close();
            
            return orderId;
        } catch (SQLException ex) {
            String message = ex.toString();
            System.out.println(message);
            String accession = "[null]";
            if (order != null && order.getAccession() != null)
            {
                accession = order.getAccession();
            }
            int userId = Preferences.userRoot().getInt("id",0);
            SysLogDAO.Add(userId, "OrderDAO::InsertOrderGetId: accession " + accession, ex.toString());            
            return null;
        }
    }
    

    @Override
    public boolean UpdateOrder(Orders order) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }

        // Double-check to ensure that the updated order is not
        // changing the accession number.
        Orders o = GetOrderById(order.getIdOrders());
        if (!o.getAccession().equals(order.getAccession())) return false;
        
        String stmt = GenerateUpdateStatement(fields)
                + " WHERE `idOrders` = " + order.getIdOrders();

        PreparedStatement pStmt = con.prepareStatement(stmt);

        try {
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
            pStmt.setBoolean(12, order.getIsFasting());
            SQLUtil.SafeSetInteger(pStmt, 13, order.getInsurance());
            SQLUtil.SafeSetInteger(pStmt, 14, order.getSecondaryInsurance());
            SQLUtil.SafeSetString(pStmt, 15, order.getPolicyNumber());
            SQLUtil.SafeSetString(pStmt, 16, order.getGroupNumber());
            SQLUtil.SafeSetString(pStmt, 17, order.getSecondaryPolicyNumber());
            SQLUtil.SafeSetString(pStmt, 18, order.getSecondaryGroupNumber());
            SQLUtil.SafeSetString(pStmt, 19, order.getMedicareNumber());
            SQLUtil.SafeSetString(pStmt, 20, order.getMedicaidNumber());
            SQLUtil.SafeSetInteger(pStmt, 21, order.getReportType());
            SQLUtil.SafeSetInteger(pStmt, 22, order.getRequisition());
            SQLUtil.SafeSetBoolean(pStmt, 23, order.isBillOnly());
            SQLUtil.SafeSetBoolean(pStmt, 24, order.isActive());
            SQLUtil.SafeSetBoolean(pStmt, 25, order.isHold());
            SQLUtil.SafeSetInteger(pStmt, 26, order.getStage());
            SQLUtil.SafeSetString(pStmt, 27, order.getHoldComment());
            SQLUtil.SafeSetString(pStmt, 28, order.getResultComment());
            SQLUtil.SafeSetString(pStmt, 29, order.getInternalComment());
            pStmt.setBoolean(30, order.getHl7Transmitted());
            SQLUtil.SafeSetDouble(pStmt, 31, order.getPayment());
            SQLUtil.SafeSetBoolean(pStmt, 32, order.getBillable());
            SQLUtil.SafeSetString(pStmt, 33, order.getEmrOrderId());
            SQLUtil.SafeSetDate(pStmt, 34, order.getDOI());
            SQLUtil.SafeSetString(pStmt, 35, order.getEOA());
            SQLUtil.SafeSetInteger(pStmt, 36, order.getReleaseJobID());
            SQLUtil.SafeSetTimeStamp(pStmt, 37, order.getReleaseDate());
            SQLUtil.SafeSetString(pStmt, 38, order.getReqId());
            SQLUtil.SafeSetInteger(pStmt, 39, order.getIcdVersion());
            SQLUtil.SafeSetBoolean(pStmt, 40, order.isAllPAndT());
            SQLUtil.SafeSetBoolean(pStmt, 41, order.isRetransmit());
            pStmt.setObject(42, order.getMiles(), java.sql.Types.INTEGER);
            pStmt.setObject(43, order.getPatientCount(), java.sql.Types.INTEGER);
            SQLUtil.SafeSetString(pStmt, 44, order.getPoNum());
            // Not updating created date
            
            pStmt.executeUpdate();

            pStmt.close();

            return true;
        } catch (Exception ex) {
            String message = ex.toString();
            //TODO:  Add exception handleing
            System.out.println(message);
            System.out.println(pStmt.toString());
            String accession = "[null]";
            if (order != null && order.getAccession() != null)
            {
                accession = order.getAccession();
            }
            int userId = Preferences.userRoot().getInt("id",0);
            SysLogDAO.Add(userId, "OrderDAO::UpdateOrder: accession " + accession, ex.toString());            
            return false;
        }
    }


    @Override
    public Orders GetOrder(String AccessionNumber, int... locationIds) throws SQLException {
        try {
            if (con.isClosed() || con.isValid(CONNECTION_VALIDATION_TIMEOUT)) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        try {
            Orders order = new Orders();
            PreparedStatement stmt = null; //con.createStatement();

            String query = "SELECT * FROM " + table
                    + " WHERE `accession` = ?";
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

            if (rs.next()) {
                setOrderFromResultSet(order, rs);
            }

            rs.close();
            stmt.close();

            return order;
        } catch (Exception ex) {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            String accession = "[null]";
            if (AccessionNumber != null) accession = AccessionNumber;
            int userId = Preferences.userRoot().getInt("id",0);
            SysLogDAO.Add(userId, "OrderDAO::GetOrder: accession " + accession, ex.toString());            
            return null;
        }
    }
    
    @Override
    public Orders GetOrder(String AccessionNumber) throws SQLException {
        try {
            if (con.isClosed() || con.isValid(CONNECTION_VALIDATION_TIMEOUT)) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        try {
            Orders order = new Orders();
            PreparedStatement stmt = null; //con.createStatement();

            String query = "SELECT * FROM " + table
                    + " WHERE `accession` = ?";

            stmt = createStatement(con, query, AccessionNumber);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                setOrderFromResultSet(order, rs);
            }

            rs.close();
            stmt.close();

            return order;
        } catch (Exception ex) {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            String accession = "[null]";
            if (AccessionNumber != null) accession = AccessionNumber;
            int userId = Preferences.userRoot().getInt("id",0);
            SysLogDAO.Add(userId, "OrderDAO::GetOrder: accession " + accession, ex.toString());            
            return null;
        }
    }

    @Override
    public Orders GetOrder(String AccessionNumber, String ARNumber) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        try {
            Orders order = new Orders();
            PatientDAO pdao = new PatientDAO();
            PreparedStatement stmt = null; //con.createStatement();
            int temp = Integer.parseInt(ARNumber);
            int PatID = pdao.GetPatientIdByAR(temp);

            String query = "SELECT * FROM " + table
                    + "WHERE `accession` = ?"
                    + " AND `patientId` = " + PatID;

            stmt = createStatement(con, query, AccessionNumber);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                setOrderFromResultSet(order, rs);
            }

            rs.close();
            stmt.close();

            return order;
        } catch (Exception ex) {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            String accession = "[null]";
            String arNo = "[null]";
            if (AccessionNumber != null)
            {
                accession = AccessionNumber;
            }
            if (ARNumber != null)
            {
                arNo = ARNumber;
            }
            int userId = Preferences.userRoot().getInt("id",0);
            SysLogDAO.Add(userId, "OrderDAO::GetOrder(Accession,ArNo): accession " + accession + " arNo " + arNo, ex.toString());            
            return null;
        }
    }

    /**
     * Uses OrderDate
     * @param AccessionNumber
     * @param oDate
     * @return
     * @throws SQLException 
     */
    @Override
    public Orders GetOrder(String AccessionNumber, java.util.Date oDate) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        try {
            Orders order = new Orders();
            PatientDAO pdao = new PatientDAO();
            PreparedStatement stmt = null; //con.createStatement();
            java.sql.Date nDate = Convert.ToSQLDate(oDate);


            String query = "SELECT * FROM " + table
                    + "WHERE `accession` = ?"
                    + " AND `orderDate` LIKE '" + nDate + "%'";

            stmt = createStatement(con, query, AccessionNumber);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                setOrderFromResultSet(order, rs);
            }

            rs.close();
            stmt.close();

            return order;
        } catch (Exception ex) {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            String accession = "[null]";
            String orderDate = "[null]";
            if (AccessionNumber != null)
            {
                accession = AccessionNumber;
            }
            if (oDate != null)
            {
                orderDate = oDate.toString();
            }
            int userId = Preferences.userRoot().getInt("id",0);
            SysLogDAO.Add(userId, "OrderDAO::GetOrder(Accession,OrderDate): accession " + accession + " order date " + orderDate, ex.toString());                        
            return null;
        }
    }

    @Override
    public List<Orders> GetAllOrdersWithAccession(String AccessionNumber) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        try {
            ArrayList<Orders> olist = new ArrayList<>();
            PreparedStatement stmt = null; //con.createStatement();

            String query = "SELECT * FROM " + table
                    + "WHERE `accession` = ?";

            stmt = createStatement(con, query, AccessionNumber);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Orders order = new Orders();
                setOrderFromResultSet(order, rs);
                olist.add(order);
            }

            rs.close();
            stmt.close();

            return olist;
        } catch (Exception ex) {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            String accession = "[null]";
            if (AccessionNumber != null)
            {
                accession = AccessionNumber;
            }
            int userId = Preferences.userRoot().getInt("id",0);
            SysLogDAO.Add(userId, "OrderDAO::GetAllOrdersWithAccession: accession " + accession, ex.toString());                                    
            return null;
        }
    }

    @Override
    public Orders GetOrderById(int Id) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        try {
            Orders order = new Orders();
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + "WHERE `idOrders` = " + Id;

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                setOrderFromResultSet(order, rs);
            }

            rs.close();
            stmt.close();

            return order;
        } catch (Exception ex) {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            int userId = Preferences.userRoot().getInt("id",0);
            SysLogDAO.Add(userId, "OrderDAO::GetOrderById: orderId " + Id, ex.toString());
            return null;
        }
    }

    @Override
    public int GetOrderIdByOrderDate(String accession, java.util.Date orderDate) {
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
        try {
            PreparedStatement stmt = null; // con.createStatement();

            String query = "SELECT `idOrders` FROM "
                    + table
                    + "WHERE `accession` = ?"
                    + " AND `orderDate` = '" + Convert.ToSQLDateTime(orderDate) + "';";

            stmt = createStatement(con, query, accession);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                ids = rs.getString(1);
            }

            id = Integer.parseInt(ids);

            rs.close();
            stmt.close();

            return id;
        } catch (Exception ex)
        {
            System.out.println("OrderDAO::GetOrderIdByOrderDate : " + ex.toString());
            if (accession == null) accession = "[null]";
            String orderDateStr = (orderDate == null? "[null]" : orderDate.toString());
            int userId = Preferences.userRoot().getInt("id",0);
            SysLogDAO.Add(userId, "OrderDAO::GetOrderIdByOrderDate(accession,orderdate): accession " + accession + " orderDate " + orderDateStr, ex.toString());
            return 0;
        }
    }

    @Override
    public int GetOrderIdForPurge(String accession, java.util.Date dos1, java.util.Date dos2) {
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
        try {
            PreparedStatement stmt = null; //con.createStatement();

            String query = "SELECT `idOrders` FROM "
                    + table
                    + "WHERE `accession` = ?"
                    + " AND `orderDate` >= '" + Convert.ToSQLDateTime(dos1) + "'"
                    + " AND `orderDate` <= '" + Convert.ToSQLDateTime(dos2) + "'";

            stmt = createStatement(con, query, accession);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                ids = rs.getString(1);
            }

            id = Integer.parseInt(ids);

            rs.close();
            stmt.close();

            return id;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return 0;
        }
    }

    @Override
    public boolean OrderExists(String acc) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }

        try {
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

    @Override
    public boolean OrderExists(String acc, int patId) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }

        try {
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

    @Override
    public boolean OrderExists(String acc, java.util.Date orderedDate) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }

        try {
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

    @Override
    public int AccessionCount(String acc) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return -1;
        }

        try {
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
        } catch (Exception ex) {
            //TODO:  Add Exception Handling
            return 0;
        }
    }
    
    public List<Orders> GetOrdersByPatient(int patientID)
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
            List<Orders> ls = new ArrayList<Orders>();
            Orders order;

            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + " WHERE `patientId` = " + patientID;

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
    public List<Orders> GetOrdersByPatient(int patientID, java.sql.Timestamp specDate) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        try {
            List<Orders> ls = new ArrayList<Orders>();
            Orders order;

            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + " WHERE `patientId` = " + patientID
                    + " AND `specimenDate` < '" + specDate + "'"
                    + " ORDER BY `specimenDate` DESC";

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
    public List<Orders> SearchOrdersByPatientName(String FirstNameFragment, String LastNameFragment) {
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
                        + "AND p.lastName LIKE '" + LastNameFragment + "%';";

            } else if (FirstNameFragment == null && LastNameFragment != null) {
                query += "WHERE p.lastname LIKE '" + LastNameFragment + "%';";
            } else if (FirstNameFragment != null && LastNameFragment == null) {
                query += "WHERE p.firstName LIKE '" + FirstNameFragment + "%';";
            } else {
                return null;
            }

            List<Orders> ls = new ArrayList<Orders>();
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
    public boolean PurgeOrder(int OrderID) {
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

            String query = "DELETE FROM " + table + " WHERE "
                    + "`idOrders` = " + OrderID;

            stmt = con.createStatement();
            int delete = stmt.executeUpdate(query);
            stmt.close();
            if (delete == 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception ex) {
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
    public int GetMaxAccession() {
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
            String query = "SELECT MAX(`accession`) FROM " + table + ";";
            stmt = con.createStatement();
            int max = stmt.executeUpdate(query);
            stmt.close();
            return max;
        } catch (Exception ex) {
            //TODO: Add exception handling
            System.out.println(ex.toString());
            return -1;
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

    public static Orders getByAccessionDateOfService(
            String accession,
            java.util.Date dateOfService,
            DateOfServiceType dateOfServiceType)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (accession == null || accession.isEmpty()) throw new IllegalArgumentException("OrderDAO::getByAccessionDateOfService: Received a [NULL] or empty accession argument");
        if (dateOfService == null) throw new IllegalArgumentException("OrderDAO::getByAccessionDateOfService: Received a [NULL] date of service argument");
        if (dateOfServiceType == null) throw new IllegalArgumentException("OrderDAO::getByAccessionDateOfService: Received a [NULL] DateOfServiceType enum argument");
        
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);
        
        String sql = "SELECT * FROM `orders` WHERE `accession` = ? AND ";
        switch (dateOfServiceType)
        {
            case ORDER_DATE:
                sql += " DATE(orderDate) = DATE(?)";
                break;
                
            case SPECIMEN_DATE:
                sql += " DATE(specimenDate) = DATE(?)";
                break;
        }
        
        PreparedStatement pStmt = con.prepareStatement(sql);
        pStmt.setString(1, accession);
        pStmt.setDate(2, new java.sql.Date(dateOfService.getTime()));
        ResultSet rs = pStmt.executeQuery();
        
        Orders order = null;
        if (rs.next())
        {
            order = setOrderFromResultSet(new Orders(), rs);
        }
        pStmt.close();
        return order;
    }

    private static Orders setOrderFromResultSet(Orders order, ResultSet rs) throws SQLException
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
        order.setIsFasting(rs.getBoolean("isFasting"));
        order.setDOI(rs.getDate("DOI"));
        order.setEOA(rs.getString("EOA"));
        order.setReleaseJobID(rs.getInt("ReleaseJobID"));
        order.setReleaseDate(rs.getTimestamp("ReleaseDate"));
        order.setReqId(rs.getString("reqId"));
        order.setIcdVersion(rs.getInt("icdVersion"));
        order.setAllPAndT(rs.getBoolean("allPAndT"));
        order.setRetransmit(rs.getBoolean("retransmit"));
        String miles = rs.getString("miles");
        if (miles != null && miles.isEmpty() == false)
        {
            order.setMiles(rs.getInt("miles"));
        }
        String patientCount = rs.getString("patientCount");
        if (patientCount != null && patientCount.isEmpty() == false)
        {
            order.setPatientCount(rs.getInt("patientCount"));
        }
        order.setCreated(rs.getTimestamp("created"));
        order.setPoNum(rs.getString("poNum"));

        return order;
    }
    
    private String GenerateInsertStatement(ArrayList<String> fields) {

        String stmt = "INSERT INTO " + table + "(";
        String values = ") values (";
        for (int i = 0; i < fields.size(); ++i) {
            stmt += " `" + fields.get(i) + "`";
            values += "?";
            if (i != fields.size() - 1) {
                stmt += ", ";
                values += ",";
            }
        }
        values += ")";
        return stmt + values;
    }

    private String GenerateUpdateStatement(ArrayList<String> fields) {

        String stmt = "UPDATE " + table + " SET";
        for (int i = 0; i < fields.size(); ++i) {
            stmt += " `" + fields.get(i) + "` = ?";
            if (i != fields.size() - 1) {
                stmt += ",";
            }
        }
        return stmt;
    }

    @Override
    public Boolean Insert(Serializable obj)
    {
        try
        {
            return InsertOrder((Orders)obj);
        }
        catch (SQLException ex)
        {
            Logger.getLogger(OrderDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public Boolean Update(Serializable obj)
    {
        try
        {
            return UpdateOrder((Orders)obj);
        }
        catch (SQLException ex)
        {
            Logger.getLogger(OrderDAO.class.getName()).log(Level.SEVERE, null, ex);
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
        try
        {
            return GetOrderById(ID);
        }
        catch (SQLException ex)
        {
            Logger.getLogger(OrderDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    @Override
    public String structureCheck() {
        return DatabaseStructureCheck.structureCheck(fields, table, con);
    }
}
