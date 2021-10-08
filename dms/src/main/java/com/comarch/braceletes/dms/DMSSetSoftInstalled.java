package com.comarch.braceletes.dms;

import android.util.Log;


import lombok.AllArgsConstructor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@AllArgsConstructor
public class DMSSetSoftInstalled implements Callback<Void> {
    private static final String TAG = "DMSSetSoftInstalled";
    private final DMSSetSoftInstalledCallback callback;

    @Override
    public void onResponse(Call<Void> call, Response<Void> response) {
        Log.d(TAG, "setSoftInstalled onResponse: " + response.code());
        if (!response.isSuccessful()) {
            callback.onFailedSetSoftInstalled();
        } else {
            callback.onSuccessSetSoftInstalled();
        }
    }

    @Override
    public void onFailure(Call<Void> call, Throwable t) {
        Log.d(TAG, "setSoftInstalled onFailure: " + t.getMessage());
        callback.onFailedSetSoftInstalled();
    }
}
