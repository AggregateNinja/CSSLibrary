/*
 * Copyright (C) 2013 CSS, Inc.  All Rights Reserved.
 * This software is the proprietary information of CSS, Inc.
 */
package DAOS;

/**
 * {Insert Class description here}
 *
 * @version $Revision: 1.1.1.1 $
 * @since Build {insert version here} 10/15/2013
 * @author Ryan S. Piper <Ryan@csslis.com >
 */
import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IStructureCheckable;
import java.sql.Connection;
import DOS.ReflexRules;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.SQLUtil;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ReflexRulesDAO implements DAOInterface, IStructureCheckable {

    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`reflexRules`";
    
    /**
     * All fields except ID
     */
    private final ArrayList<String> fields = new ArrayList<String>();
    
    public ReflexRulesDAO() {
        fields.add("idtests");
        fields.add("isHigh");
        fields.add("isLow");
        fields.add("isCIDHigh");
        fields.add("isCIDLow");
        fields.add("reflexRemark");
        fields.add("remarkTest");
        fields.add("active");
    }
    
    @Override
    public Boolean Insert(Serializable obj)
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
            ReflexRules rr = (ReflexRules) obj;
            String stmt = GenerateInsertStatement(fields);
            PreparedStatement pStmt = con.prepareStatement(stmt);
            SetStatement(rr, pStmt);
            pStmt.executeUpdate();
            
            pStmt.close();
            
            return true;
        } catch (SQLException ex) {
            String message = ex.toString();
            System.out.println(message);
            return false;
        }
    }

    @Override
    public Boolean Update(Serializable obj)
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
            ReflexRules rr = (ReflexRules) obj;
            String stmt = GenerateUpdateStatement(fields)
                    + " WHERE `idreflexRules` = " + rr.getIdreflexRules();
            PreparedStatement pStmt = con.prepareStatement(stmt);
            SetStatement(rr, pStmt);
            pStmt.executeUpdate();
            
            pStmt.close();
            
            return true;
        } catch (SQLException ex) {
            String message = ex.toString();
            System.out.println(message);
            return false;
        }
    }

    @Override
    public Boolean Delete(Serializable obj)
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
            ReflexRules rr = (ReflexRules) obj;
            String stmt = "DELETE FROM " + table + " "
                    + "WHERE `idreflexRules` = " + rr.getIdreflexRules();
            PreparedStatement pStmt = con.prepareStatement(stmt);
            SetStatement(rr, pStmt);
            pStmt.executeUpdate();
            
            pStmt.close();
            
            return true;
        } catch (SQLException ex) {
            String message = ex.toString();
            System.out.println(message);
            return false;
        }
    }

    @Override
    public Serializable getByID(Integer ID)
    {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }
        try {
            ReflexRules rr = new ReflexRules();
            Statement stmt = con.createStatement();
            String query = "SELECT * FROM " + table + " "
                    + "WHERE `idreflexRules` = " + ID;
            
            ResultSet rs = stmt.executeQuery(query);
            
            if(rs.next()){
                SetFromResultSet(rr, rs);
            }
            
            rs.close();
            stmt.close();
            
            return rr;
        } catch (SQLException ex) {
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }

    public Integer InsertReflexRuleGetID(ReflexRules rr) throws SQLException
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
            String stmt = "INSERT INTO " + table + " ("
                    + "`idtests`, "
                    + "`isHigh`, "
                    + "`isLow`, "
                    + "`isCIDHigh`, "
                    + "`isCIDLow`, "
                    + "`reflexRemark`, "
                    + "`remarkTest`) "
                    + "values (?,?,?,?,?,?,?)";

            PreparedStatement pStmt = con.prepareStatement(stmt,
                    PreparedStatement.RETURN_GENERATED_KEYS);

            SQLUtil.SafeSetInteger(pStmt, 1, rr.getIdTests());
            SQLUtil.SafeSetInteger(pStmt, 2, rr.getIsHigh());
            SQLUtil.SafeSetInteger(pStmt, 3, rr.getIsLow());
            SQLUtil.SafeSetInteger(pStmt, 4, rr.getIsCIDHigh());
            SQLUtil.SafeSetInteger(pStmt, 5, rr.getIsCIDLow());
            SQLUtil.SafeSetInteger(pStmt, 6, rr.getReflexRemark());
            SQLUtil.SafeSetInteger(pStmt, 7, rr.getRemarkTest());

            pStmt.executeUpdate();

            ResultSet rs = pStmt.getGeneratedKeys();
            Integer key = -1;
            if (rs != null && rs.next())
            {
                key = rs.getInt(1);
            }
            pStmt.close();
            
            return key;

        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }        
    }
    
    
    public boolean InsertReflexRule(ReflexRules rr) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }

        try {
            String stmt = "INSERT INTO " + table + " ("
                    + "`idtests`, "
                    + "`isHigh`, "
                    + "`isLow`, "
                    + "`isCIDHigh`, "
                    + "`isCIDLow`, "
                    + "`reflexRemark`, "
                    + "`remarkTest`) "
                    + "values (?,?,?,?,?,?,?)";

            PreparedStatement pStmt = con.prepareStatement(stmt);

            SQLUtil.SafeSetInteger(pStmt, 1, rr.getIdTests());
            SQLUtil.SafeSetInteger(pStmt, 2, rr.getIsHigh());
            SQLUtil.SafeSetInteger(pStmt, 3, rr.getIsLow());
            SQLUtil.SafeSetInteger(pStmt, 4, rr.getIsCIDHigh());
            SQLUtil.SafeSetInteger(pStmt, 5, rr.getIsCIDLow());
            SQLUtil.SafeSetInteger(pStmt, 6, rr.getReflexRemark());
            SQLUtil.SafeSetInteger(pStmt, 7, rr.getRemarkTest());

            pStmt.executeUpdate();

            pStmt.close();

            return true;

        } catch (Exception ex) {
            System.out.println(ex.toString());
            return false;
        }
    }

    public boolean UpdateReflexRule(ReflexRules rr) throws SQLException {
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
                    + "`idtests` = ?, "
                    + "`isHigh` = ?, "
                    + "`isLow` = ?, "
                    + "`isCIDHigh` = ?, "
                    + "`isCIDLow` = ?, "
                    + "`reflexRemark` = ?, "
                    + "`remarkTest` = ? "
                    + "WHERE `idreflexRules = " + rr.getIdreflexRules();

            PreparedStatement pStmt = con.prepareStatement(stmt);

            SQLUtil.SafeSetInteger(pStmt, 1, rr.getIdTests());
            SQLUtil.SafeSetInteger(pStmt, 2, rr.getIsHigh());
            SQLUtil.SafeSetInteger(pStmt, 3, rr.getIsLow());
            SQLUtil.SafeSetInteger(pStmt, 4, rr.getIsCIDHigh());
            SQLUtil.SafeSetInteger(pStmt, 5, rr.getIsCIDLow());
            SQLUtil.SafeSetInteger(pStmt, 6, rr.getReflexRemark());
            SQLUtil.SafeSetInteger(pStmt, 7, rr.getRemarkTest());

            pStmt.executeUpdate();

            pStmt.close();

            return true;

        } catch (Exception ex) {
            System.out.println(ex.toString());
            return false;
        }
    }

    public ReflexRules GetRuleByID(int ID) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        try {
            ReflexRules rr = new ReflexRules();
            String query = "SELECT * FROM " + table
                    + " WHERE `idreflexRules` = ?"
                    + " AND `active` = ?";

            PreparedStatement pStmt = con.prepareStatement(query);

            SQLUtil.SafeSetInteger(pStmt, 1, ID);
            pStmt.setBoolean(2, true);

            ResultSet rs = pStmt.executeQuery();

            if (rs.next()) {
                SetFromResultSet(rr, rs);
            }

            rs.close();
            pStmt.close();

            return rr;

        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }

    public ArrayList<ReflexRules> GetRulesByTestID(int ID){
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        try {
            ArrayList<ReflexRules> list = new ArrayList<ReflexRules>();

            String query = "SELECT * FROM " + table + " "
                    + "WHERE `idtests` = ? "
                    + "AND `active` = ?";

            PreparedStatement pStmt = con.prepareStatement(query);

            SQLUtil.SafeSetInteger(pStmt, 1, ID);
            pStmt.setBoolean(2, true);

            ResultSet rs = pStmt.executeQuery();

            while (rs.next()) {
                ReflexRules rr = new ReflexRules();
                SetFromResultSet(rr, rs);
                list.add(rr);
            }

            rs.close();
            pStmt.close();

            return list;
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            return null;
        }
    }
    
    public int ReflexRuleCount(int testID){
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return -1;
        }
        
        try{
            int count = 0;
            Statement stmt = con.createStatement();
            String query = "SELECT COUNT(*) AS 'Rules' FROM " + table + " "
                    + "WHERE `idtests` = " + testID + " "
                    + "AND `active` = " + true;
               
            ResultSet rs = stmt.executeQuery(query);
            
            if(rs.next()){
                count = rs.getInt("Rules");
            }
            
            rs.close();
            stmt.close();
            
            return count;
        } catch (SQLException ex) {
            String message = ex.toString();
            System.out.println(ex.toString());
            return -1;
        }
    }

    public boolean DeactivateRuleByID(int ID) throws SQLException {
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
                    + "`active` = ? "
                    + "WHERE `idreflexRules` = ?";

            PreparedStatement pStmt = con.prepareStatement(stmt);

            pStmt.setBoolean(1, false);
            SQLUtil.SafeSetInteger(pStmt, 2, ID);

            pStmt.executeUpdate();

            pStmt.close();

            return true;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return false;
        }

    }

    public boolean DeactivateRuleByTestID(int ID) throws SQLException {
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
                    + "`active` = ? "
                    + "WHERE `idtests` = ?";

            PreparedStatement pStmt = con.prepareStatement(stmt);

            pStmt.setBoolean(1, false);
            SQLUtil.SafeSetInteger(pStmt, 2, ID);

            pStmt.executeUpdate();

            pStmt.close();

            return true;
        } catch (Exception ex) {
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

    public ReflexRules SetFromResultSet(ReflexRules obj, ResultSet rs) throws SQLException {
        obj.setIdreflexRules(rs.getInt("idreflexRules"));
        obj.setIdTests(rs.getInt("idtests"));
        obj.setIsHigh(rs.getInt("isHigh"));
        obj.setIsLow(rs.getInt("isLow"));
        obj.setIsCIDHigh(rs.getInt("isCIDHigh"));
        obj.setIsCIDLow(rs.getInt("isCIDLow"));
        obj.setReflexRemark(rs.getInt("reflexRemark"));
        obj.setRemarkTest(rs.getInt("remarkTest"));
        obj.setActive(rs.getBoolean("active"));

        return obj;
    }
    
    private PreparedStatement SetStatement(ReflexRules obj, PreparedStatement pStmt) throws SQLException {
        SQLUtil.SafeSetInteger(pStmt, 1, obj.getIdTests());
        SQLUtil.SafeSetInteger(pStmt, 2, obj.getIsHigh());
        SQLUtil.SafeSetInteger(pStmt, 3, obj.getIsLow());
        SQLUtil.SafeSetInteger(pStmt, 4, obj.getIsCIDHigh());
        SQLUtil.SafeSetInteger(pStmt, 5, obj.getIsCIDLow());
        SQLUtil.SafeSetInteger(pStmt, 6, obj.getReflexRemark());
        SQLUtil.SafeSetInteger(pStmt, 7, obj.getRemarkTest());
        SQLUtil.SafeSetBoolean(pStmt, 8, obj.isActive());
        
        return pStmt;
    }
    
    @Override
    public String structureCheck() {
        return DatabaseStructureCheck.structureCheck(fields, table, con);
    }
}
