package com.wsx.rvdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ChildSlideRecyclerView extends RecyclerView {
    /**
     * 最小速度
     */
    private static final int MINIMUM_VELOCITY = 500;

    /**
     * 滑动的itemView
     */
    private ViewGroup mMoveView;

    /**
     * 末次滑动的itemView
     */
    private ViewGroup mLastView;

    /**
     * itemView中菜单控件宽度
     */
    private int mMenuWidth;

    private VelocityTracker mVelocity;

    /**
     * 触碰时的首个横坐标
     */
    private int mFirstX;

    /**
     * 触碰时的首个纵坐标
     */
    private int mFirstY;

    /**
     * 触碰末次的横坐标
     */
    private int mLastX;

    /**
     * 最小滑动距离
     */
    private int mTouchSlop;

    private Scroller mScroller;

    /**
     * 是否正在水平滑动
     */
    private boolean mMoving;


    public ChildSlideRecyclerView(Context context) {
        super(context);
        init();
    }

    public ChildSlideRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ChildSlideRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        mScroller = new Scroller(getContext());
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        int x = (int) e.getX();
        int y = (int) e.getY();
        addVelocityEvent(e);
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //若Scroller处于动画中，则终止动画
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }
                mFirstX = x;
                mFirstY = y;
                mLastX = x;
                //获取点击区域所在的itemView
                mMoveView = (ViewGroup) findChildViewUnder(x, y);
                //在点击区域以外的itemView开着菜单，则关闭菜单
                if (mLastView != null && mLastView != mMoveView && mLastView.getScrollX() != 0) {
                    closeMenu();
                }
                //获取itemView中菜单的宽度（规定itemView中为两个子View）
                if (mMoveView != null && mMoveView.getChildCount() == 2) {
                    View view = mMoveView.getChildAt(1);
                    MarginLayoutParams layoutParams =
                            (MarginLayoutParams) view.getLayoutParams();
                    mMenuWidth = view.getWidth() + layoutParams.leftMargin;
//                    PFLog.i(TAG, "找到mMenuWidth："+mMenuWidth);
                }
                else {
                    mMenuWidth = -1;
                    //指定宽度
//                    mMenuWidth = DpAndPxUtils.dp2px(getContext(), 189);
//                    PFLog.i(TAG, "固定mMenuWidth："+mMenuWidth);
                }


                //通知父层ViewGroup不要拦截点击事件
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                mVelocity.computeCurrentVelocity(1000);
                int velocityX = (int) Math.abs(mVelocity.getXVelocity());
                int velocityY = (int) Math.abs(mVelocity.getYVelocity());
                int moveX = Math.abs(x - mFirstX);
                int moveY = Math.abs(y - mFirstY);
                //满足如下条件其一则判定为水平滑动：
                //1、水平速度大于竖直速度,且水平速度大于最小速度
                //2、水平位移大于竖直位移,且大于最小移动距离
                //必需条件：itemView菜单栏宽度大于0，且recyclerView处于静止状态（即并不在竖直滑动和拖拽）
                boolean isHorizontalMove =
                        (Math.abs(velocityX) >= MINIMUM_VELOCITY && velocityX > velocityY || moveX > moveY
                                && moveX > mTouchSlop) && mMenuWidth > 0 && getScrollState() == 0;
                if (isHorizontalMove) {
                    //设置其已处于水平滑动状态，并拦截事件
                    mMoving = true;
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                releaseVelocity();
                //itemView以及其子view触发触碰事件(点击、长按等)，菜单未关闭则直接关闭
                closeMenuNow();

                getParent().requestDisallowInterceptTouchEvent(false);
                break;
            default:
                break;
        }
        return super.onInterceptTouchEvent(e);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        int x = (int) e.getX();
        int y = (int) e.getY();
        addVelocityEvent(e);
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastY = y;
                //不允许父View拦截事件
                getParent().requestDisallowInterceptTouchEvent(true);

                break;
            case MotionEvent.ACTION_MOVE:
                //若已处于水平滑动状态，则随手指滑动，否则进行条件判断
                if (mMoving) {
                    int dx = mLastX - x;
                    //让itemView在规定区域随手指移动
                    if (mMoveView.getScrollX() + dx >= 0 && mMoveView.getScrollX() + dx <= mMenuWidth) {
                        mMoveView.scrollBy(dx, 0);
                    }
                    mLastX = x;
                    return true;
                }
                else {
                    mVelocity.computeCurrentVelocity(1000);
                    int velocityX = (int) Math.abs(mVelocity.getXVelocity());
                    int velocityY = (int) Math.abs(mVelocity.getYVelocity());
                    int moveX = Math.abs(x - mFirstX);
                    int moveY = Math.abs(y - mFirstY);
                    //根据水平滑动条件判断，是否让itemView跟随手指滑动
                    //这里重新判断是避免itemView中不拦截ACTION_DOWN事件，则后续ACTION_MOVE并不会走onInterceptTouchEvent()方法
                    boolean isHorizontalMove =
                            (Math.abs(velocityX) >= MINIMUM_VELOCITY && velocityX > velocityY
                                    || moveX > moveY && moveX > mTouchSlop) && mMenuWidth > 0 && getScrollState() == 0;
                    if (isHorizontalMove) {
                        int dx = mLastX - x;
                        try {
                            //让itemView在规定区域随手指移动
                            if (mMoveView.getScrollX() + dx >= 0 && mMoveView.getScrollX() + dx <= mMenuWidth) {
                                mMoveView.scrollBy(dx, 0);
                            }
                            mLastX = x;
                            //设置正处于水平滑动状态
                            mMoving = true;
                            return true;
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }


                float nowY = e.getY();
                isIntercept(nowY);
                if (isBottomToTop||isTopToBottom){
                    getParent().requestDisallowInterceptTouchEvent(false);
                    return false;
                }else{
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                mLastY = nowY;

                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (mMoving) {
                    //先前没结束的动画终止，并直接到终点
                    if (!mScroller.isFinished()) {
                        mScroller.abortAnimation();
                        mLastView.scrollTo(mScroller.getFinalX(), 0);
                    }
                    mMoving = false;
                    //已放手，即现滑动的itemView成了末次滑动的itemView
                    mLastView = mMoveView;
                    mVelocity.computeCurrentVelocity(1000);
                    int scrollX = mLastView.getScrollX();
                    //若速度大于正方向最小速度，则关闭菜单栏；若速度小于反方向最小速度，则打开菜单栏
                    //若速度没到判断条件，则对菜单显示的宽度进行判断打开/关闭菜单
                    if (mVelocity.getXVelocity() >= MINIMUM_VELOCITY) {
                        mScroller.startScroll(scrollX, 0, -scrollX, 0, Math.abs(scrollX));
                    }
                    else if (mVelocity.getXVelocity() <= -MINIMUM_VELOCITY) {
                        int dx = mMenuWidth - scrollX;
                        mScroller.startScroll(scrollX, 0, dx, 0, Math.abs(dx));
                    }
                    else if (scrollX > mMenuWidth / 2) {
                        int dx = mMenuWidth - scrollX;
                        mScroller.startScroll(scrollX, 0, dx, 0, Math.abs(dx));
                    }
                    else {
                        mScroller.startScroll(scrollX, 0, -scrollX, 0, Math.abs(scrollX));
                    }
                    invalidate();
                }
                else if (mLastView != null && mLastView.getScrollX() != 0) {
                    //若不是水平滑动状态，菜单栏开着则关闭
                    closeMenu();
                }
                releaseVelocity();

                getParent().requestDisallowInterceptTouchEvent(false);
                break;
            default:
                break;
        }
        return super.onTouchEvent(e);
    }


    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            if (isInWindow(mLastView)) {
                mLastView.scrollTo(mScroller.getCurrX(), 0);
                invalidate();
            }
            else {
                //若处于动画的itemView滑出屏幕，则终止动画，并让其到达结束点位置
                mScroller.abortAnimation();
                mLastView.scrollTo(mScroller.getFinalX(), 0);
            }
        }
    }

    /**
     * 使用Scroller关闭菜单栏
     */
    public void closeMenu() {
        mScroller.startScroll(mLastView.getScrollX(), 0, -mLastView.getScrollX(), 0, 500);
        invalidate();
    }

    /**
     * 即刻关闭菜单栏
     */
    public void closeMenuNow() {
        if (mLastView != null && mLastView.getScrollX() != 0) {
            mLastView.scrollTo(0, 0);
        }
    }

    /**
     * 获取VelocityTracker实例，并为其添加事件
     *
     * @param e 触碰事件
     */
    private void addVelocityEvent(MotionEvent e) {
        if (mVelocity == null) {
            mVelocity = VelocityTracker.obtain();
        }
        mVelocity.addMovement(e);
    }

    /**
     * 释放VelocityTracker
     */
    private void releaseVelocity() {
        if (mVelocity != null) {
            mVelocity.clear();
            mVelocity.recycle();
            mVelocity = null;
        }
    }

    /**
     * 判断该itemView是否显示在屏幕内
     *
     * @param view itemView
     * @return isInWindow
     */
    private boolean isInWindow(View view) {
        if (getLayoutManager() instanceof LinearLayoutManager) {
            LinearLayoutManager manager = (LinearLayoutManager) getLayoutManager();
            int firstPosition = manager.findFirstVisibleItemPosition();
            int lastPosition = manager.findLastVisibleItemPosition();
            int currentPosition = manager.getPosition(view);
            return currentPosition >= firstPosition && currentPosition <= lastPosition;
        }
        return true;
    }

    //记录上次手指位置
    private float mLastY = 0f;
    private int lastVisibleItemPosition;
    private int firstVisibleItemPosition;
    private boolean isTopToBottom = false;
    private boolean isBottomToTop = false;

    private void isIntercept(float nowY){

        isTopToBottom = false;
        isBottomToTop = false;

        RecyclerView.LayoutManager layoutManager = getLayoutManager();
//        if (layoutManager instanceof GridLayoutManager) {
//            //得到当前界面，最后一个子视图对应的position
//            lastVisibleItemPosition = ((GridLayoutManager) layoutManager)
//                    .findLastVisibleItemPosition();
//            //得到当前界面，第一个子视图的position
//            firstVisibleItemPosition = ((GridLayoutManager) layoutManager)
//                    .findFirstVisibleItemPosition();
//        }else
        if (layoutManager instanceof LinearLayoutManager){
            //得到当前界面，最后一个子视图对应的position
            lastVisibleItemPosition = ((LinearLayoutManager) layoutManager)
                    .findLastVisibleItemPosition();
            //得到当前界面，第一个子视图的position
            firstVisibleItemPosition = ((LinearLayoutManager) layoutManager)
                    .findFirstVisibleItemPosition();
        }
        //得到当前界面可见数据的大小
        int visibleItemCount = layoutManager.getChildCount();
        //得到RecyclerView对应所有数据的大小
        int totalItemCount = layoutManager.getItemCount();
//        Log.d("nestScrolling","onScrollStateChanged");
        if (visibleItemCount>0) {

            if (lastVisibleItemPosition == totalItemCount - 1) {
                //最后视图对应的position等于总数-1时，说明上一次滑动结束时，触底了
//                Log.d("nestScrolling", "触底了");

                /**
                 * 注意这里有非常关键的两点，也是我修改完善之前哥们博客的有坑的两点，
                 * 第一点是canScrollVertically传的正负值问题，判断向上用正值1，向下则反过来用负值-1，
                 * 第二点是canScrollVertically返回值的问题，true时是代表可以滑动，false时才代表划到顶部或者底部不可以再滑动了，所以这个判断前要加逻辑非!运算符
                 * 补充了这两点基本效果就很完美了。
                 */
                if (!this.canScrollVertically(1) && nowY < mLastY) {
                    // 不能向上滑动
//                    Log.d("nestScrolling", "不能向上滑动");
                    isBottomToTop = true;
                } else {
//                    Log.d("nestScrolling", "向下滑动");
                }
            } else if (firstVisibleItemPosition == 0) {
                //第一个视图的position等于0，说明上一次滑动结束时，触顶了
//                Log.d("nestScrolling", "触顶了");
                if (!this.canScrollVertically(-1) && nowY > mLastY) {
                    // 不能向下滑动
//                    Log.d("nestScrolling", "不能向下滑动");
                    isTopToBottom = true;
                } else {
//                    Log.d("nestScrolling", "向上滑动");
                }
            }
        }
    }

}
