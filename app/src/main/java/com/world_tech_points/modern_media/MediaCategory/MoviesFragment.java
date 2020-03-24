package com.world_tech_points.modern_media.MediaCategory;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
import com.squareup.picasso.Picasso;
import com.world_tech_points.modern_media.R;
import com.world_tech_points.modern_media.ShowAllData.ShowActivity;
import com.world_tech_points.modern_media.ShowAllData.ShowDataAdapter;
import com.world_tech_points.modern_media.ShowAllData.ShowDataClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MoviesFragment extends Fragment {


    private RecyclerView recyclerView;
    private ShowDataAdapter dataAdapter;
    private ShowDataClass dataClass;

    private List<ShowDataClass>data_list;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_movies, container, false);


        dataClass = new ShowDataClass();
        data_list = new ArrayList<>();
        recyclerView = root.findViewById(R.id.movieRecyclerView_id);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),3));
        recyclerView.setHasFixedSize(true);

        movieRetriveMethod();




        return root;
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

                    Toast.makeText(getContext(), "Error"+e, Toast.LENGTH_SHORT).show();
                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getContext(), ""+error, Toast.LENGTH_SHORT).show();

            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> Params = new HashMap<>();
                //Params.put("category", "Latest_movie");
                Params.put("category", "Latest_movie");

                return Params;
            }

        };

        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(stringRequest);


    }



}