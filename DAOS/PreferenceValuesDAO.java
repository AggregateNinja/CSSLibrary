package DAOS;

import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IStructureCheckable;
import DOS.PreferenceValues;
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
public class PreferenceValuesDAO implements DAOInterface, IStructureCheckable {

    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`preferenceValues`";

    private final ArrayList<String> fields = new ArrayList<>();

    public PreferenceValuesDAO() {
        fields.add("value");
        fields.add("display");
    }

    @Override
    public Boolean Insert(Serializable obj) {
        PreferenceValues ugPrefValue = (PreferenceValues) obj;
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
            SetStatementFromPreferenceValues(ugPrefValue, pStmt);

            pStmt.executeUpdate();

            pStmt.close();

            return true;
        } catch (SQLException ex) {
            Logger.getLogger(PreferenceValuesDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("PreferenceValuesDAO::Insert - " + ex.toString());
            return false;
        }
    }

    @Override
    public Boolean Update(Serializable obj) {
        PreferenceValues prefValue = (PreferenceValues) obj;
        try {
            try {
                if (con.isClosed()) {
                    con = CheckDBConnection.Check(dbs, con);
                }
            } catch (SQLException sex) {
                System.out.println(sex.toString());
                return false;
            }

            String stmt = GenerateUpdateStatement(fields) + " WHERE `idPreferenceValues` = "
                    + prefValue.getIdPreferenceValues();
            PreparedStatement pStmt = con.prepareStatement(stmt);
            SetStatementFromPreferenceValues(prefValue, pStmt);

            pStmt.executeUpdate();

            pStmt.close();

            return true;
        } catch (SQLException ex) {
            Logger.getLogger(PreferenceValuesDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("PreferenceValuesDAO::Update - " + ex.toString());
            return false;
        }
    }

    public Boolean Delete(int idPreferenceValues)
    {
        int affected = 0;
        try
        {
            String query = "DELETE FROM ? WHERE idPreferenceValues = ?";
            PreparedStatement pStmt = con.prepareStatement(query);
            pStmt.setString(1, table);
            pStmt.setInt(2, idPreferenceValues);
            affected = pStmt.executeUpdate();
        }
        catch (SQLException ex)
        {
            Logger.getLogger(PreferenceKeys.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("PreferenceValuesDAO::Delete - " + ex.toString());
        }
        return (affected > 0);
    }

    @Override
    public Serializable getByID(Integer idPreferenceValues) {
        try{
            return GetPreferenceValuesById(idPreferenceValues);
        } catch (SQLException ex){
            Logger.getLogger(PreferenceValuesDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("PreferenceValuesDAO::getByID - " + ex.toString());
            return false;
        }
    }

    public boolean InsertPreferenceValues(PreferenceValues prefValue) throws SQLException {
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
                    + "`value`, "
                    + "`display`) "
                    + "values (?,?)";

            PreparedStatement pStmt = con.prepareStatement(stmt);

            SQLUtil.SafeSetString(pStmt, 1, prefValue.getValue());
            SQLUtil.SafeSetString(pStmt, 2, prefValue.getDisplay());

            pStmt.executeUpdate();

            pStmt.close();

            return true;
        } catch (Exception ex) {
            Logger.getLogger(PreferenceValuesDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("PreferenceValuesDAO::InsertPreferenceValues - " + ex.toString());
            return false;
        }
    }

    public boolean UpdatePreferenceValues(PreferenceValues prefValue) throws SQLException {
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
                    + "`value` = ?, "
                    + "`display` = ? "
                    + "WHERE `idPreferenceValues` = ?";

            PreparedStatement pStmt = con.prepareStatement(stmt);

            SQLUtil.SafeSetString(pStmt, 1, prefValue.getValue());
            SQLUtil.SafeSetString(pStmt, 2, prefValue.getDisplay());
            SQLUtil.SafeSetInteger(pStmt, 3, prefValue.getIdPreferenceValues());

            pStmt.executeUpdate();

            pStmt.close();

            return true;
        } catch (Exception ex) {
            Logger.getLogger(PreferenceValuesDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("PreferenceValuesDAO::UpdatePreferenceValues - " + ex.toString());
            return false;
        }
    }

    public PreferenceValues GetPreferenceValuesById(int idPreferenceValues) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        PreferenceValues prefValue = new PreferenceValues();
        try {
            String stmt = "SELECT * FROM " + table + " "
                    + "WHERE `idPreferenceValues` = ?";

            PreparedStatement pStmt = con.prepareStatement(stmt);
            
            SQLUtil.SafeSetInteger(pStmt, 1, idPreferenceValues);

            ResultSet rs = pStmt.executeQuery();

            if (rs.next()) {
                SetPreferenceValuesFromResultSet(prefValue, rs);
            }

            rs.close();
            pStmt.close();

            return prefValue;
        } catch (Exception ex) {
            Logger.getLogger(PreferenceValuesDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("PreferenceValuesDAO::GetPreferenceValuesById - " + ex.toString());
            return null;
        }
    }
    
    public PreferenceValues GetPreferenceValuesByValue(String value) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        PreferenceValues prefValue = new PreferenceValues();
        try {
            String stmt = "SELECT * FROM " + table + " "
                    + "WHERE `value` = ?";

            PreparedStatement pStmt = con.prepareStatement(stmt);
            
            SQLUtil.SafeSetString(pStmt, 1, value);

            ResultSet rs = pStmt.executeQuery();

            if (rs.next()) {
                SetPreferenceValuesFromResultSet(prefValue, rs);
            }

            rs.close();
            pStmt.close();

            return prefValue;
        } catch (Exception ex) {
            Logger.getLogger(PreferenceValuesDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("PreferenceValuesDAO::GetPreferenceValuesByValue - " + ex.toString());
            return null;
        }
    }

    public List<PreferenceValues> GetAllPreferenceValues() throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        PreferenceValues prefValue;
        try {
            List<PreferenceValues> prefValueList = new ArrayList<PreferenceValues>();
            String query = "SELECT * FROM " + table;
            PreparedStatement pStmt = con.prepareStatement(query);

            ResultSet rs = pStmt.executeQuery();

            while (rs.next()) {
                prefValue = new PreferenceValues();

                SetPreferenceValuesFromResultSet(prefValue, rs);

                prefValueList.add(prefValue);
            }

            rs.close();
            pStmt.close();

            return prefValueList;
        } catch (Exception ex) {
            Logger.getLogger(PreferenceValuesDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("PreferenceValuesDAO::GetAllPreferenceValues - " + ex.toString());
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

    private PreferenceValues SetPreferenceValuesFromResultSet(PreferenceValues prefValue, ResultSet rs) throws SQLException {
        prefValue.setIdPreferenceValues(rs.getInt("idPreferenceValues"));
        prefValue.setValue(rs.getString("value"));
        prefValue.setDisplay(rs.getString("display"));

        return prefValue;
    }

    private PreparedStatement SetStatementFromPreferenceValues(PreferenceValues prefValue, PreparedStatement pStmt) throws SQLException {
        SQLUtil.SafeSetString(pStmt, 1, prefValue.getValue());
        SQLUtil.SafeSetString(pStmt, 2, prefValue.getDisplay());

        return pStmt;
    }

    @Override
    public Boolean Delete(Serializable obj)
    {
        PreferenceValues prefValue = (PreferenceValues) obj;
        return Delete(prefValue.getIdPreferenceValues());
    }
    
    @Override
    public String structureCheck() {
        return DatabaseStructureCheck.structureCheck(fields, table, con);
    }
}
