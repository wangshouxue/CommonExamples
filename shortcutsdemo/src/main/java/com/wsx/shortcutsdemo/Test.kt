package com.wsx.shortcutsdemo

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

/**
 * @author:wangshouxue
 * @date:2022/4/8 下午4:44
 * @description:类作用
 */
class Test: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<TextView>(R.id.tv).text="测试测试啊"
    }
}