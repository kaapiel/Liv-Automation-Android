package br.com.aguido.livautomation.service.livautomation.login.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by selem.gomes on 29/10/15.
 */
public class LoginRequest {
    public static final String GRANT_TYPE_REFRESH_TOKEN = "refresh_token";
    public static final String GRANT_TYPE_LOGIN = "password";
    public static final String SCOPE_LOGIN = "MobileServices.atg UserProfile.me";

    @SerializedName("grant_type")
    private String grantType;

    @SerializedName("username")
    private String username;

    @SerializedName("password")
    private String password;

    @SerializedName("scope")
    private String scope;

    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
        this.grantType = GRANT_TYPE_LOGIN;
        this.scope = SCOPE_LOGIN;
    }

    public String getGrantType() {
        return grantType;
    }

    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}
