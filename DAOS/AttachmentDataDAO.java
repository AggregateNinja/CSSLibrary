package DAOS;

import DAOS.IDAOS.IStructureCheckable;
import DOS.AttachmentData;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.SQLUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class AttachmentDataDAO implements IStructureCheckable
{
    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`attachmentData`";
    
    private final ArrayList<String> fields = new ArrayList<>();
    
    public AttachmentDataDAO()
    {
        fields.add("id");
        fields.add("attachment");
    }
    
    public AttachmentData GetAttachmentDataById(int id) throws SQLException
    {
        if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);
        
        String sql = "SELECT * FROM " + table + " WHERE id = " + id;
        AttachmentData attachment = null;
        PreparedStatement pStmt = con.prepareStatement(sql);
        ResultSet rs = pStmt.executeQuery();
        if (rs.next())
        {
            attachment = GetAttachmentDataFromResultSet(rs);
        }
        return attachment;
    }
    
    public int InsertAttachmentDataGetId(AttachmentData attachment)
            throws SQLException, IllegalArgumentException
    {
        if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);
        String sql = GenerateInsertStatement(fields);
        PreparedStatement pStmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        
        pStmt = SetStatementFromObject(pStmt, attachment);
        int affectedRows = pStmt.executeUpdate();
        if (affectedRows == 0) throw new SQLException("AttachmentDataDAO::InsertAttachmentDataGetId: Insert failed (affected rows 0)");
        
        Integer attachmentDataId;
        try (ResultSet generatedKeys = pStmt.getGeneratedKeys())
        {
            if (generatedKeys.next())
            {
                attachmentDataId = generatedKeys.getInt(1);
            }
            else
            {
                throw new SQLException("AttachmentDataDAO::InsertAttachmentDataGetId: Insert failed, no unique ID obtained from insert.");
            }
        }
        return attachmentDataId;
    }
    
    public void UpdateAttachment(AttachmentData attachment)
            throws SQLException, IllegalArgumentException
    {
        if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);
        String sql = GenerateUpdateStatement(fields);
        sql += " WHERE id = " + attachment.getId();
        PreparedStatement pStmt = con.prepareStatement(sql);
        pStmt = SetStatementFromObject(pStmt, attachment);
        pStmt.execute(sql);
    }
    
    private PreparedStatement SetStatementFromObject(PreparedStatement pStmt, AttachmentData attachment)
            throws SQLException
    {
        int i = 0;
        SQLUtil.SafeSetInteger(pStmt, ++i, attachment.getId());
        SQLUtil.SafeSetBytes(pStmt, ++i, attachment.getAttachment());
        return pStmt;
    }
    
    private AttachmentData GetAttachmentDataFromResultSet(ResultSet rs) throws SQLException
    {
        AttachmentData attachment = new AttachmentData();
        attachment.setId(rs.getInt("id"));
        attachment.setAttachment(rs.getBytes("attachment"));
        return attachment;
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
    public String structureCheck() {
        return DatabaseStructureCheck.structureCheck(fields, table, con);
    }
}
