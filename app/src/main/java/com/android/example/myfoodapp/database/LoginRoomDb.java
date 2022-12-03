package com.android.example.myfoodapp.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {LoginData.class}, version = 2, exportSchema = false)
public abstract class LoginRoomDb extends RoomDatabase {

    private static LoginRoomDb database;
    private static String DB_NAME = "login_db";

    public synchronized static LoginRoomDb getDatabase(Context context) {
        if(database == null) {
            database = Room.databaseBuilder(context.getApplicationContext(), LoginRoomDb.class, DB_NAME)
                       .allowMainThreadQueries()
                       .fallbackToDestructiveMigration().build();
        }

        return database;
    }

    public abstract LoginDao loginDao();
}
