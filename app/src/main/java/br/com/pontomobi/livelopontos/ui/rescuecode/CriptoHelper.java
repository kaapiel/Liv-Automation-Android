package br.com.pontomobi.livelopontos.ui.rescuecode;

import android.content.Context;

/**
 * Created by vilmar.filho on 1/21/16.
 */
public class CriptoHelper {

    static {
        System.loadLibrary("Criptohelper");
    }

    public native String getSeed(Context contex, String cpf, String code);
}
