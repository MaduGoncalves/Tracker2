package com.example.tracker;

import android.app.Application;
import androidx.lifecycle.LiveData;


import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository {

    private final UserDao userDao;
    private final GenderDao genderDao;
    private final ExecutorService executor;

    public Repository(Application application) {
        AppDatabase db = AppDatabase.get(application);
        this.userDao = db.userDao();
        this.genderDao = db.genderDao();
        this.executor = Executors.newSingleThreadExecutor();
    }

    // --- SESSÃO E USUÁRIO ---
    public LiveData<User> getUsuarioAtivo() {
        return userDao.getUsuarioAtivo();
    }

    public void logout(User user) {
        executor.execute(() -> {
            if (user != null) {
                // Atualiza o estado da sessão no Room para inativo
                // user.isLogged = false;
                userDao.atualizar(user);
            }
        });
    }

    // --- GÊNEROS E MÚSICAS COM @RELATION ---
    public LiveData<List<Gender>> getAllGenders() {
        return genderDao.getAll();
    }

    public LiveData<GenderWithMusics> getGenderWithMusics(int genderId) {
        return genderDao.getGenderWithMusics(genderId);
    }
}