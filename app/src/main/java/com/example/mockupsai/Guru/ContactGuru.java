package com.example.mockupsai.Guru;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mockupsai.Loading;
import com.example.mockupsai.Login.MainActivity;
import com.example.mockupsai.R;
import com.example.mockupsai.Retrofit.BaseApiService;
import com.example.mockupsai.Retrofit.UtilsApi;
import com.example.mockupsai.User.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactGuru extends Fragment implements View.OnClickListener {
    RecyclerView recyclerView;
    List<Guru> guruList;
    List<Guru> guruListFull;
    GuruRecyclerViewAdapter guruRecyclerViewAdapter;
    BaseApiService mApiService;
    Call<ResponseBody> call;
    LinearLayoutManager linearLayoutManager;
    private String token = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle saveInstanceState) {
        LayoutInflater If = getActivity().getLayoutInflater();
        View view = If.inflate(R.layout.activity_contact_guru, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ImageView iconBack = (ImageView) getView().findViewById(R.id.iconBack);
        iconBack.setOnClickListener(this);

        recyclerView = (RecyclerView) getView().findViewById(R.id.recyclerGuru);
        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        if (recyclerView != null) {
            recyclerView.setHasFixedSize(true);
        }

        mApiService = UtilsApi.getAPIService();
        requestData();
    }

    private void requestData() {
        this.token = MainActivity.token;
        final Fragment loading = new Loading();
        getFragmentManager().beginTransaction().add(R.id.fragment_container, loading).commit();
        call = mApiService.getGuru(token);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                getFragmentManager().beginTransaction().remove(loading).commit();
                try {
                    JSONObject jsonRESULTS = new JSONObject(response.body().string());
                    TextView nama = (TextView) getView().findViewById(R.id.nameGuru);
                    TextView telp = (TextView) getView().findViewById(R.id.telpGuru);

                    ArrayList<Guru> guruArrayList = new ArrayList<>();
                    JSONArray dataArray = jsonRESULTS.getJSONArray("value");
                    for (int i = 0; i < dataArray.length(); i++) {
                        Guru guru = new Guru();
                        JSONObject data = dataArray.getJSONObject(i);
                        guru.setNama(data.getString("nama"));
                        guru.setTelp(data.getString("no_tlp"));

                        guruArrayList.add(guru);
                        guruRecyclerViewAdapter = new GuruRecyclerViewAdapter(guruArrayList);
                        recyclerView.setAdapter(guruRecyclerViewAdapter);
                    }
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
    public void onClick(View view) {
        Fragment fragment = null;

        switch (view.getId()) {
            case R.id.iconBack:
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, new User());
                fragmentTransaction.commit();
                break;
        }
    }
}
