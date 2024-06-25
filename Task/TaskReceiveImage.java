package com.example.photoboard.Task;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class TaskReceiveImage extends AsyncTask<String, Void, Bitmap> {

    String imageTitle;
    Bitmap bitmap;

    public TaskReceiveImage(String imageTitle) {
        this.imageTitle = imageTitle;
    }
    @Override
    protected Bitmap doInBackground(String... params) {
        String url_base = "http://10.0.2.2:8080/"; // server address
        URL url = null;
        try {
            url = new URL(url_base + imageTitle);   // set address with image name

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            conn.connect(); // connect with server
            InputStream is = conn.getInputStream(); // receive data
            bitmap = BitmapFactory.decodeStream(is);    // convert to bitmap


        } catch (Exception e) {
            e.printStackTrace();
        }

        return bitmap;
    }
}
