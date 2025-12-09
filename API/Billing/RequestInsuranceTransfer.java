/*
 * Computer Service & Support, Inc.  All Rights Reserved.
 */

package API.Billing;

import API.BaseRequest;
import DOS.DetailCptCode;
import java.util.List;

/**
 *
 * @author Rob
 */
public class RequestInsuranceTransfer extends BaseRequest<RequestAdjustments>
{
    private static final long serialVersionUID = 42L;
    
    private final int detailOrderId;
    private final List<DetailCptCode> detailCptCodes;
    private final int previousDetailInsuranceId;
    private final String previousGroupIdentifier;
    private final String newGroupIdentifier;
    private final String previousIdentifier;
    private final String newIdentifier;
    private final int previousInsuranceId;
    private final int newInsuranceId;
    private final int userId;
    
    public RequestInsuranceTransfer(
            int detailOrderId,
            List<DetailCptCode> detailCptCodes,
            int previousDetailInsuranceId,
            int previousInsuranceId,
            int newInsuranceId,
            String previousGroupIdentifier,
            String newGroupIdentifier,
            String previousIdentifier,
            String newIdentifier,
            int userId)
    {
        super();
        this.detailOrderId = detailOrderId;
        this.detailCptCodes = detailCptCodes;
        this.previousDetailInsuranceId = previousDetailInsuranceId;
        this.previousInsuranceId = previousInsuranceId;
        this.newInsuranceId = newInsuranceId;
        this.userId = userId;
        this.previousGroupIdentifier = previousGroupIdentifier;
        this.newGroupIdentifier = newGroupIdentifier;
        this.previousIdentifier = previousIdentifier;
        this.newIdentifier = newIdentifier;
    }
    
    public int getUserId()
    {
        return userId;
    }

    public int getDetailOrderId() {
        return detailOrderId;
    }

    public List<DetailCptCode> getDetailCptCodes() {
        return detailCptCodes;
    }

    public int getPreviousDetailInsuranceId() {
        return previousDetailInsuranceId;
    }

    public int getPreviousInsuranceId() {
        return previousInsuranceId;
    }

    public int getNewInsuranceId() {
        return newInsuranceId;
    }

    public String getPreviousGroupIdentifier() {
        return previousGroupIdentifier;
    }

    public String getNewGroupIdentifier() {
        return newGroupIdentifier;
    }

    public String getPreviousIdentifier() {
        return previousIdentifier;
    }

    public String getNewIdentifier() {
        return newIdentifier;
    }
    
    @Override
    public String toXML()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public RequestAdjustments fromXML(String xml)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
