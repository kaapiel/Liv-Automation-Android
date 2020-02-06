package br.com.aguido.livautomation.service.livautomation.brandingbank.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by selem.gomes on 04/05/16.
 */
public class PartnerPartyEnrollmentList {

    @SerializedName("PartnerPartyEnrollment")
    @Expose
    private List<PartnerPartyEnrollment> partnerPartyEnrollmentList;

    public List<PartnerPartyEnrollment> getPartnerPartyEnrollmentList() {
        return partnerPartyEnrollmentList;
    }

    public void setPartnerPartyEnrollmentList(List<PartnerPartyEnrollment> partnerPartyEnrollmentList) {
        this.partnerPartyEnrollmentList = partnerPartyEnrollmentList;
    }

    public PartnerPartyEnrollment getPartnerPartyEnrollmentEnable() {
        for(PartnerPartyEnrollment partnerPartyEnrollment : getPartnerPartyEnrollmentList()) {
            if(partnerPartyEnrollment.isAuthenticationStatus()) {
                return partnerPartyEnrollment;
            }
        }

        return null;
    }

}
