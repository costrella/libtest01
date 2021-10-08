package com.comarch.braceletes.dms;

import android.content.Context;
import android.provider.Telephony;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import lombok.Getter;


public class DMSManager implements DMSGetConfigCallback, DMSRequestTokenCallback, DMSReportConfigCallback, DMSSetSoftInstalledCallback {
    private static final String TAG = "DMSManager";
    private DMSAPI dmsAPI;
    private static DMSManager instance;
    private Gson gson;
    private DMSMainCallback dmsMainCallback;
    @Getter
    private DMSConfigDto dmsConfigDto;

    public static DMSManager getInstance() {
        if (instance == null) {
            instance = new DMSManager();
        }
        return instance;
    }

    public DMSManager() {
        init();
    }

    private void init() {
        Log.d(TAG, "init: ");
        dmsAPI = DMSAPIClient.getInstance();
        gson = new GsonBuilder().create();
    }

    public void setListener(DMSMainCallback dmsMainCallback){
        this.dmsMainCallback = dmsMainCallback;

    }
    public void start(Context context, DMSConfigDto dmsConfigDto) {
        this.dmsConfigDto = dmsConfigDto;
        Log.d(TAG, "start: ");
//        if (!NetworkUtils.isNetworkAvailable(context)) { todo
//            Storage.saveLog(TAG, "network unavailable");
//            return;
//        }
        Log.d(TAG, "start, dms token: " + dmsConfigDto.getDMSToken());
        if (dmsConfigDto.getDMSToken() != null) {
            dmsAPI.getConfig(dmsConfigDto.getImei(), dmsConfigDto.getDMSToken())
                    .enqueue(new DMSGetConfig(this));
        } else {
            if (isDefaultSmsApp(context)) {
                dmsAPI.requestToken(dmsConfigDto.getImei(), DMSUtils.getTokenForDMS(dmsConfigDto.getDmsDeviceToken(), dmsConfigDto.getImei()))
                        .enqueue(new DMSRequestToken(this));
            } else {
//                throw new RuntimeException("App must be set as default app for SMS");
                dmsMainCallback.onNotDefaultSmsApp();
            }
        }
    }

    public boolean isDefaultSmsApp(Context context) {
        return context.getPackageName().equals(Telephony.Sms.getDefaultSmsPackage(context));
    }

//    public void devCheckUpdateAvailable() {
//        if (preferences.getDMSConfig() == null) return;
//        dmsAPI.getSoftAssigned(preferences.getImei(), preferences.getDMSToken()).enqueue(new com.comarch.iot.wristbandplus.wristbandplus.manager.dms.DMSDevGetSoftAssigned(context, preferences));
//    }

//    public void checkUpdateAvailable(boolean checkForce) {
//        boolean tmp = preferences.getUpdateRejectedTimestamp() <= System.currentTimeMillis() - TimeUnit.HOURS.toMillis(Const.UPDATE_REJECTED_POPUP_HOUR_INTERVAL);
//        Log.d(TAG, "checkUpdateAvailable, checkForce:  " + checkForce + ", tmp: " + tmp + ", preferences.isForceUpdate: " + preferences.isForceUpdate());
//        if (checkForce || tmp) {
//            if (preferences.getDMSConfig() == null) return;
//            boolean isNight = (Calendar.getInstance().get(Calendar.HOUR_OF_DAY) < UPDATE_FW_HOUR_NIGHT_END
//                    || Calendar.getInstance().get(Calendar.HOUR_OF_DAY) > UPDATE_FW_HOUR_NIGHT_START);
//            dmsAPI.getSoftAssigned(preferences.getImei(), preferences.getDMSToken())
//                    .enqueue(new DMSGetSoftAssigned(context, preferences,
//                            preferences.isForceUpdate()
//                                    && isNight
//                    ));
//        }
//    }

    private void reportConfig(DMSConfigDto configDto, DMSReportConfigCallback callback) {
        Log.d(TAG, "reportConfig: ");
        dmsAPI.reportConfig(configDto.getImei(), gson.toJson(configDto), configDto.getDMSToken())
                .enqueue(new DMSReportConfig(configDto, callback));
    }

    public void saveDMSToken(String token) {
        dmsConfigDto.setDMSToken(token);
    }

    private void removeDMSToken() {
        dmsConfigDto.setDMSToken(null);
    }

    @Override
    public void onSuccessDMSRequestToken() {
        Log.d(TAG, "onSuccessDMSRequestToken: ");
    }

    @Override
    public void onFailureDMSRequestToken(int code) {
        Log.d(TAG, "onFailureDMSRequestToken: " + code);
        dmsMainCallback.onFailure(code);
    }


    @Override
    public void onFailureGetConfigInvalidToken(int code) {
        Log.d(TAG, "onFailureGetConfigInvalidToken: " + code);
        dmsMainCallback.onFailure(code);
        removeDMSToken();
    }

    @Override
    public void onFailureGetConfigEmptyConfiguration(int code) {
        Log.d(TAG, "onFailureGetConfigEmptyConfiguration: " + code);
        dmsMainCallback.onFailure(code);
    }

    @Override
    public void onSuccessGetConfig(String config) {
        if (TextUtils.isEmpty(config)) {
            onFailureGetConfigEmptyConfiguration(-1);
            return;
        }

//        resetMqtt(); todo ??

        JsonObject jobj = gson.fromJson(config, JsonObject.class);

//
//        DMSConfigDto dmsConfigDto = new DMSConfigDto(
//                DMSUtils.parse(jobj, "mqtt-url"),
//                DMSUtils.parse(jobj, "mqtt-user"),
//                DMSUtils.parse(jobj, "mqtt-passwd"),
//                DMSUtils.parse(jobj, "iotp-tenant"),
//                DMSUtils.parse(jobj, "iotp-project"),
//                DMSUtils.parse(jobj, "iotp-channel-id")
//        );
        dmsConfigDto.setMqttUrl(DMSUtils.parse(jobj, "mqtt-url"));
        //todo

        DMSManager _this = this;
        DMSConfValidator.valid(dmsConfigDto, new DMSTryConnectCallback() {
            @Override
            public void onSuccessTryConnect() {
                reportConfig(dmsConfigDto, _this);
            }

            @Override
            public void onFailedTryConnect() {
                //todo
                dmsMainCallback.onFailure(-1);
            }
        });

    }

    private void resetMqtt() {
//        Storage.saveLog(TAG, "set resetMqtt flag true");
//        AppLifecycle.setResetMqtt(true);
    }

    public void setSoftInstalledAsync(DMSConfigDto configDto) {
//        if (!NetworkUtils.isNetworkAvailable(context)) return;todo
        dmsAPI.setSoftInstalled(configDto.getImei(), configDto.getAppVersionName(), configDto.getDMSToken())
                .enqueue(new DMSSetSoftInstalled(this));
    }

    @Override
    public void onSuccessReportConfig(DMSConfigDto configDto) { //todo return dmsConfig ?
//        preferences.setDMSConfig(configDto);
//        preferences.setDMSServer(BuildConfig.FLAVOR_dms);
        dmsMainCallback.onSuccess(configDto);
        setSoftInstalledAsync(configDto);
    }

    @Override
    public void onFailureReportConfig(int code) {
        dmsMainCallback.onFailure(code);
    }

    @Override
    public void onSuccessSetSoftInstalled() {
//        checkUpdateAvailable(true); todo
    }

    @Override
    public void onFailedSetSoftInstalled() {
//        checkUpdateAvailable(true);
    }
}
