package com.hedefsvr.modayakamozadmin.genel;


import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.hedefsvr.modayakamozadmin.R;
import com.hedefsvr.modayakamozadmin.helper.ReadURL;
import com.hedefsvr.modayakamozadmin.helper.YuklemeFragment;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CalisanlarFragment extends Fragment {

    List<CalisanObject> calisanObjects;
    CalisanlarAdapter adapter;
    ListView calisanlar;
    Button calisanButton,sifirla;
    TextView atanmayan;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_calisanlar, container, false);
        calisanlar = (ListView)view.findViewById(R.id.calisanlar);
        calisanButton =(Button)view.findViewById(R.id.button22) ;
        sifirla =(Button)view.findViewById(R.id.button23) ;
        atanmayan = (TextView)view.findViewById(R.id.textView86);

        calisanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                new atama().execute("http://modayakamoz.com/admin/dagit.php");
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Siparişler Dağıtılacak!!").setPositiveButton("Dağıt", dialogClickListener)
                        .setNegativeButton("İptal", dialogClickListener).show();
            }
        });
        sifirla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                new atama().execute("http://modayakamoz.com/admin/sifirla.php");
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Hazırlanmayan Siparişler Sıfırlanacak!!").setPositiveButton("Sıfırla", dialogClickListener)
                        .setNegativeButton("İptal", dialogClickListener).show();
            }
        });
        new getData().execute();
        return view;
    }
    private class getData extends AsyncTask<String, String, String> {
        YuklemeFragment progress;

        @Override
        protected void onPreExecute() {

            FragmentManager fm = getFragmentManager();
            progress = new YuklemeFragment();
            progress.show(fm, "");
        }

        @Override
        protected String doInBackground(String... values)
        {
            calisanObjects=new ArrayList<CalisanObject>();


            ReadURL readURL=new ReadURL();
            try{
                String gelenData=readURL.readURL("http://modayakamoz.com/admin/get_calisan.php");
                JSONObject object1 = new JSONObject(gelenData);
                JSONArray array=object1.getJSONArray("bayiler");
                for (int i=0;i<array.length();i++){
                    JSONObject object = array.getJSONObject(i);
                    calisanObjects.add(new CalisanObject(object.getString("id"),object.getString("kullanciAdi"),object.getString("sifre"),
                            object.getString("yetki"),
                            object.getString("durum"),
                            object.getString("siparis"),
                            object.getString("bekleyen"),object.getString("hazir"),object.getString("tarih"),object.getString("odeme")));
                }

                return object1.getString("atanmayan");
            }
            catch (Exception e){
                return "HATA";
            }

        }

        @Override
        protected void onPostExecute(String results)
        {
            if (!results.equals("HATA")){

                Collections.sort(calisanObjects, new Comparator<CalisanObject>(){
                    public int compare(CalisanObject obj1, CalisanObject obj2) {
                        // ## Ascending order
                        // return obj1.getHazir().compareToIgnoreCase(obj2.getHazir()); // To compare string values
                        // return Integer.valueOf(obj1.getHazir()).compareTo(Integer.valueOf(obj2.getHazir())); // To compare integer values

                        // ## Descending order
                        // return obj2.firstName.compareToIgnoreCase(obj1.firstName); // To compare string values
                         return Integer.valueOf(obj2.getHazir()).compareTo(Integer.valueOf(obj1.getHazir())); // To compare integer values
                    }
                });
                atanmayan.setText(results+" Adet Atanmayan Kargo");
                adapter=new CalisanlarAdapter(getActivity(),calisanObjects,CalisanlarFragment.this);
                calisanlar.setAdapter(adapter);
            }


            progress.dismiss();

        }
    }
    public  void yenile (){
        new getData().execute();
    }

    private class atama extends AsyncTask<String, String, String> {
        YuklemeFragment progress;

        @Override
        protected void onPreExecute() {

            FragmentManager fm = getFragmentManager();
            progress = new YuklemeFragment();
            progress.show(fm, "");
        }

        @Override
        protected String doInBackground(String... values)
        {



            ReadURL readURL=new ReadURL();
            try{

                String gelenData=readURL.readURL(values[0]);

                return "1";
            }
            catch (Exception e){
                return "HATA";
            }

        }

        @Override
        protected void onPostExecute(String results)
        {
            progress.dismiss();
            new getData().execute();


        }
    }
}
