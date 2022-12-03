package com.android.example.myfoodapp.database;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface FavouritesDao {

    @Insert(onConflict = REPLACE)
    void insert(FavouritesData favouritesData);

    @Query("SELECT * FROM Favourites WHERE email = :mail")
    List<FavouritesData> getAll(String mail);

    @Query("DELETE FROM Favourites WHERE email = :mail AND foodId = :id")
    void delete(String mail, String id);

    @Query("SELECT EXISTS(SELECT * FROM Favourites WHERE email = :mail AND foodId = :id)")
    boolean isExist(String mail, String id);
}
