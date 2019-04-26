package com.artirex.sutakip.Model;

public class KullaniciYetki {

    private int yetki_id;
    private String yetki;


    public KullaniciYetki(){}
    public KullaniciYetki(int yetki_id, String yetki) {
        this.yetki_id = yetki_id;
        this.yetki = yetki;
    }

    public int getYetki_id() {
        return yetki_id;
    }

    public void setYetki_id(int yetki_id) {
        this.yetki_id = yetki_id;
    }

    public String getYetki() {
        return yetki;
    }

    public void setYetki(String yetki) {
        this.yetki = yetki;
    }
}

