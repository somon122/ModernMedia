package com.world_tech_points.modern_media;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.world_tech_points.modern_media.ShowAllData.ShowActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;
import android.view.Menu;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    DrawerLayout drawer;
    NavigationView navigationView;
    ViewPager viewPager;
    TabLayout tabLayout;
    PageViewerAdapter pageViewerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


         drawer = findViewById(R.id.drawer_layout);
         navigationView = findViewById(R.id.nav_view);

         viewPager = findViewById(R.id.viewPager_id);
         tabLayout = findViewById(R.id.tabLayout_id);

        tabLayout.addTab(tabLayout.newTab().setText("Home"));
        tabLayout.addTab(tabLayout.newTab().setText("TV"));
        tabLayout.addTab(tabLayout.newTab().setText("Movies"));
        tabLayout.addTab(tabLayout.newTab().setText("Trailers"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        pageViewerAdapter = new PageViewerAdapter(getSupportFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(pageViewerAdapter);
        viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                int id = menuItem.getItemId();

                if (id == R.id.nav_home){

                    startActivity(new Intent(MainActivity.this,MainActivity.class));
                    finish();

                }else if (id == R.id.nav_tvChannel){
                   categorySent("Tv_channel");

                }else if (id == R.id.nav_movies){
                    categorySent2("movie");
                }else if (id == R.id.nav_radioStation){
                    categorySent("Radio_station");
                }else if (id == R.id.nav_movieTrailers){
                    categorySent2("trailers");
                }else if (id == R.id.nav_sports){
                    categorySent("Sports_update");
                }else if (id == R.id.nav_drama){
                    categorySent2("drama");
                }else if (id == R.id.nav_MP3){
                    categorySent2("mp3");
                }else if (id == R.id.nav_newsPaper){
                    categorySent("Newspaper");
                }else if (id == R.id.nav_worldTechnology){
                    categorySent("World_technology");
                } else if (id == R.id.nav_privacy){

                    Toast.makeText(MainActivity.this, "nav_privacy", Toast.LENGTH_SHORT).show();
                   /* try {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("")));
                    }catch (ActivityNotFoundException e){

                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("")));

                    }*/


                }else if (id == R.id.nav_share){
                   shareApp();

                }else if (id == R.id.nav_RateUs){
                    try {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" +getPackageName())));
                    }catch (ActivityNotFoundException e){

                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" +getPackageName())));

                    }

                }else if (id == R.id.nav_exits){
                    exitsAlert();

                }else {
                    Toast.makeText(MainActivity.this, "Something Problem\nPlease try Again", Toast.LENGTH_SHORT).show();
                }


                DrawerLayout drawer = findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);


                return false;

            }
        });



    }

    private void shareApp() {

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        String shareBody = "App link : https://youtu.be/eGn-2tGoG6s";
        String shareSub = "You can enjoy by Modern Media Android App";
        intent.putExtra(Intent.EXTRA_SUBJECT,shareSub);
        intent.putExtra(Intent.EXTRA_TEXT,shareBody);
        startActivity(Intent.createChooser(intent,"Entertainment App"));

    }

    private void categorySent(String value){

        Intent intent = new Intent(MainActivity.this, ShowActivity.class);
        intent.putExtra("category",value);
        startActivity(intent);


    }

 private void categorySent2(String value){

        Intent intent = new Intent(MainActivity.this, CategoryActivity.class);
        intent.putExtra("category",value);
        startActivity(intent);


    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        switch (id){

            case R.id.action_exits:
                exitsAlert();
                break;
            case R.id.action_Admin:
                startActivity(new Intent(MainActivity.this,IdTestActivity.class));
                break;

            default:
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    private void exitsAlert() {

            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

            builder.setTitle("Exits Alert!")
                    .setMessage("Are you sure to exits!")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                           finishAffinity();

                        }
                    }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
            exitsAlert();
        } else {
            exitsAlert();
        }

    }
}




