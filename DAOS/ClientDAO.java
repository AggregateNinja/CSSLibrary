package DAOS;

/**
 * @date: Mar 12, 2012
 * @author: Keith Maggio
 *
 * @project: CSSLibrary
 * @package: DAOS
 * @file name: ClientDAO.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc. All Rights Reserved
 */
import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IStructureCheckable;
import DOS.Clients;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.Convert;
import Utility.SQLUtil;
import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static Utility.SQLUtil.createStatement;

public class ClientDAO implements DAOInterface, IStructureCheckable {

    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private static final String table = "`clients`";
    /**
     * All fields except idClients
     */
    private final ArrayList<String> fields = new ArrayList<String>();

    public ClientDAO() {
        fields.add("clientNo");
        fields.add("clientName");
        fields.add("lastName");
        fields.add("firstName");
        fields.add("title");
        fields.add("clientStreet");
        fields.add("clientStreet2");
        fields.add("clientCity");
        fields.add("clientState");
        fields.add("clientZip");
        fields.add("phoneNo");
        fields.add("faxNo");
        fields.add("transmitNo");
        fields.add("licenseNo");
        fields.add("upin");
        fields.add("otherId");
        fields.add("npi");
        fields.add("webEnabled");
        fields.add("resultCopies");
        fields.add("feeSchedule");
        fields.add("route");
        fields.add("stopNo");
        fields.add("pickupTime");
        fields.add("salesmen");
        fields.add("location");
        fields.add("resReport1");
        fields.add("resReport2");
        fields.add("resReport3");
        fields.add("billType");
        fields.add("resPrint");
        fields.add("transType");
        fields.add("statCode");
        fields.add("contact1");
        fields.add("contact2");
        fields.add("email");
        fields.add("hl7Enabled");
        fields.add("cliComment");
        fields.add("password");
        fields.add("clientType");
        fields.add("procedureset");
        fields.add("percentDiscount");
        fields.add("discountVolume");
        fields.add("referenceDiscount");
        fields.add("defaultReportType");
        fields.add("billingId");
        fields.add("transmitEMRPdf");
        fields.add("pdfToOBX");
        fields.add("emrInterface");
        fields.add("active");
        fields.add("ppsBilling");
        fields.add("stmtTerms");
        fields.add("stmtComment");
    }

    public boolean InsertClient(Clients client) throws SQLException {
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


            SetStatementFromClient(client, pStmt);
            pStmt.executeUpdate();

            pStmt.close();

            return true;
        } catch (Exception ex) {
            String message = "Client " + client.getClientNo() + ": " + ex.toString();
            //TODO:  Add logging for failures
            System.out.println(message);
            return false;
        }
    }

    public boolean UpdateClient(Clients client) throws SQLException {
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
                    + " WHERE `idClients` = " + client.getIdClients();

            PreparedStatement pStmt = con.prepareStatement(stmt);

            SetStatementFromClient(client, pStmt);

            pStmt.executeUpdate();

            pStmt.close();

            return true;
        } catch (Exception ex) {
            String message = "Client " + client.getClientNo() + ": " + ex.toString();
            //TODO:  Add exception handleing
            System.out.println(message);
            return false;
        }
    }

    public Clients GetClient(int ClientNumber) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        try {
            Clients client = new Clients();
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + "WHERE `clientNo` = " + ClientNumber;

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                SetClientFromResultSet(client, rs);

            }

            rs.close();
            stmt.close();

            return client;
        } catch (Exception ex) {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }

    public Clients GetClientById(int Id) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        try {
            Clients client = new Clients();
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + " WHERE `idClients` = " + Id;

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {

                SetClientFromResultSet(client, rs);
            }

            rs.close();
            stmt.close();

            return client;
        } catch (Exception ex) {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }

    public boolean ClientExists(int clientNo) {
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
            int rowCount = -1;

            String query = "SELECT COUNT(*) FROM " + table
                    + " WHERE `clientNo` = " + clientNo;

            ResultSet rs = stmt.executeQuery(query);

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

    public int GetClientId(int clientNo) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return -1;
        }

        try {
            Statement stmt = con.createStatement();
            int cliId = 0;

            String query = "SELECT `idClients` FROM " + table
                    + " WHERE `clientNo` = " + clientNo;

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                cliId = rs.getInt("idClients");
            }

            rs.close();
            stmt.close();

            return cliId;

        } catch (Exception ex) {
            String message = ex.toString();
            System.out.println(message);
            return 0;
        }
    }

    public List<Clients> SearchClientByNumber(String NumberFragment) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            List<Clients> clist = new ArrayList<>();
            

            String query = "SELECT * FROM " + table + " " +
                "WHERE `clientNo` REGEXP ? " +
                " ORDER BY `clientNo` ASC;";
            

            stmt = createStatement(con, query, NumberFragment);

            rs = stmt.executeQuery();

            while (rs.next()) {
                Clients c = new Clients();
                clist.add(SetClientFromResultSet(c, rs));
            }

            rs.close();
            stmt.close();
            return clist;
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            if (rs != null)
            {
                try
                {
                    rs.close();
                }
                catch (SQLException ex1)
                {
                    System.err.println("And then... " + ex1.getMessage());
                }
            }
            if (stmt != null)
            {
                try
                {
                    stmt.close();
                }
                catch (SQLException ex1)
                {
                    System.err.println("And then... " + ex1.getMessage());
                }
            }
            return null;
        }
        
    }
    
    public List<Clients> SearchClientByName(String NameFragment, boolean CaseSensitive, boolean ActiveOnly) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        List<Clients> clist = new ArrayList<>();
        try {
            //Statement stmt = null;
            PreparedStatement stmt = null;
            ResultSet rs = null;

            //stmt = con.createStatement();

            String query = "SELECT * FROM " + table + " ";
//            if (CaseSensitive == false) {
//                query += "WHERE (LOWER(`clientName`) LIKE LOWER(\"" + NameFragment + "%\") "
//                        + "OR LOWER(`lastName`) LIKE LOWER(\"" + NameFragment + "%\")) ";
//            } else {
//                query += "WHERE (`clientName` LIKE \"" + NameFragment + "%\" "
//                        + "OR `lastName` LIKE \"" + NameFragment + "%\") ";
//            }
            
            if (CaseSensitive == false) {
                query += "WHERE (LOWER(`clientName`) LIKE LOWER(?) "
                        + "OR LOWER(`lastName`) LIKE LOWER(?)) ";
            } else {
                query += "WHERE (`clientName` LIKE ? "
                        + "OR `lastName` LIKE ?) ";
            }
            
            if(ActiveOnly)
            {
                query += "AND `active` = true ";
            }
            query += "ORDER BY `clientName` ASC ";
            
            String searchParam = NameFragment + "%";
            stmt = createStatement(con, query, searchParam, searchParam);

            //rs = stmt.executeQuery(query);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Clients c = new Clients();
                clist.add(SetClientFromResultSet(c, rs));
            }

            rs.close();
            stmt.close();
            return clist;
        } catch (Exception ex) {
              ex.printStackTrace();
//            String message = ex.toString();
//            System.out.println(message);
            return null;
        }
    }
    
    public List<Clients> SearchClientByNameAndRoute(String NameFragment, Integer routeId, boolean CaseSensitive, boolean ActiveOnly) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        List<Clients> clist = new ArrayList<>();
        try {
            PreparedStatement stmt = null;
            ResultSet rs = null;

            //stmt = con.createStatement();

            String query = "SELECT * FROM " + table + " ";
            if (CaseSensitive == false) {
                query += "WHERE (LOWER(`clientName`) LIKE LOWER(?) "
                        + "OR LOWER(`lastName`) LIKE LOWER(?)) ";
            } else {
                query += "WHERE (`clientName` LIKE ? "
                        + "OR `lastName` LIKE ?) ";
            }
            
            if (routeId != null)
            {
                query += "AND `route` = " + routeId + " ";
            }
            
            if(ActiveOnly)
            {
                query += "AND `active` = true ";
            }
            query += "ORDER BY `clientName` ASC ";

            
            String searchParam = NameFragment + "%";
            stmt = createStatement(con, query, searchParam, searchParam);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Clients c = new Clients();
                clist.add(SetClientFromResultSet(c, rs));
            }

            rs.close();
            stmt.close();
            return clist;
        } catch (Exception ex) {
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }
    
    public List<Clients> SearchClientByName(String NameFragment, boolean CaseSensitive) {

        return SearchClientByName(NameFragment, CaseSensitive, false);
    }

    public List<Clients> SearchClientByName(String NameFragment) {

        return SearchClientByName(NameFragment, false, false);
    }
    
    public Integer GetMinNextAvailableClientNumber()
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
            int max = -1;
            Statement stmt = null;
            String query = "SELECT `c1`.`clientNo` AS 'Next' " +
                "FROM `clients` `c1` " +
                "LEFT JOIN `clients` `c2` ON `c1`.`clientNo` + 1 = `c2`.`clientNo` " +
                "WHERE `c2`.`clientNo` IS NULL " +
                "ORDER BY `c1`.`clientNo` ASC " +
                "LIMIT 1;";
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

    public Integer GetNextAvailableClientNumber() {
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
            String query = "SELECT MAX(`clientNo`) AS 'Next' FROM " + table + ";";
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
    
    /**
     * Uses the EMR x-ref table to determine whether this client has
     *  a bi-directional interface.
     * @param idClients
     * @return Boolean null on error
     */
    public Boolean HasBiDirectionalInterface(int idClients)
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
            String sql = "SELECT isBiDirectional"
                + " FROM emrXref x"
                + " INNER JOIN clients c on x.idEmrXref = c.emrInterface"
                + " WHERE c.idClients = " + idClients;
            
            Boolean hasBiDirectionalInterface = null;
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next()) {
                hasBiDirectionalInterface = rs.getBoolean("isBiDirectional");
            }

            rs.close();
            stmt.close();

            return hasBiDirectionalInterface;
        }
        catch (SQLException ex)
        {
            String message = ex.toString();
            System.out.println(message);
            return null;
        }        
    }
    
    public static List<Clients> getClients(boolean activeOnly) throws SQLException
    {
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        
        String sql = "SELECT * FROM " + table;
        if (activeOnly) sql += " WHERE active = 1";
        
        PreparedStatement pStmt = con.prepareCall(sql);
        ResultSet rs = pStmt.executeQuery();
        List<Clients> clients = new ArrayList<>();
        while (rs.next())
        {
            clients.add(SetClientFromResultSet(new Clients(), rs));
        }
        
        return clients;        
    }

    public static Clients SetClientFromResultSet(Clients client, ResultSet rs) throws SQLException
    {
        client.setIdClients(rs.getInt("idClients"));
        client.setClientNo(rs.getInt("clientNo"));
        client.setClientName(rs.getString("clientName"));
        client.setLastName(rs.getString("lastName"));
        client.setFirstName(rs.getString("firstName"));
        client.setTitle(rs.getString("title"));
        client.setClientStreet(rs.getString("clientStreet"));
        client.setClientStreet2(rs.getString("clientStreet2"));
        client.setClientCity(rs.getString("clientCity"));
        client.setClientState(rs.getString("clientState"));
        client.setClientZip(rs.getString("clientZip"));
        client.setPhoneNo(rs.getString("phoneNo"));
        client.setFaxNo(rs.getString("faxNo"));
        client.setTransmitNo(rs.getString("transmitNo"));
        client.setLicenseNo(rs.getString("licenseNo"));
        client.setUpin(rs.getString("upin"));
        client.setOtherId(rs.getString("otherId"));
        client.setNpi(rs.getString("npi"));
        client.setWebEnabled(rs.getBoolean("webEnabled"));
        client.setResultCopies(rs.getInt("resultCopies"));
        client.setFeeSchedule(rs.getInt("feeSchedule"));
        client.setRoute(rs.getInt("route"));
        client.setStopNo(rs.getString("stopNo"));
        client.setPickupTime(rs.getDate("pickupTime"));
        client.setSalesmen(rs.getInt("salesmen"));
        client.setLocation(rs.getInt("location"));
        client.setResReport1(rs.getString("resReport1"));
        client.setResReport2(rs.getString("resReport2"));
        client.setResReport3(rs.getString("resReport3"));
        client.setBillType(rs.getInt("billType"));
        client.setResPrint(rs.getInt("resPrint"));
        client.setTransType(rs.getInt("transType"));
        client.setStatCode(rs.getInt("statCode"));
        client.setContact1(rs.getString("contact1"));
        client.setContact2(rs.getString("contact2"));
        client.setEmail(rs.getString("email"));
        client.setHl7Enabled(rs.getBoolean("hl7Enabled"));
        client.setCliComment(rs.getBytes("cliComment"));
        client.setPassword(rs.getString("password"));
        client.setClientType(rs.getInt("clientType"));
        client.setProcedureset(rs.getInt("procedureset"));
        client.setPercentDiscount(rs.getLong("percentDiscount"));
        client.setDiscountVolume(rs.getInt("discountVolume"));
        client.setReferenceDiscount(rs.getLong("referenceDiscount"));
        client.setDefaultReportType(rs.getInt("defaultReportType"));
        client.setBillingId(rs.getInt("billingId"));
        client.setTransmitEMRPdf(rs.getBoolean("transmitEMRPdf"));
        client.setPdfToOBX(rs.getBoolean("pdfToOBX"));
        client.setEmrInterface(rs.getInt("emrInterface"));
        client.setActive(rs.getBoolean("active"));
        client.setPPSBilling(rs.getBoolean("ppsBilling"));
        client.setStmtTerms(rs.getInt("stmtTerms"));
        client.setStmtComment(rs.getString("stmtComment"));
        
        return client;
    }

    private PreparedStatement SetStatementFromClient(Clients client, PreparedStatement pStmt) throws SQLException {
        pStmt.setInt(1, client.getClientNo());
        pStmt.setString(2, client.getClientName());
        pStmt.setString(3, client.getLastName());
        pStmt.setString(4, client.getFirstName());
        pStmt.setString(5, client.getTitle());
        pStmt.setString(6, client.getClientStreet());
        pStmt.setString(7, client.getClientStreet2());
        pStmt.setString(8, client.getClientCity());
        pStmt.setString(9, client.getClientState());
        pStmt.setString(10, client.getClientZip());
        pStmt.setString(11, client.getPhoneNo());
        pStmt.setString(12, client.getFaxNo());
        pStmt.setString(13, client.getTransmitNo());
        pStmt.setString(14, client.getLicenseNo());
        pStmt.setString(15, client.getUpin());
        pStmt.setString(16, client.getOtherId());
        pStmt.setString(17, client.getNpi());
        pStmt.setBoolean(18, client.getWebEnabled());
        SQLUtil.SafeSetInteger(pStmt, 19, client.getResultCopies());
        SQLUtil.SafeSetInteger(pStmt, 20, client.getFeeSchedule());
        SQLUtil.SafeSetInteger(pStmt, 21, client.getRoute());
        pStmt.setString(22, client.getStopNo());
        SQLUtil.SafeSetDate(pStmt, 23, client.getPickupTime());
        SQLUtil.SafeSetInteger(pStmt, 24, client.getSalesmen());
        pStmt.setInt(25, client.getLocation());
        pStmt.setString(26, client.getResReport1());
        pStmt.setString(27, client.getResReport2());
        pStmt.setString(28, client.getResReport3());
        pStmt.setInt(29, client.getBillType());
        pStmt.setInt(30, client.getResPrint());
        pStmt.setInt(31, client.getTransType());
        pStmt.setInt(32, client.getStatCode());
        pStmt.setString(33, client.getContact1());
        pStmt.setString(34, client.getContact2());
        pStmt.setString(35, client.getEmail());
        pStmt.setBoolean(36, client.getHl7Enabled());
        pStmt.setBytes(37, client.getCliComment());
        pStmt.setString(38, client.getPassword());
        pStmt.setInt(39, client.getClientType());
        SQLUtil.SafeSetInteger(pStmt, 40, client.getProcedureset());
        SQLUtil.SafeSetLong(pStmt, 41, client.getPercentDiscount());
        SQLUtil.SafeSetInteger(pStmt, 42, client.getDiscountVolume());
        SQLUtil.SafeSetLong(pStmt, 43, client.getReferenceDiscount());
        SQLUtil.SafeSetInteger(pStmt, 44, client.getDefaultReportType());
        SQLUtil.SafeSetInteger(pStmt, 45, client.getBillingId());
        SQLUtil.SafeSetBoolean(pStmt, 46, client.getTransmitEMRPdf());
        SQLUtil.SafeSetBoolean(pStmt, 47, client.isPdfToOBX());
        SQLUtil.SafeSetInteger(pStmt, 48, client.getEmrInterface());
        pStmt.setBoolean(49, client.isActive());
        pStmt.setBoolean(50, client.isPPSBilling() /*false*/); // Put back when PPSBilling feature is done
        SQLUtil.SafeSetInteger(pStmt, 51, client.getStmtTerms());
        pStmt.setString(52, client.getStmtComment());
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
        try
        {
            return InsertClient((Clients)obj);
        }
        catch (SQLException ex)
        {
            Logger.getLogger(ClientDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public Boolean Update(Serializable obj)
    {
        try
        {
            return UpdateClient((Clients)obj);
        }
        catch (SQLException ex)
        {
            Logger.getLogger(ClientDAO.class.getName()).log(Level.SEVERE, null, ex);
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
            return GetClientById(ID);
        }
        catch (SQLException ex)
        {
            Logger.getLogger(ClientDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public String structureCheck() {
        return DatabaseStructureCheck.structureCheck(fields, table, con);
    }
}
