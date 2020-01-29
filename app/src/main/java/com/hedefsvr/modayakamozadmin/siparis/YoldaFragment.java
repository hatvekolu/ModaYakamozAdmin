package com.hedefsvr.modayakamozadmin.siparis;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.hedefsvr.modayakamozadmin.urunler.BedenObject;
import com.hedefsvr.modayakamozadmin.R;
import com.hedefsvr.modayakamozadmin.helper.ReadURL;
import com.hedefsvr.modayakamozadmin.urunler.ResimObject;
import com.hedefsvr.modayakamozadmin.helper.YuklemeFragment;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class YoldaFragment extends Fragment {


    ListView yolda;
    List<SatisObject> satisObject;
    List<ResimObject>resimObject;
    BedenObject bedenObject;
    List<SatisUrunObject>satisUrunObject;
    YoldaAdapter adapter;

    String sonuc="";
    EditText arama;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_yolda, container, false);

        yolda=(ListView)view.findViewById(R.id.yoldaList);
        arama=(EditText)view.findViewById(R.id.editText19);
        arama.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    sonuc = arama.getText().toString().replaceAll(" ","_");
                    new getData().execute("http://modayakamoz.com/admin/get_satis1.php?durum=2"+"&arama="+sonuc);
                    return true;
                }
                return false;
            }
        });

        new getData().execute("http://modayakamoz.com/admin/get_satis1.php?&durum=2");

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
            satisObject=new ArrayList<SatisObject>();


            ReadURL readURL=new ReadURL();
            try{
                String gelenData=readURL.readURL(values[0]);
                JSONArray array=new JSONArray(gelenData);
                if (array.length()==0)
                    return "HATA";
                for (int i=0;i<array.length();i++){
                    JSONObject object=array.getJSONObject(i);
                    JSONArray urunler=object.getJSONArray("urunler");

                    satisUrunObject=new ArrayList<SatisUrunObject>();
                    for (int j=0;j<urunler.length();j++){
                        JSONObject object1=urunler.getJSONObject(j);
                        JSONArray resimler=object1.getJSONArray("resimler");
                        JSONArray bedenler=object1.getJSONArray("beden");
                        resimObject=new ArrayList<ResimObject>();
                        for (int k=0;k<resimler.length();k++){
                            JSONObject resim= resimler.getJSONObject(k);
                            resimObject.add(new ResimObject(resim.getString("b_resim"),resim.getString("k_resim")));
                        }
                        for (int l=0;l<bedenler.length();l++){
                            JSONObject beden=bedenler.getJSONObject(l);
                            bedenObject=(new BedenObject(beden.getString("beden"),Integer.parseInt(beden.getString("adet"))));
                        }
                        satisUrunObject.add(new SatisUrunObject(Double.parseDouble(object1.getString("cikisTL")),Integer.parseInt(object1.getString("kategori_ID")),object1.getString("marka"),
                                object1.getString("yer"),object1.getString("id"),resimObject, bedenObject ));
                    }
                    satisObject.add(new SatisObject(Integer.parseInt(object.getString("id")),Integer.parseInt(object.getString("hazir")),Double.parseDouble(object.getString("tutar")),
                            Double.parseDouble(object.getString("kesilen_tutar")),2,Double.parseDouble(object.getString("hakedis")),
                            object.getString("bayi"),object.getString("ad"),object.getString("soyad"),
                            object.getString("tel"),object.getString("il"),object.getString("ilce"),object.getString("adres"),
                            object.getString("aciklama"),object.getString("kargo_no"),object.getString("kargo_firma"),object.getString("tarih"),
                            object.getString("odeme_turu"),object.getString("url"),object.getString("bayi_tel"),object.getString("calisan"),satisUrunObject));
                }

                return "0";
            }
            catch (Exception e){
                return "HATA";
            }

        }

        @Override
        protected void onPostExecute(String results)
        {
            if (!results.equals("HATA")){
                adapter=new YoldaAdapter(getActivity(),satisObject);
                yolda.setAdapter(adapter);

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
            }
            else {
                adapter=new YoldaAdapter(getActivity(),satisObject);
                yolda.setAdapter(adapter);
            }

            progress.dismiss();

        }
    }

}
