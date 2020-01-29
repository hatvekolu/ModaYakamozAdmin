package com.hedefsvr.modayakamozadmin.urunler;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hedefsvr.modayakamozadmin.R;
import com.hedefsvr.modayakamozadmin.helper.ReadURL;
import com.hedefsvr.modayakamozadmin.helper.YuklemeFragment;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class CantaFragment extends Fragment {


    List<UrunObject> urunObjects;
    List<ResimObject>resimObject;
    List<BedenObject> bedenObject;
    EditText arama;
    UrunAdapter adapter;
    GridView urunler;
    FloatingActionButton tukenen,mevcut,filter,hepsi;
    LinearLayout mev,tuk,hep;
    Boolean tiklandi = false, flag_loading = false;
    int durum = 0;
    int page = 1,sayfaSayisi=0;
    String sonuc="";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_urunler, container, false);
        filter =(FloatingActionButton)view.findViewById(R.id.floatingActionButton);
        tukenen =(FloatingActionButton)view.findViewById(R.id.floatingActionButton4);
        mevcut =(FloatingActionButton)view.findViewById(R.id.floatingActionButton5);
        hepsi =(FloatingActionButton)view.findViewById(R.id.floatingActionButton6);
        mev =(LinearLayout)view.findViewById(R.id.zemin);
        hep =(LinearLayout)view.findViewById(R.id.hep);
        tuk =(LinearLayout)view.findViewById(R.id.alt);
        arama=(EditText)view.findViewById(R.id.arama1);
        urunler=(GridView) view.findViewById(R.id.urunler);
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!tiklandi){
                    tiklandi = true;
                    mev.setVisibility(View.VISIBLE);
                    tuk.setVisibility(View.VISIBLE);
                    hep.setVisibility(View.VISIBLE);
                }
                else {
                    tiklandi = false;
                    mev.setVisibility(View.INVISIBLE);
                    tuk.setVisibility(View.INVISIBLE);
                    hep.setVisibility(View.INVISIBLE);
                }
            }
        });
        mevcut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tiklandi = false;
                mev.setVisibility(View.INVISIBLE);
                tuk.setVisibility(View.INVISIBLE);
                hep.setVisibility(View.INVISIBLE);
                page = 1;
                durum=1;
                new getData().execute(arama.getText().toString().replaceAll(" ","_"));

            }
        });
        tukenen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tiklandi = false;
                mev.setVisibility(View.INVISIBLE);
                tuk.setVisibility(View.INVISIBLE);
                hep.setVisibility(View.INVISIBLE);
                page = 1;
                durum=2;
                new getData().execute(arama.getText().toString().replaceAll(" ","_"));
            }
        });
        hepsi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tiklandi = false;
                mev.setVisibility(View.INVISIBLE);
                tuk.setVisibility(View.INVISIBLE);
                hep.setVisibility(View.INVISIBLE);
                page = 1;
                durum=0;
                new getData().execute(arama.getText().toString().replaceAll(" ","_"));
            }
        });
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener( new View.OnKeyListener()
        {
            @Override
            public boolean onKey( View v, int keyCode, KeyEvent event )
            {
                if( keyCode == KeyEvent.KEYCODE_BACK )
                {
                    getActivity().finish();
                }
                return false;
            }
        } );
        arama.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                    new getData().execute(arama.getText().toString().replaceAll(" ","_"));
                    sonuc=arama.getText().toString().replaceAll(" ","_");
                    return true;
                }
                return false;
            }
        });
        urunler.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {

                UrunObject data= (UrunObject) adapter.getItem(position);
                FragmentActivity activity = (FragmentActivity)(getContext());
                FragmentManager fm = activity.getSupportFragmentManager();
                DetayFragment alertDialog = new DetayFragment();
                alertDialog.setUrun(urunObjects.get(position).getId());
                alertDialog.setKategori(2);
                alertDialog.show(fm,"fragment_alert");

                View view1 = getActivity().getCurrentFocus();
                if (view1 != null) {
                    InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view1.getWindowToken(), 0);}

            }
        });
        urunler.setOnScrollListener(new AbsListView.OnScrollListener() {

            public void onScrollStateChanged(AbsListView view, int scrollState) {


            }

            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {

                if(firstVisibleItem+visibleItemCount == totalItemCount && totalItemCount!=0)
                {
                    if(flag_loading == false)
                    {
                        flag_loading = true;
                        page++;
                        if (page <= sayfaSayisi)
                            new getDataNew().execute(sonuc.replaceAll(" ","_"));
                    }
                }
            }
        });

        new getData().execute(sonuc.replaceAll(" ","_"));
        return view;
    }
    private class getData extends AsyncTask<String, String, String> {
        YuklemeFragment progress;

        @Override
        protected void onPreExecute() {
            page=1;
            FragmentManager fm = getFragmentManager();
            progress = new YuklemeFragment();
            progress.show(fm, "");
        }

        @Override
        protected String doInBackground(String... values)
        {

            urunObjects=new ArrayList<UrunObject>();
            urunObjects.clear();
            ReadURL readURL=new ReadURL();

            try{
                String gelenData=readURL.readURL("http://modayakamoz.com/admin/get_urunler.php?kategori=2"  + "&page="+page+"&arama="+values[0] + "&durum=" + durum);
                JSONObject jsonObject = new JSONObject(gelenData);
                sayfaSayisi =(int) Math.ceil(Double.parseDouble(jsonObject.getString("sayfa"))) ;
                JSONArray array=jsonObject.getJSONArray("urunler");

                    for (int i=0;i<array.length();i++){
                        bedenObject=new ArrayList<BedenObject>();
                        resimObject=new ArrayList<ResimObject>();
                        JSONObject object=array.getJSONObject(i);
                        JSONArray array1=object.getJSONArray("resimler");
                        JSONArray array2=object.getJSONArray("stok");

                        for (int j=0;j<array2.length();j++){
                            JSONObject stokobj=array2.getJSONObject(j);
                                bedenObject.add(new BedenObject(stokobj.getString("beden"),Integer.parseInt(stokobj.getString("miktar"))));
                        }

                        for (int j=0;j<array1.length();j++){
                            JSONObject resimobj=array1.getJSONObject(j);
                            resimObject.add(new ResimObject (resimobj.getString("b_resim"),resimobj.getString("k_resim")));
                        }
                        urunObjects.add(new UrunObject(Integer.parseInt(object.getString("id")),Integer.parseInt(object.getString("urunKodu")),Integer.parseInt(object.getString("onsiparis")),object.getString("aciklama"),
                                resimObject,bedenObject));


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

                adapter=new UrunAdapter(getActivity(),urunObjects);
                urunler.setAdapter(adapter);
                flag_loading = false;


            }
            progress.dismiss();

        }
    }
    private class getDataNew extends AsyncTask<String, String, String> {
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
                String gelenData=readURL.readURL("http://modayakamoz.com/admin/get_urunler.php?kategori=2"  + "&page="+page+"&arama="+values[0] + "&durum=" + durum );
                JSONObject jsonObject = new JSONObject(gelenData);
                sayfaSayisi =(int) Math.ceil(Double.parseDouble(jsonObject.getString("sayfa"))) ;
                JSONArray array=jsonObject.getJSONArray("urunler");
                for (int i=0;i<array.length();i++){
                    bedenObject=new ArrayList<BedenObject>();
                    resimObject=new ArrayList<ResimObject>();
                    JSONObject object=array.getJSONObject(i);
                    JSONArray array1=object.getJSONArray("resimler");
                    JSONArray array2=object.getJSONArray("stok");

                    for (int j=0;j<array2.length();j++){
                        JSONObject stokobj=array2.getJSONObject(j);
                        if (!stokobj.getString("miktar").equals("0"))
                            bedenObject.add(new BedenObject(stokobj.getString("beden"),Integer.parseInt(stokobj.getString("miktar"))));
                    }
                    for (int j=0;j<array1.length();j++){
                        JSONObject resimobj=array1.getJSONObject(j);
                        resimObject.add(new ResimObject (resimobj.getString("b_resim"),resimobj.getString("k_resim")));

                    }
                    urunObjects.add(new UrunObject(Integer.parseInt(object.getString("id")),Integer.parseInt(object.getString("urunKodu")),Integer.parseInt(object.getString("onsiparis")),object.getString("aciklama"),

                            resimObject,bedenObject));
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

                adapter.notifyDataSetChanged();
                flag_loading = false;

            }
            progress.dismiss();

        }
    }
}
