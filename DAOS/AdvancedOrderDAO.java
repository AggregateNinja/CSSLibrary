package DAOS;

import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IAdvancedOrderDAO;
import DAOS.IDAOS.IStructureCheckable;
import DOS.AdvancedOrders;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.Convert;
import Utility.SQLUtil;
import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static Utility.SQLUtil.createStatement;

/**
 * @date: Jan 28, 2014
 * @author: Keith Maggio <keith@csslis.com>
 *
 * @project: CSSLibrary
 * @package: DAOS
 * @file name: AdvancedOrderDAO.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc. All Rights Reserved
 */
public class AdvancedOrderDAO implements IAdvancedOrderDAO, DAOInterface, IStructureCheckable {

    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`advancedOrders`";
    /**
     * All fields except ID
     */
    private final ArrayList<String> fields = new ArrayList<String>();

    public AdvancedOrderDAO() {
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
        fields.add("inactive");
    }

    public boolean InsertAdvancedOrder(AdvancedOrders advOrder) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }

        try {
            String stmt = GenerateInsertStatement(fields);
            PreparedStatement pStmt = con.prepareStatement(stmt);


            SetStatementFromAdvancedOrders(advOrder, pStmt);

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
    
    public Integer InsertAdvancedOrderGetNewID(AdvancedOrders advOrder) throws SQLException
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
            String stmt = GenerateInsertStatement(fields);
            PreparedStatement pStmt = con.prepareStatement(stmt,
                    Statement.RETURN_GENERATED_KEYS);

            Integer newId = null;
            SetStatementFromAdvancedOrders(advOrder, pStmt);

            pStmt.executeUpdate();

            ResultSet rs = pStmt.getGeneratedKeys();
            if (rs.next())
            {
                newId = rs.getInt(1);
            }
            
            pStmt.close();

            return newId;
        } catch (Exception ex) {
            String message = ex.toString();
            //TODO:  Add logging for failures
            System.out.println(message);
            return null;
        }        
    }
    
    @Override
    public boolean UpdateAdvancedOrder(AdvancedOrders advOrder) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }

        try {


            String stmt = GenerateUpdateStatement(fields)
                    + " WHERE `idAdvancedOrders` = " + advOrder.getIdAdvancedOrders();

            PreparedStatement pStmt = con.prepareStatement(stmt);

            SetStatementFromAdvancedOrders(advOrder, pStmt);

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

    @Override
    public AdvancedOrders GetAdvancedOrder(int ID)
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
            AdvancedOrders advOrder = new AdvancedOrders();
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + "WHERE `idAdvancedOrders` = " + ID;

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                SetAdvancedOrdersFromResultSet(advOrder, rs);
            }

            rs.close();
            stmt.close();

            return advOrder;
        } catch (Exception ex) {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }
    
    /**
     * Gets the active advanced order from the supplied advanced order Id.
     *  If not found or not active, returns null;
     * @return 
     */
    public AdvancedOrders GetActiveAdvancedOrder(Integer ID)
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
            AdvancedOrders advOrder = null;
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + " WHERE `idAdvancedOrders` = " + ID
                    + " AND inactive = 0";                    

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                advOrder = new AdvancedOrders();
                SetAdvancedOrdersFromResultSet(advOrder, rs);
            }

            rs.close();
            stmt.close();

            return advOrder;
        } catch (Exception ex) {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return null;
        }        
    }

    public AdvancedOrders GetAdvancedOrder(String Accession) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        try {
            AdvancedOrders advOrder = null;
            PreparedStatement stmt = null; //con.createStatement();

            String query = "SELECT * FROM " + table
                    + " WHERE `accession` = ?";

            stmt = createStatement(con, query, Accession);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                advOrder = new AdvancedOrders();
                SetAdvancedOrdersFromResultSet(advOrder, rs);
            }

            rs.close();
            stmt.close();

            return advOrder;
        } catch (Exception ex) {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }

    public Boolean Exists(String Accession) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        try {
            int x = 0;
            PreparedStatement stmt = null; //con.createStatement();

            String query = "SELECT COUNT(*) AS 'count' FROM " + table
                    + "WHERE `accession` = ?";

            stmt = createStatement(con, query, Accession);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                x = rs.getInt("count");
            }

            rs.close();
            stmt.close();

            return (x > 0);
        } catch (Exception ex) {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }

    public List<AdvancedOrders> SearchAdvancedOrders(String AccessionFragment) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        try {
            ArrayList<AdvancedOrders> aolist = new ArrayList<>();
            AdvancedOrders advOrd;
            PreparedStatement stmt = null; //con.createStatement();

            String query = "SELECT * FROM " + table
                    + "WHERE `accession` LIKE ?";
            String searchParam = SQLUtil.createSearchParam2Way(AccessionFragment);
            stmt = createStatement(con, query, searchParam);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                advOrd = new AdvancedOrders();
                SetAdvancedOrdersFromResultSet(advOrd, rs);
                aolist.add(advOrd);
            }

            rs.close();
            stmt.close();

            return aolist;
        } catch (Exception ex) {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }

    private AdvancedOrders SetAdvancedOrdersFromResultSet(AdvancedOrders obj, ResultSet rs) throws SQLException {
        obj.setIdAdvancedOrders(rs.getInt("idAdvancedOrders"));
        obj.setAccession(rs.getString("accession"));
        obj.setClientId(rs.getInt("clientId"));
        obj.setLocationId(rs.getInt("locationId"));
        obj.setDoctorId(rs.getInt("doctorId"));
        obj.setOrderDate(rs.getTimestamp("orderDate"));
        obj.setSpecimenDate(rs.getTimestamp("specimenDate"));
        obj.setPatientId(rs.getInt("patientId"));
        obj.setSubscriberId(rs.getInt("subscriberId"));
        obj.setIsAdvancedOrder(rs.getBoolean("isAdvancedOrder"));
        obj.setInsurance(rs.getInt("insurance"));
        obj.setSecondaryInsurance(rs.getInt("secondaryInsurance"));
        obj.setPolicyNumber(rs.getString("policyNumber"));
        obj.setGroupNumber(rs.getString("groupNumber"));
        obj.setSecondaryPolicyNumber(rs.getString("secondaryPolicyNumber"));
        obj.setSecondaryGroupNumber(rs.getString("secondaryGroupNumber"));
        obj.setMedicareNumber(rs.getString("medicareNumber"));
        obj.setMedicaidNumber(rs.getString("medicaidNumber"));
        obj.setReportType(rs.getInt("reportType"));
        obj.setRequisition(rs.getInt("requisition"));
        obj.setBillOnly(rs.getBoolean("billOnly"));
        obj.setActive(rs.getBoolean("active"));
        obj.setHold(rs.getBoolean("hold"));
        obj.setStage(rs.getInt("stage"));
        obj.setHoldComment(rs.getString("holdComment"));
        obj.setResultComment(rs.getString("resultComment"));
        obj.setInternalComment(rs.getString("internalComment"));
        obj.setHl7Transmitted(rs.getBoolean("hl7Transmitted"));
        obj.setPayment(rs.getDouble("payment"));
        obj.setBillable(rs.getBoolean("billable"));
        obj.setEmrOrderId(rs.getString("emrOrderId"));
        obj.setRoom(rs.getString("Room"));
        obj.setBed(rs.getString("Bed"));
        obj.setIsFasting(rs.getBoolean("isFasting"));
        obj.setDOI(rs.getDate("DOI"));
        obj.setEOA(rs.getString("EOA"));
        obj.setReleaseJobID(rs.getInt("ReleaseJobID"));
        obj.setReleaseDate(rs.getTimestamp("ReleaseDate"));
        obj.setInactive(rs.getBoolean("inactive"));

        return obj;
    }

    private PreparedStatement SetStatementFromAdvancedOrders(AdvancedOrders obj, PreparedStatement pStmt) throws SQLException {
            pStmt.setString(1, obj.getAccession());
            pStmt.setInt(2, obj.getClientId());
            pStmt.setInt(3, obj.getLocationId());
            SQLUtil.SafeSetInteger(pStmt, 4, obj.getDoctorId());
            pStmt.setTimestamp(5, Convert.ToSQLDateTime(obj.getOrderDate()));
            pStmt.setTimestamp(6, Convert.ToSQLDateTime(obj.getSpecimenDate()));
            pStmt.setInt(7, obj.getPatientId());
            SQLUtil.SafeSetInteger(pStmt, 8, obj.getSubscriberId());
            pStmt.setBoolean(9, obj.getIsAdvancedOrder());
            SQLUtil.SafeSetString(pStmt, 10, obj.getRoom());
            SQLUtil.SafeSetString(pStmt, 11, obj.getBed());
            pStmt.setBoolean(12, obj.getIsFasting());
            SQLUtil.SafeSetInteger(pStmt, 13, obj.getInsurance());
            SQLUtil.SafeSetInteger(pStmt, 14, obj.getSecondaryInsurance());
            SQLUtil.SafeSetString(pStmt, 15, obj.getPolicyNumber());
            SQLUtil.SafeSetString(pStmt, 16, obj.getGroupNumber());
            SQLUtil.SafeSetString(pStmt, 17, obj.getSecondaryPolicyNumber());
            SQLUtil.SafeSetString(pStmt, 18, obj.getSecondaryGroupNumber());
            SQLUtil.SafeSetString(pStmt, 19, obj.getMedicareNumber());
            SQLUtil.SafeSetString(pStmt, 20, obj.getMedicaidNumber());
            SQLUtil.SafeSetInteger(pStmt, 21, obj.getReportType());
            SQLUtil.SafeSetInteger(pStmt, 22, obj.getRequisition());
            SQLUtil.SafeSetBoolean(pStmt, 23, obj.isBillOnly());
            SQLUtil.SafeSetBoolean(pStmt, 24, obj.isActive());
            SQLUtil.SafeSetBoolean(pStmt, 25, obj.isHold());
            SQLUtil.SafeSetInteger(pStmt, 26, obj.getStage());
            SQLUtil.SafeSetString(pStmt, 27, obj.getHoldComment());
            SQLUtil.SafeSetString(pStmt, 28, obj.getResultComment());
            SQLUtil.SafeSetString(pStmt, 29, obj.getInternalComment());
            pStmt.setBoolean(30, obj.getHl7Transmitted());
            SQLUtil.SafeSetDouble(pStmt, 31, obj.getPayment());
            SQLUtil.SafeSetBoolean(pStmt, 32, obj.isBillable());
            SQLUtil.SafeSetString(pStmt, 33, obj.getEmrOrderId());
            SQLUtil.SafeSetDate(pStmt, 34, obj.getDOI());
            SQLUtil.SafeSetString(pStmt, 35, obj.getEOA());
            SQLUtil.SafeSetInteger(pStmt, 36, obj.getReleaseJobID());
            SQLUtil.SafeSetTimeStamp(pStmt, 37, obj.getReleaseDate());
            SQLUtil.SafeSetBoolean(pStmt, 38, obj.isInactive());
        
        
        return pStmt;
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
        return DatabaseStructureCheck.structureCheck(fields, table, con);
    }
}
