package DAOS;

import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IOrderDiagnosisLookupDAO;
import DAOS.IDAOS.IStructureCheckable;
import DOS.OrderDiagnosisLookup;
import DOS.OrderDiagnosisLookup;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.Convert;
import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @date: Jan 15, 2014
 * @author: Keith Maggio <keith@csslis.com>
 *
 * @project: CSSLibrary
 * @package: DAOS
 * @file name: OrderDiagnosisLookupDAO.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc. All Rights Reserved
 */
public class OrderDiagnosisLookupDAO implements IOrderDiagnosisLookupDAO, DAOInterface, IStructureCheckable {

    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`orderDiagnosisLookup`";

    public boolean InsertOrderDiagnosisLookupDAO(OrderDiagnosisLookup ordDiag) throws SQLException {
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
                    + " `idOrders`,"
                    + " `idDiagnosisCodes`)"
                    + " values (?,?)";

            PreparedStatement pStmt = con.prepareStatement(stmt);

            pStmt.setInt(1, ordDiag.getIdOrders());
            pStmt.setInt(2, ordDiag.getIdDiagnosisCodes());

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

    public boolean UpdateOrderDiagnosisLookupDAO(OrderDiagnosisLookup ordDiag) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }
        try {


            String stmt = "UPDATE " + table + " SET"
                    + " `idOrders` = ?,"
                    + " `idDiagnosisCodes` = ? "
                    + "WHERE `idDiagnosisLookup` = " + ordDiag.getIdDiagnosisLookup();


            PreparedStatement pStmt = con.prepareStatement(stmt);

            pStmt.setInt(1, ordDiag.getIdOrders());
            pStmt.setInt(2, ordDiag.getIdDiagnosisCodes());

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

    public OrderDiagnosisLookup GetOrderDiagnosisLookup(int Id) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        try {
            OrderDiagnosisLookup ordDiag = new OrderDiagnosisLookup();
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + " WHERE `idDiagnosisLookup` = " + Id;

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                ordDiag.setIdDiagnosisLookup(rs.getInt("idDiagnosisLookup"));
                ordDiag.setIdDiagnosisCodes(rs.getInt("idDiagnosisCodes"));
                ordDiag.setIdOrders(rs.getInt("idOrders"));
            }

            rs.close();
            stmt.close();

            return ordDiag;
        } catch (Exception ex) {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }

    public ArrayList<OrderDiagnosisLookup> GetOrderDiagnosisLookupByOrderID(int OrderId) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        try {
            ArrayList<OrderDiagnosisLookup> odlList = new ArrayList<>();

            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + " WHERE `idOrders` = " + OrderId;

            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                OrderDiagnosisLookup ordDiag = new OrderDiagnosisLookup();
                ordDiag.setIdDiagnosisLookup(rs.getInt("idDiagnosisLookup"));
                ordDiag.setIdDiagnosisCodes(rs.getInt("idDiagnosisCodes"));
                ordDiag.setIdOrders(rs.getInt("idOrders"));
                odlList.add(ordDiag);
            }

            rs.close();
            stmt.close();

            return odlList;
        } catch (Exception ex) {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }

    public Boolean Exists(Integer orderID, Integer diagnosisID) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        try {
            Boolean exists = false;
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + " WHERE `idOrders` = " + orderID
                    + " AND `idDiagnosisCodes` = " + diagnosisID;

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                exists = true;
            }

            rs.close();
            stmt.close();

            return exists;
        } catch (Exception ex) {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }

    @Override
    public Boolean Insert(Serializable obj)
    {
        try
        {
            return InsertOrderDiagnosisLookupDAO((OrderDiagnosisLookup)obj);
        }
        catch (SQLException ex)
        {
            Logger.getLogger(Web.DAOS.OrderDiagnosisLookupDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public Boolean Update(Serializable obj)
    {
        try
        {
            return UpdateOrderDiagnosisLookupDAO((OrderDiagnosisLookup)obj);
        }
        catch (SQLException ex)
        {
            Logger.getLogger(Web.DAOS.OrderDiagnosisLookupDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public Boolean Delete(Serializable obj)
    {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        try {
            OrderDiagnosisLookup ordDiag = (OrderDiagnosisLookup)obj;
            Statement stmt = con.createStatement();

            // Delete the Order CPT Diagnosis Code and Diagnosis Lookup Rows
            String query = "DELETE odl, ocd "
             + "FROM " + table + " AS odl "
             + "LEFT JOIN orderedTests AS ot ON odl.idOrders = ot.orderId "
             + "LEFT JOIN orderCptCodes AS occ ON ot.idorderedTests = occ.orderedTestId "
             + "LEFT JOIN orderCptDiagnosisCodes AS ocd ON occ.idorderCptCodes = ocd.orderCptCodeId "
             + "WHERE odl.idDiagnosisLookup = " + ordDiag.getIdDiagnosisLookup() + " AND odl.idDiagnosisCodes = ocd.diagnosisCodeId";
            
            int rowCount1 = stmt.executeUpdate(query);

            // If the above query deletes nothing, use this to make sure Diagnosis Lookup Rows are deleted
            query = "DELETE FROM " + table
                    + " WHERE `idDiagnosisLookup` = " + ordDiag.getIdDiagnosisLookup();
            
            int rowCount2 = stmt.executeUpdate(query);
            stmt.close();

            return rowCount1 > 0 || rowCount2 > 0;
        } catch (Exception ex) {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }

    @Override
    public Serializable getByID(Integer ID)
    {
        try
        {
            return (Serializable) GetOrderDiagnosisLookup(ID);
        }
        catch (SQLException ex)
        {
            Logger.getLogger(Web.DAOS.OrderDiagnosisLookupDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    @Override
    public String structureCheck() {
        String query = "SELECT `orderDiagnosisLookup`.`idDiagnosisLookup`,\n"
                + "    `orderDiagnosisLookup`.`idOrders`,\n"
                + "    `orderDiagnosisLookup`.`idDiagnosisCodes`\n"
                + "FROM `css`.`orderDiagnosisLookup` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, con);
    }
}
