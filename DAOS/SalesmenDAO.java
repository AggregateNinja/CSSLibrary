/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAOS;

import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IStructureCheckable;
import DOS.ReportTypeCommission;
import DOS.Sales;
import DOS.Salesmen;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.Convert;
import Utility.SQLUtil;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @date: Apr 28, 2014
 * @author: Derrick J. Piper <derrick@csslis.com>
 *
 * @project:
 * @package: DAOS
 * @file name: SalesmenDAO.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc. All Rights Reserved
 */
public class SalesmenDAO implements DAOInterface, IStructureCheckable
{

    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`salesmen`";

   
    private final ArrayList<String> fields = new ArrayList<>();

    /**
     * All fields except idsalesmen
     */
    public SalesmenDAO()
    {
        fields.add("employeeID");
        fields.add("commisionRate");
        fields.add("territory");
        fields.add("classification");
        fields.add("salesGroup");
        fields.add("byOrders");
        fields.add("byTests");
        fields.add("byBilled");
        fields.add("byReceived");
        fields.add("byGroup");
        fields.add("byPercentage");
        fields.add("byAmount");
        fields.add("created");
        fields.add("createdBy");
    }

    /**
     * Description
     * @param obj Serializable The salesmen to be inserted.
     * @return type Boolean returns true if inserted.
     */
    @Override
    public Boolean Insert(Serializable obj)
    {
        Salesmen sal = (Salesmen) obj;
        try
        {
            try
            {
                if (con.isClosed())
                {
                    con = CheckDBConnection.Check(dbs, con);
                }
            }
            catch (SQLException sex)
            {
                System.out.println(sex.toString());
                return false;
            }
            String stmt = GenerateInsertStatement(fields);
            PreparedStatement pStmt = con.prepareStatement(stmt);
            SetStatementFromSalesmen(sal, pStmt);

            pStmt.executeUpdate();

            pStmt.close();

            return true;
        }
        catch (SQLException ex)
        {
            Logger.getLogger(Salesmen.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    /**
     * Description - Used by SalesmenConfigurationTopComponent
     * @param obj Serializable The salesmen to be inserted.
     * @return type Integer returns idsalesmen of the inserted salesman
     */
    public Integer InsertAndReturnId(Serializable obj)
    {
        Salesmen sal = (Salesmen) obj;
        try
        {
            try
            {
                if (con.isClosed())
                {
                    con = CheckDBConnection.Check(dbs, con);
                }
            }
            catch (SQLException sex)
            {
                System.out.println(sex.toString());
                return null;
            }
            String stmt = GenerateInsertStatement(fields);
            PreparedStatement pStmt = con.prepareStatement(stmt, Statement.RETURN_GENERATED_KEYS);
            SetStatementFromSalesmen(sal, pStmt);

            pStmt.executeUpdate();

            

            ResultSet rs = pStmt.getGeneratedKeys();
            
            if(rs.next())
            {
                int last_inserted_id = rs.getInt(1);
                return last_inserted_id;
            }
            
            pStmt.close();
        }
        catch (SQLException ex)
        {
            Logger.getLogger(Salesmen.class.getName()).log(Level.SEVERE, null, ex);
            
        }
        return null;
    }

    /**
     * Description
     * @param obj Serializable, The salesmen to be updated.
     * @return type Boolean returns true if updated.
     */
    @Override
    public Boolean Update(Serializable obj)
    {
        Salesmen sal = (Salesmen) obj;
        try
        {
            try
            {
                if (con.isClosed())
                {
                    con = CheckDBConnection.Check(dbs, con);
                }
            }
            catch (SQLException sex)
            {
                System.out.println(sex.toString());
                return false;
            }
            String stmt = GenerateUpdateStatement(fields) + " WHERE `idsalesmen` = " + sal.getIdsalesmen();
            PreparedStatement pStmt = con.prepareStatement(stmt);

            SetStatementFromSalesmen(sal, pStmt);

            pStmt.executeUpdate();

            pStmt.close();

            return true;
        }
        catch (SQLException ex)
        {
            Logger.getLogger(Salesmen.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    /**
     * Description
     * @param obj Serializable The salesmen to be deleted.
     * @return type Boolean returns true if deleted.
     */
    @Override
    public Boolean Delete(Serializable obj)
    {
        try
        {
            if (con.isClosed())
            {
                con = CheckDBConnection.Check(dbs, con);
            }
        }
        catch (SQLException sex)
        {
            System.out.println(sex.toString());
            return false;
        }
        return false;
    }
    
    /**
     * Description - Used by SalesmenConfigurationTopComponent
     * @param map The HashMap<Integer, ReportTypeCommission> to be inserted - The key is the reportTypeId
     * @return type Boolean returns true if inserted
     */
    public Boolean InsertReportTypeCommissions(HashMap map) {
        HashMap<Integer, ReportTypeCommission> hmRTC = map;
        
        try {
            try
            {
                if (con.isClosed())
                {
                    con = CheckDBConnection.Check(dbs, con);
                }
            }
            catch (SQLException sex)
            {
                System.out.println(sex.toString());
                return false;
            }
            
            for (Map.Entry<Integer, ReportTypeCommission> entry : hmRTC.entrySet()) {
                ReportTypeCommission rtc = entry.getValue();
                Integer reportTypeId = rtc.getReportTypeId();
                Integer salesmanId = rtc.getSalesmanId();
                BigDecimal minPayment = rtc.getMinPayment();
                BigDecimal commissionRate = rtc.getCommissionRate();
                
                String stmt = "INSERT INTO commissionRates (reportTypeId, salesmanId, minPayment, commissionRate) VALUES "
                    + "(" + reportTypeId + "," + salesmanId + "," + minPayment + "," + commissionRate + ")";
                PreparedStatement pStmt = con.prepareStatement(stmt);

                pStmt.executeUpdate();
                pStmt.close();
            }
            
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Sales.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    /**
     * Description - Used by SalesmenConfigurationTopComponent
     * @param rtc The ReportTypeCommission to be inserted
     * @return type Boolean returns true if inserted
     */
    public Boolean InsertReportTypeCommission(ReportTypeCommission rtc) {
        try {
            try
            {
                if (con.isClosed())
                {
                    con = CheckDBConnection.Check(dbs, con);
                }
            }
            catch (SQLException sex)
            {
                System.out.println(sex.toString());
                return false;
            }
            
           
            String stmt = "INSERT INTO commissionRates (reportTypeId, salesmanId, minPayment, commissionRate) VALUES "
                + "(" + rtc.getReportTypeId() + "," + rtc.getSalesmanId() + "," + rtc.getMinPayment() + "," + rtc.getCommissionRate() + ")";
            PreparedStatement pStmt = con.prepareStatement(stmt);

            pStmt.executeUpdate();
            pStmt.close();
            
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Sales.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
    }
    
    /**
     * Description - Used by SalesmenConfigurationTopComponent
     * @param rtc The HashMap<Integer, ReportTypeCommission> to be inserted
     * @return type Boolean returns true if inserted
     */
    public Boolean UpdateReportTypeCommissions(HashMap map) {        
        
        HashMap<Integer, ReportTypeCommission> hmRTC = map;
                
        try {
            try
            {
                if (con.isClosed())
                {
                    con = CheckDBConnection.Check(dbs, con);
                }
            }
            catch (SQLException sex)
            {
                System.out.println(sex.toString());
                return false;
            }
            
            for (Map.Entry<Integer, ReportTypeCommission> entry : hmRTC.entrySet()) {
            
                ReportTypeCommission rtc = entry.getValue();
                BigDecimal commissionRate = rtc.getCommissionRate();
                BigDecimal minPayment = rtc.getMinPayment();
                Integer idCommissions = rtc.getIdCommissions();
                
                if (idCommissions != null && idCommissions != 0) {
                    String stmt = "UPDATE commissionRates SET commissionRate = " + commissionRate + ", minPayment = " + minPayment + " WHERE idCommissions = " + idCommissions;
                    PreparedStatement pStmt = con.prepareStatement(stmt);

                    pStmt.executeUpdate();
                    pStmt.close();
                } else {
                    Boolean success = this.InsertReportTypeCommission(rtc);
                }
            }
            
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Sales.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    /**
     * Description - Used by SalesmenConfigurationTopComponent
     * @param salesmanId used to retrieve the report type commissions
     * @return type HashMap<Integer, ReportTypeCommission> of commissions
     */
    public HashMap<Integer, ReportTypeCommission> getReportTypeCommissionsBySalesmanId(Integer salesmanId) {
        HashMap<Integer, ReportTypeCommission> commissions = new HashMap<>();
        
        try {
            try
            {
                if (con.isClosed())
                {
                    con = CheckDBConnection.Check(dbs, con);
                }
            }
            catch (SQLException sex)
            {
                System.out.println(sex.toString());
                return null;
                //return false;
            }
            
            String query = "SELECT cr.idCommissions, cr.reportTypeId, cr.salesmanId, cr.minPayment, cr.commissionRate, rt.idreportType "
                    + "FROM css.reportType rt "
                    + "LEFT JOIN commissionRates cr ON rt.idreportType = cr.reportTypeId AND cr.isActive = true AND cr.salesmanId = " + salesmanId + " "
                    + "WHERE rt.selectable = true";
            Statement stmt = con.createStatement();
            
            ResultSet rs = stmt.executeQuery(query);
            
            ReportTypeCommission rtc;
            while (rs.next())
            {
                rtc = new ReportTypeCommission(rs.getInt("idreportType"));
                rtc.setSalesmanId(salesmanId);
                
                rtc.setIdCommissions(rs.getInt("idCommissions"));   
                
                if (rs.getBigDecimal("minPayment") != null) {
                    rtc.setMinPayment(rs.getBigDecimal("minPayment"));
                } else {
                    rtc.setMinPayment(new BigDecimal(0.00));
                }
                
                if (rs.getBigDecimal("commissionRate") != null) {
                    rtc.setCommissionRate(rs.getBigDecimal("commissionRate"));
                } else {
                    rtc.setCommissionRate(new BigDecimal(0.00));
                }
                
               
                commissions.put(rs.getInt("idreportType"), rtc);
            }

            rs.close();
            stmt.close();

        } catch (SQLException ex) {
            Logger.getLogger(Sales.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        
        return commissions;
    }

    /**
     * Description
     * @param ID Serializable The salesmen ID to be retrieved.
     * @return type Integer, The ID of the salesmen being searched for.
     */
    @Override
    public Serializable getByID(Integer ID)
    {
        try
        {
            return GetSalesmenByID(ID);
        }
        catch (SQLException ex)
        {
            Logger.getLogger(SalesmenDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public Boolean SalesmenExists(int idemployees) throws SQLException
    {
        try
        {
            if (con.isClosed())
            {
                con = CheckDBConnection.Check(dbs, con);
            }
        }
        catch (SQLException sex)
        {
            System.out.println(sex.toString());
            return null;
        }

        try
        {
            Statement stmt = con.createStatement();
            int rowCount = -1;

            String query = "SELECT COUNT(*) FROM " + table
                    + " WHERE `employeeID` = " + idemployees;

            ResultSet rs = stmt.executeQuery(query);

            rs.next();
            rowCount = rs.getInt(1);

            rs.close();
            stmt.close();
            return rowCount > 0;
        }
        catch (SQLException ex)
        {
            return null;
        }
    }

    /**
     *
     * @param ID type Integer, The salesmen ID.
     * @return String The salesmen data associated with the entered ID.
     * @throws SQLException if query fails.
     */
    public Salesmen GetSalesmenByID(int ID) throws SQLException
    {
        try
        {
            if (con.isClosed())
            {
                con = CheckDBConnection.Check(dbs, con);
            }
        }
        catch (SQLException sex)
        {
            System.out.println(sex.toString());
            return null;
        }

        Salesmen sal = new Salesmen();

        try
        {
            String query = "SELECT * FROM " + table + " "
                    + "WHERE `idsalesmen` = " + ID;

            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next())
            {
                SetSalesmenFromResultSet(sal, rs);
            }

            rs.close();
            stmt.close();

            return sal;
        }
        catch (SQLException ex)
        {
            System.out.println(ex.toString());
            return null;
        }
    }

    /**
     *
     * @return type List, return all salesmen.
     * @throws SQLException if query fails.
     */
    public ArrayList<Salesmen> GetAllSalesmen() throws SQLException
    {
        try
        {
            if (con.isClosed())
            {
                con = CheckDBConnection.Check(dbs, con);
            }
        }
        catch (SQLException sex)
        {
            System.out.println(sex.toString());
            return null;
        }

        ArrayList<Salesmen> salList = new ArrayList<>();

        try
        {
            Statement stmt = con.createStatement();
            String query = "SELECT s.* FROM " + table + " s "
                    + "LEFT JOIN employees emp "
                    + "ON s.employeeID = emp.idemployees "
                    + "ORDER BY emp.lastName, emp.firstName ASC;";

            ResultSet rs = stmt.executeQuery(query);

            while (rs.next())
            {
                Salesmen sal = new Salesmen();
                SetSalesmenFromResultSet(sal, rs);

                salList.add(sal);
            }

            rs.close();
            stmt.close();

            return salList;
        }
        catch (SQLException ex)
        {
            System.out.println(ex.toString());
            return null;
        }

    }

    /**
     *
     * @param ID type Integer, the employee ID of the salesmen.
     * @return Integer, returns the salesmen information associated with the employee information.
     * @throws SQLException if query fails.
     */
    public Salesmen GetSalesmenByEmployeeID(int ID) throws SQLException
    {
        try
        {
            if (con.isClosed())
            {
                con = CheckDBConnection.Check(dbs, con);
            }
        }
        catch (SQLException sex)
        {
            System.out.println(sex.toString());
            return null;
        }

        Salesmen sal = new Salesmen();

        try
        {
            String query = "SELECT * FROM " + table + " "
                    + "WHERE `employeeID` = ?";

            PreparedStatement pStmt = con.prepareStatement(query);
            
            SQLUtil.SafeSetInteger(pStmt, 1, ID);
            //pStmt.setInt(1, ID);

            ResultSet rs = pStmt.executeQuery();

            if (rs.next())
            {
                SetSalesmenFromResultSet(sal, rs);
            }

            rs.close();
            pStmt.close();

            return sal;
        }
        catch (SQLException ex)
        {
            System.out.println(ex.toString());
            return null;
        }
    }

    private String GenerateInsertStatement(ArrayList<String> fields)
    {

        String stmt = "INSERT INTO " + table + "(";
        String values = ") values (";
        for (int i = 0; i < fields.size(); ++i)
        {
            stmt += " `" + fields.get(i) + "`";
            values += "?";
            if (i != fields.size() - 1)
            {
                stmt += ", ";
                values += ",";
            }
        }
        values += ")";
        return stmt + values;
    }

    private String GenerateUpdateStatement(ArrayList<String> fields)
    {

        String stmt = "UPDATE " + table + " SET";
        for (int i = 0; i < fields.size(); ++i)
        {
            stmt += " `" + fields.get(i) + "` = ?";
            if (i != fields.size() - 1)
            {
                stmt += ",";
            }
        }
        return stmt;
    }

    private Salesmen SetSalesmenFromResultSet(Salesmen sales, ResultSet rs) throws SQLException
    {
        sales.setIdsalesmen(rs.getInt("idsalesmen"));
        sales.setEmployeeID(rs.getInt("employeeid"));
        sales.setCommisionRate(rs.getBigDecimal("commisionRate"));
        sales.setTerritory(rs.getInt("territory"));
        sales.setClassification(rs.getInt("classification"));
        sales.setSalesGroup(rs.getInt("salesGroup"));
        sales.setByOrders(rs.getBoolean("byOrders"));
        sales.setByTests(rs.getBoolean("byTests"));
        sales.setByBilled(rs.getBoolean("byBilled"));
        sales.setByReceived(rs.getBoolean("byReceived"));
        sales.setByGroup(rs.getBoolean("byGroup"));
        sales.setByPercentage(rs.getBoolean("byPercentage"));
        sales.setByAmount(rs.getBoolean("byAmount"));
        sales.setCreated(rs.getTimestamp("created"));
        sales.setCreatedBy(rs.getInt("createdBy"));

        return sales;
    }

    private PreparedStatement SetStatementFromSalesmen(Salesmen sal, PreparedStatement pStmt) throws SQLException
    {
        SQLUtil.SafeSetInteger(pStmt, 1, sal.getEmployeeID());
        pStmt.setBigDecimal(2, sal.getCommisionRate());
        SQLUtil.SafeSetInteger(pStmt, 3, sal.getTerritory());
        SQLUtil.SafeSetInteger(pStmt, 4, sal.getClassification());
        SQLUtil.SafeSetInteger(pStmt, 5, sal.getSalesGroup());
        SQLUtil.SafeSetBoolean(pStmt, 6, sal.getByOrders());
        SQLUtil.SafeSetBoolean(pStmt, 7, sal.getByTests());
        SQLUtil.SafeSetBoolean(pStmt, 8, sal.getByBilled());
        SQLUtil.SafeSetBoolean(pStmt, 9, sal.getByReceived());
        SQLUtil.SafeSetBoolean(pStmt, 10, sal.getByGroup());
        SQLUtil.SafeSetBoolean(pStmt, 11, sal.getByPercentage());
        SQLUtil.SafeSetBoolean(pStmt, 12, sal.getByAmount());
        SQLUtil.SafeSetTimeStamp(pStmt, 13, Convert.ToSQLDateTime(sal.getCreated()));
        SQLUtil.SafeSetInteger(pStmt, 14, sal.getCreatedBy());

        return pStmt;
    }
    
    @Override
    public String structureCheck() {
        return DatabaseStructureCheck.structureCheck(fields, table, con);
    }
}
