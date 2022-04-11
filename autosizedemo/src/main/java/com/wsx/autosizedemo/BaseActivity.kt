package com.wsx.autosizedemo

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import me.jessyan.autosize.AutoSize
import me.jessyan.autosize.AutoSizeConfig

/**
 * @author:wangshouxue
 * @date:2022/4/11 上午10:04
 * @description:类作用
 */
abstract class BaseActivity:AppCompatActivity() {
    private var mIsUseAutoSize=true

    fun setIsUseAutoSize(mIsUseAutoSize:Boolean){
        this.mIsUseAutoSize=mIsUseAutoSize
    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        if (mIsUseAutoSize) {
            val initDensity: Float = AutoSizeConfig.getInstance().getInitDensity()
            //如果开启了AutoSize，并且当前density与初始时density相同，则说明适配失效（比如因为webview），重新适配
            if (resources.displayMetrics.density == initDensity) {
                AutoSizeHelper.judgeAndFitWidth(applicationContext)
                AutoSizeHelper.resetScreenSize(this)
                AutoSize.autoConvertDensityOfGlobal(this)
            }
        }
        return super.onCreateView(name, context, attrs)
    }
}