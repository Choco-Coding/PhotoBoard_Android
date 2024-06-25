package com.example.photoboard.Task;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class TaskReceiveItem extends AsyncTask<String, Void, String> {
    private int pagenum;

    private String str, receiveMsg;
    public TaskReceiveItem(int number) {
        pagenum = number;
    }
    @Override
    protected String doInBackground(String... params) {
        String url_base = "http://10.0.2.2:8080/info/"; // server address

        URL url = null;

        try {
            url = new URL(url_base + Integer.toString((pagenum)));      //set address with id

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8"); // set property
            if (conn.getResponseCode() == conn.HTTP_OK) {
                InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                BufferedReader reader = new BufferedReader(tmp);
                StringBuffer buffer = new StringBuffer();
                while ((str = reader.readLine()) != null) {     // receive data
                    buffer.append(str);
                }
                receiveMsg = buffer.toString();
                reader.close();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return receiveMsg;
    }
}
