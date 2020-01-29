package com.hedefsvr.modayakamozadmin.genel;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.hedefsvr.modayakamozadmin.R;
import com.hedefsvr.modayakamozadmin.helper.ReadURL;
import com.hedefsvr.modayakamozadmin.helper.YuklemeFragment;


public class KomisyonOranFragment extends Fragment {

    Button kaydet;
    EditText komoran,tl;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_komisyon_oran, container, false);

        kaydet=(Button) view.findViewById(R.id.button10);
        komoran=(EditText) view.findViewById(R.id.editText17);
        tl=(EditText) view.findViewById(R.id.editText18);

        kaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (komoran.getText().toString().length()>0 && tl.getText().toString().length()>0)
                    new getData().execute(komoran.getText().toString(),tl.getText().toString());
                komoran.setText("");
                tl.setText("");
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

                String gelenData=readURL.readURL("http://modayakamoz.com/admin/komisyonduzenle.php?komoran="+values[0]+"&tl="+values[1]);



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


            } progress.dismiss();

        }
    }
}
