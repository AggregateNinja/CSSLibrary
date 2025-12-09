/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package EMR.DAOS;

import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IStructureCheckable;
import EMR.DOS.MissingPrescriptions;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.Convert;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Ryan
 */
public class MissingPrescriptionDAO implements DAOInterface, IStructureCheckable {
    EMR.Database.EMRDatabaseSingleton dbs = EMR.Database.EMRDatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`prescriptions`";
    
    public boolean InsertMissingPrescription(MissingPrescriptions mp){
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }
        
        try{
            String stmt = "INSERT INTO " + table + "("
                    + " `idorders`,"
                    + " `drugName`,"
                    + " `created`)"
                    + " values (?,?,?)";

            PreparedStatement pStmt = con.prepareStatement(stmt);

            pStmt.setInt(1, mp.getIdorders());
            pStmt.setString(2, mp.getDrugName());
            pStmt.setTimestamp(3, Convert.ToSQLDateTime(mp.getCreated()));

            pStmt.executeUpdate();

            pStmt.close();

            return true;
        }catch(Exception ex){
            System.out.println(ex.toString());
            return false;
        }
    }
    
    public MissingPrescriptions GetMissingPrescritpionByID(int Id){
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        
        try {
            MissingPrescriptions ms = new MissingPrescriptions();
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + " WHERE `id` = " + Id;

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                ms.setId(rs.getInt("id"));
                ms.setIdorders(rs.getInt("idorders"));
                ms.setDrugName(rs.getString("drugName"));
                ms.setCreated(rs.getTimestamp("created"));
            }
            
            rs.close();
            stmt.close();
            
            return ms;
        }catch(Exception ex){
            System.out.println(ex.toString());
            return null;
        }
    }
    
    public int OrderHasMissingOrders(int orderId){
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return -1;
        }
        
        try {
            int count = 0;
            Statement stmt = con.createStatement();

            String query = "SELECT COUNT(*) FROM " + table
                    + " WHERE `idorders` = " + orderId;

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                count = rs.getInt("id");
            }
            
            rs.close();
            stmt.close();
            
            return count;
        }catch(Exception ex){
            System.out.println(ex.toString());
            return -1;
        }
    }
    
    public ArrayList<MissingPrescriptions> GetMissingPrescriptionsByOrderID(int orderId){
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        
        try {
            ArrayList<MissingPrescriptions> list = new ArrayList<>();
            MissingPrescriptions ms = new MissingPrescriptions();
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + " WHERE `orderid` = " + orderId;

            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                ms = new MissingPrescriptions();
                ms.setId(rs.getInt("id"));
                ms.setIdorders(rs.getInt("idorders"));
                ms.setDrugName(rs.getString("drugName"));
                ms.setCreated(rs.getTimestamp("created"));
                
                list.add(ms);
            }
            
            rs.close();
            stmt.close();
            
            return list;
        }catch(Exception ex){
            System.out.println(ex.toString());
            return null;
        }
    }
    
    public boolean DeleteMissingPrescriptionByID(int id){
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }
        
        try {
            String stmt = "DELETE FROM " + table + " "
                    + "WHERE `id` = " + id;

            PreparedStatement pStmt = con.prepareStatement(stmt);

            int done = pStmt.executeUpdate();

            pStmt.close();
            
            if (done > 0)
                return true;
            else
                return false;
        }catch(Exception ex){
            System.out.println(ex.toString());
            return false;
        }
    }

    @Override
    public Boolean Insert(Serializable obj)
    {
        return InsertMissingPrescription((MissingPrescriptions)obj);
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
        String query = "SELECT `missingPrescriptions`.`id`,\n"
                + "    `missingPrescriptions`.`idorders`,\n"
                + "    `missingPrescriptions`.`drugName`,\n"
                + "    `missingPrescriptions`.`created`\n"
                + "FROM `emrorders`.`missingPrescriptions` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, con);
    }
}
