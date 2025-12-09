package DAOS.IDAOS;

import DOS.Phlebotomy;
import java.sql.SQLException;

/**
 * @date:   Jan 28, 2014
 * @author: Keith Maggio <keith@csslis.com>
 * 
 * @project:  
 * @package: DAOS.IDAOS
 * @file name: IPhlebotomyDAO.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc.  All Rights Reserved
 */

public interface IPhlebotomyDAO 
{
    public boolean InsertPhlebotomy(Phlebotomy phlebotomy) throws SQLException;
    public boolean UpdatePhlebotomy(Phlebotomy phlebotomy) throws SQLException;
    public Phlebotomy GetPhlebotomy(int IdPhlebotomy) throws SQLException;
}
