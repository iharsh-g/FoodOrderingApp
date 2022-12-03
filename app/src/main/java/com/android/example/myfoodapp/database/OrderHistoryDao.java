package com.android.example.myfoodapp.database;


import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface OrderHistoryDao {

    @Insert(onConflict = REPLACE)
    void insert(OrderHistoryData orderHistoryData);

    @Query("SELECT * FROM OrderHistory")
    List<OrderHistoryData> getAll();

    @Query("SELECT SUM(price * quantity) FROM OrderHistory WHERE email = :mail AND system_id = :id")
    float getTotalAmount(int id, String mail);

    @Query("SELECT * FROM OrderHistory WHERE email = :mail AND system_id = :id")
    List<OrderHistoryData> getDetails(int id, String mail);

    @Query("SELECT address FROM OrderHistory WHERE email = :mail")
    String getAddress(String mail);
}
