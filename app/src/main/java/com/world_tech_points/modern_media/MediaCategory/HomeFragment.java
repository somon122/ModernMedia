package com.world_tech_points.modern_media.MediaCategory;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdCallback;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.squareup.picasso.Picasso;
import com.world_tech_points.modern_media.CategoryActivity;
import com.world_tech_points.modern_media.Dramas.DramaAdapter;
import com.world_tech_points.modern_media.Dramas.DramaClass;
import com.world_tech_points.modern_media.MP3_Songs.Mp3_Adapter;
import com.world_tech_points.modern_media.MP3_Songs.Mp3_class;
import com.world_tech_points.modern_media.MainActivity;
import com.world_tech_points.modern_media.R;
import com.world_tech_points.modern_media.ShowAllData.ShowActivity;
import com.world_tech_points.modern_media.Trailers.TrailersAdapter;
import com.world_tech_points.modern_media.Trailers.TrailersClass;
import com.world_tech_points.modern_media.WebViewActivity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.CONNECTIVITY_SERVICE;


public class HomeFragment extends Fragment {


    private ViewFlipper viewFlipper;
    // ----- mp3 variable----
    private RecyclerView mp3RecyclerView;
    private RecyclerView dramaRecyclerView;
    private RecyclerView trailersRecyclerView;
    private int mCount;
    private Mp3_class mp3_class;
    private List<Mp3_class>mp3List;
    private Mp3_Adapter mp3_adapter;

    private DramaClass dramaClass;
    private List<DramaClass>dramaList;
    private DramaAdapter drama_adapter;

    private TrailersClass trailersClass;
    private List<TrailersClass>trailersList;
    private TrailersAdapter trailersAdapter;


    private RewardedAd rewardedAd;


    TextView dramaAllView,mp3TV,trailersAllView;
    private CardView sportsBt, newspaperBt, movieBt,mTrailersBt,musicBt,radioBt,tvBt,dramaBt,worldTBt;

    String url = "https://www.worldometers.info/coronavirus/?fbclid=IwAR04NFhGersnLjrGF-lwk81OfyzYW8cYBFC2idrfrNTMrmOLQMdbJEz26y8";


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);


        MobileAds.initialize(getContext(),
                getString(R.string.admobAppId));
        rewardedAd = new RewardedAd(getContext(),getString(R.string.admobRewardAd)
                );

        RewardedAdLoadCallback adLoadCallback = new RewardedAdLoadCallback() {
            @Override
            public void onRewardedAdLoaded() {

            }

            @Override
            public void onRewardedAdFailedToLoad(int errorCode) {

            }
        };
        rewardedAd.loadAd(new AdRequest.Builder().build(), adLoadCallback);

        viewFlipper = root.findViewById(R.id.viewFlipper_id);
        movieBt = root.findViewById(R.id.latestMovie_id);
        mTrailersBt = root.findViewById(R.id.movieTrailers_id);
        musicBt = root.findViewById(R.id.mp3Music_id);
        dramaBt = root.findViewById(R.id.latestDrama_id);

        newspaperBt = root.findViewById(R.id.allNewspaper_id);
        radioBt = root.findViewById(R.id.radio_id);
        tvBt = root.findViewById(R.id.tvChannel_id);
        worldTBt = root.findViewById(R.id.worldTechnology_id);
        sportsBt = root.findViewById(R.id.sportsUpdate_id);

        dramaAllView = root.findViewById(R.id.dramaAllView_id);
        mp3TV = root.findViewById(R.id.mp3AllView_id);
        trailersAllView = root.findViewById(R.id.trailersAllView_id);


        mp3_class =  new Mp3_class();
        mp3List = new ArrayList<>();
        mp3RecyclerView = root.findViewById(R.id.mainMP3RecyclerView_id);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        mp3RecyclerView.setLayoutManager(layoutManager);
        mp3RecyclerView.setHasFixedSize(true);

        trailersClass =  new TrailersClass();
        trailersList = new ArrayList<>();
        trailersRecyclerView = root.findViewById(R.id.trailersRecyclerView);
        LinearLayoutManager trailersLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        trailersRecyclerView.setLayoutManager(trailersLayoutManager);
        trailersRecyclerView.setHasFixedSize(true);

        dramaClass =  new DramaClass();
        dramaList = new ArrayList<>();
        dramaRecyclerView = root.findViewById(R.id.mainDramaRecyclerView_id);
        LinearLayoutManager dramaLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        dramaRecyclerView.setLayoutManager(dramaLayoutManager);
        dramaRecyclerView.setHasFixedSize(true);


        if (HaveNetwork()){

            ConnectivityManager connectivityManager = (ConnectivityManager) getContext().getSystemService(CONNECTIVITY_SERVICE);
            NetworkInfo network = connectivityManager.getActiveNetworkInfo();
            int netSubType = network.getSubtype();

            if (netSubType == TelephonyManager.NETWORK_TYPE_1xRTT ||
                    netSubType == TelephonyManager.NETWORK_TYPE_GPRS ||
                    netSubType == TelephonyManager.NETWORK_TYPE_EDGE){

                lowMobileDataAlert();

            }else {

                mCount = 1;
                mp3SongRetriveMethod();
                dramaRetriveMethod();
                trailersRetriveMethod();

            }



        }else {

            dataConnectionAlert();

        }




        mp3TV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rewardAdsShow("Newspaper");
            }
        });

     trailersAllView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rewardAdsShow2("trailers");
            }
        });


        dramaAllView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rewardAdsShow2("drama");
            }
        });






       movieBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rewardAdsShow2("movie");


            }
        });
        mTrailersBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rewardAdsShow2("trailers");

            }
        });
        dramaBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rewardAdsShow2("drama");

            }
        });
        musicBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rewardAdsShow2("mp3");
            }
        });



        tvBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rewardAdsShow("Tv_channel");
            }
        });

        newspaperBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rewardAdsShow("Newspaper");

            }
        });
        radioBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rewardAdsShow("Radio_station");

            }
        });
        sportsBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rewardAdsShow("Sports_update");

            }
        });
        worldTBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rewardAdsShow("World_technology");

            }
        });

        String images [] = new String[] {"https://image.prntscr.com/image/qHmkWY9lQFSMVT4ZxExUmg.gif",
                                          "https://image.prntscr.com/image/IHHbm6zgQ22QxONnFGxOAg.jpg",
                                         "https://image.prntscr.com/image/nN3za6ySSpupvGkvcvTnXA.gif"};

        for (String image : images){

            flipperImages(image);
        }


        viewFlipper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), WebViewActivity.class);
                intent.putExtra("link",url);
                startActivity(intent);
            }
        });


        return root;
    }


    private void rewardAdsShow(final String value){

        if (rewardedAd.isLoaded()) {

            RewardedAdCallback adCallback = new RewardedAdCallback() {
                @Override
                public void onRewardedAdOpened() {
                    // Ad opened.
                }

                @Override
                public void onRewardedAdClosed() {
                    categorySent(value);
                }

                @Override
                public void onUserEarnedReward(@NonNull RewardItem reward) {
                    // User earned reward.
                }

                @Override
                public void onRewardedAdFailedToShow(int errorCode) {
                    // Ad failed to display.
                }
            };
            rewardedAd.show(getActivity(), adCallback);
            }else {

            categorySent(value);

        }

    }

    private void rewardAdsShow2(final String value){

        if (rewardedAd.isLoaded()) {

            RewardedAdCallback adCallback = new RewardedAdCallback() {
                @Override
                public void onRewardedAdOpened() {
                    // Ad opened.
                }

                @Override
                public void onRewardedAdClosed() {
                    categorySent2(value);
                }

                @Override
                public void onUserEarnedReward(@NonNull RewardItem reward) {
                    // User earned reward.
                }

                @Override
                public void onRewardedAdFailedToShow(int errorCode) {
                    // Ad failed to display.
                }
            };
            rewardedAd.show(getActivity(), adCallback);
             }else {

            categorySent2(value);

        }





    }

    private void categorySent(String value){

        Intent intent = new Intent(getContext(), ShowActivity.class);
        intent.putExtra("category",value);
        startActivity(intent);


    }

    private void categorySent2(String value){

        Intent intent = new Intent(getContext(), CategoryActivity.class);
        intent.putExtra("category",value);
        startActivity(intent);


    }



    private void flipperImages(String image){

        ImageView imageView = new ImageView(getContext());
        Picasso.get().load(image).centerCrop().fit().into(imageView);
        viewFlipper.addView(imageView);
        viewFlipper.setFlipInterval(3000);
        viewFlipper.setAutoStart(true);
        viewFlipper.setInAnimation(getContext(),android.R.anim.slide_in_left);
        viewFlipper.setOutAnimation(getContext(),android.R.anim.slide_out_right);


    }

    private void mp3SongRetriveMethod(){

        String url = getString(R.string.main_host_read_links);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                JSONObject obj = null;
                try {
                    obj = new JSONObject(response);
                    JSONArray dataArray  = obj.getJSONArray("Success");

                    for (int i = 0; i < dataArray.length(); i++) {

                        mp3_class = new Mp3_class();
                        JSONObject dataobj = dataArray.getJSONObject(i);
                        mp3_class.setCategory(dataobj.getString("category"));
                        mp3_class.setImage_link(dataobj.getString("image_link"));
                        mp3_class.setVideo_title(dataobj.getString("title"));
                        mp3_class.setVideo_link(dataobj.getString("link"));
                        mp3_class.setLove_count(dataobj.getString("love_count"));
                        mp3_class.setView_count(dataobj.getString("view_count"));
                        mp3_class.setDate_time(dataobj.getString("date_time"));

                        mp3List.add(mp3_class);

                    }

                    mp3_adapter = new Mp3_Adapter(getContext(),mp3List);
                    mp3RecyclerView.setAdapter(mp3_adapter);
                    mp3_adapter.notifyDataSetChanged();


                } catch (JSONException e) {
                    e.printStackTrace();

                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                if (mCount >=1){

                    mobileDataAlert();

                }else {
                    dataConnectionAlert();
                }


            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> Params = new HashMap<>();
                Params.put("category", "Newspaper");

                return Params;
            }

        };

        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(stringRequest);


    }
   private void dramaRetriveMethod(){

        String url = getString(R.string.main_host_read_links);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                JSONObject obj = null;
                try {
                    obj = new JSONObject(response);
                    JSONArray dataArray  = obj.getJSONArray("Success");

                    for (int i = 0; i < dataArray.length(); i++) {

                        dramaClass = new DramaClass();
                        JSONObject dataobj = dataArray.getJSONObject(i);
                        dramaClass.setCategory(dataobj.getString("category"));
                        dramaClass.setImage_link(dataobj.getString("image_link"));
                        dramaClass.setVideo_title(dataobj.getString("title"));
                        dramaClass.setVideo_link(dataobj.getString("link"));

                        dramaClass.setLove_count(dataobj.getString("love_count"));
                        dramaClass.setView_count(dataobj.getString("view_count"));
                        dramaClass.setDate_time(dataobj.getString("date_time"));

                        dramaList.add(dramaClass);

                    }

                    drama_adapter = new DramaAdapter(getContext(),dramaList);
                    dramaRecyclerView.setAdapter(drama_adapter);
                    drama_adapter.notifyDataSetChanged();


                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                if (mCount >=1){

                    mobileDataAlert();

                }else {
                    dataConnectionAlert();
                }

            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> Params = new HashMap<>();
                Params.put("category", "BanglaDrama");

                return Params;
            }

        };

        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(stringRequest);


    }



  private void trailersRetriveMethod(){

        String url = getString(R.string.main_host_read_links);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                JSONObject obj = null;
                try {
                    obj = new JSONObject(response);
                    JSONArray dataArray  = obj.getJSONArray("Success");

                    for (int i = 0; i < dataArray.length(); i++) {

                        trailersClass = new TrailersClass();
                        JSONObject dataobj = dataArray.getJSONObject(i);
                        trailersClass.setCategory(dataobj.getString("category"));
                        trailersClass.setImage_link(dataobj.getString("image_link"));
                        trailersClass.setVideo_title(dataobj.getString("title"));
                        trailersClass.setVideo_link(dataobj.getString("link"));

                        trailersClass.setLove_count(dataobj.getString("love_count"));
                        trailersClass.setView_count(dataobj.getString("view_count"));
                        trailersClass.setDate_time(dataobj.getString("date_time"));

                        trailersList.add(trailersClass);

                    }

                    trailersAdapter = new TrailersAdapter(getContext(),trailersList);
                    trailersRecyclerView.setAdapter(trailersAdapter);
                    trailersAdapter.notifyDataSetChanged();


                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                if (mCount >=1){

                    mobileDataAlert();

                }else {
                    dataConnectionAlert();
                }
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> Params = new HashMap<>();
                Params.put("category", "HindiMovieTrailers");

                return Params;
            }

        };

        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(stringRequest);


    }


    private void dataConnectionAlert(){

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setTitle("Data Connection Alert!")
                .setMessage("Please Connected your Internet first")
                .setPositiveButton("Then try again", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        startActivity(new Intent(getContext(), MainActivity.class));

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


    private void lowMobileDataAlert(){

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setTitle("Low data Alert!")
                .setMessage("You data connection is slow")
                .setPositiveButton("Try again", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                getActivity().finishAffinity();

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();


    }

    private void mobileDataAlert(){

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setTitle("Mobile data Alert!")
                .setMessage("Have you enough data?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        startActivity(new Intent(getContext(),MainActivity.class));

                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
              getActivity().finishAffinity();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();


    }


    private boolean HaveNetwork() {
        boolean have_WiFi = false;
        boolean have_Mobile = false;

        ConnectivityManager connectivityManager = (ConnectivityManager)getActivity().getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();

        for (NetworkInfo info : networkInfo){

            if (info.getTypeName().equalsIgnoreCase("WIFI"))
            {
                if (info.isConnected())
                {
                    have_WiFi = true;
                }
            }
            if (info.getTypeName().equalsIgnoreCase("MOBILE"))

            {
                if (info.isConnected())
                {
                    have_Mobile = true;
                }
            }

        }
        return have_WiFi || have_Mobile;

    }




}