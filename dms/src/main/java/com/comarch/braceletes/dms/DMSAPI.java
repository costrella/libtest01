package com.comarch.braceletes.dms;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface DMSAPI {

    @GET("/v1/soft/{versionNumber}")
    Call<ResponseBody> getSoft(@Path("versionNumber") String versionNumber, @Header("Access-Token") String deviceToken);

    @GET("/v1/device/{serialNumber}/soft/assigned")
    Call<ResponseBody> getSoftAssigned(@Path("serialNumber") String serialNumber, @Header("Access-Token") String deviceToken);

    @PUT("/v1/device/{serialNumber}/soft/installed/{versionNumber}")
    Call<Void> setSoftInstalled(@Path("serialNumber") String serialNumber, @Path("versionNumber") String versionNumber, @Header("Access-Token") String deviceToken);

    @GET("/v1/device/{serialNumber}/request-token")
    Call<ResponseBody> requestToken(@Path("serialNumber") String serialNumber, @Header("Access-Token") String deviceToken);

    @Headers("Content-Type: text/plain")
    @GET("/v1/device/{serialNumber}/config")
    Call<ResponseBody> getConfig(@Path("serialNumber") String serialNumber, @Header("Access-Token") String deviceToken);

    @Headers("Content-Type: text/plain")
    @POST("/v1/device/{serialNumber}/config/report")
    Call<ResponseBody> reportConfig(@Path("serialNumber") String serialNumber, @Body String config, @Header("Access-Token") String deviceToken);
}
