package com.hedefsvr.modayakamozadmin.temsilci;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.hedefsvr.modayakamozadmin.R;
import com.hedefsvr.modayakamozadmin.helper.ReadURL;


/**
 * A simple {@link Fragment} subclass.
 */
public class TemsilciEkleFragment extends DialogFragment {

    Button ekle;
    EditText ad,sifre,tel,komOran,komTutar;
    TemsilciFragment tf;
    public void setTemsilciFragment(TemsilciFragment tf){
        this.tf=tf;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_temsilci_ekle, container, false);
        ekle=(Button)view.findViewById(R.id.button5);
        ad=(EditText) view.findViewById(R.id.editText10);
        sifre=(EditText)view.findViewById(R.id.editText11);
        tel=(EditText)view.findViewById(R.id.editText12);
        komOran=(EditText)view.findViewById(R.id.editText13);
        komTutar=(EditText)view.findViewById(R.id.editText14);

        ekle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ad.getText().toString().length()>0 && sifre.getText().toString().length()>0 && tel.getText().toString().length()>0 &&
                        komOran.getText().toString().length()>0 && komTutar.getText().toString().length()>0 )


                    new getData().execute(ad.getText().toString(),sifre.getText().toString(),tel.getText().toString()
                    ,komOran.getText().toString(),komTutar.getText().toString());
            }
        });
        return view;
    }
    private class getData extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() {



        }

        @Override
        protected String doInBackground(String... values)
        {

            ReadURL readURL=new ReadURL();
            try{

                String gelenData=readURL.readURL("http://modayakamoz.com/admin/komisyoncu_ekle.php?ad="+values[0]+"&sifre="+values[1]+"&tel="+values[2]+"&yuzde="+values[3]+"&tl="+values[4]);



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

            tf.new getData().execute();
                getDialog().dismiss();

            }

        }
    }
}
