package DOS;

import java.util.Date;
import java.io.Serializable;


public class PPSBillingLookup implements Serializable
{
	private static final long serialversionUID = 42L;

	private Integer idppsBillingLookup;
	private Integer orderId;
	private Date dateAdded;
        private Integer user;
        private Date dateApproved;
        private Integer approvedBy;
        private Boolean approved;
        private String approvedReason;


	public Integer getIdppsBillingLookup()
	{
		return idppsBillingLookup;
	}


	public void setIdppsBillingLookup(Integer idppsBillingLookup)
	{
		this.idppsBillingLookup = idppsBillingLookup;
	}


	public Integer getOrderId()
	{
		return orderId;
	}


	public void setOrderId(Integer orderId)
	{
		this.orderId = orderId;
	}


	public Date getDateAdded()
	{
		return dateAdded;
	}


	public void setDateAdded(Date dateAdded)
	{
		this.dateAdded = dateAdded;
	}
        
        public Integer getUser()
	{
		return user;
	}


	public void setUser(Integer user)
	{
		this.user = user;
	}
        
        public Date getDateApproved()
        {
            return dateApproved;
        }
        
        public void setDateApproved(Date dateApproved)
        {
            this.dateApproved = dateApproved;
        }
        
        public Integer getApprovedBy()
        {
            return approvedBy;
        }
        
        public void setApprovedBy(Integer approvedBy)
        {
            this.approvedBy = approvedBy;
        }
        
        public Boolean isApproved()
        {
            return approved;
        }
        
        public void setApproved(Boolean approved)
        {
            this.approved = approved;
        }
        
        public String getApprovedReason()
        {
            return approvedReason;
        }
        
        public void setApprovedReason(String approvedReason)
        {
            this.approvedReason = approvedReason;
        }
}