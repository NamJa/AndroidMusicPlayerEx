package com.example.ddori.mymusicapp;

import android.content.ContentResolver;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import com.example.ddori.mymusicapp.databinding.ActivityMainBinding;
import com.example.ddori.mymusicapp.databinding.ActivityPlayMusicBinding;

import java.util.ArrayList;

public class PlayMusicActivity extends AppCompatActivity {
    private ActivityPlayMusicBinding playMusicBinding;
    private static final int LAYOUT = R.layout.activity_play_music;
    private MediaPlayer mediaplayer;
    private ArrayList<MusicData> musicList;
    private ContentResolver res;
    private Button btn;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_music);

        playMusicBinding = DataBindingUtil.setContentView(this, LAYOUT);

        Intent intent = getIntent();
        position = intent.getIntExtra("position", 0);
        musicList = (ArrayList<MusicData>) intent.getSerializableExtra("playlist");
        res = getContentResolver();

        playMusicBinding.playMusicImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        playMusicBinding.musicSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                mediaplayer.pause();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaplayer.seekTo(playMusicBinding.musicSeekBar.getProgress());
                if(playMusicBinding.musicSeekBar.getProgress() > 0 && playMusicBinding.playMusicImgView.getVisibility() == View.GONE)
                {
                    mediaplayer.start();
                }
            }
        });


    }

}
