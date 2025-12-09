/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package BL;

import DAOS.DepartmentDAO;
import DAOS.DiffLogDAO;
import DAOS.RefResDAO;
import DAOS.ReferenceLabSettingDAO;
import DAOS.ReportTypeDAO;
import DOS.Departments;
import DOS.RefRes;
import DOS.ReferenceLabSettings;
import DOS.ReportType;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author TomR
 */
public class ReferenceLabBL
{
    /**
     * Posts and approves the un-posted reference lab results for the given order/departmentId
     *  combination.
     * 
     * Called procedure should perform all actions, including logging.
     * 
     *
     * @param approvalProcedure
     * @param orderId
     * @param departmentId
     * @param userId
     * @return 
     */
    public Integer ApproveOrder(String approvalProcedure, int orderId, int departmentId, int userId)
    {
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        DepartmentDAO ddao = new DepartmentDAO();
        Integer errorFlag = 0;
        try
        {

            // Accession, advancedOrderId, userId, out errorFlag
            String sql = "CALL " + approvalProcedure + "(?,?,?,?);";
            CallableStatement callable = con.prepareCall(sql);
            callable.setInt(1, orderId);
            callable.setInt(2, departmentId);
            callable.setInt(3, userId);
            callable.registerOutParameter(4, java.sql.Types.INTEGER);

            //ResultSet rs = 
            callable.executeQuery();
            errorFlag = callable.getInt(4); 
            callable.close();
        }
        catch (SQLException ex)
        {
            System.out.println("Unable to call approval procedure for deptId: " 
                + departmentId + ", orderId: " + orderId);
            errorFlag = 1;
        }
        return errorFlag;
    }
    
    /**
     * Called when the user right click-->"Deletes" results for a reference lab.
     *  Invalidates the results (they are made invisible on the reference
     *  lab result rows).
     * @param orderId
     * @param departmentId
     * @param userId
     * @return 
     */
    public String InvalidateUnapprovedResults(String accession, int departmentId, int userId)
    {
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        DepartmentDAO ddao = new DepartmentDAO();
        String errorMsg = "";
        try
        {
            // Accession, advancedOrderId, userId, out errorFlag
            String sql = "CALL ReferenceLabInvalidateResults(?,?,?,?);";
            CallableStatement callable = con.prepareCall(sql);
            callable.setString(1, accession);
            callable.setInt(2, departmentId);
            callable.setInt(3, userId);
            callable.registerOutParameter(4, java.sql.Types.VARCHAR);

            //ResultSet rs = 
            callable.executeQuery();
            errorMsg = callable.getString(4); 
            callable.close();
        }
        catch (SQLException ex)
        {
            System.out.println("Unable to call invalidation procedure for deptId: " 
                + departmentId + ", accession: " + accession);
            errorMsg = "Unable to remove results. Please contact support.";
        }
        return errorMsg;
    }
    
    /**
     * Returns a reference lab settings object for the supplied department.
     * 
     * Returns NULL on sql error or if there is no reference lab settings row
     *  for the supplied department
     * 
     * @param dept Departments object
     * @return ReferenceLabSettings object
     */
    public ReferenceLabSettings GetSettingsForDepartment(Departments dept)
    {        
        if (dept == null)
        {
            System.out.println("ReferenceLabBL::GetSettingsForDepartment: NULL department suppplied");
            return null;
        }
        
        if (dept.getIdDepartment() == null || dept.getIdDepartment() < 1)
        {
            System.out.println("ReferenceLabBL::GetSettingsForDepartment: empty Departments object supplied");
            return null;
        }
        
        ReferenceLabSettingDAO rlsdao = new ReferenceLabSettingDAO();
        ReferenceLabSettings settings = rlsdao.GetActiveSettingsByDeptId(dept.getIdDepartment());
        if (settings == null)
        {
            System.out.println("ReferenceLabBL::GetSettingsForDepartment: " +
                    " Received a null object from ReferenceLabSettingDAO");
        }
        return settings;
    }
    
    /**
     * Returns a department object using the supplied reference lab settings
     * 
     * NULL on error
     * 
     * @param settings
     * @return 
     */
    public Departments GetDepartmentFromSettings(ReferenceLabSettings settings)
    {
        if (settings == null)
        {
            System.out.println("ReferenceLabBL::GetDepartmentFromSettings: NULL ReferenceLabSettings suppplied");
            return null;
        }
        
        if (settings.getIdDepartment() == null || settings.getIdDepartment() < 1)
        {
            System.out.println("ReferenceLabBL::GetSettingsForDepartment: blank departments field on settings");
            return null;
        }
        
        DepartmentDAO ddao = new DepartmentDAO();
        Departments dept = null;
        try
        {
            dept = ddao.GetDepartmentByID(settings.getIdDepartment());
        }
        catch (SQLException ex)
        {
            
            System.out.println("ReferenceLabBL::GetDepartmentFromSettings: " +
                    " Unable to retrieve department for id: " + settings.getIdDepartment());
            return null;
        }
        return dept;
    }
    
    public ReportType GetManifestFromSettings(ReferenceLabSettings settings)
    {
        ReportType reportType = null;
        try
        {
            ReportTypeDAO rtdao = new ReportTypeDAO();
            reportType = rtdao.GetReportType(settings.getIdManReport());
            if (reportType == null || reportType.getIdreportType() == null || reportType.getIdreportType() < 1)
            {
                throw new SQLException();
            }
        }
        catch (SQLException ex)
        {
            System.out.println("ReferenceLabBL::GetManifestFromSettings: " +
                    "Unable to load report type object");
            reportType = null;
        }        
        return reportType;
    }
    
    public ReportType GetRequisitionFromSettings(ReferenceLabSettings settings)
    {
        ReportType reportType = null;
        try
        {
            ReportTypeDAO rtdao = new ReportTypeDAO();
            reportType = rtdao.GetReportType(settings.getIdReqReport());
            if (reportType == null || reportType.getIdreportType() == null || reportType.getIdreportType() < 1)
            {
                throw new SQLException();
            }
        }
        catch (SQLException ex)
        {
            System.out.println("ReferenceLabBL::GetRequisitionFromSettings: " +
                    "Unable to load report type object");
            reportType = null;
        }        
        return reportType;
    }
    
    /**
     * Sets the invalidated flag for a single reference lab buffer result line
     * The refRes_# table to be used is passed as a string parameter.
     * Invalidated results are logged to their corresponding diff log tables.
     *   e.g.   refRes_#_log
     * 
     * @param userId
     * @param idrefResult
     * @param refResTableName
     * @param message Message to be displayed in the log.
     * @throws IllegalArgumentException
     * @throws SQLException 
     */
    public void InvalidateBufferResult(int userId, int idrefResult, String refResTableName)
            throws IllegalArgumentException, SQLException
    {
        if (idrefResult < 0)
        {
            throw new IllegalArgumentException("ReferenceLabBL::InvalidateResult:"
                    + " idrefResult was less than zero: " + idrefResult);
        }
        
        if (refResTableName == null || refResTableName.isEmpty())
        {
            throw new IllegalArgumentException("ReferenceLabBL::InvalidateResult:"
                    + " refResTableName was NULL or empty!");
        }
        
        RefResDAO rrdao = new RefResDAO();
        RefRes rrCurrent = rrdao.GetRefResByID(refResTableName, idrefResult);
        
        RefRes rrUpdated = rrCurrent.copy();
        
        rrUpdated.setIsInvalidated(true);

        String accession = (rrUpdated.getAccession() != null ? " Accession:" + rrUpdated.getAccession() : "");
        String testName = (rrUpdated.getName() != null ? " Test:" + rrUpdated.getName() : "");
        String message = "Deleted " + accession + testName;
        
        // Insert a diff log for the deleted row
        DiffLogDAO.Insert(refResTableName + "_log",
                "idrefresult", idrefResult, rrCurrent,
                rrUpdated, message, userId);
        
        rrdao.Update(refResTableName, rrUpdated);
        
    }
}
