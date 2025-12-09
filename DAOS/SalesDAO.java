/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAOS;

import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IStructureCheckable;
import DOS.Sales;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.Convert;
import Utility.SQLUtil;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @date: Apr 28, 2014
 * @author: Derrick J. Piper <derrick@csslis.com>
 *
 * @project:
 * @package: DAOS
 * @file name: SalesDAO.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc. All Rights Reserved
 */
public class SalesDAO implements DAOInterface, IStructureCheckable
{

    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`sales`";

    /**
     * All fields except idterritory
     */
    private final ArrayList<String> fields = new ArrayList<>();

    public SalesDAO()
    {
        fields.add("idterritory");
        fields.add("byOrder");
        fields.add("byTest");
        fields.add("byBilled");
        fields.add("byRecveived");
        fields.add("percentage");
        fields.add("amount");
        fields.add("createdDate");
    }

    /**
     * Description
     * @param obj Serializable The salesmen to be inserted.
     * @return type Boolean returns true if inserted.
     */
    @Override
    public Boolean Insert(Serializable obj)
    {
        Sales sal = (Sales) obj;
        try
        {
            try
            {
                if (con.isClosed()) 
                {
                    con = CheckDBConnection.Check(dbs, con);
                }
            }
            catch (SQLException sex)
            {
                System.out.println(sex.toString());
                return false;
            }
            String stmt = GenerateInsertStatement(fields);
            PreparedStatement pStmt = con.prepareStatement(stmt);
            SetStatementFromSales(sal, pStmt);

            pStmt.executeUpdate();
        

            pStmt.close();

            return true;
        }
        catch (SQLException ex)
        {
            Logger.getLogger(Sales.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    /**
     * Description
     * @param obj Serializable, The salesmen to be updated.
     * @return type Boolean returns true if updated.
     */
    @Override
    public Boolean Update(Serializable obj)
    {
        Sales sal = (Sales) obj;
        try
        {
            try
            {
                if (con.isClosed())
                {
                    con = CheckDBConnection.Check(dbs, con);
                }
            }
            catch (SQLException sex)
            {
                System.out.println(sex.toString());
                return false;
            }
            String stmt = GenerateUpdateStatement(fields) + " WHERE `idsales` = " + sal.getIdsales();
            PreparedStatement pStmt = con.prepareStatement(stmt);

            SetStatementFromSales(sal, pStmt);

            pStmt.executeUpdate();

            pStmt.close();

            return true;
        }
        catch (SQLException ex)
        {
            Logger.getLogger(Sales.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    /**
     * Description
     * @param obj Serializable The salesmen to be deleted.
     * @return type Boolean returns true if deleted.
     */
    @Override
    public Boolean Delete(Serializable obj)
    {
        try
        {
            if (con.isClosed())
            {
                con = CheckDBConnection.Check(dbs, con);
            }
        }
        catch (SQLException sex)
        {
            System.out.println(sex.toString());
            return false;
        }
        return false;
    }

    /**
     * Description
     * @param ID Serializable The salesmen ID to be retrieved.
     * @return type Integer, The ID of the salesmen being searched for.
     */
    @Override
    public Serializable getByID(Integer ID)
    {
        try
        {
            return GetSalesByID(ID);
        }
        catch (SQLException ex)
        {
            Logger.getLogger(SalesDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public Boolean SalesExists(int idemployees) throws SQLException
    {
        try
        {
            if (con.isClosed())
            {
                con = CheckDBConnection.Check(dbs, con);
            }
        }
        catch (SQLException sex)
        {
            System.out.println(sex.toString());
            return null;
        }

        try
        {
            Statement stmt = con.createStatement();
            int rowCount = -1;

            String query = "SELECT COUNT(*) FROM " + table
                    + " WHERE `idemployees` = " + idemployees;

            ResultSet rs = stmt.executeQuery(query);

            rs.next();
            rowCount = rs.getInt(1);

            rs.close();
            stmt.close();
            return rowCount > 0;
        }
        catch (SQLException ex)
        {
            return null;
        }
    }

    /**
     *
     * @param ID type Integer, The salesmen ID.
     * @return String The salesmen data associated with the entered ID.
     * @throws SQLException if query fails.
     */
    public Sales GetSalesByID(int ID) throws SQLException
    {
        try
        {
            if (con.isClosed())
            {
                con = CheckDBConnection.Check(dbs, con);
            }
        }
        catch (SQLException sex)
        {
            System.out.println(sex.toString());
            return null;
        }

        Sales sal = new Sales();

        try
        {
            String query = "SELECT * FROM " + table + " "
                    + "WHERE `idsales` = " + ID;

            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next())
            {
                SetSalesFromResultSet(sal, rs);
            }

            rs.close();
            stmt.close();

            return sal;
        }
        catch (SQLException ex)
        {
            System.out.println(ex.toString());
            return null;
        }
    }

    /**
     *
     * @return type List, return all salesmen.
     * @throws SQLException if query fails.
     */
    public ArrayList<Sales> GetAllSales() throws SQLException
    {
        try
        {
            if (con.isClosed())
            {
                con = CheckDBConnection.Check(dbs, con);
            }
        }
        catch (SQLException sex)
        {
            System.out.println(sex.toString());
            return null;
        }

        ArrayList<Sales> salList = new ArrayList<>();

        try
        {
            Statement stmt = con.createStatement();
            String query = "SELECT * FROM " + table + " ";

            ResultSet rs = stmt.executeQuery(query);

            while (rs.next())
            {
                Sales sal = new Sales();
                SetSalesFromResultSet(sal, rs);

                salList.add(sal);
            }

            rs.close();
            stmt.close();

            return salList;
        }
        catch (SQLException ex)
        {
            System.out.println(ex.toString());
            return null;
        }

    }

    /**
     *
     * @param ID type Integer, the employee ID of the salesmen.
     * @return Integer, returns the salesmen information associated with the employee information.
     * @throws SQLException if query fails.
     */
    public Sales GetSalesByEmployeeID(int ID) throws SQLException
    {
        try
        {
            if (con.isClosed())
            {
                con = CheckDBConnection.Check(dbs, con);
            }
        }
        catch (SQLException sex)
        {
            System.out.println(sex.toString());
            return null;
        }

        Sales sal = new Sales();

        try
        {
            String query = "SELECT * FROM " + table + " "
                    + "WHERE `idemployees` = " + ID;

            PreparedStatement pStmt = con.prepareStatement(query);
            pStmt.setInt(1, ID);

            ResultSet rs = pStmt.executeQuery();

            if (rs.next())
            {
                SetSalesFromResultSet(sal, rs);
            }

            rs.close();
            pStmt.close();

            return sal;
        }
        catch (SQLException ex)
        {
            System.out.println(ex.toString());
            return null;
        }
    }

    private String GenerateInsertStatement(ArrayList<String> fields)
    {

        String stmt = "INSERT INTO " + table + "(";
        String values = ") values (";
        for (int i = 0; i < fields.size(); ++i)
        {
            stmt += " `" + fields.get(i) + "`";
            values += "?";
            if (i != fields.size() - 1)
            {
                stmt += ", ";
                values += ",";
            }
        }
        values += ")";
        return stmt + values;
    }

    private String GenerateUpdateStatement(ArrayList<String> fields)
    {

        String stmt = "UPDATE " + table + " SET";
        for (int i = 0; i < fields.size(); ++i)
        {
            stmt += " `" + fields.get(i) + "` = ?";
            if (i != fields.size() - 1)
            {
                stmt += ",";
            }
        }
        return stmt;
    }

    private Sales SetSalesFromResultSet(Sales sales, ResultSet rs) throws SQLException
    {
        sales.setIdsales(rs.getInt("idsales"));
        sales.setIdemployees(rs.getInt("idemployees"));
        sales.setIdterritory(rs.getInt("idterritory"));
        sales.setByOrder(rs.getBoolean("byOrder"));
        sales.setByTest(rs.getBoolean("byTest"));
        sales.setByBilled(rs.getBoolean("byBilled"));
        sales.setByReceived(rs.getBoolean("byRecveived"));
        sales.setPercentage(rs.getDouble("percentage"));
        sales.setAmount(rs.getDouble("amount"));
        sales.setCreatedDate(rs.getTimestamp("createdDate"));

        return sales;
    }

    private PreparedStatement SetStatementFromSales(Sales sal, PreparedStatement pStmt) throws SQLException
    {
        pStmt.setInt(1, sal.getIdemployees());
        pStmt.setInt(2, sal.getIdterritory());
        pStmt.setBoolean(3, sal.getByOrder());
        pStmt.setBoolean(4, sal.getByTest());
        pStmt.setBoolean(5, sal.getByBilled());
        pStmt.setBoolean(6, sal.getByReceived());
        SQLUtil.SafeSetDouble(pStmt, 7, sal.getPercentage());
        SQLUtil.SafeSetDouble(pStmt, 8, sal.getAmount());
        pStmt.setTimestamp(9, Convert.ToSQLDateTime(sal.getCreatedDate()));
        return pStmt;
    }
    
    @Override
    public String structureCheck() {
        return DatabaseStructureCheck.structureCheck(fields, table, con);
    }
}
