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

import static android.support.constraint.Constraints.TAG;

public class CameraFragment extends Fragment implements CameraKitView.GestureListener, View.OnClickListener {
    protected CameraKitView cameraKitView;
    Context context;
    MainActivity activity = (MainActivity)context;
    Button button;

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
    int TIME = 0;
    public int counter()
    {
        if(flag == true) {
            TIME = 1;
            flag = false;
        }
        else
        {
            flag = true;
            TIME= 2;
        }
        return TIME;

    }


    @Override
    public void onClick(final View v) {
        cameraKitView.captureImage(new CameraKitView.ImageCallback() {
            @Override
            public void onImage(CameraKitView cameraKitView, final byte[] capturedImage) {

                Bitmap bitmap = BitmapFactory.decodeByteArray(capturedImage,0,capturedImage.length);

                File savedPhoto = new File(Environment.getExternalStorageDirectory(), "photo_" + System.currentTimeMillis() + ".jpg");
                try {
                    FileOutputStream outputStream = new FileOutputStream(savedPhoto.getPath());
                    outputStream.write(capturedImage);
                    outputStream.close();
                    Toast.makeText(v.getContext(),"Capture " + counter(), Toast.LENGTH_SHORT).show();
                } catch (java.io.IOException e) {
                    e.printStackTrace();
                }
            }

        });

    }
}
