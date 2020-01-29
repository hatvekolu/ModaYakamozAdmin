package com.hedefsvr.modayakamozadmin.urunler;

/**
 * Created by Lenovo- on 22.12.2017.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.widget.TextView;
import android.widget.Toast;

import com.hedefsvr.modayakamozadmin.helper.YuklemeFragment;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class HttpRequestLongOperation extends AsyncTask<String, Void, String> {
    private Context context;
    private String durum;
    private DetayFragment detayFragment;
    private ResimYukleFragment resimYukleFragment;
    private String inputUrl;
    private String stringMethod;
    private String stringSend = "";
    private String stringImg = "";
    private Map<String, String> mapSend  = new HashMap<String, String>();
    private TextView textViewJSON;
    private final TaskListener taskListener; // This is the reference to the associated listener
    YuklemeFragment progress;




    public interface TaskListener {
        public void onFinished(String result);
    }

    /*- Constructor GET, SEND --------------------------------------------------------------- */
    public HttpRequestLongOperation(Context ctx, String url, String method, String send, TextView dynamicTextView, TaskListener listener) {
        context = ctx;
        inputUrl = url;
        stringMethod = method;
        stringSend = send;
        textViewJSON = dynamicTextView;
        this.taskListener = listener; // The listener reference is passed in through the constructor
    }
    public HttpRequestLongOperation(Context ctx, String url, String method, Map<String, String> data, TextView dynamicTextView, TaskListener listener) {
        context = ctx;
        inputUrl = url;
        stringMethod = method;
        mapSend = data;
        textViewJSON = dynamicTextView;
        this.taskListener = listener; // The listener reference is passed in through the constructor
    }
    public HttpRequestLongOperation(Context ctx, String url, String method, Map<String, String> data, String img, TextView dynamicTextView,String son ,DetayFragment df, ResimYukleFragment ryf,TaskListener listener) {
        context =  ctx;
        durum=son;
        inputUrl = url;
        stringMethod = method;
        mapSend = data;
        stringImg = img;
        detayFragment = df;
        resimYukleFragment = ryf;
        textViewJSON = dynamicTextView;
        this.taskListener = listener; // The listener reference is passed in through the constructor
    }
    public HttpRequestLongOperation(Context ctx, String url, String method, String send, TaskListener listener) {
        context = ctx;
        inputUrl = url;
        stringMethod = method;
        stringSend = send;
        this.taskListener = listener; // The listener reference is passed in through the constructor
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        FragmentManager fm = ((AppCompatActivity)context).getSupportFragmentManager();
        progress = new YuklemeFragment();
        progress.show(fm, "");


    }


    @Override
    protected String doInBackground(String... params) {
        // Run methods
        String stringResponse ="";
        try {
            if(stringMethod.equals("get")) {
                stringResponse = HttpRequest.get(inputUrl).body();
            }
            else if(stringMethod.equals("post")){
                if(!(stringSend.equals(""))){
                    int intResponse = HttpRequest.post(inputUrl).send(stringSend).code();
                    stringResponse = "" + intResponse;
                }
                else {
                    try{
                        stringResponse = HttpRequest.post(inputUrl).form(mapSend).body();
                    }
                    catch (Exception e){
                        return e.toString();
                    }
                }
            }
            else if(stringMethod.equals("post_image")){


                // Method 1 - Base 64
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 3;
                Bitmap bm = BitmapFactory.decodeFile(stringImg, options);
                //rotate bitmap
                Matrix matrix = new Matrix();
                matrix.postRotate(getExifOrientation(stringImg));
                //create new rotated bitmap
                bm = Bitmap.createBitmap(bm, 0, 0,bm.getWidth(), bm.getHeight(), matrix, true);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                try {
                    bm.compress(Bitmap.CompressFormat.JPEG, 80, baos);
                } catch (Exception compresse) {
                    Toast.makeText(context, "Compress error: " + compresse.toString(), Toast.LENGTH_LONG).show();
                }
                byte[] byteImage_photo = baos.toByteArray(); // bitmap object

                String encodedImage = Base64.encodeToString(byteImage_photo,Base64.DEFAULT); //generate base64 string of image


                mapSend.put("inp_image_base", encodedImage);
                stringResponse = HttpRequest.post(inputUrl).form(mapSend).body();

            } // post_image
        }
        catch(Exception e){
            return e.toString();
        }
        return stringResponse;
    }

    @Override
    protected void onPostExecute(String result) {
        // Set text view with result string
        if(textViewJSON == null){
            Toast.makeText(context, "NULL", Toast.LENGTH_SHORT).show();
        }
        else {
            textViewJSON.setText(result);
        }
        // In onPostExecute we check if the listener is valid
        if(this.taskListener != null) {

            // And if it is we call the callback function on it.
            this.taskListener.onFinished(result);
        }
        progress.dismiss();
        detayFragment.yenile();
        resimYukleFragment.dismiss();

    }

    @Override
    protected void onProgressUpdate(Void... values) {}
    public static int getExifOrientation(String filepath) {
        int degree = 0;
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(filepath);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        if (exif != null) {
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, -1);
            if (orientation != -1) {
                // We only recognise a subset of orientation tag values.
                switch (orientation) {
                    case ExifInterface.ORIENTATION_ROTATE_90:
                        degree = 90;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_180:
                        degree = 180;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_270:
                        degree = 270;
                        break;
                }

            }
        }

        return degree;
    }

}