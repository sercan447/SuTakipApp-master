package com.artirex.sutakip.Model;

import java.io.Serializable;

public class Urun implements Serializable {

    private int urun_id;
    private int alturun_id;
    private String urun_adi;
    private String urun_tipi;
    private String urun_boyutu;
    private int stok;
    private String urun_bilgi;
    private int ekleyenId;
    private String kayit_tarihi;
    private int durum;

    public Urun()
    {

    }

    public Urun(int urun_id, int alturun_id, String urun_adi, String urun_tipi, String urun_boyutu, int stok, String urun_bilgi, int ekleyenId, String kayit_tarihi, int durum) {
        this.urun_id = urun_id;
        this.alturun_id = alturun_id;
        this.urun_adi = urun_adi;
        this.urun_tipi = urun_tipi;
        this.urun_boyutu = urun_boyutu;
        this.stok = stok;
        this.urun_bilgi = urun_bilgi;
        this.ekleyenId = ekleyenId;
        this.kayit_tarihi = kayit_tarihi;
        this.durum = durum;
    }

    public int getUrun_id() {
        return urun_id;
    }

    public void setUrun_id(int urun_id) {
        this.urun_id = urun_id;
    }

    public String getUrun_adi() {
        return urun_adi;
    }

    public void setUrun_adi(String urun_adi) {
        this.urun_adi = urun_adi;
    }

    public String getUrun_tipi() {
        return urun_tipi;
    }

    public void setUrun_tipi(String urun_tipi) {
        this.urun_tipi = urun_tipi;
    }

    public String getUrun_boyutu() {
        return urun_boyutu;
    }

    public void setUrun_boyutu(String urun_boyutu) {
        this.urun_boyutu = urun_boyutu;
    }

    public String getUrun_bilgi() {
        return urun_bilgi;
    }

    public void setUrun_bilgi(String urun_bilgi) {
        this.urun_bilgi = urun_bilgi;
    }

    public int getEkleyenId() {
        return ekleyenId;
    }

    public void setEkleyenId(int ekleyenId) {
        this.ekleyenId = ekleyenId;
    }

    public String getKayit_tarihi() {
        return kayit_tarihi;
    }

    public void setKayit_tarihi(String kayit_tarihi) {
        this.kayit_tarihi = kayit_tarihi;
    }

    public int getDurum() {
        return durum;
    }

    public void setDurum(int durum) {
        this.durum = durum;
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
}
