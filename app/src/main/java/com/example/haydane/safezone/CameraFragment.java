package com.example.haydane.safezone;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
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

import static android.support.constraint.Constraints.TAG;

public class CameraFragment extends Fragment implements CameraKitView.GestureListener {
    protected CameraKitView cameraKitView;
    static final int REQUEST_IMAGE_CAPTURE = 1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scan, container, false);
        cameraKitView = view.findViewById(R.id.camera);
        cameraKitView.setGestureListener(this);
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
//        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
//            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
//        }

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

}
