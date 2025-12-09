package EMR.DOS;

public class OrderComment
{

    private static final long serialversionUID = 42L;

    private Integer idorderComment;
    private Integer orderId;
    private Boolean advancedOrder;
    private String comment;

    public Integer getIdorderComment()
    {
        return idorderComment;
    }

    public void setIdorderComment(Integer idorderComment)
    {
        this.idorderComment = idorderComment;
    }

    public Integer getOrderId()
    {
        return orderId;
    }

    public void setOrderId(Integer orderId)
    {
        this.orderId = orderId;
    }

    public Boolean isAdvancedOrder()
    {
        return advancedOrder;
    }

    public void setAdvancedOrder(Boolean advancedOrder)
    {
        this.advancedOrder = advancedOrder;
    }

    public String getComment()
    {
        return comment;
    }

    public void setComment(String comment)
    {
        this.comment = comment;
    }
}
