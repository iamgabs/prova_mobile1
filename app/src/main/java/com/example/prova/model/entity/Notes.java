package com.example.prova.model.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Notes {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String title;
    public String note;
}
