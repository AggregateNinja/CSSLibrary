/*
 * Copyright (C) 2013 CSS, Inc.  All Rights Reserved.
 * This software is the proprietary information of CSS, Inc.
 */

package DOS;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * {Insert Class description here}
 *
 * @version $Revision: 1.1.1.1 $
 * @since Build {insert version here} 04/02/2013  
 * @author Ryan S. Piper <Ryan@csslis.com >
 */
@Embeddable
public class PanelsPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "idpanels")
    private int idpanels;
    @Basic(optional = false)
    @Column(name = "subtestId")
    private int subtestId;

    public PanelsPK() {
    }

    public PanelsPK(int idpanels, int subtestId) {
        this.idpanels = idpanels;
        this.subtestId = subtestId;
    }

    public int getIdpanels() {
        return idpanels;
    }

    public void setIdpanels(int idpanels) {
        this.idpanels = idpanels;
    }

    public int getSubtestId() {
        return subtestId;
    }

    public void setSubtestId(int subtestId) {
        this.subtestId = subtestId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idpanels;
        hash += (int) subtestId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PanelsPK)) {
            return false;
        }
        PanelsPK other = (PanelsPK) object;
        if (this.idpanels != other.idpanels) {
            return false;
        }
        if (this.subtestId != other.subtestId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DOS.PanelsPK[ idpanels=" + idpanels + ", subtestId=" + subtestId + " ]";
    }

}
