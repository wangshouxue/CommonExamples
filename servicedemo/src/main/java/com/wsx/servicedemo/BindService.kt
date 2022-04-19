package com.wsx.servicedemo

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log

/**
 * @author:wangshouxue
 * @date:2022/4/19 上午9:10
 * @description:类作用
 */
class BindService:Service() {
    val bind=MyBind()

    override fun onCreate() {
        super.onCreate()
        Log.i("===","onCreate()")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.i("===","onStartCommand()")
        return START_REDELIVER_INTENT
    }

    override fun onBind(intent: Intent?): IBinder? {
        Log.i("===","onBind()")
        return bind
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.i("===","onUnbind()")
        return false
    }

    override fun onDestroy() {
        Log.i("===","onDestroy()")
        super.onDestroy()
    }

    inner class MyBind: Binder() {
        fun getBindService():BindService{
            return this@BindService
        }
    }

    fun serviceMedthod():String{
        return "我是BindService的内部方法"
    }

}