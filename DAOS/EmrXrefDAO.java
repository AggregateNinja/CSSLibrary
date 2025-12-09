package DAOS;

import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IStructureCheckable;
import DOS.EmrXref;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.Convert;
import Utility.SQLUtil;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import static Utility.SQLUtil.createStatement;

/**
 * @date: Apr 21, 2014
 * @author: Keith Maggio <keith@csslis.com>
 *
 * @project:
 * @package: DAOS
 * @file name: EmrXrefDAO.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc. All Rights Reserved
 */
public class EmrXrefDAO implements DAOInterface, IStructureCheckable
{

    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`emrXref`";
    /**
     * All fields except idemrReports
     */
    private final ArrayList<String> fields = new ArrayList<String>();

    public EmrXrefDAO()
    {
        fields.add("name");
        fields.add("description");
        fields.add("isBiDirectional");
        fields.add("testInterface");
        fields.add("clientInterface");
        fields.add("insuranceInterface");
        fields.add("doctorInterface");
        fields.add("created");
        fields.add("active");
        fields.add("doctorRequired");
        fields.add("isSendout");
        fields.add("autoApproval");
    }

    @Override
    public Boolean Insert(Serializable obj)
    {
        EmrXref emrX = (EmrXref) obj;
        try
        {
            try
            {
                if (con.isClosed())
                {
                    con = CheckDBConnection.Check(dbs, con);
                }
            }
            catch (SQLException sex)
            {
                System.out.println(sex.toString());
                return false;
            }
            String stmt = GenerateInsertStatement(fields);
            PreparedStatement pStmt = con.prepareStatement(stmt);
            SetStatementFromEmrXref(emrX, pStmt);
            pStmt.executeUpdate();

            pStmt.close();

            return true;
        }
        catch (SQLException ex)
        {
            Logger.getLogger(EmrReportDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public Boolean Update(Serializable obj)
    {
        EmrXref emrX = (EmrXref) obj;
        try
        {

            try
            {
                if (con.isClosed())
                {
                    con = CheckDBConnection.Check(dbs, con);
                }
            }
            catch (SQLException sex)
            {
                System.out.println(sex.toString());
                return false;
            }
            String stmt = GenerateUpdateStatement(fields) + " WHERE `idEmrXref` = " + emrX.getIdEmrXref();
            PreparedStatement pStmt = con.prepareStatement(stmt);

            //SetStatementFromEmrXref(emrX, pStmt);
            //pStmt.setInt(1, emrX.getIdEmrXref());
            int i=0;
            SQLUtil.SafeSetString(pStmt, ++i, emrX.getName());
            SQLUtil.SafeSetString(pStmt, ++i, emrX.getDescription());
            SQLUtil.SafeSetBoolean(pStmt, ++i, emrX.getIsBiDirectional(), false);
            SQLUtil.SafeSetInteger(pStmt, ++i, emrX.getTestInterface());
            SQLUtil.SafeSetInteger(pStmt, ++i, emrX.getClientInterface());
            SQLUtil.SafeSetInteger(pStmt, ++i, emrX.getInsuranceInterface());
            SQLUtil.SafeSetInteger(pStmt, ++i, emrX.getDoctorInterface());
            pStmt.setTimestamp(++i, Convert.ToSQLDateTime(emrX.getCreated()));
            SQLUtil.SafeSetBoolean(pStmt, ++i, emrX.getActive(), false);
            SQLUtil.SafeSetBoolean(pStmt, ++i, emrX.getDoctorRequired(), false);
            SQLUtil.SafeSetBoolean(pStmt, ++i, emrX.getIsSendout(), false);         
            SQLUtil.SafeSetBoolean(pStmt, ++i, emrX.getAutoApproval(), false);  

            pStmt.executeUpdate();

            pStmt.close();

            return true;
        }
        catch (SQLException ex)
        {
            Logger.getLogger(EmrReportDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public Boolean Delete(Serializable obj)
    {
        try
        {
            if (con.isClosed())
            {
                con = CheckDBConnection.Check(dbs, con);
            }
        }
        catch (SQLException sex)
        {
            System.out.println(sex.toString());
            return false;
        }
        return false;
    }
    
    public EmrXref GetById(Integer id)
            throws SQLException, NullPointerException, IllegalArgumentException
    {
        if (id == null || id.equals(0))
            throw new IllegalArgumentException("EmrXrefDAO::GetById: Received a NULL identifier argument!");
        
        if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);
        
        String sql = "SELECT * FROM " + table + " WHERE `idEmrXref` = ?";
        PreparedStatement pStmt = con.prepareStatement(sql);
        pStmt.setInt(1, id);
        ResultSet rs = pStmt.executeQuery();
        EmrXref emrXref = null;
        if (rs.next())
        {
            emrXref = new EmrXref();
            emrXref = SetEmrXrefFromResultSet(emrXref, rs);
        }
        
        rs.close();
        pStmt.close();
        
        return emrXref;
    }
    

    @Override
    public Serializable getByID(Integer ID)
    {
        try
        {
            try
            {
                if (con.isClosed())
                {
                    con = CheckDBConnection.Check(dbs, con);
                }
            }
            catch (SQLException sex)
            {
                System.out.println(sex.toString());
                return false;
            }
            EmrXref emrX = new EmrXref();
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + " WHERE `idEmrXref` = " + ID;

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next())
            {

                SetEmrXrefFromResultSet(emrX, rs);
            }

            rs.close();
            stmt.close();
            return emrX;
        }
        catch (SQLException ex)
        {
            Logger.getLogger(EmrReportDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public EmrXref GetEmrXrefByName(String name)
    {
        try
        {
            if (con.isClosed())
            {
                con = CheckDBConnection.Check(dbs, con);
            }
        }
        catch (SQLException sex)
        {
            System.out.println(sex.toString());
            return null;
        }

        try
        {
            EmrXref emrX = new EmrXref();
            PreparedStatement stmt = null; // con.createStatement();

            String query = "SELECT * FROM " + table
                    + " WHERE `name` = ?";

            stmt = createStatement(con, query, name);
            ResultSet rs = stmt.executeQuery();

            if (rs.next())
            {

                SetEmrXrefFromResultSet(emrX, rs);
            }

            rs.close();
            stmt.close();
            return emrX;
        }
        catch (SQLException ex)
        {
            Logger.getLogger(EmrReportDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public ArrayList<EmrXref> GetAllEMRs()
    {
        ArrayList<EmrXref> emrList = new ArrayList<>();
        try
        {
            try
            {
                if (con.isClosed())
                {
                    con = CheckDBConnection.Check(dbs, con);
                }
            }
            catch (SQLException sex)
            {
                System.out.println(sex.toString());
                return null;
            }

            Statement stmt = con.createStatement();
            String query = "SELECT * FROM " + table
                    + " WHERE `active` = true";

            ResultSet rs = stmt.executeQuery(query);

            while (rs.next())
            {
                EmrXref emrX = new EmrXref();
                SetEmrXrefFromResultSet(emrX, rs);
                emrList.add(emrX);
            }

            rs.close();
            stmt.close();
            return emrList;
        }
        catch (SQLException ex)
        {
            Logger.getLogger(EmrReportDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public ArrayList<EmrXref> GetEveryEMR()
    {
        ArrayList<EmrXref> emrList = new ArrayList<>();
        try
        {
            try
            {
                if (con.isClosed())
                {
                    con = CheckDBConnection.Check(dbs, con);
                }
            }
            catch (SQLException sex)
            {
                System.out.println(sex.toString());
                return null;
            }

            Statement stmt = con.createStatement();
            String query = "SELECT * FROM " + table
                    + " WHERE `active` IS NOT NULL";

            ResultSet rs = stmt.executeQuery(query);

            while (rs.next())
            {
                EmrXref emrX = new EmrXref();
                SetEmrXrefFromResultSet(emrX, rs);
                emrList.add(emrX);
            }

            rs.close();
            stmt.close();
            return emrList;
        }
        catch (SQLException ex)
        {
            Logger.getLogger(EmrReportDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    // I had to change how this function worked since we added an isSendout field
    // to the emrXRef table to be able to distinguis the bi-directional interfaces
    // that are sendout interfaces. So since java dont have default args I had
    // to overload the function to be able to pass a boolean to either get all
    // bi-directional interfaces and all bi-directional interfaces that are not
    // sendout interfaces.
    public ArrayList<EmrXref> GetAllBiDirectionalEMRs()
    {
        return GetAllBiDirectionalEMRs(false);
    }
    
    public ArrayList<EmrXref> GetAllBiDirectionalEMRs(boolean withSendouts)
    {
        ArrayList<EmrXref> emrList = new ArrayList<>();
        try
        {
            try
            {
                if (con.isClosed())
                {
                    con = CheckDBConnection.Check(dbs, con);
                }
            }
            catch (SQLException sex)
            {
                System.out.println(sex.toString());
                return null;
            }

            Statement stmt = con.createStatement();
            String query = "SELECT * FROM " + table
                    + " WHERE `isBiDirectional` = true"
                    + " AND `active` = true"
                    + " AND `isSendout` = " + withSendouts;

            ResultSet rs = stmt.executeQuery(query);

            while (rs.next())
            {
                EmrXref emrX = new EmrXref();
                SetEmrXrefFromResultSet(emrX, rs);
                emrList.add(emrX);
            }

            rs.close();
            stmt.close();
            return emrList;
        }
        catch (SQLException ex)
        {
            Logger.getLogger(EmrReportDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }        
    }
    
    public ArrayList<EmrXref> GetAllResultOnlyEMRs()
    {
        ArrayList<EmrXref> emrList = new ArrayList<>();
        try
        {
            try
            {
                if (con.isClosed())
                {
                    con = CheckDBConnection.Check(dbs, con);
                }
            }
            catch (SQLException sex)
            {
                System.out.println(sex.toString());
                return null;
            }

            Statement stmt = con.createStatement();
            String query = "SELECT * FROM " + table
                    + " WHERE `isBiDirectional` = false"
                    + " AND `active` = true";;

            ResultSet rs = stmt.executeQuery(query);

            while (rs.next())
            {
                EmrXref emrX = new EmrXref();
                SetEmrXrefFromResultSet(emrX, rs);
                emrList.add(emrX);
            }

            rs.close();
            stmt.close();
            return emrList;
        }
        catch (SQLException ex)
        {
            Logger.getLogger(EmrReportDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    // Added function to be able to get all sendout interfaces
    public ArrayList<EmrXref> GetAllSendoutInterfaces()
    {
        ArrayList<EmrXref> emrList = new ArrayList<>();
        try
        {
            try
            {
                if (con.isClosed())
                {
                    con = CheckDBConnection.Check(dbs, con);
                }
            }
            catch (SQLException sex)
            {
                System.out.println(sex.toString());
                return null;
            }

            Statement stmt = con.createStatement();
            String query = "SELECT * FROM " + table
                    + " WHERE `active` = true"
                    + " AND `isSendout` = true";

            ResultSet rs = stmt.executeQuery(query);

            while (rs.next())
            {
                EmrXref emrX = new EmrXref();
                SetEmrXrefFromResultSet(emrX, rs);
                emrList.add(emrX);
            }

            rs.close();
            stmt.close();
            return emrList;
        }
        catch (SQLException ex)
        {
            Logger.getLogger(EmrReportDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }        
    }
    
    private String GenerateInsertStatement(ArrayList<String> fields)
    {

        String stmt = "INSERT INTO " + table + "(";
        String values = ") values (";
        for (int i = 0; i < fields.size(); ++i)
        {
            stmt += " `" + fields.get(i) + "`";
            values += "?";
            if (i != fields.size() - 1)
            {
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
        for (int i = 0; i < fields.size(); ++i)
        {
            stmt += " `" + fields.get(i) + "` = ?";
            if (i != fields.size() - 1)
            {
                stmt += ",";
            }
        }
        return stmt;
    }

    private EmrXref SetEmrXrefFromResultSet(EmrXref emrX, ResultSet rs) throws SQLException
    {
        emrX.setIdEmrXref(rs.getInt("idEmrXref"));
        emrX.setName(rs.getString("name"));
        emrX.setDescription(rs.getString("description"));
        emrX.setIsBiDirectional(rs.getBoolean("isBiDirectional"));
        emrX.setTestInterface(rs.getInt("testInterface"));
        emrX.setClientInterface(rs.getInt("clientInterface"));
        emrX.setInsuranceInterface(rs.getInt("insuranceInterface"));
        emrX.setDoctorInterface(rs.getInt("doctorInterface"));
        emrX.setCreated(rs.getTimestamp("created"));
        emrX.setActive(rs.getBoolean("active"));
        emrX.setDoctorRequired(rs.getBoolean("doctorRequired"));
        emrX.setIsSendout(rs.getBoolean("isSendout"));
        emrX.setAutoApproval(rs.getBoolean("autoApproval"));
        return emrX;
    }

    private PreparedStatement SetStatementFromEmrXref(EmrXref emrX, PreparedStatement pStmt) throws SQLException
    {
        pStmt.setInt(1, emrX.getIdEmrXref());
        SQLUtil.SafeSetString(pStmt, 2, emrX.getName());
        SQLUtil.SafeSetString(pStmt, 3, emrX.getDescription());
        SQLUtil.SafeSetBoolean(pStmt, 4, emrX.getIsBiDirectional(), false);
        SQLUtil.SafeSetInteger(pStmt, 5, emrX.getTestInterface());
        SQLUtil.SafeSetInteger(pStmt, 6, emrX.getClientInterface());
        SQLUtil.SafeSetInteger(pStmt, 7, emrX.getInsuranceInterface());
        SQLUtil.SafeSetInteger(pStmt, 8, emrX.getDoctorInterface());
        pStmt.setTimestamp(9, Convert.ToSQLDateTime(emrX.getCreated()));
        SQLUtil.SafeSetBoolean(pStmt, 10, emrX.getActive(), false);
        SQLUtil.SafeSetBoolean(pStmt, 11, emrX.getDoctorRequired(), false);
        SQLUtil.SafeSetBoolean(pStmt, 12, emrX.getIsSendout(), false);
        SQLUtil.SafeSetBoolean(pStmt, 13, emrX.getAutoApproval(), false);
        return pStmt;
    }

    @Override
    public String structureCheck() {
        return DatabaseStructureCheck.structureCheck(fields, table, con);
    }
}
