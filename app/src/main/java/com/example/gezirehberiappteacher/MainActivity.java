package com.example.gezirehberiappteacher;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import com.example.gezirehberiappteacher.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding activityMainBinding;
    Map<String,Object> mapData; //Çekilecek veriler bu Map'te tutulur.
    FirebaseFirestore FirebaseFirestore;
    FirebaseStorage FirebaseStorage;
    ArrayList<GeziRehberBilgileri> yerler;
    int dilSecimi=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = activityMainBinding.getRoot();
        setContentView(view);

        yerler = new ArrayList<>();
        FirebaseFirestore = FirebaseFirestore.getInstance();
        FirebaseStorage = FirebaseStorage.getInstance();
        Intent intent = getIntent();
        dilSecimi=intent.getIntExtra("dilSecimi",0);
        verileriAlTr(); //Verilerin veri tabanından getirileceği metottur.
    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.gezi_menu,menu);
        if(dilSecimi==0){
            menu.getItem(3).setTitle("English");
        }else{
            menu.getItem(3).setTitle("Türkçe");
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.girisYap){
        }
        else if(item.getItemId()==R.id.geziEkle){
            Intent intent = new Intent(MainActivity.this,KayitActivity.class);
            startActivity(intent);
        }
        else if(item.getItemId()==R.id.geziSil){
        }
        else if(item.getItemId()==R.id.dilSecimi){
            if(dilSecimi==0){
                dilSecimi=1;
                item.setTitle("Türkçe");
                verileriAlTr();
            }else{
                dilSecimi=0;
                item.setTitle("English");
                verileriAlTr();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    //--------------
    public void verileriAlTr(){
        yerler.clear();
        FirebaseFirestore.collection("YerKayit").addSnapshotListener(new EventListener<QuerySnapshot>()
        {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error != null){
                    Toast.makeText(getApplicationContext(),error.getLocalizedMessage(),Toast.LENGTH_LONG).
                            show();
                }
                if(value!=null){
                    for(DocumentSnapshot snapshot:value.getDocuments()){
                        mapData = snapshot.getData();
                        //Firebase içindeki alan adları ile veriler çekilir.
                        String yerAdi = (String) mapData.get("adi");
                        String ulkeAdi = (String) mapData.get("ulkesi");
                        String sehirAdi = (String) mapData.get("sehir");
                        String tarihce = (String) mapData.get("tarihce");
                        String hakkinda = (String) mapData.get("hakkinda");
                        String placeName = (String) mapData.get("placeName");
                        String countryName = (String) mapData.get("countryName");
                        String cityName = (String) mapData.get("cityName");
                        String history = (String) mapData.get("historyInfo");
                        String about = (String) mapData.get("aboutInfo");
                        String imageURL = (String) mapData.get("gorselURL");
                        GeziRehberBilgileri geziRehberBilgileri = new GeziRehberBilgileri(imageURL,yerAdi,ulkeAdi,sehirAdi,tarihce,hakkinda,placeName,countryName,cityName,history,about);
                        yerler.add(geziRehberBilgileri);
                    }
                    if(dilSecimi==0){
                        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, yerler.stream().map(yerlerTr-> yerlerTr.yerAdi).collect(Collectors.toList()));
                        //Burada yerler içindeki verilerden sadece yerAdi bilgisinin ekranda gösterilmesi istenir.
                        activityMainBinding.listViewYerler.setAdapter(arrayAdapter);
                    }else{
                        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, yerler.stream().map(yerlerTr-> yerlerTr.placeName).collect(Collectors.toList()));
                        //Burada yerler içindeki verilerden sadece placeName bilgisinin ekranda gösterilmesi istenir.
                        activityMainBinding.listViewYerler.setAdapter(arrayAdapter);
                    }
                    //ListViewdeki yerlerden birine tıklandığında ne yapılacağının yazıldığı bölümdür.
                    activityMainBinding.listViewYerler.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            finish();
                            Intent intentToDetail = new Intent(MainActivity.this,GeziRehberiActivity.class);
                            //GeziRehberBilgileri’nden üretilen yerlerTr isimli ArrayList, Serializable olduğu için paket şeklinde DetayActivity’sine gönderilir.
                            intentToDetail.putExtra("geziYerleriDetay",yerler.get(i));
                            intentToDetail.putExtra("dilSecimi",dilSecimi);//dilSecimi değişkeni 0 ise Türkçe, 1 ise İngilizce açılır.
                            startActivity(intentToDetail);
                        }
                    });
                }
            }
        });
    }
    //--------------
}