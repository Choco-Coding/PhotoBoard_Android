package com.example.photoboard.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.photoboard.Item;
import com.example.photoboard.ItemImage;
import com.example.photoboard.R;
import com.example.photoboard.Task.TaskDeleteItem;
import com.example.photoboard.Task.TaskReceiveImage;
import com.example.photoboard.Task.TaskReceiveItem;
import com.example.photoboard.Task.TaskSendItem;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Collections;

public class ArticleActivity extends Activity {

    String inTitle;
    int inId;
    String inContents;
    String inImageTitle;
    ImageView imageView;
    TextView title;
    TextView contents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.article);

        Intent inIntent = getIntent();
        inId = inIntent.getIntExtra("id", 0); // get id of item
        inTitle = inIntent.getStringExtra("title"); // get title of item
        inContents = inIntent.getStringExtra("contents"); // get contents of item
        inImageTitle = inIntent.getStringExtra("imageTitle"); // get imageTitle of item



        // mapping widget//
        imageView = (ImageView) findViewById(R.id.image);
        title = (TextView) findViewById(R.id.textTitle);
        contents = (TextView) findViewById(R.id.textContents);




        /* button */
        TextView btnBack = (TextView) findViewById(R.id.btnBack);     // move to back button
        btnBack.setOnClickListener(new View.OnClickListener() {  // button click operation
            @Override
            public void onClick(View v) {

                finish();  // move to main screen
            }
        });

        Button btnModify = (Button) findViewById(R.id.btnModify);     // modify button
        btnModify.setOnClickListener(new View.OnClickListener() {  // button click operation
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ModifyActivity.class);
                intent.putExtra("id", inId); //set intent with id
                intent.putExtra("title", inTitle); //set intent with title
                intent.putExtra("contents", inContents); //set intent with contents
                intent.putExtra("imageTitle", inImageTitle); //set intent with imageTitle
                startActivity(intent);                          // move to modify screen
            }
        });

        Button btnDelete = (Button) findViewById(R.id.btnDelete);     // delete button
        btnDelete.setOnClickListener(new View.OnClickListener() {  // button click operation
            @Override
            public void onClick(View v) {
                /* delete item from the server */
                try {
                    Item item = new Item();
                    item.setId(inId);
                    item.setTitle(inTitle);
                    item.setContents(inContents);
                    item.setImageTitle(inImageTitle);       // set item to delete
                    String result = new TaskDeleteItem(item).execute().get();   // delete from server

                } catch (Exception e) {
                    e.printStackTrace();
                }

                finish();  // move to main
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        // get information from server
        try {
            String json_text = new TaskReceiveItem(inId).execute().get();
            Bitmap bitmap = new TaskReceiveImage(inImageTitle).execute().get();
            JSONObject jsonObject = new JSONObject(json_text);


            imageView.setImageBitmap(bitmap);       // set image

            title.setText(jsonObject.getString("title"));        // set title

            contents.setText(jsonObject.getString("contents"));        // set contents

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
