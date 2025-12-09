package DAOS;

import DAOS.IDAOS.IStructureCheckable;
import DOS.ReflexMultichoice;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.Convert;
import Utility.SQLUtil;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;


public class ReflexMultichoiceDAO implements IStructureCheckable
{
	public static final String table = "`reflexMultichoice`";
        
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);

	//<editor-fold defaultstate="collapsed" desc="insert (Connection,ReflexMultichoice)">
	public static ReflexMultichoice insert(Connection con, ReflexMultichoice obj) throws SQLException, IllegalArgumentException, NullPointerException
	{
            if (obj == null)
            {
                throw new IllegalArgumentException("ReflexMultichoiceDAO::Insert: Received a NULL ReflexMultichoice object");
            }

            if (con == null || false == con.isValid(2))
            {
                throw new IllegalArgumentException("ReflexMultichoiceDAO:Insert: Received a [NULL] or invalid Connection object");
            }

            String sql = "INSERT INTO " + table
                + "("
                + "  testId,"
                + "  isAbnormal,"
                + "  multichoiceId,"
                + "  choiceOrder,"
                + "  notMultichoiceId,"
                + "  hasRemarkId,"
                + "  putRemarkId,"
                + "  reflexedTestId,"
                + "  createdBy,"
                + "  createdDate,"
                + "  deactivatedBy,"
                + "  deactivatedDate,"
                + "  active"
                + ")"
                + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";

            try (PreparedStatement pStmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS))
            {
                int i = 0;
                SQLUtil.SafeSetInteger(pStmt, ++i, obj.getTestId());
                SQLUtil.SafeSetBoolean(pStmt, ++i, obj.isIsAbnormal());
                SQLUtil.SafeSetInteger(pStmt, ++i, obj.getMultichoiceId());
                SQLUtil.SafeSetInteger(pStmt, ++i, obj.getNotMultichoiceId());
                SQLUtil.SafeSetInteger(pStmt, ++i, obj.getHasRemarkId());
                SQLUtil.SafeSetInteger(pStmt, ++i, obj.getPutRemarkId());
                SQLUtil.SafeSetInteger(pStmt, ++i, obj.getReflexedTestId());
                SQLUtil.SafeSetInteger(pStmt, ++i, obj.getCreatedBy());
                SQLUtil.SafeSetTimeStamp(pStmt, ++i, Convert.ToSQLDateTime(obj.getCreatedDate()));
                SQLUtil.SafeSetInteger(pStmt, ++i, obj.getDeactivatedBy());
                SQLUtil.SafeSetTimeStamp(pStmt, ++i, Convert.ToSQLDateTime(obj.getDeactivatedDate()));
                SQLUtil.SafeSetBoolean(pStmt, ++i, obj.isActive());

                Integer newId = null;
                sql = pStmt.toString();
                pStmt.executeUpdate();
                ResultSet rs = pStmt.getGeneratedKeys();
                if (rs.next())
                {
                    newId = rs.getInt(1);
                }
                if (newId == null || newId <= 0)
                {
                    throw new NullPointerException("ReflexMultichoiceDAO::Insert: Cannot retrieve generated identifier for inserted row.");
                }
                obj.setIdReflexMultichoice(newId);
            }
            catch (Exception ex)
            {
                System.out.println(ex.getMessage() + " " + sql);
                throw ex;
            }

            return obj;
	}
	//</editor-fold>

	//<editor-fold defaultstate="collapsed" desc="insert (ReflexMultichoice)">
	public static ReflexMultichoice insert(ReflexMultichoice obj) throws SQLException, IllegalArgumentException, NullPointerException
	{
            if (obj == null)
            {
                throw new IllegalArgumentException("ReflexMultichoiceDAO::Insert: Received a NULL ReflexMultichoice object");
            }

            Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
            Connection con = dbs.getConnection(true);
            if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);

            return insert(con, obj);
	}
	//</editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="insertReflexMultichoiceGetId (ReflexMultichoice)">
        public Integer InsertReflexMultichoiceGetId(ReflexMultichoice obj) throws SQLException
        {
            try 
            {
                if (con.isClosed())
                    con = CheckDBConnection.Check(dbs, con);
            } catch (SQLException sex)
            {
                System.out.println(sex.toString());
                return null;
            }
            
            String sql = "INSERT INTO " + table
                    + "("
                    + "  testId,"
                    + "  isAbnormal,"
                    + "  multichoiceId,"
                    + "  choiceOrder,"
                    + "  notMultichoiceId,"
                    + "  hasRemarkId,"
                    + "  putRemarkId,"
                    + "  reflexedTestId,"
                    + "  createdBy,"
                    + "  createdDate,"
                    + "  deactivatedBy,"
                    + "  deactivatedDate,"
                    + "  active"
                    + ")"
                    + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
            
            try (PreparedStatement pStmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS))
            {
                int i = 0;
                SQLUtil.SafeSetInteger(pStmt, ++i, obj.getTestId());
                SQLUtil.SafeSetBoolean(pStmt, ++i, obj.isIsAbnormal());
                SQLUtil.SafeSetInteger(pStmt, ++i, obj.getMultichoiceId());
                SQLUtil.SafeSetInteger(pStmt, ++i, obj.getChoiceOrder());
                SQLUtil.SafeSetInteger(pStmt, ++i, obj.getNotMultichoiceId());
                SQLUtil.SafeSetInteger(pStmt, ++i, obj.getHasRemarkId());
                SQLUtil.SafeSetInteger(pStmt, ++i, obj.getPutRemarkId());
                SQLUtil.SafeSetInteger(pStmt, ++i, obj.getReflexedTestId());
                SQLUtil.SafeSetInteger(pStmt, ++i, obj.getCreatedBy());
                SQLUtil.SafeSetTimeStamp(pStmt, ++i, Convert.ToSQLDateTime(obj.getCreatedDate()));
                SQLUtil.SafeSetInteger(pStmt, ++i, obj.getDeactivatedBy());
                SQLUtil.SafeSetTimeStamp(pStmt, ++i, Convert.ToSQLDateTime(obj.getDeactivatedDate()));
                SQLUtil.SafeSetBoolean(pStmt, ++i, obj.isActive());

                Integer newId = null;
                sql = pStmt.toString();
                pStmt.executeUpdate();
                /*ResultSet rs = pStmt.getGeneratedKeys();
                if (rs.next())
                {
                    newId = rs.getInt(1);
                }
                if (newId == null || newId <= 0)
                {
                    throw new NullPointerException("ReflexMultichoiceDAO::Insert: Cannot retrieve generated identifier for inserted row.");
                }
                obj.setIdReflexMultichoice(newId);*/
                
                ResultSet rs = pStmt.getGeneratedKeys();
                Integer key = -1;
                if (rs != null && rs.next())
                {
                    key = rs.getInt(1);
                }
                pStmt.close();
            
            return key;
            }
            catch (Exception ex)
            {
                System.out.println(ex.getMessage() + " " + sql);
                throw ex;
            }
        }
        //</editor-fold>

	//<editor-fold defaultstate="collapsed" desc="update (Connection, ReflexMultichoice)">
	public static void update(Connection con, ReflexMultichoice obj) throws SQLException, IllegalArgumentException, NullPointerException
	{
            if (obj == null) throw new IllegalArgumentException("ReflexMultichoiceDAO::Update: Received a NULL ReflexMultichoice object.");

            if (con == null || false == con.isValid(2))
            {
                throw new IllegalArgumentException("ReflexMultichoiceDAO:Update: Received a [NULL] or invalid Connection object");
            }

            String sql = "UPDATE " + table + " SET "
                + " testId = ?,"
                + " isAbnormal = ?,"
                + " multichoiceId = ?,"
                + " choiceOrder = ?,"
                + " notMultichoiceId = ?,"
                + " hasRemarkId = ?,"
                + " putRemarkId = ?,"
                + " reflexedTestId = ?,"
                + " createdBy = ?,"
                + " createdDate = ?,"
                + " deactivatedBy = ?,"
                + " deactivatedDate = ?,"
                + " active = ?"
                + " WHERE idReflexMultichoice = " + obj.getIdReflexMultichoice();

            String sqlOutput = "";
            try (PreparedStatement pStmt = con.prepareStatement(sql))
            {
                int  i = 0;
                SQLUtil.SafeSetInteger(pStmt, ++i, obj.getTestId());
                SQLUtil.SafeSetBoolean(pStmt, ++i, obj.isIsAbnormal());
                SQLUtil.SafeSetInteger(pStmt, ++i, obj.getMultichoiceId());
                SQLUtil.SafeSetInteger(pStmt, ++i, obj.getChoiceOrder());
                SQLUtil.SafeSetInteger(pStmt, ++i, obj.getNotMultichoiceId());
                SQLUtil.SafeSetInteger(pStmt, ++i, obj.getHasRemarkId());
                SQLUtil.SafeSetInteger(pStmt, ++i, obj.getPutRemarkId());
                SQLUtil.SafeSetInteger(pStmt, ++i, obj.getReflexedTestId());
                SQLUtil.SafeSetInteger(pStmt, ++i, obj.getCreatedBy());
                SQLUtil.SafeSetTimeStamp(pStmt, ++i, Convert.ToSQLDateTime(obj.getCreatedDate()));
                SQLUtil.SafeSetInteger(pStmt, ++i, obj.getDeactivatedBy());
                SQLUtil.SafeSetTimeStamp(pStmt, ++i, Convert.ToSQLDateTime(obj.getDeactivatedDate()));
                SQLUtil.SafeSetBoolean(pStmt, ++i, obj.isActive());
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
	//</editor-fold>

	//<editor-fold defaultstate="collapsed" desc="update (ReflexMultichoice)">
	public static void update(ReflexMultichoice obj) throws SQLException, IllegalArgumentException, NullPointerException
	{
            if (obj == null) throw new IllegalArgumentException("ReflexMultichoiceDAO::Update: Received a NULL ReflexMultichoice object.");

            Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
            Connection con = dbs.getConnection(true);
            if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);

            update(con, obj);
	}
	//</editor-fold>

	//<editor-fold defaultstate="collapsed" desc="get (Connection, ReflexMultichoice, boolean (forUpdate))">
	public static ReflexMultichoice get(Connection con, Integer id, boolean forUpdate) throws SQLException, IllegalArgumentException, NullPointerException
	{
            if (id == null || id <= 0)
            {
                throw new IllegalArgumentException("ReflexMultichoiceDAO::get: Received a NULL or empty ReflexMultichoice object.");
            }

            if (con == null || false == con.isValid(2))
            {
                throw new IllegalArgumentException("ReflexMultichoiceDAO:get: Received a [NULL] or invalid Connection object");
            }

            String sql = "SELECT * FROM " + table + " WHERE idReflexMultichoice = " + String.valueOf(id);
            if (forUpdate)
            {
                sql += " FOR UPDATE ";
            }

            ReflexMultichoice obj = null;

            try (PreparedStatement pStmt = con.prepareStatement(sql))
            {

                ResultSet rs = pStmt.executeQuery();
                if (rs.next())
                {
                    obj = objectFromResultSet(rs);
                }
            }
            catch (SQLException ex)
            {
                System.out.println(ex.getMessage() + " " + sql);
                throw new SQLException(ex.getMessage() + " " + sql);
            }

            return obj;
	}
	//</editor-fold>

	//<editor-fold defaultstate="collapsed" desc="get (ReflexMultichoice)">
	public static ReflexMultichoice get(Integer id) throws SQLException, IllegalArgumentException, NullPointerException
	{
            if (id == null || id <= 0)
            {
                throw new IllegalArgumentException("ReflexMultichoiceDAO::get: Received a NULL or empty ReflexMultichoice object.");
            }

            Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
            Connection con = dbs.getConnection(true);
            if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);

            return get(con, id, false); // not 'for update'
	}
	//</editor-fold>
        
        //<editor-fold defaultstate="collapsed" desc="getReflexesForTest (testID)">
        public ArrayList<ReflexMultichoice> getReflexesForTest(Integer testID)
        {           
            ArrayList<ReflexMultichoice> reflexes = new ArrayList<>();
            
            if (testID == null || testID <= 0)
            {
                throw new IllegalArgumentException("ReflexMultichoiceDAO::get: Received a NULL or empty ReflexMultichoice object.");
            }

            try
            {
                if (con.isClosed())
                {
                    con = CheckDBConnection.Check(dbs, con);
                }
                
                if (con == null || false == con.isValid(2))
                {
                    throw new IllegalArgumentException("ReflexMultichoiceDAO:get: Received a [NULL] or invalid Connection object");
                }
                
                String stmt = "SELECT * FROM " + table + " WHERE testId = " + testID
                    + " AND active = 1";
                
                PreparedStatement pStmt = con.prepareStatement(stmt);
                
                ResultSet rs = pStmt.executeQuery();
                while (rs.next())
                {
                    reflexes.add(objectFromResultSet(rs));
                }
                
                return reflexes;
            }
            catch (SQLException sex)
            {
                System.out.println(sex.getMessage());
                return null;
            }
        }
        //</editor-fold>
        
        // <editor-fold desc="getReflexesByReflexedTest (reflexedTestId)" defaultstate="collapsed">
        public static ArrayList<ReflexMultichoice> getReflexesByReflexedTest(Integer reflexedTestId) throws SQLException, IllegalArgumentException, NullPointerException
        {
            if (reflexedTestId == null || reflexedTestId <= 0)
            {
                throw new IllegalArgumentException("ReflexMultichoiceDAO::getReflexesByReflexedTest: Received a NULL or empty ReflexMultichoice object.");
            }

            Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
            Connection con = dbs.getConnection(true);
            if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);

            return getReflexesByReflexedTest(con, reflexedTestId); // not 'for update'
        }
        // </editor-fold>
        
        // <editor-fold desc="getReflexesByReflexedTest (con, reflexedTestId)" defaultstate="collapsed">
        public static ArrayList<ReflexMultichoice> getReflexesByReflexedTest(Connection con, Integer reflexedTestId)  
                throws SQLException, IllegalArgumentException, NullPointerException
        {
            ArrayList<ReflexMultichoice> reflexes = new ArrayList<>();
            
            if (reflexedTestId == null || reflexedTestId <= 0)
            {
                throw new IllegalArgumentException("ReflexMultichoiceDAO::getReflexesByReflexedTest: Received a NULL or empty ReflexMultichoice object.");
            }

            if (con == null || false == con.isValid(2))
            {
                throw new IllegalArgumentException("ReflexMultichoiceDAO:getReflexesByReflexedTest: Received a [NULL] or invalid Connection object");
            }

            String sql = "SELECT * FROM " + table + " WHERE reflexedTestId = " + String.valueOf(reflexedTestId);
            
            PreparedStatement pStmt = con.prepareStatement(sql);
                
            ResultSet rs = pStmt.executeQuery();
            while (rs.next())
            {
                reflexes.add(objectFromResultSet(rs));
            }

            return reflexes;
        }
        //</editor-fold>

	//<editor-fold defaultstate="collapsed" desc="objectFromResultSet (ResultSet)">
	public static ReflexMultichoice objectFromResultSet(ResultSet rs) throws SQLException, NullPointerException
	{
            ReflexMultichoice obj = new ReflexMultichoice();
            obj.setIdReflexMultichoice(SQLUtil.getInteger(rs,"idReflexMultichoice"));
            obj.setTestId(SQLUtil.getInteger(rs,"testId"));
            obj.setIsAbnormal(rs.getBoolean("isAbnormal"));
            obj.setMultichoiceId(SQLUtil.getInteger(rs,"multichoiceId"));
            obj.setChoiceOrder(SQLUtil.getInteger(rs, "choiceOrder"));
            obj.setNotMultichoiceId(SQLUtil.getInteger(rs,"notMultichoiceId"));
            obj.setHasRemarkId(SQLUtil.getInteger(rs,"hasRemarkId"));
            obj.setPutRemarkId(SQLUtil.getInteger(rs,"putRemarkId"));
            obj.setReflexedTestId(SQLUtil.getInteger(rs,"reflexedTestId"));
            obj.setCreatedBy(SQLUtil.getInteger(rs,"createdBy"));
            obj.setCreatedDate(rs.getDate("createdDate"));
            obj.setDeactivatedBy(SQLUtil.getInteger(rs,"deactivatedBy"));
            obj.setDeactivatedDate(rs.getDate("deactivatedDate"));
            obj.setActive(rs.getBoolean("active"));

            return obj;
	}
	//</editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="deactivateRuleByTestID (testID, userID)">
        public boolean DeactivateRuleByTestID(Integer testID, Integer userID)
        {
            try 
            {
                if (con.isClosed())
                    con = CheckDBConnection.Check(dbs, con);
            }
            catch (SQLException sex) 
            {
                System.out.println(sex.toString());
                return false;
            }

            try
            {
                String stmt = "UPDATE " + table + " SET "
                    + "`deactivatedBy = ?,"
                    + "'deactivatedDate = NOW(),"
                    + "`active` = ? "
                    + "WHERE `idtests` = ?";

                PreparedStatement pStmt = con.prepareStatement(stmt);

                SQLUtil.SafeSetInteger(pStmt, 1, userID);
                pStmt.setBoolean(3, false);
                SQLUtil.SafeSetInteger(pStmt, 4, testID);

                pStmt.executeUpdate();

                pStmt.close();

                return true;
            } catch (SQLException ex) 
            {
                System.out.println(ex.toString());
                return false;
            }
        }
        // </editor-fold>
        
    @Override
    public String structureCheck() {
        String query = "SELECT `reflexMultichoice`.`idReflexMultichoice`,\n"
                + "    `reflexMultichoice`.`testId`,\n"
                + "    `reflexMultichoice`.`isAbnormal`,\n"
                + "    `reflexMultichoice`.`multichoiceId`,\n"
                + "    `reflexMultichoice`.`choiceOrder`,\n"
                + "    `reflexMultichoice`.`notMultichoiceId`,\n"
                + "    `reflexMultichoice`.`hasRemarkId`,\n"
                + "    `reflexMultichoice`.`putRemarkId`,\n"
                + "    `reflexMultichoice`.`reflexedTestId`,\n"
                + "    `reflexMultichoice`.`createdBy`,\n"
                + "    `reflexMultichoice`.`createdDate`,\n"
                + "    `reflexMultichoice`.`deactivatedBy`,\n"
                + "    `reflexMultichoice`.`deactivatedDate`,\n"
                + "    `reflexMultichoice`.`active`\n"
                + "FROM `css`.`reflexMultichoice` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, con);
    }
        
}
