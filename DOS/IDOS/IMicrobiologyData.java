
package DOS.IDOS;

import DOS.MicroSites;
import DOS.MicroSources;
import java.io.Serializable;

public interface IMicrobiologyData extends Serializable
{
    static final long serialVersionUID = 42L;
    
    public String GetDisplay();
    public MicroSources getSource();
    public void setSource(MicroSources microSource);
    public MicroSites getSite();
    public void setSite(MicroSites site);
    public String getComment();
    public void setComment(String comment);
    public Integer getResultId();
    public void setResultId(Integer resultId);
}
