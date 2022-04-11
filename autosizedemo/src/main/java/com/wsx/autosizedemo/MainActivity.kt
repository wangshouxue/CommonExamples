package com.wsx.autosizedemo

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.widget.AppCompatButton

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setIsUseAutoSize(true)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val bt=findViewById<AppCompatButton>(R.id.bt)
        bt.text="去设置字体"
        bt.setOnClickListener {
            startActivity(Intent(this,FontActivity::class.java))
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        val restart = intent?.getBooleanExtra("restart", false)?:false
        if (restart) {
            val restartIntent = intent
            finish()
            startActivity(restartIntent)
        }
    }
}