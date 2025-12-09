/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DOS;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author TomR
 */
public class AttachmentMetadata
{
    private static final long serialversionUID = 42L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private int id;
    @Basic(optional = false)
    @Column(name = "attachmentDataId")
    private int attachmentDataId;
    @Column(name = "fileName")
    private String fileName;    
    @Column(name = "extension")
    private String extension;
    @Column(name = "path")
    private String path;
    @Column(name = "md5")
    private String md5;    
    @Column(name = "fileSizeBytes")
    private Integer fileSizeBytes;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public int getAttachmentDataId()
    {
        return attachmentDataId;
    }

    public void setAttachmentDataId(int attachmentDataId)
    {
        this.attachmentDataId = attachmentDataId;
    }

    public String getFileName()
    {
        return fileName;
    }

    public void setFileName(String fileName)
    {
        this.fileName = fileName;
    }

    public String getExtension()
    {
        return extension;
    }

    public void setExtension(String extension)
    {
        this.extension = extension;
    }

    public String getPath()
    {
        return path;
    }

    public void setPath(String path)
    {
        this.path = path;
    }

    public String getMd5()
    {
        return md5;
    }

    public void setMd5(String md5)
    {
        this.md5 = md5;
    }

    public Integer getFileSizeBytes()
    {
        return fileSizeBytes;
    }

    public void setFileSizeBytes(Integer fileSizeBytes)
    {
        this.fileSizeBytes = fileSizeBytes;
    }    
}
 
