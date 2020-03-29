package com.world_tech_points.modern_media;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class IdTestActivity extends AppCompatActivity {

    private Button idSentButton;
    private EditText idET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_id_test);

        idET = findViewById(R.id.idTestET_id);
        idSentButton = findViewById(R.id.idTestButton);



        idSentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String id = idET.getText().toString().trim();

                if (id.isEmpty()){

                    idET.setError("Enter id");

                }else {

                    Intent intent = new Intent(IdTestActivity.this, VideoPlayerActivity.class);
                    intent.putExtra("id",id);
                    startActivity(intent);

                }

            }
        });

    }
}
