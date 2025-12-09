package DAOS;

/**
 * @date: Feb 13, 2014
 * @author: Keith Maggio <keith@csslis.com>
 *
 * @project: CSSLibrary
 * @package: Expression package is undefined on line 13, column 16 in
 * Templates/Classes/CSSLibrary/DAO.java.
 * @file name: TransLog.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc. All Rights Reserved
 */
import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IStructureCheckable;
import DOS.TransLog;
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

public class TransLogDAO implements DAOInterface, IStructureCheckable {

    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`transLog`";
    /**
     * All fields except ID
     */
    private final ArrayList<String> fields = new ArrayList<String>();

    public TransLogDAO() {
        fields.add("ReleaseJobID");
        fields.add("idOrders");
        //fields.add("created");
        fields.add("transDate");
        fields.add("transmitted");
        fields.add("billDate");
        fields.add("billingInterface");
    }

    public boolean InsertTransLog(TransLog transLog) throws SQLException {
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


            SetStatementFromTransLog(transLog, pStmt);

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

    public boolean UpdateTransLog(TransLog transLog) throws SQLException {
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

            SetStatementFromTransLog(transLog, pStmt);

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

    public TransLog GetTransLog(int ID) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        try {
            TransLog transLog = new TransLog();
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + " WHERE `idTransLog` = " + ID;

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                SetTransLogFromResultSet(transLog, rs);
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
    
    public ArrayList<TransLog> GetByOrderId(int orderId)
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
            ArrayList<TransLog> transLogs = new ArrayList<>();
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + " WHERE `idOrders` = " + orderId;

            ResultSet rs = stmt.executeQuery(query);

            while (rs.next())
            {
                TransLog transLog = new TransLog();
                SetTransLogFromResultSet(transLog, rs);
                transLogs.add(transLog);
            }

            rs.close();
            stmt.close();

            return transLogs;
        } 
        catch (SQLException ex)
        {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return null;        
        }
    }
    private TransLog SetTransLogFromResultSet(TransLog obj, ResultSet rs) throws SQLException {
        obj.setIdTransLog(rs.getInt("idTransLog"));
        obj.setReleaseJobID(rs.getInt("ReleaseJobID"));
        obj.setIdOrders(rs.getInt("idOrders"));
        obj.setTransDate(rs.getDate("created"));
        obj.setTransDate(rs.getDate("transDate"));
        obj.setTransmitted(rs.getBoolean("transmitted"));
        obj.setTransDate(rs.getDate("billDate"));
        obj.setTransmitted(rs.getBoolean("billingInterface"));
        return obj;
    }

    private PreparedStatement SetStatementFromTransLog(TransLog obj, PreparedStatement pStmt) throws SQLException {
        SQLUtil.SafeSetInteger(pStmt, 1, obj.getReleaseJobID());
        pStmt.setInt(2, obj.getIdOrders());
        // Created date omitted. Only updated by server.
        SQLUtil.SafeSetTimeStamp(pStmt, 3, Convert.ToSQLDate(obj.getTransDate()));
        pStmt.setBoolean(4, obj.getTransmitted());
        SQLUtil.SafeSetTimeStamp(pStmt, 5, Convert.ToSQLDate(obj.getBillDate()));
        pStmt.setBoolean(6, obj.getBillingInterface());
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
            return InsertTransLog((TransLog)obj);
        }
        catch (SQLException ex)
        {
            Logger.getLogger(TransLogDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public Boolean Update(Serializable obj)
    {
        try
        {
            return UpdateTransLog((TransLog)obj);
        }
        catch (SQLException ex)
        {
            Logger.getLogger(TransLogDAO.class.getName()).log(Level.SEVERE, null, ex);
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
            return GetTransLog(ID);
        }
        catch (SQLException ex)
        {
            Logger.getLogger(TransLogDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    @Override
    public String structureCheck() {
        return DatabaseStructureCheck.structureCheck(fields, table, con);
    }
}
