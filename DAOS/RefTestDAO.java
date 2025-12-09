/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DAOS;

import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IStructureCheckable;
import DOS.RefResult;
import DOS.RefTest;
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
 *
 * @author TomR
 */
public class RefTestDAO implements DAOInterface, IStructureCheckable
{
    //--------------------------------------------------------------------------
    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`refTests`";
    private final ArrayList<String> fields = new ArrayList<>();
    ArrayList<Integer> generatedIDs = new ArrayList<>();
    //--------------------------------------------------------------------------
    public RefTestDAO()
    {
        fields.add("departmentId");
        fields.add("identifier");
        fields.add("name");
        fields.add("units");
        fields.add("range");
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
        
        try
        {
            RefTest rt = (RefTest) obj;
            String stmt = GenerateInsertStatement(fields);
            PreparedStatement pStmt = con.prepareStatement(stmt);
            SetStatement(rt, pStmt);
            pStmt.executeUpdate();
            pStmt.close();
            return true;
        }
        catch (SQLException ex)
        {
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
        try {
            RefTest rt = (RefTest) obj;
            String stmt = GenerateUpdateStatement(fields)
                    + " WHERE `idRefTests` = " + rt.getIdRefTests();
            PreparedStatement pStmt = con.prepareStatement(stmt);
            SetStatement(rt, pStmt);
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
            RefTest rt = (RefTest) obj;
            Statement stmt = con.createStatement();
            String query = "DELETE FROM " + table + " "
                    + "WHERE `idRefTest` = " + rt.getIdRefTests();
            
            int result = stmt.executeUpdate(query);
            
            stmt.close();
            
            return true;
        }catch (SQLException ex) {
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
        try{
            RefTest rt = new RefTest();
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + " WHERE `idRefTests` = " + ID;

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                FromResultSet(rt, rs);
            }

            rs.close();
            stmt.close();

            return rt;
        }catch (SQLException ex) {
            String message = ex.toString();
            System.out.println(message);
            return null;
        }         
    }
    
    //--------------------------------------------------------------------------
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
    
    //--------------------------------------------------------------------------
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

    //--------------------------------------------------------------------------
    private RefTest FromResultSet(RefTest obj, ResultSet rs) throws SQLException
    {
        obj.setIdRefTests(rs.getInt("idRefTests"));
        obj.setDepartmentId(rs.getInt("departmentId"));
        obj.setIdentifier(rs.getString("identifier"));
        obj.setName(rs.getString("name"));
        obj.setUnits(rs.getString("units"));
        obj.setRange(rs.getString("range"));
        obj.setActive(rs.getBoolean("active"));
        
        return obj;
    }

    //--------------------------------------------------------------------------
    private PreparedStatement SetStatement(RefTest obj, PreparedStatement pStmt) throws SQLException {
        int i = 0;
        
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getIdRefTests());
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getDepartmentId());
        SQLUtil.SafeSetString(pStmt, ++i, obj.getIdentifier());
        SQLUtil.SafeSetString(pStmt, ++i, obj.getName());
        SQLUtil.SafeSetString(pStmt, ++i, obj.getUnits());
        SQLUtil.SafeSetString(pStmt, ++i, obj.getRange());        
        SQLUtil.SafeSetBoolean(pStmt, ++i, obj.isActive());        
        return pStmt;
    }
    
    @Override
    public String structureCheck() {
        return DatabaseStructureCheck.structureCheck(fields, table, con);
    }
}
