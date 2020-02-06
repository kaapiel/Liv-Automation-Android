package br.com.aguido.livautomation.service.livautomation.activatedevice.model.retrievemobile;

import com.google.gson.annotations.SerializedName;

public class RetrieveDeviceResponse {

    @SerializedName("QueryMobileDevicesResponse")
    private QueryMobileDevicesResponse QueryMobileDevicesResponse;

    /**
     * 
     * @return
     *     The QueryMobileDevicesResponse
     */
    public QueryMobileDevicesResponse getQueryMobileDevicesResponse() {
        return QueryMobileDevicesResponse;
    }

    /**
     * 
     * @param QueryMobileDevicesResponse
     *     The QueryMobileDevicesResponse
     */
    public void setQueryMobileDevicesResponse(QueryMobileDevicesResponse QueryMobileDevicesResponse) {
        this.QueryMobileDevicesResponse = QueryMobileDevicesResponse;
    }

}
