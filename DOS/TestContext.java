package DOS;

import DAOS.TestDAO;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.Date;
import java.util.Objects;

public class TestContext implements Serializable, Comparable<TestContext>
{

    private static final long serialVersionUID = 42L;

    private Integer batteryNumber = null;
    private Integer panelNumber = null;
    private Integer testNumber = null;
    private Integer idtestContext;
    private Date createdDate;

    public TestContext()
    {

    }

    public TestContext(Integer batteryNumber, Integer panelNumber, Integer testNumber)
            throws IllegalArgumentException
    {
        if (batteryNumber == null && panelNumber == null && testNumber == null)
        {
            throw new IllegalArgumentException("TestContext::Constructor: Received NULL battery, panel, and test number");
        }

        this.batteryNumber = batteryNumber;
        this.panelNumber = panelNumber;
        this.testNumber = testNumber;
    }
    
    public TestContext(OrderedTest orderedTest)
            throws IllegalArgumentException
    {
        if (orderedTest == null)
        {
            throw new IllegalArgumentException("TestContext::"
                    + "Constructor received a [NULL] OrderedTest argument");
        }
        
        if (orderedTest.getBatteryNumber() == null && orderedTest.getPanelNumber() == null &&
                orderedTest.getTestNumber() == null)
        {
            throw new IllegalArgumentException("TestContext::Constructor"
                    + " received an OrderedTest object with a [NULL]"
                    + " batteryNumber, panelNumber, and testNumber");
        }
        
        this.batteryNumber = orderedTest.getBatteryNumber();
        this.panelNumber = orderedTest.getPanelNumber();
        this.testNumber = orderedTest.getTestNumber();
        
    }

    @Override
    public int compareTo(TestContext other)
    {
        if (other == null) return -1;

        // All sorting from lowest to highest test number,
        // Directly added batteries and their subtests first,
        // then panels and their subtests, then singles.
        // Container tests always have their header row sorted first.
                
        if (this.batteryNumber != null && other.batteryNumber == null) return -1;
        if (this.batteryNumber == null && other.batteryNumber != null) return 1;
        if (this.batteryNumber != null && other.batteryNumber != null)
        {
            if (this.batteryNumber < other.batteryNumber) return -1;
            if (this.batteryNumber > other.batteryNumber) return 1;
            if (this.batteryNumber.equals(other.batteryNumber))
            {
                if (this.panelNumber == null && this.testNumber == null) return -1;
                if (other.panelNumber == null && other.testNumber == null) return 1;
                if (this.panelNumber == null && other.panelNumber != null) return 1;
                if (this.panelNumber != null && other.panelNumber == null) return -1;
            }            
        }
        
        if (this.panelNumber != null && other.panelNumber == null) return -1;
        if (this.panelNumber == null && other.panelNumber != null) return 1;
        if (this.panelNumber != null && other.panelNumber != null)
        {
            if (this.panelNumber < other.panelNumber) return -1;
            if (this.panelNumber > other.panelNumber) return 1;
            
            if (this.panelNumber.equals(other.panelNumber))
            {
                if (this.testNumber == null && other.testNumber != null) return -1;
                if (this.testNumber != null && other.testNumber == null) return 1;
            }
        }
        
        if (this.testNumber != null && other.testNumber == null) return 1;
        if (this.testNumber == null && other.testNumber != null) return -1;
        if (this.testNumber != null && other.testNumber != null)
        {
            if (this.testNumber < other.testNumber) return -1;
            if (this.testNumber > other.testNumber) return 1;
        }
        
        // these are equal
        return 0;
    }

    
    // This method is not safe. If you are dealing with a single test in a panel,
    // and that panel exists in one or more batteries, there is no way to know
    // what the context should be without more information. Removed to avoid
    // errors. - Tom
    
    /**
     * Provides a test context based on a relationship between a test and its
     * parent (identifiers, not numbers). Useful for getting a context from a
     * result line.
     *
     * Try to avoid using this method if possible, for example in order entry,
     * we would know if there's a battery->panel->result relationship from the
     * tree data.
     *
     * @param parentTestId
     * @param testId
     * @throws SQLException
     * @throws NullPointerException
     * @throws IllegalArgumentException
     */
    /*
    public TestContext(Integer parentTestId, Integer testId)
            throws SQLException, NullPointerException, IllegalArgumentException
    {
        if (testId == null || testId <= 0)
        {
            throw new IllegalArgumentException("TestContext::Constructor: received a NULL testId integer");
        }

        TestDAO tdao = new TestDAO();

        // Need to determine the type of this test
        Tests test = tdao.GetTestByID(testId);

        // Battery
        if (test.getHeader())
        {
            this.batteryNumber = test.getNumber();
        }
        else if (test.getTestType() == 0) // Panel
        {
            this.panelNumber = test.getNumber();

            // Is it associated with a battery?
            if (parentTestId != null)
            {
                Tests batteryParent = tdao.GetTestByID(parentTestId);
                if (batteryParent == null || batteryParent.getIdtests() == null || batteryParent.getIdtests() <= 0)
                {
                    throw new SQLException("TestContext::Constructor: "
                            + "Can't find battery test for test id " + parentTestId.toString());
                }
                this.batteryNumber = batteryParent.getNumber();
            }
        }
        else // Single
        {
            this.testNumber = test.getNumber();

            if (parentTestId != null)
            {
                Tests parentTest = tdao.GetTestByID(parentTestId);
                if (parentTest == null || parentTest.getIdtests() == null || parentTest.getIdtests() <= 0)
                {
                    throw new SQLException("TestContext::Constructor: "
                            + "Can't find parent test for test id " + parentTestId.toString());
                }

                // Is parent a battery?
                if (parentTest.getHeader())
                {
                    this.batteryNumber = parentTest.getNumber();
                }
                else // Parent is panel
                {
                    this.panelNumber = parentTest.getNumber();

                    // Is there a battery parent to this panel?
                    String sql = "SELECT DISTINCT(tb.number)"
                            + " FROM tests tb"
                            + " INNER JOIN panels pb ON tb.idtests = pb.idpanels"
                            + " INNER JOIN panels pp ON pb.subtestid = pp.idpanels"
                            + " WHERE pb.subtestid = ? AND pp.subtestId = ?;";
                    Connection conn = Database.DatabaseSingleton.getDatabaseSingleton().getConnection(true);
                    try (PreparedStatement pStmt = conn.prepareStatement(sql))
                    {
                        pStmt.setInt(1, parentTest.getIdtests());
                        pStmt.setInt(2, test.getIdtests());
                        ResultSet rs = pStmt.executeQuery();
                        if (rs.next())
                        {
                            if (rs.getInt(1) <= 0)
                            {
                                throw new SQLException(
                                        "TestContext:Constructor: Can't get battery for idpanels "
                                        + parentTest.getIdtests().toString()
                                        + " idtests " + test.getIdtests().toString());
                            }
                            this.batteryNumber = rs.getInt(1);
                        }                       
                    }
                    
                }
            }
        }
    }
    */
    
    /**
     * True if this context represents a header: battery or panel
     *
     * @return
     */
    public boolean isHeader()
    {
        return (testNumber == null);
    }
    
    /**
     * Returns the number of the test this line represents
     *  e.g.
     *  [123, NULL, NULL] would return 123
     *  [123, 456, NULL]  would return 456
     *  [123, 456, 789]   would return 789
     *  [123, NULL, 777]  would return 777
     *  
     * 
     * @return 
     */
    public Integer getNonContextualTestNumber()
    {
        if (this.testNumber != null) return testNumber;
        if (this.panelNumber != null) return panelNumber;
        if (this.batteryNumber != null) return batteryNumber;
        return null;
    }

    public Integer getIdtestContext()
    {
        return idtestContext;
    }

    public void setIdtestContext(Integer idtestContext)
    {
        this.idtestContext = idtestContext;
    }

    public Integer getBatteryNumber()
    {
        return batteryNumber;
    }

    public void setBatteryNumber(Integer batteryNumber)
    {
        this.batteryNumber = batteryNumber;
    }

    public Integer getPanelNumber()
    {
        return panelNumber;
    }

    public void setPanelNumber(Integer panelNumber)
    {
        this.panelNumber = panelNumber;
    }

    public Integer getTestNumber()
    {
        return testNumber;
    }

    public void setTestNumber(Integer testNumber)
    {
        this.testNumber = testNumber;
    }

    public Date getCreatedDate()
    {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate)
    {
        this.createdDate = createdDate;
    }
    
    public String getBatteryDisplay() throws SQLException
    {
        if (this.batteryNumber == null) return null;
        TestDAO tdao = new TestDAO();
        Tests test = tdao.GetTestOrCalcByNumber(this.batteryNumber);
        if (test != null && test.getIdtests() != null && test.getIdtests() > 0)
        {
            return "(#" + test.getNumber() + ") " + test.getName();
        }
        return null;
    }
    
    public String getPanelDisplay() throws SQLException
    {
        if (this.panelNumber == null) return null;
        TestDAO tdao = new TestDAO();
        Tests test = tdao.GetTestOrCalcByNumber(this.panelNumber);
        if (test != null && test.getIdtests() != null && test.getIdtests() > 0)
        {
            return "(#" + test.getNumber() + ") " + test.getName();
        }
        return null;
    }
    
    public String getTestDisplay() throws SQLException
    {
        if (this.testNumber == null) return null;
        TestDAO tdao = new TestDAO();
        Tests test = tdao.GetTestOrCalcByNumber(this.testNumber);
        if (test != null && test.getIdtests() != null && test.getIdtests() > 0)
        {
            return "(#" + test.getNumber() + ") " + test.getName();
        }
        return null;
    }
    
    public boolean isDirectlyOrdered()
    {
        return
                // Battery Header
                (batteryNumber != null && panelNumber == null && testNumber == null)
                ||
                // Panel Header
                (batteryNumber == null && panelNumber != null && testNumber == null)
                ||
                // Single Test
                (batteryNumber == null && panelNumber == null && testNumber != null);
    }
    
    
    public boolean inSameBattery(TestContext other)
    {
        if (other == null) return false;
        return getBatteryNumber() != null && other.getBatteryNumber() != null && getBatteryNumber().equals(other.getBatteryNumber());
    }
    
    public boolean inSamePanel(TestContext other)
    {
        if (other == null) return false;
        
        // If they share a panel (either outside of a battery, or in the same battery)
        return getPanelNumber() != null && other.getPanelNumber() != null
                && getPanelNumber().equals(other.getPanelNumber())
                && (
                        (getBatteryNumber() == null && other.getBatteryNumber() == null)
                        ||
                        (
                            getBatteryNumber() != null && other.getBatteryNumber() != null
                            && getBatteryNumber().equals(other.getBatteryNumber())
                        )
                    );
    }
    
    
    public boolean isContainedWithin(TestContext other)
    {
        if (other == null) return false;
        
        // If the other test is not a container, by definition it doesn't contain this
        if (other.getTestNumber() != null) return false;
        
        // Other test is a battery
        if (other.getBatteryNumber() != null)
        {
            // just have to see if they're in the same battery
            return (this.batteryNumber != null
                    && this.getBatteryNumber().equals(other.getBatteryNumber()));
        }
        // Other test is a panel that is not in a battery
        else
        {
            // Is this test in that panel?
            return (this.batteryNumber == null &&
                    this.panelNumber != null && this.panelNumber.equals(other.getPanelNumber()));
        }
        
    }
    
    /**
     * Shallow copies this test context and returns the new object
     * @return 
     */
    public TestContext copy()
    {
        TestContext newContext = new TestContext();
        newContext.setIdtestContext(this.getIdtestContext());
        newContext.setBatteryNumber(this.getBatteryNumber());
        newContext.setPanelNumber(this.getPanelNumber());
        newContext.setTestNumber(this.getTestNumber());
        return newContext;
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.batteryNumber);
        hash = 47 * hash + Objects.hashCode(this.panelNumber);
        hash = 47 * hash + Objects.hashCode(this.testNumber);
        return hash;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        final TestContext other = (TestContext) obj;
        if (!Objects.equals(this.batteryNumber, other.batteryNumber))
        {
            return false;
        }
        if (!Objects.equals(this.panelNumber, other.panelNumber))
        {
            return false;
        }
        if (!Objects.equals(this.testNumber, other.testNumber))
        {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString()
    {
        return ( 
                (batteryNumber == null? "[NULL]" : batteryNumber.toString())
                + " "
                + (panelNumber == null? "[NULL]" : panelNumber.toString())
                + " "
                + (testNumber == null? "[NULL]" : testNumber.toString())
                );
                
    }
}
