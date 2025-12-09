package DAOS;

import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IStructureCheckable;
import DOS.QcResults;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * @date:   Jun 10, 2014
 * @author: Keith Maggio <keith@csslis.com>
 * 
 * @project:  
 * @package: DAOS
 * @file name: QcResultsDAO.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc.  All Rights Reserved
 */

public class QcResultsDAO  implements DAOInterface, IStructureCheckable
{
    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`qcResults`";
    private final ArrayList<String> fields = new ArrayList<String>();
    
    public QcResultsDAO()
    {
        fields.add("test");
        fields.add("level");
        fields.add("idqcLot");
        fields.add("result");
        fields.add("action");
        fields.add("forced");
        fields.add("comment");
        fields.add("shift");
        fields.add("idInst");
        fields.add("dateRun");
        fields.add("approvedBy");
        fields.add("createdDate");
        fields.add("approvedDate");
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
        catch (SQLException sex)
        {
            System.out.println(sex.toString());
            return false;
        }
        try
        {
            QcResults qcres = (QcResults)obj;
            String stmt = GenerateInsertStatement(fields);
            PreparedStatement pStmt = con.prepareStatement(stmt);
            SetStatement(qcres, pStmt);
            pStmt.executeUpdate();
            
            pStmt.close();
            
            return true;
        }
        catch (Exception ex)
        {
            String message = ex.toString();
            //TODO:  Add logging for failures
            System.out.println(message);
            return false;
        }
    }
    
    public Integer InsertGetId(QcResults res) {
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
            return 0;
        }
        try
        {
            String stmt = GenerateInsertStatement(fields);
            PreparedStatement pStmt = con.prepareStatement(stmt, Statement.RETURN_GENERATED_KEYS);
            SetStatement(res, pStmt);
            pStmt.executeUpdate();
            
            ResultSet rs = pStmt.getGeneratedKeys();
            int newId = 0;
            if (rs.next())
            {
                newId = rs.getInt(1);
            }
            
            pStmt.close();
            
            return newId;
        }
        catch (Exception ex)
        {
            String message = ex.toString();
            //TODO:  Add logging for failures
            System.out.println(message);
            return 0;
        }
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
        catch (SQLException sex)
        {
            System.out.println(sex.toString());
            return false;
        }
        try
        {
            QcResults qcres = (QcResults)obj;
            String stmt = GenerateUpdateStatement(fields)
                    + " WHERE `idqcResults` = " + qcres.getIdqcResults();
            PreparedStatement pStmt = con.prepareStatement(stmt);
            
            SetStatement(qcres, pStmt);
            
            pStmt.executeUpdate();
            
            pStmt.close();
            
            return true;
        }
        catch (Exception ex)
        {
            String message = ex.toString();
            //TODO:  Add logging for failures
            System.out.println(message);
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

    @Override
    public Serializable getByID(Integer ID)
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
            QcResults qcres = new QcResults();
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + " WHERE `idqcResults` = " + ID;

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next())
            {
                SetQcResultsFromResultSet(qcres, rs);
            }

            rs.close();
            stmt.close();
            
            return qcres;
        }
        catch (Exception ex)
        {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }
    
    public ArrayList<QcResults> GetPreviousRuns(int test, int level, int idqcLot, int InstId, int limit)
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
            ArrayList<QcResults> qcrList = new ArrayList<>();
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + " WHERE `test` = " + test
                    + " AND `level` = " + level
                    + " AND `idqcLot` = " + idqcLot
                    + " AND `idInst` = " + InstId
                    + " AND `action` != 'EXCLUDE'"
                    + " ORDER BY `dateRun` DESC"
                    + " LIMIT " + limit;

            ResultSet rs = stmt.executeQuery(query);

            while (rs.next())
            {
                QcResults qcres = new QcResults();
                SetQcResultsFromResultSet(qcres, rs);
                qcrList.add(qcres);
            }

            rs.close();
            stmt.close();
            
            return qcrList;
        }
        catch (Exception ex)
        {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }
    
    public ArrayList<QcResults> GetResultsForReview(int test, java.util.Date from, java.util.Date to, int inst)
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
            ArrayList<QcResults> qcrList = new ArrayList<>();
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + " WHERE `test` = " + test
                    + " AND `idInst` = " + inst
                    + " AND `action` != 'EXCLUDE'"
                    + " AND `dateRun` BETWEEN '"+Convert.ToSQLDateTime(from)+"' AND '"+Convert.ToSQLDateTime(to) + "' "
                    + " ORDER BY `dateRun` ASC";

            ResultSet rs = stmt.executeQuery(query);

            while (rs.next())
            {
                QcResults qcres = new QcResults();
                SetQcResultsFromResultSet(qcres, rs);
                qcrList.add(qcres);
            }

            rs.close();
            stmt.close();
            
            return qcrList;
        }
        catch (Exception ex)
        {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }
    
    public Integer GetQcResultId(int test, int level, int idqcLot, int InstId, int user, Date Approved, Date Created)
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
            ArrayList<QcResults> qcrList = new ArrayList<>();
            Statement stmt = con.createStatement();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            
            String query = "SELECT `idqcResults` FROM " + table
                    + " WHERE `test` = " + test
                    + " AND `level` = " + level
                    + " AND `idqcLot` = " + idqcLot
                    + " AND `idInst` = " + InstId
                    + " AND `approvedBy` = " + user
                    + " AND `approvedDate` = '" + sdf.format(Approved) + "'"
                    + " AND `createdDate` = '" + sdf.format(Created) + "'";

            ResultSet rs = stmt.executeQuery(query);

            Integer id = null;
            if (rs.next())
            {
                id = rs.getInt("idqcResults");
            }

            rs.close();
            stmt.close();
            
            return id;
        }
        catch (Exception ex)
        {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
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

    public QcResults SetQcResultsFromResultSet(QcResults obj, ResultSet rs) throws SQLException
    {
        obj.setIdqcResults(rs.getInt("idqcResults"));
        obj.setTest(rs.getInt("test"));
        obj.setLevel(rs.getInt("level"));
        obj.setIdqcLot(rs.getInt("idqcLot"));
        obj.setResult(rs.getDouble("result"));
        obj.setAction(rs.getString("action"));
        obj.setForced(rs.getBoolean("forced"));
        obj.setComment(rs.getString("comment"));
        obj.setShift(rs.getInt("shift"));
        obj.setIdInst(rs.getInt("idInst"));
        obj.setDateRun(rs.getDate("dateRun"));
        obj.setApprovedBy(rs.getInt("approvedBy"));
        obj.setCreatedDate(rs.getDate("createdDate"));
        obj.setApprovedDate(rs.getDate("approvedDate"));
        return obj;
    }
    
    private PreparedStatement SetStatement(QcResults obj, PreparedStatement pStmt) throws SQLException
    {
        pStmt.setInt(1, obj.getTest());
        pStmt.setInt(2, obj.getLevel());
        pStmt.setInt(3, obj.getIdqcLot());
        pStmt.setDouble(4, obj.getResult());
        SQLUtil.SafeSetString(pStmt, 5, obj.getAction());
        pStmt.setBoolean(6, obj.getForced());
        SQLUtil.SafeSetString(pStmt, 7, obj.getComment());
        pStmt.setInt(8, obj.getShift());
        pStmt.setInt(9, obj.getIdInst());
        SQLUtil.SafeSetTimeStamp(pStmt, 10, obj.getDateRun());
        pStmt.setInt(11, obj.getApprovedBy());
        SQLUtil.SafeSetTimeStamp(pStmt, 12, obj.getCreatedDate());
        SQLUtil.SafeSetTimeStamp(pStmt,13, obj.getApprovedDate());
        return pStmt;
    }
    
    @Override
    public String structureCheck() {
        return DatabaseStructureCheck.structureCheck(fields, table, con);
    }
}
