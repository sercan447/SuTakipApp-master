package com.artirex.sutakip.Model;

public class Arac {

    private int arac_id;
    private String arac_plaka;
    private String arac_telefon;
    private int arac_model;
    private String arac_bilgi;
    private int ekleyenId;
    private boolean tf;


    public Arac() {
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

    public int getArac_model() {
        return arac_model;
    }

    public void setArac_model(int arac_model) {
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
}
