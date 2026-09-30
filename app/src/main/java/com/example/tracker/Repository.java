package com.example.tracker;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.tracker.dados_dao.GenderDao;
import com.example.tracker.dados_dao.MusicDao;
import com.example.tracker.dados_dao.UserDao;
import com.example.tracker.model.Gender;
import com.example.tracker.model.Music;
import com.example.tracker.model.User;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository {

    private final UserDao userDao;
    private final GenderDao genderDao;
    private final MusicDao musicDao;
    private final ExecutorService executor;

    public Repository(Application application) {
        AppDatabase db = AppDatabase.get(application);

        this.userDao = db.userDao();
        this.genderDao = db.genderDao();
        this.musicDao = db.musicDao();

        this.executor = Executors.newSingleThreadExecutor();
    }

    // --- SESSÃO E USUÁRIO ---

    public LiveData<User> getUsuarioAtivo() {
        return userDao.getUsuarioAtivo();
    }

    public void logout(User user) {
        executor.execute(() -> {
            if (user != null) {
                userDao.atualizar(user);
            }
        });
    }

    // --- GÊNEROS ---

    public LiveData<List<Gender>> getAllGenders() {
        return genderDao.getAll();
    }

    public LiveData<GenderWithMusics> getGenderWithMusics(int genderId) {
        return genderDao.getGenderWithMusics(genderId);
    }

    // --- MÚSICAS ---

    public LiveData<List<Music>> getAllMusics() {
        return musicDao.getAll();
    }
}