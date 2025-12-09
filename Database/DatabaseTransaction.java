/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Database;

import DAOS.PreferencesDAO;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author TomR
 */
public class DatabaseTransaction
{
    
    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private Integer _sessionTimeout;
    private Integer _previousSessionTimeout;
    private TransactionStatus _status;
    private boolean _disableTransactions;
    
    public enum TransactionStatus
    {
        OPEN,
        CLOSED;
    }
    
    /**
     * 
     * @param sessionTransactionTimeout Seconds to wait before timing out the connection
     */
    public DatabaseTransaction(int sessionTransactionTimeout)
    {
        _disableTransactions = false;
        
        // Prevent any of the logic in the transactions class unless the
        // user has a preference "UseDatabaseTransactions" set to true.
        
        PreferencesDAO prefDAO = new PreferencesDAO();

        Boolean useTransactions = prefDAO.getBoolean("UseDatabaseTransactions");
        if (useTransactions == null || useTransactions == false)
        {
            _disableTransactions = true;
        }

        _status = TransactionStatus.CLOSED;
        _previousSessionTimeout = GetSessionTimeout();
        _sessionTimeout = sessionTransactionTimeout;
    }
    
    public DatabaseTransaction()
    {
        _disableTransactions = false;
        
        // Prevent any of the logic in the transactions class unless the
        // user has a preference "UseDatabaseTransactions" set to true.
        
        PreferencesDAO prefDAO = new PreferencesDAO();

        Boolean useTransactions = prefDAO.getBoolean("UseDatabaseTransactions");
        if (useTransactions == null || useTransactions == false)
        {
            _disableTransactions = true;
        }

        _status = TransactionStatus.CLOSED;
        _previousSessionTimeout = GetSessionTimeout();
        _sessionTimeout = _previousSessionTimeout;
    }
    
    public TransactionStatus GetStatus()
    {
        return _status;
    }
    
    public boolean OpenTransaction()
    {
        if (_disableTransactions) return false;
        try
        {
            if (con.isClosed()) con = CheckDBConnection.Check(dbs, con);
        }
        catch (SQLException ex)
        {
            System.out.println(ex.toString());
            return false;
        }
        
        String sql = "";
        Statement stmt;
        try
        {  
            sql = "SET @@local.wait_timeout=" + _sessionTimeout + ";";
            stmt = con.createStatement();
            stmt.executeQuery(sql);

            sql = "SET AUTOCOMMIT=0;";
            stmt = con.createStatement();
            stmt.executeQuery(sql);
            
            sql = "START TRANSACTION; ";
            stmt = con.createStatement();
            stmt.executeQuery(sql);
            
            _status = TransactionStatus.OPEN;
        }
        catch (SQLException ex) {return false;}

        // Make your SQL calls here, but make sure to either commit or
        // rollback the connection depending on the status of the calls.        
        return true;

    }

    public boolean CommitTransaction()
    {
        if (_disableTransactions) return false;
        if (_status != TransactionStatus.OPEN) return false;
        
        try {
        if (con.isClosed()) {
            con = CheckDBConnection.Check(dbs, con);
        }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
        }
        
        try
        {
            String sql = "COMMIT;";
            Statement stmt = con.createStatement();
            stmt.executeQuery(sql);
            _status = TransactionStatus.CLOSED;
            return true;
        } catch (SQLException ex)
        {
            System.out.println("Unable to commit transaction!");
            return false;
        }
        finally
        {
            // Put us back in auto-commit mode.
            ResetConnection();
        }        
    }
    
    public boolean RollbackTransaction()
    {
        if (_disableTransactions) return false;
        if (_status != TransactionStatus.OPEN) return false;
        
        try {
        if (con.isClosed()) {
            con = CheckDBConnection.Check(dbs, con);
        }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
        
        try {
            String sql = "ROLLBACK;";
            Statement stmt = con.createStatement();
            stmt.executeQuery(sql);
            _status = TransactionStatus.CLOSED;
            return true;
        } catch (SQLException ex)
        {
            System.out.println("Unable to rollback transaction!");
            return false;
        }
        finally
        {
            // Take us out of auto-commit mode
            ResetConnection();
        }        
    }
    
    private void SetSessionTimeout(int seconds) throws SQLException
    {
        if (_disableTransactions) return;
        try {
        if (con.isClosed()) {
            con = CheckDBConnection.Check(dbs, con);
        }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
        
        String sql = "SET @@local.wait_timeout=" + String.valueOf(seconds) + ";";
        Statement stmt = con.createStatement();
        stmt.executeQuery(sql);            

    }
    
    public Integer GetSessionTimeout()
    {
        if (_disableTransactions) return null;
        try {
        if (con.isClosed()) {
            con = CheckDBConnection.Check(dbs, con);
        }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
        }
        
        // SQL Server's default local wait timeout is 8 hours.
        // If a user beings a transaction and gets disconnected, that
        // transaction may hold a lock on that connection. By setting
        // a low session timeout for the duration of the transaction,
        // we can reduce the chances of any connection locks.
        
        Integer result = 1337;
        
        try {
            String sql = "SELECT @@local.wait_timeout;";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            
            if (rs.next()) {
                result = rs.getInt(1);
            }
        } catch (SQLException ex)
        {
            System.out.println("Unable to retrieve session timeout for transaction.");
        }
        return result;
    }
    
    /**
     * Returns the connection settings to the default
     *  (autocommit = 1, session timeout)
     */
    public void ResetConnection()
    {
        if (_disableTransactions) return;
        try
        {
            // Switch back to the server default session timeout
            SetSessionTimeout(_previousSessionTimeout);
            
            // Return state to autocommit.
            String sql = "SET AUTOCOMMIT=1;";
            Statement stmt = con.createStatement();
            stmt.executeQuery(sql);            
        } catch (SQLException ex)
        {
            System.out.println("Unable to re-enable autocommit for this connection.");
        }
    }
}
