package com.davinci.edu.detector_robo.cameraController;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

public class PhotoHandler implements PictureCallback {

    private Context context;

    public PhotoHandler(Context context) {
        this.context = context;
    }

    @Override
    public void onPictureTaken(byte[] data, Camera camera) {

        if (data != null) {
            File pictureFileDir = getDir();

            if (!pictureFileDir.exists() && !pictureFileDir.mkdirs()) {

                Log.d("Camara", "Can't create directory to save image.");
                return;

            }

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyymmddhhmmss");
            String date = dateFormat.format(new Date());
            String photoFile = "Picture_" + date + ".jpg";

            String filename = pictureFileDir.getPath() + File.separator + photoFile;

            File pictureFile = new File(filename);

            try {
                FileOutputStream fos = new FileOutputStream(pictureFile);
                fos.write(data);
                fos.close();


                Intent i = new Intent(Intent.ACTION_SEND);
                i.putExtra(Intent.EXTRA_EMAIL, new String[]{"emanueldamian.paz@gmail.com"});
                i.putExtra(Intent.EXTRA_SUBJECT, "Aplicación de prueba");
                i.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(pictureFile));

                i.setType("image/png");
                context.startActivity(Intent.createChooser(i, "Share you on the jobing"));


                Log.d("Camara", "New Image saved:" + photoFile);
            } catch (Exception error) {
                Log.d("Camara", "File" + filename + "not saved: " + error.getMessage());
            }
        }
    }

    private File getDir() {
        File sdDir = Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        return new File(sdDir, "CameraAPIDemo");
    }
}