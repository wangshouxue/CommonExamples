package com.wsx.rvdemo;

import android.content.Context;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ParentAdapter extends RecyclerView.Adapter<ParentAdapter.MyViewHolder> {

    private Context mContext;
    private List<String> list=new ArrayList<>();
    public ParentAdapter(Context mContext){
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
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.item_parent_layout,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
//        Log.i("===",position+"--"+list.get(position).getTvt());
        holder.tv.setText(list.get(position));
        holder.delete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                removeItem(position);
            }
        });
        holder.etv.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (isExpand) {
                    isExpand=false;
                    holder.rv.setVisibility(View.GONE);
                }else {
                    isExpand=true;
                    holder.rv.setVisibility(View.VISIBLE);
                }
            }
        });
    }
    public boolean isExpand=false;
    public void removeItem(int position) {
        list.remove(position);
        notifyItemRemoved(position);
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tv,etv,delete;
        RecyclerView rv;
        public MyViewHolder(View itemView) {
            super(itemView);
            tv=itemView.findViewById(R.id.tv);
            etv=itemView.findViewById(R.id.etv);
            delete=itemView.findViewById(R.id.tvd);

            rv=itemView.findViewById(R.id.rv_child);
            LinearLayoutManager manager= new LinearLayoutManager(mContext, RecyclerView.VERTICAL,false);
            rv.setLayoutManager(manager);
            ChildAdapter adapter= new ChildAdapter(mContext);
            rv.setAdapter(adapter);

        }

    }

}
