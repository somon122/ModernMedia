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
import com.world_tech_points.modern_media.VideoPlayerActivity;


public class TrailersFragment extends Fragment {



    String id = "6HtPJigGV5k";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.trailers_share, container, false);


        Button button = root.findViewById(R.id.text_share);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), VideoPlayerActivity.class);
                intent.putExtra("id",id);
                startActivity(intent);
            }
        });



        return root;
    }
}