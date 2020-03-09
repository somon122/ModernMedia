package com.world_tech_points.modern_media.MediaCategory;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.world_tech_points.modern_media.R;
import com.world_tech_points.modern_media.WebViewActivity;


public class HomeFragment extends Fragment {


    String link = "http://bdiptv.net";
    String link2 = "http://www.jagobd.com/btvworld";
    String link3 = "http://172.50.50.8";
    String link4 = "http://172.16.50.4";


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        Button button = root.findViewById(R.id.testButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), WebViewActivity.class);
                intent.putExtra("link",link);
                startActivity(intent);
            }
        });



        return root;
    }
}