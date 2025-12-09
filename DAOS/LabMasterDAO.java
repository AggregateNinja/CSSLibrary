package DAOS;

import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IStructureCheckable;
import DOS.LabMaster;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @date: Oct 17, 2013
 * @author: Keith Maggio <keith@csslis.com>
 *
 * @project:
 * @package: DAOS
 * @file name: LabMasterDAO.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc. All Rights Reserved
 */
public class LabMasterDAO implements DAOInterface, IStructureCheckable {

    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`labMaster`";
    /**
     * All fields except idlabMaster
     */
    private final ArrayList<String> fields = new ArrayList<String>();

    public LabMasterDAO() {
        fields.add("labName");
        fields.add("facilityName");
        fields.add("address1");
        fields.add("address2");
        fields.add("city");
        fields.add("state");
        fields.add("zip");
        fields.add("physicalAddress1");
        fields.add("physicalAddress2");
        fields.add("physicalCity");
        fields.add("physicalState");
        fields.add("physicalZip");
        fields.add("country");
        fields.add("locality");
        fields.add("phone");
        fields.add("fax");
        fields.add("CLIANumber");
        fields.add("COLANumber");
        fields.add("NPINumber");
        fields.add("taxID");
        fields.add("providerID");
        fields.add("labDirector");
        fields.add("contact");
        fields.add("website");
        fields.add("email");
        fields.add("logoPath");
        fields.add("logoPath2");
        fields.add("avalonVersion");
    }

    public boolean InsertLabMaster(LabMaster labMaster) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }

        try {
            String stmt = GenerateInsertStatement(fields);

            PreparedStatement pStmt = con.prepareStatement(stmt);

            pStmt.setString(1, labMaster.getLabName());
            pStmt.setString(2, labMaster.getFacilityName());
            pStmt.setString(3, labMaster.getAddress1());
            pStmt.setString(4, labMaster.getAddress2());
            pStmt.setString(5, labMaster.getCity());
            pStmt.setString(6, labMaster.getState());
            pStmt.setString(7, labMaster.getPhysicalAddress1());
            pStmt.setString(8, labMaster.getPhysicalAddress2());
            pStmt.setString(9, labMaster.getPhysicalCity());
            pStmt.setString(10, labMaster.getPhysicalState());
            pStmt.setString(11, labMaster.getPhysicalZip());
            pStmt.setString(12, labMaster.getCountry());
            pStmt.setString(13, labMaster.getLocality());
            pStmt.setString(14, labMaster.getPhone());
            pStmt.setString(15, labMaster.getFax());
            pStmt.setString(16, labMaster.getCLIANumber());
            pStmt.setString(17, labMaster.getCOLANumber());
            pStmt.setInt(18, labMaster.getNPINumber());
            pStmt.setString(19, labMaster.getTaxID());
            pStmt.setString(20, labMaster.getProviderID());
            pStmt.setString(21, labMaster.getLabDirector());
            pStmt.setString(22, labMaster.getContact());
            pStmt.setString(23, labMaster.getWebsite());
            pStmt.setString(24, labMaster.getEmail());
            pStmt.setString(25, labMaster.getLogoPath());
            pStmt.setString(26, labMaster.getLogoPath2());
            pStmt.setString(27, labMaster.getAvalonVersion());

            String temp = pStmt.toString();

            pStmt.executeUpdate();

            pStmt.close();

            return true;

        } catch (SQLException ex) {
            System.out.println(labMaster.getLabName() + ":Insert: " + ex.toString());
            return false;
        }
    }

    public boolean UpdateLabMaster(LabMaster labMaster) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }

        try {
            String stmt = GenerateUpdateStatement(fields)
                    + "WHERE `idlabMaster` = " + labMaster.getIdlabMaster();

            PreparedStatement pStmt = con.prepareStatement(stmt);

            pStmt.setString(1, labMaster.getLabName());
            pStmt.setString(2, labMaster.getFacilityName());
            pStmt.setString(3, labMaster.getAddress1());
            pStmt.setString(4, labMaster.getAddress2());
            pStmt.setString(5, labMaster.getCity());
            pStmt.setString(6, labMaster.getState());
            pStmt.setString(7, labMaster.getPhysicalAddress1());
            pStmt.setString(8, labMaster.getPhysicalAddress2());
            pStmt.setString(9, labMaster.getPhysicalCity());
            pStmt.setString(10, labMaster.getPhysicalState());
            pStmt.setString(11, labMaster.getPhysicalZip());
            pStmt.setString(12, labMaster.getCountry());
            pStmt.setString(13, labMaster.getLocality());
            pStmt.setString(14, labMaster.getPhone());
            pStmt.setString(15, labMaster.getFax());
            pStmt.setString(16, labMaster.getCLIANumber());
            pStmt.setString(17, labMaster.getCOLANumber());
            pStmt.setInt(18, labMaster.getNPINumber());
            pStmt.setString(19, labMaster.getTaxID());
            pStmt.setString(20, labMaster.getProviderID());
            pStmt.setString(21, labMaster.getLabDirector());
            pStmt.setString(22, labMaster.getContact());
            pStmt.setString(23, labMaster.getWebsite());
            pStmt.setString(24, labMaster.getEmail());
            pStmt.setString(25, labMaster.getLogoPath());
            pStmt.setString(26, labMaster.getLogoPath2());
            pStmt.setString(27, labMaster.getAvalonVersion());

            String temp = pStmt.toString();

            pStmt.executeUpdate();

            pStmt.close();

            return true;
        } catch (SQLException ex) {
            System.out.println(labMaster.getLabName() + ":Update: " + ex.toString());
            return false;
        }
    }

    public LabMaster GetLabMaster() {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        try {
            LabMaster lm = new LabMaster();
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table;

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                SetLabMasterFromResultSet(lm, rs);
            }

            rs.close();
            stmt.close();

            return lm;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }

    public LabMaster SetLabMasterFromResultSet(LabMaster labMaster, ResultSet rs) throws SQLException {

        labMaster.setIdlabMaster(rs.getInt("idlabMaster"));
        labMaster.setLabName(rs.getString("labName"));
        labMaster.setFacilityName(rs.getString("facilityName"));
        labMaster.setAddress1(rs.getString("address1"));
        labMaster.setAddress2(rs.getString("address2"));
        labMaster.setCity(rs.getString("city"));
        labMaster.setState(rs.getString("state"));
        labMaster.setZip(rs.getString("zip"));
        labMaster.setPhysicalAddress1(rs.getString("physicalAddress1"));
        labMaster.setPhysicalAddress2(rs.getString("physicalAddress2"));
        labMaster.setPhysicalCity(rs.getString("physicalCity"));
        labMaster.setPhysicalState(rs.getString("physicalState"));
        labMaster.setPhysicalZip(rs.getString("physicalZip"));
        labMaster.setCountry(rs.getString("country"));
        labMaster.setLocality(rs.getString("locality"));
        labMaster.setPhone(rs.getString("phone"));
        labMaster.setFax(rs.getString("fax"));
        labMaster.setCLIANumber(rs.getString("CLIANumber"));
        labMaster.setCOLANumber(rs.getString("COLANumber"));
        labMaster.setNPINumber(rs.getInt("NPINumber"));
        labMaster.setTaxID(rs.getString("taxID"));
        labMaster.setProviderID(rs.getString("providerID"));
        labMaster.setLabDirector(rs.getString("labDirector"));
        labMaster.setContact(rs.getString("contact"));
        labMaster.setWebsite(rs.getString("website"));
        labMaster.setEmail(rs.getString("email"));
        labMaster.setLogoPath(rs.getString("logoPath"));
        labMaster.setLogoPath2(rs.getString("logoPath2"));
        labMaster.setAvalonVersion(rs.getString("avalonVersion"));
        return labMaster;
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
    public Boolean Insert(Serializable obj)
    {
        try
        {
            return InsertLabMaster((LabMaster)obj);
        }
        catch (SQLException ex)
        {
            Logger.getLogger(LabMasterDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public Boolean Update(Serializable obj)
    {
        try
        {
            return UpdateLabMaster((LabMaster)obj);
        }
        catch (SQLException ex)
        {
            Logger.getLogger(LabMasterDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public Boolean Delete(Serializable obj)
    {
        return false;
    }

    @Override
    public Serializable getByID(Integer ID)
    {
        return GetLabMaster();
    }
    
    @Override
    public String structureCheck() {
        return DatabaseStructureCheck.structureCheck(fields, table, con);
    }
}
