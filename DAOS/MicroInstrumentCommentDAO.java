package DAOS;


import DAOS.IDAOS.IStructureCheckable;
import DOS.MicroInstrumentComment;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.SQLUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author TomR
 */
public class MicroInstrumentCommentDAO implements IStructureCheckable
{
    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`microInstrumentComments`";
    /**
     * All fields except unique identifier
     */
    private final ArrayList<String> fields = new ArrayList<>();

    public MicroInstrumentCommentDAO()
    {
        // Excluding unique database identifier
        fields.add("instrumentId");
        fields.add("comment");
        fields.add("commentCode");
        fields.add("active");
        fields.add("associationCode");
    }
    
    /**
     * Provided an order ID, returns all answers for the given tests.
     * @param id MicroInstrumentComment unique database identifier
     * @return MicroInstrumentComment object
     */
    public MicroInstrumentComment GetByID(int id)
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
            MicroInstrumentComment comment = null;
            
            String query = "SELECT * FROM " + table +
                    " WHERE `id` = " + id;
            
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            if (rs.next())
            {
                comment = new MicroInstrumentComment();
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
    
    /**
     * Returns the currently active microbiology instrument code 
     * @param code Instrument's unique code for this comment
     * @param associationCode What type of object this comment is associated with (depends on instrument; can be null)
     * @param instrumentId Unique database identifier of the instrument
     * @return MicroInstrumentComment, or NULL if not found
     */
    public MicroInstrumentComment GetActiveByCommentCodeAssociationCode(String code, String associationCode, int instrumentId)
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
            MicroInstrumentComment comment = null;
            
            String query = "SELECT * FROM " + table
                    + " WHERE `commentCode` = ?"
                    + " AND `associationCode` = ?"
                    + " AND active = 1";
            
            PreparedStatement pStmt = con.prepareStatement(query);
            pStmt.setString(1, code);
            pStmt.setString(2, associationCode);
            System.out.println(pStmt.toString());
            ResultSet rs = pStmt.executeQuery();

            if (rs.next())
            {
                comment = new MicroInstrumentComment();
                comment = setFromResultset(comment, rs);
            }
            
            rs.close();
            pStmt.close();
            
            return comment;
        }
        catch (SQLException ex)
        {
            System.out.println("MicroInstrumentCommentDAO::GetActiveByCommentCode: Error getting " +
                "for code " + code + ", instrumentId " + instrumentId);
            return null;
        }
    }
    
    public boolean Deactivate(MicroInstrumentComment comment)
    {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            return false;
        }
        
        try
        {
            String query = "UPDATE " + table + " SET active = 0 WHERE id = ?";

            PreparedStatement pStmt = con.prepareStatement(query);
            pStmt.setInt(1, comment.getId());
            ResultSet rs = pStmt.executeQuery();
            rs.close();
            pStmt.close();
            return true;
        }
        catch (SQLException ex)
        {
            String id = "[null]";
            if (comment != null && comment.getId() != null && comment.getId() > 0)
            {
                id = comment.getId().toString();
            }
            System.out.println("MicroInstrumentCommentDAO::Deactivate: Error processing " +
                "id " + id);
            return false;
        }
    }
    
    public Integer InsertGetId(MicroInstrumentComment comment)
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
    
    private MicroInstrumentComment setFromResultset(
            MicroInstrumentComment instrumentComment,
            ResultSet rs) throws SQLException
    {
        instrumentComment.setId(rs.getInt("id"));
        instrumentComment.setInstrumentId(rs.getInt("instrumentId"));
        instrumentComment.setComment(rs.getString("comment"));
        instrumentComment.setCommentCode(rs.getString("commentCode"));
        instrumentComment.setActive(rs.getBoolean("active"));
        instrumentComment.setAssociationCode(rs.getString("associationCode"));
        return instrumentComment;
    }
    
    private PreparedStatement setStatement(MicroInstrumentComment obj, PreparedStatement pStmt) throws SQLException
    {
        int i = 0;
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getInstrumentId());
        SQLUtil.SafeSetString(pStmt, ++i, obj.getComment());
        SQLUtil.SafeSetString(pStmt, ++i, obj.getCommentCode());
        SQLUtil.SafeSetBoolean(pStmt, ++i, obj.isActive());
        SQLUtil.SafeSetString(pStmt, ++i, obj.getAssociationCode());
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
    public String structureCheck() {
        return DatabaseStructureCheck.structureCheck(fields, table, con);
    }
}
