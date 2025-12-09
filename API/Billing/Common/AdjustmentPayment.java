/*
 * Computer Service & Support, Inc.  All Rights Reserved.
 */

package API.Billing.Common;

import BL.BillingBL.AdjustmentApplicationMethod;
import DAOS.AdjustmentCategoryDAO.AdjustmentCategories;
import DOS.DetailCptCode;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author Rob
 */
public class AdjustmentPayment implements Serializable
{
    private static final long serialVersionUID = 42L;
    private final DetailCptCode detailCptCode;
    private final BigDecimal paymentAmount;
    private final BigDecimal billedAmount;
    private final AdjustmentCategories category;

    public AdjustmentPayment(DetailCptCode detailCptCode, BigDecimal paymentAmount, BigDecimal billedAmount, AdjustmentCategories category, AdjustmentApplicationMethod method)
    {
        this.detailCptCode = detailCptCode;
        this.paymentAmount = paymentAmount;
        this.billedAmount = billedAmount;
        this.category = category;
    }

    public DetailCptCode getDetailCptCode() {
        return detailCptCode;
    }

    public BigDecimal getPaymentAmount() {
        return paymentAmount;
    }

    public BigDecimal getBilledAmount() {
        return billedAmount;
    }

    public AdjustmentCategories getCategory() {
        return category;
    }

}
