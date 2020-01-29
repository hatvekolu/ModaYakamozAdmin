package com.hedefsvr.modayakamozadmin.analiz;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hedefsvr.modayakamozadmin.R;
import com.hedefsvr.modayakamozadmin.helper.ReadURL;
import com.hedefsvr.modayakamozadmin.helper.YuklemeFragment;

import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.models.BarModel;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 */
public class AylaraGoreSatisFragment extends Fragment {

    List<AylaraGoreObject> aylaraGoreObjects;
    BarChart mBarChart;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_aylara_gore_satis, container, false);
        mBarChart = (BarChart)view.findViewById(R.id.barchart);
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
            aylaraGoreObjects=new ArrayList<AylaraGoreObject>();

            ReadURL readURL=new ReadURL();
            try{
                Random r = new Random();
                int i1 = (r.nextInt(80) + 65);
                String gelenData=readURL.readURL("http://modayakamoz.com/admin/get_satis_aylar.php");
                JSONArray array=new JSONArray(gelenData);



                for (int i=0;i<array.length();i++){
                    JSONObject object1=array.getJSONObject(i);
                    aylaraGoreObjects.add(new AylaraGoreObject(object1.getString("yil"),object1.getString("ay"),
                            Integer.parseInt(object1.getString("bekleyen")),Integer.parseInt(object1.getString("yolda")),
                            Integer.parseInt(object1.getString("donen")),Integer.parseInt(object1.getString("ulasan"))
                    ,Double.parseDouble(object1.getString("yuzde"))));
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

                for (int i=0;i<aylaraGoreObjects.size();i++){
                    mBarChart.addBar(new BarModel(aylaraGoreObjects.get(i).getAy(),Float.parseFloat(""+aylaraGoreObjects.get(i).getYuzde()), 0xFFFF4081));
                }

            }mBarChart.startAnimation();
            progress.dismiss();

        }
    }
}
