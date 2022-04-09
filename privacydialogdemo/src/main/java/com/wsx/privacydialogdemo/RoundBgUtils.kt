package com.wsx.privacydialogdemo

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.View

/**
 * @author:wangshouxue
 * @date:2022/4/7 上午8:49
 * @description:view的圆角背景实现
 */

/**
 * 设置圆角背景，适用于4个角一样大
 * 背景色
 * 圆角大小
 */
@JvmOverloads
fun View.setRoundRectBg(color: Int = Color.WHITE, cornerRadius: Int = 8) {
    background = GradientDrawable().apply {
        setColor(color)
        setCornerRadius(cornerRadius.toFloat())
    }
}

/**
 * 设置圆角边框
 */

@JvmOverloads
fun View.setRoundRectStroke(color: Int = Color.WHITE, cornerRadius: Int = 8, strokeWidth: Int = 0) {
    background = GradientDrawable().apply {
        setCornerRadius(cornerRadius.toFloat())
        setStroke(strokeWidth, color)
    }
}

/**
 * 设置圆角背景，适用于4个角不同大小
 * 背景色
 * 圆角大小数组
 */

@JvmOverloads
fun View.setRoundRectBg(color: Int = Color.WHITE, cornerRadius: FloatArray) {
    background = GradientDrawable().apply {
        setColor(color)
        setCornerRadii(cornerRadius)
    }
}