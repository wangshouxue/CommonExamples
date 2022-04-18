//package com.wsx.imagedemo;
//
//import android.content.Context;
//import android.graphics.Bitmap;
//import android.graphics.BitmapShader;
//import android.graphics.Canvas;
//import android.graphics.RectF;
//import android.graphics.Shader;
//import android.util.AttributeSet;
//import android.widget.ImageView;
//
//import androidx.annotation.Nullable;
//
///**
// * @author:wangshouxue
// * @date:2022/4/18 下午4:55
// * @description:类作用
// */
//public class RoundImage extends ImageView {
//    public RoundImage(Context context) {
//        super(context);
//    }
//
//    public RoundImage(Context context, @Nullable AttributeSet attrs) {
//        super(context, attrs);
//    }
//
//    public RoundImage(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//    }
//
//    @Override
//    protected void onDraw(Canvas canvas) {
//        if (getDrawable() == null){
//            return;
//        }
//        Bitmap bitmap = drawableToBitamp(getDrawable());
//        mBitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
//        float scale = 1.0f;
//        if (!(bitmap.getWidth() == getWidth() && bitmap.getHeight() == getHeight()))
//        {
//            // 如果图片的宽或者高与view的宽高不匹配，计算出需要缩放的比例；缩放后的图片的宽高，一定要大于我们view的宽高；所以我们这里取大值；
//            scale = Math.max(getWidth() * 1.0f / bitmap.getWidth(),
//                    getHeight() * 1.0f / bitmap.getHeight());
//        }
//        // shader的变换矩阵，我们这里主要用于放大或者缩小
//        mMatrix.setScale(scale, scale);
//        // 设置变换矩阵
//        mBitmapShader.setLocalMatrix(mMatrix);
//        // 设置shader
//        mPaint.setShader(mBitmapShader);
//        canvas.drawRoundRect(new RectF(0,0,getWidth(),getHeight()), mBorderRadius, mBorderRadius,
//                mPaint);
//    }
//}
