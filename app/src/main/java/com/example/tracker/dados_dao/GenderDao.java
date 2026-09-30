package com.example.tracker.dados_dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.tracker.model.Gender;
import com.example.tracker.GenderWithMusics;

import java.util.List;

@Dao
public interface GenderDao {
    @Query("SELECT * FROM genders")
    LiveData<List<Gender>> getAll();

    // O @Transaction é obrigatório ao usar @Relation
    @Transaction
    @Query("SELECT * FROM genders WHERE id = :genderId")
    LiveData<GenderWithMusics> getGenderWithMusics(int genderId);

    @Insert
    void insert(Gender g);
}