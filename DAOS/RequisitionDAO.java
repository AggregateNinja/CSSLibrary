package DAOS;

import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IStructureCheckable;
import DOS.Requisitions;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.Convert;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @date: Nov 4, 2013
 * @author: Keith Maggio <keith@csslis.com>
 *
 * @project:
 * @package: DAOS
 * @file name: RequisitionDAO.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc. All Rights Reserved
 */
public class RequisitionDAO implements DAOInterface, IStructureCheckable {

    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`requisitions`";
    private static boolean useReportName;
    
    public RequisitionDAO(){ 
        useReportName = new PreferencesDAO().getBoolean("UseReqNames", false);
    }
    public boolean InsertRequisition(Requisitions requisition) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }

        try {
            String stmt = "INSERT INTO " + table + "(" +
                    "`idOrders`, " +
                    "`created`, " +
                    "`idUser`, " +
                    "`fileType`, " +
                    (useReportName ? "`reportName`, " : "") +
                    "`data`)"
                    + (useReportName ? " values (?,?,?,?,?,?)" : " values (?,?,?,?,?)");

            PreparedStatement pStmt = con.prepareStatement(stmt);

            java.sql.Date created;
            if (requisition.getCreated() == null) {
                created = new java.sql.Date(new java.util.Date().getTime());
            } else {
                created = Convert.ToSQLDate(requisition.getCreated());
            }

            int i = 1;
            pStmt.setInt(i++, requisition.getIdOrders());
            pStmt.setDate(i++, created);
            pStmt.setInt(i++, requisition.getIdUser());
            pStmt.setString(i++, requisition.getFileType());
            if(useReportName) {
                pStmt.setString(i++, requisition.getReportName());
            }
            pStmt.setBytes(i++, requisition.getData());

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

    public boolean UpdateRequisition(Requisitions requisition) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }

        try {


            String stmt = "UPDATE " + table + " SET " + 
                    "`created` = ?, " +
                    "`idUser` = ?, " +
                    "`fileType` = ?, " +
                    (useReportName ? "`reportName` = ?, " : "") +
                    "`data` = ? "
                    + "WHERE `idOrders` = " + requisition.getIdOrders();


            PreparedStatement pStmt = con.prepareStatement(stmt);

            java.sql.Date created;
            if (requisition.getCreated() == null) {
                created = new java.sql.Date(new java.util.Date().getTime());
            } else {
                created = Convert.ToSQLDate(requisition.getCreated());
            }
            int i = 1;
            pStmt.setDate(i++, created);
            pStmt.setInt(i++, requisition.getIdUser());
            pStmt.setString(i++, requisition.getFileType());
            if(useReportName) {
                pStmt.setString(i++, requisition.getReportName());
            }
            pStmt.setBytes(i++, requisition.getData());

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
    
    public static Requisitions getByResultSet(ResultSet rs)
            throws IllegalArgumentException, SQLException
    {
        Requisitions req = new Requisitions();
        req.setIdOrders(rs.getInt("idOrders"));
        req.setCreated(rs.getDate("created"));
        req.setIdUser(rs.getInt("idUser"));
        req.setFileType(rs.getString("fileType"));
        if (useReportName) {
            req.setReportName(rs.getString("reportName"));            
        } else {
            req.setReportName("Requisition");
        }
        
        req.setData(rs.getBytes("data"));
        
        return req;
    }

    public Requisitions GetWebRequisition(int Id) throws SQLException
    {
        try
        {
            if (con.isClosed())
            {
                con = CheckDBConnection.Check(dbs, con);
            }
        }
        catch (SQLException sex)
        {
            System.out.println(sex.toString());
            return null;
        }

        try
        {
            String schema =  dbs.getProperties().getWebSchema();
            
            Requisitions req = null;
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + schema + "." + table
                    + "WHERE `idOrders` = " + Id;

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next())
            {
                req = getByResultSet(rs);
                //req.setIdOrders(rs.getInt("idOrders"));
                //req.setCreated(rs.getDate("created"));
                //req.setIdUser(rs.getInt("idUser"));
                //req.setFileType(rs.getString("fileType"));
                //req.setData(rs.getBytes("data"));
            }

            rs.close();
            stmt.close();
            
            return req;
        }
        catch (Exception ex)
        {
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }
    
    public Requisitions GetRequisition(int Id) throws SQLException
    {
        try
        {
            if (con.isClosed())
            {
                con = CheckDBConnection.Check(dbs, con);
            }
        }
        catch (SQLException sex)
        {
            System.out.println(sex.toString());
            return null;
        }

        try
        {
            Requisitions req = null;
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + "WHERE `idOrders` = " + Id;

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next())
            {
                req = getByResultSet(rs);
                //req.setIdOrders(rs.getInt("idOrders"));
                //req.setCreated(rs.getDate("created"));
                //req.setIdUser(rs.getInt("idUser"));
                //req.setFileType(rs.getString("fileType"));
                //req.setData(rs.getBytes("data"));
            }

            rs.close();
            stmt.close();

            return req;
        }
        catch (Exception ex)
        {
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
            return InsertRequisition((Requisitions)obj);
        }
        catch (SQLException ex)
        {
            Logger.getLogger(RequisitionDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public Boolean Update(Serializable obj)
    {
        try
        {
            return UpdateRequisition((Requisitions)obj);
        }
        catch (SQLException ex)
        {
            Logger.getLogger(RequisitionDAO.class.getName()).log(Level.SEVERE, null, ex);
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
        try
        {
            return GetRequisition(ID);
        }
        catch (SQLException ex)
        {
            Logger.getLogger(RequisitionDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    @Override
    public String structureCheck() {
        String query = "SELECT `requisitions`.`idOrders`,\n"
                + "    `requisitions`.`created`,\n"
                + "    `requisitions`.`idUser`,\n"
                + "    `requisitions`.`fileType`,\n"
                + (useReportName ? "    `requisitions`.`reportName`,\n" : "")
                + "    `requisitions`.`data`\n"
                + "FROM `css`.`requisitions` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, con);
    }
}
