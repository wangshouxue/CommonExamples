package com.example.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.text.Layout
import android.text.StaticLayout
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText


/**
 * @Description: 类作用描述
 */
class CusEditText @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : AppCompatEditText(context, attrs) {

    //自定义的绘制,总的调度
    override fun draw(canvas: Canvas?) {
        canvas?.drawColor(Color.GREEN)

        super.draw(canvas)
        //如果把绘制代码写在 super.draw() 的下面，那么这段代码会在其他所有绘制完成之后再执行，也就是说，它的绘制内容会盖住其他的所有绘制内容。
        //如果把绘制代码写在 super.draw() 的上面，那么这段代码会在其他所有绘制之前被执行，所以这部分绘制内容会被其他所有的内容盖住
    }
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
//        val staticLayout = StaticLayout.Builder().setText("").build()
        val staticLayout = StaticLayout(
            "text2aaaaaaaaaaaabb", paint, 600,
            Layout.Alignment.ALIGN_NORMAL, 1f, 0f, true
        )
        canvas?.save()
        canvas?.translate(50f, 100f)
        staticLayout.draw(canvas)
        canvas?.restore()
    }

    //绘制子View的
    override fun dispatchDraw(canvas: Canvas?) {
        super.dispatchDraw(canvas)
        canvas?.drawText("自定义",0f,50f,paint) //drawText() 方法参数中的 y 值，就是指定的基线的位置。

    }
    //绘制滑动边缘渐变和滑动条,前景
    override fun onDrawForeground(canvas: Canvas?) {
        super.onDrawForeground(canvas)
    }
}