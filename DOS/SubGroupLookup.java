package DOS;

import java.io.Serializable;

public class SubGroupLookup implements Serializable {

    private static final long serialversionUID = 42L;

    private Integer idSubGroupLookup;
    private Integer salesGroupId;
    private Integer subGroupId;

    public Integer getIdSubGroupLookup() {
        return idSubGroupLookup;
    }

    public void setIdSubGroupLookup(Integer idSubGroupLookup) {
        this.idSubGroupLookup = idSubGroupLookup;
    }

    public Integer getSalesGroupId() {
        return salesGroupId;
    }

    public void setSalesGroupId(Integer salesGroupId) {
        this.salesGroupId = salesGroupId;
    }

    public Integer getSubGroupId() {
        return subGroupId;
    }

    public void setSubGroupId(Integer subGroupId) {
        this.subGroupId = subGroupId;
    }
}
