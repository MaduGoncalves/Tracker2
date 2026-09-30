package com.example.tracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tracker.model.ItemModel;

import java.util.List;

public class GridAdapter extends BaseAdapter {

    private final Context context;
    private final List<ItemModel> listaMusicas;

    public GridAdapter(Context context, List<ItemModel> listaMusicas) {
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
                    .inflate(R.layout.item_grid, parent, false);
        }

        TextView textNomeGrid = convertView.findViewById(R.id.textNomeGrid);
        TextView textCantorGrid = convertView.findViewById(R.id.textCantorGrid);
        ImageView imageCapaGrid = convertView.findViewById(R.id.imageCapaGrid);

        ItemModel musica = listaMusicas.get(position);

        textNomeGrid.setText(musica.getNome());
        textCantorGrid.setText(musica.getCantor());
        imageCapaGrid.setImageResource(musica.getImagem());

        return convertView;
    }

    public void atualizarLista(List<ItemModel> novaLista) {
        listaMusicas.clear();
        listaMusicas.addAll(novaLista);
        notifyDataSetChanged();
    }
}