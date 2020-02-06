package br.com.aguido.livautomation.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by vilmar.filho on 2/19/16.
 */
public class SMSReceiver extends BroadcastReceiver {

    private final String SMS_SENDER_NUMBER = "28430";

    private SMSReceiverListener mSMSReceiverListener;

    public SMSReceiver(SMSReceiverListener listener){
        mSMSReceiverListener = listener;
    }

    public SMSReceiver(){}

    @Override
    public void onReceive(Context context, Intent intent) {
        final Bundle bundle = intent.getExtras();

        try {
            if (bundle != null) {
                final Object[] pdusObj = (Object[]) bundle.get("pdus");

                for (Object o : pdusObj) {
                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) o);
                    String phoneNumber = currentMessage.getDisplayOriginatingAddress();
                    String message = currentMessage.getDisplayMessageBody();

                    if (phoneNumber.equals(SMS_SENDER_NUMBER)) {
                        String code = getNumber(message);
                        if(mSMSReceiverListener != null) mSMSReceiverListener.successReceiver(code);
                    }
                }
            }
        } catch (Exception e) {
            Log.e("SmsReceiver", "Exception smsReceiver" + e);
        }
    }

    private static String getNumber(String message) {
        Pattern p = Pattern.compile("[0-9]+");
        Matcher m = p.matcher(message);
        while (m.find()) {
            String number = m.group();
            return number;
        }
        return "";
    }

    public interface SMSReceiverListener {
        void successReceiver(String code);
    }
}
