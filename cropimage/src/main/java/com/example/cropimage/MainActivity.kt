package com.example.cropimage

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatButton

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
         val cl=findViewById<ClipImageLayout>(R.id.cl)
        cl.setHorizontalPadding(30)
        //设置要裁剪的图片
        cl.setImageBitmap(BitmapFactory.decodeResource(resources,R.mipmap.img))

        val iv=findViewById<ImageView>(R.id.iv)
        val bt=findViewById<AppCompatButton>(R.id.bt)
        bt.setOnClickListener {
            //得到裁剪后的图片并显示到控件上
            iv.setImageBitmap(cl.clip())
        }
    }
}