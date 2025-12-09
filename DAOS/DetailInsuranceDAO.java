package DAOS;

import DAOS.IDAOS.IStructureCheckable;
import DOS.DetailInsurance;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.SQLUtil;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class DetailInsuranceDAO implements IStructureCheckable
{

    private static final String table = Database.DatabaseSingleton.getDatabaseSingleton().getProperties().getBillingSchema() + ".`detailInsurances`";

    public static DetailInsurance insert(DetailInsurance obj) throws SQLException, IllegalArgumentException, NullPointerException
    {
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }
        return insert(con, obj);
    }    
    
    public static DetailInsurance insert(Connection con, DetailInsurance obj) throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (obj == null)
        {
            throw new IllegalArgumentException("DetailInsuranceDAO::Insert: Received a NULL DetailInsurance object");
        }

        String sql = "INSERT INTO " + table
                + "("
                + "  `detailOrderId`,"
                + "  `submissionStatusId`,"
                + "  `insuranceId`,"
                + "  `insuranceTypeId`,"
                + "  `insuranceSubmissionTypeId`,"
                + "  `insuranceSubmissionModeId`,"        
                + "  `identifier`,"
                + "  `groupIdentifier`,"
                + "  `rank`,"
                + "  `timesBilled`"
                + ")"
                + "VALUES (?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pStmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        int i = 0;
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getDetailOrderId());
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getSubmissionStatusId());
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getInsuranceId());
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getInsuranceTypeId());
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getInsuranceSubmissionTypeId());
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getInsuranceSubmissionModeId());
        SQLUtil.SafeSetString(pStmt, ++i, obj.getIdentifier());
        SQLUtil.SafeSetString(pStmt, ++i, obj.getGroupIdentifier());
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getRank());
        SQLUtil.SafeSetRangeInteger(pStmt, ++i, obj.getTimesBilled());

        try
        {
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
                throw new NullPointerException("DetailInsuranceDAO::Insert: Cannot retrieve generated identifier for inserted row.");
            }
            obj.setIddetailInsurances(newId);
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage() + " " + pStmt.toString());
            throw ex;
        }

        return obj;
    }

    public static void update(DetailInsurance obj) throws SQLException, IllegalArgumentException, NullPointerException
    {

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }
        update(con, obj);
    }    

    public static void update(Connection con, DetailInsurance obj) throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (obj == null)
        {
            throw new IllegalArgumentException("DetailInsuranceDAO::Update: Received a NULL DetailInsurance object.");
        }

        String sql = "UPDATE " + table + " SET "
                + " `detailOrderId` = ?,"
                + " `submissionStatusId` = ?,"
                + " `insuranceId` = ?,"
                + " `insuranceTypeId` = ?,"
                + " `insuranceSubmissionTypeId` = ?,"
                + " `insuranceSubmissionModeId` = ?,"
                + " `identifier` = ?,"
                + " `groupIdentifier` = ?,"
                + " `rank` = ?,"
                + " `timesBilled` = ?"
                + " WHERE `iddetailInsurances` = " + obj.getIddetailInsurances();

        String sqlOutput = "";
        PreparedStatement pStmt = con.prepareStatement(sql);
        try
        {
            int i = 0;
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getDetailOrderId());
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getSubmissionStatusId());
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getInsuranceId());
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getInsuranceTypeId());
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getInsuranceSubmissionTypeId());
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getInsuranceSubmissionModeId());
            SQLUtil.SafeSetString(pStmt, ++i, obj.getIdentifier());
            SQLUtil.SafeSetString(pStmt, ++i, obj.getGroupIdentifier());
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getRank());
            SQLUtil.SafeSetRangeInteger(pStmt, ++i, obj.getTimesBilled());
            sqlOutput = pStmt.toString();
            pStmt.executeUpdate();
        }
        catch (Exception ex)
        {
            String errorMsg = ex.getMessage() + " " + sqlOutput;
            System.out.println(errorMsg);
            throw new SQLException(errorMsg);
        }
        pStmt.close();
    }

    public static DetailInsurance get(Connection con, Integer id) throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (id == null || id <= 0)
        {
            throw new IllegalArgumentException("DetailInsuranceDAO::Get:"
                    + " Received a NULL or empty identifier object.");
        }

        String sql = "SELECT * FROM " + table + " WHERE `iddetailInsurances` = " + String.valueOf(id);

        PreparedStatement pStmt = con.prepareStatement(sql);

        DetailInsurance obj = null;
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

    public static DetailInsurance get(Integer id) throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (id == null || id <= 0)
        {
            throw new IllegalArgumentException("DetailInsuranceDAO::Get:"
                    + " Received a NULL or empty identifier object.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "SELECT * FROM " + table + " WHERE `iddetailInsurances` = " + String.valueOf(id);

        PreparedStatement pStmt = con.prepareStatement(sql);

        DetailInsurance obj = null;
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
    
    public static Collection<DetailInsurance> getByDetailOrderId(Integer detailOrderId)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (detailOrderId == null || detailOrderId <= 0)
        {
            throw new IllegalArgumentException("DetailInsuranceDAO::getByDetailOrderId(DetailOrderId):"
                    + " Received a NULL or empty detailOrderId object.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "SELECT * FROM " + table + " WHERE `detailOrderId` = "
                + String.valueOf(detailOrderId) + " ORDER BY `rank` ASC";

        PreparedStatement pStmt = con.prepareStatement(sql);

        List<DetailInsurance> detailInsurances = new LinkedList<>();
        try
        {
            ResultSet rs = pStmt.executeQuery();
            if (rs.next())
            {
                detailInsurances.add(ObjectFromResultSet(rs));
            }
        }
        catch (SQLException ex)
        {
            System.out.println(ex.getMessage() + " " + sql);
            throw new SQLException(ex.getMessage() + " " + sql);
        }
        pStmt.close();
        return detailInsurances;
    }
    
    public static DetailInsurance getByDetailOrderIdInsuranceId(int detailOrderId, int insuranceId) 
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        DetailInsurance obj = new DetailInsurance();
        
        if (detailOrderId <= 0 || insuranceId <= 0)
        {
            throw new IllegalArgumentException("DetailInsuranceDAO::getByDetailOrderIdInsuranceId(DetailOrderId, InsuranceId):"
                    + " Received a NULL or empty detailOrderId object.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }
        
        String sql = "SELECT * FROM " + table + " WHERE `detailOrderId` = ? AND `insuranceId` = ?";

        PreparedStatement pStmt = con.prepareStatement(sql);

        SQLUtil.SafeSetInteger(pStmt, 1, detailOrderId);
        SQLUtil.SafeSetInteger(pStmt, 2, insuranceId);
        
        ResultSet rs = pStmt.executeQuery();
        if (rs.next())
        {
            obj = ObjectFromResultSet(rs);
        }
        
        pStmt.close();
        
        return obj;
    }
    
    public static DetailInsurance ObjectFromResultSet(ResultSet rs) throws SQLException, NullPointerException
    {
        DetailInsurance obj = new DetailInsurance();
        obj.setIddetailInsurances(rs.getInt("iddetailInsurances"));
        obj.setDetailOrderId(rs.getInt("detailOrderId"));
        obj.setSubmissionStatusId(rs.getInt("submissionStatusId"));
        obj.setInsuranceId(rs.getInt("insuranceId"));
        obj.setInsuranceTypeId(rs.getInt("insuranceTypeId"));
        obj.setInsuranceSubmissionTypeId(rs.getInt("insuranceSubmissionTypeId"));
        obj.setInsuranceSubmissionModeId(rs.getInt("insuranceSubmissionModeId"));
        obj.setIdentifier(rs.getString("identifier"));
        obj.setGroupIdentifier(rs.getString("groupIdentifier"));
        obj.setRank(rs.getInt("rank"));
        obj.setTimesBilled(rs.getInt("timesBilled"));

        return obj;
    }
    
    @Override
    public String structureCheck() {
        String query = "SELECT `detailInsurances`.`iddetailInsurances`,\n"
                + "    `detailInsurances`.`detailOrderId`,\n"
                + "    `detailInsurances`.`submissionStatusId`,\n"
                + "    `detailInsurances`.`insuranceTypeId`,\n"
                + "    `detailInsurances`.`insuranceSubmissionTypeId`,\n"
                + "    `detailInsurances`.`insuranceSubmissionModeId`,\n"
                + "    `detailInsurances`.`insuranceId`,\n"
                + "    `detailInsurances`.`identifier`,\n"
                + "    `detailInsurances`.`groupIdentifier`,\n"
                + "    `detailInsurances`.`rank`,\n"
                + "    `detailInsurances`.`timesBilled`\n"
                + "FROM `cssbilling`.`detailInsurances` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, Database.DatabaseSingleton.getDatabaseSingleton().getConnection(true));
    }

}
