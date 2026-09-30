package com.example.tracker;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.example.tracker.model.Gender;
import com.example.tracker.model.Music;
import com.example.tracker.model.User;

import java.util.List;

public class SharedViewModel extends AndroidViewModel {

    private final Repository repository;

    // Usuário logado
    private final LiveData<User> usuarioAtivo;

    // Gêneros cadastrados no banco
    private final LiveData<List<Gender>> listaGeneros;

    // Todas as músicas cadastradas no banco
    private final LiveData<List<Music>> listaMusicas;

    // Gênero selecionado no Spinner
    private final MutableLiveData<Integer> generoSelecionadoId =
            new MutableLiveData<>();

    // Músicas do gênero selecionado
    private final LiveData<GenderWithMusics> generoComMusicas;

    public SharedViewModel(@NonNull Application application) {
        super(application);

        this.repository = new Repository(application);

        // Usuário
        this.usuarioAtivo = repository.getUsuarioAtivo();

        // Gêneros
        this.listaGeneros = repository.getAllGenders();

        // Todas as músicas
        this.listaMusicas = repository.getAllMusics();

        // Músicas do gênero selecionado
        this.generoComMusicas = Transformations.switchMap(
                generoSelecionadoId,
                id -> repository.getGenderWithMusics(id)
        );
    }

    public LiveData<User> getUsuarioAtivo() {
        return usuarioAtivo;
    }

    public void logout(User user) {
        repository.logout(user);
    }

    public LiveData<List<Gender>> getListaGeneros() {
        return listaGeneros;
    }

    public LiveData<List<Music>> getListaMusicas() {
        return listaMusicas;
    }

    public void setGeneroSelecionadoId(int genderId) {
        generoSelecionadoId.setValue(genderId);
    }

    public LiveData<GenderWithMusics> getGeneroComMusicas() {
        return generoComMusicas;
    }
}