package com.example.photoboard.Task;

import android.os.AsyncTask;

import com.example.photoboard.Item;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class TaskModifyItem  extends AsyncTask<String, Void, String> {
    private Item item;

    public TaskModifyItem(Item item) {
        this.item = item;
    }

    @Override
    protected String doInBackground(String... strings) {
        StringBuffer buf = new StringBuffer();
        String url_base = "http://10.0.2.2:8080/modify/";   // server address

        URL url = null;
        try {
            url = new URL(url_base + Integer.toString(item.getId()));
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");// post method
            conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            conn.setDoOutput(true);
            String json = "{\"id\": \"" + item.getId() + "\", \"title\": \"" + item.getTitle() + "\", \"contents\": \"" + item.getContents() + "\", \"imageTitle\": \"" + item.getImageTitle() + "\"}";
            OutputStream os = conn.getOutputStream();
            byte[] input = json.getBytes("utf-8");
            os.write(input, 0, input.length);// send data
            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // receive response -> success
                BufferedReader br =
                        new BufferedReader(
                                new InputStreamReader(conn.getInputStream(), "UTF-8"));
                String line;
                while ((line = br.readLine()) != null) {
                    buf.append(line);
                }
                br.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buf.toString();
    }
}
