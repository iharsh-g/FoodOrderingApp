package com.android.example.myfoodapp.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "Favourites")
public class FavouritesData implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "email")
    private String email;

    @ColumnInfo(name = "foodId")
    private String foodId;

    @ColumnInfo(name = "foodName")
    private String name;

    @ColumnInfo(name = "foodDescription")
    private String des;

    @ColumnInfo(name = "foodImage")
    private String image;

    @ColumnInfo(name = "price")
    private float price;

    @ColumnInfo(name = "rating")
    private float rating;

    public String getEmail() {
        return email;
    }

    public int getId() {
        return id;
    }

    public String getFoodId() {
        return foodId;
    }

    public String getName() {
        return name;
    }

    public String getDes() {
        return des;
    }

    public String getImage() {
        return image;
    }

    public float getPrice() {
        return price;
    }

    public float getRating() {
        return rating;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFoodId(String foodId) {
        this.foodId = foodId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}
