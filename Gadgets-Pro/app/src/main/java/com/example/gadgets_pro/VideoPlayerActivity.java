package com.myapp.ytvideos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeApiServiceUtil;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.myapp.ytvideos.Fragments.FragmentVideoPlayer;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoPlayerActivity extends AppCompatActivity {

    private Toolbar toolbar;
    String videoId,videoTitle;
    @BindView(R.id.tv_detail_desc) TextView tvDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);
        ButterKnife.bind(this);

        Intent intent = getIntent();

        videoId=  intent.getStringExtra("videoId");
        videoTitle = intent.getStringExtra("videoTitle");
        tvDetail.setText(videoTitle);
        setupToolbar(R.id.toolbar);
        ((View) findViewById(R.id.lyt_content)).requestFocus();
        prepareYoutube();



    }

    private void prepareYoutube() {
        final YouTubeInitializationResult result = YouTubeApiServiceUtil.isYouTubeApiServiceAvailable(this);

        if (result != YouTubeInitializationResult.SUCCESS) {
            result.getErrorDialog(this, 0).show();
            return;
        }

        final FragmentVideoPlayer fragment = (FragmentVideoPlayer) getSupportFragmentManager().findFragmentById(R.id.fragment_youtube);
        if(videoId!=null && !videoId.equals(""))
        {
            fragment.setVideoId(videoId);
        }

    }


    public void setupToolbar(int toolbarId) {
        toolbar = (Toolbar) findViewById(toolbarId);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null){
            getSupportActionBar().setTitle("Video Player");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }



}
