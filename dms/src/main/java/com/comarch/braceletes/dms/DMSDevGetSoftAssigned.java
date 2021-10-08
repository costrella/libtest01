//package com.comarch.braceletes.dms;
//
//import android.content.Context;
//import android.util.Log;
//
//
//import java.io.IOException;
//
//import okhttp3.ResponseBody;
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//public class DMSDevGetSoftAssigned implements Callback<ResponseBody> {
//    private static final String TAG = "DMSDevGetSoftAssigned";
//    private final Context context;
//    private final PreferencesManager preferences;
//
//    public DMSDevGetSoftAssigned(Context context, PreferencesManager preferencesManager) {
//        this.context = context;
//        this.preferences = preferencesManager;
//    }
//
//    @Override
//    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//        Log.d(TAG, "getSoftAssigned onResponse: " + response.code());
//        if (!response.isSuccessful()) {
//            preferences.setDMSSoftAssigned(null);
//        } else {
//            String softAssigned = null;
//            try {
//                softAssigned = response.body() != null ? response.body().string() : null;
//                preferences.setDMSSoftAssigned(softAssigned);
//                String currentVersion = PackageUtil.getAppVersionName(context);
//                boolean updateAvailable = softAssigned != null && !currentVersion.equals(softAssigned);
//                Log.d(TAG, "checkUpdateAvailable: Current version: " + currentVersion + ", softAssigned: " + softAssigned + ". UpdateAvailable: " + updateAvailable);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    @Override
//    public void onFailure(Call<ResponseBody> call, Throwable t) {
//        Log.d(TAG, "DMS getSoftAssigned failed: " + t.getMessage());
//        preferences.setDMSSoftAssigned(null);
//    }
//}
