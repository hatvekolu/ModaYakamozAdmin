package com.hedefsvr.modayakamozadmin.urunler;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hedefsvr.modayakamozadmin.R;
import com.hedefsvr.modayakamozadmin.siparis.SatisUrunObject;

import java.util.List;

import it.sephiroth.android.library.picasso.Picasso;

/**
 * Created by Lenovo- on 29.11.2017.
 */

public class UrunDetayAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private Context context;
    private List<SatisUrunObject> mKisiListesi;
    private List<SatisUrunObject> mYedek;

    public UrunDetayAdapter(Activity activity, List<SatisUrunObject> sepetObjects) {
        //XML'i alıp View'a çevirecek inflater'ı örnekleyelim
        context = activity;
        mInflater = (LayoutInflater) activity.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        //gösterilecek listeyi de alalım
        mKisiListesi = sepetObjects;
        mYedek=sepetObjects;

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
        View satirView;
        satirView = mInflater.inflate(R.layout.sepet_list_item, null);
        final SatisUrunObject satisUrunObject = mKisiListesi.get(position);
        ImageView resim=(ImageView)satirView.findViewById(R.id.imageView11);
        TextView tutar=(TextView)satirView.findViewById(R.id.textView31);
        TextView beden=(TextView)satirView.findViewById(R.id.textView32);
        TextView adet=(TextView)satirView.findViewById(R.id.textView33);
        TextView marka=(TextView)satirView.findViewById(R.id.textView28);
        TextView yer=(TextView)satirView.findViewById(R.id.textView69);


        try {
            Picasso.with(context).load("http://modayakamoz.com/resimler_k/"+satisUrunObject.getResimObject().get(0).getB_resim()).into(resim);
        }
        catch (Exception e){

        }
        resim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentActivity activity = (FragmentActivity)(context);
                FragmentManager fm = activity.getSupportFragmentManager();
                DetayFragment alertDialog = new DetayFragment();
                alertDialog.setUrun(Integer.parseInt(satisUrunObject.getId())-1000);
                alertDialog.setKategori(satisUrunObject.getKategori());
                alertDialog.show(fm,"fragment_alert");


            }
        });
        tutar.setText("Tutar: "+satisUrunObject.getTl()+ " ₺");
        beden.setText("Beden: "+satisUrunObject.getBedenObject().getBeden());
        adet.setText("Adet: "+satisUrunObject.getBedenObject().getAdet());
        yer.setText("Raf: " + satisUrunObject.getYer().toString());
        if (satisUrunObject.getBedenObject().getAdet()>1)
            adet.setBackgroundColor(0xffff8800);
        marka.setText("Marka: "+satisUrunObject.getMarka()+ " Kod: "  + satisUrunObject.getId());




        return satirView;
    }
}
