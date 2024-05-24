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

public class DragTopAdapter extends RecyclerView.Adapter<DragTopAdapter.ChildViewHolder> {

    private Context mContext;
    private List<ItemData> list=new ArrayList<>();
    public DragTopAdapter(Context mContext){
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
        holder.tv.setText(list.get(position).getmTitle());
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
