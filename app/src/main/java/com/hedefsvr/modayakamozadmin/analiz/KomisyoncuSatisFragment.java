package com.hedefsvr.modayakamozadmin.analiz;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.hedefsvr.modayakamozadmin.R;
import com.hedefsvr.modayakamozadmin.helper.ReadURL;
import com.hedefsvr.modayakamozadmin.helper.YuklemeFragment;
import com.hedefsvr.modayakamozadmin.genel.AylarObject;

import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.models.BarModel;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class KomisyoncuSatisFragment extends DialogFragment {

    ArrayList<BayiObject> bayiObjects;
    BilgiObject bilgiObjects;
    AramaAdapter adapter;
    AutoCompleteTextView arama;
    BarChart mBarChart;
    List<AylarObject> aylarObjects;
    Spinner spinner ;
    TextView ulasan,donen,yolda,bekleyen,yuzde,hakedis,cekilen_tutar,kesilen_tutar,bakiye;
    private  BayiAnalizObject bao;
    public void setUrun(BayiAnalizObject bao) {
        this.bao = bao;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_komisyoncu_satis, container, false);
        mBarChart = (BarChart)view.findViewById(R.id.barchart1);
        ulasan = (TextView)view.findViewById(R.id.textView30);
        donen = (TextView)view.findViewById(R.id.textView34);
        yolda = (TextView)view.findViewById(R.id.textView38);
        bekleyen = (TextView)view.findViewById(R.id.textView39);
        yuzde = (TextView)view.findViewById(R.id.textView40);
        hakedis = (TextView)view.findViewById(R.id.textView41);
        cekilen_tutar = (TextView)view.findViewById(R.id.textView49);
        kesilen_tutar = (TextView)view.findViewById(R.id.textView50);
        bakiye = (TextView)view.findViewById(R.id.textView51);
        spinner =(Spinner)view.findViewById(R.id.spinner1);
        arama = (AutoCompleteTextView)view.findViewById(R.id.autoCompleteTextView);
        arama.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int position, long arg3) {
                Object item = parent.getItemAtPosition(position);
                if (item instanceof BayiObject){
                    mBarChart.clearChart();
                    BayiObject student=(BayiObject) item;
                    new getData1().execute(""+student.getId());
                }
            }
        });
        List<String> categories = new ArrayList<String>();
        categories.add("ADET"  );categories.add("HAKEDİŞ");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {

                if(bilgiObjects!= null){
                if (position == 0){
                    mBarChart.clearChart();
                    for (int i=0;i<bilgiObjects.getAylarObjects().size();i++){
                        mBarChart.addBar(new BarModel(bilgiObjects.getAylarObjects().get(i).getAy(),Float.parseFloat(""+bilgiObjects.getAylarObjects().get(i).getAdet()), 0xFFFF4081));
                    }
                    mBarChart.startAnimation();
                }
                else
                {
                    mBarChart.clearChart();
                    for (int i=0;i<bilgiObjects.getAylarObjects().size();i++){
                        mBarChart.addBar(new BarModel(bilgiObjects.getAylarObjects().get(i).getAy(),Float.parseFloat(""+bilgiObjects.getAylarObjects().get(i).getTutar()), 0xFFFF4081));
                    }
                    mBarChart.startAnimation();
                }
            }

            } // to close the onItemSelected
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });
        new getData().execute();
        new getData1().execute(""+bao.getId());
        return  view;
    }
    public class getData extends AsyncTask<String, String, String> {
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
            bayiObjects=new ArrayList<BayiObject>();

            ReadURL readURL=new ReadURL();
            try{
                String gelenData=readURL.readURL("http://modayakamoz.com/admin/get_temsilci.php");
                JSONArray array=new JSONArray(gelenData);



                for (int i=0;i<array.length();i++){
                    JSONObject object1=array.getJSONObject(i);
                    bayiObjects.add(new BayiObject(object1.getInt("ID"),object1.getString("adSoyad"),object1.getString("bayiAd")));
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
                AramaAdapter customerAdapter = new AramaAdapter(getContext(), R.layout.list_bayi_item, bayiObjects);
                arama.setAdapter(customerAdapter);

            } progress.dismiss();

        }
    }
    private class getData1 extends AsyncTask<String, String, String> {
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
            aylarObjects=new ArrayList<AylarObject>();
            aylarObjects.clear();
            ReadURL readURL=new ReadURL();
            try{
                String gelenData=readURL.readURL("http://modayakamoz.com/admin/get_komisyoncu_satis_aylar.php?ID=" + values[0]);
                JSONObject object=new JSONObject(gelenData);
                JSONArray array=object.getJSONArray("aylik");
                for (int i=0;i<array.length();i++){
                    JSONObject object1=array.getJSONObject(i);
                    aylarObjects.add(new AylarObject(object1.getInt("yil"),object1.getString("ay"),
                            object1.getInt("adet"),
                            object1.getDouble("hakedis")));
                }


                bilgiObjects=new BilgiObject(object.getString("komisyoncu"),object.getString("ulasan"),
                        object.getString("donen"),object.getString("yolda"),object.getString("bekleyen"),object.getString("hakedis"),
                        object.getString("kesilen_tutar"),object.getString("cekilen_tutar"),object.getString("yuzde"),object.getString("bakiye"),aylarObjects);



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
                spinner.setSelection(0);
                mBarChart.clearChart();
                for (int i=0;i<bilgiObjects.getAylarObjects().size();i++){
                    mBarChart.addBar(new BarModel(bilgiObjects.getAylarObjects().get(i).getAy(),Float.parseFloat(""+bilgiObjects.getAylarObjects().get(i).getAdet()), 0xFFFF4081));
                }
                mBarChart.startAnimation();
                ulasan.setText("Ulaşan Kargo Sayısı: " + bilgiObjects.getUlasan());
                donen.setText("Dönen Kargo Sayısı: " + bilgiObjects.getDonen());
                yolda.setText("Yolda Olan Kargo Sayısı: " + bilgiObjects.getYolda());
                bekleyen.setText("Bekleyen Kargo Sayısı: " + bilgiObjects.getBekleyen());
                yuzde.setText("Ulaşma Yüzdesi: %" + bilgiObjects.getYuzde());
                hakedis.setText("Toplam Hakediş: " + bilgiObjects.getHakedis()+"₺");
                cekilen_tutar.setText("Çekilen Tutar: " + bilgiObjects.getCekilen_tutar()+"₺");
                kesilen_tutar.setText("Kesilen Tutar: " + bilgiObjects.getKesilen_tutar()+"₺");
                bakiye.setText("Güncel Bakiye: " + bilgiObjects.getBakiye()+"₺");
            }
            else {
                ulasan.setText("Ulaşan Kargo Sayısı: ");
                donen.setText("Dönen Kargo Sayısı: ");
                yolda.setText("Yolda Olan Kargo Sayısı: ");
                bekleyen.setText("Bekleyen Kargo Sayısı: ");
                yuzde.setText("Ulaşma Yüzdesi: %");
                hakedis.setText("Toplam Hakediş: ");
                cekilen_tutar.setText("Çekilen Tutar: ");
                kesilen_tutar.setText("Kesilen Tutar: ");
                bakiye.setText("Güncel Bakiye: ");
                Toast.makeText(getContext(), "Veri Bulunamadı.", Toast.LENGTH_LONG).show();
            }
            progress.dismiss();

        }
    }
}
