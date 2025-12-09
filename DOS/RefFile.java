/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DOS;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Lookup table for the file name that a reference lab result was contained in
 * @author TomR
 */
@Entity
@Table(name = "refFiles", catalog = "css", schema="")
public class RefFile implements Serializable
{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idRefFiles")
    private Integer idRefFiles;
    @Basic(optional = false)
    @Column(name = "departmentId")
    private int departmentId;
    @Basic(optional = false)
    @Column(name = "fileName")
    private String fileName;

    public Integer getIdRefFiles() {
        return idRefFiles;
    }

    public void setIdRefFiles(Integer idRefFiles) {
        this.idRefFiles = idRefFiles;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (idRefFiles != null ? idRefFiles.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RefFile))
        {
            return false;
        }
        RefFile other = (RefFile) object;
        if ((this.idRefFiles == null && other.idRefFiles != null) || (this.idRefFiles != null && !this.idRefFiles.equals(other.idRefFiles)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "RefFile[ id=" + idRefFiles + ", deptId:" + departmentId + ", name:" + fileName + "]";
    }
}
