package com.example.prova.model;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.prova.model.entity.Notes;
import com.example.prova.model.entity.User;

import java.util.List;
import java.util.Map;

@Dao
public interface NotesDAO {
    @Insert
    void insertNewNote(Notes note);

    @Query("DELETE FROM notes")
    void clearAll();

    @Query("DELETE FROM notes Where notes.id == :id")
    void deleteNote(int id);

    @Query("SELECT * FROM notes Where id == :id")
    Notes getNoteById(int id);

    @Update
    void updateNote(Notes note);

    @Query("SELECT * FROM user JOIN notes on user.id == notes.userId WHERE notes.userId == :userId")
    Map<User, List<Notes>> getNotesByUser(int userId);

    @Query("SELECT * FROM notes")
    List<Notes> getAllNotes();


}
