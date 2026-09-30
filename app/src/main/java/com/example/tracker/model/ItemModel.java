package com.example.tracker.model;

public class ItemModel {
    private final int id;
    private final String nome;
    private final String genero;
    private final int imagem;      // ID do drawable (ex: R.drawable.foto)
    private final int idAudio;     // ID do áudio raw (ex: R.raw.musica)
    private final double nota;
    private final String cantor;
    private final int ano;
    private final String album;
    private final String avaliador;
    private final String avaliacao;

    public ItemModel(int id, String nome, String genero, int imagem, int idAudio, double nota, String cantor, int ano, String album, String avaliador, String avaliacao) {
        this.id = id;
        this.nome = nome;
        this.genero = genero;
        this.imagem = imagem;
        this.idAudio = idAudio;
        this.nota = nota;
        this.cantor = cantor;
        this.ano = ano;
        this.album = album;
        this.avaliador = avaliador;
        this.avaliacao = avaliacao;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getGenero() {
        return genero;
    }

    public int getImagem() {
        return imagem;
    }

    public int getIdAudio() {
        return idAudio;
    }

    public double getNota() {
        return nota;
    }

    public String getCantor() {
        return cantor;
    }

    public int getAno() {
        return ano;
    }

    public String getAlbum() {
        return album;
    }
    public String getAvaliador() {
        return avaliador;
    }
    public String getAvaliacao() {
        return avaliacao;
    }
}
