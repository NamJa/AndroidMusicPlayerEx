package com.example.ddori.mymusicapp;

import android.app.Activity;
import android.app.Application;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by ddori on 2017-12-19.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ItemViewHolder> {
    ArrayList<MusicData> musicData;
    Context context;


    public RecyclerAdapter(ArrayList<MusicData> items, Context context)
    {
        musicData = items;
        this.context = context;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_view, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position)
    {
        holder.nameTv.setText(musicData.get(position).getTitle());

        Uri artWorkUri = Uri.parse("content://media/external/audio/albumart");
        Uri uri = ContentUris.withAppendedId(artWorkUri, Long.valueOf(musicData.get(position).getAlbumId()));
        Glide.with(context)
                .load(uri)
                .into(holder.imageView);
    }


    @Override
    public int getItemCount()
    {
        return musicData.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView nameTv;
        private ImageView imageView;
        public ItemViewHolder(View itemView) {
            super(itemView);
            nameTv = (TextView)itemView.findViewById(R.id.musicList_Name);
            imageView = (ImageView)itemView.findViewById(R.id.musicList_Image);
        }
    }
}
