
package DOS;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;

/**
 * @date:   Mar 3, 2012
 * @author: Ryan
 * 
 * @project: CSSLibrary 
 * @package: DAOS
 * @file name: Instruments.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc.  All Rights Reserved
 */



@Entity
@Table(name = "instruments", catalog = "css", schema = "")
@NamedQueries({
    @NamedQuery(name = "Instruments.findAll", query = "SELECT i FROM Instruments i")})
public class Instruments implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idInst", nullable = false)
    private Integer idInst;
    @Basic(optional = false)
    @Column(name = "instName", nullable = false, length = 45)
    private String instName;
    @Basic(optional = false)
    @Column(name = "instNo", nullable = false)
    private Integer instNo;
    @Basic(optional = false)
    @Column(name = "online", nullable = false)
    private boolean online;
    @Column(name = "manufacturer")
    private Integer manufacturer;
    @Column(name = "datePurchased")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date datePurchased;
    @Column(name = "serialNumber")
    private String serialNumber;
    @Column(name = "commType")
    private String commType;
    @Column(name = "filePath")
    private String filePath;
    @Column(name = "tty")
    private String tty;
    @Column(name = "port")
    private String port;
    @Column(name = "ipAddress")
    private String ipAddress;
    @Column(name = "fileRegExpr")
    private String fileRegExpr;
    @Column(name = "resTable")
    private String resTable;
    @Column(name = "ordTable")
    private String ordTable;
    @Column(name = "xrefTable")
    private String xrefTable;
    @Column(name = "processedPath")
    private String processedPath;
    @Column(name = "fileReader")
    private String fileReader;
    @Column(name = "useOnlineCode")
    private boolean useOnlineCode;
    @Column(name = "comment")
    private byte[] comment;
    @Column(name = "multipleSpecimen")
    private boolean multipleSpecimen;
    @Column(name = "usesAutomatedPosting")
    private boolean usesAutomatedPosting;
    @Column(name = "isLocal")
    private boolean isLocal;
    @Column(name = "qcTable")
    private String qcTable;
    @Column(name = "qcLevels")
    private Integer qcLevels;
    @Column(name = "lcmsInst")
    private boolean lcmsInst;
    @Column(name = "translationalInst")
    private boolean translationalInst;
    @Column(name = "postingUseSpecimenType")
    private boolean postingUseSpecimenType;
    @Column(name = "postOnServer")
    private boolean postOnServer;

    public Instruments() {
    }

    public Instruments(Integer idInst) {
        this.idInst = idInst;
    }

    public Instruments(Integer idInst, String instName, int instNo, boolean online) {
        this.idInst = idInst;
        this.instName = instName;
        this.instNo = instNo;
        this.online = online;
    }

    public Integer getIdInst() {
        return idInst;
    }

    public void setIdInst(Integer idInst) {
        this.idInst = idInst;
    }

    public String getInstName() {
        return instName;
    }

    public void setInstName(String instName) {
        this.instName = instName;
    }

    public Integer getInstNo() {
        return instNo;
    }

    public void setInstNo(Integer instNo) {
        this.instNo = instNo;
    }

    public boolean getOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public Integer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Integer manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Date getDatePurchased() {
        return datePurchased;
    }

    public void setDatePurchased(Date datePurchased) {
        this.datePurchased = datePurchased;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getCommType() {
        return commType;
    }

    public void setCommType(String commType) {
        this.commType = commType;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getTty() {
        return tty;
    }

    public void setTty(String tty) {
        this.tty = tty;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getFileRegExpr() {
        return fileRegExpr;
    }

    public void setFileRegExpr(String fileRegExpr) {
        this.fileRegExpr = fileRegExpr;
    }

    public String getResTable() {
        return resTable;
    }

    public void setResTable(String resTable) {
        this.resTable = resTable;
    }

    public String getOrdTable() {
        return ordTable;
    }

    public void setOrdTable(String ordTable) {
        this.ordTable = ordTable;
    }

    public String getXrefTable() {
        return xrefTable;
    }

    public void setXrefTable(String xrefTable) {
        this.xrefTable = xrefTable;
    }

    public String getProcessedPath() {
        return processedPath;
    }

    public void setProcessedPath(String processedPath) {
        this.processedPath = processedPath;
    }

    public String getFileReader() {
        return fileReader;
    }

    public void setFileReader(String fileReader) {
        this.fileReader = fileReader;
    }

    public boolean getUseOnlineCode() {
        return useOnlineCode;
    }

    public void setUseOnlineCode(boolean useOnlineCode) {
        this.useOnlineCode = useOnlineCode;
    }

    public byte[] getComment() {
        return comment;
    }

    public void setComment(byte[] comment) {
        this.comment = comment;
    }

    public boolean getMultipleSpecimen() {
        return multipleSpecimen;
    }

    public void setMultipleSpecimen(boolean multipleSpecimen) {
        this.multipleSpecimen = multipleSpecimen;
    }

    public boolean getUsesAutomatedPosting() {
        return usesAutomatedPosting;
    }

    public void setUsesAutomatedPosting(boolean usesAutomatedPosting) {
        this.usesAutomatedPosting = usesAutomatedPosting;
    }

    public boolean getIsLocal() {
        return isLocal;
    }

    public void setIsLocal(boolean isLocal) {
        this.isLocal = isLocal;
    }

    public String getQcTable() {
        return qcTable;
    }

    public void setQcTable(String qcTable) {
        this.qcTable = qcTable;
    }

    public Integer getQcLevels() {
        return qcLevels;
    }

    public void setQcLevels(Integer qcLevels) {
        this.qcLevels = qcLevels;
    }

    public boolean getLcmsInst() {
        return lcmsInst;
    }

    public void setLcmsInst(boolean lcmsInst) {
        this.lcmsInst = lcmsInst;
    }

    public boolean isTranslationalInst() {
        return translationalInst;
    }

    public void setTranslationalInst(boolean translationalInst) {
        this.translationalInst = translationalInst;
    }

    public boolean isPostingUseSpecimenType() {
        return postingUseSpecimenType;
    }

    public void setPostingUseSpecimenType(boolean postingUseSpecimenType) {
        this.postingUseSpecimenType = postingUseSpecimenType;
    }

    public boolean isPostOnServer() {
        return postOnServer;
    }

    public void setPostOnServer(boolean postOnServer) {
        this.postOnServer = postOnServer;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idInst != null ? idInst.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Instruments)) {
            return false;
        }
        Instruments other = (Instruments) object;
        if ((this.idInst == null && other.idInst != null) || (this.idInst != null && !this.idInst.equals(other.idInst))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return (instName == null? "Instrument name not defined" : instName);
    }

}
