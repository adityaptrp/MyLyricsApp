package com.bh183.permana;

public class Lagu {
    private int idLagu;
    private String judul;
    private String artist;
    private String gambar;
    private String album;
    private String lirikLagu;
    private String link;

    public Lagu(int idLagu, String judul, String artist, String gambar, String album, String lirikLagu, String link) {
        this.idLagu = idLagu;
        this.judul = judul;
        this.artist = artist;
        this.gambar = gambar;
        this.album = album;
        this.lirikLagu = lirikLagu;
        this.link = link;
    }

    public int getIdLagu() {
        return idLagu;
    }

    public void setIdLagu(int idLagu) {
        this.idLagu = idLagu;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getLirikLagu() {
        return lirikLagu;
    }

    public void setLirikLagu(String lirikLagu) {
        this.lirikLagu = lirikLagu;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
