package DOS;

import java.util.Date;
import java.io.Serializable;

public class SalesGroupLookup implements Serializable {

    private static final long serialversionUID = 42L;

    private Integer idSalesGroupLookup;
    private Integer salesmanId;
    private Integer salesGroupId;

    public Integer getIdSalesGroupLookup() {
        return idSalesGroupLookup;
    }

    public void setIdSalesGroupLookup(Integer idSalesGroupLookup) {
        this.idSalesGroupLookup = idSalesGroupLookup;
    }

    public Integer getSalesmanId() {
        return salesmanId;
    }

    public void setSalesmanId(Integer salesmanId) {
        this.salesmanId = salesmanId;
    }

    public Integer getSalesGroupId() {
        return salesGroupId;
    }

    public void setSalesGroupId(Integer salesGroupId) {
        this.salesGroupId = salesGroupId;
    }
}
