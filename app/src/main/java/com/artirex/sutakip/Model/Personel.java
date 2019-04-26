package com.artirex.sutakip.Model;

import java.io.Serializable;

public class Personel implements Serializable {

    private int personel_id;
    private String adi;
    private String soyadi;
    private String telefon;
    private String bilgi;
    private int yetki;
    private int durum;
    private String kayit_tarihi;
    private int cinsiyet;
    private int ekleyenId;
    private String parola;


public Personel(){

}
    public Personel(int personel_id, String adi, String soyadi, String telefon, String bilgi, int yetki, int durum, int cinsiyet) {
        this.personel_id = personel_id;
        this.adi = adi;
        this.soyadi = soyadi;
        this.telefon = telefon;
        this.bilgi = bilgi;
        this.yetki = yetki;
        this.durum = durum;
        this.cinsiyet = cinsiyet;
    }


    public int getPersonel_id() {
        return personel_id;
    }

    public void setPersonel_id(int personel_id) {
        this.personel_id = personel_id;
    }

    public String getAdi() {
        return adi;
    }

    public void setAdi(String adi) {
        this.adi = adi;
    }

    public String getSoyadi() {
        return soyadi;
    }

    public void setSoyadi(String soyadi) {
        this.soyadi = soyadi;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getBilgi() {
        return bilgi;
    }

    public void setBilgi(String bilgi) {
        this.bilgi = bilgi;
    }

    public int getYetki() {
        return yetki;
    }

    public void setYetki(int yetki) {
        this.yetki = yetki;
    }

    public int isDurum() {
        return durum;
    }

    public void setDurum(int durum) {
        this.durum = durum;
    }

    public String getKayit_tarihi() {
        return kayit_tarihi;
    }

    public void setKayit_tarihi(String kayit_tarihi) {
        this.kayit_tarihi = kayit_tarihi;
    }

    public int getCinsiyet() {
        return cinsiyet;
    }

    public void setCinsiyet(int cinsiyet) {
        this.cinsiyet = cinsiyet;
    }

    public int getEkleyenId() {
        return ekleyenId;
    }

    public void setEkleyenId(int ekleyenId) {
        this.ekleyenId = ekleyenId;
    }

    public String getParola() {
        return parola;
    }

    public void setParola(String parola) {
        this.parola = parola;
    }

    public int getDurum() {
        return durum;
    }

    @Override
    public String toString() {
        return "Personel{" +
                "personel_id=" + personel_id +
                ", adi='" + adi + '\'' +
                ", soyadi='" + soyadi + '\'' +
                ", telefon='" + telefon + '\'' +
                ", bilgi='" + bilgi + '\'' +
                ", yetki=" + yetki +
                ", durum=" + durum +
                ", kayit_tarihi='" + kayit_tarihi + '\'' +
                ", cinsiyet=" + cinsiyet +
                ", ekleyenId=" + ekleyenId +
                ", parola='" + parola + '\'' +
                '}';
    }
}
