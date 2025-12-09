/*
 * Copyright (C) 2013 CSS, Inc.  All Rights Reserved.
 * This software is the proprietary information of CSS, Inc.
 */
package DAOS;

import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IStructureCheckable;
import DOS.Calculations;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.SQLUtil;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * {Insert Class description here}
 *
 * @version $Revision: 1.1.1.1 $
 * @since Build {insert version here} 10/29/2013
 * @author Ryan S. Piper <Ryan@csslis.com >
 */
public class CalculationsDAO implements DAOInterface, IStructureCheckable {

    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`calculations`";
    /**
     * All fields except idOrders
     */
    private final ArrayList<String> fields = new ArrayList<String>();

    public CalculationsDAO() {
        fields.add("idtests");
        fields.add("step");
        fields.add("resultValueId");
        fields.add("numericValue");
        fields.add("operator");
    }

    public boolean InsertCalculation(Calculations calc) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }

        try {
            String stmt = GenerateInsertStatement(fields);

            PreparedStatement pStmt = con.prepareStatement(stmt);

            SQLUtil.SafeSetInteger(pStmt, 1, calc.getIdtests());
            SQLUtil.SafeSetInteger(pStmt, 2, calc.getStep());
            SQLUtil.SafeSetInteger(pStmt, 3, calc.getResultValueId());
            SQLUtil.SafeSetRangeDouble(pStmt, 4, calc.getNumericValue());
            SQLUtil.SafeSetString(pStmt, 5, calc.getOperator());

            pStmt.executeUpdate();

            pStmt.close();

            return true;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return false;
        }
    }

    public boolean UpdateCalculation(Calculations calc) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }

        try {
            String stmt = GenerateUpdateStatement(fields)
                    + " WHERE `idcalculations` = " + calc.getIdcalulations();

            PreparedStatement pStmt = con.prepareStatement(stmt);

            SQLUtil.SafeSetInteger(pStmt, 1, calc.getIdtests());
            SQLUtil.SafeSetInteger(pStmt, 2, calc.getStep());
            SQLUtil.SafeSetInteger(pStmt, 3, calc.getResultValueId());
            SQLUtil.SafeSetRangeDouble(pStmt, 4, calc.getNumericValue());
            SQLUtil.SafeSetString(pStmt, 5, calc.getOperator());

            pStmt.executeUpdate();

            pStmt.close();

            return true;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return false;
        }
    }

    /**
     * Returns a single Calculation step by the calculations SQL ID.
     * <p>
     * Please remember that the full calculation may have many steps.
     *
     * @param ID Calculations SQL ID
     * @return Single Calculation Step Object
     * @throws SQLException
     */
    public Calculations GetCalculationsByID(int ID) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        try {
            Calculations calc = new Calculations();
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table + " "
                    + "WHERE `idcalculations` = " + ID;

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                SetCalcFromResultSet(calc, rs);
            }

            rs.close();
            stmt.close();

            return calc;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }
    
    public ArrayList<Calculations> GetCalculationsByTestNumber(int testNumber) throws SQLException
    {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        try {
            ArrayList<Calculations> list = new ArrayList<>();
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table + " c"
                    + " INNER JOIN tests t on c.idtests = t.idtests"
                    + " WHERE t.number = " + testNumber;

            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                Calculations calc = new Calculations();
                SetCalcFromResultSet(calc, rs);
                list.add(calc);
            }

            return list;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }        
    }

    /**
     * Returns all calculation steps for a test based on the Test's ID.
     *
     * @param ID Test's SQL ID
     * @return ArrayList of all Calculations steps for Test
     * @throws SQLException
     */
    public ArrayList<Calculations> GetCalculationsByTestID(int ID) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        try {
            ArrayList<Calculations> list = new ArrayList<Calculations>();
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table + " "
                    + "WHERE `idtests` = " + ID;

            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                Calculations calc = new Calculations();
                SetCalcFromResultSet(calc, rs);
                list.add(calc);
            }

            return list;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }
    
    /**
     * See if a Test is part of any calculation
     * @param ID Test ID
     * @return True if in a calculation False if not
     */
    public boolean TestPartOfACalculation(int ID){
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }
        
        try{
            boolean result = false;
            Statement stmt = con.createStatement();
            
            String query = "SELECT `idcalculation` FROM " + table + " "
                    + "WHERE `resultValueId` = " + ID;
                    
            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                result = true;
            }
            
            rs.close();
            stmt.close();
            
            return result;
        }catch(SQLException ex){
            System.out.println(ex.toString());
            return false;
        }
    }
    
    public int DeleteUpdatedCalculations(int[] idsary){
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return -1;
        }
        
        try{
            //Create String with ID's for calc to be deleted
            int rows = 0;
            String ids = "";
            for(int i : idsary){
                if(ids.isEmpty())
                    ids = "" + i;
                else
                    ids = ids + ", " + i;
            }
            
            Statement stmt = con.createStatement();
            
            String query = "DELETE FROM " + table + " WHERE "
                    + "`idcalculations` IN (" + ids + ")";
            
            if(!stmt.execute(query)){
                rows = stmt.getUpdateCount();
            }
            
            stmt.close();
            
            return rows;
        }catch(SQLException ex){
            System.out.println(ex.toString());
            return -1;
        }
    }
    
    /**
     * Deletes all calculations rows for the supplied test ID. Used for
     *  non-retroactive calculation saves (wipe out existing and then insert new)
     * @param idTests
     * @return 
     */
    public boolean DeleteCalculationRowsByTestID(int idTests)
    {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }
        
        try{

            Statement stmt = con.createStatement();
            
            String query = "DELETE FROM " + table + " WHERE "
                    + "`idtests` = " + String.valueOf(idTests);
            
            stmt.execute(query);

            stmt.close();
            
            return true;
        }catch(SQLException ex){
            System.out.println(ex.toString());
            return false;
        }        
    }

    private String GenerateInsertStatement(ArrayList<String> fields) {

        String stmt = "INSERT INTO " + table + "(";
        String values = ") values (";
        for (int i = 0; i < fields.size(); ++i) {
            stmt += " `" + fields.get(i) + "`";
            values += "?";
            if (i != fields.size() - 1) {
                stmt += ", ";
                values += ",";
            }
        }
        values += ")";
        return stmt + values;
    }

    private String GenerateUpdateStatement(ArrayList<String> fields) {

        String stmt = "UPDATE " + table + " SET";
        for (int i = 0; i < fields.size(); ++i) {
            stmt += " `" + fields.get(i) + "` = ?";
            if (i != fields.size() - 1) {
                stmt += ",";
            }
        }
        return stmt;
    }

    public Calculations SetCalcFromResultSet(Calculations calc, ResultSet rs) throws SQLException {
        calc.setIdcalulations(rs.getInt("idcalculations"));
        calc.setIdtests(rs.getInt("idtests"));
        calc.setStep(rs.getInt("step"));
        calc.setResultValueId(rs.getInt("resultValueId"));
        calc.setNumericValue(rs.getDouble("numericValue"));
        calc.setOperator(rs.getString("operator"));

        return calc;
    }

    @Override
    public Boolean Insert(Serializable obj)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Boolean Update(Serializable obj)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Boolean Delete(Serializable obj)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Serializable getByID(Integer ID)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public String structureCheck() {
        return DatabaseStructureCheck.structureCheck(fields, table, con);
    }
}
