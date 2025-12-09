/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DAOS;

import DAOS.IDAOS.IStructureCheckable;
import DOS.AOEGroupingType;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author TomR
 */
public class AOEGroupingTypeDAO implements IStructureCheckable
{
    Database.DatabaseSingleton dbs = 
            Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`aoeGroupingType`";
    private final ArrayList<String> fields = new ArrayList<>();
    
    public AOEGroupingTypeDAO()
    {        
        fields.add("name");
        fields.add("requiresPatientHeight");
        fields.add("requiresPatientWeight");
    }
    
    public AOEGroupingType GetByID(int id)
    {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            return null;
        }
        
        AOEGroupingType result = null;
        try
        {
            String query = "SELECT * FROM " + table
                    + " WHERE id = " + id;

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            
            if (rs.next())
            {
                result = setAOEGroupingTypeFromResultSet(
                        new AOEGroupingType(), rs);
            }
            
            rs.close();
            stmt.close();
                        
        }
        catch (SQLException ex)
        {
            System.out.println("AOEGroupingTypeDAO::GetByID : Unable " +
                    "to get AOEGroupingType from id: " + id);
            return null;
        }
        return result;
    }
    
    private AOEGroupingType setAOEGroupingTypeFromResultSet(
            AOEGroupingType groupingType, ResultSet rs)
    {
        try
        {
            groupingType.setId(rs.getInt("id"));
            groupingType.setName(rs.getString("name"));
            groupingType.setRequiresPatientHeight(rs.getBoolean("requiresPatientHeight"));
            groupingType.setRequiresPatientWeight(rs.getBoolean("requiresPatientWeight"));            
        }
        catch (SQLException ex)
        {
            System.out.println("AOEGroupingTypeDAO::setAOEGroupingTypeFromResultSet "
                + "unable to create new AOEGroupingType object from resultset");
            return null;
        }

        return groupingType;
    }

    @Override
    public String structureCheck() {
        return DatabaseStructureCheck.structureCheck(fields, table, con);
    }
    
}
