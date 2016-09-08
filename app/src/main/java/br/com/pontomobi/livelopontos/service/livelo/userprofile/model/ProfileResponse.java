package br.com.pontomobi.livelopontos.service.livelo.userprofile.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by selem.gomes on 13/11/15.
 */
public class ProfileResponse {
    @SerializedName("profile")
    private UserProfileResponse userProfileResponse;

    public UserProfileResponse getUserProfileResponse() {
        return userProfileResponse;
    }

    public void setUserProfileResponse(UserProfileResponse userProfileResponse) {
        this.userProfileResponse = userProfileResponse;
    }
}
