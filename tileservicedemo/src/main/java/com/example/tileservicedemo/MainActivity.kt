package com.example.tileservicedemo

import android.content.ComponentName
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.service.quicksettings.TileService

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        TileService.requestListeningState(
//            applicationContext, ComponentName(
//                BuildConfig.APPLICATION_ID,
//                MyTileService::class.java.name
//            )
//        )

    }
}