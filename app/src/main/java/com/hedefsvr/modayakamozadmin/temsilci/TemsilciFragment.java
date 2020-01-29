package com.hedefsvr.modayakamozadmin.temsilci;


import android.Manifest;
import android.content.ContentProviderOperation;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.hedefsvr.modayakamozadmin.R;
import com.hedefsvr.modayakamozadmin.helper.ReadURL;
import com.hedefsvr.modayakamozadmin.helper.YuklemeFragment;
import com.hedefsvr.modayakamozadmin.analiz.TemsilciAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;



public class TemsilciFragment extends Fragment {

    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    List<TemsilciObject> temsilciObjects;
    TemsilciAdapter adapter;
    ListView temsilci;
    EditText arama;
    Button ekle,aktar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_temsilci, container, false);
        temsilci=(ListView)view.findViewById(R.id.temsilciler);
        arama=(EditText)view.findViewById(R.id.arama2);
        ekle=(Button)view.findViewById(R.id.button4);
        aktar=(Button)view.findViewById(R.id.button11);
        /*ContentResolver contentResolver = getActivity().getContentResolver();
        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        while (cursor.moveToNext()) {
            String lookupKey = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.LOOKUP_KEY));
            Uri uri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_LOOKUP_URI, lookupKey);
            contentResolver.delete(uri, null, null);
        }*/
        /*aktar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (temsilciObjects.size()>0){
                    if (Build.VERSION.SDK_INT >= 23)
                    {
                        if (checkAndRequestPermissions())
                        {
                            setAktar();
                        } else {
                            checkAndRequestPermissions();
                        }
                    }
                    else
                    {
                        setAktar();
                    }
                }

            }
        });*/

        ekle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentActivity activity = (FragmentActivity)(getContext());
                FragmentManager fm = activity.getSupportFragmentManager();
                TemsilciEkleFragment alertDialog = new TemsilciEkleFragment();
                alertDialog.setTemsilciFragment(TemsilciFragment.this);
                alertDialog.show(fm,"fragment_alert");
            }
        });



        new getData().execute();
        return view;
    }
    private  boolean checkAndRequestPermissions() {
        int permissionSendMessage = ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.WRITE_CONTACTS);
        int locationPermission = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_CONTACTS);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (locationPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_CONTACTS);
        }
        if (permissionSendMessage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_CONTACTS);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(getActivity(), listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    public class getData extends AsyncTask<String, String, String> {
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
            temsilciObjects=new ArrayList<TemsilciObject>();

            ReadURL readURL=new ReadURL();
            try{
                Random r = new Random();
                int i1 = (r.nextInt(80) + 65);
                String gelenData=readURL.readURL("http://modayakamoz.com/admin/get_temsilciler.php?ID="+i1);
              JSONArray array=new JSONArray(gelenData);



                for (int i=0;i<array.length();i++){
                    JSONObject object1=array.getJSONObject(i);
                    temsilciObjects.add(new TemsilciObject(object1.getInt("ID"),object1.getInt("toplamSatis"),object1.getString("ad"),
                            object1.getString("soyad"),object1.getString("komisyoncu_adi"),object1.getString("sifre")
                            ,object1.getString("banka_adi"),object1.getString("iban"),object1.getString("tel")
                            ,Integer.parseInt(object1.getString("komisyon_yuzde")),Integer.parseInt(object1.getString("komisyon_tl"))));
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
                adapter=new TemsilciAdapter(getActivity(),temsilciObjects,TemsilciFragment.this);
                temsilci.setAdapter(adapter);
                arama.addTextChangedListener(new TextWatcher() {

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        System.out.println("Text ["+s+"]");

                        adapter.getFilter().filter(s.toString());
                    }

                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count,
                                                  int after) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                    }
                });
            } progress.dismiss();

        }
    }
    public void setAktar (){

        for (int i = 0; i<temsilciObjects.size(); i++){
            String displayName  = "MY "+temsilciObjects.get(i).getKullanciAdi() + " " + temsilciObjects.get(i).getAd();
            String mobileNumber = temsilciObjects.get(i).getTel();

            ArrayList<ContentProviderOperation> contentProviderOperationArrayList = new ArrayList <ContentProviderOperation> ();

            contentProviderOperationArrayList.add(ContentProviderOperation.newInsert(
                    ContactsContract.RawContacts.CONTENT_URI)
                    .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                    .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
                    .build());

            //Name
            if (displayName != null) {
                contentProviderOperationArrayList.add(ContentProviderOperation.newInsert(
                        ContactsContract.Data.CONTENT_URI)
                        .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                        .withValue(ContactsContract.Data.MIMETYPE,
                                ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                        .withValue(
                                ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME,
                                displayName).build());
            }

            //Mobile Number
            if (mobileNumber != null) {
                contentProviderOperationArrayList.add(ContentProviderOperation.
                        newInsert(ContactsContract.Data.CONTENT_URI)
                        .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                        .withValue(ContactsContract.Data.MIMETYPE,
                                ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                        .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, mobileNumber)
                        .withValue(ContactsContract.CommonDataKinds.Phone.TYPE,
                                ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
                        .build());
            }

            //Home


            // Creating new contact
            try {
                getActivity().getContentResolver().applyBatch(ContactsContract.AUTHORITY, contentProviderOperationArrayList);
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

        Toast.makeText(getContext(), "Aktarma Tamamlandı.", Toast.LENGTH_LONG).show();

    }

}
