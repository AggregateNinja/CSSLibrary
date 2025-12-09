/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DAOS;

import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IStructureCheckable;
import static DAOS.TestDAO.SetTestFromResultSet;
import DOS.DrugInfo;
import Database.CheckDBConnection;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author eboss
 */
public class DrugInfoDAO implements DAOInterface, IStructureCheckable {
    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    
    private final String table = "`drugInfo`";
    
    private final ArrayList<String> fields = new ArrayList<String>();
    
    public DrugInfoDAO() {
        fields.add("id");
        fields.add("drugId");
        fields.add("drug");
        fields.add("strength");
        fields.add("ingredients");
        fields.add("formulation");
        fields.add("rxcui");
        fields.add("specific_prod_id");
        fields.add("dose_route");
        fields.add("dose_amount");
        fields.add("dose_units");
    }
    
    private void CheckDBConnection(){
        dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        con = dbs.getConnection(true);
    }
    
    public List<DrugInfo> GetDrugInfoByOrderId(int orderId) {
        List<DrugInfo> medList = new ArrayList<DrugInfo>();
        
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        
        try {
            Statement stmt = con.createStatement();

            String query = "SELECT di.* " +
                "FROM orders o " +
                "INNER JOIN prescriptions p ON o.idOrders = p.orderId " +
                "INNER JOIN drugInfo di ON p.drugId = di.drugId " +
                "WHERE o.idOrders = " + orderId;

            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                DrugInfo info = new DrugInfo();
                info = SetDrugInfoFromResultSet(info, rs);
                medList.add(info);
            }

            rs.close();
            stmt.close();

            return medList;
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            return null;
        }
        
    }
    
    public DrugInfo GetDrugInfoById(int id) {
        try {
            if (con.isClosed()) {
                CheckDBConnection();
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        
        try
        {
            DrugInfo info = new DrugInfo();
            Statement stmt = con.createStatement();
            
            String query = "SELECT * FROM " + table
                    + " WHERE `id` = " + id;
            
            ResultSet rs = stmt.executeQuery(query);
            
            if(rs.next())
            {
                SetDrugInfoFromResultSet(info, rs);
            }
            
            rs.close();
            stmt.close();
            
            return info;
        }catch(Exception ex)
        {
            System.out.println(ex.toString());
            return null;
        }
    }
    
    public static DrugInfo SetDrugInfoFromResultSet(DrugInfo info, ResultSet rs) throws SQLException
    {
        info.setId(rs.getInt("id"));
        info.setDrugId(rs.getString("drugId"));
        info.setDrug(rs.getString("drug"));
        info.setStrength(rs.getString("strength"));
        info.setIngredients(rs.getString("ingredients"));
        info.setFormulation(rs.getString("formulation"));
        info.setRxcui(rs.getString("rxcui"));
        info.setSpecificProdId(rs.getString("specific_prod_id"));
        info.setDoseRoute(rs.getString("dose_route"));
        info.setDoseAmount(rs.getString("dose_amount"));
        info.setDoseUnits(rs.getString("dose_units"));
        
        return info;
    }

    @Override
    public Boolean Insert(Serializable obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Boolean Update(Serializable obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Boolean Delete(Serializable obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Serializable getByID(Integer ID) {
        return GetDrugInfoById(ID);
    }

    @Override
    public String structureCheck() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
