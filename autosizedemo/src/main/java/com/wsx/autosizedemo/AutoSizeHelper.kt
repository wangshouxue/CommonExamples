package com.wsx.autosizedemo

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.pm.ActivityInfo
import android.content.res.Resources
import android.graphics.Point
import android.view.Display
import me.jessyan.autosize.AutoSizeConfig

/**
 * @author:wangshouxue
 * @date:2022/4/11 上午10:09
 * @description:类作用
 */
class AutoSizeHelper {
    companion object{
        /**
         * 始终使用竖屏的宽度来适配。防止旋转过后，屏幕中的图标文字过大。
         * @param activity
         */
        fun resetScreenSize(activity: Activity) {
            if (activity.requestedOrientation != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
                AutoSizeConfig.getInstance().setScreenWidth(getScreenWidth(activity))
            } else {
                AutoSizeConfig.getInstance().setScreenWidth(getScreenHeight(activity))
            }
        }

        fun judgeAndFitWidth(applicationContext: Context) {
            if (isFoldDevice(applicationContext)) {
                AutoSizeConfig.getInstance().setDesignWidthInDp(700)
            } else {
                AutoSizeConfig.getInstance().setDesignWidthInDp(375)
            }
        }

        /**
         * 用于Dialog，防止宽高异常(Dialog中使用的Context使用该方法转化下)
         * @param context
         * @return
         */
        fun adjustAutoSize(context: Context): Context{
            return object : ContextWrapper(context) {
                private var mResources: Resources? = null
                override fun getResources(): Resources {
                    mResources!!.displayMetrics.density = AutoSizeConfig.getInstance().getInitDensity()
                    return mResources!!
                }

                init {
                    val oldResources = super.getResources()
                    mResources = Resources(
                        oldResources.assets,
                        oldResources.displayMetrics,
                        oldResources.configuration
                    )
                }
            }
        }

        fun getScreenWidth(activity: Activity): Int {
            val display: Display = activity.getWindowManager().getDefaultDisplay()
            val size = Point()
            display.getSize(size)
            return size.x
        }
        fun getScreenHeight(activity: Activity): Int {
            val display = activity.windowManager.defaultDisplay
            val size = Point()
            display.getSize(size)
            return size.y
        }

        /**
         * 如果高/宽小于1.25，则视为折叠屏
         * @param context
         * @return
         */
        fun isFoldDevice(context: Context): Boolean {
            return screenHeight(context) * 1.0f / screenWidth(context) < 1.25f
        }

        /**
         * 获取屏幕高 这种方式在虚拟导航栏可被隐藏并隐藏时，获取的仍是减去了虚拟导航栏高度的宽
         * 故推荐使用 [.getScreenHeight]的方式
         *
         * @param context 页面上下文
         * @return int 屏幕高
         */
        fun screenHeight(context: Context): Int {
            return context.resources.displayMetrics.heightPixels
        }
        fun screenWidth(context: Context): Int {
            return context.resources.displayMetrics.widthPixels
        }
    }
}