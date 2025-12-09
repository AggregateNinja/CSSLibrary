package DAOS;

import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IStructureCheckable;
import DOS.IDOS.BaseLog;
import DOS.OrderEntryLog;
import DOS.ResultPostLog;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.SQLUtil;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @date: Sep 27, 2013
 * @author: Keith Maggio <keith@csslis.com>
 *
 * @project:
 * @package: DAOS
 * @file name: LogDAO.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc. All Rights Reserved
 */
public class LogDAO implements DAOInterface, IStructureCheckable {

    public enum LogTable {

        ResultPost("resultPostLog"),
        OrderEntry("orderEntryLog"),
        AdvancedOrderEntry("advancedOrderEntryLog");
        private final String tableName;

        LogTable(String name) {
            tableName = name;
        }

        @Override
        public String toString() {
            return tableName;
        }
    }
    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);

    public boolean InsertLog(LogTable table, BaseLog log) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }

        try {
            String stmt = "INSERT INTO " + table.toString() + "("
                    + "`action`, "
                    + "`idUser`, "
                    + "`createdDate`, "
                    + "`description`, "
                    + "`idResults`, "
                    + "`idOrders`, "
                    + "`idPatients`, "
                    + "`idSubscriber`, "
                    + "`idTests`, "
                    + "`preRemark`, "
                    + "`newRemark`, "
                    + "`prevClient`, "
                    + "`newClient`, "
                    + "`prevDoctor`, "
                    + "`newDoctor`, "
                    + "`preInsurance`, "
                    + "`newInsurance`, "
                    + "`preSecondInsurance`, "
                    + "`newSecondInsurance`, "
                    + "`preValue`, "
                    + "`newValue`, "
                    + "`prePatient`, "
                    + "`newPatient`, "
                    + "`preSubscriber`, "
                    + "`newSubscriber`) "
                    + "VALUES "
                    + "(?,?,NOW(),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";

            PreparedStatement pStmt = con.prepareStatement(stmt);
            pStmt.setString(1, log.getAction());
            pStmt.setInt(2, log.getIdUser());
            //pStmt.setTimestamp(3, Convert.ToSQLDateTime(log.getCreatedDate()));
            SQLUtil.SafeSetString(pStmt, 3, log.getDescription());
            SQLUtil.SafeSetInteger(pStmt, 4, log.getIdResults());
            SQLUtil.SafeSetInteger(pStmt, 5, log.getIdOrders());
            SQLUtil.SafeSetInteger(pStmt, 6, log.getIdPatients());
            SQLUtil.SafeSetInteger(pStmt, 7, log.getIdSubscriber());
            SQLUtil.SafeSetInteger(pStmt, 8, log.getIdTests());
            SQLUtil.SafeSetInteger(pStmt, 9, log.getPreRemark());
            SQLUtil.SafeSetInteger(pStmt, 10, log.getNewRemark());
            SQLUtil.SafeSetInteger(pStmt, 11, log.getPrevClient());
            SQLUtil.SafeSetInteger(pStmt, 12, log.getNewClient());
            SQLUtil.SafeSetInteger(pStmt, 13, log.getPrevDoctor());
            SQLUtil.SafeSetInteger(pStmt, 14, log.getNewDoctor());
            SQLUtil.SafeSetInteger(pStmt, 15, log.getPreInsurance());
            SQLUtil.SafeSetInteger(pStmt, 16, log.getNewInsurance());
            SQLUtil.SafeSetInteger(pStmt, 17, log.getPreSecondInsurance());
            SQLUtil.SafeSetInteger(pStmt, 18, log.getNewSecondInsurance());
            SQLUtil.SafeSetString(pStmt, 19, log.getPreValue());
            SQLUtil.SafeSetString(pStmt, 20, log.getNewValue());
            SQLUtil.SafeSetInteger(pStmt, 21, log.getPrePatient());
            SQLUtil.SafeSetInteger(pStmt, 22, log.getNewPatient());
            SQLUtil.SafeSetInteger(pStmt, 23, log.getPreSubscriber());
            SQLUtil.SafeSetInteger(pStmt, 24, log.getNewSubscriber());

            pStmt.executeUpdate();

            pStmt.close();

            return true;


        } catch (Exception ex) {
            System.out.println("Failed Adding Log: " + ex.toString());
            return false;
        }

    }
    
    public List<BaseLog> GetByOrderId(LogTable table, int idOrders) throws SQLException
    {
        if (table == null)
        {
            System.out.println("LogDAO::GetByOrderId: NULL table type specified!");
            return null;
        }
        
        if (idOrders < 1)
        {
            System.out.println("LogDAO::GetByOrderId: Received invalid orderId " + idOrders);
            return null;
        }
        
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        
        String sql = "SELECT * "
                    + " FROM " + table.toString()
                    + " WHERE idOrders = " + idOrders;
        
        PreparedStatement pStmt = con.prepareStatement(sql);
        ArrayList<BaseLog> logs = new ArrayList<>();
        ResultSet rs = pStmt.executeQuery();
        while (rs.next())
        {
            BaseLog log = null;
            switch (table)
            {
                case ResultPost:
                    log = new ResultPostLog();
                    ((ResultPostLog)log).setIdresultPostLog(rs.getInt("idresultPostLog"));
                    break;
                case OrderEntry:
                    log = new OrderEntryLog();
                    ((OrderEntryLog)log).setIdorderEntryLog(rs.getInt("idorderEntryLog"));
                    break;
                // Not supported yet
                default:
                    return null;
            }
            
            logs.add(SetCommonFieldsFromResultSet(log, rs));
        }
        
        rs.close();
        pStmt.close();
        
        return logs;
    }
    
    /**
     * NOTE: this does not set the unique identifier, since we don't know which
     *  table it refers to. That must be handled in the calling code
     * @param log
     * @param rs
     * @return
     * @throws SQLException 
     */
    private BaseLog SetCommonFieldsFromResultSet(BaseLog log, ResultSet rs) throws SQLException
    {
        log.setAction(rs.getString("action"));
        log.setIdUser(rs.getInt("idUser"));
        log.setCreatedDate(rs.getDate("createdDate"));
        log.setDescription(rs.getString("description"));
        log.setIdResults(rs.getInt("idResults"));
        log.setIdOrders(rs.getInt("idOrders"));
        log.setIdPatients(rs.getInt("idPatients"));
        log.setIdSubscriber(rs.getInt("idSubscriber"));
        log.setIdTests(rs.getInt("idTests"));
        log.setPreRemark(rs.getInt("preRemark"));
        log.setNewRemark(rs.getInt("newRemark"));
        log.setPrevClient(rs.getInt("prevClient"));
        log.setNewClient(rs.getInt("newClient"));
        log.setPrevDoctor(rs.getInt("prevDoctor"));
        log.setNewDoctor(rs.getInt("newDoctor"));
        log.setPreInsurance(rs.getInt("preInsurance"));
        log.setNewInsurance(rs.getInt("newInsurance"));
        log.setPreSecondInsurance(rs.getInt("preSecondInsurance"));
        log.setNewSecondInsurance(rs.getInt("newSecondInsurance"));
        log.setPreValue(rs.getString("preValue"));
        log.setNewValue(rs.getString("newValue"));
        log.setPrePatient(rs.getInt("prePatient"));
        log.setNewPatient(rs.getInt("newPatient"));
        log.setPreSubscriber(rs.getInt("preSubscriber"));
        log.setNewSubscriber(rs.getInt("newSubscriber"));
        return log;
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

        String resultPostLog = "SELECT `resultPostLog`.`idresultPostLog`,\n"
                + "    `resultPostLog`.`createdDate`,\n"
                + "    `resultPostLog`.`idUser`,\n"
                + "    `resultPostLog`.`action`,\n"
                + "    `resultPostLog`.`description`,\n"
                + "    `resultPostLog`.`idPatients`,\n"
                + "    `resultPostLog`.`idSubscriber`,\n"
                + "    `resultPostLog`.`idOrders`,\n"
                + "    `resultPostLog`.`idResults`,\n"
                + "    `resultPostLog`.`idTests`,\n"
                + "    `resultPostLog`.`preValue`,\n"
                + "    `resultPostLog`.`newValue`,\n"
                + "    `resultPostLog`.`preRemark`,\n"
                + "    `resultPostLog`.`newRemark`,\n"
                + "    `resultPostLog`.`prevClient`,\n"
                + "    `resultPostLog`.`newClient`,\n"
                + "    `resultPostLog`.`prevDoctor`,\n"
                + "    `resultPostLog`.`newDoctor`,\n"
                + "    `resultPostLog`.`preInsurance`,\n"
                + "    `resultPostLog`.`newInsurance`,\n"
                + "    `resultPostLog`.`preSecondInsurance`,\n"
                + "    `resultPostLog`.`newSecondInsurance`,\n"
                + "    `resultPostLog`.`prePatient`,\n"
                + "    `resultPostLog`.`newPatient`,\n"
                + "    `resultPostLog`.`preSubscriber`,\n"
                + "    `resultPostLog`.`newSubscriber`\n"
                + "FROM `css`.`resultPostLog` LIMIT 1";

        String orderEntryLog = "SELECT `orderEntryLog`.`idorderEntryLog`,\n"
                + "    `orderEntryLog`.`createdDate`,\n"
                + "    `orderEntryLog`.`idUser`,\n"
                + "    `orderEntryLog`.`action`,\n"
                + "    `orderEntryLog`.`description`,\n"
                + "    `orderEntryLog`.`idPatients`,\n"
                + "    `orderEntryLog`.`idSubscriber`,\n"
                + "    `orderEntryLog`.`idOrders`,\n"
                + "    `orderEntryLog`.`idResults`,\n"
                + "    `orderEntryLog`.`idTests`,\n"
                + "    `orderEntryLog`.`preValue`,\n"
                + "    `orderEntryLog`.`newValue`,\n"
                + "    `orderEntryLog`.`preRemark`,\n"
                + "    `orderEntryLog`.`newRemark`,\n"
                + "    `orderEntryLog`.`prevClient`,\n"
                + "    `orderEntryLog`.`newClient`,\n"
                + "    `orderEntryLog`.`prevDoctor`,\n"
                + "    `orderEntryLog`.`newDoctor`,\n"
                + "    `orderEntryLog`.`preInsurance`,\n"
                + "    `orderEntryLog`.`newInsurance`,\n"
                + "    `orderEntryLog`.`preSecondInsurance`,\n"
                + "    `orderEntryLog`.`newSecondInsurance`,\n"
                + "    `orderEntryLog`.`prePatient`,\n"
                + "    `orderEntryLog`.`newPatient`,\n"
                + "    `orderEntryLog`.`preSubscriber`,\n"
                + "    `orderEntryLog`.`newSubscriber`\n"
                + "FROM `css`.`orderEntryLog` LIMIT 1";

        String advancedOrderEntryLog = "SELECT `advancedOrderEntryLog`.`idorderEntryLog`,\n"
                + "    `advancedOrderEntryLog`.`createdDate`,\n"
                + "    `advancedOrderEntryLog`.`idUser`,\n"
                + "    `advancedOrderEntryLog`.`action`,\n"
                + "    `advancedOrderEntryLog`.`description`,\n"
                + "    `advancedOrderEntryLog`.`idPatients`,\n"
                + "    `advancedOrderEntryLog`.`idSubscriber`,\n"
                + "    `advancedOrderEntryLog`.`idOrders`,\n"
                + "    `advancedOrderEntryLog`.`idResults`,\n"
                + "    `advancedOrderEntryLog`.`idTests`,\n"
                + "    `advancedOrderEntryLog`.`preValue`,\n"
                + "    `advancedOrderEntryLog`.`newValue`,\n"
                + "    `advancedOrderEntryLog`.`preRemark`,\n"
                + "    `advancedOrderEntryLog`.`newRemark`,\n"
                + "    `advancedOrderEntryLog`.`prevClient`,\n"
                + "    `advancedOrderEntryLog`.`newClient`,\n"
                + "    `advancedOrderEntryLog`.`prevDoctor`,\n"
                + "    `advancedOrderEntryLog`.`newDoctor`,\n"
                + "    `advancedOrderEntryLog`.`preInsurance`,\n"
                + "    `advancedOrderEntryLog`.`newInsurance`,\n"
                + "    `advancedOrderEntryLog`.`preSecondInsurance`,\n"
                + "    `advancedOrderEntryLog`.`newSecondInsurance`,\n"
                + "    `advancedOrderEntryLog`.`prePatient`,\n"
                + "    `advancedOrderEntryLog`.`newPatient`,\n"
                + "    `advancedOrderEntryLog`.`preSubscriber`,\n"
                + "    `advancedOrderEntryLog`.`newSubscriber`\n"
                + "FROM `css`.`advancedOrderEntryLog` LIMIT 1";
        String rplCheck =  DatabaseStructureCheck.structureCheck(resultPostLog, "resultPostLog", con);
        String oelCheck = DatabaseStructureCheck.structureCheck(orderEntryLog, "orderEntryLog", con);
        String aoelCheck = DatabaseStructureCheck.structureCheck(advancedOrderEntryLog, "advancedOrderEntryLog", con);
        String result = "";
        String[] checks = {rplCheck, oelCheck, aoelCheck};
        for (String check : checks) {
            if (check != null) {
                result += check + "\r\n";
            }
        }
        if (result.isEmpty()) {
            return null;
        } else {
            return result;
        }
    }
}
