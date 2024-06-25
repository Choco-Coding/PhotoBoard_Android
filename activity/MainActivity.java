package com.example.photoboard.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.photoboard.ItemImage;
import com.example.photoboard.MyGridAdapter;
import com.example.photoboard.R;
import com.example.photoboard.Item;
import com.example.photoboard.Task.TaskReceiveImage;
import com.example.photoboard.Task.TaskReceiveItem;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    ArrayList<ItemImage> itemImages;
    int len;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        itemImages = new ArrayList<>();


        /* Grid view */
        GridView gridView = (GridView) findViewById(R.id.grid);     // get grid view
        MyGridAdapter myGridAdapter = new MyGridAdapter(this, itemImages);
        gridView.setAdapter(myGridAdapter);


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), ArticleActivity.class);
                intent.putExtra("id", itemImages.get(position).getId()); //set intent with id
                intent.putExtra("title", itemImages.get(position).getTitle()); //set intent with title
                intent.putExtra("contents", itemImages.get(position).getContents()); //set intent with contents
                intent.putExtra("imageTitle", itemImages.get(position).getImageTitle()); //set intent with imageTitle
                startActivity(intent);                          // move to item detail screen
            }
        });

        /* button */
        Button btnAdd = (Button) findViewById(R.id.btnAdd);     // upload button
        btnAdd.setOnClickListener(new View.OnClickListener() {  // button click operation
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), UploadActivity.class);
                startActivity(intent);                          // move to upload screen

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        /* get data from server */
        try {
            String json_text = new TaskReceiveItem(0).execute().get();
            JSONArray jsonArray = new JSONArray(json_text);
            len = jsonArray.length();
            for (int i = 0; i < len; i++) {      // get data and store
                ItemImage itemImage = new ItemImage();
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                itemImage.setTitle(jsonObject.getString("title"));
                itemImage.setContents(jsonObject.getString("contents"));
                itemImage.setId(jsonObject.getInt("id"));
                itemImage.setImageTitle(jsonObject.getString("imageTitle"));
                itemImages.add(itemImage);
            }

            for (int i = 0; i < len; i++) {
                Bitmap bitmap = new TaskReceiveImage(itemImages.get(i).getImageTitle()).execute().get();
                itemImages.get(i).setImage(bitmap);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        Collections.reverse(itemImages);                            // sort descending order of id

    }
}