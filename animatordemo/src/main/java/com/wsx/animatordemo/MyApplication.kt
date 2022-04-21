package com.wsx.animatordemo

import android.app.Application
import kotlin.system.exitProcess

/**
 * @author:wangshouxue
 * @date:2022/4/19 下午3:45
 * @description:类作用
 */
class MyApplication:Application(), Thread.UncaughtExceptionHandler {
    var threadCrashHandler: Thread.UncaughtExceptionHandler? = null

    override fun onCreate() {
        super.onCreate()
        //获取系统默认的UncaughtException处理器
        threadCrashHandler = Thread.getDefaultUncaughtExceptionHandler()
        //设置该CrashHandler为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this)
    }
    override fun uncaughtException(t: Thread, e: Throwable) {
        //自定义错误处理,收集错误信息 发送错误报告等操作均在此完成
//        handleException(e)
        //退出程序
        android.os.Process.killProcess(android.os.Process.myPid())
        exitProcess(1)
    }
}