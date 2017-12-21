package com.example.ddori.mymusicapp;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.ddori.mymusicapp.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;
    private static final int LAYOUT = R.layout.activity_main;
    private ActivityMainBinding mainBinding;
    private RecyclerView.Adapter adapter;
    public ArrayList<MusicData> musicList;

    private class GetMusicList extends AsyncTask<String, Integer, Integer>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Integer doInBackground(String... urls) {
            try {
                getMusicList();
            } catch (Exception e)
            {
                e.printStackTrace();
            }
            return 0;
        }
        @Override
        protected void onProgressUpdate(Integer... prograss)
        {

        }
        @Override
        protected void onPostExecute(Integer result)
        {
            super.onPostExecute(result);
            setRecyclerView();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = DataBindingUtil.setContentView(this, LAYOUT);

        checkPermission();
        GetMusicList getMusicList = new GetMusicList();
        getMusicList.execute("hahahaha");
    }
    @TargetApi(Build.VERSION_CODES.O)
    private void checkPermission() {
        onStop();
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);

            // MY_PERMISSION_REQUEST_STORAGE is an
            // app-defined int constant

        } else {
            Log.e("Permission:       ", "permission deny");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getMusicList();
                    setRecyclerView();
                    // permission was granted, yay! do the
                    // calendar task you need to do.

                } else {

                    Log.d("Permission:       ", "Permission always deny");

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                break;
        }
    }

    private void setRecyclerView() {

        mainBinding.recyclerView.setHasFixedSize(true);

        adapter = new RecyclerAdapter(musicList);
        mainBinding.recyclerView.setAdapter(adapter);
        mainBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mainBinding.recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), mainBinding.recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getApplicationContext(), PlayMusicActivity.class);
                intent.putExtra("musicName", musicList.get(position).getTitle());
                startActivity(intent);
            }

            @Override
            public void onLongItemClick(View view, int position) {
                Toast.makeText(getApplicationContext(), position + "번 째 아이템 롱 클릭", Toast.LENGTH_SHORT).show();
            }
        }));

    }

    public void getMusicList() {
        musicList = new ArrayList<>();
        String[] musicProjection = {MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.ALBUM_ID,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ARTIST};

        Cursor cursor = getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, musicProjection, null, null, null);

        while (cursor.moveToNext()) {
            MusicData musicData = new MusicData();
            musicData.setId(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media._ID)));
            musicData.setAlbumId(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)));
            musicData.setTitle(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)));
            System.out.println(musicData.getTitle());
            musicData.setArtist(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)));
            musicList.add(musicData);

        }
        cursor.close();
    }

}
