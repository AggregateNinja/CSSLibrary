package DOS;

import java.util.Date;
import java.io.Serializable;

public class AdjustmentCategory implements Serializable {

    private static final long serialversionUID = 42L;

    private Integer idAdjustmentCategory;
    private String name;

    public Integer getIdAdjustmentCategory() {
        return idAdjustmentCategory;
    }

    public void setIdAdjustmentCategory(Integer idAdjustmentCategory) {
        this.idAdjustmentCategory = idAdjustmentCategory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
