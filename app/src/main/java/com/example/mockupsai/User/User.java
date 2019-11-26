package com.example.mockupsai.User;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.example.mockupsai.Guru.ContactGuru;
import com.example.mockupsai.Loading;
import com.example.mockupsai.Login.MainActivity;
import com.example.mockupsai.R;
import com.example.mockupsai.Retrofit.BaseApiService;
import com.example.mockupsai.Retrofit.UtilsApi;
import com.makeramen.roundedimageview.RoundedImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class User extends Fragment implements View.OnClickListener {
    BaseApiService mApiService;
    Call<ResponseBody> call;
    private String token = null;
    public static String id, email, nama, nis, image, alamat, kelas, telp = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle saveInstanceState) {
        LayoutInflater If = getActivity().getLayoutInflater();
        View view = If.inflate(R.layout.activity_user, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        TextView edit = (TextView) getView().findViewById(R.id.edit);
        edit.setOnClickListener(this);

        Button logout = (Button) getView().findViewById(R.id.btnlogout);
        logout.setOnClickListener(this);

        LinearLayout contact = (LinearLayout) getView().findViewById(R.id.contact);
        contact.setOnClickListener(this);

        mApiService = UtilsApi.getAPIService();
        requestData();
    }

    private void requestData() {
        this.token = MainActivity.token;
        String type = "application/x-wwww-form-urlencoded";
        call = mApiService.dataSiswa(token, type);
        final Fragment loading = new Loading();
        getFragmentManager().beginTransaction().add(R.id.fragment_container, loading).commit();
        call.enqueue(new Callback<ResponseBody>() {
            String userID, username, userEmail, userNIS, userURL, userAlamat, userKelas, userTelp;
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    getFragmentManager().beginTransaction().remove(loading).commit();
                    final JSONObject jsonRESULTS = new JSONObject(response.body().string());
                    TextView name = (TextView) getView().findViewById(R.id.nama);
                    TextView kelas = (TextView) getView().findViewById(R.id.kelas);
                    RoundedImageView image = (RoundedImageView) getView().findViewById(R.id.imgProfile);

                    //Get Name
                    String setNama = jsonRESULTS.getJSONArray("detail").getJSONObject(0).getString("nama");
                    name.setText(setNama);

                    //Get Kelas
                    String setKelas = jsonRESULTS.getJSONArray("detail").getJSONObject(0).getString("kode_kelas");
                    kelas.setText(setKelas);

                    //Get ID
                    userID = jsonRESULTS.getJSONObject("success").getString("id");
                    User.id = this.userID;

                    //Get Email
                    userEmail = jsonRESULTS.getJSONObject("success").getString("email");
                    User.email = this.userEmail;

                    //Get Nama
                    username = jsonRESULTS.getJSONArray("detail").getJSONObject(0).getString("nama");
                    User.nama = this.username;

                    //Get Kelas
                    userKelas = jsonRESULTS.getJSONArray("detail").getJSONObject(0).getString("kode_kelas");
                    User.kelas = this.userKelas;

                    userTelp = jsonRESULTS.getJSONArray("detail").getJSONObject(0).getString("tlp_siswa");
                    User.telp = this.userTelp;

                    //Get Alamat
                    userAlamat = jsonRESULTS.getJSONArray("detail").getJSONObject(0).getString("alamat_siswa");
                    User.alamat = this.userAlamat;

                    //Get Nis
                    userNIS = jsonRESULTS.getJSONObject("success").getString("nis");
                    User.nis = this.userNIS;

                    //Get Photo
                    userURL = jsonRESULTS.getJSONObject("success").getString("url_photo");
                    User.image = this.userURL;

                    //Get Image
                    final String setImage = jsonRESULTS.getJSONObject("success").getString("url_photo");
                    GlideUrl glideUrl = new GlideUrl(setImage,
                            new LazyHeaders.Builder()
                                    .addHeader("Authorization", token)
                                    .build());

                    Glide.with(getContext())
                            .load(glideUrl)
                            .into(image);
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        call.cancel();
    }

    @Override
    public void onClick(View view) {
        FragmentTransaction fragmentTransaction =
                getFragmentManager().beginTransaction();

        switch (view.getId()) {
            case R.id.btnlogout:
                Intent logout = new Intent(getActivity().getApplication(), MainActivity.class);
                startActivity(logout);
                break;
            case R.id.edit:
                EditUser editUser = new EditUser();
                fragmentTransaction.replace(R.id.fragment_container, editUser);
                fragmentTransaction.commit();
                break;
            case R.id.contact:
                fragmentTransaction.replace(R.id.fragment_container, new ContactGuru());
                fragmentTransaction.commit();
                break;
        }
    }


}
