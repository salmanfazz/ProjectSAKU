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

import com.example.mockupsai.R;
import com.example.mockupsai.Retrofit.BaseApiService;
import com.example.mockupsai.Retrofit.UtilsApi;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import okhttp3.ResponseBody;
import retrofit2.Call;

import static android.app.Activity.RESULT_OK;

public class EditUser extends Fragment implements View.OnClickListener {
    BaseApiService mApiService;
    Call<ResponseBody> call;

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
    }

    @Override
    public void onClick(View view) {
        final String password = getArguments().getString("Token");

        switch (view.getId()) {
            case R.id.iconBack:
                Bundle bundle = new Bundle();
                bundle.putString("Token", password);
                User user = new User();
                user.setArguments(bundle);
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
        ImageView imageUpload = (ImageView) getView().findViewById(R.id.imgProfile);

        if (resultCode == RESULT_OK) {
            Uri uri = data.getData();
            Bitmap bitmap;
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            try {
                bitmap = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(uri));
                imageUpload.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
