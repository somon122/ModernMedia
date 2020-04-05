package com.world_tech_points.modern_media.MP3_Songs;

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
import com.squareup.picasso.Picasso;
import com.world_tech_points.modern_media.R;
import com.world_tech_points.modern_media.VideoPlayerActivity;
import com.world_tech_points.modern_media.WebViewActivity;

import java.util.List;


public class Mp3_Adapter extends RecyclerView.Adapter<Mp3_Adapter.ViewHolder> {

    Mp3_class mp3_class;
    Context context;
    List<Mp3_class>mp3_List;

    public Mp3_Adapter(Context context, List<Mp3_class> mp3_List) {
        this.context = context;
        this.mp3_List = mp3_List;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.mp3_model_view,parent,false);


        return new Mp3_Adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        mp3_class = mp3_List.get(position);

        holder.title.setText(mp3_class.getVideo_title());
        holder.loveCount.setText(mp3_class.getLove_count());
        holder.viewCount.setText(mp3_class.getView_count());
        Picasso.get().load(mp3_class.getImage_link()).placeholder(R.drawable.movie_t).into(holder.imageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                    mp3_class = mp3_List.get(position);
                    Intent intent = new Intent(context, WebViewActivity.class);
                    intent.putExtra("link",mp3_class.getVideo_link());
                    context.startActivity(intent);

                }
        });

    }

    @Override
    public int getItemCount() {
        return mp3_List.size();
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
