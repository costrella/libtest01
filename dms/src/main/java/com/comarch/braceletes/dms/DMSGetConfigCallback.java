package com.comarch.braceletes.dms;

public interface DMSGetConfigCallback {

    void onFailureGetConfigInvalidToken(int code);

    void onFailureGetConfigEmptyConfiguration(int code);

    void onSuccessGetConfig(String config);

}
