package com.hedefsvr.modayakamozadmin.helper;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.hedefsvr.modayakamozadmin.R;

import org.json.JSONObject;

public class LuncherActivity extends AppCompatActivity {
    DurumObject durumObject;
    DBHelper dbHelper;
    private final int SPLASH_DISPLAY_LENGTH = 1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_luncher);
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                dbHelper = new DBHelper(getApplicationContext());

                if (dbHelper.getUser().getKullaniciAdi().length() > 0)
                    new checkVersiyon().execute();
                else {
                    Intent intent = new Intent(LuncherActivity.this, GirisActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        }, SPLASH_DISPLAY_LENGTH);

    }
    class checkVersiyon extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {
            ReadURL readURL=new ReadURL();
            try {
               String gelen =   readURL.readURL("http://modayakamoz.com/admin/check.php?kullaniciAdi="+dbHelper.getUser().getKullaniciAdi()+
                 "&sifre="+dbHelper.getUser().getSifre());
                    JSONObject object = new JSONObject(gelen);
                    durumObject = (new DurumObject(object.getString("id"),object.getString("yetki")
                        ,object.getString("kullanciAdi")
                        ,object.getString("sifre")));

                return "11";


            }
            catch (Exception e){
                return "HATAaaaaaaaa";
            }


        }

        @Override
        protected void onPostExecute(String s) {
            if (!s.equals("HATAaaaaaaaa")) {
                if (durumObject.getId().length() == 0) {
                    dbHelper.deleteUser();
                    Intent intent = new Intent(LuncherActivity.this, GirisActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    dbHelper.deleteUser();
                    dbHelper.insertUser(durumObject.getKullanciAdi(), durumObject.getSifre(), durumObject.getYetki(), durumObject.getId());
                    Intent intent = new Intent(LuncherActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
            else
                Toast.makeText(getApplicationContext(),"İnternet Bağlantınızı Kontrol Ediniz!",Toast.LENGTH_LONG).show();

        }
    }
}
