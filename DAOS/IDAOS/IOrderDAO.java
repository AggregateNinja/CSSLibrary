package DAOS.IDAOS;

/**
 * @date:   Mar 12, 2012
 * @author: Keith Maggio
 * 
 * @project: CSSLibrary 
 * @package: DAOS
 * @file name: IOrderDAO.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc.  All Rights Reserved
 */

import DOS.Orders;
import java.sql.*;
import java.util.List;

public interface IOrderDAO 
{
    public boolean InsertOrder(Orders order) throws SQLException;
    
    public boolean UpdateOrder(Orders order) throws SQLException;
    
    public Orders GetOrder(String AccessionNumber) throws SQLException;
    
    public Orders GetOrder(String AccessionNumber, String ARNumber) throws SQLException;
    
    public Orders GetOrder(String AccessionNumber, int... locationIds) throws SQLException;
    
    public Orders GetOrder(String AccessionNumber, java.util.Date oDate) throws SQLException;
    
    public List<Orders> GetAllOrdersWithAccession(String AccessionNumber) throws SQLException;
    
    public Orders GetOrderById(int Id) throws SQLException;
    
    public int GetOrderIdByOrderDate(String accession, java.util.Date orderDate);
    
    public int GetOrderIdForPurge(String accession, java.util.Date dos1, java.util.Date dos2);
    
    public boolean OrderExists(String acc);
    
    public boolean OrderExists(String acc, int patId);
    
    public boolean OrderExists(String acc, java.util.Date orderedDate);
    
    public int AccessionCount(String acc);
    
    public List<Orders> GetOrdersByPatient(int patientID, java.sql.Timestamp specDate);
    
    public List<Orders> SearchOrdersByPatientName(String FirstNameFragment, String LastNameFragment);
    
    public List<Orders> SearchOrdersByPatientNameEMROrder(String FirstNameFragment, String LastNameFragment, String emrOrderId);
    
    public boolean PurgeOrder(int OrderID);
    
    /**
     * @deprecated Database updated to use VARCHAR accessions.
     */
    @Deprecated
    public int GetMaxAccession();
    
    public ResultSet GetResultSetByQuery(String Select, String Where);
}
