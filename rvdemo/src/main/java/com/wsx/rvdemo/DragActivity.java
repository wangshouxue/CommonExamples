package com.wsx.rvdemo;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public class DragActivity extends AppCompatActivity {
    RecyclerView rvTop,rvBottom;
    DragTopAdapter dragTopAdapter;
    DragBottomAdapter dragBottomAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag);
        findViewById(R.id.bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DragActivity.this, RtlRecycleViewActivity.class));
            }
        });
        rvTop=findViewById(R.id.rv_top);
        rvBottom=findViewById(R.id.rv_bottom);

        GridLayoutManager gridLayoutManager=new GridLayoutManager(this, 3,
                RecyclerView.VERTICAL, false);
        rvTop.setLayoutManager(gridLayoutManager);
        dragTopAdapter=new DragTopAdapter(this);
        rvTop.setAdapter(dragTopAdapter);

        GridLayoutManager gridLayoutManager2=new GridLayoutManager(this, 3,
                RecyclerView.VERTICAL, false);
        rvBottom.setLayoutManager(gridLayoutManager2);
        dragBottomAdapter=new DragBottomAdapter(this);
        rvBottom.setAdapter(dragBottomAdapter);

        List<ItemData> list= new ArrayList<>();
        list.add(new ItemData("我是1"));
        list.add(new ItemData("我是2"));
        list.add(new ItemData("我是3"));
        list.add(new ItemData("我是4"));
        list.add(new ItemData("我是5"));
        list.add(new ItemData("我是6"));
        list.add(new ItemData("我是7"));
        list.add(new ItemData("我是8"));
        list.add(new ItemData("我是9"));
        list.add(new ItemData("我是10"));
        dragTopAdapter.setList(list);

        list.clear();
        list.add(new ItemData("我是11"));
        list.add(new ItemData("我是12"));
        list.add(new ItemData("我是13"));
        list.add(new ItemData("我是14"));
        list.add(new ItemData("我是15"));
        list.add(new ItemData("我是16"));
        list.add(new ItemData("我是17"));
        list.add(new ItemData("我是18"));
        list.add(new ItemData("我是19"));
        list.add(new ItemData("我是20"));
        dragBottomAdapter.setList(list);

        // 创建并关联 ItemTouchHelper
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(sysCallback);
        itemTouchHelper.attachToRecyclerView(rvTop);

        mViewOnTouchAndDragListener =
                new RVOnTouchAndDragListener(rvBottom, dragBottomAdapter);
        rvBottom.setOnDragListener(mViewOnTouchAndDragListener);
        //长按开启拖拽
        dragBottomAdapter.setOnItemLongClickListener(mViewOnTouchAndDragListener);

        //接收rcvVehicle里拖拽过来的数据
        rvTop.setOnDragListener(rcvSysOnDragListener);
    }

    private RVOnTouchAndDragListener mViewOnTouchAndDragListener;
    View.OnDragListener rcvSysOnDragListener = new View.OnDragListener() {
        @Override
        public boolean onDrag(View v, DragEvent event) {
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_ENTERED:
                case DragEvent.ACTION_DRAG_LOCATION:
                    if (mViewOnTouchAndDragListener!=null){
                        mViewOnTouchAndDragListener.setCurrentDragViewGone();
                    }
                    break;
                /**============================
                 *  接收其他的控件放置到该RV内部
                 *============================*/
                case DragEvent.ACTION_DROP:
//                    if (dragTopAdapter.getDataList().size() == maxCount) {
//                        PFLog.i(TAG, "超出最多限制了");
//                        ToastNormalDialog mRefrigerantDialog = new ToastNormalDialog(mContext);
//                        mRefrigerantDialog.createDialog(String.format(mContext.getString(R.string.qs_hint_max_apps_count),
//                                maxCount));
//                        return false;
//                    }
                    //1. 层层获取到传递来的数据(通过Parcelable序列化)
                    ClipData clipData = event.getClipData();
                    ClipData.Item clipDataItem = clipData.getItemAt(0);
                    Intent intent = clipDataItem.getIntent();
                    Bundle bundle = intent.getBundleExtra("bundle");
                    bundle.setClassLoader(getClass().getClassLoader());
                    ItemData itemData = (ItemData) bundle.getSerializable("data");

                    //2. 获取到想要加入的位置
                    View childView = rvTop.findChildViewUnder(event.getX(), event.getY());
                    int targetPosition;
                    if (childView != null) {
//                        PFLog.i(TAG, "接收拖拽childView != null");
                        RecyclerView.ViewHolder toViewHolder =
                                rvTop.getChildViewHolder(childView);
                        targetPosition = toViewHolder.getAdapterPosition();
                    }
                    else {//加在末尾的情况
//                        PFLog.i(TAG, "接收拖拽childView = null");
                        targetPosition = dragTopAdapter.getList().size();
                    }
                    //3. 将控件添加到本控件内部
                    dragTopAdapter.getList().add(targetPosition, itemData);
                    dragTopAdapter.notifyItemInserted(targetPosition);

                    //4. 让第一个RV删除掉移动过来的Item
                    int fromPosition = mViewOnTouchAndDragListener.getFromPosition();

                    if (fromPosition != -1) {
                        dragBottomAdapter.getList().remove(fromPosition);
                        dragBottomAdapter.notifyItemRemoved(fromPosition);
                        mViewOnTouchAndDragListener.setFromPosition(-1); //归零防止出问题
                    }

                    //数据更新下，防止交换后和适配器那边的有出入
//                    mItemDataList = dragTopAdapter.getDataList();
//                    mTileVehicleList = dragBottomAdapter.getDataList();

                    break;
                default:
                    break;
            }
            return true;
        }
    };

    MyCallback sysCallback = new MyCallback();
    class MyCallback extends ItemTouchHelper.Callback {

        @Override
        public int getMovementFlags(@NonNull RecyclerView recyclerView,
                                    @NonNull RecyclerView.ViewHolder viewHolder) {
            return makeMovementFlags(ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT | ItemTouchHelper.UP | ItemTouchHelper.DOWN, 0);
        }

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView,
                              @NonNull RecyclerView.ViewHolder source,
                              @NonNull RecyclerView.ViewHolder target) {
            if (source.getItemViewType() != target.getItemViewType()) {
                return false;
            }

            // 交换数据源中的数据
            int fromPosition = source.getAdapterPosition();
            int toPosition = target.getAdapterPosition();
            Collections.swap(dragTopAdapter.getList(), fromPosition, toPosition);
            // 更新适配器数据
            dragTopAdapter.notifyItemMoved(fromPosition, toPosition);
            return true;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        }

    }


}