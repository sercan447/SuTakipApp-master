package com.artirex.sutakip.Model;

public class Login{
	private String hatadurumu;
	private boolean tf;
	private String mesaj;
	private int personel_id;
	private int yetki;
	private int arac_id;
	private String token;

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

	public int getPersonel_id() {
		return personel_id;
	}

	public void setPersonel_id(int personel_id) {
		this.personel_id = personel_id;
	}

	public void setYetki(int yetki){
		this.yetki = yetki;
	}

	public int getYetki(){
		return yetki;
	}

	public void setToken(String token){
		this.token = token;
	}

	public String getToken(){
		return token;
	}


	public int getArac_id() {
		return arac_id;
	}

	public void setArac_id(int arac_id) {
		this.arac_id = arac_id;
	}

	@Override
	public String toString() {
		return "Login{" +
				"hatadurumu='" + hatadurumu + '\'' +
				", tf=" + tf +
				", mesaj='" + mesaj + '\'' +
				", personel_id=" + personel_id +
				", yetki=" + yetki +
				", arac_id=" + arac_id +
				", token='" + token + '\'' +
				'}';
	}
}
