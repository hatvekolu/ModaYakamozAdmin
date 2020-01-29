package com.hedefsvr.modayakamozadmin.urunler;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hedefsvr.modayakamozadmin.R;

import java.util.List;

/**
 * Created by Lenovo- on 26.7.2018.
 */

public class BedenAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private Context context;
    private List<BedenObject> mKisiListesi;
    public BedenAdapter(Activity activity, List<BedenObject> bedenObjects ) {
        //XML'i alıp View'a çevirecek inflater'ı örnekleyelim
        context = activity;
        mInflater = (LayoutInflater) activity.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        //gösterilecek listeyi de alalım
        mKisiListesi = bedenObjects;
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
        satirView = mInflater.inflate(R.layout.beden_list_item, null);
        final BedenObject bedenObject = mKisiListesi.get(position);
        TextView beden=(TextView)satirView.findViewById(R.id.textView2);
        TextView adet=(TextView)satirView.findViewById(R.id.textView58);

        beden.setText(""+bedenObject.getBeden()+" :");
        adet.setText(""+bedenObject.getAdet());
        return satirView;
    }
}
