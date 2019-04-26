package com.artirex.sutakip.Model;

public class AracGet {

    private int arac_id;
    private String arac_plaka;
    private String arac_telefon;
    private String arac_model;
    private String arac_bilgi;
    private int ekleyenId;
    private boolean tf;


    public AracGet() {
    }

    public AracGet(int arac_id, String arac_plaka, String arac_telefon, String arac_model, String arac_bilgi, int ekleyenId, boolean tf) {
        this.arac_id = arac_id;
        this.arac_plaka = arac_plaka;
        this.arac_telefon = arac_telefon;
        this.arac_model = arac_model;
        this.arac_bilgi = arac_bilgi;
        this.ekleyenId = ekleyenId;
        this.tf = tf;
    }

    public int getArac_id() {
        return arac_id;
    }

    public void setArac_id(int arac_id) {
        this.arac_id = arac_id;
    }

    public String getArac_plaka() {
        return arac_plaka;
    }

    public void setArac_plaka(String arac_plaka) {
        this.arac_plaka = arac_plaka;
    }

    public String getArac_telefon() {
        return arac_telefon;
    }

    public void setArac_telefon(String arac_telefon) {
        this.arac_telefon = arac_telefon;
    }

    public String getArac_model() {
        return arac_model;
    }

    public void setArac_model(String arac_model) {
        this.arac_model = arac_model;
    }

    public String getArac_bilgi() {
        return arac_bilgi;
    }

    public void setArac_bilgi(String arac_bilgi) {
        this.arac_bilgi = arac_bilgi;
    }

    public int getEkleyenId() {
        return ekleyenId;
    }

    public void setEkleyenId(int ekleyenId) {
        this.ekleyenId = ekleyenId;
    }

    public boolean isTf() {
        return tf;
    }

    public void setTf(boolean tf) {
        this.tf = tf;
    }

    @Override
    public String toString() {
        return "AracGet{" +
                "arac_id=" + arac_id +
                ", arac_plaka='" + arac_plaka + '\'' +
                ", arac_telefon='" + arac_telefon + '\'' +
                ", arac_model='" + arac_model + '\'' +
                ", arac_bilgi='" + arac_bilgi + '\'' +
                ", ekleyenId=" + ekleyenId +
                ", tf=" + tf +
                '}';
    }
}
