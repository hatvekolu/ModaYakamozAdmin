package com.hedefsvr.modayakamozadmin.genel;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
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
public class PreOrderTesettur extends Fragment {


    GridView urunler;

    List<PreOrderObject> preOrderObjects;
    List<ResimObject>resimObject;
    List<BedenObject> bedenObject;
    EditText arama;
    PreOrderAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pre_order, container, false);
        urunler = (GridView)view.findViewById(R.id.urun_grid);
        arama = (EditText)view.findViewById(R.id.editText19);
        new getData().execute();
        urunler.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {

                PreOrderObject data= (PreOrderObject) adapter.getItem(position);
                FragmentActivity activity = (FragmentActivity)(getContext());
                FragmentManager fm = activity.getSupportFragmentManager();
                AlindiFragment alertDialog = new AlindiFragment();
                alertDialog.setPre(data);
                alertDialog.setKategori("3");
                alertDialog.setPot(PreOrderTesettur.this);
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
            preOrderObjects=new ArrayList<PreOrderObject>();


            ReadURL readURL=new ReadURL();
            try{
                String gelenData=readURL.readURL("http://modayakamoz.com/admin/get_pre_urunler.php?kategori=3");
                JSONArray array=new JSONArray(gelenData);
                if (array.length()==0)
                    return "HATA";
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
                    preOrderObjects.add(new PreOrderObject(object.getString("id"),object.getString("urunKodu"),object.getString("cikisTL"),
                            object.getString("onsiparis"),
                            object.getString("marka"),
                            object.getString("preOrder"),
                            object.getString("seri"),
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
                adapter=new PreOrderAdapter(getActivity(),preOrderObjects);
                urunler.setAdapter(adapter);

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
                adapter=new PreOrderAdapter(getActivity(),preOrderObjects);
                urunler.setAdapter(adapter);
            }

            progress.dismiss();

        }
    }
    public void  yenile(){
        new getData().execute();
    }
}
