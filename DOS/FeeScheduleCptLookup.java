package DOS;

import Utility.Diff;
import java.math.BigDecimal;

public class FeeScheduleCptLookup
{
    private static final long serialversionUID = 42L;

    private Integer idFeeScheduleCptLookup;
    private Integer feeScheduleTestLookupId;
    private Integer cptCodeId;
    private BigDecimal cost;
    private Integer quantity;
    private Integer feeScheduleActionId;
    private Integer quantifiedMaximum;
    private String lineNote;

    @Diff(isUniqueId = true, fieldName = "idFeeScheduleCptLookup")
    public Integer getIdFeeScheduleCptLookup()
    {
        return idFeeScheduleCptLookup;
    }

    public void setIdFeeScheduleCptLookup(Integer idFeeScheduleCptLookup)
    {
        this.idFeeScheduleCptLookup = idFeeScheduleCptLookup;
    }

    public Integer getFeeScheduleTestLookupId()
    {
        return feeScheduleTestLookupId;
    }

    public void setFeeScheduleTestLookupId(Integer feeScheduleTestLookupId)
    {
        this.feeScheduleTestLookupId = feeScheduleTestLookupId;
    }

    @Diff(fieldName = "cptCodeId")
    public Integer getCptCodeId()
    {
        return cptCodeId;
    }

    public void setCptCodeId(Integer cptCodeId)
    {
        this.cptCodeId = cptCodeId;
    }

    @Diff(fieldName = "cost")
    public BigDecimal getCost()
    {
        return cost;
    }

    public void setCost(BigDecimal cost)
    {
        this.cost = cost;
    }

    @Diff(fieldName = "quantity")
    public Integer getQuantity()
    {
        return quantity;
    }

    public void setQuantity(Integer quantity)
    {
        this.quantity = quantity;
    }

    @Diff(fieldName = "feeScheduleActionId")
    public Integer getFeeScheduleActionId()
    {
        return feeScheduleActionId;
    }

    @Diff(fieldName = "quantifiedMaximum")
    public Integer getQuantifiedMaximum()
    {
        return quantifiedMaximum;
    }

    public void setQuantifiedMaximum(Integer quantifiedMaximum)
    {
        this.quantifiedMaximum = quantifiedMaximum;
    }

    public void setFeeScheduleActionId(Integer feeScheduleActionId)
    {
        this.feeScheduleActionId = feeScheduleActionId;
    }

    @Diff(fieldName = "lineNote")
    public String getLineNote() {
        return lineNote;
    }

    public void setLineNote(String lineNote) {
        this.lineNote = lineNote;
    }

    public FeeScheduleCptLookup copy()
    {
        FeeScheduleCptLookup lookup = new FeeScheduleCptLookup();
        lookup.setIdFeeScheduleCptLookup(this.getIdFeeScheduleCptLookup());
        lookup.setFeeScheduleTestLookupId(this.getFeeScheduleTestLookupId());
        lookup.setCptCodeId(this.getCptCodeId());
        lookup.setCost(this.getCost());
        lookup.setQuantity(this.getQuantity());
        lookup.setFeeScheduleActionId(this.getFeeScheduleActionId());
        lookup.setQuantifiedMaximum(this.getQuantifiedMaximum());
        lookup.setLineNote(this.getLineNote());
        return lookup;
    }

    @Override
    public String toString()
    {
        String output = "";
        output += "\nidFeeScheduleCptLookup=" + (idFeeScheduleCptLookup == null ? "[NULL]" : idFeeScheduleCptLookup.toString());
        output += "\nfeeScheduleTestLookupId=" + (feeScheduleTestLookupId == null ? "[NULL]" : feeScheduleTestLookupId.toString());
        output += "\ncptCodeId=" + (cptCodeId == null ? "[NULL]" : cptCodeId.toString());
        output += "\ncost=" + (cost == null ? "[NULL]" : cost.toString());
        output += "\nquantity=" + (quantity == null ? "[NULL]" : quantity.toString());
        output += "\nfeeScheduleActionId=" + (feeScheduleActionId == null ? "[NULL]" : feeScheduleActionId.toString());
        output += "\nquantifiedMaximum=" + (quantifiedMaximum == null ? "[NULL]" : quantifiedMaximum.toString());
        output += "\nlineNote=" + (lineNote == null ? "[NULL]" : lineNote.toString());
        return output;
    }
}
