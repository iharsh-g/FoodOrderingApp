package com.android.example.myfoodapp.models;

public class OrderHistoryDetails {
    private String name;
    private int quantity;

    public OrderHistoryDetails(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }
}
