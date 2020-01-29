package com.hedefsvr.modayakamozadmin.analiz;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hedefsvr.modayakamozadmin.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Lenovo- on 11.7.2019.
 */

public class IllereGoreAdapter extends BaseAdapter implements Filterable {
    private LayoutInflater mInflater;
    private Context context;
    private List<IllerObject> mKisiListesi;
    private List<IllerObject> mYedek;
    private ItemFilter mFilter = new ItemFilter();

    public IllereGoreAdapter(Activity activity, List<IllerObject> illerObject) {
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
        satirView = mInflater.inflate(R.layout.list_bayi_item, null);
        final IllerObject illerObject = mKisiListesi.get(position);
        TextView il = (TextView) satirView.findViewById(R.id.textView29);
        LinearLayout genel = (LinearLayout) satirView.findViewById(R.id.genel);

        il.setText(""+illerObject.getIl());



        genel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = ((AppCompatActivity)context).getSupportFragmentManager();
                IllereGoreSatis alertDialog = new IllereGoreSatis();
                alertDialog.setIl(illerObject);
                alertDialog.show(fm,"fragment_alert");
            }
        });







        return satirView;
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        mKisiListesi.clear();
        if (charText.length() == 0) {
            mKisiListesi.addAll(mYedek);
        } else {
            for (IllerObject wp : mYedek) {
                if ((wp.getIl()).toLowerCase(Locale.getDefault())
                        .contains(charText)) {
                    mKisiListesi.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        return mFilter;
    }

    private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            String filterString = constraint.toString().toLowerCase();

            FilterResults results = new FilterResults();

            final List<IllerObject> list = mYedek;

            int count = list.size();
            final ArrayList<IllerObject> nlist = new ArrayList<IllerObject>(count);

            String filterableString ;

            for (int i = 0; i < count; i++) {
                filterableString = list.get(i).getIl();
                if (filterableString.toLowerCase().contains(filterString)) {
                    nlist.add(list.get(i));
                }
            }

            results.values = nlist;
            results.count = nlist.size();

            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mKisiListesi = (ArrayList<IllerObject>) results.values;
            notifyDataSetChanged();
        }

    }
}
