package com.wsx.rvdemo

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * @author:wangshouxue
 * @date:2022/5/11 下午5:17
 * @description:类作用
 */
class RvAdapter (private val mContext: Context, list:MutableList<String>,type:Int) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var list: MutableList<String>
    private val colors= mutableListOf("#F44336","#FFEB3B","#03A9F4","#8BC34A")
    private var type:Int

    init {
        this.list=list
        this.type=type
    }

    fun View.setOvalBg(color: Int = Color.WHITE) {
        background = GradientDrawable().apply {
            setColor(color)
            shape= GradientDrawable.OVAL
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var view= LayoutInflater.from(mContext).inflate(R.layout.item_rv, parent, false)
        return ItemHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder = holder as ItemHolder
        viewHolder.tv?.setText(list[position])
        viewHolder.tv?.setOvalBg(Color.parseColor(colors[position]))

        if (type==1){
            if (position == (list.size - 1)) {
                setMargins(viewHolder.itemView, 0, 0, 0, 0)
            }else{
                setMargins(viewHolder.itemView,-30, 0, 0, 0)
            }
        }
    }

    fun setMargins(v: View, l: Int, t: Int, r: Int, b: Int) {
        if (v.layoutParams is ViewGroup.MarginLayoutParams) {
            val p = v.layoutParams as ViewGroup.MarginLayoutParams
            p.setMargins(l, t, r, b)
            v.requestLayout()
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tv: TextView?=null
        init {
            tv = itemView.findViewById(R.id.tv)
        }
    }

}