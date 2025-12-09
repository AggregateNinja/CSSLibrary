package DOS;

import java.util.Date;
import java.io.Serializable;
import java.math.BigDecimal;

public class DetailOrderStatements implements Serializable {

    private static final long serialversionUID = 42L;

    private Integer idDetailOrderStatements;
    private Integer clientId;
    private String subscriberArNo;
    private String statementFilename;
    private BigDecimal balance;
    private Integer userId;
    private Date invoiceDate;
    private Date approvedDate;
    private Integer invoiceNumber;

    public Integer getIdDetailOrderStatements() {
        return idDetailOrderStatements;
    }

    public void setIdDetailOrderStatements(Integer idDetailOrderStatements) {
        this.idDetailOrderStatements = idDetailOrderStatements;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public String getSubscriberArNo() {
        return subscriberArNo;
    }

    public void setSubscriberArNo(String subscriberArNo) {
        this.subscriberArNo = subscriberArNo;
    }

    public String getStatementFilename() {
        return statementFilename;
    }

    public void setStatementFilename(String statementFilename) {
        this.statementFilename = statementFilename;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public Date getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(Date approvedDate) {
        this.approvedDate = approvedDate;
    }
    
    public Integer getInvoiceNumber()
    {
        return invoiceNumber;
    }
    
    public void setInvoiceNumber(Integer invoiceNumber)
    {
        this.invoiceNumber = invoiceNumber;
    }
}
