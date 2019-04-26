package com.artirex.sutakip.Model;

public class Firma {

    private int firma_id;
    private String firma_adi;
    private String firma_bilgi;
    private String firma_tel1;
    private int ekleyenId;
    private int durum;
    private String eklenme_Tarihi;

    public Firma()
    {

    }
    public Firma(int firma_id, String firma_adi, String firma_bilgi, String firma_tel1, int ekleyenId, int durum, String eklenme_Tarihi) {
        this.firma_id = firma_id;
        this.firma_adi = firma_adi;
        this.firma_bilgi = firma_bilgi;
        this.firma_tel1 = firma_tel1;
        this.ekleyenId = ekleyenId;
        this.durum = durum;
        this.eklenme_Tarihi = eklenme_Tarihi;
    }

    public int getFirma_id() {
        return firma_id;
    }

    public void setFirma_id(int firma_id) {
        this.firma_id = firma_id;
    }

    public String getFirma_adi() {
        return firma_adi;
    }

    public void setFirma_adi(String firma_adi) {
        this.firma_adi = firma_adi;
    }

    public String getFirma_bilgi() {
        return firma_bilgi;
    }

    public void setFirma_bilgi(String firma_bilgi) {
        this.firma_bilgi = firma_bilgi;
    }

    public String getFirma_tel1() {
        return firma_tel1;
    }

    public void setFirma_tel1(String firma_tel1) {
        this.firma_tel1 = firma_tel1;
    }

    public int getEkleyenId() {
        return ekleyenId;
    }

    public void setEkleyenId(int ekleyenId) {
        this.ekleyenId = ekleyenId;
    }

    public int getDurum() {
        return durum;
    }

    public void setDurum(int durum) {
        this.durum = durum;
    }

    public String getEklenme_Tarihi() {
        return eklenme_Tarihi;
    }

    public void setEklenme_Tarihi(String eklenme_Tarihi) {
        this.eklenme_Tarihi = eklenme_Tarihi;
    }
}
