package com.android.example.myfoodapp.models;

public class OrderHistory {
    private String date;
    private String time;
    private float price;
    private int i;

    public OrderHistory(String date, String time, float price, int i) {
        this.date = date;
        this.time = time;
        this.price = price;
        this.i = i;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public float getPrice() {
        return price;
    }

    public int getI() {
        return i;
    }
}
