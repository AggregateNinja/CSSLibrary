/*
 * Computer Service & Support, Inc.  All Rights Reserved Jul 29, 2014
 */

package DOS;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * @date:   Jul 29, 2014  3:21:28 PM
 * @author: Ryan Piper ryan@csslis.com
 * 
 * @project: CSSLibrary
 * @file name: SubscriberComment.java  (UTF-8)
 *
 * @Description: 
 *
 */

@Entity
@Table(name = "subscriberComment", catalog = "vessel2", schema = "")
@NamedQueries({
    @NamedQuery(name = "SubscriberComment.findAll", query = "SELECT s FROM SubscriberComment s")})
public class SubscriberComment implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "subscriber")
    private String subscriber;
    @Basic(optional = false)
    @Lob
    @Column(name = "comment")
    private String comment;

    public SubscriberComment() {
    }

    public SubscriberComment(Integer id) {
        this.id = id;
    }

    public SubscriberComment(Integer id, String comment) {
        this.id = id;
        this.comment = comment;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(String subscriber) {
        this.subscriber = subscriber;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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
        if (!(object instanceof SubscriberComment)) {
            return false;
        }
        SubscriberComment other = (SubscriberComment) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DOS.SubscriberComment[ id=" + id + " ]";
    }

}
