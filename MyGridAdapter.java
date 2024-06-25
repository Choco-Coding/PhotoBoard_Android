package com.example.photoboard;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

public class MyGridAdapter extends BaseAdapter {
    ArrayList<ItemImage> itemImages;  // item list
    Context context;

    public MyGridAdapter(Context c, ArrayList<ItemImage> itemImages) {
        context = c;
        this.itemImages = itemImages;
    }

    @Override
    public int getCount() {
        return itemImages.size();
    }

    @Override
    public Object getItem(int i) {
        return itemImages.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ImageView imageView = new ImageView(context);       // create image view widget object
        imageView.setLayoutParams(new ViewGroup.LayoutParams(300, 300));    // set size of image
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imageView.setImageBitmap(itemImages.get(i).getImage());       // set image resource

        return imageView;
    }

}
