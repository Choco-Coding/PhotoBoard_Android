package com.example.photoboard.Task;

import android.os.AsyncTask;

import org.jetbrains.annotations.NotNull;
import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class TaskSendImage extends AsyncTask<String, Void, String>  {
    private File file;

    public TaskSendImage(File file) {
        this.file = file;
    }

    @Override
    protected String doInBackground(String... strings) {

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)        //multipart build
                .addFormDataPart("file", file.getName(), RequestBody.create(MediaType.parse("image/*"), file))
                .build();

        Request request = new Request.Builder()
                .url("http://10.0.2.2:8080/uploadImage")        //url
                .post(requestBody)
                .build();
        OkHttpClient client = new OkHttpClient();

        client.newCall(request).enqueue(new Callback() {        // send image data
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

            }
        });
        return file.toString();
    }
}

