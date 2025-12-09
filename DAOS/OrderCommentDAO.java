package DAOS;

import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IOrderCommentDAO;
import DAOS.IDAOS.IStructureCheckable;
import DOS.Ordercomment;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @date: Nov 12, 2013
 * @author: Keith Maggio <keith@csslis.com>
 *
 * @project:
 * @package: DAOS
 * @file name: OrderCommentDAO.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc. All Rights Reserved
 */
public class OrderCommentDAO implements DAOInterface, IOrderCommentDAO, IStructureCheckable {

    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`orderComment`";

    public boolean InsertOrderComment(Ordercomment comment) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }

        try {
            String stmt = "INSERT INTO " + table + "("
                    + "`orderId`, "
                    + "`comment`, "
                    + "`advancedOrder`"
                    + ") "
                    + "VALUES "
                    + "( ?, ?, ?);";
            PreparedStatement pStmt = con.prepareStatement(stmt);

            pStmt.setInt(1, comment.getOrderId());
            pStmt.setString(2, comment.getComment());
            pStmt.setBoolean(3, comment.IsAdvancedOrder());

            pStmt.executeUpdate();

            pStmt.close();

            return true;
        } catch (Exception ex) {
            String message = ex.toString();
            //TODO:  Add logging for failures
            System.out.println(message);
            return false;
        }
    }

    public boolean UpdateOrderComment(Ordercomment comment) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }

        try {
            String stmt = "UPDATE " + table
                    + "SET "
                    + "`comment` = ? "
                    + "WHERE `idorderComment` = " + comment.getIdorderComment();
            PreparedStatement pStmt = con.prepareStatement(stmt);

            pStmt.setString(1, comment.getComment());

            pStmt.executeUpdate();

            pStmt.close();

            return true;

        } catch (Exception ex) {
            String message = ex.toString();
            //TODO:  Add logging for failures
            System.out.println(message);
            return false;
        }
    }

    public Ordercomment GetCommentByOrderId(Integer OrderID, Boolean isAdvancedOrder) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        Ordercomment com = new Ordercomment();
        try {
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + " WHERE `orderId` = " + OrderID
                    + " AND `advancedOrder` = " + isAdvancedOrder.toString();

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                com.setIdorderComment(rs.getInt("idorderComment"));
                com.setOrderId(rs.getInt("orderId"));
                com.setComment(rs.getString("comment"));
                com.setAdvancedOrder(rs.getBoolean("advancedOrder"));
            }
            rs.close();
            stmt.close();

            return com;

        } catch (Exception ex2) {
            System.out.println(ex2.toString());
            return null;
        }
    }

    public boolean Exists(Integer OrderID, Boolean isAdvancedOrder) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }

        int count = 0;
        try {
            Statement stmt = con.createStatement();

            String query = "SELECT COUNT(*) AS 'count' FROM " + table
                    + " WHERE `orderId` = " + OrderID
                    + " AND `advancedOrder` = " + isAdvancedOrder.toString();

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                count = rs.getInt("count");
            }
            rs.close();
            stmt.close();

            return count > 0;

        } catch (Exception ex2) {
            System.out.println(ex2.toString());
            return false;
        }
    }

    @Override
    public Boolean Insert(Serializable obj)
    {
        try
        {
            return InsertOrderComment((Ordercomment)obj);
        }
        catch (SQLException ex)
        {
            Logger.getLogger(OrderCommentDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public Boolean Update(Serializable obj)
    {
        try
        {
            return UpdateOrderComment((Ordercomment)obj);
        }
        catch (SQLException ex)
        {
            Logger.getLogger(OrderCommentDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public Boolean Delete(Serializable obj)
    {
        return false;
    }

    @Override
    public Serializable getByID(Integer ID)
    {
        return GetCommentByOrderId(ID);
    }

    @Override
    public Ordercomment GetCommentByOrderId(Integer OrderID)
    {
        return GetCommentByOrderId(OrderID, Boolean.FALSE);
    }

    @Override
    public boolean Exists(Integer OrderID)
    {
        return Exists(OrderID, Boolean.FALSE);
    }
    
    @Override
    public String structureCheck() {
        String query = "SELECT `orderComment`.`idorderComment`,\n"
                + "    `orderComment`.`orderId`,\n"
                + "    `orderComment`.`advancedOrder`,\n"
                + "    `orderComment`.`comment`\n"
                + "FROM `css`.`orderComment` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, con);
    }
}
