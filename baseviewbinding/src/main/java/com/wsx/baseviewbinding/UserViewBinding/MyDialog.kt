package com.wsx.baseviewbinding.UserViewBinding

import android.content.Context
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatDialog
import androidx.core.app.DialogCompat
import com.wsx.baseviewbinding.databinding.DialogMyBinding

/**
 * @author:wangshouxue
 * @date:2022/4/14 下午4:53
 * @description:类作用
 */
class MyDialog:AppCompatDialog {
    var binding:DialogMyBinding?=null

    constructor(context:Context):super(context){

    }
    constructor(context: Context, theme:Int):super(context,theme){

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DialogMyBinding.inflate(layoutInflater)
        setContentView(binding?.root!!)
        binding?.tvTitle?.text="标题"
        binding?.tvCon?.text="我是内容啊~"
        binding?.btSure?.setOnClickListener {
            dismiss()
        }

        val params=window?.attributes
        params?.width = WindowManager.LayoutParams.MATCH_PARENT
        window?.attributes=params
    }
}