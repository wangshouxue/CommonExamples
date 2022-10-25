package com.wsx.mydemo;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @Description: 滑动验证效果
 */
public class MyCheckView extends View {

    private boolean isBlockArea = false;
    private boolean isMove = false;
    private boolean isFinish = false;
    private boolean isDown = false;
    private int mRight;
    private int startX = 0;

    /**
     * 滑块边距
     */
    private final int blockSize = 10;

    /**
     * 相关属性
     */
    private int m_blockColor = Color.WHITE;//默认滑块颜色
    private int m_blockShadowLayer = Color.parseColor("#D8D8D8");//默认滑块阴影色
    private int m_proColor = Color.parseColor("#ff3159");//默认进度条颜色
    private int m_recColor = Color.parseColor("#D8D8D8");//默认矩形颜色
    private int blockDrawableId;//默认滑块背景图

    /**
     * 矩形画笔
     */
    private final Paint recPaint = new Paint();

    /**
     * 进度条画笔
     */
    private final Paint proPaint = new Paint();

    /**
     * 滑块画笔
     */
    private final Paint blockPaint = new Paint();

    /**
     * 圆角角度
     */
    private int circleSize = 40;

    /**
     * 记录父控件宽度
     */
    private float parentWidth = 0f;

    /**
     * 矩形高度
     */
    private int proHeight;

    /**
     * 默认高度
     */
    private final int DEFAULT_HEIGHT = 90;

    /**
     * 滑块宽度
     */
    private final int blockWidth = 120;

    /**
     * 手指落下位置
     */
    private int dX;

    /**
     * 偏移距离
     */
    private int mX;

    /**
     * 接口回调
     */
    private FinishListener finishListener;

    public void setFinishListener(FinishListener finishListener) {
        this.finishListener = finishListener;
    }

    public MyCheckView(@NonNull Context context) {
        super(context);
        init();
    }

    public MyCheckView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initParams(context, attrs);
        init();
    }

    public MyCheckView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initParams(context, attrs);
        init();
    }

    /**
     * 初始化自定义属性
     *
     * @param context 上下文
     * @param attrs   属性参数
     */
    private void initParams(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyCheckView);
        if (typedArray != null) {
            //获取滑块背景图片
            blockDrawableId = typedArray.getResourceId(R.styleable.MyCheckView_m_blockBg, -1);
            //获取滑块颜色
            m_blockColor = typedArray.getColor(R.styleable.MyCheckView_m_blockColor, m_blockColor);
            //滑块阴影色
            m_blockShadowLayer = typedArray.getColor(R.styleable.MyCheckView_m_blockColor, m_blockShadowLayer);
            //进度条颜色
            m_proColor = typedArray.getColor(R.styleable.MyCheckView_m_blockColor, m_proColor);
            //矩形颜色
            m_recColor = typedArray.getColor(R.styleable.MyCheckView_m_blockColor, m_recColor);
            //圆角角度值
            circleSize = typedArray.getInt(R.styleable.MyCheckView_m_blockColor, circleSize);
            typedArray.recycle();
        }
    }

    /**
     * 初始化画笔
     */
    private void init() {
        //设置矩形背景色
        recPaint.setColor(m_recColor);
        recPaint.setStyle(Paint.Style.FILL);
        recPaint.setAntiAlias(true);

        //设置进度条背景色
        proPaint.setColor(m_proColor);
        proPaint.setStyle(Paint.Style.FILL);
        recPaint.setAntiAlias(true);

        //判断是否使用了背景图
        if (blockDrawableId != -1) {
            //设置滑块背景色
            blockPaint.setColor(m_blockColor);
            blockPaint.setStyle(Paint.Style.FILL_AND_STROKE);
            blockPaint.setAntiAlias(true);
            //给滑块添加阴影
            blockPaint.setShadowLayer(35, 1, 1, m_blockShadowLayer);
        } else {
            blockPaint.setStyle(Paint.Style.FILL_AND_STROKE);
            blockPaint.setAntiAlias(true);
        }
    }

    public void blockReset() {
        mX = 0;
        reset(startX);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        parentWidth = getMyWSize(widthMeasureSpec);
        proHeight = getMyHSize(heightMeasureSpec);
        setMeasuredDimension((int) parentWidth, proHeight);

    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制矩形
        RectF rectF = new RectF();
        rectF.left = 1;
        rectF.right = parentWidth - 1;
        rectF.top = 1;
        rectF.bottom = proHeight - 1;
        //绘制圆角矩形
        canvas.drawRoundRect(rectF, circleSize, circleSize, recPaint);

        if (isMove || isDown) {
            //绘制进度条
            RectF rectP = new RectF();
            rectP.left = 1;
            rectP.right = blockWidth + blockSize + mX;
            rectP.top = 1;
            rectP.bottom = proHeight - 1;
            canvas.drawRoundRect(rectP, circleSize, circleSize, proPaint);
        }

        //绘制滑块
        RectF rectB = new RectF();
        rectB.left = blockSize + mX;
        rectB.right = blockWidth + mX;
        rectB.top = blockSize;
        rectB.bottom = proHeight - blockSize;

        mRight = (int) rectB.right;

        //判断是否使用了背景图
        if (blockDrawableId != -1) {
            //绘制背景图
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), blockDrawableId);
            Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
            canvas.drawBitmap(bitmap, rect, rectB, blockPaint);
        } else {
            //绘制滑块
            canvas.drawRoundRect(rectB, circleSize, circleSize, blockPaint);
        }

    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                dX = (int) event.getX();
                int dY = (int) event.getY();
                int top = getTop();
                int bottom = getBottom();
                //判断区域是否为滑块
                if (dX > blockSize && dX < blockWidth && dY > blockSize && dY < (bottom - top)) {
                    isBlockArea = true;
                }
                return true;
            case MotionEvent.ACTION_MOVE:

                if (isBlockArea) {
                    mX = (int) event.getX() - dX;
                    //设置范围
                    if ((blockWidth + blockSize + mX) < parentWidth && (blockSize + mX) >= blockSize) {
                        //计算偏移量
                        invalidate();
                        startX = (int) event.getX() - blockWidth / 2;
                    } else if ((blockSize + mX) >= blockSize) {
                        //超出复位
                        mX = (int) parentWidth - blockWidth - blockSize;
                        invalidate();
                    }
                    isMove = true;
                }
                return true;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                isBlockArea = false;
                isFinish = mRight == parentWidth - blockSize;
                if (isFinish) {
                    //监听回调
                    if (finishListener != null) {
                        finishListener.finish();
                    }
                }
                if (!isFinish && isMove) {
                    reset(startX);
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    /**
     * 松手回弹动画效果
     */
    private void reset(int start) {

        ValueAnimator valueAnimator = ValueAnimator.ofInt(start, 0);
        valueAnimator.setDuration(500);
        valueAnimator.start();
        valueAnimator.addUpdateListener(animation -> {
            mX = (int) animation.getAnimatedValue();
            //刷新
            invalidate();
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                isMove = false;
                isFinish = false;
                startX = 0;
            }
        });
    }

    /**
     * 获取测量大小
     */
    private int getMyWSize(int measureSpec) {
        int result;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;//确切大小,所以将得到的尺寸给view
        } else if (specMode == MeasureSpec.AT_MOST) {
            result = Math.min(getScreenWidth() - 20, specSize);
        } else {
            result = getScreenWidth() - 20;
        }
        return result;
    }

    /**
     * 获取测量大小
     */
    private int getMyHSize(int measureSpec) {
        int result;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;//确切大小,所以将得到的尺寸给view
        } else if (specMode == MeasureSpec.AT_MOST) {
            result = Math.min(DEFAULT_HEIGHT, specSize);
        } else {
            result = DEFAULT_HEIGHT - 20;
        }
        return result;
    }

    /**
     * 获取屏幕宽度
     */
    private int getScreenWidth() {
        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    /**
     * 接口回调方法
     */
    public interface FinishListener {
        void finish();
    }

}
