package com.comarch.braceletes.dms;


public interface DMSReportConfigCallback {

    void onSuccessReportConfig(DMSConfigDto configDto);

    void onFailureReportConfig(int code);
}
