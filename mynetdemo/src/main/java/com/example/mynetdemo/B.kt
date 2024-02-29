package com.example.mynetdemo

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout

/**
 * @Description: 类作用描述
 */
class B: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_net)
        findViewById<ConstraintLayout>(R.id.rootll).setBackgroundColor(Color.TRANSPARENT)
        Log.i("===B","onCreate")
    }

    override fun onStart() {
        super.onStart()
        Log.i("===B","onStart")

    }

    override fun onResume() {
        super.onResume()
        Log.i("===B","onResume")

    }

    override fun onPause() {
        super.onPause()
        Log.i("===B","onPause")

    }

    override fun onStop() {
        super.onStop()
        Log.i("===B","onStop")

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("===B","onDestroy")

    }

    override fun onRestart() {
        super.onRestart()
        Log.i("===B","onRestart")

    }
}