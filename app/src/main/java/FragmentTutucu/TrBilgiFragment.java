package FragmentTutucu;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.gezirehberiappteacher.KayitActivity;
import com.example.gezirehberiappteacher.R;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TrBilgiFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TrBilgiFragment extends Fragment {

    public static EditText yerAdi,ulkeAdi,sehirAdi,tarihce, hakkindaGiris;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TrBilgiFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TrBilgiFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TrBilgiFragment newInstance(String param1, String param2) {
        TrBilgiFragment fragment = new TrBilgiFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tr_bilgi, container, false);
    }

    @Override
    public void onViewCreated(@NotNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        yerAdi = view.findViewById(R.id.editText_YerAdiGiris);
        ulkeAdi = view.findViewById(R.id.editText_UlkeGiris);
        sehirAdi = view.findViewById(R.id.editText_SehirGiris);
        tarihce = view.findViewById(R.id.editText_TarihGiris);
        hakkindaGiris = view.findViewById(R.id.editText_HakkindaGiris);

        veriEkranaGetir();
    }

    public  void veriEkranaGetir(){
        if(!KayitActivity.yerAdiBilgi.equals("")){
           yerAdi.setText(KayitActivity.yerAdiBilgi);
        }
        if(!KayitActivity.ulkeAdiBilgi.equals("")){
            ulkeAdi.setText(KayitActivity.ulkeAdiBilgi);
        }
        if(!KayitActivity.sehirAdiBilgi.equals("")){
            sehirAdi.setText(KayitActivity.sehirAdiBilgi);
        }
        if(!KayitActivity.hakkindaBilgi.equals("")){
            hakkindaGiris.setText(KayitActivity.hakkindaBilgi);
        }
    }
}