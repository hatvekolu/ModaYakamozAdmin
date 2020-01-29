package com.hedefsvr.modayakamozadmin.urunler;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hedefsvr.modayakamozadmin.R;
import com.hedefsvr.modayakamozadmin.helper.DBHelper;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import static android.support.v4.content.PermissionChecker.checkSelfPermission;

/**
 * A simple {@link Fragment} subclass.
 */
public class ResimYukleFragment extends DialogFragment {

    String apiURL       = "http://modayakamoz.com./admin"; // Without ending slash
    String apiPassword  = "qw2e3erty6uiop";
    String currentImagePath = "";
    String currentImage = "";
    TextView textViewDynamicText;
    Button yukle;
    DBHelper dbHelper;
    ImageView image;
    InputStream imageStream;
    Bitmap selectedImage;
    Uri imageUri;
    String destinationFilename ="";
    private int id;
    public void setUrun(int id) {
        this.id = id;
    }
    private DetayFragment df;
    public void setDetay(DetayFragment df) {
        this.df = df;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_resim_yukle, container, false);
        yukle = (Button)view.findViewById(R.id.button33);
        image = (ImageView)view.findViewById(R.id.imageView15) ;
        dbHelper = new DBHelper(getContext());
        textViewDynamicText = (TextView) view.findViewById(R.id.textViewDynamicText); // Dynamic text
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, 1);
            }
        });
        yukle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (destinationFilename.length() > 0){
                    String urlToApi = apiURL + "/image_upload.php";
                    Map mapData = new HashMap();
                    mapData.put("inp_api_password", apiPassword);
                    mapData.put("ID", id);

                    HttpRequestLongOperation task = new HttpRequestLongOperation(getContext(),urlToApi, "post_image", mapData, destinationFilename, textViewDynamicText,"",df, ResimYukleFragment.this, new HttpRequestLongOperation.TaskListener() {
                        @Override
                        public void onFinished(String result) {
                            // Do Something after the task has finished
                            imageUploadResult();
                        }
                    });
                    task.execute();
                }
                else
                    Toast.makeText(getContext(),"Resim Seçiniz!!",Toast.LENGTH_SHORT).show();


            }
        });
        if(android.os.Build.VERSION.SDK_INT>22){
            checkPermissionRead();
            checkPermissionWrite();
        }
        return view;
    }
    private void checkPermissionRead(){
        if (checkSelfPermission(getContext(),android.Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (shouldShowRequestPermissionRationale(
                    android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
                // Explain to the user why we need to read the contacts
            }

            requestPermissions(new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                    1);

            // MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE is an
            // app-defined int constant that should be quite unique

            return;
        }
    } // checkPermissionRead
    private void checkPermissionWrite(){

        if (checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (shouldShowRequestPermissionRationale(
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                // Explain to the user why we need to read the contacts
            }

            requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    1);

            // MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE is an
            // app-defined int constant that should be quite unique

            return;
        }
    } // checkPermissionWrite
    private String pictureImagePath = "";
    private void openBackCamera() {

    }

    public void imageUploadResult() {
        // Dynamic text

        String dynamicText = textViewDynamicText.getText().toString();


        // Split
        int index = dynamicText.lastIndexOf('/');
        try {
            currentImagePath = dynamicText.substring(0, index);
        }
        catch (Exception e){
            Toast.makeText(getContext(), "path: " + e.toString(), Toast.LENGTH_LONG).show();
        }
        try {
            currentImage = dynamicText.substring(index,dynamicText.length());
        }
        catch (Exception e){
            Toast.makeText(getContext(), "image: " + e.toString(), Toast.LENGTH_LONG).show();
        }


        // Load new image
        // Todo: loadImage();

    } // imageUploadResult
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        if (data != null){
            try {
                imageUri = data.getData();
                imageStream = getActivity().getContentResolver().openInputStream(imageUri);
                selectedImage = BitmapFactory.decodeStream(imageStream);
                image.setImageBitmap(selectedImage);
                destinationFilename = FilePath.getPath(getContext(), imageUri);
                image.setRotation(getExifOrientation(destinationFilename));

            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_LONG).show();
            }
        }
        else
            Toast.makeText(getContext(),"Resim Seçiniz!!",Toast.LENGTH_SHORT).show();


    }
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
