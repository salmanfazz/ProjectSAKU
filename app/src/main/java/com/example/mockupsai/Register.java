package com.example.mockupsai;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mockupsai.Login.MainActivity;
import com.example.mockupsai.Login.MenuMain;
import com.example.mockupsai.Retrofit.BaseApiService;
import com.example.mockupsai.Retrofit.Token;
import com.example.mockupsai.Retrofit.UtilsApi;

import org.json.JSONObject;

import java.net.Inet4Address;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register extends AppCompatActivity {
    EditText editName, editPassword, editConfirmPassword, editEmail;
    TextView Login;
    Button btnRegister;
    Context mContext;
    BaseApiService mApiService;
    Call<ResponseBody> call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mContext = this;
        mApiService = UtilsApi.getAPIService();
        initComponents();
    }

    private void initComponents() {
        editName = (EditText) findViewById(R.id.editName);
        editPassword = (EditText) findViewById(R.id.editPassword);
        editEmail = (EditText) findViewById(R.id.editEmail);
        editConfirmPassword = (EditText) findViewById(R.id.editConfirmPassword);

        Login = (TextView) findViewById(R.id.Login);
        btnRegister = (Button) findViewById(R.id.btnRegister);

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Register.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestRegister();
            }
        });
    }

    public void requestRegister() {
        final String name = editName.getText().toString();
        final String password = editPassword.getText().toString();
        final String confirm_password = editConfirmPassword.getText().toString();
        final String email = editEmail.getText().toString();
        final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        call = mApiService.registerRequest(name, email, password, confirm_password);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (name.isEmpty()) {
                    editName.setError("Name is required");
                    editName.requestFocus();
                    return;
                }

                if (password.isEmpty()) {
                    editPassword.setError("Password is required");
                    editPassword.requestFocus();
                    return;
                }

                if (confirm_password.isEmpty()) {
                    editConfirmPassword.setError("Confirm Password is required");
                    editConfirmPassword.requestFocus();
                    return;
                }

                if (email.isEmpty()) {
                    editEmail.setError("Email is required");
                    editEmail.requestFocus();
                    return;
                }

                if (email.matches(emailPattern)) {

                } else {
                    editEmail.setError("Please enter valid email");
                    editEmail.requestFocus();
                    return;
                }

                if (password.equals(confirm_password) || confirm_password.equals(password)) {

                } else {
                    editPassword.setError("Password doesn't match");
                    editPassword.requestFocus();
                    editConfirmPassword.setError("Password doesn't match");
                    editConfirmPassword.requestFocus();
                }
                try {
                    if (response.code() == 200) {
                        JSONObject jsonRESULTS = new JSONObject(response.body().string());
                        Toast.makeText(mContext, "Register Success", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Register.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(mContext, "Email already exist", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}
