package DAOS;

import DAOS.IDAOS.IStructureCheckable;
import DOS.DetailCptModifier;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.SQLUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

public class DetailCptModifierDAO implements IStructureCheckable
{
    private static final String table = Database.DatabaseSingleton.getDatabaseSingleton().getProperties().getBillingSchema() + ".`detailCptModifiers`";

    public static DetailCptModifier insert(DetailCptModifier obj)
            throws Exception
    {
        if (obj == null)
        {
            throw new IllegalArgumentException("DetailCptModifierDAO::Insert: "
                    + "Received a NULL DetailCptModifier object");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "INSERT INTO " + table
                + "("
                + "  detailCptCodeId,"
                + "  cptModifierId"
                + ")"
                + "VALUES (?,?)";

        PreparedStatement pStmt = con.prepareStatement(sql);
        int i = 0;
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getDetailCptCodeId());
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getCptModifierId());

        try
        {
            int affected = pStmt.executeUpdate();
            if (affected < 1) throw new Exception(
                    "No row was inserted for detail cpt modifier");
            
            pStmt.close();
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage() + " " + pStmt.toString());
            throw ex;
        }
        return obj;
    }
    
    public static Collection<DetailCptModifier> getByDetailCptCodeId(Integer detailCptCodeId)
            throws SQLException, NullPointerException, IllegalArgumentException
    {
        if (detailCptCodeId == null || detailCptCodeId <= 0)
        {
            throw new IllegalArgumentException("DetailCptModifierDAO::getByDetailCptCodeId:"
                    + " Received a [NULL] or invalid detailCptCodeId argument");
        }
        
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "SELECT * FROM " + table + " WHERE detailCptCodeId = ?";

        PreparedStatement pStmt = con.prepareStatement(sql);
        
        SQLUtil.SafeSetInteger(pStmt, 1, detailCptCodeId);
        ResultSet rs = pStmt.executeQuery();
        Collection<DetailCptModifier> detailCptModifiers = new LinkedList<>();
        try
        {
            while (rs.next())
            {
                detailCptModifiers.add(ObjectFromResultSet(rs));
            }
            
            pStmt.close();
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage() + " " + pStmt.toString());
            throw ex;
        }
        return detailCptModifiers;
    }
    
    public static int insertAllForDetailCptCodeId(Connection con, Integer detailCptCodeId, Collection<Integer> cptModifierIds)
            throws Exception
    {
        int affected = 0;
        if (detailCptCodeId == null || cptModifierIds == null || cptModifierIds.isEmpty())
        {
            throw new IllegalArgumentException("DetailDiagnosisCodeDAO::insertAllForDetailCptCodeId: Received a NULL or EMPTY object");
        }

        String sql = "INSERT INTO " + table
                + "("
                + "  detailCptCodeId,"
                + "  cptModifierId"
                + ")"
                + "VALUES";
        for (int index = 0; index < cptModifierIds.size(); index++)
        {
            sql += " (?,?),";
        }
        sql = sql.substring(0, sql.length()-1) + ";";

        PreparedStatement pStmt = con.prepareStatement(sql);
        int i = 0;
        Iterator<Integer> cptModifierIdsIterator = cptModifierIds.iterator();
        while (cptModifierIdsIterator.hasNext())
        {
            SQLUtil.SafeSetInteger(pStmt, ++i, detailCptCodeId);
            SQLUtil.SafeSetInteger(pStmt, ++i, cptModifierIdsIterator.next());
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
            
            String stmt = "DELETE FROM `cssbilling`.`detailCptModifiers` WHERE `detailCptCodeId`='" +
                    detailCptCodeId + "';";
            PreparedStatement pstmt = con.prepareStatement(stmt);
            pstmt.executeUpdate();
            pstmt.close();
            
            return true;
        }
        catch (SQLException sex)
        {
            System.out.println("Error deleting DetailCptModifiers: " + sex.getMessage());
            return false;
        }
    }
    
    public static Collection<Integer> getCptModifierIdsByDetailCptCodeId(Connection con, Integer detailCptCodeId)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (detailCptCodeId == null || detailCptCodeId <= 0)
        {
            throw new IllegalArgumentException("DetailDiagnosisCodeDAO::getCptModifierIdsByDetailCptCodeId:"
                    + " Received a [NULL] or invalid detailCptCodeId object.");
        }

        String sql = "SELECT cptModifierId FROM " + table + " WHERE detailCptCodeId = " + String.valueOf(detailCptCodeId);

        PreparedStatement pStmt = con.prepareStatement(sql);

        Collection<Integer> cptModifierIds = new LinkedList<>();
        try
        {
            ResultSet rs = pStmt.executeQuery();
            while (rs.next())
            {
                cptModifierIds.add(rs.getInt("cptModifierId"));
            }
        }
        catch (SQLException ex)
        {
            System.out.println(ex.getMessage() + " " + sql);
            throw new SQLException(ex.getMessage() + " " + sql);
        }
        pStmt.close();
        return cptModifierIds;        
    }
    
    private static DetailCptModifier ObjectFromResultSet(ResultSet rs)
            throws SQLException, NullPointerException
    {
        DetailCptModifier obj = new DetailCptModifier();
        obj.setDetailCptCodeId(rs.getInt("detailCptCodeId"));
        obj.setCptModifierId(rs.getInt("cptModifierId"));

        return obj;
    }
    
    @Override
    public String structureCheck() {
        String query = "SELECT `detailCptModifiers`.`detailCptCodeId`,\n"
                + "    `detailCptModifiers`.`cptModifierId`\n"
                + "FROM `cssbilling`.`detailCptModifiers` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, Database.DatabaseSingleton.getDatabaseSingleton().getConnection(true));
    }
}
