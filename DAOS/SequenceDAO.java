package DAOS;

import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IStructureCheckable;
import DOS.Sequence;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;

import static Utility.SQLUtil.createStatement;

/**
 * @date: Feb 13, 2014
 * @author: Keith Maggio <keith@csslis.com>
 *
 * @project: CSSLibrary
 * @package: DAOS
 * @file name: Sequence.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc. All Rights Reserved
 */
public class SequenceDAO implements DAOInterface, IStructureCheckable {

    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`sequence`";
    /**
     * All fields except ID
     */
    private final ArrayList<String> fields = new ArrayList<String>();

    public SequenceDAO() {
        fields.add("tag");
        fields.add("iteration");
    }

    public boolean InsertSequence(Sequence seq) throws SQLException {
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


            SetStatementFromSequence(seq, pStmt);

            pStmt.executeUpdate();

            pStmt.close();

            return true;
        } catch (Exception ex) {
            String message = ex.toString();
            //TODO:  Add logging for failures
            System.out.println(message);
            return false;
        }
    }

    public boolean UpdateSequence(Sequence seq) throws SQLException {
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
                    + " WHERE `idsequence` = " + seq.getIdSequence();

            PreparedStatement pStmt = con.prepareStatement(stmt);

            SetStatementFromSequence(seq, pStmt);

            pStmt.executeUpdate();

            pStmt.close();

            return true;
        } catch (Exception ex) {
            String message = ex.toString();
            //TODO:  Add exception handleing
            System.out.println(message);
            return false;
        }
    }

    public Sequence GetSequence(int ID) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        try {
            Sequence seq = new Sequence();
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + " WHERE `idsequence` = " + ID;

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                SetSequenceFromResultSet(seq, rs);
            }

            rs.close();
            stmt.close();

            return seq;
        } catch (Exception ex) {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }

    public Sequence GetSequence(String Tag) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        try {
            Sequence seq = new Sequence();
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + " WHERE `tag` = '" + Tag + "'";

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                SetSequenceFromResultSet(seq, rs);
            }

            rs.close();
            stmt.close();

            return seq;
        } catch (Exception ex) {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }

    public Integer GetSequenceAndIncrement(String Tag) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        try {
            Integer retVal = null;
            Sequence seq = new Sequence();
            PreparedStatement stmt = null; //con.createStatement();

            String query = "SELECT * FROM " + table
                    + " WHERE `tag` = ?";

            stmt = createStatement(con, query, Tag);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                SetSequenceFromResultSet(seq, rs);
                retVal = seq.getIteration();
            }

            rs.close();
            stmt.close();

            seq.setIteration(seq.getIteration() + 1);

            if (UpdateSequence(seq) == false) {
                retVal = null;
            }

            return retVal;
        } catch (Exception ex) {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }

    private Sequence SetSequenceFromResultSet(Sequence obj, ResultSet rs) throws SQLException {
        obj.setIdSequence(rs.getInt("idsequence"));
        obj.setTag(rs.getString("tag"));
        obj.setIteration(rs.getInt("iteration"));

        return obj;
    }

    private PreparedStatement SetStatementFromSequence(Sequence obj, PreparedStatement pStmt) throws SQLException {
        pStmt.setString(1, obj.getTag());
        pStmt.setInt(2, obj.getIteration());
        return pStmt;
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Boolean Update(Serializable obj)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Boolean Delete(Serializable obj)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Serializable getByID(Integer ID)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public String structureCheck() {
        return DatabaseStructureCheck.structureCheck(fields, table, con);
    }
}
