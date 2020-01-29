package com.hedefsvr.modayakamozadmin.genel;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.hedefsvr.modayakamozadmin.R;
import com.hedefsvr.modayakamozadmin.helper.ReadURL;
import com.hedefsvr.modayakamozadmin.helper.YuklemeFragment;


public class BildirimFragment extends Fragment {

    EditText tittle,body;
    Button gonder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_bildirim, container, false);

        tittle=(EditText)view.findViewById(R.id.editText15);
        body=(EditText)view.findViewById(R.id.editText16);
        gonder=(Button)view.findViewById(R.id.button7);

        gonder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tittle.getText().toString().length()>0 && body.getText().toString().length()>0)
                    new  getData().execute(tittle.getText().toString().replaceAll(" ","_"),body.getText().toString().replaceAll(" ","_"));

                tittle.setText("");
                body.setText("");
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

                String gelenData=readURL.readURL("http://modayakamoz.com/admin/notification.php?title="+values[0]+"&body="+values[1]);



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


            } progress.dismiss();

        }
    }
}
