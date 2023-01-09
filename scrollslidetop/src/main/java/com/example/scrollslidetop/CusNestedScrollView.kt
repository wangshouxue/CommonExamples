package com.example.scrollslidetop

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.RecyclerView

/**
 * @Description: 二级置顶效果及滑动冲突解决
 * https://blog.csdn.net/u013347784/article/details/122800676
 */
@RequiresApi(Build.VERSION_CODES.M)
class CusNestedScrollView : NestedScrollView {
    /**
     * 顶部的view  id = activity_nested_view
     */
    private var topView: View? = null

    /**
     * 包裹TabLayout+RecyclerView 的 LinearLayout id = activity_nested_ll
     */
    private var contentView: ViewGroup? = null

    /**
     * 处理惯性滑动的工具类
     */
    private var mFlingHelper: FlingHelper? = null

    /**
     * 记录当前自身已经滑动的距离
     */
    var totalDy = 0

    /**
     * 用于判断RecyclerView是否在fling
     */
    var isRecyclerViewStartFling = false

    /**
     * 记录当前滑动的y轴加速度
     */
    private var velocityY = 0

    constructor(context: Context) : super(context) {
        init()
    }

    /**
     * 必须的构造函数，系统会通过反射来调用此构造方法完成view的创建
     */
    constructor(context: Context, attr: AttributeSet) : super(context, attr) {
        init()
    }

    constructor (context: Context, attr: AttributeSet, defZStyle: Int) : super(
        context,
        attr,
        defZStyle
    ) {
        init()
    }

    private fun init() {
        mFlingHelper = FlingHelper(context)
        //添加滚动监听 v就是当前NestedScrollLayout
        setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            if (isRecyclerViewStartFling) {
                totalDy = 0
                isRecyclerViewStartFling = false
            }
            //scrollY 是 当前向上滑动了多少 0 就是一点没滑动 就是在顶部状态
            if (scrollY == 0) {
//                log("TOP SCROLL")
                // refreshLayout.setEnabled(true);
            }
            //v.measuredHeight() 就是屏幕高度

            if (scrollY == topView!!.measuredHeight) {
//                log("BOTTOM SCROLL v.getMeasuredHeight() = " + v.measuredHeight)
                //滑动到底部以后 还有惯性让子类接着来滑动
                dispatchChildFling()
            }
            //在RecyclerView fling情况下，记录当前RecyclerView在y轴的偏移
            totalDy += scrollY - oldScrollY
        }
    }

    private fun dispatchChildFling() {
        if (velocityY != 0) {
            //将惯性的加速度转换为具体的距离
            val splineFlingDistance = mFlingHelper!!.getSplineFlingDistance(velocityY)
            //举例解释：假设用力滑动一下 能滑动100个单位的距离，totalDy是外层ZSNestedScrollView已经滑动的距离
            // 假设是50 那么还有50 咋办呢 ，要让子布局（RecycleView）来滑动剩下的50
            if (splineFlingDistance > totalDy) {
                childFling(
                    mFlingHelper!!.getVelocityByDistance(
                        splineFlingDistance - java.lang.Double.valueOf(
                            totalDy.toDouble()
                        )
                    )
                )
            }
        }
        //重置变量
        totalDy = 0
        velocityY = 0
    }

    private fun childFling(velY: Int) {
        if (contentView != null) {
            val childRecyclerView: RecyclerView? = getChildRecyclerView(contentView!!)
            childRecyclerView?.fling(0, velY)
        }
    }

    private fun getChildRecyclerView(viewGroup: ViewGroup): RecyclerView? {
        for (i in 0 until viewGroup.childCount) {
            val view = viewGroup.getChildAt(i)
            if (view is RecyclerView && view.javaClass == RecyclerView::class.java) {
                return viewGroup.getChildAt(i) as RecyclerView
            } else if (viewGroup.getChildAt(i) is ViewGroup) {
                val childRecyclerView: ViewGroup? =
                    getChildRecyclerView(viewGroup.getChildAt(i) as ViewGroup)
                if (childRecyclerView is RecyclerView) {
                    return childRecyclerView
                }
            }
            continue
        }
        return null
    }

    override fun fling(velocityY: Int) {
        super.fling(velocityY)
        if (velocityY <= 0) {
            this.velocityY = 0
        } else {
            isRecyclerViewStartFling = true
            this.velocityY = velocityY
        }
    }

    /**
     * view 加载完成后执行
     */
    override fun onFinishInflate() {
        super.onFinishInflate()
        topView = (getChildAt(0) as ViewGroup).getChildAt(0)
        contentView = (getChildAt(0) as ViewGroup).getChildAt(1) as ViewGroup
    }

    /**
     * 参数	解释
     * target	触发嵌套滑动的 view
     * dx	表示 view 本次 x 方向的滚动的总距离，单位：像素
     * dy	表示 view 本次 y 方向的滚动的总距离，单位：像素
     * consumed	输出：表示父布局消费的水平和垂直距离。
     * type	触发滑动事件的类型：其值有
     * ViewCompat. TYPE_TOUCH
     * ViewCompat. TYPE_NON_TOUCH
     *
     */
    override fun onNestedPreScroll(target: View, dx: Int, dy: Int, consumed: IntArray, type: Int) {
//        log(getScrollY()+"::onNestedPreScroll::"+topView.getMeasuredHeight())
//        log( "dx="+ dx + " dy=" + dy)
        // 如果能继续向上滑动，就滑动
        val canScroll = dy > 0 && scrollY < topView!!.measuredHeight
        if (canScroll) {
            scrollBy(0, dy)
            consumed[1] = dy
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val lp = contentView!!.layoutParams
        // getMeasuredHeight() 得到的就是屏幕高度 当前ZSNestedScrollView高度是MatchParent
//        log("onMeasure getMeasuredHeight() = $measuredHeight")
        lp.height = measuredHeight
        // 调整contentView的高度为屏幕高度，这样ZSNestedScrollView总高度就是屏幕高度+topView的高度
        // 因此往上滑动 滑完topView后,TabLayout就卡在顶部了，因为ZSNestedScrollView滑不动了啊，就这么高
        // 接着在滑就是其内部的RecyclerView去滑动了
        contentView!!.layoutParams = lp
    }
}