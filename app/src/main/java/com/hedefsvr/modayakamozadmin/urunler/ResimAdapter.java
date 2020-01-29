package com.hedefsvr.modayakamozadmin.urunler;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;

import com.hedefsvr.modayakamozadmin.R;
import com.hedefsvr.modayakamozadmin.helper.ReadURL;

import java.util.List;

import it.sephiroth.android.library.picasso.Picasso;

/**
 * Created by Lenovo- on 1.12.2017.
 */

public class ResimAdapter extends BaseAdapter{

    private LayoutInflater mInflater;
    private Context context;
    private List<ResimObject> mKisiListesi;
    DetayFragment detayFragment;
    String extension;
    public ResimAdapter(Activity activity, List<ResimObject> resimObjects , DetayFragment detayFragment ) {
        //XML'i alıp View'a çevirecek inflater'ı örnekleyelim
        context = activity;
        this.detayFragment = detayFragment;
        mInflater = (LayoutInflater) activity.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        //gösterilecek listeyi de alalım
        mKisiListesi = resimObjects;
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
        satirView = mInflater.inflate(R.layout.resim_grid_item, null);
        final ResimObject resimObject = mKisiListesi.get(position);
        final ImageView resim=(ImageView)satirView.findViewById(R.id.ressim);
        final Button delete = (Button)satirView.findViewById(R.id.button21);
        extension = resimObject.getB_resim().substring(resimObject.getB_resim().lastIndexOf("."));
        if (!extension.toLowerCase().equals(".mp4")){
            try{
                Picasso.with(context).load("http://modayakamoz.com/resimler_k/"+resimObject.getB_resim()).into(resim);
            }
            catch (Exception e){

            }
        }
        else {
            Picasso.with(context).load(R.drawable.images).into(resim);
        }

        resim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                extension = resimObject.getB_resim().substring(resimObject.getB_resim().lastIndexOf("."));
                if (!extension.toLowerCase().equals(".mp4"))    {
                    Intent intent = new Intent(context, ImageActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    intent.putExtra("type","image");
                    intent.putExtra("link","http://modayakamoz.com/resimler/" + resimObject.getB_resim());
                    context.startActivity(intent);
                }
                else if (extension.toLowerCase().equals(".mp4"))    {
                    Intent intent = new Intent(context, ImageActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    intent.putExtra("type","video");
                    intent.putExtra("link","http://modayakamoz.com/resimler/" + resimObject.getB_resim());
                    context.startActivity(intent);
                }


            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                new getData().execute(resimObject.getB_resim());
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Resmi Silmek İstediğinize Emin Misiniz?").setPositiveButton("Evet", dialogClickListener)
                        .setNegativeButton("Vazgeç", dialogClickListener).show();
            }
        });
        return satirView;
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

                String gelenData=readURL.readURL("http://modayakamoz.com/admin/delete_image.php?b_resim="+values[0]);

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

                detayFragment.yenile ();
            }


        }
    }
}
