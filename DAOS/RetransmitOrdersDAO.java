/*
 * Computer Service & Support, Inc.  All Rights Reserved.
 */

package DAOS;

import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IStructureCheckable;
import DOS.RetransmitOrders;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.SQLUtil;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Robert Hussey <r.hussey@csslis.com>
 * @date Mar 29, 2016
 */
public class RetransmitOrdersDAO implements DAOInterface, IStructureCheckable  {
    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`retransmitOrders`";

    private final List<String> fields = new ArrayList<>();

    public RetransmitOrdersDAO()
    {
        // all fields except id
        fields.add("orderId");
        fields.add("createdBy");
        fields.add("createdDate");
        fields.add("emr");
        fields.add("print");
        fields.add("fax");
        fields.add("transmit");
        fields.add("retransmissionRelease");
        fields.add("retransmissionDate");
        fields.add("retransmitted");
    }
    
    public boolean InsertRetransmitOrders(int orderId, Integer createdBy, Date createdDate, 
            boolean emr, boolean print, boolean fax, boolean transmit, 
            Integer retransmissionRelease, Date retransmissionDate, boolean retransmitted)
    {
        try
        {
            if (con.isClosed())
            {
                con = CheckDBConnection.Check(dbs, con);
            }
        }
        catch (SQLException ex)
        {
            System.out.println("RetransmitOrdersDAO::InsertRetransmitOrder - Exception while checking database connection: " + ex.toString());
            return false;
        }

        String stmt = GenerateInsertStatement(fields);

        try (PreparedStatement pStmt = con.prepareStatement(stmt))
        {
            int i = 0;
            SQLUtil.SafeSetInteger(pStmt, ++i, orderId);
            SQLUtil.SafeSetInteger(pStmt, ++i, createdBy);
            SQLUtil.SafeSetTimeStamp(pStmt, ++i, createdDate);
            SQLUtil.SafeSetBoolean(pStmt, ++i, emr);
            SQLUtil.SafeSetBoolean(pStmt, ++i, print);
            SQLUtil.SafeSetBoolean(pStmt, ++i, fax);
            SQLUtil.SafeSetBoolean(pStmt, ++i, transmit);
            SQLUtil.SafeSetInteger(pStmt, ++i, retransmissionRelease);
            SQLUtil.SafeSetTimeStamp(pStmt, ++i, retransmissionDate);
            SQLUtil.SafeSetBoolean(pStmt, ++i, retransmitted);

            pStmt.executeUpdate();
            pStmt.close();

        }
        catch (SQLException ex)
        {
            System.out.println("RetransmitOrdersDAO::InsertRetransmitOrder - SQLException while inserting: " + ex.toString());
            System.out.println("RetransmitOrdersDAO::InsertRetransmitOrder - SQL statement: " + stmt.toString());
            return false;
        }
        return true;
    }
    
    public boolean UpdateRetransmitOrders(int idRetransmitOrders, int orderId, Integer createdBy, Date createdDate, boolean emr, boolean print, 
            boolean fax, boolean transmit, Integer retransmissionRelease, Date retransmissionDate, boolean retransmitted)
    {
        try
        {
            if (con.isClosed())
            {
                con = CheckDBConnection.Check(dbs, con);
            }
        }
        catch (SQLException ex)
        {
            System.out.println("RetransmitOrdersDAO::UpdateRetransmitOrders - Exception while checking database connection: " + ex.toString());
            return false;
        }

        try
        {
            String stmt = GenerateUpdateStatement(fields)
                    + " WHERE `idRetransmitOrders` = " + idRetransmitOrders;

            PreparedStatement pStmt = con.prepareStatement(stmt);

            int i = 0;
            SQLUtil.SafeSetInteger(pStmt, ++i, orderId);
            SQLUtil.SafeSetInteger(pStmt, ++i, createdBy);
            SQLUtil.SafeSetTimeStamp(pStmt, ++i, createdDate);
            SQLUtil.SafeSetBoolean(pStmt, ++i, emr);
            SQLUtil.SafeSetBoolean(pStmt, ++i, print);
            SQLUtil.SafeSetBoolean(pStmt, ++i, fax);
            SQLUtil.SafeSetBoolean(pStmt, ++i, transmit);
            SQLUtil.SafeSetInteger(pStmt, ++i, retransmissionRelease);
            SQLUtil.SafeSetTimeStamp(pStmt, ++i, retransmissionDate);
            SQLUtil.SafeSetBoolean(pStmt, ++i, retransmitted);

            pStmt.executeUpdate();

            pStmt.close();

            return true;
        }
        catch (SQLException ex)
        {
            System.out.println("RetransmitOrdersDAO::UpdateRetransmitOrders - Exception while updating RetransmitOrders object: " + ex.toString());
            return false;
        }
    }
    
    public boolean DeleteRetransmitOrders(int idRetransmitOrders)
    {
        try
        {
            if (con.isClosed())
            {
                con = CheckDBConnection.Check(dbs, con);
            }
        }
        catch (SQLException ex)
        {
            System.out.println("RetransmitOrdersDAO::DeleteRetransmitOrders - Exception while checking database connection: " + ex.toString());
            return false;
        }

        try
        {
            String stmt = "DELETE FROM " + table + " WHERE `idRetransmitOrders` = " + idRetransmitOrders;
            PreparedStatement pStmt = con.prepareStatement(stmt);

            pStmt.executeUpdate();
            pStmt.close();

            return true;
        }
        catch (SQLException ex)
        {
            System.out.println("RetransmitOrdersDAO::DeleteRetransmitOrders - Exception while deleting RetransmitOrders (id=" + idRetransmitOrders + "): " + ex.toString());
            return false;
        }
    }
    
    public RetransmitOrders GetRetransmitOrdersByID(int idRetransmitOrders)
    {
        try
        {
            if (con.isClosed())
            {
                con = CheckDBConnection.Check(dbs, con);
            }
        }
        catch (SQLException ex)
        {
            System.out.println("RetransmitOrdersDAO::GetRetransmitOrdersByID - Exception while checking database connection: " + ex.toString());
            return null;
        }

        try
        {
            RetransmitOrders ro = null;

            String stmt = "SELECT * FROM " + table
                    + " WHERE `idRetransmitOrders` = " + idRetransmitOrders;
            PreparedStatement pStmt = con.prepareStatement(stmt);

            ResultSet rs = pStmt.executeQuery(stmt);

            if (rs.next())
            {
                ro = new RetransmitOrders();
                ro.setIdRetransmitOrders(rs.getInt("idRetransmitOrders"));
                ro.setRetransmissionRelease(rs.getInt("createdBy"));
                ro.setRetransmissionDate(rs.getTimestamp("createdDate"));
                ro.setOrderId(rs.getInt("orderId"));
                ro.setEmr(rs.getBoolean("emr"));
                ro.setPrint(rs.getBoolean("print"));
                ro.setFax(rs.getBoolean("fax"));
                ro.setTransmit(rs.getBoolean("transmit"));
                ro.setRetransmissionRelease(rs.getInt("retransmissionRelease"));
                ro.setRetransmissionDate(rs.getTimestamp("retransmissionDate"));
                ro.setRetransmitted(rs.getBoolean("retransmitted"));
            }

            rs.close();
            pStmt.close();

            return ro;
        }
        catch (SQLException ex)
        {
            System.out.println("RetransmitOrdersDAO::GetRetransmitOrdersByID - Exception while getting RetransmitOrders (id=" + idRetransmitOrders + "): " + ex.toString());
            return null;
        }
    }
    
    public RetransmitOrders GetPendingRetransmitOrdersByOrderId(int orderId)
    {
        try
        {
            if (con.isClosed())
            {
                con = CheckDBConnection.Check(dbs, con);
            }
        }
        catch (SQLException ex)
        {
            System.out.println("RetransmitOrdersDAO::GetRetransmitOrdersByID - Exception while checking database connection: " + ex.toString());
            return null;
        }

        try
        {
            RetransmitOrders ro = null;

            String stmt = "SELECT * FROM " + table
                    + " WHERE `orderId` = " + orderId + " AND `retransmitted` = 0";
            PreparedStatement pStmt = con.prepareStatement(stmt);

            ResultSet rs = pStmt.executeQuery(stmt);

            if (rs.next())
            {
                ro = new RetransmitOrders();
                ro.setIdRetransmitOrders(rs.getInt("idRetransmitOrders"));
                ro.setCreatedBy(rs.getInt("createdBy"));
                ro.setCreatedDate(rs.getTimestamp("createdDate"));
                ro.setOrderId(rs.getInt("orderId"));
                ro.setEmr(rs.getBoolean("emr"));
                ro.setPrint(rs.getBoolean("print"));
                ro.setFax(rs.getBoolean("fax"));
                ro.setTransmit(rs.getBoolean("transmit"));
                ro.setRetransmissionRelease(rs.getInt("retransmissionRelease"));
                ro.setRetransmissionDate(rs.getTimestamp("retransmissionDate"));
                ro.setRetransmitted(rs.getBoolean("retransmitted"));
            }

            rs.close();
            pStmt.close();

            return ro;
        }
        catch (SQLException ex)
        {
            System.out.println("RetransmitOrdersDAO::GetRetransmitOrdersByID - Exception while getting RetransmitOrders (orderId =" + orderId + "): " + ex.toString());
            return null;
        }
    }
    
    private String GenerateInsertStatement(List<String> fields)
    {

        String stmt = "INSERT INTO " + table + "(";
        String values = ") values (";
        for (int i = 0; i < fields.size(); ++i)
        {
            stmt += " `" + fields.get(i) + "`";
            values += "?";
            if (i != fields.size() - 1)
            {
                stmt += ", ";
                values += ",";
            }
        }
        values += ")";
        return stmt + values;
    }

    private String GenerateUpdateStatement(List<String> fields)
    {
        String stmt = "UPDATE " + table + " SET";
        for (int i = 0; i < fields.size(); ++i)
        {
            stmt += " `" + fields.get(i) + "` = ?";
            if (i != fields.size() - 1)
            {
                stmt += ",";
            }
        }
        return stmt;
    }
    
    @Override
    public Boolean Insert(Serializable obj)
    {
        if (obj instanceof RetransmitOrders)
        {
            try
            {
                RetransmitOrders ro = (RetransmitOrders) obj;
                return InsertRetransmitOrders(ro.getOrderId(), ro.getCreatedBy(), ro.getCreatedDate(), ro.isEmr(), ro.isPrint(),
                    ro.isFax(), ro.isTransmit(), ro.getRetransmissionRelease(), ro.getRetransmissionDate(), false);
            }
            catch (Exception ex)
            {
                System.out.println("RetransmitOrdersDAO::Insert - Exception while inserting RetransmitOrders object: " + ex.toString());
                return false;
            }
        }
        System.out.println("RetransmitOrdersDAO::Insert - Cannot insert non-RetransmitOrders object using RetransmitOrdersDAO.");
        return false;
    }

    @Override
    public Boolean Update(Serializable obj)
    {
        if (obj instanceof RetransmitOrders)
        {
            try
            {
                RetransmitOrders ro = (RetransmitOrders) obj;
                return UpdateRetransmitOrders(ro.getIdRetransmitOrders(), ro.getOrderId(), ro.getCreatedBy(), ro.getCreatedDate(), ro.isEmr(), ro.isPrint(), 
                        ro.isFax(), ro.isTransmit(), ro.getRetransmissionRelease(), ro.getRetransmissionDate(), ro.isRetransmitted());
            }
            catch (Exception ex)
            {
                System.out.println("RetransmitOrdersDAO::Update - Exception while updating RetransmitOrders object: " + ex.toString());
                return false;
            }
        }
        System.out.println("RetransmitOrdersDAO::Update - Cannot update non-RetransmitOrders object using RetransmitOrdersDAO.");
        return false;
    }

    @Override
    public Boolean Delete(Serializable obj)
    {
        if (obj instanceof RetransmitOrders)
        {
            try
            {
                RetransmitOrders ro = (RetransmitOrders) obj;
                return DeleteRetransmitOrders(ro.getIdRetransmitOrders());
            }
            catch (Exception ex)
            {
                System.out.println("RetransmitOrdersDAO::Delete - Exception while deleting RetransmitOrders object: " + ex.toString());
                return false;
            }
        }
        System.out.println("RetransmitOrdersDAO::Delete - Cannot delete non-RetransmitOrders object using RetransmitOrdersDAO.");
        return false;
    }

    @Override
    public Serializable getByID(Integer ID)
    {
        try
        {
            return GetRetransmitOrdersByID(ID);
        }
        catch (Exception ex)
        {
            System.out.println("RetransmitOrdersDAO::getByID - Exception while getting RetransmitOrders (id=" + ID + "): " + ex.toString());
            return false;
        }
    }
    
    @Override
    public String structureCheck() {
        return DatabaseStructureCheck.structureCheck(fields, table, con);
    }
}
