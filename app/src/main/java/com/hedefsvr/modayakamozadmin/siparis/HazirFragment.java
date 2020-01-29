package com.hedefsvr.modayakamozadmin.siparis;


import android.content.Context;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hedefsvr.modayakamozadmin.R;
import com.hedefsvr.modayakamozadmin.helper.DBHelper;
import com.hedefsvr.modayakamozadmin.helper.ReadURL;
import com.hedefsvr.modayakamozadmin.helper.YuklemeFragment;
import com.sdsmdg.tastytoast.TastyToast;

import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 */
public class HazirFragment extends DialogFragment {
    private int index;
    public void setIndex(int index) { this.index=index;}

    private  SatisObject so;
    public void setUrun(SatisObject so) {
        this.so = so;
    }
    private BekleyenFragment bekleyenFragment;
    public void setBekleyenFragment(BekleyenFragment bekleyenFragment) {
        this.bekleyenFragment = bekleyenFragment;
    }

    private  SiparisDetayFragment siparisDetayFragment;
    public void setSiparisDetayFragment(SiparisDetayFragment siparisDetayFragment) {
        this.siparisDetayFragment = siparisDetayFragment;
    }
    TextView adSoyad,aciklama;
    EditText kesilen;
    Button yes,no;
    DBHelper dbHelper;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_hazir, container, false);
        adSoyad = (TextView)view.findViewById(R.id.textView53);
        kesilen = (EditText) view.findViewById(R.id.editText20);
        aciklama = (TextView) view.findViewById(R.id.textView42);
        kesilen.setText(""+so.getKesilen_tutar());
        yes = (Button)view.findViewById(R.id.button16);
        no = (Button)view.findViewById(R.id.button15);
        dbHelper = new DBHelper(getContext());
        if(so.getAciklama().length() > 0) {
            aciklama.setVisibility(View.VISIBLE);
            aciklama.setText(so.getAciklama());
        }
        adSoyad.setText(""+so.getAd() + " " + so.getSoyad() + " Siparişi Hazırlandı!");
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (kesilen.getText().toString().length() > 0)
                    new getData().execute(""+so.getId(),kesilen.getText().toString());
                else
                    Toast.makeText(getContext(), "Geçerli Tutar Giriniz.", Toast.LENGTH_LONG).show();
            }
        });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
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

                String gelenData=readURL.readURL("http://modayakamoz.com/admin/siparis_duzenle.php?ID="+values[0] + "&tutar="+ values[1]+"&calisan_ID="+dbHelper.getUser().getID());
                JSONObject object =new JSONObject(gelenData);
                return object.getString("durum");
            }
            catch (Exception e){
                return "HATA";
            }

        }

        @Override
        protected void onPostExecute(String results)
        {
            if (!results.equals("HATA") && results.equals("hazir")){

                TastyToast.makeText(getContext(), so.getAd().toUpperCase()+" "+so.getSoyad().toUpperCase()+" DAHA ÖNCE HAZIRLANDI.", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                Vibrator v = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
                v.vibrate(500);
                final MediaPlayer mp = MediaPlayer.create(getContext(), R.raw.alarm);
                mp.start();
                siparisDetayFragment.dismiss();
                getDialog().dismiss();
                bekleyenFragment.yenile(index);
                progress.dismiss();


            }
            else {
                TastyToast.makeText(getContext(), so.getAd().toUpperCase()+" "+so.getSoyad().toUpperCase()+" HAZIRLANDI.", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);

                siparisDetayFragment.dismiss();
                getDialog().dismiss();
                bekleyenFragment.yenile(index);
                progress.dismiss();

            }


        }
    }
}
