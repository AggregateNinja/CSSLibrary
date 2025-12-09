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
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Rob
 */
public class EligibilityErrorDAO implements IStructureCheckable
{
    public static final String table = Database.DatabaseSingleton.getDatabaseSingleton().getProperties().getCssSchema()+ ".`eligibilityErrors`";
    
    public static List<Integer> insert(String field, List<String> errorList, String transactionUUID) throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (field == null)
        {
            throw new IllegalArgumentException("EligibilityErrorDAO::Insert: Received a NULL field object");
        }
        if (errorList == null)
        {
            throw new IllegalArgumentException("EligibilityErrorDAO::Insert: Received a NULL errorList object");
        }
        if (transactionUUID == null)
        {
            throw new IllegalArgumentException("EligibilityErrorDAO::Insert: Received a NULL transactionUUID object");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }
        
        List<Integer> newIdList = new ArrayList<Integer>();
        
        for (String error : errorList)
        {

            String sql = "INSERT INTO " + table
                    + "("
                    + "  transactionUUID,"
                    + "  errorField,"
                    + "  errorMessage"
                    + ")"
                    + "VALUES (?,?,?)";

            PreparedStatement pStmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            int i = 0;
            SQLUtil.SafeSetString(pStmt, ++i, transactionUUID);
            SQLUtil.SafeSetString(pStmt, ++i, field);
            SQLUtil.SafeSetString(pStmt, ++i, error);
            
            Integer newId = null;
            try
            {
                pStmt.executeUpdate();
                ResultSet rs = pStmt.getGeneratedKeys();
                if (rs.next())
                {
                    newId = rs.getInt(1);
                }
                pStmt.close();
                if (newId == null || newId <= 0)
                {
                    throw new NullPointerException("EligibilityResponseDAO::Insert: Cannot retrieve generated identifier for inserted row.");
                }
                newIdList.add(newId);
            }
            catch (Exception ex)
            {
                System.out.println(ex.getMessage() + " " + pStmt.toString());
                throw ex;
            }
        }

        return newIdList;
    }
    
    @Override
    public String structureCheck() {
        String query = "SELECT `eligibilityErrors`.`idEligibilityErrors`,\n"
                + "    `eligibilityErrors`.`transactionUUID`,\n"
                + "    `eligibilityErrors`.`errorField`,\n"
                + "    `eligibilityErrors`.`errorMessage`,\n"
                + "    `eligibilityErrors`.`timestamp`\n"
                + "FROM `css`.`eligibilityErrors` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, Database.DatabaseSingleton.getDatabaseSingleton().getConnection(true));
    }
}
