package br.com.aguido.livautomation.service.livautomation.brandingbank.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by selem.gomes on 04/05/16.
 */
public class BrandingBank {
    @SerializedName("PartnerPartyEnrollmentList")
    @Expose
    private PartnerPartyEnrollmentList partnerPartyEnrollmentList;

    public PartnerPartyEnrollmentList getPartnerPartyEnrollmentList() {
        return partnerPartyEnrollmentList;
    }

    public void setPartnerPartyEnrollmentList(PartnerPartyEnrollmentList partnerPartyEnrollmentList) {
        this.partnerPartyEnrollmentList = partnerPartyEnrollmentList;
    }
}
