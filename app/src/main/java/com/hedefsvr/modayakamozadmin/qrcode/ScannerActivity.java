package com.hedefsvr.modayakamozadmin.qrcode;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import me.dm7.barcodescanner.zxing.ZXingScannerView;


public class ScannerActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    ZXingScannerView ScannerView;
    private int id;
    public void setUrun(int id) {
        this.id = id;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScannerView = new ZXingScannerView(this);
        setContentView(ScannerView);

    }

    @Override
    public void handleResult(com.google.zxing.Result result) {
        QRGenelFragment.resultText.setText(result.getText());
        onBackPressed();
    }
    @Override
    protected void onPause(){
        super.onPause();
        ScannerView.stopCamera();
    }
    @Override
    protected void onResume(){
        super.onResume();
        ScannerView.setResultHandler(this);
        ScannerView.startCamera();
    }

}
