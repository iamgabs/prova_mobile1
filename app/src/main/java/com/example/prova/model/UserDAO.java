package com.example.prova.model;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.prova.model.entity.User;


import java.util.List;

@Dao
public interface UserDAO {
    @Insert
    void insertUser(User user);

    @Query("SELECT * FROM user WHERE user.cpf == :cpf")
    User getUserByCPF(String cpf);

    @Query("SELECT * FROM user")
    List<User> getAllUsers();
}
