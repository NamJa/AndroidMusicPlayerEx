package com.example.ddori.mymusicapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by jongwoo on 2017. 12. 20..
 */

public class RecyclerItemClickListener implements RecyclerView.OnItemTouchListener{
    private OnItemClickListener itemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
        void onLongItemClick(View view, int position);
    }

    GestureDetector gestureDetector;

    public RecyclerItemClickListener(Context context, final RecyclerView recyclerView, OnItemClickListener listener)
    {
        itemClickListener = listener;
        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e)
            {return true;}

            @Override
            public void onLongPress(MotionEvent e)
            {
                View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                if(child != null && itemClickListener != null)
                {
                    Log.d("long", "onLongPress: press");
                    itemClickListener.onLongItemClick(child, recyclerView.getChildAdapterPosition(child));
                }
            }

        });
    }
    @Override
    public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e)
    {
        View childView = view.findChildViewUnder(e.getX(), e.getY());
        if(childView != null && itemClickListener != null && gestureDetector.onTouchEvent(e))
        {
            itemClickListener.onItemClick(childView, view.getChildAdapterPosition(childView));
            return true;
        }
        return false;
    }
    @Override
    public void onTouchEvent(RecyclerView view, MotionEvent e) {}

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {}

}
