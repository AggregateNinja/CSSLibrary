package DAOS;

/**
 * @date: Mar 26, 2012
 * @author: Ryan
 *
 * @project: CSSLibrary
 * @package: DAOS
 * @file name: FeeDAO.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc. All Rights Reserved
 */
import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IStructureCheckable;
import DOS.Fee;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import java.sql.*;
import Utility.Convert;
import Utility.SQLUtil;
import java.io.Serializable;

public class FeeDAO implements DAOInterface, IStructureCheckable {

    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`fee`";

    public boolean InsertFee(Fee fee) throws SQLException {
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
                    + "`feeName`, "
                    + "`test`, "
                    + "`fee`, "
                    + "`panel`)"
                    + "values (?,?,?,?)";

            PreparedStatement pStmt = con.prepareStatement(stmt);

            pStmt.setString(1, fee.getFeeName());
            pStmt.setInt(2, fee.getTest());
            pStmt.setDouble(3, fee.getFee());
            SQLUtil.SafeSetInteger(pStmt, 4, fee.getPanel());
            pStmt.executeUpdate();

            pStmt.close();

            return true;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return false;
        }
    }

    public boolean UpdateFee(Fee fee) throws SQLException {
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
                    + "`feeName` = ?, "
                    + "`test` = ?, "
                    + "`fee` = ?, "
                    + "`panel` = ?"
                    + " WHERE `idFee` = " + fee.getIdFee();

            PreparedStatement pStmt = con.prepareStatement(stmt);

            pStmt.setString(1, fee.getFeeName());
            pStmt.setInt(2, fee.getTest());
            pStmt.setDouble(3, fee.getFee());
            SQLUtil.SafeSetInteger(pStmt, 4, fee.getPanel());
            pStmt.executeUpdate();

            pStmt.close();

            return true;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return false;
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
        String query = "SELECT `fee`.`idFee`,\n"
                + "    `fee`.`feeName`,\n"
                + "    `fee`.`test`,\n"
                + "    `fee`.`fee`,\n"
                + "    `fee`.`panel`\n"
                + "FROM `css`.`fee` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, con);
    }
}
