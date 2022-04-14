package com.wsx.baseviewbinding.UserViewBinding

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.wsx.baseviewbinding.R
import com.wsx.baseviewbinding.databinding.ActivityTestBinding
import com.wsx.baseviewbinding.databinding.LayoutMergeBinding
import com.wsx.baseviewbinding.databinding.LayoutViewstubBinding

/**
 * @author:wangshouxue
 * @date:2022/4/14 下午2:02
 * @description:类作用
 */
class TestActivity :AppCompatActivity(){
    private lateinit var binging:ActivityTestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binging=ActivityTestBinding.inflate(layoutInflater)
        setContentView(binging.root)

        //merge标签的使用
        //把merge标签的布局layout_merge.xml和主布局activity_test.xml关联起来
        val mergeBinding=LayoutMergeBinding.bind(binging.root)
        mergeBinding.tv1.setOnClickListener {
            mergeBinding.tv1.text="merge标签的tv1改变啦"
        }

        //include标签的使用
        binging.llInclude.bt1.setOnClickListener {
            binging.llInclude.bt1.text="bt1改变啦"
        }

        //ViewStub标签的使用
        var stubBinding:LayoutViewstubBinding?=null
        binging.llViewstub.setOnInflateListener { stub, inflated ->
            stubBinding=LayoutViewstubBinding.bind(inflated)
            stubBinding?.iv?.setBackgroundColor(Color.YELLOW)
        }
        binging.llViewstub.inflate()

        binging.llInclude.bt2.setOnClickListener {
            stubBinding?.iv?.setBackgroundColor(Color.MAGENTA)
        }

        supportFragmentManager.beginTransaction().replace(binging.fl.id, TestFragment()).commitNow()
    }
}