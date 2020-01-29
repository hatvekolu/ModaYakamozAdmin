package com.hedefsvr.modayakamozadmin.siparis;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Spinner;

import com.hedefsvr.modayakamozadmin.urunler.BedenObject;
import com.hedefsvr.modayakamozadmin.urunler.DetayFragment;
import com.hedefsvr.modayakamozadmin.R;
import com.hedefsvr.modayakamozadmin.helper.ReadURL;
import com.hedefsvr.modayakamozadmin.urunler.ResimObject;
import com.hedefsvr.modayakamozadmin.urunler.UrunAdapter;
import com.hedefsvr.modayakamozadmin.urunler.UrunObject;
import com.hedefsvr.modayakamozadmin.helper.YuklemeFragment;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class SiparisiBekleyenUrunler extends Fragment {

    List<UrunObject> urunObjects;
    List<ResimObject>resimObject;
    List<BedenObject> bedenObject;
    UrunAdapter adapter;
    Spinner spinner ;
    int kategori=1;

    GridView urunler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_siparisi_bekleyen_urunler, container, false);

        urunler=(GridView) view.findViewById(R.id.urunlerr);
        spinner =(Spinner)view.findViewById(R.id.spinner1);
        new getData().execute("1");
        List<String> categories = new ArrayList<String>();
        categories.add("ELBİSE"  );categories.add("ÇANTA");categories.add("TESETTÜR");;categories.add("AYAKKABI");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                new getData().execute(""+(position+1));
                kategori=position;
            } // to close the onItemSelected
            public void onNothingSelected(AdapterView<?> parent)
            {

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
                alertDialog.setKategori(kategori+1);
                alertDialog.show(fm,"fragment_alert");

                View view1 = getActivity().getCurrentFocus();
                if (view1 != null) {
                    InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view1.getWindowToken(), 0);}

            }
        });
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

            urunObjects=new ArrayList<UrunObject>();
            urunObjects.clear();
            ReadURL readURL=new ReadURL();

            try{
                String gelenData=readURL.readURL("http://modayakamoz.com/admin/get_onsiparis.php?kategori="+values[0]);
                JSONArray array = new JSONArray(gelenData);

                for (int i=0;i<array.length();i++){
                    bedenObject=new ArrayList<BedenObject>();
                    resimObject=new ArrayList<ResimObject>();
                    JSONObject object=array.getJSONObject(i);
                    JSONArray array1=object.getJSONArray("resimler");
                    JSONArray array2=object.getJSONArray("stok");

                    for (int j=0;j<array2.length();j++){
                        JSONObject stokobj=array2.getJSONObject(j);
                        if (Integer.parseInt(stokobj.getString("miktar"))>0)
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


            }
            progress.dismiss();

        }
    }
}
