package com.wsx.mvvmdemo.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.wsx.mvvmdemo.LoginObserver
import com.wsx.mvvmdemo.models.User

/**
 * @author:wangshouxue
 * @date:2022/4/14 上午9:29
 * @description:类作用
 */
class MainViewModel(application: Application) :AndroidViewModel(application){
    private var app: Application
    private var user: User?=null

    init {
        this.app=application
    }

    fun login(username:String,pwd:String){
        Thread.sleep(500)
        if (username.equals("wsx")&&pwd.equals("123456")){
            //登录成功
            user=User()
            user?.apply {
                name="wsx"
                age=18
                sex="女"
            }
            LoginObserver.setIsLoginSuccess(true)
        }else{
            LoginObserver.setIsLoginSuccess(false)
        }
    }


}