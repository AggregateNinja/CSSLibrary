package DAOS.IDAOS;

import DOS.AdvancedResults;
import java.sql.SQLException;
import java.util.List;

/**
 * @date:   Jan 28, 2014
 * @author: Keith Maggio <keith@csslis.com>
 * 
 * @project:  
 * @package: DAOS.IDAOS
 * @file name: IAdvancedResultsDAO.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc.  All Rights Reserved
 */

public interface IAdvancedResultDAO 
{
    public boolean InsertAdvancedResults(AdvancedResults advResult) throws SQLException;
    public boolean UpdateAdvancedResults(AdvancedResults advResult) throws SQLException;
    public AdvancedResults GetAdvancedResults(int IdAdvancedResult) throws SQLException;
    public List<AdvancedResults> GetAdvancedResultsFromAdvOrderId(int idAdvancedOrder) throws SQLException;
}
