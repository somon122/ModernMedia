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


public class ShowDataAdapter extends RecyclerView.Adapter<ShowDataAdapter.ViewHolder> {


    private ShowDataClass dataClass;
    private Context context;
    private List<ShowDataClass> data_List;

    private int  mPosition;
    private InterstitialAd mInterstitialAd;

    public ShowDataAdapter(Context context, List<ShowDataClass> data_List) {
        this.context = context;
        this.data_List = data_List;
    }

    @NonNull
    @Override
    public ShowDataAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.show_data_model_view,parent,false);

        AudienceNetworkAds.initialize(context);
        mInterstitialAd = new InterstitialAd(context, context.getString(R.string.facebookInterstitialAd));


        mInterstitialAd.setAdListener(new AbstractAdListener() {
            @Override
            public void onError(Ad ad, AdError error) {
                super.onError(ad, error);
            }

            @Override
            public void onAdLoaded(Ad ad) {
                super.onAdLoaded(ad);
            }

            @Override
            public void onAdClicked(Ad ad) {
                super.onAdClicked(ad);
            }

            @Override
            public void onInterstitialDisplayed(Ad ad) {
                super.onInterstitialDisplayed(ad);
            }

            @Override
            public void onInterstitialDismissed(Ad ad) {
                super.onInterstitialDismissed(ad);

                    dataClass = data_List.get(mPosition);
                    Intent intent = new Intent(context, WebViewActivity.class);
                    intent.putExtra("link",dataClass.getVideo_link());
                    context.startActivity(intent);


            }

            @Override
            public void onLoggingImpression(Ad ad) {
                super.onLoggingImpression(ad);
            }
        });
        mInterstitialAd.loadAd();



        return new ShowDataAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShowDataAdapter.ViewHolder holder, final int position) {

        dataClass = data_List.get(position);

        holder.title.setText(dataClass.getVideo_title());
        holder.loveCount.setText(dataClass.getLove_count());
        holder.viewCount.setText(dataClass.getView_count());
        Picasso.get().load(dataClass.getImage_link()).fit().placeholder(R.drawable.movie_t).into(holder.imageView);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                    dataClass = data_List.get(position);
                    String category = dataClass.getCategory();

                    if (category.equals("Newspaper") || category.equals("Radio_station") || category.equals("World_technology")|| category.equals("Sports_update") || category.equals("Tv_channel")){


                        if (mInterstitialAd.isAdLoaded()){

                            mPosition = position;
                            mInterstitialAd.show();


                        }else {

                            Intent intent = new Intent(context, WebViewActivity.class);
                            intent.putExtra("link",dataClass.getVideo_link());
                            context.startActivity(intent);

                        }

                    }else {

                        Intent intent = new Intent(context, VideoPlayerActivity.class);
                        intent.putExtra("id",dataClass.getVideo_link());
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
