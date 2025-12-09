
package API.Billing.Common;

import DOS.CptCode;
import DOS.CptModifier;
import java.io.Serializable;
import java.util.List;

public class CptModifierMap implements Serializable
{
    private static final long serialVersionUID = 42L;
    
    private final CptCode cptCode;
    private final List<CptModifier> cptModifiers;

    public CptModifierMap(CptCode cptCode, List<CptModifier> cptModifiers)
    {
        this.cptCode = cptCode;
        this.cptModifiers = cptModifiers;
    }

    public CptCode getCptCode()
    {
        return cptCode;
    }

    public List<CptModifier> getCptModifiers()
    {
        return cptModifiers;
    }
}
