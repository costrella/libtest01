package com.comarch.braceletes.dms;

public interface DMSRequestTokenCallback {

    void onSuccessDMSRequestToken();

    void onFailureDMSRequestToken(int code);
}
