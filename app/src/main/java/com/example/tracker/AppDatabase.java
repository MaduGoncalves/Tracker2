package com.example.tracker;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.Executors;

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
                                    "tracker_db"
                            )
                            // ADICIONA A SEEDER AQUI: Executado apenas na primeira criação do banco
                            .addCallback(new RoomDatabase.Callback() {
                                @Override
                                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                    super.onCreate(db);

                                    // Como o onCreate roda na main thread por padrão, usamos um Executor
                                    // para inserir os dados iniciais em segundo plano sem travar o app
                                    Executors.newSingleThreadExecutor().execute(() -> {
                                        AppDatabase database = INSTANCE;
                                        if (database != null) {
                                            // Popula os Gêneros (para o Spinner)
                                            GenderDao gDao = database.genderDao();

                                            Gender g1 = new Gender();
                                            g1.name = "K-pop";
                                            gDao.insert(g1);

                                            Gender g2 = new Gender();
                                            g2.name = "Gospel";
                                            gDao.insert(g2);

                                            Gender g3 = new Gender();
                                            g3.name = "Sertanejo";
                                            gDao.insert(g3);

                                            Gender g4 = new Gender();
                                            g4.name = "Pop";
                                            gDao.insert(g4);

                                            // Se quiser popular músicas iniciais vinculadas aos IDs, pode fazer aqui também!
                                        }
                                    });
                                }
                            })
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}