package com.example.mockupsai.User;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.example.mockupsai.Loading;
import com.example.mockupsai.Login.MainActivity;
import com.example.mockupsai.R;
import com.example.mockupsai.Retrofit.BaseApiService;
import com.example.mockupsai.Retrofit.UtilsApi;
import com.makeramen.roundedimageview.RoundedImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class EditUser extends Fragment implements View.OnClickListener {
    BaseApiService mApiService;
    Call<ResponseBody> call;
    private String token = null;
    public String url = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle saveInstanceState) {
        LayoutInflater If = getActivity().getLayoutInflater();
        View view = If.inflate(R.layout.activity_edit_user, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ImageView iconBack = (ImageView) getView().findViewById(R.id.iconBack);
        iconBack.setOnClickListener(this);

        TextView textUpload = (TextView) getView().findViewById(R.id.upload);
        textUpload.setOnClickListener(this);

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
                    final JSONObject jsonRESULTS = new JSONObject(response.body().string());
                    RoundedImageView image = (RoundedImageView) getView().findViewById(R.id.imgEditProfile);

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
    public void onClick(View view) {
        this.token = MainActivity.token;

        switch (view.getId()) {
            case R.id.iconBack:
                User user = new User();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, user);
                fragmentTransaction.commit();
                break;
            case R.id.upload:
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 0);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        TextView urlUpload = (TextView) getView().findViewById(R.id.url_upload);
        ImageView imageUpload = (ImageView) getView().findViewById(R.id.imgEditProfile);

        if (resultCode == RESULT_OK) {
            Uri uri = data.getData();
            Bitmap bitmap;
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            boolean status;
            
            int k = 20;
            String filename;
            try {
                this.token = MainActivity.token;
                bitmap = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(uri));
                imageUpload.setImageBitmap(bitmap);
                filename = getRandomWord(k);
                Log.d("File Name", filename);

                File file = new File(getContext().getCacheDir(), filename);
                status = file.createNewFile();

                bitmap.compress(Bitmap.CompressFormat.PNG, 0, byteArrayOutputStream);
                byte[] bitmapdata = byteArrayOutputStream.toByteArray();

                FileOutputStream fileOutputStream = new FileOutputStream(file);
                fileOutputStream.write(bitmapdata);
                fileOutputStream.flush();
                fileOutputStream.close();

                RequestBody requestFile =  RequestBody.create(MediaType.parse("multipart/form-data"), file);
                MultipartBody.Part body =  MultipartBody.Part.createFormData("photo", file.getName(), requestFile);

                call = mApiService.upload(token, body);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(getContext(), "Success Upload Image", Toast.LENGTH_SHORT).show();
                            try {
                                JSONObject jsonRESULTS = new JSONObject(response.body().string());
                                url = jsonRESULTS.getString("url");
                                Log.d("Image Url", url);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(getContext(), "Failed to Upload Image", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });

                Log.d("Upload Status", String.valueOf(status));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    static String getRandomWord(int k){
        String randomWord = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                                + "0123456789"
                                + "abcdefghijklmnopqrstuvxyz";

        StringBuilder stringBuilder = new StringBuilder();

        for(int i = 0; i < k; i++) {
            int index = (int)(randomWord.length() * Math.random());

            stringBuilder.append(randomWord.charAt(index));
        }
        return stringBuilder.toString();
    }
}
