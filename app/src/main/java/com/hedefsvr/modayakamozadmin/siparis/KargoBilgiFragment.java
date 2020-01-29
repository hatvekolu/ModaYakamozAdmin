package com.hedefsvr.modayakamozadmin.siparis;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.hedefsvr.modayakamozadmin.R;

public class KargoBilgiFragment extends DialogFragment {

    private SatisObject so;
    public void setUrun(SatisObject so) {
        this.so = so;
    }
    WebView webView;
    Button back;
    String link;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_kargo_bilgi, container, false);
        webView=(WebView)view.findViewById(R.id.web);
        back=(Button)view.findViewById(R.id.button13);
        webView.getSettings().setJavaScriptEnabled(true) ;
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().getDisplayZoomControls();
        try {
            webView.loadUrl(so.getUrl());

        }
        catch (Exception e){

        }


        webView.setWebViewClient(new myWebViewClient());

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                webView.loadUrl(link);
            }
        });


        return view;
    }
    public class myWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            link=webView.getUrl();
            try {
                view.loadUrl(url);
            }
            catch (Exception e){

            }
            return true;
        }
    }
}
