/*
 * Copyright (C) 2013 CSS, Inc.  All Rights Reserved.
 * This software is the proprietary information of CSS, Inc.
 */
package DAOS;

/**
 * {Insert Class description here}
 *
 * @version $Revision: 1.1.1.1 $
 * @since Build {insert version here} 04/02/2013
 * @author Ryan S. Piper <Ryan@csslis.com >
 */
import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IStructureCheckable;
import DOS.Detaillines;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import java.sql.*;
import Utility.Convert;
import java.io.Serializable;
import java.util.ArrayList;
import Utility.SQLUtil;

import static Utility.SQLUtil.createStatement;

public class DetaillinesDAO implements DAOInterface, IStructureCheckable {

    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`detaillines`";
    
    ArrayList<Integer> generatedIDs = new ArrayList<>();

    public boolean InsertDetail(Detaillines detail) throws SQLException {
        generatedIDs.clear();
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }

        try {
            String stmt = "INSERT INTO " + table + " ("
                    + "`detail`, "
                    + "`price`,"
                    + "`created`, "
                    + "`insurance`,"
                    + "`cpt`, "
                    + "`cpt_multiplier`, "
                    + "`modifier`, "
                    + "`modifier2`, "
                    + "`modifier3`, "
                    + "`modifier4`, "
                    + "`modifier5`, "
                    + "`modifier6`, "
                    + "`complete`"
                    + ")"
                    + "values (?,?,?,?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement pStmt = con.prepareStatement(stmt, Statement.RETURN_GENERATED_KEYS);

            pStmt.setInt(1, detail.getDetail());
            pStmt.setBigDecimal(2, detail.getPrice());
            java.util.Date now = new java.util.Date();
            pStmt.setDate(3, Convert.ToSQLDate(now));
            pStmt.setInt(4, detail.getInsurance());
            pStmt.setString(5, detail.getCpt());
            pStmt.setInt(6, detail.getCptMultiplier());
            pStmt.setString(7, detail.getModifier());
            pStmt.setString(8, detail.getModifier2());
            pStmt.setString(9, detail.getModifier3());
            pStmt.setString(10, detail.getModifier4());
            pStmt.setString(11, detail.getModifier5());
            pStmt.setString(12, detail.getModifier6());
            pStmt.setBoolean(13, detail.isComplete());

            pStmt.executeUpdate();
            
            ResultSet rs = pStmt.getGeneratedKeys();
            if (rs.next())
            {
                generatedIDs.add(rs.getInt(1));
            }
            rs.close();

            pStmt.close();

            return !generatedIDs.isEmpty();
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return false;
        }
    }

    public boolean InsertBulkDetail(Detaillines detail) throws SQLException {
        generatedIDs.clear();
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }

        try {
            String stmt = "INSERT INTO " + table + " ("
                    + "`detail`, "
                    + "`price`,"
                    + "`created`, "
                    + "`insurance`,"
                    + "`cpt`, "
                    + "`cpt_multiplier`, "
                    + "`modifier`, "
                    + "`modifier2`, "
                    + "`modifier3`, "
                    + "`modifier4`, "
                    + "`modifier5`, "
                    + "`modifier6`, "
                    + "`complete`"
                    + ")"
                    + "values (?,?,?,?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement pStmt = con.prepareStatement(stmt, Statement.RETURN_GENERATED_KEYS);

            pStmt.setInt(1, detail.getDetail());
            pStmt.setBigDecimal(2, detail.getPrice());
            //java.util.Date now = new java.util.Date();
            pStmt.setDate(3, Convert.ToSQLDate(detail.getCreated()));
            pStmt.setInt(4, detail.getInsurance());
            pStmt.setString(5, detail.getCpt());
            pStmt.setInt(6, detail.getCptMultiplier());
            pStmt.setString(7, detail.getModifier());
            pStmt.setString(8, detail.getModifier2());
            pStmt.setString(9, detail.getModifier3());
            pStmt.setString(10, detail.getModifier4());
            pStmt.setString(11, detail.getModifier5());
            pStmt.setString(12, detail.getModifier6());
            pStmt.setBoolean(13, detail.isComplete());

            pStmt.executeUpdate();

            ResultSet rs = pStmt.getGeneratedKeys();
            if (rs.next())
            {
                generatedIDs.add(rs.getInt(1));
            }
            rs.close();

            pStmt.close();

            return !generatedIDs.isEmpty();
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return false;
        }
    }

    public boolean UpdateDetail(Detaillines detail) throws SQLException {
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
                    + " `detail` = ?,"
                    + " `price` = ?,"
                    + " `created` = ?,"
                    + " `insurance` = ?,"
                    + " `cpt` = ?,"
                    + " `cpt_multiplier` = ?,"
                    + " `modifier` = ?,"
                    + " `modifier2` = ?,"
                    + " `modifier3` = ?,"
                    + " `modifier4` = ?,"
                    + " `modifier5` = ?,"
                    + " `modifier6` = ?,"
                    + " `complete` = ? "
                    + "WHERE `iddetaillines` = " + detail.getIddetailLines();

            PreparedStatement pStmt = con.prepareStatement(stmt);

            pStmt.setInt(1, detail.getDetail());
            pStmt.setBigDecimal(2, detail.getPrice());
            java.util.Date now = new java.util.Date();
            pStmt.setDate(3, Convert.ToSQLDate(now));
            pStmt.setInt(4, detail.getInsurance());
            pStmt.setString(5, detail.getCpt());
            pStmt.setInt(6, detail.getCptMultiplier());
            pStmt.setString(7, detail.getModifier());
            pStmt.setString(8, detail.getModifier2());
            pStmt.setString(9, detail.getModifier3());
            pStmt.setString(10, detail.getModifier4());
            pStmt.setString(11, detail.getModifier5());
            pStmt.setString(12, detail.getModifier6());
            pStmt.setBoolean(13, detail.isComplete());

            pStmt.executeUpdate();

            pStmt.close();

            return true;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return false;
        }
    }

    public Detaillines GetDeatilById(int ID) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        Detaillines detail = new Detaillines();

        try {
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + " WHERE `iddetaillines` = " + ID;

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                detail = new Detaillines();

                detail.setIddetailLines(rs.getInt("iddetiallines"));
                detail.setDetail(rs.getInt("detail"));
                detail.setPrice(rs.getBigDecimal("price"));
                detail.setCreated(rs.getTimestamp("crerated"));
                detail.setInsurance(rs.getInt("insurance"));
                detail.setCpt(rs.getString("cpt"));
                detail.setCptMultiplier(rs.getInt("cpt_multiplier"));
                detail.setModifier(rs.getString("modifier"));
                detail.setModifier2(rs.getString("modifier2"));
                detail.setModifier3(rs.getString("modifier3"));
                detail.setModifier4(rs.getString("modifier4"));
                detail.setModifier5(rs.getString("modifier5"));
                detail.setModifier6(rs.getString("modifier6"));
                detail.setComplete(rs.getBoolean("complete"));
            }

            rs.close();
            stmt.close();

            return detail;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }
    
    public Integer GetDetailId(String PatientID, String Accession, int TestNo) throws SQLException
    {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        Integer id = null;
        /* This query is not working since there is no field named testNo
        /*String query =  "SELECT d.iddetaillines " +
                        "FROM detaillines d " +
                        "LEFT JOIN results r " +
                        "ON r.idResults = d.detail " +
                        "LEFT JOIN tests t " +
                        "ON t.idtests = r.testid " +
                        "LEFT JOIN orders o " +
                        "ON o.idOrders = r.orderid " +
                        "LEFT JOIN patients p " +
                        "ON p.idPatients = o.patientId " +
                        "WHERE r.idResults IS NOT NULL " +
                        "AND t.idTests IS NOT NULL " +
                        "AND o.idOrders IS NOT NULL " +
                        "AND p.idPatients IS NOT NULL " + 
                        "AND p.arNo = '" + PatientID + "' " + 
                        "AND o.accession = '" + Accession + "' " + 
                        "AND t.testNo = '" + TestNo + "';";*/

        String query =  "SELECT d.iddetaillines " +
                        "FROM detaillines d " +
                        "LEFT JOIN results r " +
                        "ON r.idResults = d.detail " +
                        "LEFT JOIN tests t " +
                        "ON t.idtests = r.testid " +
                        "LEFT JOIN orders o " +
                        "ON o.idOrders = r.orderid " +
                        "LEFT JOIN patients p " +
                        "ON p.idPatients = o.patientId " +
                        "WHERE r.idResults IS NOT NULL " +
                        "AND t.idTests IS NOT NULL " +
                        "AND o.idOrders IS NOT NULL " +
                        "AND p.idPatients IS NOT NULL " + 
                        "AND p.arNo = ? " + 
                        "AND o.accession = ? " + 
                        "AND t.number = '" + TestNo + "';";
        
        
        PreparedStatement stmt = createStatement(con, query, PatientID, Accession); //con.createStatement();
        
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            id = rs.getInt(1);
        }

        rs.close();
        stmt.close();

        return id;
    }

    public int GetDetailId(int ar, String acc, int test, int subtest) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return -1;
        }

        int id;
        String ids = "";

        String x =
                "SELECT iddetaillines "
                + "FROM detaillines "
                + "WHERE detail = ( "
                + "SELECT r.idResults "
                + "FROM results r, "
                + "( "
                + "  SELECT t.idTests "
                + "  FROM tests t "
                + "  WHERE t.number = " + test + " "
                + "  AND t.subtest = " + subtest + " "
                + ") tst, "
                + "( "
                + "  SELECT o.idOrders "
                + "  FROM orders o, "
                + "  ( "
                + "    SELECT p.idPatients "
                + "    FROM patients p "
                + "    WHERE p.arNo = " + ar + " "
                + "  ) pat "
                + "  WHERE o.accession = ? "
                + "  AND o.patientId = pat.idPatients "
                + ") ords "
                + "WHERE ords.idOrders = r.orderId "
                + "AND tst.idTests = r.testId"
                + ");";

        try {
            PreparedStatement stmt = createStatement(con, x, acc);//con.createStatement();

            /*String query = "SELECT css.detaillines.iddetailLines "
             + "FROM css.detaillines "
             + "WHERE css.detaillines.detail = (select css.results.idResults "
             + "FROM css.patients, css.orders, css.results, css.tests "
             + "WHERE css.patients.idPatients = css.orders.patientID "
             + "AND css.orders.idOrders = css.results.orderId "
             + "AND css.results.testId = css.tests.idtests "
             + "AND css.patients.arNo = " + ar 
             + " AND css.orders.accession = " + acc
             + " AND css.tests.number = " + test
             + " AND css.tests.subtest = " + subtest + ")";*/

            /*String x = "SELECT iddetaillines "
             + "FROM detaillines "
             + "WHERE detail = (SELECT r.idResults"
             + " FROM results r"
             + " LEFT OUTER JOIN"
             + " orders o ON r.orderId = o.idOrders"
             + " LEFT OUTER JOIN"
             + " tests t ON r.testId = t.idTests"
             + " LEFT OUTER JOIN"
             + " patients p ON o.patientId = p.idPatients"
             + " WHERE p.arNo = " + ar
             + " AND o.accession = " + acc
             + " AND t.number = " + test
             + " AND t.subtest = " + subtest + ")";*/



            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                ids = rs.getString(1);
                id = Integer.parseInt(ids);
            } else {
                id = 0;
            }

            rs.close();
            stmt.close();

            return id;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return 0;
        }
    }

    public boolean DetailExists(int detail) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }

        try {
            Statement stmt = null;
            ResultSet rs = null;
            int rowCount = -1;

            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT COUNT(*) FROM " + table + " WHERE "
                    + "`detail` = " + detail);
            rs.next();
            rowCount = rs.getInt(1);

            rs.close();
            stmt.close();

            if (rowCount > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception ex) {
            return false;
        }
    }
    
    public ArrayList<Integer> GetGeneratedIDs()
    {
        return generatedIDs;
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
        String query = "SELECT `detaillines`.`iddetailLines`,\n"
                + "    `detaillines`.`detail`,\n"
                + "    `detaillines`.`price`,\n"
                + "    `detaillines`.`created`,\n"
                + "    `detaillines`.`insurance`,\n"
                + "    `detaillines`.`cpt`,\n"
                + "    `detaillines`.`cpt_multiplier`,\n"
                + "    `detaillines`.`modifier`,\n"
                + "    `detaillines`.`modifier2`,\n"
                + "    `detaillines`.`modifier3`,\n"
                + "    `detaillines`.`modifier4`,\n"
                + "    `detaillines`.`modifier5`,\n"
                + "    `detaillines`.`modifier6`,\n"
                + "    `detaillines`.`complete`\n"
                + "FROM `css`.`detaillines` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, con);
    }
}
