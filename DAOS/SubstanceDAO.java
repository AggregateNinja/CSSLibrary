package DAOS;

import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IStructureCheckable;
import DOS.Substances;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.SQLUtil;
import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;

import static Utility.SQLUtil.createStatement;

/**
 * @date: Jun 18, 2013
 * @author: CSS Dev
 *
 * @project: CSSLibrary
 * @package: DAOS
 * @file name: Substance.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc. All Rights Reserved
 */
public class SubstanceDAO implements DAOInterface, IStructureCheckable {

    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`substances`";

    public boolean InsertSubstance(Substances substance) throws SQLException {
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
                    + " `substance`)"
                    + " values (?);";

            PreparedStatement pStmt = con.prepareStatement(stmt);

            pStmt.setString(1, substance.getSubstance());

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

    public boolean UpdateSubstance(Substances substance) throws SQLException {
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
                    + " `substance` = ?,"
                    + "WHERE `idsubstances` = " + substance.getIdsubstances() + ";";


            PreparedStatement pStmt = con.prepareStatement(stmt);

            pStmt.setString(1, substance.getSubstance());

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

    public Substances GetSubstanceById(int Id) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        try {
            Substances substance = new Substances();
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + "WHERE `idsubstances` = " + Id + ";";

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {


                substance.setIdsubstances(rs.getInt("idsubstances"));
                substance.setSubstance(rs.getString("substance"));

            }

            rs.close();
            stmt.close();

            return substance;
        } catch (Exception ex) {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }

    public Substances GetSubstanceByName(String substanceName) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            Substances substance = new Substances();
            //stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + "WHERE `substance` = ?;";

            stmt = createStatement(con, query, substanceName);
            rs = stmt.executeQuery();

            if (rs.next()) {

                substance.setIdsubstances(rs.getInt("idsubstances"));
                substance.setSubstance(rs.getString("substance"));
                return substance;
            }

            return null;
        } catch (Exception ex) {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
        finally 
        {
            if (stmt != null) stmt.close();
            if (rs != null) rs.close();            
        }
    }

    public boolean SubstanceExists(String substanceName) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }

        try {
            PreparedStatement stmt = null;
            ResultSet rs = null;
            int rowCount = -1;

            //stmt = con.createStatement();
            String query = "SELECT COUNT(*) FROM " + table + " WHERE "
                    + "`substance` = ?;";
            stmt = createStatement(con, query, substanceName);
            rs = stmt.executeQuery();
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
            //TODO:  Add Exception Handling
            return false;
        }
    }

    public int GetSubstanceId(String substanceName) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return -1;
        }

        try {
            int substanceId = -1;
            PreparedStatement stmt = null; //con.createStatement();

            String query = "SELECT `idsubstances` FROM " + table
                    + "WHERE `substance` = ?;";

            stmt = createStatement(con, query, substanceName);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                substanceId = rs.getInt("idsubstances");

            }

            rs.close();
            stmt.close();

            return substanceId;
        } catch (Exception ex) {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return -1;
        }
    }

    public ResultSet SearchBySubstanceName(String name) throws SQLException {
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

            String query = "SELECT `idsubstances` AS 'ID', `substance` AS 'Name' "
                    + "FROM " + table + " "
                    + "WHERE `substance` LIKE ? ";

            String nameParam = SQLUtil.createSearchParam(name);
            stmt = createStatement(con, query, nameParam);
            ResultSet rs = stmt.executeQuery();

            return rs;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }
    
    public ArrayList<Substances> GetAllSubstances(){
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        
        try{
            ArrayList<Substances> list = new ArrayList<>();
            Statement stmt = con.createStatement();
            Substances sub = new Substances();
            
             String query = "SELECT `idsubstances` , `substance`  "
                    + "FROM " + table;

            ResultSet rs = stmt.executeQuery(query);
            
            while(rs.next()){
                sub = new Substances();
                
                sub.setIdsubstances(rs.getInt("idsubstances"));
                sub.setSubstance(rs.getString("substance"));
                
                list.add(sub);
            }
            
            stmt.close();
            rs.close();
            
            return list;
            
        }catch(Exception ex){
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
    
    public Boolean deleteByID(Integer ID)
    {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        
        // Need to include substancesLog?
        // idSubstanceLog
        // createdDate
        // idUser
        // action (deleted)
        // oldValue
        // newValue
        try
        {
            Statement stmt = con.createStatement();
            String query = "DELETE FROM `substances` WHERE `idsubstances` = " + ID.toString();
            ResultSet rs = stmt.executeQuery(query);
        }
        catch (SQLException ex)
        {
            System.out.println(ex.toString());
            return null;           
        }
      
        
        return null;
        
    }
    
    @Override
    public String structureCheck() {
        String query = "SELECT `substances`.`idsubstances`,\n"
                + "    `substances`.`substance`\n"
                + "FROM `css`.`substances` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, con);
    }
}
