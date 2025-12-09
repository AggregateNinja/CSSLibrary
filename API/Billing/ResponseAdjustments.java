/*
 * Computer Service & Support, Inc.  All Rights Reserved.
 */

package API.Billing;

import API.BaseResponse;
import DOS.DetailCptCode;
import DOS.Ledger;
import java.util.Map;

/**
 *
 * @author Rob
 */
public class ResponseAdjustments extends BaseResponse<ResponseAdjustments>
{
    final Map<DetailCptCode, Ledger> detailCptLedgerLines;
    
    public ResponseAdjustments(Map<DetailCptCode, Ledger>  detailCptLedgerLines)
    {
        this.detailCptLedgerLines = detailCptLedgerLines;
    }

    public Map<DetailCptCode, Ledger> getDetailCptLedgerLines() {
        return detailCptLedgerLines;
    }
    
    @Override
    public String toXML() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ResponseAdjustments fromXML(String xml) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
