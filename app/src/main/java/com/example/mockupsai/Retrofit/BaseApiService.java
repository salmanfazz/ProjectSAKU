package com.example.mockupsai.Retrofit;

import androidx.media.AudioAttributesCompat;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface BaseApiService {
    String URL = "http://laravel.simkug.com/siswa-api/public/api/";
    @FormUrlEncoded
    @POST("login")
    Call<ResponseBody> loginRequest(@Field("email") String email,
                                    @Field("password") String password);

    @GET("siswa")
    Call<ResponseBody> dataSiswa(@Header("Authorization") String token,
                                 @Header("Accept") String header);
}