/*
 * Copyright (C) 2014 CSS, Inc.  All Rights Reserved.
 * This software is the proprietary information of CSS, Inc.
 */
package DAOS;

import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IStructureCheckable;
import DOS.PatientFaceSheet;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.SQLUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * {Insert Class description here}
 *
 * @version $Revision: 1.1.1.1 $
 * @since Build {insert version here} 01/05/2014
 * @author Ryan S. Piper <Ryan@csslis.com >
 */
public class PatientFaceSheetDAO implements DAOInterface, IStructureCheckable {

    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`patientFaceSheet`";

    public boolean InsertFaceSheet(PatientFaceSheet sheet) throws SQLException {
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
                    + "`idpatients`, "
                    + "`type`, "
                    + "`faceSheet`)"
                    + "values(?,?,?)";

            PreparedStatement pStmt = con.prepareStatement(stmt);

            SQLUtil.SafeSetInteger(pStmt, 1, sheet.getIdpatients());
            SQLUtil.SafeSetString(pStmt, 2, sheet.getType());
            SQLUtil.SafeSetBytes(pStmt, 3, sheet.getFaceSheet());


            pStmt.executeUpdate();

            pStmt.close();

            return true;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return false;
        }
    }

    public PatientFaceSheet GetFaceSheet(int id) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        try {
            PatientFaceSheet sheet = new PatientFaceSheet();
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + " WHERE `idpatients` = " + id;

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                sheet.setIdpatients(rs.getInt("idpatients"));
                sheet.setType(rs.getString("type"));
                sheet.setFaceSheet(rs.getBytes("faceSheet"));
            }

            rs.close();
            stmt.close();

            return sheet;

        } catch (Exception ex) {
            System.out.println(ex.toString());
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
        String query = "SELECT `patientFaceSheet`.`idpatients`,\n"
                + "    `patientFaceSheet`.`type`,\n"
                + "    `patientFaceSheet`.`faceSheet`\n"
                + "FROM `css`.`patientFaceSheet` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, con);
    }
}
