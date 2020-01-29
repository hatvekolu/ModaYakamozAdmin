package com.hedefsvr.modayakamozadmin.urunler;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hedefsvr.modayakamozadmin.R;
import com.hedefsvr.modayakamozadmin.helper.ReadURL;
import com.hedefsvr.modayakamozadmin.helper.YuklemeFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class ToptanciFragment extends DialogFragment {


    EditText topAd,topTel;
    Button kaydet;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_toptanci, container, false);

        topAd =(EditText)view.findViewById(R.id.editText100);
        topTel=(EditText)view.findViewById(R.id.editText101);
        kaydet=(Button)view.findViewById(R.id.button50);
        kaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (topAd.getText().toString().length()>0 && topTel.getText().toString().length()>0)
                    new getData().execute(topAd.getText().toString(),topTel.getText().toString());
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


            ReadURL readURL=new ReadURL();

            try{
                String gelenData=readURL.readURL("http://modayakamoz.com/admin/toptanci_ekle.php?toptanci="+values[0]+"&tel="+values[1]);



                return gelenData;
            }
            catch (Exception e){
                return "HATA";
            }

        }

        @Override
        protected void onPostExecute(String results)
        {
            if (results.length() > 5 && !results.equals("HATA")){

                Toast.makeText(getContext(),"Toptancı Eklendi",Toast.LENGTH_LONG).show();


            }
            else
                Toast.makeText(getContext(),"Toptancı mevcut",Toast.LENGTH_LONG).show();
            progress.dismiss();

        }
    }
}
