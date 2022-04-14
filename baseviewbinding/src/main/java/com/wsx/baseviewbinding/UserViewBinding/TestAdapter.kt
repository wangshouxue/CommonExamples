package com.wsx.baseviewbinding.UserViewBinding

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.wsx.baseviewbinding.databinding.ItemAdapterBinding

/**
 * @author:wangshouxue
 * @date:2022/4/14 下午2:04
 * @description:类作用
 */
class TestAdapter: RecyclerView.Adapter<TestAdapter.MyHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val itemAdapterBinding=ItemAdapterBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyHolder(itemAdapterBinding)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.tv.text="我是RecyclerView哇"
    }

    override fun getItemCount(): Int {
        return 3
    }

    inner class MyHolder(binging:ItemAdapterBinding):RecyclerView.ViewHolder(binging.root){
        val tv:AppCompatTextView
        init {
            tv=binging.tvItem
        }
    }
}