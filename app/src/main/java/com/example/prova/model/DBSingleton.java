package com.example.prova.model;

import android.content.Context;

import androidx.room.Room;

public class DBSingleton {
    public static DBSingleton object;
    public Database db;

    private DBSingleton() {}

    public static DBSingleton getInstance(Context context) {
        if(object == null) {
            object = new DBSingleton();
            object.db = Room.databaseBuilder(context, Database.class,
                    "notes").allowMainThreadQueries().fallbackToDestructiveMigration().build();
        }
        return object;
    }
}
