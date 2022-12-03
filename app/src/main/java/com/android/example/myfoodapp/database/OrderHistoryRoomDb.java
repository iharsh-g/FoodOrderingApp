package com.android.example.myfoodapp.database;

import android.content.Context;

import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {OrderHistoryData.class}, version = 2, exportSchema = false)
public abstract class OrderHistoryRoomDb extends RoomDatabase {

    private static OrderHistoryRoomDb database;
    private static String DB_NAME = "order_history_db";

    public synchronized static OrderHistoryRoomDb getDatabase(Context context) {
        if(database == null) {
            database = Room.databaseBuilder(context.getApplicationContext(), OrderHistoryRoomDb.class, DB_NAME)
                       .allowMainThreadQueries()
                       .fallbackToDestructiveMigration().build();
        }

        return database;
    }

    public abstract OrderHistoryDao orderHistoryDao();
}
