package com.example.tracker;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

// A anotação Index garante que o user_name seja único, conforme o diagrama
@Entity(tableName = "users", indices = {@Index(value = "user_name", unique = true)})
public class User {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String user_name;
    public String password;
    public String email;
    public String photo;
}