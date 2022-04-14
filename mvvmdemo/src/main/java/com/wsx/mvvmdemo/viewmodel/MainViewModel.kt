package com.wsx.mvvmdemo.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import com.wsx.mvvmdemo.models.User

/**
 * @author:wangshouxue
 * @date:2022/4/14 上午9:29
 * @description:类作用
 */
class MainViewModel(application: Application) :AndroidViewModel(application){
    private var app: Application
    init {
        this.app=application
    }
    private var user: User=User()

    fun login(name:String,pwd:String){
        Thread.sleep(3000)
        if (name.equals("wsx")&&pwd.equals("123456")){
            //登录成功
            user.name="wsx"
            user.age=18
            user.sex="女"
        }else{
            Toast.makeText(app,"账号或密码错误",Toast.LENGTH_SHORT).show()
        }
    }
}