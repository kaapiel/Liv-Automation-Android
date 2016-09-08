package br.com.pontomobi.livelopontos.service.livelo.cart;

import com.google.gson.annotations.SerializedName;

/**
 * Created by vilmar on 5/21/16.
 */
public class LiveloResponse {

    @SerializedName("formError")
    private boolean formError;

    /**
     *
     * @return
     * The formError
     */
    public boolean isFormError() {
        return formError;
    }

    /**
     *
     * @param formError
     * The formError
     */
    public void setFormError(boolean formError) {
        this.formError = formError;
    }


}
