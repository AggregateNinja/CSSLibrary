package DAOS;

import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IStructureCheckable;
import DOS.MicroComment;
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

public class MicroCommentDAO implements DAOInterface, IStructureCheckable
{
    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`microComments`";
    private final ArrayList<String> fields = new ArrayList<>();
    
    public MicroCommentDAO()
    {
        fields.add("microOrderId");
        fields.add("microOrganismId");
        fields.add("microInstrumentCommentId");
        fields.add("comment");
        fields.add("commentTypeId");
        fields.add("invalidated");
    }

    public MicroComment GetByID(int id)
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
            MicroComment comment = null;
            
            String query = "SELECT * FROM " + table +
                    " WHERE `id` = " + id;
            
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            if (rs.next())
            {
                comment = setFromResultset(comment, rs);
            }
            
            rs.close();
            stmt.close();
            
            return comment;
        }
        catch (SQLException ex)
        {
            System.out.println("MicroInstrumentCommentDAO::GetByID: Error getting " +
                "for id " + id);            
            return null;
        }
    }
    
    public ArrayList<MicroComment> GetAllActiveByMicroOrderId(int microOrderId)
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
           
        ArrayList<MicroComment> comments = new ArrayList<>();
        MicroComment comment = null;
        try
        {
            String query = "SELECT * FROM " + table
                    + " WHERE `invalidated` = 0 AND "
                    + " `microOrderId` = " + microOrderId;
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next())
            {
                comment = new MicroComment();
                comment = setFromResultset(comment,rs);
                if (comment != null && comment.getId() != null && comment.getId() > 0)
                {
                    comments.add(comment);
                }
            }
        }
        catch (SQLException ex)
        {
            String msg = "MicroCommentDAO::GetAllActiveByMicroOrderId:Unable to retrieve active microComments for microOrder " + microOrderId;
            System.out.println(msg);
            return null;
        }
        
        return comments;
    }
    
    public boolean Invalidate(int microCommentId)
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
            return false;
        }
        
        try
        {
            String query = "UPDATE " + table + " SET invalidated = 1 WHERE id = ?";
            PreparedStatement pStmt = con.prepareStatement(query);
            pStmt.setInt(1, microCommentId);
            ResultSet rs = pStmt.executeQuery(query);
            rs.close();
            pStmt.close();
            return true;
            
        }
        catch (SQLException ex)
        {
            String msg = "Unable to invalidate comment for microCommentId " + microCommentId;
            System.out.println(msg);
            return false;
        }
    }
     
    public Integer InsertGetId(MicroComment comment)
    {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            return null;
        }
        
        try
        {
            String sql = GenerateInsertStatement(fields);
            PreparedStatement pStmt = con.prepareStatement(sql,
                                      Statement.RETURN_GENERATED_KEYS);      
            setStatement(comment, pStmt);            
            int affectedRows = pStmt.executeUpdate();
            
            if (affectedRows == 0)
            {
                throw new SQLException("Insert failed, no rows affected.");
            }
            
            Integer newId;
            
            try (ResultSet generatedKeys = pStmt.getGeneratedKeys()) 
            {
                if (generatedKeys.next())
                {
                    newId = generatedKeys.getInt(1);
                }
                else
                {
                    throw new SQLException("Insert failed, no ID obtained.");
                }
            }
            pStmt.close();
            
            return newId;
        } catch (SQLException ex)
        {
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }
    
    private MicroComment setFromResultset(
            MicroComment comment,
            ResultSet rs) throws SQLException
    {
        comment.setId(rs.getInt("id"));
        comment.setMicroOrderId(rs.getInt("microOrderId"));
        comment.setMicroOrganismId(rs.getInt("microOrganismId"));
        comment.setMicroInstrumentCommentId(rs.getInt("microInstrumentCommentId"));
        comment.setComment(rs.getString("comment"));
        comment.setCommentTypeId(rs.getInt("commentTypeId"));
        comment.setInvalidated(rs.getBoolean("invalidated"));
        return comment;
    }
    
    private PreparedStatement setStatement(MicroComment obj, PreparedStatement pStmt) throws SQLException
    {
        int i = 0;
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getMicroOrderId());
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getMicroOrganismId());
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getMicroInstrumentCommentId());
        SQLUtil.SafeSetString(pStmt, ++i, obj.getComment());
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getCommentTypeId());
        SQLUtil.SafeSetBoolean(pStmt, ++i, obj.isInvalidated());
        return pStmt;
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
    public Boolean Insert(Serializable obj)
    {
        if (obj == null)
        {
            String msg = "MicroCommentDAO::Insert: Receivied a NULL object to insert!";
            System.out.println(msg);
            return false;
        }
        
        MicroComment comment = null;
        try
        {
            comment = (MicroComment)obj;
            if (comment == null) throw new Exception();
        }
        catch (Exception ex)
        {
            String msg = "MicroCommentDAO::Insert: Unable to cast parameter object to a MicroComment!";
            System.out.println(msg);
            return false;
        }
        
        Integer newId = InsertGetId(comment);
        
        return (newId != null && newId > 0);        
    }

    @Override
    public Boolean Update(Serializable obj)
    {
        if (obj == null)
        {
            String msg = "MicroCommentDAO::Insert: Receivied a NULL object to update!";
            System.out.println(msg);
            return false;
        }
        
        MicroComment comment = null;
        try
        {
            comment = (MicroComment)obj;
            if (comment == null) throw new Exception();
        }
        catch (Exception ex)
        {
            String msg = "MicroCommentDAO::Update: Unable to cast parameter object to a MicroComment!";
            System.out.println(msg);
            return false;
        }
        
        try
        {
            String stmt = GenerateUpdateStatement(fields)
                    + " WHERE `id` = " + comment.getId();
            PreparedStatement pStmt = con.prepareStatement(stmt);
            pStmt = setStatement(comment, pStmt);
            pStmt.executeUpdate();
            pStmt.close();
            return true;
        }
        catch (SQLException ex)
        {
            String msg = "MicroCommentDAO::Update: SQL Exception on update";
            System.out.println(msg);
            return false;
        }
        
    }

    @Override
    public Boolean Delete(Serializable obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Serializable getByID(Integer ID) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public String structureCheck() {
        return DatabaseStructureCheck.structureCheck(fields, table, con);
    }
}
