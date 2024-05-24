package com.wsx.rvdemo;

import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class RtlRecycleViewActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag);

        findViewById(R.id.bt).setVisibility(View.GONE);
        findViewById(R.id.rv_bottom).setVisibility(View.GONE);

        RecyclerView rvTop=findViewById(R.id.rv_top);
        RtlGridLayoutManager gridLayoutManager=new RtlGridLayoutManager(this,3);
        rvTop.setLayoutManager(gridLayoutManager);
        DragTopAdapter dragTopAdapter=new DragTopAdapter(this);
        rvTop.setAdapter(dragTopAdapter);

        List<ItemData> list= new ArrayList<>();
        list.add(new ItemData("我是1"));
        list.add(new ItemData("我是2"));
        list.add(new ItemData("我是3"));
        list.add(new ItemData("我是4"));
        list.add(new ItemData("我是5"));
        list.add(new ItemData("我是6"));
        list.add(new ItemData("我是7"));
        list.add(new ItemData("我是8"));
//        list.add(new ItemData("我是9"));
//        list.add(new ItemData("我是10"));
        dragTopAdapter.setList(list);
    }
}
