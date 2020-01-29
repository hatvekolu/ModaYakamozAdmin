package com.hedefsvr.modayakamozadmin.analiz;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.hedefsvr.modayakamozadmin.R;
import com.hedefsvr.modayakamozadmin.helper.ReadURL;
import com.hedefsvr.modayakamozadmin.temsilci.TemsilciEditFragment;
import com.hedefsvr.modayakamozadmin.temsilci.TemsilciFragment;
import com.hedefsvr.modayakamozadmin.temsilci.TemsilciObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
/**
 * Created by Lenovo- on 27.7.2018.
 */

public class TemsilciAdapter extends BaseAdapter implements Filterable {
    private LayoutInflater mInflater;
    private Context context;
    private List<TemsilciObject> mKisiListesi;
    private List<TemsilciObject> mYedek;
    private ItemFilter mFilter = new ItemFilter();
    private TemsilciFragment temsilciFragment;
    public TemsilciAdapter(Activity activity, List<TemsilciObject> temsilciObjects, TemsilciFragment temsilciFragment) {
        //XML'i alıp View'a çevirecek inflater'ı örnekleyelim
        context = activity;
        mInflater = (LayoutInflater) activity.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        //gösterilecek listeyi de alalım
        mKisiListesi = temsilciObjects;
        mYedek=temsilciObjects;
        this.temsilciFragment=temsilciFragment;
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
        satirView = mInflater.inflate(R.layout.temsilci_list_item, null);
        final TemsilciObject to = mKisiListesi.get(position);
        Button sil=(Button)satirView.findViewById(R.id.button2);
        Button edit=(Button)satirView.findViewById(R.id.button);

        TextView ad=(TextView)satirView.findViewById(R.id.textView10);
        TextView tel=(TextView)satirView.findViewById(R.id.textView11);
        TextView kAdi=(TextView)satirView.findViewById(R.id.textView14);
        TextView sifre=(TextView)satirView.findViewById(R.id.textView13);
        TextView toplamSatis=(TextView)satirView.findViewById(R.id.textView16);
        TextView bakiye=(TextView)satirView.findViewById(R.id.textView15);

        ad.setText(to.getAd()+" "+to.getSoyad());
        tel.setText(to.getTel());
        kAdi.setText(to.getKullanciAdi());
        sifre.setText(to.getSifre());
        toplamSatis.setText("Toplam Satış: "+to.getToplamSatis());

        sil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                new getData().execute(""+to.getId());
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Silmek İstediğinize Emin Misiniz?").setPositiveButton("Evet", dialogClickListener)
                        .setNegativeButton("Hayır", dialogClickListener).show();
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentActivity activity = (FragmentActivity)(context);
                FragmentManager fm = activity.getSupportFragmentManager();
                TemsilciEditFragment alertDialog = new TemsilciEditFragment();
                alertDialog.setDetay(to);
                alertDialog.setTemsilciFragment(temsilciFragment);
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
            for (TemsilciObject wp : mYedek) {
                if ((wp.getAd()).toLowerCase(Locale.getDefault())
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

            final List<TemsilciObject> list = mYedek;

            int count = list.size();
            final ArrayList<TemsilciObject> nlist = new ArrayList<TemsilciObject>(count);

            String filterableString ;

            for (int i = 0; i < count; i++) {
                filterableString = list.get(i).getAd()+" "+list.get(i).getAd()+" "+list.get(i).getKullanciAdi()+" "+list.get(i).getTel();
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
            mKisiListesi = (ArrayList<TemsilciObject>) results.values;
            notifyDataSetChanged();
        }

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

                String gelenData=readURL.readURL("http://modayakamoz.com/admin/komisyoncu_sil.php?ID="+values[0]);



                return "0";
            }
            catch (Exception e){
                return "HATA";
            }

        }

        @Override
        protected void onPostExecute(String results)
        {
            if (!results.equals("HATA")){

                temsilciFragment.new getData().execute();

            }

        }
    }

}