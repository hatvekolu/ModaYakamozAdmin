package com.hedefsvr.modayakamozadmin.qrcode;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.hedefsvr.modayakamozadmin.R;
import com.hedefsvr.modayakamozadmin.urunler.DetayFragment;
import com.zj.btsdk.BluetoothService;
import com.zj.btsdk.PrintPic;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * A simple {@link Fragment} subclass.
 */
public class GenerateQRFragment extends DialogFragment {

    private static final int REQUEST_ENABLE_BT = 2;
    private static final int REQUEST_CONNECT_DEVICE = 1;
    private static int id;
    public void setUrun(int id) {
        this.id = id;
    }
    private static String beden;
    public void setBeden(String beden) {
        this.beden = beden;
    }
    ImageView imageView;
    TextView textView;
    LinearLayout relativeLayout,texxt;
    Bitmap b;
    Button yazdir;
    EditText adet;
    Bitmap bitmap;
    int w;
    int h;
    byte[] byteArray;
    String encoded;
    Button find;
    BluetoothService mService = null;
    BluetoothDevice con_dev = null;
    CheckBox xprinter,zywell;
    public static String macXprint = "DC:0D:30:80:EB:9A", macZywell = "DC:0D:30:7A:C1:82";
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case BluetoothService.MESSAGE_STATE_CHANGE:
                    switch (msg.arg1) {
                        case BluetoothService.STATE_CONNECTED:
                            Toast.makeText(getContext(), "Yazıcıya Bağlandı",
                                    Toast.LENGTH_SHORT).show();
                            if (DetayFragment.macAdres.equals(macZywell)){
                                zywell.setChecked(true);
                                xprinter.setChecked(false);
                            }
                            else if (DetayFragment.macAdres.equals(macXprint)){
                                xprinter.setChecked(true);
                                zywell.setChecked(false);
                            }
                            yazdir.setVisibility(View.VISIBLE);
                        case BluetoothService.STATE_LISTEN:

                    }
                    break;
                case BluetoothService.MESSAGE_UNABLE_CONNECT:
                    Toast.makeText(getContext(), "Bluetooth Erşimi Yok",
                            Toast.LENGTH_SHORT).show();
                    xprinter.setChecked(false);
                    zywell.setChecked(false);
                    DetayFragment.macAdres = "";
                    break;
            }
        }

    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_generate_qr, container, false);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        imageView = (ImageView)view.findViewById(R.id.imageView14);
        find = (Button)view.findViewById(R.id.button52);
        textView = (TextView)view.findViewById(R.id.textView100);
        yazdir = (Button)view.findViewById(R.id.button28);
        adet = (EditText)view.findViewById(R.id.editText34);
        mService = new BluetoothService(getActivity(), mHandler);
        xprinter = (CheckBox)view.findViewById(R.id.checkBox3);
        zywell = (CheckBox)view.findViewById(R.id.checkBox4);
        if (!mService.isAvailable()) {
            Toast.makeText(getContext(), "Bluetooth Erşimi Yok", Toast.LENGTH_LONG).show();
        }

        if (DetayFragment.macAdres.equals(macXprint) && mService.isAvailable()){
            xprinter.setChecked(true);
            zywell.setChecked(false);

        }
        else if (DetayFragment.macAdres.equals(macZywell) && mService.isAvailable()){
            xprinter.setChecked(false);
            zywell.setChecked(true);

        }

        textView.setText(""+id+"\n"+"\n"+beden);
        try {
            bitmap = encodeAsBitmap("[{\"ID\":\""+id+"\",\"beden\":\""+beden+"\"}]");

            imageView.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        layoutToImage(view);

        yazdir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (adet.getText().toString().length()>0)
                    printImage(Integer.parseInt(adet.getText().toString()));


            }
        });
        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent serverIntent = new Intent(getContext(), DeviceListActivity.class);
                startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE);
            }
        });
        if (DetayFragment.macAdres.length() > 0 && mService.isAvailable() ){
            con_dev = mService.getDevByMac(DetayFragment.macAdres);
            mService.connect(con_dev);
        }

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mService != null)
            mService.stop();
        mService = null;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_ENABLE_BT:
                if (resultCode == Activity.RESULT_OK) {
                    Toast.makeText(getContext(), "Bluetooth open successful", Toast.LENGTH_LONG).show();
                } else {

                }
                break;
            case REQUEST_CONNECT_DEVICE:
                if (resultCode == Activity.RESULT_OK) {
                    String address = data.getExtras()
                            .getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
                    con_dev = mService.getDevByMac(address);
                    DetayFragment.macAdres = address;
                    mService.connect(con_dev);


                }
                break;
        }
    }

    @SuppressLint("SdCardPath")
    private void printImage(int count) {
        byte[] sendData = null;
        PrintPic pg = new PrintPic();
        pg.initCanvas(800);
        pg.initPaint();
        pg.drawImage(0, 0, "/mnt/sdcard/print_test.png");
        sendData = pg.printDraw();
        for (int i = 0; i< count; i++) {
            mService.write(sendData);
            if (xprinter.isChecked()){
                mService.write((PrinterCommands.FEED_LINE));
                mService.write((PrinterCommands.FEED_LINE));
                mService.write((PrinterCommands.FEED_LINE));
                mService.write((PrinterCommands.FEED_LINE));
            }
            mService.write((PrinterCommands.FS_FONT_ALIGN));
            mService.write((PrinterCommands.ESC_ALIGN_CENTER));
            if (!xprinter.isChecked()){
                mService.write((PrinterCommands.FEED_PAPER_AND_CUT));
            }


        }


    }

    Bitmap encodeAsBitmap(String str) throws WriterException {
        BitMatrix result;
        try {
            result = new MultiFormatWriter().encode(str,
                    BarcodeFormat.QR_CODE, 800, 800, null);
        } catch (IllegalArgumentException iae) {
            // Unsupported format
            return null;
        }
        w = result.getWidth();
        h = result.getHeight();
        int[] pixels = new int[w * h];
        for (int y = 0; y < h; y++) {
            int offset = y * w;
            for (int x = 0; x < w; x++) {
                pixels[offset + x] = result.get(x, y) ? Color.BLACK : Color.WHITE;
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, 800, 0, 0, w, h);
        return bitmap;
    }
    public void layoutToImage(View view) {
        // get view group using reference

        relativeLayout = (LinearLayout) view.findViewById(R.id.back);

        texxt = (LinearLayout) view.findViewById(R.id.texxt);

        relativeLayout.setDrawingCacheEnabled(true);
        // this is the important code :)
        // Without it the view will have a dimension of 0,0 and the bitmap will be null
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT,
                1.0f
        );
        relativeLayout.setGravity(View.TEXT_ALIGNMENT_CENTER);
        relativeLayout.setLayoutParams(param);
        relativeLayout.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));

        relativeLayout.layout(0, 0, relativeLayout.getMeasuredWidth(), relativeLayout.getMeasuredHeight());

        relativeLayout.buildDrawingCache(true);
        b = Bitmap.createBitmap(relativeLayout.getDrawingCache());
        relativeLayout.setDrawingCacheEnabled(false); // clear drawing cache

        texxt.setVisibility(View.GONE);
        imageView.setImageBitmap(b);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byteArray = byteArrayOutputStream .toByteArray();
        encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
        BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
        Bitmap kk = drawable.getBitmap();
        File sdCardDirectory = Environment.getExternalStorageDirectory();
        File image = new File(sdCardDirectory, "print_test.png");
        boolean success = false;

        // Encode the file as a PNG image.
        FileOutputStream outStream;
        try {

            outStream = new FileOutputStream(image);
            kk.compress(Bitmap.CompressFormat.PNG, 100, outStream);
        /* 100 to keep full quality of the image */

            outStream.flush();
            outStream.close();
            success = true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
