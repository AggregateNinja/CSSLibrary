
package DAOS;

import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IStructureCheckable;
import DOS.RefRes;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.SQLUtil;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static Utility.SQLUtil.createStatement;

public class RefResDAO implements DAOInterface, IStructureCheckable
{
    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final ArrayList<String> fields = new ArrayList<>();
    
    public RefResDAO()
    {
        fields.add("orderId");
        fields.add("accession");
        fields.add("resIdentifier");
        fields.add("ordIdentifier");
        fields.add("ordTestNum");
        fields.add("resTestNum");
        fields.add("name");
        fields.add("result");
        fields.add("comment");
        fields.add("units");
        fields.add("range");
        fields.add("postedDate");
        fields.add("user");
        fields.add("posted");
        fields.add("fileName");
        fields.add("code");
        fields.add("isFinal");
        fields.add("isInvalidated");
        fields.add("abnormalFlag");        
    }
    
    public void Update(String tableName, RefRes refRes)
            throws IllegalArgumentException, SQLException
    {
        if (tableName == null || tableName.isEmpty())
        {
            throw new IllegalArgumentException("RefResDAO::Update:"
                    + "Received NULL or empty table name");
        }
        
        if (refRes == null || refRes.getIdRefResult() == null ||
                refRes.getIdRefResult() == 0)
        {
            throw new IllegalArgumentException("RefResDAO::Update:"
                    + "Received NULL or empty refRes object");
        }
        
        if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);
        
        
        String sql = GenerateUpdateStatement(tableName, fields) + 
                " WHERE `idrefresult` = " + refRes.getIdRefResult();
        
        PreparedStatement pStmt = con.prepareStatement(sql);
        SetStatementFromObject(pStmt, refRes);
        pStmt.executeUpdate();
        pStmt.close();

    }

    /**
     * Given a refRes table, accession, and identifier for the reference lab,
     *   sets the refRes row as posted.
     * @param table
     * @param accession
     * @param identifier The identifier column of the refRes table (reference lab)
     * @param user
     * @return 
     */
    public boolean SetRowPosted(String table, String accession, String identifier, String user)
    {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }

        //INST.getResTable(), ord.getAccession(), testXref, String.valueOf(user))
        // name is cross refRes identifier field
        try {
            
            String query = "UPDATE `" + table + "` SET"
                    + " `user` = ?,"
                    + " `posted` = " + true + ", "
                    + " `postedDate` = NOW()"
                    + " WHERE `accession` LIKE ?"
                    + " AND `identifier` = ?";

            PreparedStatement pStmt = createStatement(con, query, user, accession, identifier);//con.prepareStatement(query);
            pStmt.executeUpdate();

            pStmt.close();

            return true;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return false;
        }
    }
    
    public String GetComment(String table, int idrefRes)
    {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        String comment = "";
        //INST.getResTable(), ord.getAccession(), testXref, String.valueOf(user))
        // name is cross refRes identifier field
        try {
            
            // PostedDate is being set by the db for the current timestamp.
            String query = "SELECT comment from " + table
                    + " WHERE idrefresult = " + idrefRes;

            PreparedStatement stmt = con.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            if (rs.next())
            {
                comment = rs.getString(1);
            }

            stmt.close();

            return comment;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }        
    }
    
    public RefRes GetRefResByID(String tableName, int idrefResult)
            throws IllegalArgumentException, SQLException
    {
        if (tableName == null || tableName.isEmpty())
        {
            throw new IllegalArgumentException("RefResDAO::GetRefresultByID:"
                    + "Received a NULL or blank ref res table name");
        }
        
        if (idrefResult < 1)
        {
            throw new IllegalArgumentException("RefResDAO::GetRefresultByID: "
                    + "Received an invalid idrefresult: " + idrefResult);
        }
        
        String sql = "SELECT * FROM `" + tableName + "` WHERE idrefresult = " + idrefResult;
        
        PreparedStatement pStmt = con.prepareStatement(sql);
        ResultSet rs = pStmt.executeQuery();
        
        RefRes rr = null;
        if (rs.next())
        {
            rr = RefResultFromResultSet(tableName, rs);
        }
        return rr;
    }
    
    private RefRes RefResultFromResultSet(String tableName, ResultSet rs) throws SQLException
    {
        RefRes rr = new RefRes(tableName);
        rr.setIdRefResult(rs.getInt("idrefresult"));
        rr.setOrderId(rs.getInt("orderId"));
        rr.setAccession(rs.getString("accession"));
        rr.setResIdentifier(rs.getString("resIdentifier"));
        rr.setOrdIdentifier(rs.getString("ordIdentifier"));
        rr.setOrdTestNum(rs.getInt("ordTestNum"));
        rr.setResTestNum(rs.getInt("resTestNum"));
        rr.setName(rs.getString("name"));
        rr.setResult(rs.getString("result"));
        rr.setComment(rs.getString("comment"));
        rr.setUnits(rs.getString("units"));
        rr.setRange(rs.getString("range"));
        rr.setPostedDate(rs.getDate("postedDate"));
        rr.setUser(rs.getString("user"));
        rr.setPosted(rs.getBoolean("posted"));
        rr.setFileName(rs.getString("fileName"));
        rr.setCode(rs.getString("code"));
        rr.setIsFinal(rs.getBoolean("isFinal"));
        rr.setIsInvalidated(rs.getBoolean("isInvalidated"));
        rr.setAbnormalFlag(rs.getString("abnormalFlag"));
        return rr;
    }
    
    private PreparedStatement SetStatementFromObject(PreparedStatement pStmt, RefRes rr)
            throws SQLException
    {
        int i = 0;
        SQLUtil.SafeSetInteger(pStmt, ++i, rr.getOrderId());
        SQLUtil.SafeSetString(pStmt, ++i, rr.getAccession());
        SQLUtil.SafeSetString(pStmt, ++i, rr.getResIdentifier());
        SQLUtil.SafeSetString(pStmt, ++i, rr.getOrdIdentifier());
        SQLUtil.SafeSetInteger(pStmt, ++i, rr.getOrdTestNum());
        SQLUtil.SafeSetInteger(pStmt, ++i, rr.getResTestNum());
        SQLUtil.SafeSetString(pStmt, ++i, rr.getName());
        SQLUtil.SafeSetString(pStmt, ++i, rr.getResult());
        SQLUtil.SafeSetString(pStmt, ++i, rr.getComment());
        SQLUtil.SafeSetString(pStmt, ++i, rr.getUnits());
        SQLUtil.SafeSetString(pStmt, ++i, rr.getRange());
        SQLUtil.SafeSetTimeStamp(pStmt, ++i, rr.getPostedDate());
        SQLUtil.SafeSetString(pStmt, ++i, rr.getUser());
        SQLUtil.SafeSetBoolean(pStmt, ++i, rr.isPosted());
        SQLUtil.SafeSetString(pStmt, ++i, rr.getFileName());
        SQLUtil.SafeSetString(pStmt, ++i, rr.getCode());
        SQLUtil.SafeSetBoolean(pStmt, ++i, rr.isFinal());
        SQLUtil.SafeSetBoolean(pStmt, ++i, rr.isInvalidated());
        SQLUtil.SafeSetString(pStmt, ++i, rr.getAbnormalFlag());
        
        return pStmt;
        
    }
    
    private String GenerateUpdateStatement(String tableName, ArrayList<String> fields)
    {
        String stmt = "UPDATE " + tableName + " SET";
        for (int i = 0; i < fields.size(); ++i) {
            stmt += " `" + fields.get(i) + "` = ?";
            if (i != fields.size() - 1) {
                stmt += ",";
            }
        }
        return stmt;
    }    
    
    @Override
    public Boolean Insert(Serializable obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Boolean Update(Serializable obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
        return DatabaseStructureCheck.structureCheck(fields, "refRes_1", con);
    }

}
