package com.vijayjaidewan01vivekrai.androidmasterclass;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

public class VideoExoActivity extends AppCompatActivity {
    private PlayerView mPlayerView;
    private SimpleExoPlayer mPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_exo);
        mPlayerView = findViewById(R.id.player_view);




    }

    @Override
    protected void onStart() {
        super.onStart();
        Bundle bundle = getIntent().getExtras();
        mPlayer = ExoPlayerFactory.newSimpleInstance(this, new DefaultTrackSelector());
        mPlayerView.setPlayer(mPlayer);
        DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(this, Util.getUserAgent(this, "exo-demo"));
        ExtractorMediaSource mediaSource = new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(bundle.getString("videoId")));
        mPlayer.prepare(mediaSource);
        mPlayer.setPlayWhenReady(true);

    }

    @Override
    protected void onStop() {
        super.onStop();
        mPlayerView.setPlayer(null);
        mPlayer.release();
        mPlayer = null;

    }
}
