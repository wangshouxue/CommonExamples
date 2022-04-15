package com.wsx.baseviewbinding.UserViewBinding

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.wsx.baseviewbinding.databinding.ItemAdapterBinding
import java.util.*

/**
 * @author:wangshouxue
 * @date:2022/4/14 下午2:04
 * @description:类作用
 */
class TestAdapter: RecyclerView.Adapter<TestAdapter.MyHolder> {
    var con:MutableList<String> = mutableListOf()
    constructor(){
        con.add("ass")
        con.add("asswdcw")
        con.add("我")
        con.add("as")
        con.add("哈哈")
        con.add("我")
        con.add("注定是否打开")
        con.add("asswdcw本地跑判空")
        con.add("我不佛山")
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val itemAdapterBinding=ItemAdapterBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyHolder(itemAdapterBinding)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        var s=con.get(position)
        holder.tv.text="${s}${(position*3)}"
    }

    override fun getItemCount(): Int {
        return 9
    }

    inner class MyHolder(binging:ItemAdapterBinding):RecyclerView.ViewHolder(binging.root){
        val tv:AppCompatTextView
        init {
            tv=binging.tvItem
        }
    }
}