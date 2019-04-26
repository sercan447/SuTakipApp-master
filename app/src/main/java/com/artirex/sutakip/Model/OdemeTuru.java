package com.artirex.sutakip.Model;

public class OdemeTuru {

    private int odeme_id;
    private String odeme_adi;

    public OdemeTuru()
    {

    }

    public OdemeTuru(int odeme_id, String odeme_adi) {
        this.odeme_id = odeme_id;
        this.odeme_adi = odeme_adi;
    }

    public int getOdeme_id() {
        return odeme_id;
    }

    public void setOdeme_id(int odeme_id) {
        this.odeme_id = odeme_id;
    }

    public String getOdeme_adi() {
        return odeme_adi;
    }

    public void setOdeme_adi(String odeme_adi) {
        this.odeme_adi = odeme_adi;
    }
}
