package DOS.IDOS;

import DOS.Results;
import DOS.TestContext;
import DOS.Tests;
import java.io.Serializable;

/**
 * Passed into the test tree facade
 * @author TomR
 */
public interface ITestData extends Serializable
{
    static final long serialVersionUID = 42L;
    // Getters
    // ------------    
    Tests getTest();
    Results getResult();
    boolean isAutoOrdered();    
    int getQuantity();    
    boolean isNoCharge();
    boolean isSubTest();
    boolean isDiagnosisValid();
    boolean isPainManagement();
    boolean isResulted();
    boolean isPrintAndTransmitted();
    boolean isSentToBilling();
    boolean isApproved();
    boolean isSaved();
    boolean isPOC();
    boolean isBillable();
    Integer getTestNumber();
    String getDisplay();
    IMicrobiologyData getMicroData();
    boolean isMicroTest();
    boolean hasParentTest();
    boolean isContainerTest(); // Panel or Battery
    boolean isReferenceLabDepartment();
    TestContext getTestContext();
    boolean isNoChargeAllowed();
    
    // Setters
    // ------------
    void setQuantity(int quantity);
    void setBillable(boolean billable);
    void setMicroData(IMicrobiologyData mdata);
    void setNoCharge(boolean noCharge);
    void setDiagnosisValid(boolean diagnosisValid);
    void setAutoOrdered(boolean isAutoOrdered);
    void setPainManagement(boolean isPainManagement);
    void setOrderId(int orderId);
    void setTestContext(TestContext testContext);
    void setNoChargeAllowed(boolean noChargedAllowed);
    
    Integer save(); // Integer --> resultId or NULL on error
    boolean is(ITestData testData, boolean overlappingPanelsEnabled);
}
