package com.qianfan.clearscreendemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.qianfan.clearscreendemo.ClearScreenLayout.OnSlideClearListener

class MainActivity : AppCompatActivity() {
    lateinit var rootView:ClearScreenLayout
    lateinit var childView1: View
    var isGone=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rootView=findViewById(R.id.clearRootLayout)
        childView1=findViewById(R.id.child1)
        rootView.addClearViews(childView1)
        rootView.setOnSlideListener(object :OnSlideClearListener{
            override fun onCleared() {
                isGone=true
            }

            override fun onRestored() {
                isGone=false
            }

        })
    }

//    override fun onBackPressed() {
//        if (isGone){
//            rootView.clearWithAnim()
//            return
//        }
//        super.onBackPressed()
//    }
}