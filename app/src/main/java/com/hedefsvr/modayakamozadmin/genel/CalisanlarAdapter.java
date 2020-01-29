package com.hedefsvr.modayakamozadmin.genel;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hedefsvr.modayakamozadmin.R;
import com.hedefsvr.modayakamozadmin.helper.ReadURL;

import java.util.List;



public class CalisanlarAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private Context context;
    private List<CalisanObject> mKisiListesi;
    private CalisanlarFragment calisanlarFragment;
    public CalisanlarAdapter(Activity activity, List<CalisanObject> calisanObjects, CalisanlarFragment cf  ) {
        //XML'i alıp View'a çevirecek inflater'ı örnekleyelim
        context = activity;
        mInflater = (LayoutInflater) activity.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        //gösterilecek listeyi de alalım
        mKisiListesi = calisanObjects;
        calisanlarFragment = cf;
    }
    @Override
    public int getCount() {
        return mKisiListesi.size();
    }

    @Override
    public Object getItem(int position) {
        return mKisiListesi.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final View satirView;
        satirView = mInflater.inflate(R.layout.calisanlar_list_item, null);
        final CalisanObject calisanObject = mKisiListesi.get(position);
        final CheckBox check = (CheckBox)satirView.findViewById(R.id.checkBox2);
        TextView hazir = (TextView)satirView.findViewById(R.id.textView87);
        TextView bekle = (TextView)satirView.findViewById(R.id.textView88);
        LinearLayout back = (LinearLayout) satirView.findViewById(R.id.back);
        Button para = (Button)satirView.findViewById(R.id.button25);
        if (calisanObject.getOdeme().equals("1") && calisanObject.getDurum().equals("1")){
            back.setBackgroundColor(0x5DFF4640);
            para.setVisibility(View.VISIBLE);
        }
        para.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                new paraAl().execute(calisanObject.getId());
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Maaş Ödendi!").setPositiveButton("EVET", dialogClickListener)
                        .setNegativeButton("HAYIR", dialogClickListener).show();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = ((AppCompatActivity)context).getSupportFragmentManager();
                CalisanDetay alertDialog = new CalisanDetay();
                alertDialog.setCalisan(calisanObject);
                alertDialog.setFragment(calisanlarFragment);
                alertDialog.show(fm,"fragment_alert");
            }
        });
        if (calisanObject.getSiparis().equals("1"))
            check.setChecked(true);
        else
            check.setChecked(false);
        check.setText(calisanObject.getKullanciAdi());
        hazir.setText(calisanObject.getHazir());
        bekle.setText(calisanObject.getBekleyen());
        if (calisanObject.getDurum().equals("0"))
            check.setEnabled(false);
        else
            check.setEnabled(true);
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check.isChecked()){
                    new getData().execute("1",calisanObject.getId());
                }
                else
                    new getData().execute("0",calisanObject.getId());
            }
        });

        return satirView;
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

                String gelenData=readURL.readURL("http://modayakamoz.com/admin/calisan_duzenle.php?siparis="+values[0]+"&ID="+values[1]);

                return "1";
            }
            catch (Exception e){
                return "HATA";
            }

        }

        @Override
        protected void onPostExecute(String results)
        {
            calisanlarFragment.yenile();


        }
    }
    private class paraAl extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() {


        }

        @Override
        protected String doInBackground(String... values)
        {



            ReadURL readURL=new ReadURL();
            try{

                String gelenData=readURL.readURL("http://modayakamoz.com/admin/para.php?ID="+values[0]);

                return "1";
            }
            catch (Exception e){
                return "HATA";
            }

        }

        @Override
        protected void onPostExecute(String results)
        {
            calisanlarFragment.yenile();


        }
    }

}
