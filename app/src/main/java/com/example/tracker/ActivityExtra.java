package com.example.tracker;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.appbar.MaterialToolbar;

public class ActivityExtra extends AppCompatActivity {

    private ImageView imgDestaque;

    private TextView textTitulo;
    private TextView textArtista;
    private TextView textGenero;
    private TextView textAlbum;
    private TextView textAno;

    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_extra);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);

        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        ViewCompat.setOnApplyWindowInsetsListener(
                findViewById(R.id.mainLayout),
                (v, insets) -> {

                    Insets systemBars = insets.getInsets(
                            WindowInsetsCompat.Type.systemBars()
                    );

                    v.setPadding(
                            systemBars.left,
                            systemBars.top,
                            systemBars.right,
                            systemBars.bottom
                    );

                    return insets;
                }
        );

        // Componentes
        imgDestaque = findViewById(R.id.imgDestaque);

        textTitulo = findViewById(R.id.textTitulo);
        textArtista = findViewById(R.id.textArtista);
        textGenero = findViewById(R.id.textGenero);
        textAlbum = findViewById(R.id.textAlbum);
        textAno = findViewById(R.id.textAno);

        // Recebe os dados enviados pela tela anterior
        Intent it = getIntent();

        if (it != null) {

            // Título
            String titulo = it.getStringExtra("ch_titulo");

            if (titulo != null) {
                textTitulo.setText(titulo);
            }

            // Artista
            String artista = it.getStringExtra("ch_artista");

            if (artista != null) {
                textArtista.setText(artista);
            }

            // Gênero
            String genero = it.getStringExtra("ch_genero");

            if (genero != null) {
                textGenero.setText(genero);
            }

            // Álbum
            String album = it.getStringExtra("ch_album");

            if (album != null) {
                textAlbum.setText(album);
            }

            // Ano
            String ano = it.getStringExtra("ch_ano");

            if (ano != null) {
                textAno.setText(ano);
            }

            // Imagem
            String imagem = it.getStringExtra("ch_imagem");

            if (imagem != null && !imagem.isEmpty()) {

                imgDestaque.setImageURI(
                        Uri.parse(imagem)
                );
            }

            // Áudio
            String audio = it.getStringExtra("ch_audio");

            if (audio != null && !audio.isEmpty()) {

                mediaPlayer = MediaPlayer.create(
                        this,
                        Uri.parse(audio)
                );
            }
        }
    }

    // PLAY
    public void onClickPlay(View view) {

        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
    }

    // STOP
    public void onClickStop(View view) {

        if (mediaPlayer != null && mediaPlayer.isPlaying()) {

            mediaPlayer.pause();
            mediaPlayer.seekTo(0);
        }
    }

    // ENCERRAR
    public void onClickEncerrar(View view) {
        finish();
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();

        if (mediaPlayer != null) {

            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }

            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    // MENU
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(
                R.menu.top_menu_main,
                menu
        );

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(
            @NonNull MenuItem item
    ) {

        int id = item.getItemId();

        if (id == R.id.theme_light) {

            AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_NO
            );

            return true;

        } else if (id == R.id.theme_dark) {

            AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_YES
            );

            return true;

        } else if (id == R.id.theme_system) {

            AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
            );

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}