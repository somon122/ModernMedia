package com.world_tech_points.modern_media;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class WebViewActivity extends AppCompatActivity {


    private WebView webView;
    private String webLink;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);


        webView = findViewById(R.id.webView_id);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null){
            webLink = bundle.getString("link");
            checkConnection(webLink);

        }else {

            Toast.makeText(this, "Web link is missing", Toast.LENGTH_SHORT).show();
        }

    }

    private void checkConnection(String link){

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

               mobileDataAlert(link);


            }
            else if (netSubType == TelephonyManager.NETWORK_TYPE_1xRTT ||
                    netSubType == TelephonyManager.NETWORK_TYPE_GPRS ||
                    netSubType == TelephonyManager.NETWORK_TYPE_EDGE){

               lowMobileDataAlert();
            }



        }else if (isWifi){
            web_view(link);
        }
        else
        {
          dataConnectionAlert();
        }





    }

    private void web_view (String link){


        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(link);

    }


    private void mobileDataAlert(final String link){

        AlertDialog.Builder builder = new AlertDialog.Builder(WebViewActivity.this);

        builder.setTitle("Mobile data Alert!")
                .setMessage("Have you enough data?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        web_view(link);

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

    private void lowMobileDataAlert(){

        AlertDialog.Builder builder = new AlertDialog.Builder(WebViewActivity.this);

        builder.setTitle("Low data Alert!")
                .setMessage("You data connection is not available")
                .setPositiveButton("Try again", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        startActivity(new Intent(WebViewActivity.this,WebViewActivity.class));
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
 private void dataConnectionAlert(){

        AlertDialog.Builder builder = new AlertDialog.Builder(WebViewActivity.this);

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
