package com.hedefsvr.modayakamozadmin.urunler;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.hedefsvr.modayakamozadmin.R;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class AyakkabiFragment extends Fragment {
    List<UrunObject> urunObjects;
    List<ResimObject>resimObject;
    List<BedenObject> bedenObject;
    EditText arama;
    UrunAdapter adapter;
    GridView urunler;
    FloatingActionButton tukenen,mevcut,filter,hepsi;
    LinearLayout mev,tuk,hep;
    Boolean tiklandi = false, flag_loading = false;
    int filitre = 0;
    int page = 1,sayfaSayisi=0;
    String sonuc="";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_urunler, container, false);
    }

}
