package com.hedefsvr.modayakamozadmin.analiz;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.hedefsvr.modayakamozadmin.R;

import java.util.ArrayList;

/**
 * Created by Lenovo- on 18.12.2018.
 */

public class AramaAdapter extends ArrayAdapter<BayiObject> {
    private final String MY_DEBUG_TAG = "CustomerAdapter";
    private ArrayList<BayiObject> items;
    private ArrayList<BayiObject> itemsAll;
    private ArrayList<BayiObject> suggestions;
    private int viewResourceId;

    public AramaAdapter(Context context, int viewResourceId, ArrayList<BayiObject> items) {
        super(context, viewResourceId, items);
        this.items = items;
        this.itemsAll = (ArrayList<BayiObject>) items.clone();
        this.suggestions = new ArrayList<BayiObject>();
        this.viewResourceId = viewResourceId;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(viewResourceId, null);
        }
        BayiObject customer = items.get(position);
        if (customer != null) {
            TextView customerNameLabel = (TextView) v.findViewById(R.id.textView29);
            if (customerNameLabel != null) {
//              Log.i(MY_DEBUG_TAG, "getView Customer Name:"+customer.getName());
                customerNameLabel.setText(customer.getKullanciAdi());
            }
        }
        return v;
    }

    @Override
    public Filter getFilter() {
        return nameFilter;
    }

    Filter nameFilter = new Filter() {
        @Override
        public String convertResultToString(Object resultValue) {
            String str = ((BayiObject)(resultValue)).getKullanciAdi();
            return str;
        }
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if(constraint != null) {
                suggestions.clear();
                String filterableString ;
                String filterString = constraint.toString().toLowerCase();
                for (int i = 0; i < itemsAll.size(); i++) {
                    filterableString = itemsAll.get(i).getKullanciAdi();
                    if (filterableString.toLowerCase().contains(filterString)) {
                        suggestions.add(itemsAll.get(i));
                    }
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            ArrayList<BayiObject> filteredList = (ArrayList<BayiObject>) results.values;
            if(results != null && results.count > 0) {
                clear();
                for (BayiObject c : filteredList) {
                    add(c);
                }
                notifyDataSetChanged();
            }
        }
    };

}