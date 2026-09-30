package com.example.tracker;


import android.widget.BaseAdapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tracker.model.ItemModel;

import java.util.List;

public class ItemAdapter extends BaseAdapter {

    private final Context context;
    private List<ItemModel> listaMusicas;

    public ItemAdapter(Context context, List<ItemModel> listaMusicas) {
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
        return listaMusicas.get(position).getId();
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
        TextView textNota = convertView.findViewById(R.id.textNota);

        ItemModel musica = listaMusicas.get(position);

        imageCapa.setImageResource(musica.getImagem());
        textNome.setText(musica.getNome());
        textCantor.setText(musica.getCantor());
        textNota.setText(criarEstrelas(musica.getNota()));

        return convertView;
    }

    public void atualizarLista(List<ItemModel> novaLista) {
        this.listaMusicas = novaLista;
        notifyDataSetChanged();
    }

    private String criarEstrelas(double nota) {
        int notaInteira = (int) nota;

        StringBuilder estrelas = new StringBuilder();

        for (int i = 0; i < 5; i++) {
            if (i < notaInteira) {
                estrelas.append("★");
            } else {
                estrelas.append("☆");
            }
        }

        return estrelas.toString();
    }
}