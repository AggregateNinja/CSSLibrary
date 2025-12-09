package BL;

import DAOS.PreferencesDAO;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @date:   Jul 28, 2014
 * @author: Keith Maggio <keith@csslis.com>
 * 
 * @project:  
 * @package: BL
 * @file name: LabelBL.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc.  All Rights Reserved
 */

public class LabelBL 
{
    /**
     * Contains a key->map of each piece of data retrieved from
     * the GetLabelData stored procedure. This is matched up with the
     * label form. Cognitive uses a case insensitive search-and-replace on the
     * field name (the value between the @ symbols), and Jasper takes each
     * name/value piece as a parameter that's used to populate the label form.
     * If a parameter is not being expected by the Jasper form,
     * it should be ignored.
     * 
     * The GetLabelData stored procedure MUST return:
     * 
     *  LabelTypeName - displayed to the user to describe the label's purpose.
     *                  e.g. "Requisition", "Instrument", "Reference Lab". Can
     *                  be a blank string but must be returned.
     * 
     *  LabelFormName - the default label form to use for this row.
     *                  Name must match a unique row in the labelForms table.
     * 
     *  Tests         - human-readable listing of all tests for this label
     * 
     *  Count         - default number of copies to print
     * 
     */
    public static class DynamicLabelData
    {
        HashMap<String, Object> data = new HashMap<>();
        
        public DynamicLabelData(){}
        
        /**
         * Keys are case insensitive! "lastname" and "LastName" will
         *  be seen as the same data value.
         *  This is to avoid 
         * @param name
         * @param value 
         */
        public void AddData(String name, Object value)
        {
            if (name == null) return;
            data.put(name.toLowerCase(), value);
        }
        
        public HashMap<String, Object> GetData()
        {
            return data;
        }
        
        /**
         * Note: keys are always case-insensitive
         * @param key
         * @return 
         */
        public Object GetValue(String key)
        {
            if (key == null) return null;
            return data.get(key.toLowerCase());
        }
        
    }
    
    public static class LabelObject
    {
        String lastName;
        String firstName;
        String age;
        String sex;
        String clientName;
        String patID;
        String DOB;
        String specimenDate;
        String accession;
        String tests;
        String code;
        String tubeAbbr;
        String tubeName;
        String department;
        String instrument;
        String reqID;

        public LabelObject()
        {
        }

        public String getLastName()
        {
            return lastName;
        }

        public void setLastName(String lastName)
        {
            this.lastName = lastName;
        }

        public String getFirstName()
        {
            return firstName;
        }

        public void setFirstName(String firstName)
        {
            this.firstName = firstName;
        }

        public String getAge()
        {
            return age;
        }

        public void setAge(String age)
        {
            this.age = age;
        }

        public String getSex()
        {
            return sex;
        }

        public void setSex(String sex)
        {
            this.sex = sex;
        }

        public String getClientName()
        {
            return clientName;
        }

        public void setClientName(String clientName)
        {
            this.clientName = clientName;
        }

        public String getPatID()
        {
            return patID;
        }

        public void setPatID(String patID)
        {
            this.patID = patID;
        }

        public String getDOB()
        {
            return DOB;
        }

        public void setDOB(String DOB)
        {
            this.DOB = DOB;
        }

        public String getSpecimenDate()
        {
            return specimenDate;
        }

        public void setSpecimenDate(String specimenDate)
        {
            this.specimenDate = specimenDate;
        }

        public String getAccession()
        {
            return accession;
        }

        public void setAccession(String accession)
        {
            this.accession = accession;
        }

        public String getTests()
        {
            return tests;
        }

        public void setTests(String tests)
        {
            this.tests = tests;
        }

        public String getCode()
        {
            return code;
        }

        public void setCode(String code)
        {
            this.code = code;
        }

        public String getTubeAbbr() {
            return tubeAbbr;
        }

        public void setTubeAbbr(String tubeAbbr) {
            this.tubeAbbr = tubeAbbr;
        }

        public String getTubeName() {
            return tubeName;
        }

        public void setTubeName(String tubeName) {
            this.tubeName = tubeName;
        }

        public String getDepartment() {
            return department;
        }

        public void setDepartment(String department) {
            this.department = department;
        }

        public String getInstrument() {
            return instrument;
        }

        public void setInstrument(String instrument) {
            this.instrument = instrument;
        }

        public String getReqID() {
            return reqID;
        }

        public void setReqID(String reqID) {
            this.reqID = reqID;
        }
        
    }
    
    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    
    public LabelBL()
    {
        
    }
    
    public ArrayList<LabelObject> GetLabelData(Integer orderID, Boolean IsRequisitionLabel) throws SQLException
    {
        ArrayList<LabelObject> lolist = new ArrayList<>();
        String query =
            "SELECT " +
            "p.lastName AS \"LastName\", " +
            "p.firstName AS \"FirstName\", " +
            "FLOOR(DATEDIFF(CURRENT_DATE, p.dob)/365) AS \"Age\", " +
            "LEFT(p.sex, 1) AS \"Sex\", " +
            "c.clientName AS \"ClientName\", " +
            "p.arNo AS \"PatientID\", " +
            "DATE_FORMAT(p.dob,  \"%m/%d/%y\") AS \"DOB\", " +
            "DATE_FORMAT(o.specimenDate,  \"%m/%d/%y\") AS \"SpecimenDate\", " +
            "IF(LENGTH(o.accession)<6,LPAD(o.accession, 6, '0'),o.accession) AS \"Accession\", " +
            "GROUP_CONCAT(t.abbr ORDER BY r.idResults DESC SEPARATOR '  ') AS \"Tests\", " +
            "CASE " +
            "	WHEN (" + IsRequisitionLabel + " = false AND inst.multipleSpecimen = true) THEN st.code " +
            "	ELSE NULL " +
            "END AS 'code', " +
            "o.reqId AS 'reqId' " +
            "FROM " +
            "orders o " +
            "LEFT JOIN patients p " +
            "ON o.patientId = p.idPatients " +
            "LEFT JOIN clients c " +
            "ON o.clientId = c.idClients " +
            "LEFT JOIN results r " +
            "ON o.idOrders = r.orderId " +
            "LEFT JOIN tests t " +
            "ON t.idTests = r.testId " +
            "LEFT JOIN specimenTypes st " +
            "ON t.specimenType = st.idspecimenTypes " +
            "LEFT JOIN instruments inst " +
            "ON t.instrument = inst.idinst " +
            "WHERE  o.idOrders = " + orderID + " " +
            "AND t.instrument IS NOT NULL " +
            "GROUP BY inst.idinst, " +
            "CASE " +
            "	WHEN (" + IsRequisitionLabel + " = false AND inst.multipleSpecimen = true) THEN st.idspecimenTypes " +
            "	ELSE NULL " +
            "END;";  
        
        Statement stmt = con.createStatement();
        
        ResultSet rs = stmt.executeQuery(query);
        
        while(rs.next())
        {
            LabelObject lo = new LabelObject();
            lo.lastName = rs.getString("LastName");
            lo.firstName = rs.getString("FirstName");
            lo.age = rs.getString("Age");
            lo.sex = rs.getString("Sex");
            lo.clientName = rs.getString("ClientName");
            lo.patID = rs.getString("PatientID");
            lo.DOB = rs.getString("DOB");
            lo.specimenDate = rs.getString("SpecimenDate");
            lo.accession = rs.getString("Accession");
            lo.tests = rs.getString("Tests");
            lo.code = rs.getString("code");
            lo.reqID = rs.getString(("reqId"));
            
            lolist.add(lo);
        }
        
        rs.close();
        stmt.close();
            
        return lolist;
            
    }
    
    public ArrayList<LabelObject> GetStaticLabelData(Integer orderID) throws SQLException
    {
        ArrayList<LabelObject> lolist = new ArrayList<>();
        String query =
            "SELECT " +
            "p.lastName AS \"LastName\", " +
            "p.firstName AS \"FirstName\", " +
            "FLOOR(DATEDIFF(CURRENT_DATE, p.dob)/365) AS \"Age\", " +
            "LEFT(p.sex, 1) AS \"Sex\", " +
            "c.clientName AS \"ClientName\", " +
            "p.arNo AS \"PatientID\", " +
            "DATE_FORMAT(p.dob,  \"%m/%d/%y\") AS \"DOB\", " +
            "DATE_FORMAT(o.specimenDate,  \"%m/%d/%y\") AS \"SpecimenDate\", " +
            "IF(LENGTH(o.accession)<6,LPAD(o.accession, 6, '0'),o.accession) AS \"Accession\", " +
            "' ' AS \"Tests\", " +
            "' ' AS 'code', " +
            "o.reqId AS 'reqId'" +
            "FROM " +
            "orders o " +
            "LEFT JOIN patients p " +
            "ON o.patientId = p.idPatients " +
            "LEFT JOIN clients c " +
            "ON o.clientId = c.idClients " +
            "LEFT JOIN results r " +
            "ON o.idOrders = r.orderId " +
            "LEFT JOIN tests t " +
            "ON t.idTests = r.testId " +
            "WHERE  o.idOrders = " + orderID + " " +
            "GROUP BY o.idorders;";  
        
        Statement stmt = con.createStatement();
        
        ResultSet rs = stmt.executeQuery(query);
        
        while(rs.next())
        {
            LabelObject lo = new LabelObject();
            lo.lastName = rs.getString("LastName");
            lo.firstName = rs.getString("FirstName");
            lo.age = rs.getString("Age");
            lo.sex = rs.getString("Sex");
            lo.clientName = rs.getString("ClientName");
            lo.patID = rs.getString("PatientID");
            lo.DOB = rs.getString("DOB");
            lo.specimenDate = rs.getString("SpecimenDate");
            lo.accession = rs.getString("Accession");
            lo.tests = rs.getString("Tests");
            lo.code = rs.getString("code");
            lo.reqID = rs.getString("reqId");
            
            lolist.add(lo);
        }
        
        rs.close();
        stmt.close();
            
        return lolist;
            
    }
    
    public ArrayList<LabelObject> GetStaticNoTestsLabelData(Integer orderID) throws SQLException
    {
        ArrayList<LabelObject> lolist = new ArrayList<>();
        String query =
            "SELECT " +
            "p.lastName AS \"LastName\", " +
            "p.firstName AS \"FirstName\", " +
            "FLOOR(DATEDIFF(CURRENT_DATE, p.dob)/365) AS \"Age\", " +
            "LEFT(p.sex, 1) AS \"Sex\", " +
            "c.clientName AS \"ClientName\", " +
            "p.arNo AS \"PatientID\", " +
            "DATE_FORMAT(p.dob,  \"%m/%d/%y\") AS \"DOB\", " +
            "DATE_FORMAT(o.specimenDate,  \"%m/%d/%y\") AS \"SpecimenDate\", " +
            "IF(LENGTH(o.accession)<6,LPAD(o.accession, 6, '0'),o.accession) AS \"Accession\", " +
            "' ' AS \"Tests\", " +
            "' ' AS 'code', " +
            "o.reqId AS 'reqId' " +
            "FROM " +
            "orders o " +
            "LEFT JOIN patients p " +
            "ON o.patientId = p.idPatients " +
            "LEFT JOIN clients c " +
            "ON o.clientId = c.idClients " +
            "LEFT JOIN results r " +
            "ON o.idOrders = r.orderId " +
            "LEFT JOIN tests t " +
            "ON t.idTests = r.testId " +
            "WHERE  o.idOrders = " + orderID + " " +
            "GROUP BY o.idorders;";  
        
        Statement stmt = con.createStatement();
        
        ResultSet rs = stmt.executeQuery(query);
        
        while(rs.next())
        {
            LabelObject lo = new LabelObject();
            lo.lastName = rs.getString("LastName");
            lo.firstName = rs.getString("FirstName");
            lo.age = rs.getString("Age");
            lo.sex = rs.getString("Sex");
            lo.clientName = rs.getString("ClientName");
            lo.patID = rs.getString("PatientID");
            lo.DOB = rs.getString("DOB");
            lo.specimenDate = rs.getString("SpecimenDate");
            lo.accession = rs.getString("Accession");
            lo.tests = rs.getString("Tests");
            lo.code = rs.getString("code");
            lo.reqID = rs.getString(("reqId"));
            
            lolist.add(lo);
        }
        
        rs.close();
        stmt.close();
            
        return lolist;
            
    }
    
    public ArrayList<DynamicLabelData> GetLabelData(Integer userId, Integer orderId) throws SQLException
    {
        PreferencesDAO pdao = new PreferencesDAO();
        Integer clinitekLabelCount = pdao.getInteger("ClinitekLabelCount");
        
        ArrayList<DynamicLabelData> labelData = new ArrayList<>();        
        String sql = "CALL GetLabelData(?, ?);";
        CallableStatement callable = con.prepareCall(sql);
        callable.setInt(1, userId);
        callable.setInt(2, orderId);
        ResultSet rs = callable.executeQuery();
        while (rs.next())
        {
            LabelObject lo = new LabelObject();
            ResultSetMetaData metaData = rs.getMetaData();
            int count = metaData.getColumnCount();
            DynamicLabelData dataRow = null;
            dataRow = new DynamicLabelData();
            for (int i = 1; i <= count; i++)
            {
                dataRow.AddData(metaData.getColumnLabel(i), rs.getString(i));
            }
            
            String instrument = (String)dataRow.GetValue("Instrument");
            if (instrument != null && instrument.equals("Clinitek Advantus") && clinitekLabelCount != null)
                if(dataRow.data.containsKey("count")){
                    dataRow.data.put("count", clinitekLabelCount);
                }
                //dataRow.data.replace("count", clinitekLabelCount);
            
            labelData.add(dataRow);
        }

        callable.close();
        rs.close();
        return labelData;
    }
    
    
    public ArrayList<LabelObject> GetCustomLabelData(Integer orderID) throws SQLException
    {
        /*
        String query =
            "SELECT " +
            "p.lastName AS \"LastName\", " +
            "p.firstName AS \"FirstName\", " +
            "FLOOR(DATEDIFF(CURRENT_DATE, p.dob)/365) AS \"Age\", " +
            "LEFT(p.sex, 1) AS \"Sex\", " +
            "c.clientName AS \"ClientName\", " +
            "p.arNo AS \"PatientID\", " +
            "DATE_FORMAT(p.dob,  \"%m/%d/%y\") AS \"DOB\", " +
            "DATE_FORMAT(o.specimenDate,  \"%m/%d/%y\") AS \"SpecimenDate\", " +
            "IF(LENGTH(o.accession)<6,LPAD(o.accession, 6, '0'),o.accession) AS \"Accession\", " +
            "GROUP_CONCAT(t.abbr ORDER BY r.idResults DESC SEPARATOR '  ') AS \"Tests\", " +
            "CASE " +
            "	WHEN (" + IsRequisitionLabel + " = false AND inst.multipleSpecimen = true) THEN st.code " +
            "	ELSE NULL " +
            "END AS 'code', " +
            "inst.instName AS 'Instrument', " +
            "dept.deptName AS 'Department', " +
            "tt.abbr AS 'TubeAbbr', " +
            "tt.`name` AS 'TubeName' " +
            "FROM " +
            "orders o " +
            "LEFT JOIN patients p " +
            "ON o.patientId = p.idPatients " +
            "LEFT JOIN clients c " +
            "ON o.clientId = c.idClients " +
            "LEFT JOIN results r " +
            "ON o.idOrders = r.orderId " +
            "LEFT JOIN tests t " +
            "ON t.idTests = r.testId " +
            "LEFT JOIN specimenTypes st " +
            "ON t.specimenType = st.idspecimenTypes " +
            "LEFT JOIN instruments inst " +
            "ON t.instrument = inst.idinst " +
            "LEFT JOIN departments dept " +
            "ON t.department = dept.idDepartment " +
            "LEFT JOIN tubeType tt " +
            "ON t.tubeTypeId = tt.idTubeType " +
            "WHERE  o.idOrders = " + orderID + " " +
            "AND t.instrument IS NOT NULL " +
            "GROUP BY inst.idinst, " +
            "CASE " +
            "	WHEN (" + IsRequisitionLabel + " = false AND inst.multipleSpecimen = true) THEN st.idspecimenTypes " +
            "	ELSE NULL " +
            "END;";*/

            ArrayList<LabelObject> lolist = new ArrayList<>();        
            String sql = "CALL GetCustomLabelData(?);";
            CallableStatement callable = con.prepareCall(sql);
            callable.setInt(1, orderID);
            ResultSet rs = callable.executeQuery();
            while (rs.next())
            {
                LabelObject lo = new LabelObject();
                lo.lastName = rs.getString("LastName");
                lo.firstName = rs.getString("FirstName");
                lo.age = rs.getString("Age");
                lo.sex = rs.getString("Sex");
                lo.clientName = rs.getString("ClientName");
                lo.patID = rs.getString("PatientID");
                lo.DOB = rs.getString("DOB");
                lo.specimenDate = rs.getString("SpecimenDate");
                lo.accession = rs.getString("Accession");
                lo.tests = rs.getString("Tests");
                lo.code = rs.getString("code");
                lo.instrument = rs.getString("Instrument");
                lo.department = rs.getString("Department");
                lo.tubeAbbr = rs.getString("TubeAbbr");
                lo.tubeName = rs.getString("TubeName");

                lolist.add(lo);                
            }
            
            callable.close();
            rs.close();
            
            return lolist;
        
            /*
        Statement stmt = con.createStatement();        
        ResultSet rs = stmt.executeQuery(query);
        
        while(rs.next())
        {
            LabelObject lo = new LabelObject();
            lo.lastName = rs.getString("LastName");
            lo.firstName = rs.getString("FirstName");
            lo.age = rs.getString("Age");
            lo.sex = rs.getString("Sex");
            lo.clientName = rs.getString("ClientName");
            lo.patID = rs.getString("PatientID");
            lo.DOB = rs.getString("DOB");
            lo.specimenDate = rs.getString("SpecimenDate");
            lo.accession = rs.getString("Accession");
            lo.tests = rs.getString("Tests");
            lo.code = rs.getString("code");
            lo.instrument = rs.getString("Instrument");
            lo.department = rs.getString("Department");
            lo.tubeAbbr = rs.getString("TubeAbbr");
            lo.tubeName = rs.getString("TubeName");
            
            lolist.add(lo);
        }
        
        rs.close();
        stmt.close();
            
        return lolist;
            */
    }
    
   
}
