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
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.world_tech_points.modern_media.MainActivity;
import com.world_tech_points.modern_media.R;
import com.world_tech_points.modern_media.ShowAllData.ShowDataAdapter;
import com.world_tech_points.modern_media.ShowAllData.ShowDataClass;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.CONNECTIVITY_SERVICE;


public class MoviesFragment extends Fragment {


    private RecyclerView recyclerView;
    private ShowDataAdapter dataAdapter;
    private ShowDataClass dataClass;
    private TextView dataAlertTV;
    private int mCount;
    private List<ShowDataClass>data_list;

    private AdView adView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_movies, container, false);


        dataClass = new ShowDataClass();
        data_list = new ArrayList<>();
        recyclerView = root.findViewById(R.id.movieRecyclerView_id);
        dataAlertTV = root.findViewById(R.id.movieDataAlert_id);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),3));
        recyclerView.setHasFixedSize(true);


        adView = new AdView(getContext(), getString(R.string.facebookBannerAd), AdSize.BANNER_HEIGHT_50);
        LinearLayout adContainer = (LinearLayout) root.findViewById(R.id.movie_banner_container_id);
        adContainer.addView(adView);
        adView.loadAd();



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
                movieRetriveMethod();

            }

        }else {

            dataConnectionAlert();

        }

        return root;
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

    private void movieRetriveMethod(){

        String url = getString(R.string.main_host_read_links);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                JSONObject obj = null;
                try {
                    obj = new JSONObject(response);
                    JSONArray dataArray  = obj.getJSONArray("Success");
                    dataAlertTV.setVisibility(View.GONE);

                    for (int i = 0; i < dataArray.length(); i++) {

                        dataClass = new ShowDataClass();
                        JSONObject dataobj = dataArray.getJSONObject(i);
                        dataClass.setCategory(dataobj.getString("category"));
                        dataClass.setImage_link(dataobj.getString("image_link"));
                        dataClass.setVideo_title(dataobj.getString("title"));
                        dataClass.setVideo_link(dataobj.getString("link"));
                        dataClass.setLove_count(dataobj.getString("love_count"));
                        dataClass.setView_count(dataobj.getString("view_count"));
                        dataClass.setDate_time(dataobj.getString("date_time"));
                        data_list.add(dataClass);

                    }

                    dataAdapter = new ShowDataAdapter(getContext(),data_list);
                    recyclerView.setAdapter(dataAdapter);
                    dataAdapter.notifyDataSetChanged();


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
                Params.put("category", "HindiMovie");

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

                getActivity().finish();

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