package com.artirex.sutakip.Model;

public class StokBilgiGet {

    private int stokbilgi_id;
    private String arac_plaka;
    private String urun_adi;
    private String alturun_adi;
    private int siparis;

    public StokBilgiGet()
    {

    }

    public int getStokbilgi_id() {
        return stokbilgi_id;
    }

    public void setStokbilgi_id(int stokbilgi_id) {
        this.stokbilgi_id = stokbilgi_id;
    }

    public String getArac_plaka() {
        return arac_plaka;
    }

    public void setArac_plaka(String arac_plaka) {
        this.arac_plaka = arac_plaka;
    }

    public String getUrun_adi() {
        return urun_adi;
    }

    public void setUrun_adi(String urun_adi) {
        this.urun_adi = urun_adi;
    }

    public String getAlturun_adi() {
        return alturun_adi;
    }

    public void setAlturun_adi(String alturun_adi) {
        this.alturun_adi = alturun_adi;
    }

    public int getStok() {
        return siparis;
    }

    public void setStok(int stok) {
        this.siparis = stok;
    }
}
