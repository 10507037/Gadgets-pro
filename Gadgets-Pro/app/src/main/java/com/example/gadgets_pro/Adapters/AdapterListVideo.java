package com.myapp.ytvideos.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;


import com.myapp.ytvideos.Models.Link;
import com.myapp.ytvideos.Models.VideoModel;
import com.myapp.ytvideos.R;
import com.myapp.ytvideos.VideoPlayerActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterListVideo extends RecyclerView.Adapter<AdapterListVideo.MyViewHolder> {



    private Context context;
    private ArrayList<Link> contactList;

    public boolean isYoutubeAdapter;

    public AdapterListVideo(Context context, ArrayList<Link> contactList) {
        this.context = context;
        this.contactList = contactList;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View MyItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video, parent, false);
        return new MyViewHolder(MyItemView);

    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Link video = contactList.get(position);
        holder.title.setText(video.getTitle());
        Picasso.get().load(video.getThumbnail()).into(holder.cover);

    }

    public boolean getAdapterType()
    {
        return isYoutubeAdapter;
    }

    public void setAdapterType(boolean isYoutubeAdapter)
    {
        this.isYoutubeAdapter = isYoutubeAdapter;

    }




    @Override
    public int getItemCount() {
        return contactList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView (R.id.tv_item_title) TextView title;
        @BindView(R.id.iv_item_cover) ImageView cover;


        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v)
                {
                    Link myvideo = contactList.get(getAdapterPosition());

                    if(myvideo.getVideoId()!=null) {
                        Intent intent = new Intent(context, VideoPlayerActivity.class);
                        intent.putExtra("videoId", myvideo.getVideoId());
                        intent.putExtra("videoTitle", myvideo.getTitle());

                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        context.startActivity(intent);
                    }

                }
            });


        }
    }



}
