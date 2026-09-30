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

public class ItemAdapter extends BaseAdapter {

    private final Context context;
    private List<Music> listaMusicas;

    public ItemAdapter(Context context, List<Music> listaMusicas) {
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
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.item_lista, parent, false);
        }

        ImageView imageCapa = convertView.findViewById(R.id.imageCapa);
        TextView textNome = convertView.findViewById(R.id.textNome);
        TextView textCantor = convertView.findViewById(R.id.textCantor);

        Music musica = listaMusicas.get(position);

        textNome.setText(musica.name);
        textCantor.setText(musica.singer);

        carregarImagem(imageCapa, musica.image);

        return convertView;
    }

    public void atualizarLista(List<Music> novaLista) {
        this.listaMusicas = novaLista != null
                ? novaLista
                : new ArrayList<>();

        notifyDataSetChanged();
    }

    private void carregarImagem(ImageView imageView, String uriImagem) {

        if (uriImagem == null || uriImagem.isEmpty()) {
            imageView.setImageResource(android.R.drawable.ic_menu_gallery);
            return;
        }

        try {
            imageView.setImageURI(Uri.parse(uriImagem));
        } catch (Exception e) {
            imageView.setImageResource(android.R.drawable.ic_menu_gallery);
        }
    }
}