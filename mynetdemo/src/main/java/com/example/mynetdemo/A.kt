package com.example.mynetdemo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

/**
 * @Description: 类作用描述
 */
class A: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_net)
        Log.i("===A","onCreate")
        findViewById<TextView>(R.id.tv).setOnClickListener{
            startActivity(Intent(this@A,B::class.java))
        }
    }

    override fun onStart() {
        super.onStart()
        Log.i("===A","onStart")

    }

    override fun onResume() {
        super.onResume()
        Log.i("===A","onResume")

    }

    override fun onPause() {
        super.onPause()
        Log.i("===A","onPause")

    }

    override fun onStop() {
        super.onStop()
        Log.i("===A","onStop")

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("===A","onDestroy")

    }

    override fun onRestart() {
        super.onRestart()
        Log.i("===A","onRestart")

    }
}