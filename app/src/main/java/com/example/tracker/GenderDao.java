package com.example.tracker;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;
import java.util.List;

@Dao
public interface GenderDao {
    @Query("SELECT * FROM genders")
    List<Gender> getAll();

    @Insert
    void insert(Gender g);

    @Update
    void atualizar(Gender g);

    @Delete
    void apagar(Gender g);

    // O @Transaction é obrigatório porque o Room fará múltiplos SELECTs
    @Transaction
    @Query("SELECT * FROM genders")
    List<GenderWithMusics> getGendersWithMusics();
}