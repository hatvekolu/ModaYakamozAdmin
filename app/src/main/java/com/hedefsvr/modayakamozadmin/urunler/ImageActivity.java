package com.hedefsvr.modayakamozadmin.urunler;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.VideoView;

import com.bogdwellers.pinchtozoom.ImageMatrixTouchHandler;
import com.hedefsvr.modayakamozadmin.R;
import com.vincan.medialoader.MediaLoader;

import it.sephiroth.android.library.picasso.Callback;
import it.sephiroth.android.library.picasso.Picasso;

public class ImageActivity extends AppCompatActivity {
    ImageView resim;
    ImageButton close;
    VideoView mVideoView;
    ProgressBar progressBar;
    String proxyUrl ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        resim = (ImageView)findViewById(R.id.imageView24);
        close = (ImageButton)findViewById(R.id.imageButton2);
        progressBar = (ProgressBar)findViewById(R.id.progressBar2);
        mVideoView = (VideoView) findViewById(R.id.videoView);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(0, 0);
            }
        });

        Intent intent = getIntent();
        String link = intent.getStringExtra("link");
        String type = intent.getStringExtra("type");
        if (!type.toLowerCase().equals("video")) {
            mVideoView.setVisibility(View.GONE);
            resim.setVisibility(View.VISIBLE);
            Picasso.with(getApplicationContext()).load(link).into(resim, new Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError() {

                }
            });
            resim.setOnTouchListener(new ImageMatrixTouchHandler(getApplicationContext()));
        }
        else if (type.toLowerCase().equals("video")){
            resim.setVisibility(View.GONE);
            mVideoView.setVisibility(View.VISIBLE);
            proxyUrl = MediaLoader.getInstance(getApplication()).getProxyUrl(link);
            mVideoView.setVideoPath(proxyUrl);
            mVideoView.start();
            progressBar.setVisibility(View.VISIBLE);
            mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.setLooping(true);
                    progressBar.setVisibility(View.GONE);
                }
            });
        }



    }
    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(0, 0);
    }
}
