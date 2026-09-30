package com.example.tracker;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.example.tracker.model.Gender;
import com.example.tracker.model.User;

import java.util.List;
public class SharedViewModel extends AndroidViewModel {

    private final Repository repository;

    // variavel para monitorar user logado
    private final LiveData<User> usuarioAtivo;

    // variavel para guardar a lista de generos cadastrados no bd
    private final LiveData<List<Gender>> listaGeneros;

    // variavel para guardar o genero selecionado no spinner
    private final MutableLiveData<Integer> generoSelecionadoId = new MutableLiveData<>();

    // variavel que irá guardar as musicas do genero selecionado
    private final LiveData<GenderWithMusics> generoComMusicas;

    public SharedViewModel(@NonNull Application application) {
        super(application);
        this.repository = new Repository(application);

        // 1. Gerenciamento de Sessão via Room
        this.usuarioAtivo = repository.getUsuarioAtivo();

        // 2. Categoria para o Spinner
        this.listaGeneros = repository.getAllGenders();

        // 3. Reatividade entre seleção e busca @Relation
        this.generoComMusicas = Transformations.switchMap(generoSelecionadoId, id ->
                repository.getGenderWithMusics(id)
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

    public void setGeneroSelecionadoId(int genderId) {
        generoSelecionadoId.setValue(genderId);
    }

    public LiveData<GenderWithMusics> getGeneroComMusicas() {
        return generoComMusicas;
    }
}