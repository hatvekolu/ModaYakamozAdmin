package com.hedefsvr.modayakamozadmin.temsilci;


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

import com.hedefsvr.modayakamozadmin.R;
import com.hedefsvr.modayakamozadmin.helper.ReadURL;
import com.hedefsvr.modayakamozadmin.helper.YuklemeFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class TemsilciEditFragment extends DialogFragment {

    EditText ad,soyad,tel,banka,iban,komOrani,komTutar;
    private TemsilciObject to;
    private TemsilciFragment tf;
    public void setTemsilciFragment(TemsilciFragment tf){
        this.tf=tf;
    }
    public void setDetay(TemsilciObject to) {
        this.to = to;
    }
    Button kaydet;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_temsilci_edit, container, false);

        ad=(EditText)view.findViewById(R.id.editText2);
        soyad=(EditText)view.findViewById(R.id.editText4);
        tel=(EditText)view.findViewById(R.id.editText5);
        banka=(EditText)view.findViewById(R.id.editText6);
        iban=(EditText)view.findViewById(R.id.editText7);
        komOrani=(EditText)view.findViewById(R.id.editText8);
        komTutar=(EditText)view.findViewById(R.id.editText9);
        kaydet=(Button) view.findViewById(R.id.button3);

        ad.setText(""+to.getAd());
        soyad.setText(""+to.getSoyad());
        tel.setText(""+to.getTel());
        banka.setText(""+to.getBanka());
        iban.setText(""+to.getIban());
        komOrani.setText(""+to.getKomOrani());
        komTutar.setText(""+to.getKomEk());

        kaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new getData().execute(""+to.getId(),ad.getText().toString(),soyad.getText().toString(),tel.getText().toString(),
                        banka.getText().toString(),iban.getText().toString(),komOrani.getText().toString(),komTutar.getText().toString());

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

                String gelenData=readURL.readURL("http://modayakamoz.com/admin/komisyoncu_duzenle.php?ID="+values[0]+"&ad="+values[1]+"&soyad="+values[2]+"&tel="+values[3]+"&banka="+values[4]+"&iban="+values[5]+"&komOrani="+values[6]+"&komTutar="+values[7]);



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
            } progress.dismiss();

        }
    }
}
