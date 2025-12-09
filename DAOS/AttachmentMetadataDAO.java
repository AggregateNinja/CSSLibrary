/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DAOS;

import DAOS.IDAOS.IStructureCheckable;
import DOS.AttachmentMetadata;
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
public class AttachmentMetadataDAO implements IStructureCheckable
{

    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`attachmentMetadata`";
    
    private final ArrayList<String> fields = new ArrayList<>();
    
    public AttachmentMetadataDAO()
    {
        fields.add("id");
        fields.add("attachmentDataId");
        fields.add("fileName");
        fields.add("extension");
        fields.add("path");
        fields.add("md5");
        fields.add("fileSizeBytes");
    }
    
    public AttachmentMetadata GetAttachmentMetadataById(int id) throws SQLException
    {
        if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);
        
        String sql = "SELECT * FROM " + table + " WHERE id = " + id;
        AttachmentMetadata attachment = null;
        PreparedStatement pStmt = con.prepareStatement(sql);
        ResultSet rs = pStmt.executeQuery();
        if (rs.next())
        {
            attachment = GetAttachmentMetadataFromResultSet(rs);
        }
        return attachment;
    }
    
    public int InsertAttachmentMetadataGetId(AttachmentMetadata attachment)
            throws SQLException, IllegalArgumentException
    {
        if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);
        String sql = GenerateInsertStatement(fields);
        PreparedStatement pStmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        
        pStmt = SetStatementFromObject(pStmt, attachment);
        int affectedRows = pStmt.executeUpdate();
        if (affectedRows == 0) throw new SQLException("AttachmentDAO::InsertAttachmentGetId: Insert failed (affected rows 0)");
        
        Integer attachmentId;
        try (ResultSet generatedKeys = pStmt.getGeneratedKeys())
        {
            if (generatedKeys.next())
            {
                attachmentId = generatedKeys.getInt(1);
            }
            else
            {
                throw new SQLException("AttachmentDAO::InsertAttachmentGetId: Insert failed, no unique ID obtained from insert.");
            }
        }
        return attachmentId;
    }
    
    public void UpdateAttachmentMetadata(AttachmentMetadata attachment)
            throws SQLException, IllegalArgumentException
    {
        if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);
        String sql = GenerateUpdateStatement(fields);
        sql += " WHERE id = " + attachment.getId();
        PreparedStatement pStmt = con.prepareStatement(sql);
        pStmt = SetStatementFromObject(pStmt, attachment);//, false);
        pStmt.execute(sql);
    }
    
    private PreparedStatement SetStatementFromObject(PreparedStatement pStmt, AttachmentMetadata attachment)
            throws SQLException
    {
        int i = 0;
        SQLUtil.SafeSetInteger(pStmt, ++i, attachment.getId());
        SQLUtil.SafeSetInteger(pStmt, ++i, attachment.getAttachmentDataId());
        SQLUtil.SafeSetString(pStmt, ++i, attachment.getFileName());
        SQLUtil.SafeSetString(pStmt, ++i, attachment.getExtension());
        SQLUtil.SafeSetString(pStmt, ++i, attachment.getPath());
        SQLUtil.SafeSetString(pStmt, ++i, attachment.getMd5());
        SQLUtil.SafeSetInteger(pStmt, ++i, attachment.getFileSizeBytes());
        return pStmt;
    }
    
    private AttachmentMetadata GetAttachmentMetadataFromResultSet(ResultSet rs) throws SQLException
    {
        AttachmentMetadata attachment = new AttachmentMetadata();
        attachment.setId(rs.getInt("id"));
        attachment.setAttachmentDataId(rs.getInt("attachmentDataId"));
        attachment.setFileName(rs.getString("fileName"));
        attachment.setExtension(rs.getString("extension"));
        attachment.setPath(rs.getString("path"));
        attachment.setMd5(rs.getString("md5"));
        attachment.setFileSizeBytes(rs.getInt("fileSizeBytes"));
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
