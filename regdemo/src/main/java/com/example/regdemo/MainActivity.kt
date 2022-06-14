package com.example.regdemo

import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextUtils
import android.text.style.AbsoluteSizeSpan
import android.text.style.StyleSpan
import android.text.style.TypefaceSpan
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.regex.Pattern

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val tv1=findViewById<TextView>(R.id.tv1)
        val tv2=findViewById<TextView>(R.id.tv2)
        val bt=findViewById<TextView>(R.id.bt)
        bt.setOnClickListener{
            tv1.text=setDes()
            tv2.text=setDes2()
        }
    }
    private fun setDes(): SpannableStringBuilder? {
        val des: String="￥50+100千帆币"
        //xml按数字的样式写的，此处只需更改￥，+以及牛币的样式
        val spannableStringBuilder = SpannableStringBuilder(des)
        //￥
        val pos = des.indexOf("￥")
        if (des.contains("￥")) {
            val nameSize1: AbsoluteSizeSpan = AbsoluteSizeSpan(33)
            spannableStringBuilder.setSpan(
                nameSize1,
                pos,
                pos + 1,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
        val index = des.indexOf("+")
        //+
        if (des.contains("+")) {
            val nameSize2: AbsoluteSizeSpan = AbsoluteSizeSpan(33)
            spannableStringBuilder.setSpan(
                nameSize2,
                index,
                index + 1,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
        //汉字
        val chinaReg = "[\u4e00-\u9fa5]{0,}"
        val p = Pattern.compile(chinaReg)
        val m = p.matcher(spannableStringBuilder)
        while (m.find()) {
            val start = m.start()
            val con = m.group()
            val nameSize3: AbsoluteSizeSpan = AbsoluteSizeSpan(27)
            spannableStringBuilder.setSpan(
                nameSize3,
                start,
                start + con.length,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
        return spannableStringBuilder
    }

    private fun setDes2(): SpannableStringBuilder? {
        val des = "￥50+100千帆币"
        //xml按￥，+的样式写的，此处只需更改数字及牛币的样式
        val spannableStringBuilder = SpannableStringBuilder(des)
        var p = Pattern.compile("[0-9]{0,}")
        var m = p.matcher(spannableStringBuilder)

        while (m.find()) {
            var numCon = m.group()
            if (!TextUtils.isEmpty(numCon)){
                var numStart = m.start()
                var numSize = AbsoluteSizeSpan(80)
                spannableStringBuilder.setSpan(
                    numSize,
                    numStart,
                    numStart + numCon.length,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                spannableStringBuilder.setSpan(
                    StyleSpan(Typeface.BOLD),
                    numStart,
                    numStart + numCon.length,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
        }
        p =Pattern.compile("[\u4e00-\u9fa5]{0,}")
        m = p.matcher(spannableStringBuilder)
        while (m.find()) {
            var chinaCon =  m.group()
            if (!TextUtils.isEmpty(chinaCon)){
                var chinaStart = m.start()
                var chinaSize= AbsoluteSizeSpan(30)
                spannableStringBuilder.setSpan(
                    chinaSize,
                    chinaStart,
                    chinaStart + chinaCon.length,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
        }

        return spannableStringBuilder
    }

}