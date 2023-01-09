package com.example.copyapp

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private var copyText=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val tv=findViewById<TextView>(R.id.tv)
        tv.setText("我是需要被复制的文本啊！", TextView.BufferType.SPANNABLE)

        val mSelectTextHelper= SelectTextHelper
            .Builder(tv)// 放你的textView到这里！！
            .setCursorHandleColor(Color.parseColor("#ff0000")) // 游标颜色
            .setCursorHandleSizeInDp(12f) // 游标大小 单位dp
            .setSelectedColor(Color.parseColor("#ee2288")) // 选中文本的颜色
            .setSelectAll(true) // 初次选中是否全选 default true
            .setScrollShow(false) // 滚动时是否继续显示 default true
            .setSelectedAllNoPop(false) // 已经全选无弹窗，设置了监听会回调 onSelectAllShowCustomPop 方法
            .setMagnifierShow(false) // 放大镜 default true
            .setSelectTextLength(2)// 首次选中文本的长度 default 2
            .setPopDelay(100)// 弹窗延迟时间 default 100毫秒
            .addItem(
                "复制",
                object : SelectTextHelper.Builder.onSeparateItemClickListener {
                    override fun onClick() {
                        Toast.makeText(this@MainActivity,copyText+"---已复制",Toast.LENGTH_SHORT).show()
                    }
                })
            .addItem(
                "删除",
                object : SelectTextHelper.Builder.onSeparateItemClickListener {
                    override fun onClick() {
                    }
                })
            .setPopStyle(R.color.black,0)
            .build()


        mSelectTextHelper.setSelectListener(object : SelectTextHelper.OnSelectListener{
            override fun onClick(v: View?, originalContent: String?) {

            }

            override fun onLongClick(v: View?, mSelectTextHelper: SelectTextHelper) {
            }

            override fun onTextSelected(content: String?) {
                copyText=content?:""
            }

            override fun onDismiss() {
            }

            override fun onClickUrl(url: String?) {
            }

            override fun onSelectAllShowCustomPop() {
            }

            override fun onReset() {
            }

            override fun onDismissCustomPop() {
            }

            override fun onScrolling() {
            }
        })

    }
}