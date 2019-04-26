package com.artirex.sutakip.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AracStok {


    private int stok_id;
    @SerializedName("arac_id")
    private int arac_id;
    @SerializedName("kilometre")
    private int kilometre;
    @SerializedName("yakit")
    private int yakit;
    @SerializedName("bilgi")
    private String bilgi;
    @SerializedName("eklenme_tarihi")
    private String eklenme_tarihi;
    @SerializedName("stokbilgilist")
    @Expose
    private List<StokBilgi> stokBilgiList;

    public AracStok(int stok_id, int arac_id, int kilometre, int yakit, String bilgi, String eklenme_tarihi, List<StokBilgi> stokBilgiList) {
        this.stok_id = stok_id;
        this.arac_id = arac_id;
        this.kilometre = kilometre;
        this.yakit = yakit;
        this.bilgi = bilgi;
        this.eklenme_tarihi = eklenme_tarihi;
        this.stokBilgiList = stokBilgiList;
    }

    public AracStok(int arac_id, int kilometre, int yakit, String bilgi, String eklenme_tarihi, List<StokBilgi> stokBilgiList) {
        this.arac_id = arac_id;
        this.kilometre = kilometre;
        this.yakit = yakit;
        this.bilgi = bilgi;
        this.eklenme_tarihi = eklenme_tarihi;
        this.stokBilgiList = stokBilgiList;
    }

    public int getStok_id() {
        return stok_id;
    }

    public void setStok_id(int stok_id) {
        this.stok_id = stok_id;
    }

    public int getArac_id() {
        return arac_id;
    }

    public void setArac_id(int arac_id) {
        this.arac_id = arac_id;
    }

    public int getKilometre() {
        return kilometre;
    }

    public void setKilometre(int kilometre) {
        this.kilometre = kilometre;
    }

    public int getYakit() {
        return yakit;
    }

    public void setYakit(int yakit) {
        this.yakit = yakit;
    }

    public String getBilgi() {
        return bilgi;
    }

    public void setBilgi(String bilgi) {
        this.bilgi = bilgi;
    }

    public String getEklenme_tarihi() {
        return eklenme_tarihi;
    }

    public void setEklenme_tarihi(String eklenme_tarihi) {
        this.eklenme_tarihi = eklenme_tarihi;
    }

    public List<StokBilgi> getStokBilgiList() {
        return stokBilgiList;
    }

    public void setStokBilgiList(List<StokBilgi> stokBilgiList) {
        this.stokBilgiList = stokBilgiList;
    }
}
