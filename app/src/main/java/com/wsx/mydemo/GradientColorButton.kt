package com.wsx.mydemo

import android.content.Context
import android.content.res.TypedArray
import android.graphics.*
import android.opengl.ETC1.getHeight
import android.opengl.ETC1.getWidth
import android.text.TextUtils
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatTextView

class GradientColorButton: View {
    /**
     * 文本
     */
    private var text = ""
    /**
     * 文本是否加粗
     */
    private var isBoldText=true
    /**
     * 文本颜色
     */
    private var textcolor:Int=Color.parseColor("#ff00ff")
    /**
     * 文本大小
     */
    private var textsize=48f
    /**
     * 颜色的宽度
     */
    private var colorWidth:Float=0.5f
    /**
     * 圆角度数
     */
    private var radius:Float=24f

    //渐变的颜色
    private var colorStart = Color.parseColor("#E38746")
    private var color1 = Color.parseColor("#B620E0")
    private var colorEnd = Color.parseColor("#5995F6")

    //控件的宽高
    private var widthView:Int=0
    private var heightView:Int=0
    /**
     * 渐变颜色的Bitmap
     */
    private lateinit var bitmapColor:Bitmap

    //画笔
    private lateinit var paintColor:Paint
    private lateinit var paintFillet:Paint
    private lateinit var paintWhite:Paint
    private lateinit var paintText:Paint
    //字体的宽高
    private var widthFont=0
    private var heightFont=0

    constructor(context:Context):super(context)
    constructor(context:Context, attrs: AttributeSet):this(context, attrs, 0)
    constructor(context:Context, attrs: AttributeSet,defStyleAttr:Int):super(context, attrs, defStyleAttr) {
      //获取参数
        val a :TypedArray= context.obtainStyledAttributes(attrs, R.styleable.GradientColorButton, defStyleAttr, 0)
        text = a.getString(R.styleable.GradientColorButton_text)?:""
        isBoldText= a.getBoolean(R.styleable.GradientColorButton_isBold,true)
        textcolor = a.getColor(R.styleable.GradientColorButton_textColor, Color.parseColor("#ff00ff"))
        textsize = a.getDimension(R.styleable.GradientColorButton_textsize, 48f)
        colorWidth = a.getDimension(R.styleable.GradientColorButton_borderWidth, 3f)
        radius = a.getDimension(R.styleable.GradientColorButton_radius, 30f)
        colorStart =a.getColor(R.styleable.GradientColorButton_colorStart, Color.parseColor("#E38746"))
        color1 = a.getColor(R.styleable.GradientColorButton_colorCenter, Color.parseColor("#B620E0"))
        colorEnd = a.getColor(R.styleable.GradientColorButton_colorEnd, Color.parseColor("#5995F6"))
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        //获取View的宽高
        widthView = getWidth()
        heightView = getHeight()

        //制作一个渐变效果的Bitmap
        createGradientBitmap()

        //初始化圆角配置
        initFilletConfiguration()

        //初始化白色Bitmap配置
        initWhiteBitmapConfiguration()

        //初始化文本配置
        initTextConfiguration()

    }


    /**
     * 创建渐变颜色的Bitmap
     */
    fun createGradientBitmap() {
        //创建存放渐变效果的bitmap
        bitmapColor = Bitmap.createBitmap(widthView, heightView, Bitmap.Config.ARGB_8888)
        var canvasColor = Canvas(bitmapColor)
        var backGradient = LinearGradient(0f, heightView.toFloat(), widthView.toFloat(), 0f, intArrayOf(colorStart, color1, colorEnd), null, Shader.TileMode.CLAMP)
        //绘画渐变效果
        paintColor = Paint()
        paintColor.setShader(backGradient)
        canvasColor.drawRect(0f, 0f, widthView.toFloat(), heightView.toFloat(), paintColor)
    }


    /**
     * 初始化圆角配置
     */
    fun initFilletConfiguration() {
        //绘画出圆角渐变效果
        var bitmapShaderColor = BitmapShader(bitmapColor, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        // 初始化画笔
        paintFillet = Paint()
        paintFillet.setAntiAlias(true)
        paintFillet.setShader(bitmapShaderColor)
    }

    /**
     * 初始化白色Bitmap配置
     */
    fun initWhiteBitmapConfiguration() {
        //创建存放白底的bitmap
        var bitmapWhite = Bitmap.createBitmap( (widthView - colorWidth * 2).toInt(),(heightView - colorWidth * 2).toInt(), Bitmap.Config.RGB_565)
        bitmapWhite.eraseColor(Color.parseColor("#FFFFFF"))

        var bitmapShaderWhite = BitmapShader(bitmapWhite, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        // 初始化画笔
        paintWhite = Paint()
        paintWhite.setAntiAlias(true)
        paintWhite.setShader(bitmapShaderWhite)
    }

    /**
     * 初始化文本配置
     */
    fun initTextConfiguration() {
        var rect = Rect()
        paintText = Paint()
        paintText.setAntiAlias(true)
        paintText.setColor(textcolor)
        paintText.isFakeBoldText=isBoldText
        paintText.setTextSize(textsize)
        if (!TextUtils.isEmpty(text)) {
            paintText.getTextBounds(text, 0, text.length, rect)
            widthFont = rect.width() //文本的宽度
            heightFont = rect.height() //文本的高度

        }
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        //将圆角渐变bitmap绘画到画布中
        canvas?.drawRoundRect( RectF(0f, 0f, width.toFloat(), height.toFloat()), radius, radius, paintFillet)
        // 将白色Bitmap绘制到画布上面
        canvas?.drawRoundRect( RectF(colorWidth, colorWidth, width - colorWidth, height - colorWidth), radius, radius, paintWhite)


        if (!TextUtils.isEmpty(text)) {
            canvas?.drawText(text, ((width - widthFont) / 2).toFloat(), ((height + heightFont) / 2).toFloat(), paintText)
        }

    }

}