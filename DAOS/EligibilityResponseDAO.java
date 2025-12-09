/*
 * Computer Service & Support, Inc.  All Rights Reserved.
 */

package DAOS;

import API.Billing.InsuranceAPIResponseData;
import API.Billing.InsuranceAPIResponseData.ServiceType;
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
public class EligibilityResponseDAO implements IStructureCheckable
{
    public static final String table = Database.DatabaseSingleton.getDatabaseSingleton().getProperties().getCssSchema()+ ".`eligibilityResponses`";
    
    public static Integer insert(InsuranceAPIResponseData obj, Integer eligibilityRequestId) throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (obj == null)
        {
            throw new IllegalArgumentException("EligibilityResponseDAO::Insert: Received a NULL InsuranceAPIResponseData object");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "INSERT INTO " + table
                + "("
                + "  eligibilityRequestId,"
                + "  serviceDate,"
                + "  subscriberFirstName,"
                + "  subscriberLastName,"
                + "  subscriberID,"
                + "  subscriberGender,"
                + "  providerNPI,"
                + "  providerName,"
                + "  planDescription,"
                + "  planNumber,"
                + "  groupDescription,"
                + "  groupNumber,"
                + "  insuranceType,"
                + "  planBeginDate,"
                + "  planEndDate,"
                + "  eligibilityBeginDate,"
                + "  eligibilityEndDate,"
                + "  serviceTypes,"
                + "  validRequest,"
                + "  activeCoverage,"
                + "  transactionUUID"
                + ")"
                + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pStmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        int i = 0;
        SQLUtil.SafeSetInteger(pStmt, ++i, eligibilityRequestId);
        SQLUtil.SafeSetDate(pStmt, ++i, obj.getCoverage() == null ? null : obj.getCoverage().getServiceDate());
        SQLUtil.SafeSetString(pStmt, ++i, obj.getSubscriber() == null ? null : obj.getSubscriber().getFirstName());
        SQLUtil.SafeSetString(pStmt, ++i, obj.getSubscriber() == null ? null : obj.getSubscriber().getLastName());
        SQLUtil.SafeSetString(pStmt, ++i, obj.getSubscriber() == null ? null : obj.getSubscriber().getID());
        SQLUtil.SafeSetString(pStmt, ++i, obj.getSubscriber()== null ? null : obj.getSubscriber().getGender());
        SQLUtil.SafeSetString(pStmt, ++i, obj.getProvider() == null ? null : obj.getProvider().getNPI());
        String providerName = null;
        if (obj.getProvider() != null)
        {
            if (obj.getProvider().getOrganizationName() != null && !obj.getProvider().getOrganizationName().isEmpty())
            {
                providerName = obj.getProvider().getOrganizationName();
            }
            else
            {
                providerName = obj.getProvider().getFirstName() + " " + obj.getProvider().getLastName();
            }
        }
        SQLUtil.SafeSetString(pStmt, ++i, providerName);
        SQLUtil.SafeSetString(pStmt, ++i, obj.getCoverage() == null ? null : obj.getCoverage().getPlanDescription());
        SQLUtil.SafeSetString(pStmt, ++i, obj.getCoverage() == null ? null : obj.getCoverage().getPlanNumber());
        SQLUtil.SafeSetString(pStmt, ++i, obj.getCoverage() == null ? null : obj.getCoverage().getGroupDescription());
        SQLUtil.SafeSetString(pStmt, ++i, obj.getCoverage() == null ? null : obj.getCoverage().getGroupNumber());
        SQLUtil.SafeSetString(pStmt, ++i, obj.getCoverage() == null ? null : obj.getCoverage().getInsuranceType());
        SQLUtil.SafeSetDate(pStmt, ++i, obj.getCoverage() == null ? null : obj.getCoverage().getPolicyEffectiveDate());
        SQLUtil.SafeSetDate(pStmt, ++i, obj.getCoverage() == null ? null : obj.getCoverage().getPolicyExpirationDate());
        SQLUtil.SafeSetDate(pStmt, ++i, obj.getCoverage() == null ? null : obj.getCoverage().getEligibilityBeginDate());
        SQLUtil.SafeSetDate(pStmt, ++i, obj.getCoverage() == null ? null : obj.getCoverage().getEligibilityEndDate());
        StringBuilder serviceTypes = new StringBuilder();
        if (obj.getCoverage() != null && obj.getCoverage().getServiceTypes() != null)
        {
            for (ServiceType serviceType : obj.getCoverage().getServiceTypes())
            {
                serviceTypes.append(serviceType.getServiceType());
                serviceTypes.append(",");
            }
            if (serviceTypes.toString().endsWith(",")) serviceTypes.deleteCharAt(serviceTypes.length()-1);
        }
        SQLUtil.SafeSetString(pStmt, ++i, serviceTypes != null && serviceTypes.toString() != null ? serviceTypes.toString() : null);
        SQLUtil.SafeSetBoolean(pStmt, ++i, obj.getCoverage() == null ? null : obj.getCoverage().isValidRequest());
        SQLUtil.SafeSetBoolean(pStmt, ++i, obj.getCoverage() == null ? null : obj.getCoverage().isActive());
        SQLUtil.SafeSetString(pStmt, ++i, obj.getCoverage() == null ? null : obj.getTransactionUUID());
        
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
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage() + " " + pStmt.toString());
            throw ex;
        }

        return newId;
    }
    
    @Override
    public String structureCheck() {
        String query = "SELECT `eligibilityResponses`.`idEligibilityResponses`,\n"
                + "    `eligibilityResponses`.`eligibilityRequestId`,"
                + "    `eligibilityResponses`.`serviceDate`,\n"
                + "    `eligibilityResponses`.`subscriberFirstName`,\n"
                + "    `eligibilityResponses`.`subscriberLastName`,\n"
                + "    `eligibilityResponses`.`subscriberID`,\n"
                + "    `eligibilityResponses`.`subscriberGender`,\n"
                + "    `eligibilityResponses`.`providerNPI`,\n"
                + "    `eligibilityResponses`.`providerName`,\n"
                + "    `eligibilityResponses`.`planDescription`,\n"
                + "    `eligibilityResponses`.`planNumber`,\n"
                + "    `eligibilityResponses`.`groupDescription`,\n"
                + "    `eligibilityResponses`.`groupNumber`,\n"
                + "    `eligibilityResponses`.`insuranceType`,\n"
                + "    `eligibilityResponses`.`planBeginDate`,\n"
                + "    `eligibilityResponses`.`planEndDate`,\n"
                + "    `eligibilityResponses`.`eligibilityBeginDate`,\n"
                + "    `eligibilityResponses`.`eligibilityEndDate`,\n"
                + "    `eligibilityResponses`.`serviceTypes`,\n"
                + "    `eligibilityResponses`.`validRequest`,\n"
                + "    `eligibilityResponses`.`activeCoverage`,\n"
                + "    `eligibilityResponses`.`transactionUUID`,\n"
                + "    `eligibilityResponses`.`timestamp`\n"
                + "FROM `css`.`eligibilityResponses` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, Database.DatabaseSingleton.getDatabaseSingleton().getConnection(true));
    }
}
