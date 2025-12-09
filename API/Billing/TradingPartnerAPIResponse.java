/*
 * Computer Service & Support, Inc.  All Rights Reserved.
 */

package API.Billing;

import API.Billing.Common.TradingPartner;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Rob
 */
public class TradingPartnerAPIResponse implements Serializable
{
    private static final long serialVersionUID = 42L;
    String bestMatchTradingPartner;
    List<TradingPartner> tradingPartnerList;
    
    public TradingPartnerAPIResponse()
    {
        bestMatchTradingPartner = "";
        tradingPartnerList = new ArrayList<TradingPartner>();
    }
    
    public TradingPartnerAPIResponse(List<TradingPartner> tradingPartnerList)
    {
        if (!tradingPartnerList.isEmpty()) bestMatchTradingPartner = tradingPartnerList.get(0).getId();
        else bestMatchTradingPartner = "";
        this.tradingPartnerList = tradingPartnerList;
    }
    
    public List<TradingPartner> getTradingPartners() {
        return tradingPartnerList;
    }
}
