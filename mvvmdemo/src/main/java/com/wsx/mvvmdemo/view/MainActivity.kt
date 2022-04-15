package com.wsx.mvvmdemo.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import com.wsx.mvvmdemo.LoginObserver
import com.wsx.mvvmdemo.R
import com.wsx.mvvmdemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.avatar.setImageResource(R.mipmap.ic_launcher_round)
        binding.tvLogin.visibility= View.VISIBLE
        binding.tvLogout.visibility= View.GONE
        binding.avatar.visibility= View.GONE

        binding.tvLogin.setOnClickListener { startActivity(Intent(this,LoginActivity::class.java)) }
        binding.tvLogout.setOnClickListener {
            LoginObserver.setIsLoginSuccess(false)
        }

        LoginObserver.getIsLoginSuccess().observe(this,loginObserver)

    }
    private val loginObserver: Observer<Boolean> = object : Observer<Boolean> {
        override fun onChanged(isLoginSuccessFul: Boolean) {
            if (isLoginSuccessFul) {
                //已登录
                binding.tvLogin.visibility= View.GONE
                binding.tvLogout.visibility= View.VISIBLE
                binding.avatar.visibility= View.VISIBLE
            } else {
                binding.tvLogin.visibility= View.VISIBLE
                binding.tvLogout.visibility= View.GONE
                binding.avatar.visibility= View.GONE
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        LoginObserver.getIsLoginSuccess().removeObserver(loginObserver)
    }

}