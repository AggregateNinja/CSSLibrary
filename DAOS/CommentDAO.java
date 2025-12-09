package DAOS;

import DAOS.IDAOS.IStructureCheckable;
import DOS.Comment;
import Database.CheckDBConnection;
import Database.DatabaseSingleton;
import Database.DatabaseStructureCheck;
import Utility.Convert;
import Utility.SQLUtil;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CommentDAO implements IStructureCheckable
{

    private static final String table = DatabaseSingleton.getDatabaseSingleton().getProperties().getBillingSchema() + ".`comments`";

    public static Comment insert(Comment obj)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (obj == null)
        {
            throw new IllegalArgumentException("CommentsDAO::Insert: Received a NULL Comments object");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "INSERT INTO " + table
                + "("
                + "  commentTypeId,"
                + "  eventId,"
                + "  remarkId,"
                + "  comment,"
                //+ "  internal,"
                + "  created,"
                + "  createdById,"
                + "  modifiedById,"
                + "  modified,"
                + "  active"
                + ")"
                + "VALUES (?,?,?,?,NOW(),?,?,?,?)";

        PreparedStatement pStmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        int i = 0;
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getCommentTypeId());
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getEventId());
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getRemarkId());
        SQLUtil.SafeSetString(pStmt, ++i, obj.getComment());
        //SQLUtil.SafeSetInteger(pStmt, ++i, obj.getInternal());
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getCreatedById());
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getModifiedById());
        SQLUtil.SafeSetTimeStamp(pStmt, ++i, Convert.ToSQLDateTime(obj.getModified()));
        SQLUtil.SafeSetBoolean(pStmt, ++i, obj.getActive());

        try
        {
            Integer newId = null;
            pStmt.executeUpdate();
            ResultSet rs = pStmt.getGeneratedKeys();
            if (rs.next())
            {
                newId = rs.getInt(1);
            }
            pStmt.close();
            if (newId == null || newId <= 0)
            {
                throw new NullPointerException("CommentsDAO::Insert: Cannot retrieve generated identifier for inserted row.");
            }
            obj.setIdComments(newId);
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage() + " " + pStmt.toString());
            throw ex;
        }

        return obj;
    }

    public static void update(Comment obj)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (obj == null)
        {
            throw new IllegalArgumentException("CommentsDAO::Update: Received a NULL Comments object.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "UPDATE " + table + " SET "
                + " commentTypeId = ?,"
                + " eventId = ?,"
                + " remarkId = ?,"
                + " comment = ?,"
                //+ " internal = ?,"
                + " modifiedById = ?,"
                + " modified = ?,"
                + " active = ?"
                + " WHERE idComments = " + obj.getIdComments();

        String sqlOutput = "";
        PreparedStatement pStmt = con.prepareStatement(sql);
        try
        {
            int i = 0;
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getCommentTypeId());
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getEventId());
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getRemarkId());
            SQLUtil.SafeSetString(pStmt, ++i, obj.getComment());
            //SQLUtil.SafeSetInteger(pStmt, ++i, obj.getInternal());
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getModifiedById());
            SQLUtil.SafeSetTimeStamp(pStmt, ++i, Convert.ToSQLDateTime(obj.getModified()));
            SQLUtil.SafeSetBoolean(pStmt, ++i, obj.getActive());
            sqlOutput = pStmt.toString();
            pStmt.executeUpdate();
        }
        catch (Exception ex)
        {
            String errorMsg = ex.getMessage() + " " + sqlOutput;
            System.out.println(errorMsg);
            throw new SQLException(errorMsg);
        }
    }

    public static Comment get(Integer id)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (id == null || id <= 0)
        {
            throw new IllegalArgumentException("CommentsDAO::Get: Received a NULL or empty Comments object.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "SELECT * FROM " + table + " WHERE idComments = " + String.valueOf(id);

        PreparedStatement pStmt = con.prepareStatement(sql);

        Comment obj = null;
        try
        {
            ResultSet rs = pStmt.executeQuery();
            if (rs.next())
            {
                obj = ObjectFromResultSet(rs);
            }
        }
        catch (SQLException ex)
        {
            System.out.println(ex.getMessage() + " " + sql);
            throw new SQLException(ex.getMessage() + " " + sql);
        }

        return obj;
    }
    
//    public static List<Comment> getAllForDetailLine(int detailCptCodeId) throws SQLException
//    {
//        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
//        Connection con = dbs.getConnection(true);
//        if (con.isValid(2) == false)
//        {
//            con = CheckDBConnection.Check(dbs, con);
//        }
//        
//        String query = "SELECT * FROM " + table + " WHERE detailCptCodeId = " + detailCptCodeId;
//        PreparedStatement pStmt = con.prepareStatement(table);
//        ResultSet rs = pStmt.executeQuery();
//        
//        List<Comment> comments = new ArrayList<>();
//        
//        while (rs.next())
//        {
//            comments.add(ObjectFromResultSet(rs));
//        }
//        
//        pStmt.close();
//        
//        return null;
//    }

    private static Comment ObjectFromResultSet(ResultSet rs) throws SQLException, NullPointerException
    {
        Comment obj = new Comment();
        obj.setIdComments(rs.getInt("idComments"));
        obj.setCommentTypeId(rs.getInt("commentTypeId"));
        obj.setEventId(rs.getInt("eventId"));
        obj.setRemarkId(rs.getInt("remarkId"));
        obj.setComment(rs.getString("comment"));
        //obj.setInternal(SQLUtil.getInteger(rs, "internal"));
        obj.setCreatedById(rs.getInt("createdById"));
        obj.setModifiedById(rs.getInt("modifiedById"));
        obj.setModified(rs.getDate("modified"));
        obj.setActive(rs.getBoolean("active"));

        return obj;
    }
    
    @Override
    public String structureCheck() {
        String query = "SELECT `comments`.`idComments`,\n"
                + "    `comments`.`commentTypeId`,\n"
                + "    `comments`.`eventId`,\n"
                + "    `comments`.`remarkId`,\n"
                + "    `comments`.`comment`,\n"
                + "    `comments`.`createdById`,\n"
                + "    `comments`.`created`,\n"
                + "    `comments`.`modifiedById`,\n"
                + "    `comments`.`modified`,\n"
                + "    `comments`.`active`\n"
                + "FROM `cssbilling`.`comments` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, Database.DatabaseSingleton.getDatabaseSingleton().getConnection(true));
    }
}
