package DAOS;

import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IStructureCheckable;
import DOS.Species;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;

import static Utility.SQLUtil.createStatement;

/**
 *
 * @author Ryan
 */
public class SpeciesDAO implements DAOInterface, IStructureCheckable {

    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`species`";

    public boolean InsertSpecies(Species species) throws SQLException {
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
                    + "`idspecies`,"
                    + "`name`)"
                    + " values (?,?)";

            PreparedStatement pStmt = con.prepareStatement(stmt);

            pStmt.setInt(1, species.getIdspecies());
            pStmt.setString(2, species.getName());

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

    public boolean UpdateSpecies(Species species) throws SQLException {
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
                    + " `idspecies` = ?,"
                    + " `name` = ?"
                    + " WHERE `idspecies` = " + species.getIdspecies();

            PreparedStatement pStmt = con.prepareStatement(stmt);

            pStmt.setInt(1, species.getIdspecies());
            pStmt.setString(2, species.getName());

            pStmt.executeUpdate();

            pStmt.close();

            return true;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return false;
        }
    }

    public boolean SpeciesExists(int speciesNo) {
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
                    + "`idspecies` = " + speciesNo);
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

    public String GetSpeciesID(String species) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        try {
            PreparedStatement stmt = null;
            ResultSet rs = null;
            String name = "";

            //stmt = con.createStatement();
            String query = "SELECT `idspecies` FROM " + table + " "
                    + "WHERE `name` = ?";

            stmt = createStatement(con, query, species);
            rs = stmt.executeQuery();

            if (rs.next()) {
                name = rs.getString("idremarks");
            }

            return name;
        } catch (Exception ex) {
            return "";
        }
    }

    public Species[] GetAllSpecies(boolean FilterNotDefined) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        try {
            Statement stmt = null;
            ResultSet rs = null;
            int x = 0;
            ArrayList<Species> speciesList = new ArrayList<Species>();
            stmt = con.createStatement();
            String query = "SELECT * FROM " + table + ";";

            rs = stmt.executeQuery(query);

            while (rs.next()) {
                Species species = new Species();
                species.setIdspecies(rs.getInt("idspecies"));
                species.setName(rs.getString("name"));

                if (FilterNotDefined == true && species.getName().equalsIgnoreCase("not defined")) {
                    continue;
                }
                speciesList.add(species);
            }
            if (speciesList.isEmpty()) {
                return null;
            }

            Species[] outins = new Species[speciesList.size()];
            outins = speciesList.toArray(outins);
            return outins;
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            return null;
        }
    }

    public Species GetSpecies(int ID) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        try {
            Statement stmt = null;
            ResultSet rs = null;
            int x = 0;
            stmt = con.createStatement();
            Species species = new Species();
            String query = "SELECT * FROM " + table + " "
                    + "WHERE `idspecies` = " + ID + ";";

            rs = stmt.executeQuery(query);

            if (rs.next()) {
                species.setIdspecies(rs.getInt("idspecies"));
                species.setName(rs.getString("name"));
            }
            return species;
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
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
        String query = "SELECT `species`.`idspecies`,\n"
                + "    `species`.`name`\n"
                + "FROM `css`.`species` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, con);
    }
}
