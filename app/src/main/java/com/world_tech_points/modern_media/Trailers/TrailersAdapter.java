package com.world_tech_points.modern_media.Trailers;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.squareup.picasso.Picasso;
import com.world_tech_points.modern_media.R;
import com.world_tech_points.modern_media.VideoPlayerActivity;

import java.util.List;


public class TrailersAdapter extends RecyclerView.Adapter<TrailersAdapter.ViewHolder> {

    private Context context;
    private TrailersClass trailersClass;
    private List<TrailersClass>trailersList;

    public TrailersAdapter(Context context, List<TrailersClass> trailersList) {
        this.context = context;
        this.trailersList = trailersList;
    }

    @NonNull
    @Override
    public TrailersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.mp3_model_view,parent,false);

        return new TrailersAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailersAdapter.ViewHolder holder,final int position) {

        trailersClass = trailersList.get(position);

        holder.title.setText(trailersClass.getVideo_title());
        holder.loveCount.setText(trailersClass.getLove_count());
        holder.viewCount.setText(trailersClass.getView_count());
        Picasso.get().load(trailersClass.getImage_link()).placeholder(R.drawable.movie_t).into(holder.imageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                trailersClass = trailersList.get(position);
                Intent intent = new Intent(context, VideoPlayerActivity.class);
                intent.putExtra("id",trailersClass.getVideo_link());
                context.startActivity(intent);


            }
        });

    }

    @Override
    public int getItemCount() {
        return trailersList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView title, viewCount,loveCount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.showImage_id);
            title = itemView.findViewById(R.id.title_id);
            viewCount = itemView.findViewById(R.id.viewCount_id);
            loveCount = itemView.findViewById(R.id.loveCount_id);

        }
    }
}
