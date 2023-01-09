package com.example.vp2loadmoreview

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.OverScroller
import android.widget.TextView
import androidx.viewpager2.widget.ViewPager2

/**
 * @Description: 类作用描述
 */
class VpLoadMoreView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
) : LinearLayout(context, attrs, defStyle) {//必须是横向布局，XML的顶层布局使用的merge标签，这样既可以优化一层布局，又可以在父View中直接操作加载图文详情的子View。
    private lateinit var mMVPager2: ViewPager2
    private lateinit var mLoadMoreContainer: LinearLayout
    private lateinit var mIvArrow: ImageView
    private lateinit var mTvTips: TextView
    private var mNeedIntercept: Boolean = false //是否需要拦截VP2事件
    private var mCurPos: Int = 0 //Banner当前滑动的位置
    private var mLastX = 0f
    private var mLastDownX = 0f //用于判断滑动方向
    private var mMenuWidth = 0 //加载更多View的宽度
    private var mShowMoreMenuWidth = 0 //加载更多发生变化时的宽度
    private var mLastStatus = false // 默认箭头样式
    private var mAction: (() -> Unit)? = null
    private var mScroller: OverScroller
    private var isTouchLeft = false //是否是向左滑动

    private var animRightStart = RotateAnimation(0f, -180f,
        Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f).apply {
        duration = 300
        fillAfter = true
    }

    private var animRightEnd = RotateAnimation(-180f, 0f,
        Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f).apply {
        duration = 300
        fillAfter = true
    }
    val list= mutableListOf<Int>(R.drawable.img1,R.drawable.img2,R.drawable.img3)
    init {
        orientation = HORIZONTAL
        val view=View.inflate(context, R.layout.layout_vp2_load_more_view, this)
        mMVPager2=view.findViewById(R.id.mvp_pager2)
        mLoadMoreContainer=view.findViewById(R.id.load_more_container)
        mIvArrow=view.findViewById(R.id.iv_pull)
        mTvTips=view.findViewById(R.id.tv_tips)
        mScroller = OverScroller(context)

        mMVPager2.adapter=VPager2Adapter(getContext(),list)
        mMVPager2.registerOnPageChangeCallback(object :ViewPager2.OnPageChangeCallback(){
            override fun onPageScrollStateChanged(state: Int) {
                if (mCurPos ==(list.size -1)&& isTouchLeft && state == ViewPager2.SCROLL_STATE_DRAGGING) {
                    //Banner在最后一页 & 手势往左滑动 & 当前是滑动状态
                    mNeedIntercept = true //父View可以拦截
                    mMVPager2.setUserInputEnabled(false) //VP2设置为不可滑动
                }
            }
            override fun onPageSelected(position: Int) {
                mCurPos = position
            }
        })
    }
    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        mMenuWidth = mLoadMoreContainer.measuredWidth
        mShowMoreMenuWidth = mMenuWidth / 5 * 4
        super.onLayout(changed, l, t, r, b)
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        when (ev?.action) {
            MotionEvent.ACTION_DOWN -> {
                mLastX = ev.x
                mLastDownX = ev.x
            }
            MotionEvent.ACTION_MOVE -> {
                isTouchLeft = mLastDownX - ev.x > 0 //判断滑动方向
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        var isIntercept = false
        when (ev?.action) {
            MotionEvent.ACTION_MOVE -> isIntercept = mNeedIntercept //是否拦截Move事件
        }
        return isIntercept
    }

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        when (ev?.action) {
            MotionEvent.ACTION_MOVE -> {
                val mDeltaX = mLastX - ev.x
                if (mDeltaX > 0) {
                    //向左滑动
                    if (mDeltaX >= mMenuWidth || scrollX + mDeltaX >= mMenuWidth) {
                        //右边缘检测
                        scrollTo(mMenuWidth, 0)
                        return super.onTouchEvent(ev)
                    }
                } else if (mDeltaX < 0) {
                    //向右滑动
                    if (scrollX + mDeltaX <= 0) {
                        //左边缘检测
                        scrollTo(0, 0)
                        return super.onTouchEvent(ev)
                    }
                }
                showLoadMoreAnim(scrollX + mDeltaX)
                scrollBy(mDeltaX.toInt(), 0)
                mLastX = ev.x
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                smoothCloseMenu()
                mNeedIntercept = false
                mMVPager2.setUserInputEnabled(true)
                //执行回调
                val mDeltaX = mLastX - ev.x
                if (scrollX + mDeltaX >= mShowMoreMenuWidth) {
                    mAction?.invoke()
                }
            }
        }
        return super.onTouchEvent(ev)
    }

    private fun smoothCloseMenu() {
        mScroller.forceFinished(true)
        /**
         * 左上为正，右下为负
         * startX：X轴开始位置
         * startY: Y轴结束位置
         * dx：X轴滑动距离
         * dy：Y轴滑动距离
         * duration：滑动时间
         */
        mScroller.startScroll(scrollX, 0, -scrollX, 0, 300)
        invalidate()
    }

    override fun computeScroll() {
        if (mScroller.computeScrollOffset()) {
            showLoadMoreAnim(0f) //动画还原
            scrollTo(mScroller.currX, mScroller.currY)
            invalidate()
        }
    }

    private fun showLoadMoreAnim(dx: Float) {
        val showLoadMore = dx >= mShowMoreMenuWidth
        if (mLastStatus == showLoadMore) return
        if (showLoadMore) {
//            mIvArrow.startAnimation(animRightStart)
            mIvArrow.setImageResource(R.mipmap.icon_arrow_right)
            mTvTips.text = "释放查看图文详情"
            mLastStatus = true
        } else {
//            mIvArrow.startAnimation(animRightEnd)
            mIvArrow.setImageResource(R.mipmap.icon_arrow_left)
            mTvTips.text = "滑动查看图文详情"
            mLastStatus = false
        }
    }

}