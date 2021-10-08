package com.comarch.braceletes.dms;

import lombok.AllArgsConstructor;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


@AllArgsConstructor
public class DMSRequestToken implements Callback<ResponseBody> {
    private final DMSRequestTokenCallback dmsRequestTokenCallback;

    @Override
    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        if (response.isSuccessful()) {
            dmsRequestTokenCallback.onSuccessDMSRequestToken();
        } else {
            dmsRequestTokenCallback.onFailureDMSRequestToken(response.code());
        }
    }

    @Override
    public void onFailure(Call<ResponseBody> call, Throwable t) {
        dmsRequestTokenCallback.onFailureDMSRequestToken(-1);
    }
}
