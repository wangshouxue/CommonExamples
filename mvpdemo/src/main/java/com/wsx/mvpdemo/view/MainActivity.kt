package com.wsx.mvpdemo.view

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.wsx.mvpdemo.MVPView
import com.wsx.mvpdemo.R
import com.wsx.mvpdemo.databinding.ActivityMainBinding
import com.wsx.mvpdemo.presenter.MPresenter

/**
 * @author:wangshouxue
 * @date:2022/4/1 下午1:56
 * @description:类作用
 */
class MainActivity: AppCompatActivity(),MVPView {
    var tv:TextView?=null
    var presenter:MPresenter?=null
    lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val rootView: View = binding.getRoot()
        setContentView(rootView)
//        binding.tv

//        setContentView(R.layout.activity_main)
        tv=findViewById<TextView>(R.id.tv)
        presenter=MPresenter(this)
        tv?.setOnClickListener {
            presenter?.requestRJ()
        }
    }

    override fun updateText(text: String) {
        tv?.text=text
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter?.detachView()
    }
}