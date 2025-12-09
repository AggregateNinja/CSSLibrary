/*
 * Computer Service & Support, Inc.  All Rights Reserved.
 */

package API.Billing.Common;

import java.io.Serializable;

/**
 *
 * @author Rob
 */
public class TradingPartner implements Serializable
{
    private static final long serialVersionUID = 42L;
    String id;
    String[] enrollment_required;
    Boolean is_enabled;
    String name;
    String[] supported_transactions;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String[] getEnrollment_required() {
        return enrollment_required;
    }

    public void setEnrollment_required(String[] enrollment_required) {
        this.enrollment_required = enrollment_required;
    }

    public Boolean isIs_enabled() {
        return is_enabled;
    }

    public void setIs_enabled(Boolean is_enabled) {
        this.is_enabled = is_enabled;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getSupported_transactions() {
        return supported_transactions;
    }

    public void setSupported_transactions(String[] supported_transactions) {
        this.supported_transactions = supported_transactions;
    }
    
    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        if (name != null) sb.append(name);
        sb.append(" ");
        if (id != null)
        {
            sb.append("(");
            sb.append(id);
            sb.append(")");
        }
        return sb.toString();
    }
}
