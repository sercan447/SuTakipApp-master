package com.artirex.sutakip.Model;

public class StokBilgiShowList {

    //heö stokbilgi_id olarak hemde  arac_id olarak kullanıyotum.
    private int stokbilgi_id;
    private String arac_adi;
    private String urun_adi;
    private String alturun_adi;
    private int stok;
    private boolean tf;

    public StokBilgiShowList(){

    }

    public StokBilgiShowList(String arac_adi, String urun_adi, String alturun_adi, int stok) {
        this.arac_adi = arac_adi;
        this.urun_adi = urun_adi;
        this.alturun_adi = alturun_adi;
        this.stok = stok;
    }

    public StokBilgiShowList(int stokbilgi_id, String arac_adi, String urun_adi, String alturun_adi, int stok) {
        this.stokbilgi_id = stokbilgi_id;
        this.arac_adi = arac_adi;
        this.urun_adi = urun_adi;
        this.alturun_adi = alturun_adi;
        this.stok = stok;
    }

    public int getStokbilgi_id() {
        return stokbilgi_id;
    }

    public void setStokbilgi_id(int stokbilgi_id) {
        this.stokbilgi_id = stokbilgi_id;
    }

    public String getArac_adi() {
        return arac_adi;
    }

    public void setArac_adi(String arac_adi) {
        this.arac_adi = arac_adi;
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
        return stok;
    }

    public void setStok(int stok) {
        this.stok = stok;
    }


    public boolean isTf() {
        return tf;
    }

    public void setTf(boolean tf) {
        this.tf = tf;
    }
}
