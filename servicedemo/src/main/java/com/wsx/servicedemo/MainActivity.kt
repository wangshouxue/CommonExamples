package com.wsx.servicedemo

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    lateinit var tv:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tv=findViewById<TextView>(R.id.tv)
        tv.setOnClickListener {
            Log.i("===","Click")
            val intent=Intent(this,BindService::class.java)
            bindService(intent, serviceConn,BIND_AUTO_CREATE)
            startService(intent)
        }
    }
    val serviceConn=object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder:BindService.MyBind= service as BindService.MyBind
            val bindService=binder.getBindService()
            tv.text=bindService.serviceMedthod()

        }

        override fun onServiceDisconnected(name: ComponentName?) {

        }

    }
}