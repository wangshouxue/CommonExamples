package com.wsx.rvdemo;

import android.content.ClipData;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.View;

import java.util.Collections;

import androidx.recyclerview.widget.RecyclerView;

public class RVOnTouchAndDragListener implements View.OnDragListener, RVOnItemLongClickListenr {
    int fromPosition = -1;
    int toPosition = 0;
    RecyclerView mRecyclerView;
    DragBottomAdapter mVehicleAdapter;

    public int getFromPosition() {
        return fromPosition;
    }

    public void setFromPosition(int fromPosition) {
        this.fromPosition = fromPosition;
    }

    public int getToPosition() {
        return toPosition;
    }

    public RVOnTouchAndDragListener() {
    }

    public RVOnTouchAndDragListener(RecyclerView recyclerView, DragBottomAdapter adapter) {
        mRecyclerView = recyclerView;
        mVehicleAdapter = adapter;
    }

    private View currentDragView;

    public void setCurrentDragViewGone() {
        if (currentDragView!=null){
            currentDragView.setVisibility(View.GONE);
            currentDragView=null;
        }
    }

    @Override
    public boolean onDrag(View v, DragEvent event) {
        if (mRecyclerView == null || mVehicleAdapter == null) {
            return false;
        }
        final int action = event.getAction();
//        PFLog.i("qs_drag", "action: " + action);
        switch (action) {
            /**=======================================================
             * 1. 开始拖拽需要确定当前点击的ItemView
             * 2. 如果点击在界外，则fromPosition= -1, 需要在其他阶段确定点击的Item
             *=========================================================*/
            case DragEvent.ACTION_DRAG_STARTED:
                View itemView = mRecyclerView.findChildViewUnder(event.getX(), event.getY());
                if (itemView != null) {
                    RecyclerView.ViewHolder startViewHolder =
                            mRecyclerView.getChildViewHolder(itemView);
                    fromPosition = startViewHolder.getAdapterPosition();
                    currentDragView=startViewHolder.itemView;
                    //点击的ItemView不可见, 如同已经被拖走，进入ACTION_DRAG_ENTERED时再隐藏，否则长按就会隐藏
//                    startViewHolder.itemView.setVisibility(View.GONE);
//                    mVehicleAdapter.notifyDataSetChanged();//刷新会导致异常
                }
                return true;
            /**=======================================================
             * 2. 拖拽进入了当前控件的区域, 如果第一阶段没有确定ItemView，该阶段补充确定
             *=========================================================*/
            case DragEvent.ACTION_DRAG_ENTERED:
                if (fromPosition == -1) {
                    itemView = mRecyclerView.findChildViewUnder(event.getX(), event.getY());
                    if (itemView != null) {
                        RecyclerView.ViewHolder startViewHolder =
                                mRecyclerView.getChildViewHolder(itemView);
                        fromPosition = startViewHolder.getAdapterPosition();
                        //点击的ItemView不可见
                        startViewHolder.itemView.setVisibility(View.GONE);
//                        mVehicleAdapter.notifyDataSetChanged();
                    }
                }else {
                    mRecyclerView.findViewHolderForAdapterPosition(fromPosition).itemView.setVisibility(View.GONE);
                }
                return true;
            /**=======================================================
             * 3. 拖拽过程中对RecyclerView里面的ItemView顺序进行切换
             *=========================================================*/
            case DragEvent.ACTION_DRAG_LOCATION:
                //1. 获取目标View的VeiwHolder
                itemView = mRecyclerView.findChildViewUnder(event.getX(), event.getY());
                if (itemView != null) {
                    RecyclerView.ViewHolder toViewHolder =
                            mRecyclerView.getChildViewHolder(itemView);
                    //2. 获得目标View的Position
                    toPosition = toViewHolder.getAdapterPosition();

                    //3. 可能开始拖拽的位置不在RecylcerView中
                    if (fromPosition == -1) {
                        fromPosition = toPosition;
                    }
                    //4. from 和 to 之间进行位置变换
                    if (fromPosition < toPosition) {
                        for (int i = fromPosition; i < toPosition; i++) {
                            Collections.swap(mVehicleAdapter.getList(), i, i + 1);
                        }
                    }
                    else {
                        for (int i = fromPosition; i > toPosition; i--) {
                            Collections.swap(mVehicleAdapter.getList(), i, i - 1);
                        }
                    }
                    mVehicleAdapter.notifyItemMoved(fromPosition, toPosition);
                    //5. 已经完成了位置交换
                    fromPosition = toPosition;
                }
                return true;
            case DragEvent.ACTION_DRAG_EXITED:
                // 处理拖拽离开视图区域的动作
                v.invalidate();
                return true;
            /**=======================================================
             * 4. 拖拽行为停止，将之前不可见的子Item的View重新可见
             *=========================================================*/
            case DragEvent.ACTION_DROP:
//                mRecyclerView.findViewHolderForAdapterPosition(fromPosition).itemView.setVisibility(View.VISIBLE);
                return true;
            case DragEvent.ACTION_DRAG_ENDED:
                // 当拖拽操作完全结束时（不论成功与否）
                // 可以在这里恢复拖拽前的状态，或者显示拖拽结果
//                PFLog.i("qs_drag", "拖拽操作完全结束result: " + event.getResult());
                if (event.getResult()) {
                    // 拖拽成功，可以执行成功的处理逻辑
                    mVehicleAdapter.notifyDataSetChanged();
                }
                else {
                    // 拖拽失败，可以执行失败的处理逻辑
                    mRecyclerView.findViewHolderForAdapterPosition(fromPosition).itemView.setVisibility(View.VISIBLE);
                }
                return true;
            default:
                break;
        }
        return false;
    }

    /**
     * =======================================================
     * 长按进入拖拽状态，并将控件的数据保存到“剪切板”中
     * =========================================================
     */
    @Override
    public boolean onLongClick(View view, ItemData data) {
        //1. 将数据序列化-通过剪切板传输
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", data);
        Intent intent = new Intent();
        intent.putExtra("bundle", bundle);
        ClipData clipData = ClipData.newIntent("intent", intent);
        //2. 影子
        View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
        //3. 震动反馈，不需要震动权限
//            view.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS,
//            HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING);
        //4. 拖拽
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            view.startDragAndDrop(clipData, shadowBuilder, view, 0);
        }
        else {
            view.startDrag(clipData, shadowBuilder, view, 0);
        }
        return true;
    }
}
