package com.hedefsvr.modayakamozadmin.analiz;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.hedefsvr.modayakamozadmin.urunler.BedenObject;
import com.hedefsvr.modayakamozadmin.R;
import com.hedefsvr.modayakamozadmin.helper.ReadURL;
import com.hedefsvr.modayakamozadmin.urunler.ResimObject;
import com.hedefsvr.modayakamozadmin.helper.YuklemeFragment;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class GununEnCok extends Fragment {


    List<EnCokUrunObject> enCokUrunObjects;
    List<ResimObject>resimObject;
    List<BedenObject>bedenObjects;
    EnCokAdapter adapter;
    GridView urunler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_gunun_en_cok, container, false);

        urunler = (GridView)view.findViewById(R.id.grid_urun);
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



            ReadURL readURL=new ReadURL();

            try{
                String gelenData=readURL.readURL("http://modayakamoz.com/json/get_urun_satis_encok.php");
                JSONArray array=new JSONArray(gelenData);
                enCokUrunObjects=new ArrayList<EnCokUrunObject>();
                enCokUrunObjects.clear();
                for (int i=0;i<array.length();i++){
                    resimObject=new ArrayList<ResimObject>();
                    bedenObjects=new ArrayList<BedenObject>();
                    JSONObject object=array.getJSONObject(i);
                    JSONArray array1=object.getJSONArray("resimler");
                    JSONArray array2=object.getJSONArray("stok");
                    for (int k=0;k<array1.length();k++){
                        JSONObject object1 = array1.getJSONObject(k);
                        resimObject.add(new ResimObject(object1.getString("b_resim"),""));
                    }
                    for (int k=0;k<array2.length();k++){
                        JSONObject object1 = array2.getJSONObject(k);
                        bedenObjects.add(new BedenObject(object1.getString("beden"),Integer.parseInt(object1.getString("miktar"))));
                    }
                    enCokUrunObjects.add(new EnCokUrunObject(Integer.parseInt(object.getString("urun_ID")),Integer.parseInt(object.getString("urun_ID"))+1000,Integer.parseInt(object.getString("toplam")),resimObject,bedenObjects));
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

                adapter=new EnCokAdapter(getActivity(),enCokUrunObjects);
                urunler.setAdapter(adapter);



            }
            progress.dismiss();
        }
    }
}
