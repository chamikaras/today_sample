package com.example.chamikaras.zone24_example;

import android.content.Context;
import android.graphics.Rect;
import android.hardware.Camera;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder mHolder;
    private Camera mCamera;
    String TAG = "Camera Preview";
    public CameraPreview(Context context, Camera camera) {
        super(context);
        mCamera = camera;

        // Install a SurfaceHolder.Callback so we get notified when the
        // underlying surface is created and destroyed.
        mHolder = getHolder();
        mHolder.addCallback(this);
        // deprecated setting, but required on Android versions prior to 3.0
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    public void surfaceCreated(SurfaceHolder holder) {
        // The Surface has been created, now tell the camera where to draw the preview.
        try {
            mCamera.setPreviewDisplay(holder);
            mCamera.startPreview();
        } catch (IOException e) {
            Log.d(TAG, "Error setting camera preview: " + e.getMessage());
        }
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        // empty. Take care of releasing the Camera preview in your activity.
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        // If your preview can change or rotate, take care of those events here.
        // Make sure to stop the preview before resizing or reformatting it.

        if (mHolder.getSurface() == null){
            // preview surface does not exist
            return;
        }

        // stop preview before making changes
        try {
            mCamera.stopPreview();
        } catch (Exception e){
            // ignore: tried to stop a non-existent preview
        }

        // set preview size and make any resize, rotate or
        // reformatting changes here

        // start preview with new settings
        try {
            mCamera.setPreviewDisplay(mHolder);
            mCamera.startPreview();

        } catch (Exception e){
            Log.d(TAG, "Error starting camera preview: " + e.getMessage());
        }
    }
    public void Focused_event(){
        // if (event.getAction() == MotionEvent.ACTION_DOWN) {
        //float x = event.getX();
        // float y = event.getY();
        float x = 500;
        float y = 700;
           /*
            Rect touchRect = new Rect(
                    (int) (x - 100),
                    (int) (y - 100),
                    (int) (x + 100),
                    (int) (y + 100));
*/
        Rect touchRect = new Rect(
                (int) (300),
                (int) (500),
                (int) (700),
                (int) (800));
        Log.i("MainActivity",String.valueOf(x) );
        Log.i("MainActivity",String.valueOf(y) );

        final Rect targetfocus_rectangle = new Rect(touchRect.left * 2000 / this.getWidth() - 1000, touchRect.top * 2000 / this.getHeight() - 1000, touchRect.right * 2000 / this.getWidth() - 1000, touchRect.bottom * 2000 / this.getHeight() - 1000);
        try {
            List<Camera.Area> focusList = new ArrayList<Camera.Area>();
            Camera.Area focusArea = new Camera.Area(targetfocus_rectangle, 1000);
            focusList.add(focusArea);

            Camera.Parameters param = mCamera.getParameters();
            param.setFocusAreas(focusList);
            param.setMeteringAreas(focusList);
            mCamera.setParameters(param);

            mCamera.autoFocus(myAutoFocusCallback);
        } catch (Exception e) {

        }

        //  }
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // return super.onTouchEvent(event);
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            float x = event.getX();
             float y = event.getY();


            Rect touchRect = new Rect(
                    (int) (x - 100),
                    (int) (y - 100),
                    (int) (x + 100),
                    (int) (y + 100));


            Log.i("MainActivity",String.valueOf(x) );
            Log.i("MainActivity",String.valueOf(y) );

            final Rect targetfocus_rectangle = new Rect(touchRect.left * 2000 / this.getWidth() - 1000, touchRect.top * 2000 / this.getHeight() - 1000, touchRect.right * 2000 / this.getWidth() - 1000, touchRect.bottom * 2000 / this.getHeight() - 1000);
            try {
                List<Camera.Area> focusList = new ArrayList<Camera.Area>();
                Camera.Area focusArea = new Camera.Area(targetfocus_rectangle, 1000);
                focusList.add(focusArea);

                Camera.Parameters param = mCamera.getParameters();
                param.setFocusAreas(focusList);
                param.setMeteringAreas(focusList);
                mCamera.setParameters(param);

                mCamera.autoFocus(myAutoFocusCallback);
            } catch (Exception e) {

            }

        }

        return false;
    }

    private Camera.AutoFocusCallback myAutoFocusCallback = new Camera.AutoFocusCallback() {

        @Override
        public void onAutoFocus(boolean arg0, Camera arg1) {
            if (arg0) {
                mCamera.cancelAutoFocus();
            }
        }
    };
}