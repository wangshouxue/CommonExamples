package com.wsx.autosizedemo

import android.os.Bundle

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setIsUseAutoSize(false)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}