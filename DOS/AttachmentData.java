
package DOS;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

public class AttachmentData
{
    private static final long serialversionUID = 42L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private int id;
    @Basic(optional=false)
    @Lob
    @Column(name = "attachment")
    private byte[] attachment;    

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public byte[] getAttachment()
    {
        return attachment;
    }

    public void setAttachment(byte[] attachment)
    {
        this.attachment = attachment;
    }
}
