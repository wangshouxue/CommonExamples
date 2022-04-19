package com.wsx.animatordemo

import android.animation.TimeAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AccelerateInterpolator
import com.wsx.animatordemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.tv.setOnClickListener {
            timeAnim()
        }
    }

    fun timeAnim(){
        val timeAnimator = TimeAnimator()
        timeAnimator.setStartDelay(100)
        timeAnimator.start()
        timeAnimator.setInterpolator(AccelerateInterpolator()) //添加快速的插值器
        timeAnimator.setTimeListener(object : TimeAnimator.TimeListener {
            override fun onTimeUpdate(animation: TimeAnimator?, totalTime: Long, deltaTime: Long) {

            }
        })
        binding.tv.animation
    }
}