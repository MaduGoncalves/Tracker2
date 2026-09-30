package com.example.tracker;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

// Configurando a Chave Estrangeira baseada no relacionamento do diagrama
@Entity(tableName = "musics",
        foreignKeys = @ForeignKey(entity = Gender.class,
                parentColumns = "id",
                childColumns = "gender",
                onDelete = ForeignKey.CASCADE))
public class Music {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String name;
    public String image;
    public String audio;
    public String singer;
    public int year;
    public String album;

    // Este campo atua como a chave estrangeira referenciando Gender
    public int gender;
}