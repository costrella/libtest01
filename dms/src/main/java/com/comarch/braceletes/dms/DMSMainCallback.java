package com.comarch.braceletes.dms;

public interface DMSMainCallback {
    void onNotDefaultSmsApp();
    void onSuccess(DMSConfigDto configDto);
    void onFailure(int code);
}
