package com.artirex.sutakip.Model;

import java.util.List;

public class SiparisGet {

	private int siparis_id;
	private boolean tf;
	private String musteri_adi;
	private String musteri_soyadi;
	private String musteri_tel1;
	private String musteri_adres1;
	private String arac_plaka;
	private String siparis_eklenmeTarihi;
	private int siparis_durum;
	private String siparis_bilgi;
	private String lat;
	private String lng;
	private int siparisAdeti;
	private List<StokBilgiGet> siparisler;


	public int getSiparis_id() {
		return siparis_id;
	}

	public void setSiparis_id(int siparis_id) {
		this.siparis_id = siparis_id;
	}


	public boolean isTf() {
		return tf;
	}

	public void setTf(boolean tf) {
		this.tf = tf;
	}

	public String getMusteri_adi() {
		return musteri_adi;
	}

	public void setMusteri_adi(String musteri_adi) {
		this.musteri_adi = musteri_adi;
	}

	public String getMusteri_soyadi() {
		return musteri_soyadi;
	}

	public void setMusteri_soyadi(String musteri_soyadi) {
		this.musteri_soyadi = musteri_soyadi;
	}

	public String getMusteri_tel1() {
		return musteri_tel1;
	}

	public void setMusteri_tel1(String musteri_tel1) {
		this.musteri_tel1 = musteri_tel1;
	}

	public String getMusteri_adres1() {
		return musteri_adres1;
	}

	public void setMusteri_adres1(String musteri_adres1) {
		this.musteri_adres1 = musteri_adres1;
	}

	public String getArac_plaka() {
		return arac_plaka;
	}

	public void setArac_plaka(String arac_plaka) {
		this.arac_plaka = arac_plaka;
	}

	public String getSiparis_eklenmeTarihi() {
		return siparis_eklenmeTarihi;
	}

	public void setSiparis_eklenmeTarihi(String siparis_eklenmeTarihi) {
		this.siparis_eklenmeTarihi = siparis_eklenmeTarihi;
	}

	public int getSiparis_durum() {
		return siparis_durum;
	}

	public void setSiparis_durum(int siparis_durum) {
		this.siparis_durum = siparis_durum;
	}

	public String getSiparis_bilgi() {
		return siparis_bilgi;
	}

	public void setSiparis_bilgi(String siparis_bilgi) {
		this.siparis_bilgi = siparis_bilgi;
	}


	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLng() {
		return lng;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}

	public List<StokBilgiGet> getSiparis() {
		return siparisler;
	}

	public void setSiparis(List<StokBilgiGet> siparisList) {
		this.siparisler = siparisList;
	}

	public int getSiparisAdeti() {
		return siparisAdeti;
	}

	public void setSiparisAdeti(int siparisAdeti) {
		this.siparisAdeti = siparisAdeti;
	}

	public List<StokBilgiGet> getSiparisler() {
		return siparisler;
	}

	public void setSiparisler(List<StokBilgiGet> siparisler) {
		this.siparisler = siparisler;
	}

	@Override
	public String toString() {
		return "SiparisGet{" +
				"siparis_id=" + siparis_id +
				", tf=" + tf +
				", musteri_adi='" + musteri_adi + '\'' +
				", musteri_soyadi='" + musteri_soyadi + '\'' +
				", musteri_tel1='" + musteri_tel1 + '\'' +
				", musteri_adres1='" + musteri_adres1 + '\'' +
				", arac_plaka='" + arac_plaka + '\'' +
				", siparis_eklenmeTarihi='" + siparis_eklenmeTarihi + '\'' +
				", siparis_durum=" + siparis_durum +
				", siparis_bilgi='" + siparis_bilgi + '\'' +
				", lat='" + lat + '\'' +
				", lng='" + lng + '\'' +
				", siparisList=" + siparisler +
				'}';
	}
}
