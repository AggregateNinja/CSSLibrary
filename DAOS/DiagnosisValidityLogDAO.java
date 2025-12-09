/*
 * Computer Service & Support, Inc.  All Rights Reserved.
 */

package DAOS;

import DAOS.IDAOS.IStructureCheckable;
import DOS.DiagnosisValidityLookupLog;
import Database.DatabaseStructureCheck;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Rob
 */
public class DiagnosisValidityLogDAO implements IStructureCheckable
{
    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`diagnosisValidityLog`";
    
    public DiagnosisValidityLogDAO() {}
    
    public DiagnosisValidityLookupLog getById(int validityId) throws SQLException
    {
        String sql = "SELECT * from " + table + " WHERE idDiagnosisValidityLog = " + validityId;
        
        PreparedStatement pStmt = con.prepareStatement(sql);
        ResultSet rs = pStmt.executeQuery();
        DiagnosisValidityLookupLog dvLog = null;
        if (rs.next())
        {
            dvLog = new DiagnosisValidityLookupLog();
            dvLog = fromRecordSet(dvLog, rs);
        }
        return dvLog;
    }
    
    private List<DiagnosisValidityLookupLog> getAll() throws SQLException
    {
        String sql = "SELECT * FROM " + table;
        PreparedStatement pStmt = con.prepareStatement(sql);
        ResultSet rs = pStmt.executeQuery();
        ArrayList<DiagnosisValidityLookupLog> validityLogs = new ArrayList<>();
        while (rs.next())
        {
            DiagnosisValidityLookupLog dvLog = fromRecordSet(new DiagnosisValidityLookupLog(), rs);
            validityLogs.add(dvLog);
        }
        pStmt.close();
        return validityLogs;
    }
    
    private DiagnosisValidityLookupLog fromRecordSet(DiagnosisValidityLookupLog dvLog, ResultSet rs) throws SQLException
    {
        dvLog.setIdDiagnosisValidityLog(rs.getInt("idDiagnosisValidityLog"));
        dvLog.setIdDiagnosisValidityLookup(rs.getInt("idDiagnosisValidityLookup"));
        dvLog.setAction(rs.getString("action"));
        dvLog.setField(rs.getString("field"));
        dvLog.setPreValue(rs.getString("preValue"));
        dvLog.setPostValue(rs.getString("postValue"));
        dvLog.setDescription(rs.getString("description"));
        dvLog.setPerformedByUserId(rs.getInt("performedByUserId"));
        dvLog.setDate(rs.getTimestamp("date"));
        return dvLog;
    }
    
    @Override
    public String structureCheck() {
        String query = "SELECT `diagnosisValidityLog`.`id`,\n"
                + "    `diagnosisValidityLog`.`idDiagnosisValidity`,\n"
                + "    `diagnosisValidityLog`.`action`,\n"
                + "    `diagnosisValidityLog`.`field`,\n"
                + "    `diagnosisValidityLog`.`preValue`,\n"
                + "    `diagnosisValidityLog`.`postValue`,\n"
                + "    `diagnosisValidityLog`.`description`,\n"
                + "    `diagnosisValidityLog`.`performedByUserId`,\n"
                + "    `diagnosisValidityLog`.`date`,\n"
                + "    `diagnosisValidityLog`.`isUserVisible`\n"
                + "FROM `css`.`diagnosisValidityLog` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, con);
    }
}
