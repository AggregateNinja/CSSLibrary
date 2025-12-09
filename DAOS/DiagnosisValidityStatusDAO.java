/*
 * Computer Service & Support, Inc.  All Rights Reserved.
 */

package DAOS;

import DAOS.IDAOS.IStructureCheckable;
import DOS.DiagnosisValidityStatus;
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
public class DiagnosisValidityStatusDAO implements IStructureCheckable
{
    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`diagnosisValidityStatus`";
    
    public enum VALIDITY_STATUS
    {
        Valid(1),
        Invalid(2),
        ABN(3),
        Unknown(4);
        
        private final int validityStatusId;
        
        VALIDITY_STATUS(int id)
        {
            validityStatusId = id;
        }
        
        public int getId()
        {
            return validityStatusId;
        }
        
        public static VALIDITY_STATUS getValidityStatus(int validityStatus)
        {
            for (VALIDITY_STATUS v : VALIDITY_STATUS.values())
            {
                if (v.getId() == validityStatus)
                {
                    return v;
                }
            }
            return VALIDITY_STATUS.Unknown;
        }
        
        public static String[] getValidityStatusStrings()
        {
            String[] stringArray = new String[values().length];
            for (int i = 0; i < values().length; i++)
            {
                stringArray[i] = values()[i].toString();
            }
            return stringArray;
        }
    }
    
    public DiagnosisValidityStatusDAO() {}
    
    public DiagnosisValidityStatus getById(int validityTypeId) throws SQLException
    {
        String sql = "SELECT * from " + table + " WHERE idDiagnosisValidityStatus = " + validityTypeId;
        
        PreparedStatement pStmt = con.prepareStatement(sql);
        ResultSet rs = pStmt.executeQuery();
        DiagnosisValidityStatus vs = null;
        if (rs.next())
        {
            vs = new DiagnosisValidityStatus();
            vs = fromRecordSet(vs, rs);
        }
        pStmt.close();
        return vs;
    }
    
    private List<DiagnosisValidityStatus> getAll() throws SQLException
    {
        String sql = "SELECT * FROM " + table;
        PreparedStatement pStmt = con.prepareStatement(sql);
        ResultSet rs = pStmt.executeQuery();
        ArrayList<DiagnosisValidityStatus> validityTypes = new ArrayList<>();
        while (rs.next())
        {
            DiagnosisValidityStatus vs = fromRecordSet(new DiagnosisValidityStatus(), rs);
            validityTypes.add(vs);
        }
        pStmt.close();
        return validityTypes;
    }
    
    private DiagnosisValidityStatus fromRecordSet(DiagnosisValidityStatus vs, ResultSet rs) throws SQLException
    {
        vs.setIdDiagnosisValidityStatus(rs.getInt("idDiagnosisValidityStatus"));
        vs.setType(rs.getString("name"));
        vs.setDescription(rs.getString("description"));
        vs.setPriority(rs.getInt("priority"));
        vs.setActive(rs.getBoolean("active"));
        return vs;
    }
    
    @Override
    public String structureCheck() {
        String query = "SELECT `diagnosisValidityStatus`.`idDiagnosisValidityStatus`,\n"
                + "    `diagnosisValidityStatus`.`name`,\n"
                + "    `diagnosisValidityStatus`.`description`,\n"
                + "    `diagnosisValidityStatus`.`priority`,\n"
                + "    `diagnosisValidityStatus`.`active`\n"
                + "FROM `css`.`diagnosisValidityStatus` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, con);
    }
}
