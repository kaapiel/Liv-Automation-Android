package br.com.aguido.livautomation.service.livautomation.registration.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by vilmar.filho on 2/4/16.
 */
public class FormException {

    @SerializedName("localizedMessage")
    private String localizedMessage;

    @SerializedName("errorCode")
    private String errorCode;

    /**
     *
     * @return
     * The localizedMessage
     */
    public String getLocalizedMessage() {
        return localizedMessage;
    }

    /**
     *
     * @param localizedMessage
     * The localizedMessage
     */
    public void setLocalizedMessage(String localizedMessage) {
        this.localizedMessage = localizedMessage;
    }

    /**
     *
     * @return
     * The errorCode
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     *
     * @param errorCode
     * The errorCode
     */
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

}
