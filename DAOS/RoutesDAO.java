/*
 * Computer Service & Support, Inc.  All Rights Reserved Jul 9, 2014
 */
package DAOS;

import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IStructureCheckable;
import DOS.Routes;
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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @date: Jul 9, 2014 3:02:32 PM
 * @author: Ryan Piper ryan@csslis.com
 *
 * @project: CSSLibrary
 * @file name: RoutesDAO.java (UTF-8)
 *
 * @Description:
 *
 */
public class RoutesDAO implements DAOInterface, IStructureCheckable {

    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`routes`";

    private final ArrayList<String> fields = new ArrayList<>();

    /**
     * All fields except idroutes
     */
    public RoutesDAO() {
        fields.add("routeName");
        fields.add("description");
        fields.add("created");
        fields.add("createdBy");
    }

    @Override
    public Boolean Insert(Serializable obj) {

        Routes rte = (Routes) obj;

        try {
            try {
                if (con.isClosed()) {
                    con = CheckDBConnection.Check(dbs, con);
                }
            } catch (SQLException sex) {
                System.out.println(sex.toString());
                return false;
            }
            
            String stmt = GenerateInsertStatement(fields);
            PreparedStatement pStmt = con.prepareStatement(stmt);
            SetStatementFromRoute(rte, pStmt);
            
            pStmt.executeUpdate();
            
            pStmt.close();
            
            return true;
        }catch(SQLException ex){
            Logger.getLogger(Routes.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Exception inserting route: " + ex.toString());
            return false;
        }
    }

    @Override
    public Boolean Update(Serializable obj) {
        Routes rte = (Routes) obj;

        try {
            try {
                if (con.isClosed()) {
                    con = CheckDBConnection.Check(dbs, con);
                }
            } catch (SQLException sex) {
                System.out.println(sex.toString());
                return false;
            }
            
            String stmt = GenerateUpdateStatement(fields) + " WHERE `idroutes` = " + rte.getIdroutes();
            PreparedStatement pStmt = con.prepareStatement(stmt);
            SetStatementFromRoute(rte, pStmt);
            
            pStmt.executeUpdate();
            
            pStmt.close();
            
            return true;
        }catch(SQLException ex){
            Logger.getLogger(Routes.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Exception Updating route: " + ex.toString());
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
        return false;
    }

    @Override
    public Serializable getByID(Integer ID) {
        try{
            return GetRouteByID(ID);
        }catch(Exception ex){
            Logger.getLogger(Routes.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Exception Route getByID: " + ex.toString());
            return false;
        }
    }
    
    public boolean InsertRouteWithID(Routes rte){
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }
        
        try{
            String stmt = "INSERT INTO " + table + " ("
                    + "`idroutes`, "
                    + "`routeName`, "
                    + "`description`, "
                    + "`created`, "
                    + "`createdBy`) "
                    + "VALUES (?,?,?,?,?)";
            
            PreparedStatement pStmt = con.prepareStatement(stmt);
            
            SQLUtil.SafeSetInteger(pStmt, 1, rte.getIdroutes());
            SQLUtil.SafeSetString(pStmt, 2, rte.getRouteName());
            SQLUtil.SafeSetString(pStmt, 3, rte.getDescription());
            SQLUtil.SafeSetTimeStamp(pStmt, 4, Convert.ToSQLDateTime(rte.getCreated()));
            SQLUtil.SafeSetInteger(pStmt, 5, rte.getCreatedBy());
            
            pStmt.executeUpdate();
            
            pStmt.close();
            
            return true;
        }catch(SQLException ex){
            System.out.println("Exception InsertRouteWithID: " + ex.toString());
            return false;
        }
    }
    
    public Routes GetRouteByName(String name){
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        
        try{
            Routes rte = new Routes();
            
            String query = "SELECT * FROM " + table + " "
                    + "WHERE `routeName` = ?";
            
            PreparedStatement pStmt = con.prepareStatement(query);
            
            SQLUtil.SafeSetString(pStmt, 1, name);

            ResultSet rs = pStmt.executeQuery();
            
            if(rs.next()){
                SetRouteFromResultSet(rte, rs);
            }
            
            rs.close();
            pStmt.close();
            
            return rte;
        }catch(SQLException ex){
            Logger.getLogger(Routes.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Exception GetRouteByName: " + ex.toString());
            return null;
        }
    }
    
    public ArrayList<Routes> GetAllRoutes(){
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        
        try{
            ArrayList<Routes> list = new ArrayList<>();
            
            Statement stmt = con.createStatement();
            String query = "SELECT * FROM " + table + " ";

            ResultSet rs = stmt.executeQuery(query);

            while (rs.next())
            {
                Routes rte = new Routes();
                SetRouteFromResultSet(rte,rs);
                list.add(rte);
            }
            
            rs.close();
            stmt.close();
            
            return list;
        }catch(SQLException ex){
            System.out.println("Exception GetAllRoutes: " + ex.toString());
            return null;
        }
    }

    public Routes GetRouteByID(int ID) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        
        try{
            Routes rte = new Routes();
            
            String query = "SELECT * FROM " + table + " "
                    + "WHERE `idroutes` = " + ID;
            
            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery(query);
            
            if(rs.next()){
                SetRouteFromResultSet(rte, rs);
            }
            
            rs.close();
            stmt.close();
            
            return rte;
        }catch(SQLException ex){
            Logger.getLogger(Routes.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Exception GetRouteByID: " + ex.toString());
            return null;
        }
    }
    
    private String GenerateInsertStatement(ArrayList<String> fields) {

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

    private String GenerateUpdateStatement(ArrayList<String> fields) {

        String stmt = "UPDATE " + table + " SET";
        for (int i = 0; i < fields.size(); ++i) {
            stmt += " `" + fields.get(i) + "` = ?";
            if (i != fields.size() - 1) {
                stmt += ",";
            }
        }
        return stmt;
    }

    private Routes SetRouteFromResultSet(Routes rte, ResultSet rs) throws SQLException {
        rte.setIdroutes(rs.getInt("idroutes"));
        rte.setRouteName(rs.getString("routeName"));
        rte.setDescription(rs.getString("description"));
        rte.setCreated(rs.getTimestamp("created"));
        rte.setCreatedBy(rs.getInt("createdBy"));

        return rte;
    }

    private PreparedStatement SetStatementFromRoute(Routes rte, PreparedStatement pStmt) throws SQLException {
        SQLUtil.SafeSetString(pStmt, 1, rte.getRouteName());
        SQLUtil.SafeSetString(pStmt, 2, rte.getDescription());
        SQLUtil.SafeSetTimeStamp(pStmt, 3, Convert.ToSQLDateTime(rte.getCreated()));
        SQLUtil.SafeSetInteger(pStmt, 4, rte.getCreatedBy());

        return pStmt;
    }
    
    @Override
    public String structureCheck() {
        return DatabaseStructureCheck.structureCheck(fields, table, con);
    }
}
