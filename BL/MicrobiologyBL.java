package BL;

import DAOS.InstrumentDAO;
import DAOS.MicroInstrumentCommentDAO;
import DAOS.PreferencesDAO;
import DOS.Instruments;
import DOS.MicroInstrumentComment;
import DOS.MicroOrders;
import Database.CheckDBConnection;
import Utility.Convert;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @date: Jun 30, 2015 11:10:53 AM
 * @author: Ryan Piper ryan@csslis.com
 *
 * @project: CSSLibrary
 * @file name: MicrobiologyBL.java (UTF-8)
 *
 * @Description:
 *
 */
public class MicrobiologyBL
{

    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);

    //Naked Constructor
    public MicrobiologyBL() {

    }
    
    /**
     * Returns a MicroOrders class object from an Accession. Returns null if
     * nothing is found.
     *
     * @param acc String of Accession
     * @return MicroOrders Class Object
     */
    public MicroOrders GetMicroOrderByAccession(String acc) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        try {
            PreferencesDAO pDAO = new PreferencesDAO();
            int cultureTestID = pDAO.getInteger("CultureTestID");

            String query = "SELECT mo.`idmicroOrders`, "
                    + "mo.`idmicroOrders`, "
                    + "mo.`idResults`, "
                    + "mo.`idmicroSites`, "
                    + "mo.`idmicroSources`, "
                    + "mo.`comment`, "
                    + "mo.`complete`, "
                    + "mo.`completedBy`, "
                    + "mo.`completedOn`, "
                    + "mo.`colonyCount`, "
                    + "mo.`finalReport`, "
                    + "mo.`prelimReport`, "
                    + "mo.`gramStain` "
                    + "FROM `orders` o "
                    + "LEFT JOIN `results` r "
                    + "	ON o.`idorders` = r.`orderId` "
                    + "LEFT JOIN `microOrders` mo "
                    + "	ON r.`idresults` = mo.`idResults` "
                    + "WHERE o.`accession` = '" + acc + "' "
                    + "AND r.`testId` = " + cultureTestID;

            MicroOrders mo = new MicroOrders();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                MicroOrderFromResultSet(mo, rs);
            }

            rs.close();
            stmt.close();

            return mo;
        } catch (Exception ex) {
            System.out.println("Exception Fetching MicroOrder By Accession: " + ex.toString());
            return null;
        }
    }

    /**
     * Populates a MicroOrders class object from a ResultSet, mostly
     * used in other Methods that query for a MicroOrder.
     *
     * @param obj MicroOrders class object to be populated
     * @param rs ResultSet from which the MicroOrder is populated
     * @return Populated MicroOrders object.
     * @throws SQLException
     */
    private MicroOrders MicroOrderFromResultSet(MicroOrders obj, ResultSet rs) throws SQLException {
        obj.setIdmicroOrders(rs.getInt("idmicroorders"));
        obj.setIdResults(rs.getInt("idResults"));
        obj.setIdmicroSites(rs.getInt("idmicroSites"));
        obj.setIdmicroSources(rs.getInt("idmicroSources"));
        obj.setComment(rs.getString("comment"));
        obj.setComplete(rs.getBoolean("complete"));
        obj.setCompletedBy(rs.getInt("completedBy"));
        obj.setCompletedOn(rs.getTimestamp("completedOn"));
        obj.setColonyCount(rs.getString("colonyCount"));
        obj.setFinalReport(rs.getString("finalReport"));
        obj.setPrelimReport(rs.getString("prelimReport"));
        obj.setGramStain(rs.getString("gramStain"));
        return obj;
    }

    /**
     * Searches for Pending Microbiology tests by a combination of the past in
     * variables. If an Accession is passed in all other variables are ignored.
     * The returned class just has 3 fields and is part of the MicrobiologyBL.
     *
     * @param accession String Accession of Order, null if not used.
     * @param site
     * @param source
     * @param patientId Integer of the patientId, null if not used,
     * @param from java.util.Date of date to Start search, null if not used.
     * @param to java.util.Date of date to End search, null if not used.
     * @param useOrderDate Otherwise uses specimen date
     * @return List of MicroSearchObjects
     */
    public List<MicroSearchObject> SearchPendingResults(String accession, String site, String source, Integer patientId, Date from, Date to, boolean useOrderDate)
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
            String query = "SELECT o.`accession` AS 'Accession', "
                    + "s.`siteName` AS Site, so.`sourceName` AS Source, "
                    + "CONCAT(TRIM(p.`lastName`), ', ', TRIM(p.`firstName`)) AS 'Patient', "
                    + "o.`orderDate` AS 'OrderDate', "
                    + "mo.`idresults` AS 'ResultId', "
                    + "mo.`complete` AS 'Complete' "
                    + "FROM `microOrders` mo "
                    + "LEFT JOIN `results` r ON mo.`idResults` = r.`idresults` "
                    + "LEFT JOIN `microSites` s ON mo.`idmicroSites` = s.`idmicroSites` "
                    + "LEFT JOIN `microSources` so ON mo.`idmicroSources` = so.`idmicroSources` "
                    + "LEFT JOIN `orders` o  ON r.`orderId` = o.`idorders` "
                    + "LEFT JOIN `patients` p ON o.`patientId` = p.`idpatients` "
                    //+ "WHERE r.`printAndTransmitted` = false "
                    + " WHERE r.isInvalidated = b'0' AND mo.`complete` = b'0' "
                    + BuildSearchWhereClause(accession, site, source, patientId, from, to, useOrderDate);

            ArrayList<MicroSearchObject> list = new ArrayList<>();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                list.add(new MicroSearchObject(
                        rs.getString("Accession"),
                        rs.getString("Site"),
                        rs.getString("Source"),
                        rs.getString("Patient"),
                        rs.getDate("OrderDate"),
                        rs.getInt("ResultId"),
                        rs.getBoolean("Complete")));
            }

            rs.close();
            stmt.close();

            return list;
        } catch (SQLException ex) {
            System.out.println("Exception searching for pending Micro Results: " + ex.toString());
            return null;
        }
    }
        
    /**
     * Builds a WHERE clause for SearchPendingResults. The same parameters are
     * passed in here, and the where clause is returned as a String.
     *
     * @param accession String Accession of Order, null if not used.
     * @param patientId Integer of the patientId, null if not used,
     * @param from java.util.Date of date to Start search, null if not used.
     * @param to java.util.Date of date to End search, null if not used.
     * @return String WHERE Clause.
     */
    private String BuildSearchWhereClause(String accession, String site, String source, Integer patientId, Date from, Date to, boolean useOrderDate)
    {
        String whereClause = "";

        if (accession != null && accession.isEmpty() == false) {
            whereClause = " AND o.`accession` = '" + accession + "'";
        } else {
            //Validate and process patientid
            int patId = -1;
            if (patientId != null) {
                try {
                    patId = patientId;
                } catch (Exception ex) {
                    System.out.println("Exception converting patientId(Integer) to int: " + ex.toString());
                }
            }
            if (patId != -1) {
                whereClause += " AND p.`idpatients` = " + patId + " ";
            }

            // Validate and process site
            if (site != null) {
                whereClause += " AND s.`siteName` = '" + site + "'";
            }

            // Validate and process source
            if (source != null) {
                whereClause += " AND so.`sourceName` = '" + source + "'";
            }

            //Validate and Process Dates
            //Current Date Time
            java.util.Date d = new java.util.Date();
            //Order Date From Is populated but To is empty
            if (from != null && to == null)
            {
                if (useOrderDate)
                {
                    whereClause += " AND o.orderDate >= '" + Convert.ToSQLDate(from) + "' ";
                }
                else
                {
                    whereClause += " AND o.specimenDate >= '" + Convert.ToSQLDate(from) + "' ";
                }
                
            }
            //Order Date From is Empty but To is Populated
            if (from == null && to != null)
            {
                if (useOrderDate)
                {
                    whereClause += " AND o.orderDate <= " + Convert.ToSQLDate(to);
                }
                else
                {
                    whereClause += " AND o.specimenDate <= " + Convert.ToSQLDate(to);
                }
            }
            //Both Order Dates Are Populated
            if (from != null && to != null)
            {
                if (useOrderDate)
                {
                    whereClause += " AND o.orderDate >= '" + Convert.ToSQLDate(from) + " 00:00:01' "
                            + " AND o.orderDate <= '" + Convert.ToSQLDate(to) + " 23:59:59' ";
                }
                else
                {
                    whereClause += " AND o.specimenDate >= '" + Convert.ToSQLDate(from) + " 00:00:01' "
                            + " AND o.specimenDate <= '" + Convert.ToSQLDate(to) + " 23:59:59' ";                    
                }
            }
        }
        return whereClause;
    }
    
    /**
     * Adds a new instrument comment if it doesn't exist. If a comment code
     *  exists but the comment doesn't match, this deactivates the existing
     *  comment, inserts a new one, and sets that to active.
     * 
     *  Returns the new or existing comment object.
     *  
     * @param commentCode The unique code identifier for the comment
     * @param comment The instrument's comment
     * @param instrumentId The instrument that this code belongs to
     * @param commentAssociation
     * @return A reference to the existing or newly-created MicroInstrumentComment
     */
    public MicroInstrumentComment AddInstrumentComment(String commentCode, String comment, int instrumentId, String commentAssociation)
    {
        // All parameters are required
        if (commentCode == null || commentCode.isEmpty())
        {
            System.out.println("Received a null/empty comment code for an instrument comment!");
            return null;
        }
        
        if (comment == null || comment.isEmpty())
        {
            System.out.println("Received a null/empty comment for comment code " + commentCode);
            return null;
        }
        
        if (instrumentId < 1)
        {
            System.out.println("Received an invalid (<1) instrument Id for comment code " + commentCode);
            return null;
        }
        
        if (commentAssociation == null) commentAssociation = "";
        // See if we can get an instrument comment that matches the supplied
        // comment code:
        MicroInstrumentCommentDAO micdao = new MicroInstrumentCommentDAO();
        MicroInstrumentComment activeComment = micdao.GetActiveByCommentCodeAssociationCode(commentCode, commentAssociation, instrumentId);
        
        // We didn't have one
        if (activeComment == null)
        {
            // This is a new comment, so insert it to the table and return
            // a reference to the new comment
            MicroInstrumentComment newComment = new MicroInstrumentComment();
            newComment.setActive(true);
            newComment.setComment(comment);
            newComment.setCommentCode(commentCode);
            newComment.setInstrumentId(instrumentId);
            newComment.setAssociationCode(commentAssociation);
            
            Integer newId = micdao.InsertGetId(newComment);
            
            if (newId == null || newId < 1)
            {
                System.out.println("Error attempting to insert new instrument comment (commentCode: " + commentCode + ", instrument id: " + instrumentId + ")");
                return null;                
            }
            
            newComment.setId(newId);
            return newComment;            
        }
        else // One already exists. Check to ensure that the comment is not different
        {
            if (comment.equals(activeComment.getComment()))
            {
                // Everything matches. We already have the comment in the
                // database, so just return that.
                return activeComment;
            }
            else
            {
                // We have a mtaching comment code, but the comment itself is
                // different! Disable the current comment.
                micdao.Deactivate(activeComment);
                
                // Insert an active new comment
                MicroInstrumentComment newComment = new MicroInstrumentComment();
                newComment.setActive(true);
                newComment.setComment(comment);
                newComment.setCommentCode(commentCode);
                newComment.setInstrumentId(instrumentId);
                newComment.setAssociationCode(commentAssociation);

                Integer newId = micdao.InsertGetId(newComment);

                if (newId == null || newId < 1)
                {
                    System.out.println("Error attempting to insert new instrument comment (commentCode: " + commentCode + ", instrument id: " + instrumentId + ")");
                    return null;
                }

                newComment.setId(newId);
                return newComment;
            }
        }
    }
        
    /**
     * Deletes all microbiology result lines for a given micro order
     * Only deletes if not posted and not invalidated.
     * @param microOrders 
     */
    public boolean DeleteAllMicroResults(int microOrdersId)
    {
        try {
            if (con.isClosed())
            {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException ex)
        {
            System.out.println(ex.toString());
            return false;
        }
        
        try
        {
            String sql = "DELETE FROM microResults WHERE "
                    + " invalidated = 0 "
                    + " AND microOrderId = ?";
            
            PreparedStatement pStmt = con.prepareStatement(sql);
            pStmt.setInt(1, microOrdersId);
            int result = pStmt.executeUpdate();            
            pStmt.close();
            return true;
        }
        catch (SQLException ex)
        {
            System.out.println("Error deleting micro result lines for microOrdersId " + microOrdersId);
        }
        return false;
    }
    
    /**
     * Deletes all microbiology result lines for a given micro order
     * Only deletes if not posted and not invalidated.
     * @param microOrders 
     */    
    public boolean DeleteAllMicroComments(int microOrdersId)
    {
        try {
            if (con.isClosed())
            {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException ex)
        {
            System.out.println(ex.toString());
            return false;
        }
        
        try
        {
            String sql = "DELETE FROM microComments WHERE invalidated = 0 "
                    + " AND microOrderId = ?";
            
            PreparedStatement pStmt = con.prepareStatement(sql);
            pStmt.setInt(1, microOrdersId);
            int result = pStmt.executeUpdate();            
            pStmt.close();
            return true;
        }
        catch (SQLException ex)
        {
            System.out.println("Error deleting micro comment lines for microOrders " + microOrdersId);
        }
        return false;
    }
    
    
    /**
     * Concatenates together all active "regular" comments - not a gram stain,
     *  prelim report, or final report - for the microbiology culture
     * @param microOrderId
     * @return String comments (blank if none), NULL on error
     */
    public String GetConcatenatedRegularComments(int microOrderId)
    {
        try
        {
            if (con.isClosed())
            {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException ex)
        {
            System.out.println(ex.toString());
            return null;
        }
        String comments = "";
        try
        {
            String sql = "SELECT (CASE WHEN mcom.`comment` IS NULL OR LENGTH(mcom.`comment`) = 0 THEN mins.`comment` ELSE CASE WHEN mor.`organismName` IS NOT NULL THEN CONCAT('(', mor.`organismName`, ') ', mcom.`comment`) ELSE mcom.`comment` END END) AS `comment` "
                    + " FROM microComments mcom "
                    + " LEFT JOIN microInstrumentComments mins on mcom.microInstrumentCommentId = mins.id"
                    + " LEFT JOIN microOrganisms mor ON mcom.microOrganismId = mor.idmicroOrganisms "
                    + " WHERE mcom.invalidated = 0 AND mcom.microOrderId = ?";
            PreparedStatement pStmt = con.prepareStatement(sql);
            pStmt.setInt(1, microOrderId);
            ResultSet rs = pStmt.executeQuery();
            while (rs.next())
            {
                String temp = rs.getString(1);
                if (temp != null)
                {
                    if (!temp.trim().endsWith("."))
                    {
                        temp += ". ";
                    }
                    comments += temp;
                }
            }
            pStmt.close();
            
        }
        catch (SQLException ex)
        {
            System.out.println("Error attempting to get culture comments for orderId =  " + microOrderId);
            return null;
        }
        return comments;
    }
    
    /**
     * Gets the result Ids for the order that are associated with the culture
     *  test Id.
     * @param orderId
     * @param cultureTestId
     * @return 
     */
    public ArrayList<Integer> GetMicrobiologyResultIDs(int orderId, int cultureTestId)
    {
        try {
            if (con.isClosed())
            {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException ex)
        {
            System.out.println(ex.toString());
            return null;
        }
        
        ArrayList<Integer> resultIds = new ArrayList<>();
        try
        {
            String sql = "SELECT idResults FROM results"
                + " WHERE orderId = ?"
                + " AND testId = ?"
                + " AND (isInvalidated = 0 OR isInvalidated IS NULL) "
                + " AND (resultText IS NULL OR UPPER(resultText) != 'DELETED')";
            
            PreparedStatement pStmt = con.prepareStatement(sql);
            pStmt.setInt(1, orderId);
            pStmt.setInt(2, cultureTestId);
            ResultSet rs = pStmt.executeQuery();
            while (rs.next())
            {
                resultIds.add(rs.getInt(1));
            }
            pStmt.close();
        }
        catch (SQLException ex)
        {
            System.out.println("Error attempting to get culture resultIds for orderId =  " + orderId);
        }
        return resultIds;
    }
    
    /**
     * Returns true if there are any un-posted microbiology results in any of
     *  the instrument buffers for "microbiology" category instruments.
     * 
     * @param accession
     * @return NULL on error
     */
    public Boolean HasUnpostedMicrobiologyResultsInBuffer(String accession)
    {
        try
        {
            if (con.isClosed())
            {
                con = CheckDBConnection.Check(dbs, con);
            }
        }
        catch (SQLException ex)
        {
            System.out.println(ex.toString());
        }
        
        // Get the micro instruments
        InstrumentDAO idao = new InstrumentDAO();
        Instruments[] microInstruments = idao.GetInstrumentsByCategoryName("Microbiology");
        if (microInstruments == null)
        {
            System.out.println("Unable to retrieve microbiology instrument list");
            return null;
        }
        
        for (Instruments instrument : microInstruments)
        {
            String resultTable = instrument.getResTable();
            if (resultTable != null && !resultTable.isEmpty())
            {
                try
                {
                    // Set unposted buffer results for this accession as posted
                    String sql = "SELECT COUNT(*) FROM `" + resultTable + "` WHERE posted = 0 AND accession = ?";
                    PreparedStatement pStmt = con.prepareStatement(sql);
                    pStmt.setString(1, accession);
                    System.out.println(pStmt.toString());
                    ResultSet rs = pStmt.executeQuery();
                    int resultCount = 0;
                    if (rs.next())
                    {
                        resultCount = rs.getInt(1);
                    }
                    if (resultCount > 0) return true;
                    pStmt.close();
                }
                catch (SQLException ex)
                {
                    System.out.println("Error attempting to clear buffer for accession " + accession + 
                            " for buffer table: " + resultTable);
                }
            }
        }
        return false;
    }
    
    /**
     * Loops through every microbiology category instrument and marks all rows
     *  in the results buffer as posted for the provided accession.
     * 
     *  Called when a user posts results from manual posting to prevent them 
     *  from showing in automated.
     *  
     * @param accession
     * @param userId
     * @return 
     */
    public void SetPostedInMicrobiologyBuffers(String accession, Integer userId)
    {
        try
        {
            if (con.isClosed())
            {
                con = CheckDBConnection.Check(dbs, con);
            }
        }
        catch (SQLException ex)
        {
            System.out.println(ex.toString());
        }
        
        // Get the micro instruments
        InstrumentDAO idao = new InstrumentDAO();
        Instruments[] microInstruments = idao.GetInstrumentsByCategoryName("Microbiology");
        if (microInstruments == null)
        {
            System.out.println("Unable to retrieve microbiology instrument list");
            return;
        }
        
        for (Instruments instrument : microInstruments)
        {
            String resultTable = instrument.getResTable();
            if (resultTable != null && !resultTable.isEmpty())
            {
                try
                {
                    // Set unposted buffer results for this accession as posted
                    String sql = "UPDATE `" + resultTable + "` SET posted = 1, user = ? WHERE"
                            + " posted = 0 AND accession = ?";

                    System.out.println("Since results have been manually posted, setting accession as posted in instrument buffer: ");
                    PreparedStatement pStmt = con.prepareStatement(sql);
                    pStmt.setInt(1, userId);
                    pStmt.setString(2, accession);
                    System.out.println(pStmt.toString());
                    int affectedCount = pStmt.executeUpdate();
                    System.out.println("Query results: " +  affectedCount + " rows affected");
                    pStmt.close();
                }
                catch (SQLException ex)
                {
                    System.out.println("Error attempting to clear buffer for accession " + accession + 
                            " for buffer table: " + resultTable);
                }
            }
        }        
    }
    

    //<editor-fold defaultstate="collapsed" desc="MicroSearchObject">
    /**
     * Small class used in MIcro Manual Posting for Search Results and for
     * populating the table.
     */
    public class MicroSearchObject {

        private String Accession;
        private String Site;
        private String Source;
        private String Patient_Name;
        private Date Order_Date;
        private Integer Result_ID;
        private Boolean Complete;

        public MicroSearchObject() {
        }

        public MicroSearchObject(String Accession, String Site, String Source, String Patient_Name, Date Order_Date, Integer Result_ID, Boolean Complete)
        {
            this.Accession = Accession;
            this.Site = Site;
            this.Source = Source;
            this.Patient_Name = Patient_Name;
            this.Order_Date = Order_Date;
            this.Result_ID = Result_ID;
            this.Complete = Complete;
        }

        public String getAccession() {
            return Accession;
        }

        public void setAccession(String Accession) {
            this.Accession = Accession;
        }

        public String getSite() {
            return Site;
        }

        public void setSite(String Site) {
            this.Site = Site;
        }

        public String getSource() {
            return Source;
        }

        public void setSource(String Source) {
            this.Source = Source;
        }

        public String getPatient_Name() {
            return Patient_Name;
        }

        public void setPatient_Name(String Patient_Name) {
            this.Patient_Name = Patient_Name;
        }

        public Date getOrder_Date() {
            return Order_Date;
        }

        public void setOrder_Date(Date Order_Date) {
            this.Order_Date = Order_Date;
        }
        
        public void setResultId(Integer resultId)
        {
            this.Result_ID = resultId;
        }
        
        public Integer getResultId()
        {
            return this.Result_ID;
        }

        public Boolean isComplete()
        {
            return Complete;
        }

        public void setComplete(Boolean Complete)
        {
            this.Complete = Complete;
        }

        @Override
        public int hashCode()
        {
            int hash = 7;
            hash = 79 * hash + Objects.hashCode(this.Accession);
            hash = 79 * hash + Objects.hashCode(this.Patient_Name);
            hash = 79 * hash + Objects.hashCode(this.Order_Date);
            hash = 79 * hash + Objects.hashCode(this.Result_ID);
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final MicroSearchObject other = (MicroSearchObject) obj;
            
            if (!Objects.equals(this.Result_ID, other.Result_ID)) {
                return false;
            }            
            if (!Objects.equals(this.Accession, other.Accession)) {
                return false;
            }
            if (!Objects.equals(this.Patient_Name, other.Patient_Name)) {
                return false;
            }
            if (!Objects.equals(this.Order_Date, other.Order_Date)) {
                return false;
            }
            return true;
        }
    }
    //</editor-fold>
}
