package com.example.mockupsai.Home;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mockupsai.R;
import com.example.mockupsai.Retrofit.BaseApiService;
import com.example.mockupsai.Retrofit.UtilsApi;
import com.google.gson.JsonObject;

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

public class Home extends Fragment {
    RecyclerView recyclerView;
    HomeRecyclerViewHorizontalAdapter homeRecyclerViewHorizontalAdapter;
    BaseApiService mApiService;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle saveInstanceState) {
        LayoutInflater If = getActivity().getLayoutInflater();
        View view = If.inflate(R.layout.activity_home, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerView = (RecyclerView) getView().findViewById(R.id.listToDo);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        if (recyclerView != null) {
            recyclerView.setHasFixedSize(true);
        }

        mApiService = UtilsApi.getAPIService();
        requestData();
    }

    private void requestData() {
        final String password = "Bearer " + getArguments().getString("Token");
        String type = "application/x-wwww-form-urlencoded";
        mApiService.dataSiswa(password, type).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject jsonRESULTS = new JSONObject(response.body().string());
                    TextView name = (TextView) getView().findViewById(R.id.textWelcome);
                    String setNama = jsonRESULTS.getJSONObject("success").getString("name");
                    name.setText("Hi, " + setNama + "!");
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
        mApiService.getJadwal(password).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject jsonRESULTS = new JSONObject(response.body().string());
                    ArrayList<Homes> homeArrayList = new ArrayList<>();
                    JSONArray dataArray = jsonRESULTS.getJSONArray("XI-RPL");

                    for (int i = 0; i < dataArray.length(); i++) {
                        Homes homes = new Homes();
                        JSONObject data = dataArray.getJSONObject(i);

                        //SET KODE MAPEL -> MAPEL
                            homes.setTitle(data.getString("kode_matpel"));
                        //SET KODE HARI -> HARI
                            if (data.getString("kode_hari").equals("1")) {
                                homes.setDate("Senin");
                                homes.setColor("#3474EB");
                            } else if (data.getString("kode_hari").equals("2")) {
                                homes.setDate("Selasa");
                                homes.setColor("#00d904");
                            } else if (data.getString("kode_hari").equals("3")) {
                                homes.setDate("Rabu");
                                homes.setColor("#ffac05");
                            } else if (data.getString("kode_hari").equals("4")) {
                                homes.setDate("Kamis");
                                homes.setColor("#3474EB");
                            } else if (data.getString("kode_hari").equals("5")) {
                                homes.setDate("Jumat");
                                homes.setColor("#00d904");
                            } else if (data.getString("kode_hari").equals("6")) {
                                homes.setDate("Sabtu");
                                homes.setColor("#ffac05");
                            }

                        homeArrayList.add(homes);

                        homeRecyclerViewHorizontalAdapter = new HomeRecyclerViewHorizontalAdapter(homeArrayList);
                        recyclerView.setAdapter(homeRecyclerViewHorizontalAdapter);
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
}
