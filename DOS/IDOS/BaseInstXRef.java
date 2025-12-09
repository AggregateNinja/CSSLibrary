/*
 * Copyright (C) 2014 CSS, Inc.  All Rights Reserved.
 * This software is the proprietary information of CSS, Inc.
 */

package DOS.IDOS;

import java.io.Serializable;
import javax.persistence.Column;

/**
 * {Insert Class description here}
 *
 * @version $Revision: 1.1.1.1 $
 * @since Build {insert version here} 01/20/2014  
 * @author Ryan S. Piper <Ryan@csslis.com >
 */

public class BaseInstXRef implements Serializable {

    @Column(name = "idinstXRef")
    private Integer idinstXRef;
    @Column(name = "referencedValue")
    private String referencedValue;
    @Column(name = "test")
    private int test;

    public BaseInstXRef() {
    }

    public BaseInstXRef(Integer idinstXRef1) {
        this.idinstXRef = idinstXRef1;
    }

    public BaseInstXRef(Integer idinstXRef1, String referencedValue, int test) {
        this.idinstXRef = idinstXRef1;
        this.referencedValue = referencedValue;
        this.test = test;
    }

    public Integer getIdinstXRef1() {
        return idinstXRef;
    }

    public void setIdinstXRef1(Integer idinstXRef1) {
        this.idinstXRef = idinstXRef1;
    }

    public String getReferencedValue() {
        return referencedValue;
    }

    public void setReferencedValue(String referencedValue) {
        this.referencedValue = referencedValue;
    }

    public int getTest() {
        return test;
    }

    public void setTest(int test) {
        this.test = test;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idinstXRef != null ? idinstXRef.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BaseInstXRef)) {
            return false;
        }
        BaseInstXRef other = (BaseInstXRef) object;
        if ((this.idinstXRef == null && other.idinstXRef != null) || (this.idinstXRef != null && !this.idinstXRef.equals(other.idinstXRef))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DOS.IDOS.InstXRef1[ idinstXRef1=" + idinstXRef + " ]";
    }

}
