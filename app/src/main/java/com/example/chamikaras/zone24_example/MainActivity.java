package com.example.chamikaras.zone24_example;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.camera2.CameraDevice;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private Camera camera;
    private CameraPreview cameraPreview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        camera = getCameraIntent();
        Camera.Parameters parms = camera.getParameters();
        camera.setDisplayOrientation(90);
        cameraPreview = new CameraPreview(this,camera);
        FrameLayout preview = (FrameLayout)findViewById(R.id.camera_prev);
        preview.addView(cameraPreview);

    }

    public static Camera getCameraIntent(){
        Camera camera = null;
        try
        {
            camera = Camera.open();
        }catch (Exception e){

        }
        return camera;
    }
}
