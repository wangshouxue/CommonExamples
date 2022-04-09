package com.wsx.mvpdemo.presenter

import com.wsx.mvpdemo.MVPView
import com.wsx.mvpdemo.entity.DataEntity
import com.wsx.mvpdemo.model.HttpModel
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer


/**
 * @author:wangshouxue
 * @date:2022/4/1 下午2:00
 * @description:类作用
 */
class MPresenter {
    private var view: MVPView? = null
    private var model: HttpModel? = null

    constructor(view: MVPView){
        this.view=view
        model=HttpModel()
    }
    fun request(){
        model?.getRequestData {
            view?.updateText(it?.text?:"")
        }
    }
    fun detachView() {
        view = null
    }

    //RxJAVA观察者模式写法
    var disposable: Disposable? = null
    fun requestRJ(){
        disposable=model?.requestData()?.subscribe(object : Consumer<DataEntity> {
            override fun accept(t: DataEntity?) {
                view?.updateText(t?.text?:"")
            }
        }, object :Consumer<Throwable> {
            override fun accept(t: Throwable?) {
            }
        })
    }

    fun detach() {
        disposable?.dispose()
    }
}