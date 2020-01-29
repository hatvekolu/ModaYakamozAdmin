package com.hedefsvr.modayakamozadmin.siparis;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.hedefsvr.modayakamozadmin.R;
import com.hedefsvr.modayakamozadmin.helper.DBHelper;
import com.hedefsvr.modayakamozadmin.helper.ReadURL;
import com.hedefsvr.modayakamozadmin.helper.YuklemeFragment;
import com.hedefsvr.modayakamozadmin.urunler.BedenObject;
import com.hedefsvr.modayakamozadmin.urunler.ResimObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class BekleyenFragment extends Fragment {



    ListView bekleyen;
    List<SatisObject> satisObject;
    List<ResimObject>resimObject;
    BedenObject bedenObject;
    List<SatisUrunObject>satisUrunObject;
    SatisAdapter adapter;
    int pozisyon = 0,hazirlanan = 0,bekle = 0,durum = 7;
    EditText arama;
    TextView bekleyenKargo,hazirlananKargo;
    LinearLayout hepsi,ust,alt,zemin,canta,tesettur,rafsiz;
    FloatingActionButton filterFloat,hepsiFloat,ustFloat,altFloat,zeminFloat,cantaFloat,tesetturFloat,rafsizFloat;
    Boolean tiklandi = false;
    DBHelper dbHelper;
    String sonuc="";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_bekleyen, container, false);
        hepsi = (LinearLayout)view.findViewById(R.id.hepsi);
        alt = (LinearLayout)view.findViewById(R.id.alt);
        ust = (LinearLayout)view.findViewById(R.id.ust);
        zemin = (LinearLayout)view.findViewById(R.id.zemin);
        canta = (LinearLayout)view.findViewById(R.id.canta);
        tesettur = (LinearLayout)view.findViewById(R.id.tesettur);
        rafsiz = (LinearLayout)view.findViewById(R.id.rafsiz);

        filterFloat = (FloatingActionButton)view.findViewById(R.id.floatingActionButton);
        hepsiFloat = (FloatingActionButton)view.findViewById(R.id.floatingActionButton9);
        ustFloat = (FloatingActionButton)view.findViewById(R.id.floatingActionButton6);
        altFloat = (FloatingActionButton)view.findViewById(R.id.floatingActionButton4);
        zeminFloat = (FloatingActionButton)view.findViewById(R.id.floatingActionButton5);
        cantaFloat = (FloatingActionButton)view.findViewById(R.id.floatingActionButton3);
        tesetturFloat = (FloatingActionButton)view.findViewById(R.id.floatingActionButton7);
        rafsizFloat = (FloatingActionButton)view.findViewById(R.id.floatingActionButton8);
        bekleyen=(ListView)view.findViewById(R.id.bekleyenList);
        bekleyenKargo =(TextView)view.findViewById(R.id.textView63) ;
        hazirlananKargo =(TextView)view.findViewById(R.id.textView64) ;
        arama=(EditText)view.findViewById(R.id.editText19);
        dbHelper = new DBHelper(getContext());


        arama.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    sonuc = arama.getText().toString().replaceAll(" ","_");
                    if (dbHelper.getUser().getRegion().equals("0") ||  dbHelper.getUser().getRegion().equals("1"))
                        new getData().execute("http://modayakamoz.com/admin/get_satis_bekleyen_yetkisiz.php?durum="+durum +"&ID="+dbHelper.getUser().getID()+"&arama="+sonuc);
                    else
                        new getData().execute("http://modayakamoz.com/admin/get_satis_bekleyen.php?durum="+durum +"&ID="+dbHelper.getUser().getID()+"&arama="+sonuc);

                    return true;
                }
                return false;
            }
        });
        filterFloat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!tiklandi){
                    tiklandi = true;
                    hepsi.setVisibility(View.VISIBLE);
                    alt.setVisibility(View.VISIBLE);
                    ust.setVisibility(View.VISIBLE);
                    zemin.setVisibility(View.VISIBLE);
                    canta.setVisibility(View.VISIBLE);
                    tesettur.setVisibility(View.VISIBLE);
                    rafsiz.setVisibility(View.VISIBLE);
                }
                else {
                    tiklandi = false;
                    hepsi.setVisibility(View.INVISIBLE);
                    alt.setVisibility(View.INVISIBLE);
                    ust.setVisibility(View.INVISIBLE);
                    zemin.setVisibility(View.INVISIBLE);
                    canta.setVisibility(View.INVISIBLE);
                    tesettur.setVisibility(View.INVISIBLE);
                    rafsiz.setVisibility(View.INVISIBLE);
                }
            }
        });
        hepsiFloat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tiklandi = false;
                hepsi.setVisibility(View.INVISIBLE);
                alt.setVisibility(View.INVISIBLE);
                ust.setVisibility(View.INVISIBLE);
                zemin.setVisibility(View.INVISIBLE);
                canta.setVisibility(View.INVISIBLE);
                tesettur.setVisibility(View.INVISIBLE);
                rafsiz.setVisibility(View.INVISIBLE);
                arama.setText("");
                sonuc = "";
                durum=7;
                if (dbHelper.getUser().getRegion().equals("0") ||  dbHelper.getUser().getRegion().equals("1"))
                    new getData().execute("http://modayakamoz.com/admin/get_satis_bekleyen_yetkisiz.php?durum="+durum +"&ID="+dbHelper.getUser().getID());
                else
                    new getData().execute("http://modayakamoz.com/admin/get_satis_bekleyen.php?durum="+durum +"&ID="+dbHelper.getUser().getID());

            }
        });

        ustFloat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tiklandi = false;
                hepsi.setVisibility(View.INVISIBLE);
                alt.setVisibility(View.INVISIBLE);
                ust.setVisibility(View.INVISIBLE);
                zemin.setVisibility(View.INVISIBLE);
                canta.setVisibility(View.INVISIBLE);
                tesettur.setVisibility(View.INVISIBLE);
                rafsiz.setVisibility(View.INVISIBLE);
                arama.setText("");
                sonuc = "";
                durum=3;
                if (dbHelper.getUser().getRegion().equals("0") ||  dbHelper.getUser().getRegion().equals("1"))
                    new getData().execute("http://modayakamoz.com/admin/get_satis_bekleyen_yetkisiz.php?durum="+durum +"&ID="+dbHelper.getUser().getID());
                else
                    new getData().execute("http://modayakamoz.com/admin/get_satis_bekleyen.php?durum="+durum +"&ID="+dbHelper.getUser().getID());
            }
        });
        altFloat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tiklandi = false;
                hepsi.setVisibility(View.INVISIBLE);
                alt.setVisibility(View.INVISIBLE);
                ust.setVisibility(View.INVISIBLE);
                zemin.setVisibility(View.INVISIBLE);
                canta.setVisibility(View.INVISIBLE);
                tesettur.setVisibility(View.INVISIBLE);
                rafsiz.setVisibility(View.INVISIBLE);
                arama.setText("");
                sonuc = "";
                durum=1;
                if (dbHelper.getUser().getRegion().equals("0") ||  dbHelper.getUser().getRegion().equals("1"))
                    new getData().execute("http://modayakamoz.com/admin/get_satis_bekleyen_yetkisiz.php?durum="+durum +"&ID="+dbHelper.getUser().getID());
                else
                    new getData().execute("http://modayakamoz.com/admin/get_satis_bekleyen.php?durum="+durum +"&ID="+dbHelper.getUser().getID());
            }
        });
        zeminFloat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tiklandi = false;
                hepsi.setVisibility(View.INVISIBLE);
                alt.setVisibility(View.INVISIBLE);
                ust.setVisibility(View.INVISIBLE);
                zemin.setVisibility(View.INVISIBLE);
                canta.setVisibility(View.INVISIBLE);
                tesettur.setVisibility(View.INVISIBLE);
                rafsiz.setVisibility(View.INVISIBLE);
                arama.setText("");
                sonuc = "";
                durum=2;
                if (dbHelper.getUser().getRegion().equals("0") ||  dbHelper.getUser().getRegion().equals("1"))
                    new getData().execute("http://modayakamoz.com/admin/get_satis_bekleyen_yetkisiz.php?durum="+durum +"&ID="+dbHelper.getUser().getID());
                else
                    new getData().execute("http://modayakamoz.com/admin/get_satis_bekleyen.php?durum="+durum +"&ID="+dbHelper.getUser().getID());
            }
        });
        cantaFloat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tiklandi = false;
                hepsi.setVisibility(View.INVISIBLE);
                alt.setVisibility(View.INVISIBLE);
                ust.setVisibility(View.INVISIBLE);
                zemin.setVisibility(View.INVISIBLE);
                canta.setVisibility(View.INVISIBLE);
                tesettur.setVisibility(View.INVISIBLE);
                rafsiz.setVisibility(View.INVISIBLE);
                arama.setText("");
                sonuc = "";
                durum=6;
                if (dbHelper.getUser().getRegion().equals("0") ||  dbHelper.getUser().getRegion().equals("1"))
                    new getData().execute("http://modayakamoz.com/admin/get_satis_bekleyen_yetkisiz.php?durum="+durum +"&ID="+dbHelper.getUser().getID());
                else
                    new getData().execute("http://modayakamoz.com/admin/get_satis_bekleyen.php?durum="+durum +"&ID="+dbHelper.getUser().getID());
            }
        });
        tesetturFloat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tiklandi = false;
                hepsi.setVisibility(View.INVISIBLE);
                alt.setVisibility(View.INVISIBLE);
                ust.setVisibility(View.INVISIBLE);
                zemin.setVisibility(View.INVISIBLE);
                canta.setVisibility(View.INVISIBLE);
                tesettur.setVisibility(View.INVISIBLE);
                rafsiz.setVisibility(View.INVISIBLE);
                arama.setText("");
                sonuc = "";
                durum=5;
                if (dbHelper.getUser().getRegion().equals("0") ||  dbHelper.getUser().getRegion().equals("1"))
                    new getData().execute("http://modayakamoz.com/admin/get_satis_bekleyen_yetkisiz.php?durum="+durum +"&ID="+dbHelper.getUser().getID());
                else
                    new getData().execute("http://modayakamoz.com/admin/get_satis_bekleyen.php?durum="+durum +"&ID="+dbHelper.getUser().getID());
            }
        });
        rafsizFloat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tiklandi = false;
                hepsi.setVisibility(View.INVISIBLE);
                alt.setVisibility(View.INVISIBLE);
                ust.setVisibility(View.INVISIBLE);
                zemin.setVisibility(View.INVISIBLE);
                canta.setVisibility(View.INVISIBLE);
                tesettur.setVisibility(View.INVISIBLE);
                rafsiz.setVisibility(View.INVISIBLE);
                arama.setText("");
                sonuc = "";
                durum=4;
                if (dbHelper.getUser().getRegion().equals("0") ||  dbHelper.getUser().getRegion().equals("1"))
                    new getData().execute("http://modayakamoz.com/admin/get_satis_bekleyen_yetkisiz.php?durum="+durum +"&ID="+dbHelper.getUser().getID());
                else
                    new getData().execute("http://modayakamoz.com/admin/get_satis_bekleyen.php?durum="+durum +"&ID="+dbHelper.getUser().getID());
            }
        });


        if (dbHelper.getUser().getRegion().equals("0") ||  dbHelper.getUser().getRegion().equals("1"))
            new getData().execute("http://modayakamoz.com/admin/get_satis_bekleyen_yetkisiz.php?durum="+durum +"&ID="+dbHelper.getUser().getID());
        else
            new getData().execute("http://modayakamoz.com/admin/get_satis_bekleyen.php?durum="+durum +"&ID="+dbHelper.getUser().getID());

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
                bekle=0;
                hazirlanan=0;
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
                        satisUrunObject.add(new SatisUrunObject(Double.parseDouble(object1.getString("cikisTL")),Integer.parseInt(object1.getString("kategori_ID")),object1.getString("marka")
                                ,object1.getString("id"),object1.getString("yer")  ,resimObject,
                                bedenObject ));
                    }
                    satisObject.add(new SatisObject(Integer.parseInt(object.getString("id")),Integer.parseInt(object.getString("hazir")),Double.parseDouble(object.getString("tutar")),
                            Double.parseDouble(object.getString("kesilen_tutar")),1,Double.parseDouble(object.getString("hakedis")),
                            object.getString("bayi"),object.getString("ad"),object.getString("soyad"),
                            object.getString("tel"),object.getString("il"),object.getString("ilce"),object.getString("adres"),
                            object.getString("aciklama"),object.getString("kargo_no"),object.getString("kargo_firma"),object.getString("tarih"),
                            object.getString("odeme_turu"),object.getString("url"),object.getString("bayi_tel"),object.getString("calisan"),satisUrunObject));
                    if (Integer.parseInt(object.getString("hazir"))==0)
                        bekle++;
                    else
                        hazirlanan++;
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
                adapter=new SatisAdapter(getActivity(),satisObject,BekleyenFragment.this);
                bekleyen.setAdapter(adapter);
                bekleyen.setSelection(pozisyon);
                bekleyenKargo.setText("Bekleyen: " +bekle);
                hazirlananKargo.setText("Hazırlanan: " +hazirlanan);
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
                adapter=new SatisAdapter(getActivity(),satisObject,BekleyenFragment.this);
                bekleyen.setAdapter(adapter);

            }

            progress.dismiss();

        }
    }


    public void yenile(int id){
        pozisyon = id;
        if (dbHelper.getUser().getRegion().equals("0") ||  dbHelper.getUser().getRegion().equals("1"))
            new getData().execute("http://modayakamoz.com/admin/get_satis_bekleyen_yetkisiz.php?durum="+durum +"&ID="+dbHelper.getUser().getID()+"&arama="+sonuc.replaceAll(" ","_"));
        else
            new getData().execute("http://modayakamoz.com/admin/get_satis_bekleyen.php?durum="+durum +"&ID="+dbHelper.getUser().getID()+"&arama="+sonuc.replaceAll(" ","_"));

    }
}
