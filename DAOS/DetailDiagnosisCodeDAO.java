package DAOS;

import DAOS.IDAOS.IStructureCheckable;
import DOS.CptCode;
import DOS.DetailDiagnosisCode;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.SQLUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class DetailDiagnosisCodeDAO implements IStructureCheckable
{
    public static final String table = "`" + Database.DatabaseSingleton.getDatabaseSingleton().getProperties().getBillingSchema() +"`.`detailDiagnosisCodes`";

    public static DetailDiagnosisCode insert(DetailDiagnosisCode obj)
            throws Exception
    {
        if (obj == null)
        {
            throw new IllegalArgumentException("DetailDiagnosisCodeDAO::Insert: Received a NULL DetailDiagnosisCode object");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }
        
        // This is what I'm going to make this function do, because whatever
        // BillingRMI is doing is not adding all diagnosis codes per test that
        // it should. - Matt
        // -----------------------------
        // 1) Get the detailCptCodeId from obj
        // 2) Use detailCptCodeId to find the LIS Order it's attached to: detailOrderId - in cssbilling.detailCptCodes
        //      2a) Get the cptCodeId value from this row
        // 3) In css.orderCptDiagnosisCodes find all diagnosis codes belonging to order id: orderCptCodeId = detailOrderId
        // 4) Check the validity of the diagnosis codes with CptCode, PayorId, diagnosisValidity and diagnosisValidityLookup
        // 5) For any valid diagnosis codes, INSERT them ALL into cssbilling.detailDiagnosisCodes
        //      5a) If no valid diagnosis codes, write up to four diagnosis codes that are undetermined
        
        int detailOrderId;
        int orderId;
        int cptCodeId;
        int insuranceId;
        ArrayList<Integer> diagnosisCodeIds = new ArrayList<>();
        ArrayList<Boolean> diagnosisCodeApprovalStatus = new ArrayList<>();
        
        String sql = "SELECT dcc.detailOrderId,\n"
            + "dcc.cptCodeId,\n"
            + "dos.orderId,\n"
            + "o.insurance\n"
            + "FROM cssbilling.detailCptCodes dcc\n"
            + "INNER JOIN cssbilling.detailOrders dos ON dos.iddetailOrders = dcc.detailOrderId\n"
            + "INNER JOIN css.orders o ON o.idOrders = dos.orderId\n"
            + "WHERE iddetailCptCodes = " + obj.getDetailCptCodeId();
        PreparedStatement pStmt = con.prepareStatement(sql);
        ResultSet rs = pStmt.executeQuery();
        
        if (rs.next())
        {
            detailOrderId = rs.getInt("detailOrderId");
            cptCodeId = rs.getInt("cptCodeId");
            orderId = rs.getInt("orderId");
                insuranceId = rs.getInt("insurance");
        }
        else
            throw new Exception("Initial query failed in DetailDiagnosisCodeDAO.java");
        
        sql = "SELECT dc.*\n"
            + "FROM orderCptDiagnosisCodes ocd\n"
            + "INNER JOIN orderCptCodes occ ON ocd.orderCptCodeId = occ.idorderCptCodes\n"
            + "INNER JOIN orderedTests ot ON occ.orderedTestId = ot.idorderedTests\n"
            + "INNER JOIN diagnosisCodes dc ON ocd.diagnosisCodeId = dc.idDiagnosisCodes\n"
            + "WHERE ot.orderid = " + orderId + " AND occ.cptCodeId = " + cptCodeId + "\n"
            + "ORDER BY ocd.rank;";
        pStmt = con.prepareStatement(sql);
        rs = pStmt.executeQuery();
        
        while (rs.next())
        {
            diagnosisCodeIds.add(rs.getInt("idDiagnosisCodes"));
            
            sql = "SELECT * FROM css.diagnosisValidityLookup dvl\n"
                + "INNER JOIN css.diagnosisValidity dv ON dv.idDiagnosisValidity = dvl.diagnosisValidityId\n"
                + "INNER JOIN css.billingPayors bp ON bp.insuranceId = " + insuranceId + "\n"
                + "WHERE dvl.cptCodeId = " + cptCodeId + " AND diagnosisCodeId = " + rs.getInt("idDiagnosisCodes") + "\n"
                + "AND dvl.active = 1;";

            pStmt = con.prepareStatement(sql);
            ResultSet rs1 = pStmt.executeQuery();
            
            if (rs1.next())
            {
                int validityStatus = rs1.getInt("validityStatusId");
                if (validityStatus == 1)
                    diagnosisCodeApprovalStatus.add(Boolean.TRUE);
                else if (validityStatus == 2)
                    diagnosisCodeApprovalStatus.add(Boolean.FALSE);
                else
                    diagnosisCodeApprovalStatus.add(null);
            }
            else
                diagnosisCodeApprovalStatus.add(null);
        }
        
        // Now we're going to add the Diagnosis Codes to cssbilling.detailDiagnosisCodes
        int loops = diagnosisCodeIds.size() < 4 ? diagnosisCodeIds.size() : 4;
        boolean foundCodes = false;
        if (allApprovalStatusesNull(diagnosisCodeApprovalStatus))
        {
            // All of the diagnosis codes have undefined approval status. Add up to four codes
            sql = "INSERT INTO " + table
                    + "("
                    + "  detailCptCodeId,"
                    + "  diagnosisCodeId"
                    + ") VALUES ";
            
            for (int i = 0; i < loops; i++)
            {
                sql += "(?,?), ";
                foundCodes = true;
            }
            
            sql = sql.substring(0, sql.length() - 2) + ";";
            int index = 0;
            pStmt = con.prepareStatement(sql);
            for (int i = 0; i < loops; i++)
            {
                SQLUtil.SafeSetInteger(pStmt, ++index, obj.getDetailCptCodeId());
                SQLUtil.SafeSetInteger(pStmt, ++index, diagnosisCodeIds.get(i));
            }
        }
        else if (noApprovedStatuses(diagnosisCodeApprovalStatus))
        {
            // Try to find at least one undefined Diagnosis Code - or the highest ranked
            // Add to table if there are - up to 4 grey codes
            sql = "INSERT INTO " + table
                    + "("
                    + "  detailCptCodeId,"
                    + "  diagnosisCodeId"
                    + ") VALUES ";
            
            for (int i = 0; i < loops; i++)
            {
                if (diagnosisCodeApprovalStatus.get(i) == null)
                {
                    sql += "(?,?), ";
                    foundCodes = true;
                }
            }
            
            sql = sql.substring(0, sql.length() - 2) + ";";
            int index = 0;
            pStmt = con.prepareStatement(sql);
            for (int i = 0; i < loops; i++)
            {
                if (diagnosisCodeApprovalStatus.get(i) == null)
                {
                    SQLUtil.SafeSetInteger(pStmt, ++index, obj.getDetailCptCodeId());
                    SQLUtil.SafeSetInteger(pStmt, ++index, diagnosisCodeIds.get(i));
                }
            }
        }
        else
        {
            sql = "INSERT INTO " + table
                        + "("
                        + "  detailCptCodeId,"
                        + "  diagnosisCodeId"
                        + ") VALUES ";
            // Special cases did not occur: add all valid diagnosis codes
            for (int i = 0; i < diagnosisCodeIds.size(); i++)
            {
                // TRUE Approval status: Add
                if (diagnosisCodeApprovalStatus.get(i) != null &&
                        diagnosisCodeApprovalStatus.get(i))
                {
                    sql +=  "(?,?), ";
                    foundCodes = true;
                }
            }
            sql = sql.substring(0, sql.length() - 2) + ";";
            int index = 0;
            pStmt = con.prepareStatement(sql);
            for (int i = 0; i < diagnosisCodeIds.size(); i++)
            {
                if (diagnosisCodeApprovalStatus.get(i) != null &&
                        diagnosisCodeApprovalStatus.get(i))
                {
                    SQLUtil.SafeSetInteger(pStmt, ++index, obj.getDetailCptCodeId());
                    SQLUtil.SafeSetInteger(pStmt, ++index, diagnosisCodeIds.get(i));
                }
            }
        }

        try
        {
            if (foundCodes)
            {
                int affected = pStmt.executeUpdate();
                if (affected < 1) throw new Exception("DetailDiagnosisCodeDAO::insert: No rows were affected by insert: " + obj.toString());
            }
            pStmt.close();
            
            
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage() + " " + pStmt.toString());
            throw ex;
        }
        pStmt.close();
        return obj;
    }
    
    public static int insertAllForDetailCptCodeId(Connection con, Integer detailCptCodeId, Collection<Integer> detailDiagnosisCodeIds)
            throws Exception
    {
        int affected = 0;
        if (detailCptCodeId == null || detailDiagnosisCodeIds == null || detailDiagnosisCodeIds.isEmpty())
        {
            throw new IllegalArgumentException("DetailDiagnosisCodeDAO::insertAllForDetailCptCodeId: Received a NULL or EMPTY object");
        }

        String sql = "INSERT INTO " + table
                + "("
                + "  detailCptCodeId,"
                + "  diagnosisCodeId"
                + ")"
                + "VALUES";
        for (int index = 0; index < detailDiagnosisCodeIds.size(); index++)
        {
            sql += " (?,?),";
        }
        sql = sql.substring(0, sql.length()-1) + ";";

        PreparedStatement pStmt = con.prepareStatement(sql);
        int i = 0;
        Iterator<Integer> detailDiagnosisCodeIterator = detailDiagnosisCodeIds.iterator();
        while (detailDiagnosisCodeIterator.hasNext())
        {
            SQLUtil.SafeSetInteger(pStmt, ++i, detailCptCodeId);
            SQLUtil.SafeSetInteger(pStmt, ++i, detailDiagnosisCodeIterator.next());
        }

        try
        {
            affected = pStmt.executeUpdate();
            if (affected < 1) throw new Exception("DetailDiagnosisCodeDAO::insertAllForDetailCptCodeId: No rows were affected by insert: " + detailCptCodeId);
            pStmt.close();
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage() + " " + pStmt.toString());
            throw ex;
        }
        pStmt.close();
        return affected;
    }
    
    public static boolean deleteAllForDetailCptCodeId(int detailCptCodeId)
    {
        try
        {
            Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
            Connection con = dbs.getConnection(true);
            if (con.isValid(2) == false)
            {
                con = CheckDBConnection.Check(dbs, con);
            }
            String stmt = "DELETE FROM `cssbilling`.`detailDiagnosisCodes` WHERE `detailCptCodeId`='" +
                    detailCptCodeId + "';";
            PreparedStatement pstmt = con.prepareStatement(stmt);
            pstmt.executeUpdate();
            pstmt.close();
            
            return true;
        }
        catch (SQLException sex)
        {
            System.out.println("Error deleting DetailDiagnosisCodes: " + sex.getMessage());
            return false;
        }
    }
    
    public static Collection<Integer> getDiagnosisCodeIdsByDetailCptCodeId(Connection con, Integer detailCptCodeId)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (detailCptCodeId == null || detailCptCodeId <= 0)
        {
            throw new IllegalArgumentException("DetailDiagnosisCodeDAO::getByDetailCptCodeId:"
                    + " Received a [NULL] or invalid detailCptCodeId object.");
        }

        String sql = "SELECT diagnosisCodeId FROM " + table + " WHERE detailCptCodeId = " + String.valueOf(detailCptCodeId);

        PreparedStatement pStmt = con.prepareStatement(sql);

        Collection<Integer> diagnosisCodeIds = new LinkedList<>();
        try
        {
            ResultSet rs = pStmt.executeQuery();
            while (rs.next())
            {
                diagnosisCodeIds.add(rs.getInt("diagnosisCodeId"));
            }
        }
        catch (SQLException ex)
        {
            System.out.println(ex.getMessage() + " " + sql);
            throw new SQLException(ex.getMessage() + " " + sql);
        }
        pStmt.close();
        return diagnosisCodeIds;        
    }
    
    public static Collection<DetailDiagnosisCode> getByDetailCptCodeId(Integer detailCptCodeId)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (detailCptCodeId == null || detailCptCodeId <= 0)
        {
            throw new IllegalArgumentException("DetailDiagnosisCodeDAO::getByDetailCptCodeId:"
                    + " Received a [NULL] or invalid detailCptCodeId object.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "SELECT * FROM " + table + " WHERE detailCptCodeId = " + String.valueOf(detailCptCodeId);

        PreparedStatement pStmt = con.prepareStatement(sql);

        Collection<DetailDiagnosisCode> detailCptCodes = new LinkedList<>();
        try
        {
            ResultSet rs = pStmt.executeQuery();
            while (rs.next())
            {
                detailCptCodes.add(ObjectFromResultSet(rs));
            }
        }
        catch (SQLException ex)
        {
            System.out.println(ex.getMessage() + " " + sql);
            throw new SQLException(ex.getMessage() + " " + sql);
        }
        pStmt.close();
        return detailCptCodes;        
    }

    /**
     * Gets the detail diagnosis code for the provided unique database identifier through
     *  the supplied connection.
     * 
     *  If forUpdate is true, query is submitted in anticipation for an update
     *  to the row. Should be used for transactional work, if possible.
     * 
     * @param con
     * @param detailCptCodeId
     * @param diagnosisCodeId
     * @param forUpdate
     * @return
     * @throws SQLException
     * @throws IllegalArgumentException
     * @throws NullPointerException 
     */
    public static DetailDiagnosisCode get(Connection con, Integer detailCptCodeId, Integer diagnosisCodeId, boolean forUpdate)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (con == null)
        {
            throw new IllegalArgumentException("DetailDiagnosisCodeDAO::get(Connection,Integer,boolean):"
                    + " Received a [NULL] Connection object argument");
        }
        
        if (detailCptCodeId == null || detailCptCodeId <= 0 || diagnosisCodeId == null || diagnosisCodeId <= 0)
        {
            throw new IllegalArgumentException("DetailDiagnosisCodeDAO::get(Connection,Integer,boolean):"
                    + " Received a [NULL] Integer id argument");
        }
        
        if (con.isValid(2) == false)
        {
            throw new SQLException("DetailDiagnosisCodeDAO::get(Connection,Integer,boolean):"
                    + " Supplied Connection object was invalid (database connection was lost?)");
        }
        
        String sql = "SELECT * FROM " + table + " WHERE detailCptCodeId = " + String.valueOf(detailCptCodeId) + " AND diagnosisCodeId = " + String.valueOf(diagnosisCodeId);
        if (forUpdate) sql += " FOR UPDATE";
        
        System.out.println(sql);

        PreparedStatement pStmt = con.prepareStatement(sql);

        DetailDiagnosisCode obj = null;
        try
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
        pStmt.close();
        return obj;        
        
    }

    private static DetailDiagnosisCode ObjectFromResultSet(ResultSet rs) throws SQLException, NullPointerException
    {
        DetailDiagnosisCode obj = new DetailDiagnosisCode();
        obj.setDetailCptCodeId(rs.getInt("detailCptCodeId"));
        obj.setDiagnosisCodeId(rs.getInt("diagnosisCodeId"));

        return obj;
    }
    
    private static boolean  allApprovalStatusesNull (ArrayList<Boolean> list)
    {
        for (Boolean b : list)
        {
            if (b != null)
                return false;
        }
        return true;
    }
    
    private static boolean noApprovedStatuses (ArrayList<Boolean> list)
    {
        for (Boolean b: list)
        {
            if (b != null && b)
                return false;
        }
        return true;
    }
    
    @Override
    public String structureCheck() {
        String query = "SELECT `detailDiagnosisCodes`.`detailCptCodeId`,\n"
                + "    `detailDiagnosisCodes`.`diagnosisCodeId`\n"
                + "FROM `cssbilling`.`detailDiagnosisCodes` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, Database.DatabaseSingleton.getDatabaseSingleton().getConnection(true));
    }
}
