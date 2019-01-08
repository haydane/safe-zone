package com.example.haydane.safezone;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import com.camerakit.CameraKitView;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class CameraFragment extends Fragment implements CameraKitView.GestureListener, View.OnClickListener {
    private static final String TAG = "Tag";
    protected CameraKitView cameraKitView;
    Context context;
    MainActivity activity = (MainActivity)context;
    Button button;
    String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6MSwiZW1haWwiOiJhZG1pbkBnbWFpbC5jb20ifQ.7fPKl93r3K93PainE-_GWpQN3qdgvFGHLhOUyDR1nwg";




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scan, container, false);
        cameraKitView = view.findViewById(R.id.camera);
        cameraKitView.setGestureListener(this);
        cameraKitView.requestPermissions(activity);
        cameraKitView.setAspectRatio(2.2f);
        button = view.findViewById(R.id.btnCapture);
        button.setOnClickListener(this);

        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        cameraKitView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        cameraKitView.onResume();
    }

    @Override
    public void onPause() {
        cameraKitView.onPause();
        super.onPause();
    }

    @Override
    public void onStop() {
        cameraKitView.onStop();
        super.onStop();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        cameraKitView.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onTap(CameraKitView cameraKitView, float v, float v1) {
        Log.d(TAG, "onTap v=: " + v + "  v1= " + v1);


    }

    @Override
    public void onLongTap(CameraKitView cameraKitView, float v, float v1) {

    }

    @Override
    public void onDoubleTap(CameraKitView cameraKitView, float v, float v1) {

    }

    @Override
    public void onPinch(CameraKitView cameraKitView, float v, float v1, float v2) {

    }

    boolean flag = true;
    int TIME = 1;
    public int counter()
    {
        if(flag == true) {
            TIME = 1;
            flag = false;
            button.setText("Confirm");
        }
        if(flag== false)
        {
            flag = true;
            TIME= 2;
            button.setText("Capture");
        }
        return TIME;

    }

    File plate, phone;


    @Override
    public void onClick(final View v) {
        cameraKitView.captureImage(new CameraKitView.ImageCallback() {
            @Override
            public void onImage(CameraKitView cameraKitView, final byte[] capturedImage) {

                Bitmap bitmap = BitmapFactory.decodeByteArray(capturedImage,0,capturedImage.length);


                File savedPhoto = new File(Environment.getExternalStorageDirectory(), "photo_" + System.currentTimeMillis() + ".jpg");
                try {
//                    counter();
                    FileOutputStream outputStream = new FileOutputStream(savedPhoto.getPath());
                    outputStream.write(capturedImage);
                    outputStream.close();
                    Toast.makeText(v.getContext(),"Capture " + counter(), Toast.LENGTH_SHORT).show();
                } catch (java.io.IOException e) {
                    e.printStackTrace();
                }

//
//                if(counter()==1)
//                {
//                    plate=savedPhoto;
////                    Toast.makeText(v.getContext(),"plate: " + plate, Toast.LENGTH_LONG ).show();
//                }
//                if(counter()==2)
//                {
//                    phone=savedPhoto;
////                    Toast.makeText(v.getContext(),"phone: " + phone , Toast.LENGTH_LONG ).show();
//                }


                    postRequestionToApi(savedPhoto,savedPhoto,token ,true);
//                    button.setText("Capture");

            }

        });


    }

    public void postRequestionToApi(File plate, File phone, String token, boolean checkin)
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.123.8:8000/")
                .build();

        ApiService api = retrofit.create(ApiService.class);
        String json = "{ plate: " + plate + ", phone: " + phone +
                ", token: " + token +
                ", checkin: " +  checkin  + "}";

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),json);

        api.postCheckin(requestBody).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try{

                    Log.d(" RetrofitExample: " , response.body().string());
                }catch (IOException e)
                {
                    Log.d(TAG, "onResponse: " , e.fillInStackTrace());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.toString());
            }
        });

    }


}
