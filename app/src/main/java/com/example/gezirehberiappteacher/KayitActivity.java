package com.example.gezirehberiappteacher;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.gezirehberiappteacher.databinding.ActivityKayitBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.UUID;

import FragmentTutucu.EngBilgiFragment;
import FragmentTutucu.FotoKayitFragment;
import FragmentTutucu.TrBilgiFragment;

public class KayitActivity extends AppCompatActivity {
    FirebaseStorage FirebaseStorage;
    FirebaseFirestore FirebaseFirestore;
    StorageReference storageReference;
    private ActivityKayitBinding kayitBinding;
    private int pageNumber;
    Uri fotoBilgi; //Fotografın Uri adresi tutulur.

    public static String yerAdiBilgi, ulkeAdiBilgi, sehirAdiBilgi, tarihceBilgi, hakkindaBilgi; //Tr bilgileri tutulur.
    public static String nameInfo, countryInfo, cityInfo, historyInfo, aboutInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Değişkenlerde veri içerik temizlemesi yapılır
        fotoBilgi = Uri.EMPTY;

        yerAdiBilgi = "";
        ulkeAdiBilgi = "";
        sehirAdiBilgi = "";
        tarihceBilgi = "";
        hakkindaBilgi = "";

        nameInfo = "";
        countryInfo = "";
        cityInfo = "";
        historyInfo = "";
        aboutInfo = "";

        FirebaseStorage = FirebaseStorage.getInstance();
        FirebaseFirestore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getReference();
        kayitBinding = ActivityKayitBinding.inflate(getLayoutInflater());
        View view = kayitBinding.getRoot();
        setContentView(view);
        //setContentView(R.layout.activity_kayit);
        pageNumber = 0;

        //Fotoğraf seçimi için ilk fragment çağrılır.
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        FotoKayitFragment fotoKayitFragment = new FotoKayitFragment();
        fragmentTransaction.replace(R.id.constraintLayout_Sayfalar,fotoKayitFragment).commit();

        //İleri butonunun yapacağı işler bir listener ile dinlernir.
        kayitBinding.buttonIleri1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Buraya kendi kodlarımızı yazıyoruz
                if(pageNumber == 0){
                    fotoBilgi = FotoKayitFragment.imageData;
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    TrBilgiFragment trBilgiFragment = new TrBilgiFragment();
                    fragmentTransaction.replace(R.id.constraintLayout_Sayfalar,trBilgiFragment).commit();
                    kayitBinding.buttonIleri1.setText("İleri");
                }
                else if (pageNumber == 1) {
                    yerAdiBilgi = TrBilgiFragment.yerAdi.getText().toString();
                    ulkeAdiBilgi = TrBilgiFragment.ulkeAdi.getText().toString();
                    sehirAdiBilgi = TrBilgiFragment.sehirAdi.getText().toString();
                    tarihceBilgi = TrBilgiFragment.tarihce.getText().toString();
                    hakkindaBilgi = TrBilgiFragment.hakkindaGiris.getText().toString();
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    EngBilgiFragment engBilgiFragment = new EngBilgiFragment();
                    fragmentTransaction.replace(R.id.constraintLayout_Sayfalar,engBilgiFragment).commit();
                    kayitBinding.buttonIleri1.setText("Kaydet");
                }
                else if (pageNumber == 2) {
                    nameInfo = EngBilgiFragment.placeName.getText().toString();
                    countryInfo = EngBilgiFragment.countryName.getText().toString();
                    cityInfo = EngBilgiFragment.cityName.getText().toString();
                    historyInfo = EngBilgiFragment.history.getText().toString();
                    aboutInfo = EngBilgiFragment.aboutInfo.getText().toString();

                    //Veriler veritabanına kaydedilir.
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(KayitActivity.this);
                    alertDialog.setTitle("KAYIT");
                    alertDialog.setMessage("Kayıt etmek istiyormusunuz?");
                    alertDialog.setNegativeButton("HAYIR", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //Buraya herhangi bir şey yazılmaz.
                        }
                    });

                    alertDialog.setPositiveButton("EVET", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //Veriler veritabanına gönderilir.
                            System.out.println(fotoBilgi);
                            if(fotoBilgi != Uri.EMPTY){
                                UUID uuid = UUID.randomUUID();
                                String imageName = "images/" + uuid + ".jpg";
                                //Storage Referance kodu başlangıç
                                storageReference.child(imageName).putFile(fotoBilgi).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        //Fotograf kaydı başarılı olursa veriler veritabanına alınır.
                                        System.out.println("Foto kayıt başarılı");
                                        StorageReference yeniReferans = FirebaseStorage.getReference(imageName);
                                        yeniReferans.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri) {
                                                //------------
                                                String downloadUrl = uri.toString();
                                                HashMap<String,Object> yerData = new HashMap<>();
                                                yerData.put("adi",yerAdiBilgi);
                                                yerData.put("ulkesi",ulkeAdiBilgi);
                                                yerData.put("sehir",sehirAdiBilgi);
                                                yerData.put("tarihce",tarihceBilgi);
                                                yerData.put("hakkinda",hakkindaBilgi);
                                                yerData.put("gorselURL",downloadUrl);
                                                yerData.put("placeName",nameInfo);
                                                yerData.put("countryName",countryInfo);
                                                yerData.put("cityName",cityInfo);
                                                yerData.put("historyInfo",historyInfo);
                                                yerData.put("aboutInfo",aboutInfo);
                                                yerData.put("kayitTarihi", FieldValue.serverTimestamp());
                                                System.out.println(yerAdiBilgi+ " " + nameInfo+ " " + historyInfo);
                                                FirebaseFirestore.collection("YeniKayit").add(yerData).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                    @Override
                                                    public void onSuccess(DocumentReference documentReference) {
                                                        //Veri kaydı başarılı olursa bildirim gönderilir.
                                                        System.out.println("Veri kaydı başarılı");
                                                    }
                                                }).addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        System.out.println(e.getLocalizedMessage());
                                                        Toast.makeText(getApplicationContext(),e.getLocalizedMessage(),Toast.
                                                                LENGTH_LONG).show();
                                                    }
                                                });
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                //Fotoğraf kaydı başarısız olursa hata mesajı getLocalizedMessage ile kullanıcıya gönderilir.
                                                Toast.makeText(getApplicationContext(),e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                                                System.out.println("Başarısız");
                                                System.out.println(e.getLocalizedMessage());
                                            }
                                        });
                                    }
                                });
                                //Storage Referance kodu son
                                Toast.makeText(getApplicationContext(),"Kayıt Başarılı",Toast.LENGTH_LONG).show();
                                finish();
                                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                startActivity(intent);
                            }
                        }
                    });
                    alertDialog.show();
                }
                //------ pageNumber 2
                if(pageNumber<2){
                    pageNumber++;
                }
                System.out.println(pageNumber);
            }
        });

        //Geri butonunun yapacağı iş listener ile dinlenir.
        kayitBinding.buttonGeri1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pageNumber>0){
                    pageNumber--;
                }
                else
                {
                    //Çıkış gerektiği zamanlarda AlertDiyalog ile kullanıcının çıkışı onaylaması teyit edilir.
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(KayitActivity.this);
                    alertDialog.setTitle("ÇIKIŞ");
                    alertDialog.setMessage("Çıkmak İstiyor Musunuz? ");
                    alertDialog.setNegativeButton("HAYIR", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });
                    alertDialog.setPositiveButton("EVET", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                            Intent intent = new Intent(KayitActivity.this,MainActivity.class);
                            startActivity(intent);
                        }
                    });
                    alertDialog.show();
                }
                System.out.println(pageNumber);
                if(pageNumber==0){
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    FotoKayitFragment fotoKayitFragment = new FotoKayitFragment();
                    fragmentTransaction.replace(R.id.constraintLayout_Sayfalar,fotoKayitFragment).commit();
                    kayitBinding.buttonIleri1.setText("İleri");
                }
                else if(pageNumber==1){
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    TrBilgiFragment trBilgiFragment = new TrBilgiFragment();
                    fragmentTransaction.replace(R.id.constraintLayout_Sayfalar,trBilgiFragment).commit();
                    kayitBinding.buttonIleri1.setText("İleri");
                }
            }
        });


    }
}