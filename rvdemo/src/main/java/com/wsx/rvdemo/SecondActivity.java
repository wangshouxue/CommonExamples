package com.wsx.rvdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        RecyclerView rv=findViewById(R.id.rvbody);

        List<String> list= new ArrayList<>();
        list.add("我是1呀");
        list.add("我是2呀");
        list.add("我是3呀");
        list.add("我是4呀");
        list.add("我是5呀");

        LinearLayoutManager manager= new LinearLayoutManager(this, RecyclerView.VERTICAL,false);
        rv.setLayoutManager(manager);
        ParentAdapter adapter= new ParentAdapter(this);
        rv.setAdapter(adapter);
        adapter.setList(list);

        rv.addOnItemTouchListener(new RecyclerView.OnItemTouchListener(){

            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv,MotionEvent e) {
                //处理RecyclerView的item中嵌套RecyclerView的滑动冲突解决
                View childViewUnder = rv.findChildViewUnder(e.getX(), e.getY());
                if (rv != null && childViewUnder != null){
                    ParentAdapter.MyViewHolder baseViewHolder = (ParentAdapter.MyViewHolder)rv.getChildViewHolder(childViewUnder);
                    rv.requestDisallowInterceptTouchEvent(isTouchValue(baseViewHolder.rv,e.getRawX(),e.getRawY()));
                }
                return false;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });

    }

    /**
     *
     * @param x event的rowX
     * @param y event的rowY
     * @return 这个点在不在内部RecycleView范围内.
     */
    public static boolean isTouchValue(RecyclerView recyclerView,float x, float y) {
        int[] wh= new int[2];
        //触摸在屏幕上的位置
        recyclerView.getLocationOnScreen(wh);
        int width = recyclerView.getMeasuredWidth();
        int height = recyclerView.getMeasuredHeight();
        return x >= wh[0] && x <= wh[0] + width && y >= wh[1] && y <= wh[1] + height;
    }


}