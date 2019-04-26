package com.artirex.sutakip.Model;

public class Result{
	private String hatadurumu;
	private boolean tf;
	private String mesaj;
	private String token;
	private int kullaniciId;
public Result(){

}
	public void setHatadurumu(String hatadurumu){
		this.hatadurumu = hatadurumu;
	}

	public String getHatadurumu(){
		return hatadurumu;
	}

	public void setTf(boolean tf){
		this.tf = tf;
	}

	public boolean isTf(){
		return tf;
	}

	public void setMesaj(String mesaj){
		this.mesaj = mesaj;
	}

	public String getMesaj(){
		return mesaj;
	}

	public void setKullaniciId(int kullaniciId){
		this.kullaniciId = kullaniciId;
	}

	public int getKullaniciId(){
		return kullaniciId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public String toString() {
		return "Result{" +
				"hatadurumu='" + hatadurumu + '\'' +
				", tf=" + tf +
				", mesaj='" + mesaj + '\'' +
				", token='" + token + '\'' +
				", kullaniciId=" + kullaniciId +
				'}';
	}
}
