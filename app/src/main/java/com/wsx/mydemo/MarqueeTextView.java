package com.wsx.mydemo;

/**
 * @author:wangshouxue
 * @date:2022/5/9 下午2:55
 * @description:实现跑马灯首尾相接的效果
 * https://juejin.cn/post/7094205592402657310
 */

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class MarqueeTextView extends AppCompatTextView {

    private static final int DEFAULT_BG_COLOR = Color.parseColor("#FFffffff");

    @IntDef({HORIZONTAL, VERTICAL})
    @Retention(RetentionPolicy.SOURCE)
    public @interface OrientationMode {
    }

    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;

    private Marquee mMarquee;
    private boolean mRestartMarquee;
    private boolean isMarquee;

    private int mOrientation;
    private int runSpeed;
    private int stayTime;//重跑时间间隔

    public MarqueeTextView(@NonNull Context context) {
        this(context, null);
    }

    public MarqueeTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MarqueeTextView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MarqueeTextView, defStyleAttr, 0);
        mOrientation = ta.getInt(R.styleable.MarqueeTextView_orientation, HORIZONTAL);
        runSpeed=ta.getInt(R.styleable.MarqueeTextView_run_speed,10);
        stayTime=ta.getInt(R.styleable.MarqueeTextView_stay_time,1000);
        isMarquee=ta.getBoolean(R.styleable.MarqueeTextView_isMarquee,false);
        ta.recycle();
    }
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        if (mOrientation == HORIZONTAL) {
            if (getWidth() > 0) {
                mRestartMarquee = true;
            }
        } else {
            if (getHeight() > 0) {
                mRestartMarquee = true;
            }
        }
    }
    private void restartMarqueeIfNeeded() {
        if (mRestartMarquee) {
            mRestartMarquee = false;
            startMarquee();
        }
    }
    public void setMarquee(boolean marquee) {
        boolean wasStart = isMarquee();

        isMarquee = marquee;

        if (wasStart != marquee) {
            if (marquee) {
                startMarquee();
            } else {
                stopMarquee();
            }
        }
    }
    public void setOrientation(@OrientationMode int orientation) {
        mOrientation = orientation;
    }

    public int getOrientation() {
        return mOrientation;
    }

    public boolean isMarquee() {
        return isMarquee;
    }

    private void stopMarquee() {
        if (mOrientation == HORIZONTAL) {
            setHorizontalFadingEdgeEnabled(false);
        } else {
            setVerticalFadingEdgeEnabled(false);
        }

        requestLayout();
        invalidate();

        if (mMarquee != null && !mMarquee.isStopped()) {
            mMarquee.stop();
        }
    }

    private void startMarquee() {
        if (canMarquee()) {

            if (mOrientation == HORIZONTAL) {
                setHorizontalFadingEdgeEnabled(true);
            } else {
                setVerticalFadingEdgeEnabled(true);
            }

            if (mMarquee == null) {
                mMarquee = new Marquee(this,runSpeed,stayTime);
            }
            mMarquee.start(-1);
        }
    }

    private boolean canMarquee() {
        if (mOrientation == HORIZONTAL) {
            int viewWidth = getWidth() - getCompoundPaddingLeft() -
                    getCompoundPaddingRight();
            float lineWidth = getLayout().getLineWidth(0);
            return (mMarquee == null || mMarquee.isStopped())
                    && (isFocused() || isSelected() || isMarquee())
                    && viewWidth > 0
                    && lineWidth > viewWidth;
        } else {
            int viewHeight = getHeight() - getCompoundPaddingTop() -
                    getCompoundPaddingBottom();
            float textHeight = getLayout().getHeight();
            return (mMarquee == null || mMarquee.isStopped())
                    && (isFocused() || isSelected() || isMarquee())
                    && viewHeight > 0
                    && textHeight > viewHeight;
        }
    }

    /**
     * 仿照TextView#onDraw()方法
     */
    @Override
    protected void onDraw(Canvas canvas) {
        restartMarqueeIfNeeded();
//        super.onDraw(canvas);
//        // 再次绘制背景色，覆盖下面由TextView绘制的文本，视情况可以不调用`super.onDraw(canvas);`
//        // 如果没有背景色则使用默认颜色
//        Drawable background = getBackground();
//        if (background != null) {
//            background.draw(canvas);
//        } else {
//            canvas.drawColor(DEFAULT_BG_COLOR);
//        }

        canvas.save();

        canvas.translate(0, 0);

        // 实现左右跑马灯
        if (mOrientation == HORIZONTAL) {
            if (mMarquee != null && mMarquee.isRunning()) {
                final float dx = -mMarquee.getScroll();
                canvas.translate(dx, 0.0F);
            }

            getLayout().draw(canvas, null, null, 0);

            if (mMarquee != null && mMarquee.shouldDrawGhost()) {
                final float dx = mMarquee.getGhostOffset();
                canvas.translate(dx, 0.0F);
                getLayout().draw(canvas, null, null, 0);
            }
        } else {
            // 实现上下跑马灯
            if (mMarquee != null && mMarquee.isRunning()) {
                final float dy = -mMarquee.getScroll();
                canvas.translate(0.0F, dy);
            }
            getLayout().draw(canvas, null, null, 0);

            // 判断是否可以绘制尾部文本
            if (mMarquee != null && mMarquee.shouldDrawGhost()) {
                final float dy = mMarquee.getGhostOffset();
                canvas.translate(0.0F, dy);
                getLayout().draw(canvas, null, null, 0);
            }
        }

        canvas.restore();
    }
}
