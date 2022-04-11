package com.wsx.autosizedemo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import me.jessyan.autosize.AutoSize

/**
 * @author:wangshouxue
 * @date:2022/4/11 上午11:01
 * @description:类作用
 */
class BaseFragment :Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        AutoSizeHelper.judgeAndFitWidth(activity?.applicationContext!!)
        AutoSizeHelper.resetScreenSize(requireActivity())
        AutoSize.autoConvertDensityOfGlobal(activity)

        return super.onCreateView(inflater, container, savedInstanceState)
    }
}