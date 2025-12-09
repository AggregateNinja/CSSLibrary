/*
 * Computer Service & Support, Inc.  All Rights Reserved Jun 3, 2014
 */
package DOS;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @date: Jun 3, 2014 12:29:43 AM
 * @author: Ryan Piper ryan@csslis.com
 *
 * @project: CSSLibrary
 * @file name: QcInst1.java (UTF-8)
 *
 * @Description:
 *
 */
@Entity
@Table(name = "qcInst_1")
@NamedQueries({
    @NamedQuery(name = "QcInst1.findAll", query = "SELECT q FROM QcInst1 q")})
public class QcInst implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @Column(name = "level")
    private int level;
    @Basic(optional = false)
    @Column(name = "result")
    private String result;
    @Column(name = "specimenType")
    private Integer specimenType;
    @Column(name = "control")
    private String control;
    @Column(name = "fileName")
    private String fileName;
    @Column(name = "acquisitionDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date acquisitionDate;
    @Basic(optional = false)
    @Column(name = "createdDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Column(name = "postedBy")
    private Integer postedBy;
    @Basic(optional = false)
    @Column(name = "posted")
    private boolean posted;
    @Column(name = "postedDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date postedDate;

    public QcInst() {
    }

    public QcInst(Integer id) {
        this.id = id;
    }

    public QcInst(Integer id, String name, int level, String result, Date createdDate, boolean posted) {
        this.id = id;
        this.name = name;
        this.level = level;
        this.result = result;
        this.createdDate = createdDate;
        this.posted = posted;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Integer getSpecimenType() {
        return specimenType;
    }

    public void setSpecimenType(Integer specimenType) {
        this.specimenType = specimenType;
    }

    public String getControl()
    {
        return control;
    }

    public void setControl(String control)
    {
        this.control = control;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Date getAcquisitionDate() {
        return acquisitionDate;
    }

    public void setAcquisitionDate(Date acquisitionDate) {
        this.acquisitionDate = acquisitionDate;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Integer getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(Integer postedBy) {
        this.postedBy = postedBy;
    }

    public boolean getPosted() {
        return posted;
    }

    public void setPosted(boolean posted) {
        this.posted = posted;
    }

    public Date getPostedDate() {
        return postedDate;
    }

    public void setPostedDate(Date postedDate) {
        this.postedDate = postedDate;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof QcInst)) {
            return false;
        }
        QcInst other = (QcInst) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DOS.QcInst1[ id=" + id + " ]";
    }

}
