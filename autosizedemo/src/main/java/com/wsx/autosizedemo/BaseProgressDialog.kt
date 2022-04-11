package com.wsx.autosizedemo

import android.app.ProgressDialog
import android.content.Context

/**
 * @author:wangshouxue
 * @date:2022/4/11 上午10:57
 * @description:类作用
 */
class BaseProgressDialog :ProgressDialog{
    constructor(context: Context):super(AutoSizeHelper.adjustAutoSize(context))
    constructor(context: Context,theme:Int):super(AutoSizeHelper.adjustAutoSize(context),theme)
}