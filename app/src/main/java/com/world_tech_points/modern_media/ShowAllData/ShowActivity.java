package com.world_tech_points.modern_media.ShowAllData;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.world_tech_points.modern_media.MainActivity;
import com.world_tech_points.modern_media.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShowActivity extends AppCompatActivity {


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home){

            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private RecyclerView recyclerView;
    private List<ShowDataClass> data_list;
    private ShowDataClass dataClass;
    private ShowDataAdapter dataAdapter;
    private TextView dataAlertTV;

    private String category;

    int mCount;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        Toolbar toolbar = findViewById(R.id.showToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.showRecyclerView_id);
        dataAlertTV = findViewById(R.id.showDataAlert_id);
        dataClass = new ShowDataClass();
        data_list = new ArrayList<>();
        recyclerView.setLayoutManager(new GridLayoutManager(this,3));
        recyclerView.setHasFixedSize(true);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            category = bundle.getString("category");
            setTitle(category);
           connectionTest(category);

        }else {

            dataMissingAlert();
        }

    }


    private void connectionTest(String category){

        ConnectivityManager manager = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);

        boolean is3g = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
                .isConnectedOrConnecting();

        boolean isWifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                .isConnectedOrConnecting();


        if (is3g) {
            ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
            NetworkInfo network = connectivityManager.getActiveNetworkInfo();
            int netSubType = network.getSubtype();
            if(netSubType == TelephonyManager.NETWORK_TYPE_HSPAP ||
                    netSubType == TelephonyManager.NETWORK_TYPE_HSDPA ||
                    netSubType == TelephonyManager.NETWORK_TYPE_HSPA) {

                mCount = 1;
                allDataRetriveMethod(category);


            }
            else if (netSubType == TelephonyManager.NETWORK_TYPE_1xRTT ||
                    netSubType == TelephonyManager.NETWORK_TYPE_GPRS ||
                    netSubType == TelephonyManager.NETWORK_TYPE_EDGE){

                lowMobileDataAlert();

            }

        }else if (isWifi){

            allDataRetriveMethod(category);


        }
        else
        {

            dataConnectionAlert();


        }



    }


    private void dataMissingAlert(){

        AlertDialog.Builder builder = new AlertDialog.Builder(ShowActivity.this);

        builder.setTitle("Alert!")
                .setMessage("Some data is missing. Please try again !")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                       finish();

                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();


    }

    private void allDataRetriveMethod(final String category){

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

                    updateListUsers(data_list);


                } catch (JSONException e) {
                    e.printStackTrace();

                    dataAlertTV.setText("Data empty !");

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
                Params.put("category", category);

                return Params;
            }

        };

        RequestQueue queue = Volley.newRequestQueue(ShowActivity.this);
        queue.add(stringRequest);


    }


    private void lowMobileDataAlert(){

        AlertDialog.Builder builder = new AlertDialog.Builder(ShowActivity.this);

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

                finishAffinity();

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();


    }

    private void mobileDataAlert(){

        AlertDialog.Builder builder = new AlertDialog.Builder(ShowActivity.this);

        builder.setTitle("Mobile data Alert!")
                .setMessage("Have you enough data?")
                .setPositiveButton("Try again", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        startActivity(new Intent(ShowActivity.this, MainActivity.class));

                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finishAffinity();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();


    }


    private void dataConnectionAlert(){

        AlertDialog.Builder builder = new AlertDialog.Builder(ShowActivity.this);

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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.data_search, menu);

        MenuItem searchItem = menu.findItem(R.id.searchBar_id);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                searchQuestion(query);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                searchQuestion(newText);

                return false;
            }
        });


        return true;
    }

    private void searchQuestion(String recherche) {
        if (recherche.length() > 0)
            recherche = recherche.substring(0, 1).toUpperCase() + recherche.substring(1).toLowerCase();

        List<ShowDataClass> results = new ArrayList<>();
        for(ShowDataClass grammarItemClass : data_list){
            if(grammarItemClass.getVideo_title() != null && grammarItemClass.getVideo_title().contains(recherche)){
                results.add(grammarItemClass);
            }
        }
        updateListUsers(results);
    }

    private void updateListUsers(List<ShowDataClass> listQuestion) {

        Collections.sort(listQuestion, new Comparator<ShowDataClass>() {
            @Override
            public int compare(ShowDataClass o1, ShowDataClass o2) {
                int res = 1;
                if (o1.getVideo_title() == (o2.getVideo_title())) {
                    res = -1;
                }
                return res;
            }
        });

        dataAdapter = new ShowDataAdapter(ShowActivity.this,listQuestion);
        recyclerView.setAdapter(dataAdapter);
        dataAdapter.notifyDataSetChanged();

    }


}
