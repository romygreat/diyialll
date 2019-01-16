package com.gddiyi.aom.view;


import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.JsonReader;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.gddiyi.aom.constant.VSConstances;
import com.gddiyi.aom.presenter.RetrofitPresenter;
import com.gddiyi.aom.presenter.VideoPresenter;
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

import org.json.JSONException;
//需要导入的包不能搞错
import org.json.JSONObject;

public class VideoActivity extends Activity  {
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE"
    };
    private String testUrl = null;
    SimpleExoPlayer mPlayer;
    SimpleExoPlayerView playerView;
    String TAG = "videoActivity";
    Uri mp4Uri;
    ExtractorsFactory extractorsFactory;
    DefaultDataSourceFactory dataSourceFactory;
    VideoPresenter mVideoPresenter;
    int currentplay = 0;
    org.json.JSONArray localPathArray = null;
    static final String localPath = "localPath";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mVideoPresenter = new VideoPresenter(this);
        initExoPlayer();

//        mVideoPresenter.createFile("");
    }

    private void initExoPlayer() {
        RenderersFactory renderersFactory = new DefaultRenderersFactory(this);
        DefaultTrackSelector trackSelector = new DefaultTrackSelector();
        LoadControl loadControl = new DefaultLoadControl();
        mPlayer = ExoPlayerFactory.newSimpleInstance(renderersFactory, trackSelector, loadControl);
        Log.i(TAG, "initExoPlayer: ");
        playerView = new SimpleExoPlayerView(this);
        //playerView.setLayoutParams(new WindowManager.LayoutParams(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.FILL_PARENT));
        playerView.setPlayer(mPlayer);
        playerView.setUseController(false);
        setContentView(playerView);
        testUrl = "file:///sdcard/ad/9e810023b8e689be648c657cf50d5c43.mp4";
        testUrl = getPlayVideoUrl(currentplay);
        playVideostart(testUrl);
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
                Log.i(TAG, "onPlayerStateChanged: playWhenReady" + playWhenReady + "===playbackState" + playbackState);
                if (playbackState == 4) {
                    currentplay++;
                    playVideostart(getPlayVideoUrl(currentplay));
                    Log.i(TAG, "onPlayerStateChanged:ok== " + getPlayVideoUrl(currentplay));
                    if (currentplay == 50) {
                        currentplay = 0;
                    }

                }
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

    private void playVideostart(String testUrl) {
        Log.i(TAG, "initExoPlayer: " + testUrl);
        String videourl = VSConstances.SDdir + testUrl;
        mp4Uri = Uri.parse(videourl);
        dataSourceFactory = new DefaultDataSourceFactory(
                this, Util.getUserAgent(this, "exoPlayerTest"));
        extractorsFactory = new DefaultExtractorsFactory();
        MediaSource mediaSource = new ExtractorMediaSource(
                mp4Uri, dataSourceFactory, extractorsFactory, null, null);
        mPlayer.prepare(mediaSource);
        playerView.setBackgroundColor(Color.BLACK);
        mPlayer.setPlayWhenReady(true);
//        playerView.setOnTouchListener(this);
    }

    @Override
    protected void onDestroy() {
        mPlayer.release();
        super.onDestroy();
    }

//    @Override
//    public boolean onTouch(View v, MotionEvent event) {
//        finish();
//        return false;
//    }

    public void requestPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    PERMISSIONS_STORAGE, 10);
        }
    }

    public String getPlayVideoUrl(int index) {
        Log.i(TAG, "getPlayVideoUrl: index" + index);
        String playAllVideoPath = mVideoPresenter.readJsonFile();
        String url = null;
        try {
            JSONObject jsonObject = new JSONObject(playAllVideoPath);
            localPathArray = (org.json.JSONArray) jsonObject.get(localPath);

//        org.json.JSONArray netPathArray=( org.json.JSONArray)jsonObject.get("netPath");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            url = (String) localPathArray.get(index);
            while (!url.contains("mp4")) {
                url = (String) localPathArray.get(index++);
                currentplay++;
                Log.i(TAG, "getPlayVideoUrl: is not exist");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return url;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        finish();
        Log.i(TAG, "dispatchTouchEvent: ok");
        return true;
    }
}

