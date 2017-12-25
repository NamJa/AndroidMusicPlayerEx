package com.example.ddori.mymusicapp;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import com.example.ddori.mymusicapp.databinding.ActivityMainBinding;
import com.example.ddori.mymusicapp.databinding.ActivityPlayMusicBinding;

import java.util.ArrayList;

public class PlayMusicActivity extends AppCompatActivity{
    private ActivityPlayMusicBinding playMusicBinding;
    private static final int LAYOUT = R.layout.activity_play_music;
    private MediaPlayer mediaplayer;
    private ArrayList<MusicData> musicList;
    boolean isPlaying = true;
    private ContentResolver res;
    private Button btn;
    private ProgressUpdate progressUpdate;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        playMusicBinding = DataBindingUtil.setContentView(this, LAYOUT);

        Intent intent = getIntent();
        position = intent.getIntExtra("position", 0);
        mediaplayer = new MediaPlayer();
        musicList = (ArrayList<MusicData>) intent.getSerializableExtra("playlist");
        res = getContentResolver();

        playMusicBinding.playMusicImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playMusicBinding.pauseMusicImgVIew.setVisibility(View.VISIBLE);
                playMusicBinding.playMusicImgView.setVisibility(View.GONE);
                mediaplayer.seekTo(mediaplayer.getCurrentPosition());
                mediaplayer.start();

            }
        });

        playMusicBinding.pauseMusicImgVIew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playMusicBinding.pauseMusicImgVIew.setVisibility(View.GONE);
                playMusicBinding.playMusicImgView.setVisibility(View.VISIBLE);
                mediaplayer.pause();

            }
        });

        playMusicBinding.previewImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position-1 >= 0)
                {
                    position--;
                    playMusic(musicList.get(position));
                    playMusicBinding.musicSeekBar.setProgress(0);
                }
            }
        });

        playMusicBinding.nextImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position + 1 < musicList.size())
                {
                    position++;
                    playMusic(musicList.get(position));
                    playMusicBinding.musicSeekBar.setProgress(0);
                }
            }
        });

        playMusic(musicList.get(position));
        progressUpdate = new ProgressUpdate();
        progressUpdate.start();

        playMusicBinding.musicSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int m = progress/60000;
                int s = (progress%60000)/1000;
                int totM = mediaplayer.getDuration()/60000;
                int totS = (mediaplayer.getDuration()%60000)/1000;
                if(s < 10) {
                    playMusicBinding.musicDurTxtView.setText(m + ":0" + s);
                } else {
                    playMusicBinding.musicDurTxtView.setText(m + ":" + s);
                }
                if (totS < 10)
                    playMusicBinding.musicTotDurTxtView.setText(totM + ":0" + totS);
                else {
                    playMusicBinding.musicTotDurTxtView.setText(totM + ":" + totS);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                mediaplayer.pause();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaplayer.seekTo(playMusicBinding.musicSeekBar.getProgress());
                if (playMusicBinding.musicSeekBar.getProgress() > 0 && playMusicBinding.playMusicImgView.getVisibility() == View.GONE) {
                    mediaplayer.start();
                }
            }
        });
        mediaplayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if (position + 1 < musicList.size()) {
                    position++;
                    playMusic(musicList.get(position));
                }
            }
        });
    }


    public void playMusic(MusicData musicData)
    {
        try
        {
            playMusicBinding.musicSeekBar.setProgress(0);
            playMusicBinding.musicNameTxtView.setText(musicData.getTitle());
            Uri musicURI = Uri.withAppendedPath(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, ""+musicData.getId());
            mediaplayer.reset();
            mediaplayer.setDataSource(this, musicURI);
            mediaplayer.prepare();
            mediaplayer.start();
            playMusicBinding.musicSeekBar.setMax(mediaplayer.getDuration());
            if(mediaplayer.isPlaying())
            {
                playMusicBinding.playMusicImgView.setVisibility(View.GONE);
                playMusicBinding.pauseMusicImgVIew.setVisibility(View.VISIBLE);

            }else {
                playMusicBinding.playMusicImgView.setVisibility(View.VISIBLE);
                playMusicBinding.pauseMusicImgVIew.setVisibility(View.GONE);
            }

            Bitmap bitmap = BitmapFactory.decodeFile(getAlbumArt(Long.parseLong(musicData.getAlbumId()), getApplicationContext()));
            playMusicBinding.albumArtImgView.setImageBitmap(bitmap);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private static String getAlbumArt(long albumId, Context context)
    {
        Cursor albumCursor = context.getContentResolver().query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Audio.Albums.ALBUM_ART},
                MediaStore.Audio.Albums._ID + " = ?",
                new String[]{Long.toString(albumId)},
                null);
        boolean queryResult = albumCursor.moveToFirst();
        String result = null;
        if(queryResult)
            result = albumCursor.getString(0);
        albumCursor.close();
        return result;
    }
    class ProgressUpdate extends Thread
    {
        @Override
        public void run()
        {
            while(isPlaying)
            {
                try
                {
                    Thread.sleep(500);
                    if(mediaplayer != null) {
                        playMusicBinding.musicSeekBar.setProgress(mediaplayer.getCurrentPosition());
                    }
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        isPlaying = false;
        if(mediaplayer != null)
        {
            mediaplayer.release();
            mediaplayer = null;
        }
    }
}