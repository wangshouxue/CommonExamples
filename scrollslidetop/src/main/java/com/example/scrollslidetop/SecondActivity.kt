package com.example.scrollslidetop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SecondActivity : AppCompatActivity() {
    lateinit var rv:RecyclerView
    lateinit var mAdapter:SecondAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        rv=findViewById(R.id.srv)
        rv.layoutManager=LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        mAdapter=SecondAdapter(this)
        rv.adapter=mAdapter

        val list= mutableListOf<String>()
        list.add("1")
        list.add("2")
        list.add("3")
        list.add("4")
        list.add("5")
        list.add("6")
        list.add("7")
        list.add("8")
        list.add("9")
        list.add("10")
        list.add("11")
        list.add("12")
        list.add("13")
        list.add("14")
        list.add("15")
        mAdapter.setData(list)
    }
}