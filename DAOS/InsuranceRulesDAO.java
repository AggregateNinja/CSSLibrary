package DAOS;

import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IStructureCheckable;
import DOS.Insurances;
import DOS.InsuranceRules;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.SQLUtil;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @date: Sep 18, 2013
 * @author: Keith Maggio <keith@csslis.com>
 *
 * @project:
 * @package: DAOS
 * @file name: InsuranceRulesDAO.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc. All Rights Reserved
 */
public class InsuranceRulesDAO implements DAOInterface, IStructureCheckable {

    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`insuranceRules`";

    public boolean InsertInsurancesRule(InsuranceRules rule) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }

        try {
            String stmt = "INSERT INTO " + table + "( "
                    + "`idinsuranceRules`, "
                    + "`doctorRequired`, "
                    + "`addressRequired`, "
                    + "`ageRequired`, "
                    + "`birthDateRequired`, "
                    + "`locationRequired`, "
                    + "`genderRequired`, "
                    + "`diagnosisRequired`, "
                    + "`subscriberRequired`, "
                    + "`relationshipRequired`, "
                    + "`groupNumberRequired`, "
                    + "`group2NumberRequired`, "
                    + "`policyNumberRequired`, "
                    + "`policy2NumberRequired`, "
                    + "`medicareNumberRequired`, "
                    + "`medicaidNumberRequired`)"
                    + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement pStmt = con.prepareStatement(stmt);

            pStmt.setInt(1, rule.getIdinsurancesRules());
            SQLUtil.SafeSetBoolean(pStmt, 2, rule.getDoctorRequired(), false);
            SQLUtil.SafeSetBoolean(pStmt, 3, rule.getAddressRequired(), false);
            SQLUtil.SafeSetBoolean(pStmt, 4, rule.getAgeRequired(), false);
            SQLUtil.SafeSetBoolean(pStmt, 5, rule.getBirthDateRequired(), false);
            SQLUtil.SafeSetBoolean(pStmt, 6, rule.getLocationRequired(), false);
            SQLUtil.SafeSetBoolean(pStmt, 7, rule.getGenderRequired(), false);
            SQLUtil.SafeSetBoolean(pStmt, 8, rule.getDiagnosisRequired(), false);
            SQLUtil.SafeSetBoolean(pStmt, 9, rule.getSubscriberRequired(), false);
            SQLUtil.SafeSetBoolean(pStmt, 10, rule.getRelationshipRequired(), false);
            SQLUtil.SafeSetBoolean(pStmt, 11, rule.getGroupNumberRequired(), false);
            SQLUtil.SafeSetBoolean(pStmt, 12, rule.getGroup2NumberRequired(), false);
            SQLUtil.SafeSetBoolean(pStmt, 13, rule.getPolicyNumberRequired(), false);
            SQLUtil.SafeSetBoolean(pStmt, 14, rule.getPolicy2NumberRequired(), false);
            SQLUtil.SafeSetBoolean(pStmt, 15, rule.getMedicareNumberRequired(), false);
            SQLUtil.SafeSetBoolean(pStmt, 16, rule.getMedicaidNumberRequired(), false);


            pStmt.executeUpdate();

            pStmt.close();

            return true;
        } catch (Exception ex) {
            String message = ex.toString();
            //TODO:  Add logging for failures
            System.out.println(message);
            return false;
        }
    }

    public boolean UpdateInsurancesRule(InsuranceRules rule) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }

        try {
            String stmt = "UPDATE " + table + " SET "
                    + "`doctorRequired` = ?, "
                    + "`addressRequired` = ?, "
                    + "`ageRequired` = ?, "
                    + "`birthDateRequired` = ?, "
                    + "`locationRequired` = ?, "
                    + "`genderRequired` = ?, "
                    + "`diagnosisRequired` = ?, "
                    + "`subscriberRequired` = ?, "
                    + "`relationshipRequired` = ?, "
                    + "`groupNumberRequired` = ?, "
                    + "`group2NumberRequired` = ?, "
                    + "`policyNumberRequired` = ?, "
                    + "`policy2NumberRequired` = ?, "
                    + "`medicareNumberRequired` = ?, "
                    + "`medicaidNumberRequired` = ? "
                    + "WHERE `idinsuranceRules` = " + rule.getIdinsurancesRules();

            PreparedStatement pStmt = con.prepareStatement(stmt);

            SQLUtil.SafeSetBoolean(pStmt, 1, rule.getDoctorRequired(), false);
            SQLUtil.SafeSetBoolean(pStmt, 2, rule.getAddressRequired(), false);
            SQLUtil.SafeSetBoolean(pStmt, 3, rule.getAgeRequired(), false);
            SQLUtil.SafeSetBoolean(pStmt, 4, rule.getBirthDateRequired(), false);
            SQLUtil.SafeSetBoolean(pStmt, 5, rule.getLocationRequired(), false);
            SQLUtil.SafeSetBoolean(pStmt, 6, rule.getGenderRequired(), false);
            SQLUtil.SafeSetBoolean(pStmt, 7, rule.getDiagnosisRequired(), false);
            SQLUtil.SafeSetBoolean(pStmt, 8, rule.getSubscriberRequired(), false);
            SQLUtil.SafeSetBoolean(pStmt, 9, rule.getRelationshipRequired(), false);
            SQLUtil.SafeSetBoolean(pStmt, 10, rule.getGroupNumberRequired(), false);
            SQLUtil.SafeSetBoolean(pStmt, 11, rule.getGroup2NumberRequired(), false);
            SQLUtil.SafeSetBoolean(pStmt, 12, rule.getPolicyNumberRequired(), false);
            SQLUtil.SafeSetBoolean(pStmt, 13, rule.getPolicy2NumberRequired(), false);
            SQLUtil.SafeSetBoolean(pStmt, 14, rule.getMedicareNumberRequired(), false);
            SQLUtil.SafeSetBoolean(pStmt, 15, rule.getMedicaidNumberRequired(), false);


            pStmt.executeUpdate();

            pStmt.close();

            return true;
        } catch (Exception ex) {
            String message = ex.toString();
            //TODO:  Add exception handleing
            System.out.println(message);
            return false;
        }
    }
    
    /**
     * Removes single insuranceRules row from the database completely.
     *  Be sure to log this change before calling this method.
     * @param insuranceRuleId
     * @throws SQLException
     * @throws IllegalArgumentException
     * @throws NullPointerException 
     */
    public void DeleteInsuranceRules(int insuranceRuleId)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (insuranceRuleId <= 0) throw new IllegalArgumentException("InsuranceRulesDAO::DeleteInsuranceRules: Supplied insuranceRuleId was " + insuranceRuleId);
        
        if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs,con);
        
        String sql = "DELETE FROM " + table + " WHERE `idinsuranceRules` = " + insuranceRuleId;
        
        PreparedStatement pStmt = con.prepareStatement(sql);
        int result = pStmt.executeUpdate();
        if (result == 0)
        {
            throw new SQLException("InsuranceRulesDAO::DeleteInsuranceRules: Executed statement " + sql + " but no rows were affected");
        }
    }

    public InsuranceRules GetInsuranceRules(int InsurancesRuleId) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        try {
            InsuranceRules rule = new InsuranceRules();
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + " WHERE `idinsuranceRules` = " + InsurancesRuleId;

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                rule.setIdinsurancesRules(rs.getInt("idinsuranceRules"));
                rule.setDoctorRequired(rs.getBoolean("doctorRequired"));
                rule.setAddressRequired(rs.getBoolean("addressRequired"));
                rule.setAgeRequired(rs.getBoolean("ageRequired"));
                rule.setBirthDateRequired(rs.getBoolean("birthDateRequired"));
                rule.setLocationRequired(rs.getBoolean("locationRequired"));
                rule.setGenderRequired(rs.getBoolean("genderRequired"));
                rule.setDiagnosisRequired(rs.getBoolean("diagnosisRequired"));
                rule.setSubscriberRequired(rs.getBoolean("subscriberRequired"));
                rule.setRelationshipRequired(rs.getBoolean("relationshipRequired"));
                rule.setGroupNumberRequired(rs.getBoolean("groupNumberRequired"));
                rule.setGroup2NumberRequired(rs.getBoolean("group2NumberRequired"));
                rule.setPolicyNumberRequired(rs.getBoolean("policyNumberRequired"));
                rule.setPolicy2NumberRequired(rs.getBoolean("policy2NumberRequired"));
                rule.setMedicareNumberRequired(rs.getBoolean("medicareNumberRequired"));
                rule.setMedicaidNumberRequired(rs.getBoolean("medicaidNumberRequired"));
            }

            rs.close();
            stmt.close();
            
            return rule;
        } catch (Exception ex) {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }

    @Override
    public Boolean Insert(Serializable obj)
    {
        try
        {
            return InsertInsurancesRule((InsuranceRules)obj);
        }
        catch (SQLException ex)
        {
            Logger.getLogger(InsuranceRulesDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public Boolean Update(Serializable obj)
    {
        return UpdateInsurancesRule((InsuranceRules)obj);
    }

    @Override
    public Boolean Delete(Serializable obj)
    {
        return false;
    }

    @Override
    public Serializable getByID(Integer ID)
    {
        try
        {
            return GetInsuranceRules(ID);
        }
        catch (SQLException ex)
        {
            Logger.getLogger(InsuranceRulesDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    @Override
    public String structureCheck() {
        String query = "SELECT `insuranceRules`.`idinsuranceRules`,\n"
                + "    `insuranceRules`.`doctorRequired`,\n"
                + "    `insuranceRules`.`addressRequired`,\n"
                + "    `insuranceRules`.`ageRequired`,\n"
                + "    `insuranceRules`.`birthDateRequired`,\n"
                + "    `insuranceRules`.`locationRequired`,\n"
                + "    `insuranceRules`.`genderRequired`,\n"
                + "    `insuranceRules`.`diagnosisRequired`,\n"
                + "    `insuranceRules`.`subscriberRequired`,\n"
                + "    `insuranceRules`.`relationshipRequired`,\n"
                + "    `insuranceRules`.`groupNumberRequired`,\n"
                + "    `insuranceRules`.`group2NumberRequired`,\n"
                + "    `insuranceRules`.`policyNumberRequired`,\n"
                + "    `insuranceRules`.`policy2NumberRequired`,\n"
                + "    `insuranceRules`.`medicareNumberRequired`,\n"
                + "    `insuranceRules`.`medicaidNumberRequired`\n"
                + "FROM `css`.`insuranceRules` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, con);
    }
    
}
