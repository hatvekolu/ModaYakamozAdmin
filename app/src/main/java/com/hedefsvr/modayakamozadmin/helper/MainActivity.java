package com.hedefsvr.modayakamozadmin.helper;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.hedefsvr.modayakamozadmin.R;
import com.hedefsvr.modayakamozadmin.analiz.AnalizFragment;
import com.hedefsvr.modayakamozadmin.genel.HomeFragment;
import com.hedefsvr.modayakamozadmin.genel.KomisyonOranFragment;
import com.hedefsvr.modayakamozadmin.qrcode.QRGenelFragment;
import com.hedefsvr.modayakamozadmin.siparis.SiparislerTabHost;
import com.hedefsvr.modayakamozadmin.temsilci.TemsilciFragment;
import com.hedefsvr.modayakamozadmin.urunler.UrunlerFragmentTabs;

public class MainActivity extends AppCompatActivity {

    Button bildirim,komisyon;
    String token="";
    String deviceId="";
    DBHelper dbHelper;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
            switch (item.getItemId()) {
                case R.id.home:
                    transaction.replace(R.id.content,new HomeFragment()).commit();
                    return true;
                case R.id.urunler:
                    transaction.replace(R.id.content,new UrunlerFragmentTabs()).commit();
                    return true;
                case R.id.temsilci:
                    transaction.replace(R.id.content,new TemsilciFragment()).commit();
                    return true;
                case R.id.siparisler:
                    transaction.replace(R.id.content,new SiparislerTabHost()).commit();
                    return true;
                case R.id.rapor:
                    transaction.replace(R.id.content,new AnalizFragment()).commit();
                    return true;


            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bildirim=(Button)findViewById(R.id.button6);
        komisyon=(Button)findViewById(R.id.button9);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        BottomNavigationViewHelper.disableShiftMode(navigation);
        bildirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                QRGenelFragment cmf = new QRGenelFragment();
                FragmentManager fm = getSupportFragmentManager();
                fm.beginTransaction().replace(
                        R.id.content,
                        cmf,"0").commit();
            }
        });

        komisyon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                KomisyonOranFragment cmf = new KomisyonOranFragment();
                FragmentManager fm = getSupportFragmentManager();
                fm.beginTransaction().replace(
                        R.id.content,
                        cmf,"0").commit();
            }
        });

        if (getIntent().getExtras() != null) {
            for(String key : getIntent().getExtras().keySet()) {
                if (key.equals("mesaj"))
                    Toast.makeText(getApplicationContext(),getIntent().getExtras().getString(key),Toast.LENGTH_LONG).show();
            }
        }

        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content,new HomeFragment()).commit();
        dbHelper = new DBHelper(getApplicationContext());
        if (dbHelper.getUser().getKullaniciAdi().length() == 0){
            Intent intent = new Intent(MainActivity.this, GirisActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private class getData extends AsyncTask<String, String, String> {
        YuklemeFragment progress;

        @Override
        protected void onPreExecute() {

            FragmentManager fm =getSupportFragmentManager();
            progress = new YuklemeFragment();
            progress.show(fm, "");
        }

        @Override
        protected String doInBackground(String... values)
        {

            ReadURL readURL=new ReadURL();
            try{
                String data= readURL.readURL("http://modayakamoz.com/admin/check.php?kullaniciAdi="+values[0]+"&sifre="+values[1]);



                return data;
            }
            catch (Exception e){
                return "HATA";
            }

        }

        @Override
        protected void onPostExecute(String results)
        {
            if (!results.equals("HATA")){



            }
            else {
                dbHelper.deleteUser();
                Intent intent = new Intent(MainActivity.this, GirisActivity.class);
                startActivity(intent);
                finish();
            }


            progress.dismiss();


        }
    }
}
