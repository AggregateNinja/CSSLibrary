package DOS;

import java.util.Date;
import java.io.Serializable;
import java.math.BigDecimal;

public class DetailOrderStatementDetails implements Serializable {

    private static final long serialversionUID = 42L;

    private Integer idDetailOrderStatementDetails;
    private Integer detailOrderStatementId;
    private Integer detailOrderId;
    private BigDecimal balance;
    private Integer submissionQueueId;
    private Date created;

    public Integer getIdDetailOrderStatementDetails() {
        return idDetailOrderStatementDetails;
    }

    public void setIdDetailOrderStatementDetails(Integer idDetailOrderStatementDetails) {
        this.idDetailOrderStatementDetails = idDetailOrderStatementDetails;
    }

    public Integer getDetailOrderStatementId() {
        return detailOrderStatementId;
    }

    public void setDetailOrderStatementId(Integer detailOrderStatementId) {
        this.detailOrderStatementId = detailOrderStatementId;
    }

    public Integer getDetailOrderId() {
        return detailOrderId;
    }

    public void setDetailOrderId(Integer detailOrderId) {
        this.detailOrderId = detailOrderId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Integer getSubmissionQueueId() {
        return submissionQueueId;
    }

    public void setSubmissionQueueId(Integer submissionQueueId) {
        this.submissionQueueId = submissionQueueId;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}
