
package br.com.aguido.livautomation.service.livautomation.productCatalog.search.model;

import com.google.gson.annotations.SerializedName;

public class ResultSearchResponse {

    @SerializedName("response")
    private Response response;

    /**
     * 
     * @return
     *     The response
     */
    public Response getResponse() {
        return response;
    }

    /**
     * 
     * @param response
     *     The response
     */
    public void setResponse(Response response) {
        this.response = response;
    }

}
