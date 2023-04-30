package com.example.prova.model;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.prova.model.entity.Notes;
import com.example.prova.model.entity.User;

import java.util.List;

@Dao
public interface UserDAO {
    @Insert
    void insertUser(User user);

    @Query("SELECT * FROM user WHERE cpf == :cpf")
    User getUserByCPF(String cpf);

    @Query("SELECT * FROM user")
    List<User> getAllUsers();

    @Query("SELECT * FROM notes WHERE userId == :id")
    List<Notes> getAllNotes(int id);
}
