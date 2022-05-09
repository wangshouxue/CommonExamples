package com.wsx.mydemo;

/**
 * @author:wangshouxue
 * @date:2022/5/9 下午3:07
 * @description:类作用
 */

import static com.wsx.mydemo.MarqueeTextView.HORIZONTAL;

import android.annotation.SuppressLint;
import android.view.Choreographer;
import java.lang.ref.WeakReference;
import java.lang.reflect.Method;

public final class Marquee {
    // 修改此字段设置重跑时间间隔 - 对应不足点2
    private static int MARQUEE_DELAY = 1500;
    // 修改此字段设置跑动速度 - 对应不足点1
    private static int MARQUEE_DP_PER_SECOND = 10;

    private static final byte MARQUEE_STOPPED = 0x0;
    private static final byte MARQUEE_STARTING = 0x1;
    private static final byte MARQUEE_RUNNING = 0x2;

    private static final String METHOD_GET_FRAME_TIME = "getFrameTime";

    private final WeakReference<MarqueeTextView> mView;
    private final Choreographer mChoreographer;

    private byte mStatus = MARQUEE_STOPPED;
    private final float mPixelsPerSecond;
    private float mMaxScroll;
    private float mMaxFadeScroll;
    private float mGhostStart;
    private float mGhostOffset;
    private float mFadeStop;
    private int mRepeatLimit;

    private float mScroll;
    private long mLastAnimationMs;

    Marquee(MarqueeTextView v) {
        this(v,MARQUEE_DP_PER_SECOND,MARQUEE_DELAY);
    }
    Marquee(MarqueeTextView v,int run_speed,int stayTime) {
        MARQUEE_DP_PER_SECOND=run_speed;
        MARQUEE_DELAY=stayTime;
        final float density = v.getContext().getResources().getDisplayMetrics().density;
        mPixelsPerSecond = MARQUEE_DP_PER_SECOND * density;
        mView = new WeakReference<>(v);
        mChoreographer = Choreographer.getInstance();
    }

    private final Choreographer.FrameCallback mTickCallback = frameTimeNanos -> tick();

    private final Choreographer.FrameCallback mStartCallback = new Choreographer.FrameCallback() {
        @Override
        public void doFrame(long frameTimeNanos) {
            mStatus = MARQUEE_RUNNING;
            mLastAnimationMs = getFrameTime();
            tick();
        }
    };

    /**
     * `getFrameTime`是隐藏api，此处使用反射调用，高系统版本可能失效，可使用某些方案绕过此限制
     */
    @SuppressLint("PrivateApi")
    private long getFrameTime() {
        try {
            Class<? extends Choreographer> clz = mChoreographer.getClass();
            Method getFrameTime = clz.getDeclaredMethod(METHOD_GET_FRAME_TIME);
            getFrameTime.setAccessible(true);
            return (long) getFrameTime.invoke(mChoreographer);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    // 帧率回调，用于跑马灯重新跑动
    private final Choreographer.FrameCallback mRestartCallback = new Choreographer.FrameCallback() {
        @Override
        public void doFrame(long frameTimeNanos) {
            if (mStatus == MARQUEE_RUNNING) {
                if (mRepeatLimit >= 0) {
                    mRepeatLimit--;
                }
                start(mRepeatLimit);
            }
        }
    };

    void tick() {
        if (mStatus != MARQUEE_RUNNING) {
            return;
        }

        mChoreographer.removeFrameCallback(mTickCallback);

        final MarqueeTextView textView = mView.get();
        if (textView != null && (textView.isFocused() || textView.isSelected() || textView.isMarquee())) {
            long currentMs = getFrameTime();
            long deltaMs = currentMs - mLastAnimationMs;
            mLastAnimationMs = currentMs;
            float deltaPx = deltaMs / 1000F * mPixelsPerSecond;
            mScroll += deltaPx;
            if (mScroll > mMaxScroll) {
                mScroll = mMaxScroll;
                mChoreographer.postFrameCallbackDelayed(mRestartCallback, MARQUEE_DELAY);
//                mChoreographer.postFrameCallback(mRestartCallback);
            } else {
                mChoreographer.postFrameCallback(mTickCallback);
            }
            textView.invalidate();
        }
    }

    void stop() {
        mStatus = MARQUEE_STOPPED;
        mChoreographer.removeFrameCallback(mStartCallback);
        mChoreographer.removeFrameCallback(mRestartCallback);
        mChoreographer.removeFrameCallback(mTickCallback);
        resetScroll();
    }

    private void resetScroll() {
        mScroll = 0.0F;
        final MarqueeTextView textView = mView.get();
        if (textView != null) textView.invalidate();
    }

    void start(int repeatLimit) {
        if (repeatLimit == 0) {
            stop();
            return;
        }
        mRepeatLimit = repeatLimit;
        final MarqueeTextView textView = mView.get();
        if (textView != null && textView.getLayout() != null) {
            mStatus = MARQUEE_STARTING;
            mScroll = 0.0F;

            // 分别计算左右和上下跑动所需的数据
            if (textView.getOrientation() == HORIZONTAL) {
                int viewWidth = textView.getWidth() - textView.getCompoundPaddingLeft() -
                        textView.getCompoundPaddingRight();
                float lineWidth = textView.getLayout().getLineWidth(0);
                float gap = viewWidth / 3.0F;
                mGhostStart = lineWidth - viewWidth + gap;
                mMaxScroll = mGhostStart + viewWidth;
                mGhostOffset = lineWidth + gap;
                mFadeStop = lineWidth + viewWidth / 6.0F;
                mMaxFadeScroll = mGhostStart + lineWidth + lineWidth;
            } else {
                int viewHeight = textView.getHeight() - textView.getCompoundPaddingTop() -
                        textView.getCompoundPaddingBottom();
                float textHeight = textView.getLayout().getHeight();
                float gap = viewHeight / 3.0F;
                mGhostStart = textHeight - viewHeight + gap;
                mMaxScroll = mGhostStart + viewHeight;
                mGhostOffset = textHeight + gap;
                mFadeStop = textHeight + viewHeight / 6.0F;
                mMaxFadeScroll = mGhostStart + textHeight + textHeight;
            }

            textView.invalidate();
            mChoreographer.postFrameCallback(mStartCallback);
        }
    }

    float getGhostOffset() {
        return mGhostOffset;
    }

    float getScroll() {
        return mScroll;
    }

    float getMaxFadeScroll() {
        return mMaxFadeScroll;
    }

    boolean shouldDrawLeftFade() {
        return mScroll <= mFadeStop;
    }

    boolean shouldDrawTopFade() {
        return mScroll <= mFadeStop;
    }

    boolean shouldDrawGhost() {
        return mStatus == MARQUEE_RUNNING && mScroll > mGhostStart;
    }

    boolean isRunning() {
        return mStatus == MARQUEE_RUNNING;
    }

    boolean isStopped() {
        return mStatus == MARQUEE_STOPPED;
    }
}

