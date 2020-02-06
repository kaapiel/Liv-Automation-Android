package br.com.aguido.livautomation.ui.rescuecode;

/**
 * Created by vilmar.filho on 1/20/16.
 */
public class CriptoHelper {

    static {
        System.loadLibrary("Criptohelper");
    }

    public native String getSeed(Context context, String cpf, String code);
}
