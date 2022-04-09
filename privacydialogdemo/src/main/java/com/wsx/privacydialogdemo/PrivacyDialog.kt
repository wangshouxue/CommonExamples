package com.wsx.privacydialogdemo

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton

/**
 * @author:wangshouxue
 * @date:2022/4/7 上午8:43
 * @description:类作用
 */
class PrivacyDialog : Dialog {
    private var mContext: Context?=null
    private var mConTv: TextView?=null
    private var mOkBt: AppCompatButton?=null
    private var mCancleBt: AppCompatButton?=null
    private var spannableStringBuilder: SpannableStringBuilder? = null

    constructor(context: Context):this(context, R.style.DialogTheme){
        this.mContext=context
        initView()
    }
    constructor(context: Context, theme:Int):super(context, theme){
        this.mContext=context
        initView()
    }
    fun initView(){
        setContentView(R.layout.dialog_privacy)
        mConTv = findViewById(R.id.con_privacy)
        mOkBt = findViewById(R.id.ok_privacy)
        mCancleBt = findViewById(R.id.cancel_privacy)
        val ll=findViewById<LinearLayout>(R.id.ll_privacy)
        ll.setRoundRectBg(Color.WHITE,12)
        mCancleBt?.setRoundRectStroke(Color.parseColor("#dddddd"),20,1)
        mOkBt?.setRoundRectBg(Color.parseColor("#4c9ee8"),20)

        mConTv?.setMovementMethod(LinkMovementMethod.getInstance())

        setCanceledOnTouchOutside(false)
        setCancelable(false)
        spannableStringBuilder= SpannableStringBuilder()
        spannableStringBuilder?.append("请您认真阅读")
        val serviceSp= SpannableString("《用户协议》")
        serviceSp.setSpan(object : ClickableSpan() {
            override fun onClick(widget: View) {
                Toast.makeText(mContext,"我是用户协议",Toast.LENGTH_SHORT).show()
            }
            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.color=Color.TRANSPARENT
                ds.setUnderlineText(false)
            }
        }, 0, serviceSp.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        serviceSp.setSpan(
            ForegroundColorSpan(Color.parseColor("#4c9ee8")), 0,
            serviceSp.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannableStringBuilder?.append(serviceSp)
        spannableStringBuilder?.append("和")
        val privacySp= SpannableString("《隐私政策》")
        privacySp.setSpan(object : ClickableSpan() {
            override fun onClick(widget: View) {
                Toast.makeText(mContext,"我是隐私政策",Toast.LENGTH_SHORT).show()
            }
            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.color=Color.TRANSPARENT
                ds.setUnderlineText(false)
            }
        }, 0, privacySp.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        privacySp.setSpan(
            ForegroundColorSpan(Color.parseColor("#4c9ee8")), 0,
            privacySp.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannableStringBuilder?.append(privacySp)
        spannableStringBuilder?.append("的全部条款，接受后可开始使用我们的服务。")
        mConTv?.setText(spannableStringBuilder)
        mCancleBt?.setOnClickListener { dismiss() }
    }

    fun showDialog(okListener:() -> Unit){
        mOkBt?.setOnClickListener {
            okListener()
            dismiss()
        }
        show()
    }
}