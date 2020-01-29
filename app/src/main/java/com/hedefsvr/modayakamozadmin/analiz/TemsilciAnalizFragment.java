package com.hedefsvr.modayakamozadmin.analiz;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.hedefsvr.modayakamozadmin.R;
import com.hedefsvr.modayakamozadmin.helper.ReadURL;
import com.hedefsvr.modayakamozadmin.helper.YuklemeFragment;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class TemsilciAnalizFragment extends Fragment {
    List<BayiAnalizObject> bayiObjects;
    ListView listView;
    TemsilciAnalizAdapter analizAdapter;
    Spinner spinner ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_temsilci_analiz, container, false);
        listView = (ListView)view.findViewById(R.id.list);
        spinner =(Spinner)view.findViewById(R.id.spinner1);
        Calendar c = Calendar.getInstance();
        int month = c.get(Calendar.MONTH);

        List<String> categories = new ArrayList<String>();
        categories.add("OCAK"  );categories.add("ŞUBAT");categories.add("MART");categories.add("NİSAN");categories.add("MAYIS");
        categories.add("HAZİRAN");categories.add("TEMMUZ");categories.add("AĞUSTOS");categories.add("EYLÜL");categories.add("EKİM");
        categories.add("KASIM");categories.add("ARALIK");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        spinner.setSelection(month);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                new getData().execute(""+(position+1));
            } // to close the onItemSelected
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });

        return view;
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
            bayiObjects=new ArrayList<BayiAnalizObject>();

            ReadURL readURL=new ReadURL();
            try{
                String gelenData=readURL.readURL("http://modayakamoz.com/admin/get_komisyoncu_satis.php?ay="+values[0]);
                JSONArray array=new JSONArray(gelenData);
                for (int i=0;i<array.length();i++){
                    JSONObject object1=array.getJSONObject(i);
                    bayiObjects.add(new BayiAnalizObject(object1.getString("komisyoncu_adi"),object1.getString("id"),object1.getString("toplamSatis")));
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

                analizAdapter=new TemsilciAnalizAdapter(getActivity(),bayiObjects);
                listView.setAdapter(analizAdapter);

            } progress.dismiss();

        }
    }
}
