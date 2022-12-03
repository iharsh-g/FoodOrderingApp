package com.android.example.myfoodapp.models;

public class DetailDailyMeal {
    private String mMealId, mDetailImage;
    private String mDetailMealName, mDetailMealDes;
    private float mPrice, mRating;

    public DetailDailyMeal(String mMealId, String mDetailImage, String mDetailMealName, String mDetailMealDes, float mPrice, float mRating) {
        this.mMealId = mMealId;
        this.mDetailImage = mDetailImage;
        this.mDetailMealName = mDetailMealName;
        this.mDetailMealDes = mDetailMealDes;
        this.mPrice = mPrice;
        this.mRating = mRating;
    }

    public String getMealId() { return mMealId; }

    public String getDetailImage() {
        return mDetailImage;
    }

    public String getDetailMealName() {
        return mDetailMealName;
    }

    public String getDetailMealDes() {
        return mDetailMealDes;
    }

    public float getPrice() {
        return mPrice;
    }

    public float getRating() {
        return mRating;
    }
}
