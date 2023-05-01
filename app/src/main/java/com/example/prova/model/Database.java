package com.example.prova.model;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.example.prova.model.entity.Notes;
import com.example.prova.model.entity.User;

@androidx.room.Database(entities={Notes.class, User.class}, version= 2)
public abstract class Database extends RoomDatabase {
    public abstract NotesDAO notesDAO();
    public abstract UserDAO userDAO();
}
