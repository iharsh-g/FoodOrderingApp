package com.android.example.myfoodapp.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "Cart")
public class CartData implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "foodId")
    private String foodId;

    @ColumnInfo(name = "foodName")
    private String name;

    @ColumnInfo(name = "foodImage")
    private String foodImage;

    @ColumnInfo(name = "price")
    private float price;

    @ColumnInfo(name = "quantity")
    private int quantity;

    public int getId() { return id; }

    public String  getFoodId() { return foodId; }

    public String getName() { return name; }

    public String getFoodImage() { return foodImage; }

    public float getPrice() { return price; }

    public int getQuantity() { return quantity; }

    public void setId(int id) { this.id = id; }

    public void setFoodId(String foodId) { this.foodId = foodId; }

    public void setName(String name) { this.name = name; }

    public void setFoodImage(String foodImage) { this.foodImage = foodImage; }

    public void setPrice(float price) { this.price = price; }

    public void setQuantity(int quantity) { this.quantity = quantity; }
}
