package DAOS;

import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IStructureCheckable;
import DOS.PreferenceValueMap;
import DOS.PreferenceKeys;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.SQLUtil;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @date 10/26/2015
 * @author Robert Hussey <r.hussey@csslis.com>
 */
public class PreferenceValueMapDAO implements DAOInterface, IStructureCheckable {

    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`preferenceValueMap`";

    private final ArrayList<String> fields = new ArrayList<>();

    public PreferenceValueMapDAO() {
        fields.add("preferenceKeyId");
        fields.add("preferenceValueId");
        fields.add("editable");
    }

    @Override
    public Boolean Insert(Serializable obj) {
        PreferenceValueMap prefValueMap = (PreferenceValueMap) obj;
        try {
            try {
                if (con.isClosed()) {
                    con = CheckDBConnection.Check(dbs, con);
                }
            } catch (SQLException sex) {
                System.out.println(sex.toString());
                return false;
            }
            String stmt = GenerateInsertStatement(fields);
            PreparedStatement pStmt = con.prepareStatement(stmt);
            SetStatementFromPreferenceValueMap(prefValueMap, pStmt);

            pStmt.executeUpdate();

            pStmt.close();

            return true;
        } catch (SQLException ex) {
            Logger.getLogger(PreferenceValueMapDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("PreferenceValueMapDAO::Insert - " + ex.toString());
            return false;
        }
    }

    @Override
    public Boolean Update(Serializable obj) {
        PreferenceValueMap prefValue = (PreferenceValueMap) obj;
        try {
            try {
                if (con.isClosed()) {
                    con = CheckDBConnection.Check(dbs, con);
                }
            } catch (SQLException sex) {
                System.out.println(sex.toString());
                return false;
            }

            String stmt = GenerateUpdateStatement(fields) + " WHERE `idPreferenceValueMap` = "
                    + prefValue.getIdPreferenceValueMap();
            PreparedStatement pStmt = con.prepareStatement(stmt);
            SetStatementFromPreferenceValueMap(prefValue, pStmt);

            pStmt.executeUpdate();

            pStmt.close();

            return true;
        } catch (SQLException ex) {
            Logger.getLogger(PreferenceValueMapDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("PreferenceValueMapDAO::Update - " + ex.toString());
            return false;
        }
    }

    public Boolean Delete(int idPreferenceValueMap)
    {
        int affected = 0;
        try
        {
            String query = "DELETE FROM ? WHERE idPreferenceValueMap = ?";
            PreparedStatement pStmt = con.prepareStatement(query);
            pStmt.setString(1, table);
            pStmt.setInt(2, idPreferenceValueMap);
            affected = pStmt.executeUpdate();
        }
        catch (SQLException ex)
        {
            Logger.getLogger(PreferenceKeys.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("PreferenceValueMapDAO::Delete - " + ex.toString());
        }
        return (affected > 0);
    }

    @Override
    public Serializable getByID(Integer idPreferenceValueMap) {
        try{
            return GetPreferenceValueMapById(idPreferenceValueMap);
        } catch (SQLException ex){
            Logger.getLogger(PreferenceValueMapDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("PreferenceValueMapDAO::getByID - " + ex.toString());
            return false;
        }
    }

    public boolean InsertPreferenceValueMap(PreferenceValueMap prefValue) throws SQLException {
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
                    + "`preferenceKeyId`, "
                    + "`preferenceValueId`, "
                    + "`editable`) "
                    + "values (?,?,?)";

            PreparedStatement pStmt = con.prepareStatement(stmt);

            SQLUtil.SafeSetInteger(pStmt, 1, prefValue.getPreferenceKeyId());
            SQLUtil.SafeSetInteger(pStmt, 2, prefValue.getPreferenceValueId());
            SQLUtil.SafeSetBoolean(pStmt, 3, prefValue.isEditable());

            pStmt.executeUpdate();

            pStmt.close();

            return true;
        } catch (Exception ex) {
            Logger.getLogger(PreferenceValueMapDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("PreferenceValueMapDAO::InsertPreferenceValueMap - " + ex.toString());
            return false;
        }
    }

    public boolean UpdatePreferenceValueMap(PreferenceValueMap prefValue) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }

        try {
            String stmt = "UPDATE " + table + " SET "
                    + "`preferenceKeyId`, "
                    + "`preferenceValueId`, "
                    + "`editable`) "
                    + "WHERE `idPreferenceValueMap` = ?";

            PreparedStatement pStmt = con.prepareStatement(stmt);

            SQLUtil.SafeSetInteger(pStmt, 1, prefValue.getPreferenceKeyId());
            SQLUtil.SafeSetInteger(pStmt, 2, prefValue.getPreferenceValueId());
            SQLUtil.SafeSetBoolean(pStmt, 3, prefValue.isEditable());
            SQLUtil.SafeSetInteger(pStmt, 4, prefValue.getIdPreferenceValueMap());

            pStmt.executeUpdate();

            pStmt.close();

            return true;
        } catch (Exception ex) {
            Logger.getLogger(PreferenceValueMapDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("PreferenceValueMapDAO::UpdatePreferenceValueMap - " + ex.toString());
            return false;
        }
    }
    
    public PreferenceValueMap GetPreferenceValueMapByKeyAndValue(String key, String value)
    {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        PreferenceValueMap prefValue = new PreferenceValueMap();
        try {
            String stmt = "SELECT "+table+".* FROM " + table + " "
                    + "INNER JOIN `preferenceKeys` "
                    + "ON `preferenceKeyId` = `idPreferenceKeys` AND `preferenceKey` = ? "
                    + "INNER JOIN `preferenceValues` "
                    + "ON `preferenceValueId` = `idPreferenceValues` AND `value` = ?";

            PreparedStatement pStmt = con.prepareStatement(stmt);
            
            SQLUtil.SafeSetString(pStmt, 1, key);
            SQLUtil.SafeSetString(pStmt, 2, value);

            ResultSet rs = pStmt.executeQuery();

            if (rs.next()) {
                SetPreferenceValueMapFromResultSet(prefValue, rs);
            }

            rs.close();
            pStmt.close();

            return prefValue;
        } catch (Exception ex) {
            Logger.getLogger(PreferenceValueMapDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("PreferenceValueMapDAO::GetPreferenceValueMapByKeyAndValue - " + ex.toString());
            return null;
        }
    }

    public PreferenceValueMap GetPreferenceValueMapById(int idPreferenceValueMap) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        PreferenceValueMap prefValue = new PreferenceValueMap();
        try {
            String stmt = "SELECT * FROM " + table + " "
                    + "WHERE `idPreferenceValueMap` = ?";

            PreparedStatement pStmt = con.prepareStatement(stmt);
            
            SQLUtil.SafeSetInteger(pStmt, 1, idPreferenceValueMap);

            ResultSet rs = pStmt.executeQuery();

            if (rs.next()) {
                SetPreferenceValueMapFromResultSet(prefValue, rs);
            }

            rs.close();
            pStmt.close();

            return prefValue;
        } catch (Exception ex) {
            Logger.getLogger(PreferenceValueMapDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("PreferenceValueMapDAO::GetPreferenceValueMapById - " + ex.toString());
            return null;
        }
    }

    public List<PreferenceValueMap> GetAllPreferenceValueMap() throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        PreferenceValueMap prefValue;
        try {
            List<PreferenceValueMap> prefValueList = new ArrayList<PreferenceValueMap>();
            String query = "SELECT * FROM " + table;
            PreparedStatement pStmt = con.prepareStatement(query);

            ResultSet rs = pStmt.executeQuery();

            while (rs.next()) {
                prefValue = new PreferenceValueMap();

                SetPreferenceValueMapFromResultSet(prefValue, rs);

                prefValueList.add(prefValue);
            }

            rs.close();
            pStmt.close();

            return prefValueList;
        } catch (Exception ex) {
            Logger.getLogger(PreferenceValueMapDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("PreferenceValueMapDAO::GetAllPreferenceValueMap - " + ex.toString());
            return null;
        }

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

    private PreferenceValueMap SetPreferenceValueMapFromResultSet(PreferenceValueMap prefValue, ResultSet rs) throws SQLException {
        prefValue.setIdPreferenceValueMap(rs.getInt("idPreferenceValueMap"));
        prefValue.setPreferenceKeyId(rs.getInt("preferenceKeyId"));
        prefValue.setPreferenceValueId(rs.getInt("preferenceValueId"));
        prefValue.setEditable(rs.getBoolean("editable"));

        return prefValue;
    }

    private PreparedStatement SetStatementFromPreferenceValueMap(PreferenceValueMap prefValue, PreparedStatement pStmt) throws SQLException {
        SQLUtil.SafeSetInteger(pStmt, 1, prefValue.getPreferenceKeyId());
        SQLUtil.SafeSetInteger(pStmt, 2, prefValue.getPreferenceValueId());
        SQLUtil.SafeSetBoolean(pStmt, 3, prefValue.isEditable());

        return pStmt;
    }

    @Override
    public Boolean Delete(Serializable obj)
    {
        PreferenceValueMap prefValue = (PreferenceValueMap) obj;
        return Delete(prefValue.getIdPreferenceValueMap());
    }
    
    @Override
    public String structureCheck() {
        return DatabaseStructureCheck.structureCheck(fields, table, con);
    }
}
