package DAOS;

import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IPendingAdvancedOrderDAO;
import DAOS.IDAOS.IStructureCheckable;
import DOS.PendingAdvancedOrders;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * @date: Jan 28, 2014
 * @author: Keith Maggio <keith@csslis.com>
 *
 * @project:
 * @package: DAOS
 * @file name: PendingAdvancedOrderDAO.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc. All Rights Reserved
 */
public class PendingAdvancedOrderDAO implements IPendingAdvancedOrderDAO, DAOInterface, IStructureCheckable {

    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`pendingAdvancedOrders`";
    private final ArrayList<String> fields = new ArrayList<String>();

    public PendingAdvancedOrderDAO() {
        fields.add("idPhlebotomy");
    }

    @Override
    public boolean InsertPendingAdvancedOrder(PendingAdvancedOrders pending) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }

        try {
            String stmt = GenerateInsertStatement(fields);
            PreparedStatement pStmt = con.prepareStatement(stmt);


            SetStatementFromPendingAdvancedOrder(pending, pStmt);

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

    @Override
    public boolean DeletePendingAdvancedOrder(PendingAdvancedOrders pending) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }

        try {


            String stmt = GenerateUpdateStatement(fields)
                    + " WHERE `idPhlebotomy` = " + pending.getIdPhlebotomy();

            PreparedStatement pStmt = con.prepareStatement(stmt);

            SetStatementFromPendingAdvancedOrder(pending, pStmt);

            pStmt.executeUpdate();

            pStmt.close();

            return true;
        } catch (Exception ex) {
            String message = ex.toString();
            //TODO:  Add exception handleing
            System.out.println(message);
            return false;
        }
    }

    @Override
    public boolean Exists(int idPhlebotomy) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }

        try {
            int x = 0;
            Statement stmt = con.createStatement();

            String query = "SELECT COUNT(*) AS 'count' FROM " + table
                    + "WHERE `idPhlebotomy` = " + idPhlebotomy;

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                x = rs.getInt("count");
            }

            rs.close();
            stmt.close();

            return (x > 0 ? true : false);
        } catch (Exception ex) {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return false;
        }
    }

    @Override
    public List<PendingAdvancedOrders> GetPendingAdvancedOrders() throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        try {
            ArrayList<PendingAdvancedOrders> plist = new ArrayList<>();
            PendingAdvancedOrders pending;
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table;

            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                pending = new PendingAdvancedOrders();
                SetPendingAdvancedOrderFromResultSet(pending, rs);
                plist.add(pending);
            }

            rs.close();
            stmt.close();

            return plist;
        } catch (Exception ex) {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }

    @Override
    public PendingAdvancedOrders GetPendingAdvancedOrder(int idPhlebotomy) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        try {
            PendingAdvancedOrders pao = new PendingAdvancedOrders();
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + "WHERE `idPhlebotomy` = " + idPhlebotomy;

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                SetPendingAdvancedOrderFromResultSet(pao, rs);
            }

            rs.close();
            stmt.close();

            return pao;
        } catch (Exception ex) {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }

    private PendingAdvancedOrders SetPendingAdvancedOrderFromResultSet(PendingAdvancedOrders obj, ResultSet rs) throws SQLException {
        obj.setIdPhlebotomy(rs.getInt("idPhlebotomy"));

        return obj;
    }

    private PreparedStatement SetStatementFromPendingAdvancedOrder(PendingAdvancedOrders obj, PreparedStatement pStmt) throws SQLException {
        pStmt.setInt(1, obj.getIdPhlebotomy());
        return pStmt;
    }

    private String GenerateInsertStatement(ArrayList<String> fields) {

        String stmt = "INSERT INTO " + table + "(";
        String values = ") values (";
        for (int i = 0; i < fields.size(); ++i) {
            stmt += " `" + fields.get(i) + "`";
            values += "?";
            if (i != fields.size() - 1) {
                stmt += ", ";
                values += ",";
            }
        }
        values += ")";
        return stmt + values;
    }

    private String GenerateUpdateStatement(ArrayList<String> fields) {

        String stmt = "UPDATE " + table + " SET";
        for (int i = 0; i < fields.size(); ++i) {
            stmt += " `" + fields.get(i) + "` = ?";
            if (i != fields.size() - 1) {
                stmt += ",";
            }
        }
        return stmt;
    }

    @Override
    public Boolean Insert(Serializable obj)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Boolean Update(Serializable obj)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Boolean Delete(Serializable obj)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Serializable getByID(Integer ID)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public String structureCheck() {
        return DatabaseStructureCheck.structureCheck(fields, table, con);
    }
}
