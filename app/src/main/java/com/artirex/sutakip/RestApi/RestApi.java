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
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RestApi {

    @POST("login.php")
    @FormUrlEncoded
     Call<Login> girisYap(@Field("telefonno") String telefonNo, @Field("parola")String parola,@Field("token")String signaltoken);

    @POST("userInsert.php")
    @FormUrlEncoded
     Call<Result> userInsert(@Field("adi")String adi,@Field("soyadi")String soyadi,@Field("telefon")String telefon,
                                   @Field("bilgi")String bilgi,@Field("yetki")int yetki,@Field("durum")int durum,
                                   @Field("cinsiyet")int cinsiyet,@Field("parola")String parola,@Field("ekleyenId")int ekleyenId);

    @POST("userUpdate.php")
    @FormUrlEncoded
    Call<Result> userUpdate(@Field("personel_id")int personel_id,@Field("adi")String adi,@Field("soyadi")String soyadi,@Field("telefon")String telefon,
                            @Field("bilgi")String bilgi,@Field("yetki")int yetki,@Field("durum")int durum,
                            @Field("cinsiyet")int cinsiyet,@Field("parola")String parola,@Field("ekleyenId")int ekleyenId);

    @POST("carInsert.php")
    @FormUrlEncoded
    Call<Result> carInsert(@Field("plaka") String plaka,@Field("telefon")String telefon,@Field("model")int model,
                           @Field("bilgi")String bilgi,@Field("ekleyenId")int ekleyenId);

    @POST("carUpdate.php")
    @FormUrlEncoded
    Call<Result> carUpdate(@Field("arac_id")int arac_id,@Field("plaka") String plaka,@Field("telefon")String telefon,@Field("model")int model,
                           @Field("bilgi")String bilgi,@Field("ekleyenId")int ekleyenId);
    @GET("araclar.php")
    Call<List<AracGet>> getCarList();

    @GET("customers.php")
    Call<List<Musteri>> getCustomersList();

    @POST("customerInsert.php")
    @FormUrlEncoded
    Call<Result> customerInsert(@Field("musteri_adi")String musteri_adi,@Field("musteri_soyadi")String musteri_soyadi,
                                @Field("musteri_tel1")String musteri_tel1,@Field("musteri_tel2")String musteri_tel2,
                                @Field("musteri_tel3")String musteri_tel3,@Field("musteri_adres1")String musteri_adres1,
                                @Field("musteri_adres2")String musteri_adres2,@Field("musteri_firma_id")int musteri_firma_id,
                                @Field("musteri_bilgi")String musteri_bilgi, @Field("musteri_cinsiyet")int musteri_cinsiyet,
                                @Field("ekleyenId")int ekleyenId,@Field("late")String late,@Field("lng")String lng);
    @POST("customerupdate.php")
    @FormUrlEncoded
    Call<Result> customerUpdate(@Field("musteri_id")int musteri_id,@Field("musteri_adi")String musteri_adi,
                                @Field("musteri_soyadi")String musteri_soyadi,
                                @Field("musteri_tel1")String musteri_tel1,@Field("musteri_tel2")String musteri_tel2,
                                @Field("musteri_tel3")String musteri_tel3,@Field("musteri_adres1")String musteri_adres1,
                                @Field("musteri_adres2")String musteri_adres2,@Field("musteri_firma_id")int musteri_firma_id,
                                @Field("musteri_bilgi")String musteri_bilgi, @Field("musteri_cinsiyet")int musteri_cinsiyet,
                                @Field("ekleyenId")int ekleyenId);


    @POST("productInsert.php")
    @FormUrlEncoded
    Call<Result> productInsert(@Field("urun_adi") String urun_adi,@Field("urun_tip")String urun_tip,
                               @Field("urun_boyut")String urun_boyut,@Field("urun_stok")int urun_stok,
                               @Field("urun_bilgi")String urun_bilgi, @Field("ekleyenId")int ekleyenId);

    @POST("productUpdate.php")
    @FormUrlEncoded
    Call<Result> productUpdate(@Field("urun_id")int urun_id,@Field("alturun_id")int alturun_id ,
                               @Field("urun_adi") String urun_adi,
                               @Field("urun_tip")String urun_tip, @Field("urun_boyut")String urun_boyut,
                               @Field("urun_stok")int urun_stok,@Field("urun_bilgi")String urun_bilgi,
                               @Field("ekleyenId")int ekleyenId);

    @GET("urunler.php")
    Call<List<Urun>> getProductList();

  /*
    @POST("orderInsert.php")

    @FormUrlEncoded
    Call<Result> orderInsert(@Field("siparis_urun_tipi") int siparis_urun_tipi,@Field("siparis_urun_boyutu")int siparis_urun_boyutu,
                               @Field("siparis_bilgi")String siparis_bilgi, @Field("siparis_musteri_id")int siparis_musteri_id,
                               @Field("siparis_arac_id")int siparis_arac_id, @Field("siparis_durum")int siparis_durum,
                             @Field("ekleyenId")int ekleyenId);
    */
  @POST("orderInsert.php")
  Call<Result> orderInsert(@Body Siparis siparis);


    @GET("alturunler.php")
    Call<List<AltUrun>> getSubProductList(@Query("urun_id")int urun_id);


    @POST("aracmodelEkle.php")
    @FormUrlEncoded
    Call<Result> carModelInsert(@Field("model_adi") String model_adi, @Field("model_bilgi")String model_bilgi,@Field("ekleyenId")int ekleyenId);


    @GET("aracmodeller.php")
    Call<List<AracModel>> getCarModelList();

    @POST("firmaEkle.php")
    @FormUrlEncoded
    Call<Result> firmaInsert(@Field("firma_adi") String firma_adi, @Field("firma_bilgi")String firma_bilgi,
                             @Field("firma_tel1")String firma_tel1,@Field("ekleyenId")int ekleyenId);


    @GET("firmalar.php")
    Call<List<Firma>> getFirmaList();



    @GET("siparisler.php")
    Call<List<SiparisGet>> getOrdersList(@Query("durumCode") String durumCode);

    @GET("siparislerOdeme.php")
    Call<List<SiparisGet>> getOrdersListPayment(@Query("odeme_turu") int odeme_turu,@Query("tarih_araligi")int tarih_araligi,@Query("baslangictarihi")String baslangictarihi,@Query("bitistarihi")String bitistarihi);

    @GET("users.php")
    Call<List<Personel>> getPersonsList();


    @GET("user.php")
    Call<Personel> getUser(@Query("user_id")int user_id);

    @GET("customer.php")
    Call<Musteri> getCustomer(@Query("musteri_id")int musteri_id);

    @GET("whoCustomer.php")
    Call<Musteri> getMusteriKim(@Query("telefon")String telefon);

    @GET("siparis.php")
    Call<SiparisGet> getOrder(@Query("siparis_id")int siparis_id);


    @GET("product.php")
    Call<Urun> getProduct(@Query("urun_id")int urun_id,@Query("alturun_id")int alturun_id);

    @GET("arac.php")
    Call<AracGet> getCar(@Query("arac_id")int arac_id);


    @GET("personelAracAta.php")
    Call<Result> personelAracAta(@Query("personel_id")int personel_id,@Query("arac_id")int arac_id,
                                 @Query("ekleyenId")int ekleyenId);

    /*
    @GET("onesignalTokenEkle.php")
    Call<Result> onesignalToken(@Query("personel_id")int personel_id,@Query("onesignalToken")String onesignalToken);

    @GET("userTokenUpdate.php")
    Call<Result> useroneSignalToken(@Query("personel_id")int personel_id,@Query("onesignalToken")String onesignalToken);
    */
    @GET("soforsiparis.php")
    Call<List<SiparisGet>> getSoforSiparis(@Query("siparis_arac_id") int siparis_arac_id,@Query("siparis_durum")int siparis_durum);

    @GET("menuler.php")
    Call<List<Menu>> getMenus(@Query("yetki") int yetki);

    @POST("siparisDurumUpdate.php")
    @FormUrlEncoded
    Call<Result> siparisDurumGuncellemee(@Field("siparis_durum")int siparis_durum,@Field("durumTarihi")String durumTarihi,
                                         @Field("siparis_id")int siparis_id,@Field("odemeTuru")int odemeTuru);


    @GET("aracaAitSiparisler.php")
    Call<List<SiparisGet>> aracaAitSiparisler(@Query("siparis_arac_id") int siparis_arac_id,@Query("baslangic_tarihi")String baslangic_tarihi,
                                              @Query("bitis_tarihi")String bitis_tarihi,@Query("siparis_durum") int selectedDurumId);

    @POST("aracStokEkle.php")
    //@FormUrlEncoded
    Call<Result> aracStokEkle(@Body AracStok aracStok);


    @GET("tokenEkleGunc.php")
    Call<Result> tokensUpdate(@Query("personel_id")int personel_id,@Query("token")String token);

    @GET("aracStokListe.php")
    Call<List<AracStok>> getAracStokListe(@Query("arac_id")int arac_id);

    @GET("aracStokGunlukListe.php")
    Call<List<StokBilgiShowList>> getAracStokGunlukListe(@Query("stok_id")int stok_id);

    @GET("aracStokToplam.php")
    Call<List<StokBilgiShowList>> getAracStokToplam(@Query("arac_id")int arac_id,@Query("urun_id")int urun_id,@Query("alturun_id")int alturun_id);

    @GET("santralCagriTrafigiDinleme.php")
    Call<List<Cagrilar>> getGelenCagriAnlik();

    @GET("aracStokTarihListe.php")
    Call<List<AracStok>> getAracStokTarihListe(@Query("arac_id")int arac_id,@Query("baslangic")String baslangic_tarih,@Query("bitis")String bitis_tarih);

    @GET("musteriSiparisler.php")
    Call<List<SiparisGet>> getMusteriyeAitSiparisler(@Query("musteri_id") int musteri_id);

    @GET("toplamSatilanUrun.php")
    Call<List<ToplamSatilanUrun>> toplamSatilanUrun(@Query("siparisDurum") int siparisDurum,@Query("baslangic_tarihi")String baslangic_tarihi,@Query("bitis_tarihi") String bitis_tarihi);

    @GET("toplamOdemeturu.php")
    Call<List<ToplamOdemeTuru>> gettoplamOdemeTuru(@Query("baslangic_tarihi")String baslangic_tarihi,@Query("bitis_tarihi") String bitis_tarihi);

    @GET("toplamAylikSiparisSayisi.php")
    Call<List<ToplamAylikSiparis>> getToplamAylikSiparis(@Query("yil")String yil);

    //daha yapılmadı
    @GET("toplamMusteriSiparis.php")
    Call<List<ToplamMusteriSiparis>> getToplamMusteriSiparisi(@Query("siparis_adeti")int stokadeti,@Query("baslangic_tarihi")String baslangic_tarihi, @Query("bitis_tarihi") String bitis_tarihi);

    @GET("customerNotProduct.php")
    Call<List<Musteri>> getNotMusteriiProduct(@Query("baslangic_tarihi")String baslangic_tarihi,@Query("bitis_tarihi") String bitis_tarihi);





}
