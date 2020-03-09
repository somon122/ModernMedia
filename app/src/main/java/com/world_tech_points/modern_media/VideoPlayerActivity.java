package com.world_tech_points.modern_media;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class VideoPlayerActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {


    YouTubePlayerView playerView;
    String key = "AIzaSyBOzERj-Xnyneb5AHCyGYojRuaKvg7WvVY";
    String id = "7lQL3CVfhv8";
    int mCount = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);


        Bundle bundle = getIntent().getExtras();

        if (bundle != null){
            id = bundle.getString("id");

        }else {

            Toast.makeText(this, "Web link is missing", Toast.LENGTH_SHORT).show();
        }


        playerView = findViewById(R.id.youTubePlayerView_id);
        playerView.initialize(key,this);

    }


    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {

        ConnectivityManager manager = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
        boolean is3g = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
                .isConnectedOrConnecting();

        boolean isWifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                .isConnectedOrConnecting();


        if (is3g) {
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo network = connectivityManager.getActiveNetworkInfo();
            int netSubType = network.getSubtype();
            if(netSubType == TelephonyManager.NETWORK_TYPE_HSPAP ||
                    netSubType == TelephonyManager.NETWORK_TYPE_HSDPA ||
                    netSubType == TelephonyManager.NETWORK_TYPE_HSPA) {

                if (mCount >=1){

                    if (!b){
                        youTubePlayer.cueVideo(id);
                    }
                }else {
                   mobileDataAlert();
                }

            }
            else if (netSubType == TelephonyManager.NETWORK_TYPE_1xRTT ||
                    netSubType == TelephonyManager.NETWORK_TYPE_GPRS ||
                    netSubType == TelephonyManager.NETWORK_TYPE_EDGE){

                lowMobileDataAlert();
            }

        }else if (isWifi){


            if (!b){
                youTubePlayer.cueVideo(id);
            }

        }
        else
        {
            dataConnectionAlert();
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

    }
    private void lowMobileDataAlert(){

        AlertDialog.Builder builder = new AlertDialog.Builder(VideoPlayerActivity.this);

        builder.setTitle("Low data Alert!")
                .setMessage("You data connection is not available")
                .setPositiveButton("Try again", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        mCount = mCount+1;
                        playerView = findViewById(R.id.youTubePlayerView_id);
                        playerView.initialize(key,VideoPlayerActivity.this);

                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                finish();

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();


    }

    private void mobileDataAlert(){

        AlertDialog.Builder builder = new AlertDialog.Builder(VideoPlayerActivity.this);

        builder.setTitle("Mobile data Alert!")
                .setMessage("Have you enough data?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        playerView = findViewById(R.id.youTubePlayerView_id);
                        playerView.initialize(key,VideoPlayerActivity.this);

                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();


    }



    private void dataConnectionAlert(){

        AlertDialog.Builder builder = new AlertDialog.Builder(VideoPlayerActivity.this);

        builder.setTitle("Data Connection Alert!")
                .setMessage("Please Connected your Internet first")
                .setPositiveButton("Then try again", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        finish();

                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                finish();

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();


    }

}