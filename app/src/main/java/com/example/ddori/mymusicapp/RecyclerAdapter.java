package com.example.ddori.mymusicapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ddori on 2017-12-19.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ItemViewHolder> {
    ArrayList<RecyclerItem> recyclerItems;

    public RecyclerAdapter(ArrayList<RecyclerItem> items)
    {
        recyclerItems = items;
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
        holder.nameTv.setText(recyclerItems.get(position).getName());
    }
    @Override
    public int getItemCount()
    {
        return recyclerItems.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView nameTv;
        public ItemViewHolder(View itemView) {
            super(itemView);
            nameTv = (TextView)itemView.findViewById(R.id.musicList_Name);
        }
    }
}
