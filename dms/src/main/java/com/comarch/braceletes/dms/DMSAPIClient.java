package com.comarch.braceletes.dms;


import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class DMSAPIClient {
    private static final String TAG = "DMSAPIClient";
    public static final int REST_TIMEOUT = 3 * 60 * 1000;
    private static DMSAPI instance;

    public static DMSAPI getInstance() {
        if (instance == null) {
            instance = initClient();
        }
        return instance;
    }

    private static DMSAPI initClient() {
        //todo
        String URI = null;// = BuildConfig.DMS_URI;
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(REST_TIMEOUT, TimeUnit.MILLISECONDS)
                .readTimeout(REST_TIMEOUT, TimeUnit.MILLISECONDS)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URI)
                .client(okHttpClient)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        DMSAPI client = retrofit.create(DMSAPI.class);
        return client;
    }
}
