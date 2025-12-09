package DOS.IDOS;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @date:   Dec 20, 2013
 * @author: Ryan Piper
 * 
 * @project:  
 * @package: DOS.IDOS
 * @file name: BaseInstRes.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc.  All Rights Reserved
 */

public abstract class BaseInstRes implements Serializable
{
    //@Column(name = "idinstresult")
    //private Integer idinstresult;
    @Column(name = "accession")
    private String accession;
    @Column(name = "identifier")
    private String identifier;
    @Column(name = "name")
    private String name;
    @Column(name = "result")
    private String result;
    @Column(name = "comment")
    private String comment;
    @Column(name = "units")
    private String units;
    @Column(name = "range")
    private String range;
    @Column(name = "specimenType")
    private String specimenType;
    @Column(name = "dilution")
    private String dilution;
    @Column(name = "interpretation")
    private String interpretation;
    @Column(name = "iteration")
    private String iteration;
    @Column(name = "postedDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date postedDate;
    @Column(name = "user")
    private String user;
    @Column(name = "posted")
    private boolean posted;
    @Column(name = "fileName")
    private String fileName;
    
    @Column(name = "created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;
    
    @Column(name = "remarkId")
    private Integer remarkId;
    
    @Column(name = "postToResultId")
    private Integer postToResultId;

    public BaseInstRes()
    {
    }

    public BaseInstRes(String accession, 
                       String identifier, 
                       String result, 
                       String comment, 
                       String units, 
                       String range, 
                       String specimenType, 
                       String dilution, 
                       String interpretation, 
                       String iteration, 
                       Date postedDate, 
                       String user,
                       boolean posted
            )
    {
        this.accession = accession;
        this.identifier = identifier;
        this.result = result;
        this.comment = comment;
        this.units = units;
        this.range = range;
        this.specimenType = specimenType;
        this.dilution = dilution;
        this.interpretation = interpretation;
        this.iteration = iteration;
        this.postedDate = postedDate;
        this.user = user;
        this.posted = posted;
    }
    
    public BaseInstRes(String accession, 
                       String identifier, 
                       String result, 
                       String comment, 
                       String units, 
                       String range, 
                       String specimenType, 
                       String dilution, 
                       String interpretation, 
                       String iteration, 
                       Date postedDate, 
                       String user,
                       boolean posted,
                       Date created
            )
    {
        this.accession = accession;
        this.identifier = identifier;
        this.result = result;
        this.comment = comment;
        this.units = units;
        this.range = range;
        this.specimenType = specimenType;
        this.dilution = dilution;
        this.interpretation = interpretation;
        this.iteration = iteration;
        this.postedDate = postedDate;
        this.user = user;
        this.posted = posted;
        this.created = created;
    }

    public String getAccession()
    {
        return accession;
    }

    public void setAccession(String accession)
    {
        this.accession = accession;
    }

    public String getIdentifier()
    {
        return identifier;
    }

    public void setIdentifier(String identifier)
    {
        this.identifier = identifier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getResult()
    {
        return result;
    }

    public void setResult(String result)
    {
        this.result = result;
    }

    public String getComment()
    {
        return comment;
    }

    public void setComment(String comment)
    {
        this.comment = comment;
    }

    public String getUnits()
    {
        return units;
    }

    public void setUnits(String units)
    {
        this.units = units;
    }

    public String getRange()
    {
        return range;
    }

    public void setRange(String range)
    {
        this.range = range;
    }

    public String getSpecimenType()
    {
        return specimenType;
    }

    public void setSpecimenType(String specimenType)
    {
        this.specimenType = specimenType;
    }

    public String getDilution()
    {
        return dilution;
    }

    public void setDilution(String dilution)
    {
        this.dilution = dilution;
    }

    public String getInterpretation()
    {
        return interpretation;
    }

    public void setInterpretation(String interpretation)
    {
        this.interpretation = interpretation;
    }

    public String getIteration()
    {
        return iteration;
    }

    public void setIteration(String iteration)
    {
        this.iteration = iteration;
    }

    public Date getPostedDate()
    {
        return postedDate;
    }

    public void setPostedDate(Date postedDate)
    {
        this.postedDate = postedDate;
    }

    public String getUser()
    {
        return user;
    }

    public void setUser(String user)
    {
        this.user = user;
    }

    public boolean getPosted() {
        return posted;
    }

    public void setPosted(boolean posted) {
        this.posted = posted;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    
    public Date getCreated()
    {
        return created;
    }

    public void setCreated(Date created)
    {
        this.created = created;
    }
    
    public Integer getRemarkId() {
        return remarkId;
    }
    public void setRemarkId(Integer remarkId) {
        this.remarkId = remarkId;
    }
    
    public Integer getPostToResultId() {
        return postToResultId;
    }
    public void setPostToResultId(Integer postToResultId) {
        this.postToResultId = postToResultId;
    }
}
