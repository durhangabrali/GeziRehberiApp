package FragmentTutucu;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.gezirehberiappteacher.R;
import com.google.android.material.snackbar.Snackbar;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FotoKayitFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FotoKayitFragment extends Fragment {
    //Galeriden gelen görüntünün Uri adresinin tutulacağı değişken
    public static Uri imageData;
    ImageView imageView;  //ImageView tanımı
    ActivityResultLauncher<Intent> activityResultLauncher; //Galeriye gitmek için gereken Intent
    ActivityResultLauncher<String> permissionResultLauncher; //İzin istemek için gereken tanımlama



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FotoKayitFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FotoKayitFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FotoKayitFragment newInstance(String param1, String param2) {
        FotoKayitFragment fragment = new FotoKayitFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Kullnıcı tanımlı metot
        activityResultLauncherBaslatmalari();

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_foto_kayit, container, false);
    }


    @Override
    public void onViewCreated(@NotNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        imageView = view.findViewById(R.id.imageView_KayitFoto);
        if(imageData != Uri.EMPTY){
            imageView.setImageURI(imageData);
        }

        //ImageView'a tıklanırsa ne olacağının yazıldığı listener
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                     if(ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)){
                         Snackbar.make(view,"Galeriye izin gerekli",Snackbar.LENGTH_INDEFINITE).setAction("Give Permision", new View.OnClickListener() {
                             @Override
                             public void onClick(View view) {
                                 //Gerekli izin istenir
                                 permissionResultLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
                             }
                         }).show();
                     }else{
                        //Gerekli izin istenir
                        permissionResultLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
                    }
                }else{
                    //Galeriye gidilir
                    Intent intentToGalery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    //Launcher yazılarak getirilir.
                    activityResultLauncher.launch(intentToGalery);
                }
            }
        });

    }

    //Kullnıcı tanımlı metot
    private void activityResultLauncherBaslatmalari(){
      //Galeriden görseli ve görselin datasını döndürmek için yazılması gerekenler
        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                //---------
                if(result.getResultCode() == RESULT_OK){
                    Intent intentFromResult = result.getData();
                    if (intentFromResult != null){
                        imageData = intentFromResult.getData();
                        imageView.setImageURI(imageData);
                        System.out.println(imageData);
                    }
                }
            }
        });
        //İzin istekleri için yazılması gerekenler
        permissionResultLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
            @Override
            public void onActivityResult(Boolean result) {
                //---------
                if(result){
                    Intent intentToGalery = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    activityResultLauncher.launch(intentToGalery);
                }
                else{
                    Toast.makeText(getContext(), "İzin gerekli!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}