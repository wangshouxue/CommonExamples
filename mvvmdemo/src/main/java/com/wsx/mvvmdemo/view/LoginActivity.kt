package com.wsx.mvvmdemo.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.wsx.mvvmdemo.databinding.ActivityLoginBinding

/**
 * @author:wangshouxue
 * @date:2022/4/14 上午9:36
 * @description:类作用
 */
class LoginActivity :AppCompatActivity(){
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.getRoot())

    }
}