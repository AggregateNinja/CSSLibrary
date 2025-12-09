package DAOS;

import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IStructureCheckable;
import DOS.AutoOrderTests;
import DOS.Clients;
import DOS.Tests;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 * @date: Nov 26, 2013
 * @author: Keith Maggio <keith@csslis.com>
 *
 * @project:
 * @package: DAOS
 * @file name: AutoOrderTestDAO.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc. All Rights Reserved
 */
public class AutoOrderTestDAO implements DAOInterface, IStructureCheckable
{

    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`AutoOrderTests`";

    public void InsertAutoOrderTests(AutoOrderTests aot) throws IllegalArgumentException, SQLException
    {
        if (aot == null || aot.getClientId() == 0 || aot.getTestNumber() == 0)
        {
            throw new IllegalArgumentException("AutoOrderTestDAO::InsertAutoOrderTests: "
                    + "Received [NULL] or empty AutoOrderTests object for insert");
        }
        
        if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);
        PreparedStatement pStmt = null;
        try
        {
            String sql = "INSERT INTO " + table + " ("
                    + " `clientId`,"
                    + " `testNumber`)"
                    + " values (?,?)";

            pStmt = con.prepareStatement(sql);
            pStmt.setInt(1, aot.getClientId());
            pStmt.setInt(2, aot.getTestNumber());
            pStmt.executeUpdate();
        }
        finally
        {
            if (pStmt != null) pStmt.close();
        }
    }
    
    public void DeleteByClientId(int clientId)
            throws IllegalArgumentException, SQLException
    {
        if (clientId <= 0)
        {
            throw new IllegalArgumentException("AutoOrderTestDAO::DeleteByClientId: "
                    + "Received invalid parameter: clientId " + clientId);
        }
        
        if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);

        PreparedStatement pStmt = null;

        try
        {
            String sql = "DELETE FROM " + table + " WHERE `clientId` = " + clientId;
            pStmt = con.prepareStatement(sql);
            pStmt.executeUpdate();
        }
        finally
        {
            if (pStmt != null) pStmt.close();
        }        
    }
    
    public List<Tests> GetAutoOrderedTestsByClientId(int clientId) throws SQLException, IllegalArgumentException
    {
        if (clientId <= 0) throw new IllegalArgumentException("AutoOrderTestDAO::GetAutoOrderTestsByClientId: Received invalid clientId: " + clientId);
        if (con.isValid(2) == false) con =  CheckDBConnection.Check(dbs, con);
        String sql = "SELECT * FROM " + table + " WHERE clientId = " + clientId;
        LinkedList<Tests> tests = new LinkedList<>();
        try (PreparedStatement pStmt = con.prepareStatement(sql))
        {
            ResultSet rs = pStmt.executeQuery();
            TestDAO tdao = new TestDAO();

            while (rs.next())
            {
                Integer testNumber = rs.getInt("testNumber");
                if (testNumber == 0) throw new SQLException("AutoOrderTestDAO::GetAutoOrderTestsByClientId:"
                        + " returned test number was [NULL] for clientId " + clientId);

                Tests test = tdao.GetTestByNumber(testNumber);
                if (test == null || test.getIdtests() == null || test.getIdtests().equals(0))
                {
                    throw new SQLException("AutoOrderTestDAO::GetAutoOrderTestsByClientId:"
                            + " retrieved [NULL] or empty test object when loading test number " + testNumber + " for clientId " + clientId);
                }
                tests.add(test);
            }
        }
        return tests;
    }

    public List<Clients> GetClientRelationshipsByTestNumber(int testNumber) throws SQLException, IllegalArgumentException
    {
        if (testNumber <= 0) throw new IllegalArgumentException("AutoOrderTestDAO::GetClientRelationshipsByTestNumber: Received invalid testNumber: " + testNumber);
        if (con.isValid(2) == false) con =  CheckDBConnection.Check(dbs, con);
        
        String sql = "SELECT * FROM " + table + " WHERE testNumber = " + testNumber;
        
        LinkedList<Clients> clients = new LinkedList<>();
        try (PreparedStatement pStmt = con.prepareStatement(sql))
        {
            ResultSet rs = pStmt.executeQuery();
            ClientDAO cdao = new ClientDAO();
            while (rs.next())
            {
                Integer clientId = rs.getInt("clientId");
                if (testNumber == 0) throw new SQLException("AutoOrderTestDAO::GetClientRelationshipsByTestNumber:"
                        + " returned clientId was [NULL] for testNumber " + testNumber);

                Clients client = cdao.GetClientById(clientId);
                if (client == null || client.getIdClients()== null || client.getIdClients().equals(0))
                {
                    throw new SQLException("AutoOrderTestDAO::GetClientRelationshipsByTestNumber:"
                            + " retrieved [NULL] or empty client object when loading testNumber " + testNumber + " for clientId " + clientId);
                }
                clients.add(client);
            }
        }
        return clients;
    }

    public boolean AutoOrderTestExists(int clientId, int testNumber) throws SQLException, IllegalArgumentException
    {
        if (clientId <=0 || testNumber <= 0)
            throw new IllegalArgumentException("AutoOrderTestDAO::AutoOrderTestExists: invalid argument(s). clientId " + clientId + " testNumber " + testNumber);
        
        if (con.isValid(2) == false) con =  CheckDBConnection.Check(dbs, con);
        
        String sql = "SELECT COUNT(*) FROM " + table + " WHERE testNumber = " + testNumber + " AND clientId = " + clientId;
        Integer count = 0;
        try (PreparedStatement pStmt = con.prepareStatement(sql))
        {
            ResultSet rs = pStmt.executeQuery();
            if (rs.next())
            {
                count = rs.getInt(1);
                if (count == null) throw new SQLException("AutoOrderTestDAO::AutoOrderTestExists:"
                        + " count returned NULL for clientId " + clientId + " testNumber " + testNumber);
            }            
        }
        return (count > 0);
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
        String query = "SELECT `AutoOrderTests`.`clientId`,\n"
                + "    `AutoOrderTests`.`testNumber`\n"
                + "FROM `css`.`AutoOrderTests` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, con);
    }
}
