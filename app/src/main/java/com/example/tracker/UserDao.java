package com.example.tracker;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public interface UserDao {
    // Método para buscar o usuário pelo ID (resolve o erro de compilação)
    @Query("SELECT * FROM users WHERE id = :id LIMIT 1")
    User buscarPorId(int id);

    // Retorna o usuário logado no momento via LiveData (o Room notifica a MainActivity automaticamente)
    @Query("SELECT * FROM users WHERE isLogged = 1 LIMIT 1")
    LiveData<User> getUsuarioAtivo();

    // Busca o usuário pelo nome para validar a senha na tela de LoginFragment
    @Query("SELECT * FROM users WHERE user_name = :userName LIMIT 1")
    User getUserByUsername(String userName);

    // Retorna a lista completa de usuários
    @Query("SELECT * FROM users")
    LiveData<List<User>> getAll();

    @Insert
    void insert(User u);

    @Update
    void atualizar(User u);

    @Delete
    void apagar(User u);

}