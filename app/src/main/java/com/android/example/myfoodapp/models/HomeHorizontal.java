package com.android.example.myfoodapp.models;

public class HomeHorizontal {

    private int mImage;
    private String name;

    public HomeHorizontal(int mImage, String name) {
        this.mImage = mImage;
        this.name = name;
    }

    public int getImage() {
        return mImage;
    }

    public String getName() {
        return name;
    }
}
