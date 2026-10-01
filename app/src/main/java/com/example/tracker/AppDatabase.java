package com.example.tracker;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.tracker.dados_dao.GenderDao;
import com.example.tracker.dados_dao.MusicDao;
import com.example.tracker.dados_dao.UserDao;
import com.example.tracker.model.Gender;
import com.example.tracker.model.Music;
import com.example.tracker.model.User;

import java.util.concurrent.Executors;

@Database(entities = {User.class, Music.class, Gender.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract UserDao userDao();
    public abstract MusicDao musicDao();
    public abstract GenderDao genderDao();

    private static volatile AppDatabase INSTANCE;

    public static AppDatabase get(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    AppDatabase.class,
                                    "tracker_db"
                            )
                            .addCallback(new RoomDatabase.Callback() {
                                @Override
                                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                    super.onCreate(db);

                                    Executors.newSingleThreadExecutor().execute(() -> {
                                        AppDatabase database = INSTANCE;
                                        if (database != null) {
                                            GenderDao gDao = database.genderDao();
                                            MusicDao mDao = database.musicDao();

                                            // 1. Popula os Gêneros
                                            Gender gKpop = new Gender();
                                            gKpop.name = "K-pop";
                                            gDao.insert(gKpop); // ID 1

                                            Gender gGospel = new Gender();
                                            gGospel.name = "Gospel";
                                            gDao.insert(gGospel); // ID 2

                                            Gender gSertanejo = new Gender();
                                            gSertanejo.name = "Sertanejo";
                                            gDao.insert(gSertanejo); // ID 3

                                            Gender gPop = new Gender();
                                            gPop.name = "Pop";
                                            gDao.insert(gPop); // ID 4

                                            // String base do pacote do aplicativo para os URIs locais
                                            String pkgName = context.getPackageName();
                                            String uriAudioPrefix = "android.resource://" + pkgName + "/raw/";
                                            String uriImgPrefix = "android.resource://" + pkgName + "/drawable/";

                                            //K-POP (Gênero ID 1)
                                            mDao.insert(criarMusica("Spring Day", "BTS", 2017, "You Never Walk Alone", 1, uriImgPrefix + "springday", uriAudioPrefix + "spring_day"));
                                            mDao.insert(criarMusica("DNA", "BTS", 2021, "Love Yourself", 1, uriImgPrefix + "dna", uriAudioPrefix + "dna"));
                                            mDao.insert(criarMusica("Black Swan", "BTS", 2017, "Map of the Soul: 7", 1, uriImgPrefix + "blackswan", uriAudioPrefix + "black_swan"));
                                            mDao.insert(criarMusica("Run BTS", "BTS", 2018, "Proof", 1, uriImgPrefix + "runbts", uriAudioPrefix + "run"));
                                            mDao.insert(criarMusica("Boy With Luv", "BTS", 2019, "Map of the Soul: Persona", 1, uriImgPrefix + "boy_with_luv", uriAudioPrefix + "boy_with_luv"));
                                            mDao.insert(criarMusica("Dynamite", "BTS", 2020, "BE", 1, uriImgPrefix + "dynamite", uriAudioPrefix + "dynamite"));
                                            mDao.insert(criarMusica("Butter", "BTS", 2021, "Butter", 1, uriImgPrefix + "butter", uriAudioPrefix + "butter"));
                                            mDao.insert(criarMusica("Fake Love", "BTS", 2018, "Love Yourself: Tear", 1, uriImgPrefix + "fake_love", uriAudioPrefix + "fake_love"));
                                            mDao.insert(criarMusica("MIC Drop", "BTS", 2017, "Love Yourself: Her", 1, uriImgPrefix + "mic_drop", uriAudioPrefix + "mic_drop"));
                                            mDao.insert(criarMusica("Blood Sweat & Tears", "BTS", 2016, "Wings", 1, uriImgPrefix + "blood_sweat_tears", uriAudioPrefix + "blood_sweat_tears"));
                                            mDao.insert(criarMusica("Life Goes On", "BTS", 2020, "BE", 1, uriImgPrefix + "blackswan", uriAudioPrefix + "life_goes"));

                                            //GOSPEL (Gênero ID 2)
                                            mDao.insert(criarMusica("Filho da Fé", "Samuel Messias", 2026, "Filho da Fé", 2, uriImgPrefix + "filho_da_fe", uriAudioPrefix + "filho_da_fe"));
                                            mDao.insert(criarMusica("Lugar Secreto", "Gabriela Rocha", 2015, "Céu", 2, uriImgPrefix + "lugarsecreto", uriAudioPrefix + "lugar_secreto"));
                                            mDao.insert(criarMusica("Ousado Amor", "Isaias Saad", 2020, "Ousado Amor", 2, uriImgPrefix + "ousado", uriAudioPrefix + "ousado_amor"));
                                            mDao.insert(criarMusica("Raridade", "Anderson Freire", 2018, "Raridade", 2, uriImgPrefix + "raridade", uriAudioPrefix + "raridade"));
                                            mDao.insert(criarMusica("Faz Chover", "Fernandinho", 2018, "Faz Chover", 2, uriImgPrefix + "faz_chover", uriAudioPrefix + "faz_chover"));
                                            mDao.insert(criarMusica("Todavia Me Alegrarei", "Samuel Miranda", 2020, "Todavia", 2, uriImgPrefix + "todavia_me_alegrarei", uriAudioPrefix + "todavia_me_alegrarei"));
                                            mDao.insert(criarMusica("Vitória no Deserto", "Aline Barros", 2014, "Extraordinário Amor", 2, uriImgPrefix + "vitoria_no_deserto", uriAudioPrefix + "vitoria_no_deserto"));
                                            mDao.insert(criarMusica("Porque Ele Vive", "Harpa Cristã", 2017, "Hinos", 2, uriImgPrefix + "porque_ele_vive", uriAudioPrefix + "porque_ele_vive"));
                                            mDao.insert(criarMusica("Nossa Canção", "Gospel", 2019, "Single", 2, uriImgPrefix + "nossa_cancao", uriAudioPrefix + "nossa_cancao"));
                                            mDao.insert(criarMusica("A Ele a Glória", "Diante do Trono", 2021, "A Ele a Glória", 2, uriImgPrefix + "a_ele_a_gloria", uriAudioPrefix + "a_ele_a_gloria"));
                                            mDao.insert(criarMusica("Casa Sua", "Casa Worship", 2020, "A Casa é Sua", 2, uriImgPrefix + "faz_chover", uriAudioPrefix + "casa_sua"));

                                            // SERTANEJO (Gênero ID 3)
                                            mDao.insert(criarMusica("Amo Noite e Dia", "Jorge & Mateus", 2010, "Aí Já Era", 3, uriImgPrefix + "noite_dia", uriAudioPrefix + "amo_noite_e_dia"));
                                            mDao.insert(criarMusica("Voa Flor", "Jorge & Mateus", 2015, "Os Anjos Cantam", 3, uriImgPrefix + "voa_flor", uriAudioPrefix + "voa_flor"));
                                            mDao.insert(criarMusica("Seu Astral", "Jorge & Mateus", 2018, "Terra Sem CEP", 3, uriImgPrefix + "seu_astral", uriAudioPrefix + "seu_astral"));
                                            mDao.insert(criarMusica("Os Anjos Cantam", "Jorge & Mateus", 2015, "Os Anjos Cantam", 3, uriImgPrefix + "anjos_cantam", uriAudioPrefix + "anjos_cantam"));
                                            mDao.insert(criarMusica("Louca de Saudade", "Jorge & Mateus", 2016, "Como Sempre Feito Nunca", 3, uriImgPrefix + "louca_de_saudade", uriAudioPrefix + "louca_de_saudade"));
                                            mDao.insert(criarMusica("Pode Chorar", "Jorge & Mateus", 2007, "Ao Vivo em Goiânia", 3, uriImgPrefix + "pode_chorar", uriAudioPrefix + "pode_chorar"));
                                            mDao.insert(criarMusica("Sosseguei", "Jorge & Mateus", 2015, "Como Sempre Feito Nunca", 3, uriImgPrefix + "sosseguei", uriAudioPrefix + "sosseguei"));
                                            mDao.insert(criarMusica("Propaganda", "Jorge & Mateus", 2018, "Terra Sem CEP", 3, uriImgPrefix + "propaganda", uriAudioPrefix + "propaganda"));
                                            mDao.insert(criarMusica("Tijolão", "Jorge & Mateus", 2019, "Tijolão", 3, uriImgPrefix + "tijolao", uriAudioPrefix + "tijolao"));
                                            mDao.insert(criarMusica("Sozinho", "Jorge & Mateus", 2012, "A Hora é Agora", 3, uriImgPrefix + "seu_astral", uriAudioPrefix + "sozinho"));

                                            //POP (Gênero ID 4)
                                            mDao.insert(criarMusica("Espresso", "Sabrina Carpenter", 2024, "Short n' Sweet", 4, uriImgPrefix + "expresso", uriAudioPrefix + "expresso"));
                                            mDao.insert(criarMusica("Please Please Please", "Sabrina Carpenter", 2024, "Short n' Sweet", 4, uriImgPrefix + "please", uriAudioPrefix + "please"));
                                            mDao.insert(criarMusica("Feather", "Sabrina Carpenter", 2023, "Emails I Can't Send", 4, uriImgPrefix + "feather", uriAudioPrefix + "feather"));
                                            mDao.insert(criarMusica("Nonsense", "Sabrina Carpenter", 2022, "Emails I Can't Send", 4, uriImgPrefix + "nonsense", uriAudioPrefix + "nonsense"));
                                            mDao.insert(criarMusica("House Tour", "Sabrina Carpenter", 2025, "Short n' Sweet", 4, uriImgPrefix + "house_tour", uriAudioPrefix + "house_tour"));
                                            mDao.insert(criarMusica("Taste", "Sabrina Carpenter", 2024, "Short n' Sweet", 4, uriImgPrefix + "taste", uriAudioPrefix + "taste"));
                                            mDao.insert(criarMusica("Skin", "Sabrina Carpenter", 2021, "Skin", 4, uriImgPrefix + "skin", uriAudioPrefix + "skin"));
                                            mDao.insert(criarMusica("Thumbs", "Sabrina Carpenter", 2016, "EVOlution", 4, uriImgPrefix + "thumbs", uriAudioPrefix + "thumbs"));
                                            mDao.insert(criarMusica("Skinny Dipping", "Sabrina Carpenter", 2021, "Emails I Can't Send", 4, uriImgPrefix + "skinny_dipping", uriAudioPrefix + "skinny_dipping"));
                                            mDao.insert(criarMusica("Fast Times", "Sabrina Carpenter", 2022, "Emails I Can't Send", 4, uriImgPrefix + "fast_times", uriAudioPrefix + "fast_times"));
                                        }
                                    });
                                }
                            })
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    // Método auxiliar para construir o objeto Music
    private static Music criarMusica(String nome, String cantor, int ano, String album, int generoId, String uriImagem, String uriAudio) {
        Music m = new Music();
        m.name = nome;
        m.singer = cantor;
        m.year = ano;
        m.album = album;
        m.gender = generoId;
        m.image = uriImagem;
        m.audio = uriAudio;
        return m;
    }
}