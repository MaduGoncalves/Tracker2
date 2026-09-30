package com.example.tracker;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.tracker.model.Gender;
import com.example.tracker.model.Music;

import java.util.List;

public class GenderWithMusics {
    // @Embedded inclui todos os campos da tabela Gender no resultado
    @Embedded
    public Gender gender;

    // @Relation diz ao Room como conectar as duas tabelas
    @Relation(
            parentColumn = "id",    // A chave primária na tabela Gender
            entityColumn = "gender" // A chave estrangeira na tabela Music
    )
    public List<Music> musics;
}