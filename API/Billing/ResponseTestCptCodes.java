
package API.Billing;

import API.BaseResponse;
import API.Billing.Common.TestContextCptMap;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.basic.DateConverter;
import com.thoughtworks.xstream.converters.reflection.PureJavaReflectionProvider;
import com.thoughtworks.xstream.io.xml.DomDriver;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class ResponseTestCptCodes extends BaseResponse<ResponseTestCptCodes>
{
    private static final long serialVersionUID = 42L;
    // Each test context is one-to-many mapped to a CPT code, which is
    // zero-to-many mapped to CptModifiers

    private List<TestContextCptMap> testContextCptMaps = new LinkedList<>();
    
    public ResponseTestCptCodes()
    {
        super();
    }
    
    public ResponseTestCptCodes(Map<String, String> responseFailureCodes)
    {
        super(responseFailureCodes);
    }

    @Override
    public String toXML()
    {
        return getXStream().toXML(this);
    }

    @Override
    public ResponseTestCptCodes fromXML(String xml)
    {
        return (ResponseTestCptCodes)getXStream().fromXML(xml);
    }
    
    private XStream getXStream()
    {
        XStream xs = new XStream(new PureJavaReflectionProvider(), new DomDriver());
        // The createdDate on CPT codes is causing a date parsing problem
        String[] fallbackFormats = new String[] { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss" };
        
        xs.registerConverter(new DateConverter("yyyy-MM-dd HH:mm:ss.S", fallbackFormats));
        xs.aliasSystemAttribute(null, "class");
        xs.alias("cptcoderesponse", ResponseTestCptCodes.class);
        
        return xs;
    }
    
    public List<TestContextCptMap> getTestContextCptMaps()
    {
        return this.testContextCptMaps;
    }
    
    public void setTestContextToCptMaps(List<TestContextCptMap> testContextCptMaps)
    {
        this.testContextCptMaps = testContextCptMaps;
    }

}
