package com.shouxue.swipeback

import android.os.Bundle
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.shouxue.test.back.SwipeBackHelper
import com.shouxue.test.back.dispatchTouchEvent

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // 初始化侧滑返回帮助类
        mSwipeBackHelper = SwipeBackHelper(this)
    }

    private lateinit var mSwipeBackHelper: SwipeBackHelper

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        // 使用侧滑处理
        return mSwipeBackHelper.dispatchTouchEvent(ev) {
            super.dispatchTouchEvent(ev)
        }
    }

}