package com.wsx.imagedemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.wsx.imagedemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.iv1.setImageResource(R.mipmap.img)
        CornerViewOutlineProvider.clipView(binding.iv1,50)

    }


}