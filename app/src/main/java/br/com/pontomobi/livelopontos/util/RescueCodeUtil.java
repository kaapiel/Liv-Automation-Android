package br.com.pontomobi.livelopontos.util;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import java.util.Calendar;
import java.util.TimeZone;

import br.com.pontomobi.livelopontos.ui.rescuecode.CriptoHelper;
import br.com.pontomobi.livelopontos.ui.rescuecode.TokenGenerator;

/**
 * Created by selem.gomes on 22/01/16.
 */
public class RescueCodeUtil {

    public static final String RESET_VALUE = "000000";

    public static String generateToken(Context context, String cpf) {
        //String seed = TokenGenerator.generateNewSeed(256);
        //final String[] seeds = new String[]{
        //        "87fd3263791883d3d037ca595bde7ba6fc56fd722c2e51ca0968840ca8fecaff",
        //};

        CriptoHelper criptoHelper = new CriptoHelper();
        String deviceInfo = DeviceManager.with(context).loadDeviceInfo(cpf);
        String token;

        try {
            String[] userInfo = deviceInfo.split(":");
            String code = userInfo[2];

            String seed = criptoHelper.getSeed(context, cpf, code);

            long seconds = Calendar.getInstance(TimeZone.getTimeZone("GMT")).getTimeInMillis() / 1000;
            long eventCount = seconds / 30;
            token = TokenGenerator.generateTOTP256(seed, eventCount, 6);

        } catch (Exception e) {
            token = RESET_VALUE;
            Log.e("RescueCodeUtil", e.getMessage());
            e.printStackTrace();
        }

        return (token);
    }
}
