package com.wsx.autosizedemo

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.widget.AppCompatButton

class FontActivity : BaseActivity() {
    private var font=1f
    private var isChanged=false

    override fun onCreate(savedInstanceState: Bundle?) {
//        setFont(2f)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<AppCompatButton>(R.id.bt).setOnClickListener {
            font=2f
            isChanged=true
        }
    }

    override fun onBackPressed() {
        if (isChanged){//字体改变了
            //需要把字体大小存储起来（BaseActivity中取出字体并设置） 然后重启
            val sp=getSharedPreferences("sxdemo", MODE_PRIVATE)
            val editor =sp.edit()
            editor.putFloat("fontsize",font)
            editor.commit()
            //重启app
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.putExtra("restart", true)
            startActivity(intent)
        }
        finish()
    }
}