package com.mobiotics.deeptimay.deeptimaymobiotics.activityExoPlayer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.Util;
import com.mobiotics.deeptimay.deeptimaymobiotics.R;
import com.mobiotics.deeptimay.deeptimaymobiotics.adapters.RecordListRvSecond;
import com.mobiotics.deeptimay.deeptimaymobiotics.database.MobioticsDb;
import com.mobiotics.deeptimay.deeptimaymobiotics.model.Records;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ExoPlayerActivity extends AppCompatActivity {

    @BindView(R.id.player_view)
    PlayerView playerView;
    @BindView(R.id.rvRecords)
    RecyclerView rvRecords;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_description)
    TextView tvDescription;

    SimpleExoPlayer player;

    long playbackPosition = 0;
    int currentWindow = 0;
    boolean playWhenReady = false;
    Records record;
    RecordListRvSecond recordListRv;
    int position = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exoplayer_activity);
        ButterKnife.bind(this);

        try {
            Intent intent = this.getIntent();
            Bundle bundle = intent.getExtras();

            record = (Records) bundle.getSerializable("selectedData");
            tvTitle.setText(record.getTitle());
            tvDescription.setText(record.getDescription());
            Log.d("before Insert", record.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }

        initializePlayer();
        showRecords();
    }

    private void showRecords() {

        MobioticsDb mobioticsDb = new MobioticsDb(ExoPlayerActivity.this);
        if (mobioticsDb.getRecordCount() > 1) {
            ArrayList<Records> responseArrayList = mobioticsDb.getAllRecords();

            recordListRv = new RecordListRvSecond(responseArrayList, ExoPlayerActivity.this);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(ExoPlayerActivity.this);
            rvRecords.setLayoutManager(mLayoutManager);
            rvRecords.setAdapter(recordListRv);
            recordListRv.notifyDataSetChanged();
            rvRecords.getLayoutManager().scrollToPosition(position);

            recordListRv.setOnItemClickListener(new RecordListRvSecond.MyClickListener() {
                @Override
                public void onItemClick(Records selectedData, int pos) {
                    position = pos;
                    Intent intent = new Intent(ExoPlayerActivity.this, ExoPlayerActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("selectedData", selectedData);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            initializePlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        hideSystemUi();
        if ((Util.SDK_INT <= 23 || player == null)) {
            initializePlayer();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

    private void releasePlayer() {
        if (player != null) {
            playbackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
            playWhenReady = player.getPlayWhenReady();
            player.release();
            player = null;
            MobioticsDb mobioticsDb = new MobioticsDb(ExoPlayerActivity.this);
            record.setStartTime(playbackPosition + "");
            Log.d("while Insert", record.toString());
            mobioticsDb.updateRecord(record);
        }
    }

    @SuppressLint("InlinedApi")
    private void hideSystemUi() {
        playerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    private void initializePlayer() {

        DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory =
                new AdaptiveTrackSelection.Factory(bandwidthMeter);

        TrackSelector trackSelector =
                new DefaultTrackSelector(videoTrackSelectionFactory);

        LoadControl loadControl = new DefaultLoadControl();

        player = ExoPlayerFactory.newSimpleInstance(getApplicationContext(), trackSelector, loadControl);

        playerView.setPlayer(player);

        Uri uri = Uri.parse(record.getUrl());
        MediaSource mediaSource = buildMediaSource(uri);
        player.prepare(mediaSource, true, false);

        try {
            MobioticsDb mobioticsDb = new MobioticsDb(ExoPlayerActivity.this);
            record = mobioticsDb.getRecord(record.getId());
            playbackPosition = Long.parseLong(record.getStartTime());
        } catch (Exception e) {
            e.printStackTrace();
        }

        player.addListener(new Player.EventListener() {
            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                switch (playbackState) {
                    case Player.STATE_ENDED:

                        MobioticsDb mobioticsDb = new MobioticsDb(ExoPlayerActivity.this);
                        Records record_ = mobioticsDb.getNextRecord(record.getId());

                        Intent intent = new Intent(ExoPlayerActivity.this, ExoPlayerActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("selectedData", record_);
                        intent.putExtras(bundle);
                        startActivity(intent);

                        break;
                    default:
                        break;
                }
            }
        });

        player.setPlayWhenReady(playWhenReady);
        player.seekTo(currentWindow, playbackPosition);
    }

    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource.Factory(
                new DefaultHttpDataSourceFactory("exoplayer-mobiotics")).
                createMediaSource(uri);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
