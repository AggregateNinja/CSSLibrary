package DAOS;

import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IStructureCheckable;
import DOS.Instruments;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.Convert;
import Utility.SQLUtil;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import static Utility.SQLUtil.createStatement;

/**
 * @date: Feb 7, 2013
 * @author: Keith Maggio
 *
 * @project:
 * @package: DAOS
 * @file name: InstrumentDAO.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc. All Rights Reserved
 */
public class InstrumentDAO implements DAOInterface, IStructureCheckable {

    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`instruments`";

    public boolean InsertInstrument(Instruments instrument) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }

        try {
            String stmt = "INSERT INTO " + table + "("
                    + "`instName`, "
                    + "`instNo`, "
                    + "`online`, "
                    + "`manufacturer`, "
                    + "`datePurchased`, "
                    + "`serialNumber`, "
                    + "`commType`, "
                    + "`filePath`, "
                    + "`tty`, "
                    + "`port`, "
                    + "`ipAddress`, "
                    + "`fileRegExpr`, "
                    + "`resTable`, "
                    + "`ordTable`, "
                    + "`xrefTable`, "
                    + "`processedPath`, "
                    + "`fileReader`, "
                    + "`useOnlineCode`, "
                    + "`comment`, "
                    + "`multipleSpecimen`, "
                    + "`usesAutomatedPosting`, "
                    + "`isLocal`, "
                    + "`qcTable`, "
                    + "`qcLevels` "
                    + ") "
                    + "VALUES "
                    + "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
            PreparedStatement pStmt = con.prepareStatement(stmt);

            pStmt.setString(1, instrument.getInstName());
            pStmt.setInt(2, instrument.getInstNo());
            pStmt.setBoolean(3, instrument.getOnline());
            pStmt.setInt(4, instrument.getManufacturer());
            pStmt.setTimestamp(5, Convert.ToSQLDateTime(instrument.getDatePurchased()));
            pStmt.setString(6, instrument.getSerialNumber());
            pStmt.setString(7, instrument.getCommType());
            pStmt.setString(8, instrument.getFilePath());
            pStmt.setString(9, instrument.getTty());
            pStmt.setString(10, instrument.getPort());
            pStmt.setString(11, instrument.getIpAddress());
            pStmt.setString(12, instrument.getFileRegExpr());
            pStmt.setString(13, instrument.getResTable());
            pStmt.setString(14, instrument.getOrdTable());
            pStmt.setString(15, instrument.getXrefTable());
            pStmt.setString(16, instrument.getProcessedPath());
            pStmt.setString(17, instrument.getFileReader());
            pStmt.setBoolean(18, instrument.getUseOnlineCode());
            pStmt.setBytes(19, instrument.getComment());
            pStmt.setBoolean(20, instrument.getMultipleSpecimen());
            SQLUtil.SafeSetBoolean(pStmt, 21, instrument.getUsesAutomatedPosting());
            SQLUtil.SafeSetBoolean(pStmt, 22, instrument.getIsLocal());
            SQLUtil.SafeSetString(pStmt, 23, instrument.getQcTable());
            SQLUtil.SafeSetInteger(pStmt, 24, instrument.getQcLevels());

            pStmt.executeUpdate();

            pStmt.close();

            return true;
        } catch (Exception ex) {
            String message = ex.toString();
            //TODO:  Add logging for failures
            System.out.println(message);
            return false;
        }
    }

    public boolean UpdateInstrument(Instruments instrument) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }

        try {
            String stmt = "UPDATE " + table
                    + "SET "
                    + "`instName` = ?, "
                    + "`instNo` = ?, "
                    + "`online` = ?, "
                    + "`manufacturer` = ?, "
                    + "`datePurchased` = ?, "
                    + "`serialNumber` = ?, "
                    + "`commType` = ?, "
                    + "`filePath` = ?, "
                    + "`tty` = ?, "
                    + "`port` = ?, "
                    + "`ipAddress` = ?, "
                    + "`fileRegExpr` = ?, "
                    + "`resTable` = ?, "
                    + "`ordTable` = ?, "
                    + "`xrefTable` = ?,"
                    + "`processedPath` = ?, "
                    + "`fileReader` = ?, "
                    + "`useOnlineCode` = ?, "
                    + "`comment` = ?, "
                    + "`multipleSpecimen` = ?, "
                    + "`usesAutomatedPosting` = ?, "
                    + "`isLocal` = ?, "
                    + "`qcTable` = ?, "
                    + "`qcLevels` = ? "
                    + "WHERE `idInst` = " + instrument.getIdInst();
            PreparedStatement pStmt = con.prepareStatement(stmt);

            pStmt.setString(1, instrument.getInstName());
            pStmt.setInt(2, instrument.getInstNo());
            pStmt.setBoolean(3, instrument.getOnline());
            pStmt.setInt(4, instrument.getManufacturer());
            pStmt.setTimestamp(5, Convert.ToSQLDateTime(instrument.getDatePurchased()));
            pStmt.setString(6, instrument.getSerialNumber());
            pStmt.setString(7, instrument.getCommType());
            pStmt.setString(8, instrument.getFilePath());
            pStmt.setString(9, instrument.getTty());
            pStmt.setString(10, instrument.getPort());
            pStmt.setString(11, instrument.getIpAddress());
            pStmt.setString(12, instrument.getFileRegExpr());
            pStmt.setString(13, instrument.getResTable());
            pStmt.setString(14, instrument.getOrdTable());
            pStmt.setString(15, instrument.getXrefTable());
            pStmt.setString(16, instrument.getProcessedPath());
            pStmt.setString(17, instrument.getFileReader());
            pStmt.setBoolean(18, instrument.getUseOnlineCode());
            pStmt.setBytes(19, instrument.getComment());
            pStmt.setBoolean(20, instrument.getMultipleSpecimen());
            SQLUtil.SafeSetBoolean(pStmt, 21, instrument.getUsesAutomatedPosting());
            SQLUtil.SafeSetBoolean(pStmt, 22, instrument.getIsLocal());
            SQLUtil.SafeSetString(pStmt, 23, instrument.getQcTable());
            SQLUtil.SafeSetInteger(pStmt, 24, instrument.getQcLevels());

            pStmt.executeUpdate();

            pStmt.close();

            return true;

        } catch (Exception ex) {
            String message = ex.toString();
            //TODO:  Add logging for failures
            System.out.println(message);
            return false;
        }
    }

    public Instruments GetInstrumentByID(int ID) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        Instruments inst = new Instruments();
        try {
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + " WHERE `idInst` = " + ID;

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                //inst.setIdInst(rs.getInt("idInst"));
                //inst.setInstName(rs.getString("instName"));
                //inst.setInstNo(rs.getInt("instNo"));
                //inst.setOnline(rs.getInt("online"));
                setInstrumentFromResultSet(inst, rs);
            }
            rs.close();
            stmt.close();

            return inst;

        } catch (Exception ex2) {
            System.out.println(ex2.toString());
            return null;
        }
    }

    public Instruments GetInstrumentByNumber(int Number) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        Instruments inst = new Instruments();
        try {
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + " WHERE `instNo` = " + Number;

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                //inst.setIdInst(rs.getInt("idInst"));
                //inst.setInstName(rs.getString("instName"));
                //inst.setInstNo(rs.getInt("instNo"));
                //inst.setOnline(rs.getInt("online"));
                setInstrumentFromResultSet(inst, rs);
            }
            rs.close();
            stmt.close();

            return inst;

        } catch (Exception ex2) {
            System.out.println(ex2.toString());
            return null;
        }
    }

    public Instruments GetInstrumentByName(String name) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        Instruments inst = new Instruments();
        try {
            PreparedStatement stmt = null; //con.createStatement();

            String query = "SELECT * FROM " + table
                    + " WHERE `instName` = ?";

            stmt = createStatement(con, query, name);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                //inst.setIdInst(rs.getInt("idInst"));
                //inst.setInstName(rs.getString("instName"));
                //inst.setInstNo(rs.getInt("instNo"));
                //inst.setOnline(rs.getInt("online"));
                setInstrumentFromResultSet(inst, rs);
            }
            rs.close();
            stmt.close();

            return inst;

        } catch (Exception ex2) {
            System.out.println(ex2.toString());
            return null;
        }
    }
    
    public Instruments[] GetInstrumentsByCategoryName(String categoryName)
    {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        try {
            ArrayList< Instruments> instList = new ArrayList<>();
            
            String query = "SELECT i.* "
                    + " FROM instruments i"
                    + " INNER JOIN instrumentCategory ic"
                    + " ON i.instrumentCategoryId = id"
                    + " WHERE ic.categoryName = ?";
            PreparedStatement pStmt = con.prepareStatement(query);
            pStmt.setString(1, categoryName);
            ResultSet rs = pStmt.executeQuery();

            while (rs.next())
            {
                Instruments inst = new Instruments();
                setInstrumentFromResultSet(inst, rs);
                instList.add(inst);
            }
            rs.close();
            pStmt.close();

            Instruments[] instArray = new Instruments[instList.size()];
            instArray = instList.toArray(instArray);
            return instArray;

        } catch (Exception ex2) {
            System.out.println(ex2.toString());
            return null;
        }
    }

    public Instruments[] GetAllInstruments() {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        try {
            ArrayList< Instruments> instList = new ArrayList< Instruments>();
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table + ";";

            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                Instruments inst = new Instruments();
                //inst.setIdInst(rs.getInt("idInst"));
                //inst.setInstName(rs.getString("instName"));
                //inst.setInstNo(rs.getInt("instNo"));
                //inst.setOnline(rs.getInt("online"));
                setInstrumentFromResultSet(inst, rs);
                instList.add(inst);
            }
            rs.close();
            stmt.close();

            Instruments[] instArray = new Instruments[instList.size()];
            instArray = instList.toArray(instArray);
            return instArray;

        } catch (Exception ex2) {
            System.out.println(ex2.toString());
            return null;
        }
    }
    
    public Instruments[] GetInstrumentsForAutoPost() {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        try {
            ArrayList< Instruments> instList = new ArrayList< Instruments>();
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table + " "
                    + "WHERE `usesAutomatedPosting` = " + true;

            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                Instruments inst = new Instruments();
                setInstrumentFromResultSet(inst, rs);
                instList.add(inst);
            }
            rs.close();
            stmt.close();

            Instruments[] instArray = new Instruments[instList.size()];
            instArray = instList.toArray(instArray);
            return instArray;

        } catch (Exception ex2) {
            System.out.println(ex2.toString());
            return null;
        }
    }
    
    public ArrayList<Instruments> GetQCInsts(){
        ArrayList<Instruments> instList = new ArrayList< Instruments>();
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return instList;
        }
        
        try {
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table + " "
                    + "WHERE `qcTable` IS NOT NULL AND `qcTable` != ''";

            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                Instruments inst = new Instruments();
                setInstrumentFromResultSet(inst, rs);
                instList.add(inst);
            }
            rs.close();
            stmt.close();

            return instList;

        } catch (SQLException ex) {
            System.out.println(ex.toString());
            return instList;
        }
    }
    
    public ArrayList<Instruments> GetLCMSInsts(){
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        
        try {
            ArrayList<Instruments> instList = new ArrayList< Instruments>();
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table + " "
                    + "WHERE `lcmsInst` = " + true;

            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                Instruments inst = new Instruments();
                setInstrumentFromResultSet(inst, rs);
                instList.add(inst);
            }
            rs.close();
            stmt.close();

            return instList;

        } catch (SQLException ex) {
            System.out.println(ex.toString());
            return null;
        }
    }
    
    public ArrayList<Instruments> GetTranslationalInsts(){
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        
        try {
            ArrayList<Instruments> instList = new ArrayList< Instruments>();
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table + " "
                    + "WHERE `translationalInst` = " + true;

            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                Instruments inst = new Instruments();
                setInstrumentFromResultSet(inst, rs);
                instList.add(inst);
            }
            rs.close();
            stmt.close();

            return instList;

        } catch (SQLException ex) {
            System.out.println(ex.toString());
            return null;
        }
    }

    private Instruments setInstrumentFromResultSet(Instruments inst, ResultSet rs) throws SQLException {

        inst.setIdInst(rs.getInt("idInst"));
        inst.setInstName(rs.getString("instName"));
        inst.setInstNo(rs.getInt("instNo"));
        inst.setOnline(rs.getBoolean("online"));
        inst.setManufacturer(rs.getInt("manufacturer"));
        inst.setDatePurchased(rs.getTimestamp("datePurchased"));
        inst.setSerialNumber(rs.getString("serialNumber"));
        inst.setCommType(rs.getString("commType"));
        inst.setFilePath(rs.getString("filePath"));
        inst.setTty(rs.getString("tty"));
        inst.setPort(rs.getString("port"));
        inst.setIpAddress(rs.getString("ipAddress"));
        inst.setFileRegExpr(rs.getString("fileRegExpr"));
        inst.setResTable(rs.getString("resTable"));
        inst.setOrdTable(rs.getString("ordTable"));
        inst.setXrefTable(rs.getString("xrefTable"));
        inst.setProcessedPath(rs.getString("processedPath"));
        inst.setFileReader(rs.getString("fileReader"));
        inst.setUseOnlineCode(rs.getBoolean("useOnlineCode"));
        inst.setComment(rs.getBytes("comment"));
        inst.setMultipleSpecimen(rs.getBoolean("multipleSpecimen"));
        inst.setUsesAutomatedPosting(rs.getBoolean("usesAutomatedPosting"));
        inst.setIsLocal(rs.getBoolean("isLocal"));
        inst.setQcTable(rs.getString("qcTable"));
        inst.setQcLevels(rs.getInt("qcLevels"));
        inst.setLcmsInst(rs.getBoolean("lcmsInst"));
        inst.setTranslationalInst(rs.getBoolean("translationalInst"));
        inst.setPostingUseSpecimenType(rs.getBoolean("postingUseSpecimenType"));
        inst.setPostOnServer(rs.getBoolean("postOnServer"));

        return inst;
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
        String query = "SELECT `instruments`.`idInst`,\n"
                + "    `instruments`.`instName`,\n"
                + "    `instruments`.`instNo`,\n"
                + "    `instruments`.`online`,\n"
                + "    `instruments`.`manufacturer`,\n"
                + "    `instruments`.`datePurchased`,\n"
                + "    `instruments`.`serialNumber`,\n"
                + "    `instruments`.`commType`,\n"
                + "    `instruments`.`filePath`,\n"
                + "    `instruments`.`tty`,\n"
                + "    `instruments`.`port`,\n"
                + "    `instruments`.`ipAddress`,\n"
                + "    `instruments`.`fileRegExpr`,\n"
                + "    `instruments`.`resTable`,\n"
                + "    `instruments`.`ordTable`,\n"
                + "    `instruments`.`xrefTable`,\n"
                + "    `instruments`.`processedPath`,\n"
                + "    `instruments`.`fileReader`,\n"
                + "    `instruments`.`useOnlineCode`,\n"
                + "    `instruments`.`comment`,\n"
                + "    `instruments`.`multipleSpecimen`,\n"
                + "    `instruments`.`usesAutomatedPosting`,\n"
                + "    `instruments`.`isLocal`,\n"
                + "    `instruments`.`qcTable`,\n"
                + "    `instruments`.`qcLevels`,\n"
                + "    `instruments`.`lcmsInst`,\n"
                + "    `instruments`.`translationalInst`,\n"
                + "    `instruments`.`postingUseSpecimenType`,\n"
                + "    `instruments`.`postOnServer`,\n"
                + "    `instruments`.`instrumentCategoryId`\n"
                + "FROM `css`.`instruments` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, con);
    }
}
