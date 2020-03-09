package com.world_tech_points.modern_media.MediaCategory;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.world_tech_points.modern_media.R;


public class MoviesFragment extends Fragment {



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_movies, container, false);
        final TextView textView = root.findViewById(R.id.text_send);
        textView.setText("MoviesFragment");
        return root;
    }
}