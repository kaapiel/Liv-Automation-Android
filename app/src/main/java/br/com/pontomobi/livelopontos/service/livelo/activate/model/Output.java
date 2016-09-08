
package br.com.pontomobi.livelopontos.service.livelo.activate.model;

import com.google.gson.annotations.SerializedName;

public class Output {

    @SerializedName("result")
    private UserData result;

    @SerializedName("messages")
    private String messages;

    @SerializedName("success")
    private boolean success;

    /**
     * 
     * @return
     *     The result
     */
    public UserData getUserData() {
        return result;
    }

    /**
     * 
     * @param result
     *     The result
     */
    public void setUserData(UserData result) {
        this.result = result;
    }

    /**
     * 
     * @return
     *     The messages
     */
    public String getMessages() {
        return messages;
    }

    /**
     * 
     * @param messages
     *     The messages
     */
    public void setMessages(String messages) {
        this.messages = messages;
    }

    /**
     * 
     * @return
     *     The success
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * 
     * @param success
     *     The success
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

}
