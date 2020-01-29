package com.hedefsvr.modayakamozadmin.analiz;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hedefsvr.modayakamozadmin.R;

import java.util.List;

/**
 * Created by Lenovo- on 23.7.2019.
 */

public class TemsilciAnalizAdapter extends BaseAdapter  {
    private LayoutInflater mInflater;
    private Context context;
    private List<BayiAnalizObject> mKisiListesi;
    private List<BayiAnalizObject> mYedek;

    public TemsilciAnalizAdapter(Activity activity, List<BayiAnalizObject> illerObject) {
        //XML'i alıp View'a çevirecek inflater'ı örnekleyelim
        context = activity;
        mInflater = (LayoutInflater) activity.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        //gösterilecek listeyi de alalım
        mKisiListesi = illerObject;
        mYedek = illerObject;
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
        satirView = mInflater.inflate(R.layout.temsilci_analiz_item, null);
        final BayiAnalizObject illerObject = mKisiListesi.get(position);
        TextView bayi = (TextView) satirView.findViewById(R.id.textView83);
        TextView adet = (TextView) satirView.findViewById(R.id.textView84);
        LinearLayout genel = (LinearLayout) satirView.findViewById(R.id.genel);


        bayi.setText("Bayi: " + illerObject.getKomisyoncu());
        adet.setText("Toplam Satış: " + illerObject.getToplamSatis());

        genel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = ((AppCompatActivity)context).getSupportFragmentManager();
                KomisyoncuSatisFragment alertDialog = new KomisyoncuSatisFragment();
                alertDialog.setUrun(illerObject);
                alertDialog.show(fm,"fragment_alert");
            }
        });







        return satirView;
    }



}
