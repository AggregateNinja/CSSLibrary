package DAOS;

import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IStructureCheckable;
import DOS.ReferenceLabSettings;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.apache.commons.lang3.NotImplementedException;

/**
 *
 * @author TomR
 */
public class ReferenceLabSettingDAO implements DAOInterface, IStructureCheckable
{
    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`referenceLabSettings`";
    
    private final ArrayList<String> fields = new ArrayList<>();
    public ReferenceLabSettingDAO()
    {
        // Excluding unique database identifier
        fields.add("idDepartment");
        fields.add("idReqReport");
        fields.add("idManReport");
        fields.add("idTestXRef");
        fields.add("idClientXRef");
        fields.add("idInsuranceXRef");
        fields.add("accountNumber");
        fields.add("displayName");
        fields.add("sendingApp");
        fields.add("sendingFac");
        fields.add("receivingApp");
        fields.add("receivingFac");
        fields.add("trueFormat");
        fields.add("falseFormat");
        fields.add("dateFormat");
        fields.add("resultTable");
        fields.add("resultProcedure");
        fields.add("orderTable");
        fields.add("orderProcedure");
        fields.add("numericPaddingChar");
        fields.add("textPaddingChar");
        fields.add("suppressManifest");
        fields.add("suppressReq");
        fields.add("clientBillingOnly");
        fields.add("suppressPatientDemo");
        fields.add("breakDownPanels");
        fields.add("printCommentOnReport");
        fields.add("comment");
        fields.add("allowManualPosting");
        fields.add("created");
        fields.add("deactivatedDate");
        fields.add("active");
    }
    
    private ReferenceLabSettings setReferenceLabSettingsFromResultSet(
        ReferenceLabSettings settings, ResultSet rs) throws SQLException
    {
        if (settings == null) settings = new ReferenceLabSettings();
        settings.setId(rs.getInt("id"));
        settings.setIdDepartment(rs.getInt("idDepartment"));
        settings.setIdReqReport(rs.getInt("idReqReport"));
        settings.setIdManReport(rs.getInt("idManReport"));
        settings.setIdTestXRef(rs.getInt("idTestXRef"));
        settings.setIdClientXRef(rs.getInt("idClientXRef"));
        settings.setIdInsuranceXRef(rs.getInt("idInsuranceXRef"));        
        settings.setAccountNumber(rs.getString("accountNumber"));
        settings.setDisplayName(rs.getString("displayName"));
        settings.setSendingApp(rs.getString("sendingApp"));
        settings.setSendingFac(rs.getString("sendingFac"));
        settings.setReceivingApp(rs.getString("receivingApp"));
        settings.setReceivingFac(rs.getString("receivingFac"));
        settings.setTrueFormat(rs.getString("trueFormat"));
        settings.setFalseFormat(rs.getString("falseFormat"));
        settings.setDateFormat(rs.getString("dateFormat"));
        settings.setResultTable(rs.getString("resultTable"));
        settings.setResultProcedure(rs.getString("resultProcedure"));
        settings.setOrderTable(rs.getString("orderTable"));
        settings.setOrderProcedure(rs.getString("orderProcedure"));        
        settings.setNumericPaddingChar(rs.getString("numericPaddingChar"));
        settings.setTextPaddingChar(rs.getString("textPaddingChar"));
        settings.setSuppressManifest(rs.getBoolean("suppressManifest"));
        settings.setSuppressReq(rs.getBoolean("suppressReq"));
        settings.setClientBillingOnly(rs.getBoolean("clientBillingOnly"));
        settings.setSuppressPatientDemo(rs.getBoolean("suppressPatientDemo"));
        settings.setBreakDownPanels(rs.getBoolean("breakDownPanels"));
        settings.setPrintCommentOnReport(rs.getBoolean("printCommentOnReport"));
        settings.setComment(rs.getString("comment"));
        settings.setAllowManualPosting(rs.getBoolean("allowManualPosting"));
        settings.setCreated(rs.getTimestamp("created"));        
        settings.setDeactivatedDate(rs.getTimestamp("deactivatedDate"));
        settings.setActive(rs.getBoolean("active"));
        
        return settings;
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
    
    public ArrayList<ReferenceLabSettings> GetActive()
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
        
        ArrayList<ReferenceLabSettings> settings = new ArrayList<>();
        try
        {
            String sql = "SELECT * FROM " + table + " WHERE active = 1";
            PreparedStatement pStmt = con.prepareStatement(sql);
            ResultSet rs = pStmt.executeQuery();
            while (rs.next())
            {

                ReferenceLabSettings setting = new ReferenceLabSettings();
                setting = setReferenceLabSettingsFromResultSet(setting, rs);
                settings.add(setting);
            }
        }
        catch (SQLException ex)
        {            
            System.out.println("ReferenceLabSettingDAO::GetSettingsById: " +
                    "Unable to load all active settings");
        }        
        return settings;        
    }
    
    public ReferenceLabSettings GetSettingsById(Integer id)
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
        
        ReferenceLabSettings settings = null;
        
        try
        {
            String sql = "SELECT * FROM " + table + " WHERE id = ?";
            PreparedStatement pStmt = con.prepareStatement(sql);
            pStmt.setInt(1, id);
            ResultSet rs = pStmt.executeQuery();            
            if (rs.next())
            {
                setReferenceLabSettingsFromResultSet(settings, rs);
            }
        }
        catch (SQLException ex)
        {
            String idStr = (id != null)? id.toString(): " (null)";
            System.out.println("ReferenceLabSettingDAO::GetSettingsById: " +
                    "Unable to load settings for id = " + idStr);            
        }        
        return settings;
    }
    
    /**
     * Returns reference lab settings for the supplied department id if the
     *  settings are marked "active"
     * 
     * On error or if there are no active settings, returns NULL
     * 
     * @param deptId
     * @return On error or no active records, NULL
     */
    public ReferenceLabSettings GetActiveSettingsByDeptId(Integer deptId)
    {
       ReferenceLabSettings settings =  GetSettingsByDeptId(deptId);
       
       if (settings != null && settings.isActive())
       {
           return settings;
       }
       return null;       
    }
    
    /**
     * Gets the reference lab settings for the supplied department id
     * 
     * @param deptId
     * @return 
     */
    public ReferenceLabSettings GetSettingsByDeptId(Integer deptId)
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
        
        ReferenceLabSettings settings = null;
        
        try
        {
            String sql = "SELECT * FROM " + table + " WHERE idDepartment = ?";
            PreparedStatement pStmt = con.prepareStatement(sql);
            pStmt.setInt(1, deptId);
            ResultSet rs = pStmt.executeQuery();            
            if (rs.next())
            {
                settings = setReferenceLabSettingsFromResultSet(settings, rs);
            }
        }
        catch (SQLException ex)
        {
            String deptIdStr = (deptId != null)? deptId.toString(): "";
            System.out.println("ReferenceLabSettingDAO::GetSettingsByDeptId: " +
                    "Unable to load reference lab settings for department ID " + deptIdStr);            
        }
        return settings;
    }
    
    @Override
    public Boolean Insert(Serializable obj)
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
        
        try
        {
            ReferenceLabSettings settings = (ReferenceLabSettings)obj;
            String sql = GenerateInsertStatement(fields);
            PreparedStatement pStmt = con.prepareStatement(sql);
            pStmt.executeUpdate();            
        }
        catch (SQLException ex)
        {
            System.out.println("ReferenceLabSettingsDAO::Update: " +
                    "Error attempting to insert reference lab settings");
            return false;
        }        
        return true;
    }

    @Override
    public Boolean Update(Serializable obj)
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
        
        try
        {
            ReferenceLabSettings settings = (ReferenceLabSettings)obj;
            String sql = GenerateUpdateStatement(fields) +
                    " WHERE id = ?";
            PreparedStatement pStmt = con.prepareStatement(sql);
            pStmt.setInt(1, settings.getId());
            pStmt.executeUpdate();            
            
        }
        catch (SQLException ex)
        {
            System.out.println("ReferenceLabSettingsDAO::Update: " +
                    "Error attempting to update reference lab settings");
            return false;
        }        
        return true;        
    }

    @Override
    public Boolean Delete(Serializable obj)
    {
        throw new NotImplementedException("");
    }

    @Override
    public Serializable getByID(Integer id)
    {
        try
        {
            return (Serializable)GetSettingsById(id);
        }
        catch (Exception ex)
        {
            System.out.println("ReferenceLabSettingDAO::getByID: " +
                    "Unable to cast to serializable object");
            return null;
        }        
    }
    
    @Override
    public String structureCheck() {
        return DatabaseStructureCheck.structureCheck(fields, table, con);
    }
}
