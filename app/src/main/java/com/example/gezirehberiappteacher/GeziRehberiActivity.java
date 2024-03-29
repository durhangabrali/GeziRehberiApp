package com.example.gezirehberiappteacher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.gezirehberiappteacher.databinding.ActivityGeziRehberiBinding;
import com.squareup.picasso.Picasso;

public class GeziRehberiActivity extends AppCompatActivity {
    private ActivityGeziRehberiBinding activityGeziRehberiBinding;
    int dilSecimi;
    GeziRehberBilgileri geziRehberBilgileri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityGeziRehberiBinding = ActivityGeziRehberiBinding.inflate(getLayoutInflater());
        View view = activityGeziRehberiBinding.getRoot();
        setContentView(view);
        Intent intent = getIntent();
        geziRehberBilgileri = (GeziRehberBilgileri) intent.getSerializableExtra("geziYerleriDetay");
        dilSecimi = intent.getIntExtra("dilSecimi",0);
        verileriAl();
    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.dilsecimi_menu,menu);
        if(dilSecimi==0){
            menu.getItem(0).setTitle("English");
        }else{
            menu.getItem(0).setTitle("Türkçe");
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.geri){
            finish();
            Intent intent = new Intent(GeziRehberiActivity.this,MainActivity.class);
            intent.putExtra("dilSecimi",dilSecimi);
            startActivity(intent);
        }else if(item.getItemId()==R.id.secim){
            if(dilSecimi==0){
                dilSecimi=1;
                item.setTitle("Türkçe");
                verileriAl();
            }else{
                dilSecimi=0;
                item.setTitle("English");
                verileriAl();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    //---------
    public void verileriAl(){
        Picasso.get().load(geziRehberBilgileri.imageID).into(activityGeziRehberiBinding.imageViewGorsel);
        if(dilSecimi==0){
            activityGeziRehberiBinding.textViewYerBilgisi.setText(geziRehberBilgileri.yerAdi);
            activityGeziRehberiBinding.textViewUlkeBilgi.setText(geziRehberBilgileri.ulkeAdi);
            activityGeziRehberiBinding.textViewSehirBilgi.setText(geziRehberBilgileri.sehirAdi);
            activityGeziRehberiBinding.textViewTarihce.setText(geziRehberBilgileri.tarihce);
            activityGeziRehberiBinding.textViewHakkinda.setText(geziRehberBilgileri.hakkinda);
        }else{
            activityGeziRehberiBinding.textViewYerBilgisi.setText(geziRehberBilgileri.placeName);
            activityGeziRehberiBinding.textViewUlkeBilgi.setText(geziRehberBilgileri.countryName);
            activityGeziRehberiBinding.textViewSehirBilgi.setText(geziRehberBilgileri.cityName);
            activityGeziRehberiBinding.textViewTarihce.setText(geziRehberBilgileri.history);
            activityGeziRehberiBinding.textViewHakkinda.setText(geziRehberBilgileri.about);
        }
    }
    //---------

}