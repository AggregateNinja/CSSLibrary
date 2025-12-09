package DAOS;

import DAOS.IDAOS.IStructureCheckable;
import DOS.PaymentMethod;
import Database.CheckDBConnection;
import Database.DatabaseSingleton;
import Database.DatabaseStructureCheck;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static Utility.SQLUtil.createStatement;

public class PaymentMethodDAO implements IStructureCheckable
{

    private static final String table = DatabaseSingleton.getDatabaseSingleton().getProperties().getBillingSchema() + ".`paymentMethods`";

    public static PaymentMethod get(String name) throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (name == null || name.isEmpty())
        {
            throw new IllegalArgumentException("PaymentMethodsDAO::Get: Received a NULL or empty name object.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "SELECT * FROM " + table + " WHERE name = ? OR systemName = ?";

        PreparedStatement pStmt = createStatement(con, sql, name, name); //con.prepareStatement(sql);

        PaymentMethod obj = null;
        try
        {
            ResultSet rs = pStmt.executeQuery();
            if (rs.next())
            {
                obj = ObjectFromResultSet(rs);
            }
        }
        catch (SQLException ex)
        {
            System.out.println(ex.getMessage() + " " + sql);
            throw new SQLException(ex.getMessage() + " " + sql);
        }

        return obj;
    }
    
    public static PaymentMethod get(Integer id) throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (id == null || id <= 0)
        {
            throw new IllegalArgumentException("PaymentMethodsDAO::Get: Received a NULL or empty PaymentMethods object.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "SELECT * FROM " + table + " WHERE id = " + String.valueOf(id);

        PreparedStatement pStmt = con.prepareStatement(sql);

        PaymentMethod obj = null;
        try
        {
            ResultSet rs = pStmt.executeQuery();
            if (rs.next())
            {
                obj = ObjectFromResultSet(rs);
            }
        }
        catch (SQLException ex)
        {
            System.out.println(ex.getMessage() + " " + sql);
            throw new SQLException(ex.getMessage() + " " + sql);
        }

        return obj;
    }

    /**
     * Returns all payment methods. Limits to active only based on argument.
     * @param activeOnly
     * @return
     * @throws SQLException
     * @throws NullPointerException 
     */
    public static Collection<PaymentMethod> get(boolean activeOnly)
            throws SQLException, NullPointerException
    {
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "SELECT * FROM " + table;
        
        if (activeOnly)
        {
            sql += " WHERE active = 1";
        }

        PreparedStatement pStmt = con.prepareStatement(sql);

        List<PaymentMethod> paymentMethods = new ArrayList<>();
        PaymentMethod obj = null;
        try
        {
            ResultSet rs = pStmt.executeQuery();
            while (rs.next())
            {
                paymentMethods.add(ObjectFromResultSet(rs));
            }
        }
        catch (SQLException ex)
        {
            System.out.println(ex.getMessage() + " " + sql);
            throw new SQLException(ex.getMessage() + " " + sql);
        }

        return paymentMethods;        
    }
    
    private static PaymentMethod ObjectFromResultSet(ResultSet rs) throws SQLException, NullPointerException
    {
        PaymentMethod obj = new PaymentMethod();
        obj.setIdPaymentMethods(rs.getInt("idPaymentMethods"));
        obj.setName(rs.getString("name"));
        obj.setSystemName(rs.getString("systemName"));
        obj.setActive(rs.getInt("active"));

        return obj;
    }
    
    @Override
    public String structureCheck() {
        String query = "SELECT `paymentMethods`.`idPaymentMethods`,\n"
                + "    `paymentMethods`.`name`,\n"
                + "    `paymentMethods`.`systemName`,\n"
                + "    `paymentMethods`.`active`\n"
                + "FROM `cssbilling`.`paymentMethods` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, Database.DatabaseSingleton.getDatabaseSingleton().getConnection(true));
    }
}
