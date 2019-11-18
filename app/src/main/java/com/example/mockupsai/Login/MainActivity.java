package com.example.mockupsai.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mockupsai.R;
import com.example.mockupsai.Register;
import com.example.mockupsai.Retrofit.BaseApiService;
import com.example.mockupsai.Retrofit.Token;
import com.example.mockupsai.Retrofit.UtilsApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    Button btnLogin;
    EditText editName;
    EditText editPassword;
    TextView textRegister;
    Context mContext;
    BaseApiService mApiService;
    Call<ResponseBody> call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        mApiService = UtilsApi.getAPIService(); // meng-init yang ada di package apihelper
        initComponents();
    }

    private void initComponents() {
        editName = (EditText) findViewById(R.id.editName);
        editPassword = (EditText) findViewById(R.id.editPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        textRegister = (TextView) findViewById(R.id.Register);

        textRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Register.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestLogin();
            }
        });
    }

    public void requestLogin() {
        String email = editName.getText().toString();
        String password = editPassword.getText().toString();
        call = mApiService.loginRequest(email, password);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            JSONObject jsonRESULTS = new JSONObject(response.body().string());
                            String Success = jsonRESULTS.getJSONObject("success").getString("token");
                            Token token = new Token();
                            token.setToken(Success);
                            Log.d("Session ", ""+Success);
                            Intent intent = new Intent(mContext, MenuMain.class);
                            intent.putExtra("Token", Success);
                            startActivity(intent);
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
    protected void onDestroy() {
        super.onDestroy();
        call.cancel();
    }
}
