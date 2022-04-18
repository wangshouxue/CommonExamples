package com.wsx.imagedemo

import android.graphics.Outline
import android.view.View
import android.view.ViewOutlineProvider

/**
 * @author:wangshouxue
 * @date:2022/4/18 下午5:06
 * @description:类作用
 */
class CornerViewOutlineProvider private constructor(private val radius: Float) : ViewOutlineProvider() {

    override fun getOutline(view: View, outline: Outline) {
        outline.setRoundRect(0, 0, view.width, view.height, radius)
    }

    companion object {
        @JvmStatic
        fun clipView(view: View, radius: Int) {
            view.outlineProvider = CornerViewOutlineProvider(radius.toFloat())
            view.clipToOutline = true
        }
    }

}