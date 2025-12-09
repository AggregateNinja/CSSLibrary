/*
 * Copyright (C) 2014 CSS, Inc.  All Rights Reserved.
 * This software is the proprietary information of CSS, Inc.
 */
package DAOS;

import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IStructureCheckable;
import DOS.QcCrossRef;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.Convert;
import Utility.SQLUtil;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import static Utility.SQLUtil.createStatement;

/**
 * {Insert Class description here}
 *
 * @version $Revision: 1.1.1.1 $
 * @since Build {insert version here} 01/21/2014
 * @author Ryan S. Piper <Ryan@csslis.com >
 */
public class QcCrossRefDAO implements DAOInterface, IStructureCheckable {

    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    String table = "`qcCrossRef`";

    public boolean InsertXRef(QcCrossRef xref) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }

        try {
            String stmt = " INSERT INTO `" + table + "` ("
                    + "`crossReference`, "
                    + "`controlLevel, "
                    + "`createdBy`, "
                    + "`created`, "
                    + "`idInst`) "
                    + "VALUES (?,?,?,?,?)";
            
            PreparedStatement pStmt = con.prepareStatement(stmt);
            
            SQLUtil.SafeSetString(pStmt, 1, xref.getCrossReference());
            SQLUtil.SafeSetInteger(pStmt, 2, xref.getControlLevel());
            SQLUtil.SafeSetInteger(pStmt, 3, xref.getCreatedBy());
            SQLUtil.SafeSetTimeStamp(pStmt, 4, Convert.ToSQLDateTime(xref.getCreated()));
            SQLUtil.SafeSetInteger(pStmt, 5, xref.getIdInst());

            pStmt.executeUpdate();

            pStmt.close();

            return true;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return false;
        }
    }

    public boolean UpdateXRef(QcCrossRef xref) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }

        try {
            String stmt = "UPDATE `" + table + "` SET "
                    + "`crossReference` = ?, "
                    + "`controlLevel = ?, "
                    + "`createdBy` = ?, "
                    + "`created` = ?, "
                    + "`idInst` = ? "
                    + "WHERE `idqcCrossRef` = " + xref.getIdqcCrossRef();

            PreparedStatement pStmt = con.prepareStatement(stmt);

            SQLUtil.SafeSetString(pStmt, 1, xref.getCrossReference());
            SQLUtil.SafeSetInteger(pStmt, 2, xref.getControlLevel());
            SQLUtil.SafeSetInteger(pStmt, 3, xref.getCreatedBy());
            SQLUtil.SafeSetTimeStamp(pStmt, 4, Convert.ToSQLDateTime(xref.getCreated()));
            SQLUtil.SafeSetInteger(pStmt, 5, xref.getIdInst());

            pStmt.executeUpdate();

            pStmt.close();

            return true;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return false;
        }
    }

    @Deprecated
    public QcCrossRef GetByTestNumber(int testNum) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        try {
            QcCrossRef xref = new QcCrossRef();
            Statement stmt = con.createStatement();
            String query = "SELECT * FROM " + table + " "
                    + "WHERE `testNumber` = " + testNum;

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                xref = qcXrefFromResultSet(xref, rs);
            }

            rs.close();
            stmt.close();

            return xref;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }

    public QcCrossRef GetByReference(String ref) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        try {
            QcCrossRef xref = new QcCrossRef();
            PreparedStatement stmt = null; //con.createStatement();
            String query = "SELECT * FROM " + table + " "
                    + "WHERE `crossReference` = ?";

            stmt = createStatement(con, query, ref);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                xref = qcXrefFromResultSet(xref, rs);
            }

            rs.close();
            stmt.close();

            return xref;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }

    public QcCrossRef GetByInstReference(int inst, String ref) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        try {
            QcCrossRef xref = new QcCrossRef();
            PreparedStatement stmt = null; //con.createStatement();
            String query = "SELECT * FROM " + table + " "
                    + "WHERE `crossReference` = ? "
                    + "AND `idInst` = " + inst;

            stmt = createStatement(con, query, ref);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                xref = qcXrefFromResultSet(xref, rs);
            }

            rs.close();
            stmt.close();

            return xref;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }
    
    public int GetNumberOfLevelForInst(int inst){
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return -1;
        }
        
        try{
            int count = 0;
            
            String query = "SELECT COUNT(*) FROM " + table + " "
                    + "WHERE `idInst` = ?";
            
            PreparedStatement pStmt = con.prepareStatement(query);
            
            SQLUtil.SafeSetInteger(pStmt, 1, inst);
            
            ResultSet rs = pStmt.executeQuery();
            
            if(rs.next()){
                count = rs.getInt(1);
            }
            
            rs.close();
            pStmt.close();
            
            return count;
            
        }catch(Exception ex){
            System.out.println("QcCrossRefDAO::GetNumberOfLevelForInst - "+ex.toString());
            return -1;
        }
    }
    
    public ArrayList<QcCrossRef> GetAllForInst(int inst){
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        
        try{
            ArrayList<QcCrossRef> list = new ArrayList<>();
            QcCrossRef ref = new QcCrossRef();
            
            String query = "SELECT * FROM "+table+" "
                    + "WHERE `idInst` = ?";
            
            PreparedStatement pStmt = con.prepareStatement(query);
            
            SQLUtil.SafeSetInteger(pStmt, 1, inst);
            
            ResultSet rs = pStmt.executeQuery();
            
            while(rs.next()){
                ref = new QcCrossRef();
                list.add(qcXrefFromResultSet(ref,rs));
            }
            
            rs.close();
            pStmt.close();
            
            return list;
        }catch(Exception ex){
            System.out.println("QcCrossRefDAO::GetAllForInst - "+ex.toString());
            return null;
        }
    }
    
    public String GetReferenceByInstLvl(int inst, int lvl){
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        
        try{
            String reference= "";
            
            String query = "SELECT `crossReference` FROM "+table+" "
                    + "WHERE `controlLevel` = ? "
                    + "AND `idInst` = ?";
            
            PreparedStatement pStmt= con.prepareStatement(query);
            
            SQLUtil.SafeSetInteger(pStmt, 1, lvl);
            SQLUtil.SafeSetInteger(pStmt, 2, inst);
            
            ResultSet rs = pStmt.executeQuery();
            
            if(rs.next()){
                reference = rs.getString(1);
            }
            
            rs.close();
            pStmt.close();
            
            return reference;
            
        }catch(Exception ex){
            System.out.println("QcCrossRefDAO::GetReferenceByInstLvl - "+ex.toString());
            return null;
        }
    }

    public QcCrossRef qcXrefFromResultSet(QcCrossRef xref, ResultSet rs) throws SQLException {

        xref.setIdqcCrossRef(rs.getInt("idqcCrossRef"));
        xref.setCrossReference(rs.getString("crossReference"));
        xref.setControlLevel(rs.getInt("controlLevel"));
        xref.setCreatedBy(rs.getInt("createdBy"));
        xref.setCreated(rs.getTimestamp("created"));
        xref.setIdInst(rs.getInt("idInst"));
        return xref;
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
        String query = "SELECT `qcCrossRef`.`idqcCrossRef`,\n"
                + "    `qcCrossRef`.`crossReference`,\n"
                + "    `qcCrossRef`.`controlLevel`,\n"
                + "    `qcCrossRef`.`createdBy`,\n"
                + "    `qcCrossRef`.`created`,\n"
                + "    `qcCrossRef`.`idInst`\n"
                + "FROM `css`.`qcCrossRef` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, con);
    }
}
