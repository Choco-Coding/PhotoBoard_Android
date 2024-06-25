package com.example.photoboard;


public class Item implements Comparable<Item>{         // Item class
    private int id;
    private String title;
    private String contents;
    private String imageTitle;       // image title


    public void setId(int id) { this.id = id; }
    public int getId() { return id; }
    public void setTitle(String title) { this.title = title; }
    public String getTitle() { return title; }
    public void setContents(String contents) { this.contents = contents; }
    public String getContents() { return contents; }
    public void setImageTitle(String imageTitle) { this.imageTitle = imageTitle; }
    public String getImageTitle() { return imageTitle; }
    @Override
    public int compareTo(Item other) {
        return (int)(this.id - other.id);
    }

}

