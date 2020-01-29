package com.hedefsvr.modayakamozadmin.helper;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hedefsvr.modayakamozadmin.R;

import org.json.JSONObject;

public class GirisActivity extends AppCompatActivity {
    EditText kullaniciAdi,sifre;
    TextView basvur,hatali;
    Button giris;
    DBHelper dbHelper;
    DurumObject durumObject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giris);

        basvur=(TextView)findViewById(R.id.textView64);
        hatali=(TextView)findViewById(R.id.textView16);
        kullaniciAdi=(EditText)findViewById(R.id.editText2);
        sifre=(EditText)findViewById(R.id.editText14);
        giris=(Button)findViewById(R.id.button10);

        dbHelper=new DBHelper(getApplicationContext());

        UserObject uo=dbHelper.getUser();
        if (uo.getKullaniciAdi().length()>0){
            Intent intent = new Intent(GirisActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }



        giris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (kullaniciAdi.getText().length()>0 && sifre.getText().length()>0)
                    new getData().execute(kullaniciAdi.getText().toString(),sifre.getText().toString());
                else
                    hatali.setVisibility(View.VISIBLE);
            }
        });




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

                JSONObject object = new JSONObject(data);
                durumObject = (new DurumObject(object.getString("id"),object.getString("yetki")
                        ,object.getString("kullanciAdi")
                        ,object.getString("sifre")));


                return "1";
            }
            catch (Exception e){
                return "HATA";
            }

        }

        @Override
        protected void onPostExecute(String results)
        {
            if (!results.equals("HATA")){
                dbHelper.deleteUser();
                dbHelper.insertUser(durumObject.getKullanciAdi(),durumObject.getSifre(),durumObject.getYetki(),durumObject.getId());
                Intent intent = new Intent(GirisActivity.this, MainActivity.class);
                startActivity(intent);
                finish();

            }
            else
                hatali.setVisibility(View.VISIBLE);

            progress.dismiss();


        }
    }
}
