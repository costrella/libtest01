package com.comarch.braceletes.dms;

import android.util.Log;



public class DMSConfValidator {

    private static final String TAG = "DMSConfValidator";

    public static void valid(DMSConfigDto dmsConfigDto, DMSTryConnectCallback callback) {
        if (DMSUtils.valid(dmsConfigDto)) {
            Log.d(TAG, "vaild utils ok");

            callback.onSuccessTryConnect();
        } else {
            Log.d(TAG, "vaild utils FAIL");

            callback.onFailedTryConnect();
        }
    }
}
