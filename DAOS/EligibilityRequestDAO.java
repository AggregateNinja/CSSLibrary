/*
 * Computer Service & Support, Inc.  All Rights Reserved.
 */

package DAOS;

import API.Billing.RequestInsuranceCoverageInformation;
import DAOS.IDAOS.IStructureCheckable;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.SQLUtil;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Rob
 */
public class EligibilityRequestDAO implements IStructureCheckable
{
    public static final String table = Database.DatabaseSingleton.getDatabaseSingleton().getProperties().getCssSchema()+ ".`eligibilityRequests`";
    
    public static Integer insert(RequestInsuranceCoverageInformation obj, String transactionUUID) throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (obj == null)
        {
            throw new IllegalArgumentException("EligibilityRequestDAO::Insert: Received a NULL RequestInsuranceCoverageInformation object");
        }
        if (transactionUUID == null || transactionUUID.isEmpty()) return insert(obj);
        
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "INSERT INTO " + table
                + "("
                + "  apiProvider,"
                + "  memberFirstName,"
                + "  memberLastName,"
                + "  memberBirthDate,"
                + "  memberGender,"
                + "  groupNumber,"
                + "  memberId,"
                + "  providerFirstName,"
                + "  providerLastName,"
                + "  organizationName,"
                + "  npi,"
                + "  cptCode,"
                + "  tradingPartnerId,"
                + "  userId,"
                + "  transactionUUID,"
                + "  active"
                + ")"
                + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pStmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        int i = 0;
        SQLUtil.SafeSetString(pStmt, ++i, obj.getApiProvider().name());
        SQLUtil.SafeSetString(pStmt, ++i, obj.getFirstName());
        SQLUtil.SafeSetString(pStmt, ++i, obj.getLastName());
        SQLUtil.SafeSetString(pStmt, ++i, obj.getBirthDate());
        SQLUtil.SafeSetString(pStmt, ++i, obj.getGender());
        SQLUtil.SafeSetString(pStmt, ++i, obj.getGroupNumber());
        SQLUtil.SafeSetString(pStmt, ++i, obj.getMemberId());
        SQLUtil.SafeSetString(pStmt, ++i, obj.getProviderFirstName());
        SQLUtil.SafeSetString(pStmt, ++i, obj.getProviderLastName());
        SQLUtil.SafeSetString(pStmt, ++i, obj.getOrganizationName());
        SQLUtil.SafeSetString(pStmt, ++i, obj.getNpi());
        SQLUtil.SafeSetString(pStmt, ++i, obj.getCptCode());
        SQLUtil.SafeSetString(pStmt, ++i, obj.getTradingPartnerID());
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getUserId());
        SQLUtil.SafeSetString(pStmt, ++i, transactionUUID);
        SQLUtil.SafeSetBoolean(pStmt, ++i, Boolean.TRUE);
        
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
                throw new NullPointerException("EligibilityRequestDAO::Insert: Cannot retrieve generated identifier for inserted row.");
            }
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage() + " " + pStmt.toString());
            throw ex;
        }

        return newId;
    }
    
    public static Integer insert(RequestInsuranceCoverageInformation obj) throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (obj == null)
        {
            throw new IllegalArgumentException("EligibilityRequestDAO::Insert: Received a NULL RequestInsuranceCoverageInformation object");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "INSERT INTO " + table
                + "("
                + "  apiProvider,"
                + "  memberFirstName,"
                + "  memberLastName,"
                + "  memberBirthDate,"
                + "  memberGender,"
                + "  groupNumber,"
                + "  memberId,"
                + "  providerFirstName,"
                + "  providerLastName,"
                + "  organizationName,"
                + "  npi,"
                + "  cptCode,"
                + "  tradingPartnerId,"
                + "  userId,"
                + "  transactionUUID,"
                + "  active"
                + ")"
                + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pStmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        int i = 0;
        SQLUtil.SafeSetString(pStmt, ++i, obj.getApiProvider().name());
        SQLUtil.SafeSetString(pStmt, ++i, obj.getFirstName());
        SQLUtil.SafeSetString(pStmt, ++i, obj.getLastName());
        SQLUtil.SafeSetString(pStmt, ++i, obj.getBirthDate());
        SQLUtil.SafeSetString(pStmt, ++i, obj.getGender());
        SQLUtil.SafeSetString(pStmt, ++i, obj.getGroupNumber());
        SQLUtil.SafeSetString(pStmt, ++i, obj.getMemberId());
        SQLUtil.SafeSetString(pStmt, ++i, obj.getProviderFirstName());
        SQLUtil.SafeSetString(pStmt, ++i, obj.getProviderLastName());
        SQLUtil.SafeSetString(pStmt, ++i, obj.getOrganizationName());
        SQLUtil.SafeSetString(pStmt, ++i, obj.getNpi());
        SQLUtil.SafeSetString(pStmt, ++i, obj.getCptCode());
        SQLUtil.SafeSetString(pStmt, ++i, obj.getTradingPartnerID());
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getUserId());
        SQLUtil.SafeSetString(pStmt, ++i, obj.getTransactionUUID());
        SQLUtil.SafeSetBoolean(pStmt, ++i, Boolean.TRUE);
        
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
                throw new NullPointerException("EligibilityRequestDAO::Insert: Cannot retrieve generated identifier for inserted row.");
            }
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage() + " " + pStmt.toString());
            throw ex;
        }

        return newId;
    }
    
    public static int deactivateAllForTransactionUUID(String transactionUUID) throws SQLException, NullPointerException
        {
            if (transactionUUID == null || transactionUUID.isEmpty())
                throw new NullPointerException("EligibilityRequestDAO::deactivateAllForTransactionUUID received null/empty TransactionUUID.");
            
            Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
            Connection con = dbs.getConnection(true);
            if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);
            
            String update = "UPDATE " + table + " SET `active`=b\'0\' WHERE `transactionUUID`=?";
            PreparedStatement stmt = con.prepareStatement(update);
            SQLUtil.SafeSetString(stmt, 1, transactionUUID);
            int result = stmt.executeUpdate();
            
            return result;
        }
    
    @Override
    public String structureCheck() {
        String query = "SELECT `eligibilityRequests`.`idEligibilityRequestLog`,\n"
                + "    `eligibilityRequests`.`apiProvider`,\n"
                + "    `eligibilityRequests`.`memberFirstName`,\n"
                + "    `eligibilityRequests`.`memberLastName`,\n"
                + "    `eligibilityRequests`.`memberBirthDate`,\n"
                + "    `eligibilityRequests`.`memberGender`,\n"
                + "    `eligibilityRequests`.`groupNumber`,\n"
                + "    `eligibilityRequests`.`memberId`,\n"
                + "    `eligibilityRequests`.`providerFirstName`,\n"
                + "    `eligibilityRequests`.`providerLastName`,\n"
                + "    `eligibilityRequests`.`organizationName`,\n"
                + "    `eligibilityRequests`.`npi`,\n"
                + "    `eligibilityRequests`.`cptCode`,\n"
                + "    `eligibilityRequests`.`tradingPartnerId`,\n"
                + "    `eligibilityRequests`.`userId`,\n"
                + "    `eligibilityRequests`.`transactionUUID`,\n"
                + "    `eligibilityRequests`.`timestamp`,\n"
                + "    `eligibilityRequests`.`active`\n"
                + "FROM `css`.`eligibilityRequests` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, Database.DatabaseSingleton.getDatabaseSingleton().getConnection(true));
    }
}
