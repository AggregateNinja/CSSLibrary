package DAOS.IDAOS;

import DOS.AdvancedOrders;
import java.sql.SQLException;

/**
 * @date:   Jan 28, 2014
 * @author: Keith Maggio <keith@csslis.com>
 * 
 * @project:  
 * @package: DAOS.IDAOS
 * @file name: IAdvancedOrderDAO.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc.  All Rights Reserved
 */

public interface IAdvancedOrderDAO 
{
    public boolean InsertAdvancedOrder(AdvancedOrders advOrder) throws SQLException;
    public boolean UpdateAdvancedOrder(AdvancedOrders advOrder) throws SQLException;
    public AdvancedOrders GetAdvancedOrder(int IdAdvancedOrder) throws SQLException;
}
