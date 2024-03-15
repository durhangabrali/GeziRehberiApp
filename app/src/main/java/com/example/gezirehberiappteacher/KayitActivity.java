package com.example.gezirehberiappteacher;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class KayitActivity extends AppCompatActivity {
    public static String yerAdiBilgi,ulkeAdiBilgi,sehirAdiBilgi,tarihceBilgi,hakkindaBilgi; //Tr bilgileri tutulur.
    public static String nameInfo, countryInfo, cityInfo, historyInfo, aboutInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kayit);
    }
}