package com.example.threadpoolexecutordemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mExecutor: ExecutorService = Executors.newFixedThreadPool(3)
        mExecutor.execute {
            //耗时任务
        }
    }
}