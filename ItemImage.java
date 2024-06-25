package com.example.photoboard;

import android.graphics.Bitmap;

public class ItemImage extends Item{


    //with bitmap
    private Bitmap image;

    public void setImage(Bitmap image) { this.image = image; }
    public Bitmap getImage() { return image; }

}
