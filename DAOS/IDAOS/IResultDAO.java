package DAOS.IDAOS;

/**
 * @date:   Mar 12, 2012
 * @author: Keith Maggio
 * 
 * @project: CSSLibrary 
 * @package: DAOS
 * @file name: OrderDAO.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc.  All Rights Reserved
 */

import DOS.Results;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
//import DAOS.RemarkDAO;

public interface IResultDAO 
{
    
    public boolean InsertResult(Results result) throws SQLException;
    
    public Integer InsertResultGetID(Results result) throws SQLException;
    
    public boolean UpdateResult(Results result) throws SQLException;
    
    public Results GetResultByID(int resultID);
    
    public List<Results> GetResultsByOrderID(int orderID);
    
    public List<Results> GetActiveResultsByOrderID(int orderID);
    
    public List<Integer> GetAllTestIdsByOrderId(Integer OrderID);
    
    public Results GetResultByOrderIDTestID(int orderID, int testID);
    
    public List<Results> GetReportedResultByOrderID(int orderID);
    
    public List<Results> GetApprovedResultByOrderID(int orderID);
    
    public int GetResultIdByOrderIdTestId(int OrderID, int TestID);
    
    public boolean ResultExists(int OrderID, int TestID);
    
    public boolean ResultExists(int OrderID, int TestID, String ResultText);
    
    public boolean ResultExists(int OrderID);
    
    public boolean ResultExistsCum(int OrderID);
    
    public boolean PurgeResultByResultID(int RESULTID);
    
    public ArrayList<Results> GetSubtestResults(int idResults);
    
    public int GetIDForDetailImport(int arNo, String acc, int test, int subtest) throws SQLException;
    
    public Boolean IsTestNoCharge(Integer OrderID, Integer TestID);
    
    public Boolean IsTestNoCharge(Integer resultID);
    
    public Boolean UpdateNoChargeFlag(Integer OrderID, Integer TestID, Boolean value) throws SQLException;
}

