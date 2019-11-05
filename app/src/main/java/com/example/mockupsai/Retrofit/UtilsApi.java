package com.example.mockupsai.Retrofit;

public class UtilsApi {
    // Base URL
    public static final String BASE_URL_API = "http://laravel.simkug.com/siswa-api/public/api/";

    // Mendeklarasikan Interface BaseApiService
    public static BaseApiService getAPIService(){
        return RetrofitClient.getClient(BASE_URL_API).create(BaseApiService.class);
    }
}
