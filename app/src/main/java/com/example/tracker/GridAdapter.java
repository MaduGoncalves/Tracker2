package com.example.tracker;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tracker.model.Music;

import java.util.ArrayList;
import java.util.List;

public class GridAdapter extends BaseAdapter {

    private final Context context;
    private List<Music> listaMusicas;

    public GridAdapter(Context context, List<Music> listaMusicas) {
        this.context = context;
        this.listaMusicas = listaMusicas;
    }

    @Override
    public int getCount() {
        return listaMusicas.size();
    }

    @Override
    public Object getItem(int position) {
        return listaMusicas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return listaMusicas.get(position).id;
    }

    @Override
    public View getView(
            int position,
            View convertView,
            ViewGroup parent
    ) {

        if (convertView == null) {
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.item_grid, parent, false);
        }

        TextView textNomeGrid =
                convertView.findViewById(R.id.textNomeGrid);

        TextView textCantorGrid =
                convertView.findViewById(R.id.textCantorGrid);

        ImageView imageCapaGrid =
                convertView.findViewById(R.id.imageCapaGrid);

        Music musica = listaMusicas.get(position);

        textNomeGrid.setText(musica.name);
        textCantorGrid.setText(musica.singer);

        carregarImagem(
                imageCapaGrid,
                musica.image
        );

        return convertView;
    }

    public void atualizarLista(List<Music> novaLista) {

        this.listaMusicas = novaLista != null
                ? novaLista
                : new ArrayList<>();

        notifyDataSetChanged();
    }

    private void carregarImagem(
            ImageView imageView,
            String uriImagem
    ) {

        if (uriImagem == null || uriImagem.isEmpty()) {

            imageView.setImageResource(
                    android.R.drawable.ic_menu_gallery
            );

            return;
        }

        try {

            imageView.setImageURI(
                    Uri.parse(uriImagem)
            );

        } catch (Exception e) {

            imageView.setImageResource(
                    android.R.drawable.ic_menu_gallery
            );
        }
    }
}