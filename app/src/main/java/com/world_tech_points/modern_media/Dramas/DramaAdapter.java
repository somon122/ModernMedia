package com.world_tech_points.modern_media.Dramas;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.ads.AbstractAdListener;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.InterstitialAd;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.squareup.picasso.Picasso;
import com.world_tech_points.modern_media.R;
import com.world_tech_points.modern_media.VideoPlayerActivity;

import java.util.List;

public class DramaAdapter  extends RecyclerView.Adapter<DramaAdapter.ViewHolder>{

    private DramaClass dramaClass;
    private Context context;
    private List<DramaClass> drama_List;

    public DramaAdapter(Context context, List<DramaClass> drama_List) {
        this.context = context;
        this.drama_List = drama_List;
    }

    @NonNull
    @Override
    public DramaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.mp3_model_view,parent,false);


        return new DramaAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DramaAdapter.ViewHolder holder, final int position) {

        dramaClass = drama_List.get(position);

        holder.title.setText(dramaClass.getVideo_title());
        holder.loveCount.setText(dramaClass.getLove_count());
        holder.viewCount.setText(dramaClass.getView_count());
        Picasso.get().load(dramaClass.getImage_link()).placeholder(R.drawable.movie_t).into(holder.imageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    dramaClass = drama_List.get(position);
                    Intent intent = new Intent(context, VideoPlayerActivity.class);
                    intent.putExtra("id",dramaClass.getVideo_link());
                    context.startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return drama_List.size();
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
