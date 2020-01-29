package com.hedefsvr.modayakamozadmin.urunler;


import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.hedefsvr.modayakamozadmin.R;
import com.hedefsvr.modayakamozadmin.helper.DBHelper;
import com.hedefsvr.modayakamozadmin.helper.ReadURL;
import com.hedefsvr.modayakamozadmin.helper.YuklemeFragment;
import com.hedefsvr.modayakamozadmin.qrcode.GenerateQRFragment;
import com.sdsmdg.tastytoast.TastyToast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetayFragment extends DialogFragment {

    private int id;
    public void setUrun(int id) {
        this.id = id;
    }
    private int kategori;
    public void setKategori(int kategori) {
        this.kategori = kategori;
    }
    public static String macAdres = "";
    UrunDetayObject urunDetayObject;
    List<ResimObject>resimObject;
    List<BedenObject> bedenObject;
    List<BedenObject> siparisObject;
    TextView aciklama;
    ListView bedenler;
    BedenAdapter adapter;
    ResimAdapter resimAdapter;
    GridView  resimler;
    LinearLayout elbise, canta, ayakkabi;
    TextView elbiseXS,elbiseS,elbiseM,elbiseL,elbiseXL,elbiseXXL,elbiseXXXL,elbiseXXXXL,elbiseStandart,calisan;
    EditText editXS,editS,editM,editL,editXL,editXXL,editXXXL,editXXXXL,editStandart,firma;
    Button elbiseButtonXS,elbiseButtonS,elbiseButtonM,elbiseButtonL,elbiseButtonXL,elbiseButtonXXL,elbiseButtonXXXL,elbiseButtonXXXXL,elbiseButtonST;
    Button cantaBeden,resimYukle;
    CheckBox onSiparis;
    TextView cantaAdet;
    EditText editAdet,sutun,raf;
    Button kaydet;
    Spinner spinner ;
    int pos = 0;
    FloatingActionButton preOrder;
    String m_Text = "";
    DBHelper dbHelper;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detay, container, false);
        preOrder = (FloatingActionButton)view.findViewById(R.id.floatingActionButton11);
        bedenler=(ListView)view.findViewById(R.id.bedenList);
        resimYukle = (Button)view.findViewById(R.id.button51);
        resimler=(GridView) view.findViewById(R.id.resimler);
        aciklama=(TextView)view.findViewById(R.id.textView56) ;
        elbise = (LinearLayout)view.findViewById(R.id.elbise);
        canta = (LinearLayout)view.findViewById(R.id.canta);
        ayakkabi = (LinearLayout)view.findViewById(R.id.ayakkabi);
        elbiseS = (TextView)view.findViewById(R.id.textView61);
        elbiseXS = (TextView)view.findViewById(R.id.textView611);
        elbiseM = (TextView)view.findViewById(R.id.textView66);
        elbiseL = (TextView)view.findViewById(R.id.textView70);
        elbiseXL = (TextView)view.findViewById(R.id.textView62);
        elbiseXXL = (TextView)view.findViewById(R.id.textView67);
        elbiseXXXL = (TextView)view.findViewById(R.id.textView60);
        elbiseXXXXL = (TextView)view.findViewById(R.id.textView69);
        elbiseStandart = (TextView)view.findViewById(R.id.textView68);
        editS = (EditText)view.findViewById(R.id.editText15);
        editXS = (EditText)view.findViewById(R.id.editText151);
        editM = (EditText)view.findViewById(R.id.editText19);
        editL = (EditText)view.findViewById(R.id.editText20);
        editXL = (EditText)view.findViewById(R.id.editText14);
        editXXL = (EditText)view.findViewById(R.id.editText17);
        editXXXL = (EditText)view.findViewById(R.id.editText16);
        editXXXXL = (EditText)view.findViewById(R.id.editText333);
        editStandart = (EditText)view.findViewById(R.id.editText18);
        firma = (EditText)view.findViewById(R.id.editText);
        cantaAdet = (TextView)view.findViewById(R.id.textView82);
        editAdet = (EditText)view.findViewById(R.id.editText32);
        sutun = (EditText)view.findViewById(R.id.editText33);
        raf = (EditText)view.findViewById(R.id.editText3);
        spinner =(Spinner)view.findViewById(R.id.spinner1);
        onSiparis = (CheckBox)view.findViewById(R.id.checkBox);
        calisan =(TextView)view.findViewById(R.id.textView98);
        elbiseButtonXS = (Button)view.findViewById(R.id.button29);
        elbiseButtonS = (Button)view.findViewById(R.id.button30);
        elbiseButtonM = (Button)view.findViewById(R.id.button31);
        elbiseButtonL = (Button)view.findViewById(R.id.button32);
        elbiseButtonXL = (Button)view.findViewById(R.id.button34);
        elbiseButtonXXL = (Button)view.findViewById(R.id.button35);
        elbiseButtonXXXL = (Button)view.findViewById(R.id.button36);
        elbiseButtonXXXXL = (Button)view.findViewById(R.id.button37);
        elbiseButtonST = (Button)view.findViewById(R.id.button38);
        cantaBeden = (Button)view.findViewById(R.id.button27);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        elbiseButtonXXXXL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentActivity activity = (FragmentActivity)(getContext());
                FragmentManager fm = activity.getSupportFragmentManager();
                GenerateQRFragment alertDialog = new GenerateQRFragment();
                alertDialog.setUrun(id+1000);
                alertDialog.setBeden("XXXXL");
                alertDialog.show(fm,"fragment_alert");
            }
        });
        elbiseButtonXXXL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentActivity activity = (FragmentActivity)(getContext());
                FragmentManager fm = activity.getSupportFragmentManager();
                GenerateQRFragment alertDialog = new GenerateQRFragment();
                alertDialog.setUrun(id+1000);
                alertDialog.setBeden("XXXL");
                alertDialog.show(fm,"fragment_alert");
            }
        });
        elbiseButtonXXL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentActivity activity = (FragmentActivity)(getContext());
                FragmentManager fm = activity.getSupportFragmentManager();
                GenerateQRFragment alertDialog = new GenerateQRFragment();
                alertDialog.setUrun(id+1000);
                alertDialog.setBeden("XXL");
                alertDialog.show(fm,"fragment_alert");
            }
        });
        elbiseButtonXL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentActivity activity = (FragmentActivity)(getContext());
                FragmentManager fm = activity.getSupportFragmentManager();
                GenerateQRFragment alertDialog = new GenerateQRFragment();
                alertDialog.setUrun(id+1000);
                alertDialog.setBeden("XL");
                alertDialog.show(fm,"fragment_alert");
            }
        });
        elbiseButtonL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentActivity activity = (FragmentActivity)(getContext());
                FragmentManager fm = activity.getSupportFragmentManager();
                GenerateQRFragment alertDialog = new GenerateQRFragment();
                alertDialog.setUrun(id+1000);
                alertDialog.setBeden("L");
                alertDialog.show(fm,"fragment_alert");
            }
        });
        elbiseButtonM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentActivity activity = (FragmentActivity)(getContext());
                FragmentManager fm = activity.getSupportFragmentManager();
                GenerateQRFragment alertDialog = new GenerateQRFragment();
                alertDialog.setUrun(id+1000);
                alertDialog.setBeden("M");
                alertDialog.show(fm,"fragment_alert");
            }
        });
        elbiseButtonS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentActivity activity = (FragmentActivity)(getContext());
                FragmentManager fm = activity.getSupportFragmentManager();
                GenerateQRFragment alertDialog = new GenerateQRFragment();
                alertDialog.setUrun(id+1000);
                alertDialog.setBeden("S");
                alertDialog.show(fm,"fragment_alert");
            }
        });
        elbiseButtonXS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentActivity activity = (FragmentActivity)(getContext());
                FragmentManager fm = activity.getSupportFragmentManager();
                GenerateQRFragment alertDialog = new GenerateQRFragment();
                alertDialog.setUrun(id+1000);
                alertDialog.setBeden("XS");
                alertDialog.show(fm,"fragment_alert");
            }
        });
        elbiseButtonST.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentActivity activity = (FragmentActivity)(getContext());
                FragmentManager fm = activity.getSupportFragmentManager();
                GenerateQRFragment alertDialog = new GenerateQRFragment();
                alertDialog.setUrun(id+1000);
                alertDialog.setBeden("Standart");
                alertDialog.show(fm,"fragment_alert");
            }
        });
        cantaBeden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentActivity activity = (FragmentActivity)(getContext());
                FragmentManager fm = activity.getSupportFragmentManager();
                GenerateQRFragment alertDialog = new GenerateQRFragment();
                alertDialog.setUrun(id+1000);
                alertDialog.setBeden("Adet");
                alertDialog.show(fm,"fragment_alert");
            }
        });


        dbHelper = new DBHelper(getContext());

        if (dbHelper.getUser().getRegion().equals("2") || dbHelper.getUser().getRegion().equals("3") ){
            resimYukle.setVisibility(View.VISIBLE);
            calisan.setVisibility(View.VISIBLE);

        }

        resimYukle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentActivity activity = (FragmentActivity)(getContext());
                FragmentManager fm = activity.getSupportFragmentManager();
                ResimYukleFragment alertDialog = new ResimYukleFragment();
                alertDialog.setUrun(id);
                alertDialog.setDetay(DetayFragment.this);
                alertDialog.show(fm,"fragment_alert");
            }
        });
        preOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Seri Adedi Giriniz.");
                final EditText input = new EditText(getContext());
                input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_CLASS_NUMBER);
                input.setText("1");
                builder.setView(input);

                builder.setPositiveButton("Kaydet", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        m_Text = input.getText().toString();
                        if (m_Text.length()>0)
                            new onsiparis().execute(m_Text);

                    }
                });
                builder.setNegativeButton("İptal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });
        List<String> categories = new ArrayList<String>();
        categories.add("HAZIRLANMAYAN SİPARİŞLER"  );categories.add("TOPLAM SATIŞ");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {   pos = position;
                new getData().execute();

            } // to close the onItemSelected
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });
        kaydet = (Button) view.findViewById(R.id.button8);
        aciklama.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                ClipboardManager cm = (ClipboardManager) getContext().getSystemService(getContext().CLIPBOARD_SERVICE);
                cm.setText(aciklama.getText());
                Toast.makeText(getContext(), "Kopyalandı", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        if (kategori == 1)
            elbise.setVisibility(View.VISIBLE);
        else if (kategori == 2)
            canta.setVisibility(View.VISIBLE);
        else if (kategori == 3)
            elbise.setVisibility(View.VISIBLE);





        kaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String durum ="";
                if (onSiparis.isChecked())
                     durum = "1";
                else
                    durum = "0";

                if (kategori == 1)
                    new kaydet().execute(editXS.getText().toString(),editS.getText().toString(),editM.getText().toString(),editL.getText().toString(),editXL.getText().toString(),
                        editXXL.getText().toString(),editXXXL.getText().toString(),editXXXXL.getText().toString(),editStandart.getText().toString(),durum,sutun.getText().toString(),raf.getText().toString().toUpperCase());
                if (kategori == 2)
                    new kaydet().execute(editAdet.getText().toString(),durum,sutun.getText().toString(),raf.getText().toString().toUpperCase());
                if (kategori == 3)
                    new kaydet().execute(editXS.getText().toString(),editS.getText().toString(),editM.getText().toString(),editL.getText().toString(),editXL.getText().toString(),
                            editXXL.getText().toString(),editXXXL.getText().toString(),editXXXXL.getText().toString(),editStandart.getText().toString(),durum,sutun.getText().toString(),raf.getText().toString().toUpperCase());


            }
        });


        return view;
    }


    private class getData extends AsyncTask<String, String, String> {
        YuklemeFragment progress;

        @Override
        protected void onPreExecute() {
            FragmentManager fm = getFragmentManager();
            progress = new YuklemeFragment();
            progress.show(fm, "");
        }

        @Override
        protected String doInBackground(String... values)
        {

            ReadURL readURL=new ReadURL();

            try{
                String gelenData=readURL.readURL("http://modayakamoz.com/admin/get_urun.php?kategori="+kategori+"&ID="+id+"&durum="+pos);

                JSONArray array=new JSONArray(gelenData);


                    for (int i=0;i<array.length();i++){
                        bedenObject=new ArrayList<BedenObject>();
                        siparisObject=new ArrayList<BedenObject>();
                        resimObject=new ArrayList<ResimObject>();
                        JSONObject object=array.getJSONObject(i);
                        JSONArray array1=object.getJSONArray("resimler");
                        JSONArray array2=object.getJSONArray("stok");
                        JSONArray array3=object.getJSONArray("siparis");

                        for (int j=0;j<array2.length();j++){
                            JSONObject stokobj=array2.getJSONObject(j);
                                bedenObject.add(new BedenObject(stokobj.getString("beden"),Integer.parseInt(stokobj.getString("miktar"))));
                        }
                        for (int j=0;j<array1.length();j++){
                            JSONObject resimobj=array1.getJSONObject(j);
                            resimObject.add(new ResimObject (resimobj.getString("b_resim"),resimobj.getString("k_resim")));

                        }
                        for (int j=0;j<array3.length();j++){
                            JSONObject stokobj=array3.getJSONObject(j);
                            siparisObject.add(new BedenObject(stokobj.getString("beden"),Integer.parseInt(stokobj.getString("miktar"))));
                        }
                        urunDetayObject=(new UrunDetayObject(Integer.parseInt(object.getString("id")),Integer.parseInt(object.getString("urunKodu"))
                                ,Integer.parseInt(object.getString("onsiparis")),object.getString("aciklama"),object.getString("sutun"),object.getString("raf"),object.getString("firma"),
                                object.getString("calisan"),resimObject,bedenObject,siparisObject));
                    }



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
                resimAdapter=new ResimAdapter(getActivity(),urunDetayObject.getResimObject(), DetayFragment.this);
                resimler.setAdapter(resimAdapter);
                adapter=new BedenAdapter(getActivity(),urunDetayObject.getSiparisObject());
                bedenler.setAdapter(adapter);
                aciklama.setText(urunDetayObject.getAciklama());
                firma.setText(urunDetayObject.getFirma());
                sutun.setText(urunDetayObject.getSutun());
                raf.setText(urunDetayObject.getRaf());
                calisan.setText(urunDetayObject.getCalisan());
                if (urunDetayObject.getOnSiparis() == 1 )
                    onSiparis.setChecked(true);

                if (kategori == 1){
                    for(int i = 0 ; i< urunDetayObject.getBedenObject().size(); i++){
                        if (urunDetayObject.getBedenObject().get(i).getBeden().equals("XS")) {
                            elbiseXS.setText(urunDetayObject.getBedenObject().get(i).getBeden());
                            editXS.setText(""+urunDetayObject.getBedenObject().get(i).getAdet());
                        }
                        else if (urunDetayObject.getBedenObject().get(i).getBeden().equals("S")) {
                            elbiseS.setText(urunDetayObject.getBedenObject().get(i).getBeden());
                            editS.setText(""+urunDetayObject.getBedenObject().get(i).getAdet());
                        }
                        else if (urunDetayObject.getBedenObject().get(i).getBeden().equals("M")) {
                            elbiseM.setText(urunDetayObject.getBedenObject().get(i).getBeden());
                            editM.setText(""+urunDetayObject.getBedenObject().get(i).getAdet());

                        }
                        else if (urunDetayObject.getBedenObject().get(i).getBeden().equals("L")) {
                            elbiseL.setText(urunDetayObject.getBedenObject().get(i).getBeden());
                            editL.setText(""+urunDetayObject.getBedenObject().get(i).getAdet());

                        }
                        else if (urunDetayObject.getBedenObject().get(i).getBeden().equals("XL")) {
                            elbiseXL.setText(urunDetayObject.getBedenObject().get(i).getBeden());
                            editXL.setText(""+urunDetayObject.getBedenObject().get(i).getAdet());

                        }
                        else if (urunDetayObject.getBedenObject().get(i).getBeden().equals("XXL")) {
                            elbiseXXL.setText(urunDetayObject.getBedenObject().get(i).getBeden());
                            editXXL.setText(""+urunDetayObject.getBedenObject().get(i).getAdet());

                        }
                        else if (urunDetayObject.getBedenObject().get(i).getBeden().equals("XXXL")) {
                            elbiseXXXL.setText(urunDetayObject.getBedenObject().get(i).getBeden());
                            editXXXL.setText(""+urunDetayObject.getBedenObject().get(i).getAdet());

                        }else if (urunDetayObject.getBedenObject().get(i).getBeden().equals("XXXXL")) {
                            elbiseXXXXL.setText(urunDetayObject.getBedenObject().get(i).getBeden());
                            editXXXXL.setText(""+urunDetayObject.getBedenObject().get(i).getAdet());

                        }
                        else if (urunDetayObject.getBedenObject().get(i).getBeden().equals("Standart")) {
                            elbiseStandart.setText(urunDetayObject.getBedenObject().get(i).getBeden());
                            editStandart.setText(""+urunDetayObject.getBedenObject().get(i).getAdet());

                        }

                    }

                }else if (kategori == 2 && urunDetayObject.getBedenObject().size() > 0) {
                    cantaAdet.setText(""+urunDetayObject.getBedenObject().get(0).getBeden());
                    editAdet.setText(""+urunDetayObject.getBedenObject().get(0).getAdet());
                }
                else if (kategori == 3){
                    for(int i = 0 ; i< urunDetayObject.getBedenObject().size(); i++){
                        if (urunDetayObject.getBedenObject().get(i).getBeden().equals("XS")) {
                            elbiseXS.setText(urunDetayObject.getBedenObject().get(i).getBeden());
                            editXS.setText(""+urunDetayObject.getBedenObject().get(i).getAdet());
                        }
                        else if (urunDetayObject.getBedenObject().get(i).getBeden().equals("S")) {
                            elbiseS.setText(urunDetayObject.getBedenObject().get(i).getBeden());
                            editS.setText(""+urunDetayObject.getBedenObject().get(i).getAdet());
                        }
                        else if (urunDetayObject.getBedenObject().get(i).getBeden().equals("M")) {
                            elbiseM.setText(urunDetayObject.getBedenObject().get(i).getBeden());
                            editM.setText(""+urunDetayObject.getBedenObject().get(i).getAdet());

                        }
                        else if (urunDetayObject.getBedenObject().get(i).getBeden().equals("L")) {
                            elbiseL.setText(urunDetayObject.getBedenObject().get(i).getBeden());
                            editL.setText(""+urunDetayObject.getBedenObject().get(i).getAdet());

                        }
                        else if (urunDetayObject.getBedenObject().get(i).getBeden().equals("XL")) {
                            elbiseXL.setText(urunDetayObject.getBedenObject().get(i).getBeden());
                            editXL.setText(""+urunDetayObject.getBedenObject().get(i).getAdet());

                        }
                        else if (urunDetayObject.getBedenObject().get(i).getBeden().equals("XXL")) {
                            elbiseXXL.setText(urunDetayObject.getBedenObject().get(i).getBeden());
                            editXXL.setText(""+urunDetayObject.getBedenObject().get(i).getAdet());

                        }
                        else if (urunDetayObject.getBedenObject().get(i).getBeden().equals("XXXL")) {
                            elbiseXXXL.setText(urunDetayObject.getBedenObject().get(i).getBeden());
                            editXXXL.setText(""+urunDetayObject.getBedenObject().get(i).getAdet());

                        }else if (urunDetayObject.getBedenObject().get(i).getBeden().equals("XXXXL")) {
                            elbiseXXXXL.setText(urunDetayObject.getBedenObject().get(i).getBeden());
                            editXXXXL.setText(""+urunDetayObject.getBedenObject().get(i).getAdet());

                        }
                        else if (urunDetayObject.getBedenObject().get(i).getBeden().equals("Standart")) {
                            elbiseStandart.setText(urunDetayObject.getBedenObject().get(i).getBeden());
                            editStandart.setText(""+urunDetayObject.getBedenObject().get(i).getAdet());

                        }

                    }

                }

            }
            else
                Toast.makeText(getContext(),"asdas",Toast.LENGTH_LONG).show();
            progress.dismiss();

        }
    }
    private class kaydet extends AsyncTask<String, String, String> {
        YuklemeFragment progress;

        @Override
        protected void onPreExecute() {
            FragmentManager fm = getFragmentManager();
            progress = new YuklemeFragment();
            progress.show(fm, "");

        }

        @Override
        protected String doInBackground(String... values)
        {   String url ="";
            if (kategori == 1)
                url ="http://modayakamoz.com/admin/set_urun.php?kategori="+kategori+"&urunID="+id+
                        "&xs=" + values[0]+"&s=" + values[1]+"&m=" + values[2]+"&l=" + values[3]+"&xl=" + values[4]+"&xxl=" + values[5]
                        +"&xxxl=" + values[6]+"&xxxxl=" + values[7]+"&standart=" + values[8] + "&onsiparis="+values[9]+ "&sutun="+values[10]+ "&raf="+values[11]+"&calisanID="+dbHelper.getUser().getID();
            else if (kategori == 2)
                url = "http://modayakamoz.com/admin/set_urun.php?kategori="+kategori+"&urunID="+id+
                        "&adet=" + values[0]+ "&onsiparis="+values[1]+ "&sutun="+values[2]+ "&raf="+values[3]+"&calisanID="+dbHelper.getUser().getID();
            else if (kategori == 3)
                url ="http://modayakamoz.com/admin/set_urun.php?kategori="+kategori+"&urunID="+id+
                        "&xs=" + values[0]+"&s=" + values[1]+"&m=" + values[2]+"&l=" + values[3]+"&xl=" + values[4]+"&xxl=" + values[5]
                        +"&xxxl=" + values[6]+"&xxxxl=" + values[7]+"&standart=" + values[8] + "&onsiparis="+values[9]+ "&sutun="+values[10]+ "&raf="+values[11]+"&calisanID="+dbHelper.getUser().getID();
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
                TastyToast.makeText(getContext(), "KAYDEDİLDİ!.", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                calisan.setText(dbHelper.getUser().getKullaniciAdi());

            }
            else
                TastyToast.makeText(getContext(), "HATA.", TastyToast.LENGTH_LONG, TastyToast.ERROR);


            progress.dismiss();

        }

    }

    private class onsiparis extends AsyncTask<String, String, String> {
        YuklemeFragment progress;

        @Override
        protected void onPreExecute() {
            FragmentManager fm = getFragmentManager();
            progress = new YuklemeFragment();
            progress.show(fm, "");

        }

        @Override
        protected String doInBackground(String... values)
        {   String url ="http://modayakamoz.com/admin/set_pre.php?kategori="+kategori+"&urunID="+urunDetayObject.getId()+"&seri="+values[0];
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
                TastyToast.makeText(getContext(), "ÖN SİPARİŞE EKLENDİ.", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                new getData().execute();
            }
            else
                TastyToast.makeText(getContext(), "HATA.", TastyToast.LENGTH_LONG, TastyToast.ERROR);

            progress.dismiss();

        }

    }

    public void yenile(){
        new getData().execute();
    }





}
