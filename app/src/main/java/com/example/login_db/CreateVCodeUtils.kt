package com.example.login_db

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import java.util.*

class CreateVCodeUtils private constructor() {


    companion object {
        private var createVCodeUtils: CreateVCodeUtils? = null
        //default settings
        //验证码默认随机数的个数
        private const val DEFAULT_CODE_LENGTH = 4
        //默认字体大小
        private const val DEFAULT_FONT_SIZE = 25
        //默认线条的条数
        private const val DEFAULT_LINE_NUMBER = 5
        //padding值
        private const val BASE_PADDING_LEFT = 10 //padding值
        private const val RANGE_PADDING_LEFT = 15 //padding值
        private const val BASE_PADDING_TOP = 15 //padding值
        private const val RANGE_PADDING_TOP = 20

        //验证码的默认宽高
        private const val DEFAULT_WIDTH = 100  //验证码的默认宽高
        private const val DEFAULT_HEIGHT = 40


        //随机数数组
        private val CHARS = charArrayOf(
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j', 'k', 'm',
            'n', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
            'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
        )

        fun getInstance(): CreateVCodeUtils {
            if (createVCodeUtils == null) {
                createVCodeUtils = CreateVCodeUtils()
            }
            return createVCodeUtils!!
        }


    }

    //settings decided by the layout xml
//canvas width and height
    private var width = DEFAULT_WIDTH  //settings decided by the layout xml
    //canvas width and height
    private var height = DEFAULT_HEIGHT

    //random word space and pading_top
    private var base_padding_left = BASE_PADDING_LEFT  //random word space and pading_top
    private var range_padding_left = RANGE_PADDING_LEFT
    //random word space and pading_top
    private var base_padding_top = BASE_PADDING_TOP //random word space and pading_top
    private val range_padding_top = RANGE_PADDING_TOP

    //number of chars, lines; font size
    private var codeLength = DEFAULT_CODE_LENGTH  //number of chars, lines; font size
    private var line_number = DEFAULT_LINE_NUMBER  //number of chars, lines; font size
    private var font_size = DEFAULT_FONT_SIZE


    //variables
    private var code: String? = null
    private var padding_left = 0
    private var padding_top: Int = 0
    private var random: Random = Random()

    /**
     * 创建图片验证码
     */
    fun createBitmap(): Bitmap {
        padding_left = 0
        padding_top = 0
        val bp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        code = createCode()
        val c = Canvas(bp)
        c.drawColor(Color.WHITE)
        val paint = Paint()
        paint.setAntiAlias(true);
        paint.setTextSize(font_size.toFloat())

        /**
         * 话验证码
         */
        for (i in 0 until codeLength) {
            randomTextStyle(paint)
            randomPadding()
            val c1 = code!![i]
            c.drawText(code!![i] + "", padding_left.toFloat(), padding_top.toFloat(), paint)
        }

        /**
         * 画线条
         */
        for (i in 0 until line_number) {
            drawLine(c, paint)
        }

        c.save()
        c.restore()
        return bp
    }

    /**
     * 获取当前生成的二维码
     */
    fun getCode(): String? = code

    /**
     * 画线条
     */
    private fun drawLine(c: Canvas, paint: Paint) {
        val color = randomColor(1)
        val startX = random.nextInt(width).toFloat()
        val startY = random.nextInt(height).toFloat()
        val stopX = random.nextInt(width).toFloat()
        val stopY = random.nextInt(height).toFloat()

        paint.setStrokeWidth(1.toFloat());
        paint.setColor(color);
        c.drawLine(startX, startY, stopX, stopY, paint)

    }

    /**
     * 生成验证码
     */
    fun createCode(): String {
        val buffer = StringBuilder()

        for (i in 0 until codeLength) {
            buffer.append(CHARS[random.nextInt(CHARS.size)])
        }
        return buffer.toString()
    }

    /**
     * 随机生成 文字样式，颜色，粗细，倾斜
     */
    fun randomTextStyle(paint: Paint) {

        val randomColor = randomColor(1)
        paint.setColor(randomColor)
        //random.nextBoolean() true 表示粗体，false 表示非粗体
        paint.setFakeBoldText(random.nextBoolean())
        var skewX = (random.nextInt(11) / 10).toFloat()
        skewX = if (random.nextBoolean()) skewX else -skewX
        //表示 flot 正数左斜 负数 右斜
        paint.textSkewX = skewX
    }

    /**
     * 随机生成颜色
     */
    private fun randomColor(rate: Int): Int {
        val red = random.nextInt(256) / rate
        val green = random.nextInt(256) / rate
        val blue = random.nextInt(256) / rate
        return Color.rgb(red, green, blue)
    }

    /**
     * 随机生成padding值
     */
    private fun randomPadding() {
        padding_left += base_padding_left + random.nextInt(range_padding_left)
        padding_top = base_padding_top + random.nextInt(range_padding_top)
    }
}