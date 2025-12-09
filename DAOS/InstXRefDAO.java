/*
 * Copyright (C) 2014 CSS, Inc.  All Rights Reserved.
 * This software is the proprietary information of CSS, Inc.
 */
package DAOS;

import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IStructureCheckable;
import DOS.IDOS.BaseInstXRef;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.SQLUtil;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static Utility.SQLUtil.createStatement;

/**
 * {Insert Class description here}
 *
 * @version $Revision: 1.1.1.1 $
 * @since Build {insert version here} 01/20/2014
 * @author Ryan S. Piper <Ryan@csslis.com >
 */
public class InstXRefDAO implements DAOInterface, IStructureCheckable {

    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);

    public boolean InsertXRef(String table, BaseInstXRef xref) throws SQLException {
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
                    + "`referencedValue`, "
                    + "`test`) "
                    + "VALUES (?,?)";

            PreparedStatement pStmt = con.prepareStatement(stmt);

            SQLUtil.SafeSetString(pStmt, 1, xref.getReferencedValue());
            SQLUtil.SafeSetInteger(pStmt, 2, xref.getTest());

            pStmt.executeUpdate();

            pStmt.close();

            return true;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return false;
        }
    }

    public boolean UpdateXRef(String table, BaseInstXRef xref) throws SQLException {
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
                    + "`referencedValue` = ?, "
                    + "`test` = ? "
                    + "WHERE `idinstXRef` = ?";
            PreparedStatement pStmt = con.prepareStatement(stmt);

            SQLUtil.SafeSetString(pStmt, 1, xref.getReferencedValue());
            SQLUtil.SafeSetInteger(pStmt, 2, xref.getTest());
            SQLUtil.SafeSetInteger(pStmt, 3, xref.getIdinstXRef1());

            pStmt.executeUpdate();

            pStmt.close();

            return true;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return false;
        }
    }

    public BaseInstXRef GetXRefByTest(String table, int test) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        try {
            Statement stmt = con.createStatement();
            BaseInstXRef xref = new BaseInstXRef();
            String query = "SELECT * FROM `" + table + "` "
                    + "WHERE `test` = " + test;

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                xref = InstXRefFromResultSet(xref, rs);

            }

            rs.close();
            stmt.close();

            return xref;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }

    public BaseInstXRef GetXRefByReference(String table, String ref) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        try {
            PreparedStatement stmt = null; //con.createStatement();
            BaseInstXRef xref = new BaseInstXRef();
            String query = "SELECT * FROM `" + table + "` "
                    + "WHERE `referencedValue` = ?";

            stmt = createStatement(con, query, ref);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                xref = InstXRefFromResultSet(xref, rs);
            }

            rs.close();
            stmt.close();

            return xref;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }

    public BaseInstXRef InstXRefFromResultSet(BaseInstXRef xref, ResultSet rs) throws SQLException {
        xref.setIdinstXRef1(rs.getInt("idinstXRef"));
        xref.setReferencedValue(rs.getString("referencedValue"));
        xref.setTest(rs.getInt("test"));

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
        String query = "SELECT `instXRef_1`.`idinstXRef`,\n"
                + "    `instXRef_1`.`referencedValue`,\n"
                + "    `instXRef_1`.`test`\n"
                + "FROM `css`.`instXRef_1` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, "instXRef_1", con);
    }
}
