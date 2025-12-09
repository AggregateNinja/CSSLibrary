/*
 * Computer Service & Support, Inc.  All Rights Reserved.
 */

package API.Billing;

/**
 *
 * @author Rob
 */
public interface TradingPartnerAPIRequest
{
    public TradingPartnerAPIResponse getAllTradingPartners();
    public TradingPartnerAPIResponse getBestMatchingTradingPartners(String insuranceName);
    public TradingPartnerAPIResponse getBestMatchTradingPartner(String insuranceName);
}
