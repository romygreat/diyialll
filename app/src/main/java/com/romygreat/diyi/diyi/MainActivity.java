package com.romygreat.diyi.diyi;

import android.arch.lifecycle.ViewModelStoreOwner;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.RenderersFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {

    private  String testUrl="http://119.84.101.207/videos/v0/20180101/9c/20/33369eec370be393dd555a5a20234c02.mp4?key=0ebb94883d2df6eeb873b2dd48a35f687&dis_k=2d8cd483e5d3cf71159fcdfddad137350&dis_t=1514877572&dis_dz=CT-QIYI_SHMinRun&dis_st=44&src=iqiyi.com&uuid=a795aea-5a4b3284-bd&m=v&qd_ip=65e30cfd&qd_p=65e30cfd&qd_k=ab6b3e8679e84cccd49bfc91d5975606&qd_src=02028001010000000000&ssl=1&ip=101.227.12.253&qd_vip=0&dis_src=vrs&qd_uid=0&qdv=1&qd_tm=1514877572862";
    SimpleExoPlayer mPlayer;
    SimpleExoPlayerView playerView;
    String TAG="videotest";
    int vollume=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        testUrl="file:///sdcard/zdiyi/test.mp4";
        Uri mp4Uri=Uri.parse(testUrl);

        DefaultDataSourceFactory dataSourceFactory=new DefaultDataSourceFactory(
                this, Util.getUserAgent(this,"exoPlayerTest"));
        ExtractorsFactory extractorsFactory=new DefaultExtractorsFactory();
        MediaSource mediaSource=new ExtractorMediaSource(
                mp4Uri,dataSourceFactory,extractorsFactory,null,null);
        mPlayer.prepare(mediaSource);
        Log.i(TAG, "initExoPlayer: play");
        mPlayer.setPlayWhenReady(true);
        playerView.setOnTouchListener(this);


    }


    @Override
    protected void onDestroy() {
        mPlayer.release();
        super.onDestroy();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Log.i(TAG, "onTouch: you  touch the playView");
        vollume++;
//        if (vollume%2!=0){
//            mPlayer.stop();
//            Toast.makeText(this,"stop bocheng",Toast.LENGTH_LONG).show();
//        }else {
//            mPlayer.setPlayWhenReady(true);
//        }
        return false;
    }
}
