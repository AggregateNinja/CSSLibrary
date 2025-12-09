package EMR.DOS;

public class EmrFile
{

    private static final long serialversionUID = 42L;

    private Integer id;
    private Integer idEmrFileLog;
    private String hl7File;

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Integer getIdEmrFileLog()
    {
        return idEmrFileLog;
    }

    public void setIdEmrFileLog(Integer idEmrFileLog)
    {
        this.idEmrFileLog = idEmrFileLog;
    }

    public String getHl7File()
    {
        return hl7File;
    }

    public void setHl7File(String hl7File)
    {
        this.hl7File = hl7File;
    }
}
