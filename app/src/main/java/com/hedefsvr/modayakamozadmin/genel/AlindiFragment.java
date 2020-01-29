package com.hedefsvr.modayakamozadmin.genel;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.hedefsvr.modayakamozadmin.R;
import com.hedefsvr.modayakamozadmin.helper.ReadURL;
import com.hedefsvr.modayakamozadmin.helper.YuklemeFragment;
import com.hedefsvr.modayakamozadmin.urunler.DetayFragment;

import it.sephiroth.android.library.picasso.Picasso;


/**
 * A simple {@link Fragment} subclass.
 */
public class AlindiFragment extends DialogFragment {

    private  PreOrderObject poo;
    private String kategori;
    private PreOrderAyakkabi poa;
    private PreOrderCantaFragment poc;
    private PreOrderFragment pof ;
    private PreOrderTesettur pot ;
    public void setPoa(PreOrderAyakkabi poa) {
        this.poa = poa;
    }
    public void setPoc(PreOrderCantaFragment poc) {
        this.poc = poc;
    }
    public void setPof(PreOrderFragment pof) {
        this.pof = pof;
    }
    public void setPot(PreOrderTesettur pot) {
        this.pot = pot;
    }
    public void setKategori(String kategori) {
        this.kategori = kategori;
    }
    public void setPre(PreOrderObject poo) {
        this.poo = poo;
    }
    TextView mesaj;
    ImageView resim;
    Button alindi;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_alindi, container, false);
        mesaj = (TextView)view.findViewById(R.id.textView57);
        resim = (ImageView) view.findViewById(R.id.imageView10);
        alindi = (Button) view.findViewById(R.id.button20);

        mesaj.setText(poo.getMarka()+" "+ poo.getSeri()+ " Seri");
        Picasso.with(getContext()).load("http://modayakamoz.com/resimler/"+poo.getResimObject().get(0).getB_resim()).into(resim);
        alindi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new al().execute();
            }
        });
        resim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentActivity activity = (FragmentActivity)(getContext());
                FragmentManager fm = activity.getSupportFragmentManager();
                DetayFragment alertDialog = new DetayFragment();
                alertDialog.setUrun(Integer.parseInt(poo.getId()));
                alertDialog.setKategori(Integer.parseInt(kategori));
                alertDialog.show(fm,"fragment_alert");
                View view1 = getActivity().getCurrentFocus();
                if (view1 != null) {
                    InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view1.getWindowToken(), 0);}
            }
        });
        return view;
    }
    private class al extends AsyncTask<String, String, String> {
        YuklemeFragment progress;

        @Override
        protected void onPreExecute() {
            FragmentManager fm = getFragmentManager();
            progress = new YuklemeFragment();
            progress.show(fm, "");

        }

        @Override
        protected String doInBackground(String... values)
        {   String url ="http://modayakamoz.com/admin/set_pre.php?kategori="+kategori+"&urunID="+poo.getId()+"&seri=0";
            ReadURL readURL=new ReadURL();

            try{
                String gelenData = readURL.readURL(url);
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
                if (kategori.equals("1"))
                    pof.yenile();
                else if (kategori.equals("2"))
                    poc.yenile();
                else if (kategori.equals("3"))
                    pot.yenile();
                else if (kategori.equals("4"))
                    poa.yenile();
            }


            progress.dismiss();
            getDialog().dismiss();
        }

    }
}
