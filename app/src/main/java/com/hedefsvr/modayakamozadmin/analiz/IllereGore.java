package com.hedefsvr.modayakamozadmin.analiz;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import com.hedefsvr.modayakamozadmin.R;

import java.util.ArrayList;



public class IllereGore extends Fragment {

    ArrayList<IllerObject> ilObject;
    EditText arama;
    ListView iller;
    IllereGoreAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_illere_gore, container, false);

        arama=(EditText)view.findViewById(R.id.ilArama);
        iller=(ListView)view.findViewById(R.id.iller);

        ilObject=new ArrayList<IllerObject>();

        ilObject.add(new IllerObject("Adana","dana"));
        ilObject.add(new IllerObject("Adıyaman","yaman"));
        ilObject.add(new IllerObject("Afyon","fyon"));
        ilObject.add(new IllerObject("Ağrı","ğr"));
        ilObject.add(new IllerObject("Amasya","masya"));
        ilObject.add(new IllerObject("Ankara","nkara"));
        ilObject.add(new IllerObject("Antalya","talya"));
        ilObject.add(new IllerObject("Artvin","rtv"));
        ilObject.add(new IllerObject("Aydın","ayd"));
        ilObject.add(new IllerObject("Balıkesir","kesir"));
        ilObject.add(new IllerObject("Bilecik","lecik"));
        ilObject.add(new IllerObject("Bingöl","ing"));
        ilObject.add(new IllerObject("Bitlis","tlis"));
        ilObject.add(new IllerObject("Bolu","olu"));
        ilObject.add(new IllerObject("Burdur","urdur"));
        ilObject.add(new IllerObject("Bursa","ursa"));
        ilObject.add(new IllerObject("Çanakkale","nakka"));
        ilObject.add(new IllerObject("Çankırı","ankır"));
        ilObject.add(new IllerObject("Çorum","orum"));
        ilObject.add(new IllerObject("Denizli","enizl"));
        ilObject.add(new IllerObject("Diyarbakır","yarbak"));
        ilObject.add(new IllerObject("Edirne","dirne"));
        ilObject.add(new IllerObject("Elazığ","elaz"));
        ilObject.add(new IllerObject("Erzincan","ncan"));
        ilObject.add(new IllerObject("Erzurum","rzurum"));
        ilObject.add(new IllerObject("Eskişehir","ski"));
        ilObject.add(new IllerObject("Gaziantep","antep"));
        ilObject.add(new IllerObject("Giresun","resun"));
        ilObject.add(new IllerObject("Gümüşhane","hane"));
        ilObject.add(new IllerObject("Hakkari","akkar"));
        ilObject.add(new IllerObject("Hatay","atay"));
        ilObject.add(new IllerObject("Isparta","parta"));
        ilObject.add(new IllerObject("Mersin","ersin"));
        ilObject.add(new IllerObject("İstanbul","stanb"));
        ilObject.add(new IllerObject("İzmir","zmir"));
        ilObject.add(new IllerObject("Kars","ars"));
        ilObject.add(new IllerObject("Kastamonu","astam"));
        ilObject.add(new IllerObject("Kayseri","ayser"));
        ilObject.add(new IllerObject("Kırklareli","rkla"));
        ilObject.add(new IllerObject("Kırşehir","rşeh"));
        ilObject.add(new IllerObject("Kocaeli","cael"));
        ilObject.add(new IllerObject("Konya","nya"));
        ilObject.add(new IllerObject("Kütahya","tahya"));
        ilObject.add(new IllerObject("Malatya","latya"));
        ilObject.add(new IllerObject("Manisa","anis"));
        ilObject.add(new IllerObject("Kahramanmaraş","mara"));
        ilObject.add(new IllerObject("Mardin","ardi"));
        ilObject.add(new IllerObject("Muğla","uğla"));
        ilObject.add(new IllerObject("Muş","muş"));
        ilObject.add(new IllerObject("Nevşehir","nev"));
        ilObject.add(new IllerObject("Niğde","iğde"));
        ilObject.add(new IllerObject("Ordu","ordu"));
        ilObject.add(new IllerObject("Rize","ize"));
        ilObject.add(new IllerObject("Sakarya","akary"));
        ilObject.add(new IllerObject("Samsun","amsu"));
        ilObject.add(new IllerObject("Siirt","irt"));
        ilObject.add(new IllerObject("Sinop","nop"));
        ilObject.add(new IllerObject("Sivas","vas"));
        ilObject.add(new IllerObject("Tekirdağ","ekir"));
        ilObject.add(new IllerObject("Tokat","okat"));
        ilObject.add(new IllerObject("Trabzon","abzo"));
        ilObject.add(new IllerObject("Tunceli","unce"));
        ilObject.add(new IllerObject("Şanlıurfa","urfa"));
        ilObject.add(new IllerObject("Uşak","şak"));
        ilObject.add(new IllerObject("Van","van"));
        ilObject.add(new IllerObject("Yozgat","ozga"));
        ilObject.add(new IllerObject("Zonguldak","ong"));
        ilObject.add(new IllerObject("Aksaray","saray"));
        ilObject.add(new IllerObject("Bayburt","ayb"));
        ilObject.add(new IllerObject("Karaman","aram"));
        ilObject.add(new IllerObject("Kırıkkale","ıkkale"));
        ilObject.add(new IllerObject("Batman","atman"));
        ilObject.add(new IllerObject("Şırnak","rnak"));
        ilObject.add(new IllerObject("Bartın","bart"));
        ilObject.add(new IllerObject("Ardahan","rdah"));
        ilObject.add(new IllerObject("Iğdır","ğdır"));
        ilObject.add(new IllerObject("Yalova","lova"));
        ilObject.add(new IllerObject("Karabük","arab"));
        ilObject.add(new IllerObject("Kilis","ilis"));
        ilObject.add(new IllerObject("Osmaniye","sman"));
        ilObject.add(new IllerObject("Düzce","zce"));

        adapter=new IllereGoreAdapter(getActivity(),ilObject);
        iller.setAdapter(adapter);

        arama.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                System.out.println("Text ["+s+"]");

                adapter.getFilter().filter(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        return view;
    }

}
