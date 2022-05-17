package com.wsx.scrolltv

import android.content.Context
import android.text.method.ScrollingMovementMethod
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatTextView

/**
 * @author:wangshouxue
 * @date:2022/5/17 下午3:38
 * @description:可固定行数显示，超出内容上下滑动的TextView
 */
class ScrollableTextView:AppCompatTextView {
    constructor(context: Context):this(context, null)
    constructor(context:Context, attrs: AttributeSet?): this(context, attrs, 0)
    constructor(context:Context, attrs: AttributeSet?,defStyleAttr:Int): super(context, attrs, defStyleAttr){
        init()
    }
    private fun init() {
        movementMethod = ScrollingMovementMethod.getInstance()
    }

    override fun dispatchTouchEvent(event: MotionEvent?): Boolean {
        parent.requestDisallowInterceptTouchEvent(true)
        return super.dispatchTouchEvent(event)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        super.onTouchEvent(event)
        return true
    }
}