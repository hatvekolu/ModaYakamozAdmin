package com.hedefsvr.modayakamozadmin.genel;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.hedefsvr.modayakamozadmin.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import it.sephiroth.android.library.picasso.Picasso;


public class PreOrderAdapter extends BaseAdapter implements Filterable {


    private LayoutInflater mInflater;
    private Context context;
    private List<PreOrderObject> mKisiListesi;
    private List<PreOrderObject> mYedek;
    private ItemFilter mFilter = new ItemFilter();
    public PreOrderAdapter(Activity activity, List<PreOrderObject> preOrderObjects  ) {
        //XML'i alıp View'a çevirecek inflater'ı örnekleyelim
        context = activity;
        mInflater = (LayoutInflater) activity.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        //gösterilecek listeyi de alalım
        mKisiListesi = preOrderObjects;
        mYedek=preOrderObjects;
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
        satirView = mInflater.inflate(R.layout.urun_grid_item, null);
        final PreOrderObject preOrderObject = mKisiListesi.get(position);
        ImageView resim=(ImageView)satirView.findViewById(R.id.imageView9);
        TextView kod=(TextView)satirView.findViewById(R.id.textView21);
        TextView onsiparis = (TextView)satirView.findViewById(R.id.textView59);
        onsiparis.setVisibility(View.VISIBLE);
        onsiparis.setText(preOrderObject.getMarka()+" "+preOrderObject.getSeri()+" Seri");
        onsiparis.setTextSize(13);
        Picasso.with(context).load("http://modayakamoz.com/resimler_k/"+preOrderObject.getResimObject().get(0).getB_resim()).into(resim);
        kod.setText("Ürün Kodu: "+preOrderObject.getUrunKodu());

        return satirView;
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        mKisiListesi.clear();
        if (charText.length() == 0) {
            mKisiListesi.addAll(mYedek);
        } else {
            for (PreOrderObject wp : mYedek) {
                if ((wp.getMarka()+" "+wp.getUrunKodu()).toLowerCase(Locale.getDefault())
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

            final List<PreOrderObject> list = mYedek;

            int count = list.size();
            final ArrayList<PreOrderObject> nlist = new ArrayList<PreOrderObject>(count);

            String filterableString ;

            for (int i = 0; i < count; i++) {
                filterableString = list.get(i).getMarka()+" "+list.get(i).getUrunKodu();
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
            mKisiListesi = (ArrayList<PreOrderObject>) results.values;
            notifyDataSetChanged();
        }

    }
}
