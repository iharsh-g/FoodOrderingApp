package com.android.example.myfoodapp.database;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CartDao {

    @Insert(onConflict = REPLACE)
    void insert(CartData cartData);

    @Query("SELECT * FROM Cart")
    List<CartData> getAll();

    @Delete
    void reset(List<CartData> cartDataList);

    @Query("SELECT EXISTS(SELECT * FROM Cart WHERE foodId = :id)")
    boolean isRowExist(String id);

    @Query("DELETE FROM Cart WHERE foodId = :id")
    void delete(String id);

    @Query("SELECT SUM(price * quantity) FROM Cart")
    int SumOfPrice();

    @Query("UPDATE Cart SET quantity = :quant WHERE foodId = :id")
    void updateQuantity(int quant, String id);

    @Query("SELECT quantity FROM CART WHERE foodId = :id")
    int showQuantity(String id);
}
