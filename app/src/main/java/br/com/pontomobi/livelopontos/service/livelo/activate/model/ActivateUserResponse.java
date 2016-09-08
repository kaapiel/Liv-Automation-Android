
package br.com.pontomobi.livelopontos.service.livelo.activate.model;

import com.google.gson.annotations.SerializedName;

public class ActivateUserResponse {

    @SerializedName("output")
    private Output output;

    private String jSessionId;

    /**
     * 
     * @return
     *     The output
     */
    public Output getOutput() {
        return output;
    }

    /**
     * 
     * @param output
     *     The output
     */
    public void setOutput(Output output) {
        this.output = output;
    }

    /**
     *
     * @return
     *     The jSessionId
     */
    public String getjSessionId() {
        return jSessionId;
    }

    /**
     *
     * @param jSessionId
     *     The jSessionId
     */
    public void setjSessionId(String jSessionId) {
        this.jSessionId = jSessionId;
    }
}
