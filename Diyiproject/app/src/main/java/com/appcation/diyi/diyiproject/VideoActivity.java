package com.appcation.diyi.diyiproject;


import android.Manifest;
import android.arch.lifecycle.ViewModelStoreOwner;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

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
//            ,
//            "android.permission.ACCESS_FINE_LOCATION"
    };
    private  String testUrl="http://119.84.101.207/videos/v0/20180101/9c/20/33369eec370be393dd555a5a20234c02.mp4?key=0ebb94883d2df6eeb873b2dd48a35f687&dis_k=2d8cd483e5d3cf71159fcdfddad137350&dis_t=1514877572&dis_dz=CT-QIYI_SHMinRun&dis_st=44&src=iqiyi.com&uuid=a795aea-5a4b3284-bd&m=v&qd_ip=65e30cfd&qd_p=65e30cfd&qd_k=ab6b3e8679e84cccd49bfc91d5975606&qd_src=02028001010000000000&ssl=1&ip=101.227.12.253&qd_vip=0&dis_src=vrs&qd_uid=0&qdv=1&qd_tm=1514877572862";
    SimpleExoPlayer mPlayer;
    SimpleExoPlayerView playerView;
    String TAG="videotest";
    int vollume=0;
    Uri mp4Uri;
    ExtractorsFactory extractorsFactory;
    DefaultDataSourceFactory dataSourceFactory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestPermission();
        initExoPlayer();
    }

    private void initExoPlayer() {
        RenderersFactory renderersFactory=new DefaultRenderersFactory(this);
        DefaultTrackSelector trackSelector=new DefaultTrackSelector();
        LoadControl loadControl=new DefaultLoadControl();
        mPlayer= ExoPlayerFactory.newSimpleInstance(renderersFactory, trackSelector,loadControl);
        Log.i(TAG, "initExoPlayer: ");
        playerView=new SimpleExoPlayerView(this);
        playerView.setPlayer(mPlayer);
//        playerView.setControllerAutoShow(false);

        playerView.setUseController(false);

        setContentView(playerView);
//        test.mp4
        testUrl="file:///sdcard/zdiyi/testt.mp4";
        testUrl="file:///sdcard/zdiyi/39W888piC3ty.mp4";
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
//        Log.i(TAG, "onTouch: you  touch the playView");
//        vollume++;
//        if (vollume%2!=0){
//            Log.i(TAG, "onTouch: ");
//            mPlayer.stop();
//
//            //Toast.makeText(this,"stop bocheng",Toast.LENGTH_LONG).show();
//        }else {
//            ExtractorsFactory extractorsFactory=new DefaultExtractorsFactory();
//            MediaSource mediaSource=new ExtractorMediaSource(
//                    mp4Uri,dataSourceFactory,extractorsFactory,null,null);
//            mPlayer.prepare(mediaSource);
//            mPlayer.setPlayWhenReady(true);
//            Log.i(TAG, "onTouch: true");
//        }
//        finish();
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
        return false;
    }
    public void requestPermission(){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this,
                    PERMISSIONS_STORAGE,10);
    }

//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        return super.dispatchTouchEvent(ev);
//    }
}

