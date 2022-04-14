package com.wsx.baseviewbinding.UserViewBinding

import android.content.Context
import android.view.LayoutInflater
import android.widget.RelativeLayout
import com.wsx.baseviewbinding.R
import com.wsx.baseviewbinding.databinding.LayoutViewBinding

/**
 * @author:wangshouxue
 * @date:2022/4/14 下午5:12
 * @description:类作用
 */
class MyView: RelativeLayout {
//    自定义组件首先是 ViewGroup 子类 , View 子类无法使用视图绑定
    private val binding:LayoutViewBinding

    constructor(context: Context):super(context){
//      注意： LayoutViewBinding.inflate(LayoutInflater.from(context))不起作用
        binding=LayoutViewBinding.inflate(LayoutInflater.from(context),this,true)
        binding.cusIv.setImageResource(R.mipmap.ic_launcher_round)
    }

}