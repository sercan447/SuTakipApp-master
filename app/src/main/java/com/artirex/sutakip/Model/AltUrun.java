package com.artirex.sutakip.Model;

public class AltUrun {

    private int alturun_id;
    private String alturun_adi;
    private int tip_urun_id;


    public AltUrun()
    {

    }

    public AltUrun(int alturun_id, String alturun_adi, int tip_urun_id) {
        this.alturun_id = alturun_id;
        this.alturun_adi = alturun_adi;
        this.tip_urun_id = tip_urun_id;
    }

    public int getAlturun_id() {
        return alturun_id;
    }

    public void setAlturun_id(int alturun_id) {
        this.alturun_id = alturun_id;
    }

    public String getAlturun_adi() {
        return alturun_adi;
    }

    public void setAlturun_adi(String alturun_adi) {
        this.alturun_adi = alturun_adi;
    }

    public int getTip_urun_id() {
        return tip_urun_id;
    }

    public void setTip_urun_id(int tip_urun_id) {
        this.tip_urun_id = tip_urun_id;
    }
}
