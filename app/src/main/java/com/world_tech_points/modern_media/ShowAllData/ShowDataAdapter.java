package com.world_tech_points.modern_media.ShowAllData;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import com.world_tech_points.modern_media.R;
import com.world_tech_points.modern_media.VideoPlayerActivity;
import com.world_tech_points.modern_media.WebViewActivity;

import java.util.List;


public class ShowDataAdapter extends RecyclerView.Adapter<ShowDataAdapter.ViewHolder> {


    ShowDataClass dataClass;
    Context context;
    List<ShowDataClass> data_List;

    public ShowDataAdapter(Context context, List<ShowDataClass> data_List) {
        this.context = context;
        this.data_List = data_List;
    }

    @NonNull
    @Override
    public ShowDataAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.show_data_model_view,parent,false);

        return new ShowDataAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShowDataAdapter.ViewHolder holder, final int position) {

        dataClass = data_List.get(position);

        holder.title.setText(dataClass.getVideo_title());
        holder.loveCount.setText(dataClass.getLove_count());
        holder.viewCount.setText(dataClass.getView_count());
        Picasso.get().load(dataClass.getImage_link()).placeholder(R.drawable.movie_t).into(holder.imageView);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dataClass = data_List.get(position);
                String category = dataClass.getCategory();

                if (category.equals("Drama") || category.equals("Mp3_music") || category.equals("Latest_Movie") || category.equals("Movie_trailers")){

                    Intent intent = new Intent(context, VideoPlayerActivity.class);
                    intent.putExtra("id",dataClass.getVideo_link());
                    context.startActivity(intent);


                }else {

                    Intent intent = new Intent(context, WebViewActivity.class);
                    intent.putExtra("link",dataClass.getVideo_link());
                    context.startActivity(intent);

                }


            }
        });

    }

    @Override
    public int getItemCount() {
        return data_List.size();
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
