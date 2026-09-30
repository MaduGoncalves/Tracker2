package com.example.tracker.dados_dao;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.tracker.model.Music;

import java.util.List;

@Dao
public interface MusicDao {
    @Query("SELECT * FROM musics")
    List<Music> getAll();

    @Insert
    void insert(Music m);

    @Update
    void atualizar(Music m);

    @Delete
    void apagar(Music m);
}