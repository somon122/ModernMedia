package com.world_tech_points.modern_media;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.world_tech_points.modern_media.ShowAllData.ShowActivity;

public class CategoryActivity extends AppCompatActivity implements View.OnClickListener {


    private TextView banglaMovieTV, englishMovieTV, hindiMovieTV, chineseMovieTV;
    private TextView banglaDramaTV, englishDramaTV, hindiDramaTV, chineseDramaTV;
    private TextView banglaMovieTrailersTV, englishMovieTrailersTV, hindiMovieTrailersTV, chineseMovieTrailersTV;
    private TextView banglaMp3TV, englishMp3TV, hindiMp3TV;

    private LinearLayout movieLayout,dramaLayout,trailersLayout,mp3Layout;

    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        Toolbar toolbar = findViewById(R.id.categoryToolBar_id);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle("Media Category");

        MobileAds.initialize(this,
                getString(R.string.admobAppId));
        mAdView = findViewById(R.id.categoryBannerAdView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        movieLayout = findViewById(R.id.movieCategory_id);
        dramaLayout = findViewById(R.id.dramaCategory_id);
        trailersLayout = findViewById(R.id.trailersCategory_id);
        mp3Layout = findViewById(R.id.mp3Category_id);

        banglaDramaTV = findViewById(R.id.banglaDrama_id);
        englishDramaTV = findViewById(R.id.englishDrama_id);
        hindiDramaTV = findViewById(R.id.hindiDrama_id);
        chineseDramaTV = findViewById(R.id.chineseDrama_id);

        banglaMovieTV = findViewById(R.id.banglaMovies_id);
        englishMovieTV = findViewById(R.id.englishMovies_id);
        hindiMovieTV = findViewById(R.id.hindiMovies_id);
        chineseMovieTV = findViewById(R.id.chineseMovie_id);

        banglaMovieTrailersTV = findViewById(R.id.trailersBanglaMovies_id);
        englishMovieTrailersTV = findViewById(R.id.trailersEnglishMovies_id);
        hindiMovieTrailersTV = findViewById(R.id.trailersHindiMovies_id);
        chineseMovieTrailersTV = findViewById(R.id.trailersChineseMovie_id);

        banglaMp3TV = findViewById(R.id.banglaMp3_id);
        englishMp3TV = findViewById(R.id.englishMp3_id);
        hindiMp3TV = findViewById(R.id.hindiMp3_id);


        movieLayout.setVisibility(View.GONE);
        dramaLayout.setVisibility(View.GONE);
        mp3Layout.setVisibility(View.GONE);
        trailersLayout.setVisibility(View.GONE);


        banglaMp3TV.setOnClickListener(this);
        englishMp3TV.setOnClickListener(this);
        hindiMp3TV.setOnClickListener(this);

        banglaMovieTrailersTV.setOnClickListener(this);
        englishMovieTrailersTV.setOnClickListener(this);
        hindiMovieTrailersTV.setOnClickListener(this);
        chineseMovieTrailersTV.setOnClickListener(this);

        banglaDramaTV.setOnClickListener(this);
        englishDramaTV.setOnClickListener(this);
        hindiDramaTV.setOnClickListener(this);
        chineseDramaTV.setOnClickListener(this);

        banglaMovieTV.setOnClickListener(this);
        englishMovieTV.setOnClickListener(this);
        hindiMovieTV.setOnClickListener(this);
        chineseMovieTV.setOnClickListener(this);


        Bundle bundle = getIntent().getExtras();

        if (bundle != null){

            String category = bundle.getString("category");

            if (category.equals("movie")){

                movieLayout.setVisibility(View.VISIBLE);
            }else if (category.equals("drama")){
                dramaLayout.setVisibility(View.VISIBLE);

            }else if (category.equals("mp3")){
                mp3Layout.setVisibility(View.VISIBLE);

            }else if (category.equals("trailers")){
                trailersLayout.setVisibility(View.VISIBLE);


            }else {
                Toast.makeText(this, "No matches", Toast.LENGTH_SHORT).show();
            }





        }else {
            Toast.makeText(this, "Please try again ok!", Toast.LENGTH_SHORT).show();
        }



    }

    @Override
    public void onClick(View v) {

        int id = v.getId();


        ///   mp3 click listener -------

      if (id==R.id.banglaMp3_id){

          categorySent("BanglaMp3Song");

      }else if (id==R.id.hindiMp3_id){

          categorySent("HindiMp3Song");


      }else if (id==R.id.englishMp3_id){

          categorySent("EnglishMp3Song");


      }

      ///   movie click listener -------

      else if (id==R.id.banglaMovies_id){

          categorySent("BanglaMovie");

      }else if (id==R.id.englishMovies_id){

          categorySent("EnglishMovie");


      }else if (id==R.id.hindiMovies_id){

          categorySent("HindiMovie");


      }else if (id==R.id.chineseMovie_id){

          categorySent("ChineseMovie");


      }


      ///   drama click listener -------

      else if (id==R.id.banglaDrama_id){

          categorySent("BanglaDrama");


      } else if (id==R.id.englishDrama_id){

          categorySent("EnglishDrama");


      } else if (id==R.id.hindiDrama_id){

          categorySent("HindiDrama");


      } else if (id==R.id.chineseDrama_id){

          categorySent("ChineseDrama");


      }

      ///   movie trailers click listener -------


      else if (id==R.id.trailersBanglaMovies_id){

         categorySent("BanglaMovieTrailers");


      } else if (id==R.id.trailersEnglishMovies_id){

          categorySent("EnglishMovieTrailers");

      } else if (id==R.id.trailersHindiMovies_id){

          categorySent("HindiMovieTrailers");

      } else if (id==R.id.trailersChineseMovie_id){

          categorySent("ChineseMovieTrailers");


      }


    }

    private void categorySent(String value){

        Intent intent = new Intent(CategoryActivity.this, ShowActivity.class);
        intent.putExtra("category",value);
        startActivity(intent);


    }

}
