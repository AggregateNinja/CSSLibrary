package DAOS;

import DAOS.IDAOS.IStructureCheckable;
import DOS.FeeScheduleCptLookup;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.SQLUtil;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class FeeScheduleCptLookupDAO implements IStructureCheckable
{

    private static final String table = "`feeScheduleCptLookup`";

    public static FeeScheduleCptLookup insert(FeeScheduleCptLookup obj) throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (obj == null)
        {
            throw new IllegalArgumentException("FeeScheduleCptLookupDAO::Insert: Received a NULL FeeScheduleCptLookup object");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "INSERT INTO " + table
                + "("
                + "  feeScheduleTestLookupId,"
                + "  cptCodeId,"
                + "  cost,"
                + "  quantity,"
                + "  feeScheduleActionId,"
                + "  quantifiedMaximum,"
                + "  lineNote"
                + ")"
                + "VALUES (?,?,?,?,?,?,?)";

        PreparedStatement pStmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        int i = 0;
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getFeeScheduleTestLookupId());
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getCptCodeId());
        pStmt.setBigDecimal(++i, obj.getCost());
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getQuantity());
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getFeeScheduleActionId());
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getQuantifiedMaximum());
        SQLUtil.SafeSetString(pStmt, ++i, obj.getLineNote());

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
                throw new NullPointerException("FeeScheduleCptLookupDAO::Insert: Cannot retrieve generated identifier for inserted row.");
            }
            obj.setIdFeeScheduleCptLookup(newId);
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage() + " " + pStmt.toString());
            throw ex;
        }

        return obj;
    }

    public static void update(FeeScheduleCptLookup obj) throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (obj == null)
        {
            throw new IllegalArgumentException("FeeScheduleCptLookupDAO::Update: Received a NULL FeeScheduleCptLookup object.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "UPDATE " + table + " SET "
                + " feeScheduleTestLookupId = ?,"
                + " cptCodeId = ?,"
                + " cost = ?,"
                + " quantity = ?,"
                + " feeScheduleActionId = ?,"
                + " quantifiedMaximum = ?,"
                + " lineNote = ?"
                + " WHERE idFeeScheduleCptLookup = " + obj.getIdFeeScheduleCptLookup();

        String sqlOutput = "";
        PreparedStatement pStmt = con.prepareStatement(sql);
        try
        {
            int i = 0;
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getFeeScheduleTestLookupId());
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getCptCodeId());
            pStmt.setBigDecimal(++i, obj.getCost());
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getQuantity());
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getFeeScheduleActionId());
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getQuantifiedMaximum());
            SQLUtil.SafeSetString(pStmt, ++i, obj.getLineNote());
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

    public static FeeScheduleCptLookup get(Integer id) throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (id == null || id <= 0)
        {
            throw new IllegalArgumentException("FeeScheduleCptLookupDAO::Get: Received a NULL or empty FeeScheduleCptLookup object.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "SELECT * FROM " + table + " WHERE idFeeScheduleCptLookup = " + String.valueOf(id);

        PreparedStatement pStmt = con.prepareStatement(sql);

        FeeScheduleCptLookup obj = null;
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

    /**
     * Gets all CPT lines associated with the feeScheduleTestLookup line.
     *
     * @param feeScheduleCptLookupId
     * @return
     * @throws SQLException
     * @throws IllegalArgumentException
     * @throws NullPointerException
     */
    public static List<FeeScheduleCptLookup> getByTestLookupId(Integer feeScheduleCptLookupId) throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (feeScheduleCptLookupId == null || feeScheduleCptLookupId <= 0)
        {
            throw new IllegalArgumentException("FeeScheduleCptLookupDAO::getByTestLookupId: Received a NULL or empty feeScheduleCptLookupId object.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "SELECT * FROM " + table + " WHERE feeScheduleTestLookupId = " + String.valueOf(feeScheduleCptLookupId);

        PreparedStatement pStmt = con.prepareStatement(sql);
        List<FeeScheduleCptLookup> cptLookups = new LinkedList<>();

        try
        {
            ResultSet rs = pStmt.executeQuery();
            while (rs.next())
            {
                cptLookups.add(ObjectFromResultSet(rs));
            }
        }
        catch (SQLException ex)
        {
            System.out.println(ex.getMessage() + " " + sql);
            throw new SQLException(ex.getMessage() + " " + sql);
        }
        pStmt.close();
        return cptLookups;
    }

    public static void delete(Integer id) throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (id == null || id <= 0)
        {
            throw new IllegalArgumentException("FeeScheduleCptLookupDAO::delete: Received a NULL or invalid FeeScheduleCptLookupID.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "DELETE FROM " + table + " WHERE idFeeScheduleCptLookup = ?";

        PreparedStatement pStmt = con.prepareStatement(sql);
        pStmt.setInt(1, id);
        try
        {
            int affected = pStmt.executeUpdate();
            if (affected <= 0)
            {
                throw new SQLException(
                        "FeeScheduleCptLookupDAO::Delete: No rows could be removed "
                        + "for idFeeScheduleCptLookup " + id.toString());
            }
        }
        catch (SQLException ex)
        {
            System.out.println(ex.getMessage() + " " + sql);
            throw new SQLException(ex.getMessage() + " " + sql);
        }
        pStmt.close();

    }

    private static FeeScheduleCptLookup ObjectFromResultSet(ResultSet rs) throws SQLException, NullPointerException
    {
        FeeScheduleCptLookup obj = new FeeScheduleCptLookup();
        obj.setIdFeeScheduleCptLookup(rs.getInt("idFeeScheduleCptLookup"));
        obj.setFeeScheduleTestLookupId(rs.getInt("feeScheduleTestLookupId"));
        obj.setCptCodeId(rs.getInt("cptCodeId"));
        obj.setCost(rs.getBigDecimal("cost"));
        obj.setQuantity(rs.getInt("quantity"));
        obj.setFeeScheduleActionId(rs.getInt("feeScheduleActionId"));
        Integer quantifiedMaximum = rs.getInt("quantifiedMaximum");
        if (quantifiedMaximum > 0)
        {
            obj.setQuantifiedMaximum(rs.getInt("quantifiedMaximum"));
        }
        obj.setLineNote(rs.getString("lineNote"));
        
        return obj;
    }
    
    @Override
    public String structureCheck() {
        String query = "SELECT `feeScheduleCptLookup`.`idFeeScheduleCptLookup`,\n"
                + "    `feeScheduleCptLookup`.`feeScheduleTestLookupId`,\n"
                + "    `feeScheduleCptLookup`.`cptCodeId`,\n"
                + "    `feeScheduleCptLookup`.`cost`,\n"
                + "    `feeScheduleCptLookup`.`quantity`,\n"
                + "    `feeScheduleCptLookup`.`feeScheduleActionId`,\n"
                + "    `feeScheduleCptLookup`.`quantifiedMaximum`,\n"
                + "    `feeScheduleCptLookup`.`lineNote`\n"
                + "FROM `css`.`feeScheduleCptLookup` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, Database.DatabaseSingleton.getDatabaseSingleton().getConnection(true));
    }
}
