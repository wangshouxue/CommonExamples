package com.wsx.rvdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ChildAdapter extends RecyclerView.Adapter<ChildAdapter.ChildViewHolder> {

    private Context mContext;
    private List<String> list=new ArrayList<>();
    public ChildAdapter(Context mContext){
        this.mContext=mContext;
    }
    public void setList(List<String> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public List<String> getList() {
        return list;
    }

    @NonNull
    @Override
    public ChildViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.item_layout,parent,false);
        return new ChildViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChildViewHolder holder, int position) {
    }
    @Override
    public int getItemCount() {
        return 5;
    }

    class ChildViewHolder extends RecyclerView.ViewHolder {
        private TextView tv,delete;
        public ChildViewHolder(View itemView) {
            super(itemView);
            tv=itemView.findViewById(R.id.tv);
            delete=itemView.findViewById(R.id.tvd);
        }
    }

}
