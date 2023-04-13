package com.example.prova.model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.prova.model.entity.Notes;

import java.util.List;

@Dao
public interface NotesDAO {
    @Insert
    void insertNewNote(Notes note);

    @Query("DELETE FROM notes")
    void clearAll();

    @Query("DELETE FROM notes Where id == :id")
    void deleteNote(int id);


    @Query("SELECT * FROM notes")
    List<Notes> getAllNotes();
}
