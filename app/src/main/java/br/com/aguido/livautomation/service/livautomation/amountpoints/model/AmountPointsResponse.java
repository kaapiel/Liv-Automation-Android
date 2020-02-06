package br.com.aguido.livautomation.service.livautomation.amountpoints.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by selem.gomes on 06/11/15.
 */
public class AmountPointsResponse {

    @SerializedName("output")
    @Expose
    private OutputAmountPointsResponse output;

    public OutputAmountPointsResponse getOutput() {
        return output;
    }

    public void setOutput(OutputAmountPointsResponse output) {
        this.output = output;
    }
}
