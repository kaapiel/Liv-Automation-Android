package br.com.aguido.livautomation.service.livautomation.brandingbank.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by selem.gomes on 02/05/16.
 */
public class PartnerPartyEnrollment {
    private final String PARTNER_CODE_BRA = "BRA";
    private final String PARTNER_CODE_AMX = "AMX";
    private final String PARTNER_CODE_BDB = "BDB";

    public static final int PARTNER_BANCO_BRADESCO = 1;
    public static final int PARTNER_BANCO_BRASIL = 2;


    @SerializedName("PartnerCode")
    @Expose
    private String partnerCode;
    @SerializedName("AuthenticationStatus")
    @Expose
    private boolean authenticationStatus;

    public String getPartnerCode() {
        return partnerCode;
    }

    public void setPartnerCode(String partnerCode) {
        this.partnerCode = partnerCode;
    }

    public boolean isAuthenticationStatus() {
        return authenticationStatus;
    }

    public void setAuthenticationStatus(boolean authenticationStatus) {
        this.authenticationStatus = authenticationStatus;
    }

    public int getBankToShow() {
        if(getPartnerCode().equalsIgnoreCase(PARTNER_CODE_BRA)
                || getPartnerCode().equalsIgnoreCase(PARTNER_CODE_AMX)) {
            return PARTNER_BANCO_BRADESCO;
        }

        if(getPartnerCode().equalsIgnoreCase(PARTNER_CODE_BDB)) {
            return PARTNER_BANCO_BRASIL;
        }

        return 0;
    }
}
