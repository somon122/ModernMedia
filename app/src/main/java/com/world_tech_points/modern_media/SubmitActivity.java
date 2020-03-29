package com.world_tech_points.modern_media;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.world_tech_points.modern_media.ShowAllData.ShowActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SubmitActivity extends AppCompatActivity {

    private Spinner mainSpinner, subSpinner;
    private Button submitButton,testLinkButton;
    private EditText nameET,linkET,imageLinkET;

    private String mainCategory;
    private String subCategory;
    private String date_time;

    private List<String>subCategoryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit);

        Toolbar toolbar = findViewById(R.id.submitToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mainSpinner = findViewById(R.id.mainCategorySpinner);
        subSpinner = findViewById(R.id.subCategorySpinner);

        submitButton = findViewById(R.id.submitButton_id);
        testLinkButton = findViewById(R.id.submitLinkTest_id);
        nameET = findViewById(R.id.submitTitle_id);
        linkET = findViewById(R.id.submitLink_id);
        imageLinkET = findViewById(R.id.submitImageLink_id);

        testLinkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(SubmitActivity.this,IdTestActivity.class));

            }
        });


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
        date_time = sdf.format(new Date());

        List<String>mainList = new ArrayList<>();
        mainList.add("WebViewBass");
        mainList.add("YoutubeBass");

        ArrayAdapter<String> mainDataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mainList);
        mainDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mainSpinner.setAdapter(mainDataAdapter);

        mainSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                mainCategory = mainSpinner.getSelectedItem().toString();
                mainMethod(mainCategory);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String title = nameET.getText().toString();
                String link = linkET.getText().toString();
                String imageLink = imageLinkET.getText().toString();


                if (title.isEmpty()){

                    nameET.setError("Enter name");

                }else if (link.isEmpty()){

                    linkET.setError("Enter link ");


                }else if (imageLink.isEmpty()){

                    imageLinkET.setError("Enter Image link ");


                }else {


                    dataSubmitMethod(subCategory,title,link,imageLink,"20","10",date_time);


                }

            }
        });


    }

    private void mainMethod(String mainCategory) {

        if (mainCategory.equals("WebViewBass")){

            subCategoryList = new ArrayList<>();
            subCategoryList.add("Tv_channel");
            subCategoryList.add("Sports_update");
            subCategoryList.add("Newspaper");
            subCategoryList.add("Radio_station");
            subCategoryList.add("World_technology");

        }else if (mainCategory.equals("YoutubeBass")){

            subCategoryList = new ArrayList<>();
            subCategoryList.add("Mp3_music");
            subCategoryList.add("Movie_trailers");
            subCategoryList.add("Drama");
            subCategoryList.add("Latest_movie");
        }


        ArrayAdapter<String> subDataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, subCategoryList);
        subDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        subSpinner.setAdapter(subDataAdapter);

        subSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                subCategory = subSpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private void dataSubmitMethod(final String category,final String title, final String mainLink, final String imageLink, final String viewCount, final String loveCount, final String date_time){

        String url = getString(R.string.main_host_write_links);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray jsonArray = new JSONArray(response);

                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    String success = jsonObject.getString("response");

                    if (success.equals("data_insert_success")){

                        Toast.makeText(SubmitActivity.this, "data_insert_success", Toast.LENGTH_SHORT).show();


                    }else if (success.equals("data_insert_field")){

                        Toast.makeText(SubmitActivity.this, "data_insert_field", Toast.LENGTH_SHORT).show();


                    }else if (success.equals("data_empty")){

                        Toast.makeText(SubmitActivity.this, "data_empty", Toast.LENGTH_SHORT).show();

                    }else if (success.equals("connection_error")){

                        Toast.makeText(SubmitActivity.this, "connection_error", Toast.LENGTH_SHORT).show();

                    }else {
                        Toast.makeText(SubmitActivity.this, "Something problem", Toast.LENGTH_SHORT).show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();

                   problemAlert();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

               dataConnectionAlert();

            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> Params = new HashMap<>();

                Params.put("category", category);
                Params.put("image_link", imageLink);
                Params.put("title", title);
                Params.put("link", mainLink);
                Params.put("love_count", loveCount);
                Params.put("view_count", viewCount);
                Params.put("date_time", date_time);

                return Params;
            }

        };

        RequestQueue queue = Volley.newRequestQueue(SubmitActivity.this);
        queue.add(stringRequest);


    }

    private void dataConnectionAlert(){

        AlertDialog.Builder builder = new AlertDialog.Builder(SubmitActivity.this);

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


  private void problemAlert(){

        AlertDialog.Builder builder = new AlertDialog.Builder(SubmitActivity.this);

        builder.setTitle("Alert!")
                .setMessage("Something Problem please try again")
                .setPositiveButton("try again", new DialogInterface.OnClickListener() {
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
