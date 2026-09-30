package com.example.tracker;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

// 1. Adicionamos Music.class e Gender.class no array de entities
@Database(entities = {User.class, Music.class, Gender.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    // 2. Declaramos os métodos abstratos para os novos DAOs
    public abstract UserDao userDao();
    public abstract MusicDao musicDao();
    public abstract GenderDao genderDao();

    private static volatile AppDatabase INSTANCE;

    public static AppDatabase get(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class,
                            "db_app"
                    ).build();
                }
            }
        }
        return INSTANCE;
    }
}