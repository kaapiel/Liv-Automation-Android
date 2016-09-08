package br.com.pontomobi.livelopontos.ui.rescuecode;

/**
 * Created by vilmar.filho on 1/20/16.
 */
public class CriptoHelper {

    static {
        System.loadLibrary("Criptohelper");
    }

    public native String getSeed(Context context, String cpf, String code);
}
