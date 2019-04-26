package com.artirex.sutakip.Model;

public class ToplamSatilanUrun {

    private int id;
    private int urun_id;
    private int alturun_id;
    private String urun_adi;
    private String alturun_adi;
    private int toplamSatis;
    private String siparistarihi;
    private boolean tf;


    public ToplamSatilanUrun()
    {

    }

    public int getId() {

        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUrun_id() {
        return urun_id;
    }

    public void setUrun_id(int urun_id) {
        this.urun_id = urun_id;
    }

    public int getAlturun_id() {
        return alturun_id;
    }

    public void setAlturun_id(int alturun_id) {
        this.alturun_id = alturun_id;
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

    public int getToplamSatis() {
        return toplamSatis;
    }

    public void setToplamSatis(int toplamSatis) {
        this.toplamSatis = toplamSatis;
    }

    public String getSiparistarihi() {
        return siparistarihi;
    }

    public void setSiparistarihi(String siparistarihi) {
        this.siparistarihi = siparistarihi;
    }

    public boolean isTf() {
        return tf;
    }

    public void setTf(boolean tf) {
        this.tf = tf;
    }


    @Override
    public String toString() {
        return "ToplamSatilanUrun{" +
                "id=" + id +
                ", urun_id=" + urun_id +
                ", alturun_id=" + alturun_id +
                ", urun_adi='" + urun_adi + '\'' +
                ", alturun_adi='" + alturun_adi + '\'' +
                ", toplamSatis=" + toplamSatis +
                ", siparistarihi='" + siparistarihi + '\'' +
                ", tf=" + tf +
                '}';
    }
}
