package com.artirex.sutakip;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import com.artirex.sutakip.Helper.InternetManager;
import com.artirex.sutakip.Helper.Message;
import com.artirex.sutakip.Helper.SharedPreferenceManager;
import com.artirex.sutakip.Helper.TasarimGorunum;
import com.artirex.sutakip.Model.Login;
import com.artirex.sutakip.Model.Personel;
import com.artirex.sutakip.Model.Result;
import com.artirex.sutakip.RestApi.ManagerALL;
import com.onesignal.OneSignal;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

   private ConstraintLayout constrainlayout_login;
   private TextInputLayout til_telefonNo_login,til_parola_login;
    private TextInputEditText ed_telefonNo_login,ed_parola_login;
    private CheckBox chk_beniHatirla_login;
    private Button btn_girisYap_login;
    private ProgressDialog progressDialog;

    InternetManager internetManager;
    TelephonyManager tgMr;

    String onesignalToken="";
    String number="";

    String[] izinler = {Manifest.permission.READ_PHONE_STATE,Manifest.permission.READ_SMS,Manifest.permission.READ_PHONE_NUMBERS};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Ekran boyutlandırmaları burada ayarlanıyor.
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        Initialize();

        int kullaniciId = SharedPreferenceManager.getSharedPrefere(getApplicationContext()).getInt("kullaniciId",-1);
        boolean benihatirla =SharedPreferenceManager.getSharedPrefere(getApplicationContext()).getBoolean("beniHatirla",false);

        if(benihatirla == true && kullaniciId != -1)
        {
            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }else{

        }

        OneSignal.idsAvailable(new OneSignal.IdsAvailableHandler() {
            @Override
            public void idsAvailable(String userId, String registrationId) {
                onesignalToken = userId;
                Log.e("ASLI token",userId);
            }
        });

        ButonClick();
    }

    //Componentlerinin oluşturulma işlemi yapılıyor.
    private void Initialize()
    {
        constrainlayout_login = findViewById(R.id.constrainlayout_login);
        til_telefonNo_login = findViewById(R.id.til_telefonNo_login);
        til_parola_login = findViewById(R.id.til_parola_login);
        ed_telefonNo_login = findViewById(R.id.ed_telefonNo_login);
        ed_parola_login = findViewById(R.id.ed_parola_login);
        chk_beniHatirla_login = findViewById(R.id.chk_beniHatirla_login);
        btn_girisYap_login = findViewById(R.id.btn_girisYap_login);

        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setTitle("Sisteme Giriş Sağlanıyor..");
        progressDialog.setMessage("işlem yapılırken lütfen bekleyiniz..");
        progressDialog.setCancelable(false);

        internetManager = new InternetManager(getApplicationContext());
        tgMr = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);



            int izinKontrol = ContextCompat.checkSelfPermission(getApplicationContext(),izinler[0]);
            if(izinKontrol != PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions(this,new String[]{izinler[0]},110);
            }
        int izinKontrol1 = ContextCompat.checkSelfPermission(getApplicationContext(),izinler[1]);
        if(izinKontrol != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,new String[]{izinler[1]},120);
        }
        int izinKontrol2 = ContextCompat.checkSelfPermission(getApplicationContext(),izinler[2]);
        if(izinKontrol != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,new String[]{izinler[2]},130);
        }


        //sistemden telefon numarasını cekme işlemi hatalı kapatıyorum oyuzden
       // number = ""+tgMr.getLine1Number();
       // ed_telefonNo_login.setText(!number.equals("") ? number : "000");

    }//func

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == 110)
        {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                }
        }
        if(requestCode == 120)
        {
            if (grantResults.length > 0 && grantResults[1] == PackageManager.PERMISSION_GRANTED) {

            }
        }
        if(requestCode == 130)
        {
            if (grantResults.length > 0 && grantResults[1] == PackageManager.PERMISSION_GRANTED) {

            }
        }
    }

    //Componentlerin validate işlemleri yapılıyor.
    private void LoginValidation()
    {
        ed_parola_login.setError(null);
        ed_telefonNo_login.setError(null);

        boolean durumTelefonNo = TextUtils.isEmpty(ed_telefonNo_login.getText());
        boolean durumParola = TextUtils.isEmpty(ed_parola_login.getText());

        if(durumTelefonNo || durumParola) {
            if (durumTelefonNo)
                ed_telefonNo_login.setError("Telefon No boş.!");
            if (durumParola)
                ed_parola_login.setError("Parola alanı boş.!");
        }else
        {

            Login(ed_telefonNo_login.getText().toString(),ed_parola_login.getText().toString(),onesignalToken);
        }
    }//func

    //Giris butonun tıklanma olayı burada gerçekleşiyor.
    private void ButonClick()
    {
        btn_girisYap_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Internet baglantısı var ise Login Süreçi için Component denetimleri yapılıyor.
                if(internetManager.InternetIsConnected())
                {

                    LoginValidation();
                }else
                {
                    Snackbar.make(constrainlayout_login, Message.NO_INTERNET_MESSAGE,Snackbar.LENGTH_LONG).show();
                }
            }
        });//click

    }//FUNC


    //Login için web servise istek yollanıyorş
    private void Login(String telefonNo,String parola,String signaltoken)
    {
        progressDialog.show();

        Call<Login> request = ManagerALL.getInstance().girisYap(telefonNo,parola,signaltoken);
        request.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {

                if (response.isSuccessful())
                {
                    Login login = response.body();

                    if(response.body().isTf())
                    {
                        if(login.getToken().equals("4809"))
                        {
                            //sisteme ilk defa giriyorsa cihazın token güncellemesi burada yapılıyor
                            tokensUpdate(login.getPersonel_id(),onesignalToken);
                        }else if(!login.getToken().equals(onesignalToken))
                        {
                            //cihaz değiştirdiği için token güncellemesi yapmamız gerekiyor.
                            //çünkü ONESIGNAL kullnarak bildirim alabilmesi için bu gerekli
                            tokensUpdate(login.getPersonel_id(),onesignalToken);

                        }

                        Log.e("LOGIN GIRIS BASARI",login.toString());

                        SharedPreferences.Editor editor = SharedPreferenceManager.setSharedEdit(getApplicationContext());
                        editor.putInt("kullaniciId",login.getPersonel_id());
                        editor.putInt("aracId",login.getArac_id());

                        //token shared içerisinde yok ise kaydediyorum
                        if(!SharedPreferenceManager.getSharedPrefere(getApplicationContext()).getString("OneSignaltoken","").equals(onesignalToken)) {
                            editor.putString("OneSignaltoken", onesignalToken);
                        }
                        if (chk_beniHatirla_login.isChecked())
                            editor.putBoolean("beniHatirla",true);
                        else
                            editor.putBoolean("beniHatirla",false);

                        editor.putInt("yetki",login.getYetki());
                        editor.commit();


                        progressDialog.dismiss();
                        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                        startActivity(intent);
                    }else{
                        progressDialog.dismiss();
                        Log.e("false durummu : .",response.toString());
                        Log.e("false durummu 2: .",response.body().toString());
                        Snackbar.make(constrainlayout_login,login.getMesaj() ,Snackbar.LENGTH_LONG).show();
                    }
                }else
                {
                    progressDialog.dismiss();
                    Log.e("Success olmadı. ",response.toString());
                    Log.e("Success durummu 2: .",response.body().toString());
                }
            }
            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                progressDialog.dismiss();
                Snackbar.make(constrainlayout_login, Message.SYSTEM_PROBLEM,Snackbar.LENGTH_LONG).show();
                Log.e("LOGIN GIRIS HATA",t.getLocalizedMessage());
            }
        });
    }//Login

    private void tokensUpdate(int personel_id,String token)
    {
        Call<Result> req = ManagerALL.getInstance().tokensUpdate(personel_id,token);
        req.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {

                if(response.isSuccessful())
                    Log.e("AR_PER_ATA token B","basarılı");
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Log.e("AR_PER_ATA token HTA","HATA");
            }
        });
    }

/*
        //arac_personel_ata tablosuna kayıt yapılıyor.
    private void OnesignalTokenKInsert(int personel_id,String onesignalToken)
    {
        Call<Result> req = ManagerALL.getInstance().onesignalToken(personel_id,onesignalToken);
        req.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {

                if(response.isSuccessful())
                    Log.e("AR_PER_ATA token B","basarılı");
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Log.e("AR_PER_ATA token HTA","HATA");
            }
        });
    }

    //USER TOKEN BILGI GUNCELLEME
    private void UserOneSignalInsert(int personel_id,String token)
    {
        Call<Result> req = ManagerALL.getInstance().useroneSignalToken(personel_id,token);
        req.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if(response.isSuccessful())
                    Log.e("USER UPDATE token B","basarılı");
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {

                    Log.e("USER UPDATE token",t.getLocalizedMessage());
            }
        });
    }
    */
}
