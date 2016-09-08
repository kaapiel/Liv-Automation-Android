package br.com.pontomobi.livelopontos.service.livelo;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import br.com.pontomobi.livelopontos.service.livelo.registration.model.FormException;

/**
 * Created by vilmar.filho on 2/4/16.
 */
public class LiveloResponse {

    @SerializedName("formError")
    private boolean formError;

    @SerializedName("formExceptions")
    private List<FormException> formExceptions = new ArrayList<>();

    @SerializedName("authenticationToken")
    private String authenticationToken;

    /**
     * @return The formError
     */
    public boolean isFormError() {
        return formError;
    }

    /**
     * @param formError The formError
     */
    public void setFormError(boolean formError) {
        this.formError = formError;
    }

    /**
     * @return The formExceptions
     */
    public List<FormException> getFormExceptions() {
        return formExceptions;
    }

    /**
     * @param formExceptions The formExceptions
     */
    public void setFormExceptions(List<FormException> formExceptions) {
        this.formExceptions = formExceptions;
    }

    public String getAuthenticationToken() {
        return authenticationToken;
    }

    public void setAuthenticationToken(String authenticationToken) {
        this.authenticationToken = authenticationToken;
    }
}
