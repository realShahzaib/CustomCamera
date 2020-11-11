package com.free.image.to.text.customcamera;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {


    Camera camera;
    FrameLayout frameLayout;
    Button camera_btn, gallery_tbn;
    ShowCamera showCamera;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        views_init();
        camera = Camera.open();
        showCamera = new ShowCamera(this, camera);
        frameLayout.addView(showCamera);
        camera_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (camera != null) {
                    camera.takePicture(null, null, mpicturecallback);
                }
            }
        });


    }
    Camera.PictureCallback mpicturecallback = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            File picture_file = getoutputmediafile();
            if (picture_file == null)
            {
                return;
            }
            else
            {
                try {
                    FileOutputStream  FOS = new FileOutputStream(picture_file);
                    try {
                        FOS.write(data);
                        FOS.close();
                        camera.startPreview();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }


        }
    };
    private File getoutputmediafile() {
        String state = Environment.getExternalStorageState();
        if (!state.equals(Environment.MEDIA_MOUNTED)) {
            return null;
        } else {
            File folder = new File(Environment.getExternalStorageDirectory() + File.separator + "GUI");
            if (!folder.exists()) {
                folder.mkdirs();
            }
            File output_file = new File(folder, "temp.jpg");
            return output_file;
        }
    }
    public void views_init() {
        frameLayout = (FrameLayout) findViewById(R.id.framelayout);
        camera_btn = (Button) findViewById(R.id.button_camera);
        gallery_tbn = (Button) findViewById(R.id.button_gallery);
    }
}