package com.artirex.sutakip.Model;

public class AracModel {

    private int model_id;
    private String model_adi;
    private String model_bilgi;
    private int ekleyenId;
    private String model_kayitTarihi;
    private int durum;

    public int getModel_id() {
        return model_id;
    }

    public void setModel_id(int model_id) {
        this.model_id = model_id;
    }

    public String getModel_adi() {
        return model_adi;
    }

    public void setModel_adi(String model_adi) {
        this.model_adi = model_adi;
    }

    public String getModel_bilgi() {
        return model_bilgi;
    }

    public void setModel_bilgi(String model_bilgi) {
        this.model_bilgi = model_bilgi;
    }

    public int getEkleyenId() {
        return ekleyenId;
    }

    public void setEkleyenId(int ekleyenId) {
        this.ekleyenId = ekleyenId;
    }

    public String getModel_kayitTarihi() {
        return model_kayitTarihi;
    }

    public void setModel_kayitTarihi(String model_kayitTarihi) {
        this.model_kayitTarihi = model_kayitTarihi;
    }

    public int getDurum() {
        return durum;
    }

    public void setDurum(int durum) {
        this.durum = durum;
    }
}
