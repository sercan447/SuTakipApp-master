package com.artirex.sutakip.Model;

public class Cagrilar {

    private int cagri_id;
    private String telefon_no;
    private String aramaZamani;
    private String tarih;
    private int aramaTipi;

    public Cagrilar(int cagri_id, String telefon_no, String aramaZamani, String tarih, int aramaTipi) {
        this.cagri_id = cagri_id;
        this.telefon_no = telefon_no;
        this.aramaZamani = aramaZamani;
        this.tarih = tarih;
        this.aramaTipi = aramaTipi;
    }

    public int getCagri_id() {
        return cagri_id;
    }

    public void setCagri_id(int cagri_id) {
        this.cagri_id = cagri_id;
    }

    public String getTelefon_no() {
        return telefon_no;
    }

    public void setTelefon_no(String telefon_no) {
        this.telefon_no = telefon_no;
    }

    public String getAramaZamani() {
        return aramaZamani;
    }

    public void setAramaZamani(String aramaZamani) {
        this.aramaZamani = aramaZamani;
    }

    public String getTarih() {
        return tarih;
    }

    public void setTarih(String tarih) {
        this.tarih = tarih;
    }


    public int getAramaTipi() {
        return aramaTipi;
    }

    public void setAramaTipi(int aramaTipi) {
        this.aramaTipi = aramaTipi;
    }

    @Override
    public String toString() {
        return "Cagrilar{" +
                "cagri_id=" + cagri_id +
                ", telefon_no='" + telefon_no + '\'' +
                ", aramaZamani='" + aramaZamani + '\'' +
                ", tarih='" + tarih + '\'' +
                ", aramaTipi=" + aramaTipi +
                '}';
    }
}
