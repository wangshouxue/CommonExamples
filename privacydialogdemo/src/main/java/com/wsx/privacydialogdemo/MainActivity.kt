package com.wsx.privacydialogdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val tv=findViewById<TextView>(R.id.tv)
        tv.setOnClickListener {
            PrivacyDialog(this@MainActivity).showDialog{
                var toast=Snackbar.make(tv,"已同意协议", Snackbar.LENGTH_SHORT)
                toast.show()
//                Thread.sleep(2000)
//                toast.dismiss()
            }
        }
    }

}