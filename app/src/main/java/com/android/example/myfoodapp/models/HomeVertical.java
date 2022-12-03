package com.android.example.myfoodapp.models;

public class HomeVertical {
    private String mMealId, mImage;
    private String mName, mTiming;
    private Float mRating, mPrice;

    public HomeVertical(String mMealId, String mImage, float mRating, String mName, String mTiming, float mPrice) {
        this.mMealId = mMealId;
        this.mImage = mImage;
        this.mRating = mRating;
        this.mName = mName;
        this.mTiming = mTiming;
        this.mPrice = mPrice;
    }

    public String getMealId() {
        return mMealId;
    }

    public String getImage() {
        return mImage;
    }

    public float getRating() {
        return mRating;
    }

    public String getName() {
        return mName;
    }

    public String getTiming() {
        return mTiming;
    }

    public float getPrice() {
        return mPrice;
    }
}
