package DAOS;

/**
 * @date: Mar 26, 2012
 * @author: Ryan
 *
 * @project: CSSLibrary
 * @package: DAOS
 * @file name: ProcedureCodeDAO.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc. All Rights Reserved
 */
import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IStructureCheckable;
import DOS.Procedurecodes;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import java.io.Serializable;
import java.sql.*;

import static Utility.SQLUtil.createStatement;

public class ProcedureCodeDAO implements DAOInterface, IStructureCheckable {

    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`procedurecodes`";

    public boolean InsertProcCode(Procedurecodes proc) throws SQLException {
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
                    + "`name`, "
                    + "`cpt`, "
                    + "`modifier1`, "
                    + "`modifier2`, "
                    + "`modifier3`, "
                    + "`modifier4`, "
                    + "`modifier5`, "
                    + "`modifier6`, "
                    + "`multiplier`, "
                    + "`test`) "
                    + "values (?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement pStmt = con.prepareStatement(stmt);

            pStmt.setString(1, proc.getName());
            pStmt.setString(2, proc.getCpt());
            pStmt.setString(3, proc.getModifier1());
            pStmt.setString(4, proc.getModifier2());
            pStmt.setString(5, proc.getModifier3());
            pStmt.setString(6, proc.getModifier4());
            pStmt.setString(7, proc.getModifier5());
            pStmt.setString(8, proc.getModifier6());
            pStmt.setInt(9, proc.getMultiplier());
            pStmt.setInt(10, proc.getTest());

            pStmt.executeUpdate();

            pStmt.close();

            return true;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return false;
        }
    }

    public boolean UpdateProcCode(Procedurecodes proc) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }

        try {
            String stmt = "UPDATE " + table + " SET "
                    + "`name` = ?,"
                    + "`cpt` = ?,"
                    + "`modifier1` = ?,"
                    + "`modifier2` = ?,"
                    + "`modifier3` = ?, "
                    + "`modifier4` = ?, "
                    + "`modifier5` = ?, "
                    + "`modifier6` = ?, "
                    + "`multiplier` = ?, "
                    + "`test` = ? "
                    + "WHERE  `idprocedureCodes` = " + proc.getIdprocedureCodes();

            PreparedStatement pStmt = con.prepareStatement(stmt);

            pStmt.setString(1, proc.getName());
            pStmt.setString(2, proc.getCpt());
            pStmt.setString(3, proc.getModifier1());
            pStmt.setString(4, proc.getModifier2());
            pStmt.setString(5, proc.getModifier3());
            pStmt.setString(6, proc.getModifier4());
            pStmt.setString(7, proc.getModifier5());
            pStmt.setString(8, proc.getModifier6());
            pStmt.setInt(9, proc.getMultiplier());
            pStmt.setInt(10, proc.getTest());

            pStmt.executeUpdate();

            pStmt.close();

            return true;
        } catch (Exception ex2) {
            System.out.println(ex2.toString());
            return false;
        }
    }

    public Procedurecodes GetProcCode(String name, int test) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        try {
            Procedurecodes proc = new Procedurecodes();
            PreparedStatement stmt = null; //con.createStatement();

            String query = "SELECT * FROM " + table
                    + " WHERE `name` = ?"
                    + " AND `test` = " + test;

            stmt = createStatement(con, query, name);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                proc.setIdprocedureCodes(rs.getInt("idprocedureCodes"));
                proc.setName(rs.getString("name"));
                proc.setCpt(rs.getString("cpt"));
                proc.setModifier1(rs.getString("modifier1"));
                proc.setModifier2(rs.getString("modifier2"));
                proc.setModifier3(rs.getString("modifier3"));
                proc.setModifier4(rs.getString("modifier4"));
                proc.setModifier5(rs.getString("modifier5"));
                proc.setModifier6(rs.getString("modifier6"));
                proc.setMultiplier(rs.getInt("multiplier"));
                proc.setTest(rs.getInt("test"));

            }

            rs.close();
            stmt.close();

            return proc;
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
        String query = "SELECT `procedurecodes`.`idprocedureCodes`,\n"
                + "    `procedurecodes`.`name`,\n"
                + "    `procedurecodes`.`cpt`,\n"
                + "    `procedurecodes`.`modifier1`,\n"
                + "    `procedurecodes`.`modifier2`,\n"
                + "    `procedurecodes`.`modifier3`,\n"
                + "    `procedurecodes`.`modifier4`,\n"
                + "    `procedurecodes`.`modifier5`,\n"
                + "    `procedurecodes`.`modifier6`,\n"
                + "    `procedurecodes`.`multiplier`,\n"
                + "    `procedurecodes`.`test`\n"
                + "FROM `css`.`procedurecodes` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, con);
    }
}
