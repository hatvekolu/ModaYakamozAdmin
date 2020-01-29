package com.hedefsvr.modayakamozadmin.genel;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.hedefsvr.modayakamozadmin.R;
import com.hedefsvr.modayakamozadmin.helper.ReadURL;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CalisanDetay extends DialogFragment {


    Spinner durum,yetki;
    private CalisanObject co;
    private CalisanlarFragment cf;
    public void setFragment(CalisanlarFragment cf) {
        this.cf = cf;
    }
    public void setCalisan(CalisanObject co) {
        this.co = co;
    }
    TextView ad,sifre;
    Button kaydet;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_calisan_detay, container, false);

        ad =(TextView)view.findViewById(R.id.textView94);
        sifre =(TextView)view.findViewById(R.id.textView93);
        durum = (Spinner)view.findViewById(R.id.spinner);
        yetki = (Spinner)view.findViewById(R.id.spinner2);
        kaydet =(Button)view.findViewById(R.id.button24) ;
        ad.setText("Kullancı Adı: "+co.getKullanciAdi());
        sifre.setText("Şifre: "+co.getSifre());

        List<String> categories = new ArrayList<String>();
        categories.add("PASİF"  );categories.add("AKTİF");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        durum.setAdapter(dataAdapter);
        if (co.getDurum().equals("0"))
            durum.setSelection(0);
        else
            durum.setSelection(1);


        List<String> categorie = new ArrayList<String>();
        categorie.add("YETKİSİZ"  );categorie.add("YETKİLİ");categorie.add("ÇOK YETKİLİ");categorie.add("ADMİN");
        ArrayAdapter<String> dataAdapte = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, categorie);
        dataAdapte.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yetki.setAdapter(dataAdapte);
        if (co.getYetki().equals("0"))
            yetki.setSelection(0);
        else if (co.getYetki().equals("1"))
            yetki.setSelection(1);
        else if(co.getYetki().equals("2"))
            yetki.setSelection(2);
        else
            yetki.setSelection(3);
        kaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new getData().execute(""+yetki.getSelectedItemPosition(),""+durum.getSelectedItemPosition());
            }
        });
        return view;
    }
    private class getData extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() {


        }

        @Override
        protected String doInBackground(String... values)
        {



            ReadURL readURL=new ReadURL();
            try{

                String gelenData=readURL.readURL("http://modayakamoz.com/admin/calisan_edit.php?ID="+co.getId()+"&yetki="+values[0]+"&durum="+values[1]);

                return "1";
            }
            catch (Exception e){
                return "HATA";
            }

        }

        @Override
        protected void onPostExecute(String results)
        {

            cf.yenile();
            getDialog().dismiss();

        }
    }
}
