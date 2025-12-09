package DAOS.IDAOS;

import java.io.Serializable;

/**
 * @date:   Mar 3, 2014
 * @author: Keith Maggio <keith@csslis.com>
 * 
 * @project:  
 * @package: DOS.IDOS
 * @file name: DAOInterface.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc.  All Rights Reserved
 */

public interface DAOInterface 
{
    public Boolean Insert(Serializable obj);
    public Boolean Update(Serializable obj);
    public Boolean Delete(Serializable obj);
    public Serializable getByID(Integer ID);
}
