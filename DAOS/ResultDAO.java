package DAOS;

/**
 * @date: Mar 12, 2012
 * @author: Keith Maggio
 *
 * @project: CSSLibrary
 * @package: DAOS
 * @file name: OrderDAO.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc. All Rights Reserved
 */
import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IResultDAO;
import DAOS.IDAOS.IStructureCheckable;
import DOS.IDOS.BaseResult;
import DOS.Results;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.Convert;
import Utility.SQLUtil;
import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;
//import DAOS.RemarkDAO;

import static Utility.SQLUtil.createStatement;

public class ResultDAO implements IResultDAO, DAOInterface, IStructureCheckable {

    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`results`";

    private final ArrayList<String> fields = new ArrayList<>();

    public ResultDAO() {
        fields.add("idresults");
        fields.add("orderId");
        fields.add("testId");
        fields.add("panelId");
        fields.add("resultNo");
        fields.add("resultText");
        fields.add("resultRemark");
        fields.add("resultChoice");
        fields.add("created");
        fields.add("reportedBy");
        fields.add("dateReported");
        fields.add("isApproved");
        fields.add("approvedDate");
        fields.add("approvedby");
        fields.add("isInvalidated");
        fields.add("invalidatedDate");
        fields.add("invalidatedBy");
        fields.add("isUpdated");
        fields.add("updatedBy");
        fields.add("updatedDate");
        fields.add("isAbnormal");
        fields.add("isHigh");
        fields.add("isLow");
        fields.add("isCIDHigh");
        fields.add("isCIDLow");
        fields.add("textAnswer");
        fields.add("noCharge");
        fields.add("hl7Transmitted");
        fields.add("printAndTransmitted");
        fields.add("pAndTDate");
        fields.add("optional");
    }

    public boolean InsertResult(Results result) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }

        //if(GetResultIdByOrderIdTestId(result.getOrderId(), result.getTestId()) != 0)
        //    return UpdateResult(result);
        String stmt = "INSERT INTO " + table + "("
                + " `orderId`,"
                + " `testId`,"
                + " `panelId`,"
                + " `resultNo`,"
                + " `resultText`,"
                + " `resultRemark`,"
                + " `resultChoice`,"
                + " `created`,"
                + " `reportedBy`,"
                + " `dateReported`,"
                + " `isApproved`,"
                + " `approvedDate`,"
                + " `approvedby`,"
                + " `isInvalidated`,"
                + " `invalidatedDate`,"
                + " `invalidatedBy`,"
                + " `isUpdated`,"
                + " `updatedBy`,"
                + " `updatedDate`,"
                + " `isAbnormal`,"
                + " `isLow`,"
                + " `isHigh`,"
                + " `isCIDLow`,"
                + " `isCIDHigh`,"
                + " `textAnswer`,"
                + " `noCharge`,"
                + " `hl7Transmitted`,"
                + " `printAndTransmitted`,"
                + " `pAndTDate`,"
                + " `optional`)"
                + " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pStmt = null;

        try {

            pStmt = con.prepareStatement(stmt);

            //int debug = 0;
            //if(result.getTestId() == 2677)
            //++debug;
            /*pStmt.setInt(1,remark.getRemarkNo());
             pStmt.setInt(2,remark.getRemarkName());
             pStmt.setString(3,remark.getRemarkAbbr());
             pStmt.setInt(4,remark.getRemarkType());
             pStmt.setBytes(5,remark.getRemarkText());
             pStmt.setInt(6,remark.getIsAbnormal());
             pStmt.setInt(7,remark.getRemarkDepartment());
             pStmt.setInt(8,remark.getNoCharge());*/
            pStmt.setInt(1, result.getOrderId());
            pStmt.setInt(2, result.getTestId());
            SQLUtil.SafeSetInteger(pStmt, 3, result.getPanelId());
            SQLUtil.SafeSetDouble(pStmt, 4, result.getResultNo());
            SQLUtil.SafeSetString(pStmt, 5, result.getResultText());
            SQLUtil.SafeSetInteger(pStmt, 6, result.getResultRemark());
            SQLUtil.SafeSetInteger(pStmt, 7, result.getResultChoice());
            pStmt.setTimestamp(8, Convert.ToSQLDateTime(result.getCreated()));
            SQLUtil.SafeSetInteger(pStmt, 9, result.getReportedBy());
            SQLUtil.SafeSetTimeStamp(pStmt, 10, result.getDateReported());
            SQLUtil.SafeSetBoolean(pStmt, 11, result.getIsApproved());
            SQLUtil.SafeSetTimeStamp(pStmt, 12, result.getApprovedDate());
            SQLUtil.SafeSetInteger(pStmt, 13, result.getApprovedBy());
            SQLUtil.SafeSetBoolean(pStmt, 14, result.getIsInvalidated());
            SQLUtil.SafeSetTimeStamp(pStmt, 15, result.getInvalidatedDate());
            SQLUtil.SafeSetInteger(pStmt, 16, result.getInvalidatedBy());
            SQLUtil.SafeSetBoolean(pStmt, 17, result.getIsUpdated());
            SQLUtil.SafeSetInteger(pStmt, 18, result.getUpdatedBy());
            /*
             if(result.getUpdatedDate() == null)
             pStmt.setNull(18, java.sql.Types.TIMESTAMP);
             else
             {
             //pStmt.setTimestamp(18,Convert.ToSQLDateTime(result.getUpdatedDate()));
             java.util.Date now = new java.util.Date();
             pStmt.setTimestamp(18,Convert.ToSQLDateTime(now));
             }
             */
            java.util.Date now = new java.util.Date();
            pStmt.setTimestamp(19, Convert.ToSQLDateTime(now));
            SQLUtil.SafeSetBoolean(pStmt, 20, result.getIsAbnormal());
            SQLUtil.SafeSetBoolean(pStmt, 21, result.getIsLow());
            SQLUtil.SafeSetBoolean(pStmt, 22, result.getIsHigh());
            SQLUtil.SafeSetBoolean(pStmt, 23, result.getIsCIDLow());
            SQLUtil.SafeSetBoolean(pStmt, 24, result.getIsCIDHigh());
            SQLUtil.SafeSetString(pStmt, 25, result.getTextAnswer());
            pStmt.setBoolean(26, result.isNoCharge());
            pStmt.setBoolean(27, result.getHl7Transmitted());
            pStmt.setBoolean(28, result.isPrintAndTransmitted());
            SQLUtil.SafeSetTimeStamp(pStmt, 29, result.getPAndTDate());
            pStmt.setBoolean(30, result.isOptional());
            //pStmt.setTimestamp(29, Convert.ToSQLDateTime(result.getPAndTDate()));

            pStmt.executeUpdate();

            pStmt.close();

            return true;
        } catch (Exception ex) {
            String message = ex.toString();
            int userId = Preferences.userRoot().getInt("id", 0);
            String resultIdStr = (result.getIdResults() != null ? result.getIdResults().toString() : "[null]");
            SysLogDAO.Add(userId, "ResultDAO::InsertResult: resultId " + resultIdStr + " for orderId " + result.getOrderId(), ex.getMessage());
            //TODO:  Add logging for failures
            System.out.println(message);
            if (pStmt != null) {
                pStmt.close();
            }
            return false;
        }
    }

    /**
     * Inserts result and returns the unique database identifier assigned.
     * Returns NULL on error.
     *
     * @param result
     * @return
     */
    public Integer InsertResultGetID(Results result) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        //if(GetResultIdByOrderIdTestId(result.getOrderId(), result.getTestId()) != 0)
        //    return UpdateResult(result);
        String stmt = "INSERT INTO " + table + "("
                + " `orderId`,"
                + " `testId`,"
                + " `panelId`,"
                + " `resultNo`,"
                + " `resultText`,"
                + " `resultRemark`,"
                + " `resultChoice`,"
                + " `created`,"
                + " `reportedBy`,"
                + " `dateReported`,"
                + " `isApproved`,"
                + " `approvedDate`,"
                + " `approvedby`,"
                + " `isInvalidated`,"
                + " `invalidatedDate`,"
                + " `invalidatedBy`,"
                + " `isUpdated`,"
                + " `updatedBy`,"
                + " `updatedDate`,"
                + " `isAbnormal`,"
                + " `isLow`,"
                + " `isHigh`,"
                + " `isCIDLow`,"
                + " `isCIDHigh`,"
                + " `textAnswer`,"
                + " `noCharge`,"
                + " `hl7Transmitted`,"
                + " `printAndTransmitted`,"
                + " `pAndTDate`,"
                + " `optional`)"
                + " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {

            PreparedStatement pStmt = con.prepareStatement(stmt,
                    Statement.RETURN_GENERATED_KEYS);

            pStmt.setInt(1, result.getOrderId());
            pStmt.setInt(2, result.getTestId());
            SQLUtil.SafeSetInteger(pStmt, 3, result.getPanelId());
            SQLUtil.SafeSetDouble(pStmt, 4, result.getResultNo());
            SQLUtil.SafeSetString(pStmt, 5, result.getResultText());
            SQLUtil.SafeSetInteger(pStmt, 6, result.getResultRemark());
            SQLUtil.SafeSetInteger(pStmt, 7, result.getResultChoice());
            pStmt.setTimestamp(8, Convert.ToSQLDateTime(result.getCreated()));
            SQLUtil.SafeSetInteger(pStmt, 9, result.getReportedBy());
            SQLUtil.SafeSetTimeStamp(pStmt, 10, result.getDateReported());
            SQLUtil.SafeSetBoolean(pStmt, 11, result.getIsApproved());
            SQLUtil.SafeSetTimeStamp(pStmt, 12, result.getApprovedDate());
            SQLUtil.SafeSetInteger(pStmt, 13, result.getApprovedBy());
            SQLUtil.SafeSetBoolean(pStmt, 14, result.getIsInvalidated());
            SQLUtil.SafeSetTimeStamp(pStmt, 15, result.getInvalidatedDate());
            SQLUtil.SafeSetInteger(pStmt, 16, result.getInvalidatedBy());
            SQLUtil.SafeSetBoolean(pStmt, 17, result.getIsUpdated());
            SQLUtil.SafeSetInteger(pStmt, 18, result.getUpdatedBy());
            java.util.Date now = new java.util.Date();
            pStmt.setTimestamp(19, Convert.ToSQLDateTime(now));
            SQLUtil.SafeSetBoolean(pStmt, 20, result.getIsAbnormal());
            SQLUtil.SafeSetBoolean(pStmt, 21, result.getIsLow());
            SQLUtil.SafeSetBoolean(pStmt, 22, result.getIsHigh());
            SQLUtil.SafeSetBoolean(pStmt, 23, result.getIsCIDLow());
            SQLUtil.SafeSetBoolean(pStmt, 24, result.getIsCIDHigh());
            SQLUtil.SafeSetString(pStmt, 25, result.getTextAnswer());
            pStmt.setBoolean(26, result.isNoCharge());
            pStmt.setBoolean(27, result.getHl7Transmitted());
            pStmt.setBoolean(28, result.isPrintAndTransmitted());
            SQLUtil.SafeSetTimeStamp(pStmt, 29, result.getPAndTDate());
            pStmt.setBoolean(30, result.isOptional());

            int affectedRows = pStmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Insert failed, no rows affected.");
            }

            Integer idResults;
            try (ResultSet generatedKeys = pStmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    idResults = generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Insert failed, no ID obtained.");
                }
            }
            pStmt.close();

            return idResults;

        } catch (Exception ex) {
            String message = ex.toString();
            System.out.println(message);
            int userId = Preferences.userRoot().getInt("id", 0);
            String resultIdStr = (result.getIdResults() != null ? result.getIdResults().toString() : "[null]");
            SysLogDAO.Add(userId, "ResultDAO::InsertResultGetID resultId " + resultIdStr + " for orderId " + result.getOrderId(), ex.getMessage());
            return null;
        }
    }

    @Override
    public boolean UpdateResult(Results result) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }

        try {

            String stmt = "UPDATE " + table + " SET"
                    + " `orderId` = ?,"
                    + " `testId` = ?,"
                    + " `panelId` = ?,"
                    + " `resultNo` = ?,"
                    + " `resultText` = ?,"
                    + " `resultRemark` = ?,"
                    + " `resultChoice` = ?,"
                    + " `reportedBy` = ?,"
                    + " `dateReported` = ?,"
                    + " `isApproved` = ?,"
                    + " `approvedDate` = ?,"
                    + " `approvedby` = ?,"
                    + " `isInvalidated` = ?,"
                    + " `invalidatedDate` = ?,"
                    + " `invalidatedBy` = ?,"
                    + " `isUpdated` = ?,"
                    + " `updatedBy` = ?,"
                    + " `updatedDate` = ? ,"
                    + " `isAbnormal` = ?,"
                    + " `isLow` = ?,"
                    + " `isHigh` = ?,"
                    + " `isCIDLow` = ?,"
                    + " `isCIDHigh` = ?,"
                    + " `textAnswer` = ?,"
                    + " `noCharge` = ?,"
                    + " `hl7Transmitted` = ?,"
                    + " `printAndTransmitted` = ?,"
                    + " `pAndTDate` = ?, "
                    + " `optional` = ? "
                    + "WHERE `idResults` = " + result.getIdResults();

            PreparedStatement pStmt = con.prepareStatement(stmt);

            pStmt.setInt(1, result.getOrderId());
            pStmt.setInt(2, result.getTestId());
            SQLUtil.SafeSetInteger(pStmt, 3, result.getPanelId());
            SQLUtil.SafeSetDouble(pStmt, 4, result.getResultNo());
            SQLUtil.SafeSetString(pStmt, 5, result.getResultText());
            SQLUtil.SafeSetInteger(pStmt, 6, result.getResultRemark());
            SQLUtil.SafeSetInteger(pStmt, 7, result.getResultChoice());
            SQLUtil.SafeSetInteger(pStmt, 8, result.getReportedBy());
            if (result.getDateReported() != null) {
                SQLUtil.SafeSetTimeStamp(pStmt, 9, Convert.ToSQLDateTime(result.getDateReported()));
            } else {
                pStmt.setNull(9, java.sql.Types.TIMESTAMP);
            }
            SQLUtil.SafeSetBoolean(pStmt, 10, result.getIsApproved());
            if (result.getApprovedDate() != null) {
                SQLUtil.SafeSetTimeStamp(pStmt, 11, Convert.ToSQLDateTime(result.getApprovedDate()));
            } else {
                pStmt.setNull(11, java.sql.Types.TIMESTAMP);
            }
            SQLUtil.SafeSetInteger(pStmt, 12, result.getApprovedBy());
            SQLUtil.SafeSetBoolean(pStmt, 13, result.getIsInvalidated());
            if (result.getInvalidatedDate() != null) {
                SQLUtil.SafeSetTimeStamp(pStmt, 14, Convert.ToSQLDateTime(result.getInvalidatedDate()));
            } else {
                pStmt.setNull(14, java.sql.Types.TIMESTAMP);
            }
            SQLUtil.SafeSetInteger(pStmt, 15, result.getInvalidatedBy());
            SQLUtil.SafeSetBoolean(pStmt, 16, result.getIsUpdated());
            SQLUtil.SafeSetInteger(pStmt, 17, result.getUpdatedBy());
            java.util.Date now = new java.util.Date();
            pStmt.setTimestamp(18, Convert.ToSQLDateTime(now));
            //SQLUtil.SafeSetTimeStamp(pStmt, 18, Convert.ToSQLDateTime(result.getUpdatedDate()));
            /*
             if(result.getUpdatedDate() == null)
             pStmt.setNull(18, java.sql.Types.TIMESTAMP);
             else
             pStmt.setTimestamp(18,Convert.ToSQLDateTime(result.getUpdatedDate()));
             */
            /*Who in the hell did this?
             Calendar calendar = Calendar.getInstance();
             java.util.Date now = calendar.getTime();
             java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());
             pStmt.setTimestamp(18,Convert.ToSQLDateTime(now));
             pStmt.setTimestamp(18, currentTimestamp);
             */
            //pStmt.setInt(19, result.getIsAbnormal());
            //pStmt.setInt(20, result.getIsLow());
            //pStmt.setInt(21, result.getIsHigh());
            //pStmt.setInt(22, result.getIsCIDLow());
            //pStmt.setInt(23, result.getIsCIDHigh());
            //pStmt.setString(24, result.getTextAnswer());
            SQLUtil.SafeSetBoolean(pStmt, 19, result.getIsAbnormal());
            SQLUtil.SafeSetBoolean(pStmt, 20, result.getIsLow());
            SQLUtil.SafeSetBoolean(pStmt, 21, result.getIsHigh());
            SQLUtil.SafeSetBoolean(pStmt, 22, result.getIsCIDLow());
            SQLUtil.SafeSetBoolean(pStmt, 23, result.getIsCIDHigh());
            SQLUtil.SafeSetString(pStmt, 24, result.getTextAnswer());
            pStmt.setBoolean(25, result.isNoCharge());
            pStmt.setBoolean(26, result.getHl7Transmitted());
            pStmt.setBoolean(27, result.isPrintAndTransmitted());
            if (result.getPAndTDate() != null) {
                SQLUtil.SafeSetTimeStamp(pStmt, 28, Convert.ToSQLDateTime(result.getPAndTDate()));
            } else {
                pStmt.setNull(28, java.sql.Types.TIMESTAMP);
            }
            pStmt.setBoolean(29, result.isOptional());
            pStmt.executeUpdate();

            pStmt.close();

            return true;
        } catch (Exception ex) {
            String message = ex.toString();
            //TODO:  Add exception handleing
            System.out.println(message);
            int userId = Preferences.userRoot().getInt("id", 0);
            String resultIdStr = (result.getIdResults() != null ? result.getIdResults().toString() : "[null]");
            SysLogDAO.Add(userId, "ResultDAO::UpdateResult resultId " + resultIdStr + " for orderId " + result.getOrderId(), ex.getMessage());
            return false;
        }
    }

    /**
     * Inserts the result if it has no unique identifier, or updates if unique
     * identifier (idresults) exists.
     *
     * Returns NULL on error Returns result ID on success (whether update or
     * insert)
     *
     * @param result
     * @return
     */
    public Integer UpsertResult(Results result) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        try {
            String sql = GenerateUpsertStatement(fields);
            PreparedStatement pStmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pStmt = SetStatementFromResults(result, pStmt, 2);
            pStmt.executeUpdate();

            Integer newId = null;
            ResultSet rs = pStmt.getGeneratedKeys();
            if (rs.next()) {
                newId = rs.getInt(1);
            }

            // Always return the ID of the result so we can check failure if
            // the returned integer is null
            if (newId == null) {
                newId = result.getIdResults();
            }
            rs.close();
            pStmt.close();
            return newId;
        } catch (SQLException ex) {
            String resultIdStr = "";
            String resultOrderIdStr = "";
            if (result == null) {
                resultIdStr = resultOrderIdStr = "[Null Result object]";
            } else {
                resultIdStr = (result.getIdResults() != null ? result.getIdResults().toString() : "[null]");
            }
            int userId = Preferences.userRoot().getInt("id", 0);
            SysLogDAO.Add(userId, "ResultDAO::UpsertResult resultId " + resultIdStr + " for orderId " + resultOrderIdStr, ex.getMessage());
            return null;
        }
    }

    /**
     * Update the result information that can be changed at Order Entry. This
     * allows us to update result rows safely and avoid situations where actual
     * result data can get overwritten if an order is being posted at the same
     * time someone has opened it up in Order Entry.
     *
     * @param resultId
     * @param updatedBy
     * @param noCharge
     * @return
     * @throws java.sql.SQLException
     */
    public void UpdateOrderData(int resultId, int updatedBy, boolean noCharge)
            throws IllegalArgumentException, SQLException {
        if (resultId <= 0) {
            SysLogDAO.Add(updatedBy, "ResultDAO::UpdateOrderData: Received " + resultId + " as result ID to update", "");
            throw new IllegalArgumentException("Bad resultId for update: " + resultId);
        }

        try {
            if (con.isValid(2) == false) {
                con = CheckDBConnection.Check(dbs, con);
            }
            String sql = "UPDATE results SET isUpdated = 1, updatedBy = ?, noCharge = ? WHERE idresults = " + resultId;
            PreparedStatement pStmt = con.prepareStatement(sql);
            pStmt.setInt(1, updatedBy);
            pStmt.setBoolean(2, noCharge);
            pStmt.execute();
        } catch (SQLException ex) {
            SysLogDAO.Add(updatedBy,
                    "ResultDAO::UpdateOrderData: Sql exception for "
                    + "resultId = " + resultId
                    + " updatedBy = " + updatedBy + " noCharge "
                    + noCharge,
                    ex.getMessage());

            throw ex;
        }
    }

    private PreparedStatement SetStatementFromResults(Results result, PreparedStatement pStmt, Integer count) throws SQLException {
        int i = 0;
        if (count == null) {
            count = 1;
        }
        for (int c = 0; c < count; c++) {
            SQLUtil.SafeSetInteger(pStmt, ++i, result.getIdResults());
            pStmt.setInt(++i, result.getOrderId());
            pStmt.setInt(++i, result.getTestId());
            SQLUtil.SafeSetInteger(pStmt, ++i, result.getPanelId());
            SQLUtil.SafeSetDouble(pStmt, ++i, result.getResultNo());
            SQLUtil.SafeSetString(pStmt, ++i, result.getResultText());
            SQLUtil.SafeSetInteger(pStmt, ++i, result.getResultRemark());
            SQLUtil.SafeSetInteger(pStmt, ++i, result.getResultChoice());
            pStmt.setTimestamp(++i, Convert.ToSQLDateTime(result.getCreated()));
            SQLUtil.SafeSetInteger(pStmt, ++i, result.getReportedBy());
            SQLUtil.SafeSetTimeStamp(pStmt, ++i, result.getDateReported());
            SQLUtil.SafeSetBoolean(pStmt, ++i, result.getIsApproved());
            SQLUtil.SafeSetTimeStamp(pStmt, ++i, result.getApprovedDate());
            SQLUtil.SafeSetInteger(pStmt, ++i, result.getApprovedBy());
            SQLUtil.SafeSetBoolean(pStmt, ++i, result.getIsInvalidated());
            SQLUtil.SafeSetTimeStamp(pStmt, ++i, result.getInvalidatedDate());
            SQLUtil.SafeSetInteger(pStmt, ++i, result.getInvalidatedBy());
            SQLUtil.SafeSetBoolean(pStmt, ++i, result.getIsUpdated());
            SQLUtil.SafeSetInteger(pStmt, ++i, result.getUpdatedBy());
            java.util.Date now = new java.util.Date();
            pStmt.setTimestamp(++i, Convert.ToSQLDateTime(now));
            SQLUtil.SafeSetBoolean(pStmt, ++i, result.getIsAbnormal());
            SQLUtil.SafeSetBoolean(pStmt, ++i, result.getIsHigh());
            SQLUtil.SafeSetBoolean(pStmt, ++i, result.getIsLow());
            SQLUtil.SafeSetBoolean(pStmt, ++i, result.getIsCIDHigh());
            SQLUtil.SafeSetBoolean(pStmt, ++i, result.getIsCIDLow());
            SQLUtil.SafeSetString(pStmt, ++i, result.getTextAnswer());
            pStmt.setBoolean(++i, result.isNoCharge());
            pStmt.setBoolean(++i, result.getHl7Transmitted());
            pStmt.setBoolean(++i, result.isPrintAndTransmitted());
            SQLUtil.SafeSetTimeStamp(pStmt, ++i, result.getPAndTDate());
            pStmt.setBoolean(++i, result.isOptional());
        }
        return pStmt;
    }

    private String GenerateUpsertStatement(ArrayList<String> fields) {
        String key;
        String keys = "";
        String value;
        String values = "";
        String keysEqualValues = "";

        for (int i = 0; i < fields.size(); ++i) {
            key = "`" + fields.get(i) + "`";
            value = "?";
            if (i > 0) {
                keys += ",";
                values += ",";
                keysEqualValues += ",";
            }
            keysEqualValues += key + "=" + value;
            values += value;
            keys += key;
        }

        String statement = "INSERT INTO " + table + "(" + keys + ") values (" + values + ")";
        statement += " ON DUPLICATE KEY UPDATE " + keysEqualValues + ";";

        return statement;
    }

    /**
     * A faster version of UpdateResult that ignores the OrderID, TestID and
     * PanelID
     *
     * @param result Results Result to update.
     * @return true on success, false on error
     */
    public boolean PostResult(Results result) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }

        try {

            String stmt = "UPDATE " + table + " SET"
                    + " `resultNo` = ?,"
                    + " `resultText` = ?,"
                    + " `resultRemark` = ?,"
                    + " `resultChoice` = ?,"
                    + " `reportedBy` = ?,"
                    + " `dateReported` = ?,"
                    + " `isApproved` = ?,"
                    + " `approvedDate` = ?,"
                    + " `approvedby` = ?,"
                    + " `isInvalidated` = ?,"
                    + " `invalidatedDate` = ?,"
                    + " `invalidatedBy` = ?,"
                    + " `isUpdated` = ?,"
                    + " `updatedBy` = ?,"
                    + " `updatedDate` = ? ,"
                    + " `isAbnormal` = ?,"
                    + " `isLow` = ?,"
                    + " `isHigh` = ?,"
                    + " `isCIDLow` = ?,"
                    + " `isCIDHigh` = ?,"
                    + " `textAnswer` = ?,"
                    + " `hl7Transmitted` = ?,"
                    + " `printAndTransmitted` = ?,"
                    + " `pAndTDate` = ?, "
                    + " `optional` = ?"
                    + " WHERE `idResults` = " + result.getIdResults();

            PreparedStatement pStmt = con.prepareStatement(stmt);

            SQLUtil.SafeSetDouble(pStmt, 1, result.getResultNo());
            SQLUtil.SafeSetString(pStmt, 2, result.getResultText());
            SQLUtil.SafeSetInteger(pStmt, 3, result.getResultRemark());
            SQLUtil.SafeSetInteger(pStmt, 4, result.getResultChoice());
            SQLUtil.SafeSetInteger(pStmt, 5, result.getReportedBy());
            if (result.getDateReported() != null) {
                SQLUtil.SafeSetTimeStamp(pStmt, 6, Convert.ToSQLDateTime(result.getDateReported()));
            } else {
                pStmt.setNull(6, java.sql.Types.TIMESTAMP);
            }
            SQLUtil.SafeSetBoolean(pStmt, 7, result.getIsApproved());
            if (result.getApprovedDate() != null) {
                SQLUtil.SafeSetTimeStamp(pStmt, 8, Convert.ToSQLDateTime(result.getApprovedDate()));
            } else {
                pStmt.setNull(8, java.sql.Types.TIMESTAMP);
            }
            SQLUtil.SafeSetInteger(pStmt, 9, result.getApprovedBy());
            SQLUtil.SafeSetBoolean(pStmt, 10, result.getIsInvalidated());
            if (result.getInvalidatedDate() != null) {
                SQLUtil.SafeSetTimeStamp(pStmt, 11, Convert.ToSQLDateTime(result.getInvalidatedDate()));
            } else {
                pStmt.setNull(11, java.sql.Types.TIMESTAMP);
            }
            SQLUtil.SafeSetInteger(pStmt, 12, result.getInvalidatedBy());
            SQLUtil.SafeSetBoolean(pStmt, 13, result.getIsUpdated());
            SQLUtil.SafeSetInteger(pStmt, 14, result.getUpdatedBy());
            java.util.Date now = new java.util.Date();
            pStmt.setTimestamp(15, Convert.ToSQLDateTime(now));
            SQLUtil.SafeSetBoolean(pStmt, 16, result.getIsAbnormal());
            SQLUtil.SafeSetBoolean(pStmt, 17, result.getIsLow());
            SQLUtil.SafeSetBoolean(pStmt, 18, result.getIsHigh());
            SQLUtil.SafeSetBoolean(pStmt, 19, result.getIsCIDLow());
            SQLUtil.SafeSetBoolean(pStmt, 20, result.getIsCIDHigh());
            SQLUtil.SafeSetString(pStmt, 21, result.getTextAnswer());
            pStmt.setBoolean(22, result.getHl7Transmitted());
            pStmt.setBoolean(23, result.isPrintAndTransmitted());
            if (result.getPAndTDate() != null) {
                SQLUtil.SafeSetTimeStamp(pStmt, 24, Convert.ToSQLDateTime(result.getPAndTDate()));
            } else {
                pStmt.setNull(24, java.sql.Types.TIMESTAMP);
            }
            pStmt.setBoolean(25, result.isOptional());
            pStmt.executeUpdate();

            pStmt.close();

            return true;
        } catch (Exception ex) {
            String message = ex.toString();
            int userId = Preferences.userRoot().getInt("id", 0);
            String resultIdStr = (result.getIdResults() != null ? result.getIdResults().toString() : "[null]");
            SysLogDAO.Add(userId, "ResultDAO::PostResult: resultId " + resultIdStr + " for orderId " + result.getOrderId(), ex.getMessage());
            System.out.println(message);
            return false;
        }
    }

    public Results GetResultByID(int resultID) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        Results res = new Results();

        try {
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + " WHERE `idResults` = " + resultID;

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                res = new Results();

                setFromResultSet(res, rs);

            }

            rs.close();
            stmt.close();

            return res;

        } catch (Exception ex2) {
            System.out.println(ex2.toString());
            return null;
        }
    }

    public List<Results> GetResultsByOrderIdContainersFirst(int orderId) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        List<Results> rl = new ArrayList<>();
        Results res;

        try {
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM results r "
                    + " INNER JOIN tests t ON r.testId = t.idtests"
                    + " WHERE r.`orderId` = " + orderId + " ORDER BY t.header DESC, t.testType ASC";

            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                res = new Results();

                setFromResultSet(res, rs);

                rl.add(res);
            }

            rs.close();
            stmt.close();

            return rl;

        } catch (Exception ex2) {
            System.out.println(ex2.toString());
            return null;
        }
    }

    public List<Results> GetResultsByOrderID(int orderID) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        List<Results> rl = new ArrayList<Results>();
        Results res;

        try {
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + " WHERE `orderId` = " + orderID;

            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                res = new Results();

                setFromResultSet(res, rs);

                rl.add(res);
            }

            rs.close();
            stmt.close();

            return rl;

        } catch (Exception ex2) {
            System.out.println(ex2.toString());
            return null;
        }
    }

    public List<Results> GetActiveResultsByOrderID(int orderID) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        List<Results> rl = new ArrayList<Results>();
        Results res;

        try {
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table + " r "
                    + " WHERE `orderId` = " + orderID
                    + " AND (r.resultText IS NULL OR (r.resultText IS NOT NULL "
                    + " AND r.resultText != 'DELETED')) "
                    + " AND (r.isInvalidated IS NULL OR (r.isInvalidated IS NOT NULL "
                    + " AND r.isInvalidated = 0))";

            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                res = new Results();

                setFromResultSet(res, rs);

                rl.add(res);
            }

            rs.close();
            stmt.close();

            return rl;

        } catch (Exception ex2) {
            System.out.println(ex2.toString());
            return null;
        }
    }

    public List<Integer> GetAllTestIdsByOrderId(Integer OrderID) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        List<Integer> rl = new ArrayList<Integer>();
        int res;

        try {
            Statement stmt = con.createStatement();

            String query = "SELECT `testId` FROM " + table
                    + " WHERE `orderId` = " + OrderID;

            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                res = 0;

                res = rs.getInt("testId");

                rl.add(res);
            }

            rs.close();
            stmt.close();

            return rl;

        } catch (Exception ex2) {
            System.out.println(ex2.toString());
            return null;
        }
    }

    /**
     * If the client supports overlapping panels, results cannot be uniquely
     * idenfied by just a test number.
     */
    /*
     public Results GetResultByOrdreIdTestNumberPanelNumber(int orderId, int testNumber, panelNumber)
     {
     try {
     if (con.isClosed()) {
     con = CheckDBConnection.Check(dbs, con);
     }
     } catch (SQLException sex) {
     System.out.println(sex.toString());
     return null;
     }
     /*
     try
     {
     //"SELECT * FROM " + table + " r ";

     }
     catch (SQLException ex)
     {
            
     }
     }*/
    /**
     * Gets a result object using the provided test number. Ignores invalidated
     * records (this is to support the possibility of overlapping panel tests
     * for clinical labs; duplicate tests in these cases are made invalidated.)
     *
     * @param orderId
     * @param testNumber
     * @return
     */
    public Results GetResultByOrderIdTestNumber(int orderId, int testNumber) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        Results res = new Results();

        try {
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table + " r "
                    + " INNER JOIN tests t ON r.testId = t.idtests "
                    + " WHERE r.isInvalidated = 0 AND r.orderId = " + orderId
                    + " AND t.number = " + testNumber;

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                setFromResultSet(res, rs);
            }

            rs.close();
            stmt.close();

            return res;

        } catch (Exception ex2) {
            System.out.println(ex2.toString());
            return null;
        }
    }

    public Results GetResultByOrderIDTestID(int orderID, int testID) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        Results res = new Results();

        try {
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + " WHERE `orderId` = " + orderID
                    + " AND `testId` = " + testID;

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                setFromResultSet(res, rs);
            }

            rs.close();
            stmt.close();

            return res;

        } catch (Exception ex2) {
            System.out.println(ex2.toString());
            return null;
        }
    }

    public Results GetResultByOrderIDTestNumber(int orderId, int testNum) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        Results res = new Results();

        try {
            Statement stmt = con.createStatement();

            String query
                    = "SELECT *"
                    + " FROM results r "
                    + " INNER JOIN tests t on r.testId = t.idTests "
                    + " WHERE "
                    + " t.number = " + testNum
                    + " AND r.orderId = " + orderId;

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                setFromResultSet(res, rs);
            }

            rs.close();
            stmt.close();

            return res;

        } catch (Exception ex2) {
            System.out.println(ex2.toString());
            return null;
        }
    }

    public List<Results> GetReportedResultByOrderID(int orderID) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        List<Results> rl = new ArrayList<Results>();
        Results res;

        try {
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + " WHERE `orderId` = " + orderID
                    + " AND `dateReported` IS NOT NULL";

            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                res = new Results();

                setFromResultSet(res, rs);

                rl.add(res);
            }

            rs.close();
            stmt.close();

            return rl;

        } catch (Exception ex2) {
            System.out.println(ex2.toString());
            return null;
        }
    }

    public List<Results> GetApprovedResultByOrderID(int orderID) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        List<Results> rl = new ArrayList<Results>();
        Results res;

        try {
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + " WHERE `orderId` = " + orderID
                    + " AND `approvedDate` IS NOT NULL";

            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                res = new Results();

                setFromResultSet(res, rs);

                rl.add(res);
            }

            rs.close();
            stmt.close();

            return rl;

        } catch (Exception ex2) {
            System.out.println(ex2.toString());
            return null;
        }
    }

    public List<Results> GetPrintAndTransmittedByOrderID(int orderID) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        List<Results> rl = new ArrayList<Results>();
        Results res;

        try {
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + " WHERE `orderId` = " + orderID
                    + " AND `printAndTransmitted` = " + true;

            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                res = new Results();

                setFromResultSet(res, rs);

                rl.add(res);
            }

            rs.close();
            stmt.close();

            return rl;

        } catch (Exception ex2) {
            System.out.println(ex2.toString());
            return null;
        }
    }

    public int GetResultIdByOrderIdTestId(int OrderID, int TestID) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return -1;
        }

        int id;
        String ids = "";
        try {
            Statement stmt = con.createStatement();

            String query = "SELECT `idResults` FROM "
                    + table
                    + "WHERE `orderId` = " + OrderID
                    + " AND `testId` = " + TestID;

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                ids = rs.getString(1);
                id = Integer.parseInt(ids);
            } else {
                id = 0;
            }

            rs.close();
            stmt.close();

            return id;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return 0;
        }
    }

    public Results GetResultByOrderIdPanelIdTestNumber(int orderId, Integer panelId, int testNumber) throws SQLException {
        if (orderId < 1 || testNumber < 1) {
            return null;
        }

        if (con.isClosed()) {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "SELECT * FROM " + table + " r "
                + " INNER JOIN tests t ON r.testId = t.idtests WHERE "
                + " `orderId` = " + orderId + " AND t.`number` = " + testNumber;

        if (panelId != null && panelId > 0) {
            sql += " AND `panelId` = " + panelId;
        } else {
            sql += " AND `panelId` IS NULL";
        }

        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        Results res = null;
        if (rs.next()) {
            res = new Results();
            setFromResultSet(res, rs);
        }
        return res;
    }

    /**
     * Returns a result object for an order/test/panel combination. Usable for
     * clients who have the "overlapping panels" functionality - the ability to
     * have more than one instance of a test on an order as long as they are
     * under different panels, or if one of them is ordered as a single test.
     *
     * @param orderId
     * @param testId
     * @param panelId If NULL, method attempts to find the individually ordered
     * test (not under a panel)
     * @return Result if one exists, NULL if one cannot be found
     * @throws SQLException
     */
    public Results GetResultByOrderIdPanelIdTestId(int orderId, Integer panelId, int testId) throws SQLException {
        if (orderId < 1 || testId < 1) {
            return null;
        }

        if (con.isClosed()) {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "SELECT * FROM " + table + " WHERE "
                + " `orderId` = " + orderId + " AND `testId` = " + testId;

        if (panelId != null && panelId > 0) {
            sql += " AND `panelId` = " + panelId;
        } else {
            sql += " AND `panelId` IS NULL";
        }

        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        Results res = null;
        if (rs.next()) {
            res = new Results();
            setFromResultSet(res, rs);
        }
        return res;
    }

    public boolean ResultExists(int OrderID, int TestID) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }

        try {
            Statement stmt = null;
            ResultSet rs = null;
            int rowCount = -1;

            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT COUNT(*) FROM " + table + " WHERE "
                    + "`orderid` = " + OrderID
                    + " AND `testid` = " + TestID);
            rs.next();
            rowCount = rs.getInt(1);

            rs.close();
            stmt.close();

            if (rowCount > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception ex) {
            //TODO:  Add Exception Handling
            return false;
        }
    }

    public boolean ResultExists(int OrderID, int TestID, String ResultText) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }

        try {
            PreparedStatement stmt = null;
            ResultSet rs = null;
            int rowCount = -1;

            //stmt = con.createStatement();
            String query = "SELECT COUNT(*) FROM " + table + " WHERE "
                    + "`orderid` = " + OrderID
                    + " AND `testid` = " + TestID
                    + " AND `resultText` = ?;";
            stmt = createStatement(con, query, ResultText);
            rs = stmt.executeQuery();
            rs.next();
            rowCount = rs.getInt(1);

            rs.close();
            stmt.close();

            if (rowCount > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception ex) {
            //TODO:  Add Exception Handling
            return false;
        }
    }

    public boolean ResultExists(int OrderID) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }

        try {
            Statement stmt = null;
            ResultSet rs = null;
            int rowCount = -1;

            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT COUNT(*) FROM " + table + " WHERE "
                    + "`orderid` = " + OrderID);
            rs.next();
            rowCount = rs.getInt(1);

            rs.close();
            stmt.close();

            if (rowCount > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception ex) {
            //TODO:  Add Exception Handling
            return false;
        }
    }

    public boolean ResultExistsCum(int OrderID) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }

        try {
            Statement stmt = null;
            ResultSet rs = null;
            int rowCount = -1;

            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT COUNT(*) FROM " + table + " WHERE "
                    + "`orderid` = " + OrderID
                    + " AND `dateReported` IS NOT NULL");
            rs.next();
            rowCount = rs.getInt(1);

            rs.close();
            stmt.close();

            if (rowCount > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception ex) {
            //TODO:  Add Exception Handling
            return false;
        }
    }

    /**
     * Deletes a single result line from the database: NOTE! Calling code should
     * log this event and the data on the line!!
     *
     * @param resultId
     * @throws SQLException
     * @throws NullPointerException
     * @throws IllegalArgumentException
     */
    public static void delete(int resultId)
            throws SQLException, NullPointerException, IllegalArgumentException {
        if (resultId <= 0) {
            throw new IllegalArgumentException(
                    "ResultDAO::deleteResultLine: Received resultId of " + resultId);
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false) {
            con = CheckDBConnection.Check(dbs, con);
        }

        String query = "DELETE FROM `results` WHERE `idResults` = ?";

        PreparedStatement pStmt = con.prepareStatement(query);
        pStmt.setInt(1, resultId);
        try {
            int count = pStmt.executeUpdate();
            if (count == 0) {
                throw new SQLException("Zero rows were reported as deleted");
            }
        } catch (Exception ex) {
            query = "NULL";
            if (pStmt != null && pStmt.toString() != null) {
                query = pStmt.toString();
            }
            String errorMessage = "ResultDAO::delete: Exception for resultId "
                    + resultId + " sql statement: " + query;

            System.out.println(errorMessage + "   " + ex.getMessage());
            throw new SQLException(errorMessage);
        } finally {
            if (pStmt != null) {
                pStmt.close();
            }
        }
    }

    public boolean PurgeResultByResultID(int RESULTID) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }

        try {
            Statement stmt = null;

            String query = "DELETE FROM " + table + " WHERE "
                    + "`idResults` = " + RESULTID;

            stmt = con.createStatement();
            int delete = stmt.executeUpdate(query);

            if (delete == 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception ex) {
            //TODO:  Add Exception Handling
            System.out.println("ResultDAO::PurgeResultByResultID - " + ex.getMessage());
            return false;
        }
    }

    public boolean PurgeResultByOrderIdTestId(int orderId, int testId) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }

        try {
            String stmt = "DELETE FROM " + table + " "
                    + "WHERE `orderId` = ? "
                    + "AND `testId` = ?";

            PreparedStatement pStmt = con.prepareStatement(stmt);

            SQLUtil.SafeSetInteger(pStmt, 0, orderId);
            SQLUtil.SafeSetInteger(pStmt, 1, testId);

            int delete = pStmt.executeUpdate(stmt);

            if (delete == 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception ex) {
            //TODO:  Add Exception Handling
            System.out.println("ResultDAO::PurgeResultByResultID - " + ex.getMessage());
            return false;
        }
    }

    /**
     * Gets all rows for the order that have the provided results' test as a
     * parent (panelId).
     *
     * NOTE: ONLY retrieves the direct children of the panel, does not
     * recursively return their children. Returns empty array if no children
     * found, null on error
     *
     * @param idResults
     * @return
     */
    public ArrayList<Results> GetSubtestResults(int idResults) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        Results result = GetResultByID(idResults);
        if (result == null) {
            return null;
        }

        ArrayList<Results> results = new ArrayList<>();

        // Get all the "child" tests for the provided result
        String sql = "SELECT * FROM " + table
                + " WHERE orderId = " + result.getOrderId()
                + " AND panelId = " + result.getTestId();

        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                result = new Results();
                setFromResultSet(result, rs);
                results.add(result);
            }
            rs.close();
            stmt.close();

        } catch (SQLException ex) {
            System.out.println("ResultDAO::GetSubtestResults: Could not retrieve "
                    + "subtests for idresults: " + idResults);
            return null;
        }

        return results;
    }

    public int GetIDForDetailImport(int arNo, String acc, int test, int subtest) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return -1;
        }

        int id;
        String ids = "";

        try {
            Statement stmt = null;
            ResultSet rs = null;

            stmt = con.createStatement();

            /*rs = stmt.executeQuery("SELECT r.idResults"
             + " FROM results r"
             + " LEFT OUTER JOIN"
             + " orders o ON r.orderId = o.idOrders"
             + " LEFT OUTER JOIN"
             + " tests t ON r.testId = t.idTests"
             + " LEFT OUTER JOIN"
             + " patients p ON o.patientId = p.idPatients"
             + " WHERE p.arNo = " + arNo
             + " AND o.accession = " + acc
             + " AND t.number = " + test
             + " AND t.subtest = " + subtest);*/

            /*rs = stmt.executeQuery(
             "SELECT r.idResults " +
             "FROM results r " +
             "LEFT OUTER JOIN " +
             "tests t ON r.testId = t.idTests, " +
             "( " +
             "  SELECT o.idOrders " +
             "  FROM orders o " +
             "  LEFT OUTER JOIN " + 
             "  patients p ON o.patientId = p.idPatients " +
             "  WHERE o.accession = " + acc +
             " AND p.arNo = " + arNo +
             ") ords " +
             "WHERE ords.idOrders = r.orderId " +
             "AND t.number = " + test + " " +
             "AND t.subtest = " + subtest + ";"
             );*/
            rs = stmt.executeQuery(
                    "SELECT r.idResults "
                    + "FROM results r, "
                    + "( "
                    + "  SELECT t.idTests "
                    + "  FROM tests t "
                    + "  WHERE t.number = " + test + " "
                    + "  AND t.subtest = " + subtest + " "
                    + ") tst, "
                    + "( "
                    + "  SELECT o.idOrders "
                    + "  FROM orders o, "
                    + "  ( "
                    + "    SELECT p.idPatients "
                    + "    FROM patients p "
                    + "    WHERE p.arNo = " + arNo + " "
                    + "  ) pat "
                    + "  WHERE o.accession = \"" + acc + "\" "
                    + "  AND o.patientId = pat.idPatients "
                    + ") ords "
                    + "WHERE ords.idOrders = r.orderId "
                    + "AND tst.idTests = r.testId;");

            if (rs.next()) {
                ids = rs.getString(1);
                id = Integer.parseInt(ids);
            } else {
                id = 0;
            }

            rs.close();
            stmt.close();

            return id;
        } catch (Exception ex) {
            System.out.println("Could not retrieve ResultID: " + ex.toString());
            return 0;
        }
    }

    @Override
    public Boolean IsTestNoCharge(Integer OrderID, Integer TestID) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }

        try {
            Statement stmt = null;
            ResultSet rs = null;
            Boolean noCharge = null;

            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT `noCharge` FROM " + table
                    + " WHERE `orderId` = " + OrderID + " AND  `testId` = " + TestID);
            if (rs.next()) {
                noCharge = rs.getBoolean("noCharge");
            }

            rs.close();
            stmt.close();

            return noCharge;
        } catch (Exception ex) {
            //TODO:  Add Exception Handling
            System.out.println("ResultDAO::IsTestNoCharge - " + ex.getMessage());
            return false;
        }
    }

    public Results GetNextResult(int id) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        try {
            Results res = new Results();
            String stmt = "SELECT * FROM " + table + " "
                    + "WHERE `idresults` > ? "
                    + "ORDER BY `idresults` ASC "
                    + "LIMIT 1";

            PreparedStatement pStmt = con.prepareStatement(stmt);

            //SQLUtil.SafeSetInteger(pStmt, 1, id);
            pStmt.setInt(1, id);

            ResultSet rs = pStmt.executeQuery();

            if (rs.next()) {
                res = setFromResultSet(res, rs);
            }

            rs.close();
            pStmt.close();

            return res;
        } catch (SQLException ex) {
            System.out.println("ResultDAO::GetNextResult - " + ex.getMessage());
            return null;
        }
    }

    public Integer GetResultChoiceByID(Integer resultID) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        try {
            Statement stmt = null;
            ResultSet rs = null;
            Integer resultChoice = null;

            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT `resultChoice` FROM " + table
                    + " WHERE `idResults` = " + resultID);
            if (rs.next()) {
                resultChoice = rs.getInt("resultChoice");
            }

            rs.close();
            stmt.close();

            return resultChoice;
        } catch (Exception ex) {
            //TODO:  Add Exception Handling
            System.out.println("ResultDAO::GetResultChoiceByID - " + ex.getMessage());
            return null;
        }
    }

    @Override
    public Boolean IsTestNoCharge(Integer resultID) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }

        try {
            Statement stmt = null;
            ResultSet rs = null;
            Boolean noCharge = null;

            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT `noCharge` FROM " + table
                    + " WHERE `idResults` = " + resultID);
            if (rs.next()) {
                noCharge = rs.getBoolean("noCharge");
            }

            rs.close();
            stmt.close();

            return noCharge;
        } catch (Exception ex) {
            //TODO:  Add Exception Handling
            System.out.println("ResultDAO::IsTestNoCharge - " + ex.getMessage());
            return false;
        }
    }

    @Override
    public Boolean UpdateNoChargeFlag(Integer OrderID, Integer TestID, Boolean value) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }

        try {

            String stmt = "UPDATE " + table + " SET"
                    + " `noCharge` = ? "
                    + "WHERE `orderId` = " + OrderID
                    + " AND `testId` = " + TestID;

            PreparedStatement pStmt = con.prepareStatement(stmt);

            pStmt.setBoolean(1, value);
            int retVal = pStmt.executeUpdate();

            pStmt.close();

            return retVal > 0;
        } catch (Exception ex) {
            String message = ex.toString();
            //TODO:  Add exception handleing
            System.out.println(message);
            return false;
        }
    }

    public List<BaseResult> GetBaseResultByResultId(Integer orderId) {

        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            return null;
        }
        try {
            List<BaseResult> baseResultList = new ArrayList<>();
            BaseResult baseResult = new BaseResult();
            
            String stmt = "SELECT `idResults`, `orderId`, `testId`, `resultText`, `resultRemark`, `resultChoice`,"
                    + " `isInvalidated`, isApproved`, `resultNo`, `isAbnormal`, `isHigh`, `isLow`, `isCIDHigh`, `isCIDLow`"
                    + " FROM " + table + " "
                    + "WHERE `orderId` =  " + orderId;

            PreparedStatement pStmt = con.prepareStatement(stmt);

            pStmt.setInt(1, orderId);

            ResultSet rs = pStmt.executeQuery();

            while(rs.next()) {
                
                baseResult.setIdResults(rs.getInt("idResults"));
                baseResult.setOrderId(rs.getInt("orderId"));
                baseResult.setTestId(rs.getInt("testId"));
                baseResult.setResultText(rs.getString("resultText"));
                baseResult.setResultRemark(rs.getInt("resultRemark"));
                baseResult.setResultChoice(rs.getInt("resultChoice"));
                baseResult.setResultNo(rs.getDouble("resultNo"));
                baseResult.setIsInvalidated(rs.getBoolean("isInvalidated"));
                baseResult.setIsApproved(rs.getBoolean("isApproved"));
                baseResult.setIsAbnormal(rs.getBoolean("isAbnormal"));
                baseResult.setIsHigh(rs.getBoolean("isHigh"));
                baseResult.setIsLow(rs.getBoolean("isLow"));
                baseResult.setIsCIDHigh(rs.getBoolean("isCIDHigh"));
                baseResult.setIsCIDLow(rs.getBoolean("isCIDLow"));
                baseResultList.add(baseResult);
            }

            rs.close();
            pStmt.close();

            return baseResultList;
        } catch (SQLException ex) {
            System.out.println("ResultDAO::GetBaseResultByResultId - " + ex.getMessage());
            return null;
        }

    }

    @Override
    public Boolean Insert(Serializable obj) {
        try {
            Results res = (Results) obj;
            return InsertResult(res);
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            return false;
        }
    }

    @Override
    public Boolean Update(Serializable obj) {
        try {
            Results res = (Results) obj;
            return UpdateResult(res);
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            return false;
        }
    }

    @Override
    public Boolean Delete(Serializable obj) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }

        try {
            Statement stmt = null;

            String query = "DELETE FROM " + table + " WHERE "
                    + "`idResults` = " + String.valueOf(obj);

            stmt = con.createStatement();
            int delete = stmt.executeUpdate(query);

            if (delete == 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception ex) {
            System.out.println("ResultDAO::Delete - " + ex.getMessage());
            return false;
        }
    }

    @Override
    public Serializable getByID(Integer ID) {
        return GetResultByID(ID);
    }

    private Results setFromResultSet(Results res, ResultSet rs) throws SQLException {
        res.setIdResults(rs.getInt("idResults"));
        res.setOrderId(rs.getInt("orderId"));
        res.setTestId(rs.getInt("testId"));
        res.setPanelId(rs.getInt("panelId"));
        res.setResultNo(rs.getDouble("resultNo"));
        res.setResultText(rs.getString("resultText"));
        res.setResultRemark(rs.getInt("resultRemark"));
        res.setResultChoice(rs.getInt("resultChoice"));
        res.setCreated(rs.getTimestamp("created"));
        res.setReportedBy(rs.getInt("reportedBy"));
        res.setDateReported(rs.getTimestamp("dateReported"));
        res.setIsApproved(rs.getBoolean("isApproved"));
        res.setApprovedDate(rs.getTimestamp("approvedDate"));
        res.setApprovedBy(rs.getInt("approvedBy"));
        res.setIsInvalidated(rs.getBoolean("isInvalidated"));
        res.setInvalidatedDate(rs.getTimestamp("invalidatedDate"));
        res.setInvalidatedBy(rs.getInt("invalidatedBy"));
        res.setIsUpdated(rs.getBoolean("isUpdated"));
        res.setUpdatedBy(rs.getInt("updatedBy"));
        res.setUpdatedDate(rs.getTimestamp("updatedDate"));
        res.setIsAbnormal(rs.getBoolean("isAbnormal"));
        res.setIsHigh(rs.getBoolean("isHigh"));
        res.setIsLow(rs.getBoolean("isLow"));
        res.setIsCIDHigh(rs.getBoolean("isCIDHigh"));
        res.setIsCIDLow(rs.getBoolean("isCIDLow"));
        res.setTextAnswer(rs.getString("textAnswer"));
        res.setNoCharge(rs.getBoolean("noCharge"));
        res.setHl7Transmitted(rs.getBoolean("hl7Transmitted"));
        res.setPrintAndTransmitted(rs.getBoolean("printAndTransmitted"));
        res.setPAndTDate(rs.getTimestamp("pAndTDate"));
        res.setOptional(rs.getBoolean("optional"));
        return res;
    }

    @Override
    public String structureCheck() {
        return DatabaseStructureCheck.structureCheck(fields, table, con);
    }

}
