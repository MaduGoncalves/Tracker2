package com.example.tracker;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "genders")
public class Gender {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String name;
}