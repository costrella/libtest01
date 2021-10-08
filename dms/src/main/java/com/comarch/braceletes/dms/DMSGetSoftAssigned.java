//package com.comarch.braceletes.dms;
//
//import android.content.Context;
//import android.content.Intent;
//import android.util.Log;
//
//import java.io.IOException;
//
//import okhttp3.ResponseBody;
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//public class DMSGetSoftAssigned implements Callback<ResponseBody> {
//    private static final String TAG = "DMSGetSoftAssigned";
//    private final Context context;
//    private final PreferencesManager preferences;
//    private final boolean forceUpdate;
//
//    public DMSGetSoftAssigned(Context context, PreferencesManager preferencesManager, boolean forceUpdate) {
//        this.context = context;
//        this.preferences = preferencesManager;
//        this.forceUpdate = forceUpdate;
//    }
//
//    @Override
//    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//        Log.d(TAG, "onResponse: ");
//
//        if (response.isSuccessful()) {
//            try {
//                String softAssigned = response.body() != null ? response.body().string() : null;
//                preferences.setDMSSoftAssigned(softAssigned);
//                String currentVersion = PackageUtil.getAppVersionName(context);
//                boolean updateAvailable = softAssigned != null && (getCleanIntAppVersion(softAssigned) > getCleanIntAppVersion(currentVersion));
//
//                if (forceUpdate && updateAvailable) {
//                    Intent intent = new Intent(context, AppUpdateService.class);
//                    context.startService(intent);
//                } else {
//                    preferences.setShowUpdate(updateAvailable);
//                    if (updateAvailable) EventBus.getDefault().post(new AppUpdateAvailableEvent());
//                }
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    private long getCleanIntAppVersion(String version) {
//        return Long.parseLong(version.replace(".", ""));
//    }
//
//    @Override
//    public void onFailure(Call<ResponseBody> call, Throwable t) {
//        Log.d(TAG, "onFailure: ");
//    }
//}
