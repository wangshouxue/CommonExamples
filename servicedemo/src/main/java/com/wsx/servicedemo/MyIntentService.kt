package com.wsx.servicedemo

import android.app.IntentService
import android.content.Intent
import androidx.core.app.JobIntentService

/**
 * @author:wangshouxue
 * @date:2022/4/22 上午9:20
 * @description:类作用
 */
class MyIntentService(name: String?) : IntentService(name) {
    override fun onHandleIntent(intent: Intent?) {

    }
}

//class MyIntentService:JobIntentService(){
//    override fun onHandleWork(intent: Intent) {
//
//    }
//
//}