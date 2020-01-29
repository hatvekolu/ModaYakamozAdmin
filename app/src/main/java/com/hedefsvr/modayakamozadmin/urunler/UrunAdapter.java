package com.hedefsvr.modayakamozadmin.urunler;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hedefsvr.modayakamozadmin.R;

import java.util.List;

import it.sephiroth.android.library.picasso.Picasso;

public class UrunAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private Context context;
    private List<UrunObject> mKisiListesi;
    String extension;
    public UrunAdapter(Activity activity, List<UrunObject> urunObjects) {
        //XML'i alıp View'a çevirecek inflater'ı örnekleyelim
        context = activity;
        mInflater = (LayoutInflater) activity.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        //gösterilecek listeyi de alalım
        mKisiListesi = urunObjects;
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
        satirView = mInflater.inflate(R.layout.urun_grid_item, null);
        final UrunObject uo = mKisiListesi.get(position);

        ImageView resim=(ImageView)satirView.findViewById(R.id.imageView9);
        ImageView tukendi=(ImageView)satirView.findViewById(R.id.imageView16);
        TextView kod=(TextView)satirView.findViewById(R.id.textView21);
        TextView onsiparis = (TextView)satirView.findViewById(R.id.textView59);

        if (uo.getOnSiparis() > 0 && uo.getBedenObject().size()>0)
            onsiparis.setVisibility(View.VISIBLE);
        extension = uo.getResimObject().get(0).getB_resim().substring(uo.getResimObject().get(0).getB_resim().lastIndexOf("."));
        if (!extension.toLowerCase().equals(".mp4")){
            try{
                Picasso.with(context).load("http://modayakamoz.com/resimler_k/"+uo.getResimObject().get(0).getB_resim()).into(resim);
            }
            catch (Exception e){
                Picasso.with(context).load(R.drawable.yukleme).into(resim);
            }
        }
        else {
            Picasso.with(context).load(R.drawable.images).into(resim);
        }
        if (uo.getBedenObject().size()==0)
            tukendi.setVisibility(View.VISIBLE);
        kod.setText("Ürün Kodu: "+uo.getKod());



        return satirView;
    }




}