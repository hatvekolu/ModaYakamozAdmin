package com.hedefsvr.modayakamozadmin.siparis;


import android.content.ClipboardManager;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hedefsvr.modayakamozadmin.R;
import com.hedefsvr.modayakamozadmin.urunler.UrunDetayAdapter;


public class SiparisDetayFragment extends DialogFragment {

    private int index;
    public void setIndex(int index) { this.index=index;}

    UrunDetayAdapter adapter;
    private  SatisObject so;
    public void setUrun(SatisObject so) {
        this.so = so;
    }
    private BekleyenFragment bf;
    public void setFragment(BekleyenFragment bf) {
        this.bf = bf;
    }
    TextView adSoyad,adres,tel,toplamTutar,hakedis,kesilenTutar,tarih,kargo,odeme_turu;
    ListView urunler;
    Button detay,hazir;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        View view= inflater.inflate(R.layout.fragment_siparis_detay, container, false);

        adSoyad=(TextView)view.findViewById(R.id.textView4);
        adres=(TextView)view.findViewById(R.id.textView7);
        tel=(TextView)view.findViewById(R.id.textView44);
        kargo=(TextView)view.findViewById(R.id.textView43);
        toplamTutar=(TextView)view.findViewById(R.id.textView45);
        hakedis=(TextView)view.findViewById(R.id.textView46);
        kesilenTutar=(TextView)view.findViewById(R.id.textView47);
        tarih=(TextView)view.findViewById(R.id.textView48);
        urunler=(ListView)view.findViewById(R.id.urunler);
        odeme_turu=(TextView)view.findViewById(R.id.textView55);
        detay=(Button)view.findViewById(R.id.button12);
        hazir=(Button)view.findViewById(R.id.button14);
        adSoyad.setText("Ad Soyad: "+so.getAd()+" "+so.getSoyad());
        adres.setText("Adres: "+so.getAdres()+" "+so.getIlce()+"/"+so.getIl());
        tel.setText("Telefon: "+so.getTel());
        toplamTutar.setText("Tutar: "+so.getTutar()+" ₺");
        hakedis.setText("Hakediş: "+so.getHakedis()+" ₺");
        kesilenTutar.setText("Kesilen Tutar: "+so.getKesilen_tutar()+" ₺");
        tarih.setText("Tarih: "+so.getTarih());
        kargo.setText("Kargo: "+so.getKargo_firma()+"-"+so.getKargo_no());
        odeme_turu.setText("Ödeme Türü: "+so.getOdeme_turu());

        detay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getFragmentManager();
                KargoBilgiFragment alertDialog = new KargoBilgiFragment();
                alertDialog.setUrun(so);
                alertDialog.show(fm,"fragment_alert");
            }
        });
        adapter=new UrunDetayAdapter(getActivity(),so.getSatisUrunObject());
        urunler.setAdapter(adapter);
        kargo.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                ClipboardManager cm = (ClipboardManager) getContext().getSystemService(getContext().CLIPBOARD_SERVICE);
                cm.setText(kargo.getText());
                Toast.makeText(getContext(), "Kopyalandı", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        hazir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = ((AppCompatActivity) getContext()).getSupportFragmentManager();
                HazirFragment alertDialog = new HazirFragment();
                alertDialog.setUrun(so);
                alertDialog.setIndex(index);
                alertDialog.setBekleyenFragment(bf);

                alertDialog.setSiparisDetayFragment(SiparisDetayFragment.this);
                alertDialog.show(fm, "fragment_alert");
            }
        });

        if ( so.getDurum() >= 2 )
            hazir.setVisibility(View.INVISIBLE);


        return view;
    }

}
