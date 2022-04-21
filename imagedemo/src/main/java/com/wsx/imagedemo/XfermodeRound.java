package com.wsx.imagedemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;

/**
 * @author:wangshouxue
 * @date:2022/4/20 下午5:01
 * @description:类作用
 */
public class XfermodeRound extends AppCompatImageView {
    private Paint mPaint;
    private Paint mDSTPaint;
    private BitmapShader mSharder;
    private Path mPath;
    private Canvas mCanvas;

    private PorterDuffXfermode mMode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);

    public XfermodeRound(Context context) {
        this(context, null);
    }

    public XfermodeRound(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XfermodeRound(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setAntiAlias(true);
        mDSTPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mDSTPaint.setAntiAlias(true);
        mPath = new Path();
        setLayerType(LAYER_TYPE_SOFTWARE, mPaint);//clipPath 不支持硬件加速

    }

    @Override
    protected void onDraw(Canvas canvas) {
        mCanvas = canvas;
//        drawRoundImageByPath();
//        super.onDraw(canvas);
        drawRoundImageByShader();//AppCompatImageView会有阴影
//        drawRoundImageByXfermode();//AppCompatImageView会有阴影

    }
    //通过路径来实现圆角,注意super.onDraw(canvas)调用位置
    private void drawRoundImageByPath() {
//        mPath.addCircle(getMeasuredWidth() / 2, getMeasuredHeight() / 2, 90, Path.Direction.CCW);
        mPath.addRoundRect(new RectF(0,0,getMeasuredWidth(),getMeasuredHeight()),100, 100, Path.Direction.CCW);
//        mCanvas.setDrawFilter(new PaintFlagsDrawFilter(0,Paint.ANTI_ALIAS_FLAG|Paint.FILTER_BITMAP_FLAG));//要不要都行
        //由于我们只是需要将画布裁剪成圆形，无需考虑两种图形相交情况，所以OP参数任意
        mCanvas.clipPath(mPath);
    }

    //shader实现圆角图片，记得屏蔽super.onDraw(canvas)
    private void drawRoundImageByShader() {
        if (mSharder == null) {
            mSharder = new BitmapShader(getBitmapBeforeDraw(), Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        }
        mPaint.setShader(mSharder);
//        mCanvas.drawCircle(getMeasuredWidth() / 2, getMeasuredHeight() / 2, 180, mPaint);
        mCanvas.drawRoundRect(new RectF(0,0,getWidth(),getHeight()),80, 80,mPaint);

    }

    private void drawRoundImageByXfermode() {

        int saveLayerId = mCanvas.saveLayer(0, 0, mCanvas.getWidth(), mCanvas.getHeight(), null, Canvas.ALL_SAVE_FLAG);
        //先画DST
        mCanvas.drawBitmap(getBitmapBeforeDraw(), 0, 0, mPaint);
        //画SRC
        Bitmap bitmap = Bitmap.createBitmap(mCanvas.getWidth(), mCanvas.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas ca = new Canvas(bitmap);
//        ca.drawCircle(mCanvas.getWidth() / 2, mCanvas.getHeight() / 2, 180, mPaint);
        ca.drawRoundRect(new RectF(0,0,getWidth(),getHeight()),100, 100,  mPaint);
        //相交取下半部分
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        mCanvas.drawBitmap(bitmap, 0, 0, mPaint);
        mPaint.setXfermode(null);

        mCanvas.restoreToCount(saveLayerId);
    }


    public Bitmap getBitmapBeforeDraw() {
        BitmapDrawable b = (BitmapDrawable) this.getDrawable();
        return Bitmap.createScaledBitmap(b.getBitmap(), getMeasuredWidth(), getMeasuredHeight(), false);
    }

}
