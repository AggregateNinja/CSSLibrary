/*
 * Computer Service & Support, Inc.  All Rights Reserved.
 */
package DAOS;

import DAOS.IDAOS.IStructureCheckable;
import DOS.DetailInsuranceItems;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.SQLUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author Rob
 */
public class DetailInsuranceItemsDAO implements IStructureCheckable {

    public static final String table = Database.DatabaseSingleton.getDatabaseSingleton().getProperties().getBillingSchema() + ".`detailInsuranceItems`";

    //<editor-fold defaultstate="collapsed" desc="insert (Connection,DetailInsuranceItems)">
    public static DetailInsuranceItems insert(Connection con, DetailInsuranceItems obj) throws SQLException, IllegalArgumentException, NullPointerException {
        if (obj == null) {
            throw new IllegalArgumentException("DetailInsuranceItemsDAO::Insert: Received a NULL DetailInsuranceItems object");
        }

        if (con == null || false == con.isValid(2)) {
            throw new IllegalArgumentException("DetailInsuranceItemsDAO:Insert: Received a [NULL] or invalid Connection object");
        }

        String sql = "INSERT INTO " + table
                + "("
                + "  detailInsuranceId,"
                + "  detailCptCodeId,"
                + "  submissionStatusId,"
                + "  timesBilled"
                + ")"
                + "VALUES (?,?,?,?)";

        try (PreparedStatement pStmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            int i = 0;
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getDetailInsuranceId());
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getDetailCptCodeId());
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getSubmissionStatusId());
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getTimesBilled());

            Integer newId = null;
            sql = pStmt.toString();
            pStmt.executeUpdate();
            ResultSet rs = pStmt.getGeneratedKeys();
            if (rs.next()) {
                newId = rs.getInt(1);
            }
            if (newId == null || newId <= 0) {
                throw new NullPointerException("DetailInsuranceItemsDAO::Insert: Cannot retrieve generated identifier for inserted row.");
            }
            obj.setIdDetailInsuranceItems(newId);
        } catch (Exception ex) {
            System.out.println(ex.getMessage() + " " + sql);
            throw ex;
        }

        return obj;
    }
	//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="insert (DetailInsuranceItems)">
    public static DetailInsuranceItems insert(DetailInsuranceItems obj) throws SQLException, IllegalArgumentException, NullPointerException {
        if (obj == null) {
            throw new IllegalArgumentException("DetailInsuranceItemsDAO::Insert: Received a NULL DetailInsuranceItems object");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false) {
            con = CheckDBConnection.Check(dbs, con);
        }

        return insert(con, obj);
    }
	//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="update (Connection, DetailInsuranceItems)">
    public static void update(Connection con, DetailInsuranceItems obj) throws SQLException, IllegalArgumentException, NullPointerException {
        if (obj == null) {
            throw new IllegalArgumentException("DetailInsuranceItemsDAO::Update: Received a NULL DetailInsuranceItems object.");
        }

        if (con == null || false == con.isValid(2)) {
            throw new IllegalArgumentException("DetailInsuranceItemsDAO:Update: Received a [NULL] or invalid Connection object");
        }

        String sql = "UPDATE " + table + " SET "
                + " detailInsuranceId = ?,"
                + " detailCptCodeId = ?,"
                + " submissionStatusId = ?,"
                + " timesBilled = ?"
                + " WHERE idDetailInsuranceItems = " + obj.getIdDetailInsuranceItems();

        String sqlOutput = "";
        try (PreparedStatement pStmt = con.prepareStatement(sql)) {
            int i = 0;
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getDetailInsuranceId());
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getDetailCptCodeId());
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getSubmissionStatusId());
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getTimesBilled());
            sqlOutput = pStmt.toString();
            pStmt.executeUpdate();
        } catch (Exception ex) {
            String errorMsg = ex.getMessage() + " " + sqlOutput;
            System.out.println(errorMsg);
            throw new SQLException(errorMsg);
        }
    }
	//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="update (DetailInsuranceItems)">
    public static void update(DetailInsuranceItems obj) throws SQLException, IllegalArgumentException, NullPointerException {
        if (obj == null) {
            throw new IllegalArgumentException("DetailInsuranceItemsDAO::Update: Received a NULL DetailInsuranceItems object.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false) {
            con = CheckDBConnection.Check(dbs, con);
        }

        update(con, obj);
    }
	//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="get (Connection, DetailInsuranceItems, boolean (forUpdate))">
    public static DetailInsuranceItems get(Connection con, Integer id, boolean forUpdate) throws SQLException, IllegalArgumentException, NullPointerException {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("DetailInsuranceItemsDAO::get: Received a NULL or empty DetailInsuranceItems object.");
        }

        if (con == null || false == con.isValid(2)) {
            throw new IllegalArgumentException("DetailInsuranceItemsDAO:get: Received a [NULL] or invalid Connection object");
        }

        String sql = "SELECT * FROM " + table + " WHERE idDetailInsuranceItems = " + String.valueOf(id);
        if (forUpdate) {
            sql += " FOR UPDATE ";
        }

        DetailInsuranceItems obj = null;

        try (PreparedStatement pStmt = con.prepareStatement(sql)) {

            ResultSet rs = pStmt.executeQuery();
            if (rs.next()) {
                obj = objectFromResultSet(rs);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage() + " " + sql);
            throw new SQLException(ex.getMessage() + " " + sql);
        }

        return obj;
    }
	//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="get (DetailInsuranceItems)">
    public static DetailInsuranceItems get(Integer id) throws SQLException, IllegalArgumentException, NullPointerException {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("DetailInsuranceItemsDAO::get: Received a NULL or empty DetailInsuranceItems object.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false) {
            con = CheckDBConnection.Check(dbs, con);
        }

        return get(con, id, false); // not 'for update'
    }
	//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="getAllForDetailInsurance (Collection<DetailInsuranceItems>)">
    public static Collection<DetailInsuranceItems> getAllForDetailInsurance(Integer detailInsuranceId) throws SQLException, IllegalArgumentException, NullPointerException
    {
            if (detailInsuranceId == null || detailInsuranceId <= 0)
            {
                    throw new IllegalArgumentException("DetailInsuranceItemAdjustmentsDAO::get: Received a NULL or empty detailInsuranceId object.");
            }

            Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
            Connection con = dbs.getConnection(true);
            if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);

            String sql = "SELECT * FROM " + table + " WHERE detailInsuranceId = " + String.valueOf(detailInsuranceId);

            Collection<DetailInsuranceItems> objList = new ArrayList<DetailInsuranceItems>();
            DetailInsuranceItems obj = null;

            try (PreparedStatement pStmt = con.prepareStatement(sql))
            {

                    ResultSet rs = pStmt.executeQuery();
                    while (rs.next())
                    {
                            obj = objectFromResultSet(rs);
                            objList.add(obj);
                    }
            }
            catch (SQLException ex)
            {
                    System.out.println(ex.getMessage() + " " + sql);
                    throw new SQLException(ex.getMessage() + " " + sql);
            }

            return objList;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="getForDetailInsuranceIdDetailCptCodeId (DetailInsuranceItems)">
    public static DetailInsuranceItems getForDetailInsuranceIdDetailCptCodeId(Integer detailInsuranceId, Integer detailCptCodeId) throws SQLException, IllegalArgumentException, NullPointerException
    {
            if (detailInsuranceId == null || detailInsuranceId <= 0)
            {
                    throw new IllegalArgumentException("DetailInsuranceItemAdjustmentsDAO::get: Received a NULL or empty detailInsuranceId object.");
            }

            Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
            Connection con = dbs.getConnection(true);
            if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);

            String sql = "SELECT * FROM " + table + " WHERE detailInsuranceId = " + String.valueOf(detailInsuranceId) +
                         " AND detailCptCodeId = " + String.valueOf(detailCptCodeId);

            DetailInsuranceItems obj = null;

            try (PreparedStatement pStmt = con.prepareStatement(sql))
            {

                    ResultSet rs = pStmt.executeQuery();
                    if (rs.next())
                    {
                            obj = objectFromResultSet(rs);
                    }
            }
            catch (SQLException ex)
            {
                    System.out.println(ex.getMessage() + " " + sql);
                    throw new SQLException(ex.getMessage() + " " + sql);
            }

            return obj;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="getForDetailInsuranceIdDetailCptCodeId (Connection con, DetailInsuranceItems)">
    public static DetailInsuranceItems getForDetailInsuranceIdDetailCptCodeId(Connection con, Integer detailInsuranceId, Integer detailCptCodeId) throws SQLException, IllegalArgumentException, NullPointerException
    {
            if (detailInsuranceId == null || detailInsuranceId <= 0)
            {
                    throw new IllegalArgumentException("DetailInsuranceItemAdjustmentsDAO::get: Received a NULL or empty detailInsuranceId object.");
            }

            String sql = "SELECT * FROM " + table + " WHERE detailInsuranceId = " + String.valueOf(detailInsuranceId) +
                         " AND detailCptCodeId = " + String.valueOf(detailCptCodeId);

            DetailInsuranceItems obj = null;

            try (PreparedStatement pStmt = con.prepareStatement(sql))
            {
                ResultSet rs = pStmt.executeQuery();
                if (rs.next())
                {
                        obj = objectFromResultSet(rs);
                }
            }
            catch (SQLException ex)
            {
                    System.out.println(ex.getMessage() + " " + sql);
                    throw new SQLException(ex.getMessage() + " " + sql);
            }

            return obj;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="objectFromResultSet (ResultSet)">
    public static DetailInsuranceItems objectFromResultSet(ResultSet rs) throws SQLException, NullPointerException {
        DetailInsuranceItems obj = new DetailInsuranceItems();
        obj.setIdDetailInsuranceItems(SQLUtil.getInteger(rs, "idDetailInsuranceItems"));
        obj.setDetailInsuranceId(SQLUtil.getInteger(rs, "detailInsuranceId"));
        obj.setDetailCptCodeId(SQLUtil.getInteger(rs, "detailCptCodeId"));
        obj.setSubmissionStatusId(SQLUtil.getInteger(rs, "submissionStatusId"));
        obj.setTimesBilled(SQLUtil.getInteger(rs, "timesBilled"));

        return obj;
    }
    //</editor-fold>

    @Override
    public String structureCheck() {
        String query = "SELECT `detailInsuranceItems`.`idDetailInsuranceItems`,\n"
                + "    `detailInsuranceItems`.`detailInsuranceId`,\n"
                + "    `detailInsuranceItems`.`detailCptCodeId`,\n"
                + "    `detailInsuranceItems`.`submissionStatusId`,\n"
                + "    `detailInsuranceItems`.`timesBilled`\n"
                + "FROM `cssbilling`.`detailInsuranceItems` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, Database.DatabaseSingleton.getDatabaseSingleton().getConnection(true));
    }
    
}
