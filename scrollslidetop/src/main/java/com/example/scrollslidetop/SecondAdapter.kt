package com.example.scrollslidetop

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * @Description: 类作用描述
 */
class SecondAdapter(context: Context):RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var list:MutableList<String>?=null
    var mContext:Context
    init {
        mContext=context
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ItemHold(LayoutInflater.from(mContext).inflate(R.layout.item_second,parent,false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val hold=holder as ItemHold
        hold.tv.setText(list?.get(position))
        if (position==0){
            hold.view.visibility=View.VISIBLE
        }else{
            hold.view.visibility=View.GONE
        }
    }

    fun setData(data:MutableList<String>){
        list=data
        notifyDataSetChanged()
    }
    override fun getItemCount(): Int {
        return list?.size?:0
    }

    class ItemHold(itemView: View) : RecyclerView.ViewHolder(itemView){
        var view:View
        var tv:TextView
        init {
            view=itemView.findViewById(R.id.vw)
            tv=itemView.findViewById(R.id.stv)
        }
    }
}