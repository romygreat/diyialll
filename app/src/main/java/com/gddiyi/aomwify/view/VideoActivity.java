package com.gddiyi.aomwify.view;


import android.app.Activity;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;

import com.gddiyi.aomwify.constant.VSConstances;
import com.gddiyi.aomwify.presenter.VideoPresenter;
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
    private String testUrl = null;
    SimpleExoPlayer mPlayer;
    SimpleExoPlayerView playerView;
    String TAG = "videoActivity";
    Uri mp4Uri;
    ExtractorsFactory extractorsFactory;
    DefaultDataSourceFactory dataSourceFactory;
    VideoPresenter mVideoPresenter;
    int currentplay = 17;
    int videoPlayCount;
    org.json.JSONArray localPathArray = null;
    String playAllVideoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mVideoPresenter = new VideoPresenter();

        Log.i(TAG, "onCreate:videoCount "+videoPlayCount);
        playAllVideoPath= mVideoPresenter.readJsonFile();
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

        playerView.setPlayer(mPlayer);
        playerView.setUseController(false);
        setContentView(playerView);
        testUrl = "file:///sdcard/ad/xxx.mp4";
        testUrl = getPlayVideoUrl(currentplay);
        playVideostart(testUrl);
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
                Log.i(TAG, "onPlayerStateChanged: playWhenReady" + playWhenReady + "==playbackState==" + playbackState);

                if (playbackState == VSConstances.PLAYVIDEOFINISH) {
//                    if (currentplay == videoPlayCount) {
//                        currentplay = 0;
//                        playVideostart(getPlayVideoUrl(currentplay));
//                    }
                    currentplay++;
                    playVideostart(getPlayVideoUrl(currentplay));
                    Log.i(TAG, "onPlayerStateChanged:ok== " + getPlayVideoUrl(currentplay));
                    if(getPlayVideoUrl(currentplay)==null){
                        //此时建议应该从网络下载
                        currentplay=0;
                        playVideostart(getPlayVideoUrl(currentplay));
                    }

                    Log.i(TAG, "onPlayerStateChanged:currentplay "+currentplay);
                    Log.i(TAG, "onPlayerStateChanged:videoCount "+videoPlayCount);
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
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onDestroy() {
        mPlayer.release();
        super.onDestroy();
    }



    public String getPlayVideoUrl(int index) {
        Log.i(TAG, "getPlayVideoUrl: index" + index);

        String url = null;
        try {
            JSONObject jsonObject = new JSONObject(playAllVideoPath);
            localPathArray = (org.json.JSONArray) jsonObject.get(VSConstances.LOCALPATH);

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
        if(ev.getAction()==MotionEvent.ACTION_DOWN)
        { Log.i(TAG, "dispatchTouchEvent: ");
            finish();
        }
        return true;
    }
}

