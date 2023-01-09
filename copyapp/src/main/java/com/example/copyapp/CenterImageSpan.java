package com.example.copyapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.style.ImageSpan;

import androidx.annotation.NonNull;

/**
 * @Description: 类作用描述
 */
public class CenterImageSpan extends ImageSpan {
    public int bgColor= Color.TRANSPARENT;
    public CenterImageSpan(@NonNull Drawable drawable) {
        super(drawable);
    }

    /**
     * 如果是gif请使用此方法进行初始化，否则在28以上系统会出现图片大小异常的问题
     * @param context
     * @param bitmap
     */
    public CenterImageSpan(@NonNull Context context, @NonNull Bitmap bitmap) {
        super(context, bitmap);
    }

    @Override
    public int getSize(Paint paint, CharSequence text, int start, int end,
                       Paint.FontMetricsInt fm) {
        Drawable d = getDrawable();
        Rect rect = d.getBounds();
        d.setBounds(rect);
        if (fm != null) {
            Paint.FontMetricsInt fmPaint = paint.getFontMetricsInt();
            int fontHeight = fmPaint.bottom - fmPaint.top;
            int drHeight = rect.bottom - rect.top;

            int top = drHeight / 2 - fontHeight / 4;
            int bottom = drHeight / 2 + fontHeight / 4;

            fm.ascent = -bottom;
            fm.top = -bottom;
            fm.bottom = top;
            fm.descent = top;
        }
        return rect.right;
    }

    @Override
    public void draw(Canvas canvas, CharSequence text, int start, int end,
                     float x, int top, int y, int bottom, Paint paint) {
        Drawable drawable = getDrawable();
        canvas.save();
        //局部复制时表情背景
        paint.setColor(bgColor);
        canvas.drawRect(x, top, x + drawable.getBounds().right, bottom, paint);
        //获取画笔的文字绘制时的具体测量数据
        Paint.FontMetricsInt fm = paint.getFontMetricsInt();

        int transY;
        transY = ((y + fm.descent) + (y + fm.ascent)) / 2 - drawable.getBounds().bottom / 2;

        canvas.translate(x, transY);
        drawable.draw(canvas);
        canvas.restore();
    }
}
