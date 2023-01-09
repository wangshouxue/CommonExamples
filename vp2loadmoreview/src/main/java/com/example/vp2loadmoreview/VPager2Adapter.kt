package com.example.vp2loadmoreview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

/**
 * @Description: 类作用描述
 */
class VPager2Adapter(context: Context, lists:MutableList<Int>) : RecyclerView.Adapter<VPager2Adapter.ItemHold>(){
    private var mContext: Context
    private var list:MutableList<Int>

    init {
        mContext=context
        list=lists
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VPager2Adapter.ItemHold {
        return ItemHold(LayoutInflater.from(mContext).inflate(R.layout.item_vp,parent,false))
    }

    override fun onBindViewHolder(holder: VPager2Adapter.ItemHold, position: Int) {
        holder.iv.setImageResource(list.get(position))
    }

    override fun getItemCount(): Int {
        return list.size
    }
    inner class ItemHold(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var iv:ImageView
        init {
            iv=itemView.findViewById(R.id.iv)
        }
    }
}