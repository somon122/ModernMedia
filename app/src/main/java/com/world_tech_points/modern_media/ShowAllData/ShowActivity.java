package com.world_tech_points.modern_media.ShowAllData;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.world_tech_points.modern_media.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShowActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    private List<ShowDataClass> data_list;
    private ShowDataClass dataClass;
    private ShowDataAdapter dataAdapter;

    String category;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        Toolbar toolbar = findViewById(R.id.showToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.showRecyclerView_id);
        dataClass = new ShowDataClass();
        data_list = new ArrayList<>();
        recyclerView.setLayoutManager(new GridLayoutManager(this,3));
        recyclerView.setHasFixedSize(true);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            category = bundle.getString("category");
            setTitle(category);
            mp3SongRetriveMethod(category);

        }else {

            dataMissingAlert();
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

    private void mp3SongRetriveMethod(final String category){

        String url = getString(R.string.main_host_read_links);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                JSONObject obj = null;
                try {
                    obj = new JSONObject(response);
                    JSONArray dataArray  = obj.getJSONArray("Success");

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

                    dataAdapter = new ShowDataAdapter(ShowActivity.this,data_list);
                    recyclerView.setAdapter(dataAdapter);
                    dataAdapter.notifyDataSetChanged();


                } catch (JSONException e) {
                    e.printStackTrace();

                    Toast.makeText(ShowActivity.this, "Error"+e, Toast.LENGTH_SHORT).show();
                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(ShowActivity.this, ""+error, Toast.LENGTH_SHORT).show();

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

}
