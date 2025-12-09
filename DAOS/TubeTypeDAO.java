package DAOS;

import DAOS.IDAOS.IStructureCheckable;
import DOS.TubeType;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.SQLUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import static Utility.SQLUtil.createStatement;

/**
 *
 * @author TomR
 */
public class TubeTypeDAO implements IStructureCheckable
{
    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`tubeType`";
    /**
     * All fields except unique identifier
     */
    private final ArrayList<String> fields = new ArrayList<>();

    public TubeTypeDAO()
    {
        // Excluding unique database identifier
        fields.add("abbr");
        fields.add("name");
        fields.add("idSpecimenTypes");
    }
    
    public TubeType GetByTubeTypeID(int idTubeType)
    {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            return null;
        }
        
        try
        {
            TubeType result = null;
            String query = "SELECT * FROM " + table
                    + " WHERE `idTubeType` = " + idTubeType;
            
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            if (rs.next())
            {
                result = setTubeTypeFromResultset(new TubeType(), rs);
            }
            
            rs.close();
            stmt.close();
            
            return result;
        }
        catch (SQLException ex)
        {
            System.out.println("TubeTypeDAO::GetByTubeTypeID : Error loading " +
                    "idTubeType " + idTubeType);
            return null;
        }        
    }
    
    public TubeType GetByAbbr(String abbr)
    {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            return null;
        }
        
        try
        {
            TubeType result = null;
            String query = "SELECT * FROM " + table
                    + " WHERE `abbr` = ?";
            
            PreparedStatement stmt = createStatement(con, query, abbr); //con.createStatement();
            ResultSet rs = stmt.executeQuery();

            // There should only be one tube type for a given abbreviation
            if (rs.next())
            {
                result = setTubeTypeFromResultset(new TubeType(), rs);
            }
            
            rs.close();
            stmt.close();
            
            return result;
        }
        catch (SQLException ex)
        {
            System.out.println("TubeTypeDAO::GetByAbbr : Error loading " +
                    "tube type for abbreviation: " + abbr);
            return null;
        }
    }
    
    public TubeType GetByName(String name)
    {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            return null;
        }
        
        try
        {
            TubeType result = null;
            String query = "SELECT * FROM " + table
                    + " WHERE `name` = ?";
            
            PreparedStatement stmt = createStatement(con, query, name);//con.createStatement();
            ResultSet rs = stmt.executeQuery();

            // There should only be one tube type for a given name
            if (rs.next())
            {
                result = setTubeTypeFromResultset(new TubeType(), rs);
            }
            
            rs.close();
            stmt.close();
            
            return result;
        }
        catch (SQLException ex)
        {
            System.out.println("TubeTypeDAO::GetByName : Error loading " +
                    "tube type for name: " + name);
            return null;
        }
    }
    
    public ArrayList<TubeType> GetAll()
    {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            return null;
        }
        
        try
        {
            TubeType result = null;
            String query = "SELECT * FROM " + table;
            
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            
            ArrayList<TubeType> results = new ArrayList<>();

            while (rs.next())
            {
                results.add(setTubeTypeFromResultset(new TubeType(), rs));
            }
            
            rs.close();
            stmt.close();
            
            return results;
        }
        catch (SQLException ex)
        {
            System.out.println("TubeTypeDAO::GetAll : Error loading tube types");
            return null;
        }        
    }
    
    public ArrayList<TubeType> GetAllBySpecimenTypeId(int idSpecimenTypes)
    {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            return null;
        }
        
        try
        {
            TubeType result = null;
            String query = "SELECT * FROM " + table +
                    " WHERE idSpecimenTypes = " + idSpecimenTypes;
            
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            
            ArrayList<TubeType> results = new ArrayList<>();

            while (rs.next())
            {
                results.add(setTubeTypeFromResultset(new TubeType(), rs));
            }
            
            rs.close();
            stmt.close();
            
            return results;
        }
        catch (SQLException ex)
        {
            System.out.println("TubeTypeDAO::GetAllBySpecimenTypeId : Error loading " +
                    " all tube types for specimen Id: " + idSpecimenTypes);
            return null;
        }        
    }
    
    public boolean InsertTubeType(TubeType tubeType)
    {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            return false;
        }

        String stmt = GenerateInsertStatement(fields);

        try (PreparedStatement pStmt = con.prepareStatement(stmt))
        {
            SQLUtil.SafeSetInteger(pStmt, 1, tubeType.getIdTubeType());
            SQLUtil.SafeSetString(pStmt, 2, tubeType.getAbbr());
            SQLUtil.SafeSetString(pStmt, 3, tubeType.getName());
            SQLUtil.SafeSetInteger(pStmt, 4, tubeType.getIdSpecimenTypes());
            
            pStmt.executeUpdate();
            pStmt.close();

            return true;
        } catch (SQLException ex) {
            String message = ex.toString();
            System.out.println(message);
            System.out.println(stmt.toString());
            return false;
        }        
    }
    
    public boolean UpdateTubeType(TubeType tubeType)
    {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }
        
        String stmt = GenerateUpdateStatement(fields) +
        " WHERE `idTubeType` = " + tubeType.getIdTubeType();
        
        try (PreparedStatement pStmt = con.prepareStatement(stmt))
        {
            SQLUtil.SafeSetInteger(pStmt, 1, tubeType.getIdTubeType());
            SQLUtil.SafeSetString(pStmt, 2, tubeType.getAbbr());
            SQLUtil.SafeSetString(pStmt, 3, tubeType.getName());
            SQLUtil.SafeSetInteger(pStmt, 4, tubeType.getIdSpecimenTypes());
            pStmt.executeUpdate();
            pStmt.close();

            return true;            
        }
        catch (SQLException ex)
        {
            String message = ex.toString();
            System.out.println(message);
            System.out.println(stmt.toString());
            return false;
        }

    }
    
    private TubeType setTubeTypeFromResultset(TubeType tubeType,
            ResultSet rs) throws SQLException
    {
        tubeType.setIdTubeType(rs.getInt("idTubeType"));
        tubeType.setAbbr(rs.getString("abbr"));
        tubeType.setName(rs.getString("name"));
        tubeType.setIdSpecimenTypes(rs.getInt("idSpecimenTypes"));
        return tubeType;        
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
    
    @Override
    public String structureCheck() {
        return DatabaseStructureCheck.structureCheck(fields, table, con);
    }
        
}
