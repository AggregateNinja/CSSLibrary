package DAOS;

import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IStructureCheckable;
import DOS.Drugs;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.SQLUtil;
import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static Utility.SQLUtil.createStatement;

/**
 * @date: Jun 18, 2013
 * @author: CSS Dev
 *
 * @project: CSSLibrary
 * @package: DAOS
 * @file name: Drugs.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc. All Rights Reserved
 */
public class DrugDAO implements DAOInterface, IStructureCheckable {

    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`drugs`";

    public boolean InsertDrugs(Drugs drug) throws SQLException {
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
                    + " `genericName`,"
                    + " `substance1`,"
                    + " `substance2`,"
                    + " `substance3`"
                    + ")"
                    + " VALUES (?, ?, ?, ?);";

            PreparedStatement pStmt = con.prepareStatement(stmt);

            pStmt.setString(1, drug.getGenericName());
            SQLUtil.SafeSetInteger(pStmt, 2, drug.getSubstance1());
            SQLUtil.SafeSetInteger(pStmt, 3, drug.getSubstance2());
            SQLUtil.SafeSetInteger(pStmt, 4, drug.getSubstance3());
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

    public boolean UpdateDrugs(Drugs drug) throws SQLException {
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
                    + " `genericName` = ?,"
                    + " `substance1` = ?,"
                    + " `substance2` = ?,"
                    + " `substance3` = ?"
                    + " WHERE `iddrugs` = " + drug.getIddrugs();


            PreparedStatement pStmt = con.prepareStatement(stmt);

            pStmt.setString(1, drug.getGenericName());
            SQLUtil.SafeSetInteger(pStmt, 2, drug.getSubstance1());
            SQLUtil.SafeSetInteger(pStmt, 3, drug.getSubstance2());
            SQLUtil.SafeSetInteger(pStmt, 4, drug.getSubstance3());
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

    public Drugs GetDrugsById(int Id) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        try {
            Drugs drug = new Drugs();
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + "WHERE `iddrugs` = " + Id + ";";

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {


                drug.setIddrugs(rs.getInt("iddrugs"));
                drug.setGenericName(rs.getString("genericName"));
                drug.setSubstance1(rs.getInt("substance1"));
                drug.setSubstance2(rs.getInt("substance2"));
                drug.setSubstance3(rs.getInt("substance3"));


            }

            rs.close();
            stmt.close();

            return drug;
        } catch (Exception ex) {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }

    public Drugs GetDrugsByName(String GenericName) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        try {
            Drugs drug = new Drugs();
            PreparedStatement stmt = null; //con.createStatement();

            String query = "SELECT * FROM " + table
                    + "WHERE `genericName` = ?;";

            stmt = createStatement(con, query, GenericName);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {


                drug.setIddrugs(rs.getInt("iddrugs"));
                drug.setGenericName(rs.getString("genericName"));
                drug.setSubstance1(rs.getInt("substance1"));
                drug.setSubstance2(rs.getInt("substance2"));
                drug.setSubstance3(rs.getInt("substance3"));


            }

            rs.close();
            stmt.close();

            return drug;
        } catch (Exception ex) {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }

    public List<Drugs> GetAllDrugs() // The best function ever...
    {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        List<Drugs> lstDrugs = new ArrayList<Drugs>();
        Drugs drug;
        try {
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table + " "
                    + "ORDER BY `genericName`;";

            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                drug = new Drugs();
                drug.setIddrugs(rs.getInt("iddrugs"));
                drug.setGenericName(rs.getString("genericName"));
                drug.setSubstance1(rs.getInt("substance1"));
                drug.setSubstance2(rs.getInt("substance2"));
                drug.setSubstance3(rs.getInt("substance3"));
                lstDrugs.add(drug);
            }

            rs.close();
            stmt.close();

            return lstDrugs;
        } catch (SQLException ex) {
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
        String query = "SELECT `drugs`.`iddrugs`,\n"
                + "    `drugs`.`genericName`,\n"
                + "    `drugs`.`substance1`,\n"
                + "    `drugs`.`substance2`,\n"
                + "    `drugs`.`substance3`\n"
                + "FROM `css`.`drugs` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, con);
    }
}
