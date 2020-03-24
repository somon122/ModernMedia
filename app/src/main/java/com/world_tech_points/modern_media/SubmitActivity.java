package com.world_tech_points.modern_media;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SubmitActivity extends AppCompatActivity {

    private Spinner mainSpinner, subSpinner;
    private Button submitButton;
    private EditText nameET,linkET;

    private String mainCategory;
    private String subCategory;

    List<String>subCategoryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit);

        Toolbar toolbar = findViewById(R.id.submitToolbar);
        setSupportActionBar(toolbar);

        mainSpinner = findViewById(R.id.mainCategorySpinner);
        subSpinner = findViewById(R.id.subCategorySpinner);

        submitButton = findViewById(R.id.submitButton_id);
        nameET = findViewById(R.id.submitName_id);
        linkET = findViewById(R.id.submitLink_id);


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

                String name = nameET.getText().toString();
                String link = linkET.getText().toString();


                if (name.isEmpty()){

                    nameET.setError("Enter name");

                }else if (link.isEmpty()){

                    linkET.setError("Enter link ");


                }else {

                    Toast.makeText(SubmitActivity.this, "right action", Toast.LENGTH_SHORT).show();


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
}
