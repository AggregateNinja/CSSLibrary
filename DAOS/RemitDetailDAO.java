package DAOS;

import DAOS.IDAOS.IStructureCheckable;
import DOS.RemitDetail;
import Database.CheckDBConnection;
import Database.DatabaseSingleton;
import Database.DatabaseStructureCheck;
import Utility.Convert;
import Utility.SQLUtil;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RemitDetailDAO implements Serializable, IStructureCheckable
{
    public static final String table = DatabaseSingleton.getDatabaseSingleton().getProperties().getBillingSchema() + ".`remitDetail`";

    public static RemitDetail insert(Connection con, RemitDetail obj)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (obj == null)
        {
            throw new IllegalArgumentException("RemitDetailDAO::insert:"
                    + " Received a NULL RemitDetail object");
        }
        
        if (con == null || false == con.isValid(2))
        {
            throw new IllegalArgumentException("RemitDetailDAO::Insert:"
                    + " Received a [NULL] or invalid Connection object");
        }
        
        String sql = "INSERT INTO " + table
                + "("
                + "  idRemitInfo,"
                + "  arNo,"
                + "  policyNumber,"
                + "  traceNumber,"
                + "  claimStatusCode,"
                + "  lastName,"
                + "  firstName,"
                + "  middleName,"
                + "  dateOfService,"
                + "  timeOfService,"
                + "  cptCode,"
                + "  cptModifiers,"
                + "  adjustmentCode,"
                + "  billedAmount,"
                + "  paidAmount,"
                + "  patientAmount,"
                + "  adjustmentAmount,"
                + "  itemControlNumber,"
                + "  created,"
                + "  processed,"
                + "  userid"
                + ")"
                + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,NOW(),?,?)";

        try (PreparedStatement pStmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS))
        {
            int i = 0;
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getIdRemitInfo());
            SQLUtil.SafeSetString(pStmt, ++i, obj.getArNo());
            SQLUtil.SafeSetString(pStmt, ++i, obj.getPolicyNumber());
            SQLUtil.SafeSetString(pStmt, ++i, obj.getTraceNumber());
            SQLUtil.SafeSetString(pStmt, ++i, obj.getClaimStatusCode());
            SQLUtil.SafeSetString(pStmt, ++i, obj.getLastName());
            SQLUtil.SafeSetString(pStmt, ++i, obj.getFirstName());
            SQLUtil.SafeSetString(pStmt, ++i, obj.getMiddleName());
            SQLUtil.SafeSetDate(pStmt, ++i, obj.getDateOfService());
            SQLUtil.SafeSetDate(pStmt, ++i, obj.getTimeOfService());
            SQLUtil.SafeSetString(pStmt, ++i, obj.getCptCode());
            SQLUtil.SafeSetString(pStmt, ++i, obj.getCptModifiers());
            SQLUtil.SafeSetString(pStmt, ++i, obj.getAdjustmentCode());
            SQLUtil.SafeSetString(pStmt, ++i, obj.getItemControlNumber());
            SQLUtil.SafeSetTimeStamp(pStmt, ++i, Convert.ToSQLDateTime(obj.getProcessed()));
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getUserid());
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
                throw new NullPointerException("RemitDetailDAO::Insert:"
                        + " Cannot retrieve generated identifier for inserted row.");
            }
            obj.setIdRemitDetails(newId);            
            
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage() + " " + sql);
            throw ex;
        }

        return obj;        
        
    }
    
    public static RemitDetail insert(RemitDetail obj)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (obj == null)
        {
            throw new IllegalArgumentException("RemitDetailDAO::Insert:"
                    + " Received a NULL RemitDetail object");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }
        
        return insert(con, obj);
    }
    
    public static void update(Connection con, RemitDetail obj)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (con == null || false == con.isValid(2))
        {
            throw new IllegalArgumentException("RemitDetailDAO::update:"
                    + " Received a [NULL] or invalid Connection object");
        }
        
        if (obj == null)
        {
            throw new IllegalArgumentException("RemitDetailDAO::update:"
                    + " Received a [NULL] RemitDetail object");
        }
        
        String sql = "UPDATE " + table + " SET "
                + " idRemitInfo = ?,"
                + " arNo = ?,"
                + " policyNumber = ?,"
                + " traceNumber = ?,"
                + " claimStatusCode = ?,"
                + " lastName = ?,"
                + " firstName = ?,"
                + " middleName = ?,"
                + " dateOfService = ?,"
                + " timeOfService = ?,"
                + " cptCode = ?,"
                + " cptModifiers = ?,"
                + " adjustmentCode = ?,"
                + " billedAmount = ?,"
                + " paidAmount = ?,"
                + " patientAmount = ?,"
                + " adjustmentAmount = ?,"
                + " itemControlNumber = ?,"
                + " processed = ?,"
                + " userid = ?"
                + " WHERE idRemitDetails = " + obj.getIdRemitDetails();

        String sqlOutput = "";
        try (PreparedStatement pStmt = con.prepareStatement(sql))
        {
            int i = 0;
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getIdRemitInfo());
            SQLUtil.SafeSetString(pStmt, ++i, obj.getArNo());
            SQLUtil.SafeSetString(pStmt, ++i, obj.getPolicyNumber());
            SQLUtil.SafeSetString(pStmt, ++i, obj.getTraceNumber());
            SQLUtil.SafeSetString(pStmt, ++i, obj.getClaimStatusCode());
            SQLUtil.SafeSetString(pStmt, ++i, obj.getLastName());
            SQLUtil.SafeSetString(pStmt, ++i, obj.getFirstName());
            SQLUtil.SafeSetString(pStmt, ++i, obj.getMiddleName());
            SQLUtil.SafeSetTimeStamp(pStmt, ++i, obj.getTimeOfService());
            SQLUtil.SafeSetString(pStmt, ++i, obj.getCptCode());
            SQLUtil.SafeSetString(pStmt, ++i, obj.getCptModifiers());
            SQLUtil.SafeSetString(pStmt, ++i, obj.getAdjustmentCode());
            pStmt.setBigDecimal(++i, obj.getBilledAmount());
            pStmt.setBigDecimal(++i, obj.getPaidAmount());
            pStmt.setBigDecimal(++i, obj.getPatientAmount());
            pStmt.setBigDecimal(++i, obj.getAdjustmentAmount());
            SQLUtil.SafeSetString(pStmt, ++i, obj.getItemControlNumber());
            SQLUtil.SafeSetTimeStamp(pStmt, ++i, Convert.ToSQLDateTime(obj.getProcessed()));
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getUserid());
            sqlOutput = pStmt.toString();
            pStmt.executeUpdate();
        }
        catch (Exception ex)
        {
            String errorMsg = (ex != null && ex.getMessage() != null ? ex.getMessage() : "" ) + " " + sqlOutput;
            System.out.println(errorMsg);
            throw new SQLException(errorMsg);
        }
    }

    public static void update(RemitDetail obj)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (obj == null)
        {
            throw new IllegalArgumentException("RemitDetailDAO::Update:"
                    + " Received a NULL RemitDetails object.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        update(con, obj);
    }
    
    public static RemitDetail get(Connection con, Integer id, boolean forUpdate)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (id == null || id <= 0)
        {
            throw new IllegalArgumentException("RemitDetailDAO::get:"
                    + " Received a NULL or empty RemitDetail object.");
        }
        
        if (con == null || false == con.isValid(2))
        {
            throw new IllegalArgumentException("RemitDetailDAO::get:"
                    + " Received a [NULL] or invalid Connection object");
        }


        String sql = "SELECT * FROM " + table + " WHERE idRemitDetails = " + String.valueOf(id);
        if (forUpdate) sql += " FOR UPDATE";
        
        RemitDetail obj = null;
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

    public static RemitDetail get(Integer id)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (id == null || id <= 0)
        {
            throw new IllegalArgumentException("RemitDetailDAO::get:"
                    + " Received a NULL or empty RemitDetail object.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        return get(con, id, false);
    }

    public static RemitDetail ObjectFromResultSet(ResultSet rs) throws SQLException, NullPointerException
    {
        RemitDetail obj = new RemitDetail();
        obj.setIdRemitDetails(rs.getInt("idRemitDetails"));
        obj.setIdRemitInfo(rs.getInt("idRemitInfo"));
        obj.setArNo(rs.getString("arNo"));
        obj.setPolicyNumber(rs.getString("policyNumber"));
        obj.setTraceNumber(rs.getString("traceNumber"));
        obj.setClaimStatusCode(rs.getString("claimStatusCode"));
        obj.setLastName(rs.getString("lastName"));
        obj.setFirstName(rs.getString("firstName"));
        obj.setMiddleName(rs.getString("middleName"));
        obj.setTimeOfService(rs.getTime("timeOfService"));
        obj.setCptCode(rs.getString("cptCode"));
        obj.setCptModifiers(rs.getString("cptModifiers"));
        obj.setAdjustmentCode(rs.getString("adjustmentCode"));
        obj.setBilledAmount(rs.getBigDecimal("billedAmount"));
        obj.setPaidAmount(rs.getBigDecimal("paidAmount"));
        obj.setPatientAmount(rs.getBigDecimal("patientAmount"));
        obj.setAdjustmentAmount(rs.getBigDecimal("adjustmentAmount"));
        obj.setItemControlNumber(rs.getString("itemControlNumber"));
        obj.setProcessed(rs.getDate("processed"));
        obj.setUserid(rs.getInt("userid"));

        return obj;
    }
    
    @Override
    public String structureCheck() {
        String query = "SELECT `remitDetails`.`idRemitDetails`,\n"
                + "    `remitDetails`.`idRemitInfo`,\n"
                + "    `remitDetails`.`arNo`,\n"
                + "    `remitDetails`.`policyNumber`,\n"
                + "    `remitDetails`.`traceNumber`,\n"
                + "    `remitDetails`.`claimStatusCode`,\n"
                + "    `remitDetails`.`lastName`,\n"
                + "    `remitDetails`.`firstName`,\n"
                + "    `remitDetails`.`middleName`,\n"
                + "    `remitDetails`.`dateOfService`,\n"
                + "    `remitDetails`.`timeOfService`,\n"
                + "    `remitDetails`.`cptCode`,\n"
                + "    `remitDetails`.`cptModifiers`,\n"
                + "    `remitDetails`.`adjustmentCode`,\n"
                + "    `remitDetails`.`billedAmount`,\n"
                + "    `remitDetails`.`paidAmount`,\n"
                + "    `remitDetails`.`patientAmount`,\n"
                + "    `remitDetails`.`adjustmentAmount`,\n"
                + "    `remitDetails`.`itemControlNumber`,\n"
                + "    `remitDetails`.`created`,\n"
                + "    `remitDetails`.`processed`,\n"
                + "    `remitDetails`.`userid`,\n"
                + "    `remitDetails`.`seqAmount`,\n"
                + "    `remitDetails`.`pr1`,\n"
                + "    `remitDetails`.`pr2`,\n"
                + "    `remitDetails`.`pr3`,\n"
                + "    `remitDetails`.`prOther`,\n"
                + "    `remitDetails`.`pps1`,\n"
                + "    `remitDetails`.`pps2`,\n"
                + "    `remitDetails`.`compCptCode`\n"
                + "FROM `cssbilling`.`remitDetails` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, Database.DatabaseSingleton.getDatabaseSingleton().getConnection(true));
    }
}
