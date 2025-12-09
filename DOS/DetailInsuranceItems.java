/*
 * Computer Service & Support, Inc.  All Rights Reserved.
 */
package DOS;

import java.io.Serializable;

/**
 *
 * @author Rob
 */
public class DetailInsuranceItems implements Serializable {

    private static final long serialversionUID = 42L;

    private Integer idDetailInsuranceItems;
    private Integer detailInsuranceId;
    private Integer detailCptCodeId;
    private Integer submissionStatusId;
    private Integer timesBilled;

    public Integer getIdDetailInsuranceItems() {
        return idDetailInsuranceItems;
    }

    public void setIdDetailInsuranceItems(Integer idDetailInsuranceItems) {
        this.idDetailInsuranceItems = idDetailInsuranceItems;
    }

    public Integer getDetailInsuranceId() {
        return detailInsuranceId;
    }

    public void setDetailInsuranceId(Integer detailInsuranceId) {
        this.detailInsuranceId = detailInsuranceId;
    }

    public Integer getDetailCptCodeId() {
        return detailCptCodeId;
    }

    public void setDetailCptCodeId(Integer detailCptCodeId) {
        this.detailCptCodeId = detailCptCodeId;
    }

    public Integer getSubmissionStatusId() {
        return submissionStatusId;
    }

    public void setSubmissionStatusId(Integer submissionStatusId) {
        this.submissionStatusId = submissionStatusId;
    }

    public Integer getTimesBilled() {
        return timesBilled;
    }

    public void setTimesBilled(Integer timesBilled) {
        this.timesBilled = timesBilled;
    }
}
