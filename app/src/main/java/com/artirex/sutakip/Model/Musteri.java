package com.artirex.sutakip.Model;

import java.io.Serializable;

public class Musteri implements Serializable {

    private int musteri_id;
    private String musteri_adi;
    private String musteri_soyadi;
    private String musteri_tel1;
    private String musteri_tel2;
    private String musteri_tel3;
    private String musteri_adres1;
    private String musteri_adres2;
    private int musteri_firma_id;
    private String musteri_bilgi;
    private int musteri_cinsiyet;
    private int ekleyenId;
    private String musteri_kayitTarihi;
    private int durum;
    private boolean tf;

    public int getMusteri_id() {
        return musteri_id;
    }

    public void setMusteri_id(int musteri_id) {
        this.musteri_id = musteri_id;
    }

    public String getMusteri_adi() {
        return musteri_adi;
    }

    public void setMusteri_adi(String musteri_adi) {
        this.musteri_adi = musteri_adi;
    }

    public String getMusteri_soyadi() {
        return musteri_soyadi;
    }

    public void setMusteri_soyadi(String musteri_soyadi) {
        this.musteri_soyadi = musteri_soyadi;
    }

    public String getMusteri_tel1() {
        return musteri_tel1;
    }

    public void setMusteri_tel1(String musteri_tel1) {
        this.musteri_tel1 = musteri_tel1;
    }

    public String getMusteri_tel2() {
        return musteri_tel2;
    }

    public void setMusteri_tel2(String musteri_tel2) {
        this.musteri_tel2 = musteri_tel2;
    }

    public String getMusteri_tel3() {
        return musteri_tel3;
    }

    public void setMusteri_tel3(String musteri_tel3) {
        this.musteri_tel3 = musteri_tel3;
    }

    public String getMusteri_adres1() {
        return musteri_adres1;
    }

    public void setMusteri_adres1(String musteri_adres1) {
        this.musteri_adres1 = musteri_adres1;
    }

    public String getMusteri_adres2() {
        return musteri_adres2;
    }

    public void setMusteri_adres2(String musteri_adres2) {
        this.musteri_adres2 = musteri_adres2;
    }

    public int getMusteri_firma_id() {
        return musteri_firma_id;
    }

    public void setMusteri_firma_id(int musteri_firma_id) {
        this.musteri_firma_id = musteri_firma_id;
    }

    public String getMusteri_bilgi() {
        return musteri_bilgi;
    }

    public void setMusteri_bilgi(String musteri_bilgi) {
        this.musteri_bilgi = musteri_bilgi;
    }

    public int getMusteri_cinsiyet() {
        return musteri_cinsiyet;
    }

    public void setMusteri_cinsiyet(int musteri_cinsiyet) {
        this.musteri_cinsiyet = musteri_cinsiyet;
    }

    public int getEkleyenId() {
        return ekleyenId;
    }

    public void setEkleyenId(int ekleyenId) {
        this.ekleyenId = ekleyenId;
    }

    public String getMusteri_kayitTarihi() {
        return musteri_kayitTarihi;
    }

    public void setMusteri_kayitTarihi(String musteri_kayitTarihi) {
        this.musteri_kayitTarihi = musteri_kayitTarihi;
    }

    public int getDurum() {
        return durum;
    }

    public void setDurum(int durum) {
        this.durum = durum;
    }


    public boolean isTf() {
        return tf;
    }

    public void setTf(boolean tf) {
        this.tf = tf;
    }

    @Override
    public String toString() {
        return "Musteri{" +
                "musteri_id=" + musteri_id +
                ", musteri_adi='" + musteri_adi + '\'' +
                ", musteri_soyadi='" + musteri_soyadi + '\'' +
                ", musteri_tel1='" + musteri_tel1 + '\'' +
                ", musteri_tel2='" + musteri_tel2 + '\'' +
                ", musteri_tel3='" + musteri_tel3 + '\'' +
                ", musteri_adres1='" + musteri_adres1 + '\'' +
                ", musteri_adres2='" + musteri_adres2 + '\'' +
                ", musteri_firma_id=" + musteri_firma_id +
                ", musteri_bilgi='" + musteri_bilgi + '\'' +
                ", musteri_cinsiyet=" + musteri_cinsiyet +
                ", ekleyenId=" + ekleyenId +
                ", musteri_kayitTarihi='" + musteri_kayitTarihi + '\'' +
                ", durum=" + durum +
                ", tf=" + tf +
                '}';
    }
}
