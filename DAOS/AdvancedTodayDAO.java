/*
 * Computer Service & Support, Inc.  All Rights Reserved Apr 16, 2015
 */

package DAOS;

import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IStructureCheckable;
import DOS.AdvancedToday;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * @date:   Apr 16, 2015  3:56:56 PM
 * @author: Ryan Piper ryan@csslis.com
 * 
 * @project: CSSLibrary
 * @file name: AdvancedTodayDAO.java  (UTF-8)
 *
 * @Description: 
 *
 */

public class AdvancedTodayDAO implements DAOInterface, IStructureCheckable {

    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`advancedToday`";
    /**
     * All fields except ID
     */
    private final ArrayList<String> fields = new ArrayList<String>();
    
    public AdvancedTodayDAO(){
        fields.add("idemployees");
        fields.add("idOrders");
    }

    @Override
    public Boolean Insert(Serializable obj) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }
        
        try{
            AdvancedToday at = (AdvancedToday) obj;
            String stmt = GenerateInsertStatement(fields);
            PreparedStatement pStmt = con.prepareStatement(stmt);
            SetStatement(at, pStmt);
            pStmt.executeUpdate();

            pStmt.close();

            return true;
        } catch (Exception ex) {
            String message = ex.toString();
            System.out.println(message);
            return false;
        }
    }

    @Override
    public Boolean Update(Serializable obj) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }
        try {
            AdvancedToday at = (AdvancedToday) obj;
            String stmt = GenerateUpdateStatement(fields)
                    + " WHERE `idOrders` = " + at.getIdOrders();
            PreparedStatement pStmt = con.prepareStatement(stmt);

            SetStatement(at, pStmt);

            pStmt.executeUpdate();

            pStmt.close();

            return true;
        } catch (Exception ex) {
            String message = ex.toString();
            System.out.println(message);
            return false;
        }
    }

    @Override
    public Boolean Delete(Serializable obj) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }
        
        try{
            AdvancedToday at = (AdvancedToday) obj;
            String stmt = "DELETE FROM " + table + " "
                    + "WHERE `idemployees` = ? "
                    + "AND `idOrders` = ?";
            PreparedStatement pStmt = con.prepareStatement(stmt);
            
            SetStatement(at, pStmt);
            
            pStmt.executeUpdate();

            pStmt.close();

            return true;
        } catch (Exception ex) {
            String message = ex.toString();
            System.out.println(message);
            return false;
        }
    }

    @Override
    public Serializable getByID(Integer ID) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }
        try {
            AdvancedToday at = new AdvancedToday();
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + " WHERE `idOrders` = " + ID;

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                FromResultSet(at, rs);
            }

            rs.close();
            stmt.close();

            return at;
        } catch (Exception ex) {
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }
    
    public ArrayList<AdvancedToday> GetAllByPhelbotomistID(int ID){
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        try {
            ArrayList<AdvancedToday> list = new ArrayList<>();
            AdvancedToday at = null;
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + " WHERE `idemployees` = " + ID;

            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                at = new AdvancedToday();
                FromResultSet(at, rs);
                list.add(at);
            }

            rs.close();
            stmt.close();

            return list;
        } catch (Exception ex) {
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }
    
    public ArrayList<AdvancedToday> GetAll(){
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        try {
            ArrayList<AdvancedToday> list = new ArrayList<>();
            AdvancedToday at = null;
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table;

            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                at = new AdvancedToday();
                FromResultSet(at, rs);
                list.add(at);
            }

            rs.close();
            stmt.close();

            return list;
        } catch (Exception ex) {
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }
    
    public boolean DropTable(){
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }
        try{
            Statement stmt = con.createStatement();
            String sql = "DROP TABLE " + table;
            
            stmt.executeUpdate(sql);
            
            return true;
        }catch(Exception ex){
            String message = ex.toString();
            System.out.println(message);
            return false;
        }
    }
    
    public boolean CreateTable(){
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }
        try{
            Statement stmt = con.createStatement();
            /*
            String sql = "CREATE TABLE " + table + " ( "
                    + "  `idemployees` INT UNSIGNED NOT NULL, "
                    + "  `idOrders` INT UNSIGNED NOT NULL, "
                    + "  PRIMARY KEY (`idemployees`, `idOrders`), "
                    + "  INDEX `FK_advtdy_ordID_idx` (`idOrders` ASC), "
                    + "  CONSTRAINT `FK_advtdy_empID` "
                    + "    FOREIGN KEY (`idemployees`) "
                    + "    REFERENCES `ryan`.`employees` (`idemployees`) "
                    + "    ON DELETE NO ACTION "
                    + "    ON UPDATE NO ACTION, "
                    + "  CONSTRAINT `FK_advtdy_ordID` "
                    + "    FOREIGN KEY (`idOrders`) "
                    + "    REFERENCES `ryan`.`orders` (`idOrders`) "
                    + "    ON DELETE CASCADE "
                    + "    ON UPDATE NO ACTION) "
                    + "ENGINE = InnoDB";
             */
            
            String sql = "CREATE TABLE " + table + " ( "
                    + "  `idemployees` INT UNSIGNED NOT NULL, "
                    + "  `idOrders` INT UNSIGNED NOT NULL, "
                    + "  PRIMARY KEY (`idemployees`, `idOrders`)) "
                    + "ENGINE = InnoDB";
            
            stmt.executeUpdate(sql);
            
            return true;
        }catch(Exception ex){
            String message = ex.toString();
            System.out.println(message);
            return false;
        }
    }
    
    private String GenerateInsertStatement(ArrayList<String> fields) {

        String stmt = "INSERT INTO " + table + "(";
        String values = ") values (";
        for (int i = 0; i < fields.size(); ++i) {
            stmt += " `" + fields.get(i) + "`";
            values += "?";
            if (i != fields.size() - 1) {
                stmt += ", ";
                values += ",";
            }
        }
        values += ")";
        return stmt + values;
    }

    private String GenerateUpdateStatement(ArrayList<String> fields) {

        String stmt = "UPDATE " + table + " SET";
        for (int i = 0; i < fields.size(); ++i) {
            stmt += " `" + fields.get(i) + "` = ?";
            if (i != fields.size() - 1) {
                stmt += ",";
            }
        }
        return stmt;
    }
    
    private AdvancedToday FromResultSet(AdvancedToday obj, ResultSet rs) throws SQLException{
        obj.setIdemployees(rs.getInt("idemployees"));
        obj.setIdOrders(rs.getInt("idOrders"));
        return obj;
    }
    
    private PreparedStatement SetStatement(AdvancedToday obj, PreparedStatement pStmt) throws SQLException{
        pStmt.setInt(1, obj.getIdemployees());
        pStmt.setInt(2, obj.getIdOrders());
        return pStmt;
    }
    
    @Override
    public String structureCheck() {
        return DatabaseStructureCheck.structureCheck(fields, table, con);
    }
}
