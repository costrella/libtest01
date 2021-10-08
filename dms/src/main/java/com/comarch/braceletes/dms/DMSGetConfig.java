package com.comarch.braceletes.dms;

import android.util.Log;

import java.io.IOException;

import lombok.AllArgsConstructor;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


@AllArgsConstructor
public class DMSGetConfig implements Callback<ResponseBody> {
    private static final String TAG = "DMSGetConfig";
    private final DMSGetConfigCallback dmsGetConfigCallback;

    @Override
    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        Log.d(TAG, "onResponse: " + response.code());
        switch (response.code()) {
            case 401:
            case 403:
                dmsGetConfigCallback.onFailureGetConfigInvalidToken(response.code());
                break;
            case 204:
            case 404:
                dmsGetConfigCallback.onFailureGetConfigEmptyConfiguration(response.code());
                break;
            case 200:
                ResponseBody body = response.body();
                if (body == null) {
                    dmsGetConfigCallback.onFailureGetConfigEmptyConfiguration(response.code());
                    break;
                }
                try {
                    String value = body.string();
                    dmsGetConfigCallback.onSuccessGetConfig(value);
                } catch (IOException e) {
                    e.printStackTrace();
                    dmsGetConfigCallback.onFailureGetConfigEmptyConfiguration(response.code());
                }
                break;
            default:
                //todo
        }
    }

    @Override
    public void onFailure(Call<ResponseBody> call, Throwable t) {
        //todo
    }


}
