package com.example.mockupsai.Schedule;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mockupsai.Home.HomeRecyclerViewHorizontalAdapter;
import com.example.mockupsai.Home.HomeRecyclerViewScheduleAdapter;
import com.example.mockupsai.Home.Homes;
import com.example.mockupsai.Loading;
import com.example.mockupsai.Login.MainActivity;
import com.example.mockupsai.R;
import com.example.mockupsai.Retrofit.BaseApiService;
import com.example.mockupsai.Retrofit.UtilsApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Senin extends Fragment implements View.OnClickListener {
    RecyclerView recyclerView;
    BaseApiService mApiService;
    HomeRecyclerViewScheduleAdapter homeRecyclerViewScheduleAdapter;
    LinearLayoutManager linearLayoutManager;
    Call<ResponseBody> call;
    private String token = null;

    public Senin() {};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle saveInstanceState) {
        LayoutInflater If = getActivity().getLayoutInflater();
        View view = If.inflate(R.layout.activity_senin, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = (RecyclerView) getView().findViewById(R.id.schedule);
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
        String type = "application/x-wwww-form-urlencoded";
        call = mApiService.dataSiswa(token, type);
        final Fragment loading = new Loading();
        getFragmentManager().beginTransaction().add(R.id.fragment_container, loading).commit();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    getFragmentManager().beginTransaction().remove(loading).commit();
                    JSONObject jsonRESULTS = new JSONObject(response.body().string());
                    final String kelas = jsonRESULTS.getJSONArray("detail").getJSONObject(0).getString("kode_kelas");
                    call = mApiService.getJadwal(token);
                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            try {
                                String setKelas = "";
                                JSONObject jsonRESULTS = new JSONObject(response.body().string());
                                ArrayList<Homes> homeArrayList = new ArrayList<>();
                                if (kelas.equals("XI-13RPL")) {
                                    setKelas = "XI-RPL";
                                } else if (kelas.equals("XI-9TKJ")) {
                                    setKelas = "XI-TKJ";
                                }

                                JSONArray dataArray = jsonRESULTS.getJSONArray("" +setKelas);

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

                                    //SET Jam
                                    homes.setTime(data.getString("waktu"));
                                    homes.setGuru(data.getString("nama"));

                                    homeArrayList.add(homes);

                                    homeRecyclerViewScheduleAdapter = new HomeRecyclerViewScheduleAdapter(homeArrayList);
                                    recyclerView.setAdapter(homeRecyclerViewScheduleAdapter);
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

    }
}
