package com.example.ddori.mymusicapp;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.ddori.mymusicapp.databinding.ActivityMainBinding;
import com.example.ddori.mymusicapp.databinding.ActivityPlayMusicBinding;

public class PlayMusicActivity extends AppCompatActivity {
    private ActivityPlayMusicBinding playMusicBinding;
    private static final int LAYOUT = R.layout.activity_play_music;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_music);

        playMusicBinding = DataBindingUtil.setContentView(this, LAYOUT);

        Intent intent = getIntent();
        String musicName = intent.getExtras().getString("musicName");

        setTxtView(musicName);
    }
    public void setTxtView(String musicName)
    {
        playMusicBinding.txtView.setText(musicName + "수신");
    }
}
