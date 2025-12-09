package DAOS;

import Billing.Database.BillingDatabaseSingleton;
import DAOS.IDAOS.IStructureCheckable;
import DOS.RemitInfo;
import Database.CheckDBConnection;
import Database.DatabaseSingleton;
import Database.DatabaseStructureCheck;
import Utility.Convert;
import Utility.SQLUtil;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static Utility.SQLUtil.createStatement;

public class RemitInfoDAO implements IStructureCheckable
{
    public static final String table = DatabaseSingleton.getDatabaseSingleton().getProperties().getBillingSchema() + ".`remitInfo`";

    public static RemitInfo insert(Connection con, RemitInfo obj)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (obj == null)
        {
            throw new IllegalArgumentException("RemitInfoDAO::insert:"
                    + " Received a [NULL] RemitInfo object");
        }

        if (con == null || false == con.isValid(2))
        {
            throw new IllegalArgumentException("RemitInfoDAO::insert:"
                    + " Received a [NULL] or invalid Connection object argument");
        }

        String sql = "INSERT INTO " + table
                + "("
                + "  payorid,"
                + "  filename,"
                + "  filedate,"
                + "  controlNumber,"
                + "  groupNumber,"
                + "  batchNumber,"
                + "  paymentMethod,"
                + "  paymentDate,"
                + "  paymentNumber,"
                + "  paymentAmount,"
                + "  created,"
                + "  processed,"
                + "  userid, "
                + "  active"
                + ")"
                + "VALUES (?,?,?,?,?,?,?,?,?,?,NOW(),?,?,?)";

        try (PreparedStatement pStmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS))
        {
            int i = 0;
            SQLUtil.SafeSetString(pStmt, ++i, obj.getPayorid());
            SQLUtil.SafeSetString(pStmt, ++i, obj.getFilename());
            SQLUtil.SafeSetTimeStamp(pStmt, ++i, Convert.ToSQLDateTime(obj.getFiledate()));
            SQLUtil.SafeSetString(pStmt, ++i, obj.getControlNumber());
            SQLUtil.SafeSetString(pStmt, ++i, obj.getGroupNumber());
            SQLUtil.SafeSetString(pStmt, ++i, obj.getBatchNumber());
            SQLUtil.SafeSetString(pStmt, ++i, obj.getPaymentMethod());
            SQLUtil.SafeSetDate(pStmt, ++i, obj.getPaymentDate());
            SQLUtil.SafeSetString(pStmt, ++i, obj.getPaymentNumber());
            SQLUtil.SafeSetTimeStamp(pStmt, ++i, Convert.ToSQLDateTime(obj.getProcessed()));
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getUserid());
            SQLUtil.SafeSetBoolean(pStmt, ++i, obj.isActive());

            Integer newId = null;
            pStmt.executeUpdate();
            ResultSet rs = pStmt.getGeneratedKeys();
            if (rs.next())
            {
                newId = rs.getInt(1);
            }
            pStmt.close();
            if (newId == null || newId <= 0)
            {
                throw new NullPointerException("RemitInfoDAO::Insert: Cannot retrieve generated identifier for inserted row.");
            }
            obj.setIdRemitInfo(newId);
        }
        catch (Exception ex)
        {
            System.out.println((ex != null && ex.getMessage() != null ? ex.getMessage() : "") + " " + sql.toString());
            throw ex;
        }

        return obj;
    }
    
    
    public static RemitInfo insert(RemitInfo obj)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (obj == null)
        {
            throw new IllegalArgumentException("RemitInfoDAO::insert:"
                    + " Received a [NULL] RemitInfo object");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }
        
        return insert(con, obj);
    }
    
    public static void update(Connection con, RemitInfo obj)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (obj == null)
        {
            throw new IllegalArgumentException("RemitInfoDAO::Update: Received a NULL RemitInfo object.");
        }

        if (con == null || false == con.isValid(2))
        {
            throw new IllegalArgumentException("RemitInfoDAO::insert:"
                    + " Received a [NULL] or invalid Connection object argument");
        }

        String sql = "UPDATE " + table + " SET "
                + " payorid = ?,"
                + " filename = ?,"
                + " filedate = ?,"
                + " controlNumber = ?,"
                + " groupNumber = ?,"
                + " batchNumber = ?,"
                + " paymentMethod = ?,"
                + " paymentDate = ?,"
                + " paymentNumber = ?,"
                + " paymentAmount = ?,"
                + " processed = ?,"
                + " userid = ?, "
                + " active = ?"
                + " WHERE idRemitInfo = " + obj.getIdRemitInfo();

        String sqlOutput = "";
        PreparedStatement pStmt = con.prepareStatement(sql);
        try
        {
            int i = 0;
            SQLUtil.SafeSetString(pStmt, ++i, obj.getPayorid());
            SQLUtil.SafeSetString(pStmt, ++i, obj.getFilename());
            SQLUtil.SafeSetTimeStamp(pStmt, ++i, Convert.ToSQLDateTime(obj.getFiledate()));
            SQLUtil.SafeSetString(pStmt, ++i, obj.getControlNumber());
            SQLUtil.SafeSetString(pStmt, ++i, obj.getGroupNumber());
            SQLUtil.SafeSetString(pStmt, ++i, obj.getBatchNumber());
            SQLUtil.SafeSetString(pStmt, ++i, obj.getPaymentMethod());
            SQLUtil.SafeSetTimeStamp(pStmt, ++i, Convert.ToSQLDateTime(obj.getPaymentDate()));
            SQLUtil.SafeSetString(pStmt, ++i, obj.getPaymentNumber());
            pStmt.setBigDecimal(++i, obj.getPaymentAmount());
            SQLUtil.SafeSetTimeStamp(pStmt, ++i, Convert.ToSQLDateTime(obj.getProcessed()));
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getUserid());
            SQLUtil.SafeSetBoolean(pStmt, ++i, obj.isActive());
            sqlOutput = pStmt.toString();
            pStmt.executeUpdate();
        }
        catch (Exception ex)
        {
            String errorMsg = ex.getMessage() + " " + sqlOutput;
            System.out.println(errorMsg);
            throw new SQLException(errorMsg);
        }
    }

    public static void update(RemitInfo obj)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (obj == null)
        {
            throw new IllegalArgumentException("RemitInfoDAO::Update: Received a NULL RemitInfo object.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        update(con, obj);
    }
    
    public static RemitInfo get(Connection con, Integer id, boolean forUpdate)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (id == null || id <= 0)
        {
            throw new IllegalArgumentException("RemitInfoDAO::get:"
                    + " Received a NULL or empty RemitInfo object.");
        }

        String sql = "SELECT * FROM " + table + " WHERE idRemitInfo = " + String.valueOf(id);

        if (forUpdate) sql += " FOR UPDATE";
        
        RemitInfo obj = null;
        try (PreparedStatement pStmt = con.prepareStatement(sql))
        {
            ResultSet rs = pStmt.executeQuery();
            if (rs.next())
            {
                obj = ObjectFromResultSet(rs);
            }
        }
        catch (SQLException ex)
        {
            System.out.println(ex.getMessage() + " " + sql);
            throw new SQLException(ex.getMessage() + " " + sql);
        }

        return obj;
    }

    public static RemitInfo get(Integer id)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (id == null || id <= 0)
        {
            throw new IllegalArgumentException("RemitInfoDAO::get:"
                    + " Received a NULL or empty RemitInfo object.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        return get(con, id, false);

    }

    public RemitInfo getRemit(int remitId)
    {
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
            Connection con = dbs.getConnection(true);
            try
            {
                if(con.isClosed())
                {
                    con = CheckDBConnection.Check(BillingDatabaseSingleton.ref, con);
                }
            }
            catch(SQLException ex)
            {
                System.out.println(ex.toString());
                return null;
            }
            
            try
            {
                RemitInfo remit = new RemitInfo();
                Statement stmt = con.createStatement();
                
                String query = "SELECT * FROM " + table + " WHERE idRemitInfo = " + remitId;
                
                ResultSet rs = stmt.executeQuery(query);
                
                while(rs.next())
                {
                    remit = ObjectFromResultSet(rs);
                }
                
                rs.close();
                stmt.close();
                return remit;
            }
            catch(Exception ex)
            {
                String message = ex.getMessage();
                System.out.println(message);
                return null;
            }
    }
    
    public List<RemitInfo> SearchRemitByControlID(String IDFragment)
    {
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
            Connection con = dbs.getConnection(true);
            
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
            
            List<RemitInfo> controlList = new ArrayList<>();
            try
            {
                PreparedStatement stmt = null;
                ResultSet rs = null;
                
                //stmt = con.createStatement();
                String query = "SELECT * FROM " + table + " "
                             + "WHERE `controlNumber` LIKE ? "
                             + "ORDER BY `controlNumber`";
                
                String idParam = SQLUtil.createSearchParam(IDFragment);
                stmt = createStatement(con, query, idParam);
                rs = stmt.executeQuery();
                
                while(rs.next())
                {
                    controlList.add(ObjectFromResultSet(rs));
                }
                rs.close();
                stmt.close();
                return controlList;
            }
            catch(Exception ex)
            {
                String message = ex.getMessage();
                System.out.println(message);
                return null;
            }
    }
    
    public List<RemitInfo> SearchRemitFromPaymentNumber(String NumFragment)
    {
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
            Connection con = dbs.getConnection(true);
            
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
            
            List<RemitInfo> paymentList = new ArrayList<>();
            try
            {
                PreparedStatement stmt = null;
                ResultSet rs = null;
                
                //stmt = con.createStatement();
                String query = "SELECT * FROM " + table + " WHERE `paymentNumber` LIKE ? ORDER BY `paymentNumber`";
                String numParam = SQLUtil.createSearchParam(NumFragment);
                stmt = createStatement(con, query, numParam);
                rs = stmt.executeQuery();
                
                while(rs.next())
                {
                    paymentList.add(ObjectFromResultSet(rs));
                }   
                
                rs.close();
                stmt.close();
                return paymentList;
            }
            catch(Exception ex)
            {
                System.out.println(ex.getMessage());
                return null;
            }
    }
    
    public static RemitInfo ObjectFromResultSet(ResultSet rs)
            throws SQLException, NullPointerException
    {
        RemitInfo obj = new RemitInfo();
        obj.setIdRemitInfo(rs.getInt("idRemitInfo"));
        obj.setPayorid(rs.getString("payorid"));
        obj.setFilename(rs.getString("filename"));
        obj.setFiledate(rs.getDate("filedate"));
        obj.setControlNumber(rs.getString("controlNumber"));
        obj.setGroupNumber(rs.getString("groupNumber"));
        obj.setBatchNumber(rs.getString("batchNumber"));
        obj.setPaymentMethod(rs.getString("paymentMethod"));
        obj.setPaymentNumber(rs.getString("paymentNumber"));
        obj.setPaymentAmount(rs.getBigDecimal("paymentAmount"));
        obj.setProcessed(rs.getDate("processed"));
        obj.setUserid(rs.getInt("userid"));
        obj.setActive(rs.getBoolean("active"));

        return obj;
    }
    
    @Override
    public String structureCheck() {
        String query = "SELECT `remitInfo`.`idRemitInfo`,\n"
                + "    `remitInfo`.`payorid`,\n"
                + "    `remitInfo`.`filename`,\n"
                + "    `remitInfo`.`filedate`,\n"
                + "    `remitInfo`.`controlNumber`,\n"
                + "    `remitInfo`.`groupNumber`,\n"
                + "    `remitInfo`.`batchNumber`,\n"
                + "    `remitInfo`.`paymentMethod`,\n"
                + "    `remitInfo`.`paymentDate`,\n"
                + "    `remitInfo`.`paymentNumber`,\n"
                + "    `remitInfo`.`paymentAmount`,\n"
                + "    `remitInfo`.`created`,\n"
                + "    `remitInfo`.`processed`,\n"
                + "    `remitInfo`.`userid`,\n"
                + "    `remitInfo`.`active`\n"
                + "FROM `cssbilling`.`remitInfo` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, Database.DatabaseSingleton.getDatabaseSingleton().getConnection(true));
    }
}
