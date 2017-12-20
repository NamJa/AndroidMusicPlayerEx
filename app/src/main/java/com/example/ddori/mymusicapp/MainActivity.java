package com.example.ddori.mymusicapp;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

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

        mainBinding.recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), mainBinding.recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getApplicationContext(), PlayMusicActivity.class);
                intent.putExtra("musicName", recyclerItems.get(position).getName());
                startActivity(intent);
            }

            @Override
            public void onLongItemClick(View view, int position) {
                Toast.makeText(getApplicationContext(), position+"번 째 아이템 롱 클릭", Toast.LENGTH_SHORT).show();
            }
        }));
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
