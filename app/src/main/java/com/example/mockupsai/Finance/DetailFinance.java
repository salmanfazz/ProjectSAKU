package com.example.mockupsai.Finance;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mockupsai.Home.Home;
import com.example.mockupsai.Loading;
import com.example.mockupsai.Login.MainActivity;
import com.example.mockupsai.R;
import com.example.mockupsai.Retrofit.BaseApiService;
import com.example.mockupsai.Retrofit.UtilsApi;
import com.example.mockupsai.Schedule.Senin;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailFinance extends Fragment implements View.OnClickListener {
    BaseApiService mApiService;
    Call<ResponseBody> call;
    private String token, nis = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle saveInstanceState) {
        LayoutInflater If = getActivity().getLayoutInflater();
        View view = If.inflate(R.layout.activity_detail_finance, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ImageView back = (ImageView) getView().findViewById(R.id.back);
        back.setOnClickListener(this);

        mApiService = UtilsApi.getAPIService();
        requestData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        call.cancel();
    }

    private void requestData() {
        this.token = MainActivity.token;
        this.nis = Senin.nis;

        call = mApiService.getFinance(token, nis);
//        final Fragment loading = new Loading();
//        getFragmentManager().beginTransaction().add(R.id.fragment_container, loading).commit();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                getFragmentManager().beginTransaction().remove(loading).commit();
                try {
                    JSONObject jsonRESULTS = new JSONObject(response.body().string());
                    TextView setTitle = (TextView) getView().findViewById(R.id.tittleFinance);
                    TextView setDetail = (TextView) getView().findViewById(R.id.detailFinance);
                    TextView setStatus = (TextView) getView().findViewById(R.id.statusFinance);

                    String title = jsonRESULTS.getJSONArray("Tagihan").getJSONObject(0).getString("kode_akt");
                    setTitle.setText(title);

                    String detail = jsonRESULTS.getJSONArray("Tagihan").getJSONObject(0).getString("kode_param");
                    setDetail.setText(detail);

                    String status = jsonRESULTS.getJSONArray("Tagihan").getJSONObject(0).getString("tarif");
                    setStatus.setText(status);

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
            case R.id.back:
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, new Finance());
                fragmentTransaction.commit();
                break;
        }
    }
}
