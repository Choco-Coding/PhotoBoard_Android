package com.example.photoboard.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.photoboard.Item;
import com.example.photoboard.R;
import com.example.photoboard.Task.TaskSendImage;
import com.example.photoboard.Task.TaskSendItem;

import java.io.File;
import java.io.IOException;

public class UploadActivity extends Activity {
    Bitmap image;   // selected image
    String imageTitle = null;
    TextView textImageTitle = null;
    File file = null;
    String path = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload);

        /* get information */
        EditText editTitle = (EditText) findViewById(R.id.inputTitle);
        EditText editContents = (EditText) findViewById(R.id.inputContents);

        textImageTitle = (TextView) findViewById(R.id.textImageTitle);

        /* button */
        TextView btnBack = (TextView) findViewById(R.id.btnBack);     // move to back button
        btnBack.setOnClickListener(new View.OnClickListener() {  // button click operation
            @Override
            public void onClick(View v) {

                finish();   // move to main screen
            }
        });

        Button btnImageSelect = (Button) findViewById(R.id.btnImageSelect);     // image select button
        btnImageSelect.setOnClickListener(new View.OnClickListener() {  // button click operation
            @Override
            public void onClick(View v) {
                /* image select */
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 100);

            }
        });

        Button btnUpload = (Button) findViewById(R.id.btnUpload);     // upload complete button
        btnUpload.setOnClickListener(new View.OnClickListener() {  // button click operation
            @Override
            public void onClick(View v) {
                /* upload to server */
                String title = editTitle.getText().toString();  // get title
                String contents = editContents.getText().toString();  // get contents

                try {
                    Item item = new Item();
                    item.setId(0);
                    item.setTitle(title);
                    item.setContents(contents);
                    item.setImageTitle(imageTitle);


                    String result = new TaskSendItem(item).execute().get();

                    result = new TaskSendImage(file).execute().get();


                } catch (Exception e) {
                    e.printStackTrace();
                }

                finish();   // move to main screen
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();// get uri of image data
            imageTitle = getFileName(uri);
            textImageTitle.setText(imageTitle);

            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            cursor.moveToNext();


            @SuppressLint("Range") String uriPath = cursor.getString(cursor.getColumnIndex("_data"));

            file = new File(uriPath);/// get file of image

            path = uri.getPath();
            try {
                image = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @SuppressLint("Range")
    public String getFileName(Uri uri) {
        String result = null;
        // find for image file name
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }
        if (result == null) {
            result = uri.getLastPathSegment();// get file name of uri and return
        }
        return result;
    }

}