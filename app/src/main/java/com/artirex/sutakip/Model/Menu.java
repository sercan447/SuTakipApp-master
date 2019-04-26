package com.artirex.sutakip.Model;

public class Menu {
    private int menu_id;
    private String menu_adi;
    private int yetki;
    private String resimAdi;

    public Menu()
    {

    }

    public Menu(int menu_id, String menu_adi, int yetki, String resimAdi) {
        this.menu_id = menu_id;
        this.menu_adi = menu_adi;
        this.yetki = yetki;
        this.resimAdi = resimAdi;
    }

    public int getMenu_id() {
        return menu_id;
    }

    public void setMenu_id(int menu_id) {
        this.menu_id = menu_id;
    }

    public String getMenu_adi() {
        return menu_adi;
    }

    public void setMenu_adi(String menu_adi) {
        this.menu_adi = menu_adi;
    }

    public int getYetki() {
        return yetki;
    }

    public void setYetki(int yetki) {
        this.yetki = yetki;
    }

    public String getResimAdi() {
        return resimAdi;
    }

    public void setResimAdi(String resimAdi) {
        this.resimAdi = resimAdi;
    }
}
