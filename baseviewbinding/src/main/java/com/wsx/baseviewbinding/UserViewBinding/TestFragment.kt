package com.wsx.baseviewbinding.UserViewBinding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wsx.baseviewbinding.databinding.FragmentTestBinding

/**
 * @author:wangshouxue
 * @date:2022/4/14 下午2:04
 * @description:类作用
 */
class TestFragment:Fragment() {
//    binding变量只有在onCreateView与onDestroyView才是可用的。
//    因为我们fragment的生命周期和activity的不同，fragment 可以超出其视图的生命周期，
//    如果onDestroy不将其置为空，有可能引起内存泄漏。
    private var binding:FragmentTestBinding?=null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding=FragmentTestBinding.inflate(inflater,container,false)
        init()
        return binding?.root
    }

    fun init() {
//        val manager=LinearLayoutManager(context, RecyclerView.VERTICAL,false)
        val manager=AutoLayoutManager()
        binding?.rvFg?.layoutManager=manager
        binding?.rvFg?.addItemDecoration(SpaceItemDecoration(20))
        val myAapter=TestAdapter()
        binding?.rvFg?.adapter=myAapter
    }

    override fun onDestroy() {
        super.onDestroy()
        binding==null
    }
}