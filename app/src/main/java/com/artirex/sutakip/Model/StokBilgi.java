package com.artirex.sutakip.Model;

import com.google.gson.annotations.SerializedName;

public class StokBilgi {

    private int stokbilgi_id;
    @SerializedName("arac_id")
    private int arac_id;
    private int urun_id;
    private int alturun_id;
    @SerializedName("stok")
    private int stok;

    public StokBilgi(){

    }
    public StokBilgi(int arac_id, int urun_id, int alturun_id, int stok) {
        this.arac_id = arac_id;
        this.urun_id = urun_id;
        this.alturun_id = alturun_id;
        this.stok = stok;
    }

    public StokBilgi(int stokbilgi_id, int arac_id, int urun_id, int alturun_id, int stok) {
        this.stokbilgi_id = stokbilgi_id;
        this.arac_id = arac_id;
        this.urun_id = urun_id;
        this.alturun_id = alturun_id;
        this.stok = stok;
    }

    public int getStokbilgi_id() {
        return stokbilgi_id;
    }

    public void setStokbilgi_id(int stokbilgi_id) {
        this.stokbilgi_id = stokbilgi_id;
    }

    public int getArac_id() {
        return arac_id;
    }

    public void setArac_id(int arac_id) {
        this.arac_id = arac_id;
    }

    public int getUrun_id() {
        return urun_id;
    }

    public void setUrun_id(int urun_id) {
        this.urun_id = urun_id;
    }

    public int getAlturun_id() {
        return alturun_id;
    }

    public void setAlturun_id(int alturun_id) {
        this.alturun_id = alturun_id;
    }

    public int getStok() {
        return stok;
    }

    public void setStok(int stok) {
        this.stok = stok;
    }


    @Override
    public String toString() {
        return "StokBilgi{" +
                "stokbilgi_id=" + stokbilgi_id +
                ", arac_id=" + arac_id +
                ", urun_id=" + urun_id +
                ", alturun_id=" + alturun_id +
                ", stok=" + stok +
                '}';
    }
}
