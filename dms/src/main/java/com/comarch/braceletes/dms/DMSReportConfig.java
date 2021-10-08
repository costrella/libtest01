package com.comarch.braceletes.dms;


import lombok.AllArgsConstructor;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


@AllArgsConstructor
public class DMSReportConfig implements Callback<ResponseBody> {
    private final DMSConfigDto configDto;
    private final DMSReportConfigCallback callback;

    @Override
    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        if (response.isSuccessful()) {
            callback.onSuccessReportConfig(configDto);
        } else {
            callback.onFailureReportConfig(response.code());
        }
    }

    @Override
    public void onFailure(Call<ResponseBody> call, Throwable t) {
        callback.onFailureReportConfig(-1);
    }
}
