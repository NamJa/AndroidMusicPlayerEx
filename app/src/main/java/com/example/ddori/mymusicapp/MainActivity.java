package com.example.ddori.mymusicapp;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.ddori.mymusicapp.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private String[] names = {"kim", "lee", "park"};
    private static final int LAYOUT = R.layout.activity_main;
    private ActivityMainBinding mainBinding;
    private RecyclerView.Adapter adapter;

    private ArrayList<RecyclerItem> recyclerItems = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = DataBindingUtil.setContentView(this, LAYOUT);

        setRecyclerView();
    }

    private void setRecyclerView(){
        mainBinding.recyclerView.setHasFixedSize(true);

        adapter = new RecyclerAdapter(recyclerItems);
        mainBinding.recyclerView.setAdapter(adapter);
        mainBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));

        setData();
    }

    private void setData(){
        recyclerItems.clear();
        for(String name : names)
        {
            recyclerItems.add(new RecyclerItem(name));
        }
        adapter.notifyDataSetChanged();
    }
}
