package com.example.mockupsai.Retrofit;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface BaseApiService {
    @FormUrlEncoded
    @POST("login")
    Call<ResponseBody> loginRequest(
            @Field("nis") String nis,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("register")
    Call<ResponseBody> registerRequest(
            @Field("name") String name,
            @Field("email") String email,
            @Field("password") String password,
            @Field("confirm_password") String confirm_password
    );

    @POST("details")
    Call<ResponseBody> dataSiswa(
            @Header("Authorization") String token,
            @Header("Content-Type") String type
    );

    @GET("jadwal")
    Call<ResponseBody> getJadwal(
            @Header("Authorization") String token
    );

    @GET("siswa-tarif/{nis}")
    Call<ResponseBody> getFinance(
            @Header("Authorization") String token,
            @Path("nis") String nis
    );

    @Multipart
    @POST("file/upload")
    Call<ResponseBody> upload(
            @Header("Authorization") String token,
            @Part MultipartBody.Part image
            );

    @FormUrlEncoded
    @POST("details/{id}")
    Call<ResponseBody> setImage(
            @Path("id") String id,
            @Header("Authorization") String token,
            @Field("url_photo") String url
    );

    @FormUrlEncoded
    @PUT("siswa/{nis}")
    Call<ResponseBody> setProfile(
            @Path("nis") String nis,
            @Header("Authorization") String token,
            @Field("nama") String name,
            @Field("email") String email,
            @Field("alamat_siswa") String alamat,
            @Field("kode_kelas") String kelas,
            @Field("tlp_siswa") String telp
    );
}