package DOS;

import java.util.Date;
import java.io.Serializable;

public class AdjustmentApplicationMethods implements Serializable {

    private static final long serialversionUID = 42L;

    private Integer idAdjustmentApplicationMethods;
    private String name;

    public Integer getIdAdjustmentApplicationMethods() {
        return idAdjustmentApplicationMethods;
    }

    public void setIdAdjustmentApplicationMethods(Integer idAdjustmentApplicationMethods) {
        this.idAdjustmentApplicationMethods = idAdjustmentApplicationMethods;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
