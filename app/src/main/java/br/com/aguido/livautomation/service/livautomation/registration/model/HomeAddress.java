
package br.com.aguido.livautomation.service.livautomation.registration.model;

import com.google.gson.annotations.SerializedName;

public class HomeAddress {

    @SerializedName("atg-rest-class-type")
    private String atgRestClassType;

    @SerializedName("atg-rest-values")
    private AtgRestValues atgRestValues;

    /**
     * 
     * @return
     *     The atgRestClassType
     */
    public String getAtgRestClassType() {
        return atgRestClassType;
    }

    /**
     * 
     * @param atgRestClassType
     *     The atg-rest-class-type
     */
    public void setAtgRestClassType(String atgRestClassType) {
        this.atgRestClassType = atgRestClassType;
    }

    /**
     * 
     * @return
     *     The atgRestValues
     */
    public AtgRestValues getAtgRestValues() {
        return atgRestValues;
    }

    /**
     * 
     * @param atgRestValues
     *     The atg-rest-values
     */
    public void setAtgRestValues(AtgRestValues atgRestValues) {
        this.atgRestValues = atgRestValues;
    }

}
