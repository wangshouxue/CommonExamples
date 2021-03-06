package com.wsx.rvdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL
import androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    lateinit var rv1:RecyclerView
    lateinit var rv2:RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rv1=findViewById(R.id.rv1)
        rv2=findViewById(R.id.rv2)

        val list= mutableListOf("我是1呀","我是2呀","我是3呀","我是4呀")
        val data=list

        val manager1=LinearLayoutManager(this, HORIZONTAL,true)
        rv1.layoutManager=manager1
        data.reverse()
        rv1.adapter=RvAdapter(this,data,1)

        val manager2=LinearLayoutManager(this,VERTICAL,false)
        manager2.stackFromEnd=true
        rv2.layoutManager=manager2
        rv2.adapter=RvAdapter(this,list,2)


    }
}