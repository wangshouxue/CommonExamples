package com.wsx.mvvmdemo

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

/**
 * @author:wangshouxue
 * @date:2022/4/15 下午1:46
 * @description:登录状态
 */
object LoginObserver {
    private var isLoginSuccess: MutableLiveData<Boolean> = MutableLiveData<Boolean>()

    fun setIsLoginSuccess(value:Boolean){
        isLoginSuccess.postValue(value)
    }
    fun getIsLoginSuccess():MutableLiveData<Boolean>{
        return isLoginSuccess
    }

//    fun addObserve(owner: LifecycleOwner,observer: Observer<Boolean>) {
//        isLoginSuccess.observe(owner,observer)
//    }
}