/*
 * Copyright (C) 2013 CSS, Inc.  All Rights Reserved.
 * This software is the proprietary information of CSS, Inc.
 */

package DOS;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * {Insert Class description here}
 *
 * @version $Revision: 1.1.1.1 $
 * @since Build {insert version here} 06/14/2013  
 * @author Ryan S. Piper <Ryan@csslis.com >
 */
@Entity
@Table(name = "ordercomment")
@NamedQueries({
    @NamedQuery(name = "Ordercomment.findAll", query = "SELECT o FROM Ordercomment o")})
public class Ordercomment implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idorderComment")
    private Integer idorderComment;
    @Basic(optional = false)
    @Column(name = "orderId")
    private Integer orderId;
    @Basic(optional = false)
    @Lob
    @Column(name = "comment")
    private String comment;
    @Column(name = "advancedOrder")
    private Boolean advancedOrder;

    public Ordercomment() {
    }

    public Ordercomment(Integer idorderComment) {
        this.idorderComment = idorderComment;
    }

    public Ordercomment(Integer idorderComment, String comment) {
        this.idorderComment = idorderComment;
        this.comment = comment;
    }

    public Integer getIdorderComment() {
        return idorderComment;
    }

    public void setIdorderComment(Integer idorderComment) {
        this.idorderComment = idorderComment;
    }

    public Integer getOrderId()
    {
        return orderId;
    }

    public void setOrderId(Integer orderId)
    {
        this.orderId = orderId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Boolean IsAdvancedOrder()
    {
        return advancedOrder;
    }

    public void setAdvancedOrder(Boolean advancedOrder)
    {
        this.advancedOrder = advancedOrder;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idorderComment != null ? idorderComment.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ordercomment)) {
            return false;
        }
        Ordercomment other = (Ordercomment) object;
        if ((this.idorderComment == null && other.idorderComment != null) || (this.idorderComment != null && !this.idorderComment.equals(other.idorderComment))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DOS.Ordercomment[ idorderComment=" + idorderComment + " ]";
    }

}
