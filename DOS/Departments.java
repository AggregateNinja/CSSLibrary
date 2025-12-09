
package DOS;

import java.io.Serializable;
import javax.persistence.*;

/**
 * @date:   Mar 3, 2012
 * @author: Ryan
 * 
 * @project: CSSLibrary 
 * @package: DAOS
 * @file name: Departments.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc.  All Rights Reserved
 */



@Entity
@Table(name = "departments", catalog = "css", schema = "")
@NamedQueries({
    @NamedQuery(name = "Departments.findAll", query = "SELECT d FROM Departments d")})
public class Departments implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idDepartment", nullable = false)
    private Integer idDepartment;
    @Basic(optional = false)
    @Column(name = "deptNo", nullable = false)
    private int deptNo;
    @Basic(optional = false)
    @Column(name = "deptName", nullable = false, length = 45)
    private String deptName;
    @Basic(optional = false)
    @Column(name = "ReferenceLab", nullable = false)
    private boolean referenceLab;
    @Column(name = "promptPOC")
    private Boolean promptPOC;
    @Column(name = "comment")
    private String comment;
    @Column(name = "testXref")
    private Integer testXref;
    @Column(name = "resTable")
    private String resTable;
    @Column(name = "resultProcedure")
    private String resultProcedure;

    public Departments() {
    }

    public Departments(Integer idDepartment) {
        this.idDepartment = idDepartment;
    }

    public Departments(Integer idDepartment, int deptNo, String deptName, boolean referenceLab) {
        this.idDepartment = idDepartment;
        this.deptNo = deptNo;
        this.deptName = deptName;
        this.referenceLab = referenceLab;
    }

    public Integer getIdDepartment() {
        return idDepartment;
    }

    public void setIdDepartment(Integer idDepartment) {
        this.idDepartment = idDepartment;
    }

    public int getDeptNo() {
        return deptNo;
    }

    public void setDeptNo(int deptNo) {
        this.deptNo = deptNo;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public boolean isReferenceLab() {
        return referenceLab;
    }

    public void setReferenceLab(boolean referenceLab) {
        this.referenceLab = referenceLab;
    }
    
    public boolean isPromptPOC() {
        return promptPOC;
    }

    public void setPromptPOC(boolean promptPOC) {
        this.promptPOC = promptPOC;
    }

    public String getComment()
    {
        return comment;
    }

    public void setComment(String comment)
    {
        this.comment = comment;
    }

    public Integer getTestXref() {
        return testXref;
    }

    public void setTestXref(Integer testXref) {
        this.testXref = testXref;
    }

    public String getResTable() {
        return resTable;
    }

    public void setResTable(String resTable) {
        this.resTable = resTable;
    }
    
    public String getResultProcedure() {
        return resultProcedure;
    }

    public void setResultProcedure(String resultProcedure) {
        this.resultProcedure = resultProcedure;
    }    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDepartment != null ? idDepartment.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Departments)) {
            return false;
        }
        Departments other = (Departments) object;
        if ((this.idDepartment == null && other.idDepartment != null) || (this.idDepartment != null && !this.idDepartment.equals(other.idDepartment))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return (deptName == null? "Department name not defined" : deptName);
    }

}
