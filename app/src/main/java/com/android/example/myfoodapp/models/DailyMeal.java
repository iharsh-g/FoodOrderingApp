package com.android.example.myfoodapp.models;

public class DailyMeal {
    private int mMealImage, mDiscount;
    private String mMealName;

    public DailyMeal(int mMealImage, int mDiscount, String mMealName) {
        this.mMealImage = mMealImage;
        this.mDiscount = mDiscount;
        this.mMealName = mMealName;
    }

    public int getMealImage() {
        return mMealImage;
    }

    public int getDiscount() {
        return mDiscount;
    }

    public String getMealName() {
        return mMealName;
    }
}
