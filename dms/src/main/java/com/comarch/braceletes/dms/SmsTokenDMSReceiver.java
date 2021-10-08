package com.comarch.braceletes.dms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.util.Log;

public class SmsTokenDMSReceiver extends BroadcastReceiver {

    private static final String TAG = "SmsReceiver";
    private DMSManager dmsManager;

    @Override
    public void onReceive(Context context, Intent intent) {
        String message = "";
        for (SmsMessage smsMessage : Telephony.Sms.Intents.getMessagesFromIntent(intent)) {
            message = smsMessage.getMessageBody();
        }

        String[] messageArray = message.split(":");
        if (messageArray == null || messageArray.length == 0 || messageArray[0] == null) return;


        switch (messageArray[0]) {
            case "SETTOKEN":
                String token;
                try {
                    token = messageArray[2];
                } catch (ArrayIndexOutOfBoundsException e) {
                    Log.e(TAG, "SETTOKEN: ", e.fillInStackTrace());
                    break;
                }
                dmsManager = DMSManager.getInstance();
                dmsManager.saveDMSToken(token);
                dmsManager.start(context, dmsManager.getDmsConfigDto());
                break;
        }
    }

}
