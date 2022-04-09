package com.wsx.baseviewbinding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.wsx.baseviewbinding.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    val bing:ActivityMainBinding by binding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bing.textView.text="hi"
    }
}