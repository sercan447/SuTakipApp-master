package com.artirex.sutakip.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Siparis {

    @SerializedName("siparis_id")
    private int siparis_id;

    /*@SerializedName("siparis_urun_tipi")
    private int siparis_urun_tipi;
    @SerializedName("siparis_urun_boyutu")
    private int siparis_urun_boyutu;
    */
    @SerializedName("siparis_bilgi")
    private String siparis_bilgi;
    @SerializedName("siparis_musteri_id")
    private int siparis_musteri_id;
    @SerializedName("siparis_arac_id")
    private int siparis_arac_id;
    @SerializedName("siparis_durum")
    private int siparis_durum;
    @SerializedName("ekleyenId")
    private int ekleyenId;
    @SerializedName("siparisilist")
    @Expose
    private List<StokBilgi> stokBilgiList;

    @SerializedName("personel_id")
    private int personel_id;

    public Siparis()
    {

    }

    public int getSiparis_id() {
        return siparis_id;
    }

    public void setSiparis_id(int siparis_id) {
        this.siparis_id = siparis_id;
    }

    public int getPersonel_id() {
        return personel_id;
    }

    public void setPersonel_id(int personel_id) {
        this.personel_id = personel_id;
    }

    /*  public int getSiparis_urun_tipi() {
        return siparis_urun_tipi;
    }

    public void setSiparis_urun_tipi(int siparis_urun_tipi) {
        this.siparis_urun_tipi = siparis_urun_tipi;
    }

    public int getSiparis_urun_boyutu() {
        return siparis_urun_boyutu;
    }

    public void setSiparis_urun_boyutu(int siparis_urun_boyutu) {
        this.siparis_urun_boyutu = siparis_urun_boyutu;
    }*/

    public String getSiparis_bilgi() {
        return siparis_bilgi;
    }

    public void setSiparis_bilgi(String siparis_bilgi) {
        this.siparis_bilgi = siparis_bilgi;
    }

    public int getSiparis_musteri_id() {
        return siparis_musteri_id;
    }

    public void setSiparis_musteri_id(int siparis_musteri_id) {
        this.siparis_musteri_id = siparis_musteri_id;
    }

    public int getSiparis_arac_id() {
        return siparis_arac_id;
    }

    public void setSiparis_arac_id(int siparis_arac_id) {
        this.siparis_arac_id = siparis_arac_id;
    }

    public int getSiparis_durum() {
        return siparis_durum;
    }

    public void setSiparis_durum(int siparis_durum) {
        this.siparis_durum = siparis_durum;
    }

    public int getEkleyenId() {
        return ekleyenId;
    }

    public void setEkleyenId(int ekleyenId) {
        this.ekleyenId = ekleyenId;
    }

    public List<StokBilgi> getStokBilgiList() {
        return stokBilgiList;
    }

    public void setStokBilgiList(List<StokBilgi> stokBilgiList) {
        this.stokBilgiList = stokBilgiList;
    }
}
