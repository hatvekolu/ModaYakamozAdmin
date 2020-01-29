package com.hedefsvr.modayakamozadmin.siparis;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hedefsvr.modayakamozadmin.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Lenovo- on 20.12.2018.
 */

public class YoldaAdapter extends BaseAdapter implements Filterable {
    private LayoutInflater mInflater;
    private Context context;
    private List<SatisObject> mKisiListesi;
    private List<SatisObject> mYedek;
    private BekleyenFragment bekleyenFragment;
    private ItemFilter mFilter = new ItemFilter();
    public YoldaAdapter(Activity activity, List<SatisObject> satisObjects  ) {
        //XML'i alıp View'a çevirecek inflater'ı örnekleyelim
        context = activity;
        mInflater = (LayoutInflater) activity.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        //gösterilecek listeyi de alalım
        mKisiListesi = satisObjects;
        mYedek=satisObjects;
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
        satirView = mInflater.inflate(R.layout.yolda_list_item, null);
        final SatisObject satisObject = mKisiListesi.get(position);
        RelativeLayout genel=(RelativeLayout)satirView.findViewById(R.id.genel);
        TextView adSoyad=(TextView)satirView.findViewById(R.id.textView24);
        TextView tel=(TextView)satirView.findViewById(R.id.textView25);
        TextView tutar=(TextView)satirView.findViewById(R.id.textView23);
        TextView aciklama=(TextView)satirView.findViewById(R.id.textView27);
        TextView bayi=(TextView)satirView.findViewById(R.id.textView22);
        LinearLayout back = (LinearLayout)satirView.findViewById(R.id.background);
        Button call = (Button) satirView.findViewById(R.id.button17);
        Button whatsapp = (Button) satirView.findViewById(R.id.button19);
        TextView calisan = (TextView) satirView.findViewById(R.id.textView96);


        whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                String contact = "+90"+satisObject.getBayi_tel(); // use country code with your phone number
                                String url = "https://api.whatsapp.com/send?phone=" + contact;
                                try {
                                    PackageManager pm = context.getPackageManager();
                                    pm.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES);
                                    Intent i = new Intent(Intent.ACTION_VIEW);
                                    i.setData(Uri.parse(url));
                                    context.startActivity(i);
                                } catch (PackageManager.NameNotFoundException e) {

                                    e.printStackTrace();
                                }
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage(satisObject.getBayi() + " Whatsapp'dan Yaz!").setPositiveButton("Yaz", dialogClickListener)
                        .setNegativeButton("Vazgeç", dialogClickListener).show();

            }

        });

        aciklama.setText(""+satisObject.getAciklama());
        bayi.setText(""+satisObject.getBayi());
        adSoyad.setText(satisObject.getAd()+" "+satisObject.getSoyad());
        tel.setText(""+satisObject.getTel());
        tutar.setText("Toplam:"+satisObject.getTutar()+" ₺");
        calisan.setText(""+satisObject.getCalisan());
        if(satisObject.getOdeme_turu().equals("HAVALE"))
            back.setBackgroundColor(0xffff8800);

        genel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = ((AppCompatActivity)context).getSupportFragmentManager();
                SatisDetayFragment alertDialog = new SatisDetayFragment();
                alertDialog.setUrun(satisObject);
                alertDialog.show(fm,"fragment_alert");
            }
        });

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    final Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:+90" + satisObject.getBayi_tel()));
                    if (ContextCompat.checkSelfPermission(context,
                            Manifest.permission.CALL_PHONE)
                            != PackageManager.PERMISSION_GRANTED) {

                        ActivityCompat.requestPermissions((Activity) context,
                                new String[]{Manifest.permission.CALL_PHONE},
                                1);

                        // MY_PERMISSIONS_REQUEST_CALL_PHONE is an
                        // app-defined int constant. The callback method gets the
                        // result of the request.
                    } else {
                        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which){
                                    case DialogInterface.BUTTON_POSITIVE:
                                        context.startActivity(callIntent);
                                        break;

                                    case DialogInterface.BUTTON_NEGATIVE:
                                        //No button clicked
                                        break;
                                }
                            }
                        };

                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setMessage(satisObject.getBayi() + " Aranacak!").setPositiveButton("Ara", dialogClickListener)
                                .setNegativeButton("Vazgeç", dialogClickListener).show();

                    }

                }
                catch (Exception e){
                    return;
                }

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
            for (SatisObject wp : mYedek) {
                if ((wp.getAd()+" "+wp.getSoyad()+ " " + wp.getBayi()).toLowerCase(Locale.getDefault())
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

            final List<SatisObject> list = mYedek;

            int count = list.size();
            final ArrayList<SatisObject> nlist = new ArrayList<SatisObject>(count);

            String filterableString ;

            for (int i = 0; i < count; i++) {
                filterableString = list.get(i).getAd()+" "+list.get(i).getSoyad()+ " " + list.get(i).getBayi();
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
            mKisiListesi = (ArrayList<SatisObject>) results.values;
            notifyDataSetChanged();
        }

    }

}
