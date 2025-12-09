/*
 * Computer Service & Support, Inc.  All Rights Reserved May 11, 2015
 */

package DAOS;

import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IStructureCheckable;
import DOS.ClientCopy;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
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
 * @date:   May 11, 2015  5:20:29 PM
 * @author: Ryan Piper ryan@csslis.com
 * 
 * @project: CSSLibrary
 * @file name: ClientCopyDAO.java  (UTF-8)
 *
 * @Description: 
 *
 */

public class ClientCopyDAO implements DAOInterface, IStructureCheckable {

    //--------------------------------------------------------------------------
    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`clientCopy`";

    private final ArrayList<String> fields = new ArrayList<>();
    ArrayList<Integer> generatedIDs = new ArrayList<>();

    //--------------------------------------------------------------------------
    public ClientCopyDAO() {
        fields.add("idClients");
        fields.add("faxName");
        fields.add("faxNo");
        fields.add("doctor");
        fields.add("client");
        fields.add("faxMemo");
        fields.add("isCascading");
    }

    @Override
    public Boolean Insert(Serializable obj) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }

        try {
            ClientCopy cc = (ClientCopy) obj;
            String stmt = GenerateInsertStatement(fields);
            PreparedStatement pStmt = con.prepareStatement(stmt);
            SetStatement(cc, pStmt);
            pStmt.executeUpdate();

            pStmt.close();

            return true;
        } catch (Exception ex) {
            String message = ex.toString();
            System.out.println(message);
            return false;
        }
    }

    @Override
    public Boolean Update(Serializable obj) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }
        try {
            ClientCopy cc = (ClientCopy) obj;
            String stmt = GenerateUpdateStatement(fields)
                    + " WHERE `id` = " + cc.getId();
            PreparedStatement pStmt = con.prepareStatement(stmt);

            SetStatement(cc, pStmt);

            pStmt.executeUpdate();

            pStmt.close();

            return true;
        } catch (Exception ex) {
            String message = ex.toString();
            System.out.println(message);
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
        try{
            ClientCopy cc = (ClientCopy) obj;
            Statement stmt = con.createStatement();
            String query = "DELETE FROM " + table + " "
                    + "WHERE `id` = " + cc.getId();
            
            int result = stmt.executeUpdate(query);
            
            stmt.close();
            
            return true;
        }catch (Exception ex) {
            String message = ex.toString();
            System.out.println(message);
            return false;
        }
    }

    @Override
    public Serializable getByID(Integer ID) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }
        try{
            ClientCopy cc = new ClientCopy();
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + " WHERE `id` = " + ID;

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                FromResultSet(cc, rs);
            }

            rs.close();
            stmt.close();

            return cc;
        }catch (Exception ex) {
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }
    
    public ClientCopy GetByClientIDAndDoctorNumber(Integer idClients, Integer doctor) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        try{
            ClientCopy cc = new ClientCopy();
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + " WHERE `idClients` = " + idClients
                    + " AND `doctor` = " + doctor;

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                FromResultSet(cc, rs);
            }

            rs.close();
            stmt.close();

            return cc;
        }catch (Exception ex) {
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }
    
    public ClientCopy GetByClientIDAndClientNumber(Integer idClients, Integer client) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        try{
            ClientCopy cc = new ClientCopy();
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + " WHERE `idClients` = " + idClients
                    + " AND `client` = " + client;

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                FromResultSet(cc, rs);
            }

            rs.close();
            stmt.close();

            return cc;
        }catch (Exception ex) {
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }
    
    public ClientCopy GetByClientIDAndFaxName(Integer idClients, String faxName) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        try{
            ClientCopy cc = new ClientCopy();
            PreparedStatement stmt = null; //con.createStatement();

            String query = "SELECT * FROM " + table
                    + " WHERE `idClients` = " + idClients
                    + " AND `faxName` = ?";

            stmt = createStatement(con, query, faxName);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                FromResultSet(cc, rs);
            }

            rs.close();
            stmt.close();

            return cc;
        }catch (Exception ex) {
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }
    
    public ClientCopy GetByClientIDAndFaxNumber(Integer idClients, String faxNumber) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        try{
            ClientCopy cc = new ClientCopy();
            PreparedStatement stmt = null; //con.createStatement();

            String query = "SELECT * FROM " + table
                    + " WHERE `idClients` = " + idClients
                    + " AND `faxNo` = ?";

            stmt = createStatement(con, query, faxNumber);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                FromResultSet(cc, rs);
            }

            rs.close();
            stmt.close();

            return cc;
        }catch (Exception ex) {
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }
    
    public ArrayList<ClientCopy> GetAllForClient(Integer clientId){
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        try{
            ArrayList<ClientCopy> list = new ArrayList<>();
            ClientCopy cc= null;
            Statement stmt = con.createStatement();
            String query = "SELECT * FROM " + table + " "
                    + "WHERE `idClients` = " + clientId;
            
            ResultSet rs = stmt.executeQuery(query);
            
            while(rs.next()){
                cc = new ClientCopy();
                cc = FromResultSet(cc, rs);
                list.add(cc);
            }
            
            rs.close();
            stmt.close();
            
            return list;
        }catch(Exception ex){
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }
    
    public ArrayList<ClientCopy> GetAllDoctorsForClient(Integer clientId){
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        try{
            ArrayList<ClientCopy> list = new ArrayList<>();
            ClientCopy cc= null;
            Statement stmt = con.createStatement();
            String query = "SELECT * FROM " + table + " "
                    + "WHERE `idClients` = " + clientId + " "
                    + "AND `doctor` IS NOT NULL";
            
            ResultSet rs = stmt.executeQuery(query);
            
            while(rs.next()){
                cc = new ClientCopy();
                cc = FromResultSet(cc, rs);
                list.add(cc);
            }
            
            rs.close();
            stmt.close();
            
            return list;
        }catch(Exception ex){
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }
    
    public ArrayList<ClientCopy> GetAllClientsForClient(Integer clientId){
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        try{
            ArrayList<ClientCopy> list = new ArrayList<>();
            ClientCopy cc= null;
            Statement stmt = con.createStatement();
            String query = "SELECT * FROM " + table + " "
                    + "WHERE `idClients` = " + clientId + " "
                    + "AND `client` IS NOT NULL";
            
            ResultSet rs = stmt.executeQuery(query);
            
            while(rs.next()){
                cc = new ClientCopy();
                cc = FromResultSet(cc, rs);
                list.add(cc);
            }
            
            rs.close();
            stmt.close();
            
            return list;
        }catch(Exception ex){
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }
    
    public ArrayList<ClientCopy> GetAllFaxContactsForClient(Integer clientId){
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        try{
            ArrayList<ClientCopy> list = new ArrayList<>();
            ClientCopy cc= null;
            Statement stmt = con.createStatement();
            String query = "SELECT * FROM " + table + " "
                    + "WHERE `idClients` = " + clientId + " "
                    + "AND `client` IS NULL "
                    + "AND `doctor` IS NULL";
            
            ResultSet rs = stmt.executeQuery(query);
            
            while(rs.next()){
                cc = new ClientCopy();
                cc = FromResultSet(cc, rs);
                list.add(cc);
            }
            
            rs.close();
            stmt.close();
            
            return list;
        }catch(Exception ex){
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }
    
    public ArrayList<Integer> GetDoctorNumbersForClient(Integer clientId){
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        try{
            ArrayList<Integer> list = new ArrayList<>();
            Statement stmt = con.createStatement();
            String query = "SELECT `doctor` FROM " + table + " "
                    + "WHERE `idClients` = " + clientId + " "
                    + "AND `doctor` IS NOT NULL";
            
            ResultSet rs = stmt.executeQuery(query);
            
            while(rs.next()){
                list.add(rs.getInt("doctor"));
            }
            
            rs.close();
            stmt.close();
            
            return list;
        }catch(Exception ex){
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }
    
    public ArrayList<Integer> GetClientNumbersForClient(Integer clientId){
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        try{
            ArrayList<Integer> list = new ArrayList<>();
            ClientCopy cc= null;
            Statement stmt = con.createStatement();
            String query = "SELECT `client` FROM " + table + " "
                    + "WHERE `idClients` = " + clientId + " "
                    + "AND `client` IS NOT NULL";
            
            ResultSet rs = stmt.executeQuery(query);
            
            while(rs.next()){
                list.add(rs.getInt("client"));
            }
            
            rs.close();
            stmt.close();
            
            return list;
        }catch(Exception ex){
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }
    
    public ArrayList<Integer> GetFaxContactIdsForClient(Integer clientId){
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        try{
            ArrayList<Integer> list = new ArrayList<>();
            ClientCopy cc= null;
            Statement stmt = con.createStatement();
            String query = "SELECT `id` FROM " + table + " "
                    + "WHERE `idClients` = " + clientId + " "
                    + "AND `client` IS NULL "
                    + "AND `doctor` IS NULL";
            
            ResultSet rs = stmt.executeQuery(query);
            
            while(rs.next()){
                list.add(rs.getInt("id"));
            }
            
            rs.close();
            stmt.close();
            
            return list;
        }catch(Exception ex){
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }
    
    public ArrayList<String> GetFaxContactNamesForClient(Integer clientId){
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        try{
            ArrayList<String> list = new ArrayList<>();
            ClientCopy cc= null;
            Statement stmt = con.createStatement();
            String query = "SELECT `faxName` FROM " + table + " "
                    + "WHERE `idClients` = " + clientId + " "
                    + "AND `client` IS NULL "
                    + "AND `doctor` IS NULL";
            
            ResultSet rs = stmt.executeQuery(query);
            
            while(rs.next()){
                list.add(rs.getString("faxName"));
            }
            
            rs.close();
            stmt.close();
            
            return list;
        }catch(Exception ex){
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }
    
    public boolean DoctorCopyExists(Integer ClientID, Integer docID){
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }
        try{
            boolean exists = false;
            String stmt = "SELECT * FROM " + table + " "
                    + "WHERE `idClients` = ? "
                    + "AND `doctor` = ?";
            PreparedStatement pStmt = con.prepareStatement(stmt);
            
            SQLUtil.SafeSetInteger(pStmt, 1, ClientID);
            SQLUtil.SafeSetInteger(pStmt, 2, docID);
            
            ResultSet rs = pStmt.executeQuery();
            
            if(rs.next()){
                exists = true;
            }
            
            return exists;
        }catch(Exception ex){
            String message = ex.toString();
            System.out.println(message);
            return false;
        }
    }
    
    public boolean ClientCopyExists(Integer ClientID, Integer cliId){
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }
        try{
            boolean exists = false;
            String stmt = "SELECT * FROM " + table + " "
                    + "WHERE `idClients` = ? "
                    + "AND `client` = ?";
            PreparedStatement pStmt = con.prepareStatement(stmt);
            
            SQLUtil.SafeSetInteger(pStmt, 1, ClientID);
            SQLUtil.SafeSetInteger(pStmt, 2, cliId);
            
            ResultSet rs = pStmt.executeQuery();
            
            if(rs.next()){
                exists = true;
            }
            
            return exists;
        }catch(Exception ex){
            String message = ex.toString();
            System.out.println(message);
            return false;
        }
    }
    
    public boolean FaxContactNameExists(Integer ClientID, String faxName){
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }
        try{
            boolean exists = false;
            String stmt = "SELECT * FROM " + table + " "
                    + "WHERE `idClients` = ? "
                    + "AND `faxName` = ?";
            PreparedStatement pStmt = con.prepareStatement(stmt);
            
            SQLUtil.SafeSetInteger(pStmt, 1, ClientID);
            SQLUtil.SafeSetString(pStmt, 2, faxName);
            
            ResultSet rs = pStmt.executeQuery();
            
            if(rs.next()){
                exists = true;
            }
            
            return exists;
        }catch(Exception ex){
            String message = ex.toString();
            System.out.println(message);
            return false;
        }
    }
    
    //--------------------------------------------------------------------------
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

    //--------------------------------------------------------------------------
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

    //--------------------------------------------------------------------------
    private ClientCopy FromResultSet(ClientCopy obj, ResultSet rs) throws SQLException {
        obj.setId(rs.getInt("id"));
        obj.setIdClients(rs.getInt("idClients"));
        obj.setFaxName(rs.getString("faxName"));
        obj.setFaxNo(rs.getString("faxNo"));
        obj.setDoctor(rs.getInt("doctor"));
        obj.setClient(rs.getInt("client"));
        obj.setFaxMemo(rs.getString("faxMemo"));
        obj.setIsCascading(rs.getBoolean("isCascading"));

        return obj;
    }

    //--------------------------------------------------------------------------
    private PreparedStatement SetStatement(ClientCopy obj, PreparedStatement pStmt) throws SQLException {
        SQLUtil.SafeSetInteger(pStmt, 1, obj.getIdClients());
        SQLUtil.SafeSetString(pStmt, 2, obj.getFaxName());
        SQLUtil.SafeSetString(pStmt, 3, obj.getFaxNo());
        SQLUtil.SafeSetInteger(pStmt, 4, obj.getDoctor());
        SQLUtil.SafeSetInteger(pStmt, 5, obj.getClient());
        SQLUtil.SafeSetString(pStmt, 6, obj.getFaxMemo());
        SQLUtil.SafeSetBoolean(pStmt, 7, obj.getIsCascading());
        
        return pStmt;
    }

    @Override
    public String structureCheck() {
        return DatabaseStructureCheck.structureCheck(fields, table, con);
    }
}
