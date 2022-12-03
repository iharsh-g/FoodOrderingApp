package com.android.example.myfoodapp.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {CartData.class}, version = 2, exportSchema = false)
public abstract class CartRoomDb extends RoomDatabase {

    private static CartRoomDb database;
    private static String DB_NAME = "foods_db";

    public synchronized static CartRoomDb getDatabase(Context context) {
        if(database == null) {
            database = Room.databaseBuilder(context.getApplicationContext(), CartRoomDb.class, DB_NAME)
                       .allowMainThreadQueries()
                       .fallbackToDestructiveMigration().build();
        }

        return database;
    }

    public abstract CartDao cartDao();
}
