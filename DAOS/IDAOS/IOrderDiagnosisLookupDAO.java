package DAOS.IDAOS;

import DAOS.*;
import DOS.OrderDiagnosisLookup;
import DOS.OrderDiagnosisLookup;
import Utility.Convert;
import java.sql.*;
import java.util.ArrayList;

/**
 * @date:   Jan 15, 2014
 * @author: Keith Maggio <keith@csslis.com>
 * 
 * @project: CSSLibrary 
 * @package: DAOS
 * @file name: IOrderDiagnosisLookupDAO.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc.  All Rights Reserved
 */


public interface IOrderDiagnosisLookupDAO 
{
    public boolean InsertOrderDiagnosisLookupDAO(OrderDiagnosisLookup ordDiag) throws SQLException;
    
    public boolean UpdateOrderDiagnosisLookupDAO(OrderDiagnosisLookup ordDiag) throws SQLException;
    
    public OrderDiagnosisLookup GetOrderDiagnosisLookup(int Id) throws SQLException;
    
    public ArrayList<OrderDiagnosisLookup> GetOrderDiagnosisLookupByOrderID(int OrderId) throws SQLException;
    
    public Boolean Exists(Integer orderID, Integer diagnosisID);
}
