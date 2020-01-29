package com.hedefsvr.modayakamozadmin.genel;


import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.hedefsvr.modayakamozadmin.R;
import com.hedefsvr.modayakamozadmin.helper.DBHelper;
import com.hedefsvr.modayakamozadmin.helper.ReadURL;
import com.hedefsvr.modayakamozadmin.helper.YuklemeFragment;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.models.BarModel;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import it.sephiroth.android.library.picasso.Picasso;


public class HomeFragment extends Fragment {
    BarChart mBarChart;
    LinearLayout toplam,yolda,bekleyen,donen,ulasan;
    GenelObject genelObject;
    List<AylarObject> aylarObjects;
    TextView toplaSatis,bekleyenSatis,yoldaSatis,ulasanSatis,donenSatis,adet,yuzde;
    ImageView urun;
    CircularProgressBar circularProgressBar;
    Spinner spinner ;
    FloatingActionButton order,dagit;
    DBHelper dbHelper;
    YuklemeFragment progress;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_home, container, false);
        mBarChart = (BarChart)view.findViewById(R.id.barchart);
        toplam=(LinearLayout)view.findViewById(R.id.toplam);
        yolda=(LinearLayout)view.findViewById(R.id.yolda);
        bekleyen=(LinearLayout)view.findViewById(R.id.bekleyen);
        spinner =(Spinner)view.findViewById(R.id.planets_spinner);
        donen=(LinearLayout)view.findViewById(R.id.donen);
        ulasan=(LinearLayout)view.findViewById(R.id.ulasan);
        toplaSatis=(TextView)view.findViewById(R.id.textView4) ;
        bekleyenSatis=(TextView)view.findViewById(R.id.textView5) ;
        yoldaSatis=(TextView)view.findViewById(R.id.textView6) ;
        ulasanSatis=(TextView)view.findViewById(R.id.textView7) ;
        donenSatis=(TextView)view.findViewById(R.id.textView8) ;
        adet=(TextView)view.findViewById(R.id.adet) ;
        yuzde=(TextView)view.findViewById(R.id.ulasmaYuzde) ;
        urun=(ImageView) view.findViewById(R.id.imageView2) ;
        order =(FloatingActionButton) view.findViewById(R.id.floatingActionButton10);
        dagit =(FloatingActionButton) view.findViewById(R.id.floatingActionButton12);
        dbHelper = new DBHelper(getContext());
        if (dbHelper.getUser().getRegion().equals("0")){
            order.setVisibility(View.GONE);
            mBarChart.setVisibility(View.GONE);
            spinner.setVisibility(View.GONE);
            dagit.setVisibility(View.GONE);
        }
        else if (dbHelper.getUser().getRegion().equals("1")){
            order.setVisibility(View.GONE);
            mBarChart.setVisibility(View.GONE);
            spinner.setVisibility(View.GONE);
            dagit.setVisibility(View.GONE);
        }
        else if(dbHelper.getUser().getRegion().equals("2")){
            mBarChart.setVisibility(View.GONE);
            spinner.setVisibility(View.GONE);
            dagit.setVisibility(View.GONE);
        }
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OrderTabHost cmf = new OrderTabHost();
                FragmentManager fm = getFragmentManager();
                fm.beginTransaction().replace(
                        R.id.content,
                        cmf,"0").commit();
            }
        });
        dagit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CalisanlarFragment cmf = new CalisanlarFragment();
                FragmentManager fm = getFragmentManager();
                fm.beginTransaction().replace(
                        R.id.content,
                        cmf,"0").commit();
            }
        });
        List<String> categories = new ArrayList<String>();
        categories.add("ADET"  );categories.add("TUTAR");
        circularProgressBar = (CircularProgressBar)view.findViewById(R.id.circularProgressBar);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {  if (aylarObjects.size()>0){
                if (position == 0){
                    mBarChart.clearChart();
                    for (int i=0;i<aylarObjects.size();i++){
                        mBarChart.addBar(new BarModel(aylarObjects.get(i).getAy(),Float.parseFloat(""+aylarObjects.get(i).getAdet()), 0xFFFF4081));
                    }
                    mBarChart.startAnimation();
                }
                else {
                    mBarChart.clearChart();
                    for (int i=0;i<aylarObjects.size();i++){
                        mBarChart.addBar(new BarModel(aylarObjects.get(i).getAy(),Float.parseFloat(""+aylarObjects.get(i).getTutar()), 0xFFFF4081));
                    }
                    mBarChart.startAnimation();
                }

            }

            } // to close the onItemSelected
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });
        circularProgressBar.setProgressWithAnimation(55);
        toplam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"TOPLAM KARGO",Toast.LENGTH_SHORT).show();
            }
        });
        bekleyen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"BEKLEYEN KARGO",Toast.LENGTH_SHORT).show();
            }
        });
        yolda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"YOLDA OLAN KARGO",Toast.LENGTH_SHORT).show();
            }
        });
        ulasan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"ULAŞAN KARGO",Toast.LENGTH_SHORT).show();
            }
        });
        donen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"GERİ DÖNEN KARGO",Toast.LENGTH_SHORT).show();
            }
        });
        new getData().execute();
        return view;
    }
    private class getData extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {

            FragmentManager fm = getFragmentManager();
            progress = new YuklemeFragment();
            progress.show(fm, "");
        }

        @Override
        protected String doInBackground(String... values)
        {
            aylarObjects=new ArrayList<AylarObject>();
            ReadURL readURL=new ReadURL();
            try{
                Random r = new Random();
                int i1 = (r.nextInt(80) + 65);
                String gelenData;
                if (dbHelper.getUser().getRegion().equals("0") ||  dbHelper.getUser().getRegion().equals("1")) {
                     gelenData = readURL.readURL("http://modayakamoz.com/admin/get_genel_yetkisiz.php?ID=" + dbHelper.getUser().getID());
                }
                else {
                     gelenData=readURL.readURL("http://modayakamoz.com/admin/get_genel.php");

                }

                JSONObject object=new JSONObject(gelenData);
                JSONArray array=object.getJSONArray("aylik");



                for (int i=0;i<array.length();i++){
                    JSONObject object1=array.getJSONObject(i);
                    aylarObjects.add(new AylarObject(object1.getInt("yil"),object1.getString("ay"),
                            object1.getInt("adet"),
                            object1.getDouble("tutar")));
                }
                genelObject=(new GenelObject(object.getInt("toplam"),object.getInt("bekleyen"),object.getInt("yolda"),
                        object.getInt("ulasan"),object.getInt("donen"),object.getDouble("yuzde"),object.getString("version"),object.getInt("encok"),
                        object.getInt("encoksatis"),object.getString("komisyoncu"),object.getString("resim_url"),aylarObjects));


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
                mBarChart.clearChart();
                toplaSatis.setText(""+genelObject.getToplam());
                bekleyenSatis.setText(""+genelObject.getBekleyen());
                yoldaSatis.setText(""+genelObject.getYolda());
                ulasanSatis.setText(""+genelObject.getUlasan());
                donenSatis.setText(""+genelObject.getDonen());
                adet.setText(""+genelObject.getEnCokSatanUrun()+ " ADET");
                yuzde.setText("%"+genelObject.getYuzde());
                circularProgressBar.setProgressWithAnimation(Float.parseFloat(""+genelObject.getYuzde()));
                Picasso.with(getContext()).load(genelObject.getResimUrl()).into(urun);
                for (int i=0;i<aylarObjects.size();i++){
                    mBarChart.addBar(new BarModel(aylarObjects.get(i).getAy(),Float.parseFloat(""+aylarObjects.get(i).getAdet()), 0xFFFF4081));
                }
                mBarChart.startAnimation();
                try {
                    PackageInfo pInfo = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0);
                    String version = pInfo.versionName;
                    if (!genelObject.getVersion().equals(version)) {
                        Snackbar snackbar = Snackbar
                                .make(getActivity().findViewById(android.R.id.content), "Uygulamanız güncel değil. Güncellemek İster Misiniz?.", 5000).setAction("EVET", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        downloadFile("http://modayakamoz.com/app-debug.apk");
                                    }
                                });
                        snackbar.setActionTextColor(0xFF17EA0C);
                        snackbar.show();
                    }

                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
            } progress.dismiss();

        }
    }
    public void downloadFile(final String url) {

        FragmentManager fm = getFragmentManager();
        progress = new YuklemeFragment();
        progress.show(fm, "");
        try {
            if (url != null && !url.isEmpty()) {
                Uri uri = Uri.parse(url);
                getActivity().registerReceiver(attachmentDownloadCompleteReceive, new IntentFilter(
                        DownloadManager.ACTION_DOWNLOAD_COMPLETE));

                DownloadManager.Request request = new DownloadManager.Request(uri);
                request.setMimeType(getMimeType(uri.toString()));
                request.setTitle("YakamozAdmin.apk");
                request.setDescription("İndiriliyor");
                request.allowScanningByMediaScanner();
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "YakamozAdmin.apk");
                DownloadManager dm = (DownloadManager) getActivity().getSystemService(Context.DOWNLOAD_SERVICE);
                dm.enqueue(request);
            }
        } catch (IllegalStateException e) {
            Toast.makeText(getContext(), "Please insert an SD card to download file", Toast.LENGTH_SHORT).show();
        }
    }


    private String getMimeType(String url) {
        String type = null;
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        if (extension != null) {
            MimeTypeMap mime = MimeTypeMap.getSingleton();
            type = mime.getMimeTypeFromExtension(extension);
        }
        return type;
    }


    BroadcastReceiver attachmentDownloadCompleteReceive = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            progress.dismiss();
            intent = new Intent(DownloadManager.ACTION_VIEW_DOWNLOADS);
            getActivity().startActivity(intent);
            android.os.Process.killProcess(android.os.Process.myPid());

        }
    };




}
