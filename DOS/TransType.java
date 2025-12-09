package DOS;

import java.util.Date;
import java.io.Serializable;

public class TransType implements Serializable {

    private static final long serialversionUID = 42L;

    private Integer idTransType;
    private String transTypeName;
    private Boolean active;

    public Integer getIdTransType() {
        return idTransType;
    }

    public void setIdTransType(Integer idTransType) {
        this.idTransType = idTransType;
    }

    public String getTransTypeName() {
        return transTypeName;
    }

    public void setTransTypeName(String transTypeName) {
        this.transTypeName = transTypeName;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
