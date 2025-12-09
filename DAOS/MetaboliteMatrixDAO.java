package DAOS;

import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IStructureCheckable;
import DOS.Metabolitematrix;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @date: Jun 18, 2013
 * @author: Keith Maggio <keith@csslis.com>
 *
 * @project: CSSLibrary
 * @package: DAOS
 * @file name: MetaboliteMatrix.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc. All Rights Reserved
 */
public class MetaboliteMatrixDAO implements DAOInterface, IStructureCheckable {

    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`metaboliteMatrix`";

    public boolean InsertMetaboliteMatrix(Metabolitematrix matrix) throws SQLException {
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
                    + " `substance`,"
                    + " `metabolite`)"
                    + " values (?,?)";

            PreparedStatement pStmt = con.prepareStatement(stmt);

            pStmt.setInt(1, matrix.getSubstabance());
            pStmt.setInt(2, matrix.getMetabolite());

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

    public boolean UpdateMetaboliteMatrix(Metabolitematrix matrix) throws SQLException {
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
                    + " `metabolite` = ? "
                    + "WHERE `idmetaboliteMatrix` = " + matrix.getIdmetaboliteMatrix();


            PreparedStatement pStmt = con.prepareStatement(stmt);

            pStmt.setInt(1, matrix.getSubstabance());
            pStmt.setInt(2, matrix.getMetabolite());

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

    public Metabolitematrix GetMetaboliteMatrix(int Id) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        try {
            Metabolitematrix matrix = new Metabolitematrix();
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + "WHERE `idmetaboliteMatrix` = " + Id;

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {


                matrix.setIdmetaboliteMatrix(rs.getInt("idmetaboliteMatrix"));
                matrix.setSubstabance(rs.getInt("substance"));
                matrix.setMetabolite(rs.getInt("metabolite"));

            }

            rs.close();
            stmt.close();

            return matrix;
        } catch (Exception ex) {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }
    
    public ArrayList<Metabolitematrix> GetMetabolitesForSubstance(int SubstanceID)
    {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        try {
            ArrayList<Metabolitematrix> metabList = new ArrayList<>();
            
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + "WHERE `substance` = " + SubstanceID;

            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {

                Metabolitematrix matrix = new Metabolitematrix();
                matrix.setIdmetaboliteMatrix(rs.getInt("idmetaboliteMatrix"));
                matrix.setSubstabance(rs.getInt("substance"));
                matrix.setMetabolite(rs.getInt("metabolite"));
                metabList.add(matrix);
            }

            rs.close();
            stmt.close();

            return metabList;
        } catch (Exception ex) {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }
    
    /**
     * Deletes the substance / metabolite combination from the
     * metaboliteMatrix table.
     * @param substance The substance id
     * @param metabolite The metabolite id
     * @return Was the delete successful?
     */
    public Boolean Delete(int substance, int metabolite)
    {
        // TODO: since all calls (should) be coming throught he DAOs, 
        // logging on delete should be implemented here, but there's
        //   no place for Substance or Drug logging currently.
        
        if (!connectionOK()) return false;
        if (substance <= 0 || metabolite <= 0) return false;

        PreparedStatement pStmt = null;
        try
        {
            String stmt = "DELETE FROM " +
                    table + " WHERE `substance` = ? " +
                    "AND `metabolite` = ?";

            pStmt = con.prepareStatement(stmt);
            pStmt.setInt(1, substance);
            pStmt.setInt(2, metabolite);
            pStmt.executeUpdate();
            pStmt.close();
        }
        catch (SQLException ex)
        {
            String message = ex.toString();
            System.out.println(message);
            return false;
        }
        return true;
    }
    
    public List<Metabolitematrix> GetByMetaboliteIdSubstanceId(int metaboliteId, int substanceId) throws SQLException
    {
        if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);
        
        String sql = "SELECT * FROM " + table + " WHERE metabolite = " + metaboliteId + " AND substance = " + substanceId;
        PreparedStatement pStmt = con.prepareCall(sql);
        ResultSet rs = pStmt.executeQuery();
        
        List metabolites = new LinkedList<>();
        while (rs.next())
        {
            Metabolitematrix metabolite = new Metabolitematrix();
            metabolite.setIdmetaboliteMatrix(rs.getInt("idmetaboliteMatrix"));
            metabolite.setSubstabance(rs.getInt("substance"));
            metabolite.setMetabolite(rs.getInt("metabolite"));
            metabolites.add(metabolite);
        }
        return metabolites;
    }

    private boolean connectionOK()
    {
        try
        {
            if (con.isClosed())
            {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex)
        {
            System.out.println(sex.toString());
            return false;
        }
        return true;
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
        String query = "SELECT `metaboliteMatrix`.`idmetaboliteMatrix`,\n"
                + "    `metaboliteMatrix`.`substance`,\n"
                + "    `metaboliteMatrix`.`metabolite`\n"
                + "FROM `css`.`metaboliteMatrix` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, con);
    }
}
