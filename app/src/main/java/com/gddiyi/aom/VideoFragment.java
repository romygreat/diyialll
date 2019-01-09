package com.gddiyi.aom;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

public class VideoFragment extends Fragment {
    private  String testUrl="http://119.84.101.207/videos/v0/20180101/9c/20/33369eec370be393dd555a5a20234c02.mp4?key=0ebb94883d2df6eeb873b2dd48a35f687&dis_k=2d8cd483e5d3cf71159fcdfddad137350&dis_t=1514877572&dis_dz=CT-QIYI_SHMinRun&dis_st=44&src=iqiyi.com&uuid=a795aea-5a4b3284-bd&m=v&qd_ip=65e30cfd&qd_p=65e30cfd&qd_k=ab6b3e8679e84cccd49bfc91d5975606&qd_src=02028001010000000000&ssl=1&ip=101.227.12.253&qd_vip=0&dis_src=vrs&qd_uid=0&qdv=1&qd_tm=1514877572862";
    SimpleExoPlayer mPlayer;
    SimpleExoPlayerView playerView;
    String TAG="videotest";
    int vollume=0;
    Uri mp4Uri;
    ExtractorsFactory extractorsFactory;
    DefaultDataSourceFactory dataSourceFactory;
    Context mContext;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext=context;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         super.onCreateView(inflater, container, savedInstanceState);
        playerView=new SimpleExoPlayerView(mContext);
        initExoPlayer();
        return playerView;
    }

    void initExoPlayer() {
        RenderersFactory renderersFactory=new DefaultRenderersFactory(mContext);
        DefaultTrackSelector trackSelector=new DefaultTrackSelector();
        LoadControl loadControl=new DefaultLoadControl();
        mPlayer= ExoPlayerFactory.newSimpleInstance(renderersFactory, trackSelector,loadControl);
        Log.i(TAG, "initExoPlayer: ");
        playerView=new SimpleExoPlayerView(mContext);
        playerView.setPlayer(mPlayer);
//        playerView.setControllerAutoShow(false);

        playerView.setUseController(false);

      //  setContentView(playerView);
        testUrl="file:///sdcard/zdiyi/test.mp4";
        mp4Uri=Uri.parse(testUrl);

        dataSourceFactory=new DefaultDataSourceFactory(
                mContext, Util.getUserAgent(mContext,"exoPlayerTest"));
        extractorsFactory=new DefaultExtractorsFactory();
        MediaSource mediaSource=new ExtractorMediaSource(
                mp4Uri,dataSourceFactory,extractorsFactory,null,null);
        mPlayer.prepare(mediaSource);
        Log.i(TAG, "initExoPlayer: play");
        mPlayer.setPlayWhenReady(true);
        playerView.setOnTouchListener((View.OnTouchListener) mContext);//可能需要修改
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
}
