package com.example.mockupsai.Retrofit;

import android.telecom.CallScreeningService;

import androidx.media.AudioAttributesCompat;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Url;

public interface BaseApiService {
    @FormUrlEncoded
    @POST("login")
    Call<ResponseBody> loginRequest(@Field("email") String email,
                                    @Field("password") String password);

    @POST("details")
    Call<ResponseBody> dataSiswa(@Header("Authorization") String token, @Header("Content-Type") String type);

    @GET("jadwal")
    Call<ResponseBody> getJadwal(@Header("Authorization") String token);
}