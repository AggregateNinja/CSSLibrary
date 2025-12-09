
package DAOS;

import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IStructureCheckable;
import DOS.PhlebotomyRedrawReason;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.SQLUtil;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class PhlebotomyRedrawReasonDAO implements DAOInterface, IStructureCheckable
{
    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`phlebotomyRedrawReasons`";
    private final ArrayList<String> fields = new ArrayList<>();
    
    public PhlebotomyRedrawReasonDAO()
    {
        fields.add("reason");
        fields.add("active");
    }
    
    public ArrayList<PhlebotomyRedrawReason> GetAllActive()
    {
        try
        {
            if (con.isClosed())
            {
                con = CheckDBConnection.Check(dbs, con);
            }
        }
        catch (SQLException ex)
        {
            System.out.println(ex.toString());
            return null;
        }
        
        ArrayList<PhlebotomyRedrawReason> reasons = new ArrayList<>();
        PhlebotomyRedrawReason reason = null;
        try
        {
            String query = "SELECT * FROM " + table
                    + " WHERE `active` = 1";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next())
            {
                reason = new PhlebotomyRedrawReason();
                reason = setFromResultset(reason,rs);
                if (reason != null && reason.getId() != null && reason.getId() > 0)
                {
                    reasons.add(reason);
                }
            }
        }
        catch (SQLException ex)
        {
            String msg = "MicroRedrawReasonDAO::GetAllActive: Unable to retrieve active redraw reasons";
            System.out.println(msg);
            return null;
        }        
        return reasons;
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private PreparedStatement setStatement(PhlebotomyRedrawReason reason, PreparedStatement pStmt) throws SQLException
    {
        int i = 0;
        SQLUtil.SafeSetInteger(pStmt, ++i, reason.getId());
        SQLUtil.SafeSetString(pStmt, ++i, reason.getReason());
        SQLUtil.SafeSetBoolean(pStmt, ++i, reason.isActive());

        return pStmt;
    }
    
    private PhlebotomyRedrawReason setFromResultset(
            PhlebotomyRedrawReason reason,
            ResultSet rs) throws SQLException
    {
        reason.setId(rs.getInt("id"));
        reason.setReason(rs.getString("reason"));
        reason.setActive(rs.getBoolean("active"));
        return reason;
    }    
    
    private String GenerateInsertStatement(ArrayList<String> fields)
    {
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

    private String GenerateUpdateStatement(ArrayList<String> fields)
    {
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
