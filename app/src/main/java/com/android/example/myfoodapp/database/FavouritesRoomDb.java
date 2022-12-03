package com.android.example.myfoodapp.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {FavouritesData.class}, version = 2, exportSchema = false)
public abstract class FavouritesRoomDb extends RoomDatabase {

    private static FavouritesRoomDb database;
    private static String DB_NAME = "fav_db";

    public synchronized static FavouritesRoomDb getDatabase(Context context) {
        if(database == null) {
            database = Room.databaseBuilder(context.getApplicationContext(), FavouritesRoomDb.class, DB_NAME)
                       .allowMainThreadQueries()
                       .fallbackToDestructiveMigration().build();
        }

        return database;
    }

    public abstract FavouritesDao favouritesDao();
}
