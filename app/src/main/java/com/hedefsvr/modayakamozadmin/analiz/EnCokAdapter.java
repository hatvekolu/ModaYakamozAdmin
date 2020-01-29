package com.hedefsvr.modayakamozadmin.analiz;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hedefsvr.modayakamozadmin.R;
import com.hedefsvr.modayakamozadmin.urunler.ImageActivity;

import java.util.List;

import it.sephiroth.android.library.picasso.Picasso;

/**
 * Created by Lenovo- on 19.12.2018.
 */

public class EnCokAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private Context context;
    private List<EnCokUrunObject> mKisiListesi;
    String extension;
    public EnCokAdapter(Activity activity, List<EnCokUrunObject> urunObjects) {
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
        satirView = mInflater.inflate(R.layout.en_cok_item, null);
        final EnCokUrunObject uo = mKisiListesi.get(position);
        ImageView tukendi=(ImageView)satirView.findViewById(R.id.imageView16);
        ImageView resim=(ImageView)satirView.findViewById(R.id.imageView9);
        TextView kod=(TextView)satirView.findViewById(R.id.textView21);
        TextView toplam=(TextView)satirView.findViewById(R.id.textView52);
        extension = uo.getResimObject().get(0).getB_resim().substring(uo.getResimObject().get(0).getB_resim().lastIndexOf("."));
        resim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!extension.toLowerCase().equals(".mp4"))    {
                    Intent intent = new Intent(context, ImageActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    intent.putExtra("type","image");
                    intent.putExtra("link","http://modayakamoz.com/resimler/" + uo.getResimObject().get(0).getB_resim());
                    context.startActivity(intent);
                }
                else if (extension.toLowerCase().equals(".mp4")){
                    Intent intent = new Intent(context, ImageActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    intent.putExtra("type","video");
                    intent.putExtra("link","http://modayakamoz.com/resimler/" + uo.getResimObject().get(0).getB_resim());
                    context.startActivity(intent);
                }

            }
        });
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

        kod.setText("Ürün Kodu: "+uo.getKod());
        toplam.setText("Toplam: "+uo.getToplam());

        if (uo.getBedenObjects().size() == 0)
            tukendi.setVisibility(View.VISIBLE);

        return satirView;
    }
}
