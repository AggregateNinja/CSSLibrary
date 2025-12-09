
package API.Billing.Common;

import DOS.TestContext;
import java.io.Serializable;
import java.util.List;

public class TestContextCptMap implements Serializable
{
    private static final long serialVersionUID = 42L;

    private final TestContext testContext;
    private List<CptModifierMap> cptModifierMaps;

    public TestContextCptMap(TestContext testContext, List<CptModifierMap> cptModifierMaps)
    {
        this.testContext = testContext;
        this.cptModifierMaps = cptModifierMaps;
    }

    public TestContext getTestContext()
    {
        return testContext;
    }

    public List<CptModifierMap> getCptModifierMaps()
    {
        return cptModifierMaps;
    }
    
    public void setCptModifierMaps(List<CptModifierMap> cptModifierMaps)
    {
        this.cptModifierMaps = cptModifierMaps;
    }
    
    public void addCptModifierMap(CptModifierMap cptModifierMap)
    {
        if (cptModifierMap == null) return;
        this.cptModifierMaps.add(cptModifierMap);
    }
}
