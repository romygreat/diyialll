package com.gddiyi.aom.view;


import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.RenderersFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

public class VideoActivity extends AppCompatActivity implements View.OnTouchListener {
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE"
    };
    private  String testUrl=null;
    SimpleExoPlayer mPlayer;
    SimpleExoPlayerView playerView;
    String TAG="videoActivity";
    int vollume=0;
    Uri mp4Uri;
    ExtractorsFactory extractorsFactory;
    DefaultDataSourceFactory dataSourceFactory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        initExoPlayer();
    }

    private void initExoPlayer() {
        RenderersFactory renderersFactory=new DefaultRenderersFactory(this);
        DefaultTrackSelector trackSelector=new DefaultTrackSelector();
        LoadControl loadControl=new DefaultLoadControl();
        mPlayer= ExoPlayerFactory.newSimpleInstance(renderersFactory, trackSelector,loadControl);
        Log.i(TAG, "initExoPlayer: ");
        playerView=new SimpleExoPlayerView(this);
//        playerView.setLayoutParams(new WindowManager.LayoutParams(WindowManager.LayoutParams.FILL_PARENT,WindowManager.LayoutParams.FILL_PARENT));
        playerView.setPlayer(mPlayer);

        playerView.setUseController(false);
        setContentView(playerView);
        testUrl="file:///sdcard/ad/9e810023b8e689be648c657cf50d5c43.mp4";
        mp4Uri=Uri.parse(testUrl);
        dataSourceFactory=new DefaultDataSourceFactory(
                this, Util.getUserAgent(this,"exoPlayerTest"));
        extractorsFactory=new DefaultExtractorsFactory();
        MediaSource mediaSource=new ExtractorMediaSource(
                mp4Uri,dataSourceFactory,extractorsFactory,null,null);
        mPlayer.prepare(mediaSource);
        Log.i(TAG, "initExoPlayer: play");
        mPlayer.setPlayWhenReady(true);
        playerView.setOnTouchListener(this);
        requestPermission();


        mPlayer.addListener(new Player.EventListener() {
            @Override
            public void onTimelineChanged(Timeline timeline, Object manifest) {

            }

            @Override
            public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

            }

            @Override
            public void onLoadingChanged(boolean isLoading) {

            }
            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                // playbackState 播放视频完成的状态
                Log.i(TAG, "onPlayerStateChanged: playWhenReady"+playWhenReady+"===playbackState"+playbackState);

            }

            @Override
            public void onRepeatModeChanged(int repeatMode) {

            }

            @Override
            public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

            }

            @Override
            public void onPlayerError(ExoPlaybackException error) {

            }

            @Override
            public void onPositionDiscontinuity(int reason) {

            }

            @Override
            public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

            }

            @Override
            public void onSeekProcessed() {

            }
        });



    }


    @Override
    protected void onDestroy() {
        mPlayer.release();
        super.onDestroy();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        finish();
        return false;
    }
    public void requestPermission(){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {  ActivityCompat.requestPermissions(this,
                    PERMISSIONS_STORAGE,10);}
    }

}

