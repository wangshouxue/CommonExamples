package com.wsx.rvdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DragBottomAdapter extends RecyclerView.Adapter<DragBottomAdapter.ChildViewHolder> {

    private Context mContext;
    private List<ItemData> list=new ArrayList<>();
    public DragBottomAdapter(Context mContext){
        this.mContext=mContext;
    }
    public void setList(List<ItemData> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public List<ItemData> getList() {
        return list;
    }

    @NonNull
    @Override
    public ChildViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.item_drag_layout,parent,false);
        return new ChildViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChildViewHolder holder, int position) {
        //要设为可见，因为拖拽的时候会隐藏
        holder.itemView.setVisibility(View.VISIBLE);

        ItemData bean=list.get(position);
        holder.tv.setText(bean.getmTitle());

        //借助OnLongClickListenr将点击事件转移到自定义的Item长按事件接口
        if(mOnItemLongClickListenr != null){
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    return mOnItemLongClickListenr.onLongClick(v, bean);
                }
            });
        }
    }

    private RVOnTouchAndDragListener mOnItemLongClickListenr;

    public void setOnItemLongClickListener(RVOnTouchAndDragListener mOnItemLongClickListenr) {
        this.mOnItemLongClickListenr = mOnItemLongClickListenr;
    }

    @Override
    public int getItemCount() {
        if (list==null){
            return 0;
        }
        return list.size();
    }

    class ChildViewHolder extends RecyclerView.ViewHolder {
        private TextView tv;
        public ChildViewHolder(View itemView) {
            super(itemView);
            tv=itemView.findViewById(R.id.tv);
        }
    }

}
