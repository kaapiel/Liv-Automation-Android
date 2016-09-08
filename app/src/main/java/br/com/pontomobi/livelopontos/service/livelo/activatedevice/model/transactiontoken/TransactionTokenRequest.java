package br.com.pontomobi.livelopontos.service.livelo.activatedevice.model.transactiontoken;

import com.google.gson.annotations.SerializedName;

public class TransactionTokenRequest {

    @SerializedName("Phone")
    private Phone Phone;

    @SerializedName("RequestorAgentID")
    private String RequestorAgentID;

    @SerializedName("ReasonCode")
    private String ReasonCode;


    public TransactionTokenRequest(Phone phone, String requestorAgentID, String reasonCode) {
        Phone = phone;
        RequestorAgentID = requestorAgentID;
        ReasonCode = reasonCode;
    }

    /**
     * 
     * @return
     *     The Phone
     */
    public Phone getPhone() {
        return Phone;
    }

    /**
     * 
     * @param Phone
     *     The Phone
     */
    public void setPhone(Phone Phone) {
        this.Phone = Phone;
    }

    /**
     * 
     * @return
     *     The RequestorAgentID
     */
    public String getRequestorAgentID() {
        return RequestorAgentID;
    }

    /**
     * 
     * @param RequestorAgentID
     *     The RequestorAgentID
     */
    public void setRequestorAgentID(String RequestorAgentID) {
        this.RequestorAgentID = RequestorAgentID;
    }

    /**
     * 
     * @return
     *     The ReasonCode
     */
    public String getReasonCode() {
        return ReasonCode;
    }

    /**
     * 
     * @param ReasonCode
     *     The ReasonCode
     */
    public void setReasonCode(String ReasonCode) {
        this.ReasonCode = ReasonCode;
    }

}
