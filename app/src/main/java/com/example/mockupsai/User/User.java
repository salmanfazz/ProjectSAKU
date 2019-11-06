package com.example.mockupsai.User;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mockupsai.Guru.ContactGuru;
import com.example.mockupsai.MainActivity;
import com.example.mockupsai.MenuMain;
import com.example.mockupsai.R;
import com.example.mockupsai.Retrofit.BaseApiService;
import com.example.mockupsai.Retrofit.Token;
import com.example.mockupsai.Retrofit.UtilsApi;
import com.google.android.gms.common.internal.Objects;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class User extends Fragment implements View.OnClickListener {
    BaseApiService mApiService;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle saveInstanceState) {
        LayoutInflater If = getActivity().getLayoutInflater();
        View view = If.inflate(R.layout.activity_user, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mApiService = UtilsApi.getAPIService(); // meng-init yang ada di package apihelper
        TextView edit = (TextView) getView().findViewById(R.id.edit);
        edit.setOnClickListener(this);

        Button logout = (Button) getView().findViewById(R.id.btnlogout);
        logout.setOnClickListener(this);

        LinearLayout contact = (LinearLayout) getView().findViewById(R.id.contact);
        contact.setOnClickListener(this);

        requestData();
    }

    private void requestData() {
        final String password = "Bearer " +getArguments().getString("Token");
        String header = "application/json";
        mApiService.dataSiswa(password, header)
                .enqueue(new Callback<ResponseBody>() {
                             @Override
                             public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                 if (response.isSuccessful()) {
                                     try {
                                         JSONObject jsonRESULTS = new JSONObject(response.body().string());
                                         TextView name = (TextView) getView().findViewById(R.id.nama);
                                         TextView kelas = (TextView) getView().findViewById(R.id.kelas);

                                         //Get Nama
                                         String setNama = jsonRESULTS.getJSONArray("value").getJSONObject(0).getString("nama");
                                         name.setText(setNama);

                                         //Get Kelas
                                         String setKelas = jsonRESULTS.getJSONArray("value").getJSONObject(0).getString("jurusan");
                                         kelas.setText(setKelas);

                                         Button btnCheck = (Button) getActivity().findViewById(R.id.btnCheck);
                                         btnCheck.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View v) {

                                             }
                                         });
                                     } catch (JSONException e) {
                                         e.printStackTrace();
                                     } catch (IOException e) {
                                         e.printStackTrace();
                                     }
                                 }
                             }
                             @Override
                             public void onFailure(Call<ResponseBody> call, Throwable t) {

                             }
                         });
    }

    @Override
    public void onClick(View view) {
        final String password = getArguments().getString("Token");
        FragmentTransaction fragmentTransaction =
                getFragmentManager().beginTransaction();

        switch (view.getId()) {
            case R.id.btnlogout:
                Intent logout = new Intent(getActivity().getApplication(), MainActivity.class);
                startActivity(logout);
                break;
            case R.id.edit:
                Bundle bundle = new Bundle();
                bundle.putString("Token", password);
                EditUser editUser = new EditUser();
                editUser.setArguments(bundle);
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
