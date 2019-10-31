package com.myapp.ytvideos.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.myapp.ytvideos.Models.Link;
import com.myapp.ytvideos.R;
import com.myapp.ytvideos.SearchModel.Item;
import com.myapp.ytvideos.SearchModel.SearchModel;
import com.myapp.ytvideos.SearchModel.Snippet;
import com.myapp.ytvideos.VideoPlayerActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterSearchVideo extends RecyclerView.Adapter<AdapterSearchVideo.MyViewHolder>
{
    private Context context;
    private ArrayList<Item> videosList;


    public AdapterSearchVideo(Context context, ArrayList<Item> contactList) {
        this.context = context;
        this.videosList = contactList;


    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View MyItemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_search, viewGroup, false);
        return new AdapterSearchVideo.MyViewHolder(MyItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Item item = videosList.get(position);

        holder.title.setText(item.getSnippet().getTitle());
        Picasso.get().load(item.getSnippet().getThumbnails().getHigh().getUrl()).into(holder.cover);

    }

    @Override
    public int getItemCount() {
        return videosList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_item_title)
        TextView title;
        @BindView(R.id.iv_item_cover)
        ImageView cover;


        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Item item = videosList.get(getAdapterPosition());

                    Intent intent  = new Intent(context, VideoPlayerActivity.class);
                   intent.putExtra("videoId",item.getId().getVideoId());
                    intent.putExtra("videoTitle",item.getSnippet().getTitle());
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(intent);
                }
            });



        }
    }


}
