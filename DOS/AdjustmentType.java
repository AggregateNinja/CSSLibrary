package DOS;

import java.util.Date;
import java.io.Serializable;

public class AdjustmentType implements Serializable {

    private static final long serialversionUID = 42L;

    private Integer idAdjustmentType;
    private Integer adjustmentCategoryId;
    private String name;

    public Integer getIdAdjustmentType() {
        return idAdjustmentType;
    }

    public void setIdAdjustmentType(Integer idAdjustmentType) {
        this.idAdjustmentType = idAdjustmentType;
    }

    public Integer getAdjustmentCategoryId() {
        return adjustmentCategoryId;
    }

    public void setAdjustmentCategoryId(Integer adjustmentCategoryId) {
        this.adjustmentCategoryId = adjustmentCategoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
