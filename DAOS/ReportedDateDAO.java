/*
 * Copyright (C) 2013 CSS, Inc.  All Rights Reserved.
 * This software is the proprietary information of CSS, Inc.
 */
package DAOS;

import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IStructureCheckable;
import java.sql.Connection;
import DOS.Reporteddate;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import Utility.Convert;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * {Insert Class description here}
 *
 * @version $Revision: 1.1.1.1 $
 * @since Build {insert version here} 04/23/2013
 * @author Ryan S. Piper <Ryan@csslis.com >
 */
public class ReportedDateDAO implements DAOInterface, IStructureCheckable {

    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`reporteddate`";

    public boolean InsertRow(Reporteddate repDate) throws SQLException {
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
                    + "`idreporteddate`,"
                    + " `dateReported`)"
                    + " values(?,?)";

            PreparedStatement pStmt = con.prepareStatement(stmt);

            pStmt.setInt(1, repDate.getIdreporteddate());
            pStmt.setDate(2, Convert.ToSQLDate(repDate.getDateReported()));

            pStmt.executeUpdate();
            pStmt.close();

            return true;
        } catch (Exception ex) {
            System.out.println("ReportedDateDAO::InsertRow : " + ex.toString());
            return false;
        }
    }

    public Reporteddate GetReportedDate(int ordId) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        try {
            Reporteddate repDate = new Reporteddate();
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + " WHERE `idreporteddate` = " + ordId;

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                repDate.setIdreporteddate(rs.getInt("idreporteddate"));
                repDate.setDateReported(rs.getDate("dateReported"));
            }

            rs.close();
            stmt.close();

            return repDate;
        } catch (Exception ex) {
            System.out.println("ReportedDateDAO::GetReportedDate : " + ex.toString());
            return null;
        }
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
        String query = "SELECT `reporteddate`.`idreporteddate`,\n"
                + "    `reporteddate`.`dateReported`\n"
                + "FROM `css`.`reporteddate` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, con);
    }
}
