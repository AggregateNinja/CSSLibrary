/*
 * Computer Service & Support, Inc.  All Rights Reserved.
 */

package BL;

import DAOS.DiagnosisValidityLookupDAO;
import DOS.BillingPayor;
import DOS.DiagnosisValidityLookup;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Rob <r.hussey@csslis.com>
 * @date Mar 28, 2017
 */
public class DiagnosisValidityBL
{

    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    
    public List<DiagnosisValidityLookup> search(DiagnosisValidityLookupDAO.SearchType searchType, BillingPayor billingPayor, Integer diagnosisCodeId, Integer cptCodeId)
    {
        String query = "SELECT `dvl`.* FROM `diagnosisValidityLookup` `dvl` "
                + "INNER JOIN `diagnosisValidity` `dv` ON `dvl`.`diagnosisValidityId` = `dv`.`idDiagnosisValidity` "
                + "INNER JOIN `billingPayors` `bp` ON `dv`.`billingPayorId` = `bp`.`idBillingPayors` "
                + "WHERE F_DATE_RANGES_OVERLAP(NOW(), NOW(), `dvl`.`startDate`, `dvl`.`endDate`) = 1 ";
        switch (searchType)
        {
            case ACTIVE_ONLY:
                query += "AND `dv`.`active` = 1 AND `dvl`.`active` = 1 ";
                break;
            case INACTIVE_ONLY:
                query += "AND `dv`.`active` = 0 AND `dvl`.`active` = 0 ";
                break;
            case ANY:
                break;
        }
        
        query += "AND `bp`.`idBillingPayors` = ? ";
        if (diagnosisCodeId != null)
        {
            query += "AND `dvl`.`diagnosisCodeId` = ? ";
        }
        if (cptCodeId != null)
        {
            query += "AND `dvl`.`cptCodeId` = ? ";
        }
        
        List<DiagnosisValidityLookup> diagnosisValidityList = new ArrayList<DiagnosisValidityLookup>();
        try
        {
            int index = 1;
            java.sql.PreparedStatement pStmt = con.prepareStatement(query);
            
            if (billingPayor == null) pStmt.setNull(index++, Types.INTEGER);
            else pStmt.setInt(index++, billingPayor.getIdbillingPayors());
            
            if (diagnosisCodeId != null)
            {
                pStmt.setInt(index++, diagnosisCodeId);
            }
            if (cptCodeId != null)
            {
                pStmt.setInt(index++, cptCodeId);
            }
            
            System.out.println(pStmt);
            
            ResultSet resultSet = pStmt.executeQuery();
            
            DiagnosisValidityLookup diagnosisValidityLookup;
            while (resultSet.next())
            {
                diagnosisValidityLookup = (new DiagnosisValidityLookupDAO()).GetDiagnosisValidityLookupFromResultSet(resultSet);
                diagnosisValidityList.add(diagnosisValidityLookup);
            }
            
            return diagnosisValidityList;
        }
        catch (SQLException ex)
        {
            System.out.println("DiagnosisValidityBL::search: " +
                    " Unable to retrieve diagnosis validity records: " + ex.toString() + " : " + ex.getMessage());
        }
        catch (Exception ex)
        {
            System.out.println("DiagnosisValidityBL::search: " +
                    " Exception while retrieving and creating diagnosis validity records: " + ex.toString() + " : " + ex.getMessage());
        }
        
        return diagnosisValidityList;
    }
    
    public List<DiagnosisValidityLookup> search(DiagnosisValidityLookupDAO.SearchType searchType, int diagnosisValidityId, Integer diagnosisCodeId, Integer cptCodeId)
    {
        String query = "SELECT `dvl`.* FROM `diagnosisValidityLookup` `dvl` "
                + "INNER JOIN `diagnosisValidity` `dv` ON `dvl`.`diagnosisValidityId` = `dv`.`idDiagnosisValidity` "
                + "WHERE F_DATE_RANGES_OVERLAP(NOW(), NOW(), `dvl`.`startDate`, `dvl`.`endDate`) = 1 ";
        switch (searchType)
        {
            case ACTIVE_ONLY:
                query += "AND `dv`.`active` = 1 AND `dvl`.`active` = 1 ";
                break;
            case INACTIVE_ONLY:
                query += "AND `dv`.`active` = 0 AND `dvl`.`active` = 0 ";
                break;
            case ANY:
                break;
        }
        
        if (diagnosisCodeId != null)
        {
            query += "AND `dvl`.`diagnosisCodeId` = ? ";
        }
        if (cptCodeId != null)
        {
            query += "AND `dvl`.`cptCodeId` = ? ";
        }
        
        query += "AND `dv`.`idDiagnosisValidity` = ? ";
        
        List<DiagnosisValidityLookup> diagnosisValidityList = new ArrayList<DiagnosisValidityLookup>();
        try
        {
            int index = 1;
            java.sql.PreparedStatement pStmt = con.prepareStatement(query);
            
            if (diagnosisCodeId != null)
            {
                pStmt.setInt(index++, diagnosisCodeId);
            }
            if (cptCodeId != null)
            {
                pStmt.setInt(index++, cptCodeId);
            }
            pStmt.setInt(index++, diagnosisValidityId);
            
            System.out.println(pStmt);
            
            ResultSet resultSet = pStmt.executeQuery();
            
            DiagnosisValidityLookup diagnosisValidityLookup;
            while (resultSet.next())
            {
                diagnosisValidityLookup = (new DiagnosisValidityLookupDAO()).GetDiagnosisValidityLookupFromResultSet(resultSet);
                diagnosisValidityList.add(diagnosisValidityLookup);
            }
            
            return diagnosisValidityList;
        }
        catch (SQLException ex)
        {
            System.out.println("DiagnosisValidityBL::search: " +
                    " Unable to retrieve diagnosis validity records: " + ex.toString() + " : " + ex.getMessage());
        }
        catch (Exception ex)
        {
            System.out.println("DiagnosisValidityBL::search: " +
                    " Exception while retrieving and creating diagnosis validity records: " + ex.toString() + " : " + ex.getMessage());
        }
        
        return diagnosisValidityList;
    }
}
