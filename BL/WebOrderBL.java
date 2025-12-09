package BL;

import DAOS.LogDAO;
import DOS.OrderEntryLog;
import Database.CheckDBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

public class WebOrderBL
{
    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    
    public boolean InvalidateWebOrder(Integer webOrderId, int avalonUserId) throws SQLException, Exception
    {
        PreparedStatement pStmt = null;
        boolean hadError = false;
        try
        {
            if (con.isClosed()) con = CheckDBConnection.Check(dbs, con);
            
            con.setAutoCommit(false);
            
            String sql = "UPDATE cssweb.orders SET active = 0 WHERE idOrders = ?";
            pStmt = con.prepareStatement(sql);
            pStmt.setInt(1, webOrderId);
            pStmt.execute();
            int updated = pStmt.getUpdateCount();
            
            // If we weren't able to set active to false, rollback
            if (updated == 0) throw new Exception();
            
            sql = "UPDATE cssweb.results SET isInvalidated = 1, "
                    + "invalidatedBy = 1, invalidatedDate = NOW() WHERE orderId = ?";
            pStmt = con.prepareStatement(sql);
            pStmt.setInt(1, webOrderId);
            pStmt.execute();
            updated = pStmt.getUpdateCount();
            
            // If we weren't able to deactive the results, rollback
            if (updated == 0) throw new Exception();

            // Log event
            LogDAO logDAO = new LogDAO();
            OrderEntryLog log = new OrderEntryLog();
            log.setAction("Web Order Deactivated");
            log.setDescription("Web Order Id: " + webOrderId);
            log.setIdUser(avalonUserId);
            log.setCreatedDate(new Date());
            boolean success = logDAO.InsertLog(LogDAO.LogTable.OrderEntry, log);
            
            // If we couldn't log the changes, rollback
            if (!success) throw new Exception();
        }
        catch (Exception ex)
        {
            hadError = true;
            if (!con.isClosed()) con.rollback();
        }
        finally
        {
            if (pStmt != null) pStmt.close();
            if (!con.isClosed())
            {
                if (!hadError) con.commit();
                con.setAutoCommit(true);
            }
        }

        return hadError;
    }
    
}
