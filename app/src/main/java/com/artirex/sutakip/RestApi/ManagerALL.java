package com.artirex.sutakip.RestApi;

import com.artirex.sutakip.Model.AltUrun;
import com.artirex.sutakip.Model.Arac;
import com.artirex.sutakip.Model.AracGet;
import com.artirex.sutakip.Model.AracModel;
import com.artirex.sutakip.Model.AracStok;
import com.artirex.sutakip.Model.Cagrilar;
import com.artirex.sutakip.Model.Firma;
import com.artirex.sutakip.Model.Login;
import com.artirex.sutakip.Model.Menu;
import com.artirex.sutakip.Model.Musteri;
import com.artirex.sutakip.Model.Personel;
import com.artirex.sutakip.Model.Result;
import com.artirex.sutakip.Model.Siparis;
import com.artirex.sutakip.Model.SiparisGet;
import com.artirex.sutakip.Model.StokBilgi;
import com.artirex.sutakip.Model.StokBilgiShowList;
import com.artirex.sutakip.Model.ToplamAylikSiparis;
import com.artirex.sutakip.Model.ToplamMusteriSiparis;
import com.artirex.sutakip.Model.ToplamOdemeTuru;
import com.artirex.sutakip.Model.ToplamSatilanUrun;
import com.artirex.sutakip.Model.Urun;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class ManagerALL extends BaseManager {


    private static ManagerALL ourInstance = new ManagerALL();

    public static synchronized ManagerALL getInstance() {
        return ourInstance;
    }


    public Call<Login> girisYap(String telefonNo, String parola,String signaltoken) {
        Call<Login> loginGiris = getRestApi().girisYap(telefonNo, parola,signaltoken);
        return loginGiris;
    }

    public Call<Result> userInsert(String adi, String soyadi, String telefon, String bilgi, int yetki, int durum, int cinsiyet,
                                   String parola, int ekleyenId) {
        Call<Result> userIns = getRestApi().userInsert(adi, soyadi, telefon, bilgi, yetki, durum, cinsiyet, parola, ekleyenId);
        return userIns;
    }

    public Call<Result> userUpdate(int personel_id,String adi, String soyadi, String telefon, String bilgi, int yetki, int durum, int cinsiyet,
                                   String parola, int ekleyenId) {
        Call<Result> userIns = getRestApi().userUpdate(personel_id,adi, soyadi, telefon, bilgi, yetki, durum, cinsiyet, parola, ekleyenId);
        return userIns;
    }

    public Call<Result> carInsert(String plaka, String telefon, int model, String bilgi, int ekleyenId) {
        Call<Result> carIns = getRestApi().carInsert(plaka, telefon, model, bilgi, ekleyenId);
        return carIns;
    }
    public Call<Result> carUpdate(int arac_id,String plaka, String telefon, int model, String bilgi, int ekleyenId) {
        Call<Result> carIns = getRestApi().carUpdate(arac_id,plaka, telefon, model, bilgi, ekleyenId);
        return carIns;
    }

    public Call<Result> customerInsert(String musteri_adi, String musteri_soyadi, String musteri_tel1, String musteri_tel2,
                                       String musteri_tel3, String musteri_adres1, String musteri_adres2, int musteri_firma_id,
                                       String musteri_bilgi, int cinsiyet, int ekleyenId,String late,String lng) {
        Call<Result> cusIns = getRestApi().customerInsert(musteri_adi, musteri_soyadi, musteri_tel1, musteri_tel2,
                musteri_tel3, musteri_adres1, musteri_adres2, musteri_firma_id, musteri_bilgi, cinsiyet, ekleyenId,late,lng);
        return cusIns;
    }
    public Call<Result> customerUpdate(int musteri_id,String musteri_adi, String musteri_soyadi, String musteri_tel1, String musteri_tel2,
                                       String musteri_tel3, String musteri_adres1, String musteri_adres2, int musteri_firma_id,
                                       String musteri_bilgi, int cinsiyet, int ekleyenId) {

        Call<Result> cusIns = getRestApi().customerUpdate(musteri_id,musteri_adi, musteri_soyadi, musteri_tel1, musteri_tel2,
                musteri_tel3, musteri_adres1, musteri_adres2, musteri_firma_id, musteri_bilgi, cinsiyet, ekleyenId);
        return cusIns;
    }
    public Call<Result> productInsert(String urun_adi, String urun_tip, String urun_boyut,int stok, String urun_bilgi, int ekleyenId) {
        Call<Result> prodIns = getRestApi().productInsert(urun_adi, urun_tip, urun_boyut, stok,urun_bilgi, ekleyenId);
        return prodIns;
    }

    public Call<Result> productUpdate(int product_id,int alturun_id,String urun_adi, String urun_tip, String urun_boyut, int urun_stok,String urun_bilgi, int ekleyenId) {
        Call<Result> prodIns = getRestApi().productUpdate(product_id,alturun_id,urun_adi, urun_tip, urun_boyut, urun_stok,urun_bilgi, ekleyenId);
        return prodIns;
    }


  /*
    public Call<Result> orderInsert(int siparis_urun_tipi, int siparis_urun_boyutu, String siparis_bilgi, int siparis_musteri_id,
                                    int siparis_arac_id, int siparis_durum, int ekleyenId) {
        Call<Result> orderIns = getRestApi().orderInsert(siparis_urun_tipi, siparis_urun_boyutu, siparis_bilgi, siparis_musteri_id,
                siparis_arac_id, siparis_durum, ekleyenId);
        return orderIns;
    }
    */

    public Call<Result> orderInsert(Siparis siparis) {
        Call<Result> orderIns = getRestApi().orderInsert(siparis);
        return orderIns;
    }

    public Call<List<Urun>> getProductList() {
        Call<List<Urun>> req = getRestApi().getProductList();
        return req;
    }

  public Call<List<AltUrun>> getSubProductList(int urun_id) {
        Call<List<AltUrun>> req = getRestApi().getSubProductList(urun_id);
        return req;
    }
    public Call<List<AracGet>> getCarList() {
        Call<List<AracGet>> req = getRestApi().getCarList();
        return req;
    }

    public Call<Result> carModelInsert(String model_adi, String model_bilgi,int ekleyenId) {
        Call<Result> req = getRestApi().carModelInsert(model_adi, model_bilgi,ekleyenId);
        return req;
    }
    public Call<List<AracModel>> getCarModelList() {
        Call<List<AracModel>> req = getRestApi().getCarModelList();
        return req;
    }

    public Call<Result> firmaInsert(String firma_adi, String firma_bilgi,String firma_tel1,int ekleyenId) {
        Call<Result> req = getRestApi().firmaInsert(firma_adi, firma_bilgi,firma_tel1,ekleyenId);
        return req;
    }
    public Call<List<Firma>> getFirmaList() {
        Call<List<Firma>> req = getRestApi().getFirmaList();
        return req;
    }

    public Call<List<Musteri>> getCustomersList() {
        Call<List<Musteri>> req = getRestApi().getCustomersList();
        return req;
    }

    public Call<List<SiparisGet>> getOrdersList(String durumCode) {
        Call<List<SiparisGet>> req = getRestApi().getOrdersList(durumCode);
        return req;
    }
    public Call<List<SiparisGet>> getOrdersListPayment(int odeme_turu,int tarih_araligi,String baslangictarihi,String bitistarihi) {
        Call<List<SiparisGet>> req = getRestApi().getOrdersListPayment(odeme_turu,tarih_araligi,baslangictarihi,bitistarihi);
        return req;
    }
    public Call<List<Personel>> getPersonsList() {
        Call<List<Personel>> req = getRestApi().getPersonsList();
        return req;
    }

   public Call<Musteri> getCustomer(int musteri_id){

        Call<Musteri> req = getRestApi().getCustomer(musteri_id);
        return req;
    }
    public Call<Musteri> getMusteriKim(String telefon){

        Call<Musteri> req = getRestApi().getMusteriKim(telefon);
        return req;
    }

    public Call<SiparisGet> getOrder(int siparis_id){

        Call<SiparisGet> req = getRestApi().getOrder(siparis_id);
        return req;
    }

    public Call<Urun> getProduct(int urun_id,int alturun_id){

        Call<Urun> req = getRestApi().getProduct(urun_id,alturun_id);
        return req;
    }

    public Call<AracGet> getCar(int arac_id){

        Call<AracGet> req = getRestApi().getCar(arac_id);
        return req;
    }

    public Call<Personel> getUser(int user_id){

        Call<Personel> req = getRestApi().getUser(user_id);
        return req;
    }

    public Call<Result> personelAracAta(int personel_id, int arac_id ,int ekleyenId)
    {
        Call<Result> req = getRestApi().personelAracAta(personel_id,arac_id,ekleyenId);
        return req;
    }

  /* public Call<Result> onesignalToken(int personel_id,String onesignalToken){
        Call<Result> rew = getRestApi().onesignalToken(personel_id,onesignalToken);
        return rew;
   }

    public Call<Result> useroneSignalToken(int personel_id,String onesignalToken){
        Call<Result> rew = getRestApi().useroneSignalToken(personel_id,onesignalToken);
        return rew;
    }
    */


   public Call<List<SiparisGet>> getSoforSiparis( int siparis_sofor_id , int siparis_durum)
    {
        Call<List<SiparisGet>> req = getRestApi().getSoforSiparis(siparis_sofor_id, siparis_durum);
        return req;
    }

    public Call<List<Menu>> getMenus(int yetki)
    {
        Call<List<Menu>> req = getRestApi().getMenus(yetki);
        return req;
    }

    public Call<Result> siparisDurumGuncelleme(int siparis_durum, String durumTarihi,int siparis_id,int odemeTuru)
    {
        Call<Result> req = getRestApi().siparisDurumGuncellemee(siparis_durum,durumTarihi,siparis_id, odemeTuru);
        return req;
    }

    public Call<Result> aracStokEkle(AracStok aracStok)
    {
        Call<Result> str = getRestApi().aracStokEkle(aracStok);

        return str;
    }
    public Call<List<SiparisGet>> aracaAitSiparisler(int siparis_arac_id,String baslangic_tarihi, String bitis_tarihi, int selectedDurumId)
    {
        Call<List<SiparisGet>> req = getRestApi().aracaAitSiparisler(siparis_arac_id,baslangic_tarihi,bitis_tarihi,selectedDurumId);
        return req;
    }

    public Call<Result> tokensUpdate(int personel_id,String token){
        Call<Result> rew = getRestApi().tokensUpdate(personel_id,token);
        return rew;
    }

    public Call<List<AracStok>> getAracStokListe(int arac_id)
    {
        Call<List<AracStok>> req = getRestApi().getAracStokListe(arac_id);
        return req;
    }

    public Call<List<StokBilgiShowList>> getAracStokGunlukListe(int stok_id)
    {
        Call<List<StokBilgiShowList>> req = getRestApi().getAracStokGunlukListe(stok_id);
        return req;
    }

    public Call<List<StokBilgiShowList>> getAracStokToplam(int arac_id,int urun_id,int alturun_id)
    {
        Call<List<StokBilgiShowList>> req = getRestApi().getAracStokToplam(arac_id,urun_id,alturun_id);
        return req;
    }

    public Call<List<Cagrilar>> getGelenCagriAnlik()
    {
        Call<List<Cagrilar>> req = getRestApi().getGelenCagriAnlik();
        return req;
    }

    public Call<List<AracStok>> getAracStokTarihListe(int arac_id,String baslangic_tarih,String bitis_tarih)
    {
        Call<List<AracStok>> req = getRestApi().getAracStokTarihListe(arac_id,baslangic_tarih,bitis_tarih);
        return req;
    }
    public Call<List<SiparisGet>> getMusteriyeAitSiparisler(int musteri_id)
    {
        Call<List<SiparisGet>> req = getRestApi().getMusteriyeAitSiparisler(musteri_id);
        return req;
    }



    public Call<List<ToplamSatilanUrun>> toplamSatilanUrun(int siparisDurum,String baslangic_tarihi,String bitis_tarihi)
    {
        Call<List<ToplamSatilanUrun>> req = getRestApi().toplamSatilanUrun(siparisDurum,baslangic_tarihi,bitis_tarihi);
        return req;
    }

    public Call<List<ToplamOdemeTuru>> gettoplamOdemeTuru(String baslangic_tarihi,String bitis_tarihi)
    {
        Call<List<ToplamOdemeTuru>> req = getRestApi().gettoplamOdemeTuru(baslangic_tarihi,bitis_tarihi);
        return req;
    }

    public Call<List<ToplamAylikSiparis>> getToplamAylikSiparis(String yil)
    {
        Call<List<ToplamAylikSiparis>> req = getRestApi().getToplamAylikSiparis(yil);
        return req;
    }

    public Call<List<ToplamMusteriSiparis>> getToplamMusteriSiparisi(int sipariadeti,String baslangic_tarihi, String bitis_tarihi)
    {
        Call<List<ToplamMusteriSiparis>> req = getRestApi().getToplamMusteriSiparisi(sipariadeti,baslangic_tarihi,bitis_tarihi);
        return req;
    }

    public Call<List<Musteri>> getNotMusteriiProduct(String baslangic_tarihi, String bitis_tarihi) {
        Call<List<Musteri>> req = getRestApi().getNotMusteriiProduct(baslangic_tarihi,bitis_tarihi);
        return req;
    }

}

