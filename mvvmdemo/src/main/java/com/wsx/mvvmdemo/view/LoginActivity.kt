package com.wsx.mvvmdemo.view

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.wsx.mvvmdemo.databinding.ActivityLoginBinding
import com.wsx.mvvmdemo.viewmodel.MainViewModel
import androidx.lifecycle.Observer
import com.wsx.mvvmdemo.LoginObserver


/**
 * @author:wangshouxue
 * @date:2022/4/14 上午9:36
 * @description:类作用
 */
class LoginActivity :AppCompatActivity(){
    private lateinit var binding: ActivityLoginBinding
    private var viewModel: MainViewModel?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.getRoot())
        viewModel=MainViewModel(this.application)
        binding.etName.addTextChangedListener(textWatcher)
        binding.etPwd.addTextChangedListener(textWatcher)
        binding.btLogin.setOnClickListener {
            viewModel?.login(binding.etName.text.toString(),binding.etPwd.text.toString())
        }
        LoginObserver.getIsLoginSuccess().observe(this,loginObserver)
    }
    private val loginObserver: Observer<Boolean> = object : Observer<Boolean> {
        override fun onChanged(isLoginSuccessFul: Boolean) {
            if (isLoginSuccessFul) {
                Toast.makeText(this@LoginActivity,"登录成功",Toast.LENGTH_SHORT).show()
                Thread.sleep(500)
                finish()
            } else {
                Toast.makeText(this@LoginActivity,"登录失败", Toast.LENGTH_SHORT).show()

            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        LoginObserver.getIsLoginSuccess().removeObserver(loginObserver)
        viewModel=null
    }
    val textWatcher=object:TextWatcher{
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }

        override fun afterTextChanged(s: Editable?) {
            changeBtStatus()
        }

    }

    fun changeBtStatus(){
        if (!TextUtils.isEmpty(binding.etName.text)&&!TextUtils.isEmpty(binding.etPwd.text)){
            binding.btLogin.isEnabled=true
        }else{
            binding.btLogin.isEnabled=false
        }
    }

}