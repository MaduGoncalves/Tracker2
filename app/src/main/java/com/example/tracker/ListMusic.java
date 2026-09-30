package com.example.tracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.tracker.model.Gender;
import com.example.tracker.model.Music;

import java.util.ArrayList;
import java.util.List;

public class ListMusic extends Fragment {

    private ListView listViewMusicas;
    private ItemAdapter adapter;
    private SharedViewModel viewModel;

    // Lista de gêneros cadastrados no banco
    private List<Gender> generos = new ArrayList<>();

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {
        return inflater.inflate(
                R.layout.fragment_list,
                container,
                false
        );
    }

    @Override
    public void onViewCreated(
            @NonNull View view,
            @Nullable Bundle savedInstanceState
    ) {
        super.onViewCreated(view, savedInstanceState);

        // ---------------------------------------------------------
        // 1. Referência para o ListView
        // ---------------------------------------------------------

        listViewMusicas = view.findViewById(
                R.id.listViewMusicas
        );

        // ---------------------------------------------------------
        // 2. Obtém o SharedViewModel
        // ---------------------------------------------------------

        viewModel = new ViewModelProvider(requireActivity())
                .get(SharedViewModel.class);

        // ---------------------------------------------------------
        // 3. Cria o adapter inicialmente vazio
        // ---------------------------------------------------------

        adapter = new ItemAdapter(
                requireContext(),
                new ArrayList<>()
        );

        listViewMusicas.setAdapter(adapter);

        // ---------------------------------------------------------
        // 4. Observa os gêneros
        // ---------------------------------------------------------

        viewModel.getListaGeneros().observe(
                getViewLifecycleOwner(),
                lista -> {

                    if (lista != null) {
                        generos = lista;
                    }

                }
        );

        // ---------------------------------------------------------
        // 5. Observa as músicas do gênero selecionado
        // ---------------------------------------------------------

        viewModel.getGeneroComMusicas().observe(
                getViewLifecycleOwner(),
                resultado -> {

                    if (resultado != null && resultado.musics != null) {

                        adapter.atualizarLista(
                                resultado.musics
                        );

                    } else {

                        adapter.atualizarLista(
                                new ArrayList<>()
                        );
                    }

                }
        );

        // ---------------------------------------------------------
        // 6. Clique em uma música
        // ---------------------------------------------------------

        listViewMusicas.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(
                            AdapterView<?> parent,
                            View view,
                            int position,
                            long id
                    ) {

                        // Recupera a música clicada
                        Music musica =
                                (Music) parent.getItemAtPosition(position);

                        // Cria a Intent para a tela de detalhes
                        Intent intent = new Intent(
                                requireContext(),
                                ActivityExtra.class
                        );

                        // -------------------------------------------------
                        // Dados da música
                        // -------------------------------------------------

                        intent.putExtra(
                                "ch_titulo",
                                musica.name
                        );

                        intent.putExtra(
                                "ch_artista",
                                musica.singer
                        );

                        intent.putExtra(
                                "ch_genero",
                                obterNomeGenero(musica.gender)
                        );

                        intent.putExtra(
                                "ch_ano",
                                String.valueOf(musica.year)
                        );

                        intent.putExtra(
                                "ch_album",
                                musica.album
                        );

                        intent.putExtra(
                                "ch_imagem",
                                musica.image
                        );

                        intent.putExtra(
                                "ch_audio",
                                musica.audio
                        );

                        // Abre a tela de detalhes
                        startActivity(intent);
                    }
                }
        );
    }

    // -------------------------------------------------------------
    // Obtém o nome do gênero pelo ID armazenado em Music.gender
    // -------------------------------------------------------------

    private String obterNomeGenero(int genderId) {

        for (Gender genero : generos) {

            if (genero.id == genderId) {
                return genero.name;
            }
        }

        return "";
    }
}

