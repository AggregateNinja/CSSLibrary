package DAOS.IDAOS;

import DAOS.*;
import DOS.Ordercomment;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @date:   Nov 12, 2013
 * @author: Keith Maggio <keith@csslis.com>
 * 
 * @project:  
 * @package: DAOS
 * @file name: IOrderCommentDAO.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc.  All Rights Reserved
 */

public interface IOrderCommentDAO 
{
    
    public boolean InsertOrderComment(Ordercomment comment) throws SQLException;
    
    public boolean UpdateOrderComment(Ordercomment comment) throws SQLException;
    
    public Ordercomment GetCommentByOrderId(Integer OrderID);
    
    public boolean Exists(Integer OrderID);
}
