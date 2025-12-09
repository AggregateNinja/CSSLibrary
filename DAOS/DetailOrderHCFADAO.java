/*
 * Computer Service & Support, Inc.  All Rights Reserved.
 */

package DAOS;

import DAOS.IDAOS.IStructureCheckable;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.SQLUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Rob
 */
public class DetailOrderHCFADAO implements IStructureCheckable
{
    public static final String table = Database.DatabaseSingleton.getDatabaseSingleton().getProperties().getBillingSchema() + ".`detailOrderHCFAs`";
    
    public static void Insert(Integer detailOrderId, String hcfaFilename, Integer userId) throws SQLException
    {
        Integer newId = null;
        if (detailOrderId == null || hcfaFilename == null || userId == null)
        {
            throw new IllegalArgumentException("DetailOrderHCFADAO::Insert: Received a NULL object");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "INSERT INTO " + table
                + "("
                + "  detailOrderId,"
                + "  hcfaFilename,"
                + "  userId"
                + ")"
                + "VALUES (?,?,?) ON DUPLICATE KEY UPDATE hcfaFilename=?";

        PreparedStatement pStmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        int i = 0;
        SQLUtil.SafeSetInteger(pStmt, ++i, detailOrderId);
        SQLUtil.SafeSetString(pStmt, ++i, hcfaFilename);
        SQLUtil.SafeSetInteger(pStmt, ++i, userId);
        SQLUtil.SafeSetString(pStmt, ++i, hcfaFilename);
        
        try
        {
            pStmt.executeUpdate();
            ResultSet rs = pStmt.getGeneratedKeys();
            if (rs.next())
            {
                newId = rs.getInt(1);
            }
            pStmt.close();
//            if (newId == null || newId <= 0)
//            {
//                throw new NullPointerException("DetailOrderDAO::Insert: Cannot retrieve generated identifier for inserted row.");
//            }
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage() + " " + pStmt.toString());
            throw ex;
        }
    }
    
    @Override
    public String structureCheck() {
        String query = "SELECT `detailOrderHCFAs`.`detailOrderId`,\n"
                + "    `detailOrderHCFAs`.`hcfaFilename`,\n"
                + "    `detailOrderHCFAs`.`userId`,\n"
                + "    `detailOrderHCFAs`.`timestamp`\n"
                + "FROM `cssbilling`.`detailOrderHCFAs` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, Database.DatabaseSingleton.getDatabaseSingleton().getConnection(true));
    }
}
