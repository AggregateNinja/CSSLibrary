package DAOS;

/**
 * @date: Feb 13, 2014
 * @author: Keith Maggio <keith@csslis.com>
 *
 * @project: CSSLibrary
 * @package: Expression package is undefined on line 13, column 16 in
 * Templates/Classes/CSSLibrary/DAO.java.
 * @file name: BillTransLog.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc. All Rights Reserved
 */
import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IStructureCheckable;
import DOS.BillTransLog;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.Convert;
import Utility.SQLUtil;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BillTransLogDAO implements DAOInterface, IStructureCheckable {

    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`billTransLog`";
    /**
     * All fields except ID
     */
    private final ArrayList<String> fields = new ArrayList<String>();

    public BillTransLogDAO() {
        fields.add("ReleaseJobID");
        fields.add("idOrders");
        //fields.add("created");
        fields.add("transDate");
        fields.add("transmitted");
        fields.add("billDate");
        fields.add("billingInterface");
        fields.add("idUser");
    }

    public boolean InsertBillTransLog(BillTransLog transLog) throws SQLException {
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


            SetStatementFromBillTransLog(transLog, pStmt);

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

    public boolean UpdateBillTransLog(BillTransLog transLog) throws SQLException {
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
                    + " WHERE `idTransLog` = " + transLog.getIdTransLog();

            PreparedStatement pStmt = con.prepareStatement(stmt);

            SetStatementFromBillTransLog(transLog, pStmt);

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

    public BillTransLog GetBillTransLog(int ID) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        try {
            BillTransLog transLog = new BillTransLog();
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + " WHERE `idBillTransLog` = " + ID;

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                SetBillTransLogFromResultSet(transLog, rs);
            }

            rs.close();
            stmt.close();

            return transLog;
        } catch (Exception ex) {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }
    
    public ArrayList<BillTransLog> GetLogsByOrderId(int idOrders) throws Exception
    {        
        if (con.isClosed()) con = CheckDBConnection.Check(dbs, con);
        
        ArrayList<BillTransLog> logs = new ArrayList<>();

        String query = "SELECT * FROM " + table
                + " WHERE `idOrders` = ?";

        PreparedStatement pStmt = con.prepareStatement(query);
        pStmt.setInt(1, idOrders);
        ResultSet rs = pStmt.executeQuery();

        while (rs.next())
        {
            BillTransLog btl = new BillTransLog();
            btl = SetBillTransLogFromResultSet(btl, rs);
            if (btl != null && btl.getIdTransLog() != null && btl.getIdTransLog() > 0)
            {
                logs.add(btl);
            }
        }

        rs.close();
        pStmt.close();

        return logs;
    }
    
    public static ArrayList<BillTransLog> getAllQueued()
            throws SQLException, NullPointerException
    {
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }
        ArrayList<BillTransLog> logs = new ArrayList<>();
        
        String query = "SELECT * FROM `css`.`billTransLog` WHERE billingInterface = b'0'";
        
        PreparedStatement pStmt = con.prepareStatement(query);
        ResultSet rs = pStmt.executeQuery();
        
        while (rs.next())
        {
            BillTransLog log = SetBillTransLogFromResultSet(new BillTransLog(), rs);
            if (log == null || log.getIdTransLog() == null || log.getIdTransLog() == 0 || log.getIdOrders() <= 0)
                throw new SQLException("Ran " + query + " and received a bill trans log that was NULL or had a NULL idOrders value");
            logs.add(log);
        }
        
        pStmt.close();
        return logs;
    }
    
    public static void update(BillTransLog obj, String schemaName, String tableName)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (obj == null)
        {
            throw new IllegalArgumentException("BillTransLogDAO::update: Received a NULL BillTransLog object.");
        }
        
        if (schemaName == null || schemaName.isEmpty())
        {
            throw new IllegalArgumentException("BillTransLogDAO::update"
                    + " (BillTransLog, String schemaName, String tableName):"
                    + " Received a [NULL] schema name string argument");
        }

        if (tableName == null || tableName.isEmpty())
        {
            throw new IllegalArgumentException("BillTransLogDAO::update"
                    + " (BillTransLog, String schemaName, String tableName):"
                    + " Received a [NULL] bill trans log table name string argument");
        }
        
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "UPDATE `" + schemaName + "`.`" + tableName + "` SET "
                + " ReleaseJobID = ?,"
                + " idOrders = ?,"
                + " transDate = ?,"
                + " transmitted = ?,"
                + " billDate = ?,"
                + " billingInterface = ?,"
                + " idUser = ?"
                + " WHERE idTransLog = " + obj.getIdTransLog();

        String sqlOutput = "";
        PreparedStatement pStmt = con.prepareStatement(sql);
        try
        {
            int i = 0;
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getReleaseJobID());
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getIdOrders());
            SQLUtil.SafeSetTimeStamp(pStmt, ++i, Convert.ToSQLDateTime(obj.getTransDate()));
            SQLUtil.SafeSetBoolean(pStmt, ++i, obj.getTransmitted());
            SQLUtil.SafeSetTimeStamp(pStmt, ++i, Convert.ToSQLDateTime(obj.getBillDate()));
            SQLUtil.SafeSetBoolean(pStmt, ++i, obj.getBillingInterface());
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getIdUser());
            sqlOutput = pStmt.toString();
            int affected = pStmt.executeUpdate();
            if (affected == 0)
            {
                throw new Exception("Updating billTransLog to mark orderId "
                        + obj.getIdOrders() + " sent to billing, but zero rows"
                        + " were affected by update.");
            }
        }
        catch (Exception ex)
        {
            String errorMsg = ex.getMessage() + " " + sqlOutput;
            System.out.println(errorMsg);
            throw new SQLException(errorMsg);
        }
    }

    public static void update(BillTransLog obj)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        update(obj, "css", "billTransLog");
    }

    public static BillTransLog SetBillTransLogFromResultSet(BillTransLog obj, ResultSet rs)
            throws SQLException 
    {
        obj.setIdTransLog(rs.getInt("idTransLog"));
        obj.setReleaseJobID(rs.getInt("ReleaseJobID"));
        obj.setIdOrders(rs.getInt("idOrders"));
        obj.setCreated(rs.getDate("created"));
        obj.setTransDate(rs.getDate("transDate"));
        obj.setTransmitted(rs.getBoolean("transmitted"));
        obj.setBillDate(rs.getDate("billDate"));
        obj.setBillingInterface(rs.getBoolean("billingInterface"));
        obj.setIdUser(rs.getInt("idUser"));
        return obj;
    }

    private PreparedStatement SetStatementFromBillTransLog(BillTransLog obj, PreparedStatement pStmt) throws SQLException {
        SQLUtil.SafeSetInteger(pStmt, 1, obj.getReleaseJobID());
        pStmt.setInt(2, obj.getIdOrders());
        // Created date omitted. Only updated by server.
        SQLUtil.SafeSetTimeStamp(pStmt, 3, Convert.ToSQLDate(obj.getTransDate()));
        pStmt.setBoolean(4, obj.getTransmitted());
        SQLUtil.SafeSetTimeStamp(pStmt, 5, Convert.ToSQLDate(obj.getBillDate()));
        pStmt.setBoolean(6, obj.getBillingInterface());
        SQLUtil.SafeSetInteger(pStmt, 7, obj.getIdUser());
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
            return InsertBillTransLog((BillTransLog)obj);
        }
        catch (SQLException ex)
        {
            Logger.getLogger(BillTransLogDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public Boolean Update(Serializable obj)
    {
        try
        {
            return UpdateBillTransLog((BillTransLog)obj);
        }
        catch (SQLException ex)
        {
            Logger.getLogger(BillTransLogDAO.class.getName()).log(Level.SEVERE, null, ex);
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
            return GetBillTransLog(ID);
        }
        catch (SQLException ex)
        {
            Logger.getLogger(BillTransLogDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public String structureCheck() {
        return DatabaseStructureCheck.structureCheck(fields, table, con);
    }
}
