package DAOS.IDAOS;

import DOS.PendingAdvancedOrders;
import java.sql.SQLException;
import java.util.List;

/**
 * @date:   Jan 28, 2014
 * @author: Keith Maggio <keith@csslis.com>
 * 
 * @project:  
 * @package: DAOS.IDAOS
 * @file name: IPendingAdvancedOrderDAO.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc.  All Rights Reserved
 */

public interface IPendingAdvancedOrderDAO 
{
    public boolean InsertPendingAdvancedOrder(PendingAdvancedOrders pending) throws SQLException;
    public boolean DeletePendingAdvancedOrder(PendingAdvancedOrders pending) throws SQLException;
    public boolean Exists(int idPhlebotomy);
    public List<PendingAdvancedOrders> GetPendingAdvancedOrders() throws SQLException;
    public PendingAdvancedOrders GetPendingAdvancedOrder(int idPhlebotomy) throws SQLException;
}
