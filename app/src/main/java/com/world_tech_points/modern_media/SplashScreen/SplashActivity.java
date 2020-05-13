package com.world_tech_points.modern_media.SplashScreen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.world_tech_points.modern_media.R;

public class SplashActivity extends AppCompatActivity {


    private static final int MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);


        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED){

            OpenFragment openFragment = new OpenFragment();
            openFragmentMethod(openFragment);


        }else {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){

                OpenFragment openFragment = new OpenFragment();
                openFragmentMethod(openFragment);
                
            }else {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){

                    fetchLocation();

                }else {

                    OpenFragment openFragment = new OpenFragment();
                    openFragmentMethod(openFragment);

                }
                
            }
            
          


        }


    }

    private void openFragmentMethod(OpenFragment openFragment) {

        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.openHost, openFragment)
                .commit();

    }


    private void fetchLocation() {


        if (ContextCompat.checkSelfPermission(SplashActivity.this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(SplashActivity.this,
                    Manifest.permission.ACCESS_COARSE_LOCATION)) {
                new AlertDialog.Builder(SplashActivity.this)
                        .setTitle("Required Location Permission")
                        .setMessage("You have to give this permission to access this feature")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ActivityCompat.requestPermissions(SplashActivity.this,
                                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                               finishAffinity();
                            }
                        })
                        .create()
                        .show();


            } else {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION);

            }
        }else {

            OpenFragment openFragment = new OpenFragment();
            openFragmentMethod(openFragment);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION){

            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                OpenFragment openFragment = new OpenFragment();
                openFragmentMethod(openFragment);

            }else{

                fetchLocation();

            }
        }else {

            fetchLocation();

        }

    }


}