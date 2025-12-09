
package DAOS;

import Billing.Database.BillingDatabaseSingleton;
import DAOS.IDAOS.IStructureCheckable;
import DOS.PaymentMethodDetails;
import Database.CheckDBConnection;
import Database.DatabaseSingleton;
import Database.DatabaseStructureCheck;
import Utility.SQLUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static Utility.SQLUtil.createStatement;

public class PaymentMethodDetailsDAO implements IStructureCheckable
{
    private static String table = DatabaseSingleton.getDatabaseSingleton().getProperties().getBillingSchema() + ".`paymentMethodDetails`";
    
    public static PaymentMethodDetails insert(Connection con, PaymentMethodDetails obj) throws SQLException, IllegalArgumentException, NullPointerException
    {
        if(obj == null)
        {
            throw new IllegalArgumentException("PaymentMethodDetailsDAO::Insert: Receieved a NULL PaymentMethodDetails object");
        }
        
        if(con == null || false == con.isValid(2))
        {
            throw new IllegalArgumentException("PaymentMethodDetailsDAO:Insert: Recieved a [NULL] or invalid Connection object");
        }
        
        String sql = "INSERT INTO " + table
                + "("
                + "  idPaymentMethodDetails,"
                + "  paymentDetailId,"
                + "  paymentMethodFieldId,"
                + "  value"
                + ")"
                + "VALUES (?, ?, ?, ?)";
        
        try(PreparedStatement pStmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            int i = 0;
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getIdPaymentMethodDetail());
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getPaymentDetailId());
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getPaymentMethodFieldId());
            SQLUtil.SafeSetString(pStmt, ++i, obj.getValue());
           
            Integer newPaymentMethodDetail = null;
            sql = pStmt.toString();
            pStmt.executeUpdate();
            ResultSet rs = pStmt.getGeneratedKeys();
            if(rs.next())
            {
                newPaymentMethodDetail = rs.getInt(1);
            }
            if(newPaymentMethodDetail == null || newPaymentMethodDetail <= 0)
            {
                throw new NullPointerException("PaymentMethodDetailsDAO::Insert: Cannot retrieve generated identifier for inserted rows.");
            }
        }
        catch(Exception ex)
        {
            System.out.println(ex.getMessage() + " " + sql);
            throw ex;
        }
        return obj;
    }
    
    public static PaymentMethodDetails insert(PaymentMethodDetails obj) throws SQLException, IllegalArgumentException, NullPointerException
    {
        if(obj == null)
        {
            throw new IllegalArgumentException("PaymentMethodDetails::Insert: Recieved a NULL PaymentMethodDetails object");
        }
        
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false) {
            con = CheckDBConnection.Check(dbs, con);
        }

        return insert(con, obj);
    }
    
    public static PaymentMethodDetails get(Connection con, Integer id, boolean forUpdate) throws SQLException, IllegalArgumentException, NullPointerException
    {
        if(id == null || id <= 0)
        {
            throw new IllegalArgumentException("PaymentMethodDetailsDAO::get: Recieved a NULL or empty PaymentMethodDetails object.");
        }
        
        if(con == null || false == con.isValid(2))
        {
            throw new IllegalArgumentException("PaymentMethodDetailsDAO::get: Reieved a [NULL] or invalid Connection object.");
        }
        
        String sql = "SELECT * FROM " + table + " WHERE idPaymentMethodDetails = " + id.toString();
        if(forUpdate)
        {
            sql += " FOR UPDATE ";
        }
        
        PaymentMethodDetails obj = null;

        try (PreparedStatement pStmt = con.prepareStatement(sql)) {

            ResultSet rs = pStmt.executeQuery();
            if (rs.next()) {
                obj = objectFromResultSet(rs);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage() + " " + sql);
            throw new SQLException(ex.getMessage() + " " + sql);
        }

        return obj;
    }
    
    public static PaymentMethodDetails get(Integer id) throws SQLException, IllegalArgumentException, NullPointerException {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("AdjustmentsDAO::get: Received a NULL or empty Adjustments object.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false) {
            con = CheckDBConnection.Check(dbs, con);
        }

        return get(con, id, false); // not 'for update'
    }
     
    public static PaymentMethodDetails objectFromResultSet(ResultSet rs) throws SQLException, NullPointerException 
    {
        PaymentMethodDetails obj = new PaymentMethodDetails();
        obj.setIdPaymentMethodDetail(SQLUtil.getInteger(rs, "idPaymentMethodDetails"));
        obj.setPaymentDetailId(SQLUtil.getInteger(rs, "paymentDetailId"));
        obj.setPaymentMethodFieldId(SQLUtil.getInteger(rs, "paymentMethodFieldId"));
        obj.setValue(rs.getString("value"));

        return obj;
    }
    
    public PaymentMethodDetails getPaymentMethod(int paymentId)
    {
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
            Connection con = dbs.getConnection(true);
            try
            {
                if(con.isClosed())
                {
                    con = CheckDBConnection.Check(BillingDatabaseSingleton.ref, con);
                }
            }
            catch(SQLException ex)
            {
                System.out.println(ex.toString());
                return null;
            }
            
            try
            {
                PaymentMethodDetails payment = new PaymentMethodDetails();
                Statement stmt = con.createStatement();
                
                String query = "SELECT * FROM " + table + " WHERE idPaymentMethodDetails = " + paymentId;
                
                ResultSet rs = stmt.executeQuery(query);
                
                while(rs.next())
                {
                    payment = objectFromResultSet(rs);
                }
                
                rs.close();
                stmt.close();
                return payment;
            }
            catch(Exception ex)
            {
                System.out.println(ex.getMessage());
                return null;
            }
    }
    
    public List<PaymentMethodDetails> SearchPaymentMethodFromCheckNum(String NumFragment)
    {
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
            Connection con = dbs.getConnection(true);
            try
            {
                if(con.isClosed())
                {
                    con = CheckDBConnection.Check(BillingDatabaseSingleton.ref, con);
                }
            }
            catch(SQLException ex)
            {
                System.out.println(ex.toString());
                return null;
            }
            
            List<PaymentMethodDetails> checkList = new ArrayList<>();
            try
            {
                PreparedStatement stmt = null;
                ResultSet rs = null;
                
                //stmt = con.createStatement();
                String query = " SELECT * FROM " + table
                        + " WHERE `idPaymentMethodDetails` LIKE ?"
                        + " ORDER BY `idPaymentMethodDetails`";
                
                String numParam = SQLUtil.createSearchParam(NumFragment);
                stmt = createStatement(con, query, numParam);
                rs = stmt.executeQuery();
                
                while(rs.next())
                {
                    checkList.add(objectFromResultSet(rs));
                }
                
                rs.close();
                stmt.close();
                
                return checkList;
            }
            catch(Exception ex)
            {
                System.out.println(ex.getMessage());
                return null;
            }
    }
    
    @Override
    public String structureCheck() {
        String query = "SELECT `paymentMethodDetails`.`idPaymentMethodDetails`,\n"
                + "    `paymentMethodDetails`.`paymentDetailId`,\n"
                + "    `paymentMethodDetails`.`paymentMethodFieldId`,\n"
                + "    `paymentMethodDetails`.`value`\n"
                + "FROM `cssbilling`.`paymentMethodDetails` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, Database.DatabaseSingleton.getDatabaseSingleton().getConnection(true));
    }
}
