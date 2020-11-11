package com.free.image.to.text.customcamera;

import android.content.Context;
import android.content.res.Configuration;
import android.hardware.Camera;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.util.List;

public class ShowCamera extends SurfaceView implements SurfaceHolder.Callback {

    Camera camera;
    SurfaceHolder surfaceHolder;
    public ShowCamera(Context context, Camera camera) {
        super(context);
        this.camera = camera;
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
    }
    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {

        Camera.Parameters parameters = camera.getParameters();
        List<Camera.Size> camerasize = parameters.getSupportedPictureSizes();
        Camera.Size msize = null;
        for (Camera.Size size : camerasize)
        {

            msize = size;

        }

        if (this.getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE) {
            parameters.set("orientation", "Portrait");
            camera.setDisplayOrientation(90);
            parameters.setRotation(90);
        }
        else
        {
            parameters.set("orientation","Landscape");
            camera.setDisplayOrientation(0);
            parameters.setRotation(0);
        }
        parameters.setPictureSize(msize.width,msize.height);
        camera.setParameters(parameters);

        try {
            camera.setPreviewDisplay(surfaceHolder);
        } catch (IOException e) {
            e.printStackTrace();
        }
        camera.startPreview();
    }
    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }
    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        camera.stopPreview();
        camera.release();
    }
}
