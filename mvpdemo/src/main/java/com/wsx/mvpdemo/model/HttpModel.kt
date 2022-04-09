package com.wsx.mvpdemo.model

import com.wsx.mvpdemo.entity.DataEntity
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * @author:wangshouxue
 * @date:2022/4/1 下午2:01
 * @description:类作用
 */
class HttpModel {
    fun getRequestData(mListener: (DataEntity) -> Unit){
        GlobalScope.launch(Dispatchers.IO){
            //.....
            GlobalScope.launch(Dispatchers.Main) {
                mListener(DataEntity("我是网络数据"))
            }
        }
    }

    //RxJAVA观察者模式写法
    fun requestData(): Observable<DataEntity> {
        return Observable.create(object : ObservableOnSubscribe<DataEntity> {
            override fun subscribe(emitter: ObservableEmitter<DataEntity>) {
                emitter.onNext(DataEntity("我是网络数据"))
                emitter.onComplete()
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

}