package br.com.aguido.livautomation.service.livautomation.amountpoints.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by selem.gomes on 06/11/15.
 */
public class OutputAmountPointsResponse {
    @SerializedName("@class")
    @Expose
    private String classReturn;

    @SerializedName("messages")
    @Expose
    private Object messages;

    @SerializedName("result")
    @Expose
    private int result;

    @SerializedName("success")
    @Expose
    private boolean success;

    public String getClassReturn() {
        return classReturn;
    }

    public void setClassReturn(String classReturn) {
        this.classReturn = classReturn;
    }

    public Object getMessages() {
        return messages;
    }

    public void setMessages(Object messages) {
        this.messages = messages;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
