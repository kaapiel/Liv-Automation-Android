package br.com.pontomobi.livelopontos.service.livelo;

import android.content.Context;

import br.com.pontomobi.livelopontos.LiveloPontosApp;
import br.com.pontomobi.livelopontos.model.Alert;

/**
 * Created by selemafonso on 10/11/15.
 */
public class LiveloException {
    public static final int EXCEPTION_INTERNET_ERROR = 1;
    public static final int EXCEPTION_SERVICE_ERROR = 2;
    public static final int EXCEPTION_REFRESH_TOKEN_ERROR = 3;
    public static final int EXCEPTION_ADD_PRODUCT = 4;

    private int errorCode;
    private String message;

    public LiveloException(int errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Alert getAlertToShow(final Context context) {
        switch (getErrorCode()) {
            case EXCEPTION_INTERNET_ERROR:
                return LiveloPontosApp.getInstance().getNoConnection();

            case EXCEPTION_SERVICE_ERROR:
                return LiveloPontosApp.getInstance().getServiceError();

            case EXCEPTION_REFRESH_TOKEN_ERROR:
                return LiveloPontosApp.getInstance().getExpiredSession();

            default:
                return LiveloPontosApp.getInstance().getNoConnection();
        }
    }
}
