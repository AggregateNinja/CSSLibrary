package DAOS;

import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IStructureCheckable;
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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @date 10/23/2015
 * @author Robert Hussey <r.hussey@csslis.com>
 */
public class PreferenceKeysDAO implements DAOInterface, IStructureCheckable {

    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`preferenceKeys`";

    private final ArrayList<String> fields = new ArrayList<>();

    public PreferenceKeysDAO() {
        fields.add("preferenceKey");
        fields.add("display");
        fields.add("editable");
    }

    @Override
    public Boolean Insert(Serializable obj) {
        PreferenceKeys prefKeys = (PreferenceKeys) obj;
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
            SetStatementFromPreferenceKeys(prefKeys, pStmt);

            pStmt.executeUpdate();

            pStmt.close();

            return true;
        } catch (SQLException ex) {
            Logger.getLogger(PreferenceKeys.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("PreferenceKeysDAO::Insert - " + ex.toString());
            return false;
        }
    }

    @Override
    public Boolean Update(Serializable obj) {
        PreferenceKeys prefKeys = (PreferenceKeys) obj;
        try {
            try {
                if (con.isClosed()) {
                    con = CheckDBConnection.Check(dbs, con);
                }
            } catch (SQLException sex) {
                System.out.println(sex.toString());
                return false;
            }

            String stmt = GenerateUpdateStatement(fields) + " WHERE `idPreferenceKeys` = "
                    + prefKeys.getIdPreferenceKeys();
            PreparedStatement pStmt = con.prepareStatement(stmt);
            SetStatementFromPreferenceKeys(prefKeys, pStmt);

            pStmt.executeUpdate();

            pStmt.close();

            return true;
        } catch (SQLException ex) {
            Logger.getLogger(PreferenceKeys.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("PreferenceKeysDAO::Update - " + ex.toString());
            return false;
        }
    }

    public Boolean Delete(int idPreferenceKeys)
    {
        int affected = 0;
        try
        {
            String query = "DELETE FROM ? WHERE idPreferenceKeys = ?";
            PreparedStatement pStmt = con.prepareStatement(query);
            pStmt.setString(1, table);
            pStmt.setInt(2, idPreferenceKeys);
            affected = pStmt.executeUpdate();
        }
        catch (SQLException ex)
        {
            Logger.getLogger(PreferenceKeys.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("PreferenceKeysDAO::Delete - " + ex.toString());
        }
        return (affected > 0);
    }

    @Override
    public Serializable getByID(Integer idPreferenceKeys) {
        try{
            return GetPreferenceKeysById(idPreferenceKeys);
        } catch (SQLException ex){
            Logger.getLogger(PreferenceKeys.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("PreferenceKeysDAO::getByID - " + ex.toString());
            return false;
        }
    }

    public boolean InsertPreferenceKeys(PreferenceKeys prefKeys) throws SQLException {
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
                    + "`preferenceKey`, "
                    + "`display`, "
                    + "`editable`) "
                    + "values (?,?,?)";

            PreparedStatement pStmt = con.prepareStatement(stmt);

            SQLUtil.SafeSetString(pStmt, 1, prefKeys.getPreferenceKey());
            SQLUtil.SafeSetString(pStmt, 2, prefKeys.getDisplay());
            SQLUtil.SafeSetBoolean(pStmt, 3, prefKeys.isEditable());

            pStmt.executeUpdate();

            pStmt.close();

            return true;
        } catch (Exception ex) {
            Logger.getLogger(PreferenceKeys.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("PreferenceKeysDAO::InsertPreferenceKeys - " + ex.toString());
            return false;
        }
    }

    public boolean UpdatePreferenceKeys(PreferenceKeys prefKeys) throws SQLException {
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
                    + "`preferenceKey` = ?, "
                    + "`display` = ?, "
                    + "`editable` = ? "
                    + "WHERE `idPreferenceKeys` = ?";

            PreparedStatement pStmt = con.prepareStatement(stmt);

            SQLUtil.SafeSetString(pStmt, 1, prefKeys.getPreferenceKey());
            SQLUtil.SafeSetString(pStmt, 2, prefKeys.getDisplay());
            SQLUtil.SafeSetBoolean(pStmt, 3, prefKeys.isEditable());
            SQLUtil.SafeSetInteger(pStmt, 4, prefKeys.getIdPreferenceKeys());

            pStmt.executeUpdate();

            pStmt.close();

            return true;
        } catch (Exception ex) {
            Logger.getLogger(PreferenceKeys.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("PreferenceKeysDAO::UpdatePreferenceKeys - " + ex.toString());
            return false;
        }
    }

    public PreferenceKeys GetPreferenceKeysById(int idPreferenceKeys) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        PreferenceKeys prefKeys = new PreferenceKeys();
        try {
            String stmt = "SELECT * FROM " + table + " "
                    + "WHERE `idPreferenceKeys` = ?";

            PreparedStatement pStmt = con.prepareStatement(stmt);
            
            SQLUtil.SafeSetInteger(pStmt, 1, idPreferenceKeys);

            ResultSet rs = pStmt.executeQuery();

            if (rs.next()) {
                SetPreferenceKeysFromResultSet(prefKeys, rs);
            }

            rs.close();
            pStmt.close();

            return prefKeys;
        } catch (Exception ex) {
            Logger.getLogger(PreferenceKeys.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("PreferenceKeysDAO::GetPreferenceKeysById - " + ex.toString());
            return null;
        }
    }

    public PreferenceKeys GetPreferenceKeysByKey(String key) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        PreferenceKeys prefKeys = new PreferenceKeys();
        try {
            String stmt = "SELECT * FROM " + table + " "
                    + "WHERE `preferenceKey` = ?";

            PreparedStatement pStmt = con.prepareStatement(stmt);
            
            SQLUtil.SafeSetString(pStmt, 1, key);

            ResultSet rs = pStmt.executeQuery();

            if (rs.next()) {
                SetPreferenceKeysFromResultSet(prefKeys, rs);
            }

            rs.close();
            pStmt.close();

            return prefKeys;
        } catch (Exception ex) {
            Logger.getLogger(PreferenceKeys.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("PreferenceKeysDAO::GetPreferenceKeysByKey - " + ex.toString());
            return null;
        }
    }

    public ArrayList<PreferenceKeys> GetAllPreferenceKeys() throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        PreferenceKeys prefKeys;
        try {
            ArrayList<PreferenceKeys> prefKeyList = new ArrayList<PreferenceKeys>();
            String query = "SELECT * FROM " + table;
            PreparedStatement pStmt = con.prepareStatement(query);

            ResultSet rs = pStmt.executeQuery();

            while (rs.next()) {
                prefKeys = new PreferenceKeys();

                SetPreferenceKeysFromResultSet(prefKeys, rs);

                prefKeyList.add(prefKeys);
            }

            rs.close();
            pStmt.close();

            return prefKeyList;
        } catch (Exception ex) {
            Logger.getLogger(PreferenceKeys.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("PreferenceKeysDAO::GetAllPreferenceKeys - " + ex.toString());
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

    private PreferenceKeys SetPreferenceKeysFromResultSet(PreferenceKeys prefKeys, ResultSet rs) throws SQLException {
        prefKeys.setIdPreferenceKeys(rs.getInt("idPreferenceKeys"));
        prefKeys.setPreferenceKey(rs.getString("preferenceKey"));
        prefKeys.setDisplay(rs.getString("display"));
        prefKeys.setEditable(rs.getBoolean("editable"));

        return prefKeys;
    }

    private PreparedStatement SetStatementFromPreferenceKeys(PreferenceKeys prefKeys, PreparedStatement pStmt) throws SQLException {
        SQLUtil.SafeSetString(pStmt, 1, prefKeys.getPreferenceKey());
        SQLUtil.SafeSetString(pStmt, 2, prefKeys.getDisplay());
        SQLUtil.SafeSetBoolean(pStmt, 3, prefKeys.isEditable());

        return pStmt;
    }

    @Override
    public Boolean Delete(Serializable obj)
    {
        PreferenceKeys userGroupPref = (PreferenceKeys) obj;
        return Delete(userGroupPref.getIdPreferenceKeys());
    }
    
    @Override
    public String structureCheck() {
        return DatabaseStructureCheck.structureCheck(fields, table, con);
    }
}
